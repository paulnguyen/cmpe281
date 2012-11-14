/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.test.aop.integration.simple.test;

import java.net.URL;

import junit.framework.Test;

import org.jboss.aop.integration.junit.WovenAOPIntegrationTest;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.test.support.MockClassLoaderHelper;
import org.jboss.classloader.test.support.MockClassLoaderPolicy;

/**
 * SimpleScopedParentFirstUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SimpleScopedParentFirstUnitTestCase extends WovenAOPIntegrationTest
{
   private static String PACKAGE_SUPPORT = "org.jboss.test.aop.integration.simple.support";
   private static String PACKAGE_A = "org.jboss.test.aop.integration.simple.support.a";
   private static String CLASS_A = PACKAGE_A + ".A";
   
   public static Test suite()
   {
      return suite(SimpleScopedParentFirstUnitTestCase.class);
   }
   
   public SimpleScopedParentFirstUnitTestCase(String name)
   {
      super(name);
   }

   /*
    * A simple test that loads a class that uses aop enhanced classes
    * from a scoped classloader domain using parent first classloading rules
    * 
    * The aop enhanced classes are in the same classloader
    */
   public void testScopedParentFirst() throws Exception
   {
      ClassLoaderDomain domain = createScopedClassLoaderDomainParentFirst("Scoped");
      try
      {
         MockClassLoaderPolicy policy = MockClassLoaderHelper.createMockClassLoaderPolicy("A");
         policy.setPathsAndPackageNames(PACKAGE_A, PACKAGE_SUPPORT);
         ClassLoader classLoader = createClassLoader(domain, policy);
         URL url = deploy("a", classLoader);
         try
         {
            Class<?> classA = classLoader.loadClass(CLASS_A);
            classA.newInstance();
         }
         finally
         {
            undeploy(url);
         }
      }
      finally
      {
         unregisterDomain("Scoped");
      }
   }
}
