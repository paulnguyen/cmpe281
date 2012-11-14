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
package org.jboss.test.aop.jdk15.dynamic.prepareFieldSet;

import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.jdk15.dynamic.common.BindingInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.InstanceInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.InterceptionsCount;
import org.jboss.test.aop.jdk15.dynamic.common.POJOWrappingInfo;
import org.jboss.test.aop.jdk15.dynamic.common.ScenarioLoader;
import org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner;

/**
 * TestCase that checks the jboss aop behaviour in dynamic aop operations
 * performed with hot swap disabled. The aop operations target class, (POJO class)
 * was configured to have only the field write joinponts prepared.
 * @author Flavia Rainone
 */
public class HotSwapDisabledTester extends AOPTestWithSetup
{
   private ScenarioLoader scenarioLoader;
   
   public HotSwapDisabledTester(String name)
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
    * Asserts only pojo field write joinpoints are wrapped.
    * @param wrappingInfos contains the joinpoints wrapping informations to be checked.
    */
   private void assertOnlyFieldWriteWrapped(POJOWrappingInfo[] wrappingInfos) throws Exception
   {
      for (int i = 0; i < wrappingInfos.length; i++)
      {
         assertFalse(wrappingInfos[i].isConstructorWrapped());
         assertFalse(wrappingInfos[i].isFieldReadWrapped());
         assertTrue(wrappingInfos[i].isFieldWriteWrapped());
         assertFalse(wrappingInfos[i].isMethodWrapped());         
      }
   }
   
   /**
    * Runs "interceptPerClassLoadAfter" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerClassLoadAfter()
    */
   public void testPerClassLoadAfterInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassLoadAfter();
      // check if java 5 is being used
      if (((AOPTestDelegate)getDelegate()).getSystemProperties().getProperty("java5Agent") != null)
      { 
         // check wrapping status
         for (int i = 0; i < wrappingInfos.length; i++)
         {
            assertTrue(wrappingInfos[i].isConstructorWrapped());
            assertTrue(wrappingInfos[i].isFieldReadWrapped());
            assertTrue(wrappingInfos[i].isFieldWriteWrapped());
            assertTrue(wrappingInfos[i].isMethodWrapped());
         }
         // check interception counts
         InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
         assertEquals(0, instance.total);
         InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
         assertEquals(4, binding.total);
         assertEquals(0, binding.constructor);
         assertEquals(0, binding.fieldRead);
         assertEquals(2, binding.fieldWrite);
         assertEquals(2, binding.method);
         return;
      }
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(2, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(2, binding.fieldWrite);
      assertEquals(0, binding.method);
   }
   
   /**
    * Runs "interceptPerClassLoadBefore" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerClassLoadBefore()
    */
   public void testPerClassLoadBeforeInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassLoadBefore();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(1, binding.fieldWrite);
      assertEquals(0, binding.method);
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
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(2, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(2, binding.fieldWrite);
      assertEquals(0, binding.method);
   }

   /**
    * Runs "interceptPerInstance" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerInstance()
    */
   public void testPerInstanceInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstance();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts     
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(2, instance.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(2, instance.fieldWrite);
      assertEquals(0, binding.method);
   }
   
   /**
    * Runs "interceptPerInstanceGC" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerInstanceGC()
    */
   public void testPerInstanceGcInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstanceGC();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(2, instance.total);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(2, instance.fieldWrite);
      assertEquals(0, instance.method);
   }
   
   /**
    * Runs "interceptPerClassPerInstance" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerClassPerInstance()
    */
   public void testPerClassPerInstanceInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerClassPerInstance();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(1, binding.fieldWrite);
      assertEquals(0, binding.method);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(1, instance.total);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(1, instance.fieldWrite);
      assertEquals(0, instance.method);
   }
   
   /**
    * Runs "interceptPerInstancePerClass" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#interceptPerInstancePerClass()
    */
   public void testPerInstancePerClassInterception() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.interceptPerInstancePerClass();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(1, binding.total);
      assertEquals(0, binding.constructor);
      assertEquals(0, binding.fieldRead);
      assertEquals(1, binding.fieldWrite);
      assertEquals(0, binding.method);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(3, instance.total);
      assertEquals(0, instance.constructor);
      assertEquals(0, instance.fieldRead);
      assertEquals(3, instance.fieldWrite);
      assertEquals(0, instance.method);
   }
   
   /**
    * Runs "executeAfterBindingRemoval" scenario and checks which
    * interceptions were performed.
    * @throws Throwable 
    * @see ScenarioRunner#executeAfterBindingRemoval()
    */
   public void testAfterBindingRemovalExecution() throws Throwable
   {
      // run scenario
      POJOWrappingInfo[] wrappingInfos = scenarioLoader.executeAfterBindingRemoval();
      // check wrapping status
      assertOnlyFieldWriteWrapped(wrappingInfos);
      // check interception counts
      InterceptionsCount binding = BindingInterceptor.getInterceptionsCount();
      assertEquals(0, binding.total);
      InterceptionsCount instance = InstanceInterceptor.getInterceptionsCount();
      assertEquals(0, instance.total);
   }
}