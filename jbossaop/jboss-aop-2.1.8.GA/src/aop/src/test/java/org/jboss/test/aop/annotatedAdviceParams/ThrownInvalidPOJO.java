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
 * Plain old java object used on @Thrown parameter tests with invalid advices (its
 * joinpoint executions will throw a NoMatchingAdviceException, due to
 * the fact that there is no advice that matches the joinpoint).
 * <br>
 * This class is a "copy" of {@link ThrownPOJO} class. Each joinpoint of the original
 * class is duplicated here to be matched by each invalid advice, in order to assert
 * the invalidity of advices in an individual manner.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see ThrownInvalidPOJOTest
 */
public class ThrownInvalidPOJO
{
   /* method1(int) */
   
   public void method1Throwing1(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   /* method3(int) */
   
   public void method3Throwing3(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method3Finally3(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   /* method4(int) */
   
   public void method4Throwing4(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   /* method5(int) */
   
   public void method5Throwing5(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   /* method7() */
   
   public void method7Throwing8() throws POJOException
   {
      throw new POJOException(6);
   }
}