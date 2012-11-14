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
import java.util.List;

/**
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 *
 */
@SuppressWarnings("unchecked")
public class GenericArrayTypeTest extends VariableTargetAlgorithmTest
{
   // Scenario 1
   
   void caller1(List[] arg)
   {
      called1(arg);
   }
   
   <A extends List> void called1(A[] arg) {}
   
   public void test1() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 2
   
   void caller2(List<Runnable>[] arg)
   {
      called1(arg);
   }
   
   public void test2() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 3
   
   void caller3(List<String>[] arg)
   {
      called1(arg);
   }
   
   public void test3() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 4
   
   <A> void caller4(List<A>[] arg)
   {
      called1(arg);
   }
   
   public void test4() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 5
   
   void caller5(Collection[] arg)
   {
      //called1(arg);
   }
   
   public void test5() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 6
   
   void caller6(Object[] arg)
   {
      //called1(arg);
   }
   
   public void test6() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 7
   
   void caller7(List arg)
   {
      //called1(arg);
   }
   
   public void test7() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 8
   
   <A> void caller8(A arg)
   {
      //called1(arg);
   }
   
   public void test8() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 9
   
   <A> void caller9(A[] arg)
   {
      //called1(arg);
   }
   
   public void test9() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 10
   
   <A extends List> void caller10(A[] arg)
   {
      called1(arg);
   }
   
   public void test10() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 11
   
   <A extends List<String>> void caller11(A[] arg)
   {
      called1(arg);
   }
   
   public void test11() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 12
   
   void caller12(List<?> arg)
   {
      //called1(arg);
   }
   
   public void test12() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 13
   
   void caller13(ArrayList<String> arg)
   {
      //called1(arg);
   }
   
   public void test13() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 14
   
   <A> void caller14(List<A> arg)
   {
      //called1(arg);
   }
   
   public void test14() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 15
   
   <A extends Runnable> void caller15(List<A> arg)
   {
      //called1(arg);
   }
   
   public void test15() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 16
   
   <A extends Runnable> void caller16(List<A[]> arg)
   {
      //called1(arg);
   }
   
   public void test16() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 17
   
   <A extends Runnable> void caller17(List<List<A>[]> arg)
   {
      //called1(arg);
   }
   
   public void test17() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called1", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 18
   
   void caller18(List[] arg)
   {
      called18(arg);
   }
   
   <A extends List<Runnable>> void called18(A[] arg) {}
   
   public void test18() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 19
   
   void caller19(List<Runnable>[] arg)
   {
      called18(arg);
   }
   
   public void test19() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 20
   
   void caller20(List<String>[] arg)
   {
      //called18(arg);
   }
   
   public void test20() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 21
   
   <A> void caller21(List<A>[] arg)
   {
      //called18(arg);
   }
   
   public void test21() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 22
   
   void caller22(Collection[] arg)
   {
      //called18(arg);
   }
   
   public void test22() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 23
   
   void caller23(Object[] arg)
   {
      //called18(arg);
   }
   
   public void test23() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 24
   
   void caller24(List arg)
   {
      //called18(arg);
   }
   
   public void test24() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 25
   
   <A> void caller25(A arg)
   {
      //called18(arg);
   }
   
   public void test25() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 26
   
   <A> void caller26(A[] arg)
   {
      //called18(arg);
   }
   
   public void test26() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 27
   
   <A extends List> void caller27(A[] arg)
   {
      called18(arg);
   }
   
   public void test27() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 28
   
   <A extends List<Runnable>> void caller28(A[] arg)
   {
      called18(arg);
   }
   
   public void test28() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 29
   
   void caller29(List<?> arg)
   {
      //called18(arg);
   }
   
   public void test29() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 30
   
   void caller30(ArrayList<Runnable> arg)
   {
      //called18(arg);
   }
   
   public void test30() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 31
   
   <A> void caller31(List<A> arg)
   {
      //called18(arg);
   }
   
   public void test31() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 32
   
   <A extends Runnable> void caller32(List<A> arg)
   {
      //called18(arg);
   }
   
   public void test32() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 33
   
   <A extends Runnable> void caller33(List<A[]> arg)
   {
      //called18(arg);
   }
   
   public void test33() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 34
   
   <A extends Runnable> void caller34(List<List<A>[]> arg)
   {
      //called18(arg);
   }
   
   public void test34() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called18", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   
   // Scenario 35
   
   void caller35(List[] arg)
   {
      called35(arg);
   }
   
   <A> void called35(A[] arg) {}
   
   public void test35() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 36
   
   void caller36(List<Runnable>[] arg)
   {
      called35(arg);
   }
   
