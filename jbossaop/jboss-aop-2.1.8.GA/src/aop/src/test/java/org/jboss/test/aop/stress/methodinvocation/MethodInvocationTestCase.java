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
package org.jboss.test.aop.stress.methodinvocation;

import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.ScenarioTest;

/**
 * Primarily to make sure I got everything right for the generated advisors
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 45109 $
 */
public class MethodInvocationTestCase extends ScenarioTest
{
   
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(MethodInvocationTestCase.class);
   }

   
   public MethodInvocationTestCase(String name) throws Exception
   {
      super(name);
   }

   
   public void testStaticWovenNoAdvice() throws Exception
   {
      getRunner().executeScenario(new StaticWovenNoAdviceScenario(), this);
   }
   
   private class StaticWovenNoAdviceScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         POJO.staticMethodNoAdvice();
      }
   }
   
   public void testNonStaticWovenNoAdvice() throws Exception
   {
      getRunner().executeScenario(new NonStaticWovenNoAdviceScenario(), this);
   }

   private class NonStaticWovenNoAdviceScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.nonStaticMethodNoAdvice();
      }
   }
   
   public void testNonStaticWithOneInterceptor() throws Exception
   {
      POJO pojo = new POJO();
      PlainInterceptor.called = 0;
      pojo.oneInterceptor();
      assertEquals(1, PlainInterceptor.called);
      getRunner().executeScenario(new NonStaticWithOneInterceptorScenario(), this);
   }
   
   private class NonStaticWithOneInterceptorScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.oneInterceptor();
      }
   }
   
   public void testNonStaticWithFiveInterceptors() throws Exception
   {
      POJO pojo = new POJO();
      PlainInterceptor.called = 0;
      pojo.fiveInterceptors();
      assertEquals(5, PlainInterceptor.called);
      getRunner().executeScenario(new NonStaticWithFiveInterceptorsScenario(), this);
   }
   
   private class NonStaticWithFiveInterceptorsScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.fiveInterceptors();
      }
   }   
   
   public void testNonStaticWithOneAdvice() throws Exception
   {
      POJO pojo = new POJO();
      PlainAspect.called = 0;
      pojo.oneAdvice();
      assertEquals(1, PlainAspect.called);
      getRunner().executeScenario(new NonStaticWithOneAdviceScenario(), this);
   }
   
   private class NonStaticWithOneAdviceScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.oneAdvice();
      }
   }
   
   public void testNonStaticWithFiveAdvices() throws Exception
   {
      POJO pojo = new POJO();
      PlainAspect.called = 0;
      pojo.fiveAdvices();
      assertEquals(5, PlainAspect.called);
      getRunner().executeScenario(new NonStaticWithFiveAdvicesScenario(), this);
   }
   
   private class NonStaticWithFiveAdvicesScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.fiveAdvices();
      }
   }   
}
