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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.annotation.Annotation;

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.util.Advisable;
import org.jboss.aop.util.JavassistMethodHashing;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 78208 $
 */
public abstract class MethodExecutionTransformer
{
   public static final String METHOD_INFO_CLASS_NAME = "org.jboss.aop.MethodInfo";
   private static final WrapperTransformer wrapper = new WrapperTransformer(WrapperTransformer.SINGLE_TRANSFORMATION);
   protected Instrumentor instrumentor;
   private JoinpointClassifier classifier;

   protected MethodExecutionTransformer(Instrumentor instrumentor)
   {
      this.instrumentor = instrumentor;
      this.classifier = instrumentor.joinpointClassifier;
   }

   protected static WrapperTransformer getWrapper()
   {
      return wrapper;
   }

   protected JoinpointClassifier getClassifier()
   {
      return classifier;
   }

   /**
    * Adds a MethodInfo field to the passed in class
    */
   protected String addMethodInfoField(int modifiers, CtClass addTo, MethodTransformation trans) throws NotFoundException, CannotCompileException
   {
      return addMethodInfoField(modifiers, addTo, trans, null);
   }

   /**
    * Adds a MethodInfo field to the passed in class
    */
   protected String addMethodInfoField(int modifiers, CtClass addTo, MethodTransformation trans, CtField.Initializer init) throws NotFoundException, CannotCompileException
   {
      String name = getMethodInfoFieldName(trans.getOriginalName(), trans.getHash());
      TransformerCommon.addInfoField(instrumentor, METHOD_INFO_CLASS_NAME, name, modifiers, addTo, addInfoAsWeakReference(), init, markInfoAsSynthetic());

      return name;
   }

   protected boolean addInfoAsWeakReference()
   {
      return true;
   }

   protected boolean markInfoAsSynthetic()
   {
      return true;
   }

   public static String getMethodNameHash(String methodName, long methodHash)
   {
      String hash;
      if (methodHash < 0)
         hash = "_N_" + (-1 * methodHash);
      else
         hash = "" + methodHash;
      return methodName + hash;
   }

   public static String getMethodInfoFieldName(String methodName, long methodHash)
   {
      String name = "aop$MethodInfo_" + getMethodNameHash(methodName, methodHash);
      return name;
   }

   protected static String methodInfoFromWeakReference(String localName, String methodInfoName)
   {
      return TransformerCommon.infoFromWeakReference(METHOD_INFO_CLASS_NAME, localName, methodInfoName);
   }

   public void instrument(CtClass clazz, ClassAdvisor advisor)throws NotFoundException, CannotCompileException
   {
      CtMethod[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++)
      {
         if (Modifier.isNative(methods[i].getModifiers()))
         {
            //We still need to weave native methods
         }
         else if (!Advisable.isAdvisable(methods[i]))
         {
            continue;
         }

         JoinpointClassification classification = classifier.classifyMethodExecution(methods[i], advisor);
         if (classification == JoinpointClassification.NOT_INSTRUMENTED)
         {
            continue;
         }
         instrumentor.setupBasics(clazz);
         MethodTransformation trans = new MethodTransformation(instrumentor, clazz, methods[i]);
         boolean wrap = (classification.equals(JoinpointClassification.WRAPPED));
         transformMethod(trans, wrap);

         int modifier = trans.getWMethod().getModifiers();
         if (Modifier.isNative(modifier))
         {
            modifier &=~Modifier.NATIVE;
            trans.getWMethod().setModifiers(modifier);
         }
      }
   }

