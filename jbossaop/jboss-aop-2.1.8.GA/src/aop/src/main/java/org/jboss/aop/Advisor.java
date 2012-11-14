/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */
package org.jboss.aop;

import gnu.trove.TLongObjectHashMap;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.CFlowInterceptor;
import org.jboss.aop.advice.ClassifiedBindingAndPointcutCollection;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceSorter;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aop.annotation.AnnotationRepository;
import org.jboss.aop.instrument.ConstructionTransformer;
import org.jboss.aop.instrument.ConstructorExecutionTransformer;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.joinpoint.ConstructorJoinpoint;
import org.jboss.aop.joinpoint.FieldJoinpoint;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.InvocationResponse;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.ConstructorMetaData;
import org.jboss.aop.metadata.FieldMetaData;
import org.jboss.aop.metadata.MethodMetaData;
import org.jboss.aop.metadata.SimpleMetaData;
import org.jboss.aop.pointcut.PointcutMethodMatch;
import org.jboss.aop.util.JoinPointComparator;
import org.jboss.aop.util.UnmodifiableEmptyCollections;
import org.jboss.aop.util.logging.AOPLogger;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.util.NestedRuntimeException;
import org.jboss.util.NotImplementedException;
import org.jboss.util.collection.ConcurrentSet;

/**
 * Manages the interceptor chains of an aspect context (usually, this context is
 * composed by either all joinpoints of a class, or the joinponts of an instance).
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 94084 $
 */
public abstract class Advisor
{
   /**
    * Returns the {@code MethodInfo} that represents the execution of the method
    * identified by {@code hash}.
    * 
    * @param hash a hash code that identifies uniquely a method inside the context.
    * @return     a {@code MethodInfo} representing the queried method execution
    *             joinpoint.
    */
   public MethodInfo getMethodInfo(long hash)
   {
      return methodInfos.getMethodInfo(hash);
   }

   /**
    * Identifies an advice.
    */
   private class AdviceInterceptorKey
   {
      private String adviceName;
      private Joinpoint joinpoint;
      private int hash;

      public AdviceInterceptorKey(String adviceName, Joinpoint joinpoint)
      {
         this.adviceName = adviceName;
         this.joinpoint = joinpoint;
         hash = adviceName.hashCode();
         hash = 29 * hash + (joinpoint != null ? joinpoint.hashCode() : 0);
      }

      @Override
      public boolean equals(Object o)
      {
         if (this == o) return true;
         if (!(o instanceof AdviceInterceptorKey)) return false;

         final AdviceInterceptorKey adviceInterceptorKey = (AdviceInterceptorKey) o;

         if (!adviceName.equals(adviceInterceptorKey.adviceName)) return false;
         if (joinpoint != null ? !joinpoint.equals(adviceInterceptorKey.joinpoint) : adviceInterceptorKey.joinpoint != null) return false;

         return true;
      }

      @Override
      public int hashCode()
      {
         return hash;
      }
   }

   /** Read/Write lock to be used when lazy creating the collections */
   protected Object lazyCollectionLock = new Object();

   /** The collection of bindings that are applied to one or more joinpoints in the advised context. */
   protected Set<AdviceBinding> adviceBindings = new HashSet<AdviceBinding>();
   /** The collection of interface introductions that affect the advised context. */
   protected volatile ArrayList<InterfaceIntroduction> interfaceIntroductions = UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
   /** The collection of class metadata bindings that are applied to the advised context. */
   protected volatile ArrayList<ClassMetaDataBinding> classMetaDataBindings = UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
   /** Meta data that apply to all advised joinpoints. */
   protected SimpleMetaData defaultMetaData = new SimpleMetaData();
   /** Method execution joinpoints meta data.*/
   protected MethodMetaData methodMetaData = new MethodMetaData();
   /** Field read/write joinpoints meta data. */
   protected FieldMetaData fieldMetaData = new FieldMetaData();
   /** The target class meta data.*/
   protected SimpleMetaData classMetaData = new SimpleMetaData();
   /** Constructor execution joinpoints meta data. */
   protected ConstructorMetaData constructorMetaData = new ConstructorMetaData();
   //Does not seem to be used
   //protected HashMap classAnnotations = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   /** Repository that contains all annotations applied to the context of this advisor */
   protected AnnotationRepository annotations = new AnnotationRepository();
   /** Indicates whether there is one or more aspects applied to this context. */
   protected boolean doesHaveAspects = false;
   /** Name that identifies this advisor. */
   protected String name;
   /** Contains all the aspect instances applied to this context. */
   protected ConcurrentHashMap<String, Object> aspects = new ConcurrentHashMap<String, Object>();
   /** Contains all the interceptor instances applied to this context. */
   protected HashMap<AspectDefinition, Map<String, Interceptor>> adviceInterceptors = new HashMap<AspectDefinition, Map<String, Interceptor>>();
   /** Contains all definitions of PER_INSTANCE aspects applied to this context. */
   protected volatile Set<AspectDefinition> perInstanceAspectDefinitions = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_SET;
   /** Contains all definitions of PER_JOINPOINT scoped aspects applied to this sccontext*/
   protected volatile ConcurrentHashMap<AspectDefinition, Set<Joinpoint>> perInstanceJoinpointAspectDefinitions = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   /** The {@code java.lang.String} class */
   static Class<?> cl = java.lang.String.class;
   /** Maps all advised methods to their interceptor chains. */
   protected volatile TLongObjectHashMap advisedMethods = UnmodifiableEmptyCollections.EMPTY_TLONG_OBJECT_HASHMAP;
   // The method signatures are sorted at transformation and load time to
   // make sure the tables line up.
   //Common sense suggests that this should be lazily initialised for generated advisors, profiling shows that is a major performance hit...
   /** @deprecated use methodInfos instead. These remain here for compatibility with EJB 3 in JBoss 4.x. See JBAOP-517
    * @see AspectManager#maintainAdvisorMethodInterceptors
    */
   protected TLongObjectHashMap methodInterceptors = new TLongObjectHashMap();
   /** Collection of method execution joinpoints. */
   protected MethodInterceptors methodInfos = new MethodInterceptors(this);;
   /** Domain to which this advisor belongs. */
   protected AspectManager manager;
   
   /** Target class of the advised joinpoint. */ 
   protected Class<?> clazz = null;
   
   /** Target class loader of the advised joinpoint. */
   protected WeakReference<ClassLoader> loader = null;
   /** Contains the constructors that belong to the advised context. */
   protected Constructor<?>[] constructors;

   /** @deprecated Use constructorInfos instead */
   protected Interceptor[][] constructorInterceptors;
   protected ConstructorInfo[] constructorInfos; //This should replace constructorInterceptors

   /** @deprecated Use constructorInfos instead */
   protected Interceptor[][] constructionInterceptors;
   protected ConstructionInfo[] constructionInfos;


   /** The advisor meta data. */
   private MetaData metadata;
   /**
    * Indicates that a call to factory create has already been made, but the factory
    * returned {@code null}.
    */
   protected static Object NULL_ASPECT = new Object();
   
   private static final AOPLogger logger = AOPLogger.getLogger(Advisor.class);
   
   //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
   //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
   
   /**
    * Constructor.
    * <p>
    * Do not use this method to create {@code Advisor} instances. Instead, use the
    * {@code create} methods of {@linke AdvisorFactory}.
    * 
    * @param name    identifies the advisor to be created.
    * @param manager the domain to which this advisor belongs.
    */
   public Advisor(String name, AspectManager manager)
   {
      this.name = name;
      this.manager = manager;
   }

   /**
    * Returns the list of constructors that belong to the advised context.
    * 
    * @return an array of constructors
    */
   public Constructor<?>[] getConstructors()
   {
      return constructors;
   }

