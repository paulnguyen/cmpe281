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
import org.jboss.classloader.test.support.MockClassLoaderHelper;
import org.jboss.classloader.test.support.MockClassLoaderPolicy;

/**
 * SimplePrivateUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SimplePrivateUnitTestCase extends WovenAOPIntegrationTest
{
   private static String PACKAGE_SUPPORT = "org.jboss.test.aop.integration.simple.support";
   private static String PACKAGE_SUPPORT_RESOURCE = PACKAGE_SUPPORT.replace(".", "/");
   private static String PACKAGE_A = PACKAGE_SUPPORT + ".a";
   private static String PACKAGE_A_RESOURCE = PACKAGE_A.replace(".", "/");
   private static String CLASS_A = PACKAGE_A + ".A";
   
   public static Test suite()
   {
      return suite(SimplePrivateUnitTestCase.class);
   }
   
   public SimplePrivateUnitTestCase(String name)
   {
      super(name);
   }

   /*
    * A simple test that loads a class from another classloader
    * that uses aop enhanced classes from its own classloader
    * that are not exported to us.
    */
   public void testPrivate() throws Exception
   {
      MockClassLoaderPolicy policy = MockClassLoaderHelper.createMockClassLoaderPolicy("A");
      policy.setPackageNames(PACKAGE_A);
      policy.setPaths(PACKAGE_A_RESOURCE, PACKAGE_SUPPORT_RESOURCE);
      ClassLoader classLoader = createClassLoader(policy);
      try
      {
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
         unregisterClassLoader(classLoader);
      }
   }
}
