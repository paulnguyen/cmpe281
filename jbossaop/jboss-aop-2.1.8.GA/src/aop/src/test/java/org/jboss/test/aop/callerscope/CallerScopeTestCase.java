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
package org.jboss.test.aop.callerscope;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.advice.Scope;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.util.collection.WeakIdentityHashMap;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unused"})
public class CallerScopeTestCase extends AOPTestWithSetup
{
   Caller caller = new Caller();
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("DotInPointcutNameTester");
      suite.addTestSuite(CallerScopeTestCase.class);
      return suite;
   }

   public CallerScopeTestCase(String name)
   {
      super(name);
   }
   
   public void testWeakIdentityHashMap()
   {
      System.out.println("*** testWeakIdentityHashMap()");
      
      //NOTE: - if we allocated the strings as:
      // String a = "a";
      // String b = "a";
      // String c = "a";
      // String d = "a";
      //
      // we would end up with only one entry in the map due to the fact that when
      // allocated as above all the following expressions would return true 
      // (since "a" lives in the constant pool of the class, so they all use the 
      // same reference):
      // a==b
      // b==c
      // c==d
      String a = new String("a");
      String b = new String("a");
      Flag cCollected = new Flag();
      Object c = new GCTarget(cCollected);
      Flag dCollected = new Flag();
      Object d = new GCTarget(dCollected);
      Long l1 = new Long(1);
      Long l2 = new Long(2);
      Long l3 = new Long(3);
      Long l4 = new Long(4);

      WeakIdentityHashMap map = new WeakIdentityHashMap();
      map.put(a, l1);
      map.put(b, l2);
      map.put(c, l3);
      map.put(d, l4);
      
      assertEquals(4, map.size());
      System.gc();
      assertEquals(4, map.size());
      
      assertEquals(l1, map.get(a));
      assertEquals(l2, map.get(b));
      assertEquals(l3, map.get(c));
      assertEquals(l4, map.get(d));
      
      c = null;
      d = null;
      
      System.gc();
      // try to garbage collect c and d 31 times at most
      for (int j = 0; j < 30 && !(cCollected.value && dCollected.value); j++) {
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
      
      assertEquals(2, map.size());

      assertEquals(l1, map.get(a));
      assertEquals(l2, map.get(b));

      System.out.println("*** testWeakIdentityHashMap() - successful");
      
   }
   
   public void testPerVm()throws Exception
   {
      System.out.println("*** testPerVm");
      clearInterceptions();
      POJO pojo = new POJO();
      checkInterceptions(Scope.PER_VM);
      
      clearInterceptions();
      pojo.perVmMethod();
      checkInterceptions(Scope.PER_VM);
      System.out.println();
   }

   public void testPerClass()throws Exception
   {
      System.out.println("*** testPerClass");
      clearInterceptions();
      POJO pojo = new POJO(1);
      checkInterceptions(Scope.PER_CLASS);

      clearInterceptions();
      pojo.perClassMethod();
      checkInterceptions(Scope.PER_CLASS);
      System.out.println();
   }

   public void testPerInstance()throws Exception
   {
      System.out.println("*** testPerInstance");
      clearInterceptions();
      POJO pojo = new POJO();
      checkInterceptions(Scope.PER_VM);
      
      clearInterceptions();
      pojo.perInstanceMethod();
      checkInterceptions(Scope.PER_INSTANCE);
      
      caller.testPerInstance(pojo);
      System.out.println();
   }
   

   public void testPerJoinpoint()throws Exception
   {
      System.out.println("*** testPerJoinpoint");
      clearInterceptions();
      POJO pojo = new POJO();
      checkInterceptions(Scope.PER_VM);

      clearInterceptions();
      pojo.perJoinpointMethod();      
      checkInterceptions(Scope.PER_JOINPOINT);
      System.out.println();
   }

   public void testPerClassJoinpoint()throws Exception
   {
      System.out.println("*** testPerClassJoinpoint");
      clearInterceptions();
      POJO pojo = new POJO(false);
      checkInterceptions(Scope.PER_CLASS_JOINPOINT);

      clearInterceptions();
      pojo.perClassJoinpointMethod();
      checkInterceptions(Scope.PER_CLASS_JOINPOINT);
      System.out.println();
   }
   
   static void clearInterceptions()
   {
      PerVmAspect.intercepted = false;
      PerVmInterceptor.intercepted = false;
      PerClassAspect.intercepted = false;
      PerClassInterceptor.intercepted = false;
      PerInstanceAspect.intercepted = false;
      PerInstanceInterceptor.intercepted = false;
      PerJoinpointAspect.intercepted = false;
      PerJoinpointInterceptor.intercepted = false;
      PerClassJoinpointAspect.intercepted = false;
      PerClassJoinpointInterceptor.intercepted = false;
   }
   
   
   static void checkInterceptions(Scope scope)
   {
      if (scope == Scope.PER_VM)
      {
         checkInterceptions(true, true, false, false, false, false, false, false, false, false);
      }
      else if (scope == Scope.PER_CLASS)
      {
         checkInterceptions(false, false, true, true, false, false, false, false, false, false);         
      }
      else if (scope == Scope.PER_INSTANCE)
      {
         checkInterceptions(false, false, false, false, true, true, false, false, false, false);         
      }
      else if (scope == Scope.PER_JOINPOINT)
      {
         checkInterceptions(false, false, false, false, false, false, true, true, false, false);         
      }
      else if (scope == Scope.PER_CLASS_JOINPOINT)
      {
         checkInterceptions(false, false, false, false, false, false, false, false, true, true);         
      }
   }
   
   static void checkInterceptions(
         boolean perVmAspect, 
         boolean perVmInterceptor, 
         boolean perClassAspect, 
         boolean perClassInterceptor, 
         boolean perInstanceAspect,
         boolean perInstanceInterceptor,
         boolean perJoinpointAspect,
         boolean perJoinpointInterceptor,
         boolean perClassJoinpointAspect,
         boolean perClassJoinpointInterceptor)
   {
      assertEquals("Wrong intercepted value for PerVMAspect", perVmAspect, PerVmAspect.intercepted);
      assertEquals("Wrong intercepted value for PerVmInterceptor", perVmInterceptor, PerVmInterceptor.intercepted);
      assertEquals("Wrong intercepted value for PerClassAspect", perClassAspect, PerClassAspect.intercepted);
      assertEquals("Wrong intercepted value for PerClassInterceptor", perClassInterceptor, PerClassInterceptor.intercepted);
      assertEquals("Wrong intercepted value for PerInstanceAspect", perInstanceAspect, PerInstanceAspect.intercepted);
      assertEquals("Wrong intercepted value for PerInstanceInterceptor", perInstanceInterceptor, PerInstanceInterceptor.intercepted);
      assertEquals("Wrong intercepted value for PerJoinpointAspect", perJoinpointAspect, PerJoinpointAspect.intercepted);
      assertEquals("Wrong intercepted value for PerJoinpointInterceptor", perJoinpointInterceptor, PerJoinpointInterceptor.intercepted);
      assertEquals("Wrong intercepted value for PerClassJoinpointAspect", perClassJoinpointAspect, PerClassJoinpointAspect.intercepted);
      assertEquals("Wrong intercepted value for PerClassJoinpointInterceptor", perClassJoinpointInterceptor, PerClassJoinpointInterceptor.intercepted);
   }
   
}


/**
 * Target of a garbage collection (used in weak hash map test).
 * 
 * @author Flavia Rainone
 */
class GCTarget
{
    Flag collected;
    public GCTarget(Flag collected)
    {
        this.collected = collected;
    }
    
    protected void finalize() throws Throwable
    {
        this.collected.value = true;
    }
}

/**
 * Helper class that stores a boolean value.
 * @author Flavia Rainone
 */
class Flag
{
    boolean value = false;
}