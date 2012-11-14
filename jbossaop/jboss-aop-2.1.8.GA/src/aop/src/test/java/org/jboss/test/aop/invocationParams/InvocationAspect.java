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
package org.jboss.test.aop.invocationParams;

import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * Aspect used on tests with typed Invocation parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class InvocationAspect
{
   static String advice = null;
   static Object invokeReturn = null;
   
   public static void clear()
   {
      advice = null;
      invokeReturn = null;
   }
   
   public Object aroundMethodExecution1(MethodInvocation invocation) throws Throwable
   {
      advice = "aroundMethodExecution1";
      return "aroundMethodExecution1";
   }
   
   public Object aroundMethodExecution2(MethodInvocation invocation) throws Throwable
   {
      advice = "aroundMethodExecution1";
      return "aroundMethodExecution1";
   }
   
   public Object aroundMethodCalledByMethod(Invocation invocation) throws Throwable
   {
      advice = "aroundMethodCalledByMethod";
      invokeReturn = invocation.invokeNext();
      return 15000;
   }
   
   public Object aroundFieldWrite(FieldInvocation invocation) throws Throwable
   {
      advice = "aroundFieldWrite";
      invokeReturn = invocation.invokeNext();
      return null;
   }
   
   public Object aroundFieldRead(FieldReadInvocation invocation) throws Throwable
   {
      advice = "aroundFieldRead";
      invokeReturn = invocation.invokeNext();
      return 500;
   }
}