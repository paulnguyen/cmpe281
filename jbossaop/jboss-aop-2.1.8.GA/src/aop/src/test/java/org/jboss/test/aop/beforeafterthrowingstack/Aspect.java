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
package org.jboss.test.aop.beforeafterthrowingstack;

import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.Invocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class Aspect
{
   public static int aroundBefore;
   public static int aroundAfter;
   public static int aroundThrowing;
   public static int aroundFinally;
   
   public static int before;
   public static int after;
   public static int throwing;
   public static int finaly;
   
   public static Aspect aspect;
   
   public Object around(Invocation inv) throws Throwable
   {
      try
      {
         aroundBefore++;
         setAspect(true);
         Object o = inv.invokeNext();
         aroundAfter++;
         return o;
      }
      catch(ThrownByTestException e)
      {
         aroundThrowing++;
         throw e;
      }
      finally
      {
         aroundFinally++;
      }
   }
   
   
   public void before()
   {
      before++;
      setAspect(false);
   }
   
   public void after()
   {
      setAspect(true);
      after++;
   }
   
   public void throwing(@Thrown Throwable e)
   {
      setAspect(true);
      throwing++;
   }
   
   public void finaly()
   {
      setAspect(true);
      finaly++;
   }
   
   public static void reset()
   {
      aroundBefore = 0;
      aroundAfter = 0;
      aroundThrowing = 0;
      aroundFinally = 0;
      before = 0;
      after = 0;
      throwing = 0;
      finaly = 0;
      aspect = null;
   }
   
   private void setAspect(boolean shouldHaveBeenSet)
   {
      if (aspect == null && shouldHaveBeenSet)
      {
         throw new RuntimeException("Aspect should have existed");
      }
      
      if (aspect != null)
      {
         if (aspect != this)
         {
            throw new RuntimeException("Wrong instance");
         }
      }
      
      aspect = this;
   }
}
