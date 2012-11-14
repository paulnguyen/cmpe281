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
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.ClassAdvisor;


/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedMethodExecutionTransformer extends MethodExecutionTransformer 
{

   public OptimizedMethodExecutionTransformer(Instrumentor instrumentor) 
   {
      super(instrumentor);
   }
   
   protected void transformMethod(MethodTransformation trans, boolean wrap)throws CannotCompileException, NotFoundException
   {
      String methodInfoField = addMethodInfoField(Modifier.PRIVATE | Modifier.STATIC, trans.getClazz(), trans);
      String wrappedName = ClassAdvisor.notAdvisedMethodName(trans.getClazzName(),
                                                             trans.getOriginalName());
      CtMethod wmethod = CtNewMethod.copy(trans.getMethod(), trans.getClazz(), null);

      // generate Wrapper
      String originalName = trans.getOriginalName();
      wmethod.setName(wrappedName);
      trans.getClazz().addMethod(wmethod);
      moveAnnotationsAndCopySignature(trans.getMethod(), wmethod);
      String optimizedInvocation = OptimizedMethodInvocations.createOptimizedInvocationClass(trans.getInstrumentor(), trans.getClazz(), trans.getMethod(), wmethod);
      trans.getMethod().setName(wrappedName);
      Instrumentor.addSyntheticAttribute(trans.getMethod());
      wmethod.setName(originalName);

      trans.setWMethod(wmethod, wrappedName);
      // prepareForWrapping
      getWrapper().prepareForWrapping(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);

      if (wrap)
      {
         // wrap
         getWrapper().wrap(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);
         // executeWrapping
         setWrapperBody(trans, methodInfoField, optimizedInvocation);
      }
   }

   protected void doWrap(MethodTransformation trans, String methodInfoFieldName)throws NotFoundException, Exception
   {
      String invocationClassName = OptimizedMethodInvocations.getOptimizedInvocationClassName(trans.getClazz(), trans.getWMethod());
      invocationClassName = invocationClassName.substring(invocationClassName.lastIndexOf('.') + 1);
      invocationClassName = trans.getMethod().getDeclaringClass().getName() + "$" + invocationClassName;
      setWrapperBody(trans, methodInfoFieldName, invocationClassName);
   }
   
   protected void setWrapperBody(MethodTransformation trans, String methodInfoField, String optimizedInvocation) throws NotFoundException
   {
      boolean isStatic = Modifier.isStatic(trans.getMethod().getModifiers());
      String code = null;
      if (!isStatic)
      {
         code =
         "{ " +
         "    " + methodInfoFromWeakReference("info", methodInfoField) +
         "    org.jboss.aop.ClassInstanceAdvisor instAdv = (org.jboss.aop.ClassInstanceAdvisor)_getInstanceAdvisor();" +
         "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors();" +
         "    if (interceptors != (Object[])null || (instAdv != null && instAdv.hasInstanceAspects)) " +
         "    { " +
         "       if (instAdv != null) " +
         "       { " +
         "          interceptors = instAdv.getInterceptors(interceptors); " +
         "       } " +
         "       " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(info, interceptors); " +
         "       " + OptimizedBehaviourInvocations.setArguments(trans.getMethod().getParameterTypes().length) +
         "       invocation.setTargetObject(this); " +
         "       invocation.typedTargetObject = this; " +
         "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); " +
         "       " + getAopReturnStr(trans.getMethod()) + "invocation.invokeNext(); " +
         "    } " +
         "    else " +
         "    {" +
         "       " + getReturnStr(trans.getMethod()) + " " + trans.getWrappedName() + "($$); " +
         "    }" +
         "}";
      }
      else
      {
         code =
         "{ " +
         "    " + methodInfoFromWeakReference("info", methodInfoField) +
         "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors();" +
         "    if (interceptors != (Object[])null) " +
         "    { " +
         "       " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(info, interceptors); " +
         "       " + OptimizedBehaviourInvocations.setArguments(trans.getMethod().getParameterTypes().length) +
         "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); " +
         "       " + getAopReturnStr(trans.getMethod()) + "invocation.invokeNext(); " +
         "    } " +
         "    else " +
         "    {" +
         "       " + getReturnStr(trans.getMethod()) + " " + trans.getWrappedName() + "($$); " +
         "    }" +
         "}";
      }
      try
      {
         trans.setWMethodBody(code);
      }
      catch (CannotCompileException e)
      {
         e.printStackTrace();
         throw new RuntimeException("code was: " + code + " for method " + trans.getOriginalName());  //To change body of catch statement use Options | File Templates.
      }
   }
   
}
