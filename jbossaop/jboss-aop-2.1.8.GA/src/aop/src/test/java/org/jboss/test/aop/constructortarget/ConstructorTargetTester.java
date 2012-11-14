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
package org.jboss.test.aop.constructortarget;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/** 
 * 
 * 
 * @author stale w. pedersen(stalep@conduct.no)
 * @version 
 */
@SuppressWarnings({"unused"})
public class ConstructorTargetTester extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ConstructorTargetTester");
      suite.addTestSuite(ConstructorTargetTester.class);
      return suite;
   }

   public ConstructorTargetTester(String name)
   {
      super(name);
   }

   // Public -------------------------------------------------------

   public void setup() throws Exception
   {
      super.setUp();
      AspectTarget.intercepted = false;
   }
   
   public void testConstruction1()
   {
      System.out.println("RUNNING TEST ConstructorTargetTester");
      POJOTarget target = new POJOTarget("testing...");
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction2()
   {
      POJOTarget2 target = new POJOTarget2();
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction3()
   {
      ExternalClass1.POJOTarget target = new ExternalClass1.POJOTarget();
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction4()
   {
      ExternalClass2.POJOTarget target = new ExternalClass2.POJOTarget();
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction5()
   {
      ExternalClass1 external = new ExternalClass1();
      ExternalClass1.POJOTarget target = external.createTarget();
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction6()
   {
      ExternalClass2 external = new ExternalClass2();
      ExternalClass2.POJOTarget target = external.createTarget();
      assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
   
   public void testConstruction7()
   {
     ExternalClass3 external = new ExternalClass3();
     external.createTarget();
     assertTrue("Target object isnt correct!", AspectTarget.intercepted);
   }
}