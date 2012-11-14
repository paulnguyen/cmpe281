/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.array;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.test.aop.AOPTestWithSetup;


/**
 * Test that arrays get advised properly
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"unused"})
public class AOPArrayTestCase extends AOPTestWithSetup
{
   public AOPArrayTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AOPArrayTestCase");
      suite.addTestSuite(AOPArrayTestCase.class);
      return suite;
   }

   public void testStringArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.strings[2] = "X";
      assertEquals(2, TestArrayElementInterceptor.index);
      assertEquals("X", TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      String s = obj.strings[0];
      assertEquals("1", s);
      assertEquals(0, TestArrayElementInterceptor.index);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }

   public void testObjectArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.objects[2] = "X";
      assertEquals(2, TestArrayElementInterceptor.index);
      assertEquals("X", TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      Object o = obj.objects[0];
      assertEquals("1", o);
      assertEquals(0, TestArrayElementInterceptor.index);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testIntArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.ints[1] = 100; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(100, ((Integer)TestArrayElementInterceptor.value).intValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.ints[2] = 123;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      int val = obj.ints[2];
      assertEquals(123, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testByteArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.bytes[1] = 100; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(100, ((Byte)TestArrayElementInterceptor.value).byteValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.bytes[2] = 123;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      byte val = obj.bytes[2];
      assertEquals(123, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testBooleanArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.booleans[1] = true;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(true, ((Boolean)TestArrayElementInterceptor.value).booleanValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      clearInterceptors();
      AspectForPrecedence.invoked = false;
      boolean val = obj.booleans[1];
      assertEquals(true, val);
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testCharArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.chars[1] = 'z'; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals('z', ((Character)TestArrayElementInterceptor.value).charValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.chars[2] = 'x';
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      char val = obj.chars[2];
      assertEquals('x', val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }

   public void testDoubleArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.doubles[1] = 2.1d; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(2.1d, ((Double)TestArrayElementInterceptor.value).doubleValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.doubles[2] = 9.9d;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      double val = obj.doubles[2];
      assertEquals(9.9d, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testFloatArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.floats[1] = 2.1f; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(2.1f, ((Float)TestArrayElementInterceptor.value).floatValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.floats[2] = 9.9f;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      double val = obj.floats[2];
      assertEquals(9.9f, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testLongArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.longs[1] = 100L; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(100L, ((Long)TestArrayElementInterceptor.value).longValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.longs[2] = 200L;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      double val = obj.longs[2];
      assertEquals(200L, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testShortArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      obj.shorts[1] = 50; 
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(50, ((Short)TestArrayElementInterceptor.value).shortValue());
      assertTrue(AspectForPrecedence.invoked);
      checkWrite();

      obj.shorts[2] = 100;
      
      clearInterceptors();
      AspectForPrecedence.invoked = false;
      double val = obj.shorts[2];
      assertEquals(100, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      assertTrue(AspectForPrecedence.invoked);
      checkRead();
   }
   
   public void testMultiDimensionalTopLevelArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      
      //Create replacement array for top level, this is not registered yet so should be unadvised
      clearInterceptors();
      int[][] replacement0 = new int[][] {new int[]{11,22}, new int[] {33,44}};
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      //Store reference to array to be replaced
      clearInterceptors();
      int[][] original_0 = obj.ints3d[0];
      assertEquals(0, TestArrayElementInterceptor.index);
      checkRead();
      
      //Replace array in top-level array, interception should happen now
      clearInterceptors();
      obj.ints3d[0] = replacement0;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(replacement0, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      int i = obj.ints3d[0][0][1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(22, i);
      checkRead();
      
      clearInterceptors();
      obj.ints3d[0][0][1] = 99;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(99, ((Integer)TestArrayElementInterceptor.value).intValue());
      checkReadAndWrite();
      
      //The original array should no longer be registered
      clearInterceptors();
      i = original_0[0][1];
      assertEquals(2, i);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();   
      original_0[0][1] = 100;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();   
   }

   public void testMultiDimensionalNestedArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();

      //Create replacement for nested array 
      clearInterceptors();
      int[] replacement_0_1 = new int[] {111,222, 333};
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      //Store reference to array to be replaced
      clearInterceptors();
      int[] original_0_1 = obj.ints3d[0][1];
      assertEquals(1, TestArrayElementInterceptor.index);
      checkRead();

      //Replace nested array, interception should happen now
      clearInterceptors();
      obj.ints3d[0][1] = replacement_0_1;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNotNull(TestArrayElementInterceptor.value);
      assertEquals(replacement_0_1, TestArrayElementInterceptor.value);
      checkReadAndWrite();
      
      clearInterceptors();
      int i = obj.ints3d[0][1][2];
      assertEquals(2, TestArrayElementInterceptor.index);
      assertEquals(333, i);
      checkRead();

      clearInterceptors();
      obj.ints3d[0][0][1] = 99;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(99, ((Integer)TestArrayElementInterceptor.value).intValue());
      checkReadAndWrite();
      
      //The original array should no longer be registered
      clearInterceptors();
      original_0_1[1] = 100;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      i = original_0_1[0];
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
   }
   
   public void testMultipleFieldReferences()
   {
      ClassWithSeveralReferencesToSameArray obj = new ClassWithSeveralReferencesToSameArray();
      assertEquals(obj.one, obj.two);
      
      int[] original = obj.one;
      
      clearInterceptors();
      obj.one[1] = 100;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      int i = obj.one[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      clearInterceptors();
      i = obj.two[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      
      assertEquals(obj.one, obj.two);
      
      clearInterceptors();
      int replacement1[] = new int[2];
      replacement1[0] = 11;
      replacement1[1] = 22;
      
      obj.one = replacement1;
      clearInterceptors();
      obj.one[1] = 99;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(99, TestArrayElementInterceptor.value);
      checkWrite();

      //obj.two still references original, so make sure that we have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();

      obj.two = replacement1;
      clearInterceptors();
      obj.one[1] = 101;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(101, TestArrayElementInterceptor.value);
      checkWrite();

      //original is no longer referenced by and advised field, we should not have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
   }
   
   public void testMultipleNestedReferences()
   {
      ClassWithSeveralReferencesToSameArray obj = new ClassWithSeveralReferencesToSameArray();
      assertEquals(obj.multi[0], obj.multi[1]);
      
      int[] original = obj.multi[0];
      
      clearInterceptors();
      obj.multi[0][1] = 100;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, TestArrayElementInterceptor.value);
      checkReadAndWrite();
      clearInterceptors();
      int i = obj.multi[0][1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      clearInterceptors();
      i = obj.multi[1][1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      
      assertEquals(obj.multi[0], obj.multi[1]);
      
      clearInterceptors();
      int replacement1[] = new int[2];
      replacement1[0] = 11;
      replacement1[1] = 22;
      
      obj.multi[0] = replacement1;
      clearInterceptors();
      obj.multi[0][1] = 99;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(99, TestArrayElementInterceptor.value);
      checkReadAndWrite();

      //obj.multi[1] still references original, so make sure that we have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();

      obj.multi[1] = replacement1;
      clearInterceptors();
      obj.multi[0][1] = 101;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(101, TestArrayElementInterceptor.value);
      checkReadAndWrite();

      //original is no longer referenced by and advised field, we should not have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
   }
   
   public void testMultipleMixedReferences()
   {
      ClassWithSeveralReferencesToSameArray obj = new ClassWithSeveralReferencesToSameArray();
      obj.multi[0] = obj.one = new int[] {8,6,4};
      
      int[] original = obj.multi[0];
      
      clearInterceptors();
      obj.multi[0][1] = 100;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, TestArrayElementInterceptor.value);
      checkReadAndWrite();
      clearInterceptors();
      int i = obj.multi[0][1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      clearInterceptors();
      i = obj.one[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();
      
      assertEquals(obj.multi[0], obj.one);
      
      clearInterceptors();
      int replacement1[] = new int[2];
      replacement1[0] = 11;
      replacement1[1] = 22;
      
      obj.multi[0] = replacement1;
      clearInterceptors();
      obj.multi[0][1] = 99;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(99, TestArrayElementInterceptor.value);
      checkReadAndWrite();

      //obj.one still references original, so make sure that we have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(100, i);
      checkRead();

      obj.one = replacement1;
      clearInterceptors();
      obj.multi[0][1] = 101;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(101, TestArrayElementInterceptor.value);
      checkReadAndWrite();

      //original is no longer referenced by and advised field, we should not have interception 
      clearInterceptors();
      i = original[1];
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
   }

   public void testObjectArrayWithArrayAsObjectEntry()
   {
      int[] replacement = new int[] {1,2,3};
      ClassWithArrayFields obj = new ClassWithArrayFields();
      
      //Add an array as one of the elements in the object array
      clearInterceptors();
      obj.objects[0] = replacement;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(replacement, TestArrayElementInterceptor.value);
      checkWrite();
      
      //The sub array should be advised
      clearInterceptors();
      replacement[1] = 5;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(5, ((Integer)TestArrayElementInterceptor.value).intValue());
      checkWrite();
      
      //Put in a string object
      clearInterceptors();
      obj.objects[0] = "X";
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals("X", TestArrayElementInterceptor.value);
      checkWrite();
      
      //The nested array no longer is associated with array and should be unadvised
      clearInterceptors();
      replacement[1] = 10;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertEquals(null, TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
   }
   
   public void testObjectArrayWithArrayAsObjectEntry2()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      Object[] original = obj.objects;
      obj.objects = new Object[] {new int[] {1,2,3}};
      
      clearInterceptors();
      original[0] = "X";
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      Object val = original[0];
      assertEquals("X", val);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      int[] i = (int[])obj.objects[0];
      assertEquals(0, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      clearInterceptors();
      i[1] = 10;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(10, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      int ival = i[1];
      assertEquals(10, ival);
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
   }
   
   public void testObjectFieldWithArray()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      obj.objectField = new int[] {1,2,3};
      
      clearInterceptors();
      ((int[])obj.objectField)[1] = 10;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(10, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      int val = ((int[])obj.objectField)[2];
      assertEquals(3, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      int[] array = (int[])obj.objectField;
      clearInterceptors();
      val = array[2];
      assertEquals(3, val);
      assertEquals(2, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      obj.objectField = null;
      clearInterceptors();
      val = array[2];
      assertEquals(3, val);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
   }
   
   public void testObjectFieldWithNestedArrays()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      obj.objectField = new Object[] {new int[] {1, 2}, new Object[] {"X", "Y"}};
      
      Object[] objArray = (Object[])obj.objectField;
      
      clearInterceptors();
      int[] intArray = (int[])((Object[])obj.objectField)[0]; 
      assertEquals(0, TestArrayElementInterceptor.index);
      checkRead();
      
      clearInterceptors();
      Object[] objArray2 = (Object[])((Object[])obj.objectField)[1]; 
      assertEquals(1, TestArrayElementInterceptor.index);
      checkRead();

      clearInterceptors();
      intArray[1] = 10;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(10, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      int val = intArray[1];
      assertEquals(10, val);
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      clearInterceptors();
      objArray2[1] = "ZZZ";
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals("ZZZ", TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      Object oval = objArray2[1];
      assertEquals("ZZZ", oval);
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      clearInterceptors();
      int[] intArray2 = new int[] {11, 22};
      objArray[0] = intArray2;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(intArray2, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      intArray2[1] = 10;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertEquals(10, TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      val = intArray2[1];
      assertEquals(10, val);
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkRead();
      
      clearInterceptors();
      intArray[1] = 10;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      val = intArray[1];
      assertEquals(10, val);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      objArray[0] = null;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      objArray[1] = null;
      assertEquals(1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkWrite();
      
      clearInterceptors();
      intArray2[1] = 10;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      val = intArray2[1];
      assertEquals(10, val);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      objArray2[1] = "ZZZ";
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      clearInterceptors();
      oval = objArray2[1];
      assertEquals("ZZZ", oval);
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
   }
   
   public void testIgnoreDoubleUpdate()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      
      String sval = "X";
      clearInterceptors();
      obj.objects[0] = sval;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(sval, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.objects[0] = sval;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      byte byteVal = 10;
      clearInterceptors();
      obj.bytes[0] = byteVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(byteVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.bytes[0] = byteVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      //Set the value to be different
      obj.booleans[0] = false;
      boolean booleanVal = true;
      clearInterceptors();
      obj.booleans[0] = booleanVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(booleanVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.booleans[0] = booleanVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      booleanVal = false;
      clearInterceptors();
      obj.booleans[0] = booleanVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(booleanVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.booleans[0] = booleanVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      char charVal = 'h';
      clearInterceptors();
      obj.chars[0] = charVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(charVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.chars[0] = charVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      double doubleVal = 101.1d;
      clearInterceptors();
      obj.doubles[0] = doubleVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(doubleVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.doubles[0] = doubleVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      
      float floatVal = 66.6f;
      clearInterceptors();
      obj.floats[0] = floatVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(floatVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.floats[0] = floatVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      int intVal = 1000;
      clearInterceptors();
      obj.ints[0] = intVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(intVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.ints[0] = intVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      int[][] intVal2d = new int[][]{new int[]{1}, new int[]{2}};
      clearInterceptors();
      obj.ints3d[0] = intVal2d;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(intVal2d, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.ints3d[0] = intVal2d;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      long longVal = 10001;
      clearInterceptors();
      obj.longs[0] = longVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(longVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.longs[0] = longVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      short shortVal = 999;
      clearInterceptors();
      obj.shorts[0] = shortVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(shortVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.shorts[0] = shortVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();

      Object objectVal = new int[] {1,2,3};
      clearInterceptors();
      obj.objects[0] = objectVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(objectVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      obj.objects[0] = objectVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
      intVal = 100;
      clearInterceptors();
      ((int[])objectVal)[0] = intVal;
      assertEquals(0, TestArrayElementInterceptor.index);
      assertEquals(intVal, TestArrayElementInterceptor.value);
      checkWrite();
      clearInterceptors();
      ((int[])objectVal)[0] = intVal;
      assertEquals(-1, TestArrayElementInterceptor.index);
      assertNull(TestArrayElementInterceptor.value);
      checkNoReadWrite();
   }
   
   public void testUnadvisedArrayFields()
   {
      ClassWithUnadvisedArrayFields obj = new ClassWithUnadvisedArrayFields();

      clearInterceptors();
      obj.objects[0] = "X";
      assertEquals("X", obj.objects[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.bytes[0] = 1;
      assertEquals(1, obj.bytes[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.booleans[0] = true;
      assertEquals(true, obj.booleans[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.chars[0] = 'z';
      assertEquals('z', obj.chars[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.doubles[0] = 9.9d;
      assertEquals(9.9d, obj.doubles[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.floats[0] = 9.9f;
      assertEquals(9.9f, obj.floats[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.ints[0] = 100;
      assertEquals(100, obj.ints[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.longs[0] = 100;
      assertEquals(100, obj.longs[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
      
      clearInterceptors();
      obj.shorts[0] = 100;
      assertEquals(100, obj.shorts[0]);
      assertEquals(-1, TestArrayElementInterceptor.index);
      checkNoReadWrite();
   }

   public void testBranchesStillValidFollowingInstrumentation()
   {
      ClassWithArrayFields obj = new ClassWithArrayFields();
      StringBuffer sb = new StringBuffer();
      
      for (int i = 0 ; i < 3 ; i++)
      {
         int[] ints = new int[] {0, 1, 2};
         
         if (i >= 1)
         {
            sb.append("g");
            obj.ints = ints;
            
            clearInterceptors();
            int val = ints[i];
            assertEquals(i, val);
            assertEquals(i, TestArrayElementInterceptor.index);
            assertNull(TestArrayElementInterceptor.value);
            
            clearInterceptors();
            obj.ints[i] = i + 2;
            assertEquals(i, TestArrayElementInterceptor.index);
            assertEquals(i + 2, ((Integer)TestArrayElementInterceptor.value).intValue());
         }
         else
         {
            sb.append("s");
            obj.ints = ints;

            clearInterceptors();
            obj.ints[i] = i + 2;
            assertEquals(i, TestArrayElementInterceptor.index);
            assertEquals(i + 2, ((Integer)TestArrayElementInterceptor.value).intValue());

            clearInterceptors();
            int val = ints[i];
            assertEquals(i + 2, ints[i]);
            assertEquals(i, TestArrayElementInterceptor.index);
            assertNull(TestArrayElementInterceptor.value);
         }
      }
      assertEquals("sgg", sb.toString());
   }
   
   void clearInterceptors()
   {
      TestArrayElementInterceptor.clear();
      TestArrayElementReadInterceptor.invoked = false;
      TestArrayElementWriteInterceptor.invoked = false;
   }
   
   void checkRead()
   {
      assertTrue(TestArrayElementReadInterceptor.invoked);
      assertFalse(TestArrayElementWriteInterceptor.invoked);
   }
   
   void checkWrite()
   {
      assertFalse(TestArrayElementReadInterceptor.invoked);
      assertTrue(TestArrayElementWriteInterceptor.invoked);
   }
   
   void checkReadAndWrite()
   {
      assertTrue(TestArrayElementReadInterceptor.invoked);
      assertTrue(TestArrayElementWriteInterceptor.invoked);
   }
   
   void checkNoReadWrite()
   {
      assertFalse(TestArrayElementReadInterceptor.invoked);
      assertFalse(TestArrayElementWriteInterceptor.invoked);
   }
}
