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
package org.jboss.test.aspects.mock;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aspects.mock.Replace;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A MockTestCase.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @author Thomas Roka-Aardal
 * 
 * @version $Revision: 1.1 $
 */
public class MockTestCase extends AOPTestWithSetup
{
   
   public MockTestCase() 
   {  
      super("Default");  
   }

   public MockTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("MockTestCase");
      suite.addTestSuite(MockTestCase.class);
      return suite;
   }

   @Replace(invocation = "org.jboss.test.aspects.mock.ExternalClass->calculate(int)", 
         callbackClass = "org.jboss.test.aspects.mock.MockTestCase", 
         callbackMethod = "callback")
   public void testBusinessServiceGetNumberExternalIsolated() throws Exception
   {
      BusinessService service = new BusinessService(666);
      assertEquals(1998, service.calculateExternal());
   }

   @Replace(invocation = "org.jboss.test.aspects.mock.ExternalClass->calculate(int)", 
         callbackClass = "org.jboss.test.aspects.mock.MockTestCase", 
         callbackMethod = "callbackAlternate")
   public void testBusinessServiceGetNumberExternalIsolatedAlternate() throws Exception
   {
      BusinessService service = new BusinessService(666);
      assertEquals(42, service.calculateExternal());
   }

   @Replace(invocation = "org.jboss.test.aspects.mock.ExternalClass->calculate(int)", 
         callbackClass = "org.jboss.test.aspects.mock.MockTestCase", 
         callbackMethod = "callbackThreeTimes", expectedCalls = 3)
   public void testBusinessServiceGetNumberExternalIsolatedExactlyThreeTimes() throws Exception
   {
      BusinessService service = new BusinessService(666);
      assertEquals(0, service.calculateExternal());
      assertEquals(0, service.calculateExternal());
      assertEquals(0, service.calculateExternal());
   }

   @Replace(invocation = "org.jboss.test.aspects.mock.ExternalClass->calculate(int)", 
         callbackClass = "org.jboss.test.aspects.mock.MockTestCase", 
         callbackMethod = "callbackThreeTimes", expectedCalls = 3)
   public void testBusinessServiceGetNumberExternalIsolatedMoreThanThreeTimes() throws Exception
   {
      BusinessService service = new BusinessService(666);
      assertEquals(0, service.calculateExternal());
      assertEquals(0, service.calculateExternal());
      assertEquals(0, service.calculateExternal());
      try
      {
         assertEquals(0, service.calculateExternal());
      }
      catch (RuntimeException e)
      {
         // pass
      }
   }

   public void testBusinessServiceGetNumberExternal() throws Exception
   {
      BusinessService service = new BusinessService(666);
      assertEquals(1332, service.calculateExternal());
   }

   public int callback(int value)
   {
      return value * 3;
   }

   public int callbackAlternate(int value)
   {
      return 42;
   }

   public int callbackThreeTimes(int value)
   {
      return 0;
   }
}
