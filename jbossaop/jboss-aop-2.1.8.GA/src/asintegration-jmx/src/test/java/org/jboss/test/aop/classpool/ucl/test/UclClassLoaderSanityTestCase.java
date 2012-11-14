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
package org.jboss.test.aop.classpool.ucl.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This is here to figure out how the UCLs work, and we will duplicate this
 * in the classpool test
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class UclClassLoaderSanityTestCase extends UclClassPoolTest
{
   public UclClassLoaderSanityTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return new TestSuite(UclClassLoaderSanityTestCase.class);
   }


   public void testGlobalScope() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      try
      {
         clA = createGlobalClassLoader(JAR_A_1);
         clB = createGlobalClassLoader(JAR_B_1);
         try
         {
            Class<?> aFromA = clA.loadClass(CLASS_A);
            assertNotNull(aFromA);
            Class<?> bFromA = clA.loadClass(CLASS_B);
            assertNotNull(bFromA);
            Class<?> aFromB = clB.loadClass(CLASS_A);
            Class<?> bFromB = clB.loadClass(CLASS_B);
            assertSame(aFromA, aFromB);
            assertSame(bFromA, bFromB);
            assertSame(clA, aFromA.getClassLoader());
            assertSame(clB, bFromB.getClassLoader());
         }
         finally
         {
            removeClassLoaderFromRepository(clB);
         }
         Class<?> aFromA = clA.loadClass(CLASS_A);
         assertNotNull(aFromA);

         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         removeClassLoaderFromRepository(clA);
      }
      assertCannotLoadClass(getGlobalRepository(), CLASS_A);
   }
   
   public void testChildDomain() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      ClassLoader clC = null;
      try
      {
         clA = createGlobalClassLoader(JAR_A_1);
         clB = createGlobalClassLoader(JAR_B_1);
         clC = createChildClassLoader(JAR_C_1, false);

         Class<?> aFromA = clA.loadClass(CLASS_A);
         assertNotNull(aFromA);
         Class<?> bFromB = clB.loadClass(CLASS_B);
         assertNotNull(bFromB);
         Class<?> cFromC = clC.loadClass(CLASS_C);
         assertNotNull(cFromC);
         Class<?> aFromC = clC.loadClass(CLASS_A);
         assertNotNull(aFromC);
         Class<?> bFromC = clC.loadClass(CLASS_B);
         assertNotNull(bFromC);
         
         assertSame(aFromA, aFromC);
         assertSame(bFromB, bFromC);
         assertSame(clA, aFromA.getClassLoader());
         assertSame(clB, bFromB.getClassLoader());
         assertSame(clC, cFromC.getClassLoader());
         
         assertCannotLoadClass(clA, CLASS_C);
         assertCannotLoadClass(clB, CLASS_C);
      }
      finally
      {
         removeClassLoaderFromRepository(clA);
         removeClassLoaderFromRepository(clB);
      }
   }
   
   public void testSiblingDomains() throws Exception
   {
      ClassLoader clA = createChildClassLoader(JAR_A_1, true);
      ClassLoader clB = createChildClassLoader(JAR_B_1, false);

      Class<?> clazzA = clA.loadClass(CLASS_A);
      assertSame(clA, clazzA.getClassLoader());
      Class<?> clazzB = clB.loadClass(CLASS_B);
      assertSame(clB, clazzB.getClassLoader());
      assertCannotLoadClass(clA, CLASS_B);
      assertCannotLoadClass(clB, CLASS_A);
   }

   public void testChildWithNewClassesInParent() throws Exception
   {
      ClassLoader clGlobal = null;
      ClassLoader clScoped = null;
      try
      {
         clScoped = createChildClassLoader(JAR_B_1, true);
         assertCannotLoadClass(clScoped, CLASS_A);
         
         clGlobal = createGlobalClassLoader(JAR_A_1);
         Class<?> aFromChild = clScoped.loadClass(CLASS_A);
         assertNotNull(aFromChild);
         Class<?> aFromParent = clGlobal.loadClass(CLASS_A);
         assertNotNull(aFromParent);
         assertSame(aFromChild, aFromParent);
         assertSame(clGlobal, aFromParent.getClassLoader());
      }
      finally
      {
         removeClassLoaderFromRepository(clGlobal);
      }
   }
   
   public void testChildOverrideWithParentDelegation() throws Exception
   {
      ClassLoader clGlobal = null;
      try
      {
         clGlobal = createGlobalClassLoader(JAR_A_1);
         ClassLoader clScoped = createChildClassLoader(JAR_A_2, true);
         Class<?> aFromParent = clGlobal.loadClass(CLASS_A);
         assertNotNull(aFromParent);
         Class<?> aFromChild = clScoped.loadClass(CLASS_A);
         assertNotNull(aFromChild);
         assertSame(aFromParent, aFromChild);
         assertSame(clGlobal, aFromParent.getClassLoader());
      }
      finally
      {
         removeClassLoaderFromRepository(clGlobal);
      }
   }

   public void testChildOverrideWithNoParentDelegation() throws Exception
   {
      ClassLoader clGlobal = null;
      try
      {
         clGlobal = createGlobalClassLoader(JAR_A_1);
         ClassLoader clScoped = createChildClassLoader(JAR_A_2, false);
         Class<?> aFromParent = clGlobal.loadClass(CLASS_A);
         assertNotNull(aFromParent);
         Class<?> aFromChild = clScoped.loadClass(CLASS_A);
         assertNotNull(aFromChild);
         assertNotSame(aFromParent, aFromChild);
         assertSame(clGlobal, aFromParent.getClassLoader());
         assertSame(clScoped, aFromChild.getClassLoader());
      }
      finally
      {
         removeClassLoaderFromRepository(clGlobal);
      }
   }
   
   public void testURLChildOfGlobalUcl() throws Exception
   {
      ClassLoader clGlobal = null;
      try
      {
         clGlobal = createGlobalClassLoader(JAR_A_1);
         ClassLoader clChildA = createChildURLClassLoader(clGlobal, JAR_B_1);
         
         Class<?> aFromA = clChildA.loadClass(CLASS_A);
         assertSame(clGlobal, aFromA.getClassLoader());
         Class<?> bFromA = clChildA.loadClass(CLASS_B);
         assertSame(clChildA, bFromA.getClassLoader());
         
         ClassLoader clChildB = createChildURLClassLoader(clGlobal, JAR_A_2);
         Class<?> aFromB = clChildB.loadClass(CLASS_A);
         assertSame(clGlobal, aFromB.getClassLoader());
      }
      finally
      {
         removeClassLoaderFromRepository(clGlobal);
      }
   }
   
   public void testUndeploySibling() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      try
      {
         try
         {
            clA = createGlobalClassLoader(JAR_A_1);
            assertCannotLoadClass(clA, CLASS_B);
            
            clB = createGlobalClassLoader(JAR_B_1);
            Class<?> bFromA = clA.loadClass(CLASS_B);
            assertSame(clB, bFromA.getClassLoader());
         }
         finally
         {
            removeClassLoaderFromRepository(clB);
         }
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         removeClassLoaderFromRepository(clA);
      }
   }

   
