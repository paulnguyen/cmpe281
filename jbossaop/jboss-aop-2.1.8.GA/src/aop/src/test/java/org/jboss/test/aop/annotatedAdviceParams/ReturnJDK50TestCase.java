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
package org.jboss.test.aop.annotatedAdviceParams;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests that have been temporarily removed from {@link ReturnTestCase} (task
 * JBBUILD-422)
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ReturnJDK50TestCase extends AOPTestWithSetup
{
   private ReturnPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ReturnJDK50TestCase");
      suite.addTestSuite(ReturnJDK50TestCase.class);
      return suite;
   }
   
   public ReturnJDK50TestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      ReturnAspectGenerics.clear();
      this.pojo = new ReturnPOJO();
   }
   
   public void testGenericsExecution() throws POJOException
   {
      pojo.methodGenericsExecution(false);
      assertGenericsAdvices(false);
   }
   
   public void testGenericsExecutionWithException()
   {
      boolean thrown = false;
      try
      {
         pojo.methodGenericsExecution(true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertGenericsAdvices(thrown);
   }
   
   public void testGenericsFieldRead()
   {
      assertNull(pojo.fieldGenerics);
      assertGenericsAdvices(false);
   }
   
   public void testGenericsCall() throws POJOException
   {
      pojo.methodGenericsCall(false);
      assertGenericsAdvices(false);
   }
   
   public void testGenericsCallWithException()
   {
      boolean thrown = false;
      try
      {
         pojo.methodGenericsCall(true);
      }
      catch (POJOException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertGenericsAdvices(thrown);
   }

   private void assertGenericsAdvices(boolean thrown)
   {
      assertTrue(ReturnAspectGenerics.around3);
      assertTrue(ReturnAspectGenerics.around6);
      assertEquals(!thrown, ReturnAspectGenerics.after1);
      assertEquals(!thrown, ReturnAspectGenerics.after2);
      assertEquals(!thrown, ReturnAspectGenerics.after3);
      assertEquals(!thrown, ReturnAspectGenerics.after4);
      assertEquals(!thrown, ReturnAspectGenerics.after6);
      assertEquals(!thrown, ReturnAspectGenerics.after9);
      assertEquals(!thrown, ReturnAspectGenerics.after10);
      assertTrue(ReturnAspectGenerics.finally1);
      assertTrue(ReturnAspectGenerics.finally2);
      assertTrue(ReturnAspectGenerics.finally3);
      assertTrue(ReturnAspectGenerics.finally4);
      assertTrue(ReturnAspectGenerics.finally6);
      assertTrue(ReturnAspectGenerics.finally11);
   }
}