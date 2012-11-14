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
package org.jboss.test.aop.rebuildingchain;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A TestRebuildingChain.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RebuildingChainTestCase extends AOPTestWithSetup
{

   private static boolean failed = false;
   
   public RebuildingChainTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("RebuildingChainTestCase");
      suite.addTestSuite(RebuildingChainTestCase.class);
      return suite;
   }
   
   public void setUp() throws Exception
   {
      failed = false;
      super.setUp();
   }
   
   public void testFieldReadWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new FieldReadSyncThread(),
            FieldReadSyncThread.POINTCUT, FieldReadSyncThread.NAME);
   }
   
   public void testFieldReadWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new FieldReadSyncThread(),
            FieldReadSyncThread.POINTCUT, FieldReadSyncThread.NAME);
   }
   
   public void testStaticFieldReadWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new StaticFieldReadSyncThread(),
            StaticFieldReadSyncThread.POINTCUT, StaticFieldReadSyncThread.NAME);
   }
   
   public void testStaticFieldReadWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new StaticFieldReadSyncThread(),
            StaticFieldReadSyncThread.POINTCUT, StaticFieldReadSyncThread.NAME);
   }
   
   public void testFieldWriteWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new FieldWriteSyncThread(),
            FieldWriteSyncThread.POINTCUT, FieldWriteSyncThread.NAME);
   }
   
   public void testFieldWriteWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new FieldWriteSyncThread(),
            FieldWriteSyncThread.POINTCUT, FieldWriteSyncThread.NAME);
   }
   
   public void testStaticFieldWriteWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new StaticFieldWriteSyncThread(),
            StaticFieldWriteSyncThread.POINTCUT, StaticFieldWriteSyncThread.NAME);
   }
   
   public void testStaticFieldWriteWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new StaticFieldWriteSyncThread(),
            StaticFieldWriteSyncThread.POINTCUT, StaticFieldWriteSyncThread.NAME);
   }
   
   public void testConstructorExecutionWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new ConstructorExecutionSyncThread(),
            ConstructorExecutionSyncThread.POINTCUT,
            ConstructorExecutionSyncThread.NAME);
   }
   
   public void testConstructorExecutionWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new ConstructorExecutionSyncThread(),
            ConstructorExecutionSyncThread.POINTCUT,
            ConstructorExecutionSyncThread.NAME);
   }
   
   // TODO This must be uncommented after JBAOP-566 is fixed
   /*
   public void testConstructionWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new ConstructionSyncThread(),
            ConstructionSyncThread.POINTCUT, ConstructionSyncThread.NAME);
   }
   
   public void testConstructionWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new ConstructionSyncThread(),
            ConstructionSyncThread.POINTCUT, ConstructionSyncThread.NAME);
   }*/
   
   public void testMethodExecutionWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new MethodExecutionSyncThread(),
            MethodExecutionSyncThread.POINTCUT, MethodExecutionSyncThread.NAME);
   }
   
   public void testMethodExecutionWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new MethodExecutionSyncThread(),
            MethodExecutionSyncThread.POINTCUT, MethodExecutionSyncThread.NAME);
   }
   
   public void testStaticMethodExecutionWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new StaticMethodExecutionSyncThread(),
            StaticMethodExecutionSyncThread.POINTCUT,
            StaticMethodExecutionSyncThread.NAME);
   }
   
   public void testStaticMethodExecutionWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new StaticMethodExecutionSyncThread(),
            StaticMethodExecutionSyncThread.POINTCUT,
            StaticMethodExecutionSyncThread.NAME);
   }
   
   // TODO This must be uncommented as part of task JBAOP-537 after JBAOP-433 is fixed
   /*public void testConstructorCallByConstructorWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new ConstructorCallByConstructorSyncThread(),
            ConstructorCallByConstructorSyncThread.POINTCUT,
            ConstructorCallByConstructorSyncThread.NAME);
   }
   
   public void testConstructorCallByConstructorWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new ConstructorCallByConstructorSyncThread(),
            ConstructorCallByConstructorSyncThread.POINTCUT,
            ConstructorCallByConstructorSyncThread.NAME);
   }
   
   public void testConstructorCallByMethodWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new ConstructorCallByMethodSyncThread(),
            ConstructorCallByMethodSyncThread.POINTCUT,
            ConstructorCallByMethodSyncThread.NAME);
   }
   
   public void testConstructorCallByMethodWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new ConstructorCallByMethodSyncThread(),
            ConstructorCallByMethodSyncThread.POINTCUT,
            ConstructorCallByMethodSyncThread.NAME);
   }
   
   public void testMethodCallByConstructorWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new MethodCallByConstructorSyncThread(),
            MethodCallByConstructorSyncThread.POINTCUT,
            MethodCallByConstructorSyncThread.NAME);
   }
   
   public void testMethodCallByConstructorWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new MethodCallByConstructorSyncThread(),
            MethodCallByConstructorSyncThread.POINTCUT,
            MethodCallByConstructorSyncThread.NAME);
   }

   public void testMethodCallByMethodWithSetDone() throws Exception
   {
      assertRebuildingChainWithSetDone(new MethodCallByMethodSyncThread(),
            MethodCallByMethodSyncThread.POINTCUT,
            MethodCallByMethodSyncThread.NAME);
   }
   
   public void testMethodCallByMethodWithJoin() throws Exception
   {
      assertRebuildingChainWithJoin(new MethodCallByMethodSyncThread(),
            MethodCallByMethodSyncThread.POINTCUT,
            MethodCallByMethodSyncThread.NAME);
   }*/
   
   private void assertRebuildingChainWithSetDone(SyncThread st,
         String pointcutExpression, String bindingPrefix) throws Exception
   {
      RebuildThread rt = startThreads(st, pointcutExpression, bindingPrefix);
      
      Thread.sleep(200);
      
      st.setDone(true);
      rt.setDone(true);
      rt.unlinkAdvice();
      
      assertFalse("Failed to match pointcut when rebuilding the chain....", failed);
   }
   
   private void assertRebuildingChainWithJoin(SyncThread st, String pointcutExpression,
         String bindingPrefix) throws Exception
   {
      RebuildThread rt = startThreads(st, pointcutExpression, bindingPrefix + "Join");
      
      st.join();
      rt.join();
      
      rt.unlinkAdvice();
      
      assertFalse("Failed to match pointcut when rebuilding the chain....", failed);
   }
   
   private RebuildThread startThreads(SyncThread st, String pointcutExpression,
         String bindingPrefix) throws Exception
   {
      System.out.println("testing rebuildingchain!");
      
      RebuildThread rt = new RebuildThread(pointcutExpression, bindingPrefix);
      rt.linkNewAdvice();
      
      Thread.sleep(20);
      
      rt.start();
      st.start();
      
      return rt;
   }
   
   public static void setTestFailed()
   {
      failed = true;
   }
}