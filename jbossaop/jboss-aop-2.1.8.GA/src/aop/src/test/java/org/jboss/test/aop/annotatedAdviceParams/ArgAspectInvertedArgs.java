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
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.CurrentInvocation;

/**
 * Aspect used on @Arg parameter tests that involve arguments in inverted positions.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspect
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspetInterfaces
 */
public class ArgAspectInvertedArgs
{
   static boolean before1 = false;
   static boolean around1 = false;
   static boolean after1 = false;
   static boolean throwing1 = false;
   static boolean finally1 = false;
   
   public static void clear()
   {
      before1 = false;
      around1 = false;
      after1 = false;
      throwing1 = false;
      finally1 = false;
   }
   
   public void before1(@Arg Object arg2, @Arg (index = 0) String arg1)
   {
      before1 = true;
   }
   
   public void before2(@Arg (index = 0) Object arg2,
         @Arg (index = 0) String arg1)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public Object around1(@Arg Object arg2, @Arg (index = 0) String arg1)
      throws Throwable
   {
      around1 = true;
      return CurrentInvocation.proceed();
   }
   
   public void around2(@Arg (index = 0) Object arg2,
         @Arg (index = 0) String arg1)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after1(@Arg Object arg2, @Arg (index = 0) String arg1)
   {
      after1 = true;
   }
   
   public void after2(@Arg (index = 0) Object arg2,
         @Arg (index = 0) String arg1)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Object arg2,
         @Arg (index = 0) String arg1)
   {
      throwing1 = true;
   }
   
   public void throwing2(@Arg (index = 0) Object arg2,
         @Arg (index = 0) String arg1)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally1(@Arg Object arg2, @Arg (index = 0) String arg1)
   {
      finally1 = true;
   }
   
   public void finally2(@Arg (index = 0) Object arg2,
         @Arg (index = 0) String arg1)
   {
      Assert.fail("This advice should never be executed");
   }
}   