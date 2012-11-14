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
 * License aThrowable with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.aop.annotatedAdviceParams;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.Caller;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.MethodCallByMethod;

/**
 * Aspect used on overloaded throwing advice tests (for Call and Target tests
 * only).
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedThrowingCallAspect
{

   static String throwing1;
   static String throwing2;
   static String throwing3;
   static String throwing4;
   static String throwing5;
   static String throwing6;
   static String throwing7;
   static String throwing8;
   static String throwing9;
   static String throwing10;
   static String throwing11;
   static String throwing12;
   static String throwing13;
   static String throwing14;
   static String throwing15;
   static String throwing16;
   static String throwing17;
   static String throwing18;
   static String throwing19;
   static String throwing20;
   static String throwing21;
   static String throwing22;
   static String throwing23;
   static String throwing24;
   static String throwing25;
   static String throwing26;
   static String throwing27;
   static String throwing28;
   static String throwing29;
   static String throwing30;
   static String throwing31;
   static String throwing32;
   static String throwing33;
   static String throwing34;
   static String throwing35;
   static String throwing36;
   static String throwing37;
   static String throwing38;
   static String throwing39;
   static String throwing40;
   static String throwing41;
   static String throwing42;
   static String throwing43;
   static String throwing44;
   static String throwing45;
   static String throwing46;
   static String throwing47;
   static String throwing48;
   static String throwing49;
   static String throwing50;
   
   public static void clear()
   {
      throwing1 = null;
      throwing2 = null;
      throwing3 = null;
      throwing4 = null;
      throwing5 = null;
      throwing6 = null;
      throwing7 = null;
      throwing8 = null;
      throwing9 = null;
      throwing10 = null;
      throwing11 = null;
      throwing12 = null;
      throwing13 = null;
      throwing14 = null;
      throwing15 = null;
      throwing16 = null;
      throwing17 = null;
      throwing18 = null;
      throwing19 = null;
      throwing20 = null;
      throwing21 = null;
      throwing22 = null;
      throwing23 = null;
      throwing24 = null;
      throwing25 = null;
      throwing26 = null;
      throwing27 = null;
      throwing28 = null;
      throwing29 = null;
      throwing30 = null;
      throwing31 = null;
      throwing32 = null;
      throwing33 = null;
      throwing34 = null;
      throwing35 = null;
      throwing36 = null;
      throwing37 = null;
      throwing38 = null;
      throwing39 = null;
      throwing40 = null;
      throwing41 = null;
      throwing42 = null;
      throwing43 = null;
      throwing44 = null;
      throwing45 = null;
      throwing46 = null;
      throwing47 = null;
      throwing48 = null;
      throwing49 = null;
      throwing50 = null;
   }

   /* THROWING1 ADVICE */
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing1 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing1 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing1(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing1="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing1(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing1="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing1="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing1(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing1="Object,SuperClass,Throwable";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing1="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing1(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1="Object,Throwable,boolean";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing1(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1="Object,Throwable,Object[]";
   }
   
   public void throwing1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing1="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing1(@Target Object target, @Thrown Throwable thrown)
   {
      throwing1="Object,Throwable";
   }
   
   public void throwing1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing1="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing1(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing1 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing1(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing1 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing1 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing1(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing1 = "SuperClass,Throwable";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing1 = "Throwable,boolean";
   }
   
   public void throwing1(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING2 ADVICE */
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing2 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing2 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing2(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing2="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing2(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing2="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing2="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing2(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing2="Object,SuperClass,Throwable";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing2="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing2(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2="Object,Throwable,boolean";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing2(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2="Object,Throwable,Object[]";
   }
   
   public void throwing2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing2="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing2(@Target Object target, @Thrown Throwable thrown)
   {
      throwing2="Object,Throwable";
   }
   
   public void throwing2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing2="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing2(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing2 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing2(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing2 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing2 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing2(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing2 = "SuperClass,Throwable";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing2 = "Throwable,boolean";
   }
   
   public void throwing2(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING3 ADVICE */
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing3 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing3 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing3(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing3="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing3(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing3="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing3="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing3(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing3="Object,SuperClass,Throwable";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing3="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing3(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3="Object,Throwable,boolean";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing3(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3="Object,Throwable,Object[]";
   }
   
   public void throwing3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing3="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing3(@Target Object target, @Thrown Throwable thrown)
   {
      throwing3="Object,Throwable";
   }
   
   public void throwing3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing3="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing3(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing3 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing3(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing3 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing3 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing3(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing3 = "SuperClass,Throwable";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing3 = "Throwable,boolean";
   }
   
   public void throwing3(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING4 ADVICE */
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing4 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing4 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing4(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing4="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing4(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing4="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing4="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing4(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing4="Object,SuperClass,Throwable";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing4="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing4(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4="Object,Throwable,boolean";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing4(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4="Object,Throwable,Object[]";
   }
   
   public void throwing4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing4="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing4(@Target Object target, @Thrown Throwable thrown)
   {
      throwing4="Object,Throwable";
   }
   
   public void throwing4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing4="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing4(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing4 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing4(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing4 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing4 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing4(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing4 = "SuperClass,Throwable";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing4 = "Throwable,boolean";
   }
   
   public void throwing4(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING5 ADVICE */
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing5 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing5 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing5 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing5(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing5="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing5(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing5="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing5="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing5(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing5="Object,SuperClass,Throwable";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing5="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing5(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5="Object,Throwable,boolean";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing5(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5="Object,Throwable,Object[]";
   }
   
   public void throwing5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing5="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing5(@Target Object target, @Thrown Throwable thrown)
   {
      throwing5="Object,Throwable";
   }
   
   public void throwing5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing5="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing5(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing5 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing5(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing5 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing5 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing5(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing5 = "SuperClass,Throwable";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing5 = "Throwable,boolean";
   }
   
   public void throwing5(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING6 ADVICE */
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrown)
   {
      throwing6 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing6 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing6 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing6(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing6="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing6(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing6="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing6="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing6(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing6="Object,SuperClass,Throwable";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing6="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing6(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6="Object,Throwable,boolean";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing6(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6="Object,Throwable,Object[]";
   }
   
   public void throwing6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing6="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing6(@Target Object target, @Thrown Throwable thrown)
   {
      throwing6="Object,Throwable";
   }
   
   public void throwing6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing6="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing6(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing6 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing6(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing6 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing6 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing6(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing6 = "SuperClass,Throwable";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing6 = "Throwable,boolean";
   }
   
   public void throwing6(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING7 ADVICE */
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing7 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing7 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing7(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing7="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing7(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing7="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing7="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing7(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing7="Object,SuperClass,Throwable";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing7="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing7(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7="Object,Throwable,boolean";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing7(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7="Object,Throwable,Object[]";
   }
   
   public void throwing7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing7="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing7(@Target Object target, @Thrown Throwable thrown)
   {
      throwing7="Object,Throwable";
   }
   
   public void throwing7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing7="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing7(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing7 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing7(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing7 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing7 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing7(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing7 = "SuperClass,Throwable";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing7 = "Throwable,boolean";
   }
   
   public void throwing7(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING8 ADVICE */
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing8 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing8 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing8="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing8="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing8(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing8="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing8(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing8="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing8="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing8(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing8="Object,SuperClass,Throwable";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing8="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing8(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing8="Object,Throwable,boolean";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing8(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8="Object,Throwable,Object[]";
   }
   
   public void throwing8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing8="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing8(@Target Object target, @Thrown Throwable thrown)
   {
      throwing8="Object,Throwable";
   }
   
   public void throwing8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing8="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing8(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing8 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing8(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing8 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing8 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing8(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing8 = "SuperClass,Throwable";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing8 = "Throwable,boolean";
   }
   
   public void throwing8(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING9 ADVICE */
   
   public void throwing9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing9(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing9 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing9(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing9(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing9 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing9="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing9="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing9(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing9="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing9="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing9="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing9(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing9="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing9="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing9(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing9="Object,SuperClass,Throwable";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing9="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing9(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing9="Object,Throwable,boolean";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing9(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing9="Object,Throwable,Object[]";
   }
   
   public void throwing9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing9="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing9(@Target Object target, @Thrown Throwable thrown)
   {
      throwing9="Object,Throwable";
   }
   
   public void throwing9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing9="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing9(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing9 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing9(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing9 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing9 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing9(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing9 = "SuperClass,Throwable";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing9 = "Throwable,boolean";
   }
   
   public void throwing9(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING10 ADVICE */
   
   public void throwing10(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing10 = "MethodCallByMethod,Throwable,boolean";
   }
   
   public void throwing10(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing10(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing10 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing10="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing10="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing10(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing10="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing10="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing10="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing10(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing10="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing10="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing10(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing10="Object,SuperClass,Throwable";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing10="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing10(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing10="Object,Throwable,boolean";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing10(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing10="Object,Throwable,Object[]";
   }
   
   public void throwing10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing10="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing10(@Target Object target, @Thrown Throwable thrown)
   {
      throwing10="Object,Throwable";
   }
   
   public void throwing10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing10="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing10(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing10 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing10(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing10 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing10 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing10(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing10 = "SuperClass,Throwable";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing10 = "Throwable,boolean";
   }
   
   public void throwing10(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING11 ADVICE */
   
   public void throwing11(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11 = "MethodCallByMethod,Throwable,Object[]";
   }
   
   public void throwing11(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing11 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing11="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing11="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing11(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing11="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing11="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing11="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing11(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing11="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing11="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing11(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing11="Object,SuperClass,Throwable";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing11="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing11(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing11="Object,Throwable,boolean";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing11(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing11="Object,Throwable,Object[]";
   }
   
   public void throwing11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing11="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing11(@Target Object target, @Thrown Throwable thrown)
   {
      throwing11="Object,Throwable";
   }
   
   public void throwing11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing11="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing11(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing11 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing11(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing11 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing11 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing11(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing11 = "SuperClass,Throwable";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing11 = "Throwable,boolean";
   }
   
   public void throwing11(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING12 ADVICE */
   
   public void throwing12(@JoinPoint MethodCallByMethod joinPoint,
         @Thrown Throwable thrown)
   {
      throwing12 = "MethodCallByMethod,Throwable";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing12="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing12="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing12(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing12="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing12="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing12="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing12(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing12="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing12="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing12="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing12(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing12="Object,SuperClass,Throwable";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing12="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing12(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing12="Object,Throwable,boolean";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing12="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing12(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing12="Object,Throwable,Object[]";
   }
   
   public void throwing12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing12="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing12(@Target Object target, @Thrown Throwable thrown)
   {
      throwing12="Object,Throwable";
   }
   
   public void throwing12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing12="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing12(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing12 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing12 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing12(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing12 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing12 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing12(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing12 = "SuperClass,Throwable";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing12 = "Throwable,boolean";
   }
   
   public void throwing12(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING13 ADVICE */
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing13="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing13="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing13(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing13="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing13="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing13="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing13(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing13="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing13="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing13="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing13(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing13="Object,SuperClass,Throwable";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing13="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing13(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing13="Object,Throwable,boolean";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing13="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing13(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing13="Object,Throwable,Object[]";
   }
   
   public void throwing13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing13="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing13(@Target Object target, @Thrown Throwable thrown)
   {
      throwing13="Object,Throwable";
   }
   
   public void throwing13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing13="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing13(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing13 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing13 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing13(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing13 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing13 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing13(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing13 = "SuperClass,Throwable";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing13 = "Throwable,boolean";
   }
   
   public void throwing13(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING14 ADVICE */
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing14="OverloadedAdvicePOJO,SuperClass,Throwable,boolean";
   }
   
   public void throwing14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing14="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing14(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing14="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing14="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing14="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing14(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing14="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing14="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing14="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing14(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing14="Object,SuperClass,Throwable";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing14="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing14(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing14="Object,Throwable,boolean";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing14="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing14(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing14="Object,Throwable,Object[]";
   }
   
   public void throwing14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing14="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing14(@Target Object target, @Thrown Throwable thrown)
   {
      throwing14="Object,Throwable";
   }
   
   public void throwing14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing14="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing14(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing14 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing14 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing14(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing14 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing14 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing14(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing14 = "SuperClass,Throwable";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing14 = "Throwable,boolean";
   }
   
   public void throwing14(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING15 ADVICE */
   
   public void throwing15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing15="Object,OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing15(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing15="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing15="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing15="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing15(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing15="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing15="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing15="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing15(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing15="Object,SuperClass,Throwable";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing15="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing15(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing15="Object,Throwable,boolean";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing15="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing15(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing15="Object,Throwable,Object[]";
   }
   
   public void throwing15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing15="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing15(@Target Object target, @Thrown Throwable thrown)
   {
      throwing15="Object,Throwable";
   }
   
   public void throwing15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing15="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing15(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing15 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing15 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing15(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing15 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing15 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing15(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing15 = "SuperClass,Throwable";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing15 = "Throwable,boolean";
   }
   
   public void throwing15(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING16 ADVICE */
   
   public void throwing16(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing16="Object,SuperClass,Throwable,boolean";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing16="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing16="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing16(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing16="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing16="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing16="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing16(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing16="Object,SuperClass,Throwable";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing16="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing16(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing16="Object,Throwable,boolean";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing16="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing16(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing16="Object,Throwable,Object[]";
   }
   
   public void throwing16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing16="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing16(@Target Object target, @Thrown Throwable thrown)
   {
      throwing16="Object,Throwable";
   }
   
   public void throwing16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing16="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing16(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing16 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing16 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing16(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing16 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing16 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing16(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing16 = "SuperClass,Throwable";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing16 = "Throwable,boolean";
   }
   
   public void throwing16(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING17 ADVICE */
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing17="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing17="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing17(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing17="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing17="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing17="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing17(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing17="Object,SuperClass,Throwable";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing17="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing17(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing17="Object,Throwable,boolean";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing17="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing17(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing17="Object,Throwable,Object[]";
   }
   
   public void throwing17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing17="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing17(@Target Object target, @Thrown Throwable thrown)
   {
      throwing17="Object,Throwable";
   }
   
   public void throwing17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing17="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing17(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing17 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing17 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing17(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing17 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing17 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing17(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing17 = "SuperClass,Throwable";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing17 = "Throwable,boolean";
   }
   
   public void throwing17(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING18 ADVICE */
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing18="OverloadedAdvicePOJO,SuperClass,Throwable,Object[]";
   }
   
   public void throwing18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing18="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing18(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing18="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing18="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing18="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing18(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing18="Object,SuperClass,Throwable";
   }
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing18="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing18(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing18="Object,Throwable,boolean";
   }
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing18="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing18(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing18="Object,Throwable,Object[]";
   }
   
   public void throwing18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing18="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing18(@Target Object target, @Thrown Throwable thrown)
   {
      throwing18="Object,Throwable";
   }
   
   public void throwing18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing18="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing18(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing18 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing18 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing18(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing18 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing18 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing18(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing18 = "SuperClass,Throwable";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing18 = "Throwable,boolean";
   }
   
   public void throwing18(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING19 ADVICE */
   
   public void throwing19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing19="Object,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing19(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing19="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing19="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing19="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing19(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing19="Object,SuperClass,Throwable";
   }
   
   public void throwing19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing19="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing19(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing19="Object,Throwable,boolean";
   }
   
   public void throwing19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing19="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing19(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing19="Object,Throwable,Object[]";
   }
   
   public void throwing19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing19="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing19(@Target Object target, @Thrown Throwable thrown)
   {
      throwing19="Object,Throwable";
   }
   
   public void throwing19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing19="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing19(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing19 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing19 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing19(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing19 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing19 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing19(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing19 = "SuperClass,Throwable";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing19 = "Throwable,boolean";
   }
   
   public void throwing19(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING20 ADVICE */
   
   public void throwing20(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing20="Object,SuperClass,Throwable,Object[]";
   }
   
   public void throwing20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing20="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing20="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing20(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing20="Object,SuperClass,Throwable";
   }
   
   public void throwing20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing20="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing20(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing20="Object,Throwable,boolean";
   }
   
   public void throwing20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing20="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing20(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing20="Object,Throwable,Object[]";
   }
   
   public void throwing20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing20="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing20(@Target Object target, @Thrown Throwable thrown)
   {
      throwing20="Object,Throwable";
   }
   
   public void throwing20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing20="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing20(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing20 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing20 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing20(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing20 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing20 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing20(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing20 = "SuperClass,Throwable";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing20 = "Throwable,boolean";
   }
   
   public void throwing20(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING21 ADVICE */
   
   public void throwing21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing21="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing21="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing21(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing21="Object,SuperClass,Throwable";
   }
   
   public void throwing21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing21="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing21(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing21="Object,Throwable,boolean";
   }
   
   public void throwing21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing21="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing21(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing21="Object,Throwable,Object[]";
   }
   
   public void throwing21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing21="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing21(@Target Object target, @Thrown Throwable thrown)
   {
      throwing21="Object,Throwable";
   }
   
   public void throwing21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing21="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing21(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing21 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing21 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing21(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing21 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing21 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing21(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing21 = "SuperClass,Throwable";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing21 = "Throwable,boolean";
   }
   
   public void throwing21(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING22 ADVICE */
   
   public void throwing22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing22="OverloadedAdvicePOJO,SuperClass,Throwable";
   }
   
   public void throwing22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing22="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing22(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing22="Object,SuperClass,Throwable";
   }
   
   public void throwing22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing22="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing22(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing22="Object,Throwable,boolean";
   }
   
   public void throwing22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing22="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing22(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing22="Object,Throwable,Object[]";
   }
   
   public void throwing22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing22="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing22(@Target Object target, @Thrown Throwable thrown)
   {
      throwing22="Object,Throwable";
   }
   
   public void throwing22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing22="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing22(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing22 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing22 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing22(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing22 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing22 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing22(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing22 = "SuperClass,Throwable";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing22 = "Throwable,boolean";
   }
   
   public void throwing22(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING23 ADVICE */
   
   public void throwing23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Thrown Throwable thrown)
   {
      throwing23="Object,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing23(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing23="Object,SuperClass,Throwable";
   }
   
   public void throwing23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing23="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing23(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing23="Object,Throwable,boolean";
   }
   
   public void throwing23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing23="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing23(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing23="Object,Throwable,Object[]";
   }
   
   public void throwing23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing23="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing23(@Target Object target, @Thrown Throwable thrown)
   {
      throwing23="Object,Throwable";
   }
   
   public void throwing23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing23="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing23(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing23 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing23 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing23(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing23 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing23 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing23(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing23 = "SuperClass,Throwable";
   }
   
   public void throwing23(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing23 = "Throwable,boolean";
   }
   
   public void throwing23(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING24 ADVICE */
   
   public void throwing24(@Target Object target, @Caller SuperClass caller,
         @Thrown Throwable thrown)
   {
      throwing24="Object,SuperClass,Throwable";
   }
   
   public void throwing24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing24="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing24(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing24="Object,Throwable,boolean";
   }
   
   public void throwing24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing24="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing24(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing24="Object,Throwable,Object[]";
   }
   
   public void throwing24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing24="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing24(@Target Object target, @Thrown Throwable thrown)
   {
      throwing24="Object,Throwable";
   }
   
   public void throwing24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing24="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing24(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing24 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing24 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing24(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing24 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing24 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing24(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing24 = "SuperClass,Throwable";
   }
   
   public void throwing24(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing24 = "Throwable,boolean";
   }
   
   public void throwing24(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING25 ADVICE */
   
   public void throwing25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing25="OverloadedAdvicePOJO,Throwable,boolean";
   }
   
   public void throwing25(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing25="Object,Throwable,boolean";
   }
   
   public void throwing25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing25="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing25(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing25="Object,Throwable,Object[]";
   }
   
   public void throwing25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing25="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing25(@Target Object target, @Thrown Throwable thrown)
   {
      throwing25="Object,Throwable";
   }
   
   public void throwing25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing25="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing25(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing25 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing25 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing25(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing25 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing25 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing25(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing25 = "SuperClass,Throwable";
   }
   
   public void throwing25(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing25 = "Throwable,boolean";
   }
   
   public void throwing25(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING26 ADVICE */
   
   public void throwing26(@Target Object target, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing26="Object,Throwable,boolean";
   }
   
   public void throwing26(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing26="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing26(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing26="Object,Throwable,Object[]";
   }
   
   public void throwing26(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing26="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing26(@Target Object target, @Thrown Throwable thrown)
   {
      throwing26="Object,Throwable";
   }
   
   public void throwing26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing26="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing26(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing26 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing26 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing26(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing26 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing26 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing26(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing26 = "SuperClass,Throwable";
   }
   
   public void throwing26(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing26 = "Throwable,boolean";
   }
   
   public void throwing26(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING27 ADVICE */
   
   public void throwing27(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing27="OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void throwing27(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing27="Object,Throwable,Object[]";
   }
   
   public void throwing27(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing27="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing27(@Target Object target, @Thrown Throwable thrown)
   {
      throwing27="Object,Throwable";
   }
   
   public void throwing27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing27="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing27(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing27 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing27 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing27(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing27 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing27 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing27(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing27 = "SuperClass,Throwable";
   }
   
   public void throwing27(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing27 = "Throwable,boolean";
   }
   
   public void throwing27(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING28 ADVICE */
   
   public void throwing28(@Target Object target, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing28="Object,Throwable,Object[]";
   }
   
   public void throwing28(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing28="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing28(@Target Object target, @Thrown Throwable thrown)
   {
      throwing28="Object,Throwable";
   }
   
   public void throwing28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing28="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing28(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing28 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing28 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing28(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing28 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing28 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing28(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing28 = "SuperClass,Throwable";
   }
   
   public void throwing28(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing28 = "Throwable,boolean";
   }
   
   public void throwing28(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING29 ADVICE */
   
   public void throwing29(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrown)
   {
      throwing29="OverloadedAdvicePOJO,Throwable";
   }
   
   public void throwing29(@Target Object target, @Thrown Throwable thrown)
   {
      throwing29="Object,Throwable";
   }
   
   public void throwing29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing29="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing29(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing29 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing29 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing29(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing29 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing29 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing29(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing29 = "SuperClass,Throwable";
   }
   
   public void throwing29(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing29 = "Throwable,boolean";
   }
   
   public void throwing29(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING30 ADVICE */
   
   public void throwing30(@Target Object target, @Thrown Throwable thrown)
   {
      throwing30="Object,Throwable";
   }
   
   public void throwing30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing30="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing30(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing30 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing30 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing30(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing30 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing30 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing30(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing30 = "SuperClass,Throwable";
   }
   
   public void throwing30(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing30 = "Throwable,boolean";
   }
   
   public void throwing30(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING31 ADVICE */
   
   public void throwing31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing31="OverloadedAdvicePOJOCaller,Throwable,boolean";
   }
   
   public void throwing31(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing31 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing31 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing31(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing31 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing31 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing31(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing31 = "SuperClass,Throwable";
   }
   
   public void throwing31(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing31 = "Throwable,boolean";
   }
   
   public void throwing31(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING32 ADVICE */
   
   public void throwing32(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Arg boolean arg)
   {
      throwing32 = "SuperClass,Throwable,boolean";
   }
   
   public void throwing32(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing32 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing32(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing32 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing32(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing32 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing32(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing32 = "SuperClass,Throwable";
   }
   
   public void throwing32(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing32 = "Throwable,boolean";
   }
   
   public void throwing32(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING33 ADVICE */
   
   public void throwing33(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing33 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void throwing33(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing33 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing33(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing33 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing33(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing33 = "SuperClass,Throwable";
   }
   
   public void throwing33(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing33 = "Throwable,boolean";
   }
   
   public void throwing33(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING34 ADVICE */
   
   public void throwing34(@Caller SuperClass caller, @Thrown Throwable thrown,
         @Args Object[] args)
   {
      throwing34 = "SuperClass,Throwable,Object[]";
   }
   
   public void throwing34(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing34 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing34(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing34 = "SuperClass,Throwable";
   }
   
   public void throwing34(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing34 = "Throwable,boolean";
   }
   
   public void throwing34(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING35 ADVICE */
   
   public void throwing35(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrown)
   {
      throwing35 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void throwing35(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing35 = "SuperClass,Throwable";
   }
   
   public void throwing35(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing35 = "Throwable,boolean";
   }
   
   public void throwing35(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING36 ADVICE */
   
   public void throwing36(@Caller SuperClass caller, @Thrown Throwable thrown)
   {
      throwing36 = "SuperClass,Throwable";
   }
   
   public void throwing36(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing36 = "Throwable,boolean";
   }
   
   public void throwing36(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING37 ADVICE */
   
   public void throwing37(@Thrown Throwable thrown, @Arg boolean arg)
   {
      throwing37 = "Throwable,boolean";
   }
   
   public void throwing37(@JoinPoint MethodCallByMethod joinPoint)
   {
      Assert.fail("This advice should never be executed");
   }
}