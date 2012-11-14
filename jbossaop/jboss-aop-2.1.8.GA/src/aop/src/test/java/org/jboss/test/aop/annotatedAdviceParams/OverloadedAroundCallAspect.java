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
import org.jboss.aop.joinpoint.CurrentInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;

/**
 * Aspect used on overloaded around advice tests (for Call and Target tests
 * only).
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedAroundCallAspect
{
   static String around1 = null;
   static String around2 = null;
   static String around3 = null;
   static String around4 = null;
   static String around5 = null;
   static String around6 = null;
   static String around7 = null;
   static String around8 = null;
   static String around9 = null;
   static String around10 = null;
   static String around11 = null;
   static String around12 = null;
   static String around13 = null;
   static String around14 = null;
   static String around15 = null;
   static String around16 = null;
   static String around17 = null;
   static String around18 = null;
   static String around19 = null;
   static String around20 = null;
   static String around21 = null;
   static String around22 = null;
   static String around23 = null;
   static String around24 = null;
   static String around25 = null;
   static String around26 = null;
   static String around27 = null;
   static String around28 = null;
   static String around29 = null;
   static String around30 = null;
   static String around31 = null;
   static String around32 = null;
   static String around33 = null;
   static String around34 = null;
   static String around35 = null;
   static String around36 = null;
   static String around37 = null;
   static String around38 = null;
   static String around39 = null;
   static String around40 = null;
   static String around41 = null;
   static String around42 = null;
   static String around43 = null;
   static String around44 = null;
   static String around45 = null;
   
   public static void clear()
   {
      around1 = null;
      around2 = null;
      around3 = null;
      around4 = null;
      around5 = null;
      around6 = null;
      around7 = null;
      around8 = null;
      around9 = null;
      around10 = null;
      around11 = null;
      around12 = null;
      around13 = null;
      around14 = null;
      around15 = null;
      around16 = null;
      around17 = null;
      around18 = null;
      around19 = null;
      around20 = null;
      around21 = null;
      around22 = null;
      around23 = null;
      around24 = null;
      around25 = null;
      around26 = null;
      around27 = null;
      around28 = null;
      around29 = null;
      around30 = null;
      around31 = null;
      around32 = null;
      around33 = null;
      around34 = null;
      around35 = null;
      around36 = null;
      around37 = null;
      around38 = null;
      around39 = null;
      around40 = null;
      around41 = null;
      around42 = null;
      around43 = null;
      around44 = null;
      around45 = null;
   }
   
   /* AROUND1 ADVICE */
   
   public Object around1(Invocation invocation) throws Throwable
   {
      around1 = "defaultSignature";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around1 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around1(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around1= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around1 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around1 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around1 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around1 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target, @Arg int arg) throws Throwable
   {
      around1 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around1 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target, @Args Object[] args) throws Throwable
   {
      around1 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around1 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Target Object target) throws Throwable
   {
      around1 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around1 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around1 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around1 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around1 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around1 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Caller SuperClass caller) throws Throwable
   {
      around1 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Arg int arg) throws Throwable
   {
      around1 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around1(@Args Object[] args) throws Throwable
   {
      around1 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND2 ADVICE */
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around2 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around2(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around2= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around2 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around2 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around2 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around2 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target, @Arg int arg) throws Throwable
   {
      around2 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around2 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target, @Args Object[] args) throws Throwable
   {
      around2 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around2 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Target Object target) throws Throwable
   {
      around2 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around2 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around2 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around2 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around2 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around2 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Caller SuperClass caller) throws Throwable
   {
      around2 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Arg int arg) throws Throwable
   {
      around2 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Args Object[] args) throws Throwable
   {
      around2 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND3 ADVICE */
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around3 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around3(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around3= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around3 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around3 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around3 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around3 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target, @Arg int arg) throws Throwable
   {
      around3 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around3 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target, @Args Object[] args) throws Throwable
   {
      around3 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around3 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Target Object target) throws Throwable
   {
      around3 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around3 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around3 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around3 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around3 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around3 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Caller SuperClass caller) throws Throwable
   {
      around3 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Arg int arg) throws Throwable
   {
      around3 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Args Object[] args) throws Throwable
   {
      around3 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND4 ADVICE */
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around4 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around4(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around4= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around4 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around4 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around4 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around4 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target, @Arg int arg) throws Throwable
   {
      around4 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around4 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target, @Args Object[] args) throws Throwable
   {
      around4 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around4 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Target Object target) throws Throwable
   {
      around4 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around4 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around4 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around4 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around4 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around4 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Caller SuperClass caller) throws Throwable
   {
      around4 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Arg int arg) throws Throwable
   {
      around4 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Args Object[] args) throws Throwable
   {
      around4 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND5 ADVICE */
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around5 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around5(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around5= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around5 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around5 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around5 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around5 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target, @Arg int arg) throws Throwable
   {
      around5 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around5 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target, @Args Object[] args) throws Throwable
   {
      around5 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around5 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Target Object target) throws Throwable
   {
      around5 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around5 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around5 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around5 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around5 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around5 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Caller SuperClass caller) throws Throwable
   {
      around5 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Arg int arg) throws Throwable
   {
      around5 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Args Object[] args) throws Throwable
   {
      around5 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND6 ADVICE */
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around6 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around6(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around6= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around6 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around6 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around6 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around6 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target, @Arg int arg) throws Throwable
   {
      around6 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around6 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target, @Args Object[] args) throws Throwable
   {
      around6 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around6 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Target Object target) throws Throwable
   {
      around6 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around6 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around6 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around6 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around6 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around6 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Caller SuperClass caller) throws Throwable
   {
      around6 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Arg int arg) throws Throwable
   {
      around6 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around6(@Args Object[] args) throws Throwable
   {
      around6 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND7 ADVICE */
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around7 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around7(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around7= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around7 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around7 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around7 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around7 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target, @Arg int arg) throws Throwable
   {
      around7 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around7 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target, @Args Object[] args) throws Throwable
   {
      around7 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around7 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Target Object target) throws Throwable
   {
      around7 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around7 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around7 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around7 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around7 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around7 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Caller SuperClass caller) throws Throwable
   {
      around7 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Arg int arg) throws Throwable
   {
      around7 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around7(@Args Object[] args) throws Throwable
   {
      around7 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND8 ADVICE */
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around8 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around8(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around8= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around8 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around8 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around8 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around8 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target, @Arg int arg) throws Throwable
   {
      around8 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around8 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target, @Args Object[] args) throws Throwable
   {
      around8 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around8 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Target Object target) throws Throwable
   {
      around8 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around8 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around8 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around8 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around8 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around8 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Caller SuperClass caller) throws Throwable
   {
      around8 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Arg int arg) throws Throwable
   {
      around8 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around8(@Args Object[] args) throws Throwable
   {
      around8 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND9 ADVICE */
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around9 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around9(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around9= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around9 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around9 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around9 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around9 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target, @Arg int arg) throws Throwable
   {
      around9 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around9 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target, @Args Object[] args) throws Throwable
   {
      around9 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around9 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Target Object target) throws Throwable
   {
      around9 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around9 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around9 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around9 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around9 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around9 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Caller SuperClass caller) throws Throwable
   {
      around9 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Arg int arg) throws Throwable
   {
      around9 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around9(@Args Object[] args) throws Throwable
   {
      around9 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND10 ADVICE */
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around10 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around10(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around10= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around10 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around10 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around10 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around10 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target, @Arg int arg) throws Throwable
   {
      around10 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around10 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target, @Args Object[] args) throws Throwable
   {
      around10 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around10 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Target Object target) throws Throwable
   {
      around10 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around10 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around10 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around10 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around10 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around10 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Caller SuperClass caller) throws Throwable
   {
      around10 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Arg int arg) throws Throwable
   {
      around10 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around10(@Args Object[] args) throws Throwable
   {
      around10 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND11 ADVICE */
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller)
      throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around11 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around11(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around11= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around11 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around11 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around11 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around11 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target, @Arg int arg) throws Throwable
   {
      around11 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around11 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target, @Args Object[] args) throws Throwable
   {
      around11 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around11 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Target Object target) throws Throwable
   {
      around11 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around11 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around11 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around11 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around11 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around11 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Caller SuperClass caller) throws Throwable
   {
      around11 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Arg int arg) throws Throwable
   {
      around11 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around11(@Args Object[] args) throws Throwable
   {
      around11 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND12 ADVICE */
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller)
      throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around12 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around12(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around12= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around12 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around12 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around12 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around12 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target, @Arg int arg) throws Throwable
   {
      around12 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around12 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target, @Args Object[] args) throws Throwable
   {
      around12 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around12 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Target Object target) throws Throwable
   {
      around12 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around12 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around12 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around12 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around12 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around12 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Caller SuperClass caller) throws Throwable
   {
      around12 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Arg int arg) throws Throwable
   {
      around12 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around12(@Args Object[] args) throws Throwable
   {
      around12 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND13 ADVICE */
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Caller SuperClass caller) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,Object,SuperClass";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around13 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around13(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around13= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around13 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around13 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around13 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around13 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target, @Arg int arg) throws Throwable
   {
      around13 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around13 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target, @Args Object[] args) throws Throwable
   {
      around13 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around13 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Target Object target) throws Throwable
   {
      around13 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around13 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around13 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around13 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around13 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around13 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Caller SuperClass caller) throws Throwable
   {
      around13 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Arg int arg) throws Throwable
   {
      around13 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around13(@Args Object[] args) throws Throwable
   {
      around13 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND14 ADVICE */
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Arg int arg) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around14 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around14(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around14= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around14 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around14 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around14 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around14 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target, @Arg int arg) throws Throwable
   {
      around14 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around14 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target, @Args Object[] args) throws Throwable
   {
      around14 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around14 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Target Object target) throws Throwable
   {
      around14 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around14 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around14 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around14 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around14 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around14 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Caller SuperClass caller) throws Throwable
   {
      around14 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Arg int arg) throws Throwable
   {
      around14 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around14(@Args Object[] args) throws Throwable
   {
      around14 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND15 ADVICE */
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Arg int arg) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,Object,int";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around15 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around15(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around15= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around15 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around15 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around15 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around15 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target, @Arg int arg) throws Throwable
   {
      around15 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around15 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target, @Args Object[] args) throws Throwable
   {
      around15 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around15 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Target Object target) throws Throwable
   {
      around15 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around15 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around15 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around15 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around15 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around15 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Caller SuperClass caller) throws Throwable
   {
      around15 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Arg int arg) throws Throwable
   {
      around15 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around15(@Args Object[] args) throws Throwable
   {
      around15 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND16 ADVICE */
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target OverloadedAdvicePOJO target, @Args Object[] args) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around16 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around16(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around16= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around16 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around16 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around16 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around16 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target, @Arg int arg) throws Throwable
   {
      around16 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around16 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target, @Args Object[] args) throws Throwable
   {
      around16 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around16 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Target Object target) throws Throwable
   {
      around16 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around16 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around16 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around16 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around16 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around16 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Caller SuperClass caller) throws Throwable
   {
      around16 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Arg int arg) throws Throwable
   {
      around16 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around16(@Args Object[] args) throws Throwable
   {
      around16 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND17 ADVICE */
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Target Object target, @Args Object[] args) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,Object,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around17 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around17(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around17= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around17 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around17 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around17 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around17 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target, @Arg int arg) throws Throwable
   {
      around17 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around17 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target, @Args Object[] args) throws Throwable
   {
      around17 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around17 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Target Object target) throws Throwable
   {
      around17 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around17 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around17 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around17 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around17 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around17 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Caller SuperClass caller) throws Throwable
   {
      around17 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Arg int arg) throws Throwable
   {
      around17 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around17(@Args Object[] args) throws Throwable
   {
      around17 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND18 ADVICE */
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around18 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around18(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around18= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around18 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around18 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around18 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around18 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target, @Arg int arg) throws Throwable
   {
      around18 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around18 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target, @Args Object[] args) throws Throwable
   {
      around18 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around18 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Target Object target) throws Throwable
   {
      around18 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around18 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around18 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around18 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around18 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around18 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Caller SuperClass caller) throws Throwable
   {
      around18 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Arg int arg) throws Throwable
   {
      around18 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around18(@Args Object[] args) throws Throwable
   {
      around18 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND19 ADVICE */
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around19 = "MethodCalledByMethodInvocation,SuperClass,int";
      return invocation.invokeNext();
   }
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around19 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around19 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around19 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around19 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around19(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around19= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around19 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around19 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around19 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around19 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target, @Arg int arg) throws Throwable
   {
      around19 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around19 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target, @Args Object[] args) throws Throwable
   {
      around19 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around19 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Target Object target) throws Throwable
   {
      around19 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around19 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around19 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around19 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around19 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around19 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Caller SuperClass caller) throws Throwable
   {
      around19 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Arg int arg) throws Throwable
   {
      around19 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around19(@Args Object[] args) throws Throwable
   {
      around19 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND20 ADVICE */
   
   public Object around20(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around20 = "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around20(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around20 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around20(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around20 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around20(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around20 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around20(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around20= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around20 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around20 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around20 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around20 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target, @Arg int arg) throws Throwable
   {
      around20 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around20 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target, @Args Object[] args) throws Throwable
   {
      around20 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around20 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Target Object target) throws Throwable
   {
      around20 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around20 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around20 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around20 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around20 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around20 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Caller SuperClass caller) throws Throwable
   {
      around20 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Arg int arg) throws Throwable
   {
      around20 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around20(@Args Object[] args) throws Throwable
   {
      around20 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND21 ADVICE */
   
   public Object around21(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around21 = "MethodCalledByMethodInvocation,SuperClass,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around21(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around21 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around21(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around21 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around21(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around21= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around21 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around21 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around21 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around21 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target, @Arg int arg) throws Throwable
   {
      around21 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around21 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target, @Args Object[] args) throws Throwable
   {
      around21 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around21 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Target Object target) throws Throwable
   {
      around21 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around21 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around21 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around21 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around21 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around21 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Caller SuperClass caller) throws Throwable
   {
      around21 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Arg int arg) throws Throwable
   {
      around21 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around21(@Args Object[] args) throws Throwable
   {
      around21 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND22 ADVICE */
   
   public Object around22(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Arg int arg) throws Throwable
   {
      around22 = "MethodCalledByMethodInvocation,int";
      return invocation.invokeNext();
   }
   
   public Object around22(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around22 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around22(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around22= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around22 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around22 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around22 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around22 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target, @Arg int arg) throws Throwable
   {
      around22 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around22 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target, @Args Object[] args) throws Throwable
   {
      around22 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around22 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Target Object target) throws Throwable
   {
      around22 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around22 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around22 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around22 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around22 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around22 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Caller SuperClass caller) throws Throwable
   {
      around22 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Arg int arg) throws Throwable
   {
      around22 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around22(@Args Object[] args) throws Throwable
   {
      around22 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND23 ADVICE */
   
   public Object around23(@JoinPoint MethodCalledByMethodInvocation invocation,
         @Args Object[] args) throws Throwable
   {
      around23 = "MethodCalledByMethodInvocation,Object[]";
      return invocation.invokeNext();
   }
   
   public Object around23(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around23= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around23 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around23 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around23 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around23 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target, @Arg int arg) throws Throwable
   {
      around23 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around23 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target, @Args Object[] args) throws Throwable
   {
      around23 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around23 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Target Object target) throws Throwable
   {
      around23 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around23 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around23 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around23 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around23 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around23 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Caller SuperClass caller) throws Throwable
   {
      around23 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Arg int arg) throws Throwable
   {
      around23 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around23(@Args Object[] args) throws Throwable
   {
      around23 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND24 ADVICE */
   
   public Object around24(@JoinPoint MethodCalledByMethodInvocation invocation)
      throws Throwable
   {
      around24= "MethodCalledByMethodInvocation";
      return invocation.invokeNext();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around24 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around24 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around24 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around24 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target, @Arg int arg) throws Throwable
   {
      around24 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around24 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target, @Args Object[] args) throws Throwable
   {
      around24 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around24 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Target Object target) throws Throwable
   {
      around24 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around24 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around24 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around24 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around24 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around24 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Caller SuperClass caller) throws Throwable
   {
      around24 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Arg int arg) throws Throwable
   {
      around24 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around24(@Args Object[] args) throws Throwable
   {
      around24 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND25 ADVICE */
   
   public Object around25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around25 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around25 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around25 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around25 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target, @Arg int arg) throws Throwable
   {
      around25 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around25 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target, @Args Object[] args) throws Throwable
   {
      around25 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around25 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Target Object target) throws Throwable
   {
      around25 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around25 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around25 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around25 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around25 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around25 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Caller SuperClass caller) throws Throwable
   {
      around25 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Arg int arg) throws Throwable
   {
      around25 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around25(@Args Object[] args) throws Throwable
   {
      around25 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND26 ADVICE */
   
   public Object around26(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around26 = "OverloadedAdvicePOJO,SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around26 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around26 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around26 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around26 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around26 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around26 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target, @Arg int arg) throws Throwable
   {
      around26 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around26 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target, @Args Object[] args) throws Throwable
   {
      around26 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around26 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Target Object target) throws Throwable
   {
      around26 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around26 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around26 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around26 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around26 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around26 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Caller SuperClass caller) throws Throwable
   {
      around26 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Arg int arg) throws Throwable
   {
      around26 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around26(@Args Object[] args) throws Throwable
   {
      around26 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND27 ADVICE */
   
   public Object around27(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg) throws Throwable
   {
      around27 = "Object,OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around27 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around27 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around27 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around27 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around27 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target, @Arg int arg) throws Throwable
   {
      around27 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around27 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target, @Args Object[] args) throws Throwable
   {
      around27 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around27 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Target Object target) throws Throwable
   {
      around27 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around27 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around27 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around27 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around27 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around27 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Caller SuperClass caller) throws Throwable
   {
      around27 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Arg int arg) throws Throwable
   {
      around27 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around27(@Args Object[] args) throws Throwable
   {
      around27 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND28 ADVICE */
   
   public Object around28(@Target Object target, @Caller SuperClass caller,
         @Arg int arg) throws Throwable
   {
      around28 = "Object,SuperClass,int";
      return CurrentInvocation.proceed();
   }
 
   public Object around28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around28 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around28 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around28 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around28 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target Object target, @Arg int arg) throws Throwable
   {
      around28 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around28 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target Object target, @Args Object[] args) throws Throwable
   {
      around28 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around28 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Target Object target) throws Throwable
   {
      around28 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around28 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around28 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around28 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around28 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around28 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Caller SuperClass caller) throws Throwable
   {
      around28 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Arg int arg) throws Throwable
   {
      around28 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around28(@Args Object[] args) throws Throwable
   {
      around28 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND29 ADVICE */
   
   public Object around29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around29 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around29 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around29 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around29 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target Object target, @Arg int arg) throws Throwable
   {
      around29 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around29 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target Object target, @Args Object[] args) throws Throwable
   {
      around29 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around29 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Target Object target) throws Throwable
   {
      around29 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around29 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around29 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around29 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around29 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around29 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Caller SuperClass caller) throws Throwable
   {
      around29 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Arg int arg) throws Throwable
   {
      around29 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around29(@Args Object[] args) throws Throwable
   {
      around29 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND30 ADVICE */
   
   public Object around30(@Target OverloadedAdvicePOJO target,
         @Caller SuperClass caller, @Args Object[] args) throws Throwable
   {
      around30 = "OverloadedAdvicePOJO,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around30 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around30 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around30 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target Object target, @Arg int arg) throws Throwable
   {
      around30 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around30 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target Object target, @Args Object[] args) throws Throwable
   {
      around30 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around30 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Target Object target) throws Throwable
   {
      around30 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around30 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around30 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around30 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around30 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around30 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Caller SuperClass caller) throws Throwable
   {
      around30 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Arg int arg) throws Throwable
   {
      around30 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around30(@Args Object[] args) throws Throwable
   {
      around30 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND31 ADVICE */
   
   public Object around31(@Target Object target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
      throws Throwable
   {
      around31 = "Object,OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around31 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around31 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target Object target, @Arg int arg) throws Throwable
   {
      around31 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around31 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target Object target, @Args Object[] args) throws Throwable
   {
      around31 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around31 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Target Object target) throws Throwable
   {
      around31 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around31 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around31 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around31 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around31 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around31 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Caller SuperClass caller) throws Throwable
   {
      around31 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Arg int arg) throws Throwable
   {
      around31 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around31(@Args Object[] args) throws Throwable
   {
      around31 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND32 ADVICE */
   
   public Object around32(@Target Object target, @Caller SuperClass caller,
         @Args Object[] args) throws Throwable
   {
      around32 = "Object,SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around32 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target Object target, @Arg int arg) throws Throwable
   {
      around32 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around32 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target Object target, @Args Object[] args) throws Throwable
   {
      around32 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around32 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Target Object target) throws Throwable
   {
      around32 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around32 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around32 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around32 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around32 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around32 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Caller SuperClass caller) throws Throwable
   {
      around32 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Arg int arg) throws Throwable
   {
      around32 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around32(@Args Object[] args) throws Throwable
   {
      around32 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND33 ADVICE */
   
   public Object around33(@Target OverloadedAdvicePOJO target, @Arg int arg)
      throws Throwable
   {
      around33 = "OverloadedAdvicePOJO,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Target Object target, @Arg int arg) throws Throwable
   {
      around33 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around33 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Target Object target, @Args Object[] args) throws Throwable
   {
      around33 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around33 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Target Object target) throws Throwable
   {
      around33 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around33 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around33 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around33 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around33 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around33 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Caller SuperClass caller) throws Throwable
   {
      around33 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Arg int arg) throws Throwable
   {
      around33 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around33(@Args Object[] args) throws Throwable
   {
      around33 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND34 ADVICE */
   
   public Object around34(@Target Object target, @Arg int arg) throws Throwable
   {
      around34 = "Object,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around34 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Target Object target, @Args Object[] args) throws Throwable
   {
      around34 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around34 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Target Object target) throws Throwable
   {
      around34 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around34 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around34 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around34 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around34 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around34 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Caller SuperClass caller) throws Throwable
   {
      around34 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Arg int arg) throws Throwable
   {
      around34 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around34(@Args Object[] args) throws Throwable
   {
      around34 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND35 ADVICE */
   
   public Object around35(@Target OverloadedAdvicePOJO target, @Args Object[] args)
      throws Throwable
   {
      around35 = "OverloadedAdvicePOJO,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Target Object target, @Args Object[] args) throws Throwable
   {
      around35 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around35 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Target Object target) throws Throwable
   {
      around35 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around35 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around35 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around35 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around35 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around35 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Caller SuperClass caller) throws Throwable
   {
      around35 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Arg int arg) throws Throwable
   {
      around35 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around35(@Args Object[] args) throws Throwable
   {
      around35 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND36 ADVICE */
   
   public Object around36(@Target Object target, @Args Object[] args) throws Throwable
   {
      around36 = "Object,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around36 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Target Object target) throws Throwable
   {
      around36 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around36 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around36 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around36 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around36 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around36 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Caller SuperClass caller) throws Throwable
   {
      around36 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Arg int arg) throws Throwable
   {
      around36 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around36(@Args Object[] args) throws Throwable
   {
      around36 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND37 ADVICE */
   
   public Object around37(@Target OverloadedAdvicePOJO target) throws Throwable
   {
      around37 = "OverloadedAdvicePOJO";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Target Object target) throws Throwable
   {
      around37 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around37 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around37 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around37 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around37 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around37 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Caller SuperClass caller) throws Throwable
   {
      around37 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Arg int arg) throws Throwable
   {
      around37 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around37(@Args Object[] args) throws Throwable
   {
      around37 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND38 ADVICE */
   
   public Object around38(@Target Object target) throws Throwable
   {
      around38 = "Object";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around38 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around38 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around38 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around38 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around38 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Caller SuperClass caller) throws Throwable
   {
      around38 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Arg int arg) throws Throwable
   {
      around38 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around38(@Args Object[] args) throws Throwable
   {
      around38 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND39 ADVICE */
   
   public Object around39(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
      throws Throwable
   {
      around39 = "OverloadedAdvicePOJOCaller,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around39 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around39 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around39 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around39 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Caller SuperClass caller) throws Throwable
   {
      around39 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Arg int arg) throws Throwable
   {
      around39 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around39(@Args Object[] args) throws Throwable
   {
      around39 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND40 ADVICE */
   
   public Object around40(@Caller SuperClass caller, @Arg int arg) throws Throwable
   {
      around40 = "SuperClass,int";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around40 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around40 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around40 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Caller SuperClass caller) throws Throwable
   {
      around40 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Arg int arg) throws Throwable
   {
      around40 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around40(@Args Object[] args) throws Throwable
   {
      around40 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND41 ADVICE */
   
   public Object around41(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args) throws Throwable
   {
      around41 = "OverloadedAdvicePOJOCaller,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around41(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around41 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around41(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around41 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around41(@Caller SuperClass caller) throws Throwable
   {
      around41 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around41(@Arg int arg) throws Throwable
   {
      around41 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around41(@Args Object[] args) throws Throwable
   {
      around41 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND42 ADVICE */
   
   public Object around42(@Caller SuperClass caller, @Args Object[] args)
      throws Throwable
   {
      around42 = "SuperClass,Object[]";
      return CurrentInvocation.proceed();
   }
   
   public Object around42(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around42 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around42(@Caller SuperClass caller) throws Throwable
   {
      around42 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around42(@Arg int arg) throws Throwable
   {
      around42 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around42(@Args Object[] args) throws Throwable
   {
      around42 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND43 ADVICE */
   
   public Object around43(@Caller OverloadedAdvicePOJOCaller caller) throws Throwable
   {
      around43 = "OverloadedAdvicePOJOCaller";
      return CurrentInvocation.proceed();
   }
   
   public Object around43(@Caller SuperClass caller) throws Throwable
   {
      around43 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around43(@Arg int arg) throws Throwable
   {
      around43 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around43(@Args Object[] args) throws Throwable
   {
      around43 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND44 ADVICE */
   
   public Object around44(@Caller SuperClass caller) throws Throwable
   {
      around44 = "SuperClass";
      return CurrentInvocation.proceed();
   }
   
   public Object around44(@Arg int arg) throws Throwable
   {
      around44 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around44(@Args Object[] args) throws Throwable
   {
      around44 = "Object[]";
      return CurrentInvocation.proceed();
   }
   
   /* AROUND45 ADVICE */
   
   public Object around45(@Arg int arg) throws Throwable
   {
      around45 = "int";
      return CurrentInvocation.proceed();
   }
   
   public Object around45(@Args Object[] args) throws Throwable
   {
      around45 = "Object[]";
      return CurrentInvocation.proceed();
   }
}