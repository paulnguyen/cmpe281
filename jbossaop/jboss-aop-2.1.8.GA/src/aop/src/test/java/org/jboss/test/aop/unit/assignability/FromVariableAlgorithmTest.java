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
package org.jboss.test.aop.unit.assignability;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.jboss.aop.advice.annotation.assignability.AssignabilityAlgorithm;
import org.jboss.aop.advice.annotation.assignability.VariableHierarchy;

/**
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings("unchecked")
public class FromVariableAlgorithmTest extends TestCase
{
   AssignabilityAlgorithm algorithm;
   VariableHierarchy hierarchy;
   private static Class[] NO_ARGS = new Class[0]; 
   
   public void setUp()
   {
      this.algorithm = AssignabilityAlgorithm.FROM_VARIABLE;
      hierarchy = new VariableHierarchy();
   }
   
   private boolean runAlgorithm(int callerNumber, int calledNumber) throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller" + callerNumber, NO_ARGS);
      Method called = this.getClass().getDeclaredMethod("called" + calledNumber, NO_ARGS);
      return algorithm.isAssignable(caller.getGenericReturnType(),
         called.getGenericReturnType(), hierarchy);
   }
   
   // Scenario 1
   
   Collection<String> caller1()
   {
      return called1();
   }
   
   <A> Collection<A> called1(){ return null;}
   
   public void test1() throws Exception
   {
      assertTrue(runAlgorithm(1, 1));
   }
   
   // Scenario 2
   
   Collection<String> caller2()
   {
      return called2();
   }
   
   <A extends String> Collection<A> called2(){ return null;}
   
   public void test2() throws Exception
   {
      assertTrue(runAlgorithm(2, 2));
   }
   
   // Scenario 3
   
   Collection<Runnable> caller3()
   {
      //return called2();
      return null;
   }
   
   public void test3() throws Exception
   {
      assertFalse(runAlgorithm(3, 2));
   }
   
   // Scenario 4
   
   Collection<?> caller4()
   {
   	// TODO fix algorithm here
      //return called4();
      return null;
   }
   
   <A extends String> Collection<A> called4(){ return null;}
   
   public void test4() throws Exception
   {
      assertTrue(runAlgorithm(4, 4));
   }
   
   // Scenario 5
   
   Collection<? extends Runnable> caller5()
   {
      //return called4();
      return null;
   }
   
   public void test5() throws Exception
   {
      assertFalse(runAlgorithm(5, 4));
   }
   
   // Scenario 6
   
   Collection<?> caller6()
   {
      return called6();
   }
   
   <A> Collection<A> called6(){ return null;}
   
   public void test6() throws Exception
   {
      assertTrue(runAlgorithm(6, 6));
   }
   
   // Scenario 7
   
   Collection<? extends Runnable> caller7()
   {
      return called6();
   }
   
   public void test7() throws Exception
   {
      assertTrue(runAlgorithm(7, 6));
   }   
   
   // Scenario 8

   Collection<?> caller8(Collection<Runnable> arg)
   {
      return called8(arg);
   }

   <A> Collection<A> called8(Collection<A> arg)
   {
      return null;
   }
   
   public void test8() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
      assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(caller.getGenericReturnType(),
            called.getGenericReturnType(), hierarchy));
   }
   
   // Scenario 9

   Collection<? extends Runnable> caller9(Collection<Runnable> arg)
   {
      return called8(arg);
   }
   
   public void test9() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
      assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(caller.getGenericReturnType(),
            called.getGenericReturnType(), hierarchy));
   }
   
   // Scenario 10

   Collection<Runnable> caller10(Collection<Runnable> arg)
   {
      return called8(arg);
   }
   
   public void test10() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
      assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(caller.getGenericReturnType(),
            called.getGenericReturnType(), hierarchy));
   }
   
   // Scenario 11

   Collection<? extends List> caller11(Collection<Runnable> arg)
   {
      //return called8(arg);
      return null;
   }

   public void test11() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
      assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertFalse(algorithm.isAssignable(caller.getGenericReturnType(),
            called.getGenericReturnType(), hierarchy));
   }
}