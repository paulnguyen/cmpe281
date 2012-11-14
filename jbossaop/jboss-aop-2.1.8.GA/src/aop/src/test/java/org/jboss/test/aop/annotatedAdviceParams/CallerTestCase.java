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
 * Tests the use of @Caller parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class CallerTestCase extends AOPTestWithSetup
{
   private TargetCallerPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("CallerTestCase");
      suite.addTestSuite(CallerTestCase.class);
      return suite;
   }
   
   public CallerTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      CallerAspect.clear();
      this.pojo = new TargetCallerPOJO();
   }
   
   public void test1() throws Exception
   {
      pojo.method5();
      assertAllAdvices(pojo, false);
   }
   
   public void test2() throws Exception
   {
      boolean thrown = false;
      try
      {
         pojo.method6();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(pojo, thrown);
   }
   
   public void test3() throws Exception
   {
      pojo.method7();
      assertAllAdvices(pojo,false);
   }
   
   public void test4() throws Exception
   {
      pojo.method8();
      assertAllAdvices(pojo, false);
   }
   
   public void test5() throws Exception
   {
      boolean thrown = false;
      try
      {
         pojo.method9();
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(pojo, thrown);
   }
   
   public void test6() throws Exception
   {
      boolean thrown = false;
      try
      {
         pojo.method10();
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(pojo, thrown);
   }
   
   public void test7() throws Exception
   {
      TargetCallerPOJO.method11();
      assertAllAdvices(null, false);
   }
   
   public void test8() throws Exception
   {
      TargetCallerPOJO.method12();
      assertAllAdvices(null, false);
   }
   
   public void test9() throws Exception
   {
      TargetCallerPOJO.method13();
      assertAllAdvices(null, false);
   }
   
   public void test10() throws Exception
   {
      boolean thrown = false;
      try
      {
         TargetCallerPOJO.method14();
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(null, thrown);
   }
   
   public void test11() throws Exception
   {
      boolean thrown = false;
      try
      {
         TargetCallerPOJO.method15();
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(null, thrown);
   }
   
   public void test12() throws POJOException
   {
      TargetCallerPOJO caller = new TargetCallerPOJO(CallType.CONSTRUCTOR, false);
      assertAllAdvices(caller, false);
   }
   
   public void test13() throws POJOException
   {
      TargetCallerPOJO caller = new TargetCallerPOJO(CallType.METHOD, false);
      assertAllAdvices(caller, false);
   }
   
   public void test14() throws POJOException
   {
      TargetCallerPOJO caller = new TargetCallerPOJO(CallType.STATIC_METHOD, false);
      assertAllAdvices(caller, false);
   }
   
   public void test15()
   {
      testConCallerWithException(CallType.CONSTRUCTOR);
   }
   
   public void test16()
   {
      testConCallerWithException(CallType.METHOD);
   }
   
   public void test17()
   {
      testConCallerWithException(CallType.STATIC_METHOD);
   }
   
   public void test18()
   {
      // no call pointcut
      pojo.method1();
      
      assertTrue(CallerAspect.before1);
      assertFalse(CallerAspect.before2);
      assertFalse(CallerAspect.before3);
      assertTrue(CallerAspect.around1);
      assertFalse(CallerAspect.around2);
      assertFalse(CallerAspect.around4);
      assertTrue(CallerAspect.after1);
      assertFalse(CallerAspect.after2);
      assertFalse(CallerAspect.throwing1);
      assertFalse(CallerAspect.throwing3);
      assertFalse(CallerAspect.finally1);
      assertTrue(CallerAspect.finally2);
      assertFalse(CallerAspect.finally4);
   }
   
   public void test19()
   {
      // no call pointcut
      boolean thrown = false;
      try
      {
         pojo.method3();
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      assertTrue(CallerAspect.before1);
      assertFalse(CallerAspect.before2);
      assertFalse(CallerAspect.before3);
      assertTrue(CallerAspect.around1);
      assertFalse(CallerAspect.around2);
      assertFalse(CallerAspect.around4);
      assertFalse(CallerAspect.after1);
      assertFalse(CallerAspect.after2);
      assertTrue(CallerAspect.throwing1);
      assertFalse(CallerAspect.throwing3);
      assertFalse(CallerAspect.finally1);
      assertTrue(CallerAspect.finally2);
      assertFalse(CallerAspect.finally4);
   }
   
   public void assertAllAdvices(Object caller, boolean error)
   {
      assertAllAdvices(error);
      assertSame(caller, CallerAspect.before2Caller);
   }
   
   public void assertAllAdvicesNotNull(boolean error)
   {
      assertAllAdvices(error);
      assertNotNull(CallerAspect.before2Caller);
   }
   
   public void assertAllAdvices(boolean error)
   {
      assertTrue(CallerAspect.before1);
      assertTrue(CallerAspect.before2);
      assertTrue(CallerAspect.before3);
      assertTrue(CallerAspect.around1);
      assertTrue(CallerAspect.around2);
      assertTrue(CallerAspect.around4);
      assertEquals(!error, CallerAspect.after1);
      assertEquals(!error, CallerAspect.after2);
      assertEquals(error, CallerAspect.throwing1);
      assertEquals(error, CallerAspect.throwing3);
      assertTrue(CallerAspect.finally1);
      assertTrue(CallerAspect.finally2);
      assertTrue(CallerAspect.finally4);
      
      assertSame(CallerAspect.before2Caller, CallerAspect.before3Caller);
      assertSame(CallerAspect.before3Caller, CallerAspect.around2Caller);
      assertSame(CallerAspect.around2Caller, CallerAspect.around4Caller);
      if (error)
      {
         assertSame(CallerAspect.around4Caller, CallerAspect.throwing3Caller);
         assertNull(CallerAspect.after2Caller);
      }
      else
      {
         assertSame(CallerAspect.around4Caller, CallerAspect.after2Caller);
         assertNull(CallerAspect.throwing3Caller);
      }
      assertSame(CallerAspect.around4Caller, CallerAspect.finally1Caller);
      assertSame(CallerAspect.finally1Caller, CallerAspect.finally4Caller);
   }
   
   private void testConCallerWithException(CallType callType)
   {
      boolean thrown = false;
      try
      {
         new TargetCallerPOJO(callType, true);
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
   }
}