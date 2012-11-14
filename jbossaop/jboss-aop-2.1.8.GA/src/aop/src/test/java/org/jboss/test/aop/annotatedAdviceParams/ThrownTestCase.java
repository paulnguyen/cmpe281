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

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests parameter annotation @Thrown. 
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 *
 */
public class ThrownTestCase extends AOPTestWithSetup
{
   // as a convention, the number of the thrown exception should never be 0, so we
   // can identify when the number hasn't been set in ThrownAspect, and when it has
   
   private ThrownPOJO pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ThrownTestCase");
      suite.addTestSuite(ThrownTestCase.class);
      return suite;
   }

   public ThrownTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      ThrownAspect.clear();
      this.pojo = new ThrownPOJO();
   }

   public void test1()
   {
      boolean thrown = false;
      try
      {
         pojo.method1(11);
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(12, pojoException.number);
         
         assertNull(ThrownAspect.throwingAdvice);
         assertNull(ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertEquals("finally1", ThrownAspect.finallyAdvice);
         assertSame(pojoException, ThrownAspect.finallyThrown);
         assertEquals(11, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }

   public void test2()
   {
      boolean thrown = false;
      try
      {
         pojo.method2(23);
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(24, pojoException.number);
         
         assertEquals("throwing2", ThrownAspect.throwingAdvice);
         assertSame(pojoException, ThrownAspect.throwingThrown);
         assertEquals(23, ThrownAspect.throwingNumber);
         
         assertEquals("finally2", ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test3()
   {
      boolean thrown = false;
      try
      {
         pojo.method3(37);
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(37, pojoException.number);
         
         assertEquals("throwing3", ThrownAspect.throwingAdvice);
         assertSame(pojoException, ThrownAspect.throwingThrown);
         assertEquals(37, ThrownAspect.throwingNumber);
         
         assertNull(ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test4()
   {
      boolean thrown = false;
      try
      {
         pojo.method4(43);
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(43, pojoException.number);
         
         assertEquals("throwing4", ThrownAspect.throwingAdvice);
         assertSame(pojoException, ThrownAspect.throwingThrown);
         assertEquals(43, ThrownAspect.throwingNumber);
      }
      assertTrue(thrown);
   }
   
   public void test5()
   {
      boolean thrown = false;
      try
      {
         pojo.method5(59);
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(59, pojoException.number);
         
         assertNull(ThrownAspect.throwingAdvice);
         assertNull(ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertEquals("finally5", ThrownAspect.finallyAdvice);
         assertSame(pojoException, ThrownAspect.finallyThrown);
         assertEquals(59, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test6()
   {
      boolean thrown = false;
      try
      {
         pojo.method6();
      }
      catch(POJOException pojoException)
      {
         thrown = true;
      }
      assertFalse(thrown);
      
      assertNull(ThrownAspect.throwingAdvice);
      assertNull(ThrownAspect.throwingThrown);
      assertEquals(0, ThrownAspect.throwingNumber);
      
      assertEquals("finally6", ThrownAspect.finallyAdvice);
      assertNull(ThrownAspect.finallyThrown);
      assertEquals(0, ThrownAspect.finallyNumber);
   }
   
   public void test7()
   {
      boolean thrown = false;
      try
      {
         pojo.method7();
      }
      catch(POJOException pojoException)
      {
         thrown = true;
         assertEquals(6, pojoException.number);
         assertNull(ThrownAspect.throwingAdvice);
         assertNull(ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertNull(ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test8() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method8();
      }
      catch(RuntimeException exception)
      {
         thrown = true;
         assertEquals("throwing10", ThrownAspect.throwingAdvice);
         assertSame(exception, ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertNull(ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test9() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method9();
      }
      catch(RuntimeException exception)
      {
         thrown = true;
         assertEquals("throwing6", ThrownAspect.throwingAdvice);
         assertSame(exception, ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertNull(ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
   
   public void test10() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method10();
      }
      catch(RuntimeException exception)
      {
         thrown = true;
         assertEquals("throwing11", ThrownAspect.throwingAdvice);
         assertSame(exception, ThrownAspect.throwingThrown);
         assertEquals(0, ThrownAspect.throwingNumber);
         
         assertNull(ThrownAspect.finallyAdvice);
         assertNull(ThrownAspect.finallyThrown);
         assertEquals(0, ThrownAspect.finallyNumber);
      }
      assertTrue(thrown);
   }
}