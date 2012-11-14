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
package org.jboss.aop.instrument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.SerialVersionUID;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SyntheticAttribute;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.annotation.compiler.AnnotationInfoCreator;
import org.jboss.aop.array.ArrayAdvisor;
import org.jboss.aop.array.ArrayReplacement;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.util.Advisable;
import org.jboss.aop.util.CtConstructorComparator;
import org.jboss.aop.util.CtFieldComparator;
import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Transforms byte code, making a class advisable. Implements
 * command line class instrumentor as well. Reads classes from class path and creates
 * advised versions in specified directory. Usage:
 * <pre>
 * Instrumentor [dest. directory] [class[ class...]]
 * </pre>
 *
 * You can control which instrumentor to use by passing in the jboss.aop.instrumentor
 * system property.
 *
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:gte863h@prism.gatech.edu">Austin Chau</a>
 * @author <a href="mailto:crazybob@crazybob.org">Bob Lee</a>
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 81733 $
 */
public abstract class Instrumentor
{
   private static final AOPLogger logger = AOPLogger.getLogger(Instrumentor.class);

   /**
    * Package of AOP classes.
    */
   public static final String AOP_PACKAGE =
           Advised.class.getPackage().getName();

   /**
    * Name of helper class.
    */
   public static final String ASPECT_MANAGER_CLASS_NAME =
           AOP_PACKAGE + ".AspectManager";

   /**
    * Helper class's field name.
    */
   public static final String HELPER_FIELD_NAME = "aop$classAdvisor" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;

   protected AOPClassPool classPool;
   protected boolean basicsSet = false;


   protected CodeConverter converter;
   protected AspectManager manager;
   protected JoinpointClassifier joinpointClassifier;
   protected static Collection<CtClass> processedClasses = new ArrayList<CtClass>();

   // Transformers, more than meets the eye!
   MethodExecutionTransformer methodExecutionTransformer;
   ConstructorExecutionTransformer constructorExecutionTransformer;
   ConstructionTransformer constructionTransformer;
   FieldAccessTransformer fieldAccessTransformer;
   CallerTransformer callerTransformer;
   DynamicTransformationObserver dynamicTransformationObserver;

   /**
    * Constructs new instrumentor.
    * @param joinpointClassifier algorithm of joinpoint classification to be used.
    * @param observer need be notified of every joinpoint wrapping caused only
    * by pointcuts dynamicaly added.
    */
   protected Instrumentor(AOPClassPool pool,
         AspectManager manager,
         JoinpointClassifier joinpointClassifier,
         DynamicTransformationObserver observer)
   {
      this.classPool = pool;
      this.converter = new CodeConverter();
      this.manager = manager;
      this.joinpointClassifier = joinpointClassifier;
      this.dynamicTransformationObserver = observer;
      intitialiseTransformers();
   }

   protected Instrumentor(AspectManager manager,
         JoinpointClassifier joinpointClassifier)
   {
      this(null,
            manager,
            joinpointClassifier,
            null);
   }

   protected abstract void intitialiseTransformers();

   public ClassPool getClassPool()
   {
      return classPool;
   }

   CodeConverter getCodeConverter()
   {
      return converter;
   }

