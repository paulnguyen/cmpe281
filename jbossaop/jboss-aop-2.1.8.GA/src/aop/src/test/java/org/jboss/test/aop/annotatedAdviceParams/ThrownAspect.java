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

import java.io.IOException;
import java.rmi.AccessException;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Thrown;

/**
 * Aspect used on @Thrown parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ThrownAspect
{
   // as a convention, the number of the thrown exception should never be 0, so we
   // can identify when the number hasn't been set in ThrownAspect, and when it has
   
   public static String throwingAdvice;
   public static Throwable throwingThrown;
   public static int throwingNumber;
   public static String finallyAdvice;
   public static Throwable finallyThrown;
   public static int finallyNumber;
   
   
   public static void clear()
   {
      throwingAdvice = null;
      throwingThrown = null;
      throwingNumber = 0;
      finallyAdvice = null;
      finallyThrown = null;
      finallyNumber = 0;
   }
   
   public void throwing1()
   {
      throwingAdvice = "throwing1";
   }
   
   public void throwing2(@Thrown Throwable throwable, @Arg int i)
   {
      throwingAdvice = "throwing2";
      throwingNumber = i;
      throwingThrown = throwable;
      ((POJOException) throwable).number = throwingNumber + 1;
   }
   
   public void throwing3(@Thrown Exception exception)
   {
      throwingAdvice = "throwing3";
      throwingNumber = ((POJOException) exception).number;
      throwingThrown = exception;
   }
   
   public void throwing4(@Thrown POJOException pojoException, @Arg int i)
   {
      throwingAdvice = "throwing4";
      throwingNumber = i;
      throwingThrown = pojoException;
   }
   
   public void throwing5(@Thrown IOException runtimeException)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing6(@Thrown Throwable throwable)
   {
      throwingAdvice = "throwing6";
      throwingThrown = throwable;
   }
   
   public void throwing8(@Arg int i)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing9(@Thrown AccessException throwable)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing10(@Thrown RuntimeException throwable)
   {
      throwingAdvice = "throwing10";
      throwingThrown = throwable;
   }
   
   public void throwing11(@Thrown NullPointerException throwable)
   {
      throwingAdvice = "throwing11";
      throwingThrown = throwable;
   }
   
   public void finally1(@Thrown Throwable throwable)
   {
      finallyAdvice = "finally1";
      finallyThrown = throwable;
      finallyNumber = ((POJOException) throwable).number;
      ((POJOException) throwable).number = finallyNumber + 1;
   }
   
   public void finally2()
   {
      finallyAdvice = "finally2";
   }
   
   public void finally3(@Thrown POJOException throwable)
   {
      finallyAdvice = "finally3";
      finallyThrown = throwable;
      finallyNumber = throwable.number;
   }
 
   public void finally4(@Arg int i, @Thrown Throwable throwable)
   {
      finallyAdvice = "finally4";
      finallyThrown = throwable;
      finallyNumber = i;
   }
   
   public void finally5(@Thrown Object throwable)
   {
      finallyAdvice = "finally5";
      finallyThrown = (Throwable) throwable;
      finallyNumber = ((POJOException) throwable).number;
   }
   
   public void finally6(@Thrown Throwable throwable)
   {
      finallyAdvice = "finally6";
      finallyThrown = throwable;
   }
}