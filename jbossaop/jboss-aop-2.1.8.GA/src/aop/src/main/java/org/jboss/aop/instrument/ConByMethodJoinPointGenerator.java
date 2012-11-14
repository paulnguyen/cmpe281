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
import java.lang.reflect.Constructor;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.ConByMethodInfo;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.ConstructorCallByMethod;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.util.ReflectToJavassist;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
public class ConByMethodJoinPointGenerator extends JoinPointGenerator
{
   public static final String JOINPOINT_CLASS_PREFIX = JoinPointGenerator.JOINPOINT_CLASS_PREFIX + "CByM_";
   public static final String JOINPOINT_FIELD_PREFIX = JoinPointGenerator.JOINPOINT_FIELD_PREFIX + "CByM_";
   private static final Class<ConstructorCallByMethod> JOINPOINT_TYPE = ConstructorCallByMethod.class;
   private static final Class<ConstructorCalledByMethodInvocation> INVOCATION_TYPE = ConstructorCalledByMethodInvocation.class;
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
   
   private boolean hasCallingObject;
   private WeakReference<Class<?>> returnType;

   public ConByMethodJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, getParameters((ConByMethodInfo) info),
            ((ConByMethodInfo) info).getConstructor().getParameterTypes().length,
            false);
      hasCallingObject = !java.lang.reflect.Modifier.isStatic(((ConByMethodInfo)info).getCallingMethod().getModifiers());
      returnType = new WeakReference<Class<?>>(((ConByMethodInfo)info).getCalledClass());
   }
   
   private static JoinPointParameters getParameters(ConByMethodInfo info)
   {
      if (Modifier.isStatic(info.getCallingMethod().getModifiers()))
      {
         return JoinPointParameters.ONLY_ARGS;
      }
      return JoinPointParameters.CALLER_ARGS;
   }

   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      ConByMethodInfo cinfo = (ConByMethodInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               callingMethodHash(cinfo),
               calledClass(cinfo),
               calledConHash(cinfo));

      joinpointFieldName = getGeneratedJoinPointFieldName(
            callingMethodHash(cinfo),
            calledClass(cinfo),
            calledConHash(cinfo));

   }

   private long callingMethodHash(ConByMethodInfo info)
   {
      return info.getCallingMethodHash();
   }

   private String calledClass(ConByMethodInfo info)
   {
      return info.getCalledClass().getName();
   }

   private long calledConHash(ConByMethodInfo info)
   {
      return info.getCalledConHash();
   }

   protected boolean isVoid()
   {
      return false;
   }

   protected Class<?> getReturnClassType()
   {
      return returnType.get();
   }

   protected AdviceMethodProperties getAdviceMethodProperties(JoinPointBean joinPoint, AdviceSetup setup)
   {
      ConstructorCallByMethod call = (ConstructorCallByMethod) joinPoint;
      Constructor<?> ctor = call.getConstructor();
      AdviceMethodProperties properties = new AdviceMethodProperties(
               joinPoint,
               setup.getAspectClass(),
               setup.getAdviceName(),
               JOINPOINT_TYPE,
               INVOCATION_TYPE,
               ctor.getDeclaringClass(),
               ctor.getGenericParameterTypes(),
               ctor.getParameterTypes(),
               ctor.getGenericExceptionTypes(),
               call.getCalledClass(),
               false,
               call.getCallingClass(),
               hasCallingObject());
      return properties;
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
      return false;
   }

   protected void overrideDispatchMethods(CtClass superClass, CtClass clazz, JoinPointInfo newInfo) throws CannotCompileException, NotFoundException
   {
      super.overrideDispatchMethods(superClass, clazz, (ConByMethodInfo)newInfo);
   }

   protected static CtClass createJoinpointBaseClass(
            GeneratedAdvisorInstrumentor instrumentor,
            long callingHash,
            boolean hasCallingObject,
            CtClass callingClass,
            CtConstructor targetCtor,
            String classname,
            long calledHash,
            String ciname) throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator generator = new BaseClassGenerator(instrumentor, callingClass, callingHash, hasCallingObject, classname, targetCtor, calledHash, ciname);
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
      CtConstructor targetCtor;
      long calledHash;
      String ciname;

      CtClass jp;
      CtClass[] params;
      CtClass constructorInfoClass;

      BaseClassGenerator(
            GeneratedAdvisorInstrumentor instrumentor,
            CtClass callingClass,
            long callingHash,
            boolean hasCallingObject,
            String classname,
            CtConstructor targetCtor,
            long calledHash,
            String ciname) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.callingClass = callingClass;
         this.callingHash = callingHash;
         this.hasCallingObject = hasCallingObject;
         this.classname = classname;
         this.targetClass = instrumentor.forName(classname);
         this.targetCtor = targetCtor;
         this.calledHash = calledHash;
         this.ciname = ciname;
         this.params = targetCtor.getParameterTypes();
         constructorInfoClass = instrumentor.forName(CallerTransformer.CON_BY_METHOD_INFO_CLASS_NAME);
      }

      protected CtClass generate() throws CannotCompileException, NotFoundException
      {
         jp = setupClass();
         OptimizedBehaviourInvocations.addArgumentFieldsAndAccessors(
               instrumentor.getClassPool(), jp, params, false);
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
         String className = getGeneratedJoinPointClassName(callingHash, targetClass.getName(), calledHash);

         //Create inner joinpoint class in advised class, super class is ConstructorInvocation
         jp = TransformerCommon.makeNestedClass(callingClass, className, true, Modifier.PUBLIC | Modifier.STATIC, INVOCATION_CT_TYPE);
         addUntransformableInterface(instrumentor, jp);
         return jp;
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
               new CtClass[] {constructorInfoClass},
               new CtClass[0],
               "{super($1, null, $1.getInterceptors()); this." + INFO_FIELD + " = $1;}",
               jp);

         jp.addConstructor(publicConstructor);
      }

      /**
       * These constructors will be called by invokeJoinpoint in the generated
       * subclass when we need to instantiate a joinpoint containing target and args
       */
      protected void addProtectedConstructors() throws CannotCompileException
      {
         final int offset = (hasCallingObject) ? 2 : 1;
         CtClass[] ctorParams1 = new CtClass[params.length + offset];
         CtClass[] ctorParams2 = new CtClass[offset];
         ctorParams1[0] = ctorParams2[0] = jp;
         if (hasCallingObject)
         {
            ctorParams1[1] = ctorParams2[1] = callingClass;
         }
         System.arraycopy(params, 0, ctorParams1, offset, params.length);
                  
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");

         if (hasCallingObject)
         {
            body.append("   super.").append(CALLER_FIELD);
            body.append("=$").append(offset).append(';');
            body.append("   this.").append(TYPED_CALLER_FIELD);
            body.append("=$").append(offset).append(';');
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

      private CtClass[] getInvokeJoinPointParams()
      {
         if (hasCallingObject)
         {
            CtClass[] invokeParams = null;
            invokeParams = new CtClass[params.length + 1];
            invokeParams[0] = callingClass;
            System.arraycopy(params, 0, invokeParams, 1, params.length);
            return invokeParams;
         }
         return params;
      }

      /**
       * Add an empty invokeJoinpoint() method. This method will be overridden by generated subclasses,
       * when the interceptors are rebuilt
       */
      private CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod invokeJoinpointMethod  = CtNewMethod.make(
               targetClass,
               INVOKE_JOINPOINT,
               getInvokeJoinPointParams(),
               THROWS_THROWABLE,
               null,
               jp);
         invokeJoinpointMethod.setModifiers(Modifier.PROTECTED);
         jp.addMethod(invokeJoinpointMethod);
         return invokeJoinpointMethod;
       }

      private void addMethodInfoField()throws CannotCompileException
      {
         CtField infoField = new CtField(constructorInfoClass, INFO_FIELD, jp);
         infoField.setModifiers(javassist.Modifier.PROTECTED);//Make visible to classes in child classloaders
         jp.addField(infoField);
      }

      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         OptimizedConstructorInvocations.addDispatch(jp, DISPATCH, targetCtor);
         if (hasCallingObject || params.length > 0)
         {
            addInvokeJoinPointDispatchMethod();
         }
         
         addInvokeTargetMethod();
      }

      private void addInvokeJoinPointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices

         int offset = hasCallingObject ? 1 : 0;
         StringBuffer parameters = new StringBuffer("(");
         for (int i = 0 ; i < params.length ; i++)
         {
            if (i > 0)parameters.append(", ");
            parameters.append("$" + (i + offset + 1));
         }
         parameters.append(")");

         String body =
            "{" +
            "   " + targetClass.getName() + " obj = new " + targetClass.getName() + parameters + ";" +
            "   setTargetObject(obj);" +
            "   return obj;" +
            "}";

         CtClass[] params = getInvokeJoinPointParams();
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  targetClass,
                  JoinPointGenerator.DISPATCH,
                  params,
                  targetCtor.getExceptionTypes(),
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
         
         String body = "{return dispatch();}";
         
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