//   public void testUndeployParentDomainClassLoader() throws Exception
//   {
//      ClassLoader globalA = null;
//      ClassLoader globalB = null;
//      ClassLoader child = null;
//      try
//      {
//         try
//         {
//            globalA = createGlobalClassLoader(JAR_A_1);
//            assertCannotLoadClass(globalA, CLASS_B);
//            
//            child = createChildClassLoader(JAR_C_1, true);
//            assertCannotLoadClass(child, CLASS_B);
//            
//            globalB = createGlobalClassLoader(JAR_B_1);
//            Class<?> bFromChild = child.loadClass(CLASS_B);
//            Class<?> bFromA = globalA.loadClass(CLASS_B);
//            assertSame(globalB, bFromA.getClassLoader());
//            assertSame(bFromA, bFromChild);
//         }
//         finally
//         {
//            removeClassLoaderFromRepository(globalB);
//         }
//         assertCannotLoadClass(child, CLASS_B);
//      }
//      finally
//      {
//         removeClassLoaderFromRepository(globalA);
//      }
//   }
   
   public void testUclWithParentClassLoader() throws Exception
   {
      ClassLoader parent = createChildURLClassLoader(null, JAR_B_1);
      ClassLoader global = null;
      try
      {
         global = createGlobalClassLoaderWithParent(JAR_A_1, parent);
         Class<?> aFromGlobal = global.loadClass(CLASS_A);
         assertSame(global, aFromGlobal.getClassLoader());
         Class<?> bFromGlobal = global.loadClass(CLASS_B);
         assertSame(parent, bFromGlobal.getClassLoader());
         Class<?> bFromParent = parent.loadClass(CLASS_B);
         assertSame(bFromGlobal, bFromParent);
      }
      finally
      {
         removeClassLoaderFromRepository(global);
      }
   }

   
   public void testUclWithParentClassLoaderAndSameClassInDomain() throws Exception
   {
      ClassLoader parent = createChildURLClassLoader(null, JAR_B_1);
      ClassLoader globalA = null;
      ClassLoader globalB = null;
      try
      {
         globalA = createGlobalClassLoaderWithParent(JAR_A_1, parent);
         Class<?> aFromGlobal = globalA.loadClass(CLASS_A);
         assertSame(globalA, aFromGlobal.getClassLoader());

         globalB = createGlobalClassLoader(JAR_B_2);
         Class<?> bFromGlobalA = globalA.loadClass(CLASS_B);
         assertSame(globalB, bFromGlobalA.getClassLoader());
         Class<?> bFromParent = parent.loadClass(CLASS_B);
         assertSame(parent, bFromParent.getClassLoader());
         assertNotSame(bFromGlobalA, bFromParent);
      }
      finally
      {
         removeClassLoaderFromRepository(globalA);
         removeClassLoaderFromRepository(globalB);
      }
   }
   
   public void testUclLoaderOrdering() throws Exception
   {
      ClassLoader globalA = null;
      ClassLoader globalB = null;
      ClassLoader globalC = null;
      try
      {
         globalA = createGlobalClassLoader(JAR_A_1);
         globalB = createGlobalClassLoader(JAR_A_1);
         globalC = createGlobalClassLoader(JAR_A_1);
         
         Class<?> aFromA = globalA.loadClass(CLASS_A);
         Class<?> aFromB = globalB.loadClass(CLASS_A);
         Class<?> aFromC = globalC.loadClass(CLASS_A);
         assertSame(aFromA, aFromB);
         assertSame(aFromA, aFromC);
         assertSame(globalA, aFromA.getClassLoader());
      }
      finally
      {
         removeClassLoaderFromRepository(globalA);
         removeClassLoaderFromRepository(globalB);
         removeClassLoaderFromRepository(globalC);
      }
   }
}
