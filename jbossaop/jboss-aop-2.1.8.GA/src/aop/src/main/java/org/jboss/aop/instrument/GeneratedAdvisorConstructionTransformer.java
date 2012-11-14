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

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.util.JavassistMethodHashing;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class GeneratedAdvisorConstructionTransformer extends ConstructionTransformer
{

   public GeneratedAdvisorConstructionTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void generateConstructionInfoField(CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
      String ciname = addConstructionInfoField(
            Modifier.PROTECTED,
            genadvisor,
            constructor,
            index);

      addJoinpoint(constructor, ciname, index);

      long constructorHash = JavassistMethodHashing.constructorHash(constructor);
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseConstructionInfoField(ciname, index, constructorHash);
   }

   private void addJoinpoint(CtConstructor constructor, String ciname, int index)throws CannotCompileException, NotFoundException
   {
      CtClass clazz = constructor.getDeclaringClass();
      CtClass joinpoint = createJoinpointClass(clazz, constructor, ciname, index);
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
      CtField field = new CtField(
            joinpoint,
            ConstructionJoinPointGenerator.getGeneratedJoinPointFieldName(clazz.getSimpleName(), index),
            genadvisor);
      field.setModifiers(Modifier.PUBLIC);
      genadvisor.addField(field);
   }

   private CtClass createJoinpointClass(CtClass clazz, CtConstructor constructor, String ciname, int index) throws CannotCompileException, NotFoundException
   {
      return ConstructionJoinPointGenerator.createJoinpointBaseClass(
            (GeneratedAdvisorInstrumentor)instrumentor,
            clazz,
            constructor,
            ciname,
            index);
   }


   protected void generateNotMatchedConstructionInfoField(CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      generateConstructionInfoField(constructor, index);
   }

   @Override
   protected boolean addInfoAsWeakReference()
   {
      return false;
   }
   
   @Override
   protected boolean markInfoAsSynthetic()
   {
      return false;
   }

   public static String constructionFactory(String className)
   {
      if (className.indexOf('.') >= 0)throw new RuntimeException("constructorFactory() takes a simple class name:" + className);
      return className + "_construction_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;
   }

   protected void insertInterception(CtConstructor constructor, int index) throws Exception
   {
      final String constructionWrapperName = constructor.getDeclaringClass().getSimpleName() + "_con_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;

      String body = createInterceptingWrapperBody(constructor, index);
      @SuppressWarnings("unused")
      CtMethod wrapper = createWrapperMethod(constructor, constructionWrapperName, body);
      insertWrapperCallInCtor(constructor, constructionWrapperName);
      
   }

   private CtMethod createWrapperMethod(CtConstructor constructor, String wrapperName, String body)throws CannotCompileException, NotFoundException
   {
      final CtClass genadvisor = ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
      CtClass[] params = constructor.getParameterTypes();
      CtClass[] wrapperParams = new CtClass[params.length + 1];
      wrapperParams[0] = constructor.getDeclaringClass();
      System.arraycopy(params, 0, wrapperParams, 1, params.length);

      CtMethod wrapper = CtNewMethod.make(
            CtClass.voidType,
            wrapperName,
            wrapperParams,
            constructor.getExceptionTypes(),
            body,
            genadvisor);
      Instrumentor.addSyntheticAttribute(wrapper);
      genadvisor.addMethod(wrapper);
      return wrapper;
   }

   private void insertWrapperCallInCtor(CtConstructor constructor, String wrapperName)throws NotFoundException, CannotCompileException
   {
      String wrapperCall =
         "((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(constructor.getDeclaringClass())+ ")" + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")." +
         wrapperName + "(this" + ((constructor.getParameterTypes().length == 0) ? "" : ", $$") + ");";
      constructor.insertAfter(wrapperCall, false);
   }

   private String createInterceptingWrapperBody(CtConstructor constructor, int index)throws NotFoundException, CannotCompileException
   {
      String joinpointName = ConstructionJoinPointGenerator.getGeneratedJoinPointFieldName(constructor.getDeclaringClass().getSimpleName(), index);
      String infoName = getConstructionInfoFieldName(constructor.getDeclaringClass().getSimpleName(), index);
      String code =
         "{" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
         "   try" +
         "   {" +
         "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
         "      {" +
         "         if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
         "         {" +
         "            super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
         "         }" +
         "      }" +
         "      if (" + joinpointName + " != null)" +
         "      { " +
         "        " + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "($$);" +
         "      }" +
         "   } finally {" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
         "   }" +
         "}";

      return code;
   }
}
