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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 *
 */
@SuppressWarnings("unchecked")
public class ParameterizedTypeFromVariableTest extends ParameterizedTypeTest
{
   // Scenario 1
   
   <A extends ArrayList> void caller1(A arg)
   {
      called1(arg);
   }
   
   public void test1() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 2
   
   <A extends ArrayList> void caller2(A arg)
   {
      called2(arg);
   }
   
   public void test2() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 3
   
   <A extends ArrayList> void caller3(A arg)
   {
      called3(arg);
   }
   
   public void test3() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 4
   
   <A extends ArrayList> void caller4(A arg)
   {
      called4(arg);
   }
   
   public void test4() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 5
   
   <A extends ArrayList> void caller5(A arg)
   {
      called5(arg);
   }
   
   public void test5() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 6
   
   <A extends ArrayList> void caller6(A arg)
   {
      called6(arg);
   }
   
   public void test6() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 7
   
   <A extends ArrayList> void caller7(A arg)
   {
      called7(arg);
   }
   
   public void test7() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 8
   
   <A extends ArrayList> void caller8(A arg)
   {
      called8(arg);
   }
   
   public void test8() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 9
   
   <A extends ArrayList> void caller9(A arg)
   {
      called9(arg);
   }
   
   public void test9() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 10
   
   <A extends ArrayList> void caller10(A arg)
   {
      called10(arg);
   }
   
   public void test10() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 11
   
   <A extends ArrayList> void caller11(A arg)
   {
      called11(arg);
   }
   
   public void test11() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 12
   
   <A extends ArrayList> void caller12(A arg)
   {
      called12(arg);
   }
   
   public void test12() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 13
   
   <A extends ArrayList> void caller13(A arg)
   {
      called13(arg);
   }
   
   public void test13() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 14
   
   <A extends ArrayList> void caller14(A arg)
   {
      called14(arg);
   }
   
   public void test14() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 15
   
   <A extends ArrayList> void caller15(A arg)
   {
      called15(arg);
   }
   
   public void test15() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 16
   
   <A extends ArrayList> void caller16(A arg)
   {
      called16(arg);
   }
   
   public void test16() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 17
   
   <A extends ArrayList> void caller17(A arg)
   {
      //called17(arg);
   }
   
   public void test17() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 18
   
   <B extends ArrayList<Integer>>void caller18(B arg)
   {
      called1(arg);
   }
   
   public void test18() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 19
   
   <B extends ArrayList<Integer>>void caller19(B arg)
   {
      called2(arg);
   }
   
   public void test19() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 20
   
   <B extends ArrayList<Integer>>void caller20(B arg)
   {
      called3(arg);
   }
   
   public void test20() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 21
   
   <B extends ArrayList<Integer>>void caller21(B arg)
   {
      //called4(arg);
   }
   
   public void test21() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 22
   
   <B extends ArrayList<Integer>>void caller22(B arg)
   {
      //called5(arg);
   }
   
   public void test22() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 23
   
   <B extends ArrayList<Integer>>void caller23(B arg)
   {
      called6(arg);
   }
   
   public void test23() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 24
   
   <B extends ArrayList<Integer>>void caller24(B arg)
   {
      //called7(arg);
   }
   
   public void test24() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 25
   
   <B extends ArrayList<Integer>>void caller25(B arg)
   {
      called8(arg);
   }
   
   public void test25() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 26
   
   <B extends ArrayList<Integer>>void caller26(B arg)
   {
      called9(arg);
   }
   
   public void test26() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 27
   
   <B extends ArrayList<Integer>>void caller27(B arg)
   {
      called10(arg);
   }
   
   public void test27() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 28
   
   <B extends ArrayList<Integer>>void caller28(B arg)
   {
      called11(arg);
   }
   
   public void test28() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 29
   
   <B extends ArrayList<Integer>>void caller29(B arg)
   {
      //called12(arg);
   }
   
   public void test29() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 30
   
   <B extends ArrayList<Integer>>void caller30(B arg)
   {
      //called13(arg);
   }
   
   public void test30() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 31
   
   <B extends ArrayList<Integer>>void caller31(B arg)
   {
      called14(arg);
   }
   
   public void test31() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 32
   
   <B extends ArrayList<Integer>>void caller32(B arg)
   {
      //called15(arg);
   }
   
   public void test32() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 33
   
   <B extends ArrayList<Integer>>void caller33(B arg)
   {
      called16(arg);
   }
   
