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
 * Tests both the use of @Return parameters, and the use of return values in advices.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ReturnTestCase extends AOPTestWithSetup
{
   private ReturnPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ReturnTestCase");
      suite.addTestSuite(ReturnTestCase.class);
      return suite;
   }
   
   public ReturnTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      ReturnAspect.clear();
      this.pojo = new ReturnPOJO();
   }
   
   public void test1()
   {
      pojo.method1();
      
      assertNull(ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after1", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally1", ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test2()
   {
      assertEquals("method2", pojo.method2());
      
      assertNull(ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after2", ReturnAspect.afterAdvice);
      assertEquals("method2", ReturnAspect.afterReturn);
      assertNull(ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test3()
   {
      assertEquals("finally3", pojo.method3());
      
      assertEquals("around3", ReturnAspect.aroundAdvice);
      assertEquals("method3", ReturnAspect.aroundReturn);
      assertEquals("after3", ReturnAspect.afterAdvice);
      assertEquals("around3", ReturnAspect.afterReturn);
      assertEquals("finally3", ReturnAspect.finallyAdvice);
      assertEquals("after3", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test4()
   {
      assertEquals("finally4", pojo.method4());
      
      assertEquals("around4", ReturnAspect.aroundAdvice);
      assertEquals("method4", ReturnAspect.aroundReturn);
      assertEquals("after4", ReturnAspect.afterAdvice);
      assertEquals("around4", ReturnAspect.afterReturn);
      assertEquals("finally4", ReturnAspect.finallyAdvice);
      assertEquals("after4", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test5()
   {
      assertEquals("after5", pojo.method5());
      
      assertEquals("around5", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after5", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertNull(ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test6()
   {
      assertEquals("finally6", pojo.method6());
      
      assertNull(ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after6", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally6", ReturnAspect.finallyAdvice);
      assertEquals("method6", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test7()
   {
      assertEquals("after7", pojo.method7());
      
      assertEquals("around7", ReturnAspect.aroundAdvice);
      assertEquals("method7", ReturnAspect.aroundReturn);
      assertEquals("after7", ReturnAspect.afterAdvice);
      assertEquals("around7", ReturnAspect.afterReturn);
      assertEquals("finally7", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals("after7", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test8()
   {
      SubValue value = pojo.method8();
      assertNotNull(value);
      assertEquals(8000, value.i);
      
      assertEquals("around8", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after8", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally8", ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test10()
   {
      SuperValue value = pojo.method10();
      assertNotNull(value);
      assertEquals(10000, value.i);
      
      assertEquals("around10", ReturnAspect.aroundAdvice);
      assertNotNull(ReturnAspect.aroundReturn);
      assertEquals(10, ((SuperValue) ReturnAspect.aroundReturn).i);
      assertNull(ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally10", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals(100, ((SuperValue) ReturnAspect.finallyReturn).i);
      assertNull(ReturnAspect.finallyThrown);
   }

   public void test11()
   {
      SuperValue value = pojo.method11();
      assertNotNull(value);
      assertEquals(11000, value.i);
      
      assertEquals("around11", ReturnAspect.aroundAdvice);
      assertNotNull(ReturnAspect.aroundReturn);
      assertEquals(11, ((SuperValue) ReturnAspect.aroundReturn).i);
      assertEquals("after11", ReturnAspect.afterAdvice);
      assertNotNull(ReturnAspect.afterReturn);
      assertEquals(110, ((SuperValue) ReturnAspect.afterReturn).i);
      assertEquals("finally11", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals(1100, ((SuperValue) ReturnAspect.finallyReturn).i);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test12()
   {
      Throwable thrown = null;
      try
      {
         pojo.method12();
      }
      catch(POJOException e)
      {
         thrown = e;
      }
      assertNotNull(thrown);
      
      assertEquals("around12", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertNull(ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally12", ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertSame(thrown, ReturnAspect.finallyThrown);
   }
   
   public void test13()
   {
      Throwable thrown = null;
      try
      {
         pojo.method13();
      }
      catch(POJOException e)
      {
         thrown = e;
      }
      assertNotNull(thrown);
      
      assertEquals("around12", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertNull(ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally13", ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertSame(thrown, ReturnAspect.finallyThrown);
   }
   
   public void test14()
   {
      assertEquals("field1", pojo.field1);
      
      assertNull(ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after2", ReturnAspect.afterAdvice);
      assertEquals("field1", ReturnAspect.afterReturn);
      assertNull(ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test15()
   {
      assertEquals("finally3", pojo.field2);
      
      assertEquals("around3", ReturnAspect.aroundAdvice);
      assertEquals("field2", ReturnAspect.aroundReturn);
      assertEquals("after3", ReturnAspect.afterAdvice);
      assertEquals("around3", ReturnAspect.afterReturn);
      assertEquals("finally3", ReturnAspect.finallyAdvice);
      assertEquals("after3", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test16()
   {
      assertEquals("finally4", pojo.field3);
      
      assertEquals("around4", ReturnAspect.aroundAdvice);
      assertEquals("field3", ReturnAspect.aroundReturn);
      assertEquals("after4", ReturnAspect.afterAdvice);
      assertEquals("around4", ReturnAspect.afterReturn);
      assertEquals("finally4", ReturnAspect.finallyAdvice);
      assertEquals("after4", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test17()
   {
      assertEquals("after5", pojo.field4);
      
      assertEquals("around5", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after5", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertNull(ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test18()
   {
      assertEquals("finally6", pojo.field5);
      
      assertNull(ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after6", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally6", ReturnAspect.finallyAdvice);
      assertEquals("field5", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test19()
   {
      assertEquals("after7", pojo.field6);
      
      assertEquals("around7", ReturnAspect.aroundAdvice);
      assertEquals("field6", ReturnAspect.aroundReturn);
      assertEquals("after7", ReturnAspect.afterAdvice);
      assertEquals("around7", ReturnAspect.afterReturn);
      assertEquals("finally7", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals("after7", ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test20()
   {
      SubValue value = pojo.field7;
      assertNotNull(value);
      assertEquals(8000, value.i);
      
      assertEquals("around8", ReturnAspect.aroundAdvice);
      assertNull(ReturnAspect.aroundReturn);
      assertEquals("after8", ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally8", ReturnAspect.finallyAdvice);
      assertNull(ReturnAspect.finallyReturn);
      assertNull(ReturnAspect.finallyThrown);
   }
   
   public void test21()
   {
      SuperValue value = pojo.field9;
      assertNotNull(value);
      assertEquals(10000, value.i);
      
      assertEquals("around10", ReturnAspect.aroundAdvice);
      assertNotNull(ReturnAspect.aroundReturn);
      assertEquals(9, ((SuperValue) ReturnAspect.aroundReturn).i);
      assertNull(ReturnAspect.afterAdvice);
      assertNull(ReturnAspect.afterReturn);
      assertEquals("finally10", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals(100, ((SuperValue) ReturnAspect.finallyReturn).i);
      assertNull(ReturnAspect.finallyThrown);
   }

   public void test22()
   {
      SuperValue value = pojo.field10;
      assertNotNull(value);
      assertEquals(11000, value.i);
      
      assertEquals("around11", ReturnAspect.aroundAdvice);
      assertNotNull(ReturnAspect.aroundReturn);
      assertEquals(10, ((SuperValue) ReturnAspect.aroundReturn).i);
      assertEquals("after11", ReturnAspect.afterAdvice);
      assertNotNull(ReturnAspect.afterReturn);
      assertEquals(110, ((SuperValue) ReturnAspect.afterReturn).i);
      assertEquals("finally11", ReturnAspect.finallyAdvice);
      assertNotNull(ReturnAspect.finallyReturn);
      assertEquals(1100, ((SuperValue) ReturnAspect.finallyReturn).i);
      assertNull(ReturnAspect.finallyThrown);
   }
}