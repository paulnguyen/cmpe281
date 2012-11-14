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
package org.jboss.test.aop.construction;

import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.basic.POJO;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unused"})
public class ConstructionTester extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ConstructionTester");
      suite.addTestSuite(ConstructionTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public ConstructionTester(String name)
   {
      super(name);
   }

   // Public -------------------------------------------------------

   public void testConstruction()
   {
      System.out.println("RUNNING TEST Construction");
      POJO pojo = new POJO();
      DefaultPOJO d = new DefaultPOJO();
   }
   
   public void testPerInstance()
   {
      PerInstanceAspect.construction = false;
      PerInstanceAspect.invocation = false;
      PerInstancePOJO pojo = new PerInstancePOJO();
      assertTrue(PerInstanceAspect.construction);
      assertTrue(PerInstanceAspect.invocation);
   }
}

