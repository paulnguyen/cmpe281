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
 * Tests the use of @Target parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings("unused")
public class TargetTestCase extends AOPTestWithSetup
{
   private TargetCallerPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("TargetTestCase");
      suite.addTestSuite(TargetTestCase.class);
      return suite;
   }
   
   public TargetTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      TargetAspect.clear();
      this.pojo = new TargetCallerPOJO();
   }

   public void test1()
   {
      new TargetCallerPOJO(1);
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test2()
   {
      boolean thrown = false;
      try
      {
         new TargetCallerPOJO(1, "test2");
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(null, thrown);
   }

   public void test3()
   {
      pojo.field1 = 0;
      assertAllAdvices(false);
      assertTarget(pojo, false);
   }
   
   public void test4()
   {
      int test = pojo.field1;
      assertAllAdvices(false);
      assertTarget(pojo, false);
   }
   
   public void test5()
   {
      TargetCallerPOJO.field2 = 5;
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test6()
   {
      int test = TargetCallerPOJO.field2;
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test7()
   {
      pojo.method1();
      assertAllAdvices(false);
      assertTarget(pojo, false);
   }
   
   public void test8()
   {
      TargetCallerPOJO.method2();
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test9()
   {
      boolean thrown = false;
      try
      {
         pojo.method3();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(pojo, thrown);
   }
   
   public void test10()
   {
      boolean thrown = false;
      try
      {
         TargetCallerPOJO.method4();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(null, thrown);
   }
   
   public void test11()
   {
      pojo.method5();
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test12()
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
      assertAllAdvices(thrown);
      assertTarget(null, thrown);
   }
   
   public void test13()
   {
      pojo.method7();
      assertAllAdvices(false);
      assertTarget(false);
   }
   
   public void test14()
   {
      pojo.method8();
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test15()
   {
      boolean thrown = false;
      try
      {
         pojo.method9();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(thrown);
   }
   
   public void test16()
   {
      boolean thrown = false;
      try
      {
         pojo.method10();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(null, thrown);
   }
   
   public void test17()
   {
      TargetCallerPOJO.method11();
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test18()
   {
      TargetCallerPOJO.method12();
      assertAllAdvices(false);
      assertTarget(false);
   }
   
   public void test19()
   {
      TargetCallerPOJO.method13();
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test20()
   {
      boolean thrown = false;
      try
      {
         TargetCallerPOJO.method14();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(thrown);
   }
   
   public void test21()
   {
      boolean thrown = false;
      try
      {
         TargetCallerPOJO.method15();
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(thrown);
      assertTarget(null, thrown);
   }
   
   public void test22() throws POJOException
   {
      new TargetCallerPOJO(CallType.CONSTRUCTOR, false);
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test23()
   {
      boolean thrown = false;
      try
      {
         new TargetCallerPOJO(CallType.CONSTRUCTOR, true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(true);
      assertTarget(null, true);
   }
   
   public void test24() throws POJOException
   {
      new TargetCallerPOJO(CallType.METHOD, false);
      assertAllAdvices(false);
      assertTarget(false);
   }
   
   public void test25()
   {
      boolean thrown = false;
      try
      {
         new TargetCallerPOJO(CallType.METHOD, true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(true);
      assertTarget(true);
   }
   
   public void test26() throws POJOException
   {
      new TargetCallerPOJO(CallType.STATIC_METHOD, false);
      assertAllAdvices(false);
      assertTarget(null, false);
   }
   
   public void test27()
   {
      boolean thrown = false;
      try
      {
         new TargetCallerPOJO(CallType.STATIC_METHOD, true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertAllAdvices(true);
      assertTarget(null, true);
   }
   
   private void assertAllAdvices(boolean error)
   {
      assertTrue(TargetAspect.before1);
      assertTrue(TargetAspect.before2);
      assertTrue(TargetAspect.around1);
      assertTrue(TargetAspect.around2);
      assertEquals(!error, TargetAspect.after1);
      assertEquals(!error, TargetAspect.after2);
      assertEquals(error, TargetAspect.throwing1);
      assertEquals(error, TargetAspect.throwing2);
      assertTrue(TargetAspect.finally1);
      assertTrue(TargetAspect.finally2);
   }
   
   private void assertTarget(boolean error)
   {
      assertNotNull(TargetAspect.before2Target);
      assertSameTarget(error);
   }
   
   private void assertTarget(Object target, boolean error)
   {
      assertSame(target, TargetAspect.before2Target);
      assertSameTarget(error);
   }
   
   private void assertSameTarget(boolean error)
   {
      assertSame(TargetAspect.before2Target, TargetAspect.around2Target);
      if (error)
      {
         assertSame(TargetAspect.around2Target, TargetAspect.throwing1Target);
         assertNull(TargetAspect.after2Target);
      }
      else
      {
         assertSame(TargetAspect.around2Target, TargetAspect.after2Target);
         assertNull(TargetAspect.throwing1Target);
      }
      assertSame(TargetAspect.around2Target, TargetAspect.finally2Target);
   }
}