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

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.MethodByMethodInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodCallByMethod;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.util.ReflectToJavassist;

public class MethodByMethodJoinPointGenerator extends JoinPointGenerator
{
   public static final String JOINPOINT_CLASS_PREFIX = JoinPointGenerator.JOINPOINT_CLASS_PREFIX + "MByM_";
   public static final String JOINPOINT_FIELD_PREFIX = JoinPointGenerator.JOINPOINT_FIELD_PREFIX + "MByM_";
   private static final Class<MethodCallByMethod> JOINPOINT_TYPE = MethodCallByMethod.class;
   private static final Class<MethodCalledByMethodInvocation> INVOCATION_TYPE = MethodCalledByMethodInvocation.class;
   private static final CtClass INVOCATION_CT_TYPE;
   static
   {
      try
      {
         INVOCATION_CT_TYPE = ReflectToJavassist.classToJavassist(INVOCATION_TYPE);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   WeakReference<Class<?>> returnType;
   boolean hasCallingObject;
   boolean hasTargetObject;
   
   public MethodByMethodJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, getParameters((MethodByMethodInfo) info),
            ((MethodByMethodInfo) info).getMethod().getParameterTypes().length, false);

      if (!((MethodByMethodInfo)info).getMethod().getReturnType().equals(Void.TYPE))
      {
         returnType = new WeakReference<Class<?>>(((MethodByMethodInfo)info).getMethod().getReturnType());         
      }
      hasCallingObject = !java.lang.reflect.Modifier.isStatic(((MethodByMethodInfo)info).getCallingMethod().getModifiers());
      hasTargetObject = !java.lang.reflect.Modifier.isStatic(((MethodByMethodInfo)info).getMethod().getModifiers());
   }

   private static JoinPointParameters getParameters(MethodByMethodInfo info)
   {
      if (Modifier.isStatic(info.getCallingMethod().getModifiers()))
      {
         if (Modifier.isStatic(info.getMethod().getModifiers()))
         {
            return JoinPointParameters.ONLY_ARGS;
         }
         return JoinPointParameters.TARGET_ARGS;
      }
      if (Modifier.isStatic(info.getMethod().getModifiers()))
      {
         return JoinPointParameters.CALLER_ARGS;
      }
      return JoinPointParameters.TARGET_CALLER_ARGS;
   }
   
   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      MethodByMethodInfo minfo = (MethodByMethodInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               callingMethodHash(minfo),
               calledClass(minfo),
               calledMethodHash(minfo));

