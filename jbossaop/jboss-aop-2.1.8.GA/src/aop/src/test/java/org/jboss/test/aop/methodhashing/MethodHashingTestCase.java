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
package org.jboss.test.aop.methodhashing;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import junit.framework.TestCase;

import org.jboss.aop.util.MethodHashing;

/**
 * Test to make sure that the different mechanisms of creating method hashes return the same hashes
 * 
 * Don't use security for this test
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class MethodHashingTestCase extends TestCase
{

   public MethodHashingTestCase(String arg0)
   {
      // FIXME MethodHashingTestCase constructor
      super(arg0);
   }

   
   /**
    * Written as a sanity check when optimizing the MethodHashing class
    */
   public void testMethodHashingFunctionality() throws Exception
   {
      Method test1 = TestSubClass.class.getDeclaredMethod("test1", new Class[] {String.class}); 
      Method test2 = TestSubClass.class.getDeclaredMethod("test2", new Class[] {String.class, Integer.TYPE}); 
      long test1Hash = MethodHashing.calculateHash(test1);
      long test2Hash = MethodHashing.calculateHash(test2);
      assertEquals(-129615495583814694L, test1Hash);
      assertEquals(-1228234556488761696l, test2Hash);
      
      assertEquals(test1Hash, MethodHashing.methodHash(test1));
      assertEquals(test2Hash, MethodHashing.methodHash(test2));
      
      assertEquals(test1, MethodHashing.findMethodByHash(TestSubClass.class, test1Hash));
      assertEquals(test2, MethodHashing.findMethodByHash(TestSubClass.class, test2Hash));
      
      //This method seems pointless and stupid, but better test it anyway for backwards compatibility
      Map hashes = MethodHashing.getInterfaceHashes(TestSubClass.class);
      assertEquals(2, hashes.size());
      long h1 = ((Long)hashes.get(test1.toString())).longValue();
      assertEquals(test1Hash, h1);
      long h2 = ((Long)hashes.get(test2.toString())).longValue();
      assertEquals(test2Hash, h2);
      
      Constructor ctor1 = TestSubClass.class.getDeclaredConstructor(new Class[0]);
      Constructor ctor2 = TestSubClass.class.getDeclaredConstructor(new Class[] {Integer.TYPE});
      long con1Hash = MethodHashing.constructorHash(ctor1);
      long con2Hash = MethodHashing.constructorHash(ctor2);
      assertEquals(-6451128523270287660L, con1Hash);
      assertEquals(-4215863789501864959L, con2Hash);

      assertEquals(ctor1, MethodHashing.findConstructorByHash(TestSubClass.class, con1Hash));
      assertEquals(ctor2, MethodHashing.findConstructorByHash(TestSubClass.class, con2Hash));

   }
   
   static class TestBaseClass
   {
      TestBaseClass(){}
      TestBaseClass(int i){}
      void test3() {}
   }
   
   static class TestSubClass extends TestBaseClass
   {
      TestSubClass() {}
      
      TestSubClass(int i){super(i);}
      
      void test1(String s) {}
      
      int test2(String s, int i) {return i;}
   }
}
