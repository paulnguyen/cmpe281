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
package org.jboss.test.aop.instanceofintroduced;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class InstanceofIntroducedTestCase extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("InstanceofIntroducedTestCase");
      suite.addTestSuite(InstanceofIntroducedTestCase.class);
      return suite;
   }

   public InstanceofIntroducedTestCase(String name)
   {
      // FIXME InstanceofIntroducedTestCase constructor
      super(name);
   }

   public void testIntroduction() throws Exception
   {
      assertTrue(Interface.class.isAssignableFrom(POJO.class));
      assertTrue(MixinInterface.class.isAssignableFrom(POJO.class));
   }
   
   public void testConstructorExecution() throws Exception
   {
      SimpleInterceptor.intercepted = false;
      POJO pojo = new POJO();
      assertTrue(SimpleInterceptor.intercepted);
   }

   public void testConstructionExecution() throws Exception
   {
      SimpleInterceptor.intercepted = false;
      POJO pojo = new POJO(5);
      assertTrue(SimpleInterceptor.intercepted);
   }

   public void testMethodExecution() throws Exception
   {
      POJO pojo = new POJO();
      SimpleInterceptor.intercepted = false;
      ReturningInterceptor.intercepted = false;
      pojo.method();
      assertTrue(SimpleInterceptor.intercepted);
      assertFalse(ReturningInterceptor.intercepted);
   }
   
   public void testFieldExecution() throws Exception
   {
      POJO pojo = new POJO();
      SimpleInterceptor.intercepted = false;
      pojo.i = 5;
      assertTrue(SimpleInterceptor.intercepted);

      SimpleInterceptor.intercepted = false;
      assertEquals(5, pojo.i);
      assertTrue(SimpleInterceptor.intercepted);
   }
   
   public void testImplementsForIntroducedInterface() throws Exception
   {
      POJO pojo = new POJO();
      ReturningInterceptor.intercepted = false;
      ((ImplementsInterface)pojo).testMethod();
      assertTrue(ReturningInterceptor.intercepted);
   }
}
