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
package org.jboss.test.aop.regression.jbaop484superproxyadvised;

import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.proxy.container.AOPProxyFactory;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.AspectManaged;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SuperClassIsAdvisedByProxyTestCase extends TestCase
{
   AOPProxyFactory proxyFactory = new GeneratedAOPProxyFactory();

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("SuperClassIsAdvisedByProxyTestCase");
      suite.addTestSuite(SuperClassIsAdvisedByProxyTestCase.class);
      return suite;
   }

   public SuperClassIsAdvisedByProxyTestCase(String name)
   {
      super(name);
   }

   public void testClassAdvisorAndNotInstanceAdvisor() throws Exception
   {
      System.out.println("== Testing proxy");
      Base base = new Base();
      assertFalse(base instanceof Advised);
      
      String name = addBinding("execution(* org.jboss.test.aop.regression.jbaop484superproxyadvised.Base->*(..))", TestInterceptor.class);
      try
      {
         Object proxy = createProxy(base);
         assertTrue(proxy instanceof AspectManaged);
         assertFalse(proxy instanceof Advised);
         
         TestInterceptor.reset();
         base.baseOnly();
         assertEquals(0, TestInterceptor.interceptions);
         
         TestInterceptor.reset();
         base.baseOverridden();
         assertEquals(0, TestInterceptor.interceptions);
         
         TestInterceptor.reset();
         ((Base)proxy).baseOnly();
         assertEquals(1, TestInterceptor.interceptions);
         assertNotNull(TestInterceptor.invoked);
         assertEquals("baseOnly", TestInterceptor.invoked.getName());
         
         TestInterceptor.reset();
         ((Base)proxy).baseOverridden();
         assertEquals(1, TestInterceptor.interceptions);
         assertNotNull(TestInterceptor.invoked);
         assertEquals("baseOverridden", TestInterceptor.invoked.getName());
      }
      finally
      {
         removeBinding(name);
      }
      
      System.out.println("== Testing advised child");
      name = addBinding("execution(* org.jboss.test.aop.regression.jbaop484superproxyadvised.Base->*(..))", TestInterceptor.class);
      try
      {
         Child plainChild = new Child();
         assertTrue(plainChild instanceof Advised);
         assertFalse(plainChild instanceof AspectManaged);
         
         TestInterceptor.reset();
         plainChild.baseOnly();
         assertEquals(0, TestInterceptor.interceptions);
   
         TestInterceptor.reset();
         plainChild.childOnly();
         assertEquals(1, TestInterceptor.interceptions);
   
         TestInterceptor.reset();
         plainChild.baseOverridden();
         assertEquals(1, TestInterceptor.interceptions);
      }
      finally
      {
         removeBinding(name);
      }

      System.out.println("== Testing proxied child");
      name = addBinding("execution(* org.jboss.test.aop.regression.jbaop484superproxyadvised.Base->*(..))", TestInterceptor.class);
      Object proxy = null;
      try
      {
   
         Child proxiedChild = new Child();
         assertTrue(proxiedChild instanceof Advised);
         assertFalse(proxiedChild instanceof AspectManaged);
   
         proxy = createProxy(proxiedChild, new Class[] {ChildInterface.class});
         
         TestInterceptor.reset();
         ((Child)proxy).baseOnly();
         assertEquals(1, TestInterceptor.interceptions);
         assertNotNull(TestInterceptor.invoked);
         assertEquals("baseOnly", TestInterceptor.invoked.getName());
   
         TestInterceptor.reset();
         ((Child)proxy).childOnly();
         assertEquals(2, TestInterceptor.interceptions);
         assertNotNull(TestInterceptor.invoked);
         assertEquals("childOnly", TestInterceptor.invoked.getName());
   
         TestInterceptor.reset();
         ((Child)proxy).baseOverridden();
         assertEquals(2, TestInterceptor.interceptions);
         assertNotNull(TestInterceptor.invoked);
         assertEquals("baseOverridden", TestInterceptor.invoked.getName());
      }
      finally
      {
         removeBinding(name);
      }
      
      TestInterceptor.reset();
      ((Child)proxy).baseOnly();
      assertEquals(1, TestInterceptor.interceptions);
      
   }
   
   private String addBinding(String pointcut, Class<?> interceptor) throws ParseException
   {
      AdviceBinding binding = new AdviceBinding(pointcut, null);
      String name = binding.getName();
      binding.addInterceptor(interceptor);
      AspectManager.instance().addBinding(binding);
      return name;
   }
   
   private void removeBinding(String name)
   {
      AspectManager.instance().removeBinding(name);
   }
   
   private Object createProxy(Object target)
   {
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setProxiedClass(target.getClass());
      params.setTarget(target);
      return proxyFactory.createAdvisedProxy(params);
   }

   protected Object createProxy(Object target, Class<?>[] interfaces) throws Exception
   {
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setProxiedClass(target.getClass());
      params.setInterfaces(interfaces);
      params.setTarget(target);
      return proxyFactory.createAdvisedProxy(params);
   }

}