   /** @deprecated Use getConstructorInfos instead*/
   public Interceptor[][] getConstructorInterceptors()
   {
      return constructorInterceptors;
   }

   /**
    * Returns the list of advised constructor execution joinpoints.
    * 
    * @return an array of {@code ConstructorInfo} objects
    */
   public ConstructorInfo[] getConstructorInfos()
   {
      return constructorInfos;
   }

   /** @deprecated Use getConstructionInfos instead*/
   public Interceptor[][] getConstructionInterceptors()
   {
      return constructionInterceptors;
   }

   /**
    * Returns the list of advised construction joinpoints.
    * 
    * @return an array of {@code ConstructionInfo} objects
    */
   public ConstructionInfo[] getConstructionInfos()
   {
      return constructionInfos;
   }

   /**
    * @deprecated Need a better mechanism to override the
    *             methods seen by pointcuts, e.g. those provided
    *             by a "proxy advisor"
    */
   public Method[] getAllMethods()
   {
      return null;
   }

   /**
    * Returns the aspect manager that represents the domain to which this
    * {@code Advisor} belongs.
    * 
    * @return an {@link AspectManager} instance.
    */
   public AspectManager getManager()
   {
      return manager;
   }

   /**
    * For use by generated advisors. They will explicitly set the manager.
    * 
    * @param manager the domain to which this {@code Advisor} belongs.
    */
   protected void setManager(AspectManager manager)
   {
      this.manager = manager;
   }

   /**
    * Returns the collection of class meta data bindings that are applied to the
    * advised context.
    * 
    * @return a list of {@code ClassMetaDataBinding}. This list is not a copy, and,
    *         hence, must not be edited.
    */
   public List<ClassMetaDataBinding> getClassMetadataBindings()
   {
      return classMetaDataBindings;
   }

   /**
    * Returns the target class meta data.
    * 
    * @return the target class meta data. This object is not a copy, and, hence, must
    *         not be edited.
    */
   public SimpleMetaData getClassMetaData()
   {
      return classMetaData;
   }

   /**
    * Returns the meta data that is applied to all joinpoints and to the target
    * class.
    * 
    * @return the meta data that apply to all joinpoints and to the target class.
    *         This object is not a copy, and, hence, must not be edited. 
    */
   public SimpleMetaData getDefaultMetaData()
   {
      return defaultMetaData;
   }

   /**
    * Returns the method joinpoints meta data
    * 
    * @return the method joinpoints meta data. This object is not a copy, and, hence,
    *         must not be edited. 
    */
   public MethodMetaData getMethodMetaData()
   {
      return methodMetaData;
   }

   /**
    * Returns the field joinpoints meta data
    * 
    * @return the field joinpoints meta data. This object is not a copy, and, hence,
    *         must not be edited. 
    */
   public FieldMetaData getFieldMetaData()
   {
      return fieldMetaData;
   }

   /**
    * Returns the constructor joinpoints meta data
    * 
    * @return the constructor joinpoints meta data. This object is not a copy, and,
    *         hence, must not be edited. 
    */
   public ConstructorMetaData getConstructorMetaData()
   {
      return constructorMetaData;
   }

   /**
    * Deploys to the advised context all {@code AnnotationIntroduction} that should
    * override current annotions, in case they are present.
    * <br>
    * The annotation introductions to be deployed are extracted from this advisor
    * domain ({@link AspectManager#getAnnotationOverrides()}).
    */
   public void deployAnnotationOverrides()
   {
      List<AnnotationIntroduction> annotationOverrides = getManager().getAnnotationOverrides();
      if (annotationOverrides != null)
      {
         for (int i = 0; i < annotationOverrides.size(); ++i)
         {
            AnnotationIntroduction introduction = annotationOverrides.get(i);
            deployAnnotationOverride(introduction);
         }
      }
   }

   /**
    * Deploys {@code introduction} to the advised context, overriding current
    * annotions with the same name, in case they are present.
    * 
    * @param introduction an annotation introduction that must be applied to the
    *        advised context.
    */
   public void deployAnnotationOverride(AnnotationIntroduction introduction)
   {
      if (System.getSecurityManager() == null)
      {
         DeployAnnotationOverrideAction.NON_PRIVILEGED.deploy(this, introduction);
      }
      else
      {
         DeployAnnotationOverrideAction.PRIVILEGED.deploy(this, introduction);
      }
   }

   /**
    * Deploys {@code introduction} to the advised context, overriding current
    * annotions with the same name, in case they are present.
    * <br>
    * <b>This method must not be called externally</b>, since it will perform
    * deployment from outside a privileged environment. Use
    * {@link #deployAnnotationOverride(AnnotationIntroduction)} instead.
    * 
    * @param introduction an annotation introduction that must be applied to the
    *        advised context.
    * 
    * @param introduction
    */
   public void doDeployAnnotationOverride(AnnotationIntroduction introduction)
   {
      if (introduction.matches(this, clazz))
      {
         try
         {
            annotations.addClassAnnotation(introduction.getAnnotation().getIdentifier(), introduction.getOriginalAnnotationExpr());
         }
         catch(Exception ignore)
         {
            //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
            //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
         }
      }

      Class<?> theClass = clazz;

      deployMethodAnnotationOverrides(theClass, introduction);
      Field[] fields = theClass.getDeclaredFields();
      for (int i = 0; i < fields.length; i++)
      {
         if (introduction.matches(this, fields[i]))
         {
            try
            {
               annotations.addAnnotation(fields[i], introduction.getAnnotation().getIdentifier(), introduction.getOriginalAnnotationExpr());
            }
            catch(Exception ignore)
            {
               //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
               //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
            }
         }
      }
      Constructor<?>[] cons = theClass.getDeclaredConstructors();
      for (int i = 0; i < cons.length; i++)
      {
         if (introduction.matches(this, cons[i]))
         {
            try
            {
               annotations.addAnnotation(cons[i], introduction.getAnnotation().getIdentifier(), introduction.getOriginalAnnotationExpr());
            }
            catch(Exception ignore)
            {
               //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
               //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
            }
         }
      }
   }

   /**
    * Deploys to the advised context all {@code InterfaceIntroduction} whose pointcut
    * matches one or more advised joinpoints.
    * <br>
    * The interface introductions to be deployed are extracted from this advisor
    * domain ({@link AspectManager#getInterfaceIntroductions()}).
    */
   protected void initializeInterfaceIntroductions(Class<?> theClass)
   {
      manager.applyInterfaceIntroductions(this, theClass);
   }

