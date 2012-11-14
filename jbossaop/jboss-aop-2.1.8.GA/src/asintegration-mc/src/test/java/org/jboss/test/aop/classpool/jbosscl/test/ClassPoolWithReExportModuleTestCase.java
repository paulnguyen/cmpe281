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

import org.jboss.test.aop.classpool.jbosscl.support.BundleInfoBuilder;
import org.jboss.test.aop.classpool.jbosscl.support.Result;

/**
 * Reproduces the behaviour of ClassLoaderWithReExportModuleSanityTestCase
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolWithReExportModuleTestCase extends JBossClClassPoolTest
{

   public ClassPoolWithReExportModuleTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassPoolWithReExportModuleTestCase.class);
   }

   public void testReExport() throws Exception
   {
      ClassPool clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("a").
            createPackage(PACKAGE_A);
         clA = createClassPool(rA, "A", builderA, JAR_A_1);
         CtClass classA = assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
         assertCannotLoadCtClass(clA, CLASS_C);
         
         ClassPool clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createModule("b").
               createPackage(PACKAGE_B).
               createReExportModule("a");
            clB = createClassPool(rB, "B", builderB, JAR_B_1);
            CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertCannotLoadCtClass(clA, CLASS_C);
            classA1 = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, classA1);
            CtClass classB = assertLoadCtClass(CLASS_B, clB);
            assertCannotLoadCtClass(clB, CLASS_C);
            
            ClassPool clC = null;
            Result rC = new Result();
            try
            {
               BundleInfoBuilder builderC = BundleInfoBuilder.getBuilder().
                  createRequireModule("b");
               clC = createClassPool(rC, "C", builderC, JAR_C_1);
               
               classA1 = assertLoadCtClass(CLASS_A, clA);
               assertSame(classA, classA1);
               assertCannotLoadCtClass(clA, CLASS_B);
               assertCannotLoadCtClass(clA, CLASS_C);
               classA1 = assertLoadCtClass(CLASS_A, clB, clA);
               assertSame(classA, classA1);
               CtClass classB1 = assertLoadCtClass(CLASS_B, clB);
               assertSame(classB, classB1);
               assertCannotLoadCtClass(clB, CLASS_C);
               classA1 = assertLoadCtClass(CLASS_A, clC, clA);
               assertSame(classA, classA1);
               classB1 = assertLoadCtClass(CLASS_B, clC, clB);
               assertSame(classB, classB1);
               assertLoadCtClass(CLASS_C, clC);
            }
            finally
            {
               unregisterClassPool(clC);
            }
            assertNoClassLoader(rC);
            classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertCannotLoadCtClass(clA, CLASS_C);
            classA1 = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, classA1);
            CtClass classB1 = assertLoadCtClass(CLASS_B, clB);
            assertSame(classB, classB1);
            assertCannotLoadCtClass(clB, CLASS_C);
         }
         finally
         {
            unregisterClassPool(clB);
         }
         assertNoClassLoader(rB);
         CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadCtClass(clA, CLASS_B);
         assertCannotLoadCtClass(clA, CLASS_C);
      }
      finally
      {
         unregisterClassPool(clA);
      }
      assertNoClassLoader(rA);
   }
   
   public void testNoReExport() throws Exception
   {
      ClassPool clA = null;
      Result rA = new Result();
      try
      {
         BundleInfoBuilder builderA = BundleInfoBuilder.getBuilder().
            createModule("a").
            createPackage(PACKAGE_A);
         clA = createClassPool(rA, "A", builderA, JAR_A_1);
         CtClass classA = assertLoadCtClass(CLASS_A, clA);
         assertCannotLoadCtClass(clA, CLASS_B);
         assertCannotLoadCtClass(clA, CLASS_C);
         
         ClassPool clB = null;
         Result rB = new Result();
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createModule("b").
               createPackage(PACKAGE_B).
               createReExportModule("a");
            clB = createClassPool(rB, "B", builderB, JAR_B_1);
            CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertCannotLoadCtClass(clA, CLASS_C);
            classA1 = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, classA1);
            CtClass classB = assertLoadCtClass(CLASS_B, clB);
            assertCannotLoadCtClass(clB, CLASS_C);
            
            ClassPool clC = null;
            Result rC = new Result();
            try
            {
               BundleInfoBuilder builderC = BundleInfoBuilder.getBuilder().
                  createRequirePackage(PACKAGE_B);
               clC = createClassPool(rC, "C", builderC, JAR_C_1);
               
               classA1 = assertLoadCtClass(CLASS_A, clA);
               assertSame(classA, classA1);
               assertCannotLoadCtClass(clA, CLASS_B);
               assertCannotLoadCtClass(clA, CLASS_C);
               classA1 = assertLoadCtClass(CLASS_A, clB, clA);
               assertSame(classA, classA1);
               CtClass classB1 = assertLoadCtClass(CLASS_B, clB);
               assertSame(classB, classB1);
               assertCannotLoadCtClass(clB, CLASS_C);
               assertCannotLoadCtClass(clC, CLASS_A);
               classB1 = assertLoadCtClass(CLASS_B, clC, clB);
               assertSame(classB, classB1);
               assertLoadCtClass(CLASS_C, clC);
            }
            finally
            {
               unregisterClassPool(clC);
            }
            assertNoClassLoader(rC);
            classA1 = assertLoadCtClass(CLASS_A, clA);
            assertSame(classA, classA1);
            assertCannotLoadCtClass(clA, CLASS_B);
            assertCannotLoadCtClass(clA, CLASS_C);
            classA1 = assertLoadCtClass(CLASS_A, clB, clA);
            assertSame(classA, classA1);
            CtClass classB1 = assertLoadCtClass(CLASS_B, clB);
            assertSame(classB, classB1);
            assertCannotLoadCtClass(clB, CLASS_C);
         }
         finally
         {
            unregisterClassPool(clB);
         }
         assertNoClassLoader(rB);
         CtClass classA1 = assertLoadCtClass(CLASS_A, clA);
         assertSame(classA, classA1);
         assertCannotLoadCtClass(clA, CLASS_B);
         assertCannotLoadCtClass(clA, CLASS_C);
      }
      finally
      {
         unregisterClassPool(clA);
      }
      assertNoClassLoader(rA);
   }
}
