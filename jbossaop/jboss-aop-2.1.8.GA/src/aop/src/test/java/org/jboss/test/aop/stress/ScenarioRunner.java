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
package org.jboss.test.aop.stress;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unchecked")
public class ScenarioRunner
{
   private Class testCaseClass;
   ScenarioPropertyReaderFactory factory;
   
   private int warmup;
   private int loops;
   private int threads;
   private boolean randomSleepInterval;
   private int sleeptimeMillis;
   Random random = new Random(10);
   boolean logging;
   
   public ScenarioRunner(Class testCaseClass)
   {
      this.testCaseClass = testCaseClass;
      factory = new ScenarioPropertyReaderFactory(testCaseClass);
   }

   private void initialisePropertiesForTest(TestCase testCase)
   {
      String testName = (testCase == null) ? "" : testCase.getName();
      ScenarioPropertyReader reader = factory.getPropertyReader(testName);

      warmup = reader.getIntProperty(ScenarioPropertyReader.WARMUP);
      loops = reader.getIntProperty(ScenarioPropertyReader.LOOPS);
      threads = reader.getIntProperty(ScenarioPropertyReader.THREADS);
      randomSleepInterval = reader.getBooleanProperty(ScenarioPropertyReader.RANDOM_SLEEP_INTERVAL);
      sleeptimeMillis = reader.getIntProperty(ScenarioPropertyReader.SLEEPTIME_MILLIS);
      logging = reader.getBooleanProperty(ScenarioPropertyReader.LOGGING);

      System.out.println("============================================");
      System.out.println("Configured ScenarioRunner for " + testCaseClass.getName() + "." + testName);
      System.out.println("   warmup:                  " + warmup);
      System.out.println("   loops:                   " + loops);
      System.out.println("   threads:                 " + threads);
      System.out.println("   Random sleep Interval:   " + randomSleepInterval);
      System.out.println("   Sleep time millis:       " + randomSleepInterval);
      System.out.println("   Logging:                 " + logging);
      System.out.println("============================================");
   }
   
   public void executeScenario(Scenario scenario, TestCase testCase) throws Exception
   {
      Scenario[] scenarios = new Scenario[] {scenario};
      executeScenario(scenarios, testCase);
   }
   
   public void executeScenario(Scenario[] scenarios, TestCase testCase) throws Exception
   {
      initialisePropertiesForTest(testCase);
      warmupScenario(scenarios);
      executeScenarios(scenarios);
   }

   private void warmupScenario(Scenario[] scenarios) throws Exception
   {
      ScenarioLoader loader = getLoader(0, scenarios, true);
      loader.start();
      loader.join();
   }
   
   private void executeScenarios(Scenario[] scenarios) throws Exception
   {
      System.out.println("Starting run with Scenarios " + getScenarioNames(scenarios));
      long start = System.currentTimeMillis();
      
      ScenarioLoader[] loaders = new ScenarioLoader[threads];
      for (int thread = 0 ; thread < threads ; thread++)
      {
         loaders[thread] = getLoader(thread, scenarios, false);
      }
      
      System.out.println("Starting threads...");
      for (int thread = 0 ; thread < loaders.length ; thread++)
      {
         loaders[thread].start();
      }
      
      for (int thread = 0 ; thread < loaders.length ; thread++)
      {
         loaders[thread].join();
      }

      long end = System.currentTimeMillis();
      boolean hadExceptions = false;
      for (int thread = 0 ; thread < loaders.length ; thread++)
      {
         if (loaders[thread].exceptions.size() > 0)
         {
            hadExceptions = true;
            for (Iterator it = loaders[thread].exceptions.iterator() ; it.hasNext() ; )
            {
               ((Exception)it.next()).printStackTrace(System.err);
            }
         }
      }
      
      System.out.println("--- DONE --- test took " + (end - start) + " ms");
      
      for (int thread = 0 ; thread < loaders.length ; thread++)
      {
         loaders[thread] = null;
      }
      
      if (hadExceptions)
      {
         throw new Exception("Exceptions occurred, see System.err");
      }
   }

   
   private ScenarioLoader getLoader(int thread, Scenario[] scenarios, boolean forWarmup)
   {
      int num = thread % scenarios.length;
      Scenario scenario = scenarios[num];
      if (logging)
      {
         scenario = new ScenarioLoggingDecorator(scenario);
      }
      return new ScenarioLoader(scenario, thread, forWarmup);
   }
   
   private int getSleepInterval()
   {
      if(sleeptimeMillis ==0) return sleeptimeMillis;

      if(randomSleepInterval)
      {
         return random.nextInt(sleeptimeMillis);
      } else
      {
         return sleeptimeMillis;
      }
   }
   
   private String getScenarioNames(Scenario[] scenarios)
   {
      StringBuffer buf = new StringBuffer("[");
      for (int i = 0 ; i < scenarios.length ; i++)
      {
         if (i > 0)
         {
            buf.append(", ");
         }
         buf.append(scenarios[i].getName());
      }
      buf.append("]");
      return buf.toString();
   }
   
   private class ScenarioLoader extends Thread
   {
      int thread;
      Scenario scenario;
      int loop;
      ArrayList exceptions = new ArrayList();
      boolean forWarmup;
      
      ScenarioLoader(Scenario scenario, int thread, boolean forWarmup)
      {
         this.scenario = scenario;
         this.thread = thread;
         this.forWarmup = forWarmup;
      }
      
      public void run()
      {
         if (forWarmup && scenario.getClass().isAnnotationPresent(SkipWarmup.class))
         {
            System.out.println("Skipping warmup for " + scenario.getName());
            return;
         }
         
         final int max = forWarmup ? warmup : loops;
         try
         {
            while (loop++ < max)
            {
               scenario.execute(thread, loop);
               Thread.sleep(getSleepInterval());
            }
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         catch(Exception e)
         {
            exceptions.add(e);
            e.printStackTrace();
         }
      }
   }
}
