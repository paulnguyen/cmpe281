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
package org.jboss.test.aop.callerargs;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class CallerArgsTestCase extends AOPTestWithSetup
{
   public CallerArgsTestCase(String name)
   {
      super(name);
   }

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(CallerArgsTestCase.class);
   }

   public void testExecutionAdvisedClass() throws Exception
   {
      System.out.println("TESTING EXECUTION ADVISED");
      ExecutionInterceptor.intercepted = false;
      AdvisedPOJO.voidStaticNoArgsMethod();
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if (ExecutionInterceptor.argsLength != 0) throw new RuntimeException("Args length should have been 0");
      
      ExecutionInterceptor.intercepted = false;
      int ret = AdvisedPOJO.intStaticArgsMethod(3);
      if (ret != 6) throw new RuntimeException("Expected 6 as return value");
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if (ExecutionInterceptor.argsLength != 1) throw new RuntimeException("Args length should have been 1");
      if (((Integer)ExecutionInterceptor.args[0]).intValue() != 3) throw new RuntimeException("Args[0] should have been 3");
      
      ExecutionInterceptor.intercepted = false;
      AdvisedPOJO pojo = new AdvisedPOJO();
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if (ExecutionInterceptor.argsLength != 0) throw new RuntimeException("Args length should have been 0");
      
      ExecutionInterceptor.intercepted = false;
      pojo.voidNoArgsMethod();
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if (ExecutionInterceptor.argsLength != 0) throw new RuntimeException("Args length should have been 0");
      
      ExecutionInterceptor.intercepted = false;
      AdvisedPOJO pojo2 = new AdvisedPOJO(10, 100);
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if(pojo2.i != 10) throw new RuntimeException("Expected pojo.i to be 10");
      if(pojo2.j != 100) throw new RuntimeException("Expected pojo.i to be 100");
      if (ExecutionInterceptor.argsLength != 2) throw new RuntimeException("Args length should have been 2");
      if (((Integer)ExecutionInterceptor.args[0]).intValue() != 10) throw new RuntimeException("Args[0] should have been 10");
      if (((Integer)ExecutionInterceptor.args[1]).intValue() != 100) throw new RuntimeException("Args[1] should have been 100");
      
      ExecutionInterceptor.intercepted = false;
      ret = pojo2.intArgsMethod(20, 100);
      if (!ExecutionInterceptor.intercepted) throw new RuntimeException("Did not intercept");
      if (ret != 2000)throw new RuntimeException("Expected 2000 as return value");
      if (((Integer)ExecutionInterceptor.args[0]).intValue() != 20) throw new RuntimeException("Args[0] should have been 20");
      if (((Integer)ExecutionInterceptor.args[1]).intValue() != 100) throw new RuntimeException("Args[1] should have been 100");
   }
   
   public void testCallerCallingNotAdvisedClass() throws Exception
   {
      System.out.println("TESTING CALLING NOT ADVISED");
      NotAdvisedPOJOCaller caller = new NotAdvisedPOJOCaller();
      caller.method();
      NotAdvisedPOJOCaller.staticMethod();
   }
   
   public void testCallerCallingAdvisedClass() throws Exception
   {
      System.out.println("TESTING CALLING ADVISED");      
      AdvisedPOJOCaller caller = new AdvisedPOJOCaller();
      caller.method();
      AdvisedPOJOCaller.staticMethod();
   }
   
   public void testPreparedCaller() throws Exception
   {
      System.out.println("TESTING CALLING PREPARED");
      PreparedPOJOCaller caller = new PreparedPOJOCaller();
      caller.method();
      PreparedPOJOCaller.staticMethod();
   }
   
}
