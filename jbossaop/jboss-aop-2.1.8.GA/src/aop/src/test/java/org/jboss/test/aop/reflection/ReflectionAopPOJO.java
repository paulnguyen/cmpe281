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
package org.jboss.test.aop.reflection;

/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */

public class ReflectionAopPOJO extends ReflectionAopRootPOJO
{
   private int i;
   public int j;
   public int k;

   public ReflectionAopPOJO()
   {
      if (1 == 0)
         System.out.println(i);
      System.out.println("ReflectionAopPOJO default constructor");
   }

   public ReflectionAopPOJO(int j)
   {
      System.out.println("ReflectionAopPOJO constructor(int)");
      this.j = j;
   }

   public ReflectionAopPOJO(String s)
   {
      System.out.println("ReflectionAopPOJO constructor(string)");
   }

   public ReflectionAopPOJO(boolean b)
   {
      System.out.println("ReflectionAopPOJO constructor(boolean)");
   }
   
   public ReflectionAopPOJO(long l)
   {
      System.out.println("ReflectionAopPOJO constructor(long)");
   }
   
   public void method()
   {
      System.out.println("ReflectionAopPOJO method()");

   }

   public void method(int i)
   {
      System.out.println("ReflectionAopPOJO method(int)");
   }
   
   public void method(boolean b)
   {
      System.out.println("ReflectionAopPOJO method(boolean) ");
   }

   public void otherMethod(long l)
   {
      System.out.println("otherMethod");
   }
   
   //Overridden method
   public void method(int i, long l)
   {
      System.out.println("ReflectionAopPOJO method(int, long)");
      if (1 == 0)
         privateMethod(); // Just to avoid compiler warnings
   }

   private void privateMethod()
   {
      System.out.println("ReflectionAopPOJO privateMethod()");
   }

   public class AopPOJOInner
   {
   }
}
