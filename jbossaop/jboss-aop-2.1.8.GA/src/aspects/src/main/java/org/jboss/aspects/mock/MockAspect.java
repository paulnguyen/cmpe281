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
package org.jboss.aspects.mock;

import java.lang.reflect.Method;

import org.jboss.aop.Aspect;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Bind;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

@Aspect(scope = Scope.PER_VM)
public class MockAspect implements Interceptor
{

   private static String callbackClass;

   private static String callbackMethod;

   private static long expectedCalls;

   private static long realCalls;

   private final String BINDING_NAME = "_TestBinding";

   @Bind(pointcut = "execution(* *->@org.jboss.aspects.mock.Replace(..))")
   public Object advise(Invocation i) throws Throwable
   {
      try
      {
         // The pointcut above is a method pointcut (see Replace), so we can safely
         // cast this to a method invocation.
         MethodInvocation mi = (MethodInvocation) i;

         // Read value of Replace annotation
         Replace annotation = mi.getMethod().getAnnotation(Replace.class);
         String invocation = "execution(* " + annotation.invocation() + ")";

         // Save the annotation values as static variables for the interceptor to use later
         callbackClass = annotation.callbackClass();
         callbackMethod = annotation.callbackMethod();
         expectedCalls = annotation.expectedCalls();

         // Create new runtime interception for the pointcut given in the annotation variable
         AdviceBinding binding = new AdviceBinding(BINDING_NAME, invocation, null);
         binding.addInterceptor(MockAspect.class);
         AspectManager.instance().addBinding(binding);

         // Invoke the interceptor stack, calling the newly added interceptor
         return i.invokeNext();
      }
      finally
      {
         // Before returning the result, remove the newly added interceptor binding so that other
         // calls don't get the same binding. 
         AspectManager.instance().removeBinding(BINDING_NAME);

         // Before returning, verify that the number of calls wasn't less than expected
         if (realCalls < expectedCalls && expectedCalls < Long.MAX_VALUE)
         {
            throw new RuntimeException("Method: " + callbackMethod + " was called less than the expected "
                  + expectedCalls + " times! (" + realCalls + " times)");
         }

         // Re-initiate the used static variables.
         realCalls = 0;
      }
   }

   public java.lang.Object invoke(Invocation invocation) throws java.lang.Throwable
   {
      realCalls++;
      if (realCalls > expectedCalls)
      {
         throw new RuntimeException("Method: " + callbackMethod + " was called more than the expected " + expectedCalls
               + " times!");
      }
      MethodInvocation mi = (MethodInvocation) invocation;
      Class<?> clazz = Class.forName(callbackClass);
      Method method = clazz.getDeclaredMethod(callbackMethod, mi.getActualMethod().getParameterTypes());
      return method.invoke(clazz.newInstance(), mi.getArguments());
   }

   public String getName()
   {
      return "Conduct Isolate Aspect and Interceptor";
   }

}
