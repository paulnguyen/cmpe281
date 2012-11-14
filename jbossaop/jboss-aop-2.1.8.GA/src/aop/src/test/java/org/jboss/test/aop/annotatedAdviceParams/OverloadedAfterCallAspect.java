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

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.Caller;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.joinpoint.MethodCallByMethod;

/**
 * Aspect used on overloaded after advice tests (for Call and Target tests
 * only).
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedAfterCallAspect
{
   static String after1;
   static String after2;
   static String after3;
   static String after4;
   static String after5;
   static String after6;
   static String after7;
   static String after8;
   static String after9;
   static String after10;
   static String after11;
   static String after12;
   static String after13;
   static String after14;
   static String after15;
   static String after16;
   static String after17;
   static String after18;
   static String after19;
   static String after20;
   static String after21;
   static String after22;
   static String after23;
   static String after24;
   static String after25;
   static String after26;
   static String after27;
   static String after28;
   static String after29;
   static String after30;
   static String after31;
   static String after32;
   static String after33;
   static String after34;
   static String after35;
   static String after36;
   static String after37;
   static String after38;
   static String after39;
   static String after40;
   static String after41;
   static String after42;
   static String after43;
   static String after44;
   static String after45;
   static String after46;
   static String after47;
   static String after48;
   static String after49;
   static String after50;
   
   public static void clear()
   {
      after1 = null;
      after2 = null;
      after3 = null;
      after4 = null;
      after5 = null;
      after6 = null;
      after7 = null;
      after8 = null;
      after9 = null;
      after10 = null;
      after11 = null;
      after12 = null;
      after13 = null;
      after14 = null;
      after15 = null;
      after16 = null;
      after17 = null;
      after18 = null;
      after19 = null;
      after20 = null;
      after21 = null;
      after22 = null;
      after23 = null;
      after24 = null;
      after25 = null;
      after26 = null;
      after27 = null;
      after28 = null;
      after29 = null;
      after30 = null;
      after31 = null;
      after32 = null;
      after33 = null;
      after34 = null;
      after35 = null;
      after36 = null;
      after37 = null;
      after38 = null;
      after39 = null;
      after40 = null;
      after41 = null;
      after42 = null;
      after43 = null;
      after44 = null;
      after45 = null;
      after46 = null;
      after47 = null;
      after48 = null;
      after49 = null;
      after50 = null;
   }

   /* AFTER1 ADVICE */
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after1 = "MethodCallByMethod,long,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after1 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after1 = "MethodCallByMethod,long";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after1 = "MethodCallByMethod,int";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after1 = "MethodCallByMethod,Object[]";
   }
   
   public void after1(@JoinPoint MethodCallByMethod joinPoint)
   {
      after1 = "MethodCallByMethod";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after1="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after1="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after1(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after1="Object,SuperClass,long,int";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after1="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after1="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after1(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after1="Object,SuperClass,long,Object[]";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after1="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after1="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after1(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after1="Object,SuperClass,long";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after1="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after1="OverloadedAdvicePOJO,long,int";
   }
   
   public void after1(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after1="Object,long,int";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after1="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after1(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after1="Object,long,Object[]";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after1="OverloadedAdvicePOJO,long";
   }
   
   public void after1(@Target Object target, @Return long returnedValue)
   {
      after1="Object,long";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after1="OverloadedAdvicePOJO,int";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after1="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after1(@Target OverloadedAdvicePOJO target)
   {
      after1="OverloadedAdvicePOJO";
   }
   
   public void after1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after1="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after1(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after1 = "SuperClass,long,int";
   }
   
   public void after1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after1 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after1(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after1 = "SuperClass,long,Object[]";
   }
   
   public void after1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after1 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after1(@Caller SuperClass caller, @Return long returnedValue)
   {
      after1 = "SuperClass,long";
   }
   
   public void after1(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after1 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after1(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after1 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after1(@Return long returnedValue, @Arg int arg)
   {
      after1 = "long,int";
   }
   
   /* AFTER2 ADVICE */
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after2 = "MethodCallByMethod,long,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after2 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after2 = "MethodCallByMethod,long";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after2 = "MethodCallByMethod,int";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after2 = "MethodCallByMethod,Object[]";
   }
   
   public void after2(@JoinPoint MethodCallByMethod joinPoint)
   {
      after2 = "MethodCallByMethod";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after2="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after2="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after2(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after2="Object,SuperClass,long,int";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after2="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after2="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after2(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after2="Object,SuperClass,long,Object[]";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after2="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after2="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after2(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after2="Object,SuperClass,long";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after2="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after2="OverloadedAdvicePOJO,long,int";
   }
   
   public void after2(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after2="Object,long,int";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after2="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after2(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after2="Object,long,Object[]";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after2="OverloadedAdvicePOJO,long";
   }
   
   public void after2(@Target Object target, @Return long returnedValue)
   {
      after2="Object,long";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after2="OverloadedAdvicePOJO,int";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after2="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after2(@Target OverloadedAdvicePOJO target)
   {
      after2="OverloadedAdvicePOJO";
   }
   
   public void after2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after2="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after2(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after2 = "SuperClass,long,int";
   }
   
   public void after2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after2 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after2(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after2 = "SuperClass,long,Object[]";
   }
   
   public void after2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after2 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after2(@Caller SuperClass caller, @Return long returnedValue)
   {
      after2 = "SuperClass,long";
   }
   
   public void after2(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after2 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after2(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after2 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after2(@Return long returnedValue, @Arg int arg)
   {
      after2 = "long,int";
   }
   
   /* AFTER3 ADVICE */
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after3 = "MethodCallByMethod,long,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after3 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after3 = "MethodCallByMethod,long";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after3 = "MethodCallByMethod,int";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after3 = "MethodCallByMethod,Object[]";
   }
   
   public void after3(@JoinPoint MethodCallByMethod joinPoint)
   {
      after3 = "MethodCallByMethod";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after3="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after3="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after3(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after3="Object,SuperClass,long,int";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after3="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after3="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after3(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after3="Object,SuperClass,long,Object[]";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after3="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after3="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after3(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after3="Object,SuperClass,long";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after3="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after3="OverloadedAdvicePOJO,long,int";
   }
   
   public void after3(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after3="Object,long,int";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after3="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after3(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after3="Object,long,Object[]";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after3="OverloadedAdvicePOJO,long";
   }
   
   public void after3(@Target Object target, @Return long returnedValue)
   {
      after3="Object,long";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after3="OverloadedAdvicePOJO,int";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after3="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after3(@Target OverloadedAdvicePOJO target)
   {
      after3="OverloadedAdvicePOJO";
   }
   
   public void after3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after3="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after3(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after3 = "SuperClass,long,int";
   }
   
   public void after3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after3 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after3(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after3 = "SuperClass,long,Object[]";
   }
   
   public void after3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after3 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after3(@Caller SuperClass caller, @Return long returnedValue)
   {
      after3 = "SuperClass,long";
   }
   
   public void after3(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after3 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after3(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after3 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after3(@Return long returnedValue, @Arg int arg)
   {
      after3 = "long,int";
   }
   
   /* AFTER4 ADVICE */
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after4 = "MethodCallByMethod,long,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after4 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after4 = "MethodCallByMethod,long";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after4 = "MethodCallByMethod,int";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after4 = "MethodCallByMethod,Object[]";
   }
   
   public void after4(@JoinPoint MethodCallByMethod joinPoint)
   {
      after4 = "MethodCallByMethod";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after4="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after4="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after4(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after4="Object,SuperClass,long,int";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after4="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after4="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after4(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after4="Object,SuperClass,long,Object[]";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after4="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after4="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after4(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after4="Object,SuperClass,long";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after4="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after4="OverloadedAdvicePOJO,long,int";
   }
   
   public void after4(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after4="Object,long,int";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after4="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after4(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after4="Object,long,Object[]";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after4="OverloadedAdvicePOJO,long";
   }
   
   public void after4(@Target Object target, @Return long returnedValue)
   {
      after4="Object,long";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after4="OverloadedAdvicePOJO,int";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after4="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after4(@Target OverloadedAdvicePOJO target)
   {
      after4="OverloadedAdvicePOJO";
   }
   
   public void after4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after4="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after4(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after4 = "SuperClass,long,int";
   }
   
   public void after4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after4 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after4(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after4 = "SuperClass,long,Object[]";
   }
   
   public void after4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after4 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after4(@Caller SuperClass caller, @Return long returnedValue)
   {
      after4 = "SuperClass,long";
   }
   
   public void after4(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after4 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after4(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after4 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after4(@Return long returnedValue, @Arg int arg)
   {
      after4 = "long,int";
   }
   
   /* AFTER5 ADVICE */
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after5 = "MethodCallByMethod,long,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after5 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after5 = "MethodCallByMethod,long";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after5 = "MethodCallByMethod,int";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after5 = "MethodCallByMethod,Object[]";
   }
   
   public void after5(@JoinPoint MethodCallByMethod joinPoint)
   {
      after5 = "MethodCallByMethod";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after5="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after5="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after5(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after5="Object,SuperClass,long,int";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after5="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after5="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after5(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after5="Object,SuperClass,long,Object[]";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after5="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after5="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after5(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after5="Object,SuperClass,long";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after5="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after5="OverloadedAdvicePOJO,long,int";
   }
   
   public void after5(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after5="Object,long,int";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after5="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after5(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after5="Object,long,Object[]";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after5="OverloadedAdvicePOJO,long";
   }
   
   public void after5(@Target Object target, @Return long returnedValue)
   {
      after5="Object,long";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after5="OverloadedAdvicePOJO,int";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after5="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after5(@Target OverloadedAdvicePOJO target)
   {
      after5="OverloadedAdvicePOJO";
   }
   
   public void after5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after5="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after5(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after5 = "SuperClass,long,int";
   }
   
   public void after5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after5 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after5(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after5 = "SuperClass,long,Object[]";
   }
   
   public void after5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after5 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after5(@Caller SuperClass caller, @Return long returnedValue)
   {
      after5 = "SuperClass,long";
   }
   
   public void after5(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after5 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after5(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after5 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after5(@Return long returnedValue, @Arg int arg)
   {
      after5 = "long,int";
   }
   
   /* AFTER6 ADVICE */
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Arg int arg)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO,long,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after6 = "MethodCallByMethod,long,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after6 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after6 = "MethodCallByMethod,long";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after6 = "MethodCallByMethod,int";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after6 = "MethodCallByMethod,Object[]";
   }
   
   public void after6(@JoinPoint MethodCallByMethod joinPoint)
   {
      after6 = "MethodCallByMethod";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after6="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after6="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after6(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after6="Object,SuperClass,long,int";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after6="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after6="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after6(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after6="Object,SuperClass,long,Object[]";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after6="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after6="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after6(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after6="Object,SuperClass,long";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after6="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after6="OverloadedAdvicePOJO,long,int";
   }
   
   public void after6(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after6="Object,long,int";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after6="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after6(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after6="Object,long,Object[]";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after6="OverloadedAdvicePOJO,long";
   }
   
   public void after6(@Target Object target, @Return long returnedValue)
   {
      after6="Object,long";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after6="OverloadedAdvicePOJO,int";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after6="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after6(@Target OverloadedAdvicePOJO target)
   {
      after6="OverloadedAdvicePOJO";
   }
   
   public void after6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after6="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after6(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after6 = "SuperClass,long,int";
   }
   
   public void after6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after6 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after6(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after6 = "SuperClass,long,Object[]";
   }
   
   public void after6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after6 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after6(@Caller SuperClass caller, @Return long returnedValue)
   {
      after6 = "SuperClass,long";
   }
   
   public void after6(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after6 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after6(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after6 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after6(@Return long returnedValue, @Arg int arg)
   {
      after6 = "long,int";
   }
   
   /* AFTER7 ADVICE */
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Args Object[] args)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after7 = "MethodCallByMethod,long,int";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after7 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after7 = "MethodCallByMethod,long";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after7 = "MethodCallByMethod,int";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after7 = "MethodCallByMethod,Object[]";
   }
   
   public void after7(@JoinPoint MethodCallByMethod joinPoint)
   {
      after7 = "MethodCallByMethod";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after7="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after7="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after7(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after7="Object,SuperClass,long,int";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after7="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after7="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after7(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after7="Object,SuperClass,long,Object[]";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after7="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after7="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after7(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after7="Object,SuperClass,long";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after7="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after7="OverloadedAdvicePOJO,long,int";
   }
   
   public void after7(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after7="Object,long,int";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after7="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after7(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after7="Object,long,Object[]";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after7="OverloadedAdvicePOJO,long";
   }
   
   public void after7(@Target Object target, @Return long returnedValue)
   {
      after7="Object,long";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after7="OverloadedAdvicePOJO,int";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after7="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after7(@Target OverloadedAdvicePOJO target)
   {
      after7="OverloadedAdvicePOJO";
   }
   
   public void after7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after7="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after7(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after7 = "SuperClass,long,int";
   }
   
   public void after7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after7 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after7(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after7 = "SuperClass,long,Object[]";
   }
   
   public void after7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after7 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after7(@Caller SuperClass caller, @Return long returnedValue)
   {
      after7 = "SuperClass,long";
   }
   
   public void after7(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after7 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after7(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after7 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after7(@Return long returnedValue, @Arg int arg)
   {
      after7 = "long,int";
   }
   
   /* AFTER8 ADVICE */
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJO,long";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after8 = "MethodCallByMethod,long,int";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after8 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after8 = "MethodCallByMethod,long";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after8 = "MethodCallByMethod,int";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after8 = "MethodCallByMethod,Object[]";
   }
   
   public void after8(@JoinPoint MethodCallByMethod joinPoint)
   {
      after8 = "MethodCallByMethod";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after8="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after8="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after8(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after8="Object,SuperClass,long,int";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after8="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after8="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after8(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after8="Object,SuperClass,long,Object[]";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after8="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after8="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after8(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after8="Object,SuperClass,long";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after8="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after8="OverloadedAdvicePOJO,long,int";
   }
   
   public void after8(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after8="Object,long,int";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after8="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after8(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after8="Object,long,Object[]";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after8="OverloadedAdvicePOJO,long";
   }
   
   public void after8(@Target Object target, @Return long returnedValue)
   {
      after8="Object,long";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after8="OverloadedAdvicePOJO,int";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after8="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after8(@Target OverloadedAdvicePOJO target)
   {
      after8="OverloadedAdvicePOJO";
   }
   
   public void after8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after8="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after8(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after8 = "SuperClass,long,int";
   }
   
   public void after8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after8 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after8(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after8 = "SuperClass,long,Object[]";
   }
   
   public void after8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after8 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after8(@Caller SuperClass caller, @Return long returnedValue)
   {
      after8 = "SuperClass,long";
   }
   
   public void after8(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after8 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after8(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after8 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after8(@Return long returnedValue, @Arg int arg)
   {
      after8 = "long,int";
   }
   
   /* AFTER9 ADVICE */
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after9 = "MethodCallByMethod,long,int";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after9 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after9 = "MethodCallByMethod,long";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after9 = "MethodCallByMethod,int";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after9 = "MethodCallByMethod,Object[]";
   }
   
   public void after9(@JoinPoint MethodCallByMethod joinPoint)
   {
      after9 = "MethodCallByMethod";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after9="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after9="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after9(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after9="Object,SuperClass,long,int";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after9="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after9="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after9(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after9="Object,SuperClass,long,Object[]";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after9="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after9="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after9(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after9="Object,SuperClass,long";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after9="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after9="OverloadedAdvicePOJO,long,int";
   }
   
   public void after9(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after9="Object,long,int";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after9="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after9(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after9="Object,long,Object[]";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after9="OverloadedAdvicePOJO,long";
   }
   
   public void after9(@Target Object target, @Return long returnedValue)
   {
      after9="Object,long";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after9="OverloadedAdvicePOJO,int";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after9="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after9(@Target OverloadedAdvicePOJO target)
   {
      after9="OverloadedAdvicePOJO";
   }
   
   public void after9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after9="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after9(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after9 = "SuperClass,long,int";
   }
   
   public void after9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after9 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after9(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after9 = "SuperClass,long,Object[]";
   }
   
   public void after9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after9 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after9(@Caller SuperClass caller, @Return long returnedValue)
   {
      after9 = "SuperClass,long";
   }
   
   public void after9(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after9 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after9(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after9 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after9(@Return long returnedValue, @Arg int arg)
   {
      after9 = "long,int";
   }
   
   /* AFTER10 ADVICE */
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after10 = "MethodCallByMethod,long,int";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after10 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after10 = "MethodCallByMethod,long";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after10 = "MethodCallByMethod,int";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after10 = "MethodCallByMethod,Object[]";
   }
   
   public void after10(@JoinPoint MethodCallByMethod joinPoint)
   {
      after10 = "MethodCallByMethod";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after10="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after10="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after10(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after10="Object,SuperClass,long,int";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after10="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after10="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after10(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after10="Object,SuperClass,long,Object[]";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after10="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after10="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after10(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after10="Object,SuperClass,long";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after10="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after10="OverloadedAdvicePOJO,long,int";
   }
   
   public void after10(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after10="Object,long,int";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after10="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after10(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after10="Object,long,Object[]";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after10="OverloadedAdvicePOJO,long";
   }
   
   public void after10(@Target Object target, @Return long returnedValue)
   {
      after10="Object,long";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after10="OverloadedAdvicePOJO,int";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after10="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after10(@Target OverloadedAdvicePOJO target)
   {
      after10="OverloadedAdvicePOJO";
   }
   
   public void after10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after10="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after10(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after10 = "SuperClass,long,int";
   }
   
   public void after10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after10 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after10(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after10 = "SuperClass,long,Object[]";
   }
   
   public void after10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after10 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after10(@Caller SuperClass caller, @Return long returnedValue)
   {
      after10 = "SuperClass,long";
   }
   
   public void after10(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after10 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after10(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after10 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after10(@Return long returnedValue, @Arg int arg)
   {
      after10 = "long,int";
   }
   
   /* AFTER11 ADVICE */
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after11 = "MethodCallByMethod,long,int";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after11 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after11 = "MethodCallByMethod,long";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after11 = "MethodCallByMethod,int";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after11 = "MethodCallByMethod,Object[]";
   }
   
   public void after11(@JoinPoint MethodCallByMethod joinPoint)
   {
      after11 = "MethodCallByMethod";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after11="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after11="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after11(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after11="Object,SuperClass,long,int";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after11="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after11="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after11(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after11="Object,SuperClass,long,Object[]";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after11="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after11="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after11(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after11="Object,SuperClass,long";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after11="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after11="OverloadedAdvicePOJO,long,int";
   }
   
   public void after11(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after11="Object,long,int";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after11="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after11(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after11="Object,long,Object[]";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after11="OverloadedAdvicePOJO,long";
   }
   
   public void after11(@Target Object target, @Return long returnedValue)
   {
      after11="Object,long";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after11="OverloadedAdvicePOJO,int";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after11="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after11(@Target OverloadedAdvicePOJO target)
   {
      after11="OverloadedAdvicePOJO";
   }
   
   public void after11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after11="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after11(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after11 = "SuperClass,long,int";
   }
   
   public void after11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after11 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after11(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after11 = "SuperClass,long,Object[]";
   }
   
   public void after11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after11 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after11(@Caller SuperClass caller, @Return long returnedValue)
   {
      after11 = "SuperClass,long";
   }
   
   public void after11(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after11 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after11(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after11 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after11(@Return long returnedValue, @Arg int arg)
   {
      after11 = "long,int";
   }
   
   /* AFTER12 ADVICE */
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after12 = "MethodCallByMethod,long,int";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after12 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after12 = "MethodCallByMethod,long";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after12 = "MethodCallByMethod,int";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after12 = "MethodCallByMethod,Object[]";
   }
   
   public void after12(@JoinPoint MethodCallByMethod joinPoint)
   {
      after12 = "MethodCallByMethod";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after12="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after12="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after12(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after12="Object,SuperClass,long,int";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after12="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after12="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after12(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after12="Object,SuperClass,long,Object[]";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after12="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after12="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after12(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after12="Object,SuperClass,long";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after12="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after12="OverloadedAdvicePOJO,long,int";
   }
   
   public void after12(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after12="Object,long,int";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after12="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after12(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after12="Object,long,Object[]";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after12="OverloadedAdvicePOJO,long";
   }
   
   public void after12(@Target Object target, @Return long returnedValue)
   {
      after12="Object,long";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after12="OverloadedAdvicePOJO,int";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after12="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after12(@Target OverloadedAdvicePOJO target)
   {
      after12="OverloadedAdvicePOJO";
   }
   
   public void after12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after12="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after12(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after12 = "SuperClass,long,int";
   }
   
   public void after12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after12 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after12(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after12 = "SuperClass,long,Object[]";
   }
   
   public void after12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after12 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after12(@Caller SuperClass caller, @Return long returnedValue)
   {
      after12 = "SuperClass,long";
   }
   
   public void after12(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after12 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after12(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after12 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after12(@Return long returnedValue, @Arg int arg)
   {
      after12 = "long,int";
   }
   
   /* AFTER13 ADVICE */
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after13 = "MethodCallByMethod,long,int";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after13 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after13 = "MethodCallByMethod,long";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after13 = "MethodCallByMethod,int";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after13 = "MethodCallByMethod,Object[]";
   }
   
   public void after13(@JoinPoint MethodCallByMethod joinPoint)
   {
      after13 = "MethodCallByMethod";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after13="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after13="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after13(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after13="Object,SuperClass,long,int";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after13="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after13="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after13(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after13="Object,SuperClass,long,Object[]";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after13="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after13="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after13(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after13="Object,SuperClass,long";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after13="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after13="OverloadedAdvicePOJO,long,int";
   }
   
   public void after13(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after13="Object,long,int";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after13="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after13(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after13="Object,long,Object[]";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after13="OverloadedAdvicePOJO,long";
   }
   
   public void after13(@Target Object target, @Return long returnedValue)
   {
      after13="Object,long";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after13="OverloadedAdvicePOJO,int";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after13="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after13(@Target OverloadedAdvicePOJO target)
   {
      after13="OverloadedAdvicePOJO";
   }
   
   public void after13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after13="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after13(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after13 = "SuperClass,long,int";
   }
   
   public void after13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after13 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after13(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after13 = "SuperClass,long,Object[]";
   }
   
   public void after13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after13 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after13(@Caller SuperClass caller, @Return long returnedValue)
   {
      after13 = "SuperClass,long";
   }
   
   public void after13(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after13 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after13(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after13 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after13(@Return long returnedValue, @Arg int arg)
   {
      after13 = "long,int";
   }
   
   /* AFTER14 ADVICE */
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after14 = "MethodCallByMethod,long,int";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after14 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after14 = "MethodCallByMethod,long";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after14 = "MethodCallByMethod,int";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after14 = "MethodCallByMethod,Object[]";
   }
   
   public void after14(@JoinPoint MethodCallByMethod joinPoint)
   {
      after14 = "MethodCallByMethod";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after14="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after14="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after14(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after14="Object,SuperClass,long,int";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after14="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after14="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after14(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after14="Object,SuperClass,long,Object[]";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after14="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after14="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after14(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after14="Object,SuperClass,long";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after14="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after14="OverloadedAdvicePOJO,long,int";
   }
   
   public void after14(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after14="Object,long,int";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after14="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after14(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after14="Object,long,Object[]";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after14="OverloadedAdvicePOJO,long";
   }
   
   public void after14(@Target Object target, @Return long returnedValue)
   {
      after14="Object,long";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after14="OverloadedAdvicePOJO,int";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after14="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after14(@Target OverloadedAdvicePOJO target)
   {
      after14="OverloadedAdvicePOJO";
   }
   
   public void after14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after14="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after14(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after14 = "SuperClass,long,int";
   }
   
   public void after14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after14 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after14(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after14 = "SuperClass,long,Object[]";
   }
   
   public void after14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after14 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after14(@Caller SuperClass caller, @Return long returnedValue)
   {
      after14 = "SuperClass,long";
   }
   
   public void after14(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after14 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after14(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after14 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after14(@Return long returnedValue, @Arg int arg)
   {
      after14 = "long,int";
   }
   
   /* AFTER15 ADVICE */
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after15 = "MethodCallByMethod,long,int";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after15 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after15 = "MethodCallByMethod,long";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after15 = "MethodCallByMethod,int";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after15 = "MethodCallByMethod,Object[]";
   }
   
   public void after15(@JoinPoint MethodCallByMethod joinPoint)
   {
      after15 = "MethodCallByMethod";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after15="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after15="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after15(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after15="Object,SuperClass,long,int";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after15="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after15="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after15(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after15="Object,SuperClass,long,Object[]";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after15="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after15="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after15(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after15="Object,SuperClass,long";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after15="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after15="OverloadedAdvicePOJO,long,int";
   }
   
   public void after15(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after15="Object,long,int";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after15="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after15(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after15="Object,long,Object[]";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after15="OverloadedAdvicePOJO,long";
   }
   
   public void after15(@Target Object target, @Return long returnedValue)
   {
      after15="Object,long";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after15="OverloadedAdvicePOJO,int";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after15="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after15(@Target OverloadedAdvicePOJO target)
   {
      after15="OverloadedAdvicePOJO";
   }
   
   public void after15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after15="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after15(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after15 = "SuperClass,long,int";
   }
   
   public void after15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after15 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after15(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after15 = "SuperClass,long,Object[]";
   }
   
   public void after15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after15 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after15(@Caller SuperClass caller, @Return long returnedValue)
   {
      after15 = "SuperClass,long";
   }
   
   public void after15(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after15 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after15(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after15 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after15(@Return long returnedValue, @Arg int arg)
   {
      after15 = "long,int";
   }
   
   /* AFTER16 ADVICE */
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Arg int arg)
   {
      after16 = "MethodCallByMethod,long,int";
   }
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after16 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after16 = "MethodCallByMethod,long";
   }
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after16 = "MethodCallByMethod,int";
   }
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after16 = "MethodCallByMethod,Object[]";
   }
   
   public void after16(@JoinPoint MethodCallByMethod joinPoint)
   {
      after16 = "MethodCallByMethod";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after16="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after16="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after16(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after16="Object,SuperClass,long,int";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after16="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after16="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after16(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after16="Object,SuperClass,long,Object[]";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after16="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after16="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after16(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after16="Object,SuperClass,long";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after16="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after16="OverloadedAdvicePOJO,long,int";
   }
   
   public void after16(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after16="Object,long,int";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after16="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after16(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after16="Object,long,Object[]";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after16="OverloadedAdvicePOJO,long";
   }
   
   public void after16(@Target Object target, @Return long returnedValue)
   {
      after16="Object,long";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after16="OverloadedAdvicePOJO,int";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after16="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after16(@Target OverloadedAdvicePOJO target)
   {
      after16="OverloadedAdvicePOJO";
   }
   
   public void after16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after16="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after16(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after16 = "SuperClass,long,int";
   }
   
   public void after16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after16 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after16(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after16 = "SuperClass,long,Object[]";
   }
   
   public void after16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after16 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after16(@Caller SuperClass caller, @Return long returnedValue)
   {
      after16 = "SuperClass,long";
   }
   
   public void after16(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after16 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after16(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after16 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after16(@Return long returnedValue, @Arg int arg)
   {
      after16 = "long,int";
   }
   
   /* AFTER17 ADVICE */
   
   public void after17(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Args Object[] args)
   {
      after17 = "MethodCallByMethod,long,Object[]";
   }
   
   public void after17(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after17 = "MethodCallByMethod,long";
   }
   
   public void after17(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after17 = "MethodCallByMethod,int";
   }
   
   public void after17(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after17 = "MethodCallByMethod,Object[]";
   }
   
   public void after17(@JoinPoint MethodCallByMethod joinPoint)
   {
      after17 = "MethodCallByMethod";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after17="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after17="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after17(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after17="Object,SuperClass,long,int";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after17="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after17="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after17(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after17="Object,SuperClass,long,Object[]";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after17="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after17="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after17(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after17="Object,SuperClass,long";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after17="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after17="OverloadedAdvicePOJO,long,int";
   }
   
   public void after17(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after17="Object,long,int";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after17="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after17(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after17="Object,long,Object[]";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after17="OverloadedAdvicePOJO,long";
   }
   
   public void after17(@Target Object target, @Return long returnedValue)
   {
      after17="Object,long";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after17="OverloadedAdvicePOJO,int";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after17="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after17(@Target OverloadedAdvicePOJO target)
   {
      after17="OverloadedAdvicePOJO";
   }
   
   public void after17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after17="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after17(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after17 = "SuperClass,long,int";
   }
   
   public void after17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after17 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after17(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after17 = "SuperClass,long,Object[]";
   }
   
   public void after17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after17 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after17(@Caller SuperClass caller, @Return long returnedValue)
   {
      after17 = "SuperClass,long";
   }
   
   public void after17(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after17 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after17(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after17 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after17(@Return long returnedValue, @Arg int arg)
   {
      after17 = "long,int";
   }
   
   /* AFTER18 ADVICE */
   
   public void after18(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned)
   {
      after18 = "MethodCallByMethod,long";
   }
   
   public void after18(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after18 = "MethodCallByMethod,int";
   }
   
   public void after18(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after18 = "MethodCallByMethod,Object[]";
   }
   
   public void after18(@JoinPoint MethodCallByMethod joinPoint)
   {
      after18 = "MethodCallByMethod";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after18="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after18="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after18(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after18="Object,SuperClass,long,int";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after18="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after18="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after18(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after18="Object,SuperClass,long,Object[]";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after18="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after18="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after18(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after18="Object,SuperClass,long";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after18="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after18="OverloadedAdvicePOJO,long,int";
   }
   
   public void after18(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after18="Object,long,int";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after18="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after18(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after18="Object,long,Object[]";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after18="OverloadedAdvicePOJO,long";
   }
   
   public void after18(@Target Object target, @Return long returnedValue)
   {
      after18="Object,long";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after18="OverloadedAdvicePOJO,int";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after18="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after18(@Target OverloadedAdvicePOJO target)
   {
      after18="OverloadedAdvicePOJO";
   }
   
   public void after18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after18="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after18(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after18 = "SuperClass,long,int";
   }
   
   public void after18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after18 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after18(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after18 = "SuperClass,long,Object[]";
   }
   
   public void after18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after18 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after18(@Caller SuperClass caller, @Return long returnedValue)
   {
      after18 = "SuperClass,long";
   }
   
   public void after18(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after18 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after18(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after18 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after18(@Return long returnedValue, @Arg int arg)
   {
      after18 = "long,int";
   }
   
   /* AFTER19 ADVICE */
   
   public void after19(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      after19 = "MethodCallByMethod,int";
   }
   
   public void after19(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      after19 = "MethodCallByMethod,Object[]";
   }
   
   public void after19(@JoinPoint MethodCallByMethod joinPoint)
   {
      after19 = "MethodCallByMethod";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after19="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after19="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after19(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after19="Object,SuperClass,long,int";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after19="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after19="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after19(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after19="Object,SuperClass,long,Object[]";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after19="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after19="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after19(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after19="Object,SuperClass,long";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after19="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after19="OverloadedAdvicePOJO,long,int";
   }
   
   public void after19(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after19="Object,long,int";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after19="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after19(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after19="Object,long,Object[]";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after19="OverloadedAdvicePOJO,long";
   }
   
   public void after19(@Target Object target, @Return long returnedValue)
   {
      after19="Object,long";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after19="OverloadedAdvicePOJO,int";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after19="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after19(@Target OverloadedAdvicePOJO target)
   {
      after19="OverloadedAdvicePOJO";
   }
   
   public void after19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after19="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after19(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after19 = "SuperClass,long,int";
   }
   
   public void after19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after19 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after19(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after19 = "SuperClass,long,Object[]";
   }
   
   public void after19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after19 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after19(@Caller SuperClass caller, @Return long returnedValue)
   {
      after19 = "SuperClass,long";
   }
   
   public void after19(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after19 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after19(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after19 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after19(@Return long returnedValue, @Arg int arg)
   {
      after19 = "long,int";
   }
   
   /* AFTER20 ADVICE */
   
   public void after20(@JoinPoint MethodCallByMethod joinPoint)
   {
      after20 = "MethodCallByMethod";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after20="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after20="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after20(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after20="Object,SuperClass,long,int";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after20="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after20="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after20(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after20="Object,SuperClass,long,Object[]";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after20="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after20="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after20(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after20="Object,SuperClass,long";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after20="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after20="OverloadedAdvicePOJO,long,int";
   }
   
   public void after20(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after20="Object,long,int";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after20="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after20(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after20="Object,long,Object[]";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after20="OverloadedAdvicePOJO,long";
   }
   
   public void after20(@Target Object target, @Return long returnedValue)
   {
      after20="Object,long";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after20="OverloadedAdvicePOJO,int";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after20="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after20(@Target OverloadedAdvicePOJO target)
   {
      after20="OverloadedAdvicePOJO";
   }
   
   public void after20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after20="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after20(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after20 = "SuperClass,long,int";
   }
   
   public void after20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after20 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after20(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after20 = "SuperClass,long,Object[]";
   }
   
   public void after20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after20 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after20(@Caller SuperClass caller, @Return long returnedValue)
   {
      after20 = "SuperClass,long";
   }
   
   public void after20(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after20 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after20(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after20 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after20(@Return long returnedValue, @Arg int arg)
   {
      after20 = "long,int";
   }
   
   /* AFTER21 ADVICE */
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after21="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after21="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after21(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after21="Object,SuperClass,long,int";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after21="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after21="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after21(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after21="Object,SuperClass,long,Object[]";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after21="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after21="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after21(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after21="Object,SuperClass,long";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after21="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after21="OverloadedAdvicePOJO,long,int";
   }
   
   public void after21(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after21="Object,long,int";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after21="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after21(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after21="Object,long,Object[]";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after21="OverloadedAdvicePOJO,long";
   }
   
   public void after21(@Target Object target, @Return long returnedValue)
   {
      after21="Object,long";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after21="OverloadedAdvicePOJO,int";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after21="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after21(@Target OverloadedAdvicePOJO target)
   {
      after21="OverloadedAdvicePOJO";
   }
   
   public void after21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after21="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after21(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after21 = "SuperClass,long,int";
   }
   
   public void after21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after21 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after21(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after21 = "SuperClass,long,Object[]";
   }
   
   public void after21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after21 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after21(@Caller SuperClass caller, @Return long returnedValue)
   {
      after21 = "SuperClass,long";
   }
   
   public void after21(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after21 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after21(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after21 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after21(@Return long returnedValue, @Arg int arg)
   {
      after21 = "long,int";
   }
   
   /* AFTER22 ADVICE */
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Arg int arg)
   {
      after22="OverloadedAdvicePOJO,SuperClass,long,int";
   }
   
   public void after22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after22="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after22(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after22="Object,SuperClass,long,int";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after22="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after22="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after22="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after22(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after22="Object,SuperClass,long,Object[]";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after22="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after22="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after22="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after22(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after22="Object,SuperClass,long";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after22="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after22="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after22="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after22="OverloadedAdvicePOJO,long,int";
   }
   
   public void after22(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after22="Object,long,int";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after22="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after22(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after22="Object,long,Object[]";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after22="OverloadedAdvicePOJO,long";
   }
   
   public void after22(@Target Object target, @Return long returnedValue)
   {
      after22="Object,long";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after22="OverloadedAdvicePOJO,int";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after22="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after22(@Target OverloadedAdvicePOJO target)
   {
      after22="OverloadedAdvicePOJO";
   }
   
   public void after22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after22="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after22(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after22 = "SuperClass,long,int";
   }
   
   public void after22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after22 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after22(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after22 = "SuperClass,long,Object[]";
   }
   
   public void after22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after22 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after22(@Caller SuperClass caller, @Return long returnedValue)
   {
      after22 = "SuperClass,long";
   }
   
   public void after22(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after22 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after22(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after22 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after22(@Return long returnedValue, @Arg int arg)
   {
      after22 = "long,int";
   }
   
   /* AFTER23 ADVICE */
   
   public void after23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Arg int arg)
   {
      after23="Object,OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after23(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after23="Object,SuperClass,long,int";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after23="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after23="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after23="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after23(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after23="Object,SuperClass,long,Object[]";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after23="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after23="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after23="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after23(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after23="Object,SuperClass,long";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after23="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after23="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after23="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after23="OverloadedAdvicePOJO,long,int";
   }
   
   public void after23(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after23="Object,long,int";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after23="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after23(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after23="Object,long,Object[]";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after23="OverloadedAdvicePOJO,long";
   }
   
   public void after23(@Target Object target, @Return long returnedValue)
   {
      after23="Object,long";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after23="OverloadedAdvicePOJO,int";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after23="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after23(@Target OverloadedAdvicePOJO target)
   {
      after23="OverloadedAdvicePOJO";
   }
   
   public void after23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after23="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after23(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after23 = "SuperClass,long,int";
   }
   
   public void after23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after23 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after23(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after23 = "SuperClass,long,Object[]";
   }
   
   public void after23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after23 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after23(@Caller SuperClass caller, @Return long returnedValue)
   {
      after23 = "SuperClass,long";
   }
   
   public void after23(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after23 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after23(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after23 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after23(@Return long returnedValue, @Arg int arg)
   {
      after23 = "long,int";
   }
   
   /* AFTER24 ADVICE */
   
   public void after24(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Arg int arg)
   {
      after24="Object,SuperClass,long,int";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after24="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after24="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after24="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after24(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after24="Object,SuperClass,long,Object[]";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after24="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after24="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after24="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after24(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after24="Object,SuperClass,long";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after24="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after24="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after24="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after24="OverloadedAdvicePOJO,long,int";
   }
   
   public void after24(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after24="Object,long,int";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after24="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after24(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after24="Object,long,Object[]";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after24="OverloadedAdvicePOJO,long";
   }
   
   public void after24(@Target Object target, @Return long returnedValue)
   {
      after24="Object,long";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after24="OverloadedAdvicePOJO,int";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after24="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after24(@Target OverloadedAdvicePOJO target)
   {
      after24="OverloadedAdvicePOJO";
   }
   
   public void after24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after24="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after24(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after24 = "SuperClass,long,int";
   }
   
   public void after24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after24 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after24(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after24 = "SuperClass,long,Object[]";
   }
   
   public void after24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after24 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after24(@Caller SuperClass caller, @Return long returnedValue)
   {
      after24 = "SuperClass,long";
   }
   
   public void after24(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after24 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after24(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after24 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after24(@Return long returnedValue, @Arg int arg)
   {
      after24 = "long,int";
   }
   
   /* AFTER25 ADVICE */
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after25="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after25="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after25="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after25(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after25="Object,SuperClass,long,Object[]";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after25="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after25="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after25="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after25(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after25="Object,SuperClass,long";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after25="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after25="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after25="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after25="OverloadedAdvicePOJO,long,int";
   }
   
   public void after25(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after25="Object,long,int";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after25="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after25(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after25="Object,long,Object[]";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after25="OverloadedAdvicePOJO,long";
   }
   
   public void after25(@Target Object target, @Return long returnedValue)
   {
      after25="Object,long";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after25="OverloadedAdvicePOJO,int";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after25="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after25(@Target OverloadedAdvicePOJO target)
   {
      after25="OverloadedAdvicePOJO";
   }
   
   public void after25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after25="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after25(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after25 = "SuperClass,long,int";
   }
   
   public void after25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after25 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after25(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after25 = "SuperClass,long,Object[]";
   }
   
   public void after25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after25 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after25(@Caller SuperClass caller, @Return long returnedValue)
   {
      after25 = "SuperClass,long";
   }
   
   public void after25(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after25 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after25(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after25 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after25(@Return long returnedValue, @Arg int arg)
   {
      after25 = "long,int";
   }
   
   /* AFTER26 ADVICE */
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after26="OverloadedAdvicePOJO,SuperClass,long,Object[]";
   }
   
   public void after26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after26="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after26(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after26="Object,SuperClass,long,Object[]";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after26="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after26="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after26="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after26(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after26="Object,SuperClass,long";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after26="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after26="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after26="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after26="OverloadedAdvicePOJO,long,int";
   }
   
   public void after26(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after26="Object,long,int";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after26="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after26(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after26="Object,long,Object[]";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after26="OverloadedAdvicePOJO,long";
   }
   
   public void after26(@Target Object target, @Return long returnedValue)
   {
      after26="Object,long";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after26="OverloadedAdvicePOJO,int";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after26="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after26(@Target OverloadedAdvicePOJO target)
   {
      after26="OverloadedAdvicePOJO";
   }
   
   public void after26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after26="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after26(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after26 = "SuperClass,long,int";
   }
   
   public void after26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after26 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after26(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after26 = "SuperClass,long,Object[]";
   }
   
   public void after26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after26 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after26(@Caller SuperClass caller, @Return long returnedValue)
   {
      after26 = "SuperClass,long";
   }
   
   public void after26(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after26 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after26(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after26 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after26(@Return long returnedValue, @Arg int arg)
   {
      after26 = "long,int";
   }
   
   /* AFTER27 ADVICE */
   
   public void after27(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Args Object[] args)
   {
      after27="Object,OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after27(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after27="Object,SuperClass,long,Object[]";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after27="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after27="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after27(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after27="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after27(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after27="Object,SuperClass,long";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after27="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after27="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after27="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after27="OverloadedAdvicePOJO,long,int";
   }
   
   public void after27(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after27="Object,long,int";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after27="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after27(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after27="Object,long,Object[]";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after27="OverloadedAdvicePOJO,long";
   }
   
   public void after27(@Target Object target, @Return long returnedValue)
   {
      after27="Object,long";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after27="OverloadedAdvicePOJO,int";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after27="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after27(@Target OverloadedAdvicePOJO target)
   {
      after27="OverloadedAdvicePOJO";
   }
   
   public void after27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after27="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after27(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after27 = "SuperClass,long,int";
   }
   
   public void after27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after27 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after27(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after27 = "SuperClass,long,Object[]";
   }
   
   public void after27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after27 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after27(@Caller SuperClass caller, @Return long returnedValue)
   {
      after27 = "SuperClass,long";
   }
   
   public void after27(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after27 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after27(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after27 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after27(@Return long returnedValue, @Arg int arg)
   {
      after27 = "long,int";
   }
   
   /* AFTER28 ADVICE */
   
   public void after28(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned, @Args Object[] args)
   {
      after28="Object,SuperClass,long,Object[]";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after28="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after28="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after28(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after28="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after28(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after28="Object,SuperClass,long";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after28="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after28="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after28="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after28="OverloadedAdvicePOJO,long,int";
   }
   
   public void after28(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after28="Object,long,int";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after28="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after28(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after28="Object,long,Object[]";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after28="OverloadedAdvicePOJO,long";
   }
   
   public void after28(@Target Object target, @Return long returnedValue)
   {
      after28="Object,long";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after28="OverloadedAdvicePOJO,int";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after28="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after28(@Target OverloadedAdvicePOJO target)
   {
      after28="OverloadedAdvicePOJO";
   }
   
   public void after28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after28="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after28(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after28 = "SuperClass,long,int";
   }
   
   public void after28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after28 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after28(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after28 = "SuperClass,long,Object[]";
   }
   
   public void after28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after28 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after28(@Caller SuperClass caller, @Return long returnedValue)
   {
      after28 = "SuperClass,long";
   }
   
   public void after28(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after28 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after28(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after28 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after28(@Return long returnedValue, @Arg int arg)
   {
      after28 = "long,int";
   }
   
   /* AFTER29 ADVICE */
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after29="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after29="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after29(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after29="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after29(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after29="Object,SuperClass,long";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after29="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after29="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after29="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after29="OverloadedAdvicePOJO,long,int";
   }
   
   public void after29(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after29="Object,long,int";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after29="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after29(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after29="Object,long,Object[]";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after29="OverloadedAdvicePOJO,long";
   }
   
   public void after29(@Target Object target, @Return long returnedValue)
   {
      after29="Object,long";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after29="OverloadedAdvicePOJO,int";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after29="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after29(@Target OverloadedAdvicePOJO target)
   {
      after29="OverloadedAdvicePOJO";
   }
   
   public void after29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after29="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after29(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after29 = "SuperClass,long,int";
   }
   
   public void after29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after29 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after29(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after29 = "SuperClass,long,Object[]";
   }
   
   public void after29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after29 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after29(@Caller SuperClass caller, @Return long returnedValue)
   {
      after29 = "SuperClass,long";
   }
   
   public void after29(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after29 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after29(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after29 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after29(@Return long returnedValue, @Arg int arg)
   {
      after29 = "long,int";
   }
   
   /* AFTER30 ADVICE */
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Return long valueReturned)
   {
      after30="OverloadedAdvicePOJO,SuperClass,long";
   }
   
   public void after30(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after30="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after30(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after30="Object,SuperClass,long";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after30="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after30="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after30="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after30="OverloadedAdvicePOJO,long,int";
   }
   
   public void after30(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after30="Object,long,int";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after30="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after30(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after30="Object,long,Object[]";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after30="OverloadedAdvicePOJO,long";
   }
   
   public void after30(@Target Object target, @Return long returnedValue)
   {
      after30="Object,long";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after30="OverloadedAdvicePOJO,int";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after30="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after30(@Target OverloadedAdvicePOJO target)
   {
      after30="OverloadedAdvicePOJO";
   }
   
   public void after30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after30="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after30(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after30 = "SuperClass,long,int";
   }
   
   public void after30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after30 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after30(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after30 = "SuperClass,long,Object[]";
   }
   
   public void after30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after30 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after30(@Caller SuperClass caller, @Return long returnedValue)
   {
      after30 = "SuperClass,long";
   }
   
   public void after30(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after30 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after30(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after30 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after30(@Return long returnedValue, @Arg int arg)
   {
      after30 = "long,int";
   }
   
   /* AFTER31 ADVICE */
   
   public void after31(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned)
   {
      after31="Object,OverloadedAdvicePOJOCaller,long";
   }
   
   public void after31(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after31="Object,SuperClass,long";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after31="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after31="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after31="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after31="OverloadedAdvicePOJO,long,int";
   }
   
   public void after31(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after31="Object,long,int";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after31="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after31(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after31="Object,long,Object[]";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after31="OverloadedAdvicePOJO,long";
   }
   
   public void after31(@Target Object target, @Return long returnedValue)
   {
      after31="Object,long";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after31="OverloadedAdvicePOJO,int";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after31="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after31(@Target OverloadedAdvicePOJO target)
   {
      after31="OverloadedAdvicePOJO";
   }
   
   public void after31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after31="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after31(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after31 = "SuperClass,long,int";
   }
   
   public void after31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after31 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after31(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after31 = "SuperClass,long,Object[]";
   }
   
   public void after31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after31 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after31(@Caller SuperClass caller, @Return long returnedValue)
   {
      after31 = "SuperClass,long";
   }
   
   public void after31(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after31 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after31(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after31 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after31(@Return long returnedValue, @Arg int arg)
   {
      after31 = "long,int";
   }
   
   /* AFTER32 ADVICE */
   
   public void after32(@Target Object target, @Caller SuperClass caller,
         @Return long valueReturned)
   {
      after32="Object,SuperClass,long";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after32="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after32="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after32="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after32="OverloadedAdvicePOJO,long,int";
   }
   
   public void after32(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after32="Object,long,int";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after32="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after32(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after32="Object,long,Object[]";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after32="OverloadedAdvicePOJO,long";
   }
   
   public void after32(@Target Object target, @Return long returnedValue)
   {
      after32="Object,long";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after32="OverloadedAdvicePOJO,int";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after32="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after32(@Target OverloadedAdvicePOJO target)
   {
      after32="OverloadedAdvicePOJO";
   }
   
   public void after32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after32="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after32(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after32 = "SuperClass,long,int";
   }
   
   public void after32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after32 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after32(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after32 = "SuperClass,long,Object[]";
   }
   
   public void after32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after32 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after32(@Caller SuperClass caller, @Return long returnedValue)
   {
      after32 = "SuperClass,long";
   }
   
   public void after32(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after32 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after32(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after32 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after32(@Return long returnedValue, @Arg int arg)
   {
      after32 = "long,int";
   }
   
   /* AFTER33 ADVICE */
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after33="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      after33="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after33="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after33="OverloadedAdvicePOJO,long,int";
   }
   
   public void after33(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after33="Object,long,int";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after33="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after33(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after33="Object,long,Object[]";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after33="OverloadedAdvicePOJO,long";
   }
   
   public void after33(@Target Object target, @Return long returnedValue)
   {
      after33="Object,long";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after33="OverloadedAdvicePOJO,int";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after33="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after33(@Target OverloadedAdvicePOJO target)
   {
      after33="OverloadedAdvicePOJO";
   }
   
   public void after33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after33="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after33(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after33 = "SuperClass,long,int";
   }
   
   public void after33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after33 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after33(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after33 = "SuperClass,long,Object[]";
   }
   
   public void after33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after33 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after33(@Caller SuperClass caller, @Return long returnedValue)
   {
      after33 = "SuperClass,long";
   }
   
   public void after33(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after33 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after33(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after33 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after33(@Return long returnedValue, @Arg int arg)
   {
      after33 = "long,int";
   }
   
   /* AFTER34 ADVICE */
   
   public void after34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      after34="OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after34="OverloadedAdvicePOJO,long,int";
   }
   
   public void after34(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after34="Object,long,int";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after34="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after34(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after34="Object,long,Object[]";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after34="OverloadedAdvicePOJO,long";
   }
   
   public void after34(@Target Object target, @Return long returnedValue)
   {
      after34="Object,long";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after34="OverloadedAdvicePOJO,int";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after34="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after34(@Target OverloadedAdvicePOJO target)
   {
      after34="OverloadedAdvicePOJO";
   }
   
   public void after34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after34="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after34(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after34 = "SuperClass,long,int";
   }
   
   public void after34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after34 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after34(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after34 = "SuperClass,long,Object[]";
   }
   
   public void after34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after34 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after34(@Caller SuperClass caller, @Return long returnedValue)
   {
      after34 = "SuperClass,long";
   }
   
   public void after34(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after34 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after34(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after34 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after34(@Return long returnedValue, @Arg int arg)
   {
      after34 = "long,int";
   }
   
   /* AFTER35 ADVICE */
   
   public void after35(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Arg int arg)
   {
      after35="OverloadedAdvicePOJO,long,int";
   }
   
   public void after35(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after35="Object,long,int";
   }
   
   public void after35(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after35="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after35(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after35="Object,long,Object[]";
   }
   
   public void after35(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after35="OverloadedAdvicePOJO,long";
   }
   
   public void after35(@Target Object target, @Return long returnedValue)
   {
      after35="Object,long";
   }
   
   public void after35(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after35="OverloadedAdvicePOJO,int";
   }
   
   public void after35(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after35="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after35(@Target OverloadedAdvicePOJO target)
   {
      after35="OverloadedAdvicePOJO";
   }
   
   public void after35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after35="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after35(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after35 = "SuperClass,long,int";
   }
   
   public void after35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after35 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after35(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after35 = "SuperClass,long,Object[]";
   }
   
   public void after35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after35 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after35(@Caller SuperClass caller, @Return long returnedValue)
   {
      after35 = "SuperClass,long";
   }
   
   public void after35(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after35 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after35(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after35 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after35(@Return long returnedValue, @Arg int arg)
   {
      after35 = "long,int";
   }
   
   /* AFTER36 ADVICE */
   
   public void after36(@Target Object target, @Return long returnedValue,
         @Arg int arg)
   {
      after36="Object,long,int";
   }
   
   public void after36(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after36="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after36(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after36="Object,long,Object[]";
   }
   
   public void after36(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after36="OverloadedAdvicePOJO,long";
   }
   
   public void after36(@Target Object target, @Return long returnedValue)
   {
      after36="Object,long";
   }
   
   public void after36(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after36="OverloadedAdvicePOJO,int";
   }
   
   public void after36(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after36="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after36(@Target OverloadedAdvicePOJO target)
   {
      after36="OverloadedAdvicePOJO";
   }
   
   public void after36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after36="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after36(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after36 = "SuperClass,long,int";
   }
   
   public void after36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after36 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after36(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after36 = "SuperClass,long,Object[]";
   }
   
   public void after36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after36 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after36(@Caller SuperClass caller, @Return long returnedValue)
   {
      after36 = "SuperClass,long";
   }
   
   public void after36(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after36 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after36(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after36 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after36(@Return long returnedValue, @Arg int arg)
   {
      after36 = "long,int";
   }
   
   /* AFTER37 ADVICE */
   
   public void after37(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue, @Args Object[] args)
   {
      after37="OverloadedAdvicePOJO,long,Object[]";
   }
   
   public void after37(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after37="Object,long,Object[]";
   }
   
   public void after37(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after37="OverloadedAdvicePOJO,long";
   }
   
   public void after37(@Target Object target, @Return long returnedValue)
   {
      after37="Object,long";
   }
   
   public void after37(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after37="OverloadedAdvicePOJO,int";
   }
   
   public void after37(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after37="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after37(@Target OverloadedAdvicePOJO target)
   {
      after37="OverloadedAdvicePOJO";
   }
   
   public void after37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after37="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after37(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after37 = "SuperClass,long,int";
   }
   
   public void after37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after37 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after37(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after37 = "SuperClass,long,Object[]";
   }
   
   public void after37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after37 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after37(@Caller SuperClass caller, @Return long returnedValue)
   {
      after37 = "SuperClass,long";
   }
   
   public void after37(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after37 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after37(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after37 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after37(@Return long returnedValue, @Arg int arg)
   {
      after37 = "long,int";
   }
   
   /* AFTER38 ADVICE */
   
   public void after38(@Target Object target, @Return long returnedValue,
         @Args Object[] args)
   {
      after38="Object,long,Object[]";
   }
   
   public void after38(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after38="OverloadedAdvicePOJO,long";
   }
   
   public void after38(@Target Object target, @Return long returnedValue)
   {
      after38="Object,long";
   }
   
   public void after38(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after38="OverloadedAdvicePOJO,int";
   }
   
   public void after38(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after38="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after38(@Target OverloadedAdvicePOJO target)
   {
      after38="OverloadedAdvicePOJO";
   }
   
   public void after38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after38="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after38(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after38 = "SuperClass,long,int";
   }
   
   public void after38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after38 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after38(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after38 = "SuperClass,long,Object[]";
   }
   
   public void after38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after38 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after38(@Caller SuperClass caller, @Return long returnedValue)
   {
      after38 = "SuperClass,long";
   }
   
   public void after38(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after38 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after38(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after38 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after38(@Return long returnedValue, @Arg int arg)
   {
      after38 = "long,int";
   }
   
   /* AFTER39 ADVICE */
   
   public void after39(@Target OverloadedAdvicePOJO target,
         @Return long returnedValue)
   {
      after39="OverloadedAdvicePOJO,long";
   }
   
   public void after39(@Target Object target, @Return long returnedValue)
   {
      after39="Object,long";
   }
   
   public void after39(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after39="OverloadedAdvicePOJO,int";
   }
   
   public void after39(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after39="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after39(@Target OverloadedAdvicePOJO target)
   {
      after39="OverloadedAdvicePOJO";
   }
   
   public void after39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after39="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after39(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after39 = "SuperClass,long,int";
   }
   
   public void after39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after39 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after39(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after39 = "SuperClass,long,Object[]";
   }
   
   public void after39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after39 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after39(@Caller SuperClass caller, @Return long returnedValue)
   {
      after39 = "SuperClass,long";
   }
   
   public void after39(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after39 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after39(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after39 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after39(@Return long returnedValue, @Arg int arg)
   {
      after39 = "long,int";
   }
   
   /* AFTER40 ADVICE */
   
   public void after40(@Target Object target, @Return long returnedValue)
   {
      after40="Object,long";
   }
   
   public void after40(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after40="OverloadedAdvicePOJO,int";
   }
   
   public void after40(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after40="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after40(@Target OverloadedAdvicePOJO target)
   {
      after40="OverloadedAdvicePOJO";
   }
   
   public void after40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after40="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after40(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after40 = "SuperClass,long,int";
   }
   
   public void after40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after40 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after40(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after40 = "SuperClass,long,Object[]";
   }
   
   public void after40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after40 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after40(@Caller SuperClass caller, @Return long returnedValue)
   {
      after40 = "SuperClass,long";
   }
   
   public void after40(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after40 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after40(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after40 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after40(@Return long returnedValue, @Arg int arg)
   {
      after40 = "long,int";
   }
   
   /* AFTER41 ADVICE */
   
   public void after41(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      after41="OverloadedAdvicePOJO,int";
   }
   
   public void after41(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      after41="OverloadedAdvicePOJO,Object[]";
   }
   
   public void after41(@Target OverloadedAdvicePOJO target)
   {
      after41="OverloadedAdvicePOJO";
   }
   
   public void after41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after41="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after41(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after41 = "SuperClass,long,int";
   }
   
   public void after41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after41 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after41(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after41 = "SuperClass,long,Object[]";
   }
   
   public void after41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after41 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after41(@Caller SuperClass caller, @Return long returnedValue)
   {
      after41 = "SuperClass,long";
   }
   
   public void after41(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after41 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after41(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after41 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after41(@Return long returnedValue, @Arg int arg)
   {
      after41 = "long,int";
   }
   
   /* AFTER42 ADVICE */
   
   public void after42(@Target OverloadedAdvicePOJO target)
   {
      after42="OverloadedAdvicePOJO";
   }
   
   public void after42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after42="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after42(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after42 = "SuperClass,long,int";
   }
   
   public void after42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after42 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after42(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after42 = "SuperClass,long,Object[]";
   }
   
   public void after42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after42 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after42(@Caller SuperClass caller, @Return long returnedValue)
   {
      after42 = "SuperClass,long";
   }
   
   public void after42(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after42 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after42(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after42 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after42(@Return long returnedValue, @Arg int arg)
   {
      after42 = "long,int";
   }
   
   /* AFTER43 ADVICE */
   
   public void after43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Arg int arg)
   {
      after43="OverloadedAdvicePOJOCaller,long,int";
   }
   
   public void after43(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after43 = "SuperClass,long,int";
   }
   
   public void after43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after43 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after43(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after43 = "SuperClass,long,Object[]";
   }
   
   public void after43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after43 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after43(@Caller SuperClass caller, @Return long returnedValue)
   {
      after43 = "SuperClass,long";
   }
   
   public void after43(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after43 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after43(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after43 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after43(@Return long returnedValue, @Arg int arg)
   {
      after43 = "long,int";
   }
   
   /* AFTER44 ADVICE */
   
   public void after44(@Caller SuperClass caller, @Return long returnedValue,
         @Arg int arg)
   {
      after44 = "SuperClass,long,int";
   }
   
   public void after44(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after44 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after44(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after44 = "SuperClass,long,Object[]";
   }
   
   public void after44(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after44 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after44(@Caller SuperClass caller, @Return long returnedValue)
   {
      after44 = "SuperClass,long";
   }
   
   public void after44(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after44 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after44(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after44 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after44(@Return long returnedValue, @Arg int arg)
   {
      after44 = "long,int";
   }
   
   /* AFTER45 ADVICE */
   
   public void after45(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue, @Args Object[] args)
   {
      after45 = "OverloadedAdvicePOJOCaller,long,Object[]";
   }
   
   public void after45(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after45 = "SuperClass,long,Object[]";
   }
   
   public void after45(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after45 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after45(@Caller SuperClass caller, @Return long returnedValue)
   {
      after45 = "SuperClass,long";
   }
   
   public void after45(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after45 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after45(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after45 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after45(@Return long returnedValue, @Arg int arg)
   {
      after45 = "long,int";
   }
   
   /* AFTER46 ADVICE */
   
   public void after46(@Caller SuperClass caller, @Return long returnedValue,
         @Args Object[] args)
   {
      after46 = "SuperClass,long,Object[]";
   }
   
   public void after46(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after46 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after46(@Caller SuperClass caller, @Return long returnedValue)
   {
      after46 = "SuperClass,long";
   }
   
   public void after46(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after46 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after46(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after46 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after46(@Return long returnedValue, @Arg int arg)
   {
      after46 = "long,int";
   }
   
   /* AFTER47 ADVICE */
   
   public void after47(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long returnedValue)
   {
      after47 = "OverloadedAdvicePOJOCaller,long";
   }
   
   public void after47(@Caller SuperClass caller, @Return long returnedValue)
   {
      after47 = "SuperClass,long";
   }
   
   public void after47(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after47 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after47(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after47 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after47(@Return long returnedValue, @Arg int arg)
   {
      after47 = "long,int";
   }
   
   /* AFTER48 ADVICE */
   
   public void after48(@Caller SuperClass caller, @Return long returnedValue)
   {
      after48 = "SuperClass,long";
   }
   
   public void after48(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after48 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after48(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after48 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after48(@Return long returnedValue, @Arg int arg)
   {
      after48 = "long,int";
   }
   
   /* AFTER49 ADVICE */
   
   public void after49(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      after49 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void after49(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after49 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after49(@Return long returnedValue, @Arg int arg)
   {
      after49 = "long,int";
   }
   
   /* AFTER50 ADVICE */
   
   public void after50(@Caller OverloadedAdvicePOJOCaller caller)
   {
      after50 = "OverloadedAdvicePOJOCaller";
   }
   
   public void after50(@Return long returnedValue, @Arg int arg)
   {
      after50 = "long,int";
   }
}