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

import javassist.CtConstructor;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class NonOptimizedConstructionTransformer extends
      ConstructionTransformer
{

   public NonOptimizedConstructionTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void insertInterception(CtConstructor constructor, int index) throws Exception
   {
      String args = "";
      if (constructor.getParameterTypes().length > 0)
      {
         args = ", $args";
      }
      
      String infoName = getConstructionInfoFieldName(constructor.getDeclaringClass().getSimpleName(), index);
      
      String code =
         "    " + constructionInfoFromWeakReference("info", infoName) +
         "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors(); " +
         "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null) " +
         "    { " +
         "       org.jboss.aop.joinpoint.ConstructionInvocation invocation = new org.jboss.aop.joinpoint.ConstructionInvocation(interceptors, " +
         Instrumentor.HELPER_FIELD_NAME + ".getConstructors()[" + index + "]" + args + "); " +
         "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); " +
         "       invocation.setTargetObject(this); " +
         "       invocation.invokeNext(); " +
         "    } ";
      constructor.insertAfter(code, false);
   }

}
