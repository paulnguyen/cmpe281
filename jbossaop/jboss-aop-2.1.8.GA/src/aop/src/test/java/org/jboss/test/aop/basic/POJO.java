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

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class POJO
{

   public int notMatchedField = 10; 
   private int privateField = 5;
   protected int protectedField = 5;
   public static int staticField = 5;
   private String remoteValue;
   private boolean foo;
   private String[] arrayTest;

   public POJO()
   {
      if (1 == 0)
         System.out.println(foo + " " + arrayTest);
   }


   public POJO(String s)
   {
      System.out.println("POJO Constructor: " + s);
      remoteValue = s;
   }
   
   public POJO(int i) throws SomeException, ClassCastException
   {
      System.out.println("POJO Constructor: " + i);
   }

   public void someMethod()
   {
      System.out.println("POJO.someMethod");
   }

   public void anotherMethod()
   {
      System.out.println("POJO.anotherMethod");
   }

   public static void staticMethod()
   {
      System.out.println("POJO.staticMethod");
   }

   public void accessField()
   {
      privateField += 1;
      System.out.println("privateField = " + privateField);
   }

   public void accessStaticField()
   {
      staticField += 1;
      System.out.println("staticField = " + staticField);
   }

   public String remoteTest()
   {
      return remoteValue;
   }

   public void throwException() throws SomeException
   {
      throw new SomeException();
   }
   
   public void withSomeException() throws SomeException
   {
      System.out.println("POJO.withSomeException");
   }

   public void withExceptionAndOthers(String s) throws SomeException, NotSerializableException
   {
      System.out.println("POJO.withExceptionAndOthers");
   }
   
   public void withExceptionAndOthers(int i) throws SomeException, NotSerializableException
   {
      System.out.println("POJO.withExceptionAndOthers");
   }
   
   public void withClassCastException(int i) throws SomeException, NotSerializableException, ClassCastException
   {
      System.out.println("POJO.withClassCastException");
   }
   
}

