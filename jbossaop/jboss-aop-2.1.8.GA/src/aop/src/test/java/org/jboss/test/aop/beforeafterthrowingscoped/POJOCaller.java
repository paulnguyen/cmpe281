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
package org.jboss.test.aop.beforeafterthrowingscoped;

/**
 * Represents a call to be performed by the caller.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
enum CallAction
{
   CALL_CONSTRUCTOR("constructor"),
   CALL_METHOD("method"),
   CALL_STATIC_METHOD("staticmethod");
   
   private String name;
   
   CallAction(String name)
   {
      this.name = name;
   }
   
   public String toString()
   {
      return this.name;
   }
};

/**
 * Super class of caller (for call joinpoints)
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class SuperPOJOCaller
{
   // constructor that is not intercepted
   public SuperPOJOCaller() {}
   
   public SuperPOJOCaller(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new SuperTargetPOJO(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            SuperTargetPOJO.staticMethod(throwException);
            break;
      }
   }
   
   public void method(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new SuperTargetPOJO(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            SuperTargetPOJO.staticMethod(throwException);
            break;
      }
   }
   
   public static void staticMethod(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new SuperTargetPOJO(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            SuperTargetPOJO.staticMethod(throwException);
            break;
      }
   }
}

/**
 * Caller1 (for call joinpoints)
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class POJOCaller1 extends SuperPOJOCaller
{
   // constructor that is not intercepted
   public POJOCaller1() {
      super();
   }
   
   public POJOCaller1(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      // perform caller1 own calls (ignore super constructor calls)
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new TargetPOJO1(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            TargetPOJO1.staticMethod(throwException);
            break;
      }
   }
   
   public static void staticMethod1(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new TargetPOJO1(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            TargetPOJO1.staticMethod(throwException);
            break;
      }
   }
   
   public void method1(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new TargetPOJO1(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            TargetPOJO1.staticMethod(throwException);
            break;
      }
   }
}

/**
 * Caller2 (for call joinpoints)
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class POJOCaller2 extends SuperPOJOCaller
{
   // constructor that is not intercepted
   public POJOCaller2()
   {
      super();
   }
   
   public POJOCaller2(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      // call super target constructor this time
      super(target, callAction, throwException);
   }
   
   public void method2(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new TargetPOJO2(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            TargetPOJO2.staticMethod(throwException);
            break;
      }
   }
   
   public static void staticMethod2(SuperTargetPOJO target, CallAction callAction, boolean throwException) throws ThrownByTestException
   {
      switch(callAction)
      {
         case CALL_CONSTRUCTOR:
            new TargetPOJO2(throwException);
            break;
         case CALL_METHOD:
            target.method(throwException);
            break;
         case CALL_STATIC_METHOD:
            TargetPOJO2.staticMethod(throwException);
            break;
      }
   }
}