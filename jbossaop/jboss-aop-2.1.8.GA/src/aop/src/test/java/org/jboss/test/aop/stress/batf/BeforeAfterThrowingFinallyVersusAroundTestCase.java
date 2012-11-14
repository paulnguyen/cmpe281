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
package org.jboss.test.aop.stress.batf;

import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.ScenarioTest;

/**
 * Primarily to make sure I got everything right for the generated advisors
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 61751 $
 */
public class BeforeAfterThrowingFinallyVersusAroundTestCase extends ScenarioTest
{
   
   public static void main(String[] args)
   {int i = -5;
   i*=(-1);
      junit.textui.TestRunner.run(BeforeAfterThrowingFinallyVersusAroundTestCase.class);
   }

   
   public BeforeAfterThrowingFinallyVersusAroundTestCase(String name) throws Exception
   {
      super(name);
   }

   public void testAroundWithNoExceptionScenario() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithAroundNoExceptions();
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      assertFalse(SimpleAspect.finaly);
      getRunner().executeScenario(new AroundWithNoExceptionsScenario(), this);
   }
   
   private class AroundWithNoExceptionsScenario extends AbstractScenario
   {
      POJO pojo = new POJO();

      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithAroundNoExceptions();
      }
   }

   public void testBeforeAfterThrowingFinallyWithNoExceptionScenario() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithBeforeAfter();
      assertTrue(SimpleAspect.before);
      assertTrue(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      assertFalse(SimpleAspect.finaly);
      getRunner().executeScenario(new BeforeAfterThrowingFinallyWithNoExceptionsScenario(), this);
   }
   
   private class BeforeAfterThrowingFinallyWithNoExceptionsScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithBeforeAfter();
      }
   }

   public void testAroundWithExceptionScenario() throws Exception
   {
      SimpleAspect.reset();
      boolean exception = false;
      try
      {
         POJO pojo = new POJO();
         pojo.methodWithAroundExceptions();
      }
      catch (Exception e)
      {
         exception = true;
      }
      
      assertTrue(exception);
      assertTrue(SimpleAspect.before);
      assertFalse(SimpleAspect.after);
      assertTrue(SimpleAspect.throwing);
      assertTrue(SimpleAspect.finaly);

      getRunner().executeScenario(new AroundWithExceptionsScenario(), this);
   }
   
   private class AroundWithExceptionsScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         try
         {
            pojo.methodWithAroundExceptions();
         }
         catch(RuntimeException e) 
         {
            
         }
      }
   }


   public void testBeforeAfterThrowingFinallyWithExceptionScenario() throws Exception
   {
      SimpleAspect.reset();
      boolean exception = false;
      try
      {
         POJO pojo = new POJO();
         pojo.methodWithBeforeThrowingFinally();
      }
      catch (Exception e)
      {
         exception = true;
      }
      
      assertTrue(exception);
      assertTrue(SimpleAspect.before);
      assertFalse(SimpleAspect.after);
      assertTrue(SimpleAspect.throwing);
      assertTrue(SimpleAspect.finaly);

      getRunner().executeScenario(new BeforeAfterThrowingFinallyWithExceptionScenario(), this);
   }
   
   private class BeforeAfterThrowingFinallyWithExceptionScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         try
         {
            pojo.methodWithBeforeThrowingFinally();
         }
         catch (RuntimeException e)
         {
         }
      }
   }

   public void testAroundSimulatedBefore() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithSimulatedBefore();
      assertTrue(SimpleAspect.before);
      assertFalse(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      assertFalse(SimpleAspect.finaly);

      getRunner().executeScenario(new AroundSimulatedBeforeScenario(), this);
   }
   
   private class AroundSimulatedBeforeScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithSimulatedBefore();
      }
   }
   
   public void testBeforeOnly() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithOnlyBefore();
      assertTrue(SimpleAspect.before);
      assertFalse(SimpleAspect.after);
      assertFalse(SimpleAspect.throwing);
      assertFalse(SimpleAspect.finaly);

      getRunner().executeScenario(new BeforeOnlyScenario(), this);
   }
   
   private class BeforeOnlyScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithOnlyBefore();
      }
   }
   
   public void testAroundAccessArguments() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithAroundArguments("HELLO");
      assertEquals("HELLO", SimpleAspect.string);
      
      getRunner().executeScenario(new AroundAccessArgumentsScenario(), this);
   }
   
   private class AroundAccessArgumentsScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithAroundArguments("TEST");
      }
   }
   
   
   public void testBeforeAccessTypedArgument() throws Exception
   {
      SimpleAspect.reset();
      POJO pojo = new POJO();
      pojo.methodWithTypedArguments("HEY");
      assertEquals("HEY", SimpleAspect.string);
      
      getRunner().executeScenario(new BeforeAccessTypedArgumentScenario(), this);
   }
   
   private class BeforeAccessTypedArgumentScenario extends AbstractScenario
   {
      POJO pojo = new POJO();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.methodWithTypedArguments("TEST123");
      }
   }
   
   
   public void testNotWovenNoCtorScenario() throws Exception
   {
      getRunner().executeScenario(new NotWovenNoCtorScenario(), this);
   }
   
   private class NotWovenNoCtorScenario extends AbstractScenario
   {
      NotWoven pojo = new NotWoven();
      public void execute(int thread, int loop) throws Exception
      {
         pojo.method1();
      }
   }

   public void testNotWovenWithCtorScenario() throws Exception
   {
      getRunner().executeScenario(new NotWovenWithCtorScenario(), this);
   }
   
   private class NotWovenWithCtorScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         NotWoven pojo = new NotWoven();
         pojo.method2();
      }
   }
}