   public void test36() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 37
   
   void caller37(List<String>[] arg)
   {
      called35(arg);
   }
   
   public void test37() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 38
   
   <A> void caller38(List<A>[] arg)
   {
      called35(arg);
   }
   
   public void test38() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 39
   
   void caller39(Collection[] arg)
   {
      called35(arg);
   }
   
   public void test39() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 40
   
   void caller40(Object[] arg)
   {
      called35(arg);
   }
   
   public void test40() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 41
   
   void caller41(List arg)
   {
      //called35(arg);
   }
   
   public void test41() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 42
   
   <A> void caller42(A arg)
   {
      //called35(arg);
   }
   
   public void test42() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 43
   
   <A> void caller43(A[] arg)
   {
      called35(arg);
   }
   
   public void test43() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 44
   
   <A extends List> void caller44(A[] arg)
   {
      called35(arg);
   }
   
   public void test44() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 45
   
   <A extends List<Runnable>> void caller45(A[] arg)
   {
      called35(arg);
   }
   
   public void test45() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 46
   
   void caller46(List<?> arg)
   {
      //called35(arg);
   }
   
   public void test46() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 47
   
   void caller47(ArrayList<Runnable> arg)
   {
      //called35(arg);
   }
   
   public void test47() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller47", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 48
   
   <A> void caller48(List<A> arg)
   {
      //called35(arg);
   }
   
   public void test48() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller48", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 49
   
   <A extends Runnable> void caller49(List<A> arg)
   {
      //called35(arg);
   }
   
   public void test49() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller49", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 50
   
   <A extends Runnable> void caller50(List<A[]> arg)
   {
      //called35(arg);
   }
   
   public void test50() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller50", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 51
   
   <A extends Runnable> void caller51(List<List<A>[]> arg)
   {
      //called35(arg);
   }
   
