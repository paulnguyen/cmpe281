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
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.MethodCallByMethod;

/**
 * Aspect used on overloaded finally advice tests (for Caller and Target tests
 * only).
 * 
 * Notice that this test has to include other annotations to verify the interaction
 * of Target and Caller with all other annotations used in finally. However, no
 * polimorphim is used on these other annotations, and relative positions concluded
 * from OverloadedFinallyAspect tests are not present in this aspect.
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedFinallyCallAspect
{
   static String finally1 = null;
   static String finally2 = null;
   static String finally3 = null;
   static String finally4 = null;
   static String finally5 = null;
   static String finally6 = null;
   static String finally7 = null;
   static String finally8 = null;
   static String finally9 = null;
   static String finally10 = null;
   static String finally11 = null;
   static String finally12 = null;
   static String finally13 = null;
   static String finally14 = null;
   static String finally15 = null;
   static String finally16 = null;
   static String finally17 = null;
   static String finally18 = null;
   static String finally19 = null;
   static String finally20 = null;
   static String finally21 = null;
   static String finally22 = null;
   static String finally23 = null;
   static String finally24 = null;
   static String finally25 = null;
   static String finally26 = null;
   static String finally27 = null;
   static String finally28 = null;
   static String finally29 = null;
   static String finally30 = null;
   static String finally31 = null;
   static String finally32 = null;
   static String finally33 = null;
   static String finally34 = null;
   static String finally35 = null;
   static String finally36 = null;
   static String finally37 = null;
   static String finally38 = null;
   static String finally39 = null;
   static String finally40 = null;
   static String finally41 = null;
   static String finally42 = null;
   static String finally43 = null;
   static String finally44 = null;
   static String finally45 = null;
   static String finally46 = null;
   static String finally47 = null;
   static String finally48 = null;
   static String finally49 = null;
   static String finally50 = null;
   static String finally51 = null;
   static String finally52 = null;
   static String finally53 = null;
   static String finally54 = null;
   static String finally55 = null;
   static String finally56 = null;
   static String finally57 = null;
   static String finally58 = null;
   static String finally59 = null;
   static String finally60 = null;
   static String finally61 = null;
   static String finally62 = null;
    
   public static void clear()
   {
      finally1 = null;
      finally2 = null;
      finally3 = null;
      finally4 = null;
      finally5 = null;
      finally6 = null;
      finally7 = null;
      finally8 = null;
      finally9 = null;
      finally10 = null;
      finally11 = null;
      finally12 = null;
      finally13 = null;
      finally14 = null;
      finally15 = null;
      finally16 = null;
      finally17 = null;
      finally18 = null;
      finally19 = null;
      finally20 = null;
      finally21 = null;
      finally22 = null;
      finally23 = null;
      finally24 = null;
      finally25 = null;
      finally26 = null;
      finally27 = null;
      finally28 = null;
      finally29 = null;
      finally30 = null;
      finally31 = null;
      finally32 = null;
      finally33 = null;
      finally34 = null;
      finally35 = null;
      finally36 = null;
      finally37 = null;
      finally38 = null;
      finally39 = null;
      finally40 = null;
      finally41 = null;
      finally42 = null;
      finally43 = null;
      finally44 = null;
      finally45 = null;
      finally46 = null;
      finally47 = null;
      finally48 = null;
      finally49 = null;
      finally50 = null;
      finally51 = null;
      finally52 = null;
      finally53 = null;
      finally54 = null;
      finally55 = null;
      finally56 = null;
      finally57 = null;
      finally58 = null;
      finally59 = null;
      finally60 = null;
      finally61 = null;
      finally62 = null;
   }
   
   /* FINALLY1 ADVICE */
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally1(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally1 = "MethodCallByMethod";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally1 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally1(@Target OverloadedAdvicePOJO target)
   {
      finally1 = "OverloadedAdvicePOJO";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally1 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally1 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally1 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally1(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally1 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally1(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally1 = "long,Throwable,int";
   }
   
   /* FINALLY2 ADVICE */
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally2(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally2 = "MethodCallByMethod";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally2 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally2(@Target OverloadedAdvicePOJO target)
   {
      finally2 = "OverloadedAdvicePOJO";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally2 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally2 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally2 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally2(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally2 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally2(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally2 = "long,Throwable,int";
   }
   
   /* FINALLY3 ADVICE */
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally3(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally3 = "MethodCallByMethod";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally3 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally3(@Target OverloadedAdvicePOJO target)
   {
      finally3 = "OverloadedAdvicePOJO";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally3 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally3 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally3 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally3(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally3 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally3(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally3 = "long,Throwable,int";
   }
   
   /* FINALLY4 ADVICE */
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally4(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally4 = "MethodCallByMethod";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally4 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally4(@Target OverloadedAdvicePOJO target)
   {
      finally4 = "OverloadedAdvicePOJO";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally4 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally4 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally4 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally4(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally4 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally4(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally4 = "long,Throwable,int";
   }
   
   /* FINALLY5 ADVICE */
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally5(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally5 = "MethodCallByMethod";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally5 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally5(@Target OverloadedAdvicePOJO target)
   {
      finally5 = "OverloadedAdvicePOJO";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally5 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally5 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally5 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally5(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally5 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally5(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally5 = "long,Throwable,int";
   }
   
   /* FINALLY6 ADVICE */
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally6(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally6 = "MethodCallByMethod";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally6 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally6(@Target OverloadedAdvicePOJO target)
   {
      finally6 = "OverloadedAdvicePOJO";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally6 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally6 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally6 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally6(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally6 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally6(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally6 = "long,Throwable,int";
   }
   
   /* FINALLY7 ADVICE */
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally7(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally7 = "MethodCallByMethod";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally7 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally7(@Target OverloadedAdvicePOJO target)
   {
      finally7 = "OverloadedAdvicePOJO";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally7 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally7 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally7 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally7(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally7 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally7(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally7 = "long,Throwable,int";
   }
   
   /* FINALLY8 ADVICE */
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Caller SuperClass caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally8(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally8 = "MethodCallByMethod";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally8 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally8(@Target OverloadedAdvicePOJO target)
   {
      finally8 = "OverloadedAdvicePOJO";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally8 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally8 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally8 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally8(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally8 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally8(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally8 = "long,Throwable,int";
   }
   
   /* FINALLY9 ADVICE */
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target Object target, @Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally9(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally9 = "MethodCallByMethod";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally9 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally9(@Target OverloadedAdvicePOJO target)
   {
      finally9 = "OverloadedAdvicePOJO";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally9 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally9 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally9 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally9(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally9 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally9(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally9 = "long,Throwable,int";
   }
   
   /* FINALLY10 ADVICE */
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally10(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally10 = "MethodCallByMethod";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally10 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally10(@Target OverloadedAdvicePOJO target)
   {
      finally10 = "OverloadedAdvicePOJO";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally10 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally10 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally10 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally10(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally10 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally10(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally10 = "long,Throwable,int";
   }
   
   /* FINALLY11 ADVICE */
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally11(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally11 = "MethodCallByMethod";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally11 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally11(@Target OverloadedAdvicePOJO target)
   {
      finally11 = "OverloadedAdvicePOJO";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally11 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally11 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally11 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally11(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally11 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally11(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally11 = "long,Throwable,int";
   }
   
   /* FINALLY12 ADVICE */
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally12(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally12 = "MethodCallByMethod";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally12 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally12(@Target OverloadedAdvicePOJO target)
   {
      finally12 = "OverloadedAdvicePOJO";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally12 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally12 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally12 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally12(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally12 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally12(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally12 = "long,Throwable,int";
   }
   
   /* FINALLY13 ADVICE */
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally13(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally13 = "MethodCallByMethod";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally13 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally13(@Target OverloadedAdvicePOJO target)
   {
      finally13 = "OverloadedAdvicePOJO";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally13 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally13 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally13 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally13(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally13 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally13(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally13 = "long,Throwable,int";
   }
   
   /* FINALLY14 ADVICE */
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally14(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally14 = "MethodCallByMethod";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally14 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally14(@Target OverloadedAdvicePOJO target)
   {
      finally14 = "OverloadedAdvicePOJO";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally14 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally14 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally14 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally14(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally14 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally14(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally14 = "long,Throwable,int";
   }
   
   /* FINALLY15 ADVICE */
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally15(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally15 = "MethodCallByMethod";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally15 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally15(@Target OverloadedAdvicePOJO target)
   {
      finally15 = "OverloadedAdvicePOJO";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally15 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally15 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally15 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally15(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally15 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally15(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally15 = "long,Throwable,int";
   }
   
   /* FINALLY16 ADVICE */
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally16(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally16 = "MethodCallByMethod";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally16 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally16(@Target OverloadedAdvicePOJO target)
   {
      finally16 = "OverloadedAdvicePOJO";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally16 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally16 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally16 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally16(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally16 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally16(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally16 = "long,Throwable,int";
   }
   
   /* FINALLY17 ADVICE */
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally17(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally17 = "MethodCallByMethod";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally17 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally17(@Target OverloadedAdvicePOJO target)
   {
      finally17 = "OverloadedAdvicePOJO";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally17 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally17 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally17 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally17(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally17 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally17(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally17 = "long,Throwable,int";
   }
   
   /* FINALLY18 ADVICE */
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally18(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally18 = "MethodCallByMethod";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally18 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally18(@Target OverloadedAdvicePOJO target)
   {
      finally18 = "OverloadedAdvicePOJO";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally18 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally18 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally18 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally18(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally18 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally18(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally18 = "long,Throwable,int";
   }
   
   /* FINALLY19 ADVICE */
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Arg int arg)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally19(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally19 = "MethodCallByMethod";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally19 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally19(@Target OverloadedAdvicePOJO target)
   {
      finally19 = "OverloadedAdvicePOJO";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally19 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally19 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally19 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally19(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally19 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally19(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally19 = "long,Throwable,int";
   }
   
   /* FINALLY20 ADVICE */
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally20(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally20 = "MethodCallByMethod";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally20 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally20(@Target OverloadedAdvicePOJO target)
   {
      finally20 = "OverloadedAdvicePOJO";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally20 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally20 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally20 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally20(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally20 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally20(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally20 = "long,Throwable,int";
   }
   
   /* FINALLY21 ADVICE */
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Thrown Throwable thrownException)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally21(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally21 = "MethodCallByMethod";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally21 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally21(@Target OverloadedAdvicePOJO target)
   {
      finally21 = "OverloadedAdvicePOJO";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally21 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally21 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally21 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally21(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally21 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally21(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally21 = "long,Throwable,int";
   }
   
   /* FINALLY22 ADVICE */
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJO,int";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally22(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally22 = "MethodCallByMethod";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally22 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally22(@Target OverloadedAdvicePOJO target)
   {
      finally22 = "OverloadedAdvicePOJO";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally22 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally22 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally22 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally22(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally22 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally22(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally22 = "long,Throwable,int";
   }
   
   /* FINALLY23 ADVICE */
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally23(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally23 = "MethodCallByMethod";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally23 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally23(@Target OverloadedAdvicePOJO target)
   {
      finally23 = "OverloadedAdvicePOJO";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally23 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally23 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally23 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally23(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally23 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally23(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally23 = "long,Throwable,int";
   }
   
   /* FINALLY24 ADVICE */
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Target OverloadedAdvicePOJO target)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJO";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally24(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally24 = "MethodCallByMethod";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally24 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally24(@Target OverloadedAdvicePOJO target)
   {
      finally24 = "OverloadedAdvicePOJO";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally24 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally24 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally24 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally24(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally24 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally24(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally24 = "long,Throwable,int";
   }
   
   /* FINALLY25 ADVICE */
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally25(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally25 = "MethodCallByMethod";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally25 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally25(@Target OverloadedAdvicePOJO target)
   {
      finally25 = "OverloadedAdvicePOJO";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally25 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally25 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally25 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally25(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally25 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally25(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally25 = "long,Throwable,int";
   }
   
   /* FINALLY26 ADVICE */
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally26(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally26 = "MethodCallByMethod";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally26 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally26(@Target OverloadedAdvicePOJO target)
   {
      finally26 = "OverloadedAdvicePOJO";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally26 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally26 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally26 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally26(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally26 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally26(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally26 = "long,Throwable,int";
   }
   
   /* FINALLY27 ADVICE */
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally27(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally27 = "MethodCallByMethod";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally27 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally27(@Target OverloadedAdvicePOJO target)
   {
      finally27 = "OverloadedAdvicePOJO";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally27 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally27 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally27 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally27(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally27 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally27(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally27 = "long,Throwable,int";
   }
   
   /* FINALLY28 ADVICE */
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally28(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally28 = "MethodCallByMethod";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally28 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally28(@Target OverloadedAdvicePOJO target)
   {
      finally28 = "OverloadedAdvicePOJO";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally28 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally28 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally28 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally28(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally28 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally28(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally28 = "long,Throwable,int";
   }
   
   /* FINALLY29 ADVICE */
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally29(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally29 = "MethodCallByMethod";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally29 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally29(@Target OverloadedAdvicePOJO target)
   {
      finally29 = "OverloadedAdvicePOJO";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally29 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally29 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally29 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally29(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally29 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally29(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally29 = "long,Throwable,int";
   }
   
   /* FINALLY30 ADVICE */
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally30 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally30 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally30 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally30 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally30(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally30 = "MethodCallByMethod";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally30 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally30(@Target OverloadedAdvicePOJO target)
   {
      finally30 = "OverloadedAdvicePOJO";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally30 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally30 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally30 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally30(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally30 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally30(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally30 = "long,Throwable,int";
   }
   
   /* FINALLY31 ADVICE */
   
   public void finally31(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally31 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally31(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally31 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally31(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally31 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally31(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally31(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally31 = "MethodCallByMethod";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally31 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally31(@Target OverloadedAdvicePOJO target)
   {
      finally31 = "OverloadedAdvicePOJO";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally31 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally31 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally31 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally31(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally31 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally31(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally31 = "long,Throwable,int";
   }
   
   /* FINALLY32 ADVICE */
   
   public void finally32(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally32 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally32(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally32 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally32(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally32(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally32 = "MethodCallByMethod";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally32 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally32(@Target OverloadedAdvicePOJO target)
   {
      finally32 = "OverloadedAdvicePOJO";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally32 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally32 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally32 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally32(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally32 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally32(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally32 = "long,Throwable,int";
   }
   
   /* FINALLY33 ADVICE */
   
   public void finally33(@JoinPoint MethodCallByMethod joinPoint,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally33 = "MethodCallByMethod,OverloadedAdvicePOJOCaller";
   }
   
   public void finally33(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally33(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally33 = "MethodCallByMethod";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally33 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally33(@Target OverloadedAdvicePOJO target)
   {
      finally33 = "OverloadedAdvicePOJO";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally33 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally33 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally33 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally33(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally33 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally33(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally33 = "long,Throwable,int";
   }
   
   /* FINALLY34 ADVICE */
   
   public void finally34(@JoinPoint MethodCallByMethod joinPoint,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "MethodCallByMethod,long,Throwable,int";
   }
   
   public void finally34(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally34 = "MethodCallByMethod";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally34 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally34(@Target OverloadedAdvicePOJO target)
   {
      finally34 = "OverloadedAdvicePOJO";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally34 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally34 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally34 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally34(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally34 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally34(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally34 = "long,Throwable,int";
   }
   
   /* FINALLY35 ADVICE */
   
   public void finally35(@JoinPoint MethodCallByMethod joinPoint)
   {
      finally35 = "MethodCallByMethod";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally35 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally35(@Target OverloadedAdvicePOJO target)
   {
      finally35 = "OverloadedAdvicePOJO";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally35 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally35 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally35 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally35(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally35 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally35(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally35 = "long,Throwable,int";
   }
   
   /* FINALLY36 ADVICE */
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally36 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally36(@Target OverloadedAdvicePOJO target)
   {
      finally36 = "OverloadedAdvicePOJO";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally36 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally36 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally36 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally36(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally36 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally36(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally36 = "long,Throwable,int";
   }
   
   /* FINALLY37 ADVICE */
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally37 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally37(@Target OverloadedAdvicePOJO target)
   {
      finally37 = "OverloadedAdvicePOJO";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally37 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally37 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally37 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally37(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally37 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally37(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally37 = "long,Throwable,int";
   }
   
   /* FINALLY38 ADVICE */
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Return long valueReturned,
         @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally38 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally38(@Target OverloadedAdvicePOJO target)
   {
      finally38 = "OverloadedAdvicePOJO";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally38 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally38 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally38 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally38(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally38 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally38(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally38 = "long,Throwable,int";
   }
   
   /* FINALLY39 ADVICE */
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally39 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally39 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally39 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally39(@Target OverloadedAdvicePOJO target)
   {
      finally39 = "OverloadedAdvicePOJO";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally39 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally39 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally39 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally39 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally39(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally39 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally39(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally39 = "long,Throwable,int";
   }
   
   /* FINALLY40 ADVICE */
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally40 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally40 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally40 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally40 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally40(@Target OverloadedAdvicePOJO target)
   {
      finally40 = "OverloadedAdvicePOJO";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally40 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally40 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally40 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally40 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally40(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally40 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally40(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally40 = "long,Throwable,int";
   }
   
   /* FINALLY41 ADVICE */
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally41 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally41 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally41 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally41 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally41(@Target OverloadedAdvicePOJO target)
   {
      finally41 = "OverloadedAdvicePOJO";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally41 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally41 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally41 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally41 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally41 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally41(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally41 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally41(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally41 = "long,Throwable,int";
   }
   
   /* FINALLY42 ADVICE */
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally42 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally42 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally42 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally42(@Target OverloadedAdvicePOJO target)
   {
      finally42 = "OverloadedAdvicePOJO";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally42 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally42 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally42 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally42 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally42 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally42(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally42 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally42(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally42 = "long,Throwable,int";
   }
   
   /* FINALLY43 ADVICE */
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller, @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally43 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally43 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally43 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally43(@Target OverloadedAdvicePOJO target)
   {
      finally43 = "OverloadedAdvicePOJO";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally43 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally43 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally43 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally43 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally43 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally43(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally43 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally43(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally43 = "long,Throwable,int";
   }
   
   /* FINALLY44 ADVICE */
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Caller OverloadedAdvicePOJOCaller caller)
   {
      finally44 = "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally44 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally44 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally44 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally44 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally44 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally44(@Target OverloadedAdvicePOJO target)
   {
      finally44 = "OverloadedAdvicePOJO";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally44 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally44 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally44 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally44 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally44 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally44 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally44(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally44 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally44(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally44 = "long,Throwable,int";
   }
   
   /* FINALLY45 ADVICE */
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJO,long,Throwable,int";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally45 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally45 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally45 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally45 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally45 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally45(@Target OverloadedAdvicePOJO target)
   {
      finally45 = "OverloadedAdvicePOJO";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally45 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally45 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally45 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally45 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally45 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally45 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally45(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally45 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally45(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally45 = "long,Throwable,int";
   }
   
   /* FINALLY46 ADVICE */
   
   public void finally46(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally46 = "OverloadedAdvicePOJO,long,Throwable,Object[]";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally46 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally46 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally46 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally46 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally46 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally46 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally46(@Target OverloadedAdvicePOJO target)
   {
      finally46 = "OverloadedAdvicePOJO";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally46 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally46 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally46 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally46 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally46 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally46 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally46 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally46 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally46(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally46 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally46(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally46 = "long,Throwable,int";
   }
   
   /* FINALLY47 ADVICE */
   
   public void finally47(@Target OverloadedAdvicePOJO target,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally47 = "OverloadedAdvicePOJO,long,Throwable";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally47 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally47 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally47 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally47 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally47 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally47(@Target OverloadedAdvicePOJO target)
   {
      finally47 = "OverloadedAdvicePOJO";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally47 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally47 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally47 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally47 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally47 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally47 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally47 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally47 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally47(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally47 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally47(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally47 = "long,Throwable,int";
   }
   
   /* FINALLY48 ADVICE */
   
   public void finally48(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally48 = "OverloadedAdvicePOJO,Throwable,int";
   }
   
   public void finally48(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally48 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally48(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally48 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally48(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally48 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally48(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally48 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally48(@Target OverloadedAdvicePOJO target)
   {
      finally48 = "OverloadedAdvicePOJO";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally48 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally48 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally48 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally48 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally48 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally48 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally48 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally48 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally48(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally48 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally48(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally48 = "long,Throwable,int";
   }
   
   /* FINALLY49 ADVICE */
   
   public void finally49(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally49 = "OverloadedAdvicePOJO,Throwable,Object[]";
   }
   
   public void finally49(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally49 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally49(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally49 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally49(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally49 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally49(@Target OverloadedAdvicePOJO target)
   {
      finally49 = "OverloadedAdvicePOJO";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally49 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally49 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally49 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally49 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally49 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally49 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally49 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally49 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally49(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally49 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally49(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally49 = "long,Throwable,int";
   }
   
   /* FINALLY50 ADVICE */
   
   public void finally50(@Target OverloadedAdvicePOJO target,
         @Thrown Throwable thrownException)
   {
      finally50 = "OverloadedAdvicePOJO,Throwable";
   }
   
   public void finally50(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally50 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally50(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally50 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally50(@Target OverloadedAdvicePOJO target)
   {
      finally50 = "OverloadedAdvicePOJO";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally50 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally50 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally50 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally50 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally50 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally50 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally50 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally50 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally50(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally50 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally50(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally50 = "long,Throwable,int";
   }
   
   /* FINALLY51 ADVICE */
   
   public void finally51(@Target OverloadedAdvicePOJO target, @Arg int arg)
   {
      finally51 = "OverloadedAdvicePOJO,int";
   }
   
   public void finally51(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally51 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally51(@Target OverloadedAdvicePOJO target)
   {
      finally51 = "OverloadedAdvicePOJO";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally51 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally51 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally51 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally51 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally51 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally51 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally51 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally51 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally51(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally51 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally51(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally51 = "long,Throwable,int";
   }
   
   /* FINALLY52 ADVICE */
   
   public void finally52(@Target OverloadedAdvicePOJO target, @Args Object[] args)
   {
      finally52 = "OverloadedAdvicePOJO,Object[]";
   }
   
   public void finally52(@Target OverloadedAdvicePOJO target)
   {
      finally52 = "OverloadedAdvicePOJO";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally52 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally52 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally52 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally52 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally52 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally52 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally52 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally52 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally52(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally52 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally52(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally52 = "long,Throwable,int";
   }
   
   /* FINALLY53 ADVICE */
   
   public void finally53(@Target OverloadedAdvicePOJO target)
   {
      finally53 = "OverloadedAdvicePOJO";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally53 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally53 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally53 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally53 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally53 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally53 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally53 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally53 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally53(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally53 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally53(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally53 = "long,Throwable,int";
   }
   
   /* FINALLY54 ADVICE */
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException, @Arg int arg)
   {
      finally54 = "OverloadedAdvicePOJOCaller,long,Throwable,int";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally54 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally54 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally54 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally54 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally54 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally54 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally54 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally54(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally54 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally54(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally54 = "long,Throwable,int";
   }
   
   /* FINALLY55 ADVICE */
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally55 = "OverloadedAdvicePOJOCaller,long,Throwable,Object[]";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally55 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally55 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally55 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally55 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally55 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally55 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally55(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally55 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally55(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally55 = "long,Throwable,int";
   }
   
   /* FINALLY56 ADVICE */
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller,
         @Return long valueReturned, @Thrown Throwable thrownException)
   {
      finally56 = "OverloadedAdvicePOJOCaller,long,Throwable";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally56 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally56 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally56 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally56 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally56 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally56(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally56 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally56(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally56 = "long,Throwable,int";
   }
   
   /* FINALLY57 ADVICE */
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally57 = "OverloadedAdvicePOJOCaller,Throwable,int";
   }
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally57 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally57 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally57 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally57 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally57(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally57 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally57(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally57 = "long,Throwable,int";
   }
   
   /* FINALLY58 ADVICE */
   
   public void finally58(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally58 = "OverloadedAdvicePOJOCaller,Throwable,Object[]";
   }
   
   public void finally58(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally58 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally58(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally58 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally58(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally58 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally58(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally58 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally58(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally58 = "long,Throwable,int";
   }
   
   /* FINALLY59 ADVICE */
   
   public void finally59(@Caller OverloadedAdvicePOJOCaller caller,
         @Thrown Throwable thrownException)
   {
      finally59 = "OverloadedAdvicePOJOCaller,Throwable";
   }
   
   public void finally59(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally59 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally59(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally59 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally59(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally59 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally59(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally59 = "long,Throwable,int";
   }
   
   /* FINALLY60 ADVICE */
   
   public void finally60(@Caller OverloadedAdvicePOJOCaller caller, @Arg int arg)
   {
      finally60 = "OverloadedAdvicePOJOCaller,int";
   }
   
   public void finally60(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally60 = "MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally60(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally60 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally60(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally60 = "long,Throwable,int";
   }
   
   /* FINALLY61 ADVICE */
   
   public void finally61(@Caller OverloadedAdvicePOJOCaller caller,
         @Args Object[] args)
   {
      finally61 = "OverloadedAdvicePOJOCaller,Object[]";
   }
   
   public void finally61(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally61 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally61(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally61 = "long,Throwable,int";
   }
   
   /* FINALLY62 ADVICE */
   
   public void finally62(@Caller OverloadedAdvicePOJOCaller caller)
   {
      finally62 = "OverloadedAdvicePOJOCaller";
   }
   
   public void finally62(@Return long valueReturned,
         @Thrown Throwable thrownException, @Arg int arg)
   {
      finally62 = "long,Throwable,int";
   }
}