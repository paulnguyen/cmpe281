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
package org.jboss.test.aop.benchmark;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.dynamic.Interceptor1;

/**
 * A DynamicAddRemoveBenchTester.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class DynamicAddRemoveBenchTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("DynamicAddRemoveBenchTester");
      suite.addTestSuite(DynamicAddRemoveBenchTester.class);
      return suite;
   }
   
   public DynamicAddRemoveBenchTester(String name)
   {
      super(name);
   }
   
   public void testMethodPerformance() throws ParseException
   {
      int size = 500;
      ArrayList<AdviceBinding> bindings = new ArrayList<AdviceBinding>(size);
      long time = System.currentTimeMillis();
      for(int i = 0; i < size; i++)
      {
         AdviceBinding binding = new AdviceBinding( 
               "execution(public void org.jboss.test.aop.benchmark.POJO->foo())", null); 
         binding.setName("method"+i);
         binding.addInterceptor(Interceptor1.class);
         bindings.add(binding);
      }
      System.out.println("Creating the methodbindings took: "+(System.currentTimeMillis()-time));
      
      POJO p = new POJO();
      p.foo();
      
      time = System.currentTimeMillis();
      for(AdviceBinding ab : bindings)
      {
         AspectManager.instance().addBinding(ab);
      }
      System.out.println("Adding the methodbindings took: "+(System.currentTimeMillis()-time));
      
      time = System.currentTimeMillis();
      for(AdviceBinding ab : bindings)
      {
         AspectManager.instance().removeBinding(ab.getName());
      }
      System.out.println("Removing the methodbindings took: "+(System.currentTimeMillis()-time));
      
      
      assertTrue(true);
   }
   
   public void testFieldPerformance() throws ParseException
   {
      int size = 500;
      ArrayList<AdviceBinding> bindings = new ArrayList<AdviceBinding>(size);
      long time = System.currentTimeMillis();
      for(int i = 0; i < size/2; i++)
      {
         AdviceBinding binding = new AdviceBinding( 
               "set(* org.jboss.test.aop.benchmark.POJO->i)", null); 
         binding.setName("set"+i);
         binding.addInterceptor(Interceptor1.class);
         bindings.add(binding);
         
         AdviceBinding binding2 = new AdviceBinding( 
               "get(* org.jboss.test.aop.benchmark.POJO->i)", null); 
         binding2.setName("get"+i);
         binding2.addInterceptor(Interceptor1.class);
         bindings.add(binding2);
         
      }
      System.out.println("Creating the fieldbindings took: "+(System.currentTimeMillis()-time));
      
      POJO p = new POJO();
      p.foo();
      
      time = System.currentTimeMillis();
      for(AdviceBinding ab : bindings)
      {
         AspectManager.instance().addBinding(ab);
      }
      System.out.println("Adding the fieldbindings took: "+(System.currentTimeMillis()-time));
      
      time = System.currentTimeMillis();
      for(AdviceBinding ab : bindings)
      {
         AspectManager.instance().removeBinding(ab.getName());
      }
      System.out.println("Removing the fieldbindings took: "+(System.currentTimeMillis()-time));
      
      
      assertTrue(true);
   }


}
