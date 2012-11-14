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
package org.jboss.test.aop.jdk15.dynamic.common;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * Binding interceptor. Is used by dynamic test cases 
 * for interception of joinpoints. This interception 
 * is achieved through the dynamic addition of advice
 * bindings.
 * @author Flavia Rainone
 */
public class BindingInterceptor implements Interceptor
{

   private static InterceptionsCount interceptionsCount = new InterceptionsCount();

   /**
    * Returns the total number of times that any instance
    * of this class intercepted a joinpoint, since the
    * last time that resetCounts was called.
    */
   public static InterceptionsCount getInterceptionsCount()
   {
      return interceptionsCount;
   }

   /**
    * Resets the counting of interceptions to zero.
    */
   public static void resetCounts()
   {
      interceptionsCount = new InterceptionsCount();
   }
   
   /**
    * Returns the name of this interceptor.
    */
   public String getName() { return "InstanceInterceptor"; }

   /**
    * Intercepts the joinpoint represented by <code>invocation</code>.
    */
   public Object invoke(Invocation invocation) throws Throwable
   {  
      interceptionsCount.total++;
      if (invocation instanceof ConstructorInvocation)
      {
         interceptionsCount.constructor++;
      }
      else if (invocation instanceof FieldInvocation)
      {
         if (invocation instanceof FieldReadInvocation)
         {
            interceptionsCount.fieldRead++;
         }
         else if (invocation instanceof FieldWriteInvocation)
         {
            interceptionsCount.fieldWrite++;
         }
         else
         {
            throw new RuntimeException("Unexpected invocation type invalidates test correction.");
         }
      }
      else if (invocation instanceof MethodInvocation)
      {
         interceptionsCount.method++;
      }
      return invocation.invokeNext();
   }
}