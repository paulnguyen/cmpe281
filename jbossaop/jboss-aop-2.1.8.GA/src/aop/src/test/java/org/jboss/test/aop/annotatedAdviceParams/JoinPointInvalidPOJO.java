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
package org.jboss.test.aop.annotatedAdviceParams;

/**
 * Plain old java object used on @JoinPoint parameter tests with invalid advices(its
 * joinpoint executions will throw a NoMatchingAdviceException, due to the fact that
 * there is no advice that matches the joinpoint).
 * <br>
 * This class is a "copy" of {@link ArgsPOJO} class. Each joinpoint of the original
 * class is duplicated here to be matched by each invalid advice, in order to assert
 * the invalidity of advices in an individual manner.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see JoinPointInvalidTestCase
 */
public class JoinPointInvalidPOJO
{
   /* int number */
   public int numberBefore9;
   
   /* String text */
   public String textAfter2;
   public String textAfter8;
   public String textBefore5;
   
   // constructor without advices
   public JoinPointInvalidPOJO() {}
   
   // called constructor
   public JoinPointInvalidPOJO(boolean shouldThrow) throws POJOException
   {
      if (shouldThrow)
      {
         throw new POJOException();
      }
   }
   
   // constructor call method
   
   public JoinPointInvalidPOJO(boolean arg0, boolean shouldThrow) throws POJOException
   {
      this.calledMethodThrowing9(shouldThrow);
   }
   
   public JoinPointInvalidPOJO(long arg0, boolean shouldThrow) throws POJOException
   {
      this.calledMethodThrowing10(shouldThrow);
   }
   
   /* method1() */
   
   public void method1Finally9(){}
   
   /* method3() */
   
   public void method3Throwing2() throws POJOException
   {
      throw new POJOException();
   }
   
   /* method5() */
   
   public void method5Around() throws POJOException
   {
      throw new POJOException();
   }
   
   public void method5Finally4() throws POJOException
   {
      throw new POJOException();
   }
   
   /* callConstructor(boolean) */
   
   public void callConstructorThrowing9(boolean shouldThrow) throws POJOException
   {
      new JoinPointInvalidPOJO(shouldThrow);
   }
   
   public void callConstructorThrowing10(boolean shouldThrow) throws POJOException
   {
      new JoinPointInvalidPOJO(shouldThrow);
   }
   
   /* staticCallConstructor(boolean) */
   
   public static void staticCallConstructorThrowing9(boolean shouldThrow) throws POJOException
   {
      new JoinPointInvalidPOJO(shouldThrow);
   }
   
   public static void staticCallConstructorThrowing10(boolean shouldThrow) throws POJOException
   {
      new JoinPointInvalidPOJO(shouldThrow);
   }
   
   /* calledMethod(boolean) */
   
   public void calledMethodThrowing9(boolean shouldThrow) throws POJOException
   {
      if (shouldThrow)
      {
         throw new POJOException();
      }
   }
   
   public void calledMethodThrowing10(boolean shouldThrow) throws POJOException
   {
      if (shouldThrow)
      {
         throw new POJOException();
      }
   }
}