   public void test51() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller51", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called35", new Class[]{Object[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 52
   
   void caller52(List[] arg)
   {
      called52(arg);
   }
   
   void called52(List<?>[] arg) {}
   
   public void test52() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller52", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 53
   
   void caller53(List<Runnable>[] arg)
   {
      called52(arg);
   }
   
   public void test53() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller53", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 54
   
   void caller54(List<String>[] arg)
   {
      called52(arg);
   }
   
   public void test54() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller54", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 55
   
   <A> void caller55(List<A>[] arg)
   {
      called52(arg);
   }
   
   public void test55() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller55", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 56
   
   void caller56(Collection[] arg)
   {
      //called52(arg);
   }
   
   public void test56() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller56", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 57
   
   void caller57(Object[] arg)
   {
      //called52(arg);
   }
   
   public void test57() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller57", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 58
   
   void caller58(List arg)
   {
      //called52(arg);
   }
   
   public void test58() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller58", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 59
   
   <A> void caller59(A arg)
   {
      //called52(arg);
   }
   
   public void test59() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller59", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 60
   
   <A> void caller60(A[] arg)
   {
      //called52(arg);
   }
   
   public void test60() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller60", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 61
   
   <A extends List> void caller61(A[] arg)
   {
      called52(arg);
   }
   
   public void test61() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller61", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 62
   
   <A extends List<Runnable>> void caller62(A[] arg)
   {
      called52(arg);
   }
   
   public void test62() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller62", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 63
   
   void caller63(List<?> arg)
   {
      //called52(arg);
   }
   
   public void test63() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller63", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 64
   
   void caller64(ArrayList<Runnable> arg)
   {
      //called52(arg);
   }
   
   public void test64() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller64", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 65
   
   <A> void caller65(List<A> arg)
   {
      //called52(arg);
   }
   
   public void test65() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller65", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 66
   
   <A extends Runnable> void caller66(List<A> arg)
   {
      //called52(arg);
   }
   
   public void test66() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller66", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 67
   
   <A extends Runnable> void caller67(List<A[]> arg)
   {
      //called52(arg);
   }
   
   public void test67() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller67", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 68
   
   <A extends Runnable> void caller68(List<List<A>[]> arg)
   {
      //called52(arg);
   }
   
   public void test68() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller68", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called52", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 69
   
   void caller69(List[] arg)
   {
      called69(arg);
   }
   
   void called69(List<? super Runnable>[] arg) {}
   
   public void test69() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller69", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 70
   
   void caller70(List<Runnable>[] arg)
   {
      called69(arg);
   }
   
   public void test70() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller70", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 71
   
   void caller71(List<String>[] arg)
   {
      //called69(arg);
   }
   
   public void test71() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller71", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 72
   
   <A> void caller72(List<A>[] arg)
   {
      //called69(arg);
   }
   
   public void test72() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller72", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 73
   
   void caller73(Collection[] arg)
   {
      //called69(arg);
   }
   
   public void test73() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller73", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 74
   
   void caller74(Object[] arg)
   {
      //called69(arg);
   }
   
   public void test74() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller74", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 75
   
   void caller75(List arg)
   {
      //called69(arg);
   }
   
   public void test75() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller75", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 76
   
   <A> void caller76(A arg)
   {
      //called69(arg);
   }
   
   public void test76() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller76", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 77
   
   <A> void caller77(A[] arg)
   {
      //called69(arg);
   }
   
   public void test77() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller77", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 78
   
   <A extends List> void caller78(A[] arg)
   {
      called69(arg);
   }
   
   public void test78() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller78", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 79
   
   <A extends List<Runnable>> void caller79(A[] arg)
   {
      called69(arg);
   }
   
   public void test79() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller79", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 80
   
   void caller80(List<?> arg)
   {
      //called69(arg);
   }
   
   public void test80() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller80", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 81
   
   void caller81(ArrayList<Runnable> arg)
   {
      //called69(arg);
   }
   
   public void test81() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller81", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 82
   
   <A> void caller82(List<A> arg)
   {
      //called69(arg);
   }
   
   public void test82() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller82", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 83
   
   <A extends Runnable> void caller83(List<A> arg)
   {
      //called69(arg);
   }
   
   public void test83() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller83", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 84
   
   <A extends Runnable> void caller84(List<A[]> arg)
   {
      //called69(arg);
   }
   
   public void test84() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller84", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 85
   
   <A extends Runnable> void caller85(List<List<A>[]> arg)
   {
      //called69(arg);
   }
   
   public void test85() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller85", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 86
   
   void caller86(List[] arg)
   {
      called86(arg);
   }
   
   void called86(List<Runnable>[] arg) {}
   
   public void test86() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller86", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 87
   
   void caller87(List<Runnable>[] arg)
   {
      called86(arg);
   }
   
   public void test87() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller87", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 88
   
   void caller88(List<String>[] arg)
   {
      //called86(arg);
   }
   
   public void test88() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller88", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 89
   
   <A> void caller89(List<A>[] arg)
   {
      //called86(arg);
   }
   
   public void test89() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller89", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 90
   
   void caller90(Collection[] arg)
   {
      //called86(arg);
   }
   
   public void test90() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller90", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 91
   
   void caller91(Object[] arg)
   {
      //called86(arg);
   }
   
   public void test91() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller91", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 92
   
   void caller92(List arg)
   {
      //called86(arg);
   }
   
   public void test92() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller92", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 93
   
   <A> void caller93(A arg)
   {
      //called86(arg);
   }
   
   public void test93() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller93", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 94
   
   <A> void caller94(A[] arg)
   {
      //called86(arg);
   }
   
   public void test94() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller94", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 95
   
   <A extends List> void caller95(A[] arg)
   {
      called86(arg);
   }
   
   public void test95() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller95", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 96
   
   <A extends List<Runnable>> void caller96(A[] arg)
   {
      called86(arg);
   }
   
   public void test96() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller96", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 97
   
   void caller97(List<?> arg)
   {
      //called86(arg);
   }
   
   public void test97() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller97", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 98
   
   void caller98(ArrayList<Runnable> arg)
   {
      //called86(arg);
   }
   
   public void test98() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller98", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 99
   
   <A> void caller99(List<A> arg)
   {
      //called86(arg);
   }
   
   public void test99() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller99", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 100
   
   <A extends Runnable> void caller100(List<A> arg)
   {
      //called86(arg);
   }
   
   public void test100() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller100", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 101
   
   <A extends Runnable> void caller101(List<A[]> arg)
   {
      //called86(arg);
   }
   
   public void test101() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller101", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 102
   
   <A extends Runnable> void caller102(List<List<A>[]> arg)
   {
      //called86(arg);
   }
   
   public void test102() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller102", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called86", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }   
   
   // Scenario 103
   
   void caller103(List[] arg)
   {
      called103(arg);
   }
   
   <A> void called103(List<A>[] arg) {}
   
   public void test103() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller103", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 104
   
   void caller104(List<Runnable>[] arg)
   {
      called103(arg);
   }
   
   public void test104() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller104", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 105
   
   void caller105(List<String>[] arg)
   {
      called103(arg);
   }
   
   public void test105() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller105", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 106
   
   <A> void caller106(List<A>[] arg)
   {
      called103(arg);
   }
   
   public void test106() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller106", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 107
   
   void caller107(Collection[] arg)
   {
      //called103(arg);
   }
   
   public void test107() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller107", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 108
   
   void caller108(Object[] arg)
   {
      //called103(arg);
   }
   
   public void test108() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller108", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called69", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 109
   
   void caller109(List arg)
   {
      //called103(arg);
   }
   
   public void test109() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller109", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 110
   
   <A> void caller110(A arg)
   {
      //called103(arg);
   }
   
   public void test110() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller110", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 111
   
   <A> void caller111(A[] arg)
   {
      //called103(arg);
   }
   
   public void test111() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller111", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 112
   
   <A extends List> void caller112(A[] arg)
   {
      called103(arg);
   }
   
   public void test112() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller112", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 113
   
   <A extends List<Runnable>> void caller113(A[] arg)
   {
      called103(arg);
   }
   
   public void test113() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller113", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 114
   
   void caller114(List<?> arg)
   {
      //called103(arg);
   }
   
   public void test114() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller114", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 115
   
   void caller115(ArrayList<Runnable> arg)
   {
      //called103(arg);
   }
   
   public void test115() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller115", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 116
   
   <A> void caller116(List<A> arg)
   {
      //called103(arg);
   }
   
   public void test116() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller116", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 117
   
   <A extends Runnable> void caller117(List<A> arg)
   {
      //called103(arg);
   }
   
   public void test117() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller117", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 118
   
   <A extends Runnable> void caller118(List<A[]> arg)
   {
      //called103(arg);
   }
   
   public void test118() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller118", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 119
   
   <A extends Runnable> void caller119(List<List<A>[]> arg)
   {
      //called103(arg);
   }
   
   public void test119() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller119", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called103", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 120
   
   void caller120(List[] arg)
   {
      called120(arg);
   }
   
   <A extends Runnable> void called120(List<A>[] arg) {}
   
   public void test120() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller120", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 121
   
   void caller121(List<Runnable>[] arg)
   {
      called120(arg);
   }
   
   public void test121() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller121", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 122
   
   void caller122(List<String>[] arg)
   {
      //called120(arg);
   }
   
   public void test122() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller122", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 123
   
   <A> void caller123(List<A>[] arg)
   {
      //called120(arg);
   }
   
   public void test123() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller123", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 124
   
   void caller124(Collection[] arg)
   {
      //called120(arg);
   }
   
   public void test124() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller124", new Class[]{Collection[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 125
   
   void caller125(Object[] arg)
   {
      //called120(arg);
   }
   
   public void test125() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller125", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 126
   
   void caller126(List arg)
   {
      //called120(arg);
   }
   
   public void test126() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller126", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 127
   
   <A> void caller127(A arg)
   {
      //called120(arg);
   }
   
   public void test127() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller127", new Class[]{Object.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 128
   
   <A> void caller128(A[] arg)
   {
      //called120(arg);
   }
   
   public void test128() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller128", new Class[]{Object[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 129
   
   <A extends List> void caller129(A[] arg)
   {
      called120(arg);
   }
   
   public void test129() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller129", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }

   // Scenario 130
   
   <A extends List<Runnable>> void caller130(A[] arg)
   {
      called120(arg);
   }
   
   public void test130() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller130", new Class[]{List[].class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 131
   
   void caller131(List<?> arg)
   {
      //called120(arg);
   }
   
   public void test131() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller131", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 132
   
   void caller132(ArrayList<Runnable> arg)
   {
      //called120(arg);
   }
   
   public void test132() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller132", new Class[]{ArrayList.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 133
   
   <A> void caller133(List<A> arg)
   {
      //called120(arg);
   }
   
   public void test133() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller133", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 134
   
   <A extends Runnable> void caller134(List<A> arg)
   {
      //called120(arg);
   }
   
   public void test134() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller134", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 135
   
   <A extends Runnable> void caller135(List<A[]> arg)
   {
      //called120(arg);
   }
   
   public void test135() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller135", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
   
   // Scenario 136
   
   <A extends Runnable> void caller136(List<List<A>[]> arg)
   {
      //called120(arg);
   }
   
   public void test136() throws Exception
   {
      Method caller = this.getClass().getDeclaredMethod("caller136", new Class[]{List.class});
      Method called = this.getClass().getDeclaredMethod("called120", new Class[]{List[].class});
      assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0],
            caller.getGenericParameterTypes()[0], hierarchy));
   }
}