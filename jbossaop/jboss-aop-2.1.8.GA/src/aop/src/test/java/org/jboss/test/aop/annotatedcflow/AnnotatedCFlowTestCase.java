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
package org.jboss.test.aop.annotatedcflow;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * So far only the class uses annotations. Since StackTraceElement only returns strings, 
 * we have no way of distinguishing between overloaded methods/ctors 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 46001 $
 */
public class AnnotatedCFlowTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotatedCFlowTestCase");
      suite.addTestSuite(AnnotatedCFlowTestCase.class);
      return suite;
   }

   public AnnotatedCFlowTestCase(String name)
   {
      super(name);
   }

   public void testNotTriggeredCaller() throws Exception
   {
      TestInterceptor.intercepted = false;
      NotTriggeredCaller caller = new NotTriggeredCaller();
      caller.instantiate();
      assertFalse(TestInterceptor.intercepted);

      TestInterceptor.intercepted = false;
      caller.makeCall();
      assertFalse(TestInterceptor.intercepted);
   }
   
   public void testNotAnnotatedCaller() throws Exception
   {
      TestInterceptor.intercepted = false;
      NotAnnotatedCaller caller = new NotAnnotatedCaller();
      caller.instantiate();
      assertTrue(TestInterceptor.intercepted);

      TestInterceptor.intercepted = false;
      caller.makeCall();
      assertTrue(TestInterceptor.intercepted);
   }
   
   public void testAnnotatedCaller() throws Exception
   {
      TestInterceptor.intercepted = false;
      AnnotatedCaller caller = new AnnotatedCaller();
      caller.instantiate();
      assertTrue(TestInterceptor.intercepted);

      TestInterceptor.intercepted = false;
      caller.makeCall();
      assertTrue(TestInterceptor.intercepted);
   }
   
   public void testInstanceOfCaller() throws Exception
   {
      TestInterceptor.intercepted = false;
      InstanceOfInterfaceCaller caller = new InstanceOfInterfaceCaller();
      caller.instantiate();
      assertTrue(TestInterceptor.intercepted);

      TestInterceptor.intercepted = false;
      caller.makeCall();
      assertTrue(TestInterceptor.intercepted);
   }
   
}