   /**
    * Wraps the method executions contained in <code>methodInfos</code>.
    *
    * @param clazz              the class being instrumented.
    * @param constructorIndexes a collection of <code>org.jboss.aop.MethodInfo</code> indentifying
    *                           the methods to be wrapped.
    */
   public void wrap(CtClass clazz, Collection<org.jboss.aop.MethodInfo> methodInfos) throws Exception
   {
      for (org.jboss.aop.MethodInfo methodInfo : methodInfos)
      {
         Method method = methodInfo.getMethod();
         AOPClassPool classPool = (AOPClassPool) clazz.getClassPool();
         Class<?>[] parameterTypes = method.getParameterTypes();
         CtClass[] javassistParameterTypes = new CtClass[parameterTypes.length];
         for (int i = 0; i < parameterTypes.length; i++)
         {
            classPool.getLocally(parameterTypes[i].getName());
         }
         if (method.getName().indexOf("access$") >= 0)
         {
            continue;
         }

         String wrappedName = ClassAdvisor.notAdvisedMethodName(clazz.getName(), method.getName());
         CtMethod wmethod = clazz.getDeclaredMethod(method.getName(), javassistParameterTypes);
         if (wrapper.isNotPrepared(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX))
         {
            continue;
         }
         MethodTransformation trans = new MethodTransformation(
               instrumentor,
               clazz,
               clazz.getDeclaredMethod(wrappedName, javassistParameterTypes),
               method.getName(),
               clazz.getDeclaredMethod(method.getName(), javassistParameterTypes),
               wrappedName);

         // wrap
         wrapper.wrap(trans.getWMethod(), WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);
         // executeWrapping
         String methodInfoFieldName = getMethodInfoFieldName(trans.getOriginalName(), trans.getHash());
         doWrap(trans, methodInfoFieldName);
      }
   }

   /**
    * Unwraps the method executions contained in <code>methodInfos</code>.
    *
    * @param clazz              the class being instrumented.
    * @param constructorIndexes a collection of <code>org.jboss.aop.MethodInfo</code> indentifying
    *                           the methods to be unwrapped.
    */
   public void unwrap(CtClass clazz, Collection<org.jboss.aop.MethodInfo> methodInfos) throws Exception
   {
      for (org.jboss.aop.MethodInfo methodInfo : methodInfos)
      {
         Method method = methodInfo.getMethod();
         AOPClassPool classPool = (AOPClassPool) clazz.getClassPool();
         Class<?>[] parameterTypes = method.getParameterTypes();
         CtClass[] javassistParameterTypes = new CtClass[parameterTypes.length];
         for (int i = 0; i < parameterTypes.length; i++)
         {
            javassistParameterTypes[i] = classPool.getLocally(parameterTypes[i].getName());
         }
         CtMethod javassistWMethod = clazz.getDeclaredMethod(method.getName(), javassistParameterTypes);
         if (wrapper.isNotPrepared(javassistWMethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX))
         {
            continue;
         }
         CtMethod javassistMethod = clazz.getDeclaredMethod(ClassAdvisor.notAdvisedMethodName(clazz.getName(), method.getName()),
                                                            javassistParameterTypes);
         // wrap
         wrapper.unwrap(javassistWMethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);
         // executeUnwrapping
         javassistWMethod.setBody(javassistMethod, null);
      }
   }

   protected void moveAnnotationsAndCopySignature(CtMethod src, CtMethod dest) throws NotFoundException
   {
      MethodInfo mi = src.getMethodInfo2();
      MethodInfo wmi = dest.getMethodInfo2();

      moveAnnotations(mi, wmi, AnnotationsAttribute.invisibleTag);
      moveAnnotations(mi, wmi, AnnotationsAttribute.visibleTag);
      int numParams = src.getParameterTypes().length;
      moveParameterAnnotations(numParams, mi, wmi, ParameterAnnotationsAttribute.visibleTag);
      moveParameterAnnotations(numParams, mi, wmi, ParameterAnnotationsAttribute.invisibleTag);
      copySignature(mi, wmi);
   }

   private void moveAnnotations(MethodInfo src, MethodInfo dest, String annotationTag)
   {
      AnnotationsAttribute attribute = (AnnotationsAttribute) src.getAttribute(annotationTag);
      if (attribute != null)
      {
         @SuppressWarnings("unchecked")
         HashMap map = new HashMap();
         dest.addAttribute(attribute.copy(dest.getConstPool(), map));
         src.addAttribute(new AnnotationsAttribute(src.getConstPool(), annotationTag));
      }
   }
   
