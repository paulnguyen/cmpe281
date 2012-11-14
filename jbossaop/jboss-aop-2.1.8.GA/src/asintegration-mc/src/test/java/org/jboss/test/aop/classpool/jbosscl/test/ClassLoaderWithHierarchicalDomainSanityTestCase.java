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
public class ClassLoaderWithHierarchicalDomainSanityTestCase extends JBossClClassPoolTest
{
   public ClassLoaderWithHierarchicalDomainSanityTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassLoaderWithHierarchicalDomainSanityTestCase.class);
   }
   
   public void testParentFirst() throws Exception
   {
      ClassLoader clParentA = null;
      Result resultParentA = new Result();
      try
      {
         clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
         assertLoadClass(CLASS_A, clParentA);
         assertCannotLoadClass(CLASS_B, clParentA);
         
         ClassLoader clChildA = null;
         Result resultChildA = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            clChildA = createChildDomainParentFirstClassLoader(resultChildA, "aChild", childDomainName, true, JAR_A_2);
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clChildA, clParentA);
            assertCannotLoadClass(CLASS_B, clChildA);
            
            ClassLoader clB = null;
            Result resultB = new Result();
            try
            {
               BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
                  createRequirePackage(PACKAGE_A);
               clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
               
               assertLoadClass(CLASS_A, clParentA);
               assertLoadClass(CLASS_B, clB);
               assertLoadClass(CLASS_A, clB, clParentA);
            }
            finally
            {
               unregisterClassLoader(clB);
            }
            assertNoClassLoader(resultB);
         }
         finally
         {
            unregisterClassLoader(clChildA);
            unregisterDomain(childDomainName);
         }
         assertNoClassLoader(resultChildA);
      }
      finally
      {
         unregisterClassLoader(clParentA);
      }
      assertNoClassLoader(resultParentA);
   }
   
   public void testParentLast() throws Exception
   {
      ClassLoader clParentA = null;
      Result resultParentA = new Result();
      try
      {
         clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
         assertLoadClass(CLASS_A, clParentA);
         assertCannotLoadClass(CLASS_B, clParentA);
         
         ClassLoader clChildA = null;
         Result resultChildA = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            clChildA = createChildDomainParentLastClassLoader(resultChildA, "aChild", childDomainName, true, JAR_A_2);
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clChildA);
            assertCannotLoadClass(CLASS_B, clChildA);
            
            ClassLoader clB = null;
            Result resultB = new Result();
            try
            {
               BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
                  createRequirePackage(PACKAGE_A);
               clB = createChildDomainParentLastClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
               
               assertLoadClass(CLASS_A, clChildA);
               assertLoadClass(CLASS_B, clB);
               assertLoadClass(CLASS_A, clB, clChildA);
            }
            finally
            {
               unregisterClassLoader(clB);
            }
            assertNoClassLoader(resultB);
         }
         finally
         {
            unregisterClassLoader(clChildA);
            unregisterDomain(childDomainName);
         }
         assertNoClassLoader(resultChildA);
      }
      finally
      {
         unregisterClassLoader(clParentA);
      }
      assertNoClassLoader(resultParentA);
   }
   
   public void testParentLastNotInChild() throws Exception
   {
      ClassLoader clParentA = null;
      Result resultParentA = new Result();
      try
      {
         clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
         assertLoadClass(CLASS_A, clParentA);
         assertCannotLoadClass(CLASS_B, clParentA);
         
         ClassLoader clB = null;
         Result resultB = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequirePackage(PACKAGE_A);
            clB = createChildDomainParentLastClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
            
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_B, clB);
            assertLoadClass(CLASS_A, clB, clParentA);
         }
         finally
         {
            unregisterClassLoader(clB);
            unregisterDomain(childDomainName);
         }
         assertNoClassLoader(resultB);
      }
      finally
      {
         unregisterClassLoader(clParentA);
      }
      assertNoClassLoader(resultParentA);
   }
   
   public void testParentFirstWrongWayAround() throws Exception
   {
      ClassLoader clB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassLoader(resultB);
         
         ClassLoader clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            
            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
            assertLoadClass(CLASS_B, clB);
         }
         finally
         {
            unregisterClassLoader(clParentA);
         }
         assertNoClassLoader(resultParentA);
      }
      finally
      {
         unregisterClassLoader(clB);
         unregisterDomain(childDomainName);
      }
      assertNoClassLoader(resultB);
   }
   
   public void testParentLastWrongWayAround() throws Exception
   {
      ClassLoader clB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            clB = createChildDomainParentLastClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassLoader(resultB);
         
         ClassLoader clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            
            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
            assertLoadClass(CLASS_B, clB);
         }
         finally
         {
            unregisterClassLoader(clParentA);
         }
         assertNoClassLoader(resultParentA);
      }
      finally
      {
         unregisterClassLoader(clB);
         unregisterDomain(childDomainName);
      }
   }
   
   public void testParentRedeploy() throws Exception
   {
      ClassLoader clB = null;
      Result resultB = new Result();
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassLoader(resultB);

         ClassLoader clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            
            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
            assertLoadClass(CLASS_B, clB);
         }
         finally
         {
            unregisterClassLoader(clParentA);
         }

         assertNoClassLoader(resultParentA);
         assertNoClassLoader(resultB);
      
         try
         {
            clParentA = createClassLoader(resultParentA, "aParent", true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            
            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
            assertLoadClass(CLASS_B, clB);
         }
         finally
         {
            unregisterClassLoader(clParentA);
         }
         assertNoClassLoader(resultParentA);
      }
      finally
      {
         unregisterClassLoader(clB);
         unregisterDomain(childDomainName);
      }
      assertNoClassLoader(resultB);
   }
   
   public void testParentOtherDomain() throws Exception
   {
      ClassLoader clParentA = null;
      Result resultParentA = new Result();
      final String parentDomainName = "ParentDomain";
      try
      {
         clParentA = createChildDomainParentFirstClassLoader(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
         assertLoadClass(CLASS_A, clParentA);
         assertCannotLoadClass(CLASS_B, clParentA);
         
         ClassLoader clB = null;
         Result resultB = new Result();
         final String childDomainName = "ChildDomain";
         try
         {
            BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
               createRequirePackage(PACKAGE_A);
            clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            assertLoadClass(CLASS_A, clParentA);
            assertLoadClass(CLASS_B, clB);
            assertLoadClass(CLASS_A, clB, clParentA);
         }
         finally
         {
            unregisterClassLoader(clB);
            unregisterDomain(childDomainName);
         }
      }
      finally
      {
         unregisterClassLoader(clParentA);
         unregisterDomain(parentDomainName);
      }
   }
   
   public void testParentOtherDomainLazy() throws Exception
   {
      ClassLoader clB = null;
      Result resultB = new Result();
      final String parentDomainName = "ParentDomain";
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassLoader(resultB);
         
         ClassLoader clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createChildDomainParentFirstClassLoader(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);

            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
         }
         finally
         {
            unregisterClassLoader(clParentA);
            unregisterDomain(parentDomainName);
         }
         assertNoClassLoader(resultParentA);
      }
      finally
      {
         unregisterClassLoader(clB);
         unregisterDomain(childDomainName);
      }
      assertNoClassLoader(resultB);
   }

   public void testParentRedeployOtherDomain() throws Exception
   {
      ClassLoader clB = null;
      Result resultB = new Result();
      final String parentDomainName = "ParentDomain";
      final String childDomainName = "ChildDomain";
      try
      {
         BundleInfoBuilder builderB = BundleInfoBuilder.getBuilder().
            createRequirePackage(PACKAGE_A);
         try
         {
            clB = createChildDomainParentFirstClassLoader(resultB, "b", childDomainName, parentDomainName, builderB, JAR_B_1);
            fail("Should be no loader");
         }
         catch(NoSuchClassLoaderException e)
         {
         }
         assertNoClassLoader(resultB);
         
         ClassLoader clParentA = null;
         Result resultParentA = new Result();
         try
         {
            clParentA = createChildDomainParentFirstClassLoader(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);

            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
         }
         finally
         {
            unregisterClassLoader(clParentA);
            unregisterDomain(parentDomainName);
         }
         assertNoClassLoader(resultParentA);
         assertNoClassLoader(resultB);

         try
         {
            clParentA = createChildDomainParentFirstClassLoader(resultParentA, "aParent", parentDomainName, true, JAR_A_1);
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);

            clB = assertClassLoader(resultB.getFactory());
            assertLoadClass(CLASS_A, clParentA);
            assertCannotLoadClass(CLASS_B, clParentA);
            assertLoadClass(CLASS_A, clB, clParentA);
         }
         finally
         {
            unregisterClassLoader(clParentA);
            unregisterDomain(parentDomainName);
         }
      }
      finally
      {
         unregisterClassLoader(clB);
         unregisterDomain(childDomainName);
      }
      assertNoClassLoader(resultB);
   }
}
