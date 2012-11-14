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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 78458 $
 */
public abstract class ConstructorExecutionTransformer implements CodeConversionObserver
{
   private static final AOPLogger logger = AOPLogger.getLogger(ConstructorExecutionTransformer.class);
   
   protected static final String CONSTRUCTOR_INFO_CLASS_NAME = "org.jboss.aop.ConstructorInfo";
   
   protected Instrumentor instrumentor;
   protected Codifier codifier;
   private JoinpointClassifier classifier;
   //private JoinpointClassifier advisenessClassifier;
   private static final WrapperTransformer wrapper = 
      new WrapperTransformer(new String[]{"wrapperStatus", "constructorsWrapped"});
   protected static final int CONSTRUCTOR_STATUS = 0;
   private static final int ALL_CONSTRUCTORS_STATUS = 1;
   
   protected ConstructorExecutionTransformer(Instrumentor instrumentor)
   {
      this.instrumentor = instrumentor;
      this.codifier = new Codifier();
      this.classifier = instrumentor.joinpointClassifier;
      //this.advisenessClassifier = new JoinpointSimpleClassifier();
   }
   
   protected Instrumentor getInstrumentor()
   {
      return instrumentor;
   }

   protected WrapperTransformer getWrapper()
   {
      return wrapper;
   }

   public static String constructorFactory(String className)
   {
      if (className.indexOf('.') >= 0)throw new RuntimeException("constructorFactory() takes a simple class name: " + className);
      return className + "_new_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;
   }

   /**
    * Adds a ConstructorInfo field to the passed in class
    */
   protected String addConstructorInfoField(int modifiers, CtClass addTo, String infoName) throws NotFoundException, CannotCompileException
   {
      return addConstructorInfoField(modifiers, addTo, infoName, null);
   }
   
   /**
    * Adds a ConstructorInfo field to the passed in class
    */
   protected String addConstructorInfoField(int modifiers, CtClass addTo, String infoName, CtField.Initializer init) throws NotFoundException, CannotCompileException
   {
      //Instrumentor claspool will be null during hotswap, in which case we
      //already will have created the field
      if (instrumentor.getClassPool() != null)
      {
         try
         {
            addTo.getDeclaredField(infoName);
            return infoName;
         }
         catch(NotFoundException e)
         {
         }
         TransformerCommon.addInfoField(instrumentor, CONSTRUCTOR_INFO_CLASS_NAME, infoName, modifiers, addTo, addInfoAsWeakReference(), init, markInfoAsSynthetic());

      }
      return infoName;
   }

   protected boolean addInfoAsWeakReference()
   {
      return true;
   }
   
   protected boolean markInfoAsSynthetic()
   {
      return true;
   }

   public static String getConstructorInfoFieldName(String classname, int index)
   {
      if (classname.indexOf('.') >= 0)throw new RuntimeException("Simple name should be used: " + classname);
      return "aop$constructorInfo_" + index;
   }
      
   protected static String constructorInfoFromWeakReference(String localName, String ctorInfoName)
   {
      return TransformerCommon.infoFromWeakReference(CONSTRUCTOR_INFO_CLASS_NAME, localName, ctorInfoName);      
   }
   
   /**
    * Transforms the constructor executions of this class according to the pointcuts
    * registered in <code>AspectManager</code>.
    * @param clazz the clazz to be transformed.
    * @param classAdvisor the advisor associated to <code>clazz</code>.
    * @return <code>true</code> is <code>clazz</code> is instrumented.
    */
   public boolean transform(CtClass clazz, ClassAdvisor classAdvisor) throws Exception
   {
      List<CtConstructor> constructors = instrumentor.getConstructors(clazz);
      boolean wrappersGenerated = false;
      boolean oneOrMoreWrapped = false;
      int i = 0;
      boolean dynamicallyWrapped = false;
      boolean notDynamicallyWrapped = false;
      CtConstructor firstConstructor = null;
      if (!constructors.isEmpty())
      {
         firstConstructor = constructors.get(0);
      }
      if (constructors.size() > 0)
      {
         for (Iterator<CtConstructor> iterator = constructors.iterator(); iterator.hasNext(); i++)
         {
            CtConstructor constructor = iterator.next();
            
            JoinpointClassification classification = classifier.classifyConstructorExecution(constructor, classAdvisor);
            
            if (classification == JoinpointClassification.NOT_INSTRUMENTED
                  && !oneOrMoreWrapped)
            {
               continue;
            }
            else if (!wrappersGenerated)
            {
               //generateWrapper + prepareForWrapping
               buildConstructorWrappers(clazz, classAdvisor);
               wrappersGenerated = true;
               wrapper.prepareForWrapping(firstConstructor, ALL_CONSTRUCTORS_STATUS);
            }
            
            if (classification.equals(JoinpointClassification.WRAPPED))
            {
               if (!oneOrMoreWrapped)
               {
                  for (int j = 0; j < i; j++)
                  {
                     this.setEmptyWrapperCodeLater(constructors.get(j));
                  }
                  oneOrMoreWrapped = true;
               }
               wrap(clazz, constructor, i);
               dynamicallyWrapped = dynamicallyWrapped || classification.equals(JoinpointClassification.DYNAMICALY_WRAPPED);
               notDynamicallyWrapped = notDynamicallyWrapped || !classification.equals(JoinpointClassification.DYNAMICALY_WRAPPED);
            }
            else if (oneOrMoreWrapped)
            {
               this.setEmptyWrapperCodeLater(constructor);
            }
         }
      }
      
      if (oneOrMoreWrapped)
      {
         wrapAllConstructors(clazz, firstConstructor, null);
      }
      if (dynamicallyWrapped && !notDynamicallyWrapped)
      {
         instrumentor.dynamicTransformationObserver.constructorDynamicalyWrapped();
      }
      return wrappersGenerated;
   }


