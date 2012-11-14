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

import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.JoinPointBean;

/**
 * Aspect used both on @Return parameter tests, and on advice return type tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ReturnAspect
{
   public static String aroundAdvice = null;
   public static Object aroundReturn = null;
   public static String afterAdvice = null;
   public static Object afterReturn = null;
   public static String finallyAdvice = null;
   public static Object finallyReturn = null;
   public static Object finallyThrown = null;
   
   public static void clear()
   {
      aroundAdvice = null;
      aroundReturn = null;
      afterAdvice = null;
      afterReturn = null;
      finallyAdvice = null;
      finallyReturn = null;
      finallyThrown = null;
   }
   
   public void before(@Return Object object)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing(@Return Object object)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void around2(@JoinPoint
         Invocation invocation) throws Exception
   {
      Assert.fail("This advice should never be executed");
   }
   
   public Object around3(Invocation invocation) throws Throwable
   {
      aroundAdvice = "around3";
      aroundReturn = invocation.invokeNext();
      return aroundAdvice;
   }
   
   public Object around4(@JoinPoint
         Invocation invocation) throws Throwable
   {
      aroundAdvice = "around4";
      aroundReturn = invocation.invokeNext();
      return aroundAdvice;
   }
   
   public Object around5()
   {
      aroundAdvice = "around5";
      return aroundAdvice;
   }
   
   public void around6()
   {
      Assert.fail("This advice should never be executed");
   }
   
   public String around7(@JoinPoint Invocation
         invocation) throws Throwable
   {
      aroundAdvice = "around7";
      aroundReturn = invocation.invokeNext();
      return aroundAdvice;
   }
   
   public SubValue around8()
   {
      aroundAdvice = "around8";
      return new SubValue(80);
   }
   
   public SuperValue around9(@JoinPoint Invocation
         invocation) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public SubValue around10(@JoinPoint Invocation
         invocation) throws Throwable
   {
      aroundAdvice = "around10";
      aroundReturn = invocation.invokeNext();
      return new SubValue(100);
   }
   
   public SuperValue around11(@JoinPoint Invocation
         invocation) throws Throwable
   {
      aroundAdvice = "around11";
      aroundReturn = invocation.invokeNext();
      return new SuperValue(110);
   }

   public Object around12(Invocation invocation) throws Throwable
   {
      aroundAdvice = "around12";
      aroundReturn = invocation.invokeNext();
      return new SuperValue(120);
   }
   
   public void after1(@org.jboss.aop.advice.annotation.JoinPoint JoinPointBean info) throws Exception
   {
      afterAdvice = "after1";
   }
   
   public void after2(@Return Object returnedValue) throws Exception
   {
      afterAdvice = "after2";
      afterReturn = returnedValue;
   }
   
   public Object after3(@JoinPoint JoinPointBean info, @Return String returnedValue)
   throws Throwable
   {
      afterAdvice = "after3";
      afterReturn = returnedValue;
      return afterAdvice;
   }
   
   public Object after4(@Return Object returnedValue) throws Throwable
   {
      afterAdvice = "after4";
      afterReturn = returnedValue;
      return afterAdvice;
   }
   
   public Object after5()
   {
      afterAdvice = "after5";
      return afterAdvice;
   }
   
   public void after6()
   {
      afterAdvice = "after6";
   }
   
   public String after7(@JoinPoint JoinPointBean joinPoint,
         @Return String returnedValue) throws Throwable
   {
      afterAdvice = "after7";
      afterReturn = returnedValue;
      return afterAdvice;
   }
   
   public SubValue after8()
   {
      afterAdvice = "after8";
      return new SubValue(800);
   }
   
   public SuperValue after9() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public SubValue after10(@Return SubValue returnedValue) throws Throwable
   {
      afterAdvice = "after10";
      afterReturn = returnedValue;
      return new SubValue(1000);
   }
   
   public SubValue after11(@Return SuperValue returnedValue) throws Throwable
   {
      afterAdvice = "after11";
      afterReturn = returnedValue;
      return new SubValue(1100);
   }
   
   public SubValue after12(@Return SubValue returnedValue)
   {
      // intercepted method throws an exception, and should go directly to finally
      Assert.fail("This advice should never be executed");
      return new SubValue(0);
   }
   
   public void finally1()
   {
      finallyAdvice = "finally1";
   }
      
   public String finally3(@Return Object returnedValue, @Thrown Throwable thrown)
   {
      finallyAdvice = "finally3";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return finallyAdvice;
   }
   
   public String finally4(@Thrown Throwable thrown, @Return String returnedValue)
   {
      finallyAdvice = "finally4";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return finallyAdvice;
   }
   
   public String finally5(@Return int returnedValue, @Thrown Throwable thrown)
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public Object finally6(@Return Object returnedValue, @Thrown Throwable thrown)
   {
      finallyAdvice = "finally6";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return finallyAdvice;
   }
   
   public void finally7(@Return Object returnedValue, @Thrown Throwable thrown)
   {
      finallyAdvice = "finally7";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
   }
   
   public SubValue finally8()
   {
      finallyAdvice = "finally8";
      return new SubValue(8000);
   }
   
   public SuperValue finally9() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public SubValue finally10(@Return SuperValue returnedValue, @Thrown Throwable thrown) throws Throwable
   {
      finallyAdvice = "finally10";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return new SubValue(10000);
   }
   
   public SubValue finally11(@Return SuperValue returnedValue, @Thrown Throwable thrown) throws Throwable
   {
      finallyAdvice = "finally11";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return new SubValue(11000);
   }
   
   public SubValue finally12(@Return SuperValue returnedValue, @Thrown Throwable thrown)
   {
      finallyAdvice = "finally12";
      finallyReturn = returnedValue;
      finallyThrown = thrown;
      return new SubValue(12000);
   }
   
   public SubValue finally13()
   {
      finallyAdvice = "finally13";
      return new SubValue(13);
   }
}