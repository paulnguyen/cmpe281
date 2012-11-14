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
 * Aspect used on @Arg parameter tests that involve interface hirarchy on joinpoint
 * arguments.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspect
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspectInvertedArgs
 */
public class ArgAspectInterfaces
{
   static boolean before1 = false;
   static boolean before2 = false;
   static boolean before3 = false;
   static boolean around1 = false;
   static boolean around2 = false;
   static boolean around3 = false;
   static boolean after1 = false;
   static boolean after2 = false;
   static boolean after3 = false;
   static boolean throwing1 = false;
   static boolean throwing2 = false;
   static boolean throwing3 = false;
   static boolean finally1 = false;
   static boolean finally2 = false;
   static boolean finally3 = false;
   
   public static void clear()
   {
      before1 = false;
      before2 = false;
      before3 = false;
      around1 = false;
      around2 = false;
      around3 = false;
      after1 = false;
      after2 = false;
      after3 = false;
      throwing1 = false;
      throwing2 = false;
      throwing3 = false;
      finally1 = false;
      finally2 = false;
      finally3 = false;
   }
   
   public void before1(@Arg Interface param)
   {
      before1 = true;
   }
   
   public void before2(@Arg SuperInterface param)
   {
      before2 = true;
   }
   
   public void before3(@Arg Object param)
   {
      before3 = true;
   }
   
   public void before4(@Arg Implementor param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before5(@Arg SubInterface param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public Object around1(@Arg Interface param) throws Throwable
   {
      around1 = true;
      return CurrentInvocation.proceed();
   }
   
   public Object around2(@Arg SuperInterface param) throws Throwable
   {
      around2 = true;
      return CurrentInvocation.proceed();
   }
   
   public Object around3(@Arg Object param) throws Throwable
   {
      around3 = true;
      return CurrentInvocation.proceed();
   }
   
   public Object around4(@Arg Implementor param) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return CurrentInvocation.proceed();
   }
   
   public Object around5(@Arg SubInterface param) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return CurrentInvocation.proceed();
   }
   
   public void after1(@Arg Interface param)
   {
      after1 = true;
   }
   
   public void after2(@Arg SuperInterface param)
   {
      after2 = true;
   }
   
   public void after3(@Arg Object param)
   {
      after3 = true;
   }
   
   public void after4(@Arg Implementor param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after5(@Arg SubInterface param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface param)
   {
      throwing1 = true;
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface param)
   {
      throwing2 = true;
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Object param)
   {
      throwing3 = true;
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Implementor param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally1(@Arg Interface param)
   {
      finally1 = true;
   }
   
   public void finally2(@Arg SuperInterface param)
   {
      finally2 = true;
   }
   
   public void finally3(@Arg Object param)
   {
      finally3 = true;
   }
   
   public void finally4(@Arg Implementor param)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally5(@Arg SubInterface param)
   {
      Assert.fail("This advice should never be executed");
   }
}