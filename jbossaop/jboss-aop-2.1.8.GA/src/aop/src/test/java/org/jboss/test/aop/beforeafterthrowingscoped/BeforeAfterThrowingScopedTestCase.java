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
package org.jboss.test.aop.beforeafterthrowingscoped;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"unused"})
public class BeforeAfterThrowingScopedTestCase extends AOPTestWithSetup
{
   public BeforeAfterThrowingScopedTestCase(String arg0)
   {
      super(arg0);
   }

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("BeforeAfterThrowingScopedTestCase");
      suite.addTestSuite(BeforeAfterThrowingScopedTestCase.class);
      return suite;
   }

   public void testPerInstanceConstructorAspects()
   {
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo = new POJOWithPerInstanceAspects();
      assertNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNull(PerInstanceAspect.finaly);
   }
   
   public void testPerInstanceVoidMethodAspects()
   {
      System.out.println("Calling POJO 1");
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo1 = new POJOWithPerInstanceAspects();
      try
      {
         pojo1.methodWithPerInstanceAspects(false);
      }
      catch (ThrownByTestException e)
      {
         fail("Did not expect excpeption here");
      }
    
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect1 = PerInstanceAspect.before;
      
      System.out.println("Calling POJO 2");
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo2 = new POJOWithPerInstanceAspects();
      try
      {
         pojo2.methodWithPerInstanceAspects(true);
         fail("Should have thrown an exception");
      }
      catch (ThrownByTestException expected)
      {
      }
      
      assertNotNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNotNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.throwing);
      assertSame(PerInstanceAspect.throwing, PerInstanceAspect.finaly);
      PerInstanceAspect aspect2 = PerInstanceAspect.before;
    
      assertNotSame(aspect1, aspect2);
      
      
      System.out.println("Calling POJO 1 again");
      try
      {
         pojo1.methodWithPerInstanceAspects(false);
      }
      catch (ThrownByTestException e)
      {
         fail("Did not expect excpeption here");
      }
    
      PerInstanceAspect aspect1a = PerInstanceAspect.before;
      
      assertSame(aspect1, aspect1a);
      System.out.println("DONE");
   }

   public void testPerInstanceReturnMethodAspects()
   {
      System.out.println("Calling POJO 1");
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo1 = new POJOWithPerInstanceAspects();
      assertEquals(10, pojo1.methodWithPerInstanceAspects());
    
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect1 = PerInstanceAspect.before;
      
      System.out.println("Calling POJO 2");
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo2 = new POJOWithPerInstanceAspects();
      assertEquals(10, pojo2.methodWithPerInstanceAspects());
      
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect2 = PerInstanceAspect.before;
    
      assertNotSame(aspect1, aspect2);
      
      
      System.out.println("Calling POJO 1 again");
      assertEquals(10, pojo1.methodWithPerInstanceAspects());
    
      PerInstanceAspect aspect1a = PerInstanceAspect.before;
      
      assertSame(aspect1, aspect1a);
      System.out.println("DONE");
   }

   public void testPerInstanceFieldAspects()
   {
      System.out.println("Calling POJO 1");
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo1 = new POJOWithPerInstanceAspects();
      
      pojo1.field = 10;
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect1w = PerInstanceAspect.before;
      
      PerInstanceAspect.reset();
      assertEquals(10, pojo1.field);
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect1r = PerInstanceAspect.before;
      
      assertSame(aspect1w, aspect1r);
      
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects pojo2 = new POJOWithPerInstanceAspects();
      
      pojo2.field = 10;
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect2w = PerInstanceAspect.before;
      
      PerInstanceAspect.reset();
      assertEquals(10, pojo2.field);
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      assertSame(PerInstanceAspect.before, PerInstanceAspect.after);
      assertSame(PerInstanceAspect.after, PerInstanceAspect.finaly);
      PerInstanceAspect aspect2r = PerInstanceAspect.before;
      
      assertSame(aspect2w, aspect2r);
      assertNotSame(aspect1w, aspect2w);
    
      PerInstanceAspect.reset();
      pojo1.field = 10;
      assertSame(aspect1w, PerInstanceAspect.before);
   }
   
   public void testPerInstanceStaticMethodsAndFieldAspects()
   {
      PerInstanceAspect.reset();
      POJOWithPerInstanceAspects.staticField = 10;
      assertNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNull(PerInstanceAspect.finaly);

      assertEquals(10, POJOWithPerInstanceAspects.staticField);
      assertNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNull(PerInstanceAspect.finaly);
      
      assertEquals(101, POJOWithPerInstanceAspects.staticMethod(101));
      assertNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNull(PerInstanceAspect.finaly);
   }
   
   public void testAllPerInstanceAspectsSame() 
   {
      POJOWithPerInstanceAspects pojo = new POJOWithPerInstanceAspects();

      PerInstanceAspect.reset();
      pojo.methodWithPerInstanceAspects();
      assertNotNull(PerInstanceAspect.before);
      PerInstanceAspect aspect1 = PerInstanceAspect.before;

      PerInstanceAspect.reset();
      pojo.field = 10;
      assertNotNull(PerInstanceAspect.before);
      PerInstanceAspect aspect2 = PerInstanceAspect.before;
      
      assertSame(aspect1, aspect2);
      
      PerInstanceAspect.reset();
      try
      {
         pojo.methodWithPerInstanceAspects(false);
      }
      catch (ThrownByTestException e)
      {
         fail("unexpected exception");
      }
      assertNotNull(PerInstanceAspect.before);
      PerInstanceAspect aspect3 = PerInstanceAspect.before;
      
      assertSame(aspect1, aspect3);
   }
   
   public void testPerjoinpointConstructorAspects()
   {
      //For a constructor
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects pojo = new POJOWithPerJoinpointAspects();
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect pja = PerJoinpointAspect.before;
      
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects pojo2 = new POJOWithPerJoinpointAspects();
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      assertEquals(pja, PerJoinpointAspect.before); //Works like PER_CLASS_JOINPOINT when no instance
   }
   
   public void testPerJoinpointMethodAspects()
   {
      POJOWithPerJoinpointAspects pojo1 = new POJOWithPerJoinpointAspects();
      
      PerJoinpointAspect.reset();
      assertEquals(10, pojo1.methodA(10));
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1a = PerJoinpointAspect.before; 
      
      POJOWithPerJoinpointAspects pojo2 = new POJOWithPerJoinpointAspects();
      
      PerJoinpointAspect.reset();
      assertEquals(10, pojo2.methodA(10));
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect2a = PerJoinpointAspect.before;
      assertNotSame(aspect1a, aspect2a);
      
      
      PerJoinpointAspect.reset();
      pojo1.methodB();
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1b = PerJoinpointAspect.before;
      assertNotSame(aspect1b, aspect1a);
      
      PerJoinpointAspect.reset();
      pojo2.methodB();
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect2b = PerJoinpointAspect.before;
      assertNotSame(aspect2a, aspect2b);
      assertNotSame(aspect1b, aspect2b);
      
      PerJoinpointAspect.reset();
      assertEquals(100, pojo1.methodA(100));

      assertNotNull(PerJoinpointAspect.before);
      assertEquals(aspect1a, PerJoinpointAspect.before); 
      
   }
   
   public void testPerJoinpointStaticMethodAspects()
   {
      PerJoinpointAspect.reset();
      assertEquals(10, POJOWithPerJoinpointAspects.staticMethodA(10));
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspectA = PerJoinpointAspect.before; 
      

      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects.staticMethodB();
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspectB = PerJoinpointAspect.before;
      assertNotSame(aspectB, aspectA);
      
      PerJoinpointAspect.reset();
      assertEquals(10, POJOWithPerJoinpointAspects.staticMethodA(10));
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);    
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      assertSame(aspectA, PerJoinpointAspect.before);
   }

   public void testPerJoinpointFieldAspects()
   {
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects pojo1 = new POJOWithPerJoinpointAspects();
      PerJoinpointAspect ctorAspect = PerJoinpointAspect.before;
      assertNotNull(ctorAspect);
      POJOWithPerJoinpointAspects pojo2 = new POJOWithPerJoinpointAspects();

      PerJoinpointAspect.reset();
      pojo1.fieldA = 10;
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1Aw = PerJoinpointAspect.before;

      PerJoinpointAspect.reset();
      assertEquals(10, pojo1.fieldA );
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1Ar = PerJoinpointAspect.before;
      assertSame(aspect1Aw, aspect1Ar);
      assertNotSame(ctorAspect, aspect1Ar);
      
      PerJoinpointAspect.reset();
      pojo1.fieldB = 10;

      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      assertNotSame(aspect1Aw, PerJoinpointAspect.after);
      assertNotSame(ctorAspect, PerJoinpointAspect.after);
      
      PerJoinpointAspect.reset();
      pojo2.fieldA = 100;
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect2Aw = PerJoinpointAspect.before;
      assertNotSame(aspect2Aw, aspect1Ar);

      PerJoinpointAspect.reset();
      assertEquals(100, pojo2.fieldA );

      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect2Ar = PerJoinpointAspect.before;
      assertSame(aspect2Ar, aspect2Aw);
      
      PerJoinpointAspect.reset();
      pojo1.fieldA = 1000;
      assertNotNull(PerJoinpointAspect.before);
      assertSame(aspect1Aw, PerJoinpointAspect.before);
   }
   

   public void testPerJoinpointStaticFieldAspects()
   {
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects.staticFieldA = 10;
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1Aw = PerJoinpointAspect.before;

      PerJoinpointAspect.reset();
      assertEquals(10, POJOWithPerJoinpointAspects.staticFieldA );
      
      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      PerJoinpointAspect aspect1Ar = PerJoinpointAspect.before;
      assertSame(aspect1Aw, aspect1Ar);
      
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects.staticFieldB = 10;

      assertNotNull(PerJoinpointAspect.before);
      assertNotNull(PerJoinpointAspect.after);
      assertNull(PerJoinpointAspect.throwing);
      assertNotNull(PerJoinpointAspect.finaly);
      assertSame(PerJoinpointAspect.before, PerJoinpointAspect.after);
      assertSame(PerJoinpointAspect.finaly, PerJoinpointAspect.after);
      assertNotSame(aspect1Aw, PerJoinpointAspect.after);
      
      PerJoinpointAspect.reset();
      POJOWithPerJoinpointAspects.staticFieldA = 1000;
      assertNotNull(PerJoinpointAspect.before);
      assertSame(aspect1Aw, PerJoinpointAspect.before);
   }
   

   public void testPerClassjoinpointConstructorAspects()
   {
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects pojo1 = new POJOWithPerClassJoinpointAspects();
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect1c = PerClassJoinpointAspect.before;
      
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects pojo2 = new POJOWithPerClassJoinpointAspects();
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect2c = PerClassJoinpointAspect.before;
      assertSame(aspect1c, aspect2c);
   }
   
   public void testPerClassJoinpointFieldAspects()
   {
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects pojo1 = new POJOWithPerClassJoinpointAspects();
      PerClassJoinpointAspect ctorAspect = PerClassJoinpointAspect.before;
      assertNotNull(ctorAspect);
      POJOWithPerClassJoinpointAspects pojo2 = new POJOWithPerClassJoinpointAspects();

      PerClassJoinpointAspect.reset();
      pojo1.field = 10;
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect1fw = PerClassJoinpointAspect.before;

      PerClassJoinpointAspect.reset();
      assertEquals(10, pojo1.field );
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect1fr = PerClassJoinpointAspect.before;
      assertSame(aspect1fw, aspect1fr);
      assertNotSame(ctorAspect, aspect1fr);

      PerClassJoinpointAspect.reset();
      pojo2.field = 100;
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect2fw = PerClassJoinpointAspect.before;
      assertSame(aspect1fw, aspect1fr);

      PerClassJoinpointAspect.reset();
      assertEquals(100, pojo2.field );

      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect2fr = PerClassJoinpointAspect.before;
      assertSame(aspect2fr, aspect2fw);
      
      PerClassJoinpointAspect.reset();
      pojo1.field = 1000;
      assertNotNull(PerClassJoinpointAspect.before);
      assertSame(aspect1fw, PerClassJoinpointAspect.before);
   }
   
   
   public void testPerClassJoinpointMethodAspects()
   {
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects pojo1 = new POJOWithPerClassJoinpointAspects();
      PerClassJoinpointAspect ctorAspect = PerClassJoinpointAspect.before;
      assertNotNull(ctorAspect);
      POJOWithPerClassJoinpointAspects pojo2 = new POJOWithPerClassJoinpointAspects();

      PerClassJoinpointAspect.reset();
      pojo1.method();
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect1 = PerClassJoinpointAspect.before;

      PerClassJoinpointAspect.reset();
      pojo2.method();
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect aspect2 = PerClassJoinpointAspect.before;
      assertSame(aspect2, aspect1);

      PerClassJoinpointAspect.reset();
      pojo1.method();
      assertNotNull(PerClassJoinpointAspect.before);
      assertEquals(aspect1, PerClassJoinpointAspect.before);
   }
   
   public void testPerClassJoinpointStaticMethodAndFieldAspects()
   {
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects.staticField = 1000;
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect fieldW = PerClassJoinpointAspect.after;
      
      PerClassJoinpointAspect.reset();
      assertEquals(1000, POJOWithPerClassJoinpointAspects.staticField);
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      assertSame(fieldW, PerClassJoinpointAspect.after);
      
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects.staticMethod();
      
      assertNotNull(PerClassJoinpointAspect.before);
      assertNotNull(PerClassJoinpointAspect.after);
      assertNull(PerClassJoinpointAspect.throwing);
      assertNotNull(PerClassJoinpointAspect.finaly);
      assertSame(PerClassJoinpointAspect.before, PerClassJoinpointAspect.after);
      assertSame(PerClassJoinpointAspect.finaly, PerClassJoinpointAspect.after);
      PerClassJoinpointAspect method = PerClassJoinpointAspect.after;
      assertNotSame(method, fieldW);
      
      PerClassJoinpointAspect.reset();
      POJOWithPerClassJoinpointAspects.staticMethod();
      assertNotNull(PerClassJoinpointAspect.before);
      assertSame(method, PerClassJoinpointAspect.before);
   }
   
   public void testPerClassAndPerVmConstructorAspects()
   {
      //Call constructors
      PerVmAspect.reset();
      PerClassAspect.reset();
      POJOWithPerClassAndPerVmAspects pojo1 = new POJOWithPerClassAndPerVmAspects();
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      PerClassAspect pc1 = PerClassAspect.before;
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      PerVmAspect pv1 = PerVmAspect.before;
      
      PerVmAspect.reset();
      PerClassAspect.reset();
      
      POJOWithPerClassAndPerVmAspects pojo2 = new POJOWithPerClassAndPerVmAspects();
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
   }
   
   public void testPerClassAndPerVmFieldAspects()
   {
      PerVmAspect.reset();
      PerClassAspect.reset();
      POJOWithPerClassAndPerVmAspects pojo1 = new POJOWithPerClassAndPerVmAspects();
      assertNotNull(PerClassAspect.before);
      PerClassAspect pc1 = PerClassAspect.before;
      
      assertNotNull(PerVmAspect.before);
      PerVmAspect pv1 = PerVmAspect.before;
      
      POJOWithPerClassAndPerVmAspects pojo2 = new POJOWithPerClassAndPerVmAspects();
      
      //Call fields
      PerVmAspect.reset();
      PerClassAspect.reset();
      pojo1.field = 10; 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
      
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(10, pojo1.field); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
      
      PerVmAspect.reset();
      PerClassAspect.reset();
      pojo2.field = 100; 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
      
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(100, pojo2.field); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
   }
   
   public void testPerClassAndPerVmMethodAspects()
   {
      PerVmAspect.reset();
      PerClassAspect.reset();
      POJOWithPerClassAndPerVmAspects pojo1 = new POJOWithPerClassAndPerVmAspects();
      assertNotNull(PerClassAspect.before);
      PerClassAspect pc1 = PerClassAspect.before;
      
      assertNotNull(PerVmAspect.before);
      PerVmAspect pv1 = PerVmAspect.before;
      
      POJOWithPerClassAndPerVmAspects pojo2 = new POJOWithPerClassAndPerVmAspects();
      
      //Call fields
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(10, pojo1.method(10)); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
           
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(100, pojo2.method(100)); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
      
   }
   

   public void testPerClassAndPerVmStaticFieldAspects()
   {
      PerVmAspect.reset();
      PerClassAspect.reset();
      
      //Call fields
      PerVmAspect.reset();
      PerClassAspect.reset();
      POJOWithPerClassAndPerVmAspects.staticField = 10; 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      PerClassAspect pc1 = PerClassAspect.before;
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      PerVmAspect pv1 = PerVmAspect.before;
      
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(10, POJOWithPerClassAndPerVmAspects.staticField); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
      
      //Call fields
      PerVmAspect.reset();
      PerClassAspect.reset();
      assertEquals(10, POJOWithPerClassAndPerVmAspects.staticMethod(10)); 
      
      assertNotNull(PerClassAspect.before);
      assertNotNull(PerClassAspect.after);
      assertNull(PerClassAspect.throwing);
      assertNotNull(PerClassAspect.finaly);
      assertSame(PerClassAspect.before, PerClassAspect.after);
      assertSame(PerClassAspect.finaly, PerClassAspect.after);
      assertEquals(pc1, PerClassAspect.before);
      
      assertNotNull(PerVmAspect.before);
      assertNotNull(PerVmAspect.after);
      assertNull(PerVmAspect.throwing);
      assertNotNull(PerVmAspect.finaly);
      assertSame(PerVmAspect.before, PerVmAspect.after);
      assertSame(PerVmAspect.finaly, PerVmAspect.after);
      assertEquals(pv1, PerVmAspect.before);
   }
   
   public void testNullAspectFactory() throws Exception
   {
      // check that all joinpoints execute without exceptions
      int value = POJOWithNullAspect.staticField;
      POJOWithNullAspect.staticField = value + 1;
      POJOWithNullAspect.staticMethod();
      POJOWithNullAspect.callerMethod2();
      POJOWithNullAspect pojo = new POJOWithNullAspect();
      value += pojo.field;
      pojo.field = value - 10;
      pojo.method();
      pojo.callerMethod1();
      new POJOWithNullAspect(5, 6);
      
      // check that mixed method does get correctly intercepted without errors
      PerInstanceAspect.reset();
      pojo.mixedMethod(false);
      assertNotNull(PerInstanceAspect.before);
      assertNotNull(PerInstanceAspect.after);
      assertNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
      PerInstanceAspect.reset();
      boolean thrown = false;
      try
      {
         pojo.mixedMethod(true);
      }
      catch(Exception e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      assertNotNull(PerInstanceAspect.before);
      assertNull(PerInstanceAspect.after);
      assertNotNull(PerInstanceAspect.throwing);
      assertNotNull(PerInstanceAspect.finaly);
   }
   
   
   //PER_JOINPOINT should act like PER_CLASS_JOINPOINT for static fields and methods  
}
