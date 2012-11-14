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
package org.jboss.test.aop.bridgemethod;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 *
 * @author <a href="mailto:stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision
 */
@SuppressWarnings({"unchecked"})
public class BridgeMethodWeavingTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("BridgeMethodWeavingTestCase");
      suite.addTestSuite(BridgeMethodWeavingTestCase.class);
      return suite;
   }

   public BridgeMethodWeavingTestCase(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void testMethod()
   {
      POJO p = new POJO("bar");
      p.getFoo();
      assertTrue(SimpleMethodInterceptor.method);
     
   }
   
   public void testGenericMethod()
   {
      //doesnt work with jbossretro atm..
      String version = (String)((AOPTestDelegate)getDelegate()).getSystemProperties().get("java.vm.version"); 
      if(version.startsWith("1.5"))
      {
         SimpleMethodInterceptor.method = false;
         SubPOJO spojo = new SubPOJO();
         spojo.setFoo(new ArrayList());
         assertTrue(SimpleMethodInterceptor.method);
      }
   }
   
   public void testMethodOverride()
   {
      SimpleMethodInterceptor.method = false;
      SubPOJO2 spojo2 = new SubPOJO2();
      spojo2.setFoo("bar");
      assertTrue(SimpleMethodInterceptor.method);
   }

   public void testMethodOverrideNotWeaved()
   {
      SimpleMethodInterceptor.method = false;
      SubPOJO2 spojo2 = new SubPOJO2();
      spojo2.setFoo(new POJO());
      assertFalse(SimpleMethodInterceptor.method);
   }


}
