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
package org.jboss.test.aop.beanstyleconfig;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.ArrayList;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class ConfigTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("ConfigTester");
      suite.addTestSuite(ConfigTester.class);
      return suite;
   }

   public ConfigTester(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      System.out.println("setup");
      super.setUp();
      InvokedConfigs.clearData();
   }

   public void testPerVm() throws Exception
   {
      System.out.println("****** testPerVm");
      POJO pojo = new POJO();
      pojo.perVmMethod();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerVm", 2, invoked.size());

      System.out.println("****** testPerVm (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerVm", InterceptorPerVm.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerVm", 11, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerVm", InterceptorPerVm.class.getName(), invokedConfig.stringAttr);
      assertNull("Advisor was not null in interceptor in testPerVm", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in interceptor in testPerVm", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in interceptor in testPerVm", invokedConfig.joinpoint);

      System.out.println("****** testPerVm (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerVm", AdvicePerVm.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerVm", 21, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerVm", AdvicePerVm.class.getName(), invokedConfig.stringAttr);
      assertNull("Advisor was not null in aspect in testPerVm", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in aspect in testPerVm", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in aspect in testPerVm", invokedConfig.joinpoint);
   }

   public void testPerVmStatic() throws Exception
   {
      System.out.println("****** testPerVmStatic");
      POJO.perVmMethodStatic();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerVmStatic", 2, invoked.size());

      System.out.println("****** testPerVmStatic (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerVmStatic", InterceptorPerVm.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerVmStatic", 11, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerVmStatic", InterceptorPerVm.class.getName(), invokedConfig.stringAttr);
      assertNull("Advisor was not null in interceptor in testPerVmStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in interceptor in testPerVmStatic", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in interceptor in testPerVmStatic", invokedConfig.joinpoint);

      System.out.println("****** testPerVmStatic (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerVmStatic", AdvicePerVm.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerVmStatic", 21, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerVmStatic", AdvicePerVm.class.getName(), invokedConfig.stringAttr);
      assertNull("Advisor was not null in aspect in testPerVmStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in aspect in testPerVmStatic", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in aspect in testPerVmStatic", invokedConfig.joinpoint);
   }

   public void testPerClass() throws Exception
   {
      System.out.println("****** testPerClass");
      POJO pojo = new POJO();
      pojo.perClassMethod();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerClass", invoked.size(), 2);

      System.out.println("****** testPerClass (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerClass", InterceptorPerClass.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerClass", 12, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerClass", InterceptorPerClass.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in interceptor in testPerClass", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in interceptor in testPerClass", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in interceptor in testPerClass", invokedConfig.joinpoint);

      System.out.println("****** testPerClass (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerClass", AdvicePerClass.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerClass", 22, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerClass", AdvicePerClass.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in aspect in testPerClass", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in aspect in testPerClass", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in aspect in testPerClass", invokedConfig.joinpoint);

   }

   public void testPerClassStatic() throws Exception
   {
      System.out.println("****** testPerClassStatic");
      POJO.perClassMethodStatic();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerClassStatic", 2, invoked.size());

      System.out.println("****** testPerClassStatic (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerClassStatic", InterceptorPerClass.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerClassStatic", 12, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerClassStatic", InterceptorPerClass.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in interceptor in testPerClassStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in interceptor in testPerClassStatic", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in interceptor in testPerClassStatic", invokedConfig.joinpoint);

      System.out.println("****** testPerClassStatic (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerClassStatic", AdvicePerClass.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerClassStatic", 22, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerClassStatic", AdvicePerClass.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in aspect in testPerClassStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in aspect in testPerClassStatic", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in aspect in testPerClassStatic", invokedConfig.joinpoint);

   }

   public void testPerInstance() throws Exception
   {
      System.out.println("****** testPerInstance");
      POJO pojo = new POJO();
      pojo.perInstanceMethod();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerInstance", 2, invoked.size());

      System.out.println("****** testPerInstance (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerInstance", InterceptorPerInstance.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerInstance", 13, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerInstance", InterceptorPerInstance.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in interceptor in testPerInstance", invokedConfig.advisor);
      assertNotNull("InstanceAdvisor was null in interceptor in testPerInstance", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in interceptor in testPerInstance", invokedConfig.joinpoint);

      System.out.println("****** testPerInstance (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerInstance", AdvicePerInstance.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerInstance", 23, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerInstance", AdvicePerInstance.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in aspect in testPerInstance", invokedConfig.advisor);
      assertNotNull("InstanceAdvisor was null in aspect in testPerInstance", invokedConfig.instanceAdvisor);
      assertNull("Joinpoint was not null in aspect in testPerInstance", invokedConfig.joinpoint);


   }

   public void testPerInstanceStatic() throws Exception
   {
      System.out.println("****** testPerInstanceStatic");
      POJO.perInstanceMethodStatic();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerInstanceStatic", invoked.size(), 0);

   }

   public void testPerJoinpoint() throws Exception
   {
      System.out.println("****** testPerJoinpoint");
      POJO pojo = new POJO();
      pojo.perJoinpointMethod();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerJoinpoint", 2, invoked.size());

      System.out.println("****** testPerJoinpoint (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerJoinpoint", InterceptorPerJoinpoint.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerJoinpoint", 14, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerJoinpoint", InterceptorPerJoinpoint.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in interceptor in testPerJoinpoint", invokedConfig.advisor);
      assertNotNull("InstanceAdvisor was null in interceptor in testPerJoinpoint", invokedConfig.instanceAdvisor);
      assertNotNull("Joinpoint was null in interceptor in testPerJoinpoint", invokedConfig.joinpoint);

      System.out.println("****** testPerJoinpoint (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerJoinpoint", AdvicePerJoinpoint.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerJoinpoint", 24, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerJoinpoint", AdvicePerJoinpoint.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in aspect in testPerJoinpoint", invokedConfig.advisor);
      assertNotNull("InstanceAdvisor was null in aspect in testPerJoinpoint", invokedConfig.instanceAdvisor);
      assertNotNull("Joinpoint was null in aspect in testPerJoinpoint", invokedConfig.joinpoint);
   }

   public void testPerJoinpointStatic() throws Exception
   {
      System.out.println("****** testPerJoinpointStatic");
      POJO.perJoinpointMethodStatic();

      ArrayList invoked = InvokedConfigs.getInvokedConfigs();
      assertEquals("Wrong number of interceptions in testPerJoinpointStatic", 2, invoked.size());

      System.out.println("****** testPerJoinpointStatic (aspect)");
      InvokedConfig invokedConfig = (InvokedConfig) invoked.get(0);
      assertEquals("Wrong interceptor class in testPerJoinpointStatic", InterceptorPerJoinpoint.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute for interceptor in testPerJoinpointStatic", 14, invokedConfig.intAttr);
      assertEquals("Wrong aspect string for interceptor in testPerJoinpointStatic", InterceptorPerJoinpoint.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in interceptor in testPerJoinpointStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in interceptor in testPerJoinpointStatic", invokedConfig.instanceAdvisor);
      assertNotNull("Joinpoint was null in interceptor in testPerJoinpointStatic", invokedConfig.joinpoint);

      System.out.println("****** testPerJoinpointStatic (interceptor)");
      invokedConfig = (InvokedConfig) invoked.get(1);
      assertEquals("Wrong aspect class in testPerJoinpointStatic", AdvicePerJoinpoint.class.getName(), invokedConfig.name);
      assertEquals("Wrong aspect int attribute in testPerJoinpointStatic", 24, invokedConfig.intAttr);
      assertEquals("Wrong aspect string attribute in testPerJoinpointStatic", AdvicePerJoinpoint.class.getName(), invokedConfig.stringAttr);
      assertNotNull("Advisor was null in aspect in testPerJoinpointStatic", invokedConfig.advisor);
      assertNull("InstanceAdvisor was not null in aspect in testPerJoinpointStatic", invokedConfig.instanceAdvisor);
      assertNotNull("Joinpoint was null in aspect in testPerJoinpointStatic", invokedConfig.joinpoint);
   }

}
