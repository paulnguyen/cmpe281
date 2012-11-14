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

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.joinpoint.FieldAccess;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodExecution;

/**
 * Aspect used on overloaded around advice tests (for JoinPoint, Arg and Args tests).
 *
 * This class includes hierarchy on a single Arg parameter.
 *
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedBeforeAspect
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
   }
   
   /* BEFORE1 ADVICE */
   
   public void before1(@JoinPoint FieldAccess joinPoint, @Arg String text)
   {
      before1 = "FieldAccess,String";
   }

   public void before1(@JoinPoint FieldAccess joinPoint, @Arg Object text)
   {
      before1 = "FieldAccess,Object";
   }
   
   public void before1(@JoinPoint JoinPointBean joinPoint, @Arg String text)
   {
      before1 = "JoinPointBean,String";
   }

   public void before1(@JoinPoint JoinPointBean joinPoint, @Arg Object text)
   {
      before1 = "JoinPointBean,Object";
   }
   
   public void before1(@JoinPoint Object joinPoint, @Arg String text)
   {
      before1 = "Object,String";
   }

   public void before1(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before1 = "Object,Object";
   }
   
   public void before1(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before1 = "FieldAccess,Object[]";
   }
   
   public void before1(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before1 = "JoinPointBean,Object[]";
   }
   
   public void before1(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before1 = "Object,Object[]";
   }

   public void before1(@JoinPoint FieldAccess joinPoint)
   {
      before1 = "FieldAccess";
   }
   
   public void before1(@JoinPoint JoinPointBean joinPoint)
   {
      before1 = "JoinPointBean";
   }

   public void before1(@JoinPoint Object joinPoint)
   {
      before1 = "Object";
   }
   
   public void before1(@Arg String text)
   {
      before1 = "String";
   }
   
   public void before1(@Args Object[] args)
   {
      before1 = "Object[]";
   }
      
   public void before1()
   {
      before1 = "";
   }
   
   public void before1(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before1(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
 
   /* BEFORE2 ADVICE */
   
   public void before2(@JoinPoint FieldAccess joinPoint, @Arg Object text)
   {
      before2 = "FieldAccess,Object";
   }
   
   public void before2(@JoinPoint JoinPointBean joinPoint, @Arg String text)
   {
      before2 = "JoinPointBean,String";
   }

   public void before2(@JoinPoint JoinPointBean joinPoint, @Arg Object text)
   {
      before2 = "JoinPointBean,Object";
   }

   public void before2(@JoinPoint Object joinPoint, @Arg String text)
   {
      before2 = "Object,String";
   }

   public void before2(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before2 = "Object,Object";
   }

   public void before2(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before2 = "FieldAccess,Object[]";
   }
   
   public void before2(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before2 = "FieldAccess,Object[]";
   }
   
   public void before2(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before2 = "Object,Object[]";
   }

   public void before2(@JoinPoint FieldAccess joinPoint)
   {
      before2 = "FieldAccess";
   }
   
   public void before2(@JoinPoint JoinPointBean joinPoint)
   {
      before2 = "JoinPointBean";
   }

   public void before2(@JoinPoint Object joinPoint)
   {
      before2 = "Object";
   }
   
   public void before2(@Arg String text)
   {
      before2 = "String";
   }
   
   public void before2(@Args Object[] args)
   {
      before2 = "Object[]";
   }
      
   public void before2()
   {
      before2 = "";
   }
   
   public void before2(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before2(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE3 ADVICE */
   
   public void before3(@JoinPoint JoinPointBean joinPoint, @Arg String text)
   {
      before3 = "JoinPointBean,String";
   }

   public void before3(@JoinPoint JoinPointBean joinPoint, @Arg Object text)
   {
      before3 = "JoinPointBean,Object";
   }

   public void before3(@JoinPoint Object joinPoint, @Arg String text)
   {
      before3 = "Object,String";
   }

   public void before3(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before3 = "Object,Object";
   }

   public void before3(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before3 = "FieldAccess,Object[]";
   }

   public void before3(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before3 = "JoinPointBean,Object[]";
   }

   public void before3(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before3 = "Object,Object[]";
   }
   
   public void before3(@JoinPoint FieldAccess joinPoint)
   {
      before3 = "FieldAccess";
   }
   
   public void before3(@JoinPoint JoinPointBean joinPoint)
   {
      before3 = "JoinPointBean";
   }

   public void before3(@JoinPoint Object joinPoint)
   {
      before3 = "Object";
   }
   
   public void before3(@Arg String text)
   {
      before3 = "String";
   }
   
   public void before3(@Args Object[] args)
   {
      before3 = "Object[]";
   }
      
   public void before3()
   {
      before3 = "";
   }
   
   public void before3(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before3(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE4 ADVICE */
   
   public void before4(@JoinPoint JoinPointBean joinPoint, @Arg Object text)
   {
      before4 = "JoinPointBean,Object";
   }

   public void before4(@JoinPoint Object joinPoint, @Arg String text)
   {
      before4 = "Object,String";
   }

   public void before4(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before4 = "Object,Object";
   }
   
   public void before4(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before4 = "FieldAccess,Object[]";
   }

   public void before4(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before4 = "JoinPointBean,Object[]";
   }

   public void before4(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before4 = "Object,Object[]";
   }

   public void before4(@JoinPoint FieldAccess joinPoint)
   {
      before4 = "FieldAccess";
   }
   
   public void before4(@JoinPoint JoinPointBean joinPoint)
   {
      before4 = "JoinPointBean";
   }

   public void before4(@JoinPoint Object joinPoint)
   {
      before4 = "Object";
   }
   
   public void before4(@Arg String text)
   {
      before4 = "String";
   }

   public void before4(@Args Object[] args)
   {
      before4 = "Object[]";
   }
   
   public void before4()
   {
      before4 = "";
   }
   
   public void before4(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before4(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE5 ADVICE */
      
   public void before5(@JoinPoint Object joinPoint, @Arg String text)
   {
      before5 = "Object,String";
   }

   public void before5(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before5 = "Object,Object";
   }
   
   public void before5(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before5 = "FieldAccess,Object[]";
   }

   public void before5(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before5 = "JoinPointBean,Object[]";
   }

   public void before5(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before5 = "Object,Object[]";
   }
   
   public void before5(@JoinPoint FieldAccess joinPoint)
   {
      before5 = "FieldAccess";
   }
   
   public void before5(@JoinPoint JoinPointBean joinPoint)
   {
      before5 = "JoinPointBean";
   }

   public void before5(@JoinPoint Object joinPoint)
   {
      before5 = "Object";
   }
   
   public void before5(@Arg String text)
   {
      before5 = "String";
   }
      
   public void before5(@Args Object[] args)
   {
      before5 = "Object[]";
   }
   
   public void before5()
   {
      before5 = "";
   }
   
   public void before5(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before5(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE6 ADVICE */
   
   public void before6(@JoinPoint Object joinPoint, @Arg Object text)
   {
      before6 = "Object,Object";
   }
   
   public void before6(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before6 = "FieldAccess,Object[]";
   }

   public void before6(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before6 = "JoinPointBean,Object[]";
   }

   public void before6(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before6 = "Object,Object[]";
   }

   public void before6(@JoinPoint FieldAccess joinPoint)
   {
      before6 = "FieldAccess";
   }
   
   public void before6(@JoinPoint JoinPointBean joinPoint)
   {
      before6 = "JoinPointBean";
   }

   public void before6(@JoinPoint Object joinPoint)
   {
      before6 = "Object";
   }
   
   public void before6(@Arg String text)
   {
      before6 = "String";
   }
   
   public void before6(@Args Object[] args)
   {
      before6 = "Object[]";
   }
      
   public void before6()
   {
      before6 = "";
   }
   
   public void before6(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before6(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE7 ADVICE */
   
   public void before7(@JoinPoint FieldAccess joinPoint, @Args Object[] args)
   {
      before7 = "FieldAccess,Object[]";
   }

   public void before7(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before7 = "JoinPointBean,Object[]";
   }

   public void before7(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before7 = "Object,Object[]";
   }
   
   public void before7(@JoinPoint FieldAccess joinPoint)
   {
      before7 = "FieldAccess";
   }
   
   public void before7(@JoinPoint JoinPointBean joinPoint)
   {
      before7 = "JoinPointBean";
   }

   public void before7(@JoinPoint Object joinPoint)
   {
      before7 = "Object";
   }
   
   public void before7(@Arg String text)
   {
      before7 = "String";
   }
    
   public void before7(@Args Object[] args)
   {
      before7 = "Object[]";
   }
   
   public void before7()
   {
      before7 = "";
   }
   
   public void before7(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before7(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE8 ADVICE */
   
   public void before8(@JoinPoint JoinPointBean joinPoint, @Args Object[] args)
   {
      before8 = "JoinPointBean,Object[]";
   }

   public void before8(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before8 = "Object,Object[]";
   }
   
   public void before8(@JoinPoint FieldAccess joinPoint)
   {
      before8 = "FieldAccess";
   }
   
   public void before8(@JoinPoint JoinPointBean joinPoint)
   {
      before8 = "JoinPointBean";
   }

   public void before8(@JoinPoint Object joinPoint)
   {
      before8 = "Object";
   }
   
   public void before8(@Arg String text)
   {
      before8 = "String";
   }
    
   public void before8(@Args Object[] args)
   {
      before8 = "Object[]";
   }
   
   public void before8()
   {
      before8 = "";
   }
   
   public void before8(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before8(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE9 ADVICE */
   
   public void before9(@JoinPoint Object joinPoint, @Args Object[] args)
   {
      before9 = "Object,Object[]";
   }
   
   public void before9(@JoinPoint FieldAccess joinPoint)
   {
      before9 = "FieldAccess";
   }
   
   public void before9(@JoinPoint JoinPointBean joinPoint)
   {
      before9 = "JoinPointBean";
   }

   public void before9(@JoinPoint Object joinPoint)
   {
      before9 = "Object";
   }
   
   public void before9(@Arg String text)
   {
      before9 = "String";
   }
      
   public void before9(@Args Object[] args)
   {
      before9 = "Object[]";
   }
   
   public void before9()
   {
      before9 = "";
   }
   
   public void before9(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before9(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE10 ADVICE */
   
   public void before10(@JoinPoint FieldAccess joinPoint)
   {
      before10 = "FieldAccess";
   }
   
   public void before10(@JoinPoint JoinPointBean joinPoint)
   {
      before10 = "JoinPointBean";
   }
   
   public void before10(@JoinPoint Object joinPoint)
   {
      before10 = "Object";
   }
   
   public void before10(@Arg String text)
   {
      before10 = "String";
   }
   
   public void before10(@Args Object[] args)
   {
      before10 = "Object[]";
   }
   
   public void before10()
   {
      before10 = "";
   }
   
   public void before10(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before10(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE11 ADVICE */
   
   public void before11(@JoinPoint JoinPointBean joinPoint)
   {
      before11 = "JoinPointBean";
   }
   
   public void before11(@JoinPoint Object joinPoint)
   {
      before11 = "Object";
   }
   
   public void before11(@Arg String text)
   {
      before11 = "String";
   }

   public void before11(@Args Object[] args)
   {
      before11 = "Object[]";
   }
   
   public void before11()
   {
      before11 = "";
   }
   
   public void before11(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before11(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE12 ADVICE */
   
   public void before12(@JoinPoint Object joinPoint)
   {
      before12 = "Object";
   }
   
   public void before12(@Arg String text)
   {
      before12 = "String";
   }

   public void before12(@Args Object[] args)
   {
      before12 = "Object[]";
   }
   
   public void before12()
   {
      before12 = "";
   }
   
   public void before12(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before12(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE13 ADVICE */
   
   public void before13(@Arg String text)
   {
      before13 = "String";
   }
   
   public void before13(@Arg Object text)
   {
      before13 = "Object";
   }
   
   public void before13(@Args Object[] args)
   {
      before13 = "Object[]";
   }
   
   public void before13()
   {
      before13 = "";
   }
   
   public void before13(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before13(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE14 ADVICE */
   
   public void before14(@Arg Object text)
   {
      before14 = "Object";
   }
   
   public void before14(@Args Object[] args)
   {
      before14 = "Object[]";
   }
   
   public void before14()
   {
      before14 = "";
   }
   
   public void before14(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before14(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE15 ADVICE */
   
   public void before15(@Args Object[] args)
   {
      before15 = "Object[]";
   }
   
   public void before15()
   {
      before15 = "";
   }
   
   public void before15(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before15(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE16 ADVICE */
   
   public void before16()
   {
      before16 = "";
   }
   
   public void before16(@JoinPoint MethodExecution methodExecution)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before16(@Arg SuperValue text)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* BEFORE17 ADVICE */
   
   public void before17(@JoinPoint FieldAccess fieldAccess, @Arg String text)
   {
      before17 = "FieldAccess,String";
   }
   
   public void before17(@JoinPoint FieldAccess fieldAccess)
   {
      before17 = "FieldAccess";
   }
}