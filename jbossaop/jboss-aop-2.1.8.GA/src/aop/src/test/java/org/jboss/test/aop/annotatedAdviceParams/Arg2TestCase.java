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
package org.jboss.test.aop.annotatedAdviceParams;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


/**
 * Tests the use of @Arg parameters (with around advices only).
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 64564 $
 * 
 * @see ArgTestCase
 */
public class Arg2TestCase extends AOPTestWithSetup
{
   ArgsPOJO2 pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("Arg2TestCase");
      suite.addTestSuite(Arg2TestCase.class);
      return suite;
   }

   public Arg2TestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      pojo = new ArgsPOJO2();
      ArgAspect.clear();
   }
   
   public void testBench()
   {
      {
         long start = System.currentTimeMillis();
         for (int i = 0; i < 1000000; i++)
         {
            pojo.bunchArgs(1, 2.2, 3.3F, "four", 5);
         }
         long end = System.currentTimeMillis() - start;
         System.out.println("bunchArgs: " + end);
      }

      {
         long start = System.currentTimeMillis();
         for (int i = 0; i < 1000000; i++)
         {
            pojo.bunchWrapped(1, 2.2, 3.3F, "four", 5);
         }
         long end = System.currentTimeMillis() - start;
         System.out.println("bunchWrapped: " + end);
      }

      {
         long start = System.currentTimeMillis();
         for (int i = 0; i < 1000000; i++)
         {
            pojo.bunchArgsWithInvocation(1, 2.2, 3.3F, "four", 5);
         }
         long end = System.currentTimeMillis() - start;
         System.out.println("bunchArgsWithInvocation: " + end);
      }
      assertTrue(ArgAspect2.argsWithInvocation);
      assertTrue(ArgAspect2.args);
      assertTrue(ArgAspect2.wrapped);
   }

   public void testEcho()
   {
      pojo.echo("hello");
      assertTrue(ArgAspect2.echoCalled);
   }

   public void testBunch()
   {
      pojo.bunch(1, 2.2, 3.3F, "four", 5);
      assertTrue(ArgAspect2.bunchCalled);
      assertTrue(ArgAspect2.bunch2Called);
      assertTrue(ArgAspect2.arg1Called);
      assertTrue(ArgAspect2.arg2Called);
      assertTrue(ArgAspect2.arg3Called);
      assertTrue(ArgAspect2.arg4Called);
      assertTrue(ArgAspect2.arg15Called);
      assertTrue(ArgAspect2.arg24Called);
      assertTrue(ArgAspect2.emptyArgCalled);
   }
}