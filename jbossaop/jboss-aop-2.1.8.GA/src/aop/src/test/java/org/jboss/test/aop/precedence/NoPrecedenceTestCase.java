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
package org.jboss.test.aop.precedence;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests if, when no precedence rule has been declared, the expected advice order
 * is the same order they were declared in the bindings.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class NoPrecedenceTestCase extends AOPTestWithSetup
{
   private NoPrecedencePOJO pojo;
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite("NoPrecedenceTestCase");
      suite.addTestSuite(NoPrecedenceTestCase.class);
      return suite;
   }

   public NoPrecedenceTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      pojo = new NoPrecedencePOJO();
      Interceptions.reset();
   }

   public void testConstructor() throws Exception
   {
      System.out.println("*** Invoke constructor");
      new NoPrecedencePOJO();
      //Don't list per_instance aspects for constructor
      checkInterceptions("SimpleInterceptor4", "SimpleInterceptor5",
            "SimpleInterceptor7", "SimpleInterceptor8", "TestAspect4.advice",
            "TestAspect5.advice", "TestAspect7.advice", "TestAspect8.advice");
   }
   
   @SuppressWarnings("all")
   public void testFieldRead()
   {
      System.out.println("*** Invoke field read");
      int i = pojo.var;
      checkInterceptions("SimpleInterceptor8", "SimpleInterceptor7",
            "SimpleInterceptor6", "SimpleInterceptor5", "SimpleInterceptor4",
            "TestAspect6.advice", "TestAspect4.advice", "TestAspect5.advice",
            "TestAspect7.advice", "TestAspect8.advice");
   }
   
   public void testFieldWrite()
   {
      System.out.println("*** Invoke field write");
      Interceptions.reset();
      pojo.var = 1;
      checkInterceptions("SimpleInterceptor8", "SimpleInterceptor7",
            "SimpleInterceptor6", "SimpleInterceptor5", "SimpleInterceptor4",
            "TestAspect6.advice", "TestAspect4.advice", "TestAspect5.advice",
            "TestAspect7.advice", "TestAspect8.advice");
   }
   
   public void testOneMethodExecution()
   {
      System.out.println("*** Invoke oneMethod");
      pojo.oneMethod();
      checkInterceptions("SimpleInterceptor4", "TestAspect6.advice",
            "SimpleInterceptor5", "TestAspect4.advice", "SimpleInterceptor6",
            "TestAspect5.advice", "SimpleInterceptor7", "TestAspect7.advice",
            "SimpleInterceptor8", "TestAspect8.advice");
   }
   
   public void testTwoMethodExecution()
   {
      pojo.twoMethod();
      checkInterceptions("TestAspect4.advice", "TestAspect4.advice2",
            "TestAspect4.advice3", "TestAspect5.advice", "TestAspect7.advice",
            "TestAspect8.advice", "SimpleInterceptor4", "SimpleInterceptor5",
            "SimpleInterceptor6");
   }
   
   public void testThreeMethodExecution()
   {
      pojo.threeMethod();
      checkInterceptions("SimpleInterceptor5", "SimpleInterceptor4",
            "TestAspect4.advice", "TestAspect4.advice2", "TestAspect4.advice3",
            "TestAspect7.advice", "TestAspect8.advice");
   }
   
   public void testFiveMethodCall()
   {
      pojo.fourMethod();
      checkInterceptions("TestAspect4.advice", "TestAspect4.advice2",
            "TestAspect4.advice3", "TestAspect5.advice", "TestAspect7.advice",
            "TestAspect8.advice", "SimpleInterceptor4", "SimpleInterceptor5",
            "SimpleInterceptor6");
   }
   
   public void testConstructorCall()
   {
      NoPrecedencePOJO.factoryMethod();
      checkInterceptions("SimpleInterceptor5", "SimpleInterceptor4",
            "TestAspect4.advice", "TestAspect4.advice2", "TestAspect4.advice3",
            "TestAspect7.advice", "TestAspect8.advice");
   }

   private void checkInterceptions(String... expected)
   {
      List<String> intercepted = Interceptions.intercepted;
      assertEquals("Wrong number of interceptions", expected.length, intercepted.size());
      
      for (int i = 0 ; i < expected.length ; i++)
      {
         assertEquals("Wrong interception at index " + i, expected[i], intercepted.get(i));
      }
   }
}
