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
package org.jboss.test.aop.regression.jbaop336callnpe;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.Advised;


/**
 * http://jira.jboss.com/jira/browse/JBAOP-316
 * 
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 */
public class CallerNPETestCase extends junit.framework.TestCase 
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("CallerNPETestCase");
      suite.addTestSuite(CallerNPETestCase.class);
      return suite;
   }

   public CallerNPETestCase(String name)
   {
      super(name);
   }

   public void testConstructorCallerInterceptor1() throws Exception
   {
      POJO pojo = new POJO("XYZ");
      assertEquals("XYZ", LogInterceptor.logFile);
      assertNotNull(((Advised) pojo)._getInstanceAdvisor());
   }
   
   public void testConstructorCallerInterceptor2() throws Exception
   {
      POJO pojo = new POJO("XYZ", "ABC");
      assertEquals("ABC", LogInterceptor.logFile);
      assertNotNull(((Advised) pojo)._getInstanceAdvisor());
   }
   
   public void testMethodCallerInterceptor1()
   {
      POJO pojo = new POJO();
      pojo.recreateLog("UVW");
      assertEquals("UVW", LogInterceptor.logFile);
      assertNotNull(((Advised) pojo)._getInstanceAdvisor());
   }

   public void testMethodCallerInterceptor2()
   {
      POJO pojo = new POJO();
      pojo.setLogFile("DEF");
      assertEquals("DEF", LogInterceptor.logFile);
      assertNotNull(((Advised) pojo)._getInstanceAdvisor());
   }
}