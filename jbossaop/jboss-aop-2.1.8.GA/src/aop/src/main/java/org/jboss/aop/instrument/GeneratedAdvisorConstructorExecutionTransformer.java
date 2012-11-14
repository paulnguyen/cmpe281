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

import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class GeneratedAdvisorConstructorExecutionTransformer extends
      ConstructorExecutionTransformer
{
   private static final AOPLogger logger = AOPLogger.getLogger(GeneratedAdvisorConstructorExecutionTransformer.class);
   
   public GeneratedAdvisorConstructorExecutionTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
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

   protected void generateConstructorInfoField(CtClass clazz, CtConstructor constructor, int index) throws CannotCompileException, NotFoundException
   {
      String ciname = addConstructorInfoField(
            Modifier.PROTECTED,
            ((GeneratedAdvisorInstrumentor)getInstrumentor()).getGenadvisor(),
            getConstructorInfoFieldName(clazz.getSimpleName(), index));

      addJoinpoint(clazz, constructor, ciname, index);

      final long wrapperHash = JavassistMethodHashing.methodHash(
            clazz.getDeclaredMethod(
                  constructorFactory(clazz.getSimpleName()),
                  constructor.getParameterTypes()));
      final long constructorHash = JavassistMethodHashing.constructorHash(constructor);

      ((GeneratedAdvisorInstrumentor)getInstrumentor()).initialiseConstructorInfoField(ciname, index, constructorHash, wrapperHash);
   }

   private void addJoinpoint(CtClass clazz, CtConstructor constructor, String ciname, int index)throws CannotCompileException, NotFoundException
   {
      CtClass joinpoint = createJoinpointClass(clazz, constructor, ciname, index);
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)getInstrumentor()).getGenadvisor();
      CtField field = new CtField(
            joinpoint,
            ConstructorJoinPointGenerator.getGeneratedJoinPointFieldName(clazz.getSimpleName(), index),
            genadvisor);
      field.setModifiers(Modifier.PUBLIC);
      genadvisor.addField(field);
   }

   private CtClass createJoinpointClass(CtClass clazz, CtConstructor constructor, String ciname, int index) throws CannotCompileException, NotFoundException
   {
      return ConstructorJoinPointGenerator.createJoinpointBaseClass(
            (GeneratedAdvisorInstrumentor)getInstrumentor(),
            clazz,
            constructor,
            ciname,
            index);
   }

   protected void initialiseWrapper(int mod, CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      CtClass genadvisor = getGenAdvisor();
      CtClass clazz = constructor.getDeclaringClass();

      CtClass[] exceptions = constructor.getExceptionTypes();
      CtClass type = constructor.getDeclaringClass();

      CtMethod innerWmethod = CtNewMethod.make(
            clazz,
            getInnerWrapperMethodName(constructor),
            constructor.getParameterTypes(),
            exceptions,
            null,
            genadvisor);
      innerWmethod.setModifiers(Modifier.clear(mod, Modifier.STATIC) & Modifier.PUBLIC);
      setTemporaryWrapperCode(type, innerWmethod);
      genadvisor.addMethod(innerWmethod);

      CtMethod wmethod = CtNewMethod.make(
            clazz,
            constructorFactory(clazz.getSimpleName()),
            constructor.getParameterTypes(),
            exceptions,
            null,
            clazz);
      wmethod.setModifiers(mod);
      wmethod.setBody("{return (("  +  GeneratedAdvisorInstrumentor.getAdvisorFQN(clazz) +
            ")" + Instrumentor.HELPER_FIELD_NAME + ")." + getInnerWrapperMethodName(constructor) + "($$);}");
      Instrumentor.addSyntheticAttribute(wmethod);
      clazz.addMethod(wmethod);

      // prepare ForWrapping
      getWrapper().prepareForWrapping(constructor, CONSTRUCTOR_STATUS);
   }

   protected void setEmptyWrapperCode(CtConstructor constructor)throws NotFoundException
   {
      CtClass clazz = constructor.getDeclaringClass();


      String innerCode =
         "{ " +
         "    return new " + clazz.getName() + "($$); " +
         "}";
      try
      {
         CtMethod innerWrapperMethod = getInnerWrapperMethod(constructor);
        innerWrapperMethod.setBody(innerCode);
      }
      catch (CannotCompileException e)
      {
        logger.error(innerCode);
        throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }

      try
      {
         CtMethod outerWrappedMethod = getWrapperMethod(constructor);
         outerWrappedMethod.setBody(outerDelegatingBody(constructor));
      }
      catch(CannotCompileException e)
      {
         logger.error(outerDelegatingBody(constructor));
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }

   }

   protected void setEmptyWrapperCodeLater(CtConstructor constructor) throws NotFoundException
   {
      CtMethod outerWrapperMethod = getWrapperMethod(constructor);
      this.codifier.addPendingCode(outerWrapperMethod, outerDelegatingBody(constructor));

      CtMethod innerWrapperMethod = getInnerWrapperMethod(constructor);
      String innerCode =
         "{ " +
         "    return new " + constructor.getDeclaringClass().getName() + "($$); " +
         "}";
      this.codifier.addPendingCode(innerWrapperMethod, innerCode);
   }

   private CtMethod getInnerWrapperMethod(CtConstructor constructor)throws NotFoundException
   {
      CtClass genadvisor = getGenAdvisor();
      return genadvisor.getDeclaredMethod(
            getInnerWrapperMethodName(constructor),
            constructor.getParameterTypes());

   }

   private String getInnerWrapperMethodName(CtConstructor constructor)
   {
      return constructorFactory(GeneratedAdvisorInstrumentor.getAdvisorName(constructor.getDeclaringClass()));
   }

   private String outerDelegatingBody(CtConstructor constructor)
   {
      return "{return ((" +
      GeneratedAdvisorInstrumentor.getAdvisorFQN(constructor.getDeclaringClass()) +
      ")" + Instrumentor.HELPER_FIELD_NAME + ")." + getInnerWrapperMethodName(constructor) + "($$);}";
   }

   private CtClass getGenAdvisor()
   {
      return ((GeneratedAdvisorInstrumentor)getInstrumentor()).getGenadvisor();
   }

   protected void createWrapper(ConstructorTransformation trans)
         throws CannotCompileException, NotFoundException
   {
      CtMethod outerWrapper = getWrapperMethod(trans.getConstructor());
      outerWrapper.setBody(outerDelegatingBody(trans.getConstructor()));

      CtMethod innerWrapper = getInnerWrapperMethod(trans.getConstructor());

      String joinpointName = ConstructorJoinPointGenerator.getGeneratedJoinPointFieldName(
            trans.getConstructor().getDeclaringClass().getSimpleName(),
            trans.getIndex());

      String infoName = getConstructorInfoFieldName(            
            trans.getConstructor().getDeclaringClass().getSimpleName(),
            trans.getIndex());

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
         "      if (" + joinpointName + " == null)" +
         "      { " +
         "         return new " + trans.getClassName() + "($$); " +
         "      }" +
         "      else" +
         "      {" +
         "         return " + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "($$);" +
         "      }" +
         "   } finally {" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
         "   }" +
         "}";

      codifier.addPendingCode(innerWrapper, code);
   }
}
