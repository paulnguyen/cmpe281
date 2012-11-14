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
package org.jboss.test.aop.beforeafterthrowingscoped;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Scoped aspect tests on call joinpoints scenarios. 
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings({"unchecked"})
public class CallerJoinpointScopedTestCase extends AOPTestWithSetup
{
   public CallerJoinpointScopedTestCase(String arg)
   {
      super(arg);
   }

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("CallerJoinpointScopedTestCase");
      suite.addTestSuite(CallerJoinpointScopedTestCase.class);
      return suite;
   }
   
   public void testConstructorCall() throws ThrownByTestException
   {
      for (int i = 0; i < 10; i++)
      {
         performCall(CallAction.CALL_CONSTRUCTOR);
      }
   }
   
   public void testMethodCall() throws ThrownByTestException
   {
      for (int i = 0; i < 10; i++)
      {
         performCall(CallAction.CALL_METHOD);
      }
   }
   
   public void testStaticMethodCall() throws ThrownByTestException
   {
      for (int i = 0; i < 10; i++)
      {
         performCall(CallAction.CALL_STATIC_METHOD);
      }
   }
   
   /**
    * Performs the specified call from inside all caller contexts (constructor,
    * method and static method)
    * 
    * @param callAction the call action to be performed.
    * 
    * @throws ThrownByTestException if an unexpected exception is thrown by the call
    */
   public void performCall(CallAction callAction) throws ThrownByTestException
   {
      // reset aspects
      resetAll();      
      // create targets
      SuperTargetPOJO[] targets = new SuperTargetPOJO[]
      {new SuperTargetPOJO(), new TargetPOJO1(), new TargetPOJO2()};
      
      // for each possible target
      for (SuperTargetPOJO target: targets)
      {
         // call from constructor and from method
         createAndExecuteMethod(callAction, target);
         // call from static method
         executeStaticMethod(callAction, target);
      }
   }

   /**
    * Performs <code>callAction</code> on <code>target</code> from inside
    * constructors and methods. 
    * 
    * @param callAction  the call action to be performed
    * @param target      the target of the call
    * 
    * @throws ThrownByTestException if an unexpected exception is thrown from the
    *                               call
    */
   private void createAndExecuteMethod(CallAction callAction, SuperTargetPOJO target) throws ThrownByTestException
   {
      String superJoinPointByCon = "super" + callAction + "bycon";
      String joinPointByCon1 = callAction + "bycon1";
      String joinPointByCon2 = callAction + "bycon2";
      
      // by con
      SuperPOJOCaller superCaller = new SuperPOJOCaller(target, callAction, false);
      checkAspects(superCaller, SuperPOJOCaller.class, superJoinPointByCon, false);

      // super by con with exception
      boolean exceptionThrown = false;
      try
      {
         new SuperPOJOCaller(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(CallerAspect.CALLER, SuperPOJOCaller.class, superJoinPointByCon, exceptionThrown);
      
      // by con 1
      POJOCaller1 caller1 = new POJOCaller1(target, callAction, false);
      checkAspects(caller1, POJOCaller1.class, joinPointByCon1, false);
      
      // by con 1 with exception
      try
      {
         new POJOCaller1(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(CallerAspect.CALLER, POJOCaller1.class, joinPointByCon1, exceptionThrown);
      
      // by con2
      POJOCaller2 caller2 = new POJOCaller2(target, callAction, false);
      checkAspects(caller2, POJOCaller2.class, joinPointByCon2, false);
      
      // by con 2 with exception
      try
      {
         new POJOCaller2(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(CallerAspect.CALLER, POJOCaller2.class, joinPointByCon2, exceptionThrown);
      
      String superJoinPointByMethod = "super" + callAction + "bymethod";
      String joinPointByMethod1 = callAction + "bymethod1";
      String joinPointByMethod2 = callAction + "bymethod2";
      // IMPORTANT: call some times using the same caller, to test PER_INSTANCE and
      //            PER_JOINPOINT correctly
      for (int i = 0; i < 3; i++)
      {
         // super by method
         superCaller.method(target, callAction, false);
         checkAspects(superCaller, SuperPOJOCaller.class, superJoinPointByMethod, false);
         
         // super by method with exception
         try
         {
            superCaller.method(target, callAction, true);
         }
         catch(ThrownByTestException e)
         {
            exceptionThrown = true;
         }
         assertTrue(exceptionThrown);
         checkAspects(superCaller, SuperPOJOCaller.class, superJoinPointByMethod, exceptionThrown);
         exceptionThrown = false;
         
         // super by method - 1
         caller1.method(target, callAction, false);
         checkAspects(caller1, POJOCaller1.class, superJoinPointByMethod, false);
         
         // super by method - 1 - with exception
         try
         {
            caller1.method(target, callAction, true);
         }
         catch(ThrownByTestException e)
         {
            exceptionThrown = true;
         }
         assertTrue(exceptionThrown);
         checkAspects(caller1, POJOCaller1.class, superJoinPointByMethod, exceptionThrown);
         exceptionThrown = false;
         
         // super by method - 2
         caller2.method(target, callAction, false);
         checkAspects(caller2, POJOCaller2.class, superJoinPointByMethod, false);
         
         // super by method - 2 - with exception
         try
         {
            caller2.method(target, callAction, true);
         }
         catch(ThrownByTestException e)
         {
            exceptionThrown = true;
         }
         assertTrue(exceptionThrown);
         checkAspects(caller2, POJOCaller2.class, superJoinPointByMethod, exceptionThrown);
         exceptionThrown = false;
         
         // by method1
         caller1.method1(target, callAction, false);
         checkAspects(caller1, POJOCaller1.class, joinPointByMethod1, false);
         
         // by method1 with exception
         try
         {
            caller1.method1(target, callAction, true);
         }
         catch(ThrownByTestException e)
         {
            exceptionThrown = true;
         }
         assertTrue(exceptionThrown);
         checkAspects(caller1, POJOCaller1.class, joinPointByMethod1, exceptionThrown);
         exceptionThrown = false;
         
         // by method2
         caller2.method2(target, callAction, false);
         checkAspects(caller2, POJOCaller2.class, joinPointByMethod2, false);
         
         // by method2 with exception
         try
         {
            caller2.method2(target, callAction, true);
         }
         catch(ThrownByTestException e)
         {
            exceptionThrown = true;
         }
         assertTrue(exceptionThrown);
         checkAspects(caller2, POJOCaller2.class, joinPointByMethod2, exceptionThrown);
         exceptionThrown = false;
      }
   }
   
   /**
    * Performs <code>callAction</code> from inside static methods.
    * 
    * @param callAction the call action to be performed
    * @param target     target of the action
    * 
    * @throws ThrownByTestException if an unexpected exception is thrown by the call
    */
   public void executeStaticMethod(CallAction callAction, SuperTargetPOJO target) throws ThrownByTestException
   {
      String superJoinPoint = "super" + callAction;
      String joinPoint1 = callAction + "1";
      String joinPoint2 = callAction + "2";
      
      // super by static method
      SuperPOJOCaller.staticMethod(target, callAction, false);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, false);

      // super by static method with exception
      boolean exceptionThrown = false;
      try
      {
         SuperPOJOCaller.staticMethod(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, exceptionThrown);
      exceptionThrown = false;

      //super by static method
      POJOCaller1.staticMethod(target, callAction, false);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, false);

      // super by static method with exception
      try
      {
         POJOCaller1.staticMethod(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, exceptionThrown);
      exceptionThrown = false;

      //super by static method
      POJOCaller2.staticMethod(target, callAction, false);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, false);

      // super by static method with exception
      try
      {
         POJOCaller2.staticMethod(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(null, SuperPOJOCaller.class, superJoinPoint, exceptionThrown);
      exceptionThrown = false;
      
      // by static method1
      POJOCaller1.staticMethod1(target, callAction, false);
      checkAspects(null, POJOCaller1.class, joinPoint1, false);

      // by static method1 with exception
      try
      {
         POJOCaller1.staticMethod1(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(null, POJOCaller1.class, joinPoint1, exceptionThrown);
      exceptionThrown = false;

      // by static method2
      POJOCaller2.staticMethod2(target, callAction, false);
      checkAspects(null, POJOCaller2.class, joinPoint2, false);

      // by static method2
      try
      {
         POJOCaller2.staticMethod2(target, callAction, true);
      }
      catch(ThrownByTestException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      checkAspects(null, POJOCaller2.class, joinPoint2, exceptionThrown);
   }
   
   /**
    * 
    * @param caller1
    */
   private void checkAspects(SuperPOJOCaller context, Class<?> contextClass,
         String joinPoint, boolean exceptionThrown){
      assertAspects(exceptionThrown, context == null);
      recordAspects(context, contextClass, joinPoint);
      resetAll();
   }

   /**
    * Records the scoped aspect instances and performs assertion by comparing
    * with aspect instances previously recorded.
    * 
    * @param context      the context from where the intercepted call was performed.
    *                     Can be <code>null</code> if the call context is static
    * @param contextClass the class of context (useful with context is a static
    *                     method)
    * @param joinPoint    a description that identifies uniquely the intercepted call
    *                     joinpoint
    */
   private void recordAspects(SuperPOJOCaller context, Class<?> contextClass, String joinPoint)
   {
      if (context != null)
      {
         AspectRegister.addPerInstanceAspect(context);
      }
      AspectRegister.addPerJoinpointAspect(joinPoint, context);
      AspectRegister.addPerClassJoinpointAspect(contextClass, joinPoint);
      AspectRegister.addPerClassAspect(contextClass);
      AspectRegister.addPerVmAspect();
   }
   
   /**
    * Checks null and non-null aspect instances.
    *  
    * @param exceptionThrown indicates whether the joinpoint threw an exception
    * @param staticContext   indicates whether the call context (caller) was static
    */
   private void assertAspects(boolean exceptionThrown, boolean staticContext)
   {
      Object afterAspect = null;
      
      // PER INSTANCE
      if (staticContext)
      {
         assertNull(PerInstanceAspect.before);
         assertNull(PerInstanceAspect.after);
         assertNull(PerInstanceAspect.throwing);
         assertNull(PerInstanceAspect.finaly);
      }
      else
      {
         assertNotNull(PerInstanceAspect.before);
         if (exceptionThrown)
         {
            assertNull(PerInstanceAspect.after);
            assertNotNull(PerInstanceAspect.throwing);
            afterAspect = PerInstanceAspect.throwing;
         }
         else
         {
            assertNotNull(PerInstanceAspect.after);
            assertNull(PerInstanceAspect.throwing);
            afterAspect = PerInstanceAspect.after;
         }
         assertNotNull(PerInstanceAspect.finaly);
         assertSame(PerInstanceAspect.before, afterAspect);
         assertSame(afterAspect, PerInstanceAspect.finaly);
      }
      
      // PER JOINPOINT
      assertNotNull(PerJoinpointAspect.before);
      if (exceptionThrown)
      {
         assertNull(PerJoinpointAspect.after);
         assertNotNull(PerJoinpointAspect.throwing);
         afterAspect = PerJoinpointAspect.throwing;
      }
      else
      {
         assertNotNull(PerJoinpointAspect.after);
         assertNull(PerJoinpointAspect.throwing);
         afterAspect = PerJoinpointAspect.after;
      }
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, afterAspect);
      assertSame(afterAspect, PerJoinpointAspect.finaly);
      
      // PER CLASS JOINPOINT
      assertNotNull(PerClassJoinpointAspect.before);
      if (exceptionThrown)
      {
         assertNull(PerClassJoinpointAspect.after);
         assertNotNull(PerClassJoinpointAspect.throwing);
         afterAspect = PerClassJoinpointAspect.throwing;
      }
      else
      {
         assertNotNull(PerClassJoinpointAspect.after);
         assertNull(PerClassJoinpointAspect.throwing);
         afterAspect = PerClassJoinpointAspect.after;
      }
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, afterAspect);
      assertSame(afterAspect, PerClassJoinpointAspect.finaly);
      
      // PER CLASS
      assertNotNull(PerClassAspect.before);
      if (exceptionThrown)
      {
         assertNull(PerClassAspect.after);
         assertNotNull(PerClassAspect.throwing);
         afterAspect = PerClassAspect.throwing;
      }
      else
      {
         assertNotNull(PerClassAspect.after);
         assertNull(PerClassAspect.throwing);
         afterAspect = PerClassAspect.after;
      }
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, afterAspect);
      assertSame(afterAspect, PerClassAspect.finaly);
      
      // PER VM
      assertNotNull(PerVmAspect.before);
      if (exceptionThrown)
      {
         assertNull(PerVmAspect.after);
         assertNotNull(PerVmAspect.throwing);
         afterAspect = PerVmAspect.throwing;
      }
      else
      {
         assertNotNull(PerVmAspect.after);
         assertNull(PerVmAspect.throwing);
         afterAspect = PerVmAspect.after;
         
      }
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, afterAspect);
      assertSame(afterAspect, PerVmAspect.finaly);
   }
   
   /**
    * Resets all aspect information regarding a previous joinpoint interception.
    */
   private void resetAll()
   {
      PerInstanceAspect.reset();
      PerJoinpointAspect.reset();
      PerClassJoinpointAspect.reset();
      PerClassAspect.reset();
      PerVmAspect.reset();
      CallerAspect.reset();
   }
   
   /**
    * Records scoped aspect instances for equality assertion.
    * 
    * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
    */
   static class AspectRegister
   {
      // map of per class aspect instances
      private static Map<Class, PerClassAspect> PER_CLASS =
         new HashMap<Class, PerClassAspect>();
      // map of per joinpoitn aspect instances
      private static Map<String, PerJoinpointAspect> PER_JOINPOINT =
         new HashMap<String, PerJoinpointAspect>();
      // map of per instance aspect instances
      private static Map<SuperPOJOCaller, PerInstanceAspect> PER_INSTANCE =
         new HashMap<SuperPOJOCaller, PerInstanceAspect>();
      // map of per class joinpoint aspect instances
      private static Map<String, PerClassJoinpointAspect> PER_CLASS_JOINPOINT =
         new HashMap<String, PerClassJoinpointAspect>();
      // the single instance of per vm aspect
      private static PerVmAspect PER_VM = null;
      
      /*
       * Adds the PerJoinpointAspect instance used during the last joinpoint
       * execution.
       */
      public static void addPerJoinpointAspect(String joinpoint, SuperPOJOCaller instance)
      {
         addAspect(joinpoint + instance, PerJoinpointAspect.before, PER_JOINPOINT);
      }
      
      /*
       * Adds the PerInstanceAspect instance used during the last joinpoint
       * execution.
       */
      public static void addPerInstanceAspect(SuperPOJOCaller instance)
      {
         addAspect(instance, PerInstanceAspect.before, PER_INSTANCE);
      }
      
      /*
       * Adds the PerClassJoinpointAspect instance used during the last joinpoint
       * execution.
       */
      public static void addPerClassJoinpointAspect(Class clazz, String joinpoint)
      {
         addAspect(clazz + joinpoint, PerClassJoinpointAspect.before , PER_CLASS_JOINPOINT);
      }
      
      /*
       * Adds the PerClassAspect instance used during the last joinpoint
       * execution.
       */
      public static void addPerClassAspect(Class clazz)
      {
         addAspect(clazz, PerClassAspect.before, PER_CLASS);
      }
      
      /*
       * Adds the PerVMAspect instance used during the last joinpoint
       * execution.
       */
      public static void addPerVmAspect()
      {
         if (PER_VM != null)
         {
            assertSame(PER_VM, PerVmAspect.before);
         }
         PER_VM = PerVmAspect.before;
      }
      
      /*
       * Adds <code>aspect</code> to <code>map</code>.
       */
      private static <K, V>void addAspect(K key, V aspect, Map<K, V> map)
      {
         // if this instance is already registered, assert it is the same instance
         if (map.containsKey(key))
         {
            assertSame(map.get(key), aspect);
         }
         // otherwise, add this instance to the map
         else
         {
            map.put(key, aspect);
         }
         // check that this instance is different from all other instances in map
         for (Map.Entry<K, V> e: map.entrySet())
         {
            if (e.getKey() != key && !e.getKey().equals(key))
            {
               assertNotSame("Unexpected same value\nkey1:" + e.getKey() + " - key2:" + key, e.getValue(), aspect);
            }
         }
      }
   }
}