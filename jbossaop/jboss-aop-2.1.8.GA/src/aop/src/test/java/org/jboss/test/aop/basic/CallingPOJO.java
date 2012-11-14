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

import org.jboss.aop.Advised;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class CallingPOJO
{
   POJO pojo;
   NonadvisedPOJO nonpojo;
   PrivateProtectedPOJO privateProtectedPojo;

   public CallingPOJO()
   {
      CallerInterceptor.called = false;
      pojo = new POJO();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("constructor caller interceptor didn't work from within constructor");
      }
      CallerInterceptor.called = false;
      pojo.someMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }

      CallerInterceptor.called = false;
      POJO.staticMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }

      CallerInterceptor.called = false;
      nonpojo = new NonadvisedPOJO("helloworld");
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("constructor caller interceptor didn't work");
      }
      CallerInterceptor.called = false;
      nonpojo.remoteTest();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }
      if (nonpojo instanceof Advised)
      {
         throw new RuntimeException("nonpojo is Advised when it shouldn't be");
      }
      
      //Call twice to make sure method names have been cached properly
      privateProtectedPojo = new PrivateProtectedPOJO();
      CallerInterceptor.called = false;
      privateProtectedPojo = new PrivateProtectedPOJO();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected constructor caller interceptor didn't work");
      }
      
      //Call twice to make sure method names have been cached properly
      privateProtectedPojo.protectedMethod();
      CallerInterceptor.called = false;
      privateProtectedPojo.protectedMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected method caller interceptor didn't work");
      }

      CallerInterceptor.called = false;
      PrivateProtectedPOJO.protectedStaticMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected static method caller interceptor didn't work");
      }

      CallerInterceptor.called = false ;
      final InterfacePojo interfacePojo = new InterfacePojo() ;
      final SuperInterface superInterface = interfacePojo ;
      superInterface.superInterfaceMethod() ;
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("interface method via interface caller interceptor didn't work") ;
      }

      CallerInterceptor.called = false ;
      final SubInterface subInterface = interfacePojo ;
      subInterface.superInterfaceMethod() ;
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("interface method via sub interface caller interceptor didn't work") ;
      }
   }

   /**
    * This method should be a caller pointcut
    */
   public void callSomeMethod()
   {
      CallerInterceptor.called = false;
      pojo = new POJO();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("constructor caller interceptor didn't work within method");
      }
      CallerInterceptor.called = false;
      POJO.staticMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }
      CallerInterceptor.called = false;
      pojo.someMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }

      //Call twice to make sure method names have been cached properly
      privateProtectedPojo = new PrivateProtectedPOJO();
      CallerInterceptor.called = false;
      privateProtectedPojo = new PrivateProtectedPOJO();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected constructor caller interceptor didn't work");
      }

      //Call twice to make sure method names have been cached properly
      privateProtectedPojo.protectedMethod();
      CallerInterceptor.called = false;
      privateProtectedPojo.protectedMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected method caller interceptor didn't work");
      }

      CallerInterceptor.called = false;
      PrivateProtectedPOJO.callProtectedStaticMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected static method caller interceptor didn't work");
      }

      CallerInterceptor.called = false;
      privateProtectedPojo.callProtectedMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected method caller interceptor (2) didn't work");
      }

      CallerInterceptor.called = false;
      privateProtectedPojo.callPrivateMethod();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("protected method caller interceptor (2) didn't work");
      }

      CallerInterceptor.called = false;
      privateProtectedPojo = new PrivateProtectedPOJO("s", 1);//calls private constructor
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("private constructor caller interceptor from constructor didn't work");
      }

      CallerInterceptor.called = false;
      PrivateProtectedPOJO.callPrivateConstructor();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("private constructor caller interceptor from method didn't work");
      }

      CallerInterceptor.called = false ;
      final InterfacePojo interfacePojo = new InterfacePojo() ;
      final SuperInterface superInterface = interfacePojo ;
      superInterface.superInterfaceMethod() ;
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("interface method via interface caller interceptor didn't work") ;
      }

      CallerInterceptor.called = false ;
      final SubInterface subInterface = interfacePojo ;
      subInterface.superInterfaceMethod() ;
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("interface method via sub interface caller interceptor didn't work") ;
      }
   }

   /**
    * This method should not be a caller pointcut
    */
   public void nocallSomeMethod()
   {
      CallerInterceptor.called = false;
      pojo = new POJO();
      if (CallerInterceptor.called)
      {
         throw new RuntimeException("constructor caller interceptor didn't work, interceptor was invoked when it shouldn't have been");
      }
      pojo.someMethod();
      if (CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work, caller interceptor was invoked when it shouldn't have been");
      }
   }

   public void callUnadvised()
   {
      CallerInterceptor.called = false;
      nonpojo = new NonadvisedPOJO("helloworld");
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("consturctor caller interceptor didn't work");
      }
      CallerInterceptor.called = false;
      nonpojo.remoteTest();
      if (!CallerInterceptor.called)
      {
         throw new RuntimeException("caller interceptor didn't work");
      }
      if (nonpojo instanceof Advised)
      {
         throw new RuntimeException("nonpojo is Advised when it shouldn't be");
      }
   }
   
   public void callUnadvisedWithPointcutException()
   {
      try
      {
         CallerInterceptor.called = false;
         nonpojo = new NonadvisedPOJO("helloworld");
         if (!CallerInterceptor.called)
         {
            throw new RuntimeException("caller interceptor didn't work");
         }

         CallerInterceptor.called = false;
         nonpojo.withException("x", 1);
         if (!CallerInterceptor.called)
         {
            throw new RuntimeException("caller interceptor didn't work");
         }
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

}

