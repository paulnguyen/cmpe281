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
package org.jboss.test.aop.jdk15.dynamic.common.scenario;


import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.test.aop.jdk15.dynamic.common.BindingInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.InstanceInterceptor;
import org.jboss.test.aop.jdk15.dynamic.common.POJOWrappingInfo;

/**
 * Class responsible for running dynamic scenarios that are common
 * to all test cases contained in the
 * org.jboss.test.aop.jdk15.dynamic package.
 * 
 * @author Flavia Rainone
 *
 */
@SuppressWarnings("unused")
public class ScenarioRunner
{
   // Bindings used in the tests
   private static final AdviceBinding constructorExecution;
   private static final AdviceBinding methodExecution;
   private static final AdviceBinding fieldRead;
   private static final AdviceBinding fieldWrite;
   // javassist objects to allow P
   private static CtClass POJO_CLASS;
   private static CtField FIELD;
   private static CtMethod METHOD;
   private static String NOT_ADVISED_METHOD;
   private static CtClass POJO_CLIENT;

   static
   {
      try
      {
         constructorExecution = new AdviceBinding("execution(*.POJO->new(..))", null);
         constructorExecution.addInterceptor(BindingInterceptor.class);
         fieldRead = new AdviceBinding("get(public int *.POJO->counter)", null);
         fieldRead.addInterceptor(BindingInterceptor.class);
         fieldWrite = new AdviceBinding("set(public int *.POJO->counter)", null);
         fieldWrite.addInterceptor(BindingInterceptor.class);
         methodExecution = new AdviceBinding("execution(public void *.POJO->someMethod())", null);
         methodExecution.addInterceptor(BindingInterceptor.class);
      }
      catch(ParseException e)
      {
         throw new RuntimeException("Unexpected exception", e);
      }
   }
   
   
   /**
    * Constructor. Resets interceptor counts.
    */
   public ScenarioRunner()
   {
      InstanceInterceptor.resetCounts();
      BindingInterceptor.resetCounts();
   }


   /**
    * Adds bidings so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor. This is done after having loaded POJO class.
    * As soon as the POJO joinponts become intercepted, all of them are
    * executed once, before the bindings get removed.
    * @throws NotFoundException 
    * @throws Exception 
    */
   public POJOWrappingInfo[] interceptPerClassLoadBefore() throws Exception
   {
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[3];
      this.executeJoinpoints(false);
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.addBindings();
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      this.removeBindings();
      wrappingInfos[2] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      return wrappingInfos;
   }
   
   private static POJOWrappingInfo getCurrentWrappingInfo() throws Exception
   {
      return new POJOWrappingInfo(POJO_CLASS, FIELD, METHOD, NOT_ADVISED_METHOD, POJO_CLIENT);  
   }

