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
 * Plain old java object used on @Thrown parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ThrownPOJO
{
   // as a convention, the number of the thrown exception should never be 0, so we
   // can identify when the number hasn't been set in ThrownAspect, and when it has
   
   public void method1(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method2(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method3(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method4(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method5(int i) throws POJOException
   {
      throw new POJOException(i);
   }
   
   public void method6() throws POJOException { }
   
   public void method7() throws POJOException
   {
      throw new POJOException(6);
   }
   
   public void method8() throws POJOException
   {
      throw new RuntimeException();
   }
   
   public void method9()
   {
      throw new RuntimeException();
   }
   
   @SuppressWarnings("all")
   public void method10()
   {
      Object nullObject = null;
      System.out.println(nullObject.toString());
   }
}