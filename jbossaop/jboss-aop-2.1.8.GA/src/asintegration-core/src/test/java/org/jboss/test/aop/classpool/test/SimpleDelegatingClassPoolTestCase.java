package org.jboss.test.aop.classpool.test;

import javassist.ClassPool;
import javassist.CtClass;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.NonDelegatingClassPool;


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
public class SimpleDelegatingClassPoolTestCase extends ClassPoolTest
{
   public static Test suite()
   {
      return new TestSuite(SimpleDelegatingClassPoolTestCase.class);
   }

   public SimpleDelegatingClassPoolTestCase(String name)
   {
      super(name);
   }

   public void testAllClassesOnePool() throws Exception
   {
      ClassPoolDomain domain = createClassPoolDomain("SIMPLE", null, false);
      ClassPool pool = createDelegatingClassPool(domain, JAR_A, JAR_B);

      //The first time we access the pool it will create the classes, second time will use the cache
      accessAllClassesOnePool(pool);
      accessAllClassesOnePool(pool);
   }
   
   private void accessAllClassesOnePool(ClassPool pool) throws Exception
   {
      CtClass a = pool.get(CLASS_A);
      CtClass b = pool.get(CLASS_B);
      assertEquals(pool, a.getClassPool());
      assertEquals(pool, b.getClassPool());
      
      CtClass string = pool.get(String.class.getName());
      assertNotSame("java.lang.String should be loaded by the parent pool", pool, string.getClassPool());
      assertEquals(ClassPool.getDefault(), string.getClassPool());
   }
   
   public void testOnePoolPerClassLoadedByA() throws Exception
   {
      ClassPoolDomain domain = createClassPoolDomain("SIMPLE", null, false);
      ClassPool poolA = createDelegatingClassPool(domain, JAR_A);
      ClassPool poolB = createDelegatingClassPool(domain, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessOnePoolPerClassLoadedByA(poolA, poolB);
      accessOnePoolPerClassLoadedByA(poolA, poolB);
   }
   
   private  void accessOnePoolPerClassLoadedByA(ClassPool poolA, ClassPool poolB) throws Exception
   {
      CtClass a = poolA.get(CLASS_A);
      CtClass b = poolA.get(CLASS_B);

      assertNotSame(a.getClassPool(), b.getClassPool());
      assertEquals(poolA, a.getClassPool());
      assertEquals(poolB, b.getClassPool());
   }
   
   public void testOnePoolPerClassLoadedByB() throws Exception
   {
      ClassPoolDomain domain = createClassPoolDomain("SIMPLE", null, false);
      ClassPool poolA = createDelegatingClassPool(domain, JAR_A);
      ClassPool poolB = createDelegatingClassPool(domain, JAR_B);

      //The first time we access the pool it will create the classes, second time will use the cache
      accessOnePoolPerClassLoadedByB(poolA, poolB);
      accessOnePoolPerClassLoadedByB(poolA, poolB);
}
   
   public void accessOnePoolPerClassLoadedByB(ClassPool poolA, ClassPool poolB) throws Exception
   {
      CtClass a = poolB.get(CLASS_A);
      CtClass b = poolB.get(CLASS_B);
      
      assertNotSame(a.getClassPool(), b.getClassPool());
      assertEquals(poolA, a.getClassPool());
      assertEquals(poolB, b.getClassPool());
   }
   
   public void testCanLoadArrrayCtClass() throws Exception
   {
      ClassPoolDomain domain = createClassPoolDomain("SIMPLE", null, false);
      ClassPool poolA = createDelegatingClassPool(domain, JAR_A);
      ClassPool poolB = createDelegatingClassPool(domain, JAR_B);
      accessCanLoadCtArray(poolA, poolB);
      accessCanLoadCtArray(poolA, poolB);
   }
   
   private void accessCanLoadCtArray(ClassPool poolA, ClassPool poolB) throws Exception
   {
      poolA.get(String.class.getName() + "[][]");
      CtClass a = poolA.get(CLASS_A);
      CtClass aArray = poolA.get(CLASS_A + "[]");
      CtClass bArray = poolA.get(CLASS_B + "[][]");
      CtClass b = poolA.get(CLASS_B);
      
      CtClass byteClassA = poolA.get("byte");
      CtClass byteClassB = poolB.get("byte");
      assertSame(byteClassA, byteClassB);
      assertNotSame(poolA, byteClassA.getClassPool());
      assertNotSame(poolB, byteClassA.getClassPool());
      CtClass intArrayClass = poolA.get("int[]");
      CtClass intClass = poolB.get("int");
      assertSame(intClass, intArrayClass.getComponentType());
      assertSame(byteClassA.getClassPool(), intClass.getClassPool());
      
      
      
      assertTrue(aArray.isArray());
      assertSame(a, aArray.getComponentType());
      assertTrue(bArray.isArray());
      assertTrue(bArray.getComponentType().isArray());
      assertSame(b, bArray.getComponentType().getComponentType());
      assertNotSame(aArray.getClassPool(), bArray.getClassPool());
      assertSame(poolA, aArray.getClassPool());
      assertSame(poolB, bArray.getClassPool());
      assertSame(a.getClassPool(), aArray.getClassPool());
      assertSame(b.getClassPool(), bArray.getClassPool());
   }
}
