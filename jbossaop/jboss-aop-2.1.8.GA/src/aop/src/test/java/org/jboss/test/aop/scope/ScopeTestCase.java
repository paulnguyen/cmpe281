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
package org.jboss.test.aop.scope;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jboss.aop.Advised;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.util.MethodHashing;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Primarily to make sure I got everything right for the generated advisors
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 73238 $
 */
@SuppressWarnings({"unused", "unchecked"})
public class ScopeTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(ScopeTestCase.class);
   }

   public ScopeTestCase(String arg0)
   {
      super(arg0);
   }

   public void testSimplePerVmAspect()
   {
      Interceptions.clear();
      POJOWithAspect pojo = new POJOWithAspect();
      assertEquals(5, pojo.perVmA(5));
      pojo.perVmB();
      
      POJO2WithAspect pojo2 = new POJO2WithAspect();
      pojo2.perVmA(100);
      pojo2.perVmB();
      
      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertEquals(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerVmInterceptor()
   {
      Interceptions.clear();
      POJOWithInterceptor pojo = new POJOWithInterceptor();
      assertEquals(5, pojo.perVmA(5));
      pojo.perVmB();

      POJO2WithInterceptor pojo2 = new POJO2WithInterceptor();
      pojo2.perVmA(50);
      pojo2.perVmB();

      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertEquals(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerVmAspectFactory()
   {
      Interceptions.clear();
      POJOWithAspectFactory pojo = new POJOWithAspectFactory();
      assertEquals(5, pojo.perVmA(5));
      pojo.perVmB();

      POJO2WithAspectFactory pojo2 = new POJO2WithAspectFactory();
      pojo2.perVmA(15);
      pojo2.perVmB();

      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertEquals(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerVmInterceptorFactory()
   {
      Interceptions.clear();
      POJOWithInterceptorFactory pojo = new POJOWithInterceptorFactory();
      assertEquals(5, pojo.perVmA(5));
      pojo.perVmB();

      POJO2WithInterceptorFactory pojo2 = new POJO2WithInterceptorFactory();
      pojo2.perVmA(15);
      pojo2.perVmB();

      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertEquals(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerClassAspect()
   {
      Interceptions.clear();
      POJOWithAspect pojo = new POJOWithAspect();
      assertEquals(5, pojo.perClazzA(5));
      pojo.perClazzB();

      POJO2WithAspect pojo2 = new POJO2WithAspect();
      pojo2.perClazzA(5);
      pojo2.perClazzB();

      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerClassInterceptor()
   {
      Interceptions.clear();
      POJOWithInterceptor pojo = new POJOWithInterceptor();
      assertEquals(5, pojo.perClazzA(5));
      pojo.perClazzB();

      POJO2WithInterceptor pojo2 = new POJO2WithInterceptor();
      pojo2.perClazzA(50);
      pojo2.perClazzB();

      for (int i = 0 ; i < Interceptions.size() ; i++)
      {
         System.out.println("intercepted by " + Interceptions.get(i));
      }
      
      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerClassAspectFactory()
   {
      Interceptions.clear();
      POJOWithAspectFactory pojo = new POJOWithAspectFactory();
      assertEquals(5, pojo.perClazzA(5));
      pojo.perClazzB();

      POJO2WithAspectFactory pojo2 = new POJO2WithAspectFactory();
      pojo2.perClazzA(5);
      pojo2.perClazzB();

      
      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   
   public void testSimplePerClassInterceptorFactory()
   {
      Interceptions.clear();
      POJOWithInterceptorFactory pojo = new POJOWithInterceptorFactory();
      assertEquals(5, pojo.perClazzA(5));
      pojo.perClazzB();
      
      POJO2WithInterceptorFactory pojo2 = new POJO2WithInterceptorFactory();
      pojo2.perClazzA(5);
      pojo2.perClazzB();
      
      for (int i = 0 ; i < Interceptions.size() ; i++)
      {
         System.out.println("intercepted by " + Interceptions.get(i));
      }
      assertEquals(4, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertEquals(Interceptions.get(2), Interceptions.get(3));
   }
   

   public void testSimplePerInstanceAspect()
   {
      Interceptions.clear();
      POJOWithAspect pojo = new POJOWithAspect();
      POJO2WithAspect pojo2 = new POJO2WithAspect();
      POJOWithAspect pojo3 = new POJOWithAspect();
      POJO2WithAspect pojo4 = new POJO2WithAspect();

      assertEquals(5, pojo.perInstanceA(5));
      pojo2.perInstanceA(5);
      pojo3.perInstanceA(5);
      pojo4.perInstanceA(5);
 
      pojo.perInstanceB();
      pojo2.perInstanceB();
      pojo3.perInstanceB();
      pojo4.perInstanceB();
      
      
      assertEquals(8, Interceptions.size());
      
      assertEquals(Interceptions.get(0), Interceptions.get(4));
      assertEquals(Interceptions.get(1), Interceptions.get(5));
      assertEquals(Interceptions.get(2), Interceptions.get(6));
      assertEquals(Interceptions.get(3), Interceptions.get(7));

      assertNotSame(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(0), Interceptions.get(2));
      assertNotSame(Interceptions.get(0), Interceptions.get(3));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertNotSame(Interceptions.get(1), Interceptions.get(3));
      assertNotSame(Interceptions.get(2), Interceptions.get(3));
      
      assertNotSame(Interceptions.get(4), Interceptions.get(5));
      assertNotSame(Interceptions.get(4), Interceptions.get(6));
      assertNotSame(Interceptions.get(4), Interceptions.get(7));
      assertNotSame(Interceptions.get(5), Interceptions.get(6));
      assertNotSame(Interceptions.get(5), Interceptions.get(7));
      assertNotSame(Interceptions.get(6), Interceptions.get(7));
   }
   
   public void testSimplePerInstanceInterceptor()
   {
      Interceptions.clear();
      POJOWithInterceptor pojo = new POJOWithInterceptor();
      POJOWithInterceptor pojo2 = new POJOWithInterceptor();
      POJOWithInterceptor pojo3 = new POJOWithInterceptor();
      POJOWithInterceptor pojo4 = new POJOWithInterceptor();

      assertEquals(5, pojo.perInstanceA(5));
      pojo2.perInstanceA(5);
      pojo3.perInstanceA(5);
      pojo4.perInstanceA(5);
 
      pojo.perInstanceB();
      pojo2.perInstanceB();
      pojo3.perInstanceB();
      pojo4.perInstanceB();
      
      
      assertEquals(8, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(4));
      assertEquals(Interceptions.get(1), Interceptions.get(5));
      assertEquals(Interceptions.get(2), Interceptions.get(6));
      assertEquals(Interceptions.get(3), Interceptions.get(7));

      assertNotSame(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(0), Interceptions.get(2));
      assertNotSame(Interceptions.get(0), Interceptions.get(3));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertNotSame(Interceptions.get(1), Interceptions.get(3));
      assertNotSame(Interceptions.get(2), Interceptions.get(3));
      
      assertNotSame(Interceptions.get(4), Interceptions.get(5));
      assertNotSame(Interceptions.get(4), Interceptions.get(6));
      assertNotSame(Interceptions.get(4), Interceptions.get(7));
      assertNotSame(Interceptions.get(5), Interceptions.get(6));
      assertNotSame(Interceptions.get(5), Interceptions.get(7));
      assertNotSame(Interceptions.get(6), Interceptions.get(7));
   }
   
   public void testSimplePerInstanceAspectFactory()
   {
      Interceptions.clear();
      POJOWithAspectFactory pojo = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo2 = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo3 = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo4 = new POJOWithAspectFactory();

      assertEquals(5, pojo.perInstanceA(5));
      pojo2.perInstanceA(5);
      pojo3.perInstanceA(5);
      pojo4.perInstanceA(5);
 
      pojo.perInstanceB();
      pojo2.perInstanceB();
      pojo3.perInstanceB();
      pojo4.perInstanceB();
      
      
      assertEquals(8, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(4));
      assertEquals(Interceptions.get(1), Interceptions.get(5));
      assertEquals(Interceptions.get(2), Interceptions.get(6));
      assertEquals(Interceptions.get(3), Interceptions.get(7));

      assertNotSame(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(0), Interceptions.get(2));
      assertNotSame(Interceptions.get(0), Interceptions.get(3));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertNotSame(Interceptions.get(1), Interceptions.get(3));
      assertNotSame(Interceptions.get(2), Interceptions.get(3));
      
      assertNotSame(Interceptions.get(4), Interceptions.get(5));
      assertNotSame(Interceptions.get(4), Interceptions.get(6));
      assertNotSame(Interceptions.get(4), Interceptions.get(7));
      assertNotSame(Interceptions.get(5), Interceptions.get(6));
      assertNotSame(Interceptions.get(5), Interceptions.get(7));
      assertNotSame(Interceptions.get(6), Interceptions.get(7));
   }
   
   public void testSimplePerInstanceInterceptorFactory()
   {
      Interceptions.clear();
      POJOWithInterceptorFactory pojo = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo2 = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo3 = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo4 = new POJOWithInterceptorFactory();

      assertEquals(5, pojo.perInstanceA(5));
      pojo2.perInstanceA(5);
      pojo3.perInstanceA(5);
      pojo4.perInstanceA(5);
 
      pojo.perInstanceB();
      pojo2.perInstanceB();
      pojo3.perInstanceB();
      pojo4.perInstanceB();
      
      
      
      assertEquals(8, Interceptions.size());
      assertEquals(Interceptions.get(0), Interceptions.get(4));
      assertEquals(Interceptions.get(1), Interceptions.get(5));
      assertEquals(Interceptions.get(2), Interceptions.get(6));
      assertEquals(Interceptions.get(3), Interceptions.get(7));

      assertNotSame(Interceptions.get(0), Interceptions.get(1));
      assertNotSame(Interceptions.get(0), Interceptions.get(2));
      assertNotSame(Interceptions.get(0), Interceptions.get(3));
      assertNotSame(Interceptions.get(1), Interceptions.get(2));
      assertNotSame(Interceptions.get(1), Interceptions.get(3));
      assertNotSame(Interceptions.get(2), Interceptions.get(3));
      
      assertNotSame(Interceptions.get(4), Interceptions.get(5));
      assertNotSame(Interceptions.get(4), Interceptions.get(6));
      assertNotSame(Interceptions.get(4), Interceptions.get(7));
      assertNotSame(Interceptions.get(5), Interceptions.get(6));
      assertNotSame(Interceptions.get(5), Interceptions.get(7));
      assertNotSame(Interceptions.get(6), Interceptions.get(7));
   }
   
   public void testSimplePerJoinpointAspect()
   {
      Interceptions.clear();
      POJOWithAspect pojo = new POJOWithAspect();
      POJO2WithAspect pojo2 = new POJO2WithAspect();
      POJOWithAspect pojo3 = new POJOWithAspect();
      POJO2WithAspect pojo4 = new POJO2WithAspect();

      assertEquals(5, pojo.perJoinpointA(5));
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      pojo.perJoinpointA(5);
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      assertEquals(16, Interceptions.size());

      int[] results = new int[]{8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7};
      
      for (int i = 0 ; i < results.length ; i++)
      {
         for (int j = 0 ; j < results.length ; j++)
         {
            if (i == j || Math.abs(i - j) == results.length/2 )
            {
               assertEquals("interceptions " + i + " and " + j + " should be the same", Interceptions.get(i), Interceptions.get(j));
            }
            else
            {
               assertNotSame("interceptions " + i + " and " + j + " should not be the same", Interceptions.get(i), Interceptions.get(j));
            }
         }
      }
   }
   
   public void testSimplePerJoinpointInterceptor()
   {
      Interceptions.clear();
      POJOWithInterceptor pojo = new POJOWithInterceptor();
      POJOWithInterceptor pojo2 = new POJOWithInterceptor();
      POJOWithInterceptor pojo3 = new POJOWithInterceptor();
      POJOWithInterceptor pojo4 = new POJOWithInterceptor();

      assertEquals(5, pojo.perJoinpointA(5));
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      pojo.perJoinpointA(5);
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      assertEquals(16, Interceptions.size());
      
      int[] results = new int[]{8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7};
      
      for (int i = 0 ; i < results.length ; i++)
      {
         for (int j = 0 ; j < results.length ; j++)
         {
            if (i == j || Math.abs(i - j) == results.length/2 )
            {
               assertEquals("interceptions " + i + " and " + j + " should be the same", Interceptions.get(i), Interceptions.get(j));
            }
            else
            {
               assertNotSame("interceptions " + i + " and " + j + " should not be the same", Interceptions.get(i), Interceptions.get(j));
            }
         }
      }
   }
   
   public void testSimplePerJoinpointAspectFactory()
   {
      Interceptions.clear();
      POJOWithAspectFactory pojo = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo2 = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo3 = new POJOWithAspectFactory();
      POJOWithAspectFactory pojo4 = new POJOWithAspectFactory();

      assertEquals(5, pojo.perJoinpointA(5));
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      pojo.perJoinpointA(5);
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
      
      assertEquals(16, Interceptions.size());
      
      int[] results = new int[]{8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7};
      
      for (int i = 0 ; i < results.length ; i++)
      {
         for (int j = 0 ; j < results.length ; j++)
         {
            if (i == j || Math.abs(i - j) == results.length/2 )
            {
               assertEquals("interceptions " + i + " and " + j + " should be the same", Interceptions.get(i), Interceptions.get(j));
            }
            else
            {
               assertNotSame("interceptions " + i + " and " + j + " should not be the same", Interceptions.get(i), Interceptions.get(j));
            }
         }
      }
   }
   
   public void testSimplePerJoinpointInterceptorFactory()
   {
      Interceptions.clear();
      POJOWithInterceptorFactory pojo = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo2 = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo3 = new POJOWithInterceptorFactory();
      POJOWithInterceptorFactory pojo4 = new POJOWithInterceptorFactory();

      assertEquals(5, pojo.perJoinpointA(5));
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      pojo.perJoinpointA(5);
      pojo2.perJoinpointA(5);
      pojo3.perJoinpointA(5);
      pojo4.perJoinpointA(5);
 
      pojo.perJoinpointB();
      pojo2.perJoinpointB();
      pojo3.perJoinpointB();
      pojo4.perJoinpointB();
 
      
      
      assertEquals(16, Interceptions.size());
      
      int[] results = new int[]{8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7};
      
      for (int i = 0 ; i < results.length ; i++)
      {
         for (int j = 0 ; j < results.length ; j++)
         {
            if (i == j || Math.abs(i - j) == results.length/2 )
            {
               assertEquals("interceptions " + i + " and " + j + " should be the same", Interceptions.get(i), Interceptions.get(j));
            }
            else
            {
               assertNotSame("interceptions " + i + " and " + j + " should not be the same", Interceptions.get(i), Interceptions.get(j));
            }
         }
      }
   }

   
   public void testNullAspectFactory()
   {
      // check that all joinpoints execute without exceptions
      int value = POJOWithNullAspect.staticField;
      POJOWithNullAspect.staticField = value + 1;
      POJOWithNullAspect.staticMethod();
      POJOWithNullAspect.callerMethod2();
      POJOWithNullAspect pojo = new POJOWithNullAspect();
      value += pojo.field;
      pojo.field = value - 10;
      pojo.method();
      pojo.callerMethod1();
      new POJOWithNullAspect(3, 4);
      
      // check that mixed method does get correctly intercepted without errors
      Interceptions.clear();
      pojo.mixedMethod();
      assertEquals(1, Interceptions.size());
   }
   
   final static String[] staticNames = {
         PerJoinpointInterceptor.class.getName(),
         PerVmInterceptor.class.getName(), 
         PerClassAspect.class.getName(),
         PerClassJoinpointInterceptor.class.getName()};
   
   final static String[] nonStaticNames = {
         PerJoinpointInterceptor.class.getName(),
         PerVmInterceptor.class.getName(), 
         PerClassAspect.class.getName(),
         PerClassJoinpointInterceptor.class.getName(),
         PerInstanceAspect.class.getName()};

   final static String[] staticAndConstructionNames = {
         //Added by constuctor
         PerJoinpointInterceptor.class.getName(),
         PerVmInterceptor.class.getName(), 
         PerClassAspect.class.getName(),
         PerClassJoinpointInterceptor.class.getName(),
         //Added by constuction
         PerJoinpointInterceptor.class.getName(),
         PerVmInterceptor.class.getName(), 
         PerClassAspect.class.getName(),
         PerClassJoinpointInterceptor.class.getName(),
         PerInstanceAspect.class.getName()};
   
   public void testPOJOStaticWithMixedChains() throws Exception
   {
      System.out.println("TEST POJO WITH MIXED CHAINS");

      Interceptions.clear();
      assertEquals(5, POJOWithMixedChains.staticMethod(5));
      checkExpectedNames(staticNames);
      ArrayList staticMethod = (ArrayList)Interceptions.interceptions.clone(); 

      Interceptions.clear();
      POJOWithMixedChains.voidStaticMethod();
      checkExpectedNames(staticNames);
      ArrayList staticVoidMethod = (ArrayList)Interceptions.interceptions.clone(); 

      Interceptions.clear();
      POJOWithMixedChains.staticField = 100;
      checkExpectedNames(staticNames);
      ArrayList staticFieldWrite = (ArrayList)Interceptions.interceptions.clone(); 

      Interceptions.clear();
      assertEquals(100, POJOWithMixedChains.staticField);
      checkExpectedNames(staticNames);
      ArrayList staticFieldRead = (ArrayList)Interceptions.interceptions.clone();
      
      checkSimilarities(staticMethod, staticVoidMethod, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
      checkSimilarities(staticMethod, staticFieldWrite, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
      checkSimilarities(staticFieldWrite, staticFieldRead, new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE});
   }
   
   public void testPOJOConstructionWithMixedChains() throws Exception
   {
      Interceptions.clear();
      POJOWithMixedChains pojo = new POJOWithMixedChains();
      checkExpectedNames(staticAndConstructionNames);
      ArrayList ctorA = (ArrayList)Interceptions.interceptions.clone(); 

      Interceptions.clear();
      POJOWithMixedChains pojo2 = new POJOWithMixedChains(100);
      checkExpectedNames(staticAndConstructionNames);
      ArrayList ctorB = (ArrayList)Interceptions.interceptions.clone(); 
      checkSimilarities(ctorA, ctorB, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE});
   }
   
   public void testPOJOMethodsWithMixedChains() throws Exception
   {
      POJOWithMixedChains pojo = new POJOWithMixedChains();
      POJOWithMixedChains pojo2 = new POJOWithMixedChains();
      Interceptions.clear();
      assertEquals(100, pojo.method(100));
      checkExpectedNames(nonStaticNames);
      ArrayList methodInterceptionsA = (ArrayList)Interceptions.interceptions.clone();

      Interceptions.clear();
      assertEquals(100, pojo2.method(100));
      checkExpectedNames(nonStaticNames);
      ArrayList methodInterceptionsB = (ArrayList)Interceptions.interceptions.clone();

      System.out.println("Checking methodA and methodB");
      checkSimilarities(methodInterceptionsA, methodInterceptionsB, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
   }

   public void testPOJOFieldsWithMixedChains() throws Exception
   {
      POJOWithMixedChains pojo = new POJOWithMixedChains();
      POJOWithMixedChains pojo2 = new POJOWithMixedChains();

      Interceptions.clear();
      pojo.field = 100;
      checkExpectedNames(nonStaticNames);
      ArrayList fieldWriteA = (ArrayList)Interceptions.interceptions.clone();

      Interceptions.clear();
      pojo2.field = 50;
      checkExpectedNames(nonStaticNames);
      ArrayList fieldWriteB = (ArrayList)Interceptions.interceptions.clone();
      
      Interceptions.clear();
      assertEquals(100, pojo.field);
      checkExpectedNames(nonStaticNames);
      ArrayList fieldReadA = (ArrayList)Interceptions.interceptions.clone();
      
      Interceptions.clear();
      assertEquals(50, pojo2.field);
      checkExpectedNames(nonStaticNames);
      ArrayList fieldReadB = (ArrayList)Interceptions.interceptions.clone();
      
      checkSimilarities(fieldWriteA, fieldWriteB, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
      checkSimilarities(fieldReadA, fieldReadB, new Boolean[] {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
      checkSimilarities(fieldReadA, fieldWriteA, new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE});
      checkSimilarities(fieldReadB, fieldWriteB, new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE});
   }
   
   public void testCallerFromCtorWithMixedChains() throws Exception
   {
      Interceptions.clear();
      CallingPOJO pojo = new CallingPOJO();
      CallingPOJO pojo2 = new CallingPOJO();

      String[] expectedNames = new String[20];
      
      for (int i = 0 ; i < 20 ; i += 5)
      {
         System.arraycopy(nonStaticNames, 0, expectedNames, i, nonStaticNames.length);
      }
      checkExpectedNames(expectedNames);
      assertNotSame(Interceptions.get(0), Interceptions.get(5));
      assertSame(Interceptions.get(1), Interceptions.get(6));
      assertSame(Interceptions.get(2), Interceptions.get(7));
      assertNotSame(Interceptions.get(3), Interceptions.get(8));
      assertSame(Interceptions.get(4), Interceptions.get(9));
      
      
      for (int i = 0 ; i < Interceptions.size() ; i ++)
      {
         System.out.println(Interceptions.get(i));
      }

      Interceptions.clear();
      pojo = new CallingPOJO();
      ArrayList ctorA = (ArrayList)Interceptions.interceptions.clone();

      Interceptions.clear();
      pojo2 = new CallingPOJO();
      ArrayList ctorB = (ArrayList)Interceptions.interceptions.clone();
      
      assertNotSame(Interceptions.get(0), Interceptions.get(5));
      assertSame(Interceptions.get(1), Interceptions.get(6));
      assertSame(Interceptions.get(2), Interceptions.get(7));
      assertNotSame(Interceptions.get(3), Interceptions.get(8));
      assertSame(Interceptions.get(4), Interceptions.get(9));
      
      checkSimilarities(ctorA, ctorB, new Boolean[] {
            Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
            Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE});
   }
   
   public void testCallerFromStaticMethodWithMixedChains() throws Exception
   {
      Interceptions.clear();
      CallingPOJO.callFromStatic();
      CallingPOJO.callFromStatic();

      String[] expectedNames = new String[16];
      
      for (int i = 0 ; i < 16 ; i += 4)
      {
         System.arraycopy(staticNames, 0, expectedNames, i, staticNames.length);
      }
      checkExpectedNames(expectedNames);
      assertNotSame(Interceptions.get(0), Interceptions.get(4));
      assertSame(Interceptions.get(1), Interceptions.get(5));
      assertSame(Interceptions.get(2), Interceptions.get(6));
      assertNotSame(Interceptions.get(3), Interceptions.get(7));
      
      
      
      for (int i = 0 ; i < Interceptions.size() ; i ++)
      {
         System.out.println(Interceptions.get(i));
      }

      Interceptions.clear();
      CallingPOJO.callFromStatic();
      ArrayList staticA = (ArrayList)Interceptions.interceptions.clone();

      Interceptions.clear();
      CallingPOJO.callFromStatic();
      ArrayList staticB = (ArrayList)Interceptions.interceptions.clone();
      
      assertNotSame(Interceptions.get(0), Interceptions.get(4));
      assertSame(Interceptions.get(1), Interceptions.get(5));
      assertSame(Interceptions.get(2), Interceptions.get(6));
      assertNotSame(Interceptions.get(3), Interceptions.get(7));
      
      checkSimilarities(staticA, staticB, new Boolean[] {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE});
   }
   
   public void testCallerFromMethodWithMixedChains() throws Exception
   {
      CallingPOJO pojo = new CallingPOJO();
      CallingPOJO pojo2 = new CallingPOJO();
      Interceptions.clear();
      
      pojo.call();
      pojo2.call();

      String[] expectedNames = new String[20];
      
      for (int i = 0 ; i < 20 ; i += 5)
      {
         System.arraycopy(nonStaticNames, 0, expectedNames, i, nonStaticNames.length);
      }
      checkExpectedNames(expectedNames);

      //All PER_JOINPOINT should be different
      assertNotSame(Interceptions.get(0), Interceptions.get(5));
      assertNotSame(Interceptions.get(0), Interceptions.get(10));
      assertNotSame(Interceptions.get(0), Interceptions.get(15));
      assertNotSame(Interceptions.get(5), Interceptions.get(10));
      assertNotSame(Interceptions.get(5), Interceptions.get(15));
      assertNotSame(Interceptions.get(10), Interceptions.get(15));
      
      //PER_CLASS and PER_JOINPOINT should be the same
      if (Interceptions.get(1) != Interceptions.get(6) || Interceptions.get(1) != Interceptions.get(11) || Interceptions.get(1)!= Interceptions.get(16))
         throw new Exception("PER_CLASS scoped should have been the same");
      if (Interceptions.get(2) != Interceptions.get(7) || Interceptions.get(2) != Interceptions.get(12) || Interceptions.get(2)!= Interceptions.get(17))
         throw new Exception("PER_CLASS scoped should have been the same");
      
      //PER_CLASS_JOINPOINT
      assertSame(Interceptions.get(3), Interceptions.get(13));
      assertSame(Interceptions.get(8), Interceptions.get(18));
      assertNotSame(Interceptions.get(3), Interceptions.get(8));
      
      //PER_INSTANCE
      assertSame(Interceptions.get(4), Interceptions.get(9));
      assertSame(Interceptions.get(14), Interceptions.get(19));
      assertNotSame(Interceptions.get(4), Interceptions.get(14));
   }

   public void testDynamicInvoke() throws Throwable
   {
      POJOWithMixedChains pojo = new POJOWithMixedChains();
      
      Interceptions.clear();
      pojo.dynamicMethod();
      ArrayList methodA = (ArrayList)Interceptions.interceptions.clone();

      Interceptions.clear();
      //Being very lazy. All dynamicInvoke seems to need is a method hash and the args
      //If this changes in the future we may need to change this test
      Method method = pojo.getClass().getMethod("dynamicMethod", new Class[0]);
      long hash = MethodHashing.calculateHash(method);
      MethodInvocation invocation = new MethodInvocation(null, hash, null, null, null);
      invocation.setArguments(new Object[0]);

      ((Advised)pojo)._getAdvisor().dynamicInvoke(pojo, invocation);
      
      ArrayList methodB = (ArrayList)Interceptions.interceptions.clone();
      
      checkSimilarities(methodA, methodB);
   }
   
   public void testCopy() throws Exception
   {
      System.out.println("TEST COPY");
      Interceptions.clear();
      assertEquals(5, CopyPOJOWithMixedChains.staticMethod(5));
      assertEquals(8, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
      
      Interceptions.clear();
      CopyPOJOWithMixedChains.staticField = 17;
      assertEquals(8, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
      
      Interceptions.clear();
      assertEquals(17, CopyPOJOWithMixedChains.staticField);
      assertEquals(8, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
      
      Interceptions.clear();
      CopyPOJOWithMixedChains pojo = new CopyPOJOWithMixedChains(5);
      assertEquals(8, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
      
      Interceptions.clear();
      pojo.field = 99;
      assertEquals(10, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
      
      Interceptions.clear();
      assertEquals(99, pojo.field);
      assertEquals(10, Interceptions.size());
      checkSimilaritiesInTowHalves(Interceptions.interceptions);
   }
   
   private void checkSimilaritiesInTowHalves(ArrayList all)
   {
      ArrayList listA = new ArrayList();
      ArrayList listB = new ArrayList();
      
      for (int i = 0 ; i < all.size(); i++)
      {
         if (i < all.size()/2)
         {
            listA.add(all.get(i));
         }
         else
         {
            listB.add(all.get(i));
         }
      }
      
      checkSimilarities(listA, listB);
   }
   
   private void checkSimilarities(ArrayList listA, ArrayList listB)
   {
      Boolean[] same = new Boolean[listA.size()];
      for (int i = 0 ; i < listA.size() ; i++)
      {
         same[i] = Boolean.TRUE;
      }
      checkSimilarities(listA, listB, same);  
   }
   
   private void checkSimilarities(ArrayList listA, ArrayList listB, Boolean[] same)
   {
      assertEquals(listA.size(), listB.size());
      assertEquals(listA.size(), same.length);
      for (int i = 0 ; i < listA.size() ; i++)
      {
         if (same[i].booleanValue())
         {
            assertEquals(Integer.toString(i), listA.get(i), listB.get(i));
         }
         else
         {
            assertNotSame(Integer.toString(i), listA.get(i), listB.get(i));
         }
      }
      
   }
   
   private void checkExpectedNames(String[] expected)
   {
      assertEquals(expected.length, Interceptions.size());
      for (int i = 0 ; i < expected.length ; i++)
      {
         assertEquals(Integer.toString(i), expected[i], Interceptions.get(i).getClass().getName());
      }
   }
}
