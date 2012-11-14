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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.CurrentInvocation;

/**
 * Aspect used on @Arg parameter tests that involve generic argument types.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspect
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspetInterfaces
 */
@SuppressWarnings({"unchecked"})
public class ArgAspectGenerics
{
   static boolean before1 = false;
   static boolean before2 = false;
   static boolean before4 = false;
   static boolean before5 = false;
   static boolean before6 = false;
      
   static boolean around1 = false;
   static boolean around2 = false;
   static boolean around3 = false;
   static boolean around4 = false;
   static boolean around6 = false;
   
   static boolean after1 = false;
   static boolean after2 = false;
   static boolean after3 = false;
   static boolean after4 = false;
   static boolean after6 = false;

   static boolean throwing1 = false;
   static boolean throwing3 = false;
   static boolean throwing4 = false;
   static boolean throwing5 = false;
   static boolean throwing6 = false;
   
   static boolean finally1 = false;
   static boolean finally2 = false;
   static boolean finally3 = false;
   static boolean finally4 = false;
   static boolean finally6 = false;
      
   public static void clear()
   {
      before1 = false;
      before2 = false;
      before4 = false;
      before5 = false;
      before6 = false;
      
      around1 = false;
      around2 = false;
      around3 = false;
      around4 = false;
      around6 = false;
      
      after1 = false;
      after2 = false;
      after3 = false;
      after4 = false;
      after6 = false;
      
      throwing1 = false;
      throwing3 = false;
      throwing4 = false;
      throwing5 = false;
      throwing6 = false;
      
      finally1 = false;
      finally2 = false;
      finally3 = false;
      finally4 = false;
      finally6 = false;
   }
   
   public void before1(@Arg List<SuperValue> arg)
   {
      before1 = true;
   }
   
   public void before2(@Arg List<?> arg)
   {
      before2 = true;
   }
  
   public void before3(@Arg List<SubValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before4(@Arg List arg)
   {
      before4 = true;
   }
   
   public void before5(@Arg Collection<SuperValue> arg)
   {
      before5 = true;
   }
   
   public void before6(@Arg Collection arg)
   {
      before6 = true;
   }
   
   public void before7(@Arg ArrayList<SuperValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void before8(@Arg Set<SuperValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void around1(@Arg Collection<SuperValue> arg) throws Throwable
   {
      around1 = true;
      CurrentInvocation.proceed();
   }
   
   public void around2(@Arg Collection arg) throws Throwable
   {
      around2 = true;
      CurrentInvocation.proceed();
   }
   
   public void around3(@Arg List<SuperValue> arg) throws Throwable
   {
      around3 = true;
      CurrentInvocation.proceed();
   }
   
   public void around4(@Arg List<? extends Object> arg) throws Throwable
   {
      around4 = true;
      CurrentInvocation.proceed();
   }
   
   public void around5(@Arg List<Interface> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      CurrentInvocation.proceed();
   }
   
   public void around6(@Arg List arg) throws Throwable
   {
      around6 = true;
      CurrentInvocation.proceed();
   }
   
   public void around7(@Arg ArrayList<Object> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void around8(@Arg Set<Object> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
   }
   
   public ArgsPOJO around1_Cons(@Arg Collection<SuperValue> arg) throws Throwable
   {
      around1 = true;
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around2_Cons(@Arg Collection arg) throws Throwable
   {
      around2 = true;
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around3_Cons(@Arg List<SuperValue> arg) throws Throwable
   {
      around3 = true;
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around4_Cons(@Arg List<? extends Object> arg) throws Throwable
   {
      around4 = true;
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around5_Cons(@Arg List<Interface> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around6_Cons(@Arg List arg) throws Throwable
   {
      around6 = true;
      return (ArgsPOJO) CurrentInvocation.proceed();
   }
   
   public ArgsPOJO around7_Cons(@Arg ArrayList<Object> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public ArgsPOJO around8_Cons(@Arg Set<Object> arg) throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public void after1(@Arg List<SuperValue> arg)
   {
      after1 = true;
   }
   
   public <A> void after2(@Arg List<A> arg)
   {
      after2 = true;
   }
   
   public void after3(@Arg List arg)
   {
      after3 = true;
   }
   
   public void after4(@Arg Collection<SuperValue> arg)
   {
      after4 = true;
   }
   
   public void after5(@Arg Collection<Object> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after6(@Arg Collection arg)
   {
      after6 = true;
   }
   
   public void after7(@Arg ArrayList<SubValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after8(@Arg Set<SubValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing1(@Arg Collection<SuperValue> arg, @Thrown Throwable t)
   {
      throwing1 = true;
   }
   
   public void throwing2(@Arg Collection<SubValue> arg, @Thrown Throwable t)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing3(@Arg Collection arg, @Thrown Throwable t)
   {
      throwing3 = true;
   }
   
   public void throwing4(@Arg List<SuperValue> arg, @Thrown Throwable t)
   {
      throwing4 = true;
   }
   
   public void throwing5(@Arg List<?> arg, @Thrown Throwable t)
   {
      throwing5 = true;
   }
   
   public void throwing6(@Arg List arg, @Thrown Throwable t)
   {
      throwing6 = true;
   }
   
   public void throwing7(@Arg ArrayList<Interface> arg, @Thrown Throwable t)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void throwing8(@Arg Set<Interface> arg, @Thrown Throwable t)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally1(@Arg List<SuperValue> arg)
   {
      finally1 = true;
   }
   
   public <A extends Object> void finally2(@Arg List<A> arg)
   {
      finally2 = true;
   }
   
   public void finally3(@Arg List arg)
   {
      finally3 = true;
   }
   
   public void finally4(@Arg Collection<SuperValue> arg)
   {
      finally4 = true;
   }
   
   public void finally5(@Arg Collection<Interface> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally6(@Arg Collection arg)
   {
      finally6 = true;
   }
   
   public void finally5(@Arg ArrayList arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally5(@Arg Set arg)
   {
      Assert.fail("This advice should never be executed");
   }
}