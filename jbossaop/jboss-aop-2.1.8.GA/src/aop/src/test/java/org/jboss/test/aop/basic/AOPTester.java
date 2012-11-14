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
package org.jboss.test.aop.basic;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;

import org.jboss.aop.Advised;
import org.jboss.aop.InstanceAdvised;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class AOPTester extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AOPTester");
      suite.addTestSuite(AOPTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public AOPTester(String name)
   {
      super(name);
   }

   // Public -------------------------------------------------------

   public void testBasic()
   {
      System.out.println("RUNNING TEST BASIC");
      try
      {
         POJO pojo = new POJO();
         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.someMethod();
         if (!"someMethod".equals(SimpleInterceptor.lastIntercepted)) throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed)) throw new RuntimeException("Failed on metadata test");

         InstanceOfInterceptor.intercepted = false;
         Implements1 impl1 = new Implements1();
         if (InstanceOfInterceptor.intercepted == false) throw new RuntimeException("failed all(instanceof) constructor interception");
         InstanceOfInterceptor.intercepted = false;
         impl1.foo = 1;
         if (InstanceOfInterceptor.intercepted == false) throw new RuntimeException("failed all(instanceof) field interception");
         InstanceOfInterceptor.intercepted = false;
         impl1.someMethod();
         if (InstanceOfInterceptor.intercepted == false) throw new RuntimeException("failed all(instanceof) method interception");

         InstanceOfInterceptor.intercepted = false;
         Implements2 impl2 = new Implements2();
         if (InstanceOfInterceptor.intercepted == true) throw new RuntimeException("failed method only (instanceof) constructor interception");
         InstanceOfInterceptor.intercepted = false;
         impl2.someMethod();
         if (InstanceOfInterceptor.intercepted == false) throw new RuntimeException("failed method only(instanceof) method interception");
         InstanceOfInterceptor.intercepted = false;

         CFlowedPOJO cflow = new CFlowedPOJO();
         InterceptorCounter.count = 0;
         cflow.method3();
         if (InterceptorCounter.count > 0) throw new RuntimeException("method3 count should be null");
         InterceptorCounter.count = 0;
         cflow.method1();
         if (InterceptorCounter.count != 1) throw new RuntimeException("method1 count should be 1");
         InterceptorCounter.count = 0;
         cflow.recursive(1);
         if (InterceptorCounter.count == 0) throw new RuntimeException("recursive never get intercepted");
         if (InterceptorCounter.count > 1) throw new RuntimeException("recursive too many interceptions");
      }
      catch (Throwable ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
   }

   public void testInheritance()
   {
      System.out.println("RUNNING TEST INHERITANCE");
      try
      {
         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         POJOChild pojo = new POJOChild();
         pojo.someMethod2();
         if (!"someMethod2".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");

         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.someMethod();
         if (!"someMethod".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");

      }
      catch (Throwable ex)
      {
         throw new RuntimeException(ex);
      }
   }

   public void testMetadata()
   {
      System.out.println("RUNNING TEST METADATA");

      try
      {
         POJOChild pojo = new POJOChild();
         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.someMethod();
         if (!"someMethod".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");

         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.anotherMethod();
         if (!"anotherMethod".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"Required".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");


         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.someMethod2();
         if (!"someMethod2".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");


         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         pojo.someMethod3();
         if (!"someMethod3".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"Supports".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");

         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         org.jboss.aop.metadata.ThreadMetaData.instance().addMetaData("transaction", "trans-attribute", "Never");
         pojo.someMethod3();
         if (!"someMethod3".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"Never".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");
         org.jboss.aop.metadata.ThreadMetaData.instance().clear();

         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         InstanceAdvisor instanceAdvisor = ((Advised) pojo)._getInstanceAdvisor();
         instanceAdvisor.getMetaData().addMetaData("transaction", "trans-attribute", "NotSupported");
         pojo.someMethod3();
         if (!"someMethod3".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"NotSupported".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");
         org.jboss.aop.metadata.ThreadMetaData.instance().clear();

      }
      catch (Throwable ex)
      {
         throw new RuntimeException(ex);
      }

   }


   public void testDynamicInterceptors()
   {
      System.out.println("RUNNING TEST DYNAMIC INTERCEPTORS");
      try
      {
         POJOChild pojo = new POJOChild();
         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         BeforeInterceptor.lastIntercepted = null;
         BeforeInterceptor.lastTransAttributeAccessed = null;
         ((Advised) pojo)._getInstanceAdvisor().insertInterceptor(new BeforeInterceptor());
         pojo.someMethod();
         if (!"someMethod".equals(SimpleInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(SimpleInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");
         if (!"someMethod".equals(BeforeInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(BeforeInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");


         SimpleInterceptor.lastIntercepted = null;
         SimpleInterceptor.lastTransAttributeAccessed = null;
         BeforeInterceptor.lastIntercepted = null;
         BeforeInterceptor.lastTransAttributeAccessed = null;
         AfterInterceptor.lastIntercepted = null;
         AfterInterceptor.lastTransAttributeAccessed = null;
         ((Advised) pojo)._getInstanceAdvisor().appendInterceptor(new AfterInterceptor());
         pojo.someMethod();
         if (!"someMethod".equals(BeforeInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(BeforeInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");
         if (!"someMethod".equals(AfterInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"RequiresNew".equals(AfterInterceptor.lastTransAttributeAccessed))
            throw new RuntimeException("Failed on metadata test");


      }
      catch (Throwable ex)
      {
         throw new RuntimeException(ex);
      }


   }

   public void testFieldInterception()
   {
      System.out.println("RUNNING TEST FIELD INTERCEPTION");
      try
      {


         POJO pojo = new POJO();
         SimpleInterceptor.lastFieldIntercepted = null;
         SimpleInterceptor.lastFieldTransAttributeAccessed = null;
         pojo.accessField();

         if (!"privateField".equals(SimpleInterceptor.lastFieldIntercepted)) throw new RuntimeException("Failed on interception test");
         if (!"NotSupported".equals(SimpleInterceptor.lastFieldTransAttributeAccessed)) throw new RuntimeException("Failed on metadata test");


         POJOChild child = new POJOChild();
         SimpleInterceptor.lastFieldIntercepted = null;
         SimpleInterceptor.lastFieldTransAttributeAccessed = null;
         child.accessField();
         if (!"privateField".equals(SimpleInterceptor.lastFieldIntercepted)) throw new RuntimeException("Failed on interception test");
         if (!"NotSupported".equals(SimpleInterceptor.lastFieldTransAttributeAccessed)) throw new RuntimeException("Failed on metadata test");

         SimpleInterceptor.lastFieldIntercepted = null;
         SimpleInterceptor.lastFieldTransAttributeAccessed = null;
         child.accessProtectedField();
         if (!"protectedField".equals(SimpleInterceptor.lastFieldIntercepted)) throw new RuntimeException("Failed on interception test");
         if (!"Supports".equals(SimpleInterceptor.lastFieldTransAttributeAccessed)) throw new RuntimeException("Failed on metadata test");

         POJORef ref = new POJORef();
         SimpleInterceptor.lastFieldIntercepted = null;
         SimpleInterceptor.lastFieldTransAttributeAccessed = null;
         ref.refPOJO();


         if (!"protectedField".equals(SimpleInterceptor.lastFieldIntercepted)) throw new RuntimeException("Failed on interception test");
         if (!"Supports".equals(SimpleInterceptor.lastFieldTransAttributeAccessed)) throw new RuntimeException("Failed on metadata test");

         pojo.accessStaticField();


      }
      catch (Throwable ex)
      {
         throw new RuntimeException(ex);
      }
   }

   public void testMethodInterception()
   {
      System.out.println("RUNNING METHOD INTERCEPTION");
      try
      {
         PrivatePOJO priv = new PrivatePOJO();
         SimpleInterceptor.lastIntercepted = null;
         priv.callPrivate();
         if (SimpleInterceptor.lastIntercepted == null) throw new RuntimeException("unable to intercept private method");
         
         POJO.staticMethod();
         POJOConstructorTest vanilla;
         vanilla = new POJOConstructorTest();

         vanilla.data = "error";
         vanilla.someMethod();
         if (!vanilla.data.equals("someMethod")) throw new RuntimeException("someMethod() didn't get correct method metadata");

         vanilla.data = "error";
         vanilla.another();
         if (!vanilla.data.equals("another()")) throw new RuntimeException("another() didn't get correct method metadata: " + vanilla.data);

         vanilla.data = "nothing";
         POJOMethodInterceptor.wasHit = false;
         vanilla.another(1);
         if (POJOMethodInterceptor.wasHit) throw new RuntimeException("interceptor should not have been called");
         if (!vanilla.data.equals("nothing")) throw new RuntimeException("another(int) shouldn't get intercepted: " + vanilla.data);

         vanilla.data = "nothing";
         vanilla.another(1, 1);
         if (!vanilla.data.equals("another(int, int)")) throw new RuntimeException("another(int, int) didn't get intercepted: " + vanilla.data);
      }
      catch (Throwable ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex.getMessage());
      }
   }

   public void testAspect()
   {
      System.out.println("RUNNING ASPECT TEST");
      try
      {
         POJO.staticMethod();
         POJOAspectTester vanilla;
         vanilla = new POJOAspectTester();
         if (!vanilla.marker.equals("interceptConstructor")) throw new RuntimeException("vanilla constructor didn't get intercepted");

         vanilla.marker = "error";
         vanilla.someMethod();
         if (!vanilla.marker.equals("interceptMethod")) throw new RuntimeException("vanilla.someMethod() didn't get intercepted");

         vanilla.marker = "error";
         vanilla.field = 5;
         if (!vanilla.marker.equals("interceptField")) throw new RuntimeException("vanilla.field  didn't get intercepted");

      }
      catch (Throwable ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
   }

   public void testConstructorInterception()
   {
      System.out.println("RUNNING CONSTRUCTOR INTERCEPTION");
      try
      {

         POJO pojo = new POJO();
         System.out.println("pojo=" + pojo);
         POJOChild child = new POJOChild();
         System.out.println("child=" + child);

         POJORef ref = new POJORef();
         ref.constructPOJO();

         POJOWildCardConstructorTest wild;
         wild = new POJOWildCardConstructorTest();
         if (wild == null) throw new RuntimeException("wild was null!");
         if (wild.data.equals("error")) throw new RuntimeException("wild() didn't intercept");
         wild = new POJOWildCardConstructorTest(1);
         if (wild.data.equals("error")) throw new RuntimeException("wild(int) didn't intercept");

         POJOConstructorTest vanilla;
         vanilla = new POJOConstructorTest();
         if (vanilla == null) throw new RuntimeException("vanilla was null!");
         System.out.println("calling vanilla.data.equals");
         if (vanilla.data.equals("error")) throw new RuntimeException("vanilla() didn't intercept");
         if (!vanilla.data.equals("empty")) throw new RuntimeException("vanilla() didn't get correct constructor metadata");
         vanilla = new POJOConstructorTest(1, 1);
         if (vanilla.data.equals("error")) throw new RuntimeException("vanilla(int, int) didn't intercept");
         if (!vanilla.data.equals("int, int")) throw new RuntimeException("vanilla(int, int) didn't get correct constructor metadata");
         vanilla = new POJOConstructorTest(1);
         if (!vanilla.data.equals("error")) throw new RuntimeException("vanilla(int) did intercept when it shouldn't have");

      }
      catch (Throwable ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex.getMessage());
      }
   }

   public void testExceptions()
   {
      System.out.println("TEST AOP EXCEPTIONS");
      try
      {
         NoInterceptorsPOJO pojo = new NoInterceptorsPOJO();

         pojo.throwException();

      }
      catch (SomeException ignored)
      {
         System.out.println("caught SomeException successfully");
      }
      try
      {
         POJO pojo = new POJO();

         pojo.throwException();
      }
      catch (SomeException ignored)
      {
         System.out.println("caught SomeException successfully");
      }
   }

   public void testMixin()
   {
      try
      {
         System.out.println("TEST MIXIN");
         POJO pojo = new POJO();
         System.out.println("TEST Introduction");
         Introduction intro = (Introduction) pojo;
         System.out.println(intro.helloWorld("world"));
         System.out.println("TEST Introduction2");
         Introduction2 intro2 = (Introduction2) pojo;
         System.out.println(intro2.goodbye("world"));
         System.out.println("TEST InterfaceMixin");
         InterfaceMixin mixin = (InterfaceMixin) pojo;
         System.out.println(mixin.whazup());

         POJOChild child = new POJOChild();
         System.out.println("TEST child Introduction");
         intro = (Introduction) child;
         System.out.println(intro.helloWorld("world"));
         System.out.println("TEST child Introduction2");
         intro2 = (Introduction2) child;
         System.out.println(intro2.goodbye("world"));
         System.out.println("TEST child AnotherIntroduction");
         SubclassIntroduction sub = (SubclassIntroduction) child;
         System.out.println(sub.subclassHelloWorld("world"));
         System.out.println("TEST metadata introduction pointcut");
         NoInterceptorsPOJO nopojo = new NoInterceptorsPOJO();
         intro = (Introduction) nopojo;

      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
   }

   public void testInstanceMixin() throws Exception
   {
      try
      {
         AfterInterceptor.lastIntercepted = null;
         AfterInterceptor.lastTransAttributeAccessed = null;
         BeforeInterceptor.lastIntercepted = null;
         BeforeInterceptor.lastTransAttributeAccessed = null;

         System.out.println("TEST INSTANCE MIXIN");
         POJO pojo = new POJO();
         ((Advised)pojo)._getInstanceAdvisor().insertInterceptor(new BeforeInterceptor());
         Introduction intro = (Introduction) pojo;
         System.out.println(intro.helloWorld("world"));
         if (!"helloWorld".equals(BeforeInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         
         AfterInterceptor.lastIntercepted = null;
         AfterInterceptor.lastTransAttributeAccessed = null;
         BeforeInterceptor.lastIntercepted = null;
         BeforeInterceptor.lastTransAttributeAccessed = null;
         
         ((Advised)pojo)._getInstanceAdvisor().appendInterceptor(new AfterInterceptor());

         System.out.println(intro.helloWorld("world"));
         if (!"helloWorld".equals(BeforeInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"helloWorld".equals(AfterInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         
         
         AfterInterceptor.lastIntercepted = null;
         AfterInterceptor.lastTransAttributeAccessed = null;
         BeforeInterceptor.lastIntercepted = null;
         BeforeInterceptor.lastTransAttributeAccessed = null;

         POJOChild child = new POJOChild();
         ((Advised)child)._getInstanceAdvisor().insertInterceptor(new BeforeInterceptor());
         ((Advised)child)._getInstanceAdvisor().appendInterceptor(new AfterInterceptor());
         Introduction2 intro2 = (Introduction2) child;
         System.out.println(intro2.goodbye("world"));
         if (!"goodbye".equals(BeforeInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");
         if (!"goodbye".equals(AfterInterceptor.lastIntercepted))
            throw new RuntimeException("Failed on interception test");

      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
   }

   
   public void testCallerPointcut()
   {
      CallingPOJO callingPOJO = new CallingPOJO();
      callingPOJO.callSomeMethod();
      callingPOJO.nocallSomeMethod();
      callingPOJO.callUnadvised();
   }

   public void testNot()
   {
      System.out.println("TEST !STATIC in pointcut expression");
      NotPOJO pojo = new NotPOJO();
      CallerInterceptor.called = false;
      pojo.hello();
      if (!CallerInterceptor.called) throw new RuntimeException("should have intercepted NotPOJO.hello()");

      CallerInterceptor.called = false;
      NotPOJO.world();
      if (CallerInterceptor.called) throw new RuntimeException("should not have intercepted NotPOJO.world()");
   }

   public void testInterfaceUsedInHas()
   {
      System.out.println("TEST Interface used in has");
      SubclassInterfaceUsedInHasImpl pojo = new SubclassInterfaceUsedInHasImpl();

      Class clazz = InterfaceUsedInHasImpl.class;
      System.out.println(clazz.getName() + " methods=" + Arrays.asList(clazz.getDeclaredMethods()));

      clazz = SubclassInterfaceUsedInHasImpl.class;
      System.out.println(clazz.getName() + " methods=" + Arrays.asList(clazz.getDeclaredMethods()));
      
      CallerInterceptor.called = false;
      System.out.println("About to invoke pojo.someMethod()");
      pojo.someMethod();
      if (CallerInterceptor.called == false) throw new RuntimeException("should have intercepted InterfaceUsedInHas.someMethod()");

      CallerInterceptor.called = false;
      System.out.println("About to invoke pojo.someOtherMethod()");
      pojo.someOtherMethod();
      if (CallerInterceptor.called == false) throw new RuntimeException("should have intercepted SubclassInterfaceUsedInHasImpl.someOtherMethod()");
   }

   public void testTypedefExecution()
   {
      System.out.println("RUNNING TEST TYPEDEF EXECUTION");

      System.out.println("intercept class constructor");
      CallerInterceptor.called = false;
      ExecutionTypedefPOJO pojo = new ExecutionTypedefPOJO();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept class field read");
      int i = pojo.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO.field1 read " + i);
      CallerInterceptor.called = false;

      System.out.println("intercept class field write");
      pojo.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept class field read");
      i = pojo.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept class field write");
      pojo.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept class method execution");
      pojo.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO.method() execution");
      CallerInterceptor.called = false;

      try
      {
         Serializable ser = (Serializable)pojo;
         System.out.println(ser);
      }
      catch(ClassCastException e)
      {
         throw new RuntimeException("ExecutionTypedefPOJO does not implement serializable");
      }

      try
      {
         EmptyInterface ei = (EmptyInterface)pojo;
         System.out.println(ei);
      }
      catch(ClassCastException e)
      {
         throw new RuntimeException("ExecutionTypedefPOJO does not implement EmptyInterface");
      }

      try
      {
         Introduction intro = (Introduction)pojo;
         intro.helloWorld("lalala");
      }
      catch(ClassCastException e)
      {
         throw new RuntimeException("ExecutionTypedefPOJO does not implement Introduction");
      }
      
      System.out.println("intercept instanceof constructor");
      ExecutionTypedefPOJO2 pojo2 = new ExecutionTypedefPOJO2();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2 constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field read");
      i = pojo2.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2.field1 read");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field write");
      pojo2.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field read");
      i = pojo2.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field write");
      pojo2.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof method execution");
      pojo2.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO2 method execution");
      CallerInterceptor.called = false;

      try
      {
         Serializable ser = (Serializable)pojo;
         System.out.println("Serializable " + ser);
      }
      catch(ClassCastException e)
      {
         throw new RuntimeException("ExecutionTypedefPOJO2 does not implement serializable");
      }
      
      System.out.println("intercept annotation constructor");
      ExecutionTypedefPOJO3 pojo3 = new ExecutionTypedefPOJO3();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3 constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field read");
      i = pojo3.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3.field1 read");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field write");
      pojo3.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field read");
      i = pojo3.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field write");
      pojo3.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3.field2 write");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation method execution");
      pojo3.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept ExecutionTypedefPOJO3 method execution");

      try
      {
         Serializable ser = (Serializable)pojo;
         System.out.println(ser);
      }
      catch(ClassCastException e)
      {
         throw new RuntimeException("ExecutionTypedefPOJO does not implement serializable");
      }
      
   }

   public void testTypedefCaller()
   {
      System.out.println("RUNNING TEST TYPEDEF CALLER");
      CallerTypedefCaller caller = new CallerTypedefCaller();
      caller.call();
   }
   
   public void testPointcutExceptions()
   {
      System.out.println("RUNNING TEST POINTCUT EXCEPTIONS"); 
      try
      {
         InstanceOfInterceptor.intercepted = false;
         POJO pojo = new POJO(1);
         if (!InstanceOfInterceptor.intercepted) throw new RuntimeException("Constructor not intercepted");
         
         InstanceOfInterceptor.intercepted = false;
         pojo.withSomeException();
         if (!InstanceOfInterceptor.intercepted) throw new RuntimeException("Method 1) not intercepted");
         
         InstanceOfInterceptor.intercepted = false;
         pojo.withExceptionAndOthers("h");
         if (InstanceOfInterceptor.intercepted) throw new RuntimeException("Method 2) intercepted");
         
         InstanceOfInterceptor.intercepted = false;
         pojo.withExceptionAndOthers(1);
         if (!InstanceOfInterceptor.intercepted) throw new RuntimeException("Method 3) not intercepted");
         
         InstanceOfInterceptor.intercepted = false;
         pojo.withClassCastException(1);
         if (!InstanceOfInterceptor.intercepted) throw new RuntimeException("Method 4) not intercepted");
      }
      catch (NotSerializableException e)
      {
         // TODO Auto-generated catch block
         throw new RuntimeException(e);
      }
      catch (ClassCastException e)
      {
         // TODO Auto-generated catch block
         throw new RuntimeException(e);
      }
      catch (SomeException e)
      {
         // TODO Auto-generated catch block
         throw new RuntimeException(e);
      }
   }
   
   public void testPointcutExceptionsCaller()
   {
      System.out.println("RUNNING TEST POINTCUT EXCEPTIONS CALLER"); 
      CallingPOJO callingPOJO = new CallingPOJO();
      callingPOJO.callUnadvisedWithPointcutException();
   }
   
   public void testNotAllAdvisedFields()
   {
      System.out.println("RUNNING TEST NOT ALL ADVISED FIELDS");
      NotAllAdvisedFieldsPOJO pojo = new NotAllAdvisedFieldsPOJO();

      SimpleInterceptor.lastFieldIntercepted = null;
      assertEquals(5, pojo.notadvised);
      assertNull(SimpleInterceptor.lastFieldIntercepted);
      
      SimpleInterceptor.lastFieldIntercepted = null;
      assertEquals(10, pojo.thisisadvised);
      assertEquals("thisisadvised", SimpleInterceptor.lastFieldIntercepted);
      
      SimpleInterceptor.lastFieldIntercepted = null;
      pojo.thisisadvised = 20;
      assertEquals("thisisadvised", SimpleInterceptor.lastFieldIntercepted);      
      assertEquals(20, pojo.thisisadvised);
   }
   
   public void testMixWildCardsAndSpecifiedParameters()
   {
      NamedInterceptor.invoked.clear();
      MixedParametersPOJO pojo = new MixedParametersPOJO(1, "5", 6);
      assertTrue("Found A", NamedInterceptor.invoked.contains("A"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("B"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("C"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("D"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("E"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("F"));
      assertFalse("Didn't find NotBound", NamedInterceptor.invoked.contains("NotBound"));

      NamedInterceptor.invoked.clear();
      pojo.method(1, "5", 6, "X");
      assertTrue("Found A", NamedInterceptor.invoked.contains("A"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("B"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("C"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("D"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("E"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("F"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("G"));
      assertTrue("Found A", NamedInterceptor.invoked.contains("H"));
      assertFalse("Didn't find NotBound", NamedInterceptor.invoked.contains("NotBound"));
   }
   
   public void testInstanceAdvisorGetInstance()
   {
      POJO pojo1 = new POJO();
      POJO pojo2 = new POJO();
      
      InstanceAdvisor ia1 = ((InstanceAdvised)pojo1)._getInstanceAdvisor();
      assertNotNull(ia1);
      InstanceAdvisor ia2 = ((InstanceAdvised)pojo2)._getInstanceAdvisor();
      assertNotNull(ia2);
      
      Object instance1 = ia1.getInstance();
      assertNotNull(instance1);
      assertSame(pojo1, instance1);
      Object instance2 = ia2.getInstance();
      assertNotNull(instance2);
      assertSame(pojo2, instance2);
   }
   // Inner classes -------------------------------------------------
}

