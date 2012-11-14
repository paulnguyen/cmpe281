/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.stress.batf;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SimpleAspect
{
   public static boolean before;
   public static boolean after;
   public static boolean throwing;
   public static boolean finaly;
   
   public Object methodWithAroundNoExceptions(Invocation inv) throws Throwable
   {
      try
      {
         before = true;
         return inv.invokeNext();
      }
      finally
      {
         after = true;
      }
   }
   
   public Object methodWithAroundExceptions(Invocation inv) throws Throwable
   {
      try
      {
         before = true;
         return inv.invokeNext();
      }
      catch(Exception e)
      {
         throwing = true;
         throw e;
      }
      finally
      {
         finaly = true;
      }
   }
   
   public Object methodWithSimulatedBefore(Invocation inv) throws Throwable
   {
      before = true;
      return inv.invokeNext();
   }
   
   public void before()
   {
      before = true;
   }
   
   public void after()
   {
      after = true;
   }
   
   public void throwing(@Thrown Throwable e)
   {
      throwing = true;
   }
   
   public void finaly()
   {
      finaly = true;
   }
   
   public static void reset()
   {
      before = false;
      after = false;
      throwing = false;
      finaly = false;
      string = null;
   }

   public static String string;
   public Object aroundWithAroundArguments(MethodInvocation invocation) throws Throwable
   {
      string = (String)invocation.getArguments()[0];
      return invocation.invokeNext();
   }
   
   public void beforeWithTypedArguments(@Arg String s)
   {
      string = s;
   }

}