   private void moveParameterAnnotations(int numParams, MethodInfo src, MethodInfo dest, String paramsTag)
   {
      ParameterAnnotationsAttribute params = (ParameterAnnotationsAttribute)src.getAttribute(paramsTag);
      if (params != null)
      {
         @SuppressWarnings("unchecked")
         HashMap map = new HashMap();
         dest.addAttribute(params.copy(dest.getConstPool(), map));
         ParameterAnnotationsAttribute srcParams = new ParameterAnnotationsAttribute(src.getConstPool(), paramsTag);
         Annotation[][] emptyParamAnnotations = new Annotation[numParams][];
         for (int i = 0 ; i < numParams ; i++)
         {
            emptyParamAnnotations[i] = new Annotation[0];
         }
         srcParams.setAnnotations(emptyParamAnnotations);
         src.addAttribute(srcParams);
      }
   }

   private void copySignature(MethodInfo src, MethodInfo dest)
   {
      SignatureAttribute attribute = (SignatureAttribute) src.getAttribute(SignatureAttribute.tag);
      if (attribute != null)
      {
         @SuppressWarnings("unchecked")
         HashMap map = new HashMap();
         dest.addAttribute(attribute.copy(dest.getConstPool(), map));
      }
   }
   
   protected static String getAopReturnStr(CtMethod method)throws NotFoundException
   {
      return getAopReturnStr(method.getReturnType().equals(CtClass.voidType));
   }

   protected static String getAopReturnStr(boolean isVoid)throws NotFoundException
   {
      return isVoid ? "" : "return ($r)";
   }

   protected static String getReturnStr(CtMethod method)throws NotFoundException
   {
      return getReturnStr(method.getReturnType().equals(CtClass.voidType));
   }

   protected static String getReturnStr(boolean isVoid)throws NotFoundException
   {
      return isVoid ? "" : "return ";
   }

   protected abstract void transformMethod(MethodTransformation trans, boolean wrap) throws CannotCompileException, NotFoundException;
   protected abstract void doWrap(MethodTransformation trans, String methodInfoFieldName)throws NotFoundException, Exception;

   protected class MethodTransformation
   {
      Instrumentor instrumentor;
      CtClass clazz;
      CtMethod method;
      String originalName;
      CtMethod wmethod;
      String wrappedName;
      long hash;

      public MethodTransformation(Instrumentor instrumentor, CtClass clazz, CtMethod method)
      {
         this.instrumentor = instrumentor;
         this.clazz = clazz;
         this.method = method;
         this.originalName = method.getName();
         hash = JavassistMethodHashing.methodHash(method);
      }

      public MethodTransformation(Instrumentor instrumentor,
            CtClass clazz,
            CtMethod method,
            String originalName,
            CtMethod wmethod,
            String wrappedName)
      {
         this.instrumentor = instrumentor;
         this.clazz = clazz;
         this.method = method;
         this.originalName = originalName;
         this.wmethod = wmethod;
         this.wrappedName = wrappedName;
         hash = JavassistMethodHashing.methodHash(wmethod);
      }

      public MethodTransformation(Instrumentor instrumentor,
            CtClass clazz,
            CtMethod method,
            String originalName,
            CtMethod wmethod,
            String wrappedName,
            long hash)
      {
         this.instrumentor = instrumentor;
         this.clazz = clazz;
         this.method = method;
         this.originalName = originalName;
         this.wmethod = wmethod;
         this.wrappedName = wrappedName;
         this.hash = hash;
      }


      public void setWMethod(CtMethod wmethod, String wrappedName)
      {
         this.wmethod = wmethod;
         this.wrappedName = wrappedName;
      }

      public void setWMethodBody(String code)throws CannotCompileException
      {
         wmethod.setBody(code);
      }

      public String getOriginalName()
      {
         return originalName;
      }

      public String getWrappedName()
      {
         return wrappedName;
      }

      public CtClass getClazz()
      {
         return clazz;
      }

      public String getClazzName()
      {
         return clazz.getName();
      }

      public CtMethod getMethod()
      {
         return method;
      }

      public CtMethod getWMethod()
      {
         return wmethod;
      }

      public long getHash()
      {
         return hash;
      }

      public Instrumentor getInstrumentor()
      {
         return instrumentor;
      }

   }
}