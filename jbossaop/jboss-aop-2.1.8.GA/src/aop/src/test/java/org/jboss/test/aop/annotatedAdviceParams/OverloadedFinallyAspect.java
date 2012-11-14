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

import java.io.Serializable;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.MethodExecution;

/**
 * Aspect used on overloaded finally advice tests (for JoinPoint, Return, Thrown, Arg
 * and Args tests).
 * 
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedFinallyAspect
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
   static String finally63 = null;
   static String finally64 = null;
   static String finally65 = null;
   static String finally66 = null;
   static String finally67 = null;
   static String finally68 = null;
   static String finally69 = null;
   static String finally70 = null;
   static String finally71 = null;
   
   static void clear()
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
      finally63 = null;
      finally64 = null;
      finally65 = null;
      finally66 = null;
      finally67 = null;
      finally68 = null;
      finally69 = null;
      finally70 = null;
      finally71 = null;
   }
   
   /* FINALLY1 ADVICE */
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,MethodExecution,String,Throwable,int,long";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,MethodExecution,String,Serializable,int,long";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,MethodExecution,CharSequence,Throwable,int,long";
   }
   
   public String finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally1 = "String,MethodExecution,String,Throwable,int";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally1 = "void,MethodExecution,String,Throwable,long";
   }
   
   public String finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally1 = "String,MethodExecution,String,Serializable,long";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally1 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally1 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally1 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally1 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally1 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally1 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally1 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally1 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally1 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally1 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally1 = "Object,MethodExecution,Throwable,long";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally1 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally1 = "String,MethodExecution,Serializable,int";
      return "finally1";
   }
   
   public Object finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally1 = "Object,MethodExecution,Serializable,long";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally1 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally1 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally1 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally1 = "void,MethodExecution,Throwable";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally1 = "void,MethodExecution,Serializable";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally1 = "void,MethodExecution,int,long";
   }
   
   public String finally1(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally1 = "String,MethodExecution,long";
      return "finally1";
   }
   
   public Object finally1(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally1 = "Object,MethodExecution,int";
      return "finally1";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally1 = "void,MethodExecution,Object[]";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally1 = "void,MethodExecution,Object";
   }
   
   public void finally1(@JoinPoint MethodExecution joinPoint)
   {
      finally1 = "void,MethodExecution";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,String,Throwable,int,long";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,String,Serializable,int,long";
   }
   
   public void finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally1(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally1 = "String,String,Throwable,int";
      return "finally1";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally1 = "void,String,Throwable,long";
   }
   
   public String finally1(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally1 = "String,String,Serializable,long";
      return "finally1";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally1 = "void,String,Serializable,int";
   }
   
   public Object finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally1 = "Object,CharSequence,Throwable,int";
      return "finally1";
   }
   
   public void finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally1 = "void,CharSequence,Throwable,long";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "void,String,Throwable,Object[]";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally1 = "void,String,Throwable,Object";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally1 = "void,String,Serializable,Object[]";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally1 = "void,String,Serializable,Object";
   }
   
   public void finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally1 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "void,String,Throwable";
   }
   
   public void finally1(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally1 = "void,String,Serializable";
   }
   
   public void finally1(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally1 = "void,CharSequence,Throwable";
   }
   
   public void finally1(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,Throwable,int,long";
   }
   
   public void finally1(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally1 = "void,Serializable,int,long";
   }
      
   public Object finally1(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally1 = "Object,Throwable,long";
      return "finally1";
   }
   
   public void finally1(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally1 = "void,Throwable,int";
   }
   
   public String finally1(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally1 = "String,Serializable,int";
      return "finally1";
   }
   
   public Object finally1(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally1 = "Object,Serializable,long";
      return "finally1";
   }
   
   public void finally1(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally1 = "void,Throwable,Object[]";
   }
   
   public void finally1(@Thrown Throwable thrownException, @Args Object args)
   {
      finally1 = "void,Throwable,Object";
   }
   
   public void finally1(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally1 = "void,Serializable,Object[]";
   }
   
   public void finally1(@Thrown Serializable thrownException, @Args Object args)
   {
      finally1 = "void,Serializable,Object";
   }
   
   public void finally1(@Thrown Throwable thrownException)
   {
      finally1 = "void,Throwable";
   }
   
   public void finally1(@Thrown Serializable thrownException)
   {
      finally1 = "void,Serializable";
   }
   
   public void finally1(@Arg int arg1, @Arg long arg2)
   {
      finally1 = "void,int,long";
   }
   
   public String finally1(@Arg long arg2)
   {
      finally1 = "String,long";
      return "finally1";
   }
   
   public Object finally1(@Arg int arg1)
   {
      finally1 = "Object,int";
      return "finally1";
   }
   
   public void finally1(@Args Object[] args)
   {
      finally1 = "void,Object[]";
   }
   
   public void finally1(@Args Object args)
   {
      finally1 = "void,Object";
   }
   
   public void finally1()
   {
      finally1 = "void";
   }
   
   /* FINALLY2 ADVICE */
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,MethodExecution,String,Serializable,int,long";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,MethodExecution,CharSequence,Throwable,int,long";
   }
   
   public String finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally2 = "String,MethodExecution,String,Throwable,int";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally2 = "void,MethodExecution,String,Throwable,long";
   }
   
   public String finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally2 = "String,MethodExecution,String,Serializable,long";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally2 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally2 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally2 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally2 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally2 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally2 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally2 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally2 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally2 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally2 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally2 = "Object,MethodExecution,Throwable,long";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally2 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally2 = "String,MethodExecution,Serializable,int";
      return "finally2";
   }
   
   public Object finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally2 = "Object,MethodExecution,Serializable,long";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally2 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally2 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally2 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally2 = "void,MethodExecution,Throwable";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally2 = "void,MethodExecution,Serializable";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally2 = "void,MethodExecution,int,long";
   }
   
   public String finally2(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally2 = "String,MethodExecution,long";
      return "finally2";
   }
   
   public Object finally2(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally2 = "Object,MethodExecution,int";
      return "finally2";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally2 = "void,MethodExecution,Object[]";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally2 = "void,MethodExecution,Object";
   }
   
   public void finally2(@JoinPoint MethodExecution joinPoint)
   {
      finally2 = "void,MethodExecution";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,String,Throwable,int,long";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,String,Serializable,int,long";
   }
   
   public void finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally2(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally2 = "String,String,Throwable,int";
      return "finally2";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally2 = "void,String,Throwable,long";
   }
   
   public String finally2(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally2 = "String,String,Serializable,long";
      return "finally2";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally2 = "void,String,Serializable,int";
   }
   
   public Object finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally2 = "Object,CharSequence,Throwable,int";
      return "finally2";
   }
   
   public void finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally2 = "void,CharSequence,Throwable,long";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "void,String,Throwable,Object[]";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally2 = "void,String,Throwable,Object";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally2 = "void,String,Serializable,Object[]";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally2 = "void,String,Serializable,Object";
   }
   
   public void finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally2 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "void,String,Throwable";
   }
   
   public void finally2(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally2 = "void,String,Serializable";
   }
   
   public void finally2(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally2 = "void,CharSequence,Throwable";
   }
   
   public void finally2(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,Throwable,int,long";
   }
   
   public void finally2(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally2 = "void,Serializable,int,long";
   }
      
   public Object finally2(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally2 = "Object,Throwable,long";
      return "finally2";
   }
   
   public void finally2(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally2 = "void,Throwable,int";
   }
   
   public String finally2(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally2 = "String,Serializable,int";
      return "finally2";
   }
   
   public Object finally2(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally2 = "Object,Serializable,long";
      return "finally2";
   }
   
   public void finally2(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally2 = "void,Throwable,Object[]";
   }
   
   public void finally2(@Thrown Throwable thrownException, @Args Object args)
   {
      finally2 = "void,Throwable,Object";
   }
   
   public void finally2(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally2 = "void,Serializable,Object[]";
   }
   
   public void finally2(@Thrown Serializable thrownException, @Args Object args)
   {
      finally2 = "void,Serializable,Object";
   }
   
   public void finally2(@Thrown Throwable thrownException)
   {
      finally2 = "void,Throwable";
   }
   
   public void finally2(@Thrown Serializable thrownException)
   {
      finally2 = "void,Serializable";
   }
   
   public void finally2(@Arg int arg1, @Arg long arg2)
   {
      finally2 = "void,int,long";
   }
   
   public String finally2(@Arg long arg2)
   {
      finally2 = "String,long";
      return "finally2";
   }
   
   public Object finally2(@Arg int arg1)
   {
      finally2 = "Object,int";
      return "finally2";
   }
   
   public void finally2(@Args Object[] args)
   {
      finally2 = "void,Object[]";
   }
   
   public void finally2(@Args Object args)
   {
      finally2 = "void,Object";
   }
   
   public void finally2()
   {
      finally2 = "void";
   }
   
   /* FINALLY3 ADVICE */
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,MethodExecution,CharSequence,Throwable,int,long";
   }
   
   public String finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally3 = "String,MethodExecution,String,Throwable,int";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally3 = "void,MethodExecution,String,Throwable,long";
   }
   
   public String finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally3 = "String,MethodExecution,String,Serializable,long";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally3 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally3 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally3 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally3 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally3 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally3 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally3 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally3 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally3 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally3 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally3 = "Object,MethodExecution,Throwable,long";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally3 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally3 = "String,MethodExecution,Serializable,int";
      return "finally3";
   }
   
   public Object finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally3 = "Object,MethodExecution,Serializable,long";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally3 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally3 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally3 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally3 = "void,MethodExecution,Throwable";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally3 = "void,MethodExecution,Serializable";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally3 = "void,MethodExecution,int,long";
   }
   
   public String finally3(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally3 = "String,MethodExecution,long";
      return "finally3";
   }
   
   public Object finally3(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally3 = "Object,MethodExecution,int";
      return "finally3";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally3 = "void,MethodExecution,Object[]";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally3 = "void,MethodExecution,Object";
   }
   
   public void finally3(@JoinPoint MethodExecution joinPoint)
   {
      finally3 = "void,MethodExecution";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,String,Throwable,int,long";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,String,Serializable,int,long";
   }
   
   public void finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally3(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally3 = "String,String,Throwable,int";
      return "finally3";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally3 = "void,String,Throwable,long";
   }
   
   public String finally3(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally3 = "String,String,Serializable,long";
      return "finally3";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally3 = "void,String,Serializable,int";
   }
   
   public Object finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally3 = "Object,CharSequence,Throwable,int";
      return "finally3";
   }
   
   public void finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally3 = "void,CharSequence,Throwable,long";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "void,String,Throwable,Object[]";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally3 = "void,String,Throwable,Object";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally3 = "void,String,Serializable,Object[]";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally3 = "void,String,Serializable,Object";
   }
   
   public void finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally3 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "void,String,Throwable";
   }
   
   public void finally3(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally3 = "void,String,Serializable";
   }
   
   public void finally3(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally3 = "void,CharSequence,Throwable";
   }
   
   public void finally3(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,Throwable,int,long";
   }
   
   public void finally3(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally3 = "void,Serializable,int,long";
   }
      
   public Object finally3(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally3 = "Object,Throwable,long";
      return "finally3";
   }
   
   public void finally3(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally3 = "void,Throwable,int";
   }
   
   public String finally3(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally3 = "String,Serializable,int";
      return "finally3";
   }
   
   public Object finally3(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally3 = "Object,Serializable,long";
      return "finally3";
   }
   
   public void finally3(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally3 = "void,Throwable,Object[]";
   }
   
   public void finally3(@Thrown Throwable thrownException, @Args Object args)
   {
      finally3 = "void,Throwable,Object";
   }
   
   public void finally3(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally3 = "void,Serializable,Object[]";
   }
   
   public void finally3(@Thrown Serializable thrownException, @Args Object args)
   {
      finally3 = "void,Serializable,Object";
   }
   
   public void finally3(@Thrown Throwable thrownException)
   {
      finally3 = "void,Throwable";
   }
   
   public void finally3(@Thrown Serializable thrownException)
   {
      finally3 = "void,Serializable";
   }
   
   public void finally3(@Arg int arg1, @Arg long arg2)
   {
      finally3 = "void,int,long";
   }
   
   public String finally3(@Arg long arg2)
   {
      finally3 = "String,long";
      return "finally3";
   }
   
   public Object finally3(@Arg int arg1)
   {
      finally3 = "Object,int";
      return "finally3";
   }
   
   public void finally3(@Args Object[] args)
   {
      finally3 = "void,Object[]";
   }
   
   public void finally3(@Args Object args)
   {
      finally3 = "void,Object";
   }
   
   public void finally3()
   {
      finally3 = "void";
   }
   
   /* FINALLY4 ADVICE */
   
   public String finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally4 = "String,MethodExecution,String,Throwable,int";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally4 = "void,MethodExecution,String,Throwable,long";
   }
   
   public String finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally4 = "String,MethodExecution,String,Serializable,long";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally4 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally4(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally4 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally4 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally4 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally4 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally4 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally4 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally4 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally4 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally4 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally4 = "Object,MethodExecution,Throwable,long";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally4 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally4 = "String,MethodExecution,Serializable,int";
      return "finally4";
   }
   
   public Object finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally4 = "Object,MethodExecution,Serializable,long";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally4 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally4 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally4 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally4 = "void,MethodExecution,Throwable";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally4 = "void,MethodExecution,Serializable";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally4 = "void,MethodExecution,int,long";
   }
   
   public String finally4(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally4 = "String,MethodExecution,long";
      return "finally4";
   }
   
   public Object finally4(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally4 = "Object,MethodExecution,int";
      return "finally4";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally4 = "void,MethodExecution,Object[]";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally4 = "void,MethodExecution,Object";
   }
   
   public void finally4(@JoinPoint MethodExecution joinPoint)
   {
      finally4 = "void,MethodExecution";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,String,Throwable,int,long";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,String,Serializable,int,long";
   }
   
   public void finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally4(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally4 = "String,String,Throwable,int";
      return "finally4";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally4 = "void,String,Throwable,long";
   }
   
   public String finally4(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally4 = "String,String,Serializable,long";
      return "finally4";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally4 = "void,String,Serializable,int";
   }
   
   public Object finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally4 = "Object,CharSequence,Throwable,int";
      return "finally4";
   }
   
   public void finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally4 = "void,CharSequence,Throwable,long";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "void,String,Throwable,Object[]";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally4 = "void,String,Throwable,Object";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally4 = "void,String,Serializable,Object[]";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally4 = "void,String,Serializable,Object";
   }
   
   public void finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally4 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "void,String,Throwable";
   }
   
   public void finally4(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally4 = "void,String,Serializable";
   }
   
   public void finally4(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally4 = "void,CharSequence,Throwable";
   }
   
   public void finally4(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,Throwable,int,long";
   }
   
   public void finally4(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally4 = "void,Serializable,int,long";
   }
      
   public Object finally4(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally4 = "Object,Throwable,long";
      return "finally4";
   }
   
   public void finally4(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally4 = "void,Throwable,int";
   }
   
   public String finally4(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally4 = "String,Serializable,int";
      return "finally4";
   }
   
   public Object finally4(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally4 = "Object,Serializable,long";
      return "finally4";
   }
   
   public void finally4(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally4 = "void,Throwable,Object[]";
   }
   
   public void finally4(@Thrown Throwable thrownException, @Args Object args)
   {
      finally4 = "void,Throwable,Object";
   }
   
   public void finally4(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally4 = "void,Serializable,Object[]";
   }
   
   public void finally4(@Thrown Serializable thrownException, @Args Object args)
   {
      finally4 = "void,Serializable,Object";
   }
   
   public void finally4(@Thrown Throwable thrownException)
   {
      finally4 = "void,Throwable";
   }
   
   public void finally4(@Thrown Serializable thrownException)
   {
      finally4 = "void,Serializable";
   }
   
   public void finally4(@Arg int arg1, @Arg long arg2)
   {
      finally4 = "void,int,long";
   }
   
   public String finally4(@Arg long arg2)
   {
      finally4 = "String,long";
      return "finally4";
   }
   
   public Object finally4(@Arg int arg1)
   {
      finally4 = "Object,int";
      return "finally4";
   }
   
   public void finally4(@Args Object[] args)
   {
      finally4 = "void,Object[]";
   }
   
   public void finally4(@Args Object args)
   {
      finally4 = "void,Object";
   }
   
   public void finally4()
   {
      finally4 = "void";
   }
   
   /* FINALLY5 ADVICE */
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally5 = "void,MethodExecution,String,Throwable,long";
   }
   
   public String finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally5 = "String,MethodExecution,String,Serializable,long";
      return "finally5";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally5 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally5(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally5 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally5";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally5 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally5 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally5 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally5 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally5 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally5 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally5 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally5 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally5 = "Object,MethodExecution,Throwable,long";
      return "finally5";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally5 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally5 = "String,MethodExecution,Serializable,int";
      return "finally5";
   }
   
   public Object finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally5 = "Object,MethodExecution,Serializable,long";
      return "finally5";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally5 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally5 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally5 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally5 = "void,MethodExecution,Throwable";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally5 = "void,MethodExecution,Serializable";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally5 = "void,MethodExecution,int,long";
   }
   
   public String finally5(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally5 = "String,MethodExecution,long";
      return "finally5";
   }
   
   public Object finally5(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally5 = "Object,MethodExecution,int";
      return "finally5";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally5 = "void,MethodExecution,Object[]";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally5 = "void,MethodExecution,Object";
   }
   
   public void finally5(@JoinPoint MethodExecution joinPoint)
   {
      finally5 = "void,MethodExecution";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,String,Throwable,int,long";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,String,Serializable,int,long";
   }
   
   public void finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally5(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally5 = "String,String,Throwable,int";
      return "finally5";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally5 = "void,String,Throwable,long";
   }
   
   public String finally5(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally5 = "String,String,Serializable,long";
      return "finally5";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally5 = "void,String,Serializable,int";
   }
   
   public Object finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally5 = "Object,CharSequence,Throwable,int";
      return "finally5";
   }
   
   public void finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally5 = "void,CharSequence,Throwable,long";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "void,String,Throwable,Object[]";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally5 = "void,String,Throwable,Object";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally5 = "void,String,Serializable,Object[]";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally5 = "void,String,Serializable,Object";
   }
   
   public void finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally5 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "void,String,Throwable";
   }
   
   public void finally5(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally5 = "void,String,Serializable";
   }
   
   public void finally5(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally5 = "void,CharSequence,Throwable";
   }
   
   public void finally5(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,Throwable,int,long";
   }
   
   public void finally5(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally5 = "void,Serializable,int,long";
   }
      
   public Object finally5(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally5 = "Object,Throwable,long";
      return "finally5";
   }
   
   public void finally5(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally5 = "void,Throwable,int";
   }
   
   public String finally5(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally5 = "String,Serializable,int";
      return "finally5";
   }
   
   public Object finally5(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally5 = "Object,Serializable,long";
      return "finally5";
   }
   
   public void finally5(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally5 = "void,Throwable,Object[]";
   }
   
   public void finally5(@Thrown Throwable thrownException, @Args Object args)
   {
      finally5 = "void,Throwable,Object";
   }
   
   public void finally5(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally5 = "void,Serializable,Object[]";
   }
   
   public void finally5(@Thrown Serializable thrownException, @Args Object args)
   {
      finally5 = "void,Serializable,Object";
   }
   
   public void finally5(@Thrown Throwable thrownException)
   {
      finally5 = "void,Throwable";
   }
   
   public void finally5(@Thrown Serializable thrownException)
   {
      finally5 = "void,Serializable";
   }
   
   public void finally5(@Arg int arg1, @Arg long arg2)
   {
      finally5 = "void,int,long";
   }
   
   public String finally5(@Arg long arg2)
   {
      finally5 = "String,long";
      return "finally5";
   }
   
   public Object finally5(@Arg int arg1)
   {
      finally5 = "Object,int";
      return "finally5";
   }
   
   public void finally5(@Args Object[] args)
   {
      finally5 = "void,Object[]";
   }
   
   public void finally5(@Args Object args)
   {
      finally5 = "void,Object";
   }
   
   public void finally5()
   {
      finally5 = "void";
   }
   
   /* FINALLY6 ADVICE */
   
   public String finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg long arg2)
   {
      finally6 = "String,MethodExecution,String,Serializable,long";
      return "finally6";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally6 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally6(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally6 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally6";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally6 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally6 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally6 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally6 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally6 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally6 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally6 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally6 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally6 = "Object,MethodExecution,Throwable,long";
      return "finally6";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally6 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally6 = "String,MethodExecution,Serializable,int";
      return "finally6";
   }
   
   public Object finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally6 = "Object,MethodExecution,Serializable,long";
      return "finally6";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally6 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally6 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally6 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally6 = "void,MethodExecution,Throwable";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally6 = "void,MethodExecution,Serializable";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally6 = "void,MethodExecution,int,long";
   }
   
   public String finally6(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally6 = "String,MethodExecution,long";
      return "finally6";
   }
   
   public Object finally6(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally6 = "Object,MethodExecution,int";
      return "finally6";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally6 = "void,MethodExecution,Object[]";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally6 = "void,MethodExecution,Object";
   }
   
   public void finally6(@JoinPoint MethodExecution joinPoint)
   {
      finally6 = "void,MethodExecution";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,String,Throwable,int,long";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,String,Serializable,int,long";
   }
   
   public void finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally6(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally6 = "String,String,Throwable,int";
      return "finally6";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally6 = "void,String,Throwable,long";
   }
   
   public String finally6(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally6 = "String,String,Serializable,long";
      return "finally6";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally6 = "void,String,Serializable,int";
   }
   
   public Object finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally6 = "Object,CharSequence,Throwable,int";
      return "finally6";
   }
   
   public void finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally6 = "void,CharSequence,Throwable,long";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "void,String,Throwable,Object[]";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally6 = "void,String,Throwable,Object";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally6 = "void,String,Serializable,Object[]";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally6 = "void,String,Serializable,Object";
   }
   
   public void finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally6 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "void,String,Throwable";
   }
   
   public void finally6(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally6 = "void,String,Serializable";
   }
   
   public void finally6(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally6 = "void,CharSequence,Throwable";
   }
   
   public void finally6(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,Throwable,int,long";
   }
   
   public void finally6(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally6 = "void,Serializable,int,long";
   }
      
   public Object finally6(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally6 = "Object,Throwable,long";
      return "finally6";
   }
   
   public void finally6(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally6 = "void,Throwable,int";
   }
   
   public String finally6(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally6 = "String,Serializable,int";
      return "finally6";
   }
   
   public Object finally6(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally6 = "Object,Serializable,long";
      return "finally6";
   }
   
   public void finally6(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally6 = "void,Throwable,Object[]";
   }
   
   public void finally6(@Thrown Throwable thrownException, @Args Object args)
   {
      finally6 = "void,Throwable,Object";
   }
   
   public void finally6(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally6 = "void,Serializable,Object[]";
   }
   
   public void finally6(@Thrown Serializable thrownException, @Args Object args)
   {
      finally6 = "void,Serializable,Object";
   }
   
   public void finally6(@Thrown Throwable thrownException)
   {
      finally6 = "void,Throwable";
   }
   
   public void finally6(@Thrown Serializable thrownException)
   {
      finally6 = "void,Serializable";
   }
   
   public void finally6(@Arg int arg1, @Arg long arg2)
   {
      finally6 = "void,int,long";
   }
   
   public String finally6(@Arg long arg2)
   {
      finally6 = "String,long";
      return "finally6";
   }
   
   public Object finally6(@Arg int arg1)
   {
      finally6 = "Object,int";
      return "finally6";
   }
   
   public void finally6(@Args Object[] args)
   {
      finally6 = "void,Object[]";
   }
   
   public void finally6(@Args Object args)
   {
      finally6 = "void,Object";
   }
   
   public void finally6()
   {
      finally6 = "void";
   }
   
   /* FINALLY7 ADVICE */
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Arg int arg1)
   {
      finally7 = "void,MethodExecution,String,Serializable,int";
   }
   
   public Object finally7(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally7 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally7";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally7 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally7 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally7 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally7 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally7 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally7 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally7 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally7 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally7 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally7 = "Object,MethodExecution,Throwable,long";
      return "finally7";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally7 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally7 = "String,MethodExecution,Serializable,int";
      return "finally7";
   }
   
   public Object finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally7 = "Object,MethodExecution,Serializable,long";
      return "finally7";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally7 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally7 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally7 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally7 = "void,MethodExecution,Throwable";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally7 = "void,MethodExecution,Serializable";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally7 = "void,MethodExecution,int,long";
   }
   
   public String finally7(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally7 = "String,MethodExecution,long";
      return "finally7";
   }
   
   public Object finally7(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally7 = "Object,MethodExecution,int";
      return "finally7";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally7 = "void,MethodExecution,Object[]";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally7 = "void,MethodExecution,Object";
   }
   
   public void finally7(@JoinPoint MethodExecution joinPoint)
   {
      finally7 = "void,MethodExecution";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,String,Throwable,int,long";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,String,Serializable,int,long";
   }
   
   public void finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally7(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally7 = "String,String,Throwable,int";
      return "finally7";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally7 = "void,String,Throwable,long";
   }
   
   public String finally7(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally7 = "String,String,Serializable,long";
      return "finally7";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally7 = "void,String,Serializable,int";
   }
   
   public Object finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally7 = "Object,CharSequence,Throwable,int";
      return "finally7";
   }
   
   public void finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally7 = "void,CharSequence,Throwable,long";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "void,String,Throwable,Object[]";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally7 = "void,String,Throwable,Object";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally7 = "void,String,Serializable,Object[]";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally7 = "void,String,Serializable,Object";
   }
   
   public void finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally7 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "void,String,Throwable";
   }
   
   public void finally7(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally7 = "void,String,Serializable";
   }
   
   public void finally7(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally7 = "void,CharSequence,Throwable";
   }
   
   public void finally7(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,Throwable,int,long";
   }
   
   public void finally7(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally7 = "void,Serializable,int,long";
   }
      
   public Object finally7(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally7 = "Object,Throwable,long";
      return "finally7";
   }
   
   public void finally7(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally7 = "void,Throwable,int";
   }
   
   public String finally7(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally7 = "String,Serializable,int";
      return "finally7";
   }
   
   public Object finally7(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally7 = "Object,Serializable,long";
      return "finally7";
   }
   
   public void finally7(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally7 = "void,Throwable,Object[]";
   }
   
   public void finally7(@Thrown Throwable thrownException, @Args Object args)
   {
      finally7 = "void,Throwable,Object";
   }
   
   public void finally7(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally7 = "void,Serializable,Object[]";
   }
   
   public void finally7(@Thrown Serializable thrownException, @Args Object args)
   {
      finally7 = "void,Serializable,Object";
   }
   
   public void finally7(@Thrown Throwable thrownException)
   {
      finally7 = "void,Throwable";
   }
   
   public void finally7(@Thrown Serializable thrownException)
   {
      finally7 = "void,Serializable";
   }
   
   public void finally7(@Arg int arg1, @Arg long arg2)
   {
      finally7 = "void,int,long";
   }
   
   public String finally7(@Arg long arg2)
   {
      finally7 = "String,long";
      return "finally7";
   }
   
   public Object finally7(@Arg int arg1)
   {
      finally7 = "Object,int";
      return "finally7";
   }
   
   public void finally7(@Args Object[] args)
   {
      finally7 = "void,Object[]";
   }
   
   public void finally7(@Args Object args)
   {
      finally7 = "void,Object";
   }
   
   public void finally7()
   {
      finally7 = "void";
   }
   
   /* FINALLY8 ADVICE */
   
   public Object finally8(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg int arg1)
   {
      finally8 = "Object,MethodExecution,CharSequence,Throwable,int";
      return "finally8";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally8 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally8 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally8 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally8 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally8 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally8 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally8 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally8 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally8 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally8 = "Object,MethodExecution,Throwable,long";
      return "finally8";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally8 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally8 = "String,MethodExecution,Serializable,int";
      return "finally8";
   }
   
   public Object finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally8 = "Object,MethodExecution,Serializable,long";
      return "finally8";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally8 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally8 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally8 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally8 = "void,MethodExecution,Throwable";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally8 = "void,MethodExecution,Serializable";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally8 = "void,MethodExecution,int,long";
   }
   
   public String finally8(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally8 = "String,MethodExecution,long";
      return "finally8";
   }
   
   public Object finally8(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally8 = "Object,MethodExecution,int";
      return "finally8";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally8 = "void,MethodExecution,Object[]";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally8 = "void,MethodExecution,Object";
   }
   
   public void finally8(@JoinPoint MethodExecution joinPoint)
   {
      finally8 = "void,MethodExecution";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,String,Throwable,int,long";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,String,Serializable,int,long";
   }
   
   public void finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally8(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally8 = "String,String,Throwable,int";
      return "finally8";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally8 = "void,String,Throwable,long";
   }
   
   public String finally8(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally8 = "String,String,Serializable,long";
      return "finally8";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally8 = "void,String,Serializable,int";
   }
   
   public Object finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally8 = "Object,CharSequence,Throwable,int";
      return "finally8";
   }
   
   public void finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally8 = "void,CharSequence,Throwable,long";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "void,String,Throwable,Object[]";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally8 = "void,String,Throwable,Object";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally8 = "void,String,Serializable,Object[]";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally8 = "void,String,Serializable,Object";
   }
   
   public void finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally8 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally8 = "void,String,Throwable";
   }
   
   public void finally8(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally8 = "void,String,Serializable";
   }
   
   public void finally8(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally8 = "void,CharSequence,Throwable";
   }
   
   public void finally8(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,Throwable,int,long";
   }
   
   public void finally8(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally8 = "void,Serializable,int,long";
   }
      
   public Object finally8(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally8 = "Object,Throwable,long";
      return "finally8";
   }
   
   public void finally8(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally8 = "void,Throwable,int";
   }
   
   public String finally8(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally8 = "String,Serializable,int";
      return "finally8";
   }
   
   public Object finally8(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally8 = "Object,Serializable,long";
      return "finally8";
   }
   
   public void finally8(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally8 = "void,Throwable,Object[]";
   }
   
   public void finally8(@Thrown Throwable thrownException, @Args Object args)
   {
      finally8 = "void,Throwable,Object";
   }
   
   public void finally8(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally8 = "void,Serializable,Object[]";
   }
   
   public void finally8(@Thrown Serializable thrownException, @Args Object args)
   {
      finally8 = "void,Serializable,Object";
   }
   
   public void finally8(@Thrown Throwable thrownException)
   {
      finally8 = "void,Throwable";
   }
   
   public void finally8(@Thrown Serializable thrownException)
   {
      finally8 = "void,Serializable";
   }
   
   public void finally8(@Arg int arg1, @Arg long arg2)
   {
      finally8 = "void,int,long";
   }
   
   public String finally8(@Arg long arg2)
   {
      finally8 = "String,long";
      return "finally8";
   }
   
   public Object finally8(@Arg int arg1)
   {
      finally8 = "Object,int";
      return "finally8";
   }
   
   public void finally8(@Args Object[] args)
   {
      finally8 = "void,Object[]";
   }
   
   public void finally8(@Args Object args)
   {
      finally8 = "void,Object";
   }
   
   public void finally8()
   {
      finally8 = "void";
   }
   
   /* FINALLY9 ADVICE */
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Arg long arg2)
   {
      finally9 = "void,MethodExecution,CharSequence,Throwable,long";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally9 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally9 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally9 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally9 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally9 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally9 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally9 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally9 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally9 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally9 = "Object,MethodExecution,Throwable,long";
      return "finally9";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally9 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally9 = "String,MethodExecution,Serializable,int";
      return "finally9";
   }
   
   public Object finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally9 = "Object,MethodExecution,Serializable,long";
      return "finally9";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally9 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally9 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally9 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally9 = "void,MethodExecution,Throwable";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally9 = "void,MethodExecution,Serializable";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally9 = "void,MethodExecution,int,long";
   }
   
   public String finally9(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally9 = "String,MethodExecution,long";
      return "finally9";
   }
   
   public Object finally9(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally9 = "Object,MethodExecution,int";
      return "finally9";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally9 = "void,MethodExecution,Object[]";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally9 = "void,MethodExecution,Object";
   }
   
   public void finally9(@JoinPoint MethodExecution joinPoint)
   {
      finally9 = "void,MethodExecution";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,String,Throwable,int,long";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,String,Serializable,int,long";
   }
   
   public void finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally9(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally9 = "String,String,Throwable,int";
      return "finally9";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally9 = "void,String,Throwable,long";
   }
   
   public String finally9(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally9 = "String,String,Serializable,long";
      return "finally9";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally9 = "void,String,Serializable,int";
   }
   
   public Object finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally9 = "Object,CharSequence,Throwable,int";
      return "finally9";
   }
   
   public void finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally9 = "void,CharSequence,Throwable,long";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "void,String,Throwable,Object[]";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally9 = "void,String,Throwable,Object";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally9 = "void,String,Serializable,Object[]";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally9 = "void,String,Serializable,Object";
   }
   
   public void finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally9 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally9 = "void,String,Throwable";
   }
   
   public void finally9(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally9 = "void,String,Serializable";
   }
   
   public void finally9(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally9 = "void,CharSequence,Throwable";
   }
   
   public void finally9(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,Throwable,int,long";
   }
   
   public void finally9(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally9 = "void,Serializable,int,long";
   }
      
   public Object finally9(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally9 = "Object,Throwable,long";
      return "finally9";
   }
   
   public void finally9(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally9 = "void,Throwable,int";
   }
   
   public String finally9(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally9 = "String,Serializable,int";
      return "finally9";
   }
   
   public Object finally9(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally9 = "Object,Serializable,long";
      return "finally9";
   }
   
   public void finally9(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally9 = "void,Throwable,Object[]";
   }
   
   public void finally9(@Thrown Throwable thrownException, @Args Object args)
   {
      finally9 = "void,Throwable,Object";
   }
   
   public void finally9(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally9 = "void,Serializable,Object[]";
   }
   
   public void finally9(@Thrown Serializable thrownException, @Args Object args)
   {
      finally9 = "void,Serializable,Object";
   }
   
   public void finally9(@Thrown Throwable thrownException)
   {
      finally9 = "void,Throwable";
   }
   
   public void finally9(@Thrown Serializable thrownException)
   {
      finally9 = "void,Serializable";
   }
   
   public void finally9(@Arg int arg1, @Arg long arg2)
   {
      finally9 = "void,int,long";
   }
   
   public String finally9(@Arg long arg2)
   {
      finally9 = "String,long";
      return "finally9";
   }
   
   public Object finally9(@Arg int arg1)
   {
      finally9 = "Object,int";
      return "finally9";
   }
   
   public void finally9(@Args Object[] args)
   {
      finally9 = "void,Object[]";
   }
   
   public void finally9(@Args Object args)
   {
      finally9 = "void,Object";
   }
   
   public void finally9()
   {
      finally9 = "void";
   }
   
   /* FINALLY10 ADVICE */
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally10 = "void,MethodExecution,String,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally10 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally10 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally10 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally10 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally10 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally10 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally10 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally10 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally10 = "Object,MethodExecution,Throwable,long";
      return "finally10";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally10 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally10 = "String,MethodExecution,Serializable,int";
      return "finally10";
   }
   
   public Object finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally10 = "Object,MethodExecution,Serializable,long";
      return "finally10";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally10 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally10 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally10 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally10 = "void,MethodExecution,Throwable";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally10 = "void,MethodExecution,Serializable";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally10 = "void,MethodExecution,int,long";
   }
   
   public String finally10(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally10 = "String,MethodExecution,long";
      return "finally10";
   }
   
   public Object finally10(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally10 = "Object,MethodExecution,int";
      return "finally10";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally10 = "void,MethodExecution,Object[]";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally10 = "void,MethodExecution,Object";
   }
   
   public void finally10(@JoinPoint MethodExecution joinPoint)
   {
      finally10 = "void,MethodExecution";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,String,Throwable,int,long";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,String,Serializable,int,long";
   }
   
   public void finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally10(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally10 = "String,String,Throwable,int";
      return "finally10";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally10 = "void,String,Throwable,long";
   }
   
   public String finally10(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally10 = "String,String,Serializable,long";
      return "finally10";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally10 = "void,String,Serializable,int";
   }
   
   public Object finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally10 = "Object,CharSequence,Throwable,int";
      return "finally10";
   }
   
   public void finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally10 = "void,CharSequence,Throwable,long";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "void,String,Throwable,Object[]";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally10 = "void,String,Throwable,Object";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally10 = "void,String,Serializable,Object[]";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally10 = "void,String,Serializable,Object";
   }
   
   public void finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally10 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally10 = "void,String,Throwable";
   }
   
   public void finally10(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally10 = "void,String,Serializable";
   }
   
   public void finally10(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally10 = "void,CharSequence,Throwable";
   }
   
   public void finally10(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,Throwable,int,long";
   }
   
   public void finally10(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally10 = "void,Serializable,int,long";
   }
      
   public Object finally10(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally10 = "Object,Throwable,long";
      return "finally10";
   }
   
   public void finally10(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally10 = "void,Throwable,int";
   }
   
   public String finally10(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally10 = "String,Serializable,int";
      return "finally10";
   }
   
   public Object finally10(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally10 = "Object,Serializable,long";
      return "finally10";
   }
   
   public void finally10(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally10 = "void,Throwable,Object[]";
   }
   
   public void finally10(@Thrown Throwable thrownException, @Args Object args)
   {
      finally10 = "void,Throwable,Object";
   }
   
   public void finally10(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally10 = "void,Serializable,Object[]";
   }
   
   public void finally10(@Thrown Serializable thrownException, @Args Object args)
   {
      finally10 = "void,Serializable,Object";
   }
   
   public void finally10(@Thrown Throwable thrownException)
   {
      finally10 = "void,Throwable";
   }
   
   public void finally10(@Thrown Serializable thrownException)
   {
      finally10 = "void,Serializable";
   }
   
   public void finally10(@Arg int arg1, @Arg long arg2)
   {
      finally10 = "void,int,long";
   }
   
   public String finally10(@Arg long arg2)
   {
      finally10 = "String,long";
      return "finally10";
   }
   
   public Object finally10(@Arg int arg1)
   {
      finally10 = "Object,int";
      return "finally10";
   }
   
   public void finally10(@Args Object[] args)
   {
      finally10 = "void,Object[]";
   }
   
   public void finally10(@Args Object args)
   {
      finally10 = "void,Object";
   }
   
   public void finally10()
   {
      finally10 = "void";
   }
   
   /* FINALLY11 ADVICE */
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally11 = "void,MethodExecution,String,Throwable,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally11 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally11 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally11 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally11 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally11 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally11 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally11 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally11 = "Object,MethodExecution,Throwable,long";
      return "finally11";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally11 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally11 = "String,MethodExecution,Serializable,int";
      return "finally11";
   }
   
   public Object finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally11 = "Object,MethodExecution,Serializable,long";
      return "finally11";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally11 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally11 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally11 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally11 = "void,MethodExecution,Throwable";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally11 = "void,MethodExecution,Serializable";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally11 = "void,MethodExecution,int,long";
   }
   
   public String finally11(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally11 = "String,MethodExecution,long";
      return "finally11";
   }
   
   public Object finally11(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally11 = "Object,MethodExecution,int";
      return "finally11";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally11 = "void,MethodExecution,Object[]";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally11 = "void,MethodExecution,Object";
   }
   
   public void finally11(@JoinPoint MethodExecution joinPoint)
   {
      finally11 = "void,MethodExecution";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,String,Throwable,int,long";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,String,Serializable,int,long";
   }
   
   public void finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally11(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally11 = "String,String,Throwable,int";
      return "finally11";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally11 = "void,String,Throwable,long";
   }
   
   public String finally11(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally11 = "String,String,Serializable,long";
      return "finally11";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally11 = "void,String,Serializable,int";
   }
   
   public Object finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally11 = "Object,CharSequence,Throwable,int";
      return "finally11";
   }
   
   public void finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally11 = "void,CharSequence,Throwable,long";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "void,String,Throwable,Object[]";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally11 = "void,String,Throwable,Object";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally11 = "void,String,Serializable,Object[]";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally11 = "void,String,Serializable,Object";
   }
   
   public void finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally11 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally11 = "void,String,Throwable";
   }
   
   public void finally11(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally11 = "void,String,Serializable";
   }
   
   public void finally11(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally11 = "void,CharSequence,Throwable";
   }
   
   public void finally11(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,Throwable,int,long";
   }
   
   public void finally11(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally11 = "void,Serializable,int,long";
   }
      
   public Object finally11(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally11 = "Object,Throwable,long";
      return "finally11";
   }
   
   public void finally11(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally11 = "void,Throwable,int";
   }
   
   public String finally11(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally11 = "String,Serializable,int";
      return "finally11";
   }
   
   public Object finally11(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally11 = "Object,Serializable,long";
      return "finally11";
   }
   
   public void finally11(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally11 = "void,Throwable,Object[]";
   }
   
   public void finally11(@Thrown Throwable thrownException, @Args Object args)
   {
      finally11 = "void,Throwable,Object";
   }
   
   public void finally11(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally11 = "void,Serializable,Object[]";
   }
   
   public void finally11(@Thrown Serializable thrownException, @Args Object args)
   {
      finally11 = "void,Serializable,Object";
   }
   
   public void finally11(@Thrown Throwable thrownException)
   {
      finally11 = "void,Throwable";
   }
   
   public void finally11(@Thrown Serializable thrownException)
   {
      finally11 = "void,Serializable";
   }
   
   public void finally11(@Arg int arg1, @Arg long arg2)
   {
      finally11 = "void,int,long";
   }
   
   public String finally11(@Arg long arg2)
   {
      finally11 = "String,long";
      return "finally11";
   }
   
   public Object finally11(@Arg int arg1)
   {
      finally11 = "Object,int";
      return "finally11";
   }
   
   public void finally11(@Args Object[] args)
   {
      finally11 = "void,Object[]";
   }
   
   public void finally11(@Args Object args)
   {
      finally11 = "void,Object";
   }
   
   public void finally11()
   {
      finally11 = "void";
   }
   
   /* FINALLY12 ADVICE */
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object[] args)
   {
      finally12 = "void,MethodExecution,String,Serializable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally12 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally12 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally12 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally12 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally12 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally12 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally12 = "Object,MethodExecution,Throwable,long";
      return "finally12";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally12 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally12 = "String,MethodExecution,Serializable,int";
      return "finally12";
   }
   
   public Object finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally12 = "Object,MethodExecution,Serializable,long";
      return "finally12";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally12 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally12 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally12 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally12 = "void,MethodExecution,Throwable";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally12 = "void,MethodExecution,Serializable";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally12 = "void,MethodExecution,int,long";
   }
   
   public String finally12(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally12 = "String,MethodExecution,long";
      return "finally12";
   }
   
   public Object finally12(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally12 = "Object,MethodExecution,int";
      return "finally12";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally12 = "void,MethodExecution,Object[]";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally12 = "void,MethodExecution,Object";
   }
   
   public void finally12(@JoinPoint MethodExecution joinPoint)
   {
      finally12 = "void,MethodExecution";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,String,Throwable,int,long";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,String,Serializable,int,long";
   }
   
   public void finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally12(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally12 = "String,String,Throwable,int";
      return "finally12";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally12 = "void,String,Throwable,long";
   }
   
   public String finally12(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally12 = "String,String,Serializable,long";
      return "finally12";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally12 = "void,String,Serializable,int";
   }
   
   public Object finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally12 = "Object,CharSequence,Throwable,int";
      return "finally12";
   }
   
   public void finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally12 = "void,CharSequence,Throwable,long";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "void,String,Throwable,Object[]";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally12 = "void,String,Throwable,Object";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally12 = "void,String,Serializable,Object[]";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally12 = "void,String,Serializable,Object";
   }
   
   public void finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally12 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally12 = "void,String,Throwable";
   }
   
   public void finally12(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally12 = "void,String,Serializable";
   }
   
   public void finally12(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally12 = "void,CharSequence,Throwable";
   }
   
   public void finally12(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,Throwable,int,long";
   }
   
   public void finally12(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally12 = "void,Serializable,int,long";
   }
      
   public Object finally12(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally12 = "Object,Throwable,long";
      return "finally12";
   }
   
   public void finally12(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally12 = "void,Throwable,int";
   }
   
   public String finally12(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally12 = "String,Serializable,int";
      return "finally12";
   }
   
   public Object finally12(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally12 = "Object,Serializable,long";
      return "finally12";
   }
   
   public void finally12(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally12 = "void,Throwable,Object[]";
   }
   
   public void finally12(@Thrown Throwable thrownException, @Args Object args)
   {
      finally12 = "void,Throwable,Object";
   }
   
   public void finally12(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally12 = "void,Serializable,Object[]";
   }
   
   public void finally12(@Thrown Serializable thrownException, @Args Object args)
   {
      finally12 = "void,Serializable,Object";
   }
   
   public void finally12(@Thrown Throwable thrownException)
   {
      finally12 = "void,Throwable";
   }
   
   public void finally12(@Thrown Serializable thrownException)
   {
      finally12 = "void,Serializable";
   }
   
   public void finally12(@Arg int arg1, @Arg long arg2)
   {
      finally12 = "void,int,long";
   }
   
   public String finally12(@Arg long arg2)
   {
      finally12 = "String,long";
      return "finally12";
   }
   
   public Object finally12(@Arg int arg1)
   {
      finally12 = "Object,int";
      return "finally12";
   }
   
   public void finally12(@Args Object[] args)
   {
      finally12 = "void,Object[]";
   }
   
   public void finally12(@Args Object args)
   {
      finally12 = "void,Object";
   }
   
   public void finally12()
   {
      finally12 = "void";
   }
   
   /* FINALLY13 ADVICE */
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException,
         @Args Object args)
   {
      finally13 = "void,MethodExecution,String,Serializable,Object";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally13 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally13 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally13 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally13 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally13 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally13 = "Object,MethodExecution,Throwable,long";
      return "finally13";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally13 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally13 = "String,MethodExecution,Serializable,int";
      return "finally13";
   }
   
   public Object finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally13 = "Object,MethodExecution,Serializable,long";
      return "finally13";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally13 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally13 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally13 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally13 = "void,MethodExecution,Throwable";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally13 = "void,MethodExecution,Serializable";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally13 = "void,MethodExecution,int,long";
   }
   
   public String finally13(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally13 = "String,MethodExecution,long";
      return "finally13";
   }
   
   public Object finally13(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally13 = "Object,MethodExecution,int";
      return "finally13";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally13 = "void,MethodExecution,Object[]";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally13 = "void,MethodExecution,Object";
   }
   
   public void finally13(@JoinPoint MethodExecution joinPoint)
   {
      finally13 = "void,MethodExecution";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,String,Throwable,int,long";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,String,Serializable,int,long";
   }
   
   public void finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally13(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally13 = "String,String,Throwable,int";
      return "finally13";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally13 = "void,String,Throwable,long";
   }
   
   public String finally13(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally13 = "String,String,Serializable,long";
      return "finally13";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally13 = "void,String,Serializable,int";
   }
   
   public Object finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally13 = "Object,CharSequence,Throwable,int";
      return "finally13";
   }
   
   public void finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally13 = "void,CharSequence,Throwable,long";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "void,String,Throwable,Object[]";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally13 = "void,String,Throwable,Object";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally13 = "void,String,Serializable,Object[]";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally13 = "void,String,Serializable,Object";
   }
   
   public void finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally13 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally13 = "void,String,Throwable";
   }
   
   public void finally13(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally13 = "void,String,Serializable";
   }
   
   public void finally13(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally13 = "void,CharSequence,Throwable";
   }
   
   public void finally13(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,Throwable,int,long";
   }
   
   public void finally13(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally13 = "void,Serializable,int,long";
   }
      
   public Object finally13(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally13 = "Object,Throwable,long";
      return "finally13";
   }
   
   public void finally13(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally13 = "void,Throwable,int";
   }
   
   public String finally13(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally13 = "String,Serializable,int";
      return "finally13";
   }
   
   public Object finally13(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally13 = "Object,Serializable,long";
      return "finally13";
   }
   
   public void finally13(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally13 = "void,Throwable,Object[]";
   }
   
   public void finally13(@Thrown Throwable thrownException, @Args Object args)
   {
      finally13 = "void,Throwable,Object";
   }
   
   public void finally13(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally13 = "void,Serializable,Object[]";
   }
   
   public void finally13(@Thrown Serializable thrownException, @Args Object args)
   {
      finally13 = "void,Serializable,Object";
   }
   
   public void finally13(@Thrown Throwable thrownException)
   {
      finally13 = "void,Throwable";
   }
   
   public void finally13(@Thrown Serializable thrownException)
   {
      finally13 = "void,Serializable";
   }
   
   public void finally13(@Arg int arg1, @Arg long arg2)
   {
      finally13 = "void,int,long";
   }
   
   public String finally13(@Arg long arg2)
   {
      finally13 = "String,long";
      return "finally13";
   }
   
   public Object finally13(@Arg int arg1)
   {
      finally13 = "Object,int";
      return "finally13";
   }
   
   public void finally13(@Args Object[] args)
   {
      finally13 = "void,Object[]";
   }
   
   public void finally13(@Args Object args)
   {
      finally13 = "void,Object";
   }
   
   public void finally13()
   {
      finally13 = "void";
   }
   
   /* FINALLY14 ADVICE */
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object[] args)
   {
      finally14 = "void,MethodExecution,CharSequence,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally14 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally14 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally14 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally14 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally14 = "Object,MethodExecution,Throwable,long";
      return "finally14";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally14 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally14 = "String,MethodExecution,Serializable,int";
      return "finally14";
   }
   
   public Object finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally14 = "Object,MethodExecution,Serializable,long";
      return "finally14";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally14 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally14 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally14 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally14 = "void,MethodExecution,Throwable";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally14 = "void,MethodExecution,Serializable";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally14 = "void,MethodExecution,int,long";
   }
   
   public String finally14(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally14 = "String,MethodExecution,long";
      return "finally14";
   }
   
   public Object finally14(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally14 = "Object,MethodExecution,int";
      return "finally14";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally14 = "void,MethodExecution,Object[]";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally14 = "void,MethodExecution,Object";
   }
   
   public void finally14(@JoinPoint MethodExecution joinPoint)
   {
      finally14 = "void,MethodExecution";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,String,Throwable,int,long";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,String,Serializable,int,long";
   }
   
   public void finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally14(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally14 = "String,String,Throwable,int";
      return "finally14";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally14 = "void,String,Throwable,long";
   }
   
   public String finally14(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally14 = "String,String,Serializable,long";
      return "finally14";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally14 = "void,String,Serializable,int";
   }
   
   public Object finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally14 = "Object,CharSequence,Throwable,int";
      return "finally14";
   }
   
   public void finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally14 = "void,CharSequence,Throwable,long";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "void,String,Throwable,Object[]";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally14 = "void,String,Throwable,Object";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally14 = "void,String,Serializable,Object[]";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally14 = "void,String,Serializable,Object";
   }
   
   public void finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally14 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally14 = "void,String,Throwable";
   }
   
   public void finally14(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally14 = "void,String,Serializable";
   }
   
   public void finally14(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally14 = "void,CharSequence,Throwable";
   }
   
   public void finally14(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,Throwable,int,long";
   }
   
   public void finally14(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally14 = "void,Serializable,int,long";
   }
      
   public Object finally14(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally14 = "Object,Throwable,long";
      return "finally14";
   }
   
   public void finally14(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally14 = "void,Throwable,int";
   }
   
   public String finally14(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally14 = "String,Serializable,int";
      return "finally14";
   }
   
   public Object finally14(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally14 = "Object,Serializable,long";
      return "finally14";
   }
   
   public void finally14(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally14 = "void,Throwable,Object[]";
   }
   
   public void finally14(@Thrown Throwable thrownException, @Args Object args)
   {
      finally14 = "void,Throwable,Object";
   }
   
   public void finally14(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally14 = "void,Serializable,Object[]";
   }
   
   public void finally14(@Thrown Serializable thrownException, @Args Object args)
   {
      finally14 = "void,Serializable,Object";
   }
   
   public void finally14(@Thrown Throwable thrownException)
   {
      finally14 = "void,Throwable";
   }
   
   public void finally14(@Thrown Serializable thrownException)
   {
      finally14 = "void,Serializable";
   }
   
   public void finally14(@Arg int arg1, @Arg long arg2)
   {
      finally14 = "void,int,long";
   }
   
   public String finally14(@Arg long arg2)
   {
      finally14 = "String,long";
      return "finally14";
   }
   
   public Object finally14(@Arg int arg1)
   {
      finally14 = "Object,int";
      return "finally14";
   }
   
   public void finally14(@Args Object[] args)
   {
      finally14 = "void,Object[]";
   }
   
   public void finally14(@Args Object args)
   {
      finally14 = "void,Object";
   }
   
   public void finally14()
   {
      finally14 = "void";
   }
   
   /* FINALLY15 ADVICE */
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException,
         @Args Object args)
   {
      finally15 = "void,MethodExecution,CharSequence,Throwable,Object";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally15 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally15 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally15 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally15 = "Object,MethodExecution,Throwable,long";
      return "finally15";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally15 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally15 = "String,MethodExecution,Serializable,int";
      return "finally15";
   }
   
   public Object finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally15 = "Object,MethodExecution,Serializable,long";
      return "finally15";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally15 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally15 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally15 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally15 = "void,MethodExecution,Throwable";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally15 = "void,MethodExecution,Serializable";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally15 = "void,MethodExecution,int,long";
   }
   
   public String finally15(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally15 = "String,MethodExecution,long";
      return "finally15";
   }
   
   public Object finally15(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally15 = "Object,MethodExecution,int";
      return "finally15";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally15 = "void,MethodExecution,Object[]";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally15 = "void,MethodExecution,Object";
   }
   
   public void finally15(@JoinPoint MethodExecution joinPoint)
   {
      finally15 = "void,MethodExecution";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,String,Throwable,int,long";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,String,Serializable,int,long";
   }
   
   public void finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally15(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally15 = "String,String,Throwable,int";
      return "finally15";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally15 = "void,String,Throwable,long";
   }
   
   public String finally15(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally15 = "String,String,Serializable,long";
      return "finally15";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally15 = "void,String,Serializable,int";
   }
   
   public Object finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally15 = "Object,CharSequence,Throwable,int";
      return "finally15";
   }
   
   public void finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally15 = "void,CharSequence,Throwable,long";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "void,String,Throwable,Object[]";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally15 = "void,String,Throwable,Object";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally15 = "void,String,Serializable,Object[]";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally15 = "void,String,Serializable,Object";
   }
   
   public void finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally15 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally15 = "void,String,Throwable";
   }
   
   public void finally15(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally15 = "void,String,Serializable";
   }
   
   public void finally15(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally15 = "void,CharSequence,Throwable";
   }
   
   public void finally15(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,Throwable,int,long";
   }
   
   public void finally15(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally15 = "void,Serializable,int,long";
   }
      
   public Object finally15(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally15 = "Object,Throwable,long";
      return "finally15";
   }
   
   public void finally15(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally15 = "void,Throwable,int";
   }
   
   public String finally15(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally15 = "String,Serializable,int";
      return "finally15";
   }
   
   public Object finally15(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally15 = "Object,Serializable,long";
      return "finally15";
   }
   
   public void finally15(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally15 = "void,Throwable,Object[]";
   }
   
   public void finally15(@Thrown Throwable thrownException, @Args Object args)
   {
      finally15 = "void,Throwable,Object";
   }
   
   public void finally15(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally15 = "void,Serializable,Object[]";
   }
   
   public void finally15(@Thrown Serializable thrownException, @Args Object args)
   {
      finally15 = "void,Serializable,Object";
   }
   
   public void finally15(@Thrown Throwable thrownException)
   {
      finally15 = "void,Throwable";
   }
   
   public void finally15(@Thrown Serializable thrownException)
   {
      finally15 = "void,Serializable";
   }
   
   public void finally15(@Arg int arg1, @Arg long arg2)
   {
      finally15 = "void,int,long";
   }
   
   public String finally15(@Arg long arg2)
   {
      finally15 = "String,long";
      return "finally15";
   }
   
   public Object finally15(@Arg int arg1)
   {
      finally15 = "Object,int";
      return "finally15";
   }
   
   public void finally15(@Args Object[] args)
   {
      finally15 = "void,Object[]";
   }
   
   public void finally15(@Args Object args)
   {
      finally15 = "void,Object";
   }
   
   public void finally15()
   {
      finally15 = "void";
   }
   
   /* FINALLY16 ADVICE */
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Throwable thrownException)
   {
      finally16 = "void,MethodExecution,String,Throwable";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally16 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally16 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally16 = "Object,MethodExecution,Throwable,long";
      return "finally16";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally16 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally16 = "String,MethodExecution,Serializable,int";
      return "finally16";
   }
   
   public Object finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally16 = "Object,MethodExecution,Serializable,long";
      return "finally16";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally16 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally16 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally16 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally16 = "void,MethodExecution,Throwable";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally16 = "void,MethodExecution,Serializable";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally16 = "void,MethodExecution,int,long";
   }
   
   public String finally16(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally16 = "String,MethodExecution,long";
      return "finally16";
   }
   
   public Object finally16(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally16 = "Object,MethodExecution,int";
      return "finally16";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally16 = "void,MethodExecution,Object[]";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally16 = "void,MethodExecution,Object";
   }
   
   public void finally16(@JoinPoint MethodExecution joinPoint)
   {
      finally16 = "void,MethodExecution";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,String,Throwable,int,long";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,String,Serializable,int,long";
   }
   
   public void finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally16(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally16 = "String,String,Throwable,int";
      return "finally16";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally16 = "void,String,Throwable,long";
   }
   
   public String finally16(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally16 = "String,String,Serializable,long";
      return "finally16";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally16 = "void,String,Serializable,int";
   }
   
   public Object finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally16 = "Object,CharSequence,Throwable,int";
      return "finally16";
   }
   
   public void finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally16 = "void,CharSequence,Throwable,long";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "void,String,Throwable,Object[]";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally16 = "void,String,Throwable,Object";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally16 = "void,String,Serializable,Object[]";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally16 = "void,String,Serializable,Object";
   }
   
   public void finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally16 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally16 = "void,String,Throwable";
   }
   
   public void finally16(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally16 = "void,String,Serializable";
   }
   
   public void finally16(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally16 = "void,CharSequence,Throwable";
   }
   
   public void finally16(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,Throwable,int,long";
   }
   
   public void finally16(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally16 = "void,Serializable,int,long";
   }
      
   public Object finally16(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally16 = "Object,Throwable,long";
      return "finally16";
   }
   
   public void finally16(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally16 = "void,Throwable,int";
   }
   
   public String finally16(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally16 = "String,Serializable,int";
      return "finally16";
   }
   
   public Object finally16(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally16 = "Object,Serializable,long";
      return "finally16";
   }
   
   public void finally16(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally16 = "void,Throwable,Object[]";
   }
   
   public void finally16(@Thrown Throwable thrownException, @Args Object args)
   {
      finally16 = "void,Throwable,Object";
   }
   
   public void finally16(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally16 = "void,Serializable,Object[]";
   }
   
   public void finally16(@Thrown Serializable thrownException, @Args Object args)
   {
      finally16 = "void,Serializable,Object";
   }
   
   public void finally16(@Thrown Throwable thrownException)
   {
      finally16 = "void,Throwable";
   }
   
   public void finally16(@Thrown Serializable thrownException)
   {
      finally16 = "void,Serializable";
   }
   
   public void finally16(@Arg int arg1, @Arg long arg2)
   {
      finally16 = "void,int,long";
   }
   
   public String finally16(@Arg long arg2)
   {
      finally16 = "String,long";
      return "finally16";
   }
   
   public Object finally16(@Arg int arg1)
   {
      finally16 = "Object,int";
      return "finally16";
   }
   
   public void finally16(@Args Object[] args)
   {
      finally16 = "void,Object[]";
   }
   
   public void finally16(@Args Object args)
   {
      finally16 = "void,Object";
   }
   
   public void finally16()
   {
      finally16 = "void";
   }
   
   /* FINALLY17 ADVICE */
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Return String valueReturned, @Thrown Serializable thrownException)
   {
      finally17 = "void,MethodExecution,String,Serializable";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally17 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally17 = "Object,MethodExecution,Throwable,long";
      return "finally17";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally17 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally17 = "String,MethodExecution,Serializable,int";
      return "finally17";
   }
   
   public Object finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally17 = "Object,MethodExecution,Serializable,long";
      return "finally17";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally17 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally17 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally17 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally17 = "void,MethodExecution,Throwable";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally17 = "void,MethodExecution,Serializable";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally17 = "void,MethodExecution,int,long";
   }
   
   public String finally17(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally17 = "String,MethodExecution,long";
      return "finally17";
   }
   
   public Object finally17(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally17 = "Object,MethodExecution,int";
      return "finally17";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally17 = "void,MethodExecution,Object[]";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally17 = "void,MethodExecution,Object";
   }
   
   public void finally17(@JoinPoint MethodExecution joinPoint)
   {
      finally17 = "void,MethodExecution";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,String,Throwable,int,long";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,String,Serializable,int,long";
   }
   
   public void finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally17(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally17 = "String,String,Throwable,int";
      return "finally17";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally17 = "void,String,Throwable,long";
   }
   
   public String finally17(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally17 = "String,String,Serializable,long";
      return "finally17";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally17 = "void,String,Serializable,int";
   }
   
   public Object finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally17 = "Object,CharSequence,Throwable,int";
      return "finally17";
   }
   
   public void finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally17 = "void,CharSequence,Throwable,long";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "void,String,Throwable,Object[]";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally17 = "void,String,Throwable,Object";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally17 = "void,String,Serializable,Object[]";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally17 = "void,String,Serializable,Object";
   }
   
   public void finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally17 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally17 = "void,String,Throwable";
   }
   
   public void finally17(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally17 = "void,String,Serializable";
   }
   
   public void finally17(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally17 = "void,CharSequence,Throwable";
   }
   
   public void finally17(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,Throwable,int,long";
   }
   
   public void finally17(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally17 = "void,Serializable,int,long";
   }
      
   public Object finally17(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally17 = "Object,Throwable,long";
      return "finally17";
   }
   
   public void finally17(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally17 = "void,Throwable,int";
   }
   
   public String finally17(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally17 = "String,Serializable,int";
      return "finally17";
   }
   
   public Object finally17(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally17 = "Object,Serializable,long";
      return "finally17";
   }
   
   public void finally17(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally17 = "void,Throwable,Object[]";
   }
   
   public void finally17(@Thrown Throwable thrownException, @Args Object args)
   {
      finally17 = "void,Throwable,Object";
   }
   
   public void finally17(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally17 = "void,Serializable,Object[]";
   }
   
   public void finally17(@Thrown Serializable thrownException, @Args Object args)
   {
      finally17 = "void,Serializable,Object";
   }
   
   public void finally17(@Thrown Throwable thrownException)
   {
      finally17 = "void,Throwable";
   }
   
   public void finally17(@Thrown Serializable thrownException)
   {
      finally17 = "void,Serializable";
   }
   
   public void finally17(@Arg int arg1, @Arg long arg2)
   {
      finally17 = "void,int,long";
   }
   
   public String finally17(@Arg long arg2)
   {
      finally17 = "String,long";
      return "finally17";
   }
   
   public Object finally17(@Arg int arg1)
   {
      finally17 = "Object,int";
      return "finally17";
   }
   
   public void finally17(@Args Object[] args)
   {
      finally17 = "void,Object[]";
   }
   
   public void finally17(@Args Object args)
   {
      finally17 = "void,Object";
   }
   
   public void finally17()
   {
      finally17 = "void";
   }
   
   /* FINALLY18 ADVICE */
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Return CharSequence valueReturned, @Thrown Throwable thrownException)
   {
      finally18 = "void,MethodExecution,CharSequence,Throwable";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally18 = "Object,MethodExecution,Throwable,long";
      return "finally18";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally18 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally18 = "String,MethodExecution,Serializable,int";
      return "finally18";
   }
   
   public Object finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally18 = "Object,MethodExecution,Serializable,long";
      return "finally18";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally18 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally18 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally18 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally18 = "void,MethodExecution,Throwable";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally18 = "void,MethodExecution,Serializable";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally18 = "void,MethodExecution,int,long";
   }
   
   public String finally18(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally18 = "String,MethodExecution,long";
      return "finally18";
   }
   
   public Object finally18(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally18 = "Object,MethodExecution,int";
      return "finally18";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally18 = "void,MethodExecution,Object[]";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally18 = "void,MethodExecution,Object";
   }
   
   public void finally18(@JoinPoint MethodExecution joinPoint)
   {
      finally18 = "void,MethodExecution";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,String,Throwable,int,long";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,String,Serializable,int,long";
   }
   
   public void finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally18(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally18 = "String,String,Throwable,int";
      return "finally18";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally18 = "void,String,Throwable,long";
   }
   
   public String finally18(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally18 = "String,String,Serializable,long";
      return "finally18";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally18 = "void,String,Serializable,int";
   }
   
   public Object finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally18 = "Object,CharSequence,Throwable,int";
      return "finally18";
   }
   
   public void finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally18 = "void,CharSequence,Throwable,long";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "void,String,Throwable,Object[]";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally18 = "void,String,Throwable,Object";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally18 = "void,String,Serializable,Object[]";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally18 = "void,String,Serializable,Object";
   }
   
   public void finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally18 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally18 = "void,String,Throwable";
   }
   
   public void finally18(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally18 = "void,String,Serializable";
   }
   
   public void finally18(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally18 = "void,CharSequence,Throwable";
   }
   
   public void finally18(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,Throwable,int,long";
   }
   
   public void finally18(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally18 = "void,Serializable,int,long";
   }
      
   public Object finally18(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally18 = "Object,Throwable,long";
      return "finally18";
   }
   
   public void finally18(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally18 = "void,Throwable,int";
   }
   
   public String finally18(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally18 = "String,Serializable,int";
      return "finally18";
   }
   
   public Object finally18(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally18 = "Object,Serializable,long";
      return "finally18";
   }
   
   public void finally18(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally18 = "void,Throwable,Object[]";
   }
   
   public void finally18(@Thrown Throwable thrownException, @Args Object args)
   {
      finally18 = "void,Throwable,Object";
   }
   
   public void finally18(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally18 = "void,Serializable,Object[]";
   }
   
   public void finally18(@Thrown Serializable thrownException, @Args Object args)
   {
      finally18 = "void,Serializable,Object";
   }
   
   public void finally18(@Thrown Throwable thrownException)
   {
      finally18 = "void,Throwable";
   }
   
   public void finally18(@Thrown Serializable thrownException)
   {
      finally18 = "void,Serializable";
   }
   
   public void finally18(@Arg int arg1, @Arg long arg2)
   {
      finally18 = "void,int,long";
   }
   
   public String finally18(@Arg long arg2)
   {
      finally18 = "String,long";
      return "finally18";
   }
   
   public Object finally18(@Arg int arg1)
   {
      finally18 = "Object,int";
      return "finally18";
   }
   
   public void finally18(@Args Object[] args)
   {
      finally18 = "void,Object[]";
   }
   
   public void finally18(@Args Object args)
   {
      finally18 = "void,Object";
   }
   
   public void finally18()
   {
      finally18 = "void";
   }
   
   /* FINALLY19 ADVICE */
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,MethodExecution,Throwable,int,long";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally19 = "Object,MethodExecution,Throwable,long";
      return "finally19";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally19 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally19 = "String,MethodExecution,Serializable,int";
      return "finally19";
   }
   
   public Object finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally19 = "Object,MethodExecution,Serializable,long";
      return "finally19";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally19 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally19 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally19 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally19 = "void,MethodExecution,Throwable";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally19 = "void,MethodExecution,Serializable";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally19 = "void,MethodExecution,int,long";
   }
   
   public String finally19(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally19 = "String,MethodExecution,long";
      return "finally19";
   }
   
   public Object finally19(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally19 = "Object,MethodExecution,int";
      return "finally19";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally19 = "void,MethodExecution,Object[]";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally19 = "void,MethodExecution,Object";
   }
   
   public void finally19(@JoinPoint MethodExecution joinPoint)
   {
      finally19 = "void,MethodExecution";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,String,Throwable,int,long";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,String,Serializable,int,long";
   }
   
   public void finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally19(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally19 = "String,String,Throwable,int";
      return "finally19";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally19 = "void,String,Throwable,long";
   }
   
   public String finally19(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally19 = "String,String,Serializable,long";
      return "finally19";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally19 = "void,String,Serializable,int";
   }
   
   public Object finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally19 = "Object,CharSequence,Throwable,int";
      return "finally19";
   }
   
   public void finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally19 = "void,CharSequence,Throwable,long";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "void,String,Throwable,Object[]";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally19 = "void,String,Throwable,Object";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally19 = "void,String,Serializable,Object[]";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally19 = "void,String,Serializable,Object";
   }
   
   public void finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally19 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally19 = "void,String,Throwable";
   }
   
   public void finally19(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally19 = "void,String,Serializable";
   }
   
   public void finally19(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally19 = "void,CharSequence,Throwable";
   }
   
   public void finally19(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,Throwable,int,long";
   }
   
   public void finally19(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally19 = "void,Serializable,int,long";
   }
      
   public Object finally19(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally19 = "Object,Throwable,long";
      return "finally19";
   }
   
   public void finally19(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally19 = "void,Throwable,int";
   }
   
   public String finally19(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally19 = "String,Serializable,int";
      return "finally19";
   }
   
   public Object finally19(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally19 = "Object,Serializable,long";
      return "finally19";
   }
   
   public void finally19(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally19 = "void,Throwable,Object[]";
   }
   
   public void finally19(@Thrown Throwable thrownException, @Args Object args)
   {
      finally19 = "void,Throwable,Object";
   }
   
   public void finally19(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally19 = "void,Serializable,Object[]";
   }
   
   public void finally19(@Thrown Serializable thrownException, @Args Object args)
   {
      finally19 = "void,Serializable,Object";
   }
   
   public void finally19(@Thrown Throwable thrownException)
   {
      finally19 = "void,Throwable";
   }
   
   public void finally19(@Thrown Serializable thrownException)
   {
      finally19 = "void,Serializable";
   }
   
   public void finally19(@Arg int arg1, @Arg long arg2)
   {
      finally19 = "void,int,long";
   }
   
   public String finally19(@Arg long arg2)
   {
      finally19 = "String,long";
      return "finally19";
   }
   
   public Object finally19(@Arg int arg1)
   {
      finally19 = "Object,int";
      return "finally19";
   }
   
   public void finally19(@Args Object[] args)
   {
      finally19 = "void,Object[]";
   }
   
   public void finally19(@Args Object args)
   {
      finally19 = "void,Object";
   }
   
   public void finally19()
   {
      finally19 = "void";
   }
   
   /* FINALLY20 ADVICE */
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,MethodExecution,Serializable,int,long";
   }
      
   public Object finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally20 = "Object,MethodExecution,Throwable,long";
      return "finally20";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally20 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally20 = "String,MethodExecution,Serializable,int";
      return "finally20";
   }
   
   public Object finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally20 = "Object,MethodExecution,Serializable,long";
      return "finally20";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally20 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally20 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally20 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally20 = "void,MethodExecution,Throwable";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally20 = "void,MethodExecution,Serializable";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally20 = "void,MethodExecution,int,long";
   }
   
   public String finally20(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally20 = "String,MethodExecution,long";
      return "finally20";
   }
   
   public Object finally20(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally20 = "Object,MethodExecution,int";
      return "finally20";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally20 = "void,MethodExecution,Object[]";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally20 = "void,MethodExecution,Object";
   }
   
   public void finally20(@JoinPoint MethodExecution joinPoint)
   {
      finally20 = "void,MethodExecution";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,String,Throwable,int,long";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,String,Serializable,int,long";
   }
   
   public void finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally20(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally20 = "String,String,Throwable,int";
      return "finally20";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally20 = "void,String,Throwable,long";
   }
   
   public String finally20(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally20 = "String,String,Serializable,long";
      return "finally20";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally20 = "void,String,Serializable,int";
   }
   
   public Object finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally20 = "Object,CharSequence,Throwable,int";
      return "finally20";
   }
   
   public void finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally20 = "void,CharSequence,Throwable,long";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "void,String,Throwable,Object[]";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally20 = "void,String,Throwable,Object";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally20 = "void,String,Serializable,Object[]";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally20 = "void,String,Serializable,Object";
   }
   
   public void finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally20 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally20 = "void,String,Throwable";
   }
   
   public void finally20(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally20 = "void,String,Serializable";
   }
   
   public void finally20(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally20 = "void,CharSequence,Throwable";
   }
   
   public void finally20(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,Throwable,int,long";
   }
   
   public void finally20(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally20 = "void,Serializable,int,long";
   }
      
   public Object finally20(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally20 = "Object,Throwable,long";
      return "finally20";
   }
   
   public void finally20(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally20 = "void,Throwable,int";
   }
   
   public String finally20(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally20 = "String,Serializable,int";
      return "finally20";
   }
   
   public Object finally20(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally20 = "Object,Serializable,long";
      return "finally20";
   }
   
   public void finally20(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally20 = "void,Throwable,Object[]";
   }
   
   public void finally20(@Thrown Throwable thrownException, @Args Object args)
   {
      finally20 = "void,Throwable,Object";
   }
   
   public void finally20(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally20 = "void,Serializable,Object[]";
   }
   
   public void finally20(@Thrown Serializable thrownException, @Args Object args)
   {
      finally20 = "void,Serializable,Object";
   }
   
   public void finally20(@Thrown Throwable thrownException)
   {
      finally20 = "void,Throwable";
   }
   
   public void finally20(@Thrown Serializable thrownException)
   {
      finally20 = "void,Serializable";
   }
   
   public void finally20(@Arg int arg1, @Arg long arg2)
   {
      finally20 = "void,int,long";
   }
   
   public String finally20(@Arg long arg2)
   {
      finally20 = "String,long";
      return "finally20";
   }
   
   public Object finally20(@Arg int arg1)
   {
      finally20 = "Object,int";
      return "finally20";
   }
   
   public void finally20(@Args Object[] args)
   {
      finally20 = "void,Object[]";
   }
   
   public void finally20(@Args Object args)
   {
      finally20 = "void,Object";
   }
   
   public void finally20()
   {
      finally20 = "void";
   }
   
   /* FINALLY21 ADVICE */
   
   public Object finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally21 = "Object,MethodExecution,Throwable,long";
      return "finally21";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally21 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally21 = "String,MethodExecution,Serializable,int";
      return "finally21";
   }
   
   public Object finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally21 = "Object,MethodExecution,Serializable,long";
      return "finally21";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally21 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally21 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally21 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally21 = "void,MethodExecution,Throwable";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally21 = "void,MethodExecution,Serializable";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally21 = "void,MethodExecution,int,long";
   }
   
   public String finally21(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally21 = "String,MethodExecution,long";
      return "finally21";
   }
   
   public Object finally21(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally21 = "Object,MethodExecution,int";
      return "finally21";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally21 = "void,MethodExecution,Object[]";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally21 = "void,MethodExecution,Object";
   }
   
   public void finally21(@JoinPoint MethodExecution joinPoint)
   {
      finally21 = "void,MethodExecution";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally21 = "void,String,Throwable,int,long";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally21 = "void,String,Serializable,int,long";
   }
   
   public void finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally21 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally21(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally21 = "String,String,Throwable,int";
      return "finally21";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally21 = "void,String,Throwable,long";
   }
   
   public String finally21(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally21 = "String,String,Serializable,long";
      return "finally21";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally21 = "void,String,Serializable,int";
   }
   
   public Object finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally21 = "Object,CharSequence,Throwable,int";
      return "finally21";
   }
   
   public void finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally21 = "void,CharSequence,Throwable,long";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "void,String,Throwable,Object[]";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally21 = "void,String,Throwable,Object";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally21 = "void,String,Serializable,Object[]";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally21 = "void,String,Serializable,Object";
   }
   
   public void finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally21 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally21 = "void,String,Throwable";
   }
   
   public void finally21(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally21 = "void,String,Serializable";
   }
   
   public void finally21(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally21 = "void,CharSequence,Throwable";
   }
   
   public void finally21(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally21 = "void,Throwable,int,long";
   }
   
   public void finally21(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally21 = "void,Serializable,int,long";
   }
      
   public Object finally21(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally21 = "Object,Throwable,long";
      return "finally21";
   }
   
   public void finally21(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally21 = "void,Throwable,int";
   }
   
   public String finally21(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally21 = "String,Serializable,int";
      return "finally21";
   }
   
   public Object finally21(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally21 = "Object,Serializable,long";
      return "finally21";
   }
   
   public void finally21(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally21 = "void,Throwable,Object[]";
   }
   
   public void finally21(@Thrown Throwable thrownException, @Args Object args)
   {
      finally21 = "void,Throwable,Object";
   }
   
   public void finally21(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally21 = "void,Serializable,Object[]";
   }
   
   public void finally21(@Thrown Serializable thrownException, @Args Object args)
   {
      finally21 = "void,Serializable,Object";
   }
   
   public void finally21(@Thrown Throwable thrownException)
   {
      finally21 = "void,Throwable";
   }
   
   public void finally21(@Thrown Serializable thrownException)
   {
      finally21 = "void,Serializable";
   }
   
   public void finally21(@Arg int arg1, @Arg long arg2)
   {
      finally21 = "void,int,long";
   }
   
   public String finally21(@Arg long arg2)
   {
      finally21 = "String,long";
      return "finally21";
   }
   
   public Object finally21(@Arg int arg1)
   {
      finally21 = "Object,int";
      return "finally21";
   }
   
   public void finally21(@Args Object[] args)
   {
      finally21 = "void,Object[]";
   }
   
   public void finally21(@Args Object args)
   {
      finally21 = "void,Object";
   }
   
   public void finally21()
   {
      finally21 = "void";
   }
   
   /* FINALLY22 ADVICE */
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally22 = "void,MethodExecution,Throwable,int";
   }
   
   public String finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally22 = "String,MethodExecution,Serializable,int";
      return "finally22";
   }
   
   public Object finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally22 = "Object,MethodExecution,Serializable,long";
      return "finally22";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally22 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally22 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally22 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally22 = "void,MethodExecution,Throwable";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally22 = "void,MethodExecution,Serializable";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally22 = "void,MethodExecution,int,long";
   }
   
   public String finally22(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally22 = "String,MethodExecution,long";
      return "finally22";
   }
   
   public Object finally22(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally22 = "Object,MethodExecution,int";
      return "finally22";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally22 = "void,MethodExecution,Object[]";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally22 = "void,MethodExecution,Object";
   }
   
   public void finally22(@JoinPoint MethodExecution joinPoint)
   {
      finally22 = "void,MethodExecution";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally22 = "void,String,Throwable,int,long";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally22 = "void,String,Serializable,int,long";
   }
   
   public void finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally22 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally22(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally22 = "String,String,Throwable,int";
      return "finally22";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally22 = "void,String,Throwable,long";
   }
   
   public String finally22(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally22 = "String,String,Serializable,long";
      return "finally22";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally22 = "void,String,Serializable,int";
   }
   
   public Object finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally22 = "Object,CharSequence,Throwable,int";
      return "finally22";
   }
   
   public void finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally22 = "void,CharSequence,Throwable,long";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "void,String,Throwable,Object[]";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally22 = "void,String,Throwable,Object";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally22 = "void,String,Serializable,Object[]";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally22 = "void,String,Serializable,Object";
   }
   
   public void finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally22 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally22 = "void,String,Throwable";
   }
   
   public void finally22(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally22 = "void,String,Serializable";
   }
   
   public void finally22(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally22 = "void,CharSequence,Throwable";
   }
   
   public void finally22(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally22 = "void,Throwable,int,long";
   }
   
   public void finally22(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally22 = "void,Serializable,int,long";
   }
      
   public Object finally22(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally22 = "Object,Throwable,long";
      return "finally22";
   }
   
   public void finally22(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally22 = "void,Throwable,int";
   }
   
   public String finally22(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally22 = "String,Serializable,int";
      return "finally22";
   }
   
   public Object finally22(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally22 = "Object,Serializable,long";
      return "finally22";
   }
   
   public void finally22(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally22 = "void,Throwable,Object[]";
   }
   
   public void finally22(@Thrown Throwable thrownException, @Args Object args)
   {
      finally22 = "void,Throwable,Object";
   }
   
   public void finally22(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally22 = "void,Serializable,Object[]";
   }
   
   public void finally22(@Thrown Serializable thrownException, @Args Object args)
   {
      finally22 = "void,Serializable,Object";
   }
   
   public void finally22(@Thrown Throwable thrownException)
   {
      finally22 = "void,Throwable";
   }
   
   public void finally22(@Thrown Serializable thrownException)
   {
      finally22 = "void,Serializable";
   }
   
   public void finally22(@Arg int arg1, @Arg long arg2)
   {
      finally22 = "void,int,long";
   }
   
   public String finally22(@Arg long arg2)
   {
      finally22 = "String,long";
      return "finally22";
   }
   
   public Object finally22(@Arg int arg1)
   {
      finally22 = "Object,int";
      return "finally22";
   }
   
   public void finally22(@Args Object[] args)
   {
      finally22 = "void,Object[]";
   }
   
   public void finally22(@Args Object args)
   {
      finally22 = "void,Object";
   }
   
   public void finally22()
   {
      finally22 = "void";
   }
   
   /* FINALLY23 ADVICE */
   
   public String finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally23 = "String,MethodExecution,Serializable,int";
      return "finally23";
   }
   
   public Object finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally23 = "Object,MethodExecution,Serializable,long";
      return "finally23";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally23 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally23 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally23 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally23 = "void,MethodExecution,Throwable";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally23 = "void,MethodExecution,Serializable";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally23 = "void,MethodExecution,int,long";
   }
   
   public String finally23(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally23 = "String,MethodExecution,long";
      return "finally23";
   }
   
   public Object finally23(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally23 = "Object,MethodExecution,int";
      return "finally23";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally23 = "void,MethodExecution,Object[]";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally23 = "void,MethodExecution,Object";
   }
   
   public void finally23(@JoinPoint MethodExecution joinPoint)
   {
      finally23 = "void,MethodExecution";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally23 = "void,String,Throwable,int,long";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally23 = "void,String,Serializable,int,long";
   }
   
   public void finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally23 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally23(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally23 = "String,String,Throwable,int";
      return "finally23";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally23 = "void,String,Throwable,long";
   }
   
   public String finally23(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally23 = "String,String,Serializable,long";
      return "finally23";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally23 = "void,String,Serializable,int";
   }
   
   public Object finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally23 = "Object,CharSequence,Throwable,int";
      return "finally23";
   }
   
   public void finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally23 = "void,CharSequence,Throwable,long";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "void,String,Throwable,Object[]";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally23 = "void,String,Throwable,Object";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally23 = "void,String,Serializable,Object[]";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally23 = "void,String,Serializable,Object";
   }
   
   public void finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally23 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally23 = "void,String,Throwable";
   }
   
   public void finally23(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally23 = "void,String,Serializable";
   }
   
   public void finally23(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally23 = "void,CharSequence,Throwable";
   }
   
   public void finally23(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally23 = "void,Throwable,int,long";
   }
   
   public void finally23(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally23 = "void,Serializable,int,long";
   }
      
   public Object finally23(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally23 = "Object,Throwable,long";
      return "finally23";
   }
   
   public void finally23(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally23 = "void,Throwable,int";
   }
   
   public String finally23(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally23 = "String,Serializable,int";
      return "finally23";
   }
   
   public Object finally23(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally23 = "Object,Serializable,long";
      return "finally23";
   }
   
   public void finally23(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally23 = "void,Throwable,Object[]";
   }
   
   public void finally23(@Thrown Throwable thrownException, @Args Object args)
   {
      finally23 = "void,Throwable,Object";
   }
   
   public void finally23(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally23 = "void,Serializable,Object[]";
   }
   
   public void finally23(@Thrown Serializable thrownException, @Args Object args)
   {
      finally23 = "void,Serializable,Object";
   }
   
   public void finally23(@Thrown Throwable thrownException)
   {
      finally23 = "void,Throwable";
   }
   
   public void finally23(@Thrown Serializable thrownException)
   {
      finally23 = "void,Serializable";
   }
   
   public void finally23(@Arg int arg1, @Arg long arg2)
   {
      finally23 = "void,int,long";
   }
   
   public String finally23(@Arg long arg2)
   {
      finally23 = "String,long";
      return "finally23";
   }
   
   public Object finally23(@Arg int arg1)
   {
      finally23 = "Object,int";
      return "finally23";
   }
   
   public void finally23(@Args Object[] args)
   {
      finally23 = "void,Object[]";
   }
   
   public void finally23(@Args Object args)
   {
      finally23 = "void,Object";
   }
   
   public void finally23()
   {
      finally23 = "void";
   }
   
   /* FINALLY24 ADVICE */
   
   public Object finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Arg long arg1)
   {
      finally24 = "Object,MethodExecution,Serializable,long";
      return "finally24";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally24 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally24 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally24 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally24 = "void,MethodExecution,Throwable";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally24 = "void,MethodExecution,Serializable";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally24 = "void,MethodExecution,int,long";
   }
   
   public String finally24(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally24 = "String,MethodExecution,long";
      return "finally24";
   }
   
   public Object finally24(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally24 = "Object,MethodExecution,int";
      return "finally24";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally24 = "void,MethodExecution,Object[]";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally24 = "void,MethodExecution,Object";
   }
   
   public void finally24(@JoinPoint MethodExecution joinPoint)
   {
      finally24 = "void,MethodExecution";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally24 = "void,String,Throwable,int,long";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally24 = "void,String,Serializable,int,long";
   }
   
   public void finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally24 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally24(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally24 = "String,String,Throwable,int";
      return "finally24";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally24 = "void,String,Throwable,long";
   }
   
   public String finally24(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally24 = "String,String,Serializable,long";
      return "finally24";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally24 = "void,String,Serializable,int";
   }
   
   public Object finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally24 = "Object,CharSequence,Throwable,int";
      return "finally24";
   }
   
   public void finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally24 = "void,CharSequence,Throwable,long";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "void,String,Throwable,Object[]";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally24 = "void,String,Throwable,Object";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally24 = "void,String,Serializable,Object[]";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally24 = "void,String,Serializable,Object";
   }
   
   public void finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally24 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally24 = "void,String,Throwable";
   }
   
   public void finally24(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally24 = "void,String,Serializable";
   }
   
   public void finally24(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally24 = "void,CharSequence,Throwable";
   }
   
   public void finally24(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally24 = "void,Throwable,int,long";
   }
   
   public void finally24(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally24 = "void,Serializable,int,long";
   }
      
   public Object finally24(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally24 = "Object,Throwable,long";
      return "finally24";
   }
   
   public void finally24(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally24 = "void,Throwable,int";
   }
   
   public String finally24(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally24 = "String,Serializable,int";
      return "finally24";
   }
   
   public Object finally24(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally24 = "Object,Serializable,long";
      return "finally24";
   }
   
   public void finally24(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally24 = "void,Throwable,Object[]";
   }
   
   public void finally24(@Thrown Throwable thrownException, @Args Object args)
   {
      finally24 = "void,Throwable,Object";
   }
   
   public void finally24(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally24 = "void,Serializable,Object[]";
   }
   
   public void finally24(@Thrown Serializable thrownException, @Args Object args)
   {
      finally24 = "void,Serializable,Object";
   }
   
   public void finally24(@Thrown Throwable thrownException)
   {
      finally24 = "void,Throwable";
   }
   
   public void finally24(@Thrown Serializable thrownException)
   {
      finally24 = "void,Serializable";
   }
   
   public void finally24(@Arg int arg1, @Arg long arg2)
   {
      finally24 = "void,int,long";
   }
   
   public String finally24(@Arg long arg2)
   {
      finally24 = "String,long";
      return "finally24";
   }
   
   public Object finally24(@Arg int arg1)
   {
      finally24 = "Object,int";
      return "finally24";
   }
   
   public void finally24(@Args Object[] args)
   {
      finally24 = "void,Object[]";
   }
   
   public void finally24(@Args Object args)
   {
      finally24 = "void,Object";
   }
   
   public void finally24()
   {
      finally24 = "void";
   }
   
   /* FINALLY25 ADVICE */
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "void,MethodExecution,Throwable,Object[]";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally25 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally25 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally25 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally25 = "void,MethodExecution,Throwable";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally25 = "void,MethodExecution,Serializable";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally25 = "void,MethodExecution,int,long";
   }
   
   public String finally25(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally25 = "String,MethodExecution,long";
      return "finally25";
   }
   
   public Object finally25(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally25 = "Object,MethodExecution,int";
      return "finally25";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally25 = "void,MethodExecution,Object[]";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally25 = "void,MethodExecution,Object";
   }
   
   public void finally25(@JoinPoint MethodExecution joinPoint)
   {
      finally25 = "void,MethodExecution";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally25 = "void,String,Throwable,int,long";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally25 = "void,String,Serializable,int,long";
   }
   
   public void finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally25 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally25(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally25 = "String,String,Throwable,int";
      return "finally25";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally25 = "void,String,Throwable,long";
   }
   
   public String finally25(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally25 = "String,String,Serializable,long";
      return "finally25";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally25 = "void,String,Serializable,int";
   }
   
   public Object finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally25 = "Object,CharSequence,Throwable,int";
      return "finally25";
   }
   
   public void finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally25 = "void,CharSequence,Throwable,long";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "void,String,Throwable,Object[]";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally25 = "void,String,Throwable,Object";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally25 = "void,String,Serializable,Object[]";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally25 = "void,String,Serializable,Object";
   }
   
   public void finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally25 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally25 = "void,String,Throwable";
   }
   
   public void finally25(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally25 = "void,String,Serializable";
   }
   
   public void finally25(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally25 = "void,CharSequence,Throwable";
   }
   
   public void finally25(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally25 = "void,Throwable,int,long";
   }
   
   public void finally25(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally25 = "void,Serializable,int,long";
   }
      
   public Object finally25(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally25 = "Object,Throwable,long";
      return "finally25";
   }
   
   public void finally25(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally25 = "void,Throwable,int";
   }
   
   public String finally25(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally25 = "String,Serializable,int";
      return "finally25";
   }
   
   public Object finally25(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally25 = "Object,Serializable,long";
      return "finally25";
   }
   
   public void finally25(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally25 = "void,Throwable,Object[]";
   }
   
   public void finally25(@Thrown Throwable thrownException, @Args Object args)
   {
      finally25 = "void,Throwable,Object";
   }
   
   public void finally25(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally25 = "void,Serializable,Object[]";
   }
   
   public void finally25(@Thrown Serializable thrownException, @Args Object args)
   {
      finally25 = "void,Serializable,Object";
   }
   
   public void finally25(@Thrown Throwable thrownException)
   {
      finally25 = "void,Throwable";
   }
   
   public void finally25(@Thrown Serializable thrownException)
   {
      finally25 = "void,Serializable";
   }
   
   public void finally25(@Arg int arg1, @Arg long arg2)
   {
      finally25 = "void,int,long";
   }
   
   public String finally25(@Arg long arg2)
   {
      finally25 = "String,long";
      return "finally25";
   }
   
   public Object finally25(@Arg int arg1)
   {
      finally25 = "Object,int";
      return "finally25";
   }
   
   public void finally25(@Args Object[] args)
   {
      finally25 = "void,Object[]";
   }
   
   public void finally25(@Args Object args)
   {
      finally25 = "void,Object";
   }
   
   public void finally25()
   {
      finally25 = "void";
   }
   
   /* FINALLY26 ADVICE */
   
   public void finally26(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally26 = "void,MethodExecution,Throwable,Object";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally26 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally26 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally26 = "void,MethodExecution,Throwable";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally26 = "void,MethodExecution,Serializable";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally26 = "void,MethodExecution,int,long";
   }
   
   public String finally26(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally26 = "String,MethodExecution,long";
      return "finally26";
   }
   
   public Object finally26(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally26 = "Object,MethodExecution,int";
      return "finally26";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally26 = "void,MethodExecution,Object[]";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally26 = "void,MethodExecution,Object";
   }
   
   public void finally26(@JoinPoint MethodExecution joinPoint)
   {
      finally26 = "void,MethodExecution";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally26 = "void,String,Throwable,int,long";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally26 = "void,String,Serializable,int,long";
   }
   
   public void finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally26 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally26(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally26 = "String,String,Throwable,int";
      return "finally26";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally26 = "void,String,Throwable,long";
   }
   
   public String finally26(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally26 = "String,String,Serializable,long";
      return "finally26";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally26 = "void,String,Serializable,int";
   }
   
   public Object finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally26 = "Object,CharSequence,Throwable,int";
      return "finally26";
   }
   
   public void finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally26 = "void,CharSequence,Throwable,long";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "void,String,Throwable,Object[]";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally26 = "void,String,Throwable,Object";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally26 = "void,String,Serializable,Object[]";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally26 = "void,String,Serializable,Object";
   }
   
   public void finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally26 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally26 = "void,String,Throwable";
   }
   
   public void finally26(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally26 = "void,String,Serializable";
   }
   
   public void finally26(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally26 = "void,CharSequence,Throwable";
   }
   
   public void finally26(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally26 = "void,Throwable,int,long";
   }
   
   public void finally26(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally26 = "void,Serializable,int,long";
   }
      
   public Object finally26(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally26 = "Object,Throwable,long";
      return "finally26";
   }
   
   public void finally26(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally26 = "void,Throwable,int";
   }
   
   public String finally26(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally26 = "String,Serializable,int";
      return "finally26";
   }
   
   public Object finally26(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally26 = "Object,Serializable,long";
      return "finally26";
   }
   
   public void finally26(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally26 = "void,Throwable,Object[]";
   }
   
   public void finally26(@Thrown Throwable thrownException, @Args Object args)
   {
      finally26 = "void,Throwable,Object";
   }
   
   public void finally26(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally26 = "void,Serializable,Object[]";
   }
   
   public void finally26(@Thrown Serializable thrownException, @Args Object args)
   {
      finally26 = "void,Serializable,Object";
   }
   
   public void finally26(@Thrown Throwable thrownException)
   {
      finally26 = "void,Throwable";
   }
   
   public void finally26(@Thrown Serializable thrownException)
   {
      finally26 = "void,Serializable";
   }
   
   public void finally26(@Arg int arg1, @Arg long arg2)
   {
      finally26 = "void,int,long";
   }
   
   public String finally26(@Arg long arg2)
   {
      finally26 = "String,long";
      return "finally26";
   }
   
   public Object finally26(@Arg int arg1)
   {
      finally26 = "Object,int";
      return "finally26";
   }
   
   public void finally26(@Args Object[] args)
   {
      finally26 = "void,Object[]";
   }
   
   public void finally26(@Args Object args)
   {
      finally26 = "void,Object";
   }
   
   public void finally26()
   {
      finally26 = "void";
   }
   
   /* FINALLY27 ADVICE */
   
   public void finally27(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally27 = "void,MethodExecution,Serializable,Object[]";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally27 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally27 = "void,MethodExecution,Throwable";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally27 = "void,MethodExecution,Serializable";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally27 = "void,MethodExecution,int,long";
   }
   
   public String finally27(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally27 = "String,MethodExecution,long";
      return "finally27";
   }
   
   public Object finally27(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally27 = "Object,MethodExecution,int";
      return "finally27";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally27 = "void,MethodExecution,Object[]";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally27 = "void,MethodExecution,Object";
   }
   
   public void finally27(@JoinPoint MethodExecution joinPoint)
   {
      finally27 = "void,MethodExecution";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally27 = "void,String,Throwable,int,long";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally27 = "void,String,Serializable,int,long";
   }
   
   public void finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally27 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally27(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally27 = "String,String,Throwable,int";
      return "finally27";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally27 = "void,String,Throwable,long";
   }
   
   public String finally27(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally27 = "String,String,Serializable,long";
      return "finally27";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally27 = "void,String,Serializable,int";
   }
   
   public Object finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally27 = "Object,CharSequence,Throwable,int";
      return "finally27";
   }
   
   public void finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally27 = "void,CharSequence,Throwable,long";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "void,String,Throwable,Object[]";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally27 = "void,String,Throwable,Object";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally27 = "void,String,Serializable,Object[]";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally27 = "void,String,Serializable,Object";
   }
   
   public void finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally27 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally27 = "void,String,Throwable";
   }
   
   public void finally27(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally27 = "void,String,Serializable";
   }
   
   public void finally27(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally27 = "void,CharSequence,Throwable";
   }
   
   public void finally27(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally27 = "void,Throwable,int,long";
   }
   
   public void finally27(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally27 = "void,Serializable,int,long";
   }
      
   public Object finally27(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally27 = "Object,Throwable,long";
      return "finally27";
   }
   
   public void finally27(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally27 = "void,Throwable,int";
   }
   
   public String finally27(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally27 = "String,Serializable,int";
      return "finally27";
   }
   
   public Object finally27(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally27 = "Object,Serializable,long";
      return "finally27";
   }
   
   public void finally27(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally27 = "void,Throwable,Object[]";
   }
   
   public void finally27(@Thrown Throwable thrownException, @Args Object args)
   {
      finally27 = "void,Throwable,Object";
   }
   
   public void finally27(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally27 = "void,Serializable,Object[]";
   }
   
   public void finally27(@Thrown Serializable thrownException, @Args Object args)
   {
      finally27 = "void,Serializable,Object";
   }
   
   public void finally27(@Thrown Throwable thrownException)
   {
      finally27 = "void,Throwable";
   }
   
   public void finally27(@Thrown Serializable thrownException)
   {
      finally27 = "void,Serializable";
   }
   
   public void finally27(@Arg int arg1, @Arg long arg2)
   {
      finally27 = "void,int,long";
   }
   
   public String finally27(@Arg long arg2)
   {
      finally27 = "String,long";
      return "finally27";
   }
   
   public Object finally27(@Arg int arg1)
   {
      finally27 = "Object,int";
      return "finally27";
   }
   
   public void finally27(@Args Object[] args)
   {
      finally27 = "void,Object[]";
   }
   
   public void finally27(@Args Object args)
   {
      finally27 = "void,Object";
   }
   
   public void finally27()
   {
      finally27 = "void";
   }
   
   /* FINALLY28 ADVICE */
   
   public void finally28(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally28 = "void,MethodExecution,Serializable,Object";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally28 = "void,MethodExecution,Throwable";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally28 = "void,MethodExecution,Serializable";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally28 = "void,MethodExecution,int,long";
   }
   
   public String finally28(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally28 = "String,MethodExecution,long";
      return "finally28";
   }
   
   public Object finally28(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally28 = "Object,MethodExecution,int";
      return "finally28";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally28 = "void,MethodExecution,Object[]";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally28 = "void,MethodExecution,Object";
   }
   
   public void finally28(@JoinPoint MethodExecution joinPoint)
   {
      finally28 = "void,MethodExecution";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally28 = "void,String,Throwable,int,long";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally28 = "void,String,Serializable,int,long";
   }
   
   public void finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally28 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally28(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally28 = "String,String,Throwable,int";
      return "finally28";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally28 = "void,String,Throwable,long";
   }
   
   public String finally28(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally28 = "String,String,Serializable,long";
      return "finally28";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally28 = "void,String,Serializable,int";
   }
   
   public Object finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally28 = "Object,CharSequence,Throwable,int";
      return "finally28";
   }
   
   public void finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally28 = "void,CharSequence,Throwable,long";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "void,String,Throwable,Object[]";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally28 = "void,String,Throwable,Object";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally28 = "void,String,Serializable,Object[]";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally28 = "void,String,Serializable,Object";
   }
   
   public void finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally28 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally28 = "void,String,Throwable";
   }
   
   public void finally28(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally28 = "void,String,Serializable";
   }
   
   public void finally28(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally28 = "void,CharSequence,Throwable";
   }
   
   public void finally28(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally28 = "void,Throwable,int,long";
   }
   
   public void finally28(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally28 = "void,Serializable,int,long";
   }
      
   public Object finally28(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally28 = "Object,Throwable,long";
      return "finally28";
   }
   
   public void finally28(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally28 = "void,Throwable,int";
   }
   
   public String finally28(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally28 = "String,Serializable,int";
      return "finally28";
   }
   
   public Object finally28(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally28 = "Object,Serializable,long";
      return "finally28";
   }
   
   public void finally28(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally28 = "void,Throwable,Object[]";
   }
   
   public void finally28(@Thrown Throwable thrownException, @Args Object args)
   {
      finally28 = "void,Throwable,Object";
   }
   
   public void finally28(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally28 = "void,Serializable,Object[]";
   }
   
   public void finally28(@Thrown Serializable thrownException, @Args Object args)
   {
      finally28 = "void,Serializable,Object";
   }
   
   public void finally28(@Thrown Throwable thrownException)
   {
      finally28 = "void,Throwable";
   }
   
   public void finally28(@Thrown Serializable thrownException)
   {
      finally28 = "void,Serializable";
   }
   
   public void finally28(@Arg int arg1, @Arg long arg2)
   {
      finally28 = "void,int,long";
   }
   
   public String finally28(@Arg long arg2)
   {
      finally28 = "String,long";
      return "finally28";
   }
   
   public Object finally28(@Arg int arg1)
   {
      finally28 = "Object,int";
      return "finally28";
   }
   
   public void finally28(@Args Object[] args)
   {
      finally28 = "void,Object[]";
   }
   
   public void finally28(@Args Object args)
   {
      finally28 = "void,Object";
   }
   
   public void finally28()
   {
      finally28 = "void";
   }
   
   /* FINALLY29 ADVICE */
   
   public void finally29(@JoinPoint MethodExecution joinPoint,
         @Thrown Throwable thrownException)
   {
      finally29 = "void,MethodExecution,Throwable";
   }
   
   public void finally29(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally29 = "void,MethodExecution,Serializable";
   }
   
   public void finally29(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally29 = "void,MethodExecution,int,long";
   }
   
   public String finally29(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally29 = "String,MethodExecution,long";
      return "finally29";
   }
   
   public Object finally29(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally29 = "Object,MethodExecution,int";
      return "finally29";
   }
   
   public void finally29(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally29 = "void,MethodExecution,Object[]";
   }
   
   public void finally29(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally29 = "void,MethodExecution,Object";
   }
   
   public void finally29(@JoinPoint MethodExecution joinPoint)
   {
      finally29 = "void,MethodExecution";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally29 = "void,String,Throwable,int,long";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally29 = "void,String,Serializable,int,long";
   }
   
   public void finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally29 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally29(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally29 = "String,String,Throwable,int";
      return "finally29";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally29 = "void,String,Throwable,long";
   }
   
   public String finally29(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally29 = "String,String,Serializable,long";
      return "finally29";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally29 = "void,String,Serializable,int";
   }
   
   public Object finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally29 = "Object,CharSequence,Throwable,int";
      return "finally29";
   }
   
   public void finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally29 = "void,CharSequence,Throwable,long";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "void,String,Throwable,Object[]";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally29 = "void,String,Throwable,Object";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally29 = "void,String,Serializable,Object[]";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally29 = "void,String,Serializable,Object";
   }
   
   public void finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally29 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally29 = "void,String,Throwable";
   }
   
   public void finally29(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally29 = "void,String,Serializable";
   }
   
   public void finally29(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally29 = "void,CharSequence,Throwable";
   }
   
   public void finally29(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally29 = "void,Throwable,int,long";
   }
   
   public void finally29(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally29 = "void,Serializable,int,long";
   }
      
   public Object finally29(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally29 = "Object,Throwable,long";
      return "finally29";
   }
   
   public void finally29(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally29 = "void,Throwable,int";
   }
   
   public String finally29(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally29 = "String,Serializable,int";
      return "finally29";
   }
   
   public Object finally29(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally29 = "Object,Serializable,long";
      return "finally29";
   }
   
   public void finally29(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally29 = "void,Throwable,Object[]";
   }
   
   public void finally29(@Thrown Throwable thrownException, @Args Object args)
   {
      finally29 = "void,Throwable,Object";
   }
   
   public void finally29(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally29 = "void,Serializable,Object[]";
   }
   
   public void finally29(@Thrown Serializable thrownException, @Args Object args)
   {
      finally29 = "void,Serializable,Object";
   }
   
   public void finally29(@Thrown Throwable thrownException)
   {
      finally29 = "void,Throwable";
   }
   
   public void finally29(@Thrown Serializable thrownException)
   {
      finally29 = "void,Serializable";
   }
   
   public void finally29(@Arg int arg1, @Arg long arg2)
   {
      finally29 = "void,int,long";
   }
   
   public String finally29(@Arg long arg2)
   {
      finally29 = "String,long";
      return "finally29";
   }
   
   public Object finally29(@Arg int arg1)
   {
      finally29 = "Object,int";
      return "finally29";
   }
   
   public void finally29(@Args Object[] args)
   {
      finally29 = "void,Object[]";
   }
   
   public void finally29(@Args Object args)
   {
      finally29 = "void,Object";
   }
   
   public void finally29()
   {
      finally29 = "void";
   }
   
   /* FINALLY30 ADVICE */
   
   public void finally30(@JoinPoint MethodExecution joinPoint,
         @Thrown Serializable thrownException)
   {
      finally30 = "void,MethodExecution,Serializable";
   }
   
   public void finally30(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally30 = "void,MethodExecution,int,long";
   }
   
   public String finally30(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally30 = "String,MethodExecution,long";
      return "finally30";
   }
   
   public Object finally30(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally30 = "Object,MethodExecution,int";
      return "finally30";
   }
   
   public void finally30(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally30 = "void,MethodExecution,Object[]";
   }
   
   public void finally30(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally30 = "void,MethodExecution,Object";
   }
   
   public void finally30(@JoinPoint MethodExecution joinPoint)
   {
      finally30 = "void,MethodExecution";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally30 = "void,String,Throwable,int,long";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally30 = "void,String,Serializable,int,long";
   }
   
   public void finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally30 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally30(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally30 = "String,String,Throwable,int";
      return "finally30";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally30 = "void,String,Throwable,long";
   }
   
   public String finally30(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally30 = "String,String,Serializable,long";
      return "finally30";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally30 = "void,String,Serializable,int";
   }
   
   public Object finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally30 = "Object,CharSequence,Throwable,int";
      return "finally30";
   }
   
   public void finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally30 = "void,CharSequence,Throwable,long";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "void,String,Throwable,Object[]";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally30 = "void,String,Throwable,Object";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally30 = "void,String,Serializable,Object[]";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally30 = "void,String,Serializable,Object";
   }
   
   public void finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally30 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally30 = "void,String,Throwable";
   }
   
   public void finally30(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally30 = "void,String,Serializable";
   }
   
   public void finally30(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally30 = "void,CharSequence,Throwable";
   }
   
   public void finally30(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally30 = "void,Throwable,int,long";
   }
   
   public void finally30(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally30 = "void,Serializable,int,long";
   }
      
   public Object finally30(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally30 = "Object,Throwable,long";
      return "finally30";
   }
   
   public void finally30(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally30 = "void,Throwable,int";
   }
   
   public String finally30(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally30 = "String,Serializable,int";
      return "finally30";
   }
   
   public Object finally30(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally30 = "Object,Serializable,long";
      return "finally30";
   }
   
   public void finally30(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally30 = "void,Throwable,Object[]";
   }
   
   public void finally30(@Thrown Throwable thrownException, @Args Object args)
   {
      finally30 = "void,Throwable,Object";
   }
   
   public void finally30(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally30 = "void,Serializable,Object[]";
   }
   
   public void finally30(@Thrown Serializable thrownException, @Args Object args)
   {
      finally30 = "void,Serializable,Object";
   }
   
   public void finally30(@Thrown Throwable thrownException)
   {
      finally30 = "void,Throwable";
   }
   
   public void finally30(@Thrown Serializable thrownException)
   {
      finally30 = "void,Serializable";
   }
   
   public void finally30(@Arg int arg1, @Arg long arg2)
   {
      finally30 = "void,int,long";
   }
   
   public String finally30(@Arg long arg2)
   {
      finally30 = "String,long";
      return "finally30";
   }
   
   public Object finally30(@Arg int arg1)
   {
      finally30 = "Object,int";
      return "finally30";
   }
   
   public void finally30(@Args Object[] args)
   {
      finally30 = "void,Object[]";
   }
   
   public void finally30(@Args Object args)
   {
      finally30 = "void,Object";
   }
   
   public void finally30()
   {
      finally30 = "void";
   }
   
   /* FINALLY31 ADVICE */
   
   public void finally31(@JoinPoint MethodExecution joinPoint, @Arg int arg1,
         @Arg long arg2)
   {
      finally31 = "void,MethodExecution,int,long";
   }
   
   public String finally31(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally31 = "String,MethodExecution,long";
      return "finally31";
   }
   
   public Object finally31(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally31 = "Object,MethodExecution,int";
      return "finally31";
   }
   
   public void finally31(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally31 = "void,MethodExecution,Object[]";
   }
   
   public void finally31(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally31 = "void,MethodExecution,Object";
   }
   
   public void finally31(@JoinPoint MethodExecution joinPoint)
   {
      finally31 = "void,MethodExecution";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally31 = "void,String,Throwable,int,long";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally31 = "void,String,Serializable,int,long";
   }
   
   public void finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally31 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally31(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally31 = "String,String,Throwable,int";
      return "finally31";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally31 = "void,String,Throwable,long";
   }
   
   public String finally31(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally31 = "String,String,Serializable,long";
      return "finally31";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally31 = "void,String,Serializable,int";
   }
   
   public Object finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally31 = "Object,CharSequence,Throwable,int";
      return "finally31";
   }
   
   public void finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally31 = "void,CharSequence,Throwable,long";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "void,String,Throwable,Object[]";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally31 = "void,String,Throwable,Object";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally31 = "void,String,Serializable,Object[]";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally31 = "void,String,Serializable,Object";
   }
   
   public void finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally31 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally31 = "void,String,Throwable";
   }
   
   public void finally31(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally31 = "void,String,Serializable";
   }
   
   public void finally31(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally31 = "void,CharSequence,Throwable";
   }
   
   public void finally31(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally31 = "void,Throwable,int,long";
   }
   
   public void finally31(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally31 = "void,Serializable,int,long";
   }
      
   public Object finally31(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally31 = "Object,Throwable,long";
      return "finally31";
   }
   
   public void finally31(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally31 = "void,Throwable,int";
   }
   
   public String finally31(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally31 = "String,Serializable,int";
      return "finally31";
   }
   
   public Object finally31(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally31 = "Object,Serializable,long";
      return "finally31";
   }
   
   public void finally31(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally31 = "void,Throwable,Object[]";
   }
   
   public void finally31(@Thrown Throwable thrownException, @Args Object args)
   {
      finally31 = "void,Throwable,Object";
   }
   
   public void finally31(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally31 = "void,Serializable,Object[]";
   }
   
   public void finally31(@Thrown Serializable thrownException, @Args Object args)
   {
      finally31 = "void,Serializable,Object";
   }
   
   public void finally31(@Thrown Throwable thrownException)
   {
      finally31 = "void,Throwable";
   }
   
   public void finally31(@Thrown Serializable thrownException)
   {
      finally31 = "void,Serializable";
   }
   
   public void finally31(@Arg int arg1, @Arg long arg2)
   {
      finally31 = "void,int,long";
   }
   
   public String finally31(@Arg long arg2)
   {
      finally31 = "String,long";
      return "finally31";
   }
   
   public Object finally31(@Arg int arg1)
   {
      finally31 = "Object,int";
      return "finally31";
   }
   
   public void finally31(@Args Object[] args)
   {
      finally31 = "void,Object[]";
   }
   
   public void finally31(@Args Object args)
   {
      finally31 = "void,Object";
   }
   
   public void finally31()
   {
      finally31 = "void";
   }
   
   /* FINALLY32 ADVICE */
   
   public String finally32(@JoinPoint MethodExecution joinPoint, @Arg long arg2)
   {
      finally32 = "String,MethodExecution,long";
      return "finally32";
   }
   
   public Object finally32(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally32 = "Object,MethodExecution,int";
      return "finally32";
   }
   
   public void finally32(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally32 = "void,MethodExecution,Object[]";
   }
   
   public void finally32(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally32 = "void,MethodExecution,Object";
   }
   
   public void finally32(@JoinPoint MethodExecution joinPoint)
   {
      finally32 = "void,MethodExecution";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally32 = "void,String,Throwable,int,long";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally32 = "void,String,Serializable,int,long";
   }
   
   public void finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally32 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally32(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally32 = "String,String,Throwable,int";
      return "finally32";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally32 = "void,String,Throwable,long";
   }
   
   public String finally32(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally32 = "String,String,Serializable,long";
      return "finally32";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally32 = "void,String,Serializable,int";
   }
   
   public Object finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally32 = "Object,CharSequence,Throwable,int";
      return "finally32";
   }
   
   public void finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally32 = "void,CharSequence,Throwable,long";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "void,String,Throwable,Object[]";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally32 = "void,String,Throwable,Object";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally32 = "void,String,Serializable,Object[]";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally32 = "void,String,Serializable,Object";
   }
   
   public void finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally32 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally32 = "void,String,Throwable";
   }
   
   public void finally32(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally32 = "void,String,Serializable";
   }
   
   public void finally32(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally32 = "void,CharSequence,Throwable";
   }
   
   public void finally32(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally32 = "void,Throwable,int,long";
   }
   
   public void finally32(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally32 = "void,Serializable,int,long";
   }
      
   public Object finally32(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally32 = "Object,Throwable,long";
      return "finally32";
   }
   
   public void finally32(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally32 = "void,Throwable,int";
   }
   
   public String finally32(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally32 = "String,Serializable,int";
      return "finally32";
   }
   
   public Object finally32(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally32 = "Object,Serializable,long";
      return "finally32";
   }
   
   public void finally32(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally32 = "void,Throwable,Object[]";
   }
   
   public void finally32(@Thrown Throwable thrownException, @Args Object args)
   {
      finally32 = "void,Throwable,Object";
   }
   
   public void finally32(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally32 = "void,Serializable,Object[]";
   }
   
   public void finally32(@Thrown Serializable thrownException, @Args Object args)
   {
      finally32 = "void,Serializable,Object";
   }
   
   public void finally32(@Thrown Throwable thrownException)
   {
      finally32 = "void,Throwable";
   }
   
   public void finally32(@Thrown Serializable thrownException)
   {
      finally32 = "void,Serializable";
   }
   
   public void finally32(@Arg int arg1, @Arg long arg2)
   {
      finally32 = "void,int,long";
   }
   
   public String finally32(@Arg long arg2)
   {
      finally32 = "String,long";
      return "finally32";
   }
   
   public Object finally32(@Arg int arg1)
   {
      finally32 = "Object,int";
      return "finally32";
   }
   
   public void finally32(@Args Object[] args)
   {
      finally32 = "void,Object[]";
   }
   
   public void finally32(@Args Object args)
   {
      finally32 = "void,Object";
   }
   
   public void finally32()
   {
      finally32 = "void";
   }
   
   /* FINALLY33 ADVICE */
   
   public Object finally33(@JoinPoint MethodExecution joinPoint, @Arg int arg1)
   {
      finally33 = "Object,MethodExecution,int";
      return "finally33";
   }
   
   public void finally33(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally33 = "void,MethodExecution,Object[]";
   }
   
   public void finally33(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally33 = "void,MethodExecution,Object";
   }
   
   public void finally33(@JoinPoint MethodExecution joinPoint)
   {
      finally33 = "void,MethodExecution";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally33 = "void,String,Throwable,int,long";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally33 = "void,String,Serializable,int,long";
   }
   
   public void finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally33 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally33(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally33 = "String,String,Throwable,int";
      return "finally33";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally33 = "void,String,Throwable,long";
   }
   
   public String finally33(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally33 = "String,String,Serializable,long";
      return "finally33";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally33 = "void,String,Serializable,int";
   }
   
   public Object finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally33 = "Object,CharSequence,Throwable,int";
      return "finally33";
   }
   
   public void finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally33 = "void,CharSequence,Throwable,long";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "void,String,Throwable,Object[]";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally33 = "void,String,Throwable,Object";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally33 = "void,String,Serializable,Object[]";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally33 = "void,String,Serializable,Object";
   }
   
   public void finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally33 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally33 = "void,String,Throwable";
   }
   
   public void finally33(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally33 = "void,String,Serializable";
   }
   
   public void finally33(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally33 = "void,CharSequence,Throwable";
   }
   
   public void finally33(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally33 = "void,Throwable,int,long";
   }
   
   public void finally33(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally33 = "void,Serializable,int,long";
   }
      
   public Object finally33(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally33 = "Object,Throwable,long";
      return "finally33";
   }
   
   public void finally33(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally33 = "void,Throwable,int";
   }
   
   public String finally33(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally33 = "String,Serializable,int";
      return "finally33";
   }
   
   public Object finally33(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally33 = "Object,Serializable,long";
      return "finally33";
   }
   
   public void finally33(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally33 = "void,Throwable,Object[]";
   }
   
   public void finally33(@Thrown Throwable thrownException, @Args Object args)
   {
      finally33 = "void,Throwable,Object";
   }
   
   public void finally33(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally33 = "void,Serializable,Object[]";
   }
   
   public void finally33(@Thrown Serializable thrownException, @Args Object args)
   {
      finally33 = "void,Serializable,Object";
   }
   
   public void finally33(@Thrown Throwable thrownException)
   {
      finally33 = "void,Throwable";
   }
   
   public void finally33(@Thrown Serializable thrownException)
   {
      finally33 = "void,Serializable";
   }
   
   public void finally33(@Arg int arg1, @Arg long arg2)
   {
      finally33 = "void,int,long";
   }
   
   public String finally33(@Arg long arg2)
   {
      finally33 = "String,long";
      return "finally33";
   }
   
   public Object finally33(@Arg int arg1)
   {
      finally33 = "Object,int";
      return "finally33";
   }
   
   public void finally33(@Args Object[] args)
   {
      finally33 = "void,Object[]";
   }
   
   public void finally33(@Args Object args)
   {
      finally33 = "void,Object";
   }
   
   public void finally33()
   {
      finally33 = "void";
   }
   
   /* FINALLY34 ADVICE */
   
   public void finally34(@JoinPoint MethodExecution joinPoint, @Args Object[] args)
   {
      finally34 = "void,MethodExecution,Object[]";
   }
   
   public void finally34(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally34 = "void,MethodExecution,Object";
   }
   
   public void finally34(@JoinPoint MethodExecution joinPoint)
   {
      finally34 = "void,MethodExecution";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally34 = "void,String,Throwable,int,long";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally34 = "void,String,Serializable,int,long";
   }
   
   public void finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally34 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally34(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally34 = "String,String,Throwable,int";
      return "finally34";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally34 = "void,String,Throwable,long";
   }
   
   public String finally34(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally34 = "String,String,Serializable,long";
      return "finally34";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally34 = "void,String,Serializable,int";
   }
   
   public Object finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally34 = "Object,CharSequence,Throwable,int";
      return "finally34";
   }
   
   public void finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally34 = "void,CharSequence,Throwable,long";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "void,String,Throwable,Object[]";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally34 = "void,String,Throwable,Object";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally34 = "void,String,Serializable,Object[]";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally34 = "void,String,Serializable,Object";
   }
   
   public void finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally34 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally34 = "void,String,Throwable";
   }
   
   public void finally34(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally34 = "void,String,Serializable";
   }
   
   public void finally34(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally34 = "void,CharSequence,Throwable";
   }
   
   public void finally34(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally34 = "void,Throwable,int,long";
   }
   
   public void finally34(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally34 = "void,Serializable,int,long";
   }
      
   public Object finally34(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally34 = "Object,Throwable,long";
      return "finally34";
   }
   
   public void finally34(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally34 = "void,Throwable,int";
   }
   
   public String finally34(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally34 = "String,Serializable,int";
      return "finally34";
   }
   
   public Object finally34(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally34 = "Object,Serializable,long";
      return "finally34";
   }
   
   public void finally34(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally34 = "void,Throwable,Object[]";
   }
   
   public void finally34(@Thrown Throwable thrownException, @Args Object args)
   {
      finally34 = "void,Throwable,Object";
   }
   
   public void finally34(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally34 = "void,Serializable,Object[]";
   }
   
   public void finally34(@Thrown Serializable thrownException, @Args Object args)
   {
      finally34 = "void,Serializable,Object";
   }
   
   public void finally34(@Thrown Throwable thrownException)
   {
      finally34 = "void,Throwable";
   }
   
   public void finally34(@Thrown Serializable thrownException)
   {
      finally34 = "void,Serializable";
   }
   
   public void finally34(@Arg int arg1, @Arg long arg2)
   {
      finally34 = "void,int,long";
   }
   
   public String finally34(@Arg long arg2)
   {
      finally34 = "String,long";
      return "finally34";
   }
   
   public Object finally34(@Arg int arg1)
   {
      finally34 = "Object,int";
      return "finally34";
   }
   
   public void finally34(@Args Object[] args)
   {
      finally34 = "void,Object[]";
   }
   
   public void finally34(@Args Object args)
   {
      finally34 = "void,Object";
   }
   
   public void finally34()
   {
      finally34 = "void";
   }
   
   /* FINALLY35 ADVICE */
   
   public void finally35(@JoinPoint MethodExecution joinPoint, @Args Object args)
   {
      finally35 = "void,MethodExecution,Object";
   }
   
   public void finally35(@JoinPoint MethodExecution joinPoint)
   {
      finally35 = "void,MethodExecution";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally35 = "void,String,Throwable,int,long";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally35 = "void,String,Serializable,int,long";
   }
   
   public void finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally35 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally35(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally35 = "String,String,Throwable,int";
      return "finally35";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally35 = "void,String,Throwable,long";
   }
   
   public String finally35(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally35 = "String,String,Serializable,long";
      return "finally35";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally35 = "void,String,Serializable,int";
   }
   
   public Object finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally35 = "Object,CharSequence,Throwable,int";
      return "finally35";
   }
   
   public void finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally35 = "void,CharSequence,Throwable,long";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "void,String,Throwable,Object[]";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally35 = "void,String,Throwable,Object";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally35 = "void,String,Serializable,Object[]";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally35 = "void,String,Serializable,Object";
   }
   
   public void finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally35 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally35 = "void,String,Throwable";
   }
   
   public void finally35(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally35 = "void,String,Serializable";
   }
   
   public void finally35(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally35 = "void,CharSequence,Throwable";
   }
   
   public void finally35(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally35 = "void,Throwable,int,long";
   }
   
   public void finally35(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally35 = "void,Serializable,int,long";
   }
      
   public Object finally35(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally35 = "Object,Throwable,long";
      return "finally35";
   }
   
   public void finally35(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally35 = "void,Throwable,int";
   }
   
   public String finally35(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally35 = "String,Serializable,int";
      return "finally35";
   }
   
   public Object finally35(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally35 = "Object,Serializable,long";
      return "finally35";
   }
   
   public void finally35(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally35 = "void,Throwable,Object[]";
   }
   
   public void finally35(@Thrown Throwable thrownException, @Args Object args)
   {
      finally35 = "void,Throwable,Object";
   }
   
   public void finally35(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally35 = "void,Serializable,Object[]";
   }
   
   public void finally35(@Thrown Serializable thrownException, @Args Object args)
   {
      finally35 = "void,Serializable,Object";
   }
   
   public void finally35(@Thrown Throwable thrownException)
   {
      finally35 = "void,Throwable";
   }
   
   public void finally35(@Thrown Serializable thrownException)
   {
      finally35 = "void,Serializable";
   }
   
   public void finally35(@Arg int arg1, @Arg long arg2)
   {
      finally35 = "void,int,long";
   }
   
   public String finally35(@Arg long arg2)
   {
      finally35 = "String,long";
      return "finally35";
   }
   
   public Object finally35(@Arg int arg1)
   {
      finally35 = "Object,int";
      return "finally35";
   }
   
   public void finally35(@Args Object[] args)
   {
      finally35 = "void,Object[]";
   }
   
   public void finally35(@Args Object args)
   {
      finally35 = "void,Object";
   }
   
   public void finally35()
   {
      finally35 = "void";
   }
   
   /* FINALLY36 ADVICE */
   
   public void finally36(@JoinPoint MethodExecution joinPoint)
   {
      finally36 = "void,MethodExecution";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally36 = "void,String,Throwable,int,long";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally36 = "void,String,Serializable,int,long";
   }
   
   public void finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally36 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally36(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally36 = "String,String,Throwable,int";
      return "finally36";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally36 = "void,String,Throwable,long";
   }
   
   public String finally36(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally36 = "String,String,Serializable,long";
      return "finally36";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally36 = "void,String,Serializable,int";
   }
   
   public Object finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally36 = "Object,CharSequence,Throwable,int";
      return "finally36";
   }
   
   public void finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally36 = "void,CharSequence,Throwable,long";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "void,String,Throwable,Object[]";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally36 = "void,String,Throwable,Object";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally36 = "void,String,Serializable,Object[]";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally36 = "void,String,Serializable,Object";
   }
   
   public void finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally36 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally36 = "void,String,Throwable";
   }
   
   public void finally36(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally36 = "void,String,Serializable";
   }
   
   public void finally36(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally36 = "void,CharSequence,Throwable";
   }
   
   public void finally36(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally36 = "void,Throwable,int,long";
   }
   
   public void finally36(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally36 = "void,Serializable,int,long";
   }
      
   public Object finally36(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally36 = "Object,Throwable,long";
      return "finally36";
   }
   
   public void finally36(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally36 = "void,Throwable,int";
   }
   
   public String finally36(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally36 = "String,Serializable,int";
      return "finally36";
   }
   
   public Object finally36(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally36 = "Object,Serializable,long";
      return "finally36";
   }
   
   public void finally36(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally36 = "void,Throwable,Object[]";
   }
   
   public void finally36(@Thrown Throwable thrownException, @Args Object args)
   {
      finally36 = "void,Throwable,Object";
   }
   
   public void finally36(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally36 = "void,Serializable,Object[]";
   }
   
   public void finally36(@Thrown Serializable thrownException, @Args Object args)
   {
      finally36 = "void,Serializable,Object";
   }
   
   public void finally36(@Thrown Throwable thrownException)
   {
      finally36 = "void,Throwable";
   }
   
   public void finally36(@Thrown Serializable thrownException)
   {
      finally36 = "void,Serializable";
   }
   
   public void finally36(@Arg int arg1, @Arg long arg2)
   {
      finally36 = "void,int,long";
   }
   
   public String finally36(@Arg long arg2)
   {
      finally36 = "String,long";
      return "finally36";
   }
   
   public Object finally36(@Arg int arg1)
   {
      finally36 = "Object,int";
      return "finally36";
   }
   
   public void finally36(@Args Object[] args)
   {
      finally36 = "void,Object[]";
   }
   
   public void finally36(@Args Object args)
   {
      finally36 = "void,Object";
   }
   
   public void finally36()
   {
      finally36 = "void";
   }
   
   /* FINALLY37 ADVICE */
   
   public void finally37(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally37 = "void,String,Throwable,int,long";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally37 = "void,String,Serializable,int,long";
   }
   
   public void finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally37 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally37(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally37 = "String,String,Throwable,int";
      return "finally37";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally37 = "void,String,Throwable,long";
   }
   
   public String finally37(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally37 = "String,String,Serializable,long";
      return "finally37";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally37 = "void,String,Serializable,int";
   }
   
   public Object finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally37 = "Object,CharSequence,Throwable,int";
      return "finally37";
   }
   
   public void finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally37 = "void,CharSequence,Throwable,long";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "void,String,Throwable,Object[]";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally37 = "void,String,Throwable,Object";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally37 = "void,String,Serializable,Object[]";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally37 = "void,String,Serializable,Object";
   }
   
   public void finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally37 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally37 = "void,String,Throwable";
   }
   
   public void finally37(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally37 = "void,String,Serializable";
   }
   
   public void finally37(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally37 = "void,CharSequence,Throwable";
   }
   
   public void finally37(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally37 = "void,Throwable,int,long";
   }
   
   public void finally37(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally37 = "void,Serializable,int,long";
   }
      
   public Object finally37(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally37 = "Object,Throwable,long";
      return "finally37";
   }
   
   public void finally37(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally37 = "void,Throwable,int";
   }
   
   public String finally37(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally37 = "String,Serializable,int";
      return "finally37";
   }
   
   public Object finally37(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally37 = "Object,Serializable,long";
      return "finally37";
   }
   
   public void finally37(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally37 = "void,Throwable,Object[]";
   }
   
   public void finally37(@Thrown Throwable thrownException, @Args Object args)
   {
      finally37 = "void,Throwable,Object";
   }
   
   public void finally37(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally37 = "void,Serializable,Object[]";
   }
   
   public void finally37(@Thrown Serializable thrownException, @Args Object args)
   {
      finally37 = "void,Serializable,Object";
   }
   
   public void finally37(@Thrown Throwable thrownException)
   {
      finally37 = "void,Throwable";
   }
   
   public void finally37(@Thrown Serializable thrownException)
   {
      finally37 = "void,Serializable";
   }
   
   public void finally37(@Arg int arg1, @Arg long arg2)
   {
      finally37 = "void,int,long";
   }
   
   public String finally37(@Arg long arg2)
   {
      finally37 = "String,long";
      return "finally37";
   }
   
   public Object finally37(@Arg int arg1)
   {
      finally37 = "Object,int";
      return "finally37";
   }
   
   public void finally37(@Args Object[] args)
   {
      finally37 = "void,Object[]";
   }
   
   public void finally37(@Args Object args)
   {
      finally37 = "void,Object";
   }
   
   public void finally37()
   {
      finally37 = "void";
   }
   
   /* FINALLY38 ADVICE */
   
   public void finally38(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally38 = "void,String,Serializable,int,long";
   }
   
   public void finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally38 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally38(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally38 = "String,String,Throwable,int";
      return "finally38";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally38 = "void,String,Throwable,long";
   }
   
   public String finally38(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally38 = "String,String,Serializable,long";
      return "finally38";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally38 = "void,String,Serializable,int";
   }
   
   public Object finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally38 = "Object,CharSequence,Throwable,int";
      return "finally38";
   }
   
   public void finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally38 = "void,CharSequence,Throwable,long";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "void,String,Throwable,Object[]";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally38 = "void,String,Throwable,Object";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally38 = "void,String,Serializable,Object[]";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally38 = "void,String,Serializable,Object";
   }
   
   public void finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally38 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally38 = "void,String,Throwable";
   }
   
   public void finally38(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally38 = "void,String,Serializable";
   }
   
   public void finally38(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally38 = "void,CharSequence,Throwable";
   }
   
   public void finally38(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally38 = "void,Throwable,int,long";
   }
   
   public void finally38(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally38 = "void,Serializable,int,long";
   }
      
   public Object finally38(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally38 = "Object,Throwable,long";
      return "finally38";
   }
   
   public void finally38(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally38 = "void,Throwable,int";
   }
   
   public String finally38(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally38 = "String,Serializable,int";
      return "finally38";
   }
   
   public Object finally38(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally38 = "Object,Serializable,long";
      return "finally38";
   }
   
   public void finally38(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally38 = "void,Throwable,Object[]";
   }
   
   public void finally38(@Thrown Throwable thrownException, @Args Object args)
   {
      finally38 = "void,Throwable,Object";
   }
   
   public void finally38(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally38 = "void,Serializable,Object[]";
   }
   
   public void finally38(@Thrown Serializable thrownException, @Args Object args)
   {
      finally38 = "void,Serializable,Object";
   }
   
   public void finally38(@Thrown Throwable thrownException)
   {
      finally38 = "void,Throwable";
   }
   
   public void finally38(@Thrown Serializable thrownException)
   {
      finally38 = "void,Serializable";
   }
   
   public void finally38(@Arg int arg1, @Arg long arg2)
   {
      finally38 = "void,int,long";
   }
   
   public String finally38(@Arg long arg2)
   {
      finally38 = "String,long";
      return "finally38";
   }
   
   public Object finally38(@Arg int arg1)
   {
      finally38 = "Object,int";
      return "finally38";
   }
   
   public void finally38(@Args Object[] args)
   {
      finally38 = "void,Object[]";
   }
   
   public void finally38(@Args Object args)
   {
      finally38 = "void,Object";
   }
   
   public void finally38()
   {
      finally38 = "void";
   }
   
   /* FINALLY39 ADVICE */
   
   public void finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1, @Arg long arg2)
   {
      finally39 = "void,CharSequence,Throwable,int,long";
   }
   
   public String finally39(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally39 = "String,String,Throwable,int";
      return "finally39";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally39 = "void,String,Throwable,long";
   }
   
   public String finally39(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally39 = "String,String,Serializable,long";
      return "finally39";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally39 = "void,String,Serializable,int";
   }
   
   public Object finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally39 = "Object,CharSequence,Throwable,int";
      return "finally39";
   }
   
   public void finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally39 = "void,CharSequence,Throwable,long";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "void,String,Throwable,Object[]";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally39 = "void,String,Throwable,Object";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally39 = "void,String,Serializable,Object[]";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally39 = "void,String,Serializable,Object";
   }
   
   public void finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally39 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally39 = "void,String,Throwable";
   }
   
   public void finally39(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally39 = "void,String,Serializable";
   }
   
   public void finally39(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally39 = "void,CharSequence,Throwable";
   }
   
   public void finally39(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally39 = "void,Throwable,int,long";
   }
   
   public void finally39(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally39 = "void,Serializable,int,long";
   }
      
   public Object finally39(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally39 = "Object,Throwable,long";
      return "finally39";
   }
   
   public void finally39(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally39 = "void,Throwable,int";
   }
   
   public String finally39(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally39 = "String,Serializable,int";
      return "finally39";
   }
   
   public Object finally39(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally39 = "Object,Serializable,long";
      return "finally39";
   }
   
   public void finally39(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally39 = "void,Throwable,Object[]";
   }
   
   public void finally39(@Thrown Throwable thrownException, @Args Object args)
   {
      finally39 = "void,Throwable,Object";
   }
   
   public void finally39(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally39 = "void,Serializable,Object[]";
   }
   
   public void finally39(@Thrown Serializable thrownException, @Args Object args)
   {
      finally39 = "void,Serializable,Object";
   }
   
   public void finally39(@Thrown Throwable thrownException)
   {
      finally39 = "void,Throwable";
   }
   
   public void finally39(@Thrown Serializable thrownException)
   {
      finally39 = "void,Serializable";
   }
   
   public void finally39(@Arg int arg1, @Arg long arg2)
   {
      finally39 = "void,int,long";
   }
   
   public String finally39(@Arg long arg2)
   {
      finally39 = "String,long";
      return "finally39";
   }
   
   public Object finally39(@Arg int arg1)
   {
      finally39 = "Object,int";
      return "finally39";
   }
   
   public void finally39(@Args Object[] args)
   {
      finally39 = "void,Object[]";
   }
   
   public void finally39(@Args Object args)
   {
      finally39 = "void,Object";
   }
   
   public void finally39()
   {
      finally39 = "void";
   }
   
   /* FINALLY40 ADVICE */
   
   public String finally40(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally40 = "String,String,Throwable,int";
      return "finally40";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally40 = "void,String,Throwable,long";
   }
   
   public String finally40(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally40 = "String,String,Serializable,long";
      return "finally40";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally40 = "void,String,Serializable,int";
   }
   
   public Object finally40(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally40 = "Object,CharSequence,Throwable,int";
      return "finally40";
   }
   
   public void finally40(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally40 = "void,CharSequence,Throwable,long";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "void,String,Throwable,Object[]";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally40 = "void,String,Throwable,Object";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally40 = "void,String,Serializable,Object[]";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally40 = "void,String,Serializable,Object";
   }
   
   public void finally40(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally40(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally40 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally40 = "void,String,Throwable";
   }
   
   public void finally40(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally40 = "void,String,Serializable";
   }
   
   public void finally40(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally40 = "void,CharSequence,Throwable";
   }
   
   public void finally40(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally40 = "void,Throwable,int,long";
   }
   
   public void finally40(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally40 = "void,Serializable,int,long";
   }
      
   public Object finally40(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally40 = "Object,Throwable,long";
      return "finally40";
   }
   
   public void finally40(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally40 = "void,Throwable,int";
   }
   
   public String finally40(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally40 = "String,Serializable,int";
      return "finally40";
   }
   
   public Object finally40(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally40 = "Object,Serializable,long";
      return "finally40";
   }
   
   public void finally40(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally40 = "void,Throwable,Object[]";
   }
   
   public void finally40(@Thrown Throwable thrownException, @Args Object args)
   {
      finally40 = "void,Throwable,Object";
   }
   
   public void finally40(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally40 = "void,Serializable,Object[]";
   }
   
   public void finally40(@Thrown Serializable thrownException, @Args Object args)
   {
      finally40 = "void,Serializable,Object";
   }
   
   public void finally40(@Thrown Throwable thrownException)
   {
      finally40 = "void,Throwable";
   }
   
   public void finally40(@Thrown Serializable thrownException)
   {
      finally40 = "void,Serializable";
   }
   
   public void finally40(@Arg int arg1, @Arg long arg2)
   {
      finally40 = "void,int,long";
   }
   
   public String finally40(@Arg long arg2)
   {
      finally40 = "String,long";
      return "finally40";
   }
   
   public Object finally40(@Arg int arg1)
   {
      finally40 = "Object,int";
      return "finally40";
   }
   
   public void finally40(@Args Object[] args)
   {
      finally40 = "void,Object[]";
   }
   
   public void finally40(@Args Object args)
   {
      finally40 = "void,Object";
   }
   
   public void finally40()
   {
      finally40 = "void";
   }
   
   /* FINALLY41 ADVICE */
   
   public void finally41(@Return String valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally41 = "void,String,Throwable,long";
   }
   
   public String finally41(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally41 = "String,String,Serializable,long";
      return "finally41";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally41 = "void,String,Serializable,int";
   }
   
   public Object finally41(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally41 = "Object,CharSequence,Throwable,int";
      return "finally41";
   }
   
   public void finally41(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally41 = "void,CharSequence,Throwable,long";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally41 = "void,String,Throwable,Object[]";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally41 = "void,String,Throwable,Object";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally41 = "void,String,Serializable,Object[]";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally41 = "void,String,Serializable,Object";
   }
   
   public void finally41(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally41 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally41(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally41 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally41 = "void,String,Throwable";
   }
   
   public void finally41(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally41 = "void,String,Serializable";
   }
   
   public void finally41(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally41 = "void,CharSequence,Throwable";
   }
   
   public void finally41(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally41 = "void,Throwable,int,long";
   }
   
   public void finally41(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally41 = "void,Serializable,int,long";
   }
      
   public Object finally41(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally41 = "Object,Throwable,long";
      return "finally41";
   }
   
   public void finally41(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally41 = "void,Throwable,int";
   }
   
   public String finally41(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally41 = "String,Serializable,int";
      return "finally41";
   }
   
   public Object finally41(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally41 = "Object,Serializable,long";
      return "finally41";
   }
   
   public void finally41(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally41 = "void,Throwable,Object[]";
   }
   
   public void finally41(@Thrown Throwable thrownException, @Args Object args)
   {
      finally41 = "void,Throwable,Object";
   }
   
   public void finally41(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally41 = "void,Serializable,Object[]";
   }
   
   public void finally41(@Thrown Serializable thrownException, @Args Object args)
   {
      finally41 = "void,Serializable,Object";
   }
   
   public void finally41(@Thrown Throwable thrownException)
   {
      finally41 = "void,Throwable";
   }
   
   public void finally41(@Thrown Serializable thrownException)
   {
      finally41 = "void,Serializable";
   }
   
   public void finally41(@Arg int arg1, @Arg long arg2)
   {
      finally41 = "void,int,long";
   }
   
   public String finally41(@Arg long arg2)
   {
      finally41 = "String,long";
      return "finally41";
   }
   
   public Object finally41(@Arg int arg1)
   {
      finally41 = "Object,int";
      return "finally41";
   }
   
   public void finally41(@Args Object[] args)
   {
      finally41 = "void,Object[]";
   }
   
   public void finally41(@Args Object args)
   {
      finally41 = "void,Object";
   }
   
   public void finally41()
   {
      finally41 = "void";
   }
   
   /* FINALLY42 ADVICE */
   
   public String finally42(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg long arg2)
   {
      finally42 = "String,String,Serializable,long";
      return "finally42";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally42 = "void,String,Serializable,int";
   }
   
   public Object finally42(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally42 = "Object,CharSequence,Throwable,int";
      return "finally42";
   }
   
   public void finally42(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally42 = "void,CharSequence,Throwable,long";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally42 = "void,String,Throwable,Object[]";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally42 = "void,String,Throwable,Object";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally42 = "void,String,Serializable,Object[]";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally42 = "void,String,Serializable,Object";
   }
   
   public void finally42(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally42 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally42(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally42 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally42 = "void,String,Throwable";
   }
   
   public void finally42(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally42 = "void,String,Serializable";
   }
   
   public void finally42(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally42 = "void,CharSequence,Throwable";
   }
   
   public void finally42(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally42 = "void,Throwable,int,long";
   }
   
   public void finally42(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally42 = "void,Serializable,int,long";
   }
      
   public Object finally42(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally42 = "Object,Throwable,long";
      return "finally42";
   }
   
   public void finally42(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally42 = "void,Throwable,int";
   }
   
   public String finally42(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally42 = "String,Serializable,int";
      return "finally42";
   }
   
   public Object finally42(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally42 = "Object,Serializable,long";
      return "finally42";
   }
   
   public void finally42(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally42 = "void,Throwable,Object[]";
   }
   
   public void finally42(@Thrown Throwable thrownException, @Args Object args)
   {
      finally42 = "void,Throwable,Object";
   }
   
   public void finally42(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally42 = "void,Serializable,Object[]";
   }
   
   public void finally42(@Thrown Serializable thrownException, @Args Object args)
   {
      finally42 = "void,Serializable,Object";
   }
   
   public void finally42(@Thrown Throwable thrownException)
   {
      finally42 = "void,Throwable";
   }
   
   public void finally42(@Thrown Serializable thrownException)
   {
      finally42 = "void,Serializable";
   }
   
   public void finally42(@Arg int arg1, @Arg long arg2)
   {
      finally42 = "void,int,long";
   }
   
   public String finally42(@Arg long arg2)
   {
      finally42 = "String,long";
      return "finally42";
   }
   
   public Object finally42(@Arg int arg1)
   {
      finally42 = "Object,int";
      return "finally42";
   }
   
   public void finally42(@Args Object[] args)
   {
      finally42 = "void,Object[]";
   }
   
   public void finally42(@Args Object args)
   {
      finally42 = "void,Object";
   }
   
   public void finally42()
   {
      finally42 = "void";
   }
   
   /* FINALLY43 ADVICE */
   
   public void finally43(@Return String valueReturned,
         @Thrown Serializable thrownException, @Arg int arg1)
   {
      finally43 = "void,String,Serializable,int";
   }
   
   public Object finally43(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally43 = "Object,CharSequence,Throwable,int";
      return "finally43";
   }
   
   public void finally43(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally43 = "void,CharSequence,Throwable,long";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally43 = "void,String,Throwable,Object[]";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally43 = "void,String,Throwable,Object";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally43 = "void,String,Serializable,Object[]";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally43 = "void,String,Serializable,Object";
   }
   
   public void finally43(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally43 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally43(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally43 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally43 = "void,String,Throwable";
   }
   
   public void finally43(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally43 = "void,String,Serializable";
   }
   
   public void finally43(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally43 = "void,CharSequence,Throwable";
   }
   
   public void finally43(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally43 = "void,Throwable,int,long";
   }
   
   public void finally43(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally43 = "void,Serializable,int,long";
   }
      
   public Object finally43(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally43 = "Object,Throwable,long";
      return "finally43";
   }
   
   public void finally43(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally43 = "void,Throwable,int";
   }
   
   public String finally43(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally43 = "String,Serializable,int";
      return "finally43";
   }
   
   public Object finally43(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally43 = "Object,Serializable,long";
      return "finally43";
   }
   
   public void finally43(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally43 = "void,Throwable,Object[]";
   }
   
   public void finally43(@Thrown Throwable thrownException, @Args Object args)
   {
      finally43 = "void,Throwable,Object";
   }
   
   public void finally43(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally43 = "void,Serializable,Object[]";
   }
   
   public void finally43(@Thrown Serializable thrownException, @Args Object args)
   {
      finally43 = "void,Serializable,Object";
   }
   
   public void finally43(@Thrown Throwable thrownException)
   {
      finally43 = "void,Throwable";
   }
   
   public void finally43(@Thrown Serializable thrownException)
   {
      finally43 = "void,Serializable";
   }
   
   public void finally43(@Arg int arg1, @Arg long arg2)
   {
      finally43 = "void,int,long";
   }
   
   public String finally43(@Arg long arg2)
   {
      finally43 = "String,long";
      return "finally43";
   }
   
   public Object finally43(@Arg int arg1)
   {
      finally43 = "Object,int";
      return "finally43";
   }
   
   public void finally43(@Args Object[] args)
   {
      finally43 = "void,Object[]";
   }
   
   public void finally43(@Args Object args)
   {
      finally43 = "void,Object";
   }
   
   public void finally43()
   {
      finally43 = "void";
   }
   
   /* FINALLY44 ADVICE */
   
   public Object finally44(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg int arg1)
   {
      finally44 = "Object,CharSequence,Throwable,int";
      return "finally44";
   }
   
   public void finally44(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally44 = "void,CharSequence,Throwable,long";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally44 = "void,String,Throwable,Object[]";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally44 = "void,String,Throwable,Object";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally44 = "void,String,Serializable,Object[]";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally44 = "void,String,Serializable,Object";
   }
   
   public void finally44(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally44 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally44(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally44 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally44 = "void,String,Throwable";
   }
   
   public void finally44(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally44 = "void,String,Serializable";
   }
   
   public void finally44(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally44 = "void,CharSequence,Throwable";
   }
   
   public void finally44(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally44 = "void,Throwable,int,long";
   }
   
   public void finally44(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally44 = "void,Serializable,int,long";
   }
      
   public Object finally44(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally44 = "Object,Throwable,long";
      return "finally44";
   }
   
   public void finally44(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally44 = "void,Throwable,int";
   }
   
   public String finally44(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally44 = "String,Serializable,int";
      return "finally44";
   }
   
   public Object finally44(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally44 = "Object,Serializable,long";
      return "finally44";
   }
   
   public void finally44(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally44 = "void,Throwable,Object[]";
   }
   
   public void finally44(@Thrown Throwable thrownException, @Args Object args)
   {
      finally44 = "void,Throwable,Object";
   }
   
   public void finally44(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally44 = "void,Serializable,Object[]";
   }
   
   public void finally44(@Thrown Serializable thrownException, @Args Object args)
   {
      finally44 = "void,Serializable,Object";
   }
   
   public void finally44(@Thrown Throwable thrownException)
   {
      finally44 = "void,Throwable";
   }
   
   public void finally44(@Thrown Serializable thrownException)
   {
      finally44 = "void,Serializable";
   }
   
   public void finally44(@Arg int arg1, @Arg long arg2)
   {
      finally44 = "void,int,long";
   }
   
   public String finally44(@Arg long arg2)
   {
      finally44 = "String,long";
      return "finally44";
   }
   
   public Object finally44(@Arg int arg1)
   {
      finally44 = "Object,int";
      return "finally44";
   }
   
   public void finally44(@Args Object[] args)
   {
      finally44 = "void,Object[]";
   }
   
   public void finally44(@Args Object args)
   {
      finally44 = "void,Object";
   }
   
   public void finally44()
   {
      finally44 = "void";
   }
   
   /* FINALLY45 ADVICE */
   
   public void finally45(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Arg long arg2)
   {
      finally45 = "void,CharSequence,Throwable,long";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally45 = "void,String,Throwable,Object[]";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally45 = "void,String,Throwable,Object";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally45 = "void,String,Serializable,Object[]";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally45 = "void,String,Serializable,Object";
   }
   
   public void finally45(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally45 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally45(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally45 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally45 = "void,String,Throwable";
   }
   
   public void finally45(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally45 = "void,String,Serializable";
   }
   
   public void finally45(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally45 = "void,CharSequence,Throwable";
   }
   
   public void finally45(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally45 = "void,Throwable,int,long";
   }
   
   public void finally45(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally45 = "void,Serializable,int,long";
   }
      
   public Object finally45(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally45 = "Object,Throwable,long";
      return "finally45";
   }
   
   public void finally45(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally45 = "void,Throwable,int";
   }
   
   public String finally45(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally45 = "String,Serializable,int";
      return "finally45";
   }
   
   public Object finally45(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally45 = "Object,Serializable,long";
      return "finally45";
   }
   
   public void finally45(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally45 = "void,Throwable,Object[]";
   }
   
   public void finally45(@Thrown Throwable thrownException, @Args Object args)
   {
      finally45 = "void,Throwable,Object";
   }
   
   public void finally45(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally45 = "void,Serializable,Object[]";
   }
   
   public void finally45(@Thrown Serializable thrownException, @Args Object args)
   {
      finally45 = "void,Serializable,Object";
   }
   
   public void finally45(@Thrown Throwable thrownException)
   {
      finally45 = "void,Throwable";
   }
   
   public void finally45(@Thrown Serializable thrownException)
   {
      finally45 = "void,Serializable";
   }
   
   public void finally45(@Arg int arg1, @Arg long arg2)
   {
      finally45 = "void,int,long";
   }
   
   public String finally45(@Arg long arg2)
   {
      finally45 = "String,long";
      return "finally45";
   }
   
   public Object finally45(@Arg int arg1)
   {
      finally45 = "Object,int";
      return "finally45";
   }
   
   public void finally45(@Args Object[] args)
   {
      finally45 = "void,Object[]";
   }
   
   public void finally45(@Args Object args)
   {
      finally45 = "void,Object";
   }
   
   public void finally45()
   {
      finally45 = "void";
   }
   
   /* FINALLY46 ADVICE */
   
   public void finally46(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally46 = "void,String,Throwable,Object[]";
   }
   
   public void finally46(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally46 = "void,String,Throwable,Object";
   }
   
   public void finally46(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally46 = "void,String,Serializable,Object[]";
   }
   
   public void finally46(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally46 = "void,String,Serializable,Object";
   }
   
   public void finally46(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally46 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally46(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally46 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally46(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally46 = "void,String,Throwable";
   }
   
   public void finally46(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally46 = "void,String,Serializable";
   }
   
   public void finally46(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally46 = "void,CharSequence,Throwable";
   }
   
   public void finally46(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally46 = "void,Throwable,int,long";
   }
   
   public void finally46(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally46 = "void,Serializable,int,long";
   }
      
   public Object finally46(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally46 = "Object,Throwable,long";
      return "finally46";
   }
   
   public void finally46(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally46 = "void,Throwable,int";
   }
   
   public String finally46(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally46 = "String,Serializable,int";
      return "finally46";
   }
   
   public Object finally46(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally46 = "Object,Serializable,long";
      return "finally46";
   }
   
   public void finally46(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally46 = "void,Throwable,Object[]";
   }
   
   public void finally46(@Thrown Throwable thrownException, @Args Object args)
   {
      finally46 = "void,Throwable,Object";
   }
   
   public void finally46(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally46 = "void,Serializable,Object[]";
   }
   
   public void finally46(@Thrown Serializable thrownException, @Args Object args)
   {
      finally46 = "void,Serializable,Object";
   }
   
   public void finally46(@Thrown Throwable thrownException)
   {
      finally46 = "void,Throwable";
   }
   
   public void finally46(@Thrown Serializable thrownException)
   {
      finally46 = "void,Serializable";
   }
   
   public void finally46(@Arg int arg1, @Arg long arg2)
   {
      finally46 = "void,int,long";
   }
   
   public String finally46(@Arg long arg2)
   {
      finally46 = "String,long";
      return "finally46";
   }
   
   public Object finally46(@Arg int arg1)
   {
      finally46 = "Object,int";
      return "finally46";
   }
   
   public void finally46(@Args Object[] args)
   {
      finally46 = "void,Object[]";
   }
   
   public void finally46(@Args Object args)
   {
      finally46 = "void,Object";
   }
   
   public void finally46()
   {
      finally46 = "void";
   }
   
   /* FINALLY47 ADVICE */
   
   public void finally47(@Return String valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally47 = "void,String,Throwable,Object";
   }
   
   public void finally47(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally47 = "void,String,Serializable,Object[]";
   }
   
   public void finally47(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally47 = "void,String,Serializable,Object";
   }
   
   public void finally47(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally47 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally47(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally47 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally47(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally47 = "void,String,Throwable";
   }
   
   public void finally47(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally47 = "void,String,Serializable";
   }
   
   public void finally47(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally47 = "void,CharSequence,Throwable";
   }
   
   public void finally47(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally47 = "void,Throwable,int,long";
   }
   
   public void finally47(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally47 = "void,Serializable,int,long";
   }
      
   public Object finally47(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally47 = "Object,Throwable,long";
      return "finally47";
   }
   
   public void finally47(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally47 = "void,Throwable,int";
   }
   
   public String finally47(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally47 = "String,Serializable,int";
      return "finally47";
   }
   
   public Object finally47(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally47 = "Object,Serializable,long";
      return "finally47";
   }
   
   public void finally47(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally47 = "void,Throwable,Object[]";
   }
   
   public void finally47(@Thrown Throwable thrownException, @Args Object args)
   {
      finally47 = "void,Throwable,Object";
   }
   
   public void finally47(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally47 = "void,Serializable,Object[]";
   }
   
   public void finally47(@Thrown Serializable thrownException, @Args Object args)
   {
      finally47 = "void,Serializable,Object";
   }
   
   public void finally47(@Thrown Throwable thrownException)
   {
      finally47 = "void,Throwable";
   }
   
   public void finally47(@Thrown Serializable thrownException)
   {
      finally47 = "void,Serializable";
   }
   
   public void finally47(@Arg int arg1, @Arg long arg2)
   {
      finally47 = "void,int,long";
   }
   
   public String finally47(@Arg long arg2)
   {
      finally47 = "String,long";
      return "finally47";
   }
   
   public Object finally47(@Arg int arg1)
   {
      finally47 = "Object,int";
      return "finally47";
   }
   
   public void finally47(@Args Object[] args)
   {
      finally47 = "void,Object[]";
   }
   
   public void finally47(@Args Object args)
   {
      finally47 = "void,Object";
   }
   
   public void finally47()
   {
      finally47 = "void";
   }
   
   /* FINALLY48 ADVICE */
   
   public void finally48(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object[] args)
   {
      finally48 = "void,String,Serializable,Object[]";
   }
   
   public void finally48(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally48 = "void,String,Serializable,Object";
   }
   
   public void finally48(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally48 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally48(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally48 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally48(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally48 = "void,String,Throwable";
   }
   
   public void finally48(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally48 = "void,String,Serializable";
   }
   
   public void finally48(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally48 = "void,CharSequence,Throwable";
   }
   
   public void finally48(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally48 = "void,Throwable,int,long";
   }
   
   public void finally48(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally48 = "void,Serializable,int,long";
   }
      
   public Object finally48(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally48 = "Object,Throwable,long";
      return "finally48";
   }
   
   public void finally48(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally48 = "void,Throwable,int";
   }
   
   public String finally48(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally48 = "String,Serializable,int";
      return "finally48";
   }
   
   public Object finally48(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally48 = "Object,Serializable,long";
      return "finally48";
   }
   
   public void finally48(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally48 = "void,Throwable,Object[]";
   }
   
   public void finally48(@Thrown Throwable thrownException, @Args Object args)
   {
      finally48 = "void,Throwable,Object";
   }
   
   public void finally48(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally48 = "void,Serializable,Object[]";
   }
   
   public void finally48(@Thrown Serializable thrownException, @Args Object args)
   {
      finally48 = "void,Serializable,Object";
   }
   
   public void finally48(@Thrown Throwable thrownException)
   {
      finally48 = "void,Throwable";
   }
   
   public void finally48(@Thrown Serializable thrownException)
   {
      finally48 = "void,Serializable";
   }
   
   public void finally48(@Arg int arg1, @Arg long arg2)
   {
      finally48 = "void,int,long";
   }
   
   public String finally48(@Arg long arg2)
   {
      finally48 = "String,long";
      return "finally48";
   }
   
   public Object finally48(@Arg int arg1)
   {
      finally48 = "Object,int";
      return "finally48";
   }
   
   public void finally48(@Args Object[] args)
   {
      finally48 = "void,Object[]";
   }
   
   public void finally48(@Args Object args)
   {
      finally48 = "void,Object";
   }
   
   public void finally48()
   {
      finally48 = "void";
   }
   
   /* FINALLY49 ADVICE */
   
   public void finally49(@Return String valueReturned,
         @Thrown Serializable thrownException, @Args Object args)
   {
      finally49 = "void,String,Serializable,Object";
   }
   
   public void finally49(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally49 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally49(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally49 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally49(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally49 = "void,String,Throwable";
   }
   
   public void finally49(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally49 = "void,String,Serializable";
   }
   
   public void finally49(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally49 = "void,CharSequence,Throwable";
   }
   
   public void finally49(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally49 = "void,Throwable,int,long";
   }
   
   public void finally49(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally49 = "void,Serializable,int,long";
   }
      
   public Object finally49(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally49 = "Object,Throwable,long";
      return "finally49";
   }
   
   public void finally49(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally49 = "void,Throwable,int";
   }
   
   public String finally49(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally49 = "String,Serializable,int";
      return "finally49";
   }
   
   public Object finally49(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally49 = "Object,Serializable,long";
      return "finally49";
   }
   
   public void finally49(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally49 = "void,Throwable,Object[]";
   }
   
   public void finally49(@Thrown Throwable thrownException, @Args Object args)
   {
      finally49 = "void,Throwable,Object";
   }
   
   public void finally49(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally49 = "void,Serializable,Object[]";
   }
   
   public void finally49(@Thrown Serializable thrownException, @Args Object args)
   {
      finally49 = "void,Serializable,Object";
   }
   
   public void finally49(@Thrown Throwable thrownException)
   {
      finally49 = "void,Throwable";
   }
   
   public void finally49(@Thrown Serializable thrownException)
   {
      finally49 = "void,Serializable";
   }
   
   public void finally49(@Arg int arg1, @Arg long arg2)
   {
      finally49 = "void,int,long";
   }
   
   public String finally49(@Arg long arg2)
   {
      finally49 = "String,long";
      return "finally49";
   }
   
   public Object finally49(@Arg int arg1)
   {
      finally49 = "Object,int";
      return "finally49";
   }
   
   public void finally49(@Args Object[] args)
   {
      finally49 = "void,Object[]";
   }
   
   public void finally49(@Args Object args)
   {
      finally49 = "void,Object";
   }
   
   public void finally49()
   {
      finally49 = "void";
   }
   
   /* FINALLY50 ADVICE */
   
   public void finally50(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object[] args)
   {
      finally50 = "void,CharSequence,Throwable,Object[]";
   }
   
   public void finally50(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally50 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally50(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally50 = "void,String,Throwable";
   }
   
   public void finally50(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally50 = "void,String,Serializable";
   }
   
   public void finally50(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally50 = "void,CharSequence,Throwable";
   }
   
   public void finally50(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally50 = "void,Throwable,int,long";
   }
   
   public void finally50(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally50 = "void,Serializable,int,long";
   }
      
   public Object finally50(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally50 = "Object,Throwable,long";
      return "finally50";
   }
   
   public void finally50(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally50 = "void,Throwable,int";
   }
   
   public String finally50(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally50 = "String,Serializable,int";
      return "finally50";
   }
   
   public Object finally50(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally50 = "Object,Serializable,long";
      return "finally50";
   }
   
   public void finally50(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally50 = "void,Throwable,Object[]";
   }
   
   public void finally50(@Thrown Throwable thrownException, @Args Object args)
   {
      finally50 = "void,Throwable,Object";
   }
   
   public void finally50(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally50 = "void,Serializable,Object[]";
   }
   
   public void finally50(@Thrown Serializable thrownException, @Args Object args)
   {
      finally50 = "void,Serializable,Object";
   }
   
   public void finally50(@Thrown Throwable thrownException)
   {
      finally50 = "void,Throwable";
   }
   
   public void finally50(@Thrown Serializable thrownException)
   {
      finally50 = "void,Serializable";
   }
   
   public void finally50(@Arg int arg1, @Arg long arg2)
   {
      finally50 = "void,int,long";
   }
   
   public String finally50(@Arg long arg2)
   {
      finally50 = "String,long";
      return "finally50";
   }
   
   public Object finally50(@Arg int arg1)
   {
      finally50 = "Object,int";
      return "finally50";
   }
   
   public void finally50(@Args Object[] args)
   {
      finally50 = "void,Object[]";
   }
   
   public void finally50(@Args Object args)
   {
      finally50 = "void,Object";
   }
   
   public void finally50()
   {
      finally50 = "void";
   }
   
   /* FINALLY51 ADVICE */
   
   public void finally51(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException, @Args Object args)
   {
      finally51 = "void,CharSequence,Throwable,Object";
   }
   
   public void finally51(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally51 = "void,String,Throwable";
   }
   
   public void finally51(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally51 = "void,String,Serializable";
   }
   
   public void finally51(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally51 = "void,CharSequence,Throwable";
   }
   
   public void finally51(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally51 = "void,Throwable,int,long";
   }
   
   public void finally51(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally51 = "void,Serializable,int,long";
   }
      
   public Object finally51(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally51 = "Object,Throwable,long";
      return "finally51";
   }
   
   public void finally51(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally51 = "void,Throwable,int";
   }
   
   public String finally51(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally51 = "String,Serializable,int";
      return "finally51";
   }
   
   public Object finally51(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally51 = "Object,Serializable,long";
      return "finally51";
   }
   
   public void finally51(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally51 = "void,Throwable,Object[]";
   }
   
   public void finally51(@Thrown Throwable thrownException, @Args Object args)
   {
      finally51 = "void,Throwable,Object";
   }
   
   public void finally51(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally51 = "void,Serializable,Object[]";
   }
   
   public void finally51(@Thrown Serializable thrownException, @Args Object args)
   {
      finally51 = "void,Serializable,Object";
   }
   
   public void finally51(@Thrown Throwable thrownException)
   {
      finally51 = "void,Throwable";
   }
   
   public void finally51(@Thrown Serializable thrownException)
   {
      finally51 = "void,Serializable";
   }
   
   public void finally51(@Arg int arg1, @Arg long arg2)
   {
      finally51 = "void,int,long";
   }
   
   public String finally51(@Arg long arg2)
   {
      finally51 = "String,long";
      return "finally51";
   }
   
   public Object finally51(@Arg int arg1)
   {
      finally51 = "Object,int";
      return "finally51";
   }
   
   public void finally51(@Args Object[] args)
   {
      finally51 = "void,Object[]";
   }
   
   public void finally51(@Args Object args)
   {
      finally51 = "void,Object";
   }
   
   public void finally51()
   {
      finally51 = "void";
   }
   
   /* FINALLY52 ADVICE */
   
   public void finally52(@Return String valueReturned,
         @Thrown Throwable thrownException)
   {
      finally52 = "void,String,Throwable";
   }
   
   public void finally52(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally52 = "void,String,Serializable";
   }
   
   public void finally52(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally52 = "void,CharSequence,Throwable";
   }
   
   public void finally52(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally52 = "void,Throwable,int,long";
   }
   
   public void finally52(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally52 = "void,Serializable,int,long";
   }
      
   public Object finally52(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally52 = "Object,Throwable,long";
      return "finally52";
   }
   
   public void finally52(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally52 = "void,Throwable,int";
   }
   
   public String finally52(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally52 = "String,Serializable,int";
      return "finally52";
   }
   
   public Object finally52(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally52 = "Object,Serializable,long";
      return "finally52";
   }
   
   public void finally52(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally52 = "void,Throwable,Object[]";
   }
   
   public void finally52(@Thrown Throwable thrownException, @Args Object args)
   {
      finally52 = "void,Throwable,Object";
   }
   
   public void finally52(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally52 = "void,Serializable,Object[]";
   }
   
   public void finally52(@Thrown Serializable thrownException, @Args Object args)
   {
      finally52 = "void,Serializable,Object";
   }
   
   public void finally52(@Thrown Throwable thrownException)
   {
      finally52 = "void,Throwable";
   }
   
   public void finally52(@Thrown Serializable thrownException)
   {
      finally52 = "void,Serializable";
   }
   
   public void finally52(@Arg int arg1, @Arg long arg2)
   {
      finally52 = "void,int,long";
   }
   
   public String finally52(@Arg long arg2)
   {
      finally52 = "String,long";
      return "finally52";
   }
   
   public Object finally52(@Arg int arg1)
   {
      finally52 = "Object,int";
      return "finally52";
   }
   
   public void finally52(@Args Object[] args)
   {
      finally52 = "void,Object[]";
   }
   
   public void finally52(@Args Object args)
   {
      finally52 = "void,Object";
   }
   
   public void finally52()
   {
      finally52 = "void";
   }
   
   /* FINALLY53 ADVICE */
   
   public void finally53(@Return String valueReturned,
         @Thrown Serializable thrownException)
   {
      finally53 = "void,String,Serializable";
   }
   
   public void finally53(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally53 = "void,CharSequence,Throwable";
   }
   
   public void finally53(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally53 = "void,Throwable,int,long";
   }
   
   public void finally53(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally53 = "void,Serializable,int,long";
   }
      
   public Object finally53(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally53 = "Object,Throwable,long";
      return "finally53";
   }
   
   public void finally53(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally53 = "void,Throwable,int";
   }
   
   public String finally53(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally53 = "String,Serializable,int";
      return "finally53";
   }
   
   public Object finally53(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally53 = "Object,Serializable,long";
      return "finally53";
   }
   
   public void finally53(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally53 = "void,Throwable,Object[]";
   }
   
   public void finally53(@Thrown Throwable thrownException, @Args Object args)
   {
      finally53 = "void,Throwable,Object";
   }
   
   public void finally53(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally53 = "void,Serializable,Object[]";
   }
   
   public void finally53(@Thrown Serializable thrownException, @Args Object args)
   {
      finally53 = "void,Serializable,Object";
   }
   
   public void finally53(@Thrown Throwable thrownException)
   {
      finally53 = "void,Throwable";
   }
   
   public void finally53(@Thrown Serializable thrownException)
   {
      finally53 = "void,Serializable";
   }
   
   public void finally53(@Arg int arg1, @Arg long arg2)
   {
      finally53 = "void,int,long";
   }
   
   public String finally53(@Arg long arg2)
   {
      finally53 = "String,long";
      return "finally53";
   }
   
   public Object finally53(@Arg int arg1)
   {
      finally53 = "Object,int";
      return "finally53";
   }
   
   public void finally53(@Args Object[] args)
   {
      finally53 = "void,Object[]";
   }
   
   public void finally53(@Args Object args)
   {
      finally53 = "void,Object";
   }
   
   public void finally53()
   {
      finally53 = "void";
   }
   
   /* FINALLY54 ADVICE */
   
   public void finally54(@Return CharSequence valueReturned,
         @Thrown Throwable thrownException)
   {
      finally54 = "void,CharSequence,Throwable";
   }
   
   public void finally54(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally54 = "void,Throwable,int,long";
   }
   
   public void finally54(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally54 = "void,Serializable,int,long";
   }
      
   public Object finally54(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally54 = "Object,Throwable,long";
      return "finally54";
   }
   
   public void finally54(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally54 = "void,Throwable,int";
   }
   
   public String finally54(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally54 = "String,Serializable,int";
      return "finally54";
   }
   
   public Object finally54(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally54 = "Object,Serializable,long";
      return "finally54";
   }
   
   public void finally54(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally54 = "void,Throwable,Object[]";
   }
   
   public void finally54(@Thrown Throwable thrownException, @Args Object args)
   {
      finally54 = "void,Throwable,Object";
   }
   
   public void finally54(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally54 = "void,Serializable,Object[]";
   }
   
   public void finally54(@Thrown Serializable thrownException, @Args Object args)
   {
      finally54 = "void,Serializable,Object";
   }
   
   public void finally54(@Thrown Throwable thrownException)
   {
      finally54 = "void,Throwable";
   }
   
   public void finally54(@Thrown Serializable thrownException)
   {
      finally54 = "void,Serializable";
   }
   
   public void finally54(@Arg int arg1, @Arg long arg2)
   {
      finally54 = "void,int,long";
   }
   
   public String finally54(@Arg long arg2)
   {
      finally54 = "String,long";
      return "finally54";
   }
   
   public Object finally54(@Arg int arg1)
   {
      finally54 = "Object,int";
      return "finally54";
   }
   
   public void finally54(@Args Object[] args)
   {
      finally54 = "void,Object[]";
   }
   
   public void finally54(@Args Object args)
   {
      finally54 = "void,Object";
   }
   
   public void finally54()
   {
      finally54 = "void";
   }
   
   /* FINALLY55 ADVICE */
   
   public void finally55(@Thrown Throwable thrownException,
         @Arg int arg1, @Arg long arg2)
   {
      finally55 = "void,Throwable,int,long";
   }
   
   public void finally55(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally55 = "void,Serializable,int,long";
   }
      
   public Object finally55(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally55 = "Object,Throwable,long";
      return "finally55";
   }
   
   public void finally55(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally55 = "void,Throwable,int";
   }
   
   public String finally55(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally55 = "String,Serializable,int";
      return "finally55";
   }
   
   public Object finally55(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally55 = "Object,Serializable,long";
      return "finally55";
   }
   
   public void finally55(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally55 = "void,Throwable,Object[]";
   }
   
   public void finally55(@Thrown Throwable thrownException, @Args Object args)
   {
      finally55 = "void,Throwable,Object";
   }
   
   public void finally55(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally55 = "void,Serializable,Object[]";
   }
   
   public void finally55(@Thrown Serializable thrownException, @Args Object args)
   {
      finally55 = "void,Serializable,Object";
   }
   
   public void finally55(@Thrown Throwable thrownException)
   {
      finally55 = "void,Throwable";
   }
   
   public void finally55(@Thrown Serializable thrownException)
   {
      finally55 = "void,Serializable";
   }
   
   public void finally55(@Arg int arg1, @Arg long arg2)
   {
      finally55 = "void,int,long";
   }
   
   public String finally55(@Arg long arg2)
   {
      finally55 = "String,long";
      return "finally55";
   }
   
   public Object finally55(@Arg int arg1)
   {
      finally55 = "Object,int";
      return "finally55";
   }
   
   public void finally55(@Args Object[] args)
   {
      finally55 = "void,Object[]";
   }
   
   public void finally55(@Args Object args)
   {
      finally55 = "void,Object";
   }
   
   public void finally55()
   {
      finally55 = "void";
   }
   
   /* FINALLY56 ADVICE */
   
   public void finally56(@Thrown Serializable thrownException, @Arg int arg1,
         @Arg long arg2)
   {
      finally56 = "void,Serializable,int,long";
   }
      
   public Object finally56(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally56 = "Object,Throwable,long";
      return "finally56";
   }
   
   public void finally56(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally56 = "void,Throwable,int";
   }
   
   public String finally56(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally56 = "String,Serializable,int";
      return "finally56";
   }
   
   public Object finally56(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally56 = "Object,Serializable,long";
      return "finally56";
   }
   
   public void finally56(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally56 = "void,Throwable,Object[]";
   }
   
   public void finally56(@Thrown Throwable thrownException, @Args Object args)
   {
      finally56 = "void,Throwable,Object";
   }
   
   public void finally56(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally56 = "void,Serializable,Object[]";
   }
   
   public void finally56(@Thrown Serializable thrownException, @Args Object args)
   {
      finally56 = "void,Serializable,Object";
   }
   
   public void finally56(@Thrown Throwable thrownException)
   {
      finally56 = "void,Throwable";
   }
   
   public void finally56(@Thrown Serializable thrownException)
   {
      finally56 = "void,Serializable";
   }
   
   public void finally56(@Arg int arg1, @Arg long arg2)
   {
      finally56 = "void,int,long";
   }
   
   public String finally56(@Arg long arg2)
   {
      finally56 = "String,long";
      return "finally56";
   }
   
   public Object finally56(@Arg int arg1)
   {
      finally56 = "Object,int";
      return "finally56";
   }
   
   public void finally56(@Args Object[] args)
   {
      finally56 = "void,Object[]";
   }
   
   public void finally56(@Args Object args)
   {
      finally56 = "void,Object";
   }
   
   public void finally56()
   {
      finally56 = "void";
   }
   
   /* FINALLY57 ADVICE */
   
   public Object finally57(@Thrown Throwable thrownException, @Arg long arg2)
   {
      finally57 = "Object,Throwable,long";
      return "finally57";
   }
   
   public void finally57(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally57 = "void,Throwable,int";
   }
   
   public String finally57(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally57 = "String,Serializable,int";
      return "finally57";
   }
   
   public Object finally57(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally57 = "Object,Serializable,long";
      return "finally57";
   }
   
   public void finally57(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally57 = "void,Throwable,Object[]";
   }
   
   public void finally57(@Thrown Throwable thrownException, @Args Object args)
   {
      finally57 = "void,Throwable,Object";
   }
   
   public void finally57(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally57 = "void,Serializable,Object[]";
   }
   
   public void finally57(@Thrown Serializable thrownException, @Args Object args)
   {
      finally57 = "void,Serializable,Object";
   }
   
   public void finally57(@Thrown Throwable thrownException)
   {
      finally57 = "void,Throwable";
   }
   
   public void finally57(@Thrown Serializable thrownException)
   {
      finally57 = "void,Serializable";
   }
   
   public void finally57(@Arg int arg1, @Arg long arg2)
   {
      finally57 = "void,int,long";
   }
   
   public String finally57(@Arg long arg2)
   {
      finally57 = "String,long";
      return "finally57";
   }
   
   public Object finally57(@Arg int arg1)
   {
      finally57 = "Object,int";
      return "finally57";
   }
   
   public void finally57(@Args Object[] args)
   {
      finally57 = "void,Object[]";
   }
   
   public void finally57(@Args Object args)
   {
      finally57 = "void,Object";
   }
   
   public void finally57()
   {
      finally57 = "void";
   }
   
   /* FINALLY58 ADVICE */
   
   public void finally58(@Thrown Throwable thrownException, @Arg int arg1)
   {
      finally58 = "void,Throwable,int";
   }
   
   public String finally58(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally58 = "String,Serializable,int";
      return "finally58";
   }
   
   public Object finally58(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally58 = "Object,Serializable,long";
      return "finally58";
   }
   
   public void finally58(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally58 = "void,Throwable,Object[]";
   }
   
   public void finally58(@Thrown Throwable thrownException, @Args Object args)
   {
      finally58 = "void,Throwable,Object";
   }
   
   public void finally58(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally58 = "void,Serializable,Object[]";
   }
   
   public void finally58(@Thrown Serializable thrownException, @Args Object args)
   {
      finally58 = "void,Serializable,Object";
   }
   
   public void finally58(@Thrown Throwable thrownException)
   {
      finally58 = "void,Throwable";
   }
   
   public void finally58(@Thrown Serializable thrownException)
   {
      finally58 = "void,Serializable";
   }
   
   public void finally58(@Arg int arg1, @Arg long arg2)
   {
      finally58 = "void,int,long";
   }
   
   public String finally58(@Arg long arg2)
   {
      finally58 = "String,long";
      return "finally58";
   }
   
   public Object finally58(@Arg int arg1)
   {
      finally58 = "Object,int";
      return "finally58";
   }
   
   public void finally58(@Args Object[] args)
   {
      finally58 = "void,Object[]";
   }
   
   public void finally58(@Args Object args)
   {
      finally58 = "void,Object";
   }
   
   public void finally58()
   {
      finally58 = "void";
   }
   
   /* FINALLY59 ADVICE */
   
   public String finally59(@Thrown Serializable thrownException, @Arg int arg1)
   {
      finally59 = "String,Serializable,int";
      return "finally59";
   }
   
   public Object finally59(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally59 = "Object,Serializable,long";
      return "finally59";
   }
   
   public void finally59(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally59 = "void,Throwable,Object[]";
   }
   
   public void finally59(@Thrown Throwable thrownException, @Args Object args)
   {
      finally59 = "void,Throwable,Object";
   }
   
   public void finally59(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally59 = "void,Serializable,Object[]";
   }
   
   public void finally59(@Thrown Serializable thrownException, @Args Object args)
   {
      finally59 = "void,Serializable,Object";
   }
   
   public void finally59(@Thrown Throwable thrownException)
   {
      finally59 = "void,Throwable";
   }
   
   public void finally59(@Thrown Serializable thrownException)
   {
      finally59 = "void,Serializable";
   }
   
   public void finally59(@Arg int arg1, @Arg long arg2)
   {
      finally59 = "void,int,long";
   }
   
   public String finally59(@Arg long arg2)
   {
      finally59 = "String,long";
      return "finally59";
   }
   
   public Object finally59(@Arg int arg1)
   {
      finally59 = "Object,int";
      return "finally59";
   }
   
   public void finally59(@Args Object[] args)
   {
      finally59 = "void,Object[]";
   }
   
   public void finally59(@Args Object args)
   {
      finally59 = "void,Object";
   }
   
   public void finally59()
   {
      finally59 = "void";
   }
   
   /* FINALLY60 ADVICE */
   
   public Object finally60(@Thrown Serializable thrownException, @Arg long arg1)
   {
      finally60 = "Object,Serializable,long";
      return "finally60";
   }
   
   public void finally60(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally60 = "void,Throwable,Object[]";
   }
   
   public void finally60(@Thrown Throwable thrownException, @Args Object args)
   {
      finally60 = "void,Throwable,Object";
   }
   
   public void finally60(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally60 = "void,Serializable,Object[]";
   }
   
   public void finally60(@Thrown Serializable thrownException, @Args Object args)
   {
      finally60 = "void,Serializable,Object";
   }
   
   public void finally60(@Thrown Throwable thrownException)
   {
      finally60 = "void,Throwable";
   }
   
   public void finally60(@Thrown Serializable thrownException)
   {
      finally60 = "void,Serializable";
   }
   
   public void finally60(@Arg int arg1, @Arg long arg2)
   {
      finally60 = "void,int,long";
   }
   
   public String finally60(@Arg long arg2)
   {
      finally60 = "String,long";
      return "finally60";
   }
   
   public Object finally60(@Arg int arg1)
   {
      finally60 = "Object,int";
      return "finally60";
   }
   
   public void finally60(@Args Object[] args)
   {
      finally60 = "void,Object[]";
   }
   
   public void finally60(@Args Object args)
   {
      finally60 = "void,Object";
   }
   
   public void finally60()
   {
      finally60 = "void";
   }
   
   /* FINALLY61 ADVICE */
   
   public void finally61(@Thrown Throwable thrownException, @Args Object[] args)
   {
      finally61 = "void,Throwable,Object[]";
   }
   
   public void finally61(@Thrown Throwable thrownException, @Args Object args)
   {
      finally61 = "void,Throwable,Object";
   }
   
   public void finally61(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally61 = "void,Serializable,Object[]";
   }
   
   public void finally61(@Thrown Serializable thrownException, @Args Object args)
   {
      finally61 = "void,Serializable,Object";
   }
   
   public void finally61(@Thrown Throwable thrownException)
   {
      finally61 = "void,Throwable";
   }
   
   public void finally61(@Thrown Serializable thrownException)
   {
      finally61 = "void,Serializable";
   }
   
   public void finally61(@Arg int arg1, @Arg long arg2)
   {
      finally61 = "void,int,long";
   }
   
   public String finally61(@Arg long arg2)
   {
      finally61 = "String,long";
      return "finally61";
   }
   
   public Object finally61(@Arg int arg1)
   {
      finally61 = "Object,int";
      return "finally61";
   }
   
   public void finally61(@Args Object[] args)
   {
      finally61 = "void,Object[]";
   }
   
   public void finally61(@Args Object args)
   {
      finally61 = "void,Object";
   }
   
   public void finally61()
   {
      finally61 = "void";
   }
   
   /* FINALLY62 ADVICE */
   
   public void finally62(@Thrown Throwable thrownException, @Args Object args)
   {
      finally62 = "void,Throwable,Object";
   }
   
   public void finally62(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally62 = "void,Serializable,Object[]";
   }
   
   public void finally62(@Thrown Serializable thrownException, @Args Object args)
   {
      finally62 = "void,Serializable,Object";
   }
   
   public void finally62(@Thrown Throwable thrownException)
   {
      finally62 = "void,Throwable";
   }
   
   public void finally62(@Thrown Serializable thrownException)
   {
      finally62 = "void,Serializable";
   }
   
   public void finally62(@Arg int arg1, @Arg long arg2)
   {
      finally62 = "void,int,long";
   }
   
   public String finally62(@Arg long arg2)
   {
      finally62 = "String,long";
      return "finally62";
   }
   
   public Object finally62(@Arg int arg1)
   {
      finally62 = "Object,int";
      return "finally62";
   }
   
   public void finally62(@Args Object[] args)
   {
      finally62 = "void,Object[]";
   }
   
   public void finally62(@Args Object args)
   {
      finally62 = "void,Object";
   }
   
   public void finally62()
   {
      finally62 = "void";
   }
   
   /* FINALLY63 ADVICE */
   
   public void finally63(@Thrown Serializable thrownException, @Args Object[] args)
   {
      finally63 = "void,Serializable,Object[]";
   }
   
   public void finally63(@Thrown Serializable thrownException, @Args Object args)
   {
      finally63 = "void,Serializable,Object";
   }
   
   public void finally63(@Thrown Throwable thrownException)
   {
      finally63 = "void,Throwable";
   }
   
   public void finally63(@Thrown Serializable thrownException)
   {
      finally63 = "void,Serializable";
   }
   
   public void finally63(@Arg int arg1, @Arg long arg2)
   {
      finally63 = "void,int,long";
   }
   
   public String finally63(@Arg long arg2)
   {
      finally63 = "String,long";
      return "finally63";
   }
   
   public Object finally63(@Arg int arg1)
   {
      finally63 = "Object,int";
      return "finally63";
   }
   
   public void finally63(@Args Object[] args)
   {
      finally63 = "void,Object[]";
   }
   
   public void finally63(@Args Object args)
   {
      finally63 = "void,Object";
   }
   
   public void finally63()
   {
      finally63 = "void";
   }
   
   /* FINALLY64 ADVICE */
   
   public void finally64(@Thrown Serializable thrownException, @Args Object args)
   {
      finally64 = "void,Serializable,Object";
   }
   
   public void finally64(@Thrown Throwable thrownException)
   {
      finally64 = "void,Throwable";
   }
   
   public void finally64(@Thrown Serializable thrownException)
   {
      finally64 = "void,Serializable";
   }
   
   public void finally64(@Arg int arg1, @Arg long arg2)
   {
      finally64 = "void,int,long";
   }
   
   public String finally64(@Arg long arg2)
   {
      finally64 = "String,long";
      return "finally64";
   }
   
   public Object finally64(@Arg int arg1)
   {
      finally64 = "Object,int";
      return "finally64";
   }
   
   public void finally64(@Args Object[] args)
   {
      finally64 = "void,Object[]";
   }
   
   public void finally64(@Args Object args)
   {
      finally64 = "void,Object";
   }
   
   public void finally64()
   {
      finally64 = "void";
   }
   
   /* FINALLY65 ADVICE */
   
   public void finally65(@Thrown Throwable thrownException)
   {
      finally65 = "void,Throwable";
   }
   
   public void finally65(@Thrown Serializable thrownException)
   {
      finally65 = "void,Serializable";
   }
   
   public void finally65(@Arg int arg1, @Arg long arg2)
   {
      finally65 = "void,int,long";
   }
   
   public String finally65(@Arg long arg2)
   {
      finally65 = "String,long";
      return "finally65";
   }
   
   public Object finally65(@Arg int arg1)
   {
      finally65 = "Object,int";
      return "finally65";
   }
   
   public void finally65(@Args Object[] args)
   {
      finally65 = "void,Object[]";
   }
   
   public void finally65(@Args Object args)
   {
      finally65 = "void,Object";
   }
   
   public void finally65()
   {
      finally65 = "void";
   }
   
   /* FINALLY66 ADVICE */
   
   public void finally66(@Thrown Serializable thrownException)
   {
      finally66 = "void,Serializable";
   }
   
   public void finally66(@Arg int arg1, @Arg long arg2)
   {
      finally66 = "void,int,long";
   }
   
   public String finally66(@Arg long arg2)
   {
      finally66 = "String,long";
      return "finally66";
   }
   
   public Object finally66(@Arg int arg1)
   {
      finally66 = "Object,int";
      return "finally66";
   }
   
   public void finally66(@Args Object[] args)
   {
      finally66 = "void,Object[]";
   }
   
   public void finally66(@Args Object args)
   {
      finally66 = "void,Object";
   }
   
   public void finally66()
   {
      finally66 = "void";
   }
   
   /* FINALLY67 ADVICE */
   
   public void finally67(@Arg int arg1, @Arg long arg2)
   {
      finally67 = "void,int,long";
   }
   
   public String finally67(@Arg long arg2)
   {
      finally67 = "String,long";
      return "finally67";
   }
   
   public Object finally67(@Arg int arg1)
   {
      finally67 = "Object,int";
      return "finally67";
   }
   
   public void finally67(@Args Object[] args)
   {
      finally67 = "void,Object[]";
   }
   
   public void finally67(@Args Object args)
   {
      finally67 = "void,Object";
   }
   
   public void finally67()
   {
      finally67 = "void";
   }
   
   /* FINALLY68 ADVICE */
   
   public String finally68(@Arg long arg2)
   {
      finally68 = "String,long";
      return "finally68";
   }
   
   public Object finally68(@Arg int arg1)
   {
      finally68 = "Object,int";
      return "finally68";
   }
   
   public void finally68(@Args Object[] args)
   {
      finally68 = "void,Object[]";
   }
   
   public void finally68(@Args Object args)
   {
      finally68 = "void,Object";
   }
   
   public void finally68()
   {
      finally68 = "void";
   }
   
   /* FINALLY69 ADVICE */
   
   public Object finally69(@Arg int arg1)
   {
      finally69 = "Object,int";
      return "finally69";
   }
   
   public void finally69(@Args Object[] args)
   {
      finally69 = "void,Object[]";
   }
   
   public void finally69(@Args Object args)
   {
      finally69 = "void,Object";
   }
   
   public void finally69()
   {
      finally69 = "void";
   }
   
   /* FINALLY70 ADVICE */
   
   public void finally70(@Args Object[] args)
   {
      finally70 = "void,Object[]";
   }
   
   public void finally70(@Args Object args)
   {
      finally70 = "void,Object";
   }
   
   public void finally70()
   {
      finally70 = "void";
   }
   
   /* FINALLY71 ADVICE */
   
   public void finally71(@Args Object args)
   {
      finally71 = "void,Object";
   }
   
   public void finally71()
   {
      finally71 = "void";
   }
}