package org.jboss.test.aop.classpool.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.classpool.BaseClassPool;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.instrument.TransformerCommon;


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

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ParentFirstDelegatingClassPoolTestCase extends ClassPoolTest
{
   public static Test suite()
   {
      return new TestSuite(ParentFirstDelegatingClassPoolTestCase.class);
   }

   public ParentFirstDelegatingClassPoolTestCase(String name)
   {
      super(name);
   }

   public void testClassInParentOnly() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessClassInParentOnly(parentPool, childPool);
      accessClassInParentOnly(parentPool, childPool);
   }
   
   private void accessClassInParentOnly(ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(parentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(parentPool, a2.getClassPool());
      assertEquals(a1, a2);
      
      CtClass b = childPool.get(CLASS_B);
      assertEquals(childPool, b.getClassPool());
      try
      {
         parentPool.get(CLASS_B);
         fail("Should not have been found");
      }
      catch(NotFoundException e)
      {
      }
      
      CtClass string = childPool.get(String.class.getName());
      assertNotSame("java.lang.String should be loaded by the parent pool", childPool, string.getClassPool());
      assertEquals(ClassPool.getDefault(), string.getClassPool());
   }
   
   public void testClassInGrandParentOnly() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, true);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessClassInGrandParentOnly(grandParentPool, parentPool, childPool);
      accessClassInGrandParentOnly(grandParentPool, parentPool, childPool);
   }
   
   private void accessClassInGrandParentOnly(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      assertEquals(a1, a2);
      
      CtClass a3 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());

      assertEquals(a2, a3);
      
      
      CtClass b1 = childPool.get(CLASS_B);
      assertEquals(parentPool, b1.getClassPool());
      CtClass b2 = childPool.get(CLASS_B);
      assertEquals(parentPool, b2.getClassPool());
      assertEquals(b1, b2);
      
      try
      {
         grandParentPool.get(CLASS_B);
         fail("Should not have been found");
      }
      catch(NotFoundException e)
      {
      }
   }
   
   public void testChildNotInParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);

      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildNotInParent(parentPool, childPool);
      accessChildNotInParent(parentPool, childPool);
   }
   
   private void accessChildNotInParent(ClassPool parentPool, ClassPool childPool) throws Exception
   {
      try
      {
         parentPool.get(CLASS_A);
         fail("Should not have been found");
      }
      catch(NotFoundException e)
      {
      }
      CtClass a = childPool.get(CLASS_A);
      assertEquals(childPool, a.getClassPool());
      
   }
   
   public void testChildInParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInParent(parentPool, childPool);
      accessChildInParent(parentPool, childPool);
   }
   
   private void accessChildInParent(ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(parentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(parentPool, a2.getClassPool());
      
      assertSame(a1, a2);
   }
   
   public void testChildInParentAndGrandParent() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, true);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInParentAndGrandParent(grandParentPool, parentPool, childPool);
      accessChildInParentAndGrandParent(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInParentAndGrandParent(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      assertSame(a1, a2);

      CtClass a3 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
      assertSame(a2, a3);
   }

   public void testChildInParentAndGrandParentFromTop() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, true);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInParentAndGrandParentFromTop(grandParentPool, parentPool, childPool);
      accessChildInParentAndGrandParentFromTop(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInParentAndGrandParentFromTop(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      assertSame(a1, a2);

      CtClass a3 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
      assertSame(a2, a3);
   }

   public void testChildInGrandParent() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, true);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, true);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInGrandParent(grandParentPool, parentPool, childPool);
      accessChildInGrandParent(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInGrandParent(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      assertSame(a1, a2);

      CtClass a3 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
      assertSame(a2, a3);
   }
   
   public void testGenerateSameNestedClassInChildAndParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, true);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      CtClass parentA = parentPool.get(CLASS_A);
      CtClass parentClazz = TransformerCommon.makeNestedClass(parentA, "Test", true);
      assertSame(parentPool, parentClazz.getClassPool());
      Class<?> parentClass = parentClazz.toClass();
      assertSame(parentPool.getClassLoader(), parentClass.getClassLoader());
      Class<?> parentAClass = parentA.toClass();
      assertSame(parentPool.getClassLoader(), parentAClass.getClassLoader());
      
      CtClass childA = childPool.get(CLASS_A);
      try
      {
         TransformerCommon.makeNestedClass(childA, "Test", true);
      }
      catch(Exception e)
      {
         
      }
      assertSame(parentA, childA);
      assertSame(parentClazz, childPool.get(CLASS_A + "$Test"));
   }
   
   public void testNonDelegatingClassPoolAsParent() throws Exception
   {
      ClassPoolDomain root = createNonDelegatingClassPoolDomain(JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", root, true);
      ClassPool childPoolB = createDelegatingClassPool(child, JAR_B);
      
      CtClass b = childPoolB.get(CLASS_B);
      assertSame(childPoolB, b.getClassPool());
      assertSame(b, childPoolB.get(CLASS_B));
      
      CtClass a = childPoolB.get(CLASS_A);
      assertNotSame(childPoolB, a.getClassPool());
      assertSame(a, childPoolB.get(CLASS_A));
      
      ClassPool childPoolA = createDelegatingClassPool(child, JAR_A);
      assertSame(a, childPoolA.get(CLASS_A));
      assertSame(a, childPoolB.get(CLASS_A));
      
      ((BaseClassPool)childPoolA).close();
      assertSame(a, childPoolB.get(CLASS_A));
   }
}
