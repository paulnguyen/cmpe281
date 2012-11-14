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
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings("unchecked")
public class ParameterizedTypeFromArrayTest extends ParameterizedTypeTest
{
   // Scenario 1
   
   <A>void caller1(A[] arg)
   {
      //called1(arg);
   }
   
   public void test1() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 2
   
   <A>void caller2(A[] arg)
   {
      //called2(arg);
   }
   
   public void test2() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 3
   
   <A>void caller3(A[] arg)
   {
      //called3(arg);
   }
   
   public void test3() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 4
   
   <A>void caller4(A[] arg)
   {
      //called4(arg);
   }
   
   public void test4() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 5
   
   <A>void caller5(A[] arg)
   {
      //called5(arg);
   }
   
   public void test5() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 6
   
   <A>void caller6(A[] arg)
   {
      //called6(arg);
   }
   
   public void test6() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 7
   
   <A>void caller7(A[] arg)
   {
      //called7(arg);
   }
   
   public void test7() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 8
   
   <A>void caller8(A[] arg)
   {
      //called8(arg);
   }
   
   public void test8() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 9
   
   <A>void caller9(A[] arg)
   {
      //called9(arg);
   }
   
   public void test9() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 10
   
   <A>void caller10(A[] arg)
   {
      //called10(arg);
   }
   
   public void test10() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 11
   
   <A>void caller11(A[] arg)
   {
      //called11(arg);
   }
   
   public void test11() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 12
   
   <A>void caller12(A[] arg)
   {
      //called12(arg);
   }
   
   public void test12() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 13
   
   <A>void caller13(A[] arg)
   {
      //called13(arg);
   }
   
   public void test13() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 14
   
   <A>void caller14(A[] arg)
   {
      //called14(arg);
   }
   
   public void test14() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 15
   
   <A>void caller15(A[] arg)
   {
      //called15(arg);
   }
   
   public void test15() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 16
   
   <A>void caller16(A[] arg)
   {
      //called16(arg);
   }
   
   public void test16() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 17
   
   <A>void caller17(A[] arg)
   {
      //called17(arg);
   }
   
   public void test17() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 18
   
   <A extends ArrayList>void caller18(A[] arg)
   {
      //called1(arg);
   }
   
   public void test18() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 19
   
   <A extends ArrayList>void caller19(A[] arg)
   {
      //called2(arg);
   }
   
   public void test19() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 20
   
   <A extends ArrayList>void caller20(A[] arg)
   {
      //called3(arg);
   }
   
   public void test20() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 21
   
   <A extends ArrayList>void caller21(A[] arg)
   {
      //called4(arg);
   }
   
   public void test21() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 22
   
   <A extends ArrayList>void caller22(A[] arg)
   {
      //called5(arg);
   }
   
   public void test22() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 23
   
   <A extends ArrayList>void caller23(A[] arg)
   {
      //called6(arg);
   }
   
   public void test23() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 24
   
   <A extends ArrayList>void caller24(A[] arg)
   {
      //called7(arg);
   }
   
   public void test24() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 25
   
   <A extends ArrayList>void caller25(A[] arg)
   {
      //called8(arg);
   }
   
   public void test25() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 26
   
   <A extends ArrayList>void caller26(A[] arg)
   {
      //called9(arg);
   }
   
   public void test26() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 27
   
   <A extends ArrayList>void caller27(A[] arg)
   {
      //called10(arg);
   }
   
   public void test27() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 28
   
   <A extends ArrayList>void caller28(A[] arg)
   {
      //called11(arg);
   }
   
   public void test28() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 29
   
   <A extends ArrayList>void caller29(A[] arg)
   {
      //called12(arg);
   }
   
   public void test29() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 30
   
   <A extends ArrayList>void caller30(A[] arg)
   {
      //called13(arg);
   }
   
   public void test30() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 31
   
   <A extends ArrayList>void caller31(A[] arg)
   {
      //called14(arg);
   }
   
   public void test31() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 32
   
   <A extends ArrayList>void caller32(A[] arg)
   {
      //called15(arg);
   }
   
   public void test32() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 33
   
   <A extends ArrayList>void caller33(A[] arg)
   {
      //called16(arg);
   }
   
   public void test33() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 34
   
