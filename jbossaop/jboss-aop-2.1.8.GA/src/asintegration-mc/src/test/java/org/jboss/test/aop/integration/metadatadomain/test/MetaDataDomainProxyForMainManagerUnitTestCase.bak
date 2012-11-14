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
package org.jboss.test.aop.integration.metadatadomain.test;

import java.net.URL;

import junit.framework.Test;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.asintegration.jboss5.MetaDataClassLoaderScopingPolicy;
import org.jboss.aop.integration.junit.NotWovenAOPIntegrationTest;
import org.jboss.aop.proxy.container.AOPProxyFactory;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;
import org.jboss.test.aop.integration.metadatadomain.support.TestIntercepted;
import org.jboss.test.aop.integration.metadatadomain.support.TestInterceptor;

/**
 * FIXME This test requires a classloading snapshot before it can be run
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class MetaDataDomainProxyForMainManagerUnitTestCase extends NotWovenAOPIntegrationTest
{
   public MetaDataDomainProxyForMainManagerUnitTestCase(String name)
   {
      // FIXME SimpleMetaDataDomainProxyTestCase constructor
      super(name);
   }

   public static Test suite()
   {
      return suite(MetaDataDomainProxyForMainManagerUnitTestCase.class, TestIntercepted.class);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
      AspectManager.setClassLoaderScopingPolicy(new MetaDataClassLoaderScopingPolicy());
   }

   @Override
   protected void tearDown() throws Exception
   {
      super.tearDown();
      AspectManager.setClassLoaderScopingPolicy(null);
   }

   /**
    * Create a domain and deploy into that domain.
    * 
    */
   public void testDomain() throws Exception
   {
      URL url = null;
      Domain domain = null;
      try
      {
         
         //Create our instance to be proxied before we start deploying stuff
         TestIntercepted test = new TestIntercepted();
         
         //Create a domain and deploy into that
         domain = Helper.createScopedDomain("xxx");
         url = deploy("1", Thread.currentThread().getContextClassLoader(), domain);
         
         //create the proxy, it uses the main manager
         AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
         params.setTarget(test);
         params.setMetaData(null);
         
         AOPProxyFactory factory = new GeneratedAOPProxyFactory();
         TestIntercepted proxy = (TestIntercepted)factory.createAdvisedProxy(params);
         assertTrue(proxy.getClass() == TestIntercepted.class);
                  
         TestInterceptor.intercepted = null;
         proxy.doSomething();
         assertNull(TestInterceptor.intercepted);
      }
      finally
      {
         undeploy(url, domain);
      }
   }
}