   public void test33() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 34
   
   <B extends ArrayList<Integer>>void caller34(B arg)
   {
      //called17(arg);
   }
   
   public void test34() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 35
   
   <C extends List<?>,Runnable> void caller35(C arg)
   {
      //called1(arg);
   }
   
   public void test35() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 36
   
   <C extends List<?>,Runnable> void caller36(C arg)
   {
      //called2(arg);
   }
   
   public void test36() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 37
   
   <C extends List<?>,Runnable> void caller37(C arg)
   {
      //called3(arg);
   }
   
   public void test37() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 38
   
   <C extends List<?>,Runnable> void caller38(C arg)
   {
      //called4(arg);
   }
   
   public void test38() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 39
   
   <C extends List<?>,Runnable> void caller39(C arg)
   {
      //called5(arg);
   }
   
   public void test39() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 40
   
   <C extends List<?>,Runnable> void caller40(C arg)
   {
      //called6(arg);
   }
   
   public void test40() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 41
   
   <C extends List<?>,Runnable> void caller41(C arg)
   {
      //called7(arg);
   }
   
   public void test41() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 42
   
   <C extends List<?>,Runnable> void caller42(C arg)
   {
      //called8(arg);
   }
   
   public void test42() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 43
   
   <C extends List<?>,Runnable> void caller43(C arg)
   {
      //called9(arg);
   }
   
   public void test43() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 44
   
   <C extends List<?>,Runnable> void caller44(C arg)
   {
      called10(arg);
   }
   
   public void test44() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 45
   
   <C extends List<?>,Runnable> void caller45(C arg)
   {
      called11(arg);
   }
   
   public void test45() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 46
   
   <C extends List<?>,Runnable> void caller46(C arg)
   {
      //called12(arg);
   }
   
   public void test46() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 47
   
   <C extends List<?>,Runnable> void caller47(C arg)
   {
      //called13(arg);
   }
   
   public void test47() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller47", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 48
   
   <C extends List<?>,Runnable> void caller48(C arg)
   {
      //called14(arg);
   }
   
   public void test48() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller48", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 49
   
   <C extends List<?>,Runnable> void caller49(C arg)
   {
      //called15(arg);
   }
   
   public void test49() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller49", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 50
   
   <C extends List<?>,Runnable> void caller50(C arg)
   {
      //called16(arg);
   }
   
   public void test50() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller50", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 51
   
   <C extends List<?>,Runnable> void caller51(C arg)
   {
      //called17(arg);
   }
   
   public void test51() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller51", new Class[]{List.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 52
   
   <D, C extends ArrayList<D>,Runnable> void caller52(C arg)
   {
      called1(arg);
   }
   
   public void test52() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller52", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 53
   
   <D, C extends ArrayList<D>,Runnable> void caller53(C arg)
   {
      called2(arg);
   }
   
   public void test53() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller53", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 54
   
   <D, C extends ArrayList<D>,Runnable> void caller54(C arg)
   {
      called3(arg);
   }
   
   public void test54() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller54", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 55
   
   <D, C extends ArrayList<D>,Runnable> void caller55(C arg)
   {
      //called4(arg);
   }
   
   public void test55() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller55", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 56
   
   <D, C extends ArrayList<D>,Runnable> void caller56(C arg)
   {
      //called5(arg);
   }
   
   public void test56() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller56", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 57
   
   <D, C extends ArrayList<D>,Runnable> void caller57(C arg)
   {
      //called6(arg);
   }
   
   public void test57() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller57", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 58
   
   <D, C extends ArrayList<D>,Runnable> void caller58(C arg)
   {
      //called7(arg);
   }
   
   public void test58() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller58", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 59
   
   <D, C extends ArrayList<D>,Runnable> void caller59(C arg)
   {
      //called8(arg);
   }
   
   public void test59() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller59", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 60
   
   <D, C extends ArrayList<D>,Runnable> void caller60(C arg)
   {
      called9(arg);
   }
   
   public void test60() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller60", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 61
   
   <D, C extends ArrayList<D>,Runnable> void caller61(C arg)
   {
      called10(arg);
   }
   
   public void test61() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller61", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 62
   
   <D, C extends ArrayList<D>,Runnable> void caller62(C arg)
   {
      called11(arg);
   }
   
   public void test62() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller62", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 63
   
