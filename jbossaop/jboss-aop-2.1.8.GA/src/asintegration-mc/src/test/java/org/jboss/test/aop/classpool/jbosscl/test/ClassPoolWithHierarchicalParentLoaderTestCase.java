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

import java.net.URL;

import javassist.ClassPool;

import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ParentPolicy;
import org.jboss.classloader.spi.filter.ClassFilter;
import org.jboss.test.aop.classpool.jbosscl.support.NoMatchClassFilter;

import junit.framework.Test;

/**
 * Tests the behaviour of the new classloaders so that we can get the same in the new classpools
 * Reproduces org.jboss.test.classloader.domain.test.HierarchicalParentLoaderUnitTestCase using our test framework, 
 * ClassPoolWithHierarchicalParentLoaderUnitTestCaseSanityTestCase replicates this with the javassist classpools
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolWithHierarchicalParentLoaderTestCase extends JBossClClassPoolTest
{
   public ClassPoolWithHierarchicalParentLoaderTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassPoolWithHierarchicalParentLoaderTestCase.class);
   }
   
   public void testHierarchyBefore() throws Exception
   {
      runTest(ParentPolicy.BEFORE_BUT_JAVA_ONLY, ParentPolicy.BEFORE, true);
   }
   
   
   public void testHierarchyBeforeNotFound() throws Exception
   {
      runTest(ParentPolicy.BEFORE_BUT_JAVA_ONLY, ParentPolicy.BEFORE, false, false);
   }
   
   public void testHierarchyAfterNotReached() throws Exception
   {
      runTest(ParentPolicy.BEFORE_BUT_JAVA_ONLY, ParentPolicy.AFTER_BUT_JAVA_BEFORE, false);
   }
   
   public void testHierarchyAfterReached() throws Exception
   {
      runTest(ParentPolicy.BEFORE_BUT_JAVA_ONLY, ParentPolicy.AFTER_BUT_JAVA_BEFORE, null, true);
   }
   
   public void testHierarchyFiltered() throws Exception
   {
      NoMatchClassFilter filter = new NoMatchClassFilter(CLASS_A);
      runTest(ParentPolicy.BEFORE_BUT_JAVA_ONLY, new ParentPolicy(filter, ClassFilter.NOTHING), false);

      assertTrue("Should have been filtered", filter.filtered);
   }


   private void runTest(ParentPolicy parentParentPolicy, ParentPolicy childParentPolicy, boolean expectedParent) throws Exception
   {
      runTest(parentParentPolicy, childParentPolicy, true, JAR_A_2, expectedParent);
   }
   
   private void runTest(ParentPolicy parentParentPolicy, ParentPolicy childParentPolicy, boolean createParent, boolean expectedParent) throws Exception
   {
      runTest(parentParentPolicy, childParentPolicy, createParent, JAR_A_2, expectedParent);
   }
   
   private void runTest(ParentPolicy parentParentPolicy, ParentPolicy childParentPolicy, URL url, boolean expectedParent) throws Exception
   {
      runTest(parentParentPolicy, childParentPolicy, true, null, expectedParent);
   }
   
   private void runTest(ParentPolicy parentParentPolicy, ParentPolicy childParentPolicy, boolean createParent, URL childURL, boolean expectedParent) throws Exception
   {
      final String parentName = "parent";
      final String childName = "child";
      ClassLoaderDomain parent = getSystem().createAndRegisterDomain(parentName, parentParentPolicy);
      ClassLoaderDomain child = getSystem().createAndRegisterDomain(childName, childParentPolicy, parent);

      ClassPool parentPool = null;
      ClassPool childPool = null;
      try
      {
         //Since the domain is created first, the first/last flag from these two methods is ignored, instead the
         //policy used when creating the domain is used
         if (createParent)
         {
            parentPool = createChildDomainParentLastClassPool("ParentLoader", parentName, true, JAR_A_1);
         }
         childPool = createChildDomainParentLastClassPool("ChildLoader", childName, parentName, true, childURL);
         assertLoadCtClass(CLASS_A, childPool, expectedParent ? parentPool : childPool);
      }
      finally
      {
         unregisterClassPool(parentPool);
         unregisterClassPool(childPool);
         unregisterDomain(child);
         unregisterDomain(parent);
      }
   }
}
