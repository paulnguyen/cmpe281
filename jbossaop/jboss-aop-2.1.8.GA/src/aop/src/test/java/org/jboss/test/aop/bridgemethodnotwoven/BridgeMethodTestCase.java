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
package org.jboss.test.aop.bridgemethodnotwoven;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.proxy.ClassProxyFactory;
import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.bridgemethod.POJO;
import org.jboss.test.aop.bridgemethod.SubPOJO;
import org.jboss.test.aop.bridgemethod.SubPOJO2;
import org.jboss.test.aop.bridgemethod.SuperPOJO;

/**
 * Test that we handle bridgemethods created by the java15 compiler correctly
 * regarding reflection.
 *
 * @author <a href="mailto:stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision
 */
@SuppressWarnings({"unused", "unchecked"})
public class BridgeMethodTestCase extends AOPTestWithSetup
{
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("BridgeMethodTestCase");
      suite.addTestSuite(BridgeMethodTestCase.class);
      return suite;
   }

   public BridgeMethodTestCase(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void testMethod()
   {
      try {
         ClassProxyFactory.newInstance(POJO.class);
         assertTrue("ClassProxy failed to instrument class", true);
      }
      catch(Exception e)
      {
         System.out.println("ERROR: "+e.getMessage());
         e.printStackTrace();
         assertTrue("ClassProxy failed to instrument class", false);
         
      }
     
   }
   
   public void testGenericMethod()
   {
      //doesnt work with jbossretro atm...
      AOPTestDelegate delegate = (AOPTestDelegate)getDelegate();
      Properties properties = delegate.getSystemProperties();
      if(((String)properties.get("java.vm.version")).startsWith("1.5"))
      {
         try {
            SuperPOJO superPojo = (SuperPOJO) ClassProxyFactory.newInstance(SubPOJO.class);
            assertTrue("ClassProxy failed to instrument generic class", true);
         }
         catch(Exception e)
         {
            System.out.println("ERROR: "+e.getMessage());
            e.printStackTrace();
            assertTrue("ClassProxy failed to instrument generic class", false);
            
         }
      }
   }
   
   public void testMethodOverride()
   {
      try {
         ClassProxyFactory.newInstance(SubPOJO2.class);
         assertTrue("ClassProxy failed to instrument overrided class", true);
      }
      catch(Exception e)
      {
         System.out.println("ERROR: "+e.getMessage());
         e.printStackTrace();
         assertTrue("ClassProxy failed to instrument overrided class", false); 
      }
   }

}