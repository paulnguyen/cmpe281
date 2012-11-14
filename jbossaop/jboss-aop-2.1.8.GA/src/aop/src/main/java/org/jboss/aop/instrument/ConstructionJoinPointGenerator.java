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

import org.jboss.aop.Advisor;
import org.jboss.aop.ConstructionInfo;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.Construction;
import org.jboss.aop.joinpoint.ConstructionInvocation;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.util.ReflectToJavassist;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class ConstructionJoinPointGenerator extends JoinPointGenerator
{
   public static final String JOINPOINT_CLASS_PREFIX = JoinPointGenerator.JOINPOINT_CLASS_PREFIX + "construction_";
   public static final String JOINPOINT_FIELD_PREFIX = JoinPointGenerator.JOINPOINT_FIELD_PREFIX + "construction_";
   
   private static final Class<Construction> JOINPOINT_TYPE = Construction.class;
   private static final Class<ConstructionInvocation> INVOCATION_TYPE = ConstructionInvocation.class;
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

   public ConstructionJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, JoinPointParameters.TARGET_ARGS,
            ((ConstructionInfo) info).getConstructor().getParameterTypes().length,
            false);
   }


   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      ConstructionInfo cinfo = (ConstructionInfo)info;
      joinpointClassName = getGeneratedJoinPointClassName(
               classSimpleName(cinfo),
               index(cinfo));

      joinpointFieldName = getGeneratedJoinPointFieldName(
               classSimpleName(cinfo),
               index(cinfo));
   }

   private String classSimpleName(ConstructionInfo info)
   {
      Constructor<?> ctor = info.getConstructor();
      return Advisor.getSimpleName(ctor.getDeclaringClass());
   }

   private int index(ConstructionInfo info)
   {
      return info.getIndex();
   }

   protected boolean isVoid()
   {
      return true;
   }

   protected Class<?> getReturnClassType()
   {
      return null;
   }

   protected AdviceMethodProperties getAdviceMethodProperties(JoinPointBean joinPoint, AdviceSetup setup)
   {
      Constructor<?> ctor = ((Construction)joinPoint).getConstructor();
      return new AdviceMethodProperties(
            joinPoint,
            setup.getAspectClass(),
            setup.getAdviceName(),
            JOINPOINT_TYPE,
            INVOCATION_TYPE,
            ctor.getDeclaringClass(),
            ctor.getGenericParameterTypes(),
            ctor.getParameterTypes(),
            ctor.getGenericExceptionTypes(),
            ctor.getDeclaringClass(),
            true);
   }

   protected boolean hasTargetObject()
   {
      return true;
   }

