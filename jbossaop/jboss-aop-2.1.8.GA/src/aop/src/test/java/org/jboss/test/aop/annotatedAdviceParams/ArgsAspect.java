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

import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * Aspect used on @Args parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings({"cast"})
public class ArgsAspect
{
   static boolean before1 = false;
   static boolean before2 = false;
   static boolean before3 = false;
   static boolean before6 = false;
   static boolean before7 = false;
   static boolean before8 = false;
   static Object[] before2Args = null;
   static Object[] before3Args = null;
   static Object[] before6Args = null;
   static Object[] before7Args = null;
   static Object[] before8Args = null;

   static boolean after1 = false;
   static boolean after2 = false;
   static boolean after3 = false;
   static boolean after6 = false;
   static boolean after7 = false;
   static boolean after8 = false;
   static Object[] after2Args = null;
   static Object[] after3Args = null;
   static Object[] after6Args = null;
   static Object[] after7Args = null;
   static Object[] after8Args = null;

   static boolean around1 = false;
   static boolean around2 = false;
   static boolean around3 = false;
   static Object[] around1Args = null;
   static Object[] around2Args = null;
   static Object[] around3Args = null;
   
   static boolean throwing = false;
   static Object[] throwingArgs = null;
   
   static boolean finally1 = false;
   static Object[] finally1Args = null;
   static boolean finally3 = false;
   static Object[] finally3Args = null;
   
   public static void clear()
   {
      before1 = false;
      before2 = false;
      before3 = false;
      before6 = false;
      before7 = false;
      before8 = false;
      before2Args = null;
      before3Args = null;
      before6Args = null;
      before7Args = null;
      before8Args = null;
      
      after1 = false;
      after2 = false;
      after3 = false;
      after6 = false;
      after7 = false;
      after8 = false;
      after2Args = null;
      after3Args = null;
      after6Args = null;
      after7Args = null;
      after8Args = null;
      
      around1 = false;
      around2 = false;
      around3 = false;
      around1Args = null;
      around2Args = null;
      around3Args = null;
      
      throwing = false;
      throwingArgs = null;
      
      finally1 = false;
      finally1Args = null;
      finally3 = false;
      finally3Args = null;
   }
   
   public void before1()
   {
      before1 = true;
   }
   
   public void before2(@Args Object[] arguments)
   {
      before2 = true;
      before2Args = arguments;
   }
   
   public void before3(@Args Object arguments)
   {
      before3 = true;
      before3Args = (Object[]) arguments;
   }
   
   public void before4(@Args String arguments)
   {
      Assert.fail("This advice should never be executed");
   }

   public void before5(@Args char[] arguments)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before6(@Args Object[] arguments)
   {
      before6 = true;
      before6Args = arguments;
      arguments[0] = "before6";
   }
   
   public void before7(@Args Object[] arguments)
   {
      before7 = true;
      before7Args = arguments;
      before7Args[0] = "before7";
      before7Args[1] = new Integer(7);
      before7Args[2] = Boolean.TRUE;
      before7Args[3] = new ArgsPOJO[0];
   }

   public void before8(@Args Object[] arguments)
   {
      before8 = true;
      before8Args = arguments;
      before8Args[0] = new Short((short) -8);
      before8Args[1] = new Long((long) 8);
   }
   
   public void after1()
   {
      after1 = true;
   }
   
   public void after2(@Args Object[] arguments)
   {
      after2 = true;
      after2Args = arguments;
   }
   
   public void after3(@Args Object arguments)
   {
      after3 = true;
      after3Args = (Object[]) arguments;
   }
   
   public void after4(@Args String arguments)
   {
      Assert.fail("This advice should never be executed");
   }

   public void after5(@Args char[] arguments)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after6(@Args Object[] arguments)
   {
      after6 = true;
      after6Args = arguments;
      arguments[0] = "after6";
   }

   public void after7(@Args Object[] arguments)
   {
      after7 = true;
      after7Args = arguments;
      after7Args[0] = "after7";
      after7Args[1] = new Integer(14);
      after7Args[2] = Boolean.FALSE;
      after7Args[3] = new ArgsPOJO[]{null};
   }
   
   public void after8(@Args Object[] arguments)
   {
      after8 = true;
      after8Args = arguments;
   }
   
   public Object around1(MethodInvocation invocation) throws Throwable
   {
      around1 = true;
      around1Args = invocation.getArguments();
      return invocation.invokeNext();
   }
   
   public Object around2(MethodCalledByMethodInvocation invocation) throws Throwable
   {
      around2 = true;
      around2Args = invocation.getArguments();
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint Invocation invocation, @Args Object[] arguments)
      throws Throwable
   {
      around3 = true;
      around3Args = arguments;
      return invocation.invokeNext();
   }
   
   public void throwing(@Args Object[] arguments, @Thrown Throwable throwable)
   {
      throwing = true;
      throwingArgs = arguments;
   }
   
   public void finally1(@Args Object[] arguments)
   {
      finally1 = true;
      finally1Args = arguments;
   }
   
   public void finally2(@Args int arguments)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally3(@Args Object[] arguments)
   {
      finally3 = true;
      arguments[0]="finally3";
      finally3Args = arguments;
   }
}