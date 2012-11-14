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
package org.jboss.test.aop.jdk15.dynamic.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Properties;

import org.jboss.aop.AspectManager;

/**
 * Loads the scenario classes using a class loader. This assures that the scenario to
 * be tested runs isolated.
 * The isolation of scenarios is essencial for testing, because each scenario
 * requires that the POJO and ScenarioRunner classes were not loaded before its execution.
 * With scenario loader, we can run more than one scenario in a same test instance, avoiding
 * to write a TestCase class for each scenario.
 * @author Flavia Rainone
 *
 */
@SuppressWarnings({"unused", "unchecked"})
public class ScenarioLoader
{
   private URL[] urls;
   private ClassLoader classLoader;
   private Object scenarioRunner;
   private Class scenarioRunnerClass; 
   private Field totalInterceptions;
   private Field fieldReadInterceptions;
   private Field fieldWriteInterceptions;
   private Field constructorInterceptions;
   private Field methodInterceptions; 
   private boolean scenarioRunned = false;
   
   /**
    * Constructor.
    */
   public ScenarioLoader(Properties properties) throws Exception
   {
      urls = new URL[]{new URL("file:" + properties.getProperty("scenario.jar"))};      

      System.out.println("URLS " + Arrays.asList(urls));
      // create a new class loader
      this.classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
      // loads the scenario reflection data.
      this.scenarioRunnerClass =  classLoader.loadClass(getClass().getPackage().getName() + ".scenario.ScenarioRunner");
      Class interceptionsCountClass = this.classLoader.loadClass(InterceptionsCount.class.getName());
      this.totalInterceptions = interceptionsCountClass.getField("total");
      this.fieldReadInterceptions = interceptionsCountClass.getField("fieldRead");
      this.fieldWriteInterceptions = interceptionsCountClass.getField("fieldWrite");
      this.constructorInterceptions = interceptionsCountClass.getField("constructor");
      this.methodInterceptions = interceptionsCountClass.getField("method");
      
      this.scenarioRunner = scenarioRunnerClass.newInstance();
   }

   /**
    * Runs the "interceptPerClassLoadBefore" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#interceptPerClassLoadBefore()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerClassLoadBefore() throws Throwable
   {
      return this.executeScenario("interceptPerClassLoadBefore");
   }
   
   /**
    * Runs the "interceptPerClassLoadAfter" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#interceptPerClassLoadAfter()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerClassLoadAfter() throws Throwable
   {
      return this.executeScenario("interceptPerClassLoadAfter");
   }
   
   /**
    * Runs the "addAndRemoveBindingTwice" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#addAndRemoveBindingTwice()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] addAndRemoveBindingTwice() throws Throwable
   {
      return this.executeScenario("addAndRemoveBindingTwice");
   }
   
   /**
    * Runs the "executeAfterBindingRemoval" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#executeAfterBindingRemoval()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] executeAfterBindingRemoval() throws Throwable
   {
      return this.executeScenario("executeAfterBindingRemoval");
   }
   
   /**
    * Runs the "executeAfterBindingRemoval" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#executeAfterBindingRemoval()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstance() throws Throwable {
      return this.executeScenario("interceptPerInstance");
   }
   
   /**
    * Runs the "interceptPerInstanceGC" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#interceptPerInstanceGC()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstanceGC() throws Throwable
   {
      return this.executeScenario("interceptPerInstanceGC");
   }
   
   /**
    * Runs the "interceptPerClassPerInstance" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#interceptPerClassPerInstance()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerClassPerInstance() throws Throwable
   {
      return this.executeScenario("interceptPerClassPerInstance");
   }
   
   /**
    * Runs the "interceptPerInstancePerClass" scenario.
    * @see org.jboss.test.aop.jdk15.dynamic.common.scenario.ScenarioRunner#interceptPerInstancePerClass()
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   public POJOWrappingInfo[] interceptPerInstancePerClass() throws Throwable
   {
      return this.executeScenario("interceptPerInstancePerClass");
   }
      
   /**
    * Executes a test scenario.
    * @param scenarioName the name of the scenario to be executed.
    * @return the pojo wrapping infos obtained in the key points of the scenario execution.
    */
   private POJOWrappingInfo[] executeScenario(String scenarioName) throws Throwable
   {
      if (scenarioRunned)
         throw new RuntimeException("Should not run more than one scenario.");
      Method method = this.scenarioRunnerClass.getMethod(scenarioName);
      POJOWrappingInfo[] result = null;
      try
      {
         result = (POJOWrappingInfo[]) method.invoke(scenarioRunner, new Object[0]);
      } catch (InvocationTargetException e)
      {
         throw e.getCause();
      }
      scenarioRunned = true;
      this.removeClassLoaderFromAspectManager();
      return result;
   }
   
   /**
    * Avoids conflicts. JBoss AOP uses a "unified class loader" structure in the class pools.
    * This means that it sees the javassist ctclasses used in the previous scenarios executions
    * and uses this classes instead of creating a new one. The intent of this
    * class is to allow the running more than one scenario in a same java virtual
    * machine instance as if each scenario runner was running in a different java virtual
    * machine.
    */
   public void removeClassLoaderFromAspectManager()
   {
      AspectManager.instance().unregisterClassLoader(classLoader);
   }
}