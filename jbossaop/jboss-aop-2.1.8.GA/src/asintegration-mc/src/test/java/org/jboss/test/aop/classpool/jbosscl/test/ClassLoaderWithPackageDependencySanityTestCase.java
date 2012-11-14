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
 * Reproduces org.jboss.test.classloading.vfs.metadata.test.PackageDependencyUnitTestCase using our test framework, 
 * the original tests are testImportNoVersionCheck, testImportVersionCheck and testImportVersionCheckFailed. 
 * ClassPoolWithPackageDependencyTestCase replicates this with the javassist classpools
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoaderWithPackageDependencySanityTestCase extends JBossClClassPoolTest
{

   public ClassLoaderWithPackageDependencySanityTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassLoaderWithPackageDependencySanityTestCase.class);
   }

   public void testImportNoVersionCheck() throws Exception
   {
      ClassLoader clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A);
         clA = createClassLoader(rA, "A", builderA, JAR_A_1);
         Class<?> classA = assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);
         
         ClassLoader clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A);
            clB = createClassLoader(rB, "B", builderB, JAR_B_1);
            Class<?> classA1 = assertLoadClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadClass(clA, CLASS_B);
            assertLoadClass(CLASS_B, clB);
            Class<?> aFromB = assertLoadClass(CLASS_A, clB, clA);
            assertSame(classA, aFromB);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(rB);
         
         Class<?> classA1 = assertLoadClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertNoClassLoader(rA);
   }
   
   public void testImportVersionCheck() throws Exception
   {
      ClassLoader clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A, "1.0.0");
         clA = createClassLoader(rA, "A", builderA, JAR_A_1);
         Class<?> classA = assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);
         
         ClassLoader clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", "2.0.0"));
            clB = createClassLoader(rB, "B", builderB, JAR_B_1);
            Class<?> classA1 = assertLoadClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadClass(clA, CLASS_B);
            assertLoadClass(CLASS_B, clB);
            Class<?> aFromB = assertLoadClass(CLASS_A, clB, clA);
            assertSame(classA, aFromB);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(rB);
         
         assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
      assertNoClassLoader(rA);
   }
   
   public void testImportVersionCheckFailed() throws Exception
   {
      ClassLoader clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A, "3.0.0");
         clA = createClassLoader(rA, "A", builderA, JAR_A_1);
         Class<?> classA = assertLoadClass(CLASS_A, clA);
         assertCannotLoadClass(clA, CLASS_B);
         
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", "2.0.0"));
            try
            {
               createClassLoader(rB, "B", builderB, JAR_B_1);
               fail("Should not have been able to create loader");
            }
            catch(NoSuchClassLoaderException expected)
            {
            }
            Class<?> classA1 = assertLoadClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadClass(clA, CLASS_B);
         }
         finally
         {
            unregisterClassLoader("B");
         }
         Class<?> classA1 = assertLoadClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA);
      }
   }
   
   //These are mine
   
   public void testSeveralModulesWithSamePackagesDifferentVersions() throws Exception
   {
      ClassLoader clA1 = null;
      ClassLoader clA2 = null;
      ClassLoader clA3 = null;
      Result rA1 = new Result();
      Result rA2 = new Result();
      Result rA3 = new Result();
      try
      {
         BundleInfoBuilder builderA1 = BundleInfoBuilder.getBuilder().
            createModule("PackageA").
            createPackage(PACKAGE_A, "1.0.0");
         clA1 = createClassLoader(rA1, "A1", builderA1, JAR_A_1);
         
         BundleInfoBuilder builderA2 = BundleInfoBuilder.getBuilder().
            createModule("PackageA").
            createPackage(PACKAGE_A, "2.0.0");
         clA2 = createClassLoader(rA2, "A2", builderA2, JAR_A_1);
         
         BundleInfoBuilder builderA3 = BundleInfoBuilder.getBuilder().
         createModule("PackageA").
         createPackage(PACKAGE_A, "3.0.0");
         clA3 = createClassLoader(rA3, "A3", builderA3, JAR_A_1);
                  
         Class<?> classA1 = assertLoadClass(CLASS_A, clA1);
         Class<?> classA2 = assertLoadClass(CLASS_A, clA2);
         Class<?> classA3 = assertLoadClass(CLASS_A, clA3);
         assertCannotLoadClass(clA1, CLASS_B);
         assertCannotLoadClass(clA2, CLASS_B);
         assertCannotLoadClass(clA3, CLASS_B);
         
         ClassLoader clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", false, "2.0.0", true));
            clB = createClassLoader(rB, "B", builderB, JAR_B_1);
            Class<?> classA11 = assertLoadClass(CLASS_A, clA1);
            assertSame(classA1, classA11);
            Class<?> classA21 = assertLoadClass(CLASS_A, clA2);
            assertSame(classA2, classA21);
            Class<?> classA31 = assertLoadClass(CLASS_A, clA3);
            assertSame(classA3, classA31);
            assertCannotLoadClass(clA1, CLASS_B);
            assertCannotLoadClass(clA2, CLASS_B);
            assertCannotLoadClass(clA3, CLASS_B);

            assertLoadClass(CLASS_B, clB);
            Class<?> classAFromB = assertLoadClass(CLASS_A, clB, clA2);
            assertSame(classA2, classAFromB);
         }
         finally
         {
            unregisterClassLoader(clB);
         }
         assertNoClassLoader(rB);
         Class<?> classA11 = assertLoadClass(CLASS_A, clA1);
         assertSame(classA1, classA11);
         Class<?> classA21 = assertLoadClass(CLASS_A, clA2);
         assertSame(classA2, classA21);
         Class<?> classA31 = assertLoadClass(CLASS_A, clA3);
         assertSame(classA3, classA31);
         assertCannotLoadClass(clA1, CLASS_B);
         assertCannotLoadClass(clA2, CLASS_B);
         assertCannotLoadClass(clA3, CLASS_B);
      }
      finally
      {
         unregisterClassLoader(clA3);
         unregisterClassLoader(clA2);
         unregisterClassLoader(clA1);
      }
      assertNoClassLoader(rA1);
      assertNoClassLoader(rA2);
      assertNoClassLoader(rA3);
      
   }
}
