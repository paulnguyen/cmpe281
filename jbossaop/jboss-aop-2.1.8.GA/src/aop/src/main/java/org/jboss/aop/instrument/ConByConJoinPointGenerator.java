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
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jboss.aop.ConByConInfo;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.ConstructorCallByConstructor;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.util.ReflectToJavassist;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
public class ConByConJoinPointGenerator extends JoinPointGenerator
{
   public static final String JOINPOINT_CLASS_PREFIX = JoinPointGenerator.JOINPOINT_CLASS_PREFIX + "CByC_";
   public static final String JOINPOINT_FIELD_PREFIX = JoinPointGenerator.JOINPOINT_FIELD_PREFIX + "CByC_";

   private static final Class<ConstructorCallByConstructor> JOINPOINT_TYPE = ConstructorCallByConstructor.class;
   private static final Class<ConstructorCalledByConstructorInvocation> INVOCATION_TYPE = ConstructorCalledByConstructorInvocation.class;
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

   private WeakReference<Class<?>> returnType;

   public ConByConJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, JoinPointParameters.CALLER_ARGS,
            ((ConByConInfo) info).getConstructor().getParameterTypes().length,
            false);
      returnType = new WeakReference<Class<?>>(((ConByConInfo)info).getCalledClass());
   }

   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      ConByConInfo cinfo = (ConByConInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               callingIndex(cinfo),
               calledClass(cinfo),
               calledConHash(cinfo));

      joinpointFieldName = getGeneratedJoinPointFieldName(
            callingIndex(cinfo),
            calledClass(cinfo),
            calledConHash(cinfo));

   }

   private int callingIndex(ConByConInfo info)
   {
      return info.getCallingIndex();
   }

   private String calledClass(ConByConInfo info)
   {
      return info.getCalledClass().getName();
   }

   private long calledConHash(ConByConInfo info)
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
      ConstructorCallByConstructor call = (ConstructorCallByConstructor) joinPoint;
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
            call.getCalledClass(), false,
            call.getCallingClass(),
            true);
      return properties;
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
      return false;
   }


   protected void overrideDispatchMethods(CtClass superClass, CtClass clazz, JoinPointInfo newInfo) throws CannotCompileException, NotFoundException
   {
      super.overrideDispatchMethods(superClass, clazz, (ConByConInfo)newInfo);
   }

   protected static CtClass createJoinpointBaseClass(
            GeneratedAdvisorInstrumentor instrumentor,
            int callingIndex,
            CtClass callingClass,
            CtConstructor targetCtor,
            String classname,
            long calledHash,
            String ciname) throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator generator = new BaseClassGenerator(instrumentor, callingClass, callingIndex, classname, targetCtor, calledHash, ciname);
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
            int callingIndex,
            String classname,
            CtConstructor targetCtor,
            long calledHash,
            String ciname) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.callingClass = callingClass;
         this.callingIndex = callingIndex;
         this.classname = classname;
         this.targetClass = instrumentor.forName(classname);
         this.targetCtor = targetCtor;
         this.calledHash = calledHash;
         this.ciname = ciname;
         this.params = targetCtor.getParameterTypes();
         constructorInfoClass = instrumentor.forName(CallerTransformer.CON_BY_CON_INFO_CLASS_NAME);
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
         String className = getGeneratedJoinPointClassName(callingIndex, targetClass.getName(), calledHash);

         //Create inner joinpoint class in advised class, super class is ConstructorInvocation
         jp = TransformerCommon.makeNestedClass(callingClass, className, true, Modifier.PUBLIC | Modifier.STATIC, INVOCATION_CT_TYPE);
         addUntransformableInterface(instrumentor, jp);
         return jp;
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
         CtClass[] ctorParams1 = new CtClass[params.length + 2];
         CtClass[] ctorParams2 = new CtClass[2];
         ctorParams1[0] = ctorParams2[0] = jp;
         ctorParams1[1] = ctorParams2[1] = callingClass;
         System.arraycopy(params, 0, ctorParams1, 2, params.length);
         
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");
         body.append("   super.").append(CALLER_FIELD).append("=$2;");
         body.append("   this.").append(callingField.getName()).append("=$2;");
         
         StringBuffer setArguments = new StringBuffer();
         int offset = 2;
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
               targetClass,
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
         CtField infoField = new CtField(constructorInfoClass, INFO_FIELD, jp);
         infoField.setModifiers(javassist.Modifier.PROTECTED);//Make visible to classes in child classloaders
         jp.addField(infoField);
      }

      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         OptimizedConstructorInvocations.addDispatch(jp, DISPATCH, targetCtor);
         addInvokeJoinpointDispatchMethod();
         addInvokeTargetMethod();
      }

      private void addInvokeJoinpointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         final int offset = 1;
         StringBuffer parameters = new StringBuffer();
         for (int i = 0 ; i < params.length ; i++)
         {
            if (i > 0)parameters.append(", ");
            parameters.append("$" + (i + offset + 1));
         }
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices
         String body =
            "{" +
            "   " + targetClass.getName() + " obj = new " + targetClass.getName() + "(" + parameters.toString() + ");" +
            "   setTargetObject(obj);" +
            "   return obj;" +
            "}";


         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  targetClass,
                  JoinPointGenerator.DISPATCH,
                  getInvokeJoinPointParameters(),
                  targetCtor.getExceptionTypes(),
                  body,
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for dispatch method " + getMethodString(jp, JoinPointGenerator.DISPATCH, params), e);
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