//   protected String getInfoName()
//   {
//      return ConstructionTransformer.getConstructionInfoFieldName(
//            Advisor.getSimpleName(advisor.getClazz()), ((ConstructionInfo)info).getIndex());
//   }


   protected static CtClass createJoinpointBaseClass(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass advisedClass,
         CtConstructor advisedCtor,
         String ciname,
         int index)throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator generator = new BaseClassGenerator(instrumentor, advisedClass, advisedCtor, ciname, index);
      return generator.generate();
   }

   protected static String getGeneratedJoinPointFieldName(String className, int index)
   {
      return JOINPOINT_FIELD_PREFIX + className + "_" + index;
   }

   private static String getGeneratedJoinPointClassName(String className, int index)
   {
      return JOINPOINT_CLASS_PREFIX + className + "_" + index;
   }

   private static class BaseClassGenerator
   {
      GeneratedAdvisorInstrumentor instrumentor;
      CtClass advisedClass;
      CtConstructor advisedCtor;
      String ciname;
      int index;

      CtClass jp;
      CtField targetField;
      CtClass[] originalParams;
      CtClass[] params;
      CtClass constructionInfoClass;

      BaseClassGenerator(
            GeneratedAdvisorInstrumentor instrumentor,
            CtClass advisedClass,
            CtConstructor advisedCtor,
            String ciname,
            int index) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.advisedClass = advisedClass;
         this.advisedCtor = advisedCtor;
         this.ciname = ciname;
         this.index = index;
         this.originalParams = advisedCtor.getParameterTypes();
         this.params = new CtClass[originalParams.length + 1];
         this.params[0] = advisedClass;
         System.arraycopy(originalParams, 0, params, 1, originalParams.length);

         constructionInfoClass = instrumentor.forName(ConstructionTransformer.CONSTRUCTION_INFO_CLASS_NAME);
      }

      protected CtClass generate() throws CannotCompileException, NotFoundException
      {
         jp = setupClass();
         OptimizedBehaviourInvocations.addArgumentFieldsAndAccessors(
               instrumentor.getClassPool(), jp, originalParams, false);
         addTypedTargetField();
         addInvokeJoinpointMethod();
         addConstructionInfoField();
         addPublicConstructor();
         addProtectedConstructors();
         addDispatchMethods();

         TransformerCommon.compileOrLoadClass(advisedClass, jp);
         return jp;
      }


      private CtClass setupClass()throws NotFoundException, CannotCompileException
      {
         String className = getGeneratedJoinPointClassName(advisedClass.getSimpleName(), index);

         //Create inner joinpoint class in advised class, super class is jp
         jp = TransformerCommon.makeNestedClass(advisedClass, className, true, Modifier.PUBLIC | Modifier.STATIC, INVOCATION_CT_TYPE);
         addUntransformableInterface(instrumentor, jp);
         return jp;
      }

      private void addTypedTargetField()throws CannotCompileException
      {
         targetField = new CtField(advisedClass, TYPED_TARGET_FIELD, jp);
         jp.addField(targetField);
         targetField.setModifiers(Modifier.PROTECTED);
      }

      /**
       * This constructor is used by the advisor when we have regenerated the joinpoint.
       * This just creates a generic JoinPoint instance with no data specific to the
       * method call
       */
      private void addPublicConstructor() throws CannotCompileException
      {
         CtConstructor publicConstructor = CtNewConstructor.make(
               new CtClass[] {constructionInfoClass},
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
         CtClass[] ctorParams2 = new CtClass[2];
         ctorParams1[0] = ctorParams2[0] = jp;
         System.arraycopy(params, 0, ctorParams1, 1, params.length);
         ctorParams2[1] = params[0];
                  
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");

         int offset = 2;
         body.append("   this." + targetField.getName() + " = $2;");
         body.append("   super.setTargetObject($2);");

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
               CtClass.voidType,
               INVOKE_JOINPOINT,
               params,
               THROWS_THROWABLE,
               null,
               jp);
         invokeJoinpointMethod.setModifiers(Modifier.PROTECTED);
         jp.addMethod(invokeJoinpointMethod);
         return invokeJoinpointMethod;
       }

      private void addConstructionInfoField()throws CannotCompileException
      {
         CtField infoField = new CtField(constructionInfoClass, INFO_FIELD, jp);
         infoField.setModifiers(javassist.Modifier.PROTECTED);//Make visible to classes in child classloaders
         jp.addField(infoField);
      }

      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         addInvokeNextDispatchMethod();
         addInvokeJoinPointDispatchMethod();
      }

      private void addInvokeNextDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeNext() methods for around advice

         String body =
               "{ return null;}";
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  CtClass.voidType,
                  JoinPointGenerator.DISPATCH,
                  EMPTY_CTCLASS_ARRAY,
                  advisedCtor.getExceptionTypes(),
                  body,
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, EMPTY_CTCLASS_ARRAY), e);
         }
      }

      private void addInvokeJoinPointDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices
         String body =
               "{ return null;}";

         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  CtClass.voidType,
                  JoinPointGenerator.DISPATCH,
                  params,
                  advisedCtor.getExceptionTypes(),
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
   }
}