   /**
    * This method is responsible for replacing all invocations of constructors
    * by wrapper calls. Because we are using CodeConverter, either all constructor
    * calls are invoked, or none. This means that when there are one or more constructors
    * WRAPPED, all constructor calls are replaced by ivocations to a constructor wrapper, in
    * which case unwrapped constructors must provide a wrapper method. 
    * This method updates the ALL_CONSTRUCTORS_STATUS to WRAPPED.
    * @param clazz the target class
    * @param firstConstructor the ALL_CONSTRUCTORS_STATUS is registered in the first
    * constructor (index 0) of <code>clazz</code>. So, this constructor is needed for 
    * ALL_CONSTRUCTORS_STATUS update.
    * @param constructors provide this list if you want to assure wrapper methods associated
    * with UNWRAPPED constructors keep unchanged (avoid infinite recursion in this wrappers).
    */
   private void wrapAllConstructors(final CtClass clazz, CtConstructor firstConstructor, List<CtConstructor> constructors) throws NotFoundException, CannotCompileException
   {
      wrapper.wrap(firstConstructor, ALL_CONSTRUCTORS_STATUS);
      if (constructors == null)
      {
         return;
      }
      for (Iterator<CtConstructor> i = constructors.iterator(); i.hasNext();)
      {
         CtConstructor constructor = i.next();
         if (!wrapper.isWrapped(constructor, CONSTRUCTOR_STATUS))
         {
            setEmptyWrapperCodeLater(constructor);
         }
      }
      instrumentor.converter.replaceNew(clazz, clazz, constructorFactory(clazz.getSimpleName()));
   }

   /**
    * Wraps the constructor executions contained in <code>constructorIndexes</code>.
    * @param clazz the class being instrumented.
    * @param constructorIndexes a collection of <code>java.lang.Integer</code> indentifying
    * the constructors to be wrapped.
    */
   public void wrap(CtClass clazz, Collection<Integer> constructorIndexes) throws Exception
   {
      List<CtConstructor> constructors = instrumentor.getConstructors(clazz);
      // if none constructor has been prepared, do nothing
      CtConstructor firstConstructor = constructors.get(0);
      if (wrapper.isNotPrepared(firstConstructor , ALL_CONSTRUCTORS_STATUS))
      {
         return;
      }
      // generate wrapper code
      for (Integer constructorIndex : constructorIndexes)
      {
         CtConstructor constructor = constructors.get(constructorIndex);
         wrap(clazz, constructor, constructorIndex);
      }
      // if none constructors have been wrapped until now, replace constructors calls
      // by wrapper invocations
      if (!wrapper.isWrapped(firstConstructor, ALL_CONSTRUCTORS_STATUS))
      {
         wrapAllConstructors(clazz, firstConstructor, constructors);
      }
   }

   /**
    * Wraps the <code>constructor</code> execution.
    * @param clazz the class being instrumented.
    * @param constructor the constructor to be wrapped.
    * @param constructorIndex the index that identifies <code>constructor</code>.
    */
   private void wrap(CtClass clazz, CtConstructor constructor, int constructorIndex) throws NotFoundException, Exception, CannotCompileException
   {
      //CtClass type = constructor.getDeclaringClass();
      if (wrapper.isNotPrepared(constructor, CONSTRUCTOR_STATUS))
      {
         return;
      }
      //wrap
      wrapper.wrap(constructor, CONSTRUCTOR_STATUS);
      // executeWrapping
      CtMethod wrapperMethod = clazz.getDeclaredMethod(constructorFactory(clazz.getSimpleName()), constructor.getParameterTypes());
      setTemporaryWrapperCode(constructor.getDeclaringClass(), wrapperMethod);
      ConstructorTransformation trans = new ConstructorTransformation(clazz, constructor, wrapperMethod, constructorIndex);
      createWrapper(trans);
   }

