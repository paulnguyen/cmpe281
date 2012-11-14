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
import junit.framework.Test;

import org.jboss.test.aop.classpool.jbosscl.support.BundleInfoBuilder;
import org.jboss.test.aop.classpool.jbosscl.support.Result;

/**
 * Tests the behaviour of the new classloaders so that we can get the same in the new classpools
 * Reproduces org.jboss.test.classloading.dependency.test.HierarchicalDomainUnitTestCase using our test framework, 
 * ClassPoolWithHierarchicalDomainSanityTestCase replicates this with the javassist classpools
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolWithHierarchicalDomainTestCase extends JBossClClassPoolTest
{
   public ClassPoolWithHierarchicalDomainTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassPoolWithHierarchicalDomainTestCase.class);
   }

   public void testParentFirst() throws Exception
   {
      ClassPool poolParentA = null;
      Result resultParentA = new Result();
      try
      {
         poolParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
         assertLoadCtClass(CLASS_A, poolParentA);
         assertCannotLoadCtClass(CLASS_B, poolParentA);
         
         ClassPool poolChildA = null;
         Result resultChildA = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            poolChildA = createChildDomainParentFirstClassPool(resultChildA, "aChild", childDomainName, true, JAR_A_2);
            assertLoadCtClass(CLASS_A, poolParentA);
            assertLoadCtClass(CLASS_A, poolChildA, poolParentA);
            assertCannotLoadCtClass(CLASS_B, poolChildA);
            
            ClassPool poolB = null;
            Result resultB = new Result();
            try
            {
               BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
                  createRequirePackage(PACKAGE_A);
               poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
               
               assertLoadCtClass(CLASS_A, poolParentA);
               assertLoadCtClass(CLASS_B, poolB);
               assertLoadCtClass(CLASS_A, poolB, poolParentA);
            }
            finally
            {
               unregisterClassPool(poolB);
            }
            assertNoClassPool(resultB);
         }
         finally
         {
            unregisterClassPool(poolChildA);
            unregisterDomain(childDomainName);
         }
         assertNoClassPool(resultChildA);
      }
      finally
      {
         unregisterClassPool(poolParentA);
      }
      assertNoClassPool(resultParentA);
   }
   
   public void testParentLast() throws Exception
   {
      ClassPool clParentA = null;
      Result resultParentA = new Result();
      try
      {
         clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
         assertLoadCtClass(CLASS_A, clParentA);
         assertCannotLoadCtClass(CLASS_B, clParentA);
         
         ClassPool poolChildA = null;
         Result resultChildA = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            poolChildA = createChildDomainParentLastClassPool(resultChildA, "aChild", childDomainName, true, JAR_A_2);
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_A, poolChildA);
            assertCannotLoadCtClass(CLASS_B, poolChildA);
            
            ClassPool poolB = null;
            Result resultB = new Result();
            try
            {
               BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
                  createRequirePackage(PACKAGE_A);
               poolB = createChildDomainParentLastClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
               
               assertLoadCtClass(CLASS_A, poolChildA);
               assertLoadCtClass(CLASS_B, poolB);
               assertLoadCtClass(CLASS_A, poolB, poolChildA);
            }
            finally
            {
               unregisterClassPool(poolB);
            }
            assertNoClassPool(resultB);
         }
         finally
         {
            unregisterClassPool(poolChildA);
            unregisterDomain(childDomainName);
         }
         assertNoClassPool(resultChildA);
      }
      finally
      {
         unregisterClassPool(clParentA);
      }
      assertNoClassPool(resultParentA);
   }
   
   public void testParentLastNotInChild() throws Exception
   {
      ClassPool clParentA = null;
      Result resultParentA = new Result();
      try
      {
         clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
         assertLoadCtClass(CLASS_A, clParentA);
         assertCannotLoadCtClass(CLASS_B, clParentA);
         
         ClassPool poolB = null;
         Result resultB = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequirePackage(PACKAGE_A);
            poolB = createChildDomainParentLastClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
            
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
         }
         finally
         {
            unregisterClassPool(poolB);
            unregisterDomain(childDomainName);
         }
         assertNoClassPool(resultB);
      }
      finally
      {
         unregisterClassPool(clParentA);
      }
      assertNoClassPool(resultParentA);
   }
   
   public void testParentFirstWrongWayAround() throws Exception
   {
      ClassPool poolB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassPool(resultB);
         
         ClassPool clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            
            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
         }
         finally
         {
            unregisterClassPool(clParentA);
         }
         assertNoClassPool(resultParentA);
      }
      finally
      {
         unregisterClassPool(poolB);
         unregisterDomain(childDomainName);
      }
      assertNoClassPool(resultB);
   }
   
   public void testParentLastWrongWayAround() throws Exception
   {
      ClassPool poolB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            poolB = createChildDomainParentLastClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassPool(resultB);
         
         ClassPool clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            
            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
         }
         finally
         {
            unregisterClassPool(clParentA);
         }
         assertNoClassPool(resultParentA);
      }
      finally
      {
         unregisterClassPool(poolB);
         unregisterDomain(childDomainName);
      }
   }
   
   public void testParentRedeploy() throws Exception
   {
      ClassPool poolB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassPool(resultB);

         ClassPool clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            
            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
         }
         finally
         {
            unregisterClassPool(clParentA);
         }

         assertNoClassPool(resultParentA);
         assertNoClassPool(resultB);
      
         try
         {
            clParentA = createClassPool(resultParentA, "aParent", true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            
            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
         }
         finally
         {
            unregisterClassPool(clParentA);
         }
         assertNoClassPool(resultParentA);
      }
      finally
      {
         unregisterClassPool(poolB);
         unregisterDomain(childDomainName);
      }
      assertNoClassPool(resultB);
   }
   
   public void testParentOtherDomain() throws Exception
   {
      ClassPool clParentA = null;
      Result resultParentA = new Result();
      final String parentDomainName = "ParentDomain";
      try
      {
         clParentA = createChildDomainParentFirstClassPool(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
         assertLoadCtClass(CLASS_A, clParentA);
         assertCannotLoadCtClass(CLASS_B, clParentA);
         
         ClassPool poolB = null;
         Result resultB = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequirePackage(PACKAGE_A);
            poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertLoadCtClass(CLASS_B, poolB);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
         }
         finally
         {
            unregisterClassPool(poolB);
            unregisterDomain(childDomainName);
         }
      }
      finally
      {
         unregisterClassPool(clParentA);
         unregisterDomain(parentDomainName);
      }
   }
   
   public void testParentOtherDomainLazy() throws Exception
   {
      ClassPool poolB = null;
      Result resultB = new Result();
      final String parentDomainName = "ParentDomain";
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassPool(resultB);
         
         ClassPool clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createChildDomainParentFirstClassPool(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);

            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
         }
         finally
         {
            unregisterClassPool(clParentA);
            unregisterDomain(parentDomainName);
         }
         assertNoClassPool(resultParentA);
      }
      finally
      {
         unregisterClassPool(poolB);
         unregisterDomain(childDomainName);
      }
      assertNoClassPool(resultB);
   }

   public void testParentRedeployOtherDomain() throws Exception
   {
      ClassPool poolB = null;
      Result resultB = new Result();
      final String parentDomainName = "ParentDomain";
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            poolB = createChildDomainParentFirstClassPool(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassPool(resultB);
         
         ClassPool clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createChildDomainParentFirstClassPool(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);

            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
         }
         finally
         {
            unregisterClassPool(clParentA);
            unregisterDomain(parentDomainName);
         }
         assertNoClassPool(resultParentA);
         assertNoClassPool(resultB);

         try
         {
            clParentA = createChildDomainParentFirstClassPool(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);

            poolB = assertClassPool(resultB.getFactory());
            assertLoadCtClass(CLASS_A, clParentA);
            assertCannotLoadCtClass(CLASS_B, clParentA);
            assertLoadCtClass(CLASS_A, poolB, clParentA);
         }
         finally
         {
            unregisterClassPool(clParentA);
            unregisterDomain(parentDomainName);
         }
      }
      finally
      {
         unregisterClassPool(poolB);
         unregisterDomain(childDomainName);
      }
      assertNoClassPool(resultB);
   }
}