   /**
    * Recursively deploys all method annotation introductions, overriding annotations
    * with the same name.
    * <p><i>For internal use only.</i>
    * 
    * @param theClass      the class that contains the affected methods. Must be
    *                      equal to {@link #getClazz()} or to one of its super
    *                      classes.
    * @param introduction  the introduction to be deployed.
    */
   protected void deployMethodAnnotationOverrides(Class<?> theClass, AnnotationIntroduction introduction)
   {
      if (theClass.getSuperclass() != null)
      {
         deployMethodAnnotationOverrides(theClass.getSuperclass(), introduction);
      }
      Method[] methods = theClass.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++)
      {
         if (introduction.matches(this, methods[i]))
         {
            try
            {
               annotations.addAnnotation(methods[i], introduction.getAnnotation().getIdentifier(), introduction.getOriginalAnnotationExpr());
            }
            catch(Exception ignore)
            {
               //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
               //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
            }
         }
      }
   }

   /**
    * Returns the annotation repository used by this advisor to keep all annotations
    * that are applied to the advised joinpoints.
    * <p><i>For internal use only.</i>
    * 
    * @return the annotation repository.
    */
   public AnnotationRepository getAnnotations()
   {
      return annotations;
   }

   /**
    * Returns an object representing the meta data of type {@code annotation}, if
    * present. 
    * 
    * @param  annotation the type of meta data/annotation queried
    * @return the meta data/annotation of type {@code annotation}
    */
   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(annotation);
   }
   
   /**
    * Returns an object representing the meta data of type {@code annotation}, if
    * present. 
    * 
    * @param  annotation the type of meta data/annotation queried
    * @return the meta data/annotation of type {@code annotation}
    */
   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      if (metadata != null)
      {
         T value = metadata.getAnnotation(annotation);
         if (value != null) return value;
      }

      //Need to use the untyped version since that is used by EJB3

      if (annotations.isDisabled(annotation))
         return null;

      //MUST call this instead of AR.resolveTypedClassAnnotation since EJB3 overrides AR.resolveClassAnnotation
      T value = (T)annotations.resolveClassAnnotation(annotation);
      if (value == null && clazz != null && metadata == null)
      {
         value = AnnotationElement.getVisibleAnnotation(clazz, annotation);
      }
      return value;
   }

   /**
    * Indicates whether the advised class has an annotation/meta data whose name is
    * {@code annotation}.
    * 
    * @param  annotation the name of an annotation
    * @return {@code true} if there is an annotation/meta data whose name is {@code
    *         annotation}
    */
   public boolean hasAnnotation(String annotation)
   {
      return hasAnnotation(clazz, annotation);
   }

   /**
    * Indicates whether {@code tgt} has an annotation/meta data whose name is {@code
    * annotation}.
    * 
    * @param  tgt the target class whose annotations/meta data will be examined.
    * @param  annotation the name of an annotation
    * @return {@code true} if there is an annotation/meta data whose name is {@code
    *         annotation}
    */
   public boolean hasAnnotation(Class<?> tgt, String annotation)
   {
      return hasAnnotation(tgt, annotation, null);
   }

   /**
    * Indicates whether {@code tgt} has an annotation/meta data whose type is {@code
    * annotation}.
    * 
    * @param  tgt the target class whose annotations/meta data will be examined.
    * @param  annotation the type of an annotation
    * @return {@code true} if {@code tgt} has an annotation/meta data whose type is
    * {@code annotation}
    */
   public boolean hasAnnotation(Class<?> tgt, Class<? extends Annotation> annotation)
   {
      return hasAnnotation(tgt, null, annotation);
   }

   /**
    * Indicates whether {@code tgt} has an annotation/meta data whose type is {@code
    * annotationClass}, and name is {@code annotation}.
    * 
    * @param  tgt             the target class whose annotations/meta data will be
    *                         examined
    * @param  annotation      the name of an annotation
    * @param  annotationClass the type of an annotation
    * @return {@code true} if {@code tgt} has an annotation/meta data whose type is
    * {@code annotationClass} and name is {@code annotation}
    */
   private boolean hasAnnotation(Class<?> tgt, String annotation, Class<? extends Annotation> annotationClass)
   {
      if (annotation == null && annotationClass == null)
      {
         throw new RuntimeException("annotation or annotationClass must be passed in");
      }

      if (annotation == null)
      {
         annotation = annotationClass.getName();
      }

      if (metadata != null)
      {
         if (metadata.isMetaDataPresent(annotation)) return true;
      }

      try
      {
         if (annotations.hasClassAnnotation(annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      if (tgt == null) return false;
      try
      {
         if (metadata == null)
         {
            return AnnotationElement.isAnyAnnotationPresent(tgt, annotation);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   /**
    * Returns an object representing the method {@code m} annotation of type
    * {@code annotation}, if {@code m} has such annotation.
    * 
    * @param  m          the method whose annotation is being queried
    * @param  annotation the type of the queried annotation
    * @return the meta data/annotation of type {@code annotation} that apply to
    *         {@code m}
    */
   public Object resolveAnnotation(Method m, Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(0, m, annotation);
   }
   
   /**
    * Returns an object representing the method {@code m} annotation of type
    * {@code annotation}, if {@code m} has such annotation.
    * 
    * @param  m          the method whose annotation is being queried
    * @param  annotation the type of the queried annotation
    * @return the meta data/annotation of type {@code annotation} that apply to
    *         {@code m}
    */
   public <T extends Annotation> T resolveTypedAnnotation(Method m, Class<T> annotation)
   {
      return resolveTypedAnnotation(0, m, annotation);
   }

   /**
    * Returns an object representing the method {@code m} annotation of type
    * {@code annotation}, if {@code m} has such annotation.
    * 
    * @param hash        the hashcode of {@code m}
    * @param  m          the method whose annotation is being queried
    * @param  annotation the type of the queried annotation
    * @return the meta data/annotation of type {@code annotation} that apply to
    *         {@code m}
    */
   public Object resolveAnnotation(long hash, Method m, Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(hash, m, annotation);
   }
   
   /**
    * Returns an object representing the method {@code m} annotation of type
    * {@code annotation}, if {@code m} has such annotation.
    * 
    * @param hash        the hashcode of {@code m}
    * @param  m          the method whose annotation is being queried
    * @param  annotation the type of the queried annotation
    * @return the meta data/annotation of type {@code annotation} that apply to
    *         {@code m}
    */
   public <T extends Annotation> T resolveTypedAnnotation(long hash, Method m, Class<T> annotation)
   {
      if (metadata != null)
      {
         MethodSignature signature = new MethodSignature(m.getName(), m.getParameterTypes());
         MetaData methodMD = metadata.getComponentMetaData(signature);
         if (methodMD != null)
         {
            T val = methodMD.getAnnotation(annotation);
            if (val != null) return val;
         }
      }

      if (annotations.isDisabled(m,annotation))
         return null;

      //MUST call this instead of AR.resolveTypedClassAnnotation since EJB3 overrides AR.resolveClassAnnotation
      T value = (T)annotations.resolveAnnotation(m, annotation);
      if (value == null && metadata == null) 
      {
         value = AnnotationElement.getVisibleAnnotation(m, annotation);
      }
      return value;
   }

   /**
    * Returns an object representing the method {@code m} annotation whose type is
    * one of the elements in {@code annotationChoices}, if {@code m} has such
    * annotation.
    * 
    * @param  m                 the method whose annotation is being queried
    * @param  annotationChoices the queried annotation must have one of the types
    *                           contained in this array
    * @return the meta data/annotation that apply to {@code m} whose type is in
    *         {@code annotationChoices} 
    */
   public Object resolveAnnotation(Method m, Class<?>[] annotationChoices)
   {
      for (Class<?> ann : annotationChoices)
      {
         Object val = resolveTypedAnnotation(m, (Class<? extends Annotation>)ann);
         if (val != null) return val;
      }
      return null;
   }

   /**
    * Returns an object representing the method {@code m} annotation whose type is
    * one of the elements in {@code annotationChoices}, if {@code m} has such
    * annotation.
    * 
    * @param  m                 the method whose annotation is being queried
    * @param  annotationChoices the queried annotation must have one of the types
    *                           contained in this array
    * @return the meta data/annotation that apply to {@code m} whose type is in
    *         {@code annotationChoices} 
    */
   public <T extends Annotation> T resolveTypedAnnotation(Method m, Class<T>[] annotationChoices)
   {
      for (Class<T> ann : annotationChoices)
      {
         T val = resolveTypedAnnotation(m, ann);
         if (val != null) return val;
      }
      return null;
   }


   public Object resolveAnnotation(Field f, Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(f, annotation);
   }
   
   public <T extends Annotation> T resolveTypedAnnotation(Field f, Class<T> annotation)
   {
      T value = null;
      if (metadata != null)
      {
         FieldSignature signature = new FieldSignature(f);
         MetaData fieldMD = metadata.getComponentMetaData(signature);
         if (fieldMD != null)
         {
            value = fieldMD.getAnnotation(annotation);
            if (value != null) return value;
         }
      }
      
      //MUST call this instead of AR.resolveTypedClassAnnotation since EJB3 overrides AR.resolveClassAnnotation
      value = (T)annotations.resolveAnnotation(f, annotation);
      if (value == null && metadata == null)
      {
         value = AnnotationElement.getVisibleAnnotation(f, annotation);
      }
      return value;
   }

   public Object resolveAnnotation(Constructor<?> c, Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(c, annotation);
   }
   
   public <T extends Annotation> T resolveTypedAnnotation(Constructor<?> c, Class<T> annotation)
   {
      T value = null;
      if (metadata != null)
      {
         ConstructorSignature signature = new ConstructorSignature(c);
         MetaData conMD = metadata.getComponentMetaData(signature);
         if (conMD != null)
         {
            value = conMD.getAnnotation(annotation);
            if (value != null) return value;
         }
      }
      
      //MUST call this instead of AR.resolveTypedClassAnnotation since EJB3 overrides AR.resolveClassAnnotation
      value = (T)annotations.resolveAnnotation(c, annotation);
      if (value == null && metadata == null)
      {
         value = AnnotationElement.getVisibleAnnotation(c, annotation);
      }
      return value;
   }

   public boolean hasAnnotation(Method m, String annotation)
   {
      return hasAnnotation(0, m, annotation, null);
   }

   public boolean hasAnnotation(Method m, Class<? extends Annotation> annotation)
   {
      return hasAnnotation(0, m, null, annotation);
   }

   private boolean hasAnnotation(long hash, Method m, String annotation, Class<? extends Annotation> annotationClass)
   {
      if (annotation == null && annotationClass == null)
      {
         throw new RuntimeException("annotation or annotationClass must be passed in");
      }

      if (annotation == null)
      {
         annotation = annotationClass.getName();
      }
      if (metadata != null)
      {
         if (hasJoinPointAnnotation(m.getDeclaringClass(), new MethodSignature(m), annotation))
         {
            return true;
         }
      }

      try
      {
         // if annotationClass is null, it means that annotation is not null
         if (annotationClass != null)
         {
            if (annotations.hasAnnotation(m, annotationClass))
               return true;
         }
         else
         {
            if (annotations.hasAnnotation(m, annotation))
               return true;
         }
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      try
      {
         if (metadata == null)
         {
            return AnnotationElement.isAnyAnnotationPresent(m, annotation);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   public boolean hasAnnotation(Field m, Class<? extends Annotation> annotation)
   {
      return hasAnnotation(m, annotation.getName());
   }
   
   public boolean hasAnnotation(Field m, String annotation)
   {
      if (metadata != null)
      {
         if (hasJoinPointAnnotation(m.getDeclaringClass(), new FieldSignature(m), annotation))
         {
            return true;
         }
      }
      try
      {
         if (annotations.hasAnnotation(m, annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      try
      {
         if (metadata == null)
         {
            return AnnotationElement.isAnyAnnotationPresent(m, annotation);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   public boolean hasAnnotation(Constructor<?> m, String annotation)
   {
      if (metadata != null)
      {
         if (hasJoinPointAnnotation(m.getDeclaringClass(), new ConstructorSignature(m), annotation))
         {
            return true;
         }
      }
      try
      {
         if (annotations.hasAnnotation(m, annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      try
      {
         if (metadata == null)
         {
            return AnnotationElement.isAnyAnnotationPresent(m, annotation);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   private boolean hasJoinPointAnnotation(Class<?> declaringClass, org.jboss.metadata.spi.signature.Signature sig, String annotation)
   {
      if (metadata != null)
      {
         if (annotation != null)
         {
            MetaData md = metadata.getComponentMetaData(sig);
            if (md != null)
            {
               if (md.isMetaDataPresent(annotation))
                  return true;
            }
         }
      }
      return false;
   }
   
   public boolean hasAnnotation(CtClass clazz, String annotation)
   {
      try
      {
         if (annotations.hasClassAnnotation(annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      try
      {
         return AnnotationElement.isAnyAnnotationPresent(clazz, annotation);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public boolean hasAnnotation(CtMethod member, String annotation)
   {
      // todo these are here so that we can chain configuration domains
      try
      {
         if (annotations.hasAnnotation(member, annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      return AnnotationElement.isAnyAnnotationPresent(member, annotation);
   }

   public boolean hasAnnotation(CtField member, String annotation)
   {
      // todo these are here so that we can chain configuration domains
      try
      {
         if (annotations.hasAnnotation(member, annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      return AnnotationElement.isAnyAnnotationPresent(member, annotation);
   }

   public boolean hasAnnotation(CtConstructor member, String annotation)
   {
      // todo these are here so that we can chain configuration domains
      try
      {
         if (annotations.hasAnnotation(member, annotation)) return true;
      }
      catch(Exception ignore)
      {
         //When resolving annotations from the annotation repository we don't want to hit the annotation repository with the metadata from base-aspects.xml (@security, @transaction etc.)
         //EJB 3 uses a custom metadata loader that tries to load up all these classes by name, and that will cause errors when loading up the annotations
      }

      return AnnotationElement.isAnyAnnotationPresent(member, annotation);
   }

   /**
    * Get the metadata
    * 
    * @return the metadata
    */
   public MetaData getMetadata()
   {
      return metadata;
   }

   /**
    * Set the metadata
    * 
    * @param metadata the metadata
    */
   public void setMetadata(MetaData metadata)
   {
      this.metadata = metadata;
   }

   public String getName()
   {
      return name;
   }

   public final boolean hasAspects()
   {
      return doesHaveAspects;
   }

   public synchronized void removeAdviceBinding(AdviceBinding binding)
   {
      adviceBindings.remove(binding);
      rebuildInterceptorsForRemovedBinding(binding);
      doesHaveAspects = adviceBindings.size() > 0;
   }

   public synchronized void removeAdviceBindings(ArrayList<AdviceBinding> bindings)
   {
      adviceBindings.removeAll(bindings);
      rebuildInterceptors();
      doesHaveAspects = adviceBindings.size() > 0;
   }

   /**
    * a new binding has been added to the AspectManager, recalculate interceptors
    */
   public synchronized void newBindingAdded()
   {
      rebuildInterceptors();
      doesHaveAspects = adviceBindings.size() > 0;
   }
   
   public synchronized void newBindingAdded(AdviceBinding binding)
   {
      rebuildInterceptorsForAddedBinding(binding);
      doesHaveAspects = adviceBindings.size() > 0;
   }

   public ArrayList<InterfaceIntroduction> getInterfaceIntroductions()
   {
      return interfaceIntroductions;
   }

   public synchronized void addInterfaceIntroduction(InterfaceIntroduction pointcut)
   {
      initInterfaceIntroductionsList();      
      interfaceIntroductions.add(pointcut);
   }

   public synchronized void removeInterfaceIntroduction(InterfaceIntroduction pointcut)
   {
      interfaceIntroductions.remove(pointcut);
   }

   protected abstract void rebuildInterceptors();

   protected abstract void rebuildInterceptorsForAddedBinding(AdviceBinding binding);
   
   protected  abstract void rebuildInterceptorsForRemovedBinding(AdviceBinding removedBinding);

   /**
    * If the info was updated in response to a rebuildInterceptorsForAddedBinding call it will have the
    * original interceptors in the interceptors array, and the appended interceptors in the interceptorChain
    * List. We need to merge the two so that all the interceptors appear in the interceptorChain List before finalizing
    * the chain  
    */
   protected final void adjustInfoForAddedBinding(JoinPointInfo info)
   {
      Interceptor[] icptrs = info.getInterceptors();
      if (icptrs != null && icptrs.length > 0)
      {
         List<Interceptor> chain = info.getInterceptorChain();
         List<Interceptor> buf = new ArrayList<Interceptor>(chain.size() + icptrs.length);
         buf.addAll(Arrays.asList(icptrs));
         buf.addAll(chain);

         chain.clear();
         chain.addAll(buf);
      }
   }
   
   ////////////////////////////////
   // Metadata.  Metadata will be used for things like Transaction attributes (Required, RequiresNew, etc...)
   //

   public abstract void addClassMetaData(ClassMetaDataBinding data);

   public abstract void removeClassMetaData(ClassMetaDataBinding data);

   // This is aspect stuff.  Aspect again, is a class that encapsulates advices
   
   public Object getPerVMAspect(AspectDefinition def)
   {
      return getManager().getPerVMAspect(def);
   }
   
   public void addPerInstanceAspect(AspectDefinition def)
   {
      initPerInstanceAspectDefinitionsSet();
      perInstanceAspectDefinitions.add(def);
      def.registerAdvisor(this);
   }

   public void removePerInstanceAspect(AspectDefinition def)
   {
      perInstanceAspectDefinitions.remove(def);
   }

   public Set<AspectDefinition> getPerInstanceAspectDefinitions()
   {
      return perInstanceAspectDefinitions;
   }

   // This is aspect stuff.  Aspect again, is a class that encapsulates advices

   public void addPerInstanceJoinpointAspect(Joinpoint joinpoint, AspectDefinition def)
   {
      Set<Joinpoint> joinpoints = perInstanceJoinpointAspectDefinitions.get(def);
      if (joinpoints == null)
      {
         joinpoints = new ConcurrentSet<Joinpoint>();
         initPerInstanceJoinpointAspectDefinitionsMap();
         perInstanceJoinpointAspectDefinitions.put(def, joinpoints);
         def.registerAdvisor(this);
      }
      joinpoints.add(joinpoint);
   }

   void addPerInstanceJoinpointAspect(Set<Joinpoint> joinpoints, AspectDefinition def)
   {
      initPerInstanceJoinpointAspectDefinitionsMap();
      Set<Joinpoint> setJoinpoints = perInstanceJoinpointAspectDefinitions.get(def);
      if (setJoinpoints == null)
      {
         setJoinpoints = new ConcurrentSet<Joinpoint>();
         perInstanceJoinpointAspectDefinitions.put(def, setJoinpoints);
         def.registerAdvisor(this);
      }
      setJoinpoints.addAll(joinpoints);
   }
   
   public void removePerInstanceJoinpointAspect(AspectDefinition def)
   {
      perInstanceJoinpointAspectDefinitions.remove(def);
   }

   public Map<AspectDefinition, Set<Joinpoint>> getPerInstanceJoinpointAspectDefinitions()
   {
      return perInstanceJoinpointAspectDefinitions;
   }

   public Object getPerClassAspect(AspectDefinition def)
   {
      Object aspect = aspects.get(def.getName());
      if (aspect == NULL_ASPECT)
      {
         return null;
      }
      return aspect;
   }

   public Object getPerClassAspect(String def)
   {
      Object aspect = aspects.get(def);
      if (aspect == NULL_ASPECT)
      {
         return null;
      }
      return aspect;
   }

   public void addPerClassAspect(AspectDefinition def)
   {
      if (aspects.containsKey(def.getName())) return;
      Object aspect = def.getFactory().createPerClass(this);
      if (aspect == null)
      {
         aspect = NULL_ASPECT;
      }
      aspects.put(def.getName(), aspect);
      def.registerAdvisor(this);
   }

   public void removePerClassAspect(AspectDefinition def)
   {
      aspects.remove(def.getName());
      adviceInterceptors.remove(def);
   }

   public Interceptor getAdviceInterceptor(AspectDefinition def, String adviceName, Joinpoint joinpoint)
   {
      AdviceInterceptorKey key = new AdviceInterceptorKey(adviceName, joinpoint);
      synchronized (adviceInterceptors)
      {
         Map<String, Interceptor> map = adviceInterceptors.get(def);
         if (map != null)
         {
            return map.get(key);
         }
      }
      return null;
   }

   public void addAdviceInterceptor(AspectDefinition def, String adviceName, Interceptor interceptor, Joinpoint joinpoint)
   {
      synchronized (adviceInterceptors)
      {
         Map<String, Interceptor> map = adviceInterceptors.get(def);
         if (map == null)
         {
            map = new HashMap<String, Interceptor>();
            adviceInterceptors.put(def, map);
         }
         map.put(adviceName, interceptor);
      }
   }


   protected void createInterceptorChain(InterceptorFactory[] factories, ArrayList<Interceptor> newinterceptors, Joinpoint joinpoint)
   {
      for (int i = 0; i < factories.length; i++)
      {
         if (factories[i].getType().isGeneratedOnly())
         {
            throw new RuntimeException("Before/After/Throwing is only supported for Generated Advisors");
         }
         if (factories[i].isDeployed())
         {
            Interceptor interceptor = factories[i].create(this, joinpoint);
            if (interceptor != null)
            {
               newinterceptors.add(interceptor);
            }
         }
      }
   }

   protected void resolveMethodPointcut(AdviceBinding binding)
   {
      long[] keys = methodInfos.keys();
      for (int i = 0; i < keys.length; i++)
      {
         Method method = (Method) advisedMethods.get(keys[i]);
         PointcutMethodMatch match = binding.getPointcut().matchesExecution(this, method);
         
         if (match != null && match.isMatch())
         {
            adviceBindings.add(binding);
            if (AspectManager.verbose)
            {
               /*
               RepositoryClassLoader loader = (RepositoryClassLoader)clazz.getClassLoader();
               try
               {
                  System.err.println("method matched binding " + binding.getPointcut().getExpr() + " " + method.toString() + " " + loader.getObjectName());
               }
               catch (MalformedObjectNameException e)
               {
                  throw new RuntimeException(e);
               }
               */
               logger.debug("method matched binding: " + method.toString());
            }
            binding.addAdvisor(this);
            MethodMatchInfo info = methodInfos.getMatchInfo(keys[i]);
            info.addMatchedBinding(binding, match);
            if (AspectManager.maintainAdvisorMethodInterceptors)
            {
               methodInterceptors.put(keys[i], info);
            }
         }
      }
   }
   
   protected void updateMethodPointcutAfterRemove(AdviceBinding binding)
   {
      long[] keys = methodInfos.keys();
      for(int i =0; i < keys.length; i++)
      {
         Method method = (Method) advisedMethods.get(keys[i]);
         PointcutMethodMatch match = binding.getPointcut().matchesExecution(this, method);

         if (match != null && match.isMatch())
         {
            if (AspectManager.verbose)
            {
               logger.debug("removing matched binding: "+method.toString());
            }
            MethodMatchInfo info = methodInfos.getMatchInfo(keys[i]);
            info.removeMatchedBinding(binding, match);
            info.getInfo().clear();
            if (AspectManager.maintainAdvisorMethodInterceptors)
            {
               methodInterceptors.put(keys[i], info);
            }
         }
      }
   }
   
   protected void updateFieldPointcutAfterRemove(FieldInfo[] fieldInfos, AdviceBinding binding, boolean write)
   {
      ClassifiedBindingAndPointcutCollection bindingCol = manager.getBindingCollection();
      Collection<AdviceBinding> bindings = write? bindingCol.getFieldWriteBindings():
            bindingCol.getFieldReadBindings();
      for (int i = 0; i < fieldInfos.length; i++)
      {
         Field field = fieldInfos[i].getField();
         fieldInfos[i].resetInterceptors();

         if ((!write && binding.getPointcut().matchesGet(this, field))
         || (write && binding.getPointcut().matchesSet(this, field)))
         {
            if (AspectManager.verbose) logger.debug("Removing field, matched " + ((write) ? "write" : "read") + " binding: " + field);
            fieldInfos[i].clear();
            
            for(AdviceBinding ab : bindings)
            {
               if ((!write && ab.getPointcut().matchesGet(this, field))
                     || (write && ab.getPointcut().matchesSet(this, field)))
               {    
                  ab.addAdvisor(this);
                  pointcutResolved(fieldInfos[i], ab, new FieldJoinpoint(field));
               }
            }
            this.updateFieldPointcutAfterRemove(fieldInfos[i], i, write);
         }         
      }
   }
   
   protected void updateFieldPointcutAfterRemove(FieldInfo fieldInfo, int i,
         boolean write)
   {
   }
   
   protected void updateConstructorPointcutAfterRemove(AdviceBinding binding)
   {
      ClassifiedBindingAndPointcutCollection bindingCol = manager.getBindingCollection();
      if(constructorInfos != null && constructorInfos.length > 0)
      {
         for (int i = 0; i < constructorInfos.length; i++)
         {
            constructorInfos[i].resetInterceptors();
            Constructor<?> constructor = constructors[i];
            if (binding.getPointcut().matchesExecution(this, constructor))
            {
               if (AspectManager.verbose) logger.debug("Removing constructor, matched binding: " + constructor);
               constructorInfos[i].clear();
               for(AdviceBinding ab : bindingCol.getConstructorExecutionBindings())
               {
                  if (ab.getPointcut().matchesExecution(this, constructor))
                  {
                     ab.addAdvisor(this);
                     pointcutResolved(constructorInfos[i], ab, new ConstructorJoinpoint(constructor));
                  }
               }
               if (AspectManager.maintainAdvisorMethodInterceptors)
               {
                  this.constructorInterceptors[i] = constructorInfos[i].getInterceptors();
               }
            }
         }
      }
   }
   
   protected void updateConstructionPointcutAfterRemove(AdviceBinding binding)
   {
      ClassifiedBindingAndPointcutCollection bindingCol = manager.getBindingCollection();
      if (constructionInfos.length > 0)
      {
         for (int i = 0; i < constructionInfos.length ;i++)
         {
            constructionInfos[i].resetInterceptors();
            ConstructionInfo info = constructionInfos[i];
            Constructor<?> constructor = info.getConstructor();
            if (binding.getPointcut().matchesConstruction(this, constructor))
            {
               if (AspectManager.verbose) logger.debug("Removing construction, matched binding: " + constructor);
               constructionInfos[i].clear();
               for(AdviceBinding ab : bindingCol.getConstructionBindings())
               {
                  if (binding.getPointcut().matchesConstruction(this, constructor))
                  {
                     ab.addAdvisor(this);
                     pointcutResolved(constructionInfos[i], ab, new ConstructorJoinpoint(constructor));
                  }
               }
               if (AspectManager.maintainAdvisorMethodInterceptors)
               {
                  this.constructionInterceptors[i] = constructionInfos[i].getInterceptors();
               }
            }
         }
      }
   }

   protected void lockWriteChain(MethodInterceptors methodInterceptors)
   {
      Object[] methodMatchInfos = methodInterceptors.infos.getValues();
      for (int i = 0; i < methodMatchInfos.length; i++)
      {
         MethodMatchInfo methodMatchInfo = (MethodMatchInfo) methodMatchInfos[i];
         methodMatchInfo.getInfo().getInterceptorChainReadWriteLock().writeLock().lock();
      }
   }
   
   protected void unlockWriteChain(MethodInterceptors methodInterceptors)
   {
      Object[] methodMatchInfos = methodInterceptors.infos.getValues();
      for (int i = 0; i < methodMatchInfos.length; i++)
      {
         MethodMatchInfo methodMatchInfo = (MethodMatchInfo) methodMatchInfos[i];
         methodMatchInfo.getInfo().getInterceptorChainReadWriteLock().writeLock().unlock();
      }
   }
   
   protected void resetChain(MethodInterceptors methodInterceptors)
   {
      Object[] methodMatchInfos = methodInterceptors.infos.getValues();
      for (int i = 0; i < methodMatchInfos.length; i++)
      {
         MethodMatchInfo methodMatchInfo = (MethodMatchInfo) methodMatchInfos[i];
         if (methodMatchInfo.bindings != null)
         {
            methodMatchInfo.bindings.clear();
         }
         if (methodMatchInfo.pointcutMethodMatches != null)
         {
            methodMatchInfo.pointcutMethodMatches.clear();
         }
         methodMatchInfo.getInfo().clear();
      }
   }
   
   protected void resetChainKeepInterceptors(MethodInterceptors methodInterceptors)
   {
      Object[] methodMatchInfos = methodInterceptors.infos.getValues();
      for (int i = 0; i < methodMatchInfos.length; i++)
      {
         MethodMatchInfo methodMatchInfo = (MethodMatchInfo) methodMatchInfos[i];
         JoinPointInfo info = methodMatchInfo.getInfo();
         info.clear();
      }
   }
   
   protected void finalizeMethodChain()
   {
      boolean maintain = AspectManager.maintainAdvisorMethodInterceptors;
      TLongObjectHashMap newMethodInfos = (maintain) ? new TLongObjectHashMap() : null;
      
      long[] keys = methodInfos.keys();
      for (int i = 0; i < keys.length; i++)
      {
         MethodMatchInfo matchInfo = methodInfos.getMatchInfo(keys[i]);
         matchInfo.populateBindings();
         MethodInfo info = matchInfo.getInfo();
         adjustInfoForAddedBinding(info);
         ArrayList<Interceptor> list = info.getInterceptorChain();
         Interceptor[] interceptors = null;
         if (list.size() > 0)
         {
            interceptors = applyPrecedence(list.toArray(new Interceptor[list.size()]));
         }
         info.setInterceptors(interceptors);
         
         if (maintain)
         {
            newMethodInfos.put(keys[i], info);
         }
      }
      methodInterceptors = newMethodInfos;
   }

   public InvocationResponse dynamicInvoke(Object target, Invocation invocation)
   throws Throwable
   {
      // Only need to set Method because fields will already have been set.
      if (invocation instanceof MethodInvocation)
      {
         Interceptor[] aspects = null;
         MethodInvocation methodInvocation = (MethodInvocation) invocation;
         long hash = methodInvocation.getMethodHash();
         MethodInfo info = methodInfos.getMethodInfo(hash);
         // this method may be unadvised. In this case, just create an info 
         // to use as parameter of MethodInvocation
         if (info == null)
         {
            info = new MethodInfo(clazz, hash, hash, this);
            methodInfos.put(hash, info);
         }
         aspects = info.getInterceptors();
         if (aspects == null) aspects = new Interceptor[0];
         if (target != null && target instanceof Advised)
         {
            InstanceAdvised advised = (InstanceAdvised) target;
            aspects = advised._getInstanceAdvisor().getInterceptors(aspects);
         }
         MethodInvocation nextInvocation = new MethodInvocation(info, aspects);
         nextInvocation.setMetaData(invocation.getMetaData());
         nextInvocation.setTargetObject(target);
         nextInvocation.setArguments(methodInvocation.getArguments());
         nextInvocation.setAdvisor(this);
         InvocationResponse response = new InvocationResponse(nextInvocation.invokeNext());
         response.setContextInfo(nextInvocation.getResponseContextInfo());
         return response;
      }
      throw new RuntimeException("dynamic field invocations not supported yet!");
   }

   /**
    * EJB3 counts on this being unchecked
    */
   @SuppressWarnings("unchecked")
   public Class getClazz()
   {
      return clazz;
   }

   void setClazz(Class<?> clazz)
   {
      this.clazz = clazz;
   }

   /**
    * Defines the class loader associated with the advised context.
    * 
    * @param loader the class loader that loaded all joinpoints contained in the
    *        advised context.
    * @throws IllegalStateException when there is already a class associated with
    *         this advisor ({@link #getClazz()}), and {@link loader} is non-null and
    *         different from the class loader that loaded the advised class.
    */
   void setClassLoader(ClassLoader loader) throws IllegalStateException
   {
      if (this.clazz != null && loader != null &&
            loader != SecurityActions.getClassLoader(this.clazz))
      {
         throw new IllegalStateException("Cannot have both loader and class");
      }
      if (loader == null)
      {
         this.loader = null;
      }
      else
      {
         this.loader = new WeakReference<ClassLoader>(loader);
      }
   }

   /**
    * Returns the class loader associated with this advisor.
    * 
    * @return the class loader that loaded all joinpoints contained in the advised
    *         context. 
    */
   public ClassLoader getClassLoader()
   {
      if (this.loader != null)
      {
         ClassLoader cl = loader.get();
         if (cl != null)
         {
            return cl;
         }
      }
      if (clazz != null)
      {
         ClassLoader cl = SecurityActions.getClassLoader(clazz);
         if (cl != null)
         {
            return cl;
         }
      }
      return null;
   }

   /**
    * @deprecated Use Class.getSimpleName() instead
    */
   public static String getSimpleName(Class<?> clazz)
   {
      String name = clazz.getName();
      int lastIndex = name.lastIndexOf('.');
      if (lastIndex < 0)
      {
         return name;
      }

      return name.substring(lastIndex + 1);
   }

   protected void initializeConstructorChain()
   {
      if (clazz != null && constructors == null)
      {
          constructors = clazz.getDeclaredConstructors();
      }

      this.constructorInfos = new ConstructorInfo[constructors.length];
      for (int i = 0; i < constructors.length; i++)
      {
         final ConstructorInfo info = new ConstructorInfo();
         info.setConstructor(constructors[i]);
         info.setIndex(i);
         try
         {
            final String name = ConstructorExecutionTransformer.constructorFactory(clazz.getSimpleName());
            final Class<?>[] types = constructors[i].getParameterTypes();
            Method method = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>()
            {
               public Method run() throws Exception
               {
                  return clazz.getDeclaredMethod(name, types);
               }
            });
            info.setWrapper(method);
         }
         catch (PrivilegedActionException e1)
         {
            Exception e = e1.getException();
            if (e instanceof NoSuchMethodException == false)
               throw new NestedRuntimeException(e);
         }

         info.setAdvisor(this);
         constructorInfos[i] = info;

         try
         {
            final String name = ConstructorExecutionTransformer.getConstructorInfoFieldName(getSimpleName(clazz), i);
            AccessController.doPrivileged(new PrivilegedExceptionAction<Object>()
            {
               public Object run() throws Exception
               {
                  Field infoField = clazz.getDeclaredField(name);
                  infoField.setAccessible(true);
                  infoField.set(null, new WeakReference<ConstructorInfo>(info));
                  return null;
               }
            });
         }
         catch (PrivilegedActionException e1)
         {
            Exception e = e1.getException();
            if (e instanceof NoSuchFieldException == false)
               throw new NestedRuntimeException(e);
         }
      }
   }

   protected void initializeConstructionChain()
   {
      this.constructionInfos = new ConstructionInfo[constructors.length];
      for (int i = 0; i < constructors.length; i++)
      {
         ConstructionInfo info = new ConstructionInfo();
         info.setConstructor(constructors[i]);
         info.setIndex(i);
         info.setAdvisor(this);
         constructionInfos[i] = info;

         try
         {
            Field infoField = clazz.getDeclaredField(ConstructionTransformer.getConstructionInfoFieldName(clazz.getSimpleName(), i));
            infoField.setAccessible(true);
            infoField.set(null, new WeakReference<ConstructionInfo>(info));
         }
         catch (NoSuchFieldException e)
         {
            // ignore, method may not be advised.
         }
         catch (IllegalAccessException e)
         {
            throw new RuntimeException(e);
         }
      }
   }

   protected void finalizeChain(JoinPointInfo[] infos)
   {
      if (infos == null)
      {
         return;
      }
      for (int i = 0; i < infos.length; i++)
      {
         JoinPointInfo info = infos[i];
         adjustInfoForAddedBinding(info);
         ArrayList<Interceptor> list = info.getInterceptorChain();
         Interceptor[] interceptors = null;
         if (list.size() > 0)
         {
            interceptors = applyPrecedence(list.toArray(new Interceptor[list.size()]));
         }
         info.setInterceptors(interceptors);
      }
   }
   
   protected void lockWriteChain(JoinPointInfo[] infos)
   {
      if (infos == null)
      {
         return;
      }
      for (int i = 0; i < infos.length; i++)
      {
         infos[i].getInterceptorChainReadWriteLock().writeLock().lock();
      }
   }
   
   protected void unlockWriteChain(JoinPointInfo[] infos)
   {
      if (infos == null)
      {
         return;
      }
      for (int i = 0; i < infos.length; i++)
      {
         infos[i].getInterceptorChainReadWriteLock().writeLock().unlock();
      }
   }
   
   protected void resetChain(JoinPointInfo[] infos)
   {
      if (infos == null)
      {
         return;
      }
      for (int i = 0; i < infos.length; i++)
      {
         infos[i].clear();
      }
   }
   
   protected void resetChainKeepInterceptors(JoinPointInfo[] infos)
   {
      if (infos == null)
      {
         return;
      }
      for (int i = 0; i < infos.length; i++)
      {
         infos[i].resetChainKeepInterceptors();
      }
   }  

//   protected void finalizeConstructionChain(ArrayList newConstructionInfos)
//   {
//      for (int i = 0; i < newConstructionInfos.size(); i++)
//      {
//         ConstructionInfo info = (ConstructionInfo) newConstructionInfos.get(i);
//         ArrayList list = info.getInterceptorChain();
//         Interceptor[] interceptors = null;
//         if (list.size() > 0)
//         {
//          interceptors = applyPrecedence((Interceptor[]) list.toArray(new Interceptor[list.size()]));
//         }
//         info.setInterceptors(interceptors);
//      }
//   }

   protected void resolveConstructorPointcut(AdviceBinding binding)
   {
      for (int i = 0; i < constructors.length; i++)
      {
         Constructor<?> constructor = constructors[i];
         if (binding.getPointcut().matchesExecution(this, constructor))
         {
            if (AspectManager.verbose) logger.debug("constructor matched binding: " + constructor);
            adviceBindings.add(binding);
            binding.addAdvisor(this);
            pointcutResolved(constructorInfos[i], binding, new ConstructorJoinpoint(constructor));
            // if we must keep track of deprecated fields and the field is already initialized
            if (AspectManager.maintainAdvisorMethodInterceptors && constructorInterceptors != null)
            {
               constructorInterceptors[i] = constructorInfos[i].getInterceptors();
            }
         }
      }
   }

   protected void resolveConstructionPointcut(AdviceBinding binding)
   {
      if (constructionInfos.length > 0)
      {
         for (int i = 0; i < constructionInfos.length ;i++)
         {
            ConstructionInfo info = constructionInfos[i];
            Constructor<?> constructor = info.getConstructor();
            if (binding.getPointcut().matchesConstruction(this, constructor))
            {
               if (AspectManager.verbose) logger.debug("construction matched binding: " + constructor);
               adviceBindings.add(binding);
               binding.addAdvisor(this);
               pointcutResolved(info, binding, new ConstructorJoinpoint(constructor));
               // if we must keep track of deprecated fields and the field is already initialized
               if (AspectManager.maintainAdvisorMethodInterceptors && this.constructionInterceptors != null)
               {
                  this.constructionInterceptors[i] = constructionInfos[i].getInterceptors();
               }
            }
         }
      }
   }

   /** @deprecated We should just be using xxxxInfos */
   protected void populateInterceptorsFromInfos()
   {
      if (!AspectManager.maintainAdvisorMethodInterceptors)
      {
         return;
      }
      if (constructorInfos == null)
      {
         constructorInterceptors = new Interceptor[0][];
         
      }
      else
      {
         constructorInterceptors = new Interceptor[constructorInfos.length][];
         for (int i = 0 ; i < constructorInfos.length ; i++)
         {
            constructorInterceptors[i] = constructorInfos[i].getInterceptors();
         }
      }
   }

   /**
    * Default implementation adds interceptorChain directly to the info.
    * GeneratedClassAdvisor overrides this
    */
   protected void pointcutResolved(JoinPointInfo info, AdviceBinding binding, Joinpoint joinpoint)
   {
      ArrayList<Interceptor> curr = info.getInterceptorChain();
      if (binding.getCFlow() != null)
      {
         ArrayList<Interceptor> cflowChain = new ArrayList<Interceptor>();
         createInterceptorChain(binding.getInterceptorFactories(), cflowChain, joinpoint);
         Interceptor[] cflowInterceptors = cflowChain.toArray(new Interceptor[cflowChain.size()]);
         curr.add(new CFlowInterceptor(binding.getCFlowString(), binding.getCFlow(), cflowInterceptors));
      }
      else
      {
         createInterceptorChain(binding.getInterceptorFactories(), curr, joinpoint);
      }
   }

   Interceptor[] applyPrecedence(Interceptor[] interceptors)
   {
      return PrecedenceSorter.applyPrecedence(interceptors, manager);
   }

   /**
    * Whether the type of advisor supports matching on pointcut expression, where the method is defined in a superclass only,
    * while the pointcut expression class matches the subclass. This is currently only supported for generated advisors, due to
    * the new weaving model. So (with generated advisors) if we have<BR/>
    * <code><BR/>
    * public class Super {<BR/>
    * &nbsp;&nbsp;void method(){}<BR/>
    * }<BR/>
    * <BR/>
    * public class Sub etxends Super {<BR/>
    * }<BR/>
    * </code>
    * and<BR/>
    * <code>
    *    &lt;bind pointcut="execution(* Super->method())"&gt;<BR/>
    *    &nbsp;&nbsp;&lt;interceptor class="A"/&gt;<BR/>
    *    &lt;/bind&gt;<BR/>
    *    &lt;bind pointcut="execution(* sub->method())"&gt;<BR/>
    *    &nbsp;&nbsp;&lt;interceptor class="B"/&gt;<BR/>
    *    &lt;/bind&gt;<BR/>
    * </code><BR/>
    * Super.method() will be intercepted by A only<BR/>
    * Sub.method() will be intercepted by A and B
    *
    */
   public boolean chainOverridingForInheritedMethods()
   {
      return false;
   }

   /**
    * @param overriding the new value of chainOverridingForInheritedMethods
    * @see Advisor#chainOverridingForInheritedMethods()
    */
   protected void setChainOverridingForInheritedMethods(boolean overriding)
   {
      //Implemented by base-classes
      throw new NotImplementedException("Not a legal operation for Advisor");
   }
   
   interface DeployAnnotationOverrideAction
   {
      void deploy(Advisor advisor, AnnotationIntroduction introduction);

      DeployAnnotationOverrideAction PRIVILEGED = new DeployAnnotationOverrideAction()
      {
         public void deploy(final Advisor advisor, final AnnotationIntroduction introduction)
         {
            try
            {
               AccessController.doPrivileged(new PrivilegedExceptionAction<Object>()
               {
                  public Object run()
                  {
                     advisor.doDeployAnnotationOverride(introduction);
                     return null;
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception ex = e.getException();
               if (ex instanceof RuntimeException)
               {
                  throw (RuntimeException)ex;
               }
               throw new RuntimeException(ex);
            }
         }
      };

      DeployAnnotationOverrideAction NON_PRIVILEGED = new DeployAnnotationOverrideAction()
      {
         public void deploy(Advisor advisor, AnnotationIntroduction introduction)
         {
            advisor.doDeployAnnotationOverride(introduction);
         }
      };
   }
   
   public void cleanup()
   {
      //AspectDefinitions have strong links back to us
      for(AspectDefinition def : perInstanceAspectDefinitions)
      {
         removePerInstanceAspect(def);
         def.unregisterAdvisor(this);
      }
      
      for(AspectDefinition def : perInstanceJoinpointAspectDefinitions.keySet())
      {
         removePerInstanceJoinpointAspect(def);
         def.unregisterAdvisor(this);
      }

      AspectDefinition[] defs = adviceInterceptors.keySet().toArray(new AspectDefinition[adviceInterceptors.size()]);
      for(int i = 0 ; i < defs.length ; i++)
      {
         if (defs[i].getScope() == Scope.PER_CLASS)
         {
            removePerClassAspect(defs[i]);
            defs[i].unregisterAdvisor(this);
         }
      }
            
      if (methodInfos != null)
      {
         methodInfos. clear();
      }
   }
   
   protected void initInterfaceIntroductionsList()
   {
      if (interfaceIntroductions == UnmodifiableEmptyCollections.EMPTY_ARRAYLIST)
      {
         synchronized(lazyCollectionLock)
         {
            if (interfaceIntroductions == UnmodifiableEmptyCollections.EMPTY_ARRAYLIST)
            {
               interfaceIntroductions = new ArrayList<InterfaceIntroduction>();
            }
         }
      }
   }
   
   protected void initClassMetaDataBindingsList()
   {
      if (classMetaDataBindings == UnmodifiableEmptyCollections.EMPTY_ARRAYLIST)
      {
         synchronized(lazyCollectionLock)
         {
            if (classMetaDataBindings == UnmodifiableEmptyCollections.EMPTY_ARRAYLIST)
            {
               classMetaDataBindings = new ArrayList<ClassMetaDataBinding>();
            }
         }
      }
   }
   
   protected void initPerInstanceAspectDefinitionsSet()
   {
      if (perInstanceAspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_SET)
      {
         synchronized(lazyCollectionLock)
         {
            if (perInstanceAspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_SET)
            {
               perInstanceAspectDefinitions = new ConcurrentSet<AspectDefinition>(16, .75f, 2);
            }
         }
      }
   }
   
   protected void initPerInstanceJoinpointAspectDefinitionsMap()
   {
      if (perInstanceJoinpointAspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (perInstanceJoinpointAspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               perInstanceJoinpointAspectDefinitions = new ConcurrentHashMap<AspectDefinition, Set<Joinpoint>>();
            }
         }
      }
   }

   protected void initAdvisedMethodsMap()
   {
      if (advisedMethods == UnmodifiableEmptyCollections.EMPTY_TLONG_OBJECT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (advisedMethods == UnmodifiableEmptyCollections.EMPTY_TLONG_OBJECT_HASHMAP)
            {
               advisedMethods = new TLongObjectHashMap();
            }
         }
      }
   }
   
   public boolean hasSameMethodAspectLength(Advisor other)
   {
      if (this.getClazz() != other.getClazz())
      {
         throw new IllegalArgumentException("The advisors must be of the same type. Mine: " + 
               this.getClazz().getName() + "; other: " + other.getClazz().getName());
      }
      
      long[] myKeys = this.methodInfos.infos.keys();
      long[] otherKeys = other.methodInfos.keys();
      return JoinPointComparator.hasSameMethodAspectLength(myKeys, otherKeys, this, other);
   }
   
   public boolean hasSameConstructorAspectLength(Advisor other)
   {
      if (this.getClazz() != other.getClazz())
      {
         throw new IllegalArgumentException("The advisors must be of the same type. Mine: " + 
               this.getClazz().getName() + "; other: " + other.getClazz().getName());
      }
      ConstructorInfo[] myInfos = this.getConstructorInfos();
      ConstructorInfo[] otherInfos = other.getConstructorInfos();
      return JoinPointComparator.hasSameConstructorAspectLength(myInfos, otherInfos);
   }
}
