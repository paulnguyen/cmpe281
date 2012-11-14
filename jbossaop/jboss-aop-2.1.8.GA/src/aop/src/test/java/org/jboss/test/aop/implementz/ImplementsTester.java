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
package org.jboss.test.aop.implementz;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
public class ImplementsTester extends AOPTestWithSetup
{

   /**
    * Constructor for ImplementsTester.
    * @param arg0
    */
   public ImplementsTester(String arg0)
   {
      super(arg0);
   }

   public void testImplements() throws Exception
   {
      System.out.println("RUNNING TEST $IMPLEMENTS");
      
      ImplementsPOJO pojo = new ImplementsPOJO();

      CallerInterceptor.called = false;
      pojo.method2();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      pojo.method3();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      pojo.method3("x");
      assertTrue(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      pojo.method4();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      pojo.pojoMethod();
      assertFalse(CallerInterceptor.called);
      
      ImplementsPOJOChild child = new ImplementsPOJOChild();
      CallerInterceptor.called = false;
      child.method1();
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method1("s");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method2();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method3();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method3("c");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method4();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method4("x");
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.notImplemented();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      child.matchedUsingHas();
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.pojoMethod();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.pojoChildMethod();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      child.methodAnnotated();
      assertTrue(CallerInterceptor.called);

      ImplementsPOJOGrandChild grandChild = new ImplementsPOJOGrandChild();
      CallerInterceptor.called = false;
      grandChild.pojoMethod();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      grandChild.pojoChildMethod();
      assertTrue(CallerInterceptor.called);
      
   }

   public void testImplementing() throws Exception
   {
      System.out.println("RUNNING TEST $IMPLEMENTING");
      
      ImplementingPOJO pojo = new ImplementingPOJO();

      CallerInterceptor.called = false;
      pojo.method2();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      pojo.method3();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      pojo.method3("x");
      assertTrue(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      pojo.method4();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      pojo.method4("x");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      pojo.pojoMethod();
      assertFalse(CallerInterceptor.called);
      
      ImplementingPOJOChild child = new ImplementingPOJOChild();
      CallerInterceptor.called = false;
      child.method1();
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method1("s");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method2();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method3();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method3("c");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method4();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.method4("x");
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.notImplemented();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      child.pojoMethod();
      assertFalse(CallerInterceptor.called);

      CallerInterceptor.called = false;
      child.pojoChildMethod();
      assertFalse(CallerInterceptor.called);
      
      CallerInterceptor.called = false;
      child.methodAnnotated();
      assertTrue(CallerInterceptor.called);

      ImplementingPOJOGrandChild grandChild = new ImplementingPOJOGrandChild();
      CallerInterceptor.called = false;
      grandChild.pojoMethod();
      assertTrue(CallerInterceptor.called);

      CallerInterceptor.called = false;
      grandChild.pojoChildMethod();
      assertTrue(CallerInterceptor.called);
      
   }

}
