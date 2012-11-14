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
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of @Arg parameters (this class complements <code>
 * org.jboss.test.aop.args.ArgTestCase</code>, by testing advices that are allowed
 * only with generated advisors).
 * 
 * @Args and <code>invocation.setArguments()</code> are used only as a complement, so
 * we can test @Arg functionality combined with access of arguments array.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see ArgsTestCase
 * @see org.jboss.test.aop.annotatedAdviceParams.Arg2TestCase
 * @see org.jboss.test.aop.arguments.ArgumentsTestCase
 */
@SuppressWarnings("cast")
public class ArgTestCase extends AOPTestWithSetup
{
   private ArgsPOJO pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ArgTestCase");
      suite.addTestSuite(ArgTestCase.class);
      return suite;
   }

   public ArgTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      this.pojo = new ArgsPOJO();
      ArgAspect.clear();
      ArgAspectInterfaces.clear();
      ArgAspectInvertedArgs.clear();
      ArgAspectGenerics.clear();
   }

   public void test1()
   {
      assertEquals(52, this.pojo.bunch1(5, (double) 1.3, (float) 0, "test1", 1));
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.before3);
      assertTrue(ArgAspect.before4);
      assertTrue(ArgAspect.before5);
      assertTrue(ArgAspect.around4);
      assertTrue(ArgAspect.around5);
      assertTrue(ArgAspect.after1);
      assertTrue(ArgAspect.after4);
      
      assertEquals(5, ArgAspect.before1X);
      assertEquals(5, ArgAspect.before2X);
      assertEquals(1, ArgAspect.before3Q);
      assertEquals(25, ArgAspect.before5X);
      assertEquals(-17, ArgAspect.before5Q);
      assertEquals(17, ArgAspect.around5X);
      assertEquals(34, ArgAspect.around5Q);
      assertEquals(17, ArgAspect.after1X);
      assertEquals(17, ArgAspect.after4X);
      assertEquals(34, ArgAspect.after4Q);
      
      assertFalse(ArgAspect.finally1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test2()
   {
      assertEquals(108, this.pojo.bunch2(1, (double) 2.0, (float) 3, 4));
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.before4);
      assertTrue(ArgAspect.before5);
      assertTrue(ArgAspect.around6);
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      assertTrue(ArgAspect.finally1);
      
      assertEquals(1, ArgAspect.before1X);
      assertEquals(1, ArgAspect.before2X);
      assertEquals(5, ArgAspect.before5X);
      assertEquals(4, ArgAspect.before5Q);
      assertEquals(6, ArgAspect.after4X);
      assertEquals(48, ArgAspect.after4Q);
      assertEquals(5, ArgAspect.finally1X);
      assertSame(ArgAspect.around6Args, ArgAspect.after6Args);
      
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test3()
   {
      assertEquals(18, this.pojo.bunch3(1, (double) 2.0, (float) 3, 4));
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.before4);
      assertTrue(ArgAspect.before5);
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      assertTrue(ArgAspect.finally2);
      
      assertEquals(1, ArgAspect.before1X);
      assertEquals(1, ArgAspect.before2X);
      assertEquals(5, ArgAspect.before5X);
      assertEquals(4, ArgAspect.before5Q);
      assertEquals(5, ArgAspect.after4X);
      assertEquals(4, ArgAspect.after4Q);  
      assertNotNull(ArgAspect.after6Args);
      assertNotNull(ArgAspect.finally2Args);
      assertSame(ArgAspect.after6Args, ArgAspect.finally2Args);
      assertEquals(4, ((Integer) ArgAspect.after6Args[0]).intValue());
      assertEquals(2.0, ((Double) ArgAspect.after6Args[1]).doubleValue());
      assertEquals(3.0, ((Float) ArgAspect.after6Args[2]).floatValue());
      assertEquals(4, ((Integer) ArgAspect.after6Args[3]).intValue());
      
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.around6);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.finally1);
   }
   
   public void test4()
   {
      assertEquals(132, this.pojo.bunch4(10, (double) 9.0, (float) 8.0, 7));
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.before4);
      assertTrue(ArgAspect.before5);
      assertTrue(ArgAspect.around6);
      
      assertEquals(10, ArgAspect.before1X);
      assertEquals(10, ArgAspect.before2X);
      assertEquals(50, ArgAspect.before5X);
      assertEquals(7, ArgAspect.before5Q);
      
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.after4);
      assertFalse(ArgAspect.after6);
      assertFalse(ArgAspect.finally1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test5()
   {
      assertEquals(110, this.pojo.bunch5(10, (double) 9.0, (float) 8.0, 7));
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.before4);
      assertTrue(ArgAspect.before5);
      
      
      assertEquals(10, ArgAspect.before1X);
      assertEquals(10, ArgAspect.before2X);
      assertEquals(50, ArgAspect.before5X);
      assertEquals(7, ArgAspect.before5Q);
      
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.around6);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.after4);
      assertFalse(ArgAspect.after6);
      assertFalse(ArgAspect.finally1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test6()
   {
      assertEquals(156, this.pojo.bunch6(51, (double) 5.3, (float) 61, 131));
      
      assertTrue(ArgAspect.around6);
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      
      assertEquals(6, ArgAspect.after4X);
      assertEquals(48, ArgAspect.after4Q);  
      assertSame(ArgAspect.around6Args, ArgAspect.after6Args);
      
      assertFalse(ArgAspect.before1);
      assertFalse(ArgAspect.before2);
      assertFalse(ArgAspect.before4);
      assertFalse(ArgAspect.before5);
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.finally1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test7()
   {
      assertEquals(168, this.pojo.bunch7(15, (double) 3.5, (float) 16, 131));
      
      assertTrue(ArgAspect.around6);
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      assertTrue(ArgAspect.finally1);
      assertTrue(ArgAspect.finally2);
      
      assertEquals(6, ArgAspect.after4X);
      assertEquals(48, ArgAspect.after4Q);  
      assertEquals(5, ArgAspect.finally1X);
      assertSame(ArgAspect.around6Args, ArgAspect.after6Args);
      assertSame(ArgAspect.after6Args, ArgAspect.finally2Args);
      
      assertFalse(ArgAspect.before1);
      assertFalse(ArgAspect.before2);
      assertFalse(ArgAspect.before4);
      assertFalse(ArgAspect.before5);
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.after1);
   }
   
   public void test8()
   {
      assertEquals(283, this.pojo.bunch8(51, (double) 5.3, (float) 61, 131));
      
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      
      assertEquals(51, ArgAspect.after4X);
      assertEquals(131, ArgAspect.after4Q);  
      assertNotNull(ArgAspect.after6Args);
      assertEquals(50, ((Integer) ArgAspect.after6Args[0]).intValue());
      assertEquals(5.3, ((Double) ArgAspect.after6Args[1]).doubleValue());
      assertEquals(61, ((Float) ArgAspect.after6Args[2]).floatValue());
      assertEquals(131, ((Integer) ArgAspect.after6Args[3]).intValue());
      
      assertFalse(ArgAspect.before1);
      assertFalse(ArgAspect.before2);
      assertFalse(ArgAspect.before4);
      assertFalse(ArgAspect.before5);
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.around6);
      assertFalse(ArgAspect.after1);
      assertFalse(ArgAspect.finally1);
      assertFalse(ArgAspect.finally2);
   }
   
   public void test9()
   {
      assertEquals(189, this.pojo.bunch9(15, (double) 3.5, (float) 16, 131));
      
      assertTrue(ArgAspect.after4);
      assertTrue(ArgAspect.after6);
      assertTrue(ArgAspect.finally1);
      assertTrue(ArgAspect.finally2);
      
      assertEquals(15, ArgAspect.after4X);
      assertEquals(131, ArgAspect.after4Q);  
      assertNotNull(ArgAspect.after6Args);
      assertEquals(14, ArgAspect.finally1X);
      assertEquals(14, ((Integer) ArgAspect.after6Args[0]).intValue());
      assertEquals(3.5, ((Double) ArgAspect.after6Args[1]).doubleValue());
      assertEquals(16, ((Float) ArgAspect.after6Args[2]).floatValue());
      assertEquals(131, ((Integer) ArgAspect.after6Args[3]).intValue());
      
      assertFalse(ArgAspect.before1);
      assertFalse(ArgAspect.before2);
      assertFalse(ArgAspect.before4);
      assertFalse(ArgAspect.before5);
      assertFalse(ArgAspect.before3);
      assertFalse(ArgAspect.around6);
      assertFalse(ArgAspect.after1);
   }
   
   public void test10() throws POJOException
   {
      this.pojo.method9(new Implementor());
      
      assertTrue(ArgAspectInterfaces.before1);
      assertTrue(ArgAspectInterfaces.before2);
      assertTrue(ArgAspectInterfaces.before3);
      
      assertTrue(ArgAspectInterfaces.around1);
      assertTrue(ArgAspectInterfaces.around2);
      assertTrue(ArgAspectInterfaces.around3);
      
      assertTrue(ArgAspectInterfaces.after1);
      assertTrue(ArgAspectInterfaces.after2);
      assertTrue(ArgAspectInterfaces.after3);
      
      assertFalse(ArgAspectInterfaces.throwing1);
      assertFalse(ArgAspectInterfaces.throwing2);
      assertFalse(ArgAspectInterfaces.throwing3);
      
      assertTrue(ArgAspectInterfaces.finally1);
      assertTrue(ArgAspectInterfaces.finally2);
      assertTrue(ArgAspectInterfaces.finally3);
   }
   
   public void test11()
   {
      boolean thrown = false;
      try
      {
         this.pojo.method10(null);
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      
      assertTrue(thrown); // verify precondition for this test
      
      assertTrue(ArgAspectInterfaces.before1);
      assertTrue(ArgAspectInterfaces.before2);
      assertTrue(ArgAspectInterfaces.before3);
      
      assertTrue(ArgAspectInterfaces.around1);
      assertTrue(ArgAspectInterfaces.around2);
      assertTrue(ArgAspectInterfaces.around3);
      
      assertFalse(ArgAspectInterfaces.after1);
      assertFalse(ArgAspectInterfaces.after2);
      assertFalse(ArgAspectInterfaces.after3);
      
      assertTrue(ArgAspectInterfaces.throwing1);
      assertTrue(ArgAspectInterfaces.throwing2);
      assertTrue(ArgAspectInterfaces.throwing3);
      
      assertTrue(ArgAspectInterfaces.finally1);
      assertTrue(ArgAspectInterfaces.finally2);
      assertTrue(ArgAspectInterfaces.finally3);
   }
   
   public void test12()
   {
      pojo.field3 = 12;
      
      assertTrue(ArgAspect.before1);
      assertTrue(ArgAspect.before2);
      assertTrue(ArgAspect.finally1);
      
      assertEquals(12, ArgAspect.before1X);
      assertEquals(12, ArgAspect.before2X);
      assertEquals(12, ArgAspect.finally1X);
   }
   
   public void testInverted1()
   {
      pojo.method11("testInverted", null);
      
      assertTrue(ArgAspectInvertedArgs.before1);
      assertTrue(ArgAspectInvertedArgs.around1);
      assertTrue(ArgAspectInvertedArgs.after1);
      assertFalse(ArgAspectInvertedArgs.throwing1);
      assertTrue(ArgAspectInvertedArgs.finally1);
   }
   
   public void testInverted2()
   {
      boolean thrown = false;
      try
      {
         pojo.method12("testInverted", null);
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      // verify precondition for this test
      assertTrue(thrown);
      
      assertTrue(ArgAspectInvertedArgs.before1);
      assertTrue(ArgAspectInvertedArgs.around1);
      assertFalse(ArgAspectInvertedArgs.after1);
      assertTrue(ArgAspectInvertedArgs.throwing1);
      assertTrue(ArgAspectInvertedArgs.finally1);
   }
   
   public void testGenericsOnMethod()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      pojo.methodGenericsExecution1(list);
      assertGenericsAdvices(false);
   }

   public void testGenericsOnMethodWithException()
   {
      boolean thrown = false;
      try
      {
         List<SuperValue> list = new ArrayList<SuperValue>();
         pojo.methodGenericsExecution2(list);
      }
      catch(POJOException e)
      {
         thrown = true;
      }
      // verify precondition for this test
      assertTrue(thrown);
      assertGenericsAdvices(thrown);
   }
   
   public void testGenericsOnField()
   {
      pojo.field5 = new ArrayList<SuperValue>();
      assertGenericsAdvices(false);
   }
   
   public void testGenericsOnConsExec() throws POJOException
   {
      new ArgsPOJO(new ArrayList<SuperValue>(), false);
      assertGenericsAdvices(false);
   }
   
   public void testGenericsOnConsExecWithException() throws POJOException
   {
      boolean thrown = false;
      try
      {
         new ArgsPOJO(new ArrayList<SuperValue>(), true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      // verify precondition for this test
      assertTrue(thrown);
      assertGenericsAdvices(thrown);
   }
   
   public void testGenericsOnConstruction() throws POJOException
   {
      new ArgsPOJO(false, new ArrayList<SuperValue>());
      assertGenericsAdvices(false);
   }
   
   public void testGenericsOnConstructionWithException() throws POJOException
   {
      boolean thrown = false;
      try
      {
         new ArgsPOJO(true, new ArrayList<SuperValue>());
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      // verify precondition for this test
      assertTrue(thrown);
      // construction is executed in the end of constructor body. So, if this
      // cons body throws an exception, advices are not called
      assertFalse(ArgAspectGenerics.before1);
      assertFalse(ArgAspectGenerics.before2);
      assertFalse(ArgAspectGenerics.before4);
      assertFalse(ArgAspectGenerics.before5);
      assertFalse(ArgAspectGenerics.before6);
      assertFalse(ArgAspectGenerics.around1);
      assertFalse(ArgAspectGenerics.around2);
      assertFalse(ArgAspectGenerics.around3);
      assertFalse(ArgAspectGenerics.around4);
      assertFalse(ArgAspectGenerics.around6);
      assertFalse(ArgAspectGenerics.after1);
      assertFalse(ArgAspectGenerics.after2);
      assertFalse(ArgAspectGenerics.after3);
      assertFalse(ArgAspectGenerics.after4);
      assertFalse(ArgAspectGenerics.after6);
      assertFalse(ArgAspectGenerics.throwing1);
      assertFalse(ArgAspectGenerics.throwing3);
      assertFalse(ArgAspectGenerics.throwing4);
      assertFalse(ArgAspectGenerics.throwing5);
      assertFalse(ArgAspectGenerics.throwing6);
      assertFalse(ArgAspectGenerics.finally1);
      assertFalse(ArgAspectGenerics.finally2);
      assertFalse(ArgAspectGenerics.finally3);
      assertFalse(ArgAspectGenerics.finally4);
      assertFalse(ArgAspectGenerics.finally6);
   }
   
   private void assertGenericsAdvices(boolean thrown)
   {
      assertTrue(ArgAspectGenerics.before1);
      assertTrue(ArgAspectGenerics.before2);
      assertTrue(ArgAspectGenerics.before4);
      assertTrue(ArgAspectGenerics.before5);
      assertTrue(ArgAspectGenerics.before6);
      assertTrue(ArgAspectGenerics.around1);
      assertTrue(ArgAspectGenerics.around2);
      assertTrue(ArgAspectGenerics.around3);
      assertTrue(ArgAspectGenerics.around4);
      assertTrue(ArgAspectGenerics.around6);
      assertEquals(!thrown, ArgAspectGenerics.after1);
      assertEquals(!thrown, ArgAspectGenerics.after2);
      assertEquals(!thrown, ArgAspectGenerics.after3);
      assertEquals(!thrown, ArgAspectGenerics.after4);
      assertEquals(!thrown, ArgAspectGenerics.after6);
      assertEquals(thrown, ArgAspectGenerics.throwing1);
      assertEquals(thrown, ArgAspectGenerics.throwing3);
      assertEquals(thrown, ArgAspectGenerics.throwing4);
      assertEquals(thrown, ArgAspectGenerics.throwing5);
      assertEquals(thrown, ArgAspectGenerics.throwing6);
      assertTrue(ArgAspectGenerics.finally1);
      assertTrue(ArgAspectGenerics.finally2);
      assertTrue(ArgAspectGenerics.finally3);
      assertTrue(ArgAspectGenerics.finally4);
      assertTrue(ArgAspectGenerics.finally6);
   }  
}