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
package org.jboss.test.aop.typedAdvices;

import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class BeforeAfterThrowingTestCase extends AOPTestWithSetup
{
   public BeforeAfterThrowingTestCase(String arg0)
   {
      // FIXME BeforeAfterThrowingTestCase constructor
      super(arg0);
   }

   public void testSimple() throws Exception
   {
      System.out.println("=== TESING SIMPLE");
      POJO pojo = new POJO();
      
      System.out.println("* Calling method1 with error=false");
      SimpleAspect.clear();
      assertEquals(1, pojo.method1(false));
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.around);
      assertTrue(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      
      
      System.out.println("* Calling method1 with error=true");
      SimpleAspect.clear();
      try
      {
         pojo.method1(true);
         assertFalse("Should not get here", true);
      }
      catch(TestException e) 
      {
         assertSame(e, SimpleAspect.exception);
      }
      
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.around);
      assertFalse(SimpleAspect.after);
      assertTrue(SimpleAspect.throwing);
   }
   
   public void testSimpleWithFinally() throws Exception
   {
      System.out.println("=== TESING SIMPLE WITH FINALLY");
      POJO pojo = new POJO();
      
      System.out.println("* Calling method2 with error=false");
      SimpleAspect.clear();
      assertEquals(1, pojo.method2(false));
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.around);
      assertTrue(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      assertTrue(SimpleAspect.finallyAdvice);
      
      
      System.out.println("* Calling method2 with error=true");
      SimpleAspect.clear();
      try
      {
         pojo.method2(true);
         assertFalse("Should not get here", true);
      }
      catch(TestException e) 
      {
         assertSame(e, SimpleAspect.exception);
      }
      
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.around);
      assertFalse(SimpleAspect.after);
      assertTrue(SimpleAspect.throwing);
      assertTrue(SimpleAspect.finallyAdvice);
   }

   public void testNoMatchingSimple() throws Exception
   {
      System.out.println("=== TESING NO MATCHING SIMPLE");
      boolean thrown = false;
      try
      {
         new InvalidPOJO();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         new InvalidPOJO((short) 5);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         new InvalidPOJO(5l);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      InvalidPOJO invalidPojo = new InvalidPOJO("");
      thrown = false;
      try
      {
         invalidPojo.methodBefore();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.methodBefore(true);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.methodAfter();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.methodAfter(true);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         new InvalidAdviceNamePOJO();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         System.out.println(invalidPojo.i);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         System.out.println(invalidPojo.superValue);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.superValue = new SuperValue(5);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.subValue = new SubValue(5);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         invalidPojo.methodAfter(new SubValue(1), 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testArgs() throws Exception
   {
      System.out.println("=== TESTING WITH ARGUMENTS");
      

      System.out.println("* Testing ctor(boolean, int, long, String) with exception");
      ArgsAspect.clear();
      SimpleAspect.clear();
      try
      {
         POJO pojo = new POJO(true, 1, 1, "x");
         throw new RuntimeException("TestException not thrown");
      }
      catch (TestException e)
      {
          assertEquals("before1", ArgsAspect.before);
          assertNull(ArgsAspect.after);
          assertEquals("throwing1", ArgsAspect.throwing);
          assertSame(e, ArgsAspect.exception);
      }
      
      System.out.println("* Testing ctor(boolean, int, long, String)");
      ArgsAspect.clear();
      POJO pojo = new POJO(false, 2, 3, "y");
      assertEquals("before1", ArgsAspect.before);
      assertEquals("after2", ArgsAspect.after);
      assertNull(ArgsAspect.throwing);
      
      SimpleAspect.clear();
      System.out.println("* Testing method(boolean, int, long, String) with exception");
      ArgsAspect.clear();
      try
      {
         pojo.method(true, 10, 100L, "abc");
         throw new RuntimeException("TestException not thrown");
      }
      catch (TestException e)
      {
         assertSame(e, ArgsAspect.exception);
      }

      System.out.println("* Testing method(boolean, int, long, String)");
      ArgsAspect.clear();
      pojo.method(false, 10, 100L, "abc");
      

      System.out.println("* Testing method(boolean, int, long, String, int) with exception");
      ArgsAspect.clear();
      try
      {
         POJO ret = pojo.method(true, 10, 100L, "abc", 25);
         throw new RuntimeException("TestException not thrown");
      }
      catch (TestException e)
      {
         assertSame(e, ArgsAspect.exception);
      }
      
      assertEquals("before2", ArgsAspect.before);
      assertNull(ArgsAspect.after);
      assertEquals("throwing3", ArgsAspect.throwing);

      System.out.println("* Testing method(boolean, int, long, String, int)");
      ArgsAspect.clear();
      POJO ret = pojo.method(false, 10, 100L, "abc", 25);
      
      assertNotNull(ret);
      assertEquals("before2", ArgsAspect.before);
      assertEquals("after1", ArgsAspect.after);
      assertNull(ArgsAspect.throwing);
      
      assertFalse(SimpleAspect.before);
      assertFalse(SimpleAspect.around);
      assertFalse(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
   }

   public void testSimpleFields() throws Exception
   {
      System.out.println("=== TESTING Fields");
      POJO pojo = new POJO();

      ArgsAspect.clear();
      System.out.println("* Writing i");
      pojo.i = 5;
      assertEquals("before3", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertNull(ArgsAspect.after);
      
      ArgsAspect.clear();
      System.out.println("* Reading i");
      int i = pojo.i;
      assertEquals("before4", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals(null, ArgsAspect.after);
   }  
      
   public void testFieldsWithInheritance() throws Exception
   {
      System.out.println("=== TESTING Fields with inheritance");
      POJO pojo = new POJO();

      ArgsAspect.clear();
      System.out.println("* Writing superValue");
      pojo.superValue = new SuperValue(5);
      assertEquals("before4", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertNull(null, ArgsAspect.after);

      ArgsAspect.clear();
      System.out.println("* Reading superValue");
      SuperValue superVal = pojo.superValue;
      assertEquals("before4", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after5", ArgsAspect.after);
      assertEquals(10, superVal.getValue()); //The after4 and after5 methods double the value
      
      
      ArgsAspect.clear();
      System.out.println("* Writing subValue");
      pojo.subValue = new SubValue(5);
      assertEquals("before5", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals(null, ArgsAspect.after);
      
      ArgsAspect.clear();
      System.out.println("* Writing subValue");
      SubValue subVal = pojo.subValue;
      assertEquals("before4", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after4", ArgsAspect.after);
      assertEquals(10, subVal.getValue());//The after4 methods double the value
   }
   
   public void testMethodsWithInheritance()
   {
      System.out.println("=== TESTING METHODS WITH INHERITANCE OF RETURN AND PARAMETERS");

      GeneralAspect.clear();
      System.out.println(" * Testing constructor(SuperValue, int)");
      SuperValue superValue = new SuperValue(5);
      POJO pojo = new POJO(superValue, 5);
      assertEquals("before", GeneralAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after", GeneralAspect.after);
      
      GeneralAspect.clear();
      System.out.println(" * Testing constructor(SubValue, int)");
      SubValue subValue = new SubValue(6);
      POJO pojo2 = new POJO(subValue, 6);
      assertEquals("before", GeneralAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after", GeneralAspect.after);
      
      GeneralAspect.clear();
      System.out.println(" * Testing method(SubValue, int)");
      subValue = pojo.method(subValue, 5);
      assertEquals("before", GeneralAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after", GeneralAspect.after);
      
      GeneralAspect.clear();
      System.out.println(" * Testing method(SuperValue, int)");
      superValue = pojo.method(superValue, 10);
      assertEquals("before", GeneralAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after", GeneralAspect.after);
     
      ArgsAspect.clear();
      System.out.println(" * Testing method(SubValue, SubValue)");
      SuperValue ret = pojo.method(new SubValue(5), new SubValue(6));
      assertEquals("before6", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after6", ArgsAspect.after);
      
      ArgsAspect.clear();
      System.out.println(" * Testing method(SuperValue, SubValue)");
      pojo.method(new SuperValue(7), new SubValue(8));
      assertEquals("before7", ArgsAspect.before);
      assertNull(ArgsAspect.throwing);
      assertEquals("after7", ArgsAspect.after);
   }
}