   <D, C extends ArrayList<D>,Runnable> void caller63(C arg)
   {
      //called12(arg);
   }
   
   public void test63() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller63", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 64
   
   <D, C extends ArrayList<D>,Runnable> void caller64(C arg)
   {
      //called13(arg);
   }
   
   public void test64() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller64", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 65
   
   <D, C extends ArrayList<D>,Runnable> void caller65(C arg)
   {
      //called14(arg);
   }
   
   public void test65() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller65", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 66
   
   <D, C extends ArrayList<D>,Runnable> void caller66(C arg)
   {
      //called15(arg);
   }
   
   public void test66() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller66", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 67
   
   <D, C extends ArrayList<D>,Runnable> void caller67(C arg)
   {
      //called16(arg);
   }
   
   public void test67() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller67", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 68
   
   <D, C extends ArrayList<D>,Runnable> void caller68(C arg)
   {
      //called17(arg);
   }
   
   public void test68() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller68", new Class[]{ArrayList.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 69
   
   <D, C extends ArrayList<D>, Runnable> void caller69(C arg1, D arg2)
   {
      called69(arg1, arg2);
   }
   
   protected <A> void called69(List<A> arg1, A arg2) {}
   
   public void test69() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller69", new Class[]{ArrayList.class, Object.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List.class, Object.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            caller.getGenericParameterTypes()[1], hierarchy));
   }
   
   // Scenario 70
   
   <D, C extends ArrayList<D>, Runnable> void caller70(C arg1)
   {
      //called70(arg1, "");
   }
   
   protected <A> void called70(List<A> arg1, A arg2) {}
   
   public void test70() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller70", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called70", new Class[]{List.class, Object.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            String.class, hierarchy));
   }
   
   // Scenario 71
   
   <D, C extends ArrayList<D>, Runnable> void caller71(C arg1)
   {
      called71(arg1, "");
   }
   
   protected <A,C> void called71(List<A> arg1, C arg2) {}
   
   public void test71() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller71", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called71", new Class[]{List.class, Object.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            String.class, hierarchy));
   }
   
   // Scenario 72
   
   <D extends Collection<D>> void caller72(D arg)
   {
      //called72(arg);
   }
   
   void called72(Collection<String> arg) {}
   
   public void test72() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller72", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called72", new Class[]{Collection.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 73
   
   <D extends Collection<D>> void caller73(D arg)
   {
      called73(arg);
   }
   
   void called73(Collection<?> arg) {}
   
   public void test73() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller73", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called73", new Class[]{Collection.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 74
   
   <D extends Collection<D>> void caller74(D arg)
   {
      //called74(arg);
   }
   
   <A extends Runnable>void called74(Collection<A> arg) {}
   
   public void test74() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller74", new Class[]{Collection.class});
      Method called = this.getClass().getDeclaredMethod("called74", new Class[]{Collection.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 75
   
   <D extends List<D>> void caller75(D arg)
   {
      called75(arg);
   }
   
   <A extends Collection>void called75(Collection<A> arg) {}
   
   public void test75() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller75", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called75", new Class[]{Collection.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 76
   
   public <A extends Integer & Runnable> void caller76(ArrayList<A> arg1, ArrayList<Runnable> arg2)
   {
      called76(arg1, arg2);
   }

   public <B, D extends B> void called76(Collection<D> arg, Collection<B> arg2){}

   public void test76() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller76", new Class[]{ArrayList.class, ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called76", new Class[]{Collection.class, Collection.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            caller.getGenericParameterTypes()[1], hierarchy));
   }

   // Scenario 77
   
   void caller77(Collection<Integer> ci, Collection<Collection<Integer>> cci)
   {
      called77(ci, cci);
   }

   <B> void called77(B arg, Collection<B> arg2){}
   
   public void test77() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller77", new Class[]{Collection.class, Collection.class});
      Method called = this.getClass().getDeclaredMethod("called77", new Class[]{Object.class, Collection.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            caller.getGenericParameterTypes()[1], hierarchy));
   }
   
   // Scenario 78
   
   public void caller78(Collection<Integer> ci, Collection<Collection<String>> ccs)
   {
      //called77(ci, ccs);
   }
   
   public void test78() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller78", new Class[]{Collection.class, Collection.class});
      Method called = this.getClass().getDeclaredMethod("called77", new Class[]{Object.class, Collection.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1],
            caller.getGenericParameterTypes()[1], hierarchy));
   }
}