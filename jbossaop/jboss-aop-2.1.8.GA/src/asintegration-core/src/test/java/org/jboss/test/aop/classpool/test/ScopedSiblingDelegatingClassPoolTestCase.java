package org.jboss.test.aop.classpool.test;

import javassist.ClassPool;
import javassist.CtClass;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.classpool.ClassPoolDomain;


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
public class ScopedSiblingDelegatingClassPoolTestCase extends ClassPoolTest
{
   public static Test suite()
   {
      return new TestSuite(ScopedSiblingDelegatingClassPoolTestCase.class);
   }

   public ScopedSiblingDelegatingClassPoolTestCase(String name)
   {
      super(name);
   }

   public void testNoVisibilityBetweenSiblings() throws Exception
   {
      ClassPoolDomain root = createClassPoolDomain("ROOT", null, false);
      ClassPoolDomain domainA = createClassPoolDomain("A", root, false);
      ClassPool poolA = createDelegatingClassPool(domainA, JAR_A);
      ClassPoolDomain domainB = createClassPoolDomain("B", root, false);
      ClassPool poolB = createDelegatingClassPool(domainB, JAR_B);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessNoVisibilityBetweenSiblings(poolA, poolB);
      accessNoVisibilityBetweenSiblings(poolA, poolB);
   }
   
   public void accessNoVisibilityBetweenSiblings(ClassPool poolA, ClassPool poolB) throws Exception
   {
      CtClass a = poolA.get(CLASS_A);
      assertEquals(poolA, a.getClassPool());
      CtClass b = poolB.get(CLASS_B);
      assertEquals(poolB, b.getClassPool());
      
      try
      {
         poolA.get(CLASS_B);
         fail("Should not have found B in poolA");
      }
      catch (Exception e)
      {
      }
      try
      {
         poolB.get(CLASS_A);
         fail("Should not have found A in poolB");
      }
      catch (Exception e)
      {
      }
   }
   
   public void testUsesOwnCopy() throws Exception
   {
      ClassPoolDomain root = createClassPoolDomain("ROOT", null, false);
      ClassPoolDomain domainA = createClassPoolDomain("A", root, false);
      ClassPool poolA = createDelegatingClassPool(domainA, JAR_A, JAR_B);
      ClassPoolDomain domainB = createClassPoolDomain("B", root, false);
      ClassPool poolB = createDelegatingClassPool(domainB, JAR_B, JAR_A);
      
      //The first time we access the pool it will create the classes, second time will use the cache
      accessUsesOwnCopy(poolA, poolB);
      accessUsesOwnCopy(poolA, poolB);
   }
   
   public void accessUsesOwnCopy(ClassPool poolA, ClassPool poolB) throws Exception
   {
      CtClass aa = poolA.get(CLASS_A);
      assertEquals(poolA, aa.getClassPool());
      CtClass ab = poolA.get(CLASS_A);
      assertEquals(poolA, ab.getClassPool());
      
      CtClass ba = poolB.get(CLASS_A);
      assertEquals(poolB, ba.getClassPool());
      CtClass bb = poolB.get(CLASS_B);
      assertEquals(poolB, bb.getClassPool());
   }   
}
