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
package org.jboss.test.aop.jdk15annotated;

import org.jboss.aop.Aspect;
import org.jboss.aop.Bind;
import org.jboss.aop.PointcutDef;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 75959 $
 */
@Aspect(scope = Scope.PER_INSTANCE)
        public class AspectPerInstance
{
   public int methodCalled;
   public int fieldRead;
   public int fieldWrite;
   
   @PointcutDef("execution(void org.jboss.test.aop.jdk15annotated.POJO*->someMethod())")
   Object pointcut;
   
   @PointcutDef("set(* org.jboss.test.aop.jdk15annotated.POJO*->field)")
   Object pointcut2;

   @Bind(pointcut = "this.pointcut")
           public Object methodAdvice(MethodInvocation invocation) throws Throwable
   {
      System.out.println("AspectPerInstance.methodAdvice accessing: " + invocation.getMethod().toString());
      methodCalled++;
      return invocation.invokeNext();
   }

   @Bind(pointcut = "org.jboss.test.aop.jdk15annotated.AspectPerInstance.pointcut2")
           public Object fieldAdvice(FieldWriteInvocation invocation) throws Throwable
   {
      System.out.println("AspectPerInstance.fieldAdvice writing to field: " + invocation.getField().getName());
      fieldWrite++;
      return invocation.invokeNext();
   }

   @Bind(pointcut = "get(* org.jboss.test.aop.jdk15annotated.POJO*->field)")
           public Object fieldAdvice(FieldReadInvocation invocation) throws Throwable
   {
      System.out.println("AspectPerInstance.fieldAdvice reading field: " + invocation.getField().getName());
      fieldRead++;
      return invocation.invokeNext();
   }
}
