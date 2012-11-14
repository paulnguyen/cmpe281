/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.synthetic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.proxy.container.AOPProxyFactoryMixin;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests that fields and methods introduced by JBoss AOP have the synthetic attribute set
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SyntheticTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("SyntheticTestCase");
      suite.addTestSuite(SyntheticTestCase.class);
      return suite;
   }

   public SyntheticTestCase(String name)
   {
      super(name);
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      //Don't use security for this test
      AOPTestDelegate delegate = new AOPTestDelegate(clazz);
      return delegate;
   }
   
   public void testWovenSynthetic()
   {
      HashSet<String> fieldsAndMethods = new HashSet<String>();
      fieldsAndMethods.add("field");
      fieldsAndMethods.add("notAdvisedField");
      fieldsAndMethods.add("staticField");
      fieldsAndMethods.add("notAdvisedStaticField");
      fieldsAndMethods.add("someMethod");
      fieldsAndMethods.add("staticMethod");
      fieldsAndMethods.add("notAdvised");
      fieldsAndMethods.add("staticNotAdvised");
      fieldsAndMethods.add("introducedMethod");
      fieldsAndMethods.add("mixinMethod");
      
      
      //Check that we are woven first, and that the correct interceptions occur
      SimpleInterceptor.intercepted = false;
      POJO temp = new POJO(1);
      assertFalse(SimpleInterceptor.intercepted);
      temp.notAdvised();
      assertFalse(SimpleInterceptor.intercepted);
      temp.notAdvisedField = 1;
      assertFalse(SimpleInterceptor.intercepted);
      assertEquals(1, temp.notAdvisedField);
      assertFalse(SimpleInterceptor.intercepted);
      POJO.staticNotAdvised();
      assertFalse(SimpleInterceptor.intercepted);
      POJO.notAdvisedStaticField = 1;
      assertFalse(SimpleInterceptor.intercepted);
      assertEquals(1, POJO.notAdvisedStaticField);
      assertFalse(SimpleInterceptor.intercepted);
         
      SimpleInterceptor.intercepted = false;
      POJO pojo = new POJO();
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      pojo.someMethod();
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      POJO.staticMethod();
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      pojo.field = 1;
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      assertEquals(1, pojo.field);
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      POJO.staticField = 2;
      assertTrue(SimpleInterceptor.intercepted);
      
      SimpleInterceptor.intercepted = false;
      assertEquals(2, POJO.staticField);
      assertTrue(SimpleInterceptor.intercepted);
      
      @SuppressWarnings("unused")
      MixinInterface mi = (MixinInterface)pojo;
      @SuppressWarnings("unused")
      SomeInterface si = (SomeInterface)pojo;

      //Now check that we have the synthetic attributes
      for (Constructor<?> ctor : pojo.getClass().getDeclaredConstructors())
      {
         assertFalse(ctor.toString() + " should not be synthetic", ctor.isSynthetic());
      }
      for (Field field : pojo.getClass().getDeclaredFields())
      {
         if (fieldsAndMethods.contains(field.getName()))
         {
            assertFalse(field.toString() + " should not be synthetic", field.isSynthetic());
         }
         else
         {
            assertTrue(field.toString() + " should be synthetic", field.isSynthetic());
         }
      }
      for (Method method : pojo.getClass().getDeclaredMethods())
      {
         if (fieldsAndMethods.contains(method.getName()))
         {
            assertFalse(method.toString() + " should not be synthetic", method.isSynthetic());
         }
         else
         {
            assertTrue(method.toString() + " should be synthetic", method.isSynthetic());
         }
      }
   }
   
   public void testCallerSynthetic() throws Exception
   {
      //These validate interception behind the scenes
      Caller caller = new Caller();
      caller.call();

   
      //Now check that we have the synthetic attributes
      for (Constructor<?> ctor : caller.getClass().getDeclaredConstructors())
      {
         assertFalse(ctor.toString() + " should not be synthetic", ctor.isSynthetic());
      }
      for (Field field : caller.getClass().getDeclaredFields())
      {
         assertTrue(field.toString() + " should be synthetic", field.isSynthetic());
      }
      for (Method method : caller.getClass().getDeclaredMethods())
      {
         if (method.getName().equals("call"))
         {
            assertFalse(method.toString() + " should not be synthetic", method.isSynthetic());
         }
         else
         {
            assertTrue(method.toString() + " should be synthetic", method.isSynthetic());
         }
      }
}
   
   public void testProxiedSynthetic() throws Exception
   {
      HashSet<String> fieldsAndMethods = new HashSet<String>();
      fieldsAndMethods.add("someMethod");
      fieldsAndMethods.add("introducedMethod");
      fieldsAndMethods.add("mixinMethod");

      Proxied tgt = new Proxied();//Load the class before we deploy the xml that will cause weaving
      
      URL url = getURLRelativeToProjectRoot("/src/resources/test/synthetic/manual-aop.xml");
      AspectXmlLoader.deployXML(url);
      
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setTarget(tgt);
      params.setInterfaces(new Class[] {SomeInterface.class});
      params.setMixins(new AOPProxyFactoryMixin[] {new AOPProxyFactoryMixin(Mixin.class, new Class<?>[] {MixinInterface.class})});
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      Proxied proxied = (Proxied)factory.createAdvisedProxy(params);
      
      SimpleInterceptor.intercepted = false;
      proxied.someMethod();
      assertTrue(SimpleInterceptor.intercepted);
      
      @SuppressWarnings("unused")
      MixinInterface mi = (MixinInterface)proxied;
      @SuppressWarnings("unused")
      SomeInterface si = (SomeInterface)proxied;

      for (Field field : proxied.getClass().getDeclaredFields())
      {
         assertTrue(field.toString() + " should be synthetic", field.isSynthetic());
      }
      for (Method method : proxied.getClass().getDeclaredMethods())
      {
         if (fieldsAndMethods.contains(method.getName()))
         {
            assertFalse(method.toString() + " should not be synthetic", method.isSynthetic());
         }
         else
         {
            assertTrue(method.toString() + " should be synthetic", method.isSynthetic());
         }
      }
      
   }

}