   public boolean isAdvised(CtClass clazz) throws NotFoundException
   {
      CtClass[] interfaces = clazz.getInterfaces();
      CtClass advised = forName(AOP_PACKAGE + ".Advised");
      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].equals(advised)) return true;
         if (interfaces[i].getName().equals(AOP_PACKAGE + ".Advised")) return true;
      }
      return false;
   }
   
   public boolean isProxyObject(CtClass clazz) throws NotFoundException
   {
      CtClass[] interfaces = clazz.getInterfaces();
      CtClass proxyObject = forName("javassist.util.proxy.ProxyObject");
      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].equals(proxyObject)) return true;
         if (interfaces[i].getName().equals("javassist.util.proxy.ProxyObject")) return true;
      }
      return false;
   }
   
   public void prepareClassForTransformation(CtClass clazz) throws NotFoundException
   {
      CtMethod[] methods = clazz.getDeclaredMethods();
      for(int i=0; i < methods.length;i++)
      {
         if(methods[i].getName().equals("_getAdvisor") ||
               methods[i].getName().equals("_getInstanceAdvisor"))
            clazz.removeMethod(methods[i]);
      }
   }

   public static boolean implementsAdvised(CtClass clazz) throws NotFoundException
   {
      CtClass[] interfaces = clazz.getInterfaces();
      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].getName().equals(AOP_PACKAGE + ".Advised")) return true;
      }
      return false;
   }


   public static boolean isTransformable(CtClass clazz) throws NotFoundException
   {
      CtClass[] interfaces = clazz.getInterfaces();
      //CtClass advised = forName(AOP_PACKAGE + ".instrument.Untransformable");
      for (int i = 0; i < interfaces.length; i++)
      {
         //if (interfaces[i].equals(advised)) return false;
         if (interfaces[i].getName().equals(AOP_PACKAGE + ".instrument.Untransformable")) return false;
      }
      return true;
   }

   protected boolean isBaseClass(CtClass clazz)
           throws NotFoundException
   {
      if (clazz.getSuperclass() != null)
      {
         if (isAdvised(clazz.getSuperclass()))
         {
            return false;
         }
         else
         {
            return isBaseClass(clazz.getSuperclass());
         }
      }
      return true;
   }


   protected static String mixinFieldName(CtClass mixinClass)
   {
      StringBuffer buf = new StringBuffer("_");
      buf.append(mixinClass.getName().replace('.', '$'));
      buf.append("$aop$mixin");
      return buf.toString();
   }

   protected CtMethod addMixinMethod(Advisor advisor, CtMethod method, CtClass clazz, CtMethod delegate, long hash) throws Exception
   {
      CtClass[] exceptions = method.getExceptionTypes();

      // create base, delegating method.
      CtMethod newMethod = CtNewMethod.wrapped(method.getReturnType(),
                                               method.getName(),
                                               method.getParameterTypes(),
                                               exceptions,
                                               delegate,
                                               CtMethod.ConstParameter.integer(hash),
                                               clazz);
      newMethod.setModifiers(Modifier.PUBLIC);
      clazz.addMethod(newMethod);
      
      return newMethod;
   }

   private void addMixin(CtClass clazz, InterfaceIntroduction pointcut, InterfaceIntroduction.Mixin mixin, HashMap<Long, CtMethod> baseMethods) throws Exception
   {
      // REVISIT:
      // Later on we should follow the same pattern as
      // C++ public virtual Mixins
      // But, for now, just throw an exception if the
      // mixin is adding any interfaces already
      // defined in base class or another mixin.
      CtClass mixinClass = classPool.get(mixin.getClassName());

      // check mixin constructor method if this is the case
      if (pointcut.getConstructorClass() != null)
      {
         CtClass type = forName(pointcut.getConstructorClass());
         CtMethod[] methods = type.getDeclaredMethods();
         boolean correct = false;
         for (int i = 0; i < methods.length; i++)
         {
            if (methods[i].getName().equals(pointcut.getConstructorMethod())
                  && methods[i].getParameterTypes().length == 1)
            {
               if (clazz.subclassOf(methods[i].getParameterTypes()[0]))
               {
                  correct = true;
               }

            }
         }
         if (!correct)
         {
            throw new RuntimeException("Could not find a method named '" +
                  pointcut.getConstructorMethod() + "' on class " +
                  pointcut.getConstructorClass() + " that receives " +
                  clazz.getName() + " or one of its superclasses as parameter.");
         }
      }

      String initializer = null;
      if (mixin.getConstruction() == null)
      {
         if (mixinClass.getConstructor("()V") == null)
         {
            throw new RuntimeException("Default constructor of mixin class '" +
                  mixinClass + "' not found.");
         }
         initializer = "new " + mixinClass.getName() + "()";
      }
      else
      {
         initializer = mixin.getConstruction();
      }
      CtClass type = forName(mixinClass.getName());
      CtField field = new CtField(type, mixinFieldName(mixinClass), clazz);
      int modifiers = Modifier.PRIVATE;
      if (mixin.isTransient()) modifiers = modifiers | Modifier.TRANSIENT;
      field.setModifiers(modifiers);
      addSyntheticAttribute(field);
      clazz.addField(field, CtField.Initializer.byExpr(initializer));
      HashSet<Long> addedMethods = new HashSet<Long>();

      String[] interfaces = mixin.getInterfaces();
      for (int i = 0; i < interfaces.length; i++)
      {
         CtClass intf = classPool.get(interfaces[i]);
         if (clazz.subtypeOf(intf)) continue;
         clazz.addInterface(intf);
         HashMap<Long, CtMethod> intfMap = JavassistMethodHashing.getMethodMap(intf);
         Iterator<Map.Entry<Long, CtMethod>> entries = intfMap.entrySet().iterator();
         while (entries.hasNext())
         {
            Map.Entry<Long, CtMethod> entry = entries.next();
            Long hash = entry.getKey();
            CtMethod method = entry.getValue();
            CtMethod baseMethod = baseMethods.get(hash);
            if (baseMethod != null && !addedMethods.contains(hash))
            {
               String msg = "Mixin " + mixinClass.getName() +
                        " of pointcut " + pointcut.getName() +
                        " is trying to apply an already existing method" + method.getName() + " for class " + clazz.getName();

               if (baseMethod.getDeclaringClass().equals(clazz))
               {
                  throw new RuntimeException(msg);
               }
               else
               {
                  if (AspectManager.verbose)logger.warn(msg);
               }
            }
            // If another interface of this mixin has a duplicate method, then its ok, but don't re-add
            if (addedMethods.contains(hash)) continue;
            try
            {
               createMixinInvokeMethod(clazz, mixinClass, initializer, method, hash.longValue());
            }
            catch (CannotCompileException e)
            {
               String generatedCode = "class " + clazz.getName() + "\n{\n   ...\n" +
                      "   private " + type.getName() + " = " + initializer + ";\n"
                      + "   ...\n}";
               throw new RuntimeException("Mixin construction expression '"
                  + initializer + "' may have sintax error:\n" + generatedCode, e);
            }
            baseMethods.put(hash, method);
            addedMethods.add(hash);
         }
      }
   }

   private void addIntroductionPointcutInterface(CtClass clazz, Advisor advisor, String intf, HashMap<Long, CtMethod> baseMethods) throws Exception
   {
      CtClass iface = classPool.get(intf);
      if (!clazz.subtypeOf(iface) && !clazz.subclassOf(iface))
      {
         clazz.addInterface(iface);
      }

      CtMethod mixinInvokeMethod = createInvokeMethod(clazz);
      HashMap<Long, CtMethod> intfMap = JavassistMethodHashing.getMethodMap(iface);
      Iterator<Map.Entry<Long, CtMethod>> entries = intfMap.entrySet().iterator();
      while (entries.hasNext())
      {
         Map.Entry<Long, CtMethod> entry = entries.next();
         Long hash = entry.getKey();
         if (baseMethods.containsKey(hash)) continue;
         CtMethod method = entry.getValue();
         addMixinMethod(advisor, method, clazz, mixinInvokeMethod, hash.longValue());
         baseMethods.put(hash, method);
      }
   }

   private void instrumentIntroductions(CtClass clazz, Advisor advisor)
           throws Exception
   {
      ArrayList<InterfaceIntroduction> pointcuts = advisor.getInterfaceIntroductions();

      if (pointcuts.size() == 0) return;
      HashMap<Long, CtMethod> baseMethods = JavassistMethodHashing.getDeclaredMethodMap(clazz);
      Iterator<InterfaceIntroduction> it = pointcuts.iterator();
      if (it.hasNext()) setupBasics(clazz);
      while (it.hasNext())
      {

         InterfaceIntroduction pointcut = it.next();
         ArrayList<InterfaceIntroduction.Mixin> mixins = pointcut.getMixins();
         for (InterfaceIntroduction.Mixin mixin: mixins)
         {
            addMixin(clazz, pointcut, mixin, baseMethods);
         }
      }

      // pointcut interfaces.  If a method is already implemented for it then use that method
      // otherwise delegate to an interceptor
      it = pointcuts.iterator();
      while (it.hasNext())
      {
         InterfaceIntroduction pointcut = it.next();
         String[] interfaces = pointcut.getInterfaces();
         if (interfaces == null) continue;
         for (int i = 0; i < interfaces.length; i++)
         {
            addIntroductionPointcutInterface(clazz, advisor, interfaces[i], baseMethods);
         }
      }
   }

   private boolean instrumentAnnotationIntroductions(CtClass clazz, ClassAdvisor advisor)
           throws Exception
   {
      boolean changed = false;
      for (AnnotationIntroduction introduction : advisor.getManager().getAnnotationIntroductions())
      {
         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("**** " + introduction.getOriginalAnnotationExpr() + " invisible: " + introduction.isInvisible() + " expr: " + introduction.getOriginalExpression());
         if (introduction.matches(advisor, clazz))
         {
            if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug(introduction.getAnnotation() + " binds to " + clazz.getName());
            javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(classPool, clazz.getClassFile2().getConstPool(), introduction.getAnnotation());
            if (introduction.isInvisible())
            {
               AnnotationsAttribute invisible = (AnnotationsAttribute) clazz.getClassFile2().getAttribute(AnnotationsAttribute.invisibleTag);
               if (invisible == null)
               {
                  invisible = new AnnotationsAttribute(clazz.getClassFile2().getConstPool(), AnnotationsAttribute.invisibleTag);
                  clazz.getClassFile2().addAttribute(invisible);
               }
               changed = true;
               invisible.addAnnotation(info);
            }
            else
            {
               AnnotationsAttribute visible = (AnnotationsAttribute) clazz.getClassFile2().getAttribute(AnnotationsAttribute.visibleTag);
               if (visible == null)
               {
                  visible = new AnnotationsAttribute(clazz.getClassFile2().getConstPool(), AnnotationsAttribute.visibleTag);
                  clazz.getClassFile2().addAttribute(visible);
               }
               changed = true;
               visible.addAnnotation(info);
            }
         }

         CtMethod[] methods = clazz.getDeclaredMethods();
         for (int i = 0; i < methods.length; i++)
         {
            if (introduction.matches(advisor, methods[i]))
            {
               javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(classPool, methods[i].getMethodInfo2().getConstPool(), introduction.getAnnotation());
               MethodInfo mi = methods[i].getMethodInfo2();
               if (introduction.isInvisible())
               {
                  AnnotationsAttribute invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
                  if (invisible == null)
                  {
                     invisible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.invisibleTag);
                     mi.addAttribute(invisible);
                  }
                  changed = true;
                  invisible.addAnnotation(info);
               }
               else
               {
                  AnnotationsAttribute visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
                  if (visible == null)
                  {
                     visible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.visibleTag);
                     mi.addAttribute(visible);
                  }
                  changed = true;
                  visible.addAnnotation(info);
               }
            }

         }

         CtConstructor[] cons = clazz.getDeclaredConstructors();
         for (int i = 0; i < cons.length; i++)
         {
            if (introduction.matches(advisor, cons[i]))
            {
               javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(classPool, cons[i].getMethodInfo2().getConstPool(), introduction.getAnnotation());
               MethodInfo mi = cons[i].getMethodInfo2();
               if (introduction.isInvisible())
               {
                  AnnotationsAttribute invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
                  if (invisible == null)
                  {
                     invisible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.invisibleTag);
                     mi.addAttribute(invisible);
                  }
                  changed = true;
                  invisible.addAnnotation(info);
               }
               else
               {
                  AnnotationsAttribute visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
                  if (visible == null)
                  {
                     visible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.visibleTag);
                     mi.addAttribute(visible);
                  }
                  changed = true;
                  visible.addAnnotation(info);
               }
            }
         }

         CtField[] fields = clazz.getDeclaredFields();
         for (int i = 0; i < fields.length; i++)
         {
            if (introduction.matches(advisor, fields[i]))
            {
               javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(classPool, fields[i].getFieldInfo2().getConstPool(), introduction.getAnnotation());
               FieldInfo mi = fields[i].getFieldInfo2();
               if (introduction.isInvisible())
               {
                  AnnotationsAttribute invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
                  if (invisible == null)
                  {
                     invisible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.invisibleTag);
                     mi.addAttribute(invisible);
                  }
                  changed = true;
                  invisible.addAnnotation(info);
               }
               else
               {
                  AnnotationsAttribute visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
                  if (visible == null)
                  {
                     visible = new AnnotationsAttribute(mi.getConstPool(), AnnotationsAttribute.visibleTag);
                     mi.addAttribute(visible);
                  }
                  changed = true;
                  visible.addAnnotation(info);
               }
            }
         }
      }
      return changed;
   }

   private boolean instrumentAnnotationOverrides(CtClass clazz, ClassAdvisor advisor)
           throws Exception
   {
      boolean changed = false;
      for (AnnotationIntroduction introduction : advisor.getManager().getAnnotationOverrides())
      {
         if (introduction.matches(advisor, clazz))
         {
            advisor.getAnnotations().addClassAnnotation(introduction.getAnnotation().getIdentifier(), introduction.getOriginalAnnotationExpr());
         }

         CtMethod[] methods = clazz.getDeclaredMethods();
         for (int i = 0; i < methods.length; i++)
         {
            if (introduction.matches(advisor, methods[i]))
            {
               advisor.getAnnotations().addAnnotation(methods[i], introduction.getAnnotation().getIdentifier());
            }
         }

         CtConstructor[] cons = clazz.getDeclaredConstructors();
         for (int i = 0; i < cons.length; i++)
         {
            if (introduction.matches(advisor, cons[i]))
            {
               advisor.getAnnotations().addAnnotation(cons[i], introduction.getAnnotation().getIdentifier());
            }
         }

         CtField[] fields = clazz.getDeclaredFields();
         for (int i = 0; i < fields.length; i++)
         {
            if (introduction.matches(advisor, fields[i]))
            {
               advisor.getAnnotations().addAnnotation(fields[i], introduction.getAnnotation().getIdentifier());
            }
         }
      }
      return changed;
   }

   public boolean applyCallerPointcuts(CtClass clazz, ClassAdvisor advisor) throws CannotCompileException
   {
      return callerTransformer.applyCallerPointcuts(clazz, advisor);
   }

   /**
    * Find all classes that this class references.  If any of those classes are advised and have field and/or constructor
    * interception, do instrumentation on this class so that those fields and constructors are instrumented
    */
   protected boolean convertReferences(CtClass clazz, ClassAdvisor clazzAdvisor) throws Exception
   {
      boolean converted = false;
      String ref = null;
      try
      {
         AOPClassPool pool = AOPClassPool.createAOPClassPool(clazz.getClassPool(), AOPClassPoolRepository.getInstance());

         //Class.getRefClasses() only gets classes explicitly referenced in the class. We need to check the super classes and do some extra handling
         for (ReferenceClassIterator it = new ReferenceClassIterator(clazz.getRefClasses()) ; it.hasNext() ; )
         {
            ref = it.next();
            if (!manager.getInterceptionMarkers(clazz.getClassPool().getClassLoader()).convertReference(ref)
                || manager.isNonAdvisableClassName(ref)
                || ref.startsWith("java.")
                || ref.startsWith("javax.")
                || ref.startsWith("["))
            {
               continue;
            }
            // Only need a temporary advisor for resolving metadata
            CtClass ctRef = null;
            ClassAdvisor advisor = null;
            if (ref.equals(clazz.getName()))
            {
               ctRef = clazz;
               advisor = clazzAdvisor;
            }
            else
            {
               try
               {
                  ctRef = pool.get(ref);
               }
               catch (NotFoundException e)
               {
                  if (AspectManager.suppressReferenceErrors)
                  {
                     System.err.println("[warn] Could not find class " + ref + " (or one of its implemented interfaces) that " + clazz.getName() + " references.  It may not be in your classpath and you may not be getting field and constructor weaving for this class.");
                     if (AspectManager.verbose) e.printStackTrace();
                     continue;
                  }
                  else
                  {
                     throw e;
                  }
               }
               if (!isTransformable(ctRef)) continue;
               advisor = manager.getTempClassAdvisor(ctRef);
            }
            it.addSuperClass(ctRef);
            //converted = false;
            
            final ClassLoader refCl = ctRef.getClassPool().getClassLoader();

            if (!manager.getInterceptionMarkers(refCl).shouldSkipFieldAccess(ref) && !ref.equals(clazz.getName()))
            {
               List<CtField> fields = getAdvisableFields(ctRef);
               if (fieldAccessTransformer.replaceFieldAccess(fields, ctRef, advisor))
               {
                  manager.getInterceptionMarkers(refCl).addFieldInterceptionMarker(ref);
                  converted = true;
               }
               else
               {
                  manager.getInterceptionMarkers(refCl).skipFieldAccess(ref);
               }
            }
            if (!manager.getInterceptionMarkers(refCl).shouldSkipConstruction(ref))
            {
               if (constructorExecutionTransformer.replaceConstructorAccess(advisor, ctRef))
               {
                  manager.getInterceptionMarkers(refCl).addConstructionInterceptionMarker(ref);
                  converted = true;
               }
               else
               {
                  manager.getInterceptionMarkers(refCl).skipConstruction(ref);
               }
            }

            if (!converted)
            {
               manager.getInterceptionMarkers(refCl).skipReference(ref);
            }
            ref = null;
         }
      }
      catch (Exception ex)
      {
         if (ref != null)
         {
            throw new TransformationException("Failed to aspectize class " + clazz.getName() + ".  Could not find class it references " + ref + " (or one of its implemented interfaces). It may not be in your classpath and you may not be getting field and constructor weaving for this class.");
         }
         throw ex;
      }
      return converted;
   }

   protected boolean shouldNotTransform(CtClass clazz)throws NotFoundException
   {
      return (clazz.isInterface() ||
            clazz.isFrozen() ||
            clazz.isArray() ||
            clazz.getName().startsWith("org.jboss.aop.") ||
            isAdvised(clazz) ||
//            isProxyObject(clazz) ||
            !isTransformable(clazz));
   }

   /**
    * Makes class advisable.
    */
   public boolean transform(CtClass clazz,
                            ClassAdvisor advisor)
   {
      try
      {
         if (shouldNotTransform(clazz)) return false;
         prepareClassForTransformation(clazz);
         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("trying to transform " + clazz.getName());

         DeclareChecker.checkDeclares(manager, clazz, advisor);

         boolean converted = instrumentAnnotationIntroductions(clazz, advisor);
         converted = instrumentAnnotationOverrides(clazz, advisor) || converted;
         boolean constructorAccessConverted = false;
         converted = applyCallerPointcuts(clazz, advisor) || converted;
         methodExecutionTransformer.instrument(clazz, advisor);
         @SuppressWarnings("unused")
         boolean constructionTransformation = constructionTransformer.insertConstructionInterception(clazz, advisor);
         constructorAccessConverted = constructorExecutionTransformer.transform(clazz, advisor);
         String classname = clazz.getName();

         final ClassLoader cl = clazz.getClassPool().getClassLoader();
         if (constructorAccessConverted)
         {
            manager.getInterceptionMarkers(cl).addConstructionInterceptionMarker(classname);
         }
         else
         {
            manager.getInterceptionMarkers(cl).skipConstruction(classname);
         }
         converted = converted || constructorAccessConverted;

         instrumentIntroductions(clazz, advisor);

         converted = convertReferences(clazz, advisor) || converted;

         boolean shouldReplaceArrayAccess = replaceArrayAccess(clazz, advisor);
         converted = converted || shouldReplaceArrayAccess;

         // need to instrument no matter what so that
         // previously declared field and constructor interceptions
         // get instrumented within this class.
         if (converted || basicsSet)
         {
            clazz.instrument(converter);
         }

         // create static wrapper methods after
         // clazz.instrument because the wrappers may call cons or fields
         fieldAccessTransformer.buildFieldWrappers(clazz, advisor, shouldReplaceArrayAccess);
         if (constructorAccessConverted)
         {
            constructorExecutionTransformer.codeConverted();
         }
         else
         {
            if (manager.getInterceptionMarkers(cl).shouldSkipFieldAccess(classname))
            {
               manager.getInterceptionMarkers(cl).skipReference(classname);
            }
         }


         // notifies dynamic transformation observer
         dynamicTransformationObserver.transformationFinished(clazz, converter);

         synchronized(processedClasses)
         {
            processedClasses.add(clazz);
         }

         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("was " + clazz.getName() + " converted: " + (basicsSet || converted));

         if (basicsSet || converted)
         {
            return true;
         }
         else
         {
            //classPool.flushClass(clazz.getName());
            return false;
         }

      }
      catch (Throwable e)
      {
         if (AspectManager.suppressTransformationErrors)
         {
            logger.error("[warn] AOP Instrumentor failed to transform " + clazz.getName(), e);
            return false;
         }
         else
         {
            if (e instanceof TransformationException)
            {
               throw ((TransformationException) e);
            }
            else
            {
               e.printStackTrace();
               throw new RuntimeException("failed to transform: " + clazz.getName(), e);
            }
         }

      }
   }


   public List<CtConstructor> getConstructors(CtClass clazz)
   {
      List<CtConstructor> list = new ArrayList<CtConstructor>();

      CtConstructor[] constructors = clazz.getDeclaredConstructors();

      for (int i = 0; i < constructors.length; i++)
      {
         list.add(constructors[i]);
      }
      Collections.sort(list, CtConstructorComparator.INSTANCE);

      return list;
   }

   /**
    * Gets sorted collection of advisable methods.
    */
   public static List<CtField> getAdvisableFields(CtClass clazz) throws NotFoundException
   {
      List<CtField> list = new ArrayList<CtField>();
      CtField[] fields = clazz.getDeclaredFields();
      for (int i = 0; i < fields.length; i++)
      {
         if (Advisable.isAdvisable(fields[i]))
         {
            list.add(fields[i]);
         }
      }
      Collections.sort(list, CtFieldComparator.INSTANCE);

      return list;
   }

   /**
    * Creates generic invoke method to be wrapped by real signatures.
    */
   protected CtMethod createInvokeMethod(CtClass clazz)
           throws CannotCompileException
   {
      return CtNewMethod.make("public java.lang.Object invoke(java.lang.Object[] args, long i)" +
                              "       throws java.lang.Throwable {" +
                              "   return ((org.jboss.aop.ClassAdvisor)this._getAdvisor()).invokeMethod(this, i, args);" +
                              "}",
                              clazz);
   }

   /**
    * Gets a class by its name.
    */
   public CtClass forName(String name) throws NotFoundException
   {
      return this.classPool.get(name);
   }

   /**
    * Gets a class by its name.
    */
   public CtClass forName(ClassPool pool, String name) throws NotFoundException
   {
      return pool.get(name);
   }


   /**
    * Adds a static field to a class.
    */
   protected CtField addStaticField(CtClass clazz, String name, String typeName,
                                  CtField.Initializer initializer)
           throws CannotCompileException, NotFoundException
   {
      CtClass type = forName(typeName);
      CtField field = new CtField(type, name, clazz);
      field.setModifiers(Modifier.PRIVATE | Modifier.STATIC);
      Instrumentor.addSyntheticAttribute(field);
      clazz.addField(field, initializer);

      return field;
   }

   /**
    * Adds a protected field to a class.
    */
   protected CtField addProtectedField(CtClass clazz, String name, String typeName,
                                     CtField.Initializer initializer)
           throws CannotCompileException, NotFoundException
   {
      CtClass type = forName(typeName);
      CtField field = new CtField(type, name, clazz);
      field.setModifiers(Modifier.PROTECTED | Modifier.TRANSIENT);
      Instrumentor.addSyntheticAttribute(field);
      if (initializer != null)
      {
         clazz.addField(field, initializer);
      }
      else
      {
         clazz.addField(field);
      }
      return field;
   }

   public void setupBasics(CtClass clazz) throws CannotCompileException, NotFoundException
   {
      if (basicsSet) return;
      basicsSet = true;
      // add serialVersionUID.
      SerialVersionUID.setSerialVersionUID(clazz);

      // add marker interface.
      clazz.addInterface(forName(AOP_PACKAGE + ".Advised"));

      doSetupBasics(clazz);
   }

   /**
    * Notifies the <code>Instrumentor</code> that some joinpoint status were updated.
    * This method hot swaps the code of afected classes.
    * @param joinpointUpdates a collection of <code>org.jboss.aop.instrument.JoinpointStatusUpdate</code>.
    * @param hotSwapper object capable of hot swapping classes.
    */
   public synchronized void interceptorChainsUpdated(Collection<JoinpointStatusUpdate> joinpointUpdates, HotSwapper hotSwapper) {
      //creates a converter
      this.converter = new CodeConverter();
      // list of instrumented classes
      Collection<CtClass> classes = new HashSet<CtClass>();
      try {
         // transform classes whose joinpont status have changed
         for (JoinpointStatusUpdate update : joinpointUpdates)
         {
            CtClass clazz = update.clazz;
            JoinpointStatusUpdate.ClassJoinpoints wrapTargets = update.newlyAdvisedJoinpoints;
            JoinpointStatusUpdate.ClassJoinpoints unwrapTargets = update.newlyUnadvisedJoinpoints;

            clazz.defrost();
            fieldAccessTransformer.wrap(clazz, wrapTargets.fieldReads, wrapTargets.fieldWrites);
            fieldAccessTransformer.unwrap(clazz, unwrapTargets.fieldReads, unwrapTargets.fieldWrites);
            constructorExecutionTransformer.wrap(clazz, wrapTargets.constructorExecutions);
            constructorExecutionTransformer.unwrap(clazz, unwrapTargets.constructorExecutions);
            methodExecutionTransformer.wrap(clazz, wrapTargets.methodExecutions);
            methodExecutionTransformer.unwrap(clazz, unwrapTargets.methodExecutions);
            if (!update.isEmpty())
            {
               clazz.instrument(converter);
               classes.add(clazz);
            }
         }
         // instrument classes that access the joinpoints whose status have changed, in
         // order to make this classes access the joinpoint wrapper instead
         synchronized(processedClasses)
         {
            for (CtClass clazz : processedClasses) 
            {
               if (manager.isNonAdvisableClassName(clazz.getName()) || ! isTransformable(clazz))
               {
                  continue;
               }
               // class already instrumented
               if (classes.contains(clazz))
               {
                  continue;
               }
               // check if clazz should be added to classes
               clazz.defrost();
               byte[] previousByteCode = clazz.toBytecode();
               clazz.defrost();
               clazz.instrument(converter);
               if (!java.util.Arrays.equals(clazz.toBytecode(), previousByteCode))
               {
                  classes.add(clazz);
               }
               clazz.defrost();
            }
         }
         // notifies code conversion observers
         fieldAccessTransformer.codeConverted();
         constructorExecutionTransformer.codeConverted();

         // registers the classes bytecodes to be hot swapped
         for (CtClass clazz : classes)
         {
            AOPClassPool classPool = (AOPClassPool) clazz.getClassPool();
            clazz.defrost();
            hotSwapper.registerChange(classPool.getClassLoader().loadClass(clazz.getName()),
                  clazz.toBytecode());
         }
         // performs the hot swap of registered classes
         hotSwapper.hotSwap();
      }
      catch (Exception e) {
         e.printStackTrace();
         if (AspectManager.suppressTransformationErrors)
         {
            logger.error("[warn] AOP Instrumentor failed to updated wrapping status.", e);
         }
         else
         {
            if (e instanceof TransformationException)
            {
               throw ((TransformationException) e);
            }
            else
            {
               throw new RuntimeException("failed to update wrapping status", e);
            }
         }
      }
   }

   private boolean replaceArrayAccess(CtClass clazz, Advisor advisor) throws Exception
   {
      boolean shouldReplaceArrayAccess = false;
      Map<String, ArrayReplacement> arrayReplacements = manager.getArrayReplacements();
      for (ArrayReplacement arrayReplacement : arrayReplacements.values())
      {
         if (arrayReplacement.matches(advisor, clazz))
         {
            shouldReplaceArrayAccess = true;
            break;
         }
      }

      if (shouldReplaceArrayAccess)
      {
		  if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("[debug] Replacing array access in " + clazz.getName());
         converter.replaceArrayAccess(classPool.get(ArrayAdvisor.class.getName()), new CodeConverter.DefaultArrayAccessReplacementMethodNames());
      }
      return shouldReplaceArrayAccess;
   }

   public static void addSyntheticAttribute(CtMethod method)
   {
      MethodInfo info = method.getMethodInfo();
      addSyntheticAttribute(info);
   }
   
   public static void addSyntheticAttribute(MethodInfo info)
   {
      ConstPool cp = info.getConstPool();
      info.addAttribute(new SyntheticAttribute(cp));
   }

   public static void addSyntheticAttribute(CtConstructor ctor)
   {
      MethodInfo info = ctor.getMethodInfo();
      ConstPool cp = info.getConstPool();
      info.addAttribute(new SyntheticAttribute(cp));
   }

   public static void addSyntheticAttribute(CtField field)
   {
      FieldInfo info = field.getFieldInfo();
      ConstPool cp = info.getConstPool();
      info.addAttribute(new SyntheticAttribute(cp));
   }

   /**
    * Converts all processed classes to make wrapping of the appropriate joinpoints.
    * This method must be called if some dynamic transformation ocurred (i. e. a
    * class has just been loaded and one or more of its joinpoints were wrapped due
    * only to bindings added dynamicaly; in this case, the previously loaded classes
    * may not call the wrappers of this joinpoints, and need to be instrumented).
    *
    * @param hotSwapper responsible for performing any hot swapping operations when
    *                   needed.
    * @param clazz the clazz whose transformation involved dynamic wrapping.
    * @param fieldReads collection of fields whose read joinpoit was dynamicaly wrapped.
    * @param fieldWrites collection of fields whose read joinpoit was dynamicaly wrapped.
    * @param constructor <code>true</code> if the <code>clazz</code> constructors were
    * dynamicaly wrapped.
    */
   public void convertProcessedClasses(HotSwapper hotSwapper, CtClass clazz,
         Collection<CtField> fieldReads, Collection<CtField> fieldWrites, boolean constructor)
   {
      AOPClassPool classPool = (AOPClassPool) clazz.getClassPool();
      CodeConverter codeConverter = new CodeConverter();
      for (CtField field : fieldReads)
      {
         codeConverter.replaceFieldRead(field, clazz, FieldAccessTransformer.fieldRead(field.getName()));
      }
      for (CtField field : fieldWrites)
      {
         codeConverter.replaceFieldWrite(field, clazz, FieldAccessTransformer.fieldWrite(field.getName()));
      }
      if (constructor)
      {
         codeConverter.replaceNew(clazz, clazz, ConstructorExecutionTransformer.constructorFactory(clazz.getSimpleName()));
      }

      synchronized(processedClasses)
      {
      for (CtClass processedClass : processedClasses)
      {
         if (processedClass == clazz)
            continue;
         if (processedClass.getRefClasses() == null ||
                ! clazz.getRefClasses().contains(clazz.getName()))
          {
             continue;
          }
          try
          {
             processedClass.defrost();
             byte[] previousByteCode = processedClass.toBytecode();
             processedClass.defrost();
             processedClass.instrument(codeConverter);
             byte[] updatedByteCode = processedClass.toBytecode();
             if (!java.util.Arrays.equals(updatedByteCode, previousByteCode))
             {
               hotSwapper.registerChange(classPool.getClassLoader().loadClass(processedClass.getName()), updatedByteCode);
             }
             processedClass.defrost();
          }
          catch (Exception e)
          {
             e.printStackTrace();
             if (AspectManager.suppressTransformationErrors)
             {
                logger.error("[warn] AOP Instrumentor failed to updated wrapping status.", e);
             }
             else if (e instanceof TransformationException)
             {
                throw ((TransformationException) e);
             }
             else
             {
                throw new RuntimeException("failed to update wrapping status", e);
             }
          }

         }
      }
      hotSwapper.hotSwap();
   }

   protected abstract void doSetupBasics(CtClass clazz) throws CannotCompileException, NotFoundException;

   /**
    * Creates generic invoke method to be wrapped by real signatures.
    */
   protected abstract CtMethod createMixinInvokeMethod(CtClass clazz, CtClass mixinClass, String initializer, CtMethod method, long hash)
           throws CannotCompileException, NotFoundException, Exception;

   private static class ReferenceClassIterator
   {
      int size;
      int current;
      ArrayList<String> classes;
      HashSet<String> handledClasses;
      String currentEntry;

      public ReferenceClassIterator(Collection<String> refClasses)
      {
         size = refClasses.size();
         classes = new ArrayList<String>(refClasses.size());
         classes.addAll(refClasses);
         handledClasses = new HashSet<String>(refClasses.size());
      }

      boolean hasNext()
      {
         while (current < size)
         {
            String s = classes.get(current++);
            if (!handledClasses.contains(s))
            {
               handledClasses.add(s);
               currentEntry = s;
               return true;
            }
         }
         return false;
      }

      String next()
      {
         return currentEntry;
      }

      void addSuperClass(CtClass clazz)throws NotFoundException
      {
         if (clazz != null)
         {
            CtClass superClass = clazz.getSuperclass();
            if (superClass != null)
            {
               String name = superClass.getName();
               if (!handledClasses.contains(name))
               {
                  classes.add(name);
                  size++;
               }
            }
         }
      }
   }

   AspectManager getManager()
   {
      return manager;
   }
}
