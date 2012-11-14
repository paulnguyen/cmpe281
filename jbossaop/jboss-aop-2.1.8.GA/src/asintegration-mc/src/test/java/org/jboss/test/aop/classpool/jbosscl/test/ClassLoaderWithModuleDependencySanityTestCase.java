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

import org.jboss.classloading.spi.version.VersionRange;
import org.jboss.test.aop.classpool.jbosscl.support.BundleInfoBuilder;
import org.jboss.test.aop.classpool.jbosscl.support.Result;

/**
 * Tests the behaviour of the new classloaders so that we can get the same in the new classpools
 * Reproduces org.jboss.test.classloading.vfs.metadata.test.ModuleDependencyUnitTestCase using our test framework, 
 * the original tests are testImportNoVersionCheck, testImportVersionCheck and testImportVersionCheckFailed. 
 * ClassPoolWithModuleDependencyTestCase replicates this with the javassist classpools
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoaderWithModuleDependencySanityTestCase extends JBossClClassPoolTest
{

   public ClassLoaderWithModuleDependencySanityTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassLoaderWithModuleDependencySanityTestCase.class);
   }

   public void testImportNoVersionCheck() throws Exception
   {
      ClassLoader clA = null;
      Result resultA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A);
         clA = createClassLoader(resultA, "A", builderA, JAR_A_1);
         
         Class<?> aFromA = assertLoadClass(CLASS_A, clA, clA);
         assertCannotLoadClass(clA, CLASS_B);
         
         ClassLoader clB = null;
         Result resultB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createModule("ModuleB").
               createRequireModule("ModuleA");
            clB = createClassLoader(resultB, "B", builderB, JAR_B_1);

            Class<?> aFromA1 = assertLoadClass(CLASS_A, clA, clA);
            assertSame(aFromA, aFromA1);
            assertCannotLoadClass(clA, CLASS_B);
            assertLoadClass(CLASS_B, clB, clB);
            Class<?> aFromB = assertLoadClass(CLASS_A, clB, clA);
            assertSame(aFromA, aFromB);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         Class<?> aFromA1 = assertLoadClass(CLASS_A, clA);
         assertSame(aFromA, aFromA1);
         assertCannotLoadClass(clA, CLASS_B);
         assertNoClassLoader(resultB);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertNoClassLoader(resultA);
   }
   
   public void testImportVersionCheck() throws Exception
   {
      ClassLoader clA = null;
      Result resultA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA", "1.0.0").
            createPackage(PACKAGE_A);
         clA = createClassLoader(resultA, "A", builderA, JAR_A_1);
         Class<?> classA = assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);

         ClassLoader clB = null;
         Result resultB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequireModule("ModuleA", new VersionRange("1.0.0", "2.0.0")).
               createPackage(PACKAGE_B);
            clB = createClassLoader(resultB, "B", builderB, JAR_B_1);
            Class<?> classA1 = assertLoadClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadClass(clA, CLASS_B);
            assertLoadClass(CLASS_B, clB);
            classA1 = assertLoadClass(CLASS_A, clB, clA);
            assertSame(classA, classA1);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(resultB);

         Class<?> classA1 = assertLoadClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertNoClassLoader(resultA);
   }   

   public void testImportVersionCheckFailed() throws Exception
   {
      ClassLoader clA = null;
      Result resultA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA", "3.0.0").
            createPackage(PACKAGE_A);
         clA = createClassLoader(resultA, "A", builderA, JAR_A_1);
         Class<?> classA = assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);
         Result resultB = new Result(); 
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequireModule("ModuleA", new VersionRange("1.0.0", "2.0.0")).
               createPackage(PACKAGE_B);
            try
            {
               createClassLoader(resultB, "B", builderB, JAR_B_1);
               fail("Should not have been able to create loader");
            }
            catch(NoSuchClassLoaderException expected)
            {
            }
            Class<?> classA1 = assertLoadClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadClass(clA, CLASS_B);
            assertNoClassLoader(resultB);
         }
         finally
         {
            unregisterClassLoader("B");
         }
         assertNoClassLoader(resultB);

         Class<?> classA1 = assertLoadClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertNoClassLoader(resultA);
   }
   
   //These are my extra tests
   public void testSeveralModulesWithSamePackages() throws Exception
   {
      ClassLoader clAModuleX = null;
      ClassLoader clAModuleA = null;
      ClassLoader clAModuleY = null;
      Result rAX = new Result();
      Result rAA = new Result();
      Result rAY = new Result();
      try
      {
         BundleInfoBuilder builderAX = BundleInfoBuilder.getBuilder().
            createModule("ModuleX").
            createPackage(PACKAGE_A);
         clAModuleX = createClassLoader(rAX, "X", builderAX, JAR_A_1);
      
         BundleInfoBuilder builderAA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A);
         clAModuleA = createClassLoader(rAA, "A", builderAA, JAR_A_1);
         
         BundleInfoBuilder builderAY = BundleInfoBuilder.getBuilder().
            createModule("ModuleY").
            createPackage(PACKAGE_A);
         clAModuleY = createClassLoader(rAY, "Y", builderAY, JAR_A_1);
      
         Class<?> classAX = assertLoadClass(CLASS_A, clAModuleX);
         Class<?> classAA = assertLoadClass(CLASS_A, clAModuleA);
         Class<?> classAY = assertLoadClass(CLASS_A, clAModuleY);
         assertCannotLoadClass(clAModuleX, CLASS_B);
         assertCannotLoadClass(clAModuleA, CLASS_B);
         assertCannotLoadClass(clAModuleY, CLASS_B);
         
         ClassLoader clB = null;
         Result resultB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequireModule("ModuleA").
               createPackage(PACKAGE_B);
            clB = createClassLoader(resultB, "B", builderB, JAR_B_1);
            Class<?> classAX1 = assertLoadClass(CLASS_A, clAModuleX);
            assertSame(classAX, classAX1);
            Class<?> classAA1 = assertLoadClass(CLASS_A, clAModuleA);
            assertSame(classAA, classAA1);
            Class<?> classAY1 = assertLoadClass(CLASS_A, clAModuleY);
            assertSame(classAY, classAY1);
            assertCannotLoadClass(clAModuleX, CLASS_B);
            assertCannotLoadClass(clAModuleA, CLASS_B);
            assertCannotLoadClass(clAModuleY, CLASS_B);
            
            assertLoadClass(CLASS_B, clB, clB);
            Class<?> aFromB = assertLoadClass(CLASS_A, clB, clAModuleA);
            assertSame(aFromB, classAA);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(resultB);
         
         Class<?> classAX1 = assertLoadClass(CLASS_A, clAModuleX);
         assertSame(classAX, classAX1);
         Class<?> classAA1 = assertLoadClass(CLASS_A, clAModuleA);
         assertSame(classAA, classAA1);
         Class<?> classAY1 = assertLoadClass(CLASS_A, clAModuleY);
         assertSame(classAY, classAY1);
         assertCannotLoadClass(clAModuleX, CLASS_B);
         assertCannotLoadClass(clAModuleA, CLASS_B);
         assertCannotLoadClass(clAModuleY, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clAModuleY);
         unregisterClassLoader(clAModuleA);
         unregisterClassLoader(clAModuleX);
      }
      assertNoClassLoader(rAY);
      assertNoClassLoader(rAA);
      assertNoClassLoader(rAX);
   }
   
   public void testSeveralModulesWithSameNamesDifferentVersions() throws Exception
   {
      ClassLoader clAModuleA1 = null;
      ClassLoader clAModuleA2 = null;
      ClassLoader clAModuleA3 = null;
      Result rA1 = new Result();
      Result rA2 = new Result();
      Result rA3 = new Result();
      try
      {
         BundleInfoBuilder builderA1 = BundleInfoBuilder.getBuilder().
            createModule("ModuleA", "1.0.0").
            createPackage(PACKAGE_A);
         clAModuleA1 = createClassLoader(rA1, "X", builderA1, JAR_A_1);
      
         BundleInfoBuilder builderA2 = BundleInfoBuilder.getBuilder().
            createModule("ModuleA", "2.0.0").
            createPackage(PACKAGE_A);
         clAModuleA2 = createClassLoader(rA2, "A", builderA2, JAR_A_1);
         
         BundleInfoBuilder builderA3 = BundleInfoBuilder.getBuilder().
            createModule("ModuleA", "3.0.0").
            createPackage(PACKAGE_A);
         clAModuleA3 = createClassLoader(rA3, "Y", builderA3, JAR_A_1);
      
         Class<?> classAX = assertLoadClass(CLASS_A, clAModuleA1);
         Class<?> classAA = assertLoadClass(CLASS_A, clAModuleA2);
         Class<?> classAY = assertLoadClass(CLASS_A, clAModuleA3);
         assertCannotLoadClass(clAModuleA1, CLASS_B);
         assertCannotLoadClass(clAModuleA2, CLASS_B);
         assertCannotLoadClass(clAModuleA3, CLASS_B);
         
         ClassLoader clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequireModule("ModuleA", new VersionRange("2.0.0", true, "3.0.0", false)).
               createPackage(PACKAGE_B);
            clB = createClassLoader(rB, "B", builderB, JAR_B_1);
            Class<?> classAX1 = assertLoadClass(CLASS_A, clAModuleA1);
            assertSame(classAX, classAX1);
            Class<?> classAA1 = assertLoadClass(CLASS_A, clAModuleA2);
            assertSame(classAA, classAA1);
            Class<?> classAY1 = assertLoadClass(CLASS_A, clAModuleA3);
            assertSame(classAY, classAY1);
            assertCannotLoadClass(clAModuleA1, CLASS_B);
            assertCannotLoadClass(clAModuleA2, CLASS_B);
            assertCannotLoadClass(clAModuleA3, CLASS_B);
            
            assertLoadClass(CLASS_B, clB, clB);
            Class<?> aFromB = assertLoadClass(CLASS_A, clB, clAModuleA2);
            assertSame(aFromB, classAA);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(rB);

         Class<?> classAX1 = assertLoadClass(CLASS_A, clAModuleA1);
         assertSame(classAX, classAX1);
         Class<?> classAA1 = assertLoadClass(CLASS_A, clAModuleA2);
         assertSame(classAA, classAA1);
         Class<?> classAY1 = assertLoadClass(CLASS_A, clAModuleA3);
         assertSame(classAY, classAY1);
         assertCannotLoadClass(clAModuleA1, CLASS_B);
         assertCannotLoadClass(clAModuleA2, CLASS_B);
         assertCannotLoadClass(clAModuleA3, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clAModuleA3);
         unregisterClassLoader(clAModuleA2);
         unregisterClassLoader(clAModuleA1);
      }
      assertNoClassLoader(rA1);
      assertNoClassLoader(rA2);
      assertNoClassLoader(rA3);
   }
}
