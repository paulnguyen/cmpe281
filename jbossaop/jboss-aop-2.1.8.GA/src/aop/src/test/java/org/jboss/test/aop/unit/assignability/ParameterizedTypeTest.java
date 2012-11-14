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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 *
 */
@SuppressWarnings("unchecked")
public class ParameterizedTypeTest extends VariableTargetAlgorithmTest
{
   protected <A> void called1(ArrayList<A> arg) {}
   protected void called2(ArrayList<?> arg) {}
   protected void called3(ArrayList arg) {}
   protected void called4(ArrayList<String> arg) {}
   protected void called5(ArrayList<Object> arg) {}
   protected void called6(ArrayList<Integer> arg) {}
   protected void called7(ArrayList<? extends String> arg) {}
   protected void called8(ArrayList<? extends Integer> arg) {}
   protected <A> void called9(List<A> arg) {}
   protected void called10(List<?> arg) {}
   protected void called11(List arg) {}
   protected void called12(List<String> arg) {}
   protected void called13(List<Object> arg) {}
   protected void called14(List<Integer> arg) {}
   protected void called15(List<? extends String> arg) {}
   protected void called16(List<? extends Integer> arg) {}
   protected void called17(HashMap<Integer,String> a) {}
}


@SuppressWarnings({"serial","unchecked"})class MyClass1 extends MyClass2{}
@SuppressWarnings("serial")class MyClass2<A extends Runnable> extends MyClass3<A>{}
@SuppressWarnings("serial")class MyClass3<A> extends ArrayList<A>{}

@SuppressWarnings({"serial","unchecked"})class NewClass2<A> extends NewClass1{}
@SuppressWarnings({"serial","unchecked"})class NewClass3<A> extends MyClass2{}
@SuppressWarnings("serial")class NewClass1<A> extends NewClass{}
@SuppressWarnings("serial")class NewClass extends MyClass2<Runnable>{}

@SuppressWarnings("serial")class MyClass_<A extends Serializable, B> extends MyClass2_<A>{}
@SuppressWarnings("serial")class MyClass2_<A extends Serializable> extends HashMap<String, A>{}

@SuppressWarnings({"serial","unchecked"})class MyClass3_<A extends Serializable, B> extends MyClass2_{}