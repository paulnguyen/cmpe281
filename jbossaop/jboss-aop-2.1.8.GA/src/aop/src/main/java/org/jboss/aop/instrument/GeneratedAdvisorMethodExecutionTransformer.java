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

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.util.JavassistMethodHashing;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Used with GeneratedAdvisorInstrumentor
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class GeneratedAdvisorMethodExecutionTransformer extends
      MethodExecutionTransformer
{
   public GeneratedAdvisorMethodExecutionTransformer(GeneratedAdvisorInstrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected String addMethodInfoFieldToGenAdvisor(MethodTransformation trans)throws NotFoundException, CannotCompileException
   {
      GeneratedAdvisorInstrumentor instrumentor = (GeneratedAdvisorInstrumentor)trans.getInstrumentor();
      CtClass genadvisor = instrumentor.getGenadvisor();
      String miname = addMethodInfoField(
            Modifier.PUBLIC,
            genadvisor,
            trans);

      addJoinpoint(miname, trans);

      instrumentor.initaliseMethodInfo(miname, trans.getHash(), JavassistMethodHashing.methodHash(trans.getMethod()));
      return miname;
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

   public static String getJoinPointFieldName(MethodTransformation trans)
   {
      return MethodJoinPointGenerator.getGeneratedJoinPointFieldName(trans.getOriginalName(), trans.getHash());
   }

   private void addJoinpoint(String miname, MethodTransformation trans)throws CannotCompileException, NotFoundException
   {
      CtClass joinpoint = createJoinpointClass(miname, trans);
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)trans.getInstrumentor()).getGenadvisor();
      CtField field = new CtField(
            joinpoint,
            getJoinPointFieldName(trans),
            genadvisor);
      field.setModifiers(Modifier.PUBLIC);
      genadvisor.addField(field);
   }

   private CtClass createJoinpointClass(String miname, MethodTransformation trans) throws CannotCompileException, NotFoundException
   {
      return MethodJoinPointGenerator.createJoinpointBaseClass(
            (GeneratedAdvisorInstrumentor)trans.getInstrumentor(),
            trans.getClazz(),
            trans.getMethod(),
            trans.getWMethod(),
            miname,
            trans.getOriginalName(),
            trans.getWrappedName(),
            trans.getHash());
   }

   public CtMethod addMixinWrappersAndInfo(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass clazz,
         CtClass mixinClass,
         String initializer,
         CtClass genadvisor,
         CtMethod mixinMethod) throws CannotCompileException, NotFoundException
   {
      String originalName = mixinMethod.getName();
      String originalBody =
         "{" +
         "   " + getReturnStr(mixinMethod) + " " + Instrumentor.mixinFieldName(mixinClass) + "." + mixinMethod.getName() + "($$);" +
         "}";

      CtMethod original = CtNewMethod.make(
            Modifier.PUBLIC,
            mixinMethod.getReturnType(),
            mixinMethod.getName(),
            mixinMethod.getParameterTypes(),
            mixinMethod.getExceptionTypes(),
            originalBody,
            clazz);
      clazz.addMethod(original);
      long hash = JavassistMethodHashing.methodHash(original);
      moveAnnotationsAndCopySignature(mixinMethod, original);

      String wrappedName = ClassAdvisor.notAdvisedMethodName(clazz.getName(), originalName);
      CtMethod wmethod = CtNewMethod.copy(original, clazz, null);

      wmethod.setName(wrappedName);
      clazz.addMethod(wmethod);
      moveAnnotationsAndCopySignature(original, wmethod);

      original.setName(wrappedName);
      Instrumentor.addSyntheticAttribute(original);
      wmethod.setName(originalName);

      MethodTransformation trans = new MethodTransformation(instrumentor, clazz, original, originalName, wmethod, wrappedName, hash);

      String methodInfoField = addMethodInfoFieldToGenAdvisor(trans);
      addMethodToGeneratedAdvisor(trans, methodInfoField);

      String wrapperBody =
         "{" +
         "   if (" + Instrumentor.mixinFieldName(mixinClass) + " == null)" +
         "   {" +
         "      " + Instrumentor.mixinFieldName(mixinClass) + " = " + initializer + ";" +
         "   }" +
         "   " + getReturnStr(trans.getMethod()) + " ((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(trans.getClazz()) + ")" + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")." + getAdvisorMethodName(trans) + "(this,$$);" +
         "}";
      wmethod.setBody(wrapperBody);
      return wmethod;
   }

   public CtMethod addMixinWrappersAndInfo(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass clazz,
         CtClass genadvisor,
         CtMethod mixinMethod,
         CtMethod delegate) throws CannotCompileException, NotFoundException
   {
      String originalName = mixinMethod.getName();
      String originalBody = "{ ";
      CtClass returnType = mixinMethod.getReturnType();
      if (!returnType.equals(CtClass.voidType))
      {
         if (returnType.isPrimitive())
         {
            if (returnType.equals(CtClass.booleanType))
            {
               originalBody += "return false;";
            }
            else if (returnType.equals(CtClass.byteType))
            {
               originalBody += "return (byte) 0;";
            }
            else if (returnType.equals(CtClass.charType))
            {
               originalBody += "return (char) 0;";
            }
            else if (returnType.equals(CtClass.doubleType))
            {
               originalBody += "return (double) 0.0;";
            }
            else if (returnType.equals(CtClass.floatType))
            {
               originalBody += "return (float) 0.0;";
            }
            else if (returnType.equals(CtClass.intType))
            {
               originalBody += "return 0;";
            }
            else if (returnType.equals(CtClass.longType))
            {
               originalBody += "return 0l;";
            }
            else if (returnType.equals(CtClass.shortType))
            {
               originalBody += "return (short) 0;";
            }
            else
            {
               throw new RuntimeException ("Unexpected primitive type: " + returnType.getName());
            }
         }
         else
         {
            originalBody += "return null;";
         }
      }
      originalBody += "}";

      CtMethod original = CtNewMethod.make(
            Modifier.PUBLIC,
            mixinMethod.getReturnType(),
            mixinMethod.getName(),
            mixinMethod.getParameterTypes(),
            mixinMethod.getExceptionTypes(),
            originalBody,
            clazz);
      clazz.addMethod(original);
      long hash = JavassistMethodHashing.methodHash(original);
      moveAnnotationsAndCopySignature(mixinMethod, original);

      String wrappedName = ClassAdvisor.notAdvisedMethodName(clazz.getName(), originalName);
      CtMethod wmethod = CtNewMethod.copy(original, clazz, null);

      wmethod.setName(wrappedName);
      clazz.addMethod(wmethod);
      moveAnnotationsAndCopySignature(original, wmethod);

      original.setName(wrappedName);
      Instrumentor.addSyntheticAttribute(original);
      wmethod.setName(originalName);

      MethodTransformation trans = new MethodTransformation(instrumentor, clazz, original, originalName, wmethod, wrappedName, hash);

      String methodInfoField = addMethodInfoFieldToGenAdvisor(trans);
      //delegate = genadvisor.getSuperclass().getSuperclass().getDeclaredMethod("invokeMethod");
      String body = "{ return ((org.jboss.aop.ClassAdvisor)this._getAdvisor()).invokeMethod($1, ";
      body += trans.getHash() + "L";
      for (int i = 0; i < mixinMethod.getParameterTypes().length; i++)
      {
         body +=  "$" + (i + 2);
      }
      body += ");}";
      addMethodToGeneratedAdvisor(trans, methodInfoField, body);
//      CtMethod newMethod = CtNewMethod.wrapped(trans.getWMethod().getReturnType(),
//            getAdvisorMethodName(trans),
//            addTargetToParamsForNonStaticMethod(trans.getClazz(), trans.getWMethod()),
//            trans.getWMethod().getExceptionTypes(),
//            delegate,
//            CtMethod.ConstParameter.integer(hash),
//            clazz);
//      genadvisor.addMethod(newMethod);
//      newMethod.setModifiers(Modifier.setProtected(newMethod.getModifiers()));
//      
      String wrapperBody =
         "{" +
         "   " + getReturnStr(trans.getMethod()) + " ((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(trans.getClazz()) + ")" + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")." + getAdvisorMethodName(trans) + "(this,$$);" +
         "}";
      wmethod.setBody(wrapperBody);
      return wmethod;
   }
   
   public void addMethodIntroductionInfo(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass clazz,
         CtMethod introducedMethod,
         long hash) throws CannotCompileException, NotFoundException
   {
      MethodTransformation trans = new MethodTransformation(instrumentor, clazz,
            introducedMethod, introducedMethod.getName(), introducedMethod,
            introducedMethod.getName(), hash);
      String methodInfoField = addMethodInfoFieldToGenAdvisor(trans);
      // TODO: this method is never called: we need to make interface introductions
      // work in the new mode.
      addMethodToGeneratedAdvisor(trans, methodInfoField);
   }
   
   protected void transformMethod(MethodTransformation trans, boolean wrap)
         throws CannotCompileException, NotFoundException
   {
      // generate Wrapper
      String wrappedName = ClassAdvisor.notAdvisedMethodName(trans.getClazzName(),
                                                             trans.getMethod().getName());
      CtMethod wmethod = CtNewMethod.copy(trans.getMethod(), trans.getClazz(), null);

      String originalName = trans.getOriginalName();
      wmethod.setName(wrappedName);
      trans.getClazz().addMethod(wmethod);
      moveAnnotationsAndCopySignature(trans.getMethod(), wmethod);
      trans.getMethod().setName(wrappedName);
      Instrumentor.addSyntheticAttribute(trans.getMethod());
      wmethod.setName(originalName);

      trans.setWMethod(wmethod, wrappedName);

      String methodInfoField = addMethodInfoFieldToGenAdvisor(trans);
      addMethodToGeneratedAdvisor(trans, methodInfoField);

      // prepareForWrapping
      getWrapper().prepareForWrapping(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);


      if (wrap)
      {
         // wrap
         getWrapper().wrap(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);

         // executeWrapping
         setWrapperBody(trans, methodInfoField);
      }
   }

   protected void doWrap(MethodTransformation trans, String methodInfoFieldName) throws NotFoundException,
         Exception
   {
      // TODO Auto-generated method stub

   }

   private void setWrapperBody(MethodTransformation trans, String methodInfoField) throws NotFoundException
   {
      String code = null;
      final String className =
         GeneratedAdvisorInstrumentor.getAdvisorFQN(trans.getClazz());

      try
      {
         if (Modifier.isStatic(trans.getMethod().getModifiers()))
         {
            code =
               "{" +
               "   " + getReturnStr(trans.getMethod()) + " ((" + className + ")" + Instrumentor.HELPER_FIELD_NAME + ")." + getAdvisorMethodName(trans) + "($$);" +
               "}";
            trans.setWMethodBody(code);
         }
         else
         {
            code =
               "{" +
               "   " + getReturnStr(trans.getMethod()) + " ((" + className + ")" + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")." + getAdvisorMethodName(trans) + "(this,$$);" +
               "}";

            trans.setWMethodBody(code);
         }
      }
      catch (CannotCompileException e)
      {
         e.printStackTrace();
         throw new RuntimeException("code was: " + code + " for method " + trans.getOriginalName());
      }
   }
   
   protected static CtClass[] addTargetToParamsForNonStaticMethod(CtClass outer, CtMethod method)throws NotFoundException
   {
      CtClass[] params = method.getParameterTypes();

      if (!Modifier.isStatic(method.getModifiers()))
      {
         CtClass[] tempParams = params;
         params = new CtClass[params.length + 1];
         params[0] = outer;
         System.arraycopy(tempParams, 0, params, 1, tempParams.length);
      }

      return params;
   }

   /**
    * Generates the method name for the inner advisor class. It cannot have the same
    * name/signature as the original in the outer class, or javassist gets
    * confused (Jira: JASSIST-12)
    *
    */
   private String getAdvisorMethodName(MethodTransformation trans)
   {
      if (trans.getHash() >= 0)
      {
         return trans.getOriginalName() + trans.getHash();
      }
      else
      {
         return trans.getOriginalName() + "_N_" + Math.abs(trans.getHash());
      }
   }

   private void addMethodToGeneratedAdvisor(MethodTransformation trans, String methodInfoField)throws CannotCompileException, NotFoundException
   {
      // TODO send methodInfoField value to createAdvisorMethodBody as a parameter
      String code = createAdvisorMethodBody(trans);
      this.addMethodToGeneratedAdvisor(trans, methodInfoField, code);
   }
   
   private void addMethodToGeneratedAdvisor(MethodTransformation trans, String methodInfoField, String body)throws CannotCompileException, NotFoundException
   {
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)trans.getInstrumentor()).getGenadvisor();

      CtClass[] params = addTargetToParamsForNonStaticMethod(trans.getClazz(), trans.getWMethod());

      String code = createAdvisorMethodBody(trans);
      try
      {
         CtMethod advisorMethod = CtNewMethod.make(
               Modifier.PROTECTED,
               trans.getWMethod().getReturnType(),
               getAdvisorMethodName(trans),
               params,
               trans.getWMethod().getExceptionTypes(),
               code,
               genadvisor);

         genadvisor.addMethod(advisorMethod);
         advisorMethod.setModifiers(Modifier.setProtected(advisorMethod.getModifiers()));
      }
      catch (CannotCompileException e)
      {
         throw new RuntimeException("code was: " + code + " for method " + getAdvisorMethodName(trans), e);
      }
   }

   private String createAdvisorMethodBody(MethodTransformation trans)throws NotFoundException
   {
      if (Modifier.isStatic(trans.getWMethod().getModifiers()))
      {
         return createStaticAdvisorMethodBody(trans);
      }
      else
      {
         return createNonStaticAdvisorMethodBody(trans);
      }
   }

   private String createStaticAdvisorMethodBody(MethodTransformation trans)throws NotFoundException
   {
      String joinpointName = getJoinPointFieldName(trans);
      String infoName = getMethodInfoFieldName(trans.getOriginalName(), trans.getHash());

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
         "           " + getReturnStr(trans.getWMethod()) + trans.getClazzName() + "." + trans.getWrappedName() +"($$);" +
         "      }" +
         "      else" +
         "      {" +
         "          " + getAopReturnStr(trans.getWMethod()) + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "($$);" +
         "      }" +
         "   } finally {" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
         "   }" +
         "}";

      return code;
   }

   private String createNonStaticAdvisorMethodBody(MethodTransformation trans)throws NotFoundException
   {
      String joinpointName = getJoinPointFieldName(trans);
      String infoName = getMethodInfoFieldName(trans.getOriginalName(), trans.getHash());
      
      String code =
         "{" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
         "   try" +
         "   {" +

         "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
         "      {" +
         "           super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
         "      }" +
         "      if (" + joinpointName + " == null)" +
         "      { " +
         "         " + getAopReturnStr(trans.getWMethod()) + "$1." + trans.getWrappedName() + "(" + getNonStaticJavasistParamString(trans.getWMethod().getParameterTypes().length) + ");" +
         "      }" +
         "      else" +
         "      {" +
         "       " + getAopReturnStr(trans.getWMethod()) + joinpointName + "." + MethodJoinPointGenerator.INVOKE_JOINPOINT + "($$);" +
         "      }" +
         "   } finally {" +
         GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
         "   }" +
         "}";

      return code;
   }

   public static String getNonStaticJavasistParamString(long parameterLength)throws NotFoundException
   {
      StringBuffer str = new StringBuffer();

      for (int i = 0 ; i < parameterLength ; i++)
      {
         if (i > 0)
         {
            str.append(", ");
         }
         str.append("$" + (i + 2));
      }

      return str.toString();
   }
}
