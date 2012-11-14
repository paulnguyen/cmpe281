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
import org.jboss.aop.MethodByConInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodCallByConstructor;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.util.ReflectToJavassist;

public class MethodByConJoinPointGenerator extends JoinPointGenerator
{
   public static final String JOINPOINT_CLASS_PREFIX = JoinPointGenerator.JOINPOINT_CLASS_PREFIX + "MByC_";
   public static final String JOINPOINT_FIELD_PREFIX = JoinPointGenerator.JOINPOINT_FIELD_PREFIX + "MByC_";
   private static final Class<MethodCalledByConstructorInvocation> INVOCATION_TYPE = MethodCalledByConstructorInvocation.class;
   private static final Class<MethodCallByConstructor> JOINPOINT_TYPE = MethodCallByConstructor.class;
   
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

   boolean hasTargetObject;
   WeakReference<Class<?>> returnType;
   
   public MethodByConJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, getParameters((MethodByConInfo) info),
            ((MethodByConInfo) info).getMethod().getParameterTypes().length, false);
      if (!((MethodByConInfo)info).getMethod().getReturnType().equals(Void.TYPE))
      {
         returnType = new WeakReference<Class<?>>(((MethodByConInfo)info).getMethod().getReturnType());         
      }
      hasTargetObject = !java.lang.reflect.Modifier.isStatic(((MethodByConInfo)info).getMethod().getModifiers());
   }
   
   private static JoinPointParameters getParameters(MethodByConInfo info)
   {
      if (Modifier.isStatic(info.getMethod().getModifiers()))
      {
         return JoinPointParameters.CALLER_ARGS;
      }
      return JoinPointParameters.TARGET_CALLER_ARGS;
   }

   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      MethodByConInfo minfo = (MethodByConInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               callingIndex(minfo),
               calledClass(minfo),
               calledMethodHash(minfo));

      joinpointFieldName = getGeneratedJoinPointFieldName(
            callingIndex(minfo),
            calledClass(minfo),
            calledMethodHash(minfo));
   }

   private int callingIndex(MethodByConInfo info)
   {
      return info.getCallingIndex();
   }

   private String calledClass(MethodByConInfo info)
   {
      return info.getCalledClass().getName();
   }

   private long calledMethodHash(MethodByConInfo info)
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
      Method method = ((MethodCallByConstructor)joinPoint).getMethod();
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
            ((MethodCallByConstructor) joinPoint).getCallingClass(),
            hasCallingObject());
   }

   protected boolean isCaller()
   {
      return true;
   }

   protected boolean hasCallingObject()
   {
      return true;
   }

   protected boolean hasTargetObject()
   {
      return hasTargetObject;
   }

   protected static CtClass createJoinpointBaseClass(
            GeneratedAdvisorInstrumentor instrumentor,
            int callingIndex,
            CtClass callingClass,
            CtMethod targetMethod,
            String classname,
            long calledHash,
            String ciname) throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator generator = new BaseClassGenerator(instrumentor, callingClass, callingIndex, classname, targetMethod, calledHash, ciname);
      return generator.generate();
   }

   protected static String getGeneratedJoinPointClassName(int callingIndex, String classname, long calledHash)
   {
      return JOINPOINT_CLASS_PREFIX + CallerTransformer.getUniqueInvocationFieldname(callingIndex, classname, calledHash);
   }

   protected static String getGeneratedJoinPointFieldName(int callingIndex, String classname, long calledHash)
   {
      return JOINPOINT_FIELD_PREFIX + CallerTransformer.getUniqueInvocationFieldname(callingIndex, classname, calledHash);
   }

   private static class BaseClassGenerator
   {
      GeneratedAdvisorInstrumentor instrumentor;
      CtClass callingClass;
      int callingIndex;
      CtField callingField;
      CtClass targetClass;
      String classname;
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
            int callingIndex,
            String classname,
            CtMethod targetMethod,
            long calledHash,
            String ciname) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.callingClass = callingClass;
         this.callingIndex = callingIndex;
         this.classname = classname;
         this.targetClass = instrumentor.forName(classname);
         this.targetMethod = targetMethod;
         this.calledHash = calledHash;
         this.ciname = ciname;
         this.params = targetMethod.getParameterTypes();
         methodInfoClass = instrumentor.forName(CallerTransformer.METHOD_BY_CON_INFO_CLASS_NAME);
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
         addTypedCallingField();
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
         String className = getGeneratedJoinPointClassName(callingIndex, targetClass.getName(), calledHash);

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
         callingField = new CtField(callingClass, TYPED_CALLER_FIELD, jp);
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
         final int offset =  hasTargetObject ? 3 : 2;
         CtClass[] ctorParams1 = new CtClass[params.length + offset];
         CtClass[] ctorParams2 = new CtClass[offset];
         ctorParams1[0] = ctorParams2[0] = jp;
         if (hasTargetObject)
         {
            ctorParams1[1] = ctorParams2[1] = targetClass;
            ctorParams1[2] = ctorParams2[2] = callingClass;
         }
         else
         {
            ctorParams1[1] = ctorParams2[1] = callingClass;
         }
         System.arraycopy(params, 0, ctorParams1, offset, params.length);
                  
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");
         
         
         if (hasTargetObject)
         {
            body.append("   super.").append(TARGET_FIELD).append("=$2;");
            body.append("   this.").append(TYPED_TARGET_FIELD).append("=$2;");
            body.append("   super.").append(CALLER_FIELD).append("=$3;");
            body.append("   this.").append(callingField.getName()).append("=$3;");
         }
         else
         {
            body.append("   super.").append(CALLER_FIELD).append("=$2;");
            body.append("   this.").append(callingField.getName()).append("=$2;");
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


      private CtClass[] getInvokeJoinPointParameters()
      {
         if (hasTargetObject)
         {
            CtClass[] invokeParams = new CtClass[params.length + 2];
            invokeParams[0] = targetClass;
            invokeParams[1] = callingClass;
            System.arraycopy(params, 0, invokeParams, 2, params.length);
            return invokeParams;
         }
         CtClass[] invokeParams = new CtClass[params.length + 1];
         invokeParams[0] = callingClass;
         System.arraycopy(params, 0, invokeParams, 1, params.length);
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
               getInvokeJoinPointParameters(),
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
         addInvokeJoinPointDispatchMethod();
         addInvokeTargetMethod();
      }

      private void addInvokeJoinPointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices
         final boolean isVoid = targetMethod.getReturnType().equals(CtClass.voidType);

         final int offset = hasTargetObject ? 2 : 1;
         StringBuffer parameters = new StringBuffer();
         for (int i = 0 ; i < params.length ; i++)
         {
            if (i > 0)parameters.append(", ");
            parameters.append("$" + (i + offset + 1));
         }

         StringBuffer body = new StringBuffer("{");

         if (Modifier.isStatic(targetMethod.getModifiers()))
         {
            body.append(MethodExecutionTransformer.getReturnStr(isVoid) + targetClass.getName() + "." + targetMethod.getName() + "(" + parameters + ");");
         }
         else
         {
            body.append(MethodExecutionTransformer.getAopReturnStr(isVoid) + "$1." + targetMethod.getName() + "(" + parameters + ");");
         }

         body.append("}");
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  (isVoid) ? CtClass.voidType : targetMethod.getReturnType(),
                  JoinPointGenerator.DISPATCH,
                  getInvokeJoinPointParameters(),
                  targetMethod.getExceptionTypes(),
                  body.toString(),
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, getInvokeJoinPointParameters()), e);
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
