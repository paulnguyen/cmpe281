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
 * Target super class of call joinpoints.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class SuperTargetPOJO
{
   // constructor whose call is not intercepted
   public SuperTargetPOJO() {}

   public SuperTargetPOJO(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
   
   public static void staticMethod(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
   
   public void method(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   } 
}

/**
 * Target 1 (used on call joinpoints).
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class TargetPOJO1 extends SuperTargetPOJO
{
   // constructor whose call is not intercepted
   public TargetPOJO1() {}
   
   public TargetPOJO1(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
   
   public static void staticMethod1(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
}

/**
 * Target 2 (used on call joinpoints).
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class TargetPOJO2 extends SuperTargetPOJO
{
   // constructor whose call is not intercepted
   public TargetPOJO2() {}
   
   public TargetPOJO2(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
   
   public static void staticMethod2(boolean throwException) throws ThrownByTestException
   {
      if (throwException)
      {
         throw new ThrownByTestException();
      }
   }
}