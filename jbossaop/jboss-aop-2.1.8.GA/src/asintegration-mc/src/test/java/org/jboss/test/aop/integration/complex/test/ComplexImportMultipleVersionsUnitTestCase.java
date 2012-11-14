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

import java.net.URL;

import junit.framework.Test;

import org.jboss.aop.integration.junit.WovenAOPIntegrationTest;
import org.jboss.classloader.test.support.MockClassLoaderHelper;
import org.jboss.classloader.test.support.MockClassLoaderPolicy;

/**
 * ComplexImportMultipleVersionsUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ComplexImportMultipleVersionsUnitTestCase extends WovenAOPIntegrationTest
{
   private static String PACKAGE_SUPPORT = "org.jboss.test.aop.integration.complex.support";
   private static String PACKAGE_A = PACKAGE_SUPPORT + ".a";
   private static String CLASS_A = PACKAGE_A + ".A";
   
   public static Test suite()
   {
      return suite(ComplexImportMultipleVersionsUnitTestCase.class);
   }
   
   public ComplexImportMultipleVersionsUnitTestCase(String name)
   {
      super(name);
   }

   /*
    * A complex test that loads classes from other classloaders
    * they use aop enhanced classes from other classloaders.
    * 
    * The aop enchanced classes are the same class but in
    * different classloaders simulating versioning in OSGi 
    */
   public void testMultiVersion() throws Exception
   {
      MockClassLoaderPolicy support1Policy = MockClassLoaderHelper.createMockClassLoaderPolicy("Support1");
      support1Policy.setPathsAndPackageNames(PACKAGE_SUPPORT);
      ClassLoader support1 = createClassLoader(support1Policy);
      try
      {
         MockClassLoaderPolicy a1Policy = MockClassLoaderHelper.createMockClassLoaderPolicy("A1");
         a1Policy.setPathsAndPackageNames(PACKAGE_A);
         a1Policy.setDelegates(createDelegates(support1Policy));
         ClassLoader a1 = createClassLoader(a1Policy);
         try
         {
            URL url1 = deploy("1", a1);
            try
            {
               MockClassLoaderPolicy support2Policy = MockClassLoaderHelper.createMockClassLoaderPolicy("Support2");
               support2Policy.setPathsAndPackageNames(PACKAGE_SUPPORT);
               ClassLoader support2 = createClassLoader(support2Policy);
               try
               {
                  MockClassLoaderPolicy a2Policy = MockClassLoaderHelper.createMockClassLoaderPolicy("A2");
                  a2Policy.setPathsAndPackageNames(PACKAGE_A);
                  a2Policy.setDelegates(createDelegates(support2Policy));
                  ClassLoader a2 = createClassLoader(a2Policy);
                  
                  System.out.println("--------------> Support1\t" + support1);
                  System.out.println("--------------> A1\t\t" + a1);
                  System.out.println("--------------> Support2\t" + support2);
                  System.out.println("--------------> A2\t\t" + a2);
                  Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("org.jboss.test.aop.integration.complex.support.TestInterceptor");
                  System.out.println("====== " + clazz + " " + clazz.getClassLoader());
                  
                  Class<?> clazz1 = a1.loadClass("org.jboss.test.aop.integration.complex.support.TestInterceptor"); 
                  System.out.println("====== " + clazz1 + " " + clazz1.getClassLoader());
                  Class<?> clazz2 = a2.loadClass("org.jboss.test.aop.integration.complex.support.TestInterceptor"); 
                  System.out.println("====== " + clazz2 + " " + clazz2.getClassLoader());
                  
                  try
                  {
                     URL url2 = deploy("2", a2);
                     try
                     {
                        Class<?> classA1 = a1.loadClass(CLASS_A);
                        @SuppressWarnings("unused")
                        Object instanceA1 = classA1.newInstance();

                        Class<?> classA2 = a2.loadClass(CLASS_A);
                        assertNotSame(classA2, classA1);
                        classA2.newInstance();
                     }
                     finally
                     {
                        undeploy(url2);
                     }
                  }
                  finally
                  {
                     unregisterClassLoader(a2);
                  }
               }
               finally
               {
                  unregisterClassLoader(support2);
               }
            }
            finally
            {
               undeploy(url1);
            }
         }
         finally
         {
            unregisterClassLoader(a1);
         }
      }
      finally
      {
         unregisterClassLoader(support1);
      }
   }
 }
