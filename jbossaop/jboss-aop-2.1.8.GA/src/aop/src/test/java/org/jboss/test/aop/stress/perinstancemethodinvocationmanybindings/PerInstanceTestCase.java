/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.stress.perinstancemethodinvocationmanybindings;

import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.ScenarioTest;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unused")
public class PerInstanceTestCase extends ScenarioTest
{
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(PerInstanceTestCase.class);
   }

   public PerInstanceTestCase(String name)
   {
      // FIXME PerInstanceTestCase constructor
      super(name);
   }

   public void testPerInstanceInterceptorRepeatInstantiation() throws Exception
   {
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo = new POJO();
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo1 = new POJO(1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo2 = new POJO(1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo3 = new POJO(1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo4 = new POJO(1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo5 = new POJO(1, 1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo6 = new POJO(1, 1, 1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo7 = new POJO(1, 1, 1, 1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo8 = new POJO(1, 1, 1, 1, 1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      POJO pojo9 = new POJO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
      assertEquals(1, PerVmInterceptor.called);
      assertEquals(0, PerInstanceInterceptor.called);
      
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      pojo.method1();
      pojo.method2();
      pojo.method3();
      pojo.method4();
      pojo.method5();
      pojo.method6();
      pojo.method7();
      pojo.method8();
      pojo.method9();
      pojo.method10();
      pojo.method11();
      pojo.method12();
      assertEquals(0, PerVmInterceptor.called);
      assertEquals(12, PerInstanceInterceptor.called);
      
      PerInstanceInterceptor.called = 0;
      PerVmInterceptor.called = 0;
      pojo.field1 = 1;
      pojo.field2 = 2;
      pojo.field3 = 3;
      pojo.field4 = 4;
      pojo.field5 = 5;
      pojo.field6 = 6;
      pojo.field7 = 7;
      pojo.field8 = 8;
      pojo.field9 = 9;
      pojo.field10 = 10;
      pojo.field11 = 11;
      pojo.field12 = 12;
      assertEquals(1, pojo.field1);
      assertEquals(2, pojo.field2);
      assertEquals(3, pojo.field3);
      assertEquals(4, pojo.field4);
      assertEquals(5, pojo.field5);
      assertEquals(6, pojo.field6);
      assertEquals(7, pojo.field7);
      assertEquals(8, pojo.field8);
      assertEquals(9, pojo.field9);
      assertEquals(10, pojo.field10);
      assertEquals(11, pojo.field11);
      assertEquals(12, pojo.field12);
      assertEquals(0, PerVmInterceptor.called);
      assertEquals(24, PerInstanceInterceptor.called);
      
      getRunner().executeScenario(new PerInstanceInterceptorRepeatInstantiationScenario(), this);
   }
   
   private class PerInstanceInterceptorRepeatInstantiationScenario extends AbstractScenario
   {
      public void execute(int thread, int loop) throws Exception
      {
         POJO pojo = new POJO();
         pojo.method1();
//         pojo.field1 = 10;
//         int i = pojo.field1;
      }
   }
}
