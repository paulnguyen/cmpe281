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
package org.jboss.test.aop.introduction;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.rmi.MarshalledObject;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 60922 $
 */
public class IntroductionTester extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(IntroductionTester.class);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("IntroductionTester");
      suite.addTestSuite(IntroductionTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public IntroductionTester(String name)
   {
      super(name);
   }

   public void testSerialization() throws Exception
   {
      SerializedPOJO pojo = new SerializedPOJO();
      ((NonSerializedIntroduction) pojo).setNonMessage("hello");
      ((SerializedIntroduction) pojo).setMessage("world");

      if (!((NonSerializedIntroduction) pojo).getNonMessage().equals("hello")) throw new Exception("non serialized isn't set correctly");
      if (!((SerializedIntroduction) pojo).getMessage().equals("world")) throw new Exception("serialized isn't set correctly");

      MarshalledObject mo = new MarshalledObject(pojo);
      pojo = (SerializedPOJO) mo.get();

      if (!((NonSerializedIntroduction) pojo).getNonMessage().equals("original")) throw new Exception("non serialized isn't set correctly" + ((NonSerializedIntroduction) pojo).getNonMessage().equals("original"));
      if (!((SerializedIntroduction) pojo).getMessage().equals("world")) throw new Exception("serialized isn't set correctly");

      assertTrue(((NonSerializedIntroduction) pojo)._testUnderscore());
   }   

   public void testHandledByInterceptor() throws Exception
   {
      // load first POJO
      POJO pojo = new POJO();
      int ret = ((InterceptorHandledIntroduction) pojo).handledByIntroduction();
      // is it being intercepted by HandlerInterceptor?
      assertEquals(5, ret);
      
      // then, test on its subclass...
      POJOSubClass pojoSubClass = new POJOSubClass();
      ret = ((InterceptorHandledIntroduction) pojoSubClass).handledByIntroduction();
      // is it being intercepted by SubClassHandlerInterceptor?
      assertEquals(25, ret);
   
      // finally, test on its superclass...
      POJOSuperClass pojoSuperClass = new POJOSuperClass();
      // is it being intercepted by SuperClassHandlerInterceptor?
      ret = ((InterceptorHandledIntroduction) pojoSuperClass).handledByIntroduction();
      assertEquals(1, ret);
   }   
   
   public void testOverrideBaseclassMethodsWithMixin() throws Exception
   {
      BaseClass base = new BaseClass();
      assertEquals("sumfink", base.getNonMessage());
      
      SubClass sub = new SubClass();
      NonSerializedIntroduction intro = (NonSerializedIntroduction)sub;
      assertEquals("original", intro.getNonMessage());
   }
   
   public void testObjectHasSameMethodsAsIntroduction() throws Exception
   {
      OverrideObjectPOJO pojo = new OverrideObjectPOJO();
      assertFalse(pojo.invokedEquals);
      assertFalse(pojo.invokedHashCode);
      assertFalse(pojo.invokedToString);

      OverrideObjectInterface iface = (OverrideObjectInterface)pojo;
      
      iface.equals(null);
      assertTrue(pojo.invokedEquals);
      
      iface.hashCode();
      assertTrue(pojo.invokedHashCode);
      
      iface.toString();
      assertTrue(pojo.invokedToString);
   }
}
