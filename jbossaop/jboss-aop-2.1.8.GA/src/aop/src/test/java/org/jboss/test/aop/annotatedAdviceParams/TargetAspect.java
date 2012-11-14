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

import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.CurrentInvocation;

/**
 * Aspect used on @Args parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class TargetAspect
{
   static boolean before1 = false;
   static boolean before2 = false;
   static boolean around1 = false;
   static boolean around2 = false;
   static boolean after1 = false;
   static boolean after2 = false;
   static boolean throwing1 = false;
   static boolean throwing2 = false;
   static boolean finally1 = false;
   static boolean finally2 = false;

   static Object before2Target = null;
   static Object around2Target = null;
   static Object after2Target = null;
   static Object throwing1Target = null;
   static Object finally2Target = null;
   
   public static void clear()
   {
      before1 = false;
      before2 = false;
      around1 = false;
      around2 = false;
      after1 = false;
      after2 = false;
      throwing1 = false;
      throwing2 = false;
      finally1 = false;
      finally2 = false;
      
      before2Target = null;
      around2Target = null;
      after2Target = null;
      throwing1Target = null;
      finally2Target = null;
   }
   
   public void before1()
   {
      before1 = true;
   }
   
   public void before2(@Target Object target)
   {
      before2 = true;
      before2Target = target;
   }

   public void before3(@Target TargetAspect target)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public Object around1() throws Throwable
   {
      around1 = true;
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target) throws Throwable
   {
      around2 = true;
      around2Target = target;
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target ReturnPOJO target) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public void after1()
   {
      after1 = true;
   }
   
   public void after2(@Target TargetCallerPOJO target)
   {
      after2 = true;
      after2Target = target;
   }
   
   public void throwing1(@Thrown Throwable thrown, @Target TargetCallerPOJO target)
   {
      throwing1 = true;
      throwing1Target = target;
   }
   
   public void throwing2(@Thrown Throwable thrown)
   {
      throwing2 = true;
   }
   
   public void throwing3(@Thrown Throwable thrown, @Target String target)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally1()
   {
      finally1 = true;
   }
   
   public void finally2(@Target Object target)
   {
      finally2 = true;
      finally2Target = target;
   }
   
   public void finally3(@Target double target)
   {
      Assert.fail("This advice should never be executed");
   }
}