      joinpointFieldName = getGeneratedJoinPointFieldName(
            callingMethodHash(minfo),
            calledClass(minfo),
            calledMethodHash(minfo));
   }

   private long callingMethodHash(MethodByMethodInfo info)
   {
      return info.getCallingMethodHash();
   }

   private String calledClass(MethodByMethodInfo info)
   {
      return info.getCalledClass().getName();
   }

   private long calledMethodHash(MethodByMethodInfo info)
   {
      return info.getCalledMethodHash();
   }

   protected boolean isVoid()
   {
      return getReturnClassType() == null;
   }

   protected Class<?> getReturnClassType()
   {
      if (returnType == null)
      {
         return null;
      }
      return returnType.get();
   }

   protected AdviceMethodProperties getAdviceMethodProperties(JoinPointBean joinPoint, AdviceSetup setup)
   {
      Method method = ((MethodCallByMethod)joinPoint).getMethod();
      return new AdviceMethodProperties(
            joinPoint,
            setup.getAspectClass(),
            setup.getAdviceName(),
            JOINPOINT_TYPE,
            INVOCATION_TYPE,
            method.getGenericReturnType(),
            method.getGenericParameterTypes(),
            method.getParameterTypes(),
            method.getGenericExceptionTypes(),
            method.getDeclaringClass(),
            hasTargetObject(),
            ((MethodCallByMethod) joinPoint).getCallingClass(),
            hasCallingObject());
   }

   protected boolean isCaller()
   {
      return true;
   }

   protected boolean hasCallingObject()
   {
      return hasCallingObject;
   }

   protected boolean hasTargetObject()
   {
      return hasTargetObject;
   }

   protected static CtClass createJoinpointBaseClass(
         GeneratedAdvisorInstrumentor instrumentor,
         long callingHash,
         boolean hasCallingObject,
         CtClass callingClass,
         CtMethod targetMethod,
         String classname,
         long calledHash,
         String ciname) throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator generator = new BaseClassGenerator(instrumentor, callingClass, callingHash, hasCallingObject, classname, targetMethod, calledHash, ciname);
      return generator.generate();
   }

   protected static String getGeneratedJoinPointClassName(long callingHash, String classname, long calledHash)
   {
      return JOINPOINT_CLASS_PREFIX + CallerTransformer.getUniqueInvocationFieldname(callingHash, classname, calledHash);
   }

   protected static String getGeneratedJoinPointFieldName(long callingHash, String classname, long calledHash)
   {
      return JOINPOINT_FIELD_PREFIX + CallerTransformer.getUniqueInvocationFieldname(callingHash, classname, calledHash);
   }

   private static class BaseClassGenerator
   {
      GeneratedAdvisorInstrumentor instrumentor;
      CtClass callingClass;
      long callingHash;
      boolean hasCallingObject;
      String classname;
      CtClass targetClass;
      CtMethod targetMethod;
      long calledHash;
      String ciname;
      boolean hasTargetObject;

      CtClass jp;
      CtClass[] params;
      CtClass methodInfoClass;

      BaseClassGenerator(
            GeneratedAdvisorInstrumentor instrumentor,
            CtClass callingClass,
            long callingHash,
            boolean hasCallingObject,
            String classname,
            CtMethod targetMethod,
            long calledHash,
            String ciname) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.callingClass = callingClass;
         this.callingHash = callingHash;
         this.classname = classname;
         this.hasCallingObject = hasCallingObject;
         this.targetClass = instrumentor.forName(classname);
         this.targetMethod = targetMethod;
         this.calledHash = calledHash;
         this.ciname = ciname;
         this.params = targetMethod.getParameterTypes();
         methodInfoClass = instrumentor.forName(CallerTransformer.METHOD_BY_METHOD_INFO_CLASS_NAME);
         hasTargetObject = !Modifier.isStatic(targetMethod.getModifiers());
      }

      protected CtClass generate() throws CannotCompileException, NotFoundException
      {
         jp = setupClass();
         OptimizedBehaviourInvocations.addArgumentFieldsAndAccessors(
               instrumentor.getClassPool(), jp, params, false);
         if (hasTargetObject)
         {
            addTypedTargetField();
         }
         if (hasCallingObject)
         {
            addTypedCallingField();
         }
         addInvokeJoinpointMethod();
         addMethodInfoField();
         addPublicConstructor();
         addProtectedConstructors();
         addDispatchMethods();

         TransformerCommon.compileOrLoadClass(callingClass, jp);
         return jp;
      }


      private CtClass setupClass()throws NotFoundException, CannotCompileException
      {
         String className = getGeneratedJoinPointClassName(callingHash, targetClass.getName(), calledHash);

         //Create inner joinpoint class in advised class, super class is ConstructorInvocation
         jp = TransformerCommon.makeNestedClass(callingClass, className, true);
         int mod = jp.getModifiers();
         jp.setModifiers(mod | Modifier.PUBLIC);

         CtClass invocation = INVOCATION_CT_TYPE;
         jp.setSuperclass(invocation);
         addUntransformableInterface(instrumentor, jp);
         return jp;
      }

      private void addTypedTargetField()throws CannotCompileException
      {
         CtField targetField = new CtField(targetClass, TYPED_TARGET_FIELD, jp);
         jp.addField(targetField);
         targetField.setModifiers(Modifier.PROTECTED);
      }
      
      private void addTypedCallingField()throws CannotCompileException
      {
         CtField callingField = new CtField(callingClass, TYPED_CALLER_FIELD, jp);
         jp.addField(callingField);
         callingField.setModifiers(Modifier.PROTECTED);
      }
      
      /**
       * This constructor is used by the advisor when we have regenerated the joinpoint.
       * This just creates a generic JoinPoint instance with no data specific to the
       * method call
       */
      private void addPublicConstructor() throws CannotCompileException
      {
         CtConstructor publicConstructor = CtNewConstructor.make(
               new CtClass[] {methodInfoClass},
               new CtClass[0],
               "{super($1, null, null, $1.getInterceptors()); this." + INFO_FIELD + " = $1;}",
               jp);

         jp.addConstructor(publicConstructor);
      }

      /**
       * These constructors will be called by invokeJoinpoint in the generated
       * subclass when we need to instantiate a joinpoint containing target and args
       */
      protected void addProtectedConstructors() throws CannotCompileException
      {
         int offset = 1;
         if (hasTargetObject) offset++;
         if (hasCallingObject) offset++;
         CtClass[] ctorParams1 = new CtClass[params.length + offset];
         CtClass[] ctorParams2 = new CtClass[offset];

         int index = 0;
         ctorParams1[index] = ctorParams2[index++] = jp;
         if (hasTargetObject) ctorParams1[index] = ctorParams2[index++] = targetClass;
         if (hasCallingObject) ctorParams1[index] = ctorParams2[index++] = callingClass;
         System.arraycopy(params, 0, ctorParams1, offset, params.length);
                  
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");
         if (hasTargetObject)
         {
            body.append("   super.").append(TARGET_FIELD).append("=$2;");
            body.append("   this.").append(TYPED_TARGET_FIELD).append("=$2;");
         }
         if (hasCallingObject)
         {
            int arg = hasTargetObject ? 3 : 2;
            body.append("   super.").append(CALLER_FIELD);
            body.append("=$").append(arg).append(';');
            body.append("   this.").append(TYPED_CALLER_FIELD);
            body.append("=$").append(arg).append(';');
         }
         
         StringBuffer setArguments = new StringBuffer();
         for (int i = offset ; i < ctorParams1.length ; i++)
         {
            setArguments.append("   arg" + (i - offset) + " = $" + (i + 1)  + ";");
         }
         setArguments.append("}");

         CtConstructor protectedConstructor = CtNewConstructor.make(
               ctorParams1,
               new CtClass[0],
               body.toString() + setArguments.toString(),
               jp);
         protectedConstructor.setModifiers(Modifier.PROTECTED);
         jp.addConstructor(protectedConstructor);
         if (params.length > 0)
         {
            protectedConstructor = CtNewConstructor.make(
               ctorParams2,
               new CtClass[0],
               body.toString() + "}",
               jp);
            protectedConstructor.setModifiers(Modifier.PROTECTED);
            jp.addConstructor(protectedConstructor);
         }
      }

      private CtClass[] getInvokeJoinpointParameters()
      {
         if (!hasCallingObject && ! hasTargetObject)
         {
            return params;
         }

         int offset = 0;
         if (hasTargetObject) offset++;
         if (hasCallingObject) offset++;
         CtClass[] invokeParams = new CtClass[params.length + offset];

         int index = 0;
         if (hasTargetObject) invokeParams[index++] = targetClass;
         if (hasCallingObject) invokeParams[index++] = callingClass;
         System.arraycopy(params, 0, invokeParams, offset, params.length);

         return invokeParams;
      }
      /**
       * Add an empty invokeJoinpoint() method. This method will be overridden by generated subclasses,
       * when the interceptors are rebuilt
       */
      private CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod invokeJoinpointMethod  = CtNewMethod.make(
               targetMethod.getReturnType(),
               INVOKE_JOINPOINT,
               getInvokeJoinpointParameters(),
               THROWS_THROWABLE,
               null,
               jp);

         invokeJoinpointMethod.setModifiers(Modifier.PROTECTED);
         jp.addMethod(invokeJoinpointMethod);
         return invokeJoinpointMethod;
       }

      private void addMethodInfoField()throws CannotCompileException
      {
         CtField infoField = new CtField(methodInfoClass, INFO_FIELD, jp);
         infoField.setModifiers(javassist.Modifier.PROTECTED);//Make visible to classes in child classloaders
         jp.addField(infoField);
      }

      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         OptimizedMethodInvocations.addDispatch(jp, DISPATCH, targetMethod,
               !hasTargetObject);
         if (hasCallingObject || hasTargetObject || params.length > 0)
         {
            addInvokeJoinPointDispatchMethod();
         }
         addInvokeTargetMethod();
      }

      private void addInvokeJoinPointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices
         final boolean isVoid = targetMethod.getReturnType().equals(CtClass.voidType);
         CtClass[] invokeParams = getInvokeJoinpointParameters();

         final int offset = invokeParams.length - params.length;
         StringBuffer parameters = new StringBuffer();
         for (int i = 0 ; i < params.length ; i++)
         {
            if (i > 0)parameters.append(", ");
            parameters.append("$" + (i + offset + 1));
         }

         StringBuffer body = new StringBuffer("{");

         if (hasTargetObject)
         {
            body.append(MethodExecutionTransformer.getAopReturnStr(isVoid) + "$1." + targetMethod.getName() + "(" + parameters + ");");
         }
         else
         {
            body.append(MethodExecutionTransformer.getReturnStr(isVoid) + targetClass.getName() + "." + targetMethod.getName() + "(" + parameters + ");");
         }

         body.append("}");
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  (isVoid) ? CtClass.voidType : targetMethod.getReturnType(),
                  JoinPointGenerator.DISPATCH,
                  invokeParams,
                  targetMethod.getExceptionTypes(),
                  body.toString(),
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, invokeParams), e);
         }
      }

      private void addInvokeTargetMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod template = INVOCATION_CT_TYPE.getDeclaredMethod(INVOKE_TARGET);
         
         boolean isVoid = targetMethod.getReturnType().equals(CtClass.voidType);
         String body = (isVoid) ? "{dispatch(); return null;}" : "{return ($w)dispatch();}";
         
         CtMethod invokeTarget = CtNewMethod.make(
               template.getReturnType(),
               template.getName(),
               template.getParameterTypes(),
               template.getExceptionTypes(),
               body,
               jp);
         jp.addMethod(invokeTarget);
      }
   }
}