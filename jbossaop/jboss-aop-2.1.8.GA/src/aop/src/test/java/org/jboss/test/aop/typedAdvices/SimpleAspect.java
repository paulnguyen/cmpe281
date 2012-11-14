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
package org.jboss.test.aop.typedAdvices;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.Invocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 65215 $
 */
public class SimpleAspect
{
   public static boolean before;
   public static boolean around;
   public static boolean after;
   public static boolean throwing;
   public static boolean finallyAdvice;
   public static Throwable exception;
   
   public static void clear()
   {
      before = false;
      around = false;
      after = false;
      throwing = false;
      finallyAdvice = false;
      POJO.joinPointRun = false;
   }
   
   public void before()
   {
      System.out.println("SimpleAspect.before");
      before = true;
      Assert.assertFalse(POJO.joinPointRun);
   }

   public Object around(Invocation invocation) throws Throwable
   {
      System.out.println("SimpleAspect.around");
      around = true;
      Assert.assertFalse(POJO.joinPointRun);
      Object result = invocation.invokeNext();
      Assert.assertTrue(POJO.joinPointRun);
      return result;
   }

   //Do we have to return the same exception as the target joinpoint?
   public int after(@Return int i)
   {
      System.out.println("SimpleAspect.after");
      after = true;
      Assert.assertTrue(POJO.joinPointRun);
      return i;
   }

   public void throwing(@Thrown Throwable t)
   {
      System.out.println("SimpleAspect.throwing");
      exception = t;
      throwing = true;
   }
   
   public void finallyAdvice()
   {
      System.out.println("SimpleAspect.finallyAdvice");
      finallyAdvice = true;
      Assert.assertTrue(POJO.joinPointRun);
   }
}