   /**
    * Unwraps the constructor executions contained in <code>constructorIndexes</code>.
    * @param clazz the class being instrumented.
    * @param constructorIndexes a collection of <code>java.lang.Integer</code> indentifying
    * the constructors to be unwrapped.
    */
   public void unwrap(CtClass clazz, Collection<Integer> constructorIndexes) throws NotFoundException
   {
      List<CtConstructor> constructors = instrumentor.getConstructors(clazz);
      // the joinpoint is not prepared for wrapping
      if (wrapper.isNotPrepared(constructors.get(0), ALL_CONSTRUCTORS_STATUS))
      {
         return;
      }
      for (Integer constructorIndex : constructorIndexes)
      {
         CtConstructor constructor = constructors.get(constructorIndex);
         if (wrapper.isNotPrepared(constructor, CONSTRUCTOR_STATUS))
         {
            continue;
         }
         //unwrap
         wrapper.unwrap(constructor, CONSTRUCTOR_STATUS);
         // executeUnwrapping
         
         setEmptyWrapperCode(constructor);
      }
   }
   
   /**
    * Notifies this transformer that the code conversion is done.
    */
   public void codeConverted() throws CannotCompileException
   {
      this.codifier.codifyPending();
   }

   public boolean replaceConstructorAccess(ClassAdvisor sourceAdvisor, CtClass source) throws NotFoundException
   {
      if (!isAnyConstructorAdvised(source, sourceAdvisor))
      {
         return false;
      }
      instrumentor.converter.replaceNew(source, source, constructorFactory(source.getSimpleName()));
      return true;
   }

   // generateWrapper + prepareForWrapping
   protected void buildConstructorWrappers(CtClass clazz, ClassAdvisor advisor)
   throws Exception
   {
      instrumentor.setupBasics(clazz);
      List<CtConstructor> constructors = instrumentor.getConstructors(clazz);

      Iterator<CtConstructor> it = constructors.iterator();
      for (int index = 0; it.hasNext(); index++)
      {
         // generate wrapper
         CtConstructor constructor = it.next();
         int mod = Modifier.STATIC;
         if ((constructor.getModifiers() & Modifier.PUBLIC) != 0)
         {
            mod |= Modifier.PUBLIC;
         }
         else if ((constructor.getModifiers() & Modifier.PROTECTED) != 0)
         {
            mod |= Modifier.PROTECTED;
         }
         else if ((constructor.getModifiers() & Modifier.PRIVATE) != 0)
         {
            mod |= Modifier.PRIVATE;
         }
         else
         {
            mod |= Modifier.PUBLIC;
         }

         initialiseWrapper(mod, constructor, index);
         generateConstructorInfoField(clazz, constructor, index);         
      }
   }
   
   protected void generateConstructorInfoField(CtClass clazz, CtConstructor constructor, int index) throws CannotCompileException, NotFoundException
   {
      String name = getConstructorInfoFieldName(clazz.getSimpleName(), index);
      addConstructorInfoField(Modifier.PRIVATE | Modifier.STATIC, clazz, name);
   }
   
