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
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.stack.MetaDataStack;
import org.jboss.test.aop.integration.metadatadomain.support.TestIntercepted;
import org.jboss.test.aop.integration.metadatadomain.support.TestInterceptor;

/**
 * FIXME This test requires a classloading snapshot before it can be run
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class MetaDataDomainProxyForSeveralDomainsUnitTestCase extends NotWovenAOPIntegrationTest
{
   public MetaDataDomainProxyForSeveralDomainsUnitTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      System.setProperty("jboss.test.parent.pkgs", "org.jboss.metadata.spi.repository, org.jboss.metadata.spi.retrieval, org.jboss.metadata.spi.scope");
      return suite(MetaDataDomainProxyForSeveralDomainsUnitTestCase.class, TestIntercepted.class);
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
    * Push a domain onto the metadata stack and deploy into that domain.
    * Create a proxy for that domain.
    */
   public void testDomain() throws Exception
   {
      URL[] urls = new URL[2];
      Domain[] domains = new Domain[2];
      try
      {
         //Create our instance to be proxied before we start deploying stuff
         TestIntercepted test = new TestIntercepted();
         
         TestIntercepted proxy1 = createDomainAndProxy("1", this.getClass().getClassLoader(), test, 0, urls, domains);
         TestIntercepted proxy2 = createDomainAndProxy("2", this.getClass().getClassLoader(), test, 1, urls, domains);
         
         assertFalse(proxy1.getClass() == TestIntercepted.class);
         assertFalse(proxy2.getClass() == TestIntercepted.class);
         
         TestInterceptor.intercepted = null;
         proxy1.doSomething();
         assertNotNull(TestInterceptor.intercepted);
         assertEquals("First", TestInterceptor.intercepted);
         
         TestInterceptor.intercepted = null;
         proxy2.doSomething();
         assertNotNull(TestInterceptor.intercepted);
         assertEquals("Second", TestInterceptor.intercepted);
      }
      finally
      {
         while (MetaDataStack.peek() != null)
         {
            MetaDataStack.pop();
         }
         for (int i = 0 ; i < urls.length ; i++)
         {
            if (urls[i] != null)
            {
               undeploy(urls[i], domains[i]);
            }
         }
      }
   }

   private TestIntercepted createDomainAndProxy(String suffix, ClassLoader cl, TestIntercepted test, int i, URL[] urls, Domain[] domains) throws Exception
   {
      //Deployer creates metadata and populates that with the necessary domain_1
      Helper.createAndPushMetaData(Helper.createScopedDomain("xxx"));
      
      //We get the domain_1 out of the metadata and deploy into that
      MetaData md = MetaDataStack.peek();
      domains[i] = md.getMetaData(Domain.class);
      assertNotNull(domains[0]);
      urls[i] = deploy("1", this.getClass().getClassLoader(), domains[0]);
      
      //create the proxy, the scoped manger is taken from the metadata stack
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setTarget(test);
      params.setMetaData(md);
      
      AOPProxyFactory factory = new GeneratedAOPProxyFactory();
      return (TestIntercepted)factory.createAdvisedProxy(params);       
   }
   
}