   <A extends ArrayList>void caller34(A[] arg)
   {
      //called17(arg);
   }
   
   public void test34() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 35
   
   void caller35(Object[] arg)
   {
      //called1(arg);
   }
   
   public void test35() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 36
   
   void caller36(Object[] arg)
   {
      //called2(arg);
   }
   
   public void test36() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 37
   
   void caller37(Object[] arg)
   {
      //called3(arg);
   }
   
   public void test37() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 38
   
   void caller38(Object[] arg)
   {
      //called4(arg);
   }
   
   public void test38() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 39
   
   void caller39(Object[] arg)
   {
      //called5(arg);
   }
   
   public void test39() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 40
   
   void caller40(Object[] arg)
   {
      //called6(arg);
   }
   
   public void test40() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 41
   
   void caller41(Object[] arg)
   {
      //called7(arg);
   }
   
   public void test41() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 42
   
   void caller42(Object[] arg)
   {
      //called8(arg);
   }
   
   public void test42() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 43
   
   void caller43(Object[] arg)
   {
      //called9(arg);
   }
   
   public void test43() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 44
   
   void caller44(Object[] arg)
   {
      //called10(arg);
   }
   
   public void test44() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 45
   
   void caller45(Object[] arg)
   {
      //called11(arg);
   }
   
   public void test45() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 46
   
   void caller46(Object[] arg)
   {
      //called12(arg);
   }
   
   public void test46() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 47
   
   void caller47(Object[] arg)
   {
      //called13(arg);
   }
   
   public void test47() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller47", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 48
   
   void caller48(Object[] arg)
   {
      //called14(arg);
   }
   
   public void test48() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller48", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 49
   
   void caller49(Object[] arg)
   {
      //called15(arg);
   }
   
   public void test49() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller49", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 50
   
   void caller50(Object[] arg)
   {
      //called16(arg);
   }
   
   public void test50() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller50", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 51
   
   void caller51(Object[] arg)
   {
      //called17(arg);
   }
   
   public void test51() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller51", new Class[]{Object[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 52
   
   void caller52(List[] arg)
   {
      //called1(arg);
   }
   
   public void test52() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller52", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 53
   
   void caller53(List[] arg)
   {
      //called2(arg);
   }
   
   public void test53() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller53", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 54
   
   void caller54(List[] arg)
   {
      //called3(arg);
   }
   
   public void test54() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller54", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 55
   
   void caller55(List[] arg)
   {
      //called4(arg);
   }
   
   public void test55() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller55", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 56
   
   void caller56(List[] arg)
   {
      //called5(arg);
   }
   
   public void test56() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller56", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 57
   
   void caller57(List[] arg)
   {
      //called6(arg);
   }
   
   public void test57() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller57", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 58
   
   void caller58(List[] arg)
   {
      //called7(arg);
   }
   
   public void test58() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller58", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 59
   
   void caller59(List[] arg)
   {
      //called8(arg);
   }
   
   public void test59() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller59", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 60
   
   void caller60(List[] arg)
   {
      //called9(arg);
   }
   
   public void test60() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller60", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 61
   
   void caller61(List[] arg)
   {
      //called10(arg);
   }
   
   public void test61() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller61", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 62
   
   void caller62(List[] arg)
   {
      //called11(arg);
   }
   
   public void test62() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller62", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 63
   
   void caller63(List[] arg)
   {
      //called12(arg);
   }
   
   public void test63() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller63", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 64
   
   void caller64(List[] arg)
   {
      //called13(arg);
   }
   
   public void test64() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller64", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 65
   
   void caller65(List[] arg)
   {
      //called14(arg);
   }
   
   public void test65() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller65", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 66
   
   void caller66(List[] arg)
   {
      //called15(arg);
   }
   
   public void test66() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller66", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 67
   
   void caller67(List[] arg)
   {
      //called16(arg);
   }
   
   public void test67() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller67", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 68
   
   void caller68(List[] arg)
   {
      //called17(arg);
   }
   
   public void test68() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller68", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 69
   
   void caller69(ArrayList<?>[] arg)
   {
      //called1(arg);
   }
   
   public void test69() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller69", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 70
   
   void caller70(ArrayList<?>[] arg)
   {
      //called2(arg);
   }
   
