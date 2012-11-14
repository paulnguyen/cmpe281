package org.jboss.test.aop.classpool.test;

import javassist.ClassPool;
import javassist.CtClass;

import org.jboss.aop.classpool.BaseClassPool;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.instrument.TransformerCommon;

import junit.framework.Test;
import junit.framework.TestSuite;


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
public class ParentLastDelegatingClassPoolTestCase extends ClassPoolTest
{
   public static Test suite()
   {
      return new TestSuite(ParentLastDelegatingClassPoolTestCase.class);
   }

   public ParentLastDelegatingClassPoolTestCase(String name)
   {
      super(name);
   }

   public void testChildInParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInParent(parentPool, childPool);
      accessChildInParent(parentPool, childPool);
   }

   private void accessChildInParent(ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(childPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(parentPool, a2.getClassPool());
   }

   public void testChildInGrandParent() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, false);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInGrandParent(grandParentPool, parentPool, childPool);
      accessChildInGrandParent(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInGrandParent(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(childPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      
      CtClass a3 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
   }
   
   public void testChildInGrandParentFromTop() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, false);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInGrandParentFromTop(grandParentPool, parentPool, childPool);
      accessChildInGrandParentFromTop(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInGrandParentFromTop(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());

      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      
      CtClass a3 = childPool.get(CLASS_A);
      assertEquals(childPool, a3.getClassPool());      
   }
   
   public void testChildInSibling() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain childA = createClassPoolDomain("CHILDA", parent, false);
      ClassPool childPoolA = createDelegatingClassPool(childA, JAR_A);
      ClassPoolDomain childB = createClassPoolDomain("CHILDB", parent, false);
      ClassPool childPoolB = createDelegatingClassPool(childB, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInSibling(parentPool, childPoolA, childPoolB);
      accessChildInSibling(parentPool, childPoolA, childPoolB);
   }
   
   private void accessChildInSibling(ClassPool parentPool, ClassPool childPoolA, ClassPool childPoolB) throws Exception
   {
      CtClass a1 = childPoolA.get(CLASS_A);
      assertEquals(childPoolA, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(parentPool, a2.getClassPool());

      CtClass a3 = childPoolB.get(CLASS_A);
      assertEquals(childPoolB, a3.getClassPool());
   }

   public void testClassInParentOnly() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
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
   }

   public void testClassInGrandParentOnly() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, false);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInGrandParentOnly(grandParentPool, parentPool, childPool);
      accessChildInGrandParentOnly(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInGrandParentOnly(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      
      CtClass a3 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
   }

   public void testClassInGrandParentOnlyFromTop() throws Exception
   {
      ClassPoolDomain grandParent = createClassPoolDomain("GRANDPARENT", null, false);
      ClassPool grandParentPool = createDelegatingClassPool(grandParent, JAR_A);
      ClassPoolDomain parent = createClassPoolDomain("PARENT", grandParent, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_B);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessChildInGrandParentOnlyFromTop(grandParentPool, parentPool, childPool);
      accessChildInGrandParentOnlyFromTop(grandParentPool, parentPool, childPool);
   }
   
   private void accessChildInGrandParentOnlyFromTop(ClassPool grandParentPool, ClassPool parentPool, ClassPool childPool) throws Exception
   {
      CtClass a1 = grandParentPool.get(CLASS_A);
      assertEquals(grandParentPool, a1.getClassPool());
      
      CtClass a2 = parentPool.get(CLASS_A);
      assertEquals(grandParentPool, a2.getClassPool());
      
      CtClass a3 = childPool.get(CLASS_A);
      assertEquals(grandParentPool, a3.getClassPool());
   }
   
   public void testGenerateSameClassInChildAndParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      CtClass parentClazz = TransformerCommon.makeClass(parentPool, "test.Test");
      assertSame(parentClazz, parentPool.get("test.Test"));
      assertSame(parentPool, parentClazz.getClassPool());
      Class<?> parentClass = parentClazz.toClass();
      assertSame(parentPool.getClassLoader(), parentClass.getClassLoader());
      
      CtClass childClazz = TransformerCommon.makeClass(childPool, "test.Test"); 
      assertSame(childClazz, childPool.get("test.Test"));
      assertSame(childPool, childClazz.getClassPool());
      Class<?> childClass = childClazz.toClass();
      assertSame(childPool.getClassLoader(), childClass.getClassLoader());
   }

   public void testGenerateSameNestedClassInChildAndParent() throws Exception
   {
      ClassPoolDomain parent = createClassPoolDomain("PARENT", null, false);
      ClassPool parentPool = createDelegatingClassPool(parent, JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", parent, false);
      ClassPool childPool = createDelegatingClassPool(child, JAR_A);
      CtClass parentA = parentPool.get(CLASS_A);
      CtClass parentClazz = TransformerCommon.makeNestedClass(parentA, "Test", true);
      assertSame(parentPool, parentClazz.getClassPool());
      Class<?> parentClass = parentClazz.toClass();
      assertSame(parentPool.getClassLoader(), parentClass.getClassLoader());
      Class<?> parentAClass = parentA.toClass();
      assertSame(parentPool.getClassLoader(), parentAClass.getClassLoader());
      
      CtClass childA = childPool.get(CLASS_A);
      CtClass childClazz = TransformerCommon.makeNestedClass(childA, "Test", true); 
      assertSame(childPool, childClazz.getClassPool());
      Class<?> childClass = childClazz.toClass();
      assertSame(childPool.getClassLoader(), childClass.getClassLoader());
      Class<?> childAClass = childA.toClass();
      assertSame(childPool.getClassLoader(), childAClass.getClassLoader());
   }

   public void testNonDelegatingClassPoolAsParent() throws Exception
   {
      ClassPoolDomain root = createNonDelegatingClassPoolDomain(JAR_A);
      ClassPoolDomain child = createClassPoolDomain("CHILD", root, false);
      ClassPool childPoolB = createDelegatingClassPool(child, JAR_B);
      
      CtClass b = childPoolB.get(CLASS_B);
      assertSame(childPoolB, b.getClassPool());
      assertSame(b, childPoolB.get(CLASS_B));
      
      CtClass a = childPoolB.get(CLASS_A);
      assertNotSame(childPoolB, a.getClassPool());
      assertSame(a, childPoolB.get(CLASS_A));
      
      ClassPool childPoolA = createDelegatingClassPool(child, JAR_A);
      CtClass a1 = childPoolA.get(CLASS_A);
      assertNotSame(a, a1);
      assertSame(a1, childPoolA.get(CLASS_A));
      
      ((BaseClassPool)childPoolA).close();
      assertSame(a, childPoolB.get(CLASS_A));
   }
}
