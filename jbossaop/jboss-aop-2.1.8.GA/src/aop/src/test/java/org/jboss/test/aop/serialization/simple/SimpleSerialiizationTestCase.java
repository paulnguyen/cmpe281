/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.serialization.simple;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.AspectManaged;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A test case to test that woven objects can be serialized. This does not take into 
 * account anything deployed "below" the main aspect manager or per instance aspects.
 * The main idea is to test reattachment within the same vm
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SimpleSerialiizationTestCase extends AOPTestWithSetup
{

   public static Test suite()
   {
      TestSuite suite = new TestSuite("SimpleSerialiizationTestCase");
      suite.addTestSuite(SimpleSerialiizationTestCase.class);
      return suite;
   }
   
   public SimpleSerialiizationTestCase(String name)
   {
      super(name);
   }
   
   public void testWoven() throws Exception
   {
      SimpleInterceptor.invoked = false;
      POJO pojo = new POJO();
      pojo.someMethod();
      assertTrue(SimpleInterceptor.invoked);
      
      byte[] bytes = serialize(pojo);
      POJO newPojo = (POJO)deserialize(bytes);
      
      assertNotNull(newPojo);
      SimpleInterceptor.invoked = false;
      newPojo.someMethod();
      assertTrue(SimpleInterceptor.invoked);
      
      Proxied proxied = new Proxied();
      SimpleInterceptor.invoked = false;
      proxied.someMethod();
      assertFalse(SimpleInterceptor.invoked);
      
      Advisor advisor = ((Advised)pojo)._getAdvisor();
      Advisor newAdvisor = ((Advised)newPojo)._getAdvisor();
      assertSame(advisor, newAdvisor);
   }

   public void testProxy() throws Exception
   {
      Proxied tgt = new Proxied();
      URL url = getURLRelativeToProjectRoot("/src/resources/test/serialization/simple/manual-aop.xml");
      AspectXmlLoader.deployXML(url);

      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setTarget(tgt);
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      Proxied proxied = (Proxied)factory.createAdvisedProxy(params);
      
      proxied.someMethod();
      assertTrue(SimpleInterceptor.invoked);
      
      byte[] bytes = serialize(proxied);
      Proxied newProxied = (Proxied)deserialize(bytes);
      
      assertNotNull(newProxied);
      SimpleInterceptor.invoked = false;
      newProxied.someMethod();
      assertTrue(SimpleInterceptor.invoked);
      
      Advisor advisor = ((AspectManaged)proxied).getAdvisor();
      Advisor newAdvisor = ((AspectManaged)newProxied).getAdvisor();
      assertSame(advisor, newAdvisor);
   }

}
