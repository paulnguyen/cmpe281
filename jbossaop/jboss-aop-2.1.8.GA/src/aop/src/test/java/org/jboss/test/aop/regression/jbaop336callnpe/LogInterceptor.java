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
package org.jboss.test.aop.regression.jbaop336callnpe;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class LogInterceptor implements Interceptor
{
   public static String logFile;

   public String getName()
   {
      return this.getClass().getName();
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      if (invocation instanceof ConstructorCalledByConstructorInvocation)
      {
         logFile = (String)((ConstructorCalledByConstructorInvocation)invocation).getArguments()[0];
      }
      else if (invocation instanceof ConstructorCalledByMethodInvocation)
      {
         logFile = (String) ((ConstructorCalledByMethodInvocation) invocation).getArguments()[0];
      }
      else if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         logFile = (String)((MethodCalledByConstructorInvocation)invocation).getArguments()[0];
      }
      else if (invocation instanceof MethodCalledByMethodInvocation)
      {
         logFile = (String) ((MethodCalledByMethodInvocation) invocation).getArguments()[0];
      }
      
      return invocation.invokeNext();
   }

}
