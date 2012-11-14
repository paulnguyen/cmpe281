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
package org.jboss.test.aop.jdk15.dynamic.prepareMethod;

import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.jdk15.dynamic.common.BindingInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.InstanceInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.InterceptionsCount;
import org.jboss.test.aop.jdk15.dynamic.common.POJOWrappingInfo;
import org.jboss.test.aop.jdk15.dynamic.common.ScenarioLoader;

/**
 * TestCase that checks the jboss aop behaviour in dynamic aop operations
 * performed with hot swap enabled. The aop operations target class, (POJO class)
 * was configured to have only the method execution joinpoints prepared.
 * @author Flavia Rainone
 */
public class HotSwapEnabledTester extends AOPTestWithSetup
{
   private ScenarioLoader scenarioLoader;
 
   public HotSwapEnabledTester(String name)
   {
      super(name);
   }

   /**
    * Runs before each test method.
    */
   public void setUp() throws Exception
   {
      super.setUp();
      this.scenarioLoader = new ScenarioLoader(((AOPTestDelegate)getDelegate()).getSystemProperties());
   }

   /**
    * Asserts no pojo joinpoints are wrapped.
    * @param wrappingInfo contains the joinpoints wrapping information to be checked.
    */
   private void assertUnwrapped(POJOWrappingInfo wrappingInfo)
   {
      assertFalse(wrappingInfo.isConstructorWrapped());
      assertFalse(wrappingInfo.isFieldReadWrapped());
      assertFalse(wrappingInfo.isFieldWriteWrapped());
      assertFalse(wrappingInfo.isMethodWrapped());
   }
   
   /**
    * Asserts all pojo joinpoints are wrapped.
    * @param wrappingInfo contains the joinpoints wrapping information to be checked.
    */
   private void assertFullyWrapped(POJOWrappingInfo wrappingInfo)
   {
      assertTrue(wrappingInfo.isConstructorWrapped());
      assertTrue(wrappingInfo.isFieldReadWrapped());
      assertTrue(wrappingInfo.isFieldWriteWrapped());
      assertTrue(wrappingInfo.isMethodWrapped());
   }

   /**
    * Asserts only pojo method execution joinpoints are wrapped.
    * @param wrappingInfo contains the joinpoints wrapping information to be checked.
    */
   private void assertOnlyMethodWrapped(POJOWrappingInfo wrappingInfo)
   {
      assertFalse(wrappingInfo.isConstructorWrapped());
      assertFalse(wrappingInfo.isFieldReadWrapped());
      assertFalse(wrappingInfo.isFieldWriteWrapped());
      assertTrue(wrappingInfo.isMethodWrapped());
   }
   
   /**
    * Runs "interceptPerClassLoadAfter" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerClassLoadAfter()
    */
   public void testPerClassLoadAfterInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassLoadAfter();
      // check wrapping status
      assertFullyWrapped(wrappingInfos[0]);
      assertUnwrapped(wrappingInfos[1]);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertTrue(binding.total >= 5 && binding.total <= 8);
      assertTrue(binding.constructor >= 1 && binding.constructor <= 2);
      assertTrue(binding.fieldRead >= 1 && binding.fieldRead <= 2);
      assertTrue(binding.fieldWrite >= 1 && binding.fieldWrite <= 2);
      assertEquals(2, binding.method);
   }
   
   /**
    * Runs "interceptPerClassLoadBefore" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioscenarioLoader#interceptPerClassLoadBefore()
    */
   public void testPerClassLoadBeforeInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassLoadBefore();
      // check wrapping status
      assertUnwrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      assertUnwrapped(wrappingInfos[2]);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(0, binding.fieldWrite);
      assertEquals(1, binding.method);
   }

   /**
    * Runs "addAndRemoveBindingTwice" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerClassLoadBefore()
    */
   public void testAddAndRemoveBindingTwice() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.addAndRemoveBindingTwice();
      // check wrapping status
      assertUnwrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      assertUnwrapped(wrappingInfos[2]);
      assertOnlyMethodWrapped(wrappingInfos[3]);
      assertUnwrapped(wrappingInfos[4]);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(2, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(0, binding.fieldWrite);
      assertEquals(2, binding.method);
   }
   
   /**
    * Runs "interceptPerInstance" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerInstance()
    */
   public void testPerInstanceInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstance();
      // check wrapping status
      assertUnwrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      // check interception counts      
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertTrue(instance.total >= 1 && instance.total <= 2);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(0, instance.fieldWrite);
      assertTrue(instance.method >= 1 && instance.method <= 2);
   }

   
   /**
    * Runs "interceptPerInstanceGC" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerInstanceGC()
    */
   public void testPerInstanceGcInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstanceGC();
      // check wrapping status
      assertUnwrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      assertUnwrapped(wrappingInfos[2]);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      // result of the time the JVMTI takes to hot swap classes
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertTrue(instance.total >= 1 && instance.total <= 2);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(0, instance.fieldWrite);
      assertTrue(instance.method >= 1 && instance.method <= 2);
   }
   
   /**
    * Runs "interceptPerClassPerInstance" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerClassPerInstance()
    */
   public void testPerClassPerInstanceInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassPerInstance();
      // check wrapping status
      assertUnwrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      assertOnlyMethodWrapped(wrappingInfos[2]);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(0, binding.fieldWrite);
      assertEquals(1, binding.method);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(1, instance.total);
      assertEquals(1, instance.method);
   }
   
   /**
    * Runs "interceptPerInstancePerClass" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#interceptPerInstancePerClass()
    */
   public void testPerInstancePerClassInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstancePerClass();
      // check wrapping status
      assertOnlyMethodWrapped(wrappingInfos[0]);
      assertOnlyMethodWrapped(wrappingInfos[1]);
      assertOnlyMethodWrapped(wrappingInfos[2]);
      // check interception counts      
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(0, binding.fieldWrite);
      assertEquals(1, binding.method);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertTrue(instance.total >= 2 && instance.total <= 3);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(0, instance.fieldWrite);
      assertTrue(instance.method >= 2 && instance.method <= 3);
   }
   
   /**
    * Runs "executeAfterBindingRemoval" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioLoader#executeAfterBindingRemoval()
    */
   public void testAfterBindingRemovalExecution() throws Throwable
   {
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.executeAfterBindingRemoval();
      assertUnwrapped(wrappingInfos[0]);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
   }
}