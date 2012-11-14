/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.aop.reflection;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
public class ReflectionTester extends AOPTestWithSetup
{
   ReflectionPOJO reflectionPOJO;
   public static Test suite()
   {
      TestSuite suite = new TestSuite("ReflectionTester");
      suite.addTestSuite(ReflectionTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public ReflectionTester(String name)
   {
      super(name);
   }

   // Public -------------------------------------------------------

   public void testAccessFromConstructor()
   {
      System.out.println("RUNNING TEST ACCESS FROM CONSTRUCTOR");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO(1);
      System.out.println("pojo=" + reflectionPOJO);
   }
   
   public void testAccessFromMethod()
   {
      System.out.println("RUNNING TEST ACCESS FROM METHOD");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCreationAndFieldAccess();
   }
   
   public void testCleanGetMethods()
   {
      System.out.println("RUNNING TEST CLEAN GET METHODS");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetMethods();
   }
   
   public void testCleanGetDeclaredMethods()
   {
      System.out.println("RUNNING TEST CLEAN GET DECLARED METHODS");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetDeclaredMethods();
   }
   
   public void testCleanGetDeclaredFields()
   {
      System.out.println("RUNNING TEST CLEAN GET DECLARED FIELDS");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetDeclaredFields();
   }
   
   public void testCleanGetInterfaces()
   {
      System.out.println("RUNNING TEST CLEAN GET INTERFACES");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetInterfaces();
   }
   
   public void testCleanGetDeclaredMethod()
   {
      System.out.println("RUNNING TEST CLEAN GET DECLARED METHOD");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetDeclaredMethod();
   }
   
   public void testCleanGetDeclaredField()
   {
      System.out.println("RUNNING TEST CLEAN GET DECLARED FIELD");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetDeclaredField();
   }
   
   public void testCleanGetMethod()
   {
      System.out.println("RUNNING TEST CLEAN GET METHOD");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetMethod();
   }
   
   public void testCleanGetClasses()
   {
      System.out.println("RUNNING TEST CLEAN GET CLASSES");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetClasses();
   }
   
   public void testCleanGetDeclaredClasses()
   {
      System.out.println("RUNNING TEST CLEAN GET DECLARED CLASSES");
      ReflectionPOJO reflectionPOJO = new ReflectionPOJO();
      reflectionPOJO.testCleanGetDeclaredClasses();
   }
   

   // Inner classes -------------------------------------------------
}

