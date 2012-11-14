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
package org.jboss.test.aop.annotatedAdviceParams;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.joinpoint.CurrentInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * Aspect used on @Arg parameter tests.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 64564 $
 */
@SuppressWarnings({"cast"})
public class ArgAspect2 implements ArgPOJOInterface
{
   public static boolean echoCalled = false;
   public static boolean wrapped = false;
   public static boolean args = false;
   public static boolean argsWithInvocation = false;
   public static boolean bunchCalled = false;
   public static boolean bunch2Called = false;
   public static boolean arg1Called = false;
   public static boolean arg2Called = false;
   public static boolean arg3Called = false;
   public static boolean arg4Called = false;
   public static boolean arg15Called = false;
   public static boolean arg24Called = false;
   public static boolean emptyArgCalled = false;
   
   public static void clear()
   {
      echoCalled = false;
      wrapped = false;
      args = false;
      argsWithInvocation = false;
      bunchCalled = false;
      bunch2Called = false;
      arg1Called = false;
      arg2Called = false;
      arg3Called = false;
      arg4Called = false;
      arg15Called = false;
      arg24Called = false;
      emptyArgCalled = false;
   }
   
   public String echo(@Arg String arg)
   {
      echoCalled = true;

      Assert.assertEquals("Args don't match", "hello", arg);

      try
      {
         return (String) CurrentInvocation.proceed();
      }
      catch (Throwable throwable)
      {
      
         throw new RuntimeException(throwable);
      }
   }

   public Object wrap(MethodInvocation invocation) throws Throwable
   {
      wrapped = true;
      return invocation.invokeNext();
   }

   public Object bunchArgs(@Arg int x, @Arg double y, @Arg float z,
         @Arg String str, @Arg int q) throws Throwable
   {
      args = true;
      return (Integer)CurrentInvocation.proceed();
   }

   public int bunchArgsWithInvocation(
         @JoinPoint MethodInvocation invocation,
         @Arg int x, @Arg double y, @Arg float z, @Arg String str, @Arg int q) throws Throwable
   {
      argsWithInvocation = true;
      return (Integer) invocation.invokeNext();
   }

   public int bunch(@Arg int x, @Arg double y, @Arg float z, @Arg String str, @Arg int q)
   {
      bunchCalled = true;

      Assert.assertEquals("Arguments don't match", 1, x);
      Assert.assertEquals("Arguments don't match", 2.2, y);
      Assert.assertEquals("Arguments don't match", 3.3F, z);
      Assert.assertEquals("Arguments don't match", "four", str);
      try
      {
         return ((Integer) CurrentInvocation.proceed()).intValue();
      }
      catch (Throwable throwable)
      {
         throw new RuntimeException(throwable);
      }
   }

   public int bunch1(@Arg int x, @Arg int q, @Arg String str, @Arg double y, @Arg float z)
   {
      bunch2Called = true;

      Assert.assertEquals("Arguments don't match", 1, x);
      Assert.assertEquals("Arguments don't match", 2.2, y);
      Assert.assertEquals("Arguments don't match", 3.3F, z);
      Assert.assertEquals("Arguments don't match", "four", str);
      try
      {
         return ((Integer) CurrentInvocation.proceed()).intValue();
      }
      catch (Throwable throwable)
      {
         throw new RuntimeException(throwable);
      }
   }
   
   public Object arg1(@Arg int x) throws Throwable
   {
      arg1Called = true;
      Assert.assertEquals("Arguments don't match", 1, x);
      return CurrentInvocation.proceed();
   }

   public int arg2(@Arg double y) throws Throwable
   {
      arg2Called = true;
      Assert.assertEquals("Arguments don't match", 2.2, y);
      return (Integer) CurrentInvocation.proceed();
   }

   public Object arg3(@Arg float z) throws Throwable
   {
      arg3Called = true;
      Assert.assertEquals("Arguments don't match", 3.3F, z);
      return CurrentInvocation.proceed();
   }

   public Object arg4(@Arg Object str) throws Throwable
   {
      arg4Called = true;
      Assert.assertEquals("Arguments don't match", "four", str);
      return (Integer) CurrentInvocation.proceed();
   }

   public Object arg15(@Arg int x, @Arg int q) throws Throwable
   {
      arg15Called = true;
      Assert.assertEquals("Arguments don't match", 1, x);
      Assert.assertEquals("Arguments don't match", 5, q);
      return CurrentInvocation.proceed();
   }

   public int arg24(@Arg String str, @Arg double y) throws Throwable
   {
      arg24Called = true;
      Assert.assertEquals("Arguments don't match", 2.2, y);
      Assert.assertEquals("Arguments don't match", "four", str);
      return (Integer) CurrentInvocation.proceed();
   }
   
   public Object emptyArg() throws Throwable
   {
      emptyArgCalled = true;
      return CurrentInvocation.proceed();
   }
}