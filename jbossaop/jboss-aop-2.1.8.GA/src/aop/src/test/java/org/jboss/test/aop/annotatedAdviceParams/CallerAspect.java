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

import org.jboss.aop.advice.annotation.Caller;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.CurrentInvocation;

/**
 * Aspect used on @Caller parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class CallerAspect
{
   static boolean before1 = false;
   static boolean before2 = false;
   static boolean before3 = false;
   static boolean around1 = false;
   static boolean around2 = false;
   static boolean around4 = false;
   static boolean after1 = false;
   static boolean after2 = false;
   static boolean throwing1 = false;
   static boolean throwing3 = false;
   static boolean finally1 = false;
   static boolean finally2 = false;
   static boolean finally4 = false;
   
   static Object before2Caller = null;
   static Object before3Caller = null;
   static Object around2Caller = null;
   static Object around4Caller = null;
   static Object after2Caller = null;
   static Object throwing3Caller = null;
   static Object finally1Caller = null;
   static Object finally4Caller = null;
   
   public static void clear()
   {
      before1 = false;
      before2 = false;
      before3 = false;
      around1 = false;
      around2 = false;
      around4 = false;
      after1 = false;
      after2 = false;
      throwing1 = false;
      throwing3 = false;
      finally1 = false;
      finally2 = false;
      finally4 = false;
      before2Caller = null;
      before3Caller = null;
      around2Caller = null;
      around4Caller = null;
      after2Caller = null;
      throwing3Caller = null;
      finally1Caller = null;
      finally4Caller = null;
   }

   
   public void before1()
   {
      before1 = true;
   }
   
   public void before2(@Caller Object caller)
   {
      before2 = true;
      before2Caller = caller;
   }
   
   public void before3(@Caller TargetCallerPOJO caller)
   {
      before3 = true;
      before3Caller = caller;
   }
   
   public void before3(@Caller TargetCallerInvalidPOJO caller)
   {
      before3 = true;
      before3Caller = caller;
   }
   
   public void before4(@Caller ArgsPOJO caller)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public Object around1() throws Throwable
   {
      around1 = true;
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller TargetCallerPOJO caller) throws Throwable
   {
      around2 = true;
      around2Caller = caller;
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller TargetCallerInvalidPOJO caller) throws Throwable
   {
      around2 = true;
      around2Caller = caller;
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller String caller) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public Object around4(@Caller Object caller) throws Throwable
   {
      around4 = true;
      around4Caller = caller;
      return CurrentInvocation.proceed();
   }
   
   public void after1()
   {
      after1 = true;
   }
   
   public void after2(@Caller Object caller)
   {
      after2 = true;
      after2Caller = caller;
   }
   
   public void after3(@Caller TargetCallerInvalidPOJO2 caller)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing1(@Thrown Throwable throwable)
   {
      throwing1 = true;
   }
   
   public void throwing2(@Caller String caller, @Thrown Throwable throwable)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing3(@Caller Object caller, @Thrown Throwable throwable)
   {
      throwing3 = true;
      throwing3Caller = caller;
   }
   
   public void finally1(@Caller TargetCallerPOJO caller)
   {
      finally1 = true;
      finally1Caller = caller;
   }
   
   public void finally1(@Caller TargetCallerInvalidPOJO caller)
   {
      finally1 = true;
      finally1Caller = caller;
   }
   
   public void finally2()
   {
      finally2 = true;
   }
   
   public void finally3(@Caller ArgTestCase caller)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally4(@Caller Object caller)
   {
      finally4 = true;
      finally4Caller = caller;
   }
}