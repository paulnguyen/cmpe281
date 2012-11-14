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
package org.jboss.test.aop.invoketarget;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class InvokeTargetTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ProxyTester");
      suite.addTestSuite(InvokeTargetTestCase.class);
      return suite;
   }

   public InvokeTargetTestCase(String name)
   {
      super(name);
   }
   
   public void testExecution() throws Exception
   {
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      POJO pojo = new POJO("Test");
      assertEquals("Test", pojo.plain); //not advised
      
      //2 invocations for both of these since we have constructor and construction advices
      assertEquals(2, BypassInterceptor.invocations);
      assertEquals(2, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.i = 5;
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);

      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      int i = pojo.i;
      assertEquals(5, i);
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.s = "Blah";
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);

      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      String str = pojo.s;
      assertEquals("Blah", str);
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.test();
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      String s = pojo.echo("Hello");
      assertEquals("Hello", s);
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      i = pojo.echo(100);
      assertEquals(100, i);
      assertEquals(1, BypassInterceptor.invocations);
      assertEquals(1, CountingInterceptor.invocations);
   }
   
   public void testConstructorCaller()
   {
      CallingPOJO pojo = new CallingPOJO();
   }
   
   public void testStaticMethodCaller()
   {
      CallingPOJO.testStatic();
   }
   
   public void testMethodCaller()
   {
      CallingPOJO pojo = new CallingPOJO();
      pojo.test();
   }
}
