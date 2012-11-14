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
package org.jboss.test.aop.invocationParams;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of typed invocation parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class InvocationTestCase extends AOPTestWithSetup
{
   private POJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("InvocationTestCase");
      suite.addTestSuite(InvocationTestCase.class);
      return suite;
   }
   
   public InvocationTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      InvocationAspect.clear();
      this.pojo = new POJO();
   }
   
   public void test1()
   {
      assertEquals("aroundMethodExecution1", pojo.method1());
      assertEquals("aroundMethodExecution1", InvocationAspect.advice);
      assertNull(InvocationAspect.invokeReturn);
   }
   
   public void test2()
   {
      assertEquals(2, pojo.method2());
      assertNull(InvocationAspect.advice);
      assertNull(InvocationAspect.invokeReturn);
   }
   
   public void test3()
   {
      assertEquals(15000, pojo.callerMethod(20));
      assertEquals("aroundMethodCalledByMethod", InvocationAspect.advice);
      assertNotNull(InvocationAspect.invokeReturn);
      assertEquals(40, ((Integer) InvocationAspect.invokeReturn).intValue());
   }
   
   public void test4()
   {
      pojo.number = 35;
      assertEquals("aroundFieldWrite", InvocationAspect.advice);
      assertNull(InvocationAspect.invokeReturn);
      
      
      assertEquals(500, pojo.number);
      assertEquals("aroundFieldRead", InvocationAspect.advice);
      assertNotNull(InvocationAspect.invokeReturn);
      assertEquals(35, ((Integer) InvocationAspect.invokeReturn).intValue());
   }
}