   public void test70() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller70", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 71
   
   void caller71(ArrayList<?>[] arg)
   {
      //called3(arg);
   }
   
   public void test71() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller71", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 72
   
   void caller72(ArrayList<?>[] arg)
   {
      //called4(arg);
   }
   
   public void test72() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller72", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 73
   
   void caller73(ArrayList<?>[] arg)
   {
      //called5(arg);
   }
   
   public void test73() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller73", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 74
   
   void caller74(ArrayList<?>[] arg)
   {
      //called6(arg);
   }
   
   public void test74() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller74", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 75
   
   void caller75(ArrayList<?>[] arg)
   {
      //called7(arg);
   }
   
   public void test75() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller75", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 76
   
   void caller76(ArrayList<?>[] arg)
   {
      //called8(arg);
   }
   
   public void test76() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller76", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 77
   
   void caller77(ArrayList<?>[] arg)
   {
      //called9(arg);
   }
   
   public void test77() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller77", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 78
   
   void caller78(ArrayList<?>[] arg)
   {
      //called10(arg);
   }
   
   public void test78() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller78", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 79
   
   void caller79(ArrayList<?>[] arg)
   {
      //called11(arg);
   }
   
   public void test79() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller79", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 80
   
   void caller80(ArrayList<?>[] arg)
   {
      //called12(arg);
   }
   
   public void test80() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller80", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 81
   
   void caller81(ArrayList<?>[] arg)
   {
      //called13(arg);
   }
   
   public void test81() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller81", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 82
   
   void caller82(ArrayList<?>[] arg)
   {
      //called14(arg);
   }
   
   public void test82() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller82", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 83
   
   void caller83(ArrayList<?>[] arg)
   {
      //called15(arg);
   }
   
   public void test83() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller83", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 84
   
   void caller84(ArrayList<?>[] arg)
   {
      //called16(arg);
   }
   
   public void test84() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller84", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 85
   
   void caller85(ArrayList<?>[] arg)
   {
      //called17(arg);
   }
   
   public void test85() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller85", new Class[]{ArrayList[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 86
   
   void caller86(List<String>[] arg)
   {
      //called1(arg);
   }
   
   public void test86() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller86", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 87
   
   void caller87(List<String>[] arg)
   {
      //called2(arg);
   }
   
   public void test87() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller87", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 88
   
   void caller88(List<String>[] arg)
   {
      //called3(arg);
   }
   
   public void test88() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller88", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 89
   
   void caller89(List<String>[] arg)
   {
      //called4(arg);
   }
   
   public void test89() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller89", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 90
   
   void caller90(List<String>[] arg)
   {
      //called5(arg);
   }
   
   public void test90() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller90", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 91
   
   void caller91(List<String>[] arg)
   {
      //called6(arg);
   }
   
   public void test91() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller91", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 92
   
   void caller92(List<String>[] arg)
   {
      //called7(arg);
   }
   
   public void test92() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller92", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 93
   
   void caller93(List<String>[] arg)
   {
      //called8(arg);
   }
   
   public void test93() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller93", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 94
   
   void caller94(List<String>[] arg)
   {
      //called9(arg);
   }
   
   public void test94() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller94", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 95
   
   void caller95(List<String>[] arg)
   {
      //called10(arg);
   }
   
   public void test95() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller95", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 96
   
   void caller96(List<String>[] arg)
   {
      //called11(arg);
   }
   
   public void test96() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller96", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 97
   
   void caller97(List<String>[] arg)
   {
      //called12(arg);
   }
   
   public void test97() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller97", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 98
   
   void caller98(List<String>[] arg)
   {
      //called13(arg);
   }
   
   public void test98() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller98", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 99
   
   void caller99(List<String>[] arg)
   {
      //called14(arg);
   }
   
   public void test99() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller99", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 100
   
   void caller100(List<String>[] arg)
   {
      //called15(arg);
   }
   
   public void test100() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller100", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 101
   
   void caller101(List<String>[] arg)
   {
      //called16(arg);
   }
   
   public void test101() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller101", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 102
   
   void caller102(List<String>[] arg)
   {
      //called17(arg);
   }
   
   public void test102() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller102", new Class[]{List[].class});
      Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
}