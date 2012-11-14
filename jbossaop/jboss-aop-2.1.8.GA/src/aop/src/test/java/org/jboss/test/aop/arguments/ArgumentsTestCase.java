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
package org.jboss.test.aop.arguments;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of get/setArguments invocation methods.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ArgumentsTestCase extends AOPTestWithSetup
{
   private ArgumentsPOJO1 pojo1;
   private ArgumentsPOJO2 pojo2;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ArgumentsTestCase");
      suite.addTestSuite(ArgumentsTestCase.class);
      return suite;
   }

   public ArgumentsTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      pojo1 = new ArgumentsPOJO1();
      pojo2 = new ArgumentsPOJO2();
      ArgumentsAspect.clear();
   }
   
   public void testConstructorInvocation1()
   {
      ArgumentsPOJO1 pojo = new ArgumentsPOJO1(5);
      
      assertEquals(2, pojo.arg);
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(2, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testConstructorInvocation2()
   {
      ArgumentsPOJO2 pojo = new ArgumentsPOJO2(23);
      
      assertEquals(37, pojo.arg);
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(23, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testMethod1()
   {
      assertEquals(0, pojo1.method(1));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(0, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testMethod2()
   {
      assertEquals(111, pojo2.method(1100));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(1100, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testStaticMethod1()
   {
      assertEquals(4193, pojo1.staticMethod(8347));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(4173, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testStaticMethod2()
   {
      assertEquals(57, pojo2.staticMethod(148));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(148, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testConCreatePOJO1()
   {
      ArgumentsPOJO1 pojo = new ArgumentsPOJO1(Call.CONSTRUCTOR, 2007);
      assertEquals(1003, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(1003, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testConCreatePOJO2()
   {
      ArgumentsPOJO2 pojo = new ArgumentsPOJO2(Call.CONSTRUCTOR, 2037);
      assertEquals(37, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(2037, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testMethodCreatePOJO1()
   {
      // intercepted twice by each advice: once for caller, once for called
      CalledPOJO pojo = pojo1.createPOJO(16);
      assertEquals(4, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(4, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testMethodCreatePOJO2()
   {
      // intercepted twice by each advice: once for caller, once for called
      CalledPOJO pojo = pojo2.createPOJO(1791);
      assertEquals(37, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testConCallPOJO1()
   {
      ArgumentsPOJO1 pojo = new ArgumentsPOJO1(Call.METHOD, 30);
      assertEquals(105, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(15, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testConCallPOJO2()
   {
      ArgumentsPOJO2 pojo = new ArgumentsPOJO2(Call.METHOD, 56);
      assertEquals(259, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(56, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testMethodCallPOJO1()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(665, pojo1.callPOJO(382));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(95, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testMethodCallPOJO2()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(259, pojo2.callPOJO(1203));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testConCallStaticPOJO1()
   {
      ArgumentsPOJO1 pojo = new ArgumentsPOJO1(Call.STATIC_METHOD, -2041);
      assertEquals(17, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(-2041, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testConCallStaticPOJO2()
   {
      ArgumentsPOJO2 pojo = new ArgumentsPOJO2(Call.STATIC_METHOD, -8);
      assertEquals(17, pojo.arg);
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(-8, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(18, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testMethodCallStaticPOJO1()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(17, pojo1.callPOJOStatic(123456));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(61728, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
   }
   
   public void testMethodCallStaticPOJO2()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(17, pojo2.callPOJOStatic(99999));
      
      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(37, ((Integer) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(18, ((Integer) ArgumentsAspect.arguments2[0]).intValue());
   }
   
   public void testNoPrecedenceConCallStaticPOJO1()
   {
      ArgumentsPOJO1 pojo = new ArgumentsPOJO1(Call.STATIC_METHOD_2, -2041);
      assertEquals(36, pojo.arg);

      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(-1020, ((Long) ArgumentsAspect.arguments1[0]).intValue());
   }

   public void testNoPrecedenceConCallStaticPOJO2()
   {
      ArgumentsPOJO2 pojo = new ArgumentsPOJO2(Call.STATIC_METHOD_2, -8);
      assertEquals(17, pojo.arg);

      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(-8, ((Long) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(18, ((Long) ArgumentsAspect.arguments2[0]).intValue());
   }

   public void testNoPrecedenceMethodCallStaticPOJO1()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(36, pojo1.callPOJOStatic((long) 123456));

      assertNotNull(ArgumentsAspect.arguments1);
      assertSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(30864, ((Long) ArgumentsAspect.arguments1[0]).intValue());
   }

   public void testNoPrecedenceMethodCallStaticPOJO2()
   {
      // intercepted twice by each advice: once for caller, once for called
      assertEquals(17, pojo2.callPOJOStatic((new Long(99999)).longValue()) );

      assertNotNull(ArgumentsAspect.arguments1);
      assertNotSame(ArgumentsAspect.arguments1, ArgumentsAspect.arguments2);
      assertEquals(1, ArgumentsAspect.arguments1.length);
      assertEquals(37, ((Long) ArgumentsAspect.arguments1[0]).intValue());
      assertEquals(1, ArgumentsAspect.arguments2.length);
      assertEquals(18, ((Long) ArgumentsAspect.arguments2[0]).intValue());
   }
}