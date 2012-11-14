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
package org.jboss.test.aop.integration.complex.test;

import junit.framework.Test;

import org.jboss.aop.integration.junit.WovenAOPIntegrationTest;
import org.jboss.classloader.test.support.MockClassLoaderHelper;
import org.jboss.classloader.test.support.MockClassLoaderPolicy;

/**
 * ComplexImportAdviceTransitivelyUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ComplexImportAdviceTransitivelyUnitTestCase extends WovenAOPIntegrationTest
{
   private static String PACKAGE_SUPPORT = "org.jboss.test.aop.integration.complex.support";
   private static String PACKAGE_B = PACKAGE_SUPPORT + ".b";
   private static String PACKAGE_C = PACKAGE_SUPPORT + ".c";
   private static String CLASS_C = PACKAGE_C + ".C";
   
   public static Test suite()
   {
      return suite(ComplexImportAdviceTransitivelyUnitTestCase.class);
   }
   
   public ComplexImportAdviceTransitivelyUnitTestCase(String name)
   {
      super(name);
   }

   /*
    * A complex test that loads classes from its own classloader
    * but the advice is imported from a different classloader
    */
   public void testImportAdvice() throws Exception
   {
      MockClassLoaderPolicy supportPolicy = MockClassLoaderHelper.createMockClassLoaderPolicy("Support");
      supportPolicy.setPathsAndPackageNames(PACKAGE_SUPPORT);
      ClassLoader support = createClassLoader(supportPolicy);
      try
      {
         MockClassLoaderPolicy bPolicy = MockClassLoaderHelper.createMockClassLoaderPolicy("B");
         bPolicy.setPathsAndPackageNames(PACKAGE_B);
         bPolicy.setDelegates(createDelegates(supportPolicy));
         ClassLoader b = createClassLoader(bPolicy);
         try
         {
            MockClassLoaderPolicy cPolicy = MockClassLoaderHelper.createMockClassLoaderPolicy("C");
            cPolicy.setPathsAndPackageNames(PACKAGE_C);
            cPolicy.setDelegates(createDelegates(bPolicy));
            ClassLoader c = createClassLoader(cPolicy);
            try
            {
               Class<?> classC = c.loadClass(CLASS_C);
               classC.newInstance();
            }
            finally
            {
               unregisterClassLoader(c);
            }
         }
         finally
         {
            unregisterClassLoader(b);
         }
      }
      finally
      {
         unregisterClassLoader(support);
      }
   }
 }
