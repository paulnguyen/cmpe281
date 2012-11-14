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
package org.jboss.test.aop.dynamicgenadvisor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.metadata.DynamicSimpleClassMetadataLoader;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
@SuppressWarnings({"unused", "static-access"})
public class DynamicTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("DynamicTester");
      suite.addTestSuite(DynamicTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public DynamicTester(String name)
   {
      super(name);
   }

   public void testNotAdvised() throws Exception
   {
      System.out.println("TEST NOT ADVISED");
      Interceptions.clear();
      POJO pojo = new POJO();
      pojo.i = 10;
      pojo.j = 20;
      pojo.someMethod(123);
      pojo.notPrepared();
      assertTrue(Interceptions.isEmpty());
   }
   
   public void testConstructor() throws Exception
   {
      System.out.println("TEST CONSTRUCTOR");
      Interceptions.clear();
      AdviceBinding binding = new AdviceBinding("execution(org.jboss.test.aop.dynamicgenadvisor.POJO->new())", null);
      String name = binding.getName();
      binding.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding);
      
      POJO pojo = new POJO();
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getConstructorName("MyInterceptor", "POJO"), Interceptions.get(0));
      
      AdviceBinding binding2 = new AdviceBinding("execution(org.jboss.test.aop.dynamicgenadvisor.POJO->new())", null);
      String name2 = binding2.getName();
      AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
      binding2.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
      binding2.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding2);
      
      Interceptions.clear();
      pojo = new POJO();
      assertEquals(Interceptions.getToString(), 3, Interceptions.size());
      assertEquals(Interceptions.getConstructorName("MyInterceptor", "POJO"), Interceptions.get(0));
      assertEquals(Interceptions.getConstructorName("MyAspect", "POJO"), Interceptions.get(1));
      assertEquals(Interceptions.getConstructorName("MyInterceptor", "POJO"), Interceptions.get(2));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name);
      pojo = new POJO();
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getConstructorName("MyAspect", "POJO"), Interceptions.get(0));
      assertEquals(Interceptions.getConstructorName("MyInterceptor", "POJO"), Interceptions.get(1));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name2);
      pojo = new POJO();
      assertTrue(Interceptions.isEmpty());
   }

   
   public void testConstruction() throws Exception
   {
      System.out.println("TEST CONSTRUCTION");
      Interceptions.clear();
      AdviceBinding binding = new AdviceBinding("construction(org.jboss.test.aop.dynamicgenadvisor.POJO->new())", null);
      String name = binding.getName();
      binding.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding);
      
      POJO pojo = new POJO();
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getConstructionName("MyInterceptor", "POJO"), Interceptions.get(0));
      
      AdviceBinding binding2 = new AdviceBinding("construction(org.jboss.test.aop.dynamicgenadvisor.POJO->new())", null);
      String name2 = binding2.getName();
      AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
      binding2.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
      binding2.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding2);
      
      Interceptions.clear();
      pojo = new POJO();
      assertEquals(3, Interceptions.size());
      assertEquals(Interceptions.getConstructionName("MyInterceptor", "POJO"), Interceptions.get(0));
      assertEquals(Interceptions.getConstructionName("MyAspect", "POJO"), Interceptions.get(1));
      assertEquals(Interceptions.getConstructionName("MyInterceptor", "POJO"), Interceptions.get(2));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name);
      pojo = new POJO();
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getConstructionName("MyAspect", "POJO"), Interceptions.get(0));
      assertEquals(Interceptions.getConstructionName("MyInterceptor", "POJO"), Interceptions.get(1));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name2);
      pojo = new POJO();
      assertTrue(Interceptions.isEmpty());
   }

   public void testMethodExecution() throws Exception
   {
      System.out.println("TEST METHOD");
      Interceptions.clear();
      AdviceBinding binding = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
      String name = binding.getName();
      binding.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding);
      
      POJO pojo = new POJO();
      pojo.someMethod(123);
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
      
      AdviceBinding binding2 = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
      String name2 = binding2.getName();
      AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
      binding2.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
      binding2.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding2);
      
      Interceptions.clear();
      pojo.someMethod(123);
      assertEquals(Interceptions.getToString(), 3, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
      assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(2));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name);
      pojo.someMethod(123);
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(0));
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(1));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name2);
      pojo.someMethod(123);
      assertTrue(Interceptions.isEmpty());
   }

   public void testMethodExecutionWithInstanceAdvisorAccess() throws Exception
   {
      System.out.println("TEST METHOD WITH INSTANCEADVISOR ACCESS");
      AdviceBinding binding = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
      binding.addInterceptor(MetadataInterceptor.class);
      AspectManager.instance().addBinding(binding);

      POJO pojo = new POJO();
      MetadataInterceptor.clear();
      pojo.someMethod(123);
      assertTrue(MetadataInterceptor.intercepted);
      
      MetadataInterceptor.clear();
      AspectManager.instance().removeBinding(binding.getName());
      pojo.someMethod(123);
      assertFalse(MetadataInterceptor.intercepted);
   }

   public void testFields() throws Exception
   {
      System.out.println("TEST FIELDS");
      AdviceBinding binding = new AdviceBinding("set(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
      String name = binding.getName();
      binding.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding);
      
      Interceptions.clear();
      POJO pojo = new POJO();
      pojo.i = 5;
      assertEquals(5, pojo.i);
      POJO.j = 0;
      assertEquals(0, POJO.j);
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));

      AdviceBinding binding2 = new AdviceBinding("get(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
      String name2 = binding2.getName();
      binding2.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding2);
      
      Interceptions.clear();
      pojo.i = 5;
      assertEquals(5, pojo.i);
      POJO.j++;
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(1));

      AdviceBinding binding3 = new AdviceBinding("field(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
      String name3 = binding3.getName();
      AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
      binding3.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
      binding3.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding3);
      
      Interceptions.clear();
      pojo.i = 20;
      assertEquals(3, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldWriteName("MyAspect", "POJO", "i"), Interceptions.get(1));
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(2));
      
      Interceptions.clear();
      assertEquals(20, pojo.i);
      assertEquals(3, Interceptions.size());
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(1));
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(2));
      
      AspectManager.instance().removeBinding(name);
      AspectManager.instance().removeBinding(name2);

      AdviceBinding binding4 = new AdviceBinding("field(static * org.jboss.test.aop.dynamicgenadvisor.POJO->j)", null);
      String name4 = binding4.getName();
      binding4.addInterceptor(MyInterceptor.class);
      AspectManager.instance().addBinding(binding4);
      
      Interceptions.clear();
      pojo.i=15;
      assertEquals(15, pojo.i);
      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyAspect", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(1));
      assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(2));
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(3));

      Interceptions.clear();
      assertEquals(2, ++POJO.j);
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "j"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "j"), Interceptions.get(1));
      
      Interceptions.clear();
      AspectManager.instance().removeBinding(name3);
      AspectManager.instance().removeBinding(name4);
      pojo.i++;
      POJO.j++;
      assertTrue(Interceptions.isEmpty());
   }

   public void testSimpleInstanceUsingDomainForMethod()throws Exception
   {
      System.out.println("TEST SIMPLE INSTANCE USING DOMAIN FOR METHOD");
      POJO pojo1 = new POJO();
      POJO pojo2 = new POJO();
      
      AdviceBinding binding = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
      String name = binding.getName();
      binding.addInterceptor(MyInterceptor.class);
      getInstanceDomain(pojo2).addBinding(binding);
      
      Interceptions.clear();
      pojo1.someMethod(123);
      assertTrue(Interceptions.isEmpty());
      
      Interceptions.clear();
      pojo2.someMethod(123);
      assertEquals(Interceptions.getToString(), 1, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));

      getInstanceDomain(pojo2).removeBinding(name);
      
      Interceptions.clear();
      pojo1.someMethod(123);
      pojo2.someMethod(123);
      assertTrue(Interceptions.isEmpty());
      
      
   }

   public void testInstanceUsingDomainForMethod()throws Exception
   {
      String nameTopA = null, nameTopB = null;
      try
      {
         System.out.println("TEST INSTANCE USING DOMAIN FOR METHOD");
         AdviceBinding bindingTopA = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
         nameTopA = bindingTopA.getName();
         bindingTopA.addInterceptor(MyInterceptor.class);
         AspectManager.instance().addBinding(bindingTopA);
         
         POJO pojo1 = new POJO();
         POJO pojo2 = new POJO();
         
         Interceptions.clear();
         pojo1.someMethod(123);
         pojo2.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(1));
         
         //Add instance advices
         AdviceBinding bindingA = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
         String nameA = bindingA.getName();
         bindingA.addInterceptor(MyInterceptor.class);
         getInstanceDomain(pojo1).addBinding(bindingA);
         
         AdviceBinding bindingB = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
         String nameB = bindingB.getName();
         AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
         bindingB.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
         getInstanceDomain(pojo2).addBinding(bindingB);
         
         System.out.println("Added instance advices");
         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(1));
         
         
         Interceptions.clear();
         pojo2.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));
        
         //Add top level advice
         POJO pojo3 = new POJO();
         AdviceBinding bindingTopB = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..))", null);
         nameTopB = bindingTopB.getName();
         bindingTopB.addInterceptor(YourInterceptor.class);
         AspectManager.instance().addBinding(bindingTopB);
         
         System.out.println("Added top level advice");
         
         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(3, Interceptions.size());
         Interceptions.printInterceptions();
         //Since we are adding at the top-level that will get added to the end of the chain, i.e. the order is no longer guaranteed
         checkInterceptions(createExpectedList(
               Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), 
               Interceptions.getMethodName("YourInterceptor", "POJO", "someMethod"), 
               Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod")));
   
         Interceptions.clear();
         pojo2.someMethod(123);
         assertEquals(3, Interceptions.size());
         //Since we are adding at the top-level that will get added to the end of the chain, i.e. the order is no longer guaranteed
         checkInterceptions(createExpectedList(
               Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), 
               Interceptions.getMethodName("YourInterceptor", "POJO", "someMethod"),
               Interceptions.getMethodName("MyAspect", "POJO", "someMethod")));
   
         
         Interceptions.clear();
         pojo3.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("YourInterceptor", "POJO", "someMethod"), Interceptions.get(1));
   
         
         AspectManager.instance().removeBinding(nameTopB);
   
         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(1));
         
         Interceptions.clear();
         pojo2.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));
         
         getInstanceDomain(pojo1).removeBinding(nameA);
         
         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(1, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         
         AspectManager.instance().removeBinding(nameTopA);
         
         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(0, Interceptions.size());
         
         Interceptions.clear();
         
         pojo2.someMethod(123);
         assertEquals(1, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(0));
   
         getInstanceDomain(pojo2).removeBinding(nameB);
   
         Interceptions.clear();
         pojo1.someMethod(123);
         pojo2.someMethod(123);
         assertEquals(0, Interceptions.size());
      }
      finally
      {
         if (nameTopA != null)
         {
            AspectManager.instance().removeBinding(nameTopA);
         }
         if (nameTopB != null)
         {
            AspectManager.instance().removeBinding(nameTopB);
         }
      }
   }
   
   public void testSimpleInstanceUsingDomainForField() throws Exception
   {
      System.out.println("TEST SIMPLE INSTANCE USING DOMAIN FOR FIELD");
      POJO pojo1 = new POJO();
      POJO pojo2 = new POJO();
      
      AdviceBinding binding = new AdviceBinding("set(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
      String name = binding.getName();
      AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
      binding.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
      getInstanceDomain(pojo2).addBinding(binding);
      
      Interceptions.clear();
      pojo1.i = 10;
      assertEquals(10, pojo1.i);
      assertTrue(Interceptions.isEmpty());
      
      Interceptions.clear();
      pojo2.i = 20;
      assertEquals(20, pojo2.i);
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyAspect", "POJO", "i"), Interceptions.get(0));

      AdviceBinding binding2 = new AdviceBinding("get(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
      String name2 = binding2.getName();
      binding2.addInterceptor(MyInterceptor.class);
      getInstanceDomain(pojo2).addBinding(binding2);
      
      Interceptions.clear();
      pojo2.i = 30;
      assertEquals(30, pojo2.i);
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getFieldWriteName("MyAspect", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(1));

      
      getInstanceDomain(pojo2).removeBinding(name);
      
      Interceptions.clear();
      pojo1.i = 15;
      assertEquals(15, pojo1.i);
      assertTrue(Interceptions.isEmpty());
      
      pojo2.i = 40;
      assertEquals(0, Interceptions.size());
      assertEquals(40, pojo2.i);
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(0));

      getInstanceDomain(pojo2).removeBinding(name2);
      
      Interceptions.clear();
      pojo1.i = pojo2.i;
      pojo2.i = pojo2.i;
      assertTrue(Interceptions.isEmpty());
   }

   public void testInstanceUsingDomainForField()throws Exception
   {
      String nameTopA = null;
      String nameTopB = null;
      try
      {
         System.out.println("TEST INSTANCE USING DOMAIN FOR FIELD");
         AdviceBinding bindingTopA = new AdviceBinding("field(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
         nameTopA = bindingTopA.getName();
         bindingTopA.addInterceptor(MyInterceptor.class);
         AspectManager.instance().addBinding(bindingTopA);
         
         POJO pojo1 = new POJO();
         POJO pojo2 = new POJO();
         
         Interceptions.clear();
         pojo1.i = 10;
         pojo2.i=20;
         assertEquals(10, pojo1.i);
         assertEquals(20, pojo2.i);
         assertEquals(4, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(2));
         assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(3));
         
         //Add instance advices
         AdviceBinding bindingA = new AdviceBinding("set(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
         String nameA = bindingA.getName();
         bindingA.addInterceptor(MyInterceptor.class);
         getInstanceDomain(pojo1).addBinding(bindingA);
         
         AdviceBinding bindingB = new AdviceBinding("get(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
         String nameB = bindingB.getName();
         AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
         bindingB.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
         getInstanceDomain(pojo2).addBinding(bindingB);
         
         System.out.println("Added instance advices");
         Interceptions.clear();
         pojo1.i = 50;
         assertEquals(50, pojo1.i);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(2));
         
         Interceptions.clear();
         pojo2.i = 100;
         assertEquals(100, pojo2.i);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(2));
        
         //Add top level advice
         POJO pojo3 = new POJO();
         AdviceBinding bindingTopB = new AdviceBinding("field(* org.jboss.test.aop.dynamicgenadvisor.POJO->i)", null);
         nameTopB = bindingTopB.getName();
         bindingTopB.addInterceptor(YourInterceptor.class);
         AspectManager.instance().addBinding(bindingTopB);
         
         System.out.println("Added top level advice");
         
         Interceptions.clear();
         pojo1.i = 66;
         assertEquals(66, pojo1.i);
         assertEquals(5, Interceptions.size());
         //Since we are adding at the top-level that will get added to the end of the chain, i.e. the order is no longer guaranteed
         checkInterceptions(createExpectedList(
               Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), 
               Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), 
               Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), 
               Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"),
               Interceptions.getFieldReadName("YourInterceptor", "POJO", "i")));
   
         Interceptions.clear();
         pojo2.i = 99;
         assertEquals(99, pojo2.i);
         assertEquals(5, Interceptions.size());
         //Since we are adding at the top-level that will get added to the end of the chain, i.e. the order is no longer guaranteed
         checkInterceptions(createExpectedList(
               Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"),
               Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), 
               Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), 
               Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), 
               Interceptions.getFieldReadName("MyAspect", "POJO", "i")));
   
         
         Interceptions.clear();
         pojo3.i = 12;
         assertEquals(12, pojo3.i);
         assertEquals(4, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(2));
         assertEquals(Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), Interceptions.get(3));
   
         
         AspectManager.instance().removeBinding(nameTopA);
         Interceptions.clear();
         pojo1.i = 50;
         assertEquals(50, pojo1.i);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), Interceptions.get(2));
         
         Interceptions.clear();
         pojo2.i = 100;
         assertEquals(100, pojo2.i);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(2));
   
   
         System.out.println("================> removing binding");
         getInstanceDomain(pojo1).removeBinding(nameA);
   
         
         Interceptions.clear();
         pojo1.i = 50;
         assertEquals(50, pojo1.i);
         Interceptions.printInterceptions();
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), Interceptions.get(1));
         
         Interceptions.clear();
         pojo2.i = 100;
         assertEquals(100, pojo2.i);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getFieldWriteName("YourInterceptor", "POJO", "i"), Interceptions.get(0));
         assertEquals(Interceptions.getFieldReadName("YourInterceptor", "POJO", "i"), Interceptions.get(1));
         assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(2));
   
         AspectManager.instance().removeBinding(nameTopB);
         Interceptions.clear();
         pojo1.i = 14;
         assertEquals(14, pojo1.i);
         pojo2.i = 100;
         assertEquals(100, pojo2.i);
         assertEquals(1, Interceptions.size());
         assertEquals(Interceptions.getFieldReadName("MyAspect", "POJO", "i"), Interceptions.get(0));
   
         getInstanceDomain(pojo2).removeBinding(nameB);
         
         Interceptions.clear();
         pojo1.i = 123;
         assertEquals(123, pojo1.i);
         pojo2.i = 123;
         assertEquals(123, pojo2.i);
         assertEquals(0, Interceptions.size());
      }
      finally
      {
         if (nameTopA != null)
         {
            AspectManager.instance().removeBinding(nameTopA);
         }
         if (nameTopB != null)
         {
            AspectManager.instance().removeBinding(nameTopB);
         }
      }
   }
   
   public void testInstanceUsingOldApiForMethod() throws Exception
   {
      System.out.println("TEST INSTANCE USING OLD API FOR METHOD");
      POJO pojo1 = new POJO();
      AdviceBinding bindingTopA = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.*POJO->someMethod*(..))", null);
      String nameTopA = bindingTopA.getName();
      try
      {
         AspectDefinition myAspect = AspectManager.instance().getAspectDefinition("org.jboss.test.aop.dynamicgenadvisor.MyAspect"); 
         bindingTopA.addInterceptorFactory(new AdviceFactory(myAspect, "intercept"));
         AspectManager.instance().addBinding(bindingTopA);

         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(1, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(0));

         System.out.println("---- Adding more interceptors");

         InstanceAdvisor pojoIa1 = ((Advised)pojo1)._getInstanceAdvisor();
         pojoIa1.insertInterceptor(new MyInterceptor());

         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(2, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));

         pojoIa1.appendInterceptor(new YourInterceptor());

         Interceptions.clear();
         pojo1.someMethod(123);
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));
         assertEquals(Interceptions.getMethodName("YourInterceptor", "POJO", "someMethod"), Interceptions.get(2));

         System.out.println("Testing SubPOJO");
         SubPOJO sub1 = new SubPOJO();
         Interceptions.clear();
         sub1.someMethod(123);
         assertEquals(1, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(0));

         InstanceAdvisor subPojoIa1 = ((Advised)sub1)._getInstanceAdvisor();
         subPojoIa1.insertInterceptor(new MyInterceptor());
         subPojoIa1.appendInterceptor(new YourInterceptor());

         Interceptions.clear();
         sub1.someMethod(123);
         assertEquals(3, Interceptions.size());
         assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
         assertEquals(Interceptions.getMethodName("MyAspect", "POJO", "someMethod"), Interceptions.get(1));
         assertEquals(Interceptions.getMethodName("YourInterceptor", "POJO", "someMethod"), Interceptions.get(2));
      }
      finally
      {
         AspectManager.instance().removeBinding(nameTopA);
      }
   }
   
   public void testAddAnnotation() throws Exception
   {
      System.out.println("TEST ADD ANNOTATION TO INSTANCE");
      POJO pojo1 = new POJO();
      POJO pojo2 = new POJO();
      POJO pojo3 = new POJO();
      POJO pojo4 = new POJO();

      AnnotationIntroduction intro1 = AnnotationIntroduction.createClassAnnotationIntroduction(
            "org.jboss.test.aop.dynamicgenadvisor.POJO",
            "@org.jboss.test.aop.dynamicgenadvisor.MyAnnotation",
            true);
      getInstanceDomain(pojo1).addAnnotationOverride(intro1);
      AdviceBinding binding1 = new AdviceBinding("execution(* @org.jboss.test.aop.dynamicgenadvisor.MyAnnotation->someMethod*(..))", null);
      binding1.addInterceptor(MyInterceptor.class);
      getInstanceDomain(pojo1).addBinding(binding1);

      AnnotationIntroduction intro2 = AnnotationIntroduction.createFieldAnnotationIntroduction(
            "int org.jboss.test.aop.dynamicgenadvisor.POJO->i",
            "@org.jboss.test.aop.dynamicgenadvisor.MyAnnotation",
            true);
      getInstanceDomain(pojo2).addAnnotationOverride(intro2);
      AdviceBinding binding2 = new AdviceBinding("field(* org.jboss.test.aop.dynamicgenadvisor.POJO->@org.jboss.test.aop.dynamicgenadvisor.MyAnnotation)", null);
      binding2.addInterceptor(MyInterceptor.class);
      getInstanceDomain(pojo2).addBinding(binding2);

      AnnotationIntroduction intro3 = AnnotationIntroduction.createMethodAnnotationIntroduction(
            "* org.jboss.test.aop.dynamicgenadvisor.POJO->someMethod(..)",
            "@org.jboss.test.aop.dynamicgenadvisor.MyAnnotation",
            true);
      getInstanceDomain(pojo3).addAnnotationOverride(intro3);
      AdviceBinding binding3 = new AdviceBinding("execution(* org.jboss.test.aop.dynamicgenadvisor.POJO->@org.jboss.test.aop.dynamicgenadvisor.MyAnnotation(..))", null);
      binding3.addInterceptor(MyInterceptor.class);
      getInstanceDomain(pojo3).addBinding(binding3);

      
      System.out.println("--- POJO1");
      Interceptions.clear();
      pojo1.someMethod(123);
      pojo1.notPrepared();
      pojo1.i = pojo1.i;
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
      
      System.out.println("--- POJO2");
      Interceptions.clear();
      pojo2.someMethod(123);
      pojo2.notPrepared();
      pojo2.i = pojo2.i;
      assertEquals(2, Interceptions.size());
      assertEquals(Interceptions.getFieldReadName("MyInterceptor", "POJO", "i"), Interceptions.get(0));
      assertEquals(Interceptions.getFieldWriteName("MyInterceptor", "POJO", "i"), Interceptions.get(1));

      System.out.println("--- POJO3");
      Interceptions.clear();
      pojo3.someMethod(123);
      pojo3.notPrepared();
      pojo3.i = pojo3.i;
      assertEquals(1, Interceptions.size());
      assertEquals(Interceptions.getMethodName("MyInterceptor", "POJO", "someMethod"), Interceptions.get(0));
      
      System.out.println("--- POJO4");
      Interceptions.clear();
      pojo4.someMethod(123);
      pojo4.notPrepared();
      pojo4.i = pojo4.i;
      assertEquals(0, Interceptions.size());
   }
   
   
   public void testAddClassMetadata()throws Exception
   {
      AdviceBinding binding = null;
      try
      {
         System.out.println("TEST ADD CLASS METADATA");
   
         DynamicSimpleClassMetadataLoader loader = new DynamicSimpleClassMetadataLoader(
               "T1", "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");
         loader.addDefaultMetaData("default", "default");
         loader.addClassMetaData("class", "class");
         loader.addMethodMetaData("* someMethod(..)", "fm", "someMethod");
         loader.addFieldMetaData("i", "fm", "i");
         AspectManager.instance().addClassMetaData(loader.getClassMetaDataBinding());
         
         binding = new AdviceBinding("all(org.jboss.test.aop.dynamicgenadvisor.POJO)", null);
         binding.addInterceptor(MetadataInterceptor.class);
         AspectManager.instance().addBinding(binding);
         
         POJO pojo = new POJO();
         
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         pojo.i = 100;
         pojo.notPrepared();
         assertTrue(MetadataInterceptor.intercepted);
         assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
         
         //Make sure we get the metadata expected
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
         
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
         
         assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
         
         assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));
         
         AspectManager.instance().removeClassMetaData(loader.getClassMetaDataBinding().getName());
   
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         pojo.i = 100;
         pojo.notPrepared();
         assertEquals(0, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(0, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(0, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(0, MetadataInterceptor.lastFieldMetadata.size());
         
         //Try adding metadata again now that instance already exists
         AspectManager.instance().addClassMetaData(loader.getClassMetaDataBinding());
   
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         pojo.i = 100;
         pojo.notPrepared();
         assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
         
         //Make sure we get the metadata expected
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
         
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
         
         assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
         
         assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));
         
         AspectManager.instance().removeClassMetaData(loader.getClassMetaDataBinding().getName());
   
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         pojo.i = 100;
         pojo.notPrepared();
         assertEquals(0, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(0, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(0, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(0, MetadataInterceptor.lastFieldMetadata.size());
         
         AspectManager.instance().removeBinding(binding.getName());
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         assertFalse(MetadataInterceptor.intercepted);
      }
      finally
      {
         if (binding != null)
         {
            AspectManager.instance().removeBinding(binding.getName());
         }
      }
   }

   public void testBindingsUpdatedWhenAddMetadata() throws Exception
   {
      AdviceBinding binding = null;
      try
      {
         System.out.println("TEST BINDINGS UPDATED WHEN ADD METADATA");
         
         binding = new AdviceBinding("all(@TEST)", null);
         binding.addInterceptor(MetadataInterceptor.class);
         AspectManager.instance().addBinding(binding);
         
         MetadataInterceptor.clear();
         POJO pojox = new POJO();
         pojox.someMethod(123);
         assertFalse(MetadataInterceptor.intercepted);
         
         //Add metadata so binding takes effect
         DynamicSimpleClassMetadataLoader loader = new DynamicSimpleClassMetadataLoader(
               "DA", "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");
               loader.addDefaultMetaData("default", "default");
               loader.addClassMetaData("class", "class");
               loader.addMethodMetaData("* someMethod(..)", "fm", "someMethod");
               loader.addFieldMetaData("i", "fm", "i");
               AspectManager.instance().addClassMetaData(loader.getClassMetaDataBinding());
               
         MetadataInterceptor.clear();
         POJO pojo = new POJO();
         pojo.someMethod(123);
         int i = pojo.i;
         assertTrue(MetadataInterceptor.intercepted);
         assertEquals(3, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(3, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
         
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(2));
         
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(2));
         
         assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
         
         assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));
         
         MetadataInterceptor.clear();
         pojox.someMethod(123);
         assertTrue(MetadataInterceptor.intercepted);
         
         AspectManager.instance().removeClassMetaData("DA");
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         pojo.i = 100;
         assertFalse(MetadataInterceptor.intercepted);
         
         //Add metadata to instance domain so binding takes effect
         getInstanceDomain(pojo).addClassMetaData(loader.getClassMetaDataBinding());
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         i = pojo.i;
         assertTrue(MetadataInterceptor.intercepted);
         assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
         assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
         assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
         assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
         
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
         assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
         
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
         assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
         
         assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
         
         assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));
         
         //Clean up
         getInstanceDomain(pojo).removeClassMetaData(loader.getClassMetaDataBinding().getName());
         AspectManager.instance().removeBinding(binding.getName());
         MetadataInterceptor.clear();
         pojo.someMethod(123);
         assertFalse(MetadataInterceptor.intercepted);
      }
      finally
      {
         if (binding != null)
         {
            AspectManager.instance().removeBinding(binding.getName());
         }
      }
   }
   
   public void testAddInstanceMetadata() throws Exception
   {
      System.out.println("TEST ADD METADATA TO INSTANCE");
      POJO pojo1 = new POJO();
      POJO pojo2 = new POJO();
      POJO pojo3 = new POJO();

      DynamicSimpleClassMetadataLoader loader1 = new DynamicSimpleClassMetadataLoader(
            "D1", "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");
      loader1.addDefaultMetaData("default", "default1");
      loader1.addClassMetaData("class", "class1");
      loader1.addMethodMetaData("* someMethod(..)", "fm", "someMethod1");
      loader1.addFieldMetaData("i", "fm", "i1");
      
      DynamicSimpleClassMetadataLoader loader2 = new DynamicSimpleClassMetadataLoader(
            "D1", "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");  //Use same name for loaders, they're in different domains
      loader2.addDefaultMetaData("default", "default2");
      loader2.addClassMetaData("class", "class2");
      loader2.addMethodMetaData("* someMethod(..)", "fm", "someMethod2");
      loader2.addFieldMetaData("i", "fm", "i2");
      
      getInstanceDomain(pojo1).addClassMetaData(loader1.getClassMetaDataBinding());
      AdviceBinding binding1 = new AdviceBinding("all(@TEST)", null);
      binding1.addInterceptor(MetadataInterceptor.class);
      getInstanceDomain(pojo1).addBinding(binding1);
      
      AdviceBinding binding2 = new AdviceBinding("all(@TEST)", null);
      getInstanceDomain(pojo2).addClassMetaData(loader2.getClassMetaDataBinding());
      binding2.addInterceptor(MetadataInterceptor.class);
      getInstanceDomain(pojo2).addBinding(binding2);
      
      AdviceBinding binding3 = new AdviceBinding("all(org.jboss.test.aop.dynamicgenadvisor.POJO)", null);
      binding3.addInterceptor(MetadataInterceptor.class);
      getInstanceDomain(pojo3).addBinding(binding3);
      
      MetadataInterceptor.clear();
      System.out.println("---POJO1 set i");
      pojo1.i = 10;
      System.out.println("---POJO1 someMethod");
      pojo1.someMethod(123);
      System.out.println("---POJO1 not Prepared");
      pojo1.notPrepared();
      
      System.out.println("---POJO2 set i");
      pojo2.i = 20;
      System.out.println("---POJO2 someMethod");
      pojo2.someMethod(123);
      System.out.println("---POJO2 not Prepared");
      pojo2.notPrepared();
      
      assertEquals(4, MetadataInterceptor.lastDefaultMetadata.size());
      assertEquals(4, MetadataInterceptor.lastClassMetadata.size());
      assertEquals(2, MetadataInterceptor.lastMethodMetadata.size());
      assertEquals(2, MetadataInterceptor.lastFieldMetadata.size());
      
      //Make sure we get the metadata expected
      assertEquals("default1", MetadataInterceptor.lastDefaultMetadata.get(0));
      assertEquals("default1", MetadataInterceptor.lastDefaultMetadata.get(1));
      assertEquals("default2", MetadataInterceptor.lastDefaultMetadata.get(2));
      assertEquals("default2", MetadataInterceptor.lastDefaultMetadata.get(3));
      
      assertEquals("class1", MetadataInterceptor.lastClassMetadata.get(0));
      assertEquals("class1", MetadataInterceptor.lastClassMetadata.get(1));
      assertEquals("class2", MetadataInterceptor.lastClassMetadata.get(2));
      assertEquals("class2", MetadataInterceptor.lastClassMetadata.get(3));
      
      assertEquals("someMethod1", MetadataInterceptor.lastMethodMetadata.get(0));
      assertEquals("someMethod2", MetadataInterceptor.lastMethodMetadata.get(1));
      
      assertEquals("i1", MetadataInterceptor.lastFieldMetadata.get(0));
      assertEquals("i2", MetadataInterceptor.lastFieldMetadata.get(1));

      
      //This one has no metadata
      MetadataInterceptor.clear();
      System.out.println("---POJO3 set i");
      pojo3.i = 20;
      System.out.println("---POJO3 someMethod");
      pojo3.someMethod(123);
      System.out.println("---POJO3 not Prepared");
      pojo3.notPrepared();
      
      assertTrue(MetadataInterceptor.intercepted);
      assertEquals(0, MetadataInterceptor.lastDefaultMetadata.size());
      assertEquals(0, MetadataInterceptor.lastClassMetadata.size());
      assertEquals(0, MetadataInterceptor.lastMethodMetadata.size());
      assertEquals(0, MetadataInterceptor.lastFieldMetadata.size());
      
   }
   
   
   public void testInstanceMetadataOverrides() throws Exception
   {
      System.out.println("TEST INSTANCE METADATA OVERRIDES");

      MetadataInterceptor.clear();
      POJO pojo = new POJO();
      POJO pojo1 = new POJO();
      pojo.someMethod(123);
      assertFalse(MetadataInterceptor.intercepted);

      AdviceBinding binding1 = new AdviceBinding("all(org.jboss.test.aop.dynamicgenadvisor.POJO)", null);
      binding1.addInterceptor(MetadataInterceptor.class);
      AspectManager.instance().addBinding(binding1);

      DynamicSimpleClassMetadataLoader loader = new DynamicSimpleClassMetadataLoader(
            "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");
      loader.addDefaultMetaData("default", "default");
      loader.addClassMetaData("class", "class");
      loader.addMethodMetaData("* someMethod(..)", "fm", "someMethod");
      loader.addFieldMetaData("i", "fm", "i");
      AspectManager.instance().addClassMetaData(loader.getClassMetaDataBinding());
      
      DynamicSimpleClassMetadataLoader instanceLoader = new DynamicSimpleClassMetadataLoader(
            "TEST", "org.jboss.test.aop.dynamicgenadvisor.POJO");
      instanceLoader.addDefaultMetaData("default", "defaultXXX");
      instanceLoader.addClassMetaData("class", "classXXX");
      instanceLoader.addMethodMetaData("* someMethod(..)", "fm", "someMethodXXX");
      instanceLoader.addFieldMetaData("i", "fm", "iXXX");
      getInstanceDomain(pojo1).addClassMetaData(instanceLoader.getClassMetaDataBinding());

      MetadataInterceptor.clear();
      pojo.someMethod(123);
      pojo.i = 1000;
      assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
      assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
      assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
      assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
      
      //Make sure we get the metadata expected
      assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
      assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
      
      assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
      assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
      
      assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
      
      assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));

      MetadataInterceptor.clear();
      pojo1.someMethod(123);
      pojo1.i = 1000;
      assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
      assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
      assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
      assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
      
      //Make sure we get the metadata expected
      assertEquals("defaultXXX", MetadataInterceptor.lastDefaultMetadata.get(0));
      assertEquals("defaultXXX", MetadataInterceptor.lastDefaultMetadata.get(1));
      
      assertEquals("classXXX", MetadataInterceptor.lastClassMetadata.get(0));
      assertEquals("classXXX", MetadataInterceptor.lastClassMetadata.get(1));
      
      assertEquals("someMethodXXX", MetadataInterceptor.lastMethodMetadata.get(0));
      
      assertEquals("iXXX", MetadataInterceptor.lastFieldMetadata.get(0));


      getInstanceDomain(pojo1).removeClassMetaData(instanceLoader.getClassMetaDataBinding().getName());
      
      MetadataInterceptor.clear();
      pojo1.someMethod(123);
      pojo1.i = 1000;
      assertEquals(2, MetadataInterceptor.lastDefaultMetadata.size());
      assertEquals(2, MetadataInterceptor.lastClassMetadata.size());
      assertEquals(1, MetadataInterceptor.lastMethodMetadata.size());
      assertEquals(1, MetadataInterceptor.lastFieldMetadata.size());
      
      //Make sure we get the metadata expected
      assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(0));
      assertEquals("default", MetadataInterceptor.lastDefaultMetadata.get(1));
      
      assertEquals("class", MetadataInterceptor.lastClassMetadata.get(0));
      assertEquals("class", MetadataInterceptor.lastClassMetadata.get(1));
      
      assertEquals("someMethod", MetadataInterceptor.lastMethodMetadata.get(0));
      
      assertEquals("i", MetadataInterceptor.lastFieldMetadata.get(0));
      
      AspectManager.instance().removeClassMetaData(loader.getClassMetaDataBinding().getName());
      AspectManager.instance().removeBinding(binding1.getName());
      MetadataInterceptor.clear();
      pojo.someMethod(123);
      assertFalse(MetadataInterceptor.intercepted);
   }
   
   private Domain getInstanceDomain(Object obj)
   {
      Advised advised = ((Advised)obj);
      InstanceAdvisor advisor = advised._getInstanceAdvisor(); 
      return advisor.getDomain();
   }
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }
   
   private List<String> createExpectedList(String...elements)
   {
      return Arrays.asList(elements);
   }
   
   private void checkInterceptions(List<String> expected)
   {
      List<String> buf = new ArrayList<String>();
      buf.addAll(expected);
      
      assertEquals(buf.size(), Interceptions.size());
      for (int i = 0 ; i < Interceptions.size() ; i++)
      {
         String name = Interceptions.get(i);
         boolean found = false;
         for (Iterator<String> it = buf.iterator() ; it.hasNext() ; i++)
         {
            String expectedName = it.next();
            if (expectedName.equals(name))
            {
               it.remove();
               found = true;
            }
         }
         if (!found)
         {
            fail("Unexpected interception " + name);
         }
      }
   }
}
