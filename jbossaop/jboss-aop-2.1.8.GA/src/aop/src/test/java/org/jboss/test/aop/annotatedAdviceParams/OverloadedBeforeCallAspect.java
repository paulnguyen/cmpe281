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
import org.jboss.aop.advice.annotation.Target;
import org.jboss.aop.joinpoint.MethodCallByMethod;

/**
 * Aspect used on overloaded before advice tests (for Call and Target tests
 * only).
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedBeforeCallAspect
{
   static String before1 = null;
   static String before2 = null;
   static String before3 = null;
   static String before4 = null;
   static String before5 = null;
   static String before6 = null;
   static String before7 = null;
   static String before8 = null;
   static String before9 = null;
   static String before10 = null;
   static String before11 = null;
   static String before12 = null;
   static String before13 = null;
   static String before14 = null;
   static String before15 = null;
   static String before16 = null;
   static String before17 = null;
   static String before18 = null;
   static String before19 = null;
   static String before20 = null;
   static String before21 = null;
   static String before22 = null;
   static String before23 = null;
   static String before24 = null;
   static String before25 = null;
   static String before26 = null;
   static String before27 = null;
   static String before28 = null;
   static String before29 = null;
   static String before30 = null;
   static String before31 = null;
   static String before32 = null;
   static String before33 = null;
   static String before34 = null;
   static String before35 = null;
   static String before36 = null;
   static String before37 = null;
   static String before38 = null;
   static String before39 = null;
   static String before40 = null;
   static String before41 = null;
   static String before42 = null;
   static String before43 = null;
   static String before44 = null;
   
   public static void clear()
   {
      before1 = null;
      before2 = null;
      before3 = null;
      before4 = null;
      before5 = null;
      before6 = null;
      before7 = null;
      before8 = null;
      before9 = null;
      before10 = null;
      before11 = null;
      before12 = null;
      before13 = null;
      before14 = null;
      before15 = null;
      before16 = null;
      before17 = null;
      before18 = null;
      before19 = null;
      before20 = null;
      before21 = null;
      before22 = null;
      before23 = null;
      before24 = null;
      before25 = null;
      before26 = null;
      before27 = null;
      before28 = null;
      before29 = null;
      before30 = null;
      before31 = null;
      before32 = null;
      before33 = null;
      before34 = null;
      before35 = null;
      before36 = null;
      before37 = null;
      before38 = null;
      before39 = null;
      before40 = null;
      before41 = null;
      before42 = null;
      before43 = null;
      before44 = null;
   }
   
   /* BEFORE1 ADVICE */
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg)
   {
      before1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before1 = "MethodCallByMethod,Object,SuperClass,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before1 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before1 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before1 = "MethodCallByMethod,Object,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before1 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before1 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before1 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before1 = "MethodCallByMethod,int";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before1 = "MethodCallByMethod,Object[]";
   }
   
   public void before1(@JoinPoint MethodCallByMethod joinPoint)
   {
      before1= "MethodCallByMethod";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before1 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before1 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before1 = "Object,SuperClass,int";
   }
 
   public void before1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before1 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before1 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before1 = "Object,SuperClass,Object[]";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before1 = "OverloadedAdvicePOJO,int";
   }
   
   public void before1(@Target Object target, @Arg int arg)
   {
      before1 = "Object,int";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before1 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before1(@Target Object target, @Args Object[] args)
   {
      before1 = "Object,Object[]";
   }
   
   public void before1(@Target OverloadedAdvicePOJO target)
   {
      before1 = "OverloadedAdvicePOJO";
   }
   
   public void before1(@Target Object target)
   {
      before1 = "Object";
   }
   
   public void before1(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before1 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before1(@Caller SuperClass caller, @Arg int arg)
   {
      before1 = "SuperClass,int";
   }
   
   public void before1(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before1 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before1(@Caller SuperClass caller, @Args Object[] args)
   {
      before1 = "SuperClass,Object[]";
   }
   
   public void before1(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before1 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before1(@Caller SuperClass caller)
   {
      before1 = "SuperClass";
   }
   
   public void before1(@Arg int arg)
   {
      before1 = "int";
   }
   
   public void before1(@Args Object[] args)
   {
      before1 = "Object[]";
   }
   
   /* BEFORE2 ADVICE */
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg)
   {
      before2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before2 = "MethodCallByMethod,Object,SuperClass,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before2 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before2 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before2 = "MethodCallByMethod,Object,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before2 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before2 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before2 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before2 = "MethodCallByMethod,int";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before2 = "MethodCallByMethod,Object[]";
   }
   
   public void before2(@JoinPoint MethodCallByMethod joinPoint)
   {
      before2= "MethodCallByMethod";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before2 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before2 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before2(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before2 = "Object,SuperClass,int";
   }
 
   public void before2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before2 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before2 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before2 = "Object,SuperClass,Object[]";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before2 = "OverloadedAdvicePOJO,int";
   }
   
   public void before2(@Target Object target, @Arg int arg)
   {
      before2 = "Object,int";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before2 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before2(@Target Object target, @Args Object[] args)
   {
      before2 = "Object,Object[]";
   }
   
   public void before2(@Target OverloadedAdvicePOJO target)
   {
      before2 = "OverloadedAdvicePOJO";
   }
   
   public void before2(@Target Object target)
   {
      before2 = "Object";
   }
   
   public void before2(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before2 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before2(@Caller SuperClass caller, @Arg int arg)
   {
      before2 = "SuperClass,int";
   }
   
   public void before2(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before2 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before2(@Caller SuperClass caller, @Args Object[] args)
   {
      before2 = "SuperClass,Object[]";
   }
   
   public void before2(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before2 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before2(@Caller SuperClass caller)
   {
      before2 = "SuperClass";
   }
   
   public void before2(@Arg int arg)
   {
      before2 = "int";
   }
   
   public void before2(@Args Object[] args)
   {
      before2 = "Object[]";
   }
   
   /* BEFORE3 ADVICE */
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg)
   {
      before3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before3 = "MethodCallByMethod,Object,SuperClass,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before3 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before3 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before3 = "MethodCallByMethod,Object,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before3 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before3 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before3 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before3 = "MethodCallByMethod,int";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before3 = "MethodCallByMethod,Object[]";
   }
   
   public void before3(@JoinPoint MethodCallByMethod joinPoint)
   {
      before3= "MethodCallByMethod";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before3 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before3 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before3(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before3 = "Object,SuperClass,int";
   }
 
   public void before3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before3 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before3 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before3 = "Object,SuperClass,Object[]";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before3 = "OverloadedAdvicePOJO,int";
   }
   
   public void before3(@Target Object target, @Arg int arg)
   {
      before3 = "Object,int";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before3 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before3(@Target Object target, @Args Object[] args)
   {
      before3 = "Object,Object[]";
   }
   
   public void before3(@Target OverloadedAdvicePOJO target)
   {
      before3 = "OverloadedAdvicePOJO";
   }
   
   public void before3(@Target Object target)
   {
      before3 = "Object";
   }
   
   public void before3(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before3 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before3(@Caller SuperClass caller, @Arg int arg)
   {
      before3 = "SuperClass,int";
   }
   
   public void before3(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before3 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before3(@Caller SuperClass caller, @Args Object[] args)
   {
      before3 = "SuperClass,Object[]";
   }
   
   public void before3(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before3 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before3(@Caller SuperClass caller)
   {
      before3 = "SuperClass";
   }
   
   public void before3(@Arg int arg)
   {
      before3 = "int";
   }
   
   public void before3(@Args Object[] args)
   {
      before3 = "Object[]";
   }
   
   /* BEFORE4 ADVICE */
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before4 = "MethodCallByMethod,Object,SuperClass,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before4 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before4 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before4 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before4 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before4 = "MethodCallByMethod,Object,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before4 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before4 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before4 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before4 = "MethodCallByMethod,int";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before4 = "MethodCallByMethod,Object[]";
   }
   
   public void before4(@JoinPoint MethodCallByMethod joinPoint)
   {
      before4= "MethodCallByMethod";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before4 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before4 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before4(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before4 = "Object,SuperClass,int";
   }
 
   public void before4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before4 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before4 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before4 = "Object,SuperClass,Object[]";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before4 = "OverloadedAdvicePOJO,int";
   }
   
   public void before4(@Target Object target, @Arg int arg)
   {
      before4 = "Object,int";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before4 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before4(@Target Object target, @Args Object[] args)
   {
      before4 = "Object,Object[]";
   }
   
   public void before4(@Target OverloadedAdvicePOJO target)
   {
      before4 = "OverloadedAdvicePOJO";
   }
   
   public void before4(@Target Object target)
   {
      before4 = "Object";
   }
   
   public void before4(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before4 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before4(@Caller SuperClass caller, @Arg int arg)
   {
      before4 = "SuperClass,int";
   }
   
   public void before4(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before4 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before4(@Caller SuperClass caller, @Args Object[] args)
   {
      before4 = "SuperClass,Object[]";
   }
   
   public void before4(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before4 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before4(@Caller SuperClass caller)
   {
      before4 = "SuperClass";
   }
   
   public void before4(@Arg int arg)
   {
      before4 = "int";
   }
   
   public void before4(@Args Object[] args)
   {
      before4 = "Object[]";
   }
   
   /* BEFORE5 ADVICE */
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before5 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before5 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before5 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before5 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before5 = "MethodCallByMethod,Object,int";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before5 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before5 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before5 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before5 = "MethodCallByMethod,int";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before5 = "MethodCallByMethod,Object[]";
   }
   
   public void before5(@JoinPoint MethodCallByMethod joinPoint)
   {
      before5= "MethodCallByMethod";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before5 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before5 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before5(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before5 = "Object,SuperClass,int";
   }
 
   public void before5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before5 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before5 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before5 = "Object,SuperClass,Object[]";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before5 = "OverloadedAdvicePOJO,int";
   }
   
   public void before5(@Target Object target, @Arg int arg)
   {
      before5 = "Object,int";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before5 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before5(@Target Object target, @Args Object[] args)
   {
      before5 = "Object,Object[]";
   }
   
   public void before5(@Target OverloadedAdvicePOJO target)
   {
      before5 = "OverloadedAdvicePOJO";
   }
   
   public void before5(@Target Object target)
   {
      before5 = "Object";
   }
   
   public void before5(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before5 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before5(@Caller SuperClass caller, @Arg int arg)
   {
      before5 = "SuperClass,int";
   }
   
   public void before5(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before5 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before5(@Caller SuperClass caller, @Args Object[] args)
   {
      before5 = "SuperClass,Object[]";
   }
   
   public void before5(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before5 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before5(@Caller SuperClass caller)
   {
      before5 = "SuperClass";
   }
   
   public void before5(@Arg int arg)
   {
      before5 = "int";
   }
   
   public void before5(@Args Object[] args)
   {
      before5 = "Object[]";
   }
   
   /* BEFORE6 ADVICE */
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before6 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before6 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before6 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before6 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before6 = "MethodCallByMethod,Object,int";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before6 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before6 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before6 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before6 = "MethodCallByMethod,int";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before6 = "MethodCallByMethod,Object[]";
   }
   
   public void before6(@JoinPoint MethodCallByMethod joinPoint)
   {
      before6= "MethodCallByMethod";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before6 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before6 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before6(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before6 = "Object,SuperClass,int";
   }
 
   public void before6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before6 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before6 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before6(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before6 = "Object,SuperClass,Object[]";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before6 = "OverloadedAdvicePOJO,int";
   }
   
   public void before6(@Target Object target, @Arg int arg)
   {
      before6 = "Object,int";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before6 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before6(@Target Object target, @Args Object[] args)
   {
      before6 = "Object,Object[]";
   }
   
   public void before6(@Target OverloadedAdvicePOJO target)
   {
      before6 = "OverloadedAdvicePOJO";
   }
   
   public void before6(@Target Object target)
   {
      before6 = "Object";
   }
   
   public void before6(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before6 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before6(@Caller SuperClass caller, @Arg int arg)
   {
      before6 = "SuperClass,int";
   }
   
   public void before6(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before6 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before6(@Caller SuperClass caller, @Args Object[] args)
   {
      before6 = "SuperClass,Object[]";
   }
   
   public void before6(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before6 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before6(@Caller SuperClass caller)
   {
      before6 = "SuperClass";
   }
   
   public void before6(@Arg int arg)
   {
      before6 = "int";
   }
   
   public void before6(@Args Object[] args)
   {
      before6 = "Object[]";
   }
   
   /* BEFORE7 ADVICE */
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before7 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before7 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before7 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before7 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before7 = "MethodCallByMethod,Object,int";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before7 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before7 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before7 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before7 = "MethodCallByMethod,int";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before7 = "MethodCallByMethod,Object[]";
   }
   
   public void before7(@JoinPoint MethodCallByMethod joinPoint)
   {
      before7= "MethodCallByMethod";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before7 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before7 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before7(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before7 = "Object,SuperClass,int";
   }
 
   public void before7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before7 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before7 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before7(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before7 = "Object,SuperClass,Object[]";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before7 = "OverloadedAdvicePOJO,int";
   }
   
   public void before7(@Target Object target, @Arg int arg)
   {
      before7 = "Object,int";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before7 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before7(@Target Object target, @Args Object[] args)
   {
      before7 = "Object,Object[]";
   }
   
   public void before7(@Target OverloadedAdvicePOJO target)
   {
      before7 = "OverloadedAdvicePOJO";
   }
   
   public void before7(@Target Object target)
   {
      before7 = "Object";
   }
   
   public void before7(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before7 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before7(@Caller SuperClass caller, @Arg int arg)
   {
      before7 = "SuperClass,int";
   }
   
   public void before7(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before7 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before7(@Caller SuperClass caller, @Args Object[] args)
   {
      before7 = "SuperClass,Object[]";
   }
   
   public void before7(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before7 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before7(@Caller SuperClass caller)
   {
      before7 = "SuperClass";
   }
   
   public void before7(@Arg int arg)
   {
      before7 = "int";
   }
   
   public void before7(@Args Object[] args)
   {
      before7 = "Object[]";
   }
   
   /* BEFORE8 ADVICE */
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before8 = "MethodCallByMethod,Object,SuperClass,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before8 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before8 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before8 = "MethodCallByMethod,Object,int";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before8 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before8 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before8 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before8 = "MethodCallByMethod,int";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before8 = "MethodCallByMethod,Object[]";
   }
   
   public void before8(@JoinPoint MethodCallByMethod joinPoint)
   {
      before8= "MethodCallByMethod";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before8 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before8 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before8(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before8 = "Object,SuperClass,int";
   }
 
   public void before8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before8 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before8 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before8(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before8 = "Object,SuperClass,Object[]";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before8 = "OverloadedAdvicePOJO,int";
   }
   
   public void before8(@Target Object target, @Arg int arg)
   {
      before8 = "Object,int";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before8 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before8(@Target Object target, @Args Object[] args)
   {
      before8 = "Object,Object[]";
   }
   
   public void before8(@Target OverloadedAdvicePOJO target)
   {
      before8 = "OverloadedAdvicePOJO";
   }
   
   public void before8(@Target Object target)
   {
      before8 = "Object";
   }
   
   public void before8(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before8 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before8(@Caller SuperClass caller, @Arg int arg)
   {
      before8 = "SuperClass,int";
   }
   
   public void before8(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before8 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before8(@Caller SuperClass caller, @Args Object[] args)
   {
      before8 = "SuperClass,Object[]";
   }
   
   public void before8(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before8 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before8(@Caller SuperClass caller)
   {
      before8 = "SuperClass";
   }
   
   public void before8(@Arg int arg)
   {
      before8 = "int";
   }
   
   public void before8(@Args Object[] args)
   {
      before8 = "Object[]";
   }
   
   /* BEFORE9 ADVICE */
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before9 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before9 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before9 = "MethodCallByMethod,Object,int";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before9 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before9 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before9 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before9 = "MethodCallByMethod,int";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before9 = "MethodCallByMethod,Object[]";
   }
   
   public void before9(@JoinPoint MethodCallByMethod joinPoint)
   {
      before9= "MethodCallByMethod";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before9 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before9 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before9(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before9 = "Object,SuperClass,int";
   }
 
   public void before9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before9 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before9 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before9(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before9 = "Object,SuperClass,Object[]";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before9 = "OverloadedAdvicePOJO,int";
   }
   
   public void before9(@Target Object target, @Arg int arg)
   {
      before9 = "Object,int";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before9 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before9(@Target Object target, @Args Object[] args)
   {
      before9 = "Object,Object[]";
   }
   
   public void before9(@Target OverloadedAdvicePOJO target)
   {
      before9 = "OverloadedAdvicePOJO";
   }
   
   public void before9(@Target Object target)
   {
      before9 = "Object";
   }
   
   public void before9(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before9 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before9(@Caller SuperClass caller, @Arg int arg)
   {
      before9 = "SuperClass,int";
   }
   
   public void before9(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before9 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before9(@Caller SuperClass caller, @Args Object[] args)
   {
      before9 = "SuperClass,Object[]";
   }
   
   public void before9(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before9 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before9(@Caller SuperClass caller)
   {
      before9 = "SuperClass";
   }
   
   public void before9(@Arg int arg)
   {
      before9 = "int";
   }
   
   public void before9(@Args Object[] args)
   {
      before9 = "Object[]";
   }
   
   /* BEFORE10 ADVICE */
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
   {
      before10 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before10 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before10 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before10 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before10 = "MethodCallByMethod,Object,int";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before10 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before10 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before10 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before10 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before10 = "MethodCallByMethod,int";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before10 = "MethodCallByMethod,Object[]";
   }
   
   public void before10(@JoinPoint MethodCallByMethod joinPoint)
   {
      before10= "MethodCallByMethod";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before10 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before10 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before10(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before10 = "Object,SuperClass,int";
   }
 
   public void before10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before10 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before10 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before10(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before10 = "Object,SuperClass,Object[]";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before10 = "OverloadedAdvicePOJO,int";
   }
   
   public void before10(@Target Object target, @Arg int arg)
   {
      before10 = "Object,int";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before10 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before10(@Target Object target, @Args Object[] args)
   {
      before10 = "Object,Object[]";
   }
   
   public void before10(@Target OverloadedAdvicePOJO target)
   {
      before10 = "OverloadedAdvicePOJO";
   }
   
   public void before10(@Target Object target)
   {
      before10 = "Object";
   }
   
   public void before10(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before10 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before10(@Caller SuperClass caller, @Arg int arg)
   {
      before10 = "SuperClass,int";
   }
   
   public void before10(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before10 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before10(@Caller SuperClass caller, @Args Object[] args)
   {
      before10 = "SuperClass,Object[]";
   }
   
   public void before10(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before10 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before10(@Caller SuperClass caller)
   {
      before10 = "SuperClass";
   }
   
   public void before10(@Arg int arg)
   {
      before10 = "int";
   }
   
   public void before10(@Args Object[] args)
   {
      before10 = "Object[]";
   }
   
   /* BEFORE11 ADVICE */
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
   {
      before11 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before11 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before11 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before11 = "MethodCallByMethod,Object,int";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before11 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before11 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before11 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before11 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before11 = "MethodCallByMethod,int";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before11 = "MethodCallByMethod,Object[]";
   }
   
   public void before11(@JoinPoint MethodCallByMethod joinPoint)
   {
      before11= "MethodCallByMethod";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before11 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before11 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before11(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before11 = "Object,SuperClass,int";
   }
 
   public void before11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before11 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before11 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before11(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before11 = "Object,SuperClass,Object[]";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before11 = "OverloadedAdvicePOJO,int";
   }
   
   public void before11(@Target Object target, @Arg int arg)
   {
      before11 = "Object,int";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before11 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before11(@Target Object target, @Args Object[] args)
   {
      before11 = "Object,Object[]";
   }
   
   public void before11(@Target OverloadedAdvicePOJO target)
   {
      before11 = "OverloadedAdvicePOJO";
   }
   
   public void before11(@Target Object target)
   {
      before11 = "Object";
   }
   
   public void before11(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before11 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before11(@Caller SuperClass caller, @Arg int arg)
   {
      before11 = "SuperClass,int";
   }
   
   public void before11(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before11 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before11(@Caller SuperClass caller, @Args Object[] args)
   {
      before11 = "SuperClass,Object[]";
   }
   
   public void before11(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before11 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before11(@Caller SuperClass caller)
   {
      before11 = "SuperClass";
   }
   
   public void before11(@Arg int arg)
   {
      before11 = "int";
   }
   
   public void before11(@Args Object[] args)
   {
      before11 = "Object[]";
   }
   
   /* BEFORE12 ADVICE */
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller SuperClass caller)
   {
      before12 = "MethodCallByMethod,Object,SuperClass";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before12 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before12 = "MethodCallByMethod,Object,int";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before12 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before12 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before12 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before12 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before12 = "MethodCallByMethod,int";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before12 = "MethodCallByMethod,Object[]";
   }
   
   public void before12(@JoinPoint MethodCallByMethod joinPoint)
   {
      before12= "MethodCallByMethod";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before12 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before12 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before12(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before12 = "Object,SuperClass,int";
   }
 
   public void before12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before12 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before12 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before12(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before12 = "Object,SuperClass,Object[]";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before12 = "OverloadedAdvicePOJO,int";
   }
   
   public void before12(@Target Object target, @Arg int arg)
   {
      before12 = "Object,int";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before12 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before12(@Target Object target, @Args Object[] args)
   {
      before12 = "Object,Object[]";
   }
   
   public void before12(@Target OverloadedAdvicePOJO target)
   {
      before12 = "OverloadedAdvicePOJO";
   }
   
   public void before12(@Target Object target)
   {
      before12 = "Object";
   }
   
   public void before12(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before12 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before12(@Caller SuperClass caller, @Arg int arg)
   {
      before12 = "SuperClass,int";
   }
   
   public void before12(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before12 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before12(@Caller SuperClass caller, @Args Object[] args)
   {
      before12 = "SuperClass,Object[]";
   }
   
   public void before12(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before12 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before12(@Caller SuperClass caller)
   {
      before12 = "SuperClass";
   }
   
   public void before12(@Arg int arg)
   {
      before12 = "int";
   }
   
   public void before12(@Args Object[] args)
   {
      before12 = "Object[]";
   }
   
   /* BEFORE13 ADVICE */
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before13 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before13 = "MethodCallByMethod,Object,int";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before13 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before13 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before13 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before13 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before13 = "MethodCallByMethod,int";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before13 = "MethodCallByMethod,Object[]";
   }
   
   public void before13(@JoinPoint MethodCallByMethod joinPoint)
   {
      before13= "MethodCallByMethod";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before13 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before13 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before13(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before13 = "Object,SuperClass,int";
   }
 
   public void before13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before13 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before13 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before13(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before13 = "Object,SuperClass,Object[]";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before13 = "OverloadedAdvicePOJO,int";
   }
   
   public void before13(@Target Object target, @Arg int arg)
   {
      before13 = "Object,int";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before13 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before13(@Target Object target, @Args Object[] args)
   {
      before13 = "Object,Object[]";
   }
   
   public void before13(@Target OverloadedAdvicePOJO target)
   {
      before13 = "OverloadedAdvicePOJO";
   }
   
   public void before13(@Target Object target)
   {
      before13 = "Object";
   }
   
   public void before13(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before13 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before13(@Caller SuperClass caller, @Arg int arg)
   {
      before13 = "SuperClass,int";
   }
   
   public void before13(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before13 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before13(@Caller SuperClass caller, @Args Object[] args)
   {
      before13 = "SuperClass,Object[]";
   }
   
   public void before13(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before13 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before13(@Caller SuperClass caller)
   {
      before13 = "SuperClass";
   }
   
   public void before13(@Arg int arg)
   {
      before13 = "int";
   }
   
   public void before13(@Args Object[] args)
   {
      before13 = "Object[]";
   }
   
   /* BEFORE14 ADVICE */
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Arg int arg)
   {
      before14 = "MethodCallByMethod,Object,int";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before14 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before14 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before14 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before14 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before14 = "MethodCallByMethod,int";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before14 = "MethodCallByMethod,Object[]";
   }
   
   public void before14(@JoinPoint MethodCallByMethod joinPoint)
   {
      before14= "MethodCallByMethod";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before14 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before14 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before14(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before14 = "Object,SuperClass,int";
   }
 
   public void before14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before14 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before14 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before14(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before14 = "Object,SuperClass,Object[]";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before14 = "OverloadedAdvicePOJO,int";
   }
   
   public void before14(@Target Object target, @Arg int arg)
   {
      before14 = "Object,int";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before14 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before14(@Target Object target, @Args Object[] args)
   {
      before14 = "Object,Object[]";
   }
   
   public void before14(@Target OverloadedAdvicePOJO target)
   {
      before14 = "OverloadedAdvicePOJO";
   }
   
   public void before14(@Target Object target)
   {
      before14 = "Object";
   }
   
   public void before14(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before14 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before14(@Caller SuperClass caller, @Arg int arg)
   {
      before14 = "SuperClass,int";
   }
   
   public void before14(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before14 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before14(@Caller SuperClass caller, @Args Object[] args)
   {
      before14 = "SuperClass,Object[]";
   }
   
   public void before14(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before14 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before14(@Caller SuperClass caller)
   {
      before14 = "SuperClass";
   }
   
   public void before14(@Arg int arg)
   {
      before14 = "int";
   }
   
   public void before14(@Args Object[] args)
   {
      before14 = "Object[]";
   }
   
   /* BEFORE15 ADVICE */
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before15 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before15 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before15 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before15 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before15 = "MethodCallByMethod,int";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before15 = "MethodCallByMethod,Object[]";
   }
   
   public void before15(@JoinPoint MethodCallByMethod joinPoint)
   {
      before15= "MethodCallByMethod";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before15 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before15 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before15(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before15 = "Object,SuperClass,int";
   }
 
   public void before15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before15 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before15 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before15(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before15 = "Object,SuperClass,Object[]";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before15 = "OverloadedAdvicePOJO,int";
   }
   
   public void before15(@Target Object target, @Arg int arg)
   {
      before15 = "Object,int";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before15 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before15(@Target Object target, @Args Object[] args)
   {
      before15 = "Object,Object[]";
   }
   
   public void before15(@Target OverloadedAdvicePOJO target)
   {
      before15 = "OverloadedAdvicePOJO";
   }
   
   public void before15(@Target Object target)
   {
      before15 = "Object";
   }
   
   public void before15(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before15 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before15(@Caller SuperClass caller, @Arg int arg)
   {
      before15 = "SuperClass,int";
   }
   
   public void before15(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before15 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before15(@Caller SuperClass caller, @Args Object[] args)
   {
      before15 = "SuperClass,Object[]";
   }
   
   public void before15(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before15 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before15(@Caller SuperClass caller)
   {
      before15 = "SuperClass";
   }
   
   public void before15(@Arg int arg)
   {
      before15 = "int";
   }
   
   public void before15(@Args Object[] args)
   {
      before15 = "Object[]";
   }
   
   /* BEFORE16 ADVICE */
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Args Object[] args)
   {
      before16 = "MethodCallByMethod,Object,Object[]";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before16 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before16 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before16 = "MethodCallByMethod,int";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before16 = "MethodCallByMethod,Object[]";
   }
   
   public void before16(@JoinPoint MethodCallByMethod joinPoint)
   {
      before16= "MethodCallByMethod";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before16 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before16 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before16(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before16 = "Object,SuperClass,int";
   }
 
   public void before16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before16 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before16 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before16(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before16 = "Object,SuperClass,Object[]";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before16 = "OverloadedAdvicePOJO,int";
   }
   
   public void before16(@Target Object target, @Arg int arg)
   {
      before16 = "Object,int";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before16 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before16(@Target Object target, @Args Object[] args)
   {
      before16 = "Object,Object[]";
   }
   
   public void before16(@Target OverloadedAdvicePOJO target)
   {
      before16 = "OverloadedAdvicePOJO";
   }
   
   public void before16(@Target Object target)
   {
      before16 = "Object";
   }
   
   public void before16(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before16 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before16(@Caller SuperClass caller, @Arg int arg)
   {
      before16 = "SuperClass,int";
   }
   
   public void before16(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before16 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before16(@Caller SuperClass caller, @Args Object[] args)
   {
      before16 = "SuperClass,Object[]";
   }
   
   public void before16(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before16 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before16(@Caller SuperClass caller)
   {
      before16 = "SuperClass";
   }
   
   public void before16(@Arg int arg)
   {
      before16 = "int";
   }
   
   public void before16(@Args Object[] args)
   {
      before16 = "Object[]";
   }
   
   /* BEFORE17 ADVICE */
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before17 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before17 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before17 = "MethodCallByMethod,int";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before17 = "MethodCallByMethod,Object[]";
   }
   
   public void before17(@JoinPoint MethodCallByMethod joinPoint)
   {
      before17= "MethodCallByMethod";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before17 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before17 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before17(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before17 = "Object,SuperClass,int";
   }
 
   public void before17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before17 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before17 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before17(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before17 = "Object,SuperClass,Object[]";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before17 = "OverloadedAdvicePOJO,int";
   }
   
   public void before17(@Target Object target, @Arg int arg)
   {
      before17 = "Object,int";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before17 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before17(@Target Object target, @Args Object[] args)
   {
      before17 = "Object,Object[]";
   }
   
   public void before17(@Target OverloadedAdvicePOJO target)
   {
      before17 = "OverloadedAdvicePOJO";
   }
   
   public void before17(@Target Object target)
   {
      before17 = "Object";
   }
   
   public void before17(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before17 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before17(@Caller SuperClass caller, @Arg int arg)
   {
      before17 = "SuperClass,int";
   }
   
   public void before17(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before17 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before17(@Caller SuperClass caller, @Args Object[] args)
   {
      before17 = "SuperClass,Object[]";
   }
   
   public void before17(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before17 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before17(@Caller SuperClass caller)
   {
      before17 = "SuperClass";
   }
   
   public void before17(@Arg int arg)
   {
      before17 = "int";
   }
   
   public void before17(@Args Object[] args)
   {
      before17 = "Object[]";
   }
   
   /* BEFORE18 ADVICE */
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Arg int arg)
   {
      before18 = "MethodCallByMethod,SuperClass,int";
   }
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before18 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before18 = "MethodCallByMethod,int";
   }
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before18 = "MethodCallByMethod,Object[]";
   }
   
   public void before18(@JoinPoint MethodCallByMethod joinPoint)
   {
      before18= "MethodCallByMethod";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before18 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before18 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before18(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before18 = "Object,SuperClass,int";
   }
 
   public void before18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before18 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before18 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before18(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before18 = "Object,SuperClass,Object[]";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before18 = "OverloadedAdvicePOJO,int";
   }
   
   public void before18(@Target Object target, @Arg int arg)
   {
      before18 = "Object,int";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before18 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before18(@Target Object target, @Args Object[] args)
   {
      before18 = "Object,Object[]";
   }
   
   public void before18(@Target OverloadedAdvicePOJO target)
   {
      before18 = "OverloadedAdvicePOJO";
   }
   
   public void before18(@Target Object target)
   {
      before18 = "Object";
   }
   
   public void before18(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before18 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before18(@Caller SuperClass caller, @Arg int arg)
   {
      before18 = "SuperClass,int";
   }
   
   public void before18(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before18 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before18(@Caller SuperClass caller, @Args Object[] args)
   {
      before18 = "SuperClass,Object[]";
   }
   
   public void before18(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before18 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before18(@Caller SuperClass caller)
   {
      before18 = "SuperClass";
   }
   
   public void before18(@Arg int arg)
   {
      before18 = "int";
   }
   
   public void before18(@Args Object[] args)
   {
      before18 = "Object[]";
   }
   
   /* BEFORE19 ADVICE */
   
   public void before19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before19 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before19(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before19 = "MethodCallByMethod,int";
   }
   
   public void before19(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before19 = "MethodCallByMethod,Object[]";
   }
   
   public void before19(@JoinPoint MethodCallByMethod joinPoint)
   {
      before19= "MethodCallByMethod";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before19 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before19 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before19(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before19 = "Object,SuperClass,int";
   }
 
   public void before19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before19 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before19 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before19(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before19 = "Object,SuperClass,Object[]";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before19 = "OverloadedAdvicePOJO,int";
   }
   
   public void before19(@Target Object target, @Arg int arg)
   {
      before19 = "Object,int";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before19 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before19(@Target Object target, @Args Object[] args)
   {
      before19 = "Object,Object[]";
   }
   
   public void before19(@Target OverloadedAdvicePOJO target)
   {
      before19 = "OverloadedAdvicePOJO";
   }
   
   public void before19(@Target Object target)
   {
      before19 = "Object";
   }
   
   public void before19(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before19 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before19(@Caller SuperClass caller, @Arg int arg)
   {
      before19 = "SuperClass,int";
   }
   
   public void before19(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before19 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before19(@Caller SuperClass caller, @Args Object[] args)
   {
      before19 = "SuperClass,Object[]";
   }
   
   public void before19(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before19 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before19(@Caller SuperClass caller)
   {
      before19 = "SuperClass";
   }
   
   public void before19(@Arg int arg)
   {
      before19 = "int";
   }
   
   public void before19(@Args Object[] args)
   {
      before19 = "Object[]";
   }
   
   /* BEFORE20 ADVICE */
   
   public void before20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before20 = "MethodCallByMethod,SuperClass,Object[]";
   }
   
   public void before20(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before20 = "MethodCallByMethod,int";
   }
   
   public void before20(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before20 = "MethodCallByMethod,Object[]";
   }
   
   public void before20(@JoinPoint MethodCallByMethod joinPoint)
   {
      before20= "MethodCallByMethod";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before20 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before20 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before20(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before20 = "Object,SuperClass,int";
   }
 
   public void before20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before20 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before20 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before20(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before20 = "Object,SuperClass,Object[]";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before20 = "OverloadedAdvicePOJO,int";
   }
   
   public void before20(@Target Object target, @Arg int arg)
   {
      before20 = "Object,int";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before20 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before20(@Target Object target, @Args Object[] args)
   {
      before20 = "Object,Object[]";
   }
   
   public void before20(@Target OverloadedAdvicePOJO target)
   {
      before20 = "OverloadedAdvicePOJO";
   }
   
   public void before20(@Target Object target)
   {
      before20 = "Object";
   }
   
   public void before20(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before20 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before20(@Caller SuperClass caller, @Arg int arg)
   {
      before20 = "SuperClass,int";
   }
   
   public void before20(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before20 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before20(@Caller SuperClass caller, @Args Object[] args)
   {
      before20 = "SuperClass,Object[]";
   }
   
   public void before20(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before20 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before20(@Caller SuperClass caller)
   {
      before20 = "SuperClass";
   }
   
   public void before20(@Arg int arg)
   {
      before20 = "int";
   }
   
   public void before20(@Args Object[] args)
   {
      before20 = "Object[]";
   }
   
   /* BEFORE21 ADVICE */
   
   public void before21(@JoinPoint MethodCallByMethod joinPoint, @Arg int arg)
   {
      before21 = "MethodCallByMethod,int";
   }
   
   public void before21(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before21 = "MethodCallByMethod,Object[]";
   }
   
   public void before21(@JoinPoint MethodCallByMethod joinPoint)
   {
      before21= "MethodCallByMethod";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before21 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before21 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before21(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before21 = "Object,SuperClass,int";
   }
 
   public void before21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before21 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before21 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before21(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before21 = "Object,SuperClass,Object[]";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before21 = "OverloadedAdvicePOJO,int";
   }
   
   public void before21(@Target Object target, @Arg int arg)
   {
      before21 = "Object,int";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before21 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before21(@Target Object target, @Args Object[] args)
   {
      before21 = "Object,Object[]";
   }
   
   public void before21(@Target OverloadedAdvicePOJO target)
   {
      before21 = "OverloadedAdvicePOJO";
   }
   
   public void before21(@Target Object target)
   {
      before21 = "Object";
   }
   
   public void before21(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before21 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before21(@Caller SuperClass caller, @Arg int arg)
   {
      before21 = "SuperClass,int";
   }
   
   public void before21(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before21 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before21(@Caller SuperClass caller, @Args Object[] args)
   {
      before21 = "SuperClass,Object[]";
   }
   
   public void before21(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before21 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before21(@Caller SuperClass caller)
   {
      before21 = "SuperClass";
   }
   
   public void before21(@Arg int arg)
   {
      before21 = "int";
   }
   
   public void before21(@Args Object[] args)
   {
      before21 = "Object[]";
   }
   
   /* BEFORE22 ADVICE */
   
   public void before22(@JoinPoint MethodCallByMethod joinPoint,
         @Args Object[] args)
   {
      before22 = "MethodCallByMethod,Object[]";
   }
   
   public void before22(@JoinPoint MethodCallByMethod joinPoint)
   {
      before22= "MethodCallByMethod";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before22 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before22 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before22(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before22 = "Object,SuperClass,int";
   }
 
   public void before22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before22 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before22 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before22(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before22 = "Object,SuperClass,Object[]";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before22 = "OverloadedAdvicePOJO,int";
   }
   
   public void before22(@Target Object target, @Arg int arg)
   {
      before22 = "Object,int";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before22 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before22(@Target Object target, @Args Object[] args)
   {
      before22 = "Object,Object[]";
   }
   
   public void before22(@Target OverloadedAdvicePOJO target)
   {
      before22 = "OverloadedAdvicePOJO";
   }
   
   public void before22(@Target Object target)
   {
      before22 = "Object";
   }
   
   public void before22(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before22 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before22(@Caller SuperClass caller, @Arg int arg)
   {
      before22 = "SuperClass,int";
   }
   
   public void before22(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before22 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before22(@Caller SuperClass caller, @Args Object[] args)
   {
      before22 = "SuperClass,Object[]";
   }
   
   public void before22(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before22 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before22(@Caller SuperClass caller)
   {
      before22 = "SuperClass";
   }
   
   public void before22(@Arg int arg)
   {
      before22 = "int";
   }
   
   public void before22(@Args Object[] args)
   {
      before22 = "Object[]";
   }
   
   /* BEFORE23 ADVICE */
   
   public void before23(@JoinPoint MethodCallByMethod joinPoint)
   {
      before23= "MethodCallByMethod";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before23 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before23 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before23(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before23 = "Object,SuperClass,int";
   }
 
   public void before23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before23 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before23 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before23(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before23 = "Object,SuperClass,Object[]";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before23 = "OverloadedAdvicePOJO,int";
   }
   
   public void before23(@Target Object target, @Arg int arg)
   {
      before23 = "Object,int";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before23 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before23(@Target Object target, @Args Object[] args)
   {
      before23 = "Object,Object[]";
   }
   
   public void before23(@Target OverloadedAdvicePOJO target)
   {
      before23 = "OverloadedAdvicePOJO";
   }
   
   public void before23(@Target Object target)
   {
      before23 = "Object";
   }
   
   public void before23(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before23 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before23(@Caller SuperClass caller, @Arg int arg)
   {
      before23 = "SuperClass,int";
   }
   
   public void before23(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before23 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before23(@Caller SuperClass caller, @Args Object[] args)
   {
      before23 = "SuperClass,Object[]";
   }
   
   public void before23(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before23 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before23(@Caller SuperClass caller)
   {
      before23 = "SuperClass";
   }
   
   public void before23(@Arg int arg)
   {
      before23 = "int";
   }
   
   public void before23(@Args Object[] args)
   {
      before23 = "Object[]";
   }
   
   /* BEFORE24 ADVICE */
   
   public void before24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before24 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before24 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before24(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before24 = "Object,SuperClass,int";
   }
 
   public void before24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before24 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before24 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before24(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before24 = "Object,SuperClass,Object[]";
   }
   
   public void before24(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before24 = "OverloadedAdvicePOJO,int";
   }
   
   public void before24(@Target Object target, @Arg int arg)
   {
      before24 = "Object,int";
   }
   
   public void before24(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before24 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before24(@Target Object target, @Args Object[] args)
   {
      before24 = "Object,Object[]";
   }
   
   public void before24(@Target OverloadedAdvicePOJO target)
   {
      before24 = "OverloadedAdvicePOJO";
   }
   
   public void before24(@Target Object target)
   {
      before24 = "Object";
   }
   
   public void before24(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before24 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before24(@Caller SuperClass caller, @Arg int arg)
   {
      before24 = "SuperClass,int";
   }
   
   public void before24(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before24 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before24(@Caller SuperClass caller, @Args Object[] args)
   {
      before24 = "SuperClass,Object[]";
   }
   
   public void before24(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before24 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before24(@Caller SuperClass caller)
   {
      before24 = "SuperClass";
   }
   
   public void before24(@Arg int arg)
   {
      before24 = "int";
   }
   
   public void before24(@Args Object[] args)
   {
      before24 = "Object[]";
   }
   
   /* BEFORE25 ADVICE */
   
   public void before25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg)
   {
      before25 = "OverloadedAdvicePOJO,SuperClass,int";
   }
   
   public void before25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before25 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before25(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before25 = "Object,SuperClass,int";
   }
 
   public void before25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before25 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before25 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before25(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before25 = "Object,SuperClass,Object[]";
   }
   
   public void before25(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before25 = "OverloadedAdvicePOJO,int";
   }
   
   public void before25(@Target Object target, @Arg int arg)
   {
      before25 = "Object,int";
   }
   
   public void before25(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before25 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before25(@Target Object target, @Args Object[] args)
   {
      before25 = "Object,Object[]";
   }
   
   public void before25(@Target OverloadedAdvicePOJO target)
   {
      before25 = "OverloadedAdvicePOJO";
   }
   
   public void before25(@Target Object target)
   {
      before25 = "Object";
   }
   
   public void before25(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before25 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before25(@Caller SuperClass caller, @Arg int arg)
   {
      before25 = "SuperClass,int";
   }
   
   public void before25(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before25 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before25(@Caller SuperClass caller, @Args Object[] args)
   {
      before25 = "SuperClass,Object[]";
   }
   
   public void before25(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before25 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before25(@Caller SuperClass caller)
   {
      before25 = "SuperClass";
   }
   
   public void before25(@Arg int arg)
   {
      before25 = "int";
   }
   
   public void before25(@Args Object[] args)
   {
      before25 = "Object[]";
   }
   
   /* BEFORE26 ADVICE */
   
   public void before26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before26 = "Object,OverloadedAdvicePOJOCaller,int";
   }
   
   public void before26(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before26 = "Object,SuperClass,int";
   }
 
   public void before26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before26(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before26 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before26 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before26(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before26 = "Object,SuperClass,Object[]";
   }
   
   public void before26(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before26 = "OverloadedAdvicePOJO,int";
   }
   
   public void before26(@Target Object target, @Arg int arg)
   {
      before26 = "Object,int";
   }
   
   public void before26(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before26 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before26(@Target Object target, @Args Object[] args)
   {
      before26 = "Object,Object[]";
   }
   
   public void before26(@Target OverloadedAdvicePOJO target)
   {
      before26 = "OverloadedAdvicePOJO";
   }
   
   public void before26(@Target Object target)
   {
      before26 = "Object";
   }
   
   public void before26(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before26 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before26(@Caller SuperClass caller, @Arg int arg)
   {
      before26 = "SuperClass,int";
   }
   
   public void before26(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before26 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before26(@Caller SuperClass caller, @Args Object[] args)
   {
      before26 = "SuperClass,Object[]";
   }
   
   public void before26(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before26 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before26(@Caller SuperClass caller)
   {
      before26 = "SuperClass";
   }
   
   public void before26(@Arg int arg)
   {
      before26 = "int";
   }
   
   public void before26(@Args Object[] args)
   {
      before26 = "Object[]";
   }
   
   /* BEFORE27 ADVICE */
   
   public void before27(@Target Object target, @Caller SuperClass caller,
         @Arg int arg)
   {
      before27 = "Object,SuperClass,int";
   }
 
   public void before27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before27(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before27 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before27(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before27 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before27(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before27 = "Object,SuperClass,Object[]";
   }
   
   public void before27(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before27 = "OverloadedAdvicePOJO,int";
   }
   
   public void before27(@Target Object target, @Arg int arg)
   {
      before27 = "Object,int";
   }
   
   public void before27(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before27 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before27(@Target Object target, @Args Object[] args)
   {
      before27 = "Object,Object[]";
   }
   
   public void before27(@Target OverloadedAdvicePOJO target)
   {
      before27 = "OverloadedAdvicePOJO";
   }
   
   public void before27(@Target Object target)
   {
      before27 = "Object";
   }
   
   public void before27(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before27 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before27(@Caller SuperClass caller, @Arg int arg)
   {
      before27 = "SuperClass,int";
   }
   
   public void before27(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before27 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before27(@Caller SuperClass caller, @Args Object[] args)
   {
      before27 = "SuperClass,Object[]";
   }
   
   public void before27(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before27 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before27(@Caller SuperClass caller)
   {
      before27 = "SuperClass";
   }
   
   public void before27(@Arg int arg)
   {
      before27 = "int";
   }
   
   public void before27(@Args Object[] args)
   {
      before27 = "Object[]";
   }
   
   /* BEFORE28 ADVICE */
   
   public void before28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before28(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before28 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before28(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before28 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before28(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before28 = "Object,SuperClass,Object[]";
   }
   
   public void before28(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before28 = "OverloadedAdvicePOJO,int";
   }
   
   public void before28(@Target Object target, @Arg int arg)
   {
      before28 = "Object,int";
   }
   
   public void before28(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before28 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before28(@Target Object target, @Args Object[] args)
   {
      before28 = "Object,Object[]";
   }
   
   public void before28(@Target OverloadedAdvicePOJO target)
   {
      before28 = "OverloadedAdvicePOJO";
   }
   
   public void before28(@Target Object target)
   {
      before28 = "Object";
   }
   
   public void before28(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before28 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before28(@Caller SuperClass caller, @Arg int arg)
   {
      before28 = "SuperClass,int";
   }
   
   public void before28(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before28 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before28(@Caller SuperClass caller, @Args Object[] args)
   {
      before28 = "SuperClass,Object[]";
   }
   
   public void before28(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before28 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before28(@Caller SuperClass caller)
   {
      before28 = "SuperClass";
   }
   
   public void before28(@Arg int arg)
   {
      before28 = "int";
   }
   
   public void before28(@Args Object[] args)
   {
      before28 = "Object[]";
   }
   
   /* BEFORE29 ADVICE */
   
   public void before29(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args)
   {
      before29 = "OverloadedAdvicePOJO,SuperClass,Object[]";
   }
   
   public void before29(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before29 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before29(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before29 = "Object,SuperClass,Object[]";
   }
   
   public void before29(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before29 = "OverloadedAdvicePOJO,int";
   }
   
   public void before29(@Target Object target, @Arg int arg)
   {
      before29 = "Object,int";
   }
   
   public void before29(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before29 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before29(@Target Object target, @Args Object[] args)
   {
      before29 = "Object,Object[]";
   }
   
   public void before29(@Target OverloadedAdvicePOJO target)
   {
      before29 = "OverloadedAdvicePOJO";
   }
   
   public void before29(@Target Object target)
   {
      before29 = "Object";
   }
   
   public void before29(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before29 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before29(@Caller SuperClass caller, @Arg int arg)
   {
      before29 = "SuperClass,int";
   }
   
   public void before29(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before29 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before29(@Caller SuperClass caller, @Args Object[] args)
   {
      before29 = "SuperClass,Object[]";
   }
   
   public void before29(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before29 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before29(@Caller SuperClass caller)
   {
      before29 = "SuperClass";
   }
   
   public void before29(@Arg int arg)
   {
      before29 = "int";
   }
   
   public void before29(@Args Object[] args)
   {
      before29 = "Object[]";
   }
   
   /* BEFORE30 ADVICE */
   
   public void before30(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      before30 = "Object,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before30(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before30 = "Object,SuperClass,Object[]";
   }
   
   public void before30(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before30 = "OverloadedAdvicePOJO,int";
   }
   
   public void before30(@Target Object target, @Arg int arg)
   {
      before30 = "Object,int";
   }
   
   public void before30(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before30 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before30(@Target Object target, @Args Object[] args)
   {
      before30 = "Object,Object[]";
   }
   
   public void before30(@Target OverloadedAdvicePOJO target)
   {
      before30 = "OverloadedAdvicePOJO";
   }
   
   public void before30(@Target Object target)
   {
      before30 = "Object";
   }
   
   public void before30(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before30 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before30(@Caller SuperClass caller, @Arg int arg)
   {
      before30 = "SuperClass,int";
   }
   
   public void before30(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before30 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before30(@Caller SuperClass caller, @Args Object[] args)
   {
      before30 = "SuperClass,Object[]";
   }
   
   public void before30(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before30 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before30(@Caller SuperClass caller)
   {
      before30 = "SuperClass";
   }
   
   public void before30(@Arg int arg)
   {
      before30 = "int";
   }
   
   public void before30(@Args Object[] args)
   {
      before30 = "Object[]";
   }
   
   /* BEFORE31 ADVICE */
   
   public void before31(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args)
   {
      before31 = "Object,SuperClass,Object[]";
   }
   
   public void before31(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before31 = "OverloadedAdvicePOJO,int";
   }
   
   public void before31(@Target Object target, @Arg int arg)
   {
      before31 = "Object,int";
   }
   
   public void before31(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before31 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before31(@Target Object target, @Args Object[] args)
   {
      before31 = "Object,Object[]";
   }
   
   public void before31(@Target OverloadedAdvicePOJO target)
   {
      before31 = "OverloadedAdvicePOJO";
   }
   
   public void before31(@Target Object target)
   {
      before31 = "Object";
   }
   
   public void before31(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before31 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before31(@Caller SuperClass caller, @Arg int arg)
   {
      before31 = "SuperClass,int";
   }
   
   public void before31(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before31 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before31(@Caller SuperClass caller, @Args Object[] args)
   {
      before31 = "SuperClass,Object[]";
   }
   
   public void before31(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before31 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before31(@Caller SuperClass caller)
   {
      before31 = "SuperClass";
   }
   
   public void before31(@Arg int arg)
   {
      before31 = "int";
   }
   
   public void before31(@Args Object[] args)
   {
      before31 = "Object[]";
   }
   
   /* BEFORE32 ADVICE */
   
   public void before32(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      before32 = "OverloadedAdvicePOJO,int";
   }
   
   public void before32(@Target Object target, @Arg int arg)
   {
      before32 = "Object,int";
   }
   
   public void before32(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before32 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before32(@Target Object target, @Args Object[] args)
   {
      before32 = "Object,Object[]";
   }
   
   public void before32(@Target OverloadedAdvicePOJO target)
   {
      before32 = "OverloadedAdvicePOJO";
   }
   
   public void before32(@Target Object target)
   {
      before32 = "Object";
   }
   
   public void before32(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before32 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before32(@Caller SuperClass caller, @Arg int arg)
   {
      before32 = "SuperClass,int";
   }
   
   public void before32(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before32 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before32(@Caller SuperClass caller, @Args Object[] args)
   {
      before32 = "SuperClass,Object[]";
   }
   
   public void before32(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before32 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before32(@Caller SuperClass caller)
   {
      before32 = "SuperClass";
   }
   
   public void before32(@Arg int arg)
   {
      before32 = "int";
   }
   
   public void before32(@Args Object[] args)
   {
      before32 = "Object[]";
   }
   
   /* BEFORE33 ADVICE */
   
   public void before33(@Target Object target, @Arg int arg)
   {
      before33 = "Object,int";
   }
   
   public void before33(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before33 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before33(@Target Object target, @Args Object[] args)
   {
      before33 = "Object,Object[]";
   }
   
   public void before33(@Target OverloadedAdvicePOJO target)
   {
      before33 = "OverloadedAdvicePOJO";
   }
   
   public void before33(@Target Object target)
   {
      before33 = "Object";
   }
   
   public void before33(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before33 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before33(@Caller SuperClass caller, @Arg int arg)
   {
      before33 = "SuperClass,int";
   }
   
   public void before33(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before33 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before33(@Caller SuperClass caller, @Args Object[] args)
   {
      before33 = "SuperClass,Object[]";
   }
   
   public void before33(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before33 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before33(@Caller SuperClass caller)
   {
      before33 = "SuperClass";
   }
   
   public void before33(@Arg int arg)
   {
      before33 = "int";
   }
   
   public void before33(@Args Object[] args)
   {
      before33 = "Object[]";
   }
   
   /* BEFORE34 ADVICE */
   
   public void before34(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      before34 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void before34(@Target Object target, @Args Object[] args)
   {
      before34 = "Object,Object[]";
   }
   
   public void before34(@Target OverloadedAdvicePOJO target)
   {
      before34 = "OverloadedAdvicePOJO";
   }
   
   public void before34(@Target Object target)
   {
      before34 = "Object";
   }
   
   public void before34(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before34 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before34(@Caller SuperClass caller, @Arg int arg)
   {
      before34 = "SuperClass,int";
   }
   
   public void before34(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before34 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before34(@Caller SuperClass caller, @Args Object[] args)
   {
      before34 = "SuperClass,Object[]";
   }
   
   public void before34(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before34 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before34(@Caller SuperClass caller)
   {
      before34 = "SuperClass";
   }
   
   public void before34(@Arg int arg)
   {
      before34 = "int";
   }
   
   public void before34(@Args Object[] args)
   {
      before34 = "Object[]";
   }
   
   /* BEFORE35 ADVICE */
   
   public void before35(@Target Object target, @Args Object[] args)
   {
      before35 = "Object,Object[]";
   }
   
   public void before35(@Target OverloadedAdvicePOJO target)
   {
      before35 = "OverloadedAdvicePOJO";
   }
   
   public void before35(@Target Object target)
   {
      before35 = "Object";
   }
   
   public void before35(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before35 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before35(@Caller SuperClass caller, @Arg int arg)
   {
      before35 = "SuperClass,int";
   }
   
   public void before35(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before35 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before35(@Caller SuperClass caller, @Args Object[] args)
   {
      before35 = "SuperClass,Object[]";
   }
   
   public void before35(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before35 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before35(@Caller SuperClass caller)
   {
      before35 = "SuperClass";
   }
   
   public void before35(@Arg int arg)
   {
      before35 = "int";
   }
   
   public void before35(@Args Object[] args)
   {
      before35 = "Object[]";
   }
   
   /* BEFORE36 ADVICE */
   
   public void before36(@Target OverloadedAdvicePOJO target)
   {
      before36 = "OverloadedAdvicePOJO";
   }
   
   public void before36(@Target Object target)
   {
      before36 = "Object";
   }
   
   public void before36(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before36 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before36(@Caller SuperClass caller, @Arg int arg)
   {
      before36 = "SuperClass,int";
   }
   
   public void before36(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before36 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before36(@Caller SuperClass caller, @Args Object[] args)
   {
      before36 = "SuperClass,Object[]";
   }
   
   public void before36(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before36 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before36(@Caller SuperClass caller)
   {
      before36 = "SuperClass";
   }
   
   public void before36(@Arg int arg)
   {
      before36 = "int";
   }
   
   public void before36(@Args Object[] args)
   {
      before36 = "Object[]";
   }
   
   /* BEFORE37 ADVICE */
   
   public void before37(@Target Object target)
   {
      before37 = "Object";
   }
   
   public void before37(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before37 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before37(@Caller SuperClass caller, @Arg int arg)
   {
      before37 = "SuperClass,int";
   }
   
   public void before37(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before37 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before37(@Caller SuperClass caller, @Args Object[] args)
   {
      before37 = "SuperClass,Object[]";
   }
   
   public void before37(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before37 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before37(@Caller SuperClass caller)
   {
      before37 = "SuperClass";
   }
   
   public void before37(@Arg int arg)
   {
      before37 = "int";
   }
   
   public void before37(@Args Object[] args)
   {
      before37 = "Object[]";
   }
   
   /* BEFORE38 ADVICE */
   
   public void before38(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      before38 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void before38(@Caller SuperClass caller, @Arg int arg)
   {
      before38 = "SuperClass,int";
   }
   
   public void before38(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before38 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before38(@Caller SuperClass caller, @Args Object[] args)
   {
      before38 = "SuperClass,Object[]";
   }
   
   public void before38(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before38 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before38(@Caller SuperClass caller)
   {
      before38 = "SuperClass";
   }
   
   public void before38(@Arg int arg)
   {
      before38 = "int";
   }
   
   public void before38(@Args Object[] args)
   {
      before38 = "Object[]";
   }
   
   /* BEFORE39 ADVICE */
   
   public void before39(@Caller SuperClass caller, @Arg int arg)
   {
      before39 = "SuperClass,int";
   }
   
   public void before39(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before39 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before39(@Caller SuperClass caller, @Args Object[] args)
   {
      before39 = "SuperClass,Object[]";
   }
   
   public void before39(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before39 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before39(@Caller SuperClass caller)
   {
      before39 = "SuperClass";
   }
   
   public void before39(@Arg int arg)
   {
      before39 = "int";
   }
   
   public void before39(@Args Object[] args)
   {
      before39 = "Object[]";
   }
   
   /* BEFORE40 ADVICE */
   
   public void before40(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      before40 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void before40(@Caller SuperClass caller, @Args Object[] args)
   {
      before40 = "SuperClass,Object[]";
   }
   
   public void before40(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before40 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before40(@Caller SuperClass caller)
   {
      before40 = "SuperClass";
   }
   
   public void before40(@Arg int arg)
   {
      before40 = "int";
   }
   
   public void before40(@Args Object[] args)
   {
      before40 = "Object[]";
   }
   
   /* BEFORE41 ADVICE */
   
   public void before41(@Caller SuperClass caller, @Args Object[] args)
   {
      before41 = "SuperClass,Object[]";
   }
   
   public void before41(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before41 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before41(@Caller SuperClass caller)
   {
      before41 = "SuperClass";
   }
   
   public void before41(@Arg int arg)
   {
      before41 = "int";
   }
   
   public void before41(@Args Object[] args)
   {
      before41 = "Object[]";
   }
   
   /* BEFORE42 ADVICE */
   
   public void before42(@Caller OverloadedAdvicePOJOCaller caller)
   {
      before42 = "OverloadedAdvicePOJOCaller";
   }
   
   public void before42(@Caller SuperClass caller)
   {
      before42 = "SuperClass";
   }
   
   public void before42(@Arg int arg)
   {
      before42 = "int";
   }
   
   public void before42(@Args Object[] args)
   {
      before42 = "Object[]";
   }
   
   /* BEFORE43 ADVICE */
   
   public void before43(@Caller SuperClass caller)
   {
      before43 = "SuperClass";
   }
   
   public void before43(@Arg int arg)
   {
      before43 = "int";
   }
   
   public void before43(@Args Object[] args)
   {
      before43 = "Object[]";
   }
   
   /* BEFORE44 ADVICE */
   
   public void before44(@Arg int arg)
   {
      before44 = "int";
   }
   
   public void before44(@Args Object[] args)
   {
      before44 = "Object[]";
   }  
}