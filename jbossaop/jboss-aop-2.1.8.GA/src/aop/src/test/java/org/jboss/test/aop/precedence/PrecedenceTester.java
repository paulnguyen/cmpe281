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
package org.jboss.test.aop.precedence;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 92597 $
 */
@SuppressWarnings({"unchecked"})
public class PrecedenceTester extends AOPTestWithSetup
{
  final static Precedence[] precedences = {
      new Precedence("SimpleInterceptor2", "TestAspect.advice"),
      new Precedence("SimpleInterceptor2", "SimpleInterceptor3"),
      new Precedence("SimpleInterceptor3", "TestAspect.advice"),
      new Precedence("TestAspect.advice", "TestAspect2.advice"),
      new Precedence("TestAspect.advice2", "TestAspect.advice3"),
      new Precedence("TestAspect.advice3", "TestAspect2.advice"),
      new Precedence("SimpleInterceptor", "SimpleInterceptor2"),
      new Precedence("TestAspect2.advice", "TestAspect3.advice")
   };
         
   private POJO pojo;

   public static Test suite()
   {
      TestSuite suite = new TestSuite("PrecedenceTester");
      suite.addTestSuite(PrecedenceTester.class);
      return suite;
   }

   public PrecedenceTester(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      pojo = new POJO();
      Interceptions.reset();
   }

   public void testConstructor() throws Exception
   {
      System.out.println("*** Invoke constructor");
      Interceptions.reset();
      new POJO();
      checkInterceptions(8);
      /* expected something like 
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor2",
      "SimpleInterceptor3",
      "TestAspect.advice",
      "TestAspect2.advice",
      "LastAspect.advice",
      "LastAspect2.advice"*/
   }

   @SuppressWarnings("all")
   public void testVarRead()
   {
      System.out.println("*** Invoke field read");
      Interceptions.reset();
      int i = pojo.var;
      checkInterceptions(10);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor",
      "SimpleInterceptor2",
      "SimpleInterceptor3",
      "TestAspect.advice",
      "TestAspect2.advice",
      "TestAspect3.advice",
      "LastAspect.advice",
      "LastAspect2.advice"
       */
   }

   public void testVarWrite()
   {
      System.out.println("*** Invoke field write");
      Interceptions.reset();
      pojo.var = 1;
      checkInterceptions(10);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor",
      "SimpleInterceptor2",
      "SimpleInterceptor3",
      "TestAspect.advice",
      "TestAspect2.advice",
      "TestAspect3.advice",
      "LastAspect.advice",
      "LastAspect2.advice"
       */
   }

   public void testOneMethod()
   {
      System.out.println("*** Invoke oneMethod");
      Interceptions.reset();
      pojo.oneMethod();
      checkInterceptions(10);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor",
      "SimpleInterceptor2",
      "SimpleInterceptor3",
      "TestAspect.advice",
      "TestAspect2.advice",
      "TestAspect3.advice",
      "LastAspect.advice",
      "LastAspect2.advice"
       */
   }

   public void testTwoMethod()
   {
      Interceptions.reset();
      pojo.twoMethod();
      checkInterceptions(9);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3",
      "TestAspect2.advice",
      "LastAspect.advice",
      "LastAspect2.advice"
       */
   }

   public void testThreeMethod()
   {
      Interceptions.reset();
      pojo.threeMethod();
      checkInterceptions(7);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3",
      "LastAspect.advice",
      "LastAspect2.advice" */
   }

   public void testFourMethod()
   {
      Interceptions.reset();
      pojo.fourMethod();
      checkInterceptions(9);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "SimpleInterceptor",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3",
      "TestAspect2.advice",
      "LastAspect.advice",
      "LastAspect2.advice"
       */
   }

   public void testFiveMethod()
   {
      Interceptions.reset();
      POJO.factoryMethod();
      checkInterceptions(7);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3",
      "LastAspect.advice",
      "LastAspect2.advice" */
   }

   public void testSixMethod()
   {
      Interceptions.reset();
      pojo.sixMethod();
      checkInterceptions(7);
      /* expected something like
      "FirstInterceptor",
      "FirstInterceptor2",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3",
      "LastAspect.advice",
      "LastAspect2.advice" */
   }

   public void testSevenMethod()
   {
      Interceptions.reset();
      pojo.sevenMethod();
      checkInterceptions(6);
      /* expected something like
      "SimpleInterceptor",
      "SimpleInterceptor2",
      "SimpleInterceptor3",
      "TestAspect.advice",
      "TestAspect.advice2",
      "TestAspect.advice3"*/
   }

   private void checkInterceptions(int expected)
   {
      ArrayList intercepted = Interceptions.intercepted;
      assertEquals("Wrong number of interceptions", expected,intercepted.size());
      // for printing error messages only
      StringBuffer interceptedAdviceString = new StringBuffer('\"');
      if (expected > 0)
      {
         Iterator<String> iterator = Interceptions.intercepted.iterator();
         interceptedAdviceString.append('\"').append(iterator.next());
         while (iterator.hasNext())
         {
            interceptedAdviceString.append(", ").append(iterator.next());
         }
      }
      interceptedAdviceString.append('\"');
      
      for (Precedence precedence: precedences)
      {
         boolean foundSecondAdvice = false;
         
         for (String advice: Interceptions.intercepted)
         {
            if (advice.equals(precedence.firstAdvice))
            {
               // can only have found first advice before second advice
               assertFalse("Precedence rule not asserted: " + precedence.firstAdvice + 
                     " is after " + precedence.secondAdvice + " at chain " +
                     interceptedAdviceString, foundSecondAdvice );
            }
            else if (advice.equals(precedence.secondAdvice))
            {
               foundSecondAdvice = true;
            }
         }
      }
   }

   private static class Precedence
   {
      public String firstAdvice;
      public String secondAdvice;
      public Precedence(String firstAdvice, String secondAdvice)
      {
         this.firstAdvice= firstAdvice;
         this.secondAdvice= secondAdvice;
      }
   }
}