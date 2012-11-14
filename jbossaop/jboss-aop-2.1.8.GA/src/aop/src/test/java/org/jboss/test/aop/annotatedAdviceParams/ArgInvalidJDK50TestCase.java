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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests that have been temporarily removed from {@link ArgInvalidTestCase} (task
 * JBAOP-458)
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ArgInvalidJDK50TestCase extends AOPTestWithSetup
{
   private ArgsInvalidPOJO pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ArgTestCase");
      suite.addTestSuite(ArgInvalidJDK50TestCase.class);
      return suite;
   }

   public ArgInvalidJDK50TestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      this.pojo = new ArgsInvalidPOJO();
      ArgAspect.clear();
      ArgAspectInterfaces.clear();
      ArgAspectInvertedArgs.clear();
      ArgAspectGenerics.clear();
   }

   public void testGenericsExecution()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      boolean thrown = false;
      try
      {
         pojo.methodGenericsExecutionBefore7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionBefore8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAround5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAround7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAround8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAfter5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAfter7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionAfter8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionThrowing2(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionThrowing7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionThrowing8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionFinally5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionFinally7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsExecutionFinally8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testGenericsFieldWrite()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      boolean thrown = false;
      try
      {
         pojo.field5Before7 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Before8 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Around5 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Around7 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Around8 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5After5 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5After7 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5After8 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Throwing2 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Throwing7 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Throwing8 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Finally5 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Finally7 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.field5Finally8 = list;
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testGenericsConstructorExecution()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      boolean thrown = false;
      try
      {
         // test before7
         new ArgsInvalidPOJO(list, false);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test before8
         new ArgsInvalidPOJO(list, '\0');
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around5_
         new ArgsInvalidPOJO(list, (byte)0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around7_
         new ArgsInvalidPOJO(list, (short)0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around8_
         new ArgsInvalidPOJO(list, 0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after5
         new ArgsInvalidPOJO(list, 0l);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after7
         new ArgsInvalidPOJO(list, (float) 0.0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after8
         new ArgsInvalidPOJO(list, 0.0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing2
         new ArgsInvalidPOJO(list, false, false);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing7
         new ArgsInvalidPOJO(list, false, '\0');
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing8
         new ArgsInvalidPOJO(list, false, (byte) 0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally5
         new ArgsInvalidPOJO(list, false, (short) 0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally7
         new ArgsInvalidPOJO(list, false, 0);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally8
         new ArgsInvalidPOJO(list, false, 0l);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testGenericsConstruction()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      boolean thrown = false;
      try
      {
         // test before7
         new ArgsInvalidPOJO(false, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test before8
         new ArgsInvalidPOJO('\0', list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around5_
         new ArgsInvalidPOJO((byte)0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around7_
         new ArgsInvalidPOJO((short)0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test around8_
         new ArgsInvalidPOJO(0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after5
         new ArgsInvalidPOJO(0l, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after7
         new ArgsInvalidPOJO((float) 0.0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test after8
         new ArgsInvalidPOJO(0.0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing2
         new ArgsInvalidPOJO(false, false, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing7
         new ArgsInvalidPOJO(false, '\0', list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test throwing8
         new ArgsInvalidPOJO(false, (byte) 0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally5
         new ArgsInvalidPOJO(false, (short) 0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally7
         new ArgsInvalidPOJO(false, 0, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         // test finally8
         new ArgsInvalidPOJO(false, 0l, list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testGenericsCall()
   {
      List<SuperValue> list = new ArrayList<SuperValue>();
      boolean thrown = false;
      try
      {
         pojo.methodGenericsCallBefore7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallBefore8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAround5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAround7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAround8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAfter5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAfter7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallAfter8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallThrowing2(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallThrowing7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallThrowing8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallFinally5(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallFinally7(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.methodGenericsCallFinally8(list);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
}