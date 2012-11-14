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
package org.jboss.test.aop.beforeafterthrowingstack;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class BeforeAfterThrowingStackTestCase  extends AOPTestWithSetup
{
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("BeforeAfterThrowingStackTestCase");
      suite.addTestSuite(BeforeAfterThrowingStackTestCase.class);
      return suite;
   }

   public BeforeAfterThrowingStackTestCase(String name)
   {
      super(name);
   }

   public void testMethodNoStack()
   {
      Aspect.reset();
      POJO pojo = new POJO();
      pojo.noStack();
      
      assertEquals(0, Aspect.before);
      assertEquals(0, Aspect.after);
      assertEquals(0, Aspect.throwing);
      assertEquals(0, Aspect.finaly);
      assertEquals(0, Aspect.aroundBefore);
      assertEquals(0, Aspect.aroundAfter);
      assertEquals(0, Aspect.aroundThrowing);
      assertEquals(0, Aspect.aroundFinally);
   }
   
   public void testMethodWithStack()
   {
      Aspect.reset();
      POJO pojo = new POJO();
      pojo.stack();

      assertEquals(1, Aspect.before);
      assertEquals(1, Aspect.after);
      assertEquals(0, Aspect.throwing);
      assertEquals(1, Aspect.finaly);
      assertEquals(1, Aspect.aroundBefore);
      assertEquals(1, Aspect.aroundAfter);
      assertEquals(0, Aspect.aroundThrowing);
      assertEquals(1, Aspect.aroundFinally);
   }
   
   public void testMethodWithStackAndException()
   {
      Aspect.reset();
      POJO pojo = new POJO();
      try
      {
         pojo.stackWithException();
         fail("Should have had ThrownByTestException");
      }
      catch(ThrownByTestException expected)
      {
      }
      
      assertEquals(1, Aspect.before);
      assertEquals(0, Aspect.after);
      assertEquals(1, Aspect.throwing);
      assertEquals(1, Aspect.finaly);
      assertEquals(1, Aspect.aroundBefore);
      assertEquals(0, Aspect.aroundAfter);
      assertEquals(1, Aspect.aroundThrowing);
      assertEquals(1, Aspect.aroundFinally);
   }
}