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

import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.CurrentInvocation;

/**
 * Aspect used on @Return parameter tests that involve generic return types.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspect
 * @see org.jboss.test.aop.annotatedAdviceParams.ArgAspetInterfaces
 */
@SuppressWarnings({"unchecked"})
public class ReturnAspectGenerics
{
   static boolean around3 = false;
   static boolean around6 = false;
   
   static boolean after1 = false;
   static boolean after2 = false;
   static boolean after3 = false;
   static boolean after4 = false;
   static boolean after6 = false;
   static boolean after9 = false;
   static boolean after10 = false;

   static boolean finally1 = false;
   static boolean finally2 = false;
   static boolean finally3 = false;
   static boolean finally4 = false;
   static boolean finally6 = false;
   static boolean finally11 = false;
      
   public static void clear()
   {
      around3 = false;
      around6 = false;
      
      after1 = false;
      after2 = false;
      after3 = false;
      after4 = false;
      after6 = false;
      after9 = false;
      after10 = false;
      
      finally1 = false;
      finally2 = false;
      finally3 = false;
      finally4 = false;
      finally6 = false;
      finally11 = false;
   }
   
   public Collection<SuperValue> around1() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public Collection around2() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public List<SuperValue> around3() throws Throwable
   {
      around3 = true;
      return (List<SuperValue>) CurrentInvocation.proceed();
   }
   
   public List<? extends Object> around4() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public List<Interface> around5() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public List around6() throws Throwable
   {
      around6 = true;
      return (List) CurrentInvocation.proceed();
   }
   
   public ArrayList<Object> around7() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public Set<Object> around8() throws Throwable
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public void after1(@Return List<SuperValue> arg)
   {
      after1 = true;
   }
   
   public <A> void after2(@Return List<A> arg)
   {
      after2 = true;
   }
   
   public void after3(@Return List arg)
   {
      after3 = true;
   }
   
   public void after4(@Return Collection<SuperValue> arg)
   {
      after4 = true;
   }
   
   public void after5(@Return Collection<Object> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after6(@Return Collection arg)
   {
      after6 = true;
   }
   
   public void after7(@Return ArrayList<SubValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void after8(@Return Set<SubValue> arg)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public ArrayList<SuperValue> after9()
   {
      after9 = true;
      return null;
   }
   
   public List<SuperValue> after10()
   {
      after10 = true;
      return null;
   }
   
   public void finally1(@Return List<SuperValue> arg, @Thrown Throwable thrown)
   {
      finally1 = true;
   }
   
   public <A extends Object> void finally2(@Return List<A> arg, @Thrown Throwable thrown)
   {
      finally2 = true;
   }
   
   public void finally3(@Return List arg, @Thrown Throwable thrown)
   {
      finally3 = true;
   }
   
   public void finally4(@Return Collection<SuperValue> arg, @Thrown Throwable thrown)
   {
      finally4 = true;
   }
   
   public void finally5(@Return Collection<Interface> arg, @Thrown Throwable thrown)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally6(@Return Collection arg, @Thrown Throwable thrown)
   {
      finally6 = true;
   }
   
   public void finally7(@Return ArrayList arg, @Thrown Throwable thrown)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public void finally8(@Return Set arg, @Thrown Throwable thrown)
   {
      Assert.fail("This advice should never be executed");
   }
   
   public List<?> finally9()
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public ArrayList<?> finally10()
   {
      Assert.fail("This advice should never be executed");
      return null;
   }
   
   public ArrayList<SuperValue> finally11()
   {
      finally11 = true;
      return null;
   }
}