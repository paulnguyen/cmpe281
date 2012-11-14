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
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedConstructorExecutionTransformer extends
      ConstructorExecutionTransformer
{
   public OptimizedConstructorExecutionTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }
   
   protected void createWrapper(ConstructorTransformation trans)throws NotFoundException, CannotCompileException 
    {
      String invocationClass = OptimizedConstructorInvocations.getOptimizedInvocationClassName(trans.getClazz(), trans.getIndex());
      if(!Modifier.isPublic(trans.getConstructor().getModifiers())) {
         invocationClass = invocationClass.substring(invocationClass.lastIndexOf('.') + 1);
         invocationClass = trans.getClazz().getName() + "$" + invocationClass;
      }

      String infoName = getConstructorInfoFieldName(trans.getSimpleName(), trans.getIndex());
      String code =
         "{ " +
         "    " + constructorInfoFromWeakReference("info", infoName) +
         "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors(); " +
         "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null) " +
         "    { " +
         "       " + invocationClass + " invocation = new " + invocationClass + "(interceptors); " +
         "       invocation.setConstructor(info.getConstructor()); " +
         OptimizedBehaviourInvocations.setArguments(trans.getConstructor().getParameterTypes().length) +
         "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); " +
         "       return ($r)invocation.invokeNext(); " +
         "    } " +
         "    return new " + trans.getClazz().getName() + "($$); " +
         "}";
      
      // fill wrapped code only after the constructor invocations have been converted
      // by wrapper calls
      codifier.addPendingCode(trans.getWrapperMethod(), code);
   }
   
   protected void generateConstructorInfoField(CtClass clazz, CtConstructor constructor, int index) throws CannotCompileException, NotFoundException
   {
      super.generateConstructorInfoField(clazz, constructor, index);
      OptimizedConstructorInvocations.createOptimizedInvocationClass(getInstrumentor(), constructor.getDeclaringClass(), constructor, index);
   }

}
