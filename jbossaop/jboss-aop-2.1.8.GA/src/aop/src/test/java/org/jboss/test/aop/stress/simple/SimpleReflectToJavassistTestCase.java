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
package org.jboss.test.aop.stress.simple;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.aop.util.ReflectToJavassist;
import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.Scenario;
import org.jboss.test.aop.stress.ScenarioTest;

/**
 * Primarily to make sure I got everything right for the generated advisors
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings({"unchecked", "unused"})
public class SimpleReflectToJavassistTestCase extends ScenarioTest
{
   
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(SimpleReflectToJavassistTestCase.class);
   }

   
   public SimpleReflectToJavassistTestCase(String name) throws Exception
   {
      super(name);
   }

   public void testException() throws Exception
   {
      boolean exception = false;
      try
      {
         getRunner().executeScenario(new ExceptionScenario(), this);
      }
      catch (Exception e)
      {
         exception = true;
      }
      
      assertTrue(exception);
   }
   
   public void testAnnotationsUnderStress() throws Exception
   {
      Scenario[] scenarios = new Scenario[] {
        new SimpleClassToJavassistScenario(),
        new SimpleFieldToJavassistScenario(),
        new SimpleMethodToJavassistScenario()};
      
      getRunner().executeScenario(scenarios, this);
   }

   private class ExceptionScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         if (thread == 0 && loop == 1)
         {
            System.out.println("Throwing exception!!!!");
            throw new Exception("Thrown Exception");
         }
      }
      
   }
   private class SimpleClassToJavassistScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         Class pojo = LargePojo.class;
         ReflectToJavassist.classToJavassist(pojo);
      }
   }

   private class SimpleMethodToJavassistScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         Class pojo = LargePojo.class;
         Method m = pojo.getMethod("method", new Class[0]);
         ReflectToJavassist.methodToJavassist(m);
      }
   }

   private class SimpleFieldToJavassistScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         Class pojo = LargePojo.class;
         Field f = pojo.getField("field");
         ReflectToJavassist.fieldToJavassist(f);
      }
   }

   /**
    * Just here to check the overhead of creating/throwing exceptions
    */
   public void testExceptionOverhead() throws Throwable
   {
      getRunner().executeScenario(new InstantiateObjectScenario(), this);
      getRunner().executeScenario(new CreateExceptionScenario(), this);
      getRunner().executeScenario(new CreateExceptionGetStackTraceScenario(), this);
      getRunner().executeScenario(new CallMethodWithExceptionScenario(), this);
   }
   
   private class InstantiateObjectScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         Object o = new Object();
      }
   }
   
   private class CreateExceptionScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         RuntimeException e = new RuntimeException();
      }
   }
   
   private class CreateExceptionGetStackTraceScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         RuntimeException e = new RuntimeException();
         e.getStackTrace();
      }
   }
   
   
   private class CallMethodWithExceptionScenario extends AbstractScenario
   {
      RuntimeException e = new RuntimeException();
      public void execute(int thread, int loop) throws Exception
      {
         try
         {
            method();
         }
         catch(Exception e)
         {
            
         }
      }
      
      private void method()
      {
         throw e;
      }
   }
   
}
