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

import javassist.ClassPool;
import javassist.CtClass;
import junit.framework.Test;

import org.jboss.classloading.spi.version.VersionRange;
import org.jboss.test.aop.classpool.jbosscl.support.BundleInfoBuilder;
import org.jboss.test.aop.classpool.jbosscl.support.Result;

/**
 * Reproduces the behaviour found in ClassLoaderWithPackageDependencySanityTestCase 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolWithPackageDependencyTestCase extends JBossClClassPoolTest
{

   public ClassPoolWithPackageDependencyTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassPoolWithPackageDependencyTestCase.class);
   }

   public void testImportNoVersionCheck() throws Exception
   {
      ClassPool clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A);
         clA = createClassPool(rA, "A", builderA, JAR_A_1);
         CtClass classA = assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
         
         ClassPool clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A);
            clB = createClassPool(rB, "B", builderB, JAR_B_1);
            CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertLoadCtClass(CLASS_B, clB);
            CtClass aFromB = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, aFromB);
         }
         finally
         {
            unregisterClassPool(clB);
         }
         assertNoClassLoader(rB);
         
         CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadCtClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassPool(clA);
      }
      assertNoClassLoader(rA);
   }
   
   public void testImportVersionCheck() throws Exception
   {
      ClassPool clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A, "1.0.0");
         clA = createClassPool(rA, "A", builderA, JAR_A_1);
         CtClass classA = assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
         
         ClassPool clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", "2.0.0"));
            clB = createClassPool(rB, "B", builderB, JAR_B_1);
            CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertLoadCtClass(CLASS_B, clB);
            CtClass aFromB = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, aFromB);
         }
         finally
         {
            unregisterClassPool(clB);
         }
         assertNoClassLoader(rB);

         assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassPool(clA);
      }
      assertNoClassLoader(rA);
   }
   
   public void testImportVersionCheckFailed() throws Exception
   {
      ClassPool clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("ModuleA").
            createPackage(PACKAGE_A, "3.0.0");
         clA = createClassPool(rA, "A", builderA, JAR_A_1);
         CtClass classA = assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
         
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", "2.0.0"));
            try
            {
               createClassPool(rB, "B", builderB, JAR_B_1);
               fail("Should not have been able to create loader");
            }
            catch(NoSuchClassLoaderException expected)
            {
            }
            CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
         }
         finally
         {
            unregisterClassLoader("B");
         }
         CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadCtClass(clA, CLASS_B);
      }
      finally
      {
         unregisterClassPool(clA);
      }
   }
   
   //These are mine
   
   public void testSeveralModulesWithSamePackagesDifferentVersions() throws Exception
   {
      ClassPool clA1 = null;
      ClassPool clA2 = null;
      ClassPool clA3 = null;
      Result rA1 = new Result();
      Result rA2 = new Result();
      Result rA3 = new Result();
      try
      {
         BundleInfoBuilder builderA1 = BundleInfoBuilder.getBuilder().
            createModule("PackageA").
            createPackage(PACKAGE_A, "1.0.0");
         clA1 = createClassPool(rA1, "A1", builderA1, JAR_A_1);
         
         BundleInfoBuilder builderA2 = BundleInfoBuilder.getBuilder().
            createModule("PackageA").
            createPackage(PACKAGE_A, "2.0.0");
         clA2 = createClassPool(rA2, "A2", builderA2, JAR_A_1);
         
         BundleInfoBuilder builderA3 = BundleInfoBuilder.getBuilder().
         createModule("PackageA").
         createPackage(PACKAGE_A, "3.0.0");
         clA3 = createClassPool(rA3, "A3", builderA3, JAR_A_1);
                  
         CtClass classA1 = assertLoadCtClass(CLASS_A, clA1);
         CtClass classA2 = assertLoadCtClass(CLASS_A, clA2);
         CtClass classA3 = assertLoadCtClass(CLASS_A, clA3);
         assertCannotLoadCtClass(clA1, CLASS_B);
         assertCannotLoadCtClass(clA2, CLASS_B);
         assertCannotLoadCtClass(clA3, CLASS_B);
         
         ClassPool clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createPackage(PACKAGE_B).
               createRequirePackage(PACKAGE_A, new VersionRange("1.0.0", false, "2.0.0", true));
            clB = createClassPool(rB, "B", builderB, JAR_B_1);
            CtClass classA11 = assertLoadCtClass(CLASS_A, clA1);
            assertSame(classA1, classA11);
            CtClass classA21 = assertLoadCtClass(CLASS_A, clA2);
            assertSame(classA2, classA21);
            CtClass classA31 = assertLoadCtClass(CLASS_A, clA3);
            assertSame(classA3, classA31);
            assertCannotLoadCtClass(clA1, CLASS_B);
            assertCannotLoadCtClass(clA2, CLASS_B);
            assertCannotLoadCtClass(clA3, CLASS_B);

            assertLoadCtClass(CLASS_B, clB);
            CtClass classAFromB = assertLoadCtClass(CLASS_A, clB, clA2);
            assertSame(classA2, classAFromB);
         }
         finally
         {
            unregisterClassPool(clB);
         }
         assertNoClassLoader(rB);
         CtClass classA11 = assertLoadCtClass(CLASS_A, clA1);
         assertSame(classA1, classA11);
         CtClass classA21 = assertLoadCtClass(CLASS_A, clA2);
         assertSame(classA2, classA21);
         CtClass classA31 = assertLoadCtClass(CLASS_A, clA3);
         assertSame(classA3, classA31);
         assertCannotLoadCtClass(clA1, CLASS_B);
         assertCannotLoadCtClass(clA2, CLASS_B);
         assertCannotLoadCtClass(clA3, CLASS_B);
      }
      finally
      {
         unregisterClassPool(clA3);
         unregisterClassPool(clA2);
         unregisterClassPool(clA1);
      }
      assertNoClassLoader(rA1);
      assertNoClassLoader(rA2);
      assertNoClassLoader(rA3);
   }
}