   /**
    * Sets a temporary wrapper method code. Later, the wrapper method body
    * must be replaced by it's definitive implementation.
    * @param type
    * @param wrapperMethod
    */
   protected void setTemporaryWrapperCode(CtClass type, CtMethod wrapperMethod)
   {
      String code = "{" +
      "   return null;" +
      "}";
      try
      {
        wrapperMethod.setBody(code);
      }
      catch (CannotCompileException e)
      {
        logger.error("Cannot compile " + code);
        throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   /**
    * Sets the wrapper method code as an empty wrapper, i.e., a wrapper that simply
    * invokes the constructor.
    * @param type the class being instrumented.
    * @param wrapperMethod the wrapper method.
    */
   protected void setEmptyWrapperCode(CtConstructor constructor)throws NotFoundException
   {
      CtMethod wrapperMethod = getWrapperMethod(constructor);      
      String code =
         "{ " +
         "    return new " + constructor.getDeclaringClass().getName() + "($$); " +
         "}";
      try
      {
        wrapperMethod.setBody(code);
      }
      catch (CannotCompileException e)
      {
        logger.error("Cannot compile " + code);
        throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   /**
    * Sets the wrapper method code as an empty wrapper, i.e., a wrapper that simply
    * invokes the constructor.
    * @param type the class being instrumented.
    * @param wrapperMethod the wrapper method.
    * @throws NotFoundException 
    */
   protected void setEmptyWrapperCodeLater(CtConstructor constructor) throws NotFoundException
   {
      CtMethod wrapperMethod = getWrapperMethod(constructor);      
      String code =
         "{ " +
         "    return new " + constructor.getDeclaringClass().getName() + "($$); " +
         "}";
        this.codifier.addPendingCode(wrapperMethod, code);
   }
   

   /**
    * Returns true if there is at least one constructor that is classified as WRAPPED.
    * @param clazz  the clazz whose constructors will be classified.
    * @param advisor the advisor associated with <code>clazz</code>
    * @return <code>true</code> only if one or more <code>clazz</code> constructors are
    * classified as WRAPPED.
    */
   protected boolean isAnyConstructorAdvised(CtClass clazz, ClassAdvisor advisor) throws NotFoundException
   {
      CtConstructor[] constructors = clazz.getDeclaredConstructors();
      for (int i = 0; i < constructors.length; i++)
      {
         JoinpointClassification classification = classifier.classifyConstructorExecution(constructors[i], advisor);
         if (classification.equals(JoinpointClassification.WRAPPED))
            return true;
      }
      return false;
   }

   // currently used by CallerTransformer
   public static boolean isAdvisableConstructor(CtConstructor con, ClassAdvisor advisor) throws NotFoundException
   {
      Collection<Pointcut> pointcuts = advisor.getManager().getBindingCollection().getConstructorExecutionPointcuts();
      for (Pointcut pointcut : pointcuts)
      {
         if (pointcut.matchesExecution(advisor, con))
         {
            return true;
         }
      }
      return false;
   }

   protected abstract void createWrapper(ConstructorTransformation trans) throws CannotCompileException, NotFoundException;

   protected void initialiseWrapper(int mod, CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      CtClass clazz = constructor.getDeclaringClass();
      CtClass[] exceptions = constructor.getExceptionTypes();
      String name = clazz.getSimpleName();
      CtClass type = constructor.getDeclaringClass();

      CtMethod wmethod = CtNewMethod.make(type, constructorFactory(name), constructor.getParameterTypes(), exceptions, null, clazz);
      wmethod.setModifiers(mod);
      setTemporaryWrapperCode(type, wmethod);
      Instrumentor.addSyntheticAttribute(wmethod);
      clazz.addMethod(wmethod);
      
      // copy attribute signature
      MethodInfo constructorInfo = constructor.getMethodInfo2();
      SignatureAttribute attribute = (SignatureAttribute) constructorInfo.getAttribute(SignatureAttribute.tag);
      if (attribute != null)
      {
         MethodInfo wrapperInfo = wmethod.getMethodInfo2();
         @SuppressWarnings("unchecked")
         HashMap map = new HashMap();
         wrapperInfo.addAttribute(attribute.copy(wrapperInfo.getConstPool(), map));
      }
      
      // prepare ForWrapping
      getWrapper().prepareForWrapping(constructor, CONSTRUCTOR_STATUS);
   }

   protected CtMethod getWrapperMethod(CtConstructor constructor) throws NotFoundException
   {
      CtClass clazz = constructor.getDeclaringClass();
      return clazz.getDeclaredMethod(
            constructorFactory(clazz.getSimpleName()), 
            constructor.getParameterTypes());      
   }

   protected class ConstructorTransformation
   {
      CtClass clazz;
      CtConstructor constructor;
      CtMethod wrapperMethod;
      int index;
      
      public ConstructorTransformation(CtClass clazz, CtConstructor constructor, CtMethod wrapper, int index)
      {
         this.clazz = clazz;
         this.constructor = constructor;
         this.wrapperMethod = wrapper;
         this.index = index;
      }

      public CtMethod getWrapperMethod()
      {
         return wrapperMethod;
      }
      
      public void setWrapperMethod(CtMethod wrapperMethod)
      {
         this.wrapperMethod = wrapperMethod;
      }
      
      public CtClass getClazz()
      {
         return clazz;
      }
      
      public CtConstructor getConstructor()
      {
         return constructor;
      }
      
      public int getIndex()
      {
         return index;
      }

      public String getClassName()
      {
         return clazz.getName();
      }

      public String getSimpleName()
      {
         return clazz.getSimpleName();
      }
   }
}
