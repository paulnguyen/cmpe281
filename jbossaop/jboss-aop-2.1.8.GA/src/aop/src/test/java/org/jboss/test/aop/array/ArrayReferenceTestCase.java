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

import java.util.HashSet;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.array.ArrayReference;
import org.jboss.aop.array.ArrayRegistry;
import org.jboss.test.aop.AOPTestWithSetup;


/**
 * Test that ArrayRegistry.getArrayOwners() returns the right data
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"unused", "unchecked"})
public class ArrayReferenceTestCase extends AOPTestWithSetup
{
   public ArrayReferenceTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ArrayReferenceTestCase");
      suite.addTestSuite(ArrayReferenceTestCase.class);
      return suite;
   }

   public void testSimpleFieldReference()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      obj.fieldA = arr;
      List<ArrayReference> references = registry.getArrayOwners(arr);

      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertNull(reference.getNestedArrayIndices());

      obj.fieldA = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }
   
   public void testMultipleFieldReferencesSameRootObjects()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      obj.fieldA = arr;
      obj.fieldB = arr;
      List<ArrayReference> references = registry.getArrayOwners(arr);
      assertEquals(2, references.size());
      
      HashSet fields = new HashSet();
      fields.add("fieldA");
      fields.add("fieldB");
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertEquals(obj, owner);
         assertTrue("Expeced 'fieldA' or 'fieldB'; was " + ref.getRootField(), ref.getRootField() == "fieldA" || ref.getRootField() == "fieldB");
         assertNull(ref.getNestedArrayIndices());
         fields.remove(ref.getRootField());
      }
      
      assertTrue("Did not find all references " + fields, fields.size() == 0);
      
      obj.fieldA = null;
      references = registry.getArrayOwners(arr);
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldB", reference.getRootField());
      assertNull(reference.getNestedArrayIndices());
      
      obj.fieldB = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleFieldReferencesDifferentRootObjects()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      ClassForReference obj2 = new ClassForReference();
      obj.fieldA = arr;
      obj2.fieldB = arr;
      List<ArrayReference> references = registry.getArrayOwners(arr);
      assertEquals(2, references.size());
      
      HashSet rootObjects = new HashSet();
      rootObjects.add(obj);
      rootObjects.add(obj2);
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertTrue("Expected " + obj + " or " + obj2 + "; was" + owner, owner == obj || owner == obj2);
         if (owner == obj)
         {
            assertEquals("fieldA", ref.getRootField());
         }
         else if (owner == obj2)
         {
            assertEquals("fieldB", ref.getRootField());
         }
         assertNull(ref.getNestedArrayIndices());
         rootObjects.remove(owner);
      }
      
      assertTrue("Did not find all references " + rootObjects, rootObjects.size() == 0);
      
      obj.fieldA = null;
      references = registry.getArrayOwners(arr);
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj2, reference.getRootObject());
      assertEquals("fieldB", reference.getRootField());
      assertNull(reference.getNestedArrayIndices());
      
      obj2.fieldB = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleFieldReferencesStatic()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference.staticA = arr;
      ClassForReference.staticB = arr;
      List<ArrayReference> references = registry.getArrayOwners(arr);
      assertEquals(2, references.size());
      
      HashSet fields = new HashSet();
      fields.add("staticA");
      fields.add("staticB");
      
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertEquals(ClassForReference.class, owner);
         assertTrue("Expected 'staticA' or 'staticB'; was " + ref.getRootField(), ref.getRootField() == "staticA" || ref.getRootField() == "staticB");
         assertNull(ref.getNestedArrayIndices());
         fields.remove(ref.getRootField());
      }
      
      assertTrue("Did not find all references " + fields, fields.size() == 0);
      
      ClassForReference.staticA = null;
      references = registry.getArrayOwners(arr);
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(ClassForReference.class, reference.getRootObject());
      assertEquals("staticB", reference.getRootField());
      assertNull(reference.getNestedArrayIndices());
      
      ClassForReference.staticB = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleFieldReferencesStaticAndNonStatic()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      obj.fieldA = arr;
      ClassForReference.staticA = arr;
      List<ArrayReference> references = registry.getArrayOwners(arr);
      assertEquals(2, references.size());
      
      HashSet rootObjects = new HashSet();
      rootObjects.add(obj);
      rootObjects.add(ClassForReference.class);
      
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertTrue("Expected " + obj + " or " + ClassForReference.class + "; was" + owner, owner == obj || owner == ClassForReference.class);
         if (owner == obj)
         {
            assertEquals("fieldA", ref.getRootField());
         }
         else if (owner == ClassForReference.class)
         {
            assertEquals("staticA", ref.getRootField());
         }
         assertNull(ref.getNestedArrayIndices());
         rootObjects.remove(owner);
      }
      
      assertTrue("Did not find all references " + rootObjects, rootObjects.size() == 0);
      
      obj.fieldA = null;
      references = registry.getArrayOwners(arr);
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(ClassForReference.class, reference.getRootObject());
      assertEquals("staticA", reference.getRootField());
      assertNull(reference.getNestedArrayIndices());
      
      ClassForReference.staticA = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }
   
   public void testSimpleElementReferences()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      obj.fieldA = new Object[] {arr};
      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertNotNull(reference.getNestedArrayIndices());
      assertEquals(1, reference.getNestedArrayIndices().size()); 
      assertEquals(new Integer(0), reference.getNestedArrayIndices().get(0));

      obj.fieldA[0] = null;
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleElementReferencesSameArraySameRootObject()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      obj.fieldA = new Object[] {arr, null, arr};
      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(2, references.size());
      
      HashSet indeces = new HashSet();
      indeces.add(new Integer(0));
      indeces.add(new Integer(2));
      
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertEquals(obj, owner);
         assertEquals("fieldA", ref.getRootField());
         assertNotNull(ref.getNestedArrayIndices());
         assertEquals(1, ref.getNestedArrayIndices().size());
         indeces.remove(ref.getNestedArrayIndices().get(0));
      }
      assertTrue("Did not find all references " + indeces, indeces.size() == 0);

      obj.fieldA[2] = null; 
      
      references = registry.getArrayOwners(arr);
      
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertEquals(1, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(0), reference.getNestedArrayIndices().get(0));

      obj.fieldA[0] = null; 
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleElementReferencesSameArrayDifferentRootObject()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference obj = new ClassForReference();
      ClassForReference obj2 = new ClassForReference();
      obj.fieldA = new Object[] {arr};
      obj2.fieldA = new Object[] {arr};
      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(2, references.size());
      
      HashSet owners = new HashSet();
      owners.add(obj);
      owners.add(obj2);
      
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertEquals("fieldA", ref.getRootField());
         assertNotNull(ref.getNestedArrayIndices());
         assertEquals(1, ref.getNestedArrayIndices().size());
         assertEquals(new Integer(0), ref.getNestedArrayIndices().get(0));
         owners.remove(owner);
      }
      assertTrue("Did not find all references " + owners, owners.size() == 0);

      obj.fieldA[0] = null; 
      
      references = registry.getArrayOwners(arr);
      
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(obj2, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertEquals(1, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(0), reference.getNestedArrayIndices().get(0));

      obj2.fieldA[0] = null; 
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testMultipleElementReferencesStatic()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      Object[] arr = new Object[] {"1", "2", "3"};
      ClassForReference.staticA = new Object[] {arr};
      ClassForReference.staticB = new Object[] {null, arr};
      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(2, references.size());
      
      HashSet elements = new HashSet();
      elements.add("staticA0");
      elements.add("staticB1");
      
      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertEquals(ClassForReference.class, owner);
         assertNotNull(ref.getNestedArrayIndices());
         assertEquals(1, ref.getNestedArrayIndices().size());
         elements.remove(ref.getRootField() + ref.getNestedArrayIndices().get(0));
      }
      assertTrue("Did not find all references " + elements, elements.size() == 0);

      ClassForReference.staticA[0] = null; 
      
      references = registry.getArrayOwners(arr);
      
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(ClassForReference.class, reference.getRootObject());
      assertEquals("staticB", reference.getRootField());
      assertEquals(1, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(1), reference.getNestedArrayIndices().get(0));

      ClassForReference.staticB[1] = null; 
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }
   
   public void testNestedMultipleReferences()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      int[] arr = new int[] {1, 2, 3};
      ClassForReference obj = new ClassForReference();
      Object[][][][] fieldA = new Object[][][][] {null, null, new Object[][][] {null, new Object[][] {null, null, new Object[] {null, null, null, arr}}}}; //2, 1, 2, 3
      obj.fieldA = fieldA;
      Object[][][] staticA = new Object[][][] {new Object[][][] {null, new Object[][] {null, null, new Object[]{null, null, arr}}},null}; //0, 1, 2, 2
      ClassForReference.staticA = staticA;
      
      final String FIELDA = "fieldA2123"; 
      final String STATICA = "staticA0122";
      
      HashSet elements = new HashSet();
      elements.add(FIELDA);
      elements.add(STATICA);

      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(2, references.size());

      for (ArrayReference ref : references)
      {
         Object owner = ref.getRootObject();
         assertNotNull(ref.getNestedArrayIndices());
         StringBuffer elem = new StringBuffer(ref.getRootField());
         
         List<Integer> indices = ref.getNestedArrayIndices();
         for (Integer index : indices)
         {
            elem.append(index);
         }
         
         System.out.println("----> elem " + elem.toString());
         elements.remove(elem.toString());
         if (elem.toString().equals(STATICA))
         {
            assertEquals(ClassForReference.class, owner);
         }
         else if (elem.toString().equals(FIELDA))
         {
            assertEquals(obj, owner);
         }
      }   
      assertTrue("Did not find all references " + elements, elements.size() == 0);

      fieldA[2][1][2][3] = null;
      references = registry.getArrayOwners(arr);
      assertEquals(1, references.size());
      ArrayReference reference = references.get(0);
      assertEquals(ClassForReference.class, reference.getRootObject());
      assertEquals("staticA", reference.getRootField());
      assertEquals(4, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(0), reference.getNestedArrayIndices().get(0));
      assertEquals(new Integer(1), reference.getNestedArrayIndices().get(1));
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(2));
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(3));

      ((Object[])staticA[0][1][2])[2] = null; 
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }

   public void testNestedReferencesRemoveAtHigherLevel()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      int[] arr = new int[] {1, 2, 3};
      ClassForReference obj = new ClassForReference();
      Object[][][][] fieldA = new Object[][][][] {null, null, new Object[][][] {null, new Object[][] {null, null, new Object[] {null, null, null, arr}}}}; //2, 1, 2, 3
      obj.fieldA = fieldA;

      List<ArrayReference> references = registry.getArrayOwners(arr);
      
      assertEquals(1, references.size());
      
      ArrayReference reference = references.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertEquals(4, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(0));
      assertEquals(new Integer(1), reference.getNestedArrayIndices().get(1));
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(2));
      assertEquals(new Integer(3), reference.getNestedArrayIndices().get(3));

      fieldA[2][1] = null; 
      references = registry.getArrayOwners(arr);
      assertNull(references);
   }
   
   public void testArrayReferenceFromInvocation()
   {
      ClassForReference obj = new ClassForReference();
      int[] arr = new int[] {1, 2, 3};
      Object[][][][] fieldA = new Object[][][][] {null, null, new Object[][][] {null, new Object[][] {null, null, new Object[] {null, null, null, null}}}}; //2, 1, 2, 3
      obj.fieldA = fieldA;
      TestArrayElementInterceptor.clear();
      fieldA[2][1][2][3] = "X";
      assertEquals(1, TestArrayElementInterceptor.owners.size());
      
      ArrayReference reference = TestArrayElementInterceptor.owners.get(0);
      assertEquals(obj, reference.getRootObject());
      assertEquals("fieldA", reference.getRootField());
      assertEquals(3, reference.getNestedArrayIndices().size());
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(0));
      assertEquals(new Integer(1), reference.getNestedArrayIndices().get(1));
      assertEquals(new Integer(2), reference.getNestedArrayIndices().get(2));

      //TODO For an ObjectArrayElementInvocation, where the value is an array belonging to a registered array,
      //should the references be updated at the end of the invocation, or in the weaving as is done at present?
   }
}
