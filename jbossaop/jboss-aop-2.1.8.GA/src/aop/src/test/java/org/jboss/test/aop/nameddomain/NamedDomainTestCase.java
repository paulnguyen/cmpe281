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
package org.jboss.test.aop.nameddomain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.AspectManaged;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * This is only relevant for generated advisors
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class NamedDomainTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("NamedDomainTestCase");
      suite.addTestSuite(NamedDomainTestCase.class);
      return suite;
   }

   public NamedDomainTestCase(String name)
   {
      super(name);
   }

   public void testWovenNamedDomains()
   {
      AspectManager manager = AspectManager.instance();;
      POJO pojo = new POJO();
      pojo.someMethod();
      
      Advisor advisor = ((Advised)pojo)._getAdvisor();
      AspectManager domain = advisor.getManager();
      String fqn = domain.getManagerFQN();
      
      System.out.println("fqn: " + fqn);
      
      AspectManager foundDomain = manager.findManagerByName(fqn);
      assertSame(domain, foundDomain);
      
      InstanceAdvisor ia = ((Advised)pojo)._getInstanceAdvisor();
      assertNotNull(ia);
      AspectManager instanceDomain = ((Advisor)ia).getManager();
      fqn = instanceDomain.getManagerFQN();
      System.out.println("fqn: " + fqn);
      
      AspectManager foundInstanceDomain = manager.findManagerByName(fqn);
      assertSame(instanceDomain, foundInstanceDomain);
      
      for (Field field : pojo.getClass().getDeclaredFields())
      {
         System.out.println("F " + field.getName() + " - " + field.isSynthetic());
      }
      for (Method method : pojo.getClass().getDeclaredMethods())
      {
         System.out.println("M " + method.getName() + " - " + method.isSynthetic());
      }
   }
   
   public void testProxiedNamedDomains() throws Exception
   {
      Proxied tgt = new Proxied();//Load the class before we deploy the xml that will cause weaving
      
      URL url = getURLRelativeToProjectRoot("/src/resources/test/nameddomain/manual-aop.xml");
      AspectXmlLoader.deployXML(url);
      
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setTarget(tgt);
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      Proxied proxied = (Proxied)factory.createAdvisedProxy(params);
      
      proxied.someMethod();
      
      Advisor advisor = ((AspectManaged)proxied).getAdvisor();
      assertNotNull(advisor);
      AspectManager domain = advisor.getManager();
      String fqn = domain.getManagerFQN();
      AspectManager foundDomain = AspectManager.instance().findManagerByName(fqn);
      assertSame(domain, foundDomain);
      
      InstanceAdvisor instanceAdvisor = ((AspectManaged)proxied).getInstanceAdvisor();
      assertNotNull(instanceAdvisor);
      AspectManager instanceDomain = advisor.getManager();
      fqn = instanceDomain.getManagerFQN();
      AspectManager foundInstanceDomain = AspectManager.instance().findManagerByName(fqn);
      assertSame(instanceDomain, foundInstanceDomain);
   }

}
