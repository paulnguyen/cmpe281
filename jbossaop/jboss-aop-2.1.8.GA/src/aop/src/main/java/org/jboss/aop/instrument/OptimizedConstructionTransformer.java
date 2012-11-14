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


import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedConstructionTransformer extends ConstructionTransformer
{

   public OptimizedConstructionTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void insertInterception(CtConstructor constructor, int index) throws Exception
   {
      CtClass type = constructor.getDeclaringClass();
      
      OptimizedConstructionInvocations.createOptimizedInvocationClass(instrumentor, type, constructor, index);
      createWrapper(type, constructor,  constructor.getDeclaringClass(), index);
   }

   private void createWrapper(CtClass type, CtConstructor constructor, CtClass clazz, int index) throws Exception
   {
      String invocationClass = OptimizedConstructionInvocations.getOptimizedInvocationClassName(type, index);
      if(!Modifier.isPublic(constructor.getModifiers())) {
         invocationClass = invocationClass.substring(invocationClass.lastIndexOf('.') + 1);
         invocationClass = clazz.getName() + "$" + invocationClass;
      }
      
      String infoName = getConstructionInfoFieldName(clazz.getSimpleName(), index);
      
      String code =
         "    " + constructionInfoFromWeakReference("info", infoName) +
         "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors(); " +
         "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null) " +
         "    { " +
         "       " + invocationClass + " invocation = new " + invocationClass + "(interceptors, " +
         Instrumentor.HELPER_FIELD_NAME + ".getConstructors()[" + index + "]); " +
         OptimizedBehaviourInvocations.setArguments(constructor.getParameterTypes().length) +
         "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); " +
         "       invocation.setTargetObject(this); " +
         "       invocation.invokeNext(); " +
         "    } ";

      constructor.insertAfter(code, false);
   }}
