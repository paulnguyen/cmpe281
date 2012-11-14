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
package org.jboss.test.aop.perjoinpoint;


import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests an annotated introduction
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 45977 $
 */
public class PerJoinpointTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("PerJoinpointTester");
      suite.addTestSuite(PerJoinpointTester.class);
      return suite;
   }

   public PerJoinpointTester(String name)
   {
      super(name);
   }

   private class StaticTask extends Thread
   {
      public boolean failed = false;

      public void run()
      {
         ThreadbasedTest.staticCounter2 = 10;
         for (int i = 0; i < 10; i++)
         {
            if (ThreadbasedTest.staticCounter != i)
            {
               failed = true;
               break;
            }
            if (ThreadbasedTest.staticCounter2 != i + 10)
            {
               failed = true;
               break;
            }
            ThreadbasedTest.staticCounter++;
            ThreadbasedTest.staticCounter2++;
            try
            {
               Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
               failed = true;
               throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
            }
         }
      }
   }

   private class Task extends Thread
   {
      public boolean failed = false;
      public ThreadbasedTest test;

      public void run()
      {
         test.counter2 = 10;
         for (int i = 0; i < 10; i++)
         {
            if (test.counter != i)
            {
               failed = true;
               break;
            }
            if (test.counter2 != i + 10)
            {
               failed = true;
               break;
            }
            test.counter++;
            test.counter2++;
            try
            {
               Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
               failed = true;
               throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
            }
         }
      }
   }

   public void testStaticThreadbased() throws Exception
   {
      System.out.println("testStaticThreadbased...");
      StaticTask task1 = new StaticTask();
      StaticTask task2 = new StaticTask();
      task1.start();
      Thread.sleep(1000);
      task2.start();
      task1.join();
      task2.join();
      if (task1.failed || task2.failed) throw new RuntimeException("task failed");
   }

   public void testSharedThreadbased() throws Exception
   {
      ThreadbasedTest test = new ThreadbasedTest();
      Task task1 = new Task();
      task1.test = test;
      Task task2 = new Task();
      task2.test = test;
      task1.start();
      Thread.sleep(1000);
      task2.start();
      task1.join();
      task2.join();
      if (task1.failed || task2.failed) throw new RuntimeException("task failed");
   }

   public void testUnsharedThreadbased() throws Exception
   {
      Task task1 = new Task();
      task1.test = new ThreadbasedTest();
      Task task2 = new Task();
      task2.test = new ThreadbasedTest();
      task1.start();
      Thread.sleep(1000);
      task2.start();
      task1.join();
      task2.join();
      if (task1.failed || task2.failed) throw new RuntimeException("task failed");
   }


}