   /**
    * Adds bidings so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor. This is done before the POJO class gets loaded.
    * As soon as the POJO joinponts become intercepted, all of them are
    * executed once, before the bindings get removed.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerClassLoadAfter() throws Exception
   {
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[2];
      this.addBindings();
      this.executeJoinpoints(false);
      this.executeJoinpoints(false);
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.removeBindings();
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      return wrappingInfos;
   }

   /**
    * Adds bindings so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor. After this, executes the POJO joinpoints and
    * remove the bindings. Repeat the previous steps.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] addAndRemoveBindingTwice() throws Exception
   {
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[5];
      this.executeJoinpoints(false);
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.addBindings();
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      this.removeBindings();
      wrappingInfos[2] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      this.addBindings();
      wrappingInfos[3] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      this.removeBindings();
      wrappingInfos[4] = getCurrentWrappingInfo();
      this.executeJoinpoints(false);
      return wrappingInfos;
   }
   
   /**
    * Adds bindings so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor. As soon as the bindings are added, they are
    * removed before POJO joinponts are executed, which happens once.
    * The goal of this use case is to test how the code instrumentation
    * (in load time) behaves, hopefully removing completely the added bindings.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] executeAfterBindingRemoval() throws Exception
   {
      this.addBindings();
      this.removeBindings();
      this.executeJoinpoints(false);
      return new POJOWrappingInfo[]{getCurrentWrappingInfo()};
   }
   
   /**
    * This method does the following step sequence twice: adding a InstanceInterceptor
    * to the ClassInstanceAdvisor interceptor chain associated with a POJO instance
    * and executing all POJO instance joinpoints.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstance() throws Exception {
      executeJoinpoints(false);
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[2]; 
      wrappingInfos[0] = getCurrentWrappingInfo();
      executeJoinpoints(true);
      wrappingInfos[1] = getCurrentWrappingInfo();
      executeJoinpoints(true);
      return wrappingInfos;
   }
   
   /**
    * This method does the same that interceptPerInstance, except that it
    * tries to force the garbage collector to run, on a best effort basis.
    * The goal of this scenario is to check if the joinpoints are unwrapped
    * as soon as jboss aop realizes the ClassInstanceAdvisors were garbage
    * collected and the joinpoints are no more intercepted.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstanceGC() throws Exception
   {
      this.instancesCollected = 0;
      this.executeJoinpoints(false);
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[3];
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.executeJoinpoints(true);
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(true);
      while(this.instancesCollected < 3) {
         for (int i = 0; i < 10000; i++)
         {
            String string = new String("any string to fill memory space...");
         }
         System.gc();
         try
         {
            Thread.sleep(10000);         
         }
         catch (InterruptedException e)
         {
            throw new RuntimeException("Unexpected exception, e");
         }
      }
      this.executeJoinpoints(false);
      this.executeJoinpoints(false);
      wrappingInfos[2] = getCurrentWrappingInfo();
      return wrappingInfos;
   }
   
   /**
    * Adds bidings so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor. As soon as the POJO joinponts become intercepted,
    * a POJO instance is created and a InstanceInterceptor is added to its
    * ClassInstanceAdvisor interceptor chain. All of the POJO instance joinpoints
    * are executed, after which the bindings are removed.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerClassPerInstance() throws Exception
   {
      this.executeJoinpoints(false);
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[3];
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.addBindings();
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(true);
      wrappingInfos[2] = getCurrentWrappingInfo();
      this.removeBindings();
      this.executeJoinpoints(false);
      return wrappingInfos;
   }
   
  /**
   *  Adds a InstanceInterceptor
    * to the ClassInstanceAdvisor interceptor chain associated with a POJO
    * instance and executes all POJO instance joinpoints.
    * After this, this method adds bidings so that all joinpoints of POJO class
    * become intercepted by BindingInterceptor. As soon as the POJO joinponts
    * become intercepted, a POJO instance is created and a InstanceInterceptor
    * is added to its ClassInstanceAdvisor interceptor chain. All of the POJO
    * instance joinpoints are executed, after which the bindings are removed.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstancePerClass() throws Exception
   {
      this.executeJoinpoints(true);
      this.executeJoinpoints(true);
      POJOWrappingInfo[] wrappingInfos = new POJOWrappingInfo[3];
      wrappingInfos[0] = getCurrentWrappingInfo();
      this.addBindings();
      wrappingInfos[1] = getCurrentWrappingInfo();
      this.executeJoinpoints(true);
      wrappingInfos[2] = getCurrentWrappingInfo();
      this.removeBindings();
      this.executeJoinpoints(false);
      return wrappingInfos;
   }
   
   /** Keeps track of the number of POJO instances that were collected. */
   private int instancesCollected = 0;
   
   /**
    * Tells scenario runner that a pojo instance is being collected.
    */
   void pojoCollected()
   {
      instancesCollected++;
   }
   
   /**
    * Executes all joinpoints of POJO class.
    * @param interceptInstance if <code>true</code>, a InstanceInterceptor
    * will be added to the interceptor chain of the ClassInstanceAdvisor
    * associated with a POJO instance.
    */
   private void executeJoinpoints(boolean interceptInstance)
   {
      POJO pojo = new POJO(this);
      InstanceInterceptor instanceInterceptor  = null;
      if (interceptInstance)
      {
         instanceInterceptor = new InstanceInterceptor();
         Advised advised = (Advised) pojo;
         advised._getInstanceAdvisor().insertInterceptor(instanceInterceptor);
      }
      pojo.counter++;
      pojo.someMethod();
      //after loading pojo into the class loader
      if (POJO_CLASS == null)
      {
         loadPOJOData();
      }
   }
   
   /**
    * Call after pojo has been loaded, to not invalidade tests results.
    */
   private static void loadPOJOData()
   {
      Map<ClassLoader, ClassPool> cls = AspectManager.getRegisteredCLs();
      AOPClassPool classPool = (AOPClassPool) cls.get(POJO.class.getClassLoader());
      try
      {
         POJO_CLASS = classPool.getLocally(POJO.class.getName());
         FIELD = POJO_CLASS.getDeclaredField("counter");
         METHOD = POJO_CLASS.getDeclaredMethod("someMethod");
         NOT_ADVISED_METHOD = ClassAdvisor.notAdvisedMethodName(POJO.class.getName(), METHOD.getName());
         POJO_CLIENT= classPool.getLocally(ScenarioRunner.class.getName());   
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException("Unexpected exception", e);
      }
   }
   
   /**
    * Adds bindings to AspectManager so that all joinpoints of POJO class become intercepted
    * by BindingInterceptor.
    */
   void addBindings()
   {
      AspectManager aspectManager = AspectManager.instance();
      aspectManager.addBinding(constructorExecution);
      aspectManager.addBinding(fieldRead);
      aspectManager.addBinding(fieldWrite);
      aspectManager.addBinding(methodExecution);      
   }
   
   /**
    * Removes the bindings added through the addBindings method.
    */
   private void removeBindings()
   {
      AspectManager aspectManager = AspectManager.instance();
      aspectManager.removeBinding(constructorExecution.getName());
      aspectManager.removeBinding(fieldRead.getName());
      aspectManager.removeBinding(fieldWrite.getName());
      aspectManager.removeBinding(methodExecution.getName());
   }
}