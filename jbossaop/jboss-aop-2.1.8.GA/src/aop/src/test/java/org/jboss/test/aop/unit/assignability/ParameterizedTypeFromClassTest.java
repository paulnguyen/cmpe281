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
import java.util.HashMap;
import java.util.List;

/**
 * Tests for assignability algorithm on parameterized type from class cases.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings("unchecked")
public class ParameterizedTypeFromClassTest extends ParameterizedTypeTest
{
   // Scenario 1
   
   void caller1(Class1 arg)
   {
      called1(arg);
   }
   
   public void test1() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 2
   
   void caller2(Class1 arg)
   {
      called2(arg);
   }
   
   public void test2() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 3
   
   void caller3(Class1 arg)
   {
      called3(arg);
   }
   
   public void test3() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 4
   
   void caller4(Class1 arg)
   {
      called4(arg);
   }
   
   public void test4() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 5
   
   void caller5(Class1 arg)
   {
      //called5(arg);
   }
   
   public void test5() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 6
   
   void caller6(Class1 arg)
   {
      //called6(arg);
   }
   
   public void test6() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 7
   
   void caller7(Class1 arg)
   {
      called7(arg);
   }
   
   public void test7() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 8
   
   void caller8(Class1 arg)
   {
      //called8(arg);
   }
   
   public void test8() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 9
   
   void caller9(Class1 arg)
   {
      called9(arg);
   }
   
   public void test9() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 10
   
   void caller10(Class1 arg)
   {
      called10(arg);
   }
   
   public void test10() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 11
   
   void caller11(Class1 arg)
   {
      called11(arg);
   }
   
   public void test11() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 12
   
   void caller12(Class1 arg)
   {
      called12(arg);
   }
   
   public void test12() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 13
   
   void caller13(Class1 arg)
   {
      //called13(arg);
   }
   
   public void test13() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 14
   
   void caller14(Class1 arg)
   {
      //called14(arg);
   }
   
   public void test14() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 15
   
   void caller15(Class1 arg)
   {
      called15(arg);
   }
   
   public void test15() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 16
   
   void caller16(Class1 arg)
   {
      //called16(arg);
   }
   
   public void test16() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 17
   
   void caller17(Class1 arg)
   {
      //called17(arg);
   }
   
   public void test17() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{Class1.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 18
   
   void caller18(Class2 arg)
   {
      called1(arg);
   }
   
   public void test18() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 19
   
   void caller19(Class2 arg)
   {
      called2(arg);
   }
   
   public void test19() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 20
   
   void caller20(Class2 arg)
   {
      called3(arg);
   }
   
   public void test20() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 21
   
   void caller21(Class2 arg)
   {
      called4(arg);
   }
   
   public void test21() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 22
   
   void caller22(Class2 arg)
   {
      called5(arg);
   }
   
   public void test22() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 23
   
   void caller23(Class2 arg)
   {
      called6(arg);
   }
   
   public void test23() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 24
   
   void caller24(Class2 arg)
   {
      called7(arg);
   }
   
   public void test24() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 25
   
   void caller25(Class2 arg)
   {
      called8(arg);
   }
   
   public void test25() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 26
   
   void caller26(Class2 arg)
   {
      called9(arg);
   }
   
   public void test26() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 27
   
   void caller27(Class2 arg)
   {
      called10(arg);
   }
   
   public void test27() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 28
   
   void caller28(Class2 arg)
   {
      called11(arg);
   }
   
   public void test28() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 29
   
   void caller29(Class2 arg)
   {
      called12(arg);
   }
   
   public void test29() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 30
   
   void caller30(Class2 arg)
   {
      called13(arg);
   }
   
   public void test30() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 31
   
   void caller31(Class2 arg)
   {
      called14(arg);
   }
   
   public void test31() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 32
   
   void caller32(Class2 arg)
   {
      called15(arg);
   }
   
   public void test32() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 33
   
   void caller33(Class2 arg)
   {
      called16(arg);
   }
   
   public void test33() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 34
   
   void caller34(Class2 arg)
   {
      //called17(arg);
   }
   
   public void test34() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{Class2.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 35
   
   void caller35(Class3 arg)
   {
      called1(arg);
   }
   
   public void test35() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 36
   
   void caller36(Class3 arg)
   {
      called2(arg);
   }
   
   public void test36() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 37
   
   void caller37(Class3 arg)
   {
      called3(arg);
   }
   
   public void test37() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 38
   
   void caller38(Class3 arg)
   {
      called4(arg);
   }
   
   public void test38() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 39
   
   void caller39(Class3 arg)
   {
      called5(arg);
   }
   
   public void test39() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 40
   
   void caller40(Class3 arg)
   {
      called6(arg);
   }
   
   public void test40() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 41
   
   void caller41(Class3 arg)
   {
      called7(arg);
   }
   
   public void test41() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 42
   
   void caller42(Class3 arg)
   {
      called8(arg);
   }
   
   public void test42() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 43
   
   void caller43(Class3 arg)
   {
      called9(arg);
   }
   
   public void test43() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 44
   
   void caller44(Class3 arg)
   {
      called10(arg);
   }
   
   public void test44() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 45
   
   void caller45(Class3 arg)
   {
      called11(arg);
   }
   
   public void test45() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 46
   
   void caller46(Class3 arg)
   {
      called12(arg);
   }
   
   public void test46() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 47
   
   void caller47(Class3 arg)
   {
      called13(arg);
   }
   
   public void test47() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller47", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 48
   
   void caller48(Class3 arg)
   {
      called14(arg);
   }
   
   public void test48() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller48", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 49
   
   void caller49(Class3 arg)
   {
      called15(arg);
   }
   
   public void test49() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller49", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 50
   
   void caller50(Class3 arg)
   {
      called16(arg);
   }
   
   public void test50() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller50", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 51
   
   void caller51(Class3 arg)
   {
      //called17(arg);
   }
   
   public void test51() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller51", new Class[]{Class3.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 52
   
   void caller52(Class4 arg)
   {
      called1(arg);
   }
   
   public void test52() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller52", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 53
   
   void caller53(Class4 arg)
   {
      called2(arg);
   }
   
   public void test53() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller53", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 54
   
   void caller54(Class4 arg)
   {
      called3(arg);
   }
   
   public void test54() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller54", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 55
   
   void caller55(Class4 arg)
   {
      called4(arg);
   }
   
   public void test55() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller55", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 56
   
   void caller56(Class4 arg)
   {
      called5(arg);
   }
   
   public void test56() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller56", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 57
   
   void caller57(Class4 arg)
   {
      called6(arg);
   }
   
   public void test57() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller57", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 58
   
   void caller58(Class4 arg)
   {
      called7(arg);
   }
   
   public void test58() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller58", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 59
   
   void caller59(Class4 arg)
   {
      called8(arg);
   }
   
   public void test59() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller59", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 60
   
   void caller60(Class4 arg)
   {
      called9(arg);
   }
   
   public void test60() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller60", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 61
   
   void caller61(Class4 arg)
   {
      called10(arg);
   }
   
   public void test61() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller61", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 62
   
   void caller62(Class4 arg)
   {
      called11(arg);
   }
   
   public void test62() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller62", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 63
   
   void caller63(Class4 arg)
   {
      called12(arg);
   }
   
   public void test63() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller63", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 64
   
   void caller64(Class4 arg)
   {
      called13(arg);
   }
   
   public void test64() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller64", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 65
   
   void caller65(Class4 arg)
   {
      called14(arg);
   }
   
   public void test65() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller65", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 66
   
   void caller66(Class4 arg)
   {
      called15(arg);
   }
   
   public void test66() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller66", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 67
   
   void caller67(Class4 arg)
   {
      called16(arg);
   }
   
   public void test67() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller67", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 68
   
   void caller68(Class4 arg)
   {
      //called17(arg);
   }
   
   public void test68() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller68", new Class[]{Class4.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 69
   
   void caller69(Class5 arg)
   {
      //called1(arg);
   }
   
   public void test69() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller69", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 70
   
   void caller70(Class5 arg)
   {
      //called2(arg);
   }
   
   public void test70() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller70", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 71
   
   void caller71(Class5 arg)
   {
      //called3(arg);
   }
   
   public void test71() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller71", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 72
   
   void caller72(Class5 arg)
   {
      //called4(arg);
   }
   
   public void test72() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller72", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 73
   
   void caller73(Class5 arg)
   {
      //called5(arg);
   }
   
   public void test73() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller73", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 74
   
   void caller74(Class5 arg)
   {
      //called6(arg);
   }
   
   public void test74() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller74", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 75
   
   void caller75(Class5 arg)
   {
      //called7(arg);
   }
   
   public void test75() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller75", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 76
   
   void caller76(Class5 arg)
   {
      //called8(arg);
   }
   
   public void test76() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller76", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 77
   
   void caller77(Class5 arg)
   {
      //called9(arg);
   }
   
   public void test77() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller77", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 78
   
   void caller78(Class5 arg)
   {
      //called10(arg);
   }
   
   public void test78() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller78", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 79
   
   void caller79(Class5 arg)
   {
      //called11(arg);
   }
   
   public void test79() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller79", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 80
   
   void caller80(Class5 arg)
   {
      //called12(arg);
   }
   
   public void test80() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller80", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 81
   
   void caller81(Class5 arg)
   {
      //called13(arg);
   }
   
   public void test81() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller81", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 82
   
   void caller82(Class5 arg)
   {
      //called14(arg);
   }
   
   public void test82() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller82", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 83
   
   void caller83(Class5 arg)
   {
      //called15(arg);
   }
   
   public void test83() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller83", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 84
   
   void caller84(Class5 arg)
   {
      //called16(arg);
   }
   
   public void test84() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller84", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 85
   
   void caller85(Class5 arg)
   {
      called17(arg);
   }
   
   public void test85() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller85", new Class[]{Class5.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 86
   
   void caller86(Class6 arg)
   {
      called1(arg);
   }
   
   public void test86() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller86", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 87
   
   void caller87(Class6 arg)
   {
      called2(arg);
   }
   
   public void test87() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller87", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 88
   
   void caller88(Class6 arg)
   {
      called3(arg);
   }
   
   public void test88() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller88", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 89
   
   void caller89(Class6 arg)
   {
      called4(arg);
   }
   
   public void test89() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller89", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 90
   
   void caller90(Class6 arg)
   {
      called5(arg);
   }
   
   public void test90() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller90", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 91
   
   void caller91(Class6 arg)
   {
      called6(arg);
   }
   
   public void test91() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller91", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 92
   
   void caller92(Class6 arg)
   {
      called7(arg);
   }
   
   public void test92() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller92", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 93
   
   void caller93(Class6 arg)
   {
      called8(arg);
   }
   
   public void test93() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller93", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 94
   
   void caller94(Class6 arg)
   {
      called9(arg);
   }
   
   public void test94() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller94", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 95
   
   void caller95(Class6 arg)
   {
      called10(arg);
   }
   
   public void test95() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller95", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 96
   
   void caller96(Class6 arg)
   {
      called11(arg);
   }
   
   public void test96() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller96", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 97
   
   void caller97(Class6 arg)
   {
      called12(arg);
   }
   
   public void test97() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller97", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 98
   
   void caller98(Class6 arg)
   {
      called13(arg);
   }
   
   public void test98() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller98", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 99
   
   void caller99(Class6 arg)
   {
      called14(arg);
   }
   
   public void test99() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller99", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 100
   
   void caller100(Class6 arg)
   {
      called15(arg);
   }
   
   public void test100() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller100", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 101
   
   void caller101(Class6 arg)
   {
      called16(arg);
   }
   
   public void test101() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller101", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 102
   
   void caller102(Class6 arg)
   {
      //called17(arg);
   }
   
   public void test102() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller102", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 103
   
   void caller103(Class6 arg)
   {
      called1(arg);
   }
   
   public void test103() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller103", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 104
   
   void caller104(Class6 arg)
   {
      called2(arg);
   }
   
   public void test104() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller104", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 105
   
   void caller105(Class6 arg)
   {
      called3(arg);
   }
   
   public void test105() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller105", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 106
   
   void caller106(Class6 arg)
   {
      called4(arg);
   }
   
   public void test106() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller106", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 107
   
   void caller107(Class6 arg)
   {
      called5(arg);
   }
   
   public void test107() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller107", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 108
   
   void caller108(Class6 arg)
   {
      called6(arg);
   }
   
   public void test108() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller108", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 109
   
   void caller109(Class6 arg)
   {
      called7(arg);
   }
   
   public void test109() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller109", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 110
   
   void caller110(Class6 arg)
   {
      called8(arg);
   }
   
   public void test110() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller110", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 111
   
   void caller111(Class6 arg)
   {
      called9(arg);
   }
   
   public void test111() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller111", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 112
   
   void caller112(Class6 arg)
   {
      called10(arg);
   }
   
   public void test112() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller112", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 113
   
   void caller113(Class6 arg)
   {
      called11(arg);
   }
   
   public void test113() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller113", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 114
   
   void caller114(Class6 arg)
   {
      called12(arg);
   }
   
   public void test114() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller114", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 115
   
   void caller115(Class6 arg)
   {
      called13(arg);
   }
   
   public void test115() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller115", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 116
   
   void caller116(Class6 arg)
   {
      called14(arg);
   }
   
   public void test116() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller116", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 117
   
   void caller117(Class6 arg)
   {
      called15(arg);
   }
   
   public void test117() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller117", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 118
   
   void caller118(Class6 arg)
   {
      called16(arg);
   }
   
   public void test118() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller118", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 119
   
   void caller119(Class6 arg)
   {
      //called17(arg);
   }
   
   public void test119() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller119", new Class[]{Class6.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 120
   
   void caller120(Class7 arg)
   {
      called1(arg);
   }
   
   public void test120() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller120", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 121
   
   void caller121(Class7 arg)
   {
      called2(arg);
   }
   
   public void test121() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller121", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 122
   
   void caller122(Class7 arg)
   {
      called3(arg);
   }
   
   public void test122() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller122", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 123
   
   void caller123(Class7 arg)
   {
      called4(arg);
   }
   
   public void test123() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller123", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 124
   
   void caller124(Class7 arg)
   {
      called5(arg);
   }
   
   public void test124() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller124", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 125
   
   void caller125(Class7 arg)
   {
      called6(arg);
   }
   
   public void test125() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller125", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 126
   
   void caller126(Class7 arg)
   {
      called7(arg);
   }
   
   public void test126() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller126", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 127
   
   void caller127(Class7 arg)
   {
      called8(arg);
   }
   
   public void test127() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller127", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 128
   
   void caller128(Class7 arg)
   {
      called9(arg);
   }
   
   public void test128() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller128", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 129
   
   void caller129(Class7 arg)
   {
      called10(arg);
   }
   
   public void test129() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller129", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 130
   
   void caller130(Class7 arg)
   {
      called11(arg);
   }
   
   public void test130() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller130", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 131
   
   void caller131(Class7 arg)
   {
      called12(arg);
   }
   
   public void test131() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller131", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 132
   
   void caller132(Class7 arg)
   {
      called13(arg);
   }
   
   public void test132() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller132", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 133
   
   void caller133(Class7 arg)
   {
      called14(arg);
   }
   
   public void test133() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller133", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 134
   
   void caller134(Class7 arg)
   {
      called15(arg);
   }
   
   public void test134() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller134", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 135
   
   void caller135(Class7 arg)
   {
      called16(arg);
   }
   
   public void test135() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller135", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 136
   
   void caller136(Class7 arg)
   {
      //called17(arg);
   }
   
   public void test136() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller136", new Class[]{Class7.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 137
   
   void caller137(Class8 arg)
   {
      //called1(arg);
   }
   
   public void test137() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller137", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 138
   
   void caller138(Class8 arg)
   {
      //called2(arg);
   }
   
   public void test138() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller138", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 139
   
   void caller139(Class8 arg)
   {
      //called3(arg);
   }
   
   public void test139() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller139", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 140
   
   void caller140(Class8 arg)
   {
      //called4(arg);
   }
   
   public void test140() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller140", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 141
   
   void caller141(Class8 arg)
   {
      //called5(arg);
   }
   
   public void test141() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller141", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 142
   
   void caller142(Class8 arg)
   {
      //called6(arg);
   }
   
   public void test142() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller142", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 143
   
   void caller143(Class8 arg)
   {
      //called7(arg);
   }
   
   public void test143() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller143", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 144
   
   void caller144(Class8 arg)
   {
      //called8(arg);
   }
   
   public void test144() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller144", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 145
   
   void caller145(Class8 arg)
   {
      //called9(arg);
   }
   
   public void test145() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller145", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 146
   
   void caller146(Class8 arg)
   {
      //called10(arg);
   }
   
   public void test146() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller146", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 147
   
   void caller147(Class8 arg)
   {
      //called11(arg);
   }
   
   public void test147() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller147", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 148
   
   void caller148(Class8 arg)
   {
      //called12(arg);
   }
   
   public void test148() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller148", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 149
   
   void caller149(Class8 arg)
   {
      //called13(arg);
   }
   
   public void test149() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller149", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 150
   
   void caller150(Class8 arg)
   {
      //called14(arg);
   }
   
   public void test150() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller150", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 151
   
   void caller151(Class8 arg)
   {
      //called15(arg);
   }
   
   public void test151() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller151", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 152
   
   void caller152(Class8 arg)
   {
      //called16(arg);
   }
   
   public void test152() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller152", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 153
   
   void caller153(Class8 arg)
   {
      called17(arg);
   }
   
   public void test153() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller153", new Class[]{Class8.class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 154
   
   void caller154(MyClass1 arg)
   {
      called154(arg);
   }
   
   void called154(List<? extends Runnable> arg) {}
   
   public void test154() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller154", new Class[]{MyClass1.class});
      Method called = this.getClass().getDeclaredMethod("called154", new Class[]{List.class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
}

@SuppressWarnings("serial")class Class1 extends ArrayList<String>{}
@SuppressWarnings("serial")class Class2<D> extends ArrayList<D>{}
@SuppressWarnings("serial")class Class3<D> extends ArrayList<String>{}
@SuppressWarnings({"serial", "unchecked"})class Class4 extends ArrayList{}
@SuppressWarnings("serial")class Class5<A,B> extends HashMap<B,A>{}
@SuppressWarnings("serial")class Class6<D extends String> extends ArrayList<D>{}
@SuppressWarnings("serial")class Class7<A,B> extends ArrayList<String>{}
@SuppressWarnings("serial")class Class8<A,B> extends HashMap<String,Integer>{}