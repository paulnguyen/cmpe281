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
package org.jboss.test.aop.classpool.jbosscl.test;

import junit.framework.Test;

import org.jboss.classloader.spi.ClassLoaderDomain;

/**
 * Tests the behaviour of the new classloaders
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoaderWithRepositorySanityTestCase extends JBossClClassPoolTest
{

   public ClassLoaderWithRepositorySanityTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassLoaderWithRepositorySanityTestCase.class);
   }

   public void testGlobalScope() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      try
      {
         clA = createClassLoader("A", true, JAR_A_1);
         assertModule(clA);
         clB = createClassLoader("B", true, JAR_B_1);
         assertModule(clB);
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
            unregisterClassLoader(clB);
         }
         Class<?> aFromA = clA.loadClass(CLASS_A);
         assertNotNull(aFromA);

         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertCannotLoadClass(getDefaultDomain(), CLASS_A);
   }

   public void testChildDomain() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      ClassLoaderDomain childDomain = null;
      ClassLoader clC = null;
      try
      {
         clA = createClassLoader("A", true, JAR_A_1);
         assertModule(clA);
         clB = createClassLoader("B", true, JAR_B_2);
         assertModule(clB);
         clC = createChildDomainParentFirstClassLoader("C", "CHILD", true, JAR_C_1);
         assertModule(clC);
         
         childDomain = getChildDomainForLoader(clC);
         assertNotNull(childDomain);
         assertSame(getSystem().getDefaultDomain(), childDomain.getParent());

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
         unregisterClassLoader(clA);
         unregisterClassLoader(clB);
         unregisterClassLoader(clC);
         unregisterDomain(childDomain);
      }
   }
   
   public void testSiblingDomains() throws Exception
   {
      ClassLoader clA = null;
      ClassLoader clB = null;
      ClassLoaderDomain domainA = null;
      ClassLoaderDomain domainB = null;
      try
      {
         clA = createChildDomainParentFirstClassLoader("A", "ChildA", true, JAR_A_1);
         assertModule(clA);
         clB = createChildDomainParentLastClassLoader("B", "ChildB", true, JAR_B_1);
         assertModule(clB);

         domainA = getChildDomainForLoader(clA);
         assertNotNull(domainA);
         assertSame(getSystem().getDefaultDomain(), domainA.getParent());
         domainB = getChildDomainForLoader(clB);
         assertNotNull(domainB);
         assertSame(getSystem().getDefaultDomain(), domainB.getParent());
         assertNotSame(domainA, domainB);

         Class<?> clazzA = clA.loadClass(CLASS_A);
         assertSame(clA, clazzA.getClassLoader());
         Class<?> clazzB = clB.loadClass(CLASS_B);
         assertSame(clB, clazzB.getClassLoader());
         assertCannotLoadClass(clA, CLASS_B);
         assertCannotLoadClass(clB, CLASS_A);
      }
      finally
      {
         unregisterClassLoader(clA);
         unregisterClassLoader(clB);
         unregisterDomain(clA);
         unregisterDomain(clB);
      }
   }
   
   public void testChildWithNewClassesInParent() throws Exception
   {
      ClassLoader clGlobal = null;
      ClassLoader clScoped = null;
      try
      {
         clScoped = createChildDomainParentFirstClassLoader("SCOPED", "SCOPED", true, JAR_B_1);
         assertModule(clScoped);
         assertCannotLoadClass(clScoped, CLASS_A);
         
         clGlobal = createClassLoader("GLOBAL", true, JAR_A_1);
         assertModule(clScoped);
         assertModule(clGlobal);

         Class<?> aFromChild = clScoped.loadClass(CLASS_A);
         assertNotNull(aFromChild);
         Class<?> aFromParent = clGlobal.loadClass(CLASS_A);
         assertNotNull(aFromParent);
         assertSame(aFromChild, aFromParent);
         assertSame(clGlobal, aFromParent.getClassLoader());
      }
      finally
      {
         unregisterClassLoader(clGlobal);
         unregisterClassLoader(clScoped);
         unregisterDomain(clScoped);
      }
   }
   
   public void testChildOverrideWithParentDelegation() throws Exception
   {
      ClassLoader clGlobal = null;
      ClassLoader clScoped = null;
      try
      {
         clGlobal = createClassLoader("GLOBAL", true, JAR_A_1);
         clScoped = createChildDomainParentFirstClassLoader("SCOPED", "SCOPED", true, JAR_B_1);
         Class<?> aFromParent = clGlobal.loadClass(CLASS_A);
         assertNotNull(aFromParent);
         Class<?> aFromChild = clScoped.loadClass(CLASS_A);
         assertNotNull(aFromChild);
         assertSame(aFromParent, aFromChild);
         assertSame(clGlobal, aFromParent.getClassLoader());
      }
      finally
      {
         unregisterClassLoader(clGlobal);
         unregisterClassLoader(clScoped);
         unregisterDomain(clScoped);
      }
   }
   
   public void testChildOverrideWithNoParentDelegation() throws Exception
   {
      ClassLoader clGlobal = null;
      ClassLoader clScoped = null;
      try
      {
         clGlobal = createClassLoader("GLOBAL", true, JAR_A_1);
         clScoped = createChildDomainParentLastClassLoader("CHILD", "CHILD", true, JAR_A_1);
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
         unregisterClassLoader(clGlobal);
         unregisterClassLoader(clScoped);
         unregisterDomain(clScoped);
      }
   }
   
   public void testURLChildOfGlobalUcl() throws Exception
   {
      ClassLoader clGlobal = null;
      ClassLoader clChildA = null;
      ClassLoader clChildB = null;
      try
      {
         clGlobal = createClassLoader("GLOBAL", true, JAR_A_1);
         clChildA = createChildURLClassLoader(clGlobal, JAR_B_1);
         
         Class<?> aFromA = clChildA.loadClass(CLASS_A);
         assertSame(clGlobal, aFromA.getClassLoader());
         Class<?> bFromA = clChildA.loadClass(CLASS_B);
         assertSame(clChildA, bFromA.getClassLoader());
         
         clChildB = createChildURLClassLoader(clGlobal, JAR_A_2);
         Class<?> aFromB = clChildB.loadClass(CLASS_A);
         assertSame(clGlobal, aFromB.getClassLoader());
      }
      finally
      {
         unregisterClassLoader(clGlobal);
         unregisterClassLoader(clChildA);
         unregisterClassLoader(clChildB);
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
            clA = createClassLoader("A", true, JAR_A_1);
            assertCannotLoadClass(clA, CLASS_B);
            
            clB = createClassLoader("B", true, JAR_B_1);
            Class<?> bFromA = clA.loadClass(CLASS_B);
            assertSame(clB, bFromA.getClassLoader());
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
   }

   
   public void testUndeployParentDomainClassLoader() throws Exception
   {
      ClassLoader globalA = null;
      ClassLoader globalB = null;
      ClassLoader child = null;
      try
      {
         try
         {
            globalA = createClassLoader("A", true, JAR_A_1);
            assertCannotLoadClass(globalA, CLASS_B);
            
            child = createChildDomainParentLastClassLoader("C", "C", true, JAR_C_1);
            assertCannotLoadClass(child, CLASS_B);
            
            globalB = createClassLoader("B", true, JAR_B_1);
            Class<?> bFromChild = child.loadClass(CLASS_B);
            Class<?> bFromA = globalA.loadClass(CLASS_B);
            assertSame(globalB, bFromA.getClassLoader());
            assertSame(bFromA, bFromChild);
         }
         finally
         {
            unregisterClassLoader(globalB);
         }
         assertCannotLoadClass(child, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(globalA);
         unregisterClassLoader(child);
         unregisterDomain(getChildDomainForLoader(child));
      }
   }
   
   public void testClassLoaderlWithParentClassLoader() throws Exception
   {
      ClassLoader parent = createChildURLClassLoader(null, JAR_B_1);
      ClassLoader global = null;
      try
      {
         global = createChildDomainParentFirstClassLoader("A", "A", true, parent, JAR_A_1);
         Class<?> aFromGlobal = global.loadClass(CLASS_A);
         assertSame(global, aFromGlobal.getClassLoader());
         Class<?> bFromGlobal = global.loadClass(CLASS_B);
         assertSame(parent, bFromGlobal.getClassLoader());
         Class<?> bFromParent = parent.loadClass(CLASS_B);
         assertSame(bFromGlobal, bFromParent);
      }
      finally
      {
         unregisterClassLoader(global);
         unregisterClassLoader(parent);
         unregisterDomain(global);
      }
   }

   
   public void testClassLoaderWithParentClassLoaderAndSameClassInDomain() throws Exception
   {
      ClassLoader parent = createChildURLClassLoader(null, JAR_B_1);
      ClassLoader globalA = null;
      ClassLoader globalB = null;
      ClassLoader globalC = null;
      try
      {
         final String domain = "CHILD";
         globalA = createChildDomainParentFirstClassLoader("A", domain, true, parent, JAR_A_1);
         Class<?> aFromGlobal = globalA.loadClass(CLASS_A);
         assertSame(globalA, aFromGlobal.getClassLoader());
         Class<?> bFromGlobalA = globalA.loadClass(CLASS_B);
         assertSame(parent, bFromGlobalA.getClassLoader());

         globalB = createChildDomainParentFirstClassLoader("B", domain, true, parent, JAR_B_2);
         Class<?> bFromParent = parent.loadClass(CLASS_B);
         assertSame(parent, bFromParent.getClassLoader());
         assertSame(bFromGlobalA, bFromParent);
         
         Class<?> bFromGlobalB = globalB.loadClass(CLASS_B);
         assertSame(bFromGlobalB, bFromParent);
         
         globalC = createChildDomainParentLastClassLoader("C", domain + "2", true, parent, JAR_B_2);
         assertLoadClass(CLASS_B, globalC);
      }
      finally
      {
         unregisterClassLoader(globalA);
         unregisterClassLoader(globalB);
         unregisterClassLoader(globalC);
         unregisterClassLoader(parent);
         unregisterDomain(globalA);
         unregisterDomain(globalC);
      }
   }

   public void testSeveralLevelsOfDomain() throws Exception
   {
      ClassLoader parent = null;
      ClassLoader cl1B = null;
      ClassLoader cl1C = null;
      ClassLoader cl2B = null;
      ClassLoader cl2C = null;
      ClassLoader cl11A = null;
      ClassLoader cl11B = null;
      ClassLoader cl11C = null;
      ClassLoader cl12A = null;
      ClassLoader cl12B = null;
      ClassLoader cl12C = null;
      
      try
      {
         parent = createClassLoader("A", true, JAR_A_1);
         Class<?> aFromParent = parent.loadClass(CLASS_A);
         assertSame(parent, aFromParent.getClassLoader());
         
         final String domain1 = "1";
         cl1B = createChildDomainParentFirstClassLoader("1B", domain1, true, JAR_B_1);
         cl1C =  createChildDomainParentFirstClassLoader("1C", domain1, true, JAR_C_1);
         Class<?> aFrom1B = cl1B.loadClass(CLASS_A);
         Class<?> bFrom1B = cl1B.loadClass(CLASS_B);
         Class<?> cFrom1B = cl1B.loadClass(CLASS_C);
         Class<?> aFrom1C = cl1C.loadClass(CLASS_A);
         Class<?> bFrom1C = cl1C.loadClass(CLASS_B);
         Class<?> cFrom1C = cl1C.loadClass(CLASS_C);
         assertSame(aFromParent, aFrom1B);
         assertSame(aFromParent, aFrom1C);
         assertSame(bFrom1B, bFrom1C);
         assertSame(cFrom1B, cFrom1C);
         assertSame(cl1B, bFrom1B.getClassLoader());
         assertSame(cl1C, cFrom1B.getClassLoader());
         
         
         final String domain2 = "2";
         cl2B = createChildDomainParentFirstClassLoader("2B", domain2, true, JAR_B_1);
         cl2C = createChildDomainParentFirstClassLoader("2C", domain2, true, JAR_C_1);
         Class<?> aFrom2B = cl2B.loadClass(CLASS_A);
         Class<?> bFrom2B = cl2B.loadClass(CLASS_B);
         Class<?> cFrom2B = cl2B.loadClass(CLASS_C);
         Class<?> aFrom2C = cl2C.loadClass(CLASS_A);
         Class<?> bFrom2C = cl2C.loadClass(CLASS_B);
         Class<?> cFrom2C = cl2C.loadClass(CLASS_C);
         assertSame(aFromParent, aFrom2B);
         assertSame(aFromParent, aFrom2C);
         assertSame(bFrom2B, bFrom2C);
         assertSame(cFrom2B, cFrom2C);
         assertSame(cl2B, bFrom2B.getClassLoader());
         assertSame(cl2C, cFrom2B.getClassLoader());
         assertNotSame(bFrom1B, bFrom2B);
         assertNotSame(bFrom2C, bFrom1C);
         assertNotSame(cFrom2C, cFrom1C);
         
         final String domain11 = "11";
         cl11A = createChildDomainParentFirstClassLoader("11A", domain11, domain1, true, JAR_A_2);
         cl11B = createChildDomainParentFirstClassLoader("11B", domain11, domain1, true, JAR_B_2);
         cl11C = createChildDomainParentFirstClassLoader("11C", domain11, domain1, true, JAR_C_2);
         final String domain12 = "12";
         cl12A = createChildDomainParentLastClassLoader("12A", domain12, domain1, true, JAR_A_2);
         cl12B = createChildDomainParentLastClassLoader("12B", domain12, domain1, true, JAR_B_2);
         cl12C = createChildDomainParentLastClassLoader("12C", domain12, domain1, true, JAR_C_2);
         Class<?> aFrom11A = cl11A.loadClass(CLASS_A);
         Class<?> aFrom11B = cl11B.loadClass(CLASS_A);
         Class<?> aFrom11C = cl11C.loadClass(CLASS_A);
         assertSame(aFromParent, aFrom11A);
         assertSame(aFromParent, aFrom11B);
         assertSame(aFromParent, aFrom11C);
         Class<?> aFrom12A = cl12A.loadClass(CLASS_A);
         Class<?> aFrom12B = cl12B.loadClass(CLASS_A);
         Class<?> aFrom12C = cl12C.loadClass(CLASS_A);
         assertNotSame(aFromParent, aFrom12A);
         assertSame(aFrom12A, aFrom12B);
         assertSame(aFrom12A, aFrom12C);
         assertSame(cl12A, aFrom12A.getClassLoader());
         
         Class<?> bFrom11A = cl11A.loadClass(CLASS_B);
         Class<?> bFrom11B = cl11B.loadClass(CLASS_B);
         Class<?> bFrom11C = cl11C.loadClass(CLASS_B);
         Class<?> cFrom11A = cl11A.loadClass(CLASS_C);
         Class<?> cFrom11B = cl11B.loadClass(CLASS_C);
         Class<?> cFrom11C = cl11C.loadClass(CLASS_C);
         assertSame(bFrom11A, bFrom11B);
         assertSame(bFrom11A, bFrom11C);
         assertSame(cl1B, bFrom11B.getClassLoader());
         assertSame(cFrom11A, cFrom11B);
         assertSame(cFrom11A, cFrom11C);
         assertSame(cl1C, cFrom11C.getClassLoader());
         
         Class<?> bFrom12A = cl12A.loadClass(CLASS_B);
         Class<?> bFrom12B = cl12B.loadClass(CLASS_B);
         Class<?> bFrom12C = cl12C.loadClass(CLASS_B);
         Class<?> cFrom12A = cl12A.loadClass(CLASS_C);
         Class<?> cFrom12B = cl12B.loadClass(CLASS_C);
         Class<?> cFrom12C = cl12C.loadClass(CLASS_C);
         assertSame(bFrom12A, bFrom12B);
         assertSame(bFrom12A, bFrom12C);
         assertSame(cl12B, bFrom12B.getClassLoader());
         assertSame(cFrom12A, cFrom12B);
         assertSame(cFrom12A, cFrom12C);
         assertSame(cl12C, cFrom12C.getClassLoader());
         assertNotSame(bFrom11B, bFrom12B);
         assertNotSame(cFrom11C, cFrom12C);
      }
      finally
      {
         unregisterClassLoader(parent);
         unregisterClassLoader(cl1B);
         unregisterClassLoader(cl1C);
         unregisterClassLoader(cl2B);
         unregisterClassLoader(cl2C);
         unregisterClassLoader(cl11A);
         unregisterClassLoader(cl11B);
         unregisterClassLoader(cl11C);
         unregisterClassLoader(cl12A);
         unregisterClassLoader(cl12B);
         unregisterClassLoader(cl12C);
         
         unregisterDomain(cl12A);
         unregisterDomain(cl11A);
         unregisterDomain(cl2B);
         unregisterDomain(cl1B);
      }
   }
   
   public void testUclLoaderOrdering() throws Exception
   {
      ClassLoader globalA = null;
      ClassLoader globalB = null;
      ClassLoader globalC = null;
      try
      {
         globalA = createClassLoader("A1", true, JAR_A_1);
         globalB = createClassLoader("A2", true, JAR_A_1);
         globalC = createClassLoader("A3", true, JAR_A_1);
         
         Class<?> aFromA = globalA.loadClass(CLASS_A);
         Class<?> aFromB = globalB.loadClass(CLASS_A);
         Class<?> aFromC = globalC.loadClass(CLASS_A);
         assertSame(aFromA, aFromB);
         assertSame(aFromA, aFromC);
         assertSame(globalA, aFromA.getClassLoader());
      }
      finally
      {
         unregisterClassLoader(globalA);
         unregisterClassLoader(globalB);
         unregisterClassLoader(globalC);
      }
   }

}
