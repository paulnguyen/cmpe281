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
package org.jboss.test.aop.dynamicinvoke;

import java.lang.reflect.Method;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.util.MethodHashing;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests {@link Advisor#dynamicInvoke(Object, org.jboss.aop.joinpoint.Invocation)}
 * method.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class DynamicInvokeTestCase extends AOPTestWithSetup
{
   Advisor advisor;
   POJO pojo;
   
   public DynamicInvokeTestCase(String name)
   {
      super(name);
   }
  
   public void setUp() throws Exception
   {
      super.setUp();
      this.pojo = new POJO();
      this.advisor = ((Advised) pojo)._getAdvisor();
      // check prerequisites
      pojo.advised();
      assertTrue(MyInterceptor.isInvoked());
      MyInterceptor.clearHistory();
      pojo.unadvised();
      assertFalse(MyInterceptor.isInvoked());
      MyInterceptor.clearHistory();
   }
   
   public void testDynamicInvokeAdvisedMethod() throws Throwable
   {
      invokeMethodDynamically("advised", true);
   }
   
   public void testDynamicInvokeUnadvisedMethod() throws Throwable
   {
      invokeMethodDynamically("unadvised", false);
   }
   
   public void invokeMethodDynamically(String methodName, boolean intercepted) throws Throwable
   {
      Method method = POJO.class.getDeclaredMethod(methodName);
      long hash = MethodHashing.calculateHash(method);
      
      MethodInvocation methodInvocation  = new MethodInvocation(null, hash,
            method, null, advisor);
      advisor.dynamicInvoke(pojo, methodInvocation);
      
      assertSame(intercepted, MyInterceptor.isInvoked());
      assertEquals(methodName, POJO.getLastRun());
   }
}