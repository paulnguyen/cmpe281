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
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodExecution;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.util.ReflectToJavassist;

/** Creates the Joinpoint invocation replacement classes used with Generated advisors
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class MethodJoinPointGenerator extends JoinPointGenerator
{
   private static final Class<MethodInvocation> INVOCATION_TYPE = MethodInvocation.class;
   private static final Class<MethodExecution> JOINPOINT_TYPE = MethodExecution.class;
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
   boolean hasTargetObject;

   public MethodJoinPointGenerator(GeneratedClassAdvisor advisor, MethodInfo info)
   {
      super(advisor, info, getParameters(info),
            info.getMethod().getParameterTypes().length, false);
      if (super.getJoinpointField() != null)
      {
         if (!info.getUnadvisedMethod().getReturnType().equals(Void.TYPE))
         {
            returnType = new WeakReference<Class<?>>(info.getUnadvisedMethod().getReturnType());
         }
         hasTargetObject = !Modifier.isStatic(info.getMethod().getModifiers());
      }

   }
   
   private static JoinPointParameters getParameters(MethodInfo info)
   {
      if (Modifier.isStatic(info.getMethod().getModifiers()))
      {
         return JoinPointParameters.ONLY_ARGS;
      }
      return JoinPointParameters.TARGET_ARGS;
   }

   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      MethodInfo minfo = (MethodInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               advisedMethodName(minfo), 
               methodHash(minfo));
      
      joinpointFieldName = getGeneratedJoinPointFieldName(
               advisedMethodName(minfo), 
               methodHash(minfo));
   }
   
   private String advisedMethodName(MethodInfo info)
   {
      return info.getMethod().getName();
   }
   
   private long methodHash(MethodInfo info)
   {
      return info.getHash();
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
      Method method = ((MethodExecution)joinPoint).getMethod();
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
            hasTargetObject());
   }
   

   protected boolean hasTargetObject()
   {
      return hasTargetObject; 
   }

//   protected String getInfoName()
//   {
//      return MethodExecutionTransformer.getMethodInfoFieldName(
//            ((MethodInfo)info).getAdvisedMethod().getName(), ((MethodInfo)info).getHash());
//   }

   protected static CtClass createJoinpointBaseClass(
         GeneratedAdvisorInstrumentor instrumentor, 
         CtClass advisedClass, 
         CtMethod targetMethod,
         CtMethod wMethod,
         String miname, 
         String originalMethodName, 
         String wrappedMethodName, 
         long hash) throws CannotCompileException, NotFoundException
   {
      BaseClassGenerator factory = new BaseClassGenerator(instrumentor, advisedClass, targetMethod, wMethod, miname, originalMethodName, wrappedMethodName, hash);
      return factory.generate();
   }

   protected static String getGeneratedJoinPointFieldName(String methodName, long hash)
   {
      return JOINPOINT_FIELD_PREFIX + MethodExecutionTransformer.getMethodNameHash(methodName, hash);
   }

   private static String getGeneratedJoinPointClassName(String methodName, long hash)
   {
      return JOINPOINT_CLASS_PREFIX + MethodExecutionTransformer.getMethodNameHash(methodName, hash);
   }
   
   private static class BaseClassGenerator
   {
      GeneratedAdvisorInstrumentor instrumentor; 
      CtClass advisedClass; 
      CtMethod advisedMethod;
      CtMethod wMethod;
      String miname; 
      String originalMethodName; 
      String wrappedMethodName;
      long hash;
      boolean hasTargetObject;
      CtMethod targetMethod;
      CtClass jp;
      
      CtClass[] originalParams;
      CtClass[] params;
      CtClass methodInfoClass;
      
      BaseClassGenerator(GeneratedAdvisorInstrumentor instrumentor,  CtClass advisedClass, 
                        CtMethod targetMethod, CtMethod wMethod, String miname, 
                       String originalMethodName, String wrappedMethodName,long hash) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.advisedClass = advisedClass;
         this.advisedMethod = targetMethod;
         this.targetMethod = targetMethod;
         this.wMethod = wMethod;
         this.miname = miname;
         this.originalMethodName = originalMethodName;
         this.wrappedMethodName = wrappedMethodName;
         this.hash = hash;
         this.originalParams = targetMethod.getParameterTypes();
         this.params = GeneratedAdvisorMethodExecutionTransformer.addTargetToParamsForNonStaticMethod(advisedClass, targetMethod);
         methodInfoClass = instrumentor.forName(MethodExecutionTransformer.METHOD_INFO_CLASS_NAME);
         hasTargetObject = !Modifier.isStatic(advisedMethod.getModifiers());
      }

      protected CtClass generate() throws CannotCompileException, NotFoundException
      {
         jp = setupClass();
         // We might be marshalled so add special get arguments code
         OptimizedBehaviourInvocations.addArgumentFieldsAndAccessors(
               instrumentor.getClassPool(), jp, originalParams, true);
         if (hasTargetObject)
         {
            addTypedTargetField();
         }
         addInvokeJoinpointMethod();
         addMethodInfoField();
         addDefaultConstructor();
         addPublicConstructor();
         addProtectedConstructors();
         addDispatchMethods();
         
         TransformerCommon.compileOrLoadClass(advisedClass, jp);
         return jp; 
      }

      
      private CtClass setupClass()throws NotFoundException, CannotCompileException
      {
         String className = getGeneratedJoinPointClassName(originalMethodName, hash);
         
         //Create inner joinpoint class in advised class, super class is MethodInvocation
         jp = TransformerCommon.makeNestedClass(advisedClass, className, true);
         int mod = jp.getModifiers();
         jp.setModifiers(mod | Modifier.PUBLIC);
         
         CtClass methodInvocation = INVOCATION_CT_TYPE;
         jp.setSuperclass(methodInvocation);
         addUntransformableInterface(instrumentor, jp);
         
         return jp;
      }
      
      private void addTypedTargetField()throws CannotCompileException
      {
         CtField targetField = new CtField(advisedClass, TYPED_TARGET_FIELD, jp);
         jp.addField(targetField);
         targetField.setModifiers(Modifier.PROTECTED | Modifier.TRANSIENT);
      }
      
      /**
       * We need a default constructor so we can serialize
       */
      private void addDefaultConstructor() throws CannotCompileException
      {
         CtConstructor defaultConstructor = CtNewConstructor.defaultConstructor(jp);
         jp.addConstructor(defaultConstructor);
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
               "{super($1, $1.getInterceptors()); this." + INFO_FIELD + " = $1;}",
               jp);
               
         jp.addConstructor(publicConstructor);
      }
      
      /**
       * These constructors will be called by invokeJoinpoint in the generated
       * subclass when we need to instantiate a joinpoint containing target and args
       */
      protected void addProtectedConstructors() throws CannotCompileException
      {
         CtClass[] ctorParams1 = new CtClass[params.length + 1];
         CtClass[] ctorParams2 = null;
         ctorParams1[0] = jp;
         // copy first parameter
         System.arraycopy(params, 0, ctorParams1, 1, params.length);
         
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");
         
         int offset = 1;
         if (hasTargetObject)
         {
            offset = 2;
            // second version of constructor, that receives Arguments array
            ctorParams2 = new CtClass[2];
            System.arraycopy(ctorParams1, 0, ctorParams2, 0, 2);
                        
            body.append("   this." + TYPED_TARGET_FIELD + " = $2;");
            body.append("   super.setTargetObject($2);");
         }
         else
         {
            ctorParams2 = new CtClass[1];
            ctorParams2[0] = ctorParams1[0];
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
         if (params.length > 1)
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
      
      /**
       * Add an empty invokeJoinpoint() method. This method will be overridden by generated subclasses,
       * when the interceptors are rebuilt
       */
      private CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod invokeJoinpointMethod  = CtNewMethod.make(
               advisedMethod.getReturnType(), 
               INVOKE_JOINPOINT, 
               params, 
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
         jp.addField(infoField);
         infoField.setModifiers(Modifier.PROTECTED | Modifier.TRANSIENT);//Make visible to classes in child classloaders
      }      
      
      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         OptimizedMethodInvocations.addDispatch(jp, DISPATCH, targetMethod,
               !hasTargetObject);
         if (params.length > 0)
         {
            addInvokeJoinPointDispatchMethod();
         }
         
         addInvokeTargetMethod();
      }
      
      private void addInvokeJoinPointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices

         int offset = hasTargetObject ? 1 : 0;
         StringBuffer parameters = new StringBuffer();
         for (int i = 0 ; i < originalParams.length ; i++)
         {
            if (i > 0)parameters.append(", ");
            parameters.append("$" + (i + offset + 1));
         }
      
         String body = (!hasTargetObject) ?
               "{" + MethodExecutionTransformer.getReturnStr(advisedMethod) + advisedClass.getName() + "." + wrappedMethodName + "(" + parameters + ");}" :
               "{" + MethodExecutionTransformer.getAopReturnStr(advisedMethod) + "$1." + wrappedMethodName + "(" + parameters + ");}"; 
      
         
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  advisedMethod.getReturnType(), 
                  JoinPointGenerator.DISPATCH, 
                  params, 
                  advisedMethod.getExceptionTypes(), 
                  body, 
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, params), e);
         }
      }
      
      private void addInvokeTargetMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod template = INVOCATION_CT_TYPE.getDeclaredMethod(INVOKE_TARGET);
         
         boolean isVoid = advisedMethod.getReturnType().equals(CtClass.voidType);
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
