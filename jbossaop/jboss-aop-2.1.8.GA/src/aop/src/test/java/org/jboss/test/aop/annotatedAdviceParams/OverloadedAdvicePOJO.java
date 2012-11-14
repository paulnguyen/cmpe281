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
 * Plain old java object used on overloaded advice tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings({"cast"})
public class OverloadedAdvicePOJO
{
   public String text;
   
   public OverloadedAdvicePOJO() {}
   
   public OverloadedAdvicePOJO(SubInterface arg1, Implementor arg2) throws POJOException
   {
      throw new POJOException();
   }
   
   public OverloadedAdvicePOJO(Interface arg1, Implementor arg2, int arg3) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method1(int arg1, long arg2) {}
   
   public SuperClass method2(float arg1, SubValue arg2)
   {
      return null;
   }
   
   public long method3(int arg)
   {
      return (long) arg;
   }
   
   public void method4(boolean arg) throws POJOException
   {
      throw new POJOException();
   }
   
   public String method5(int arg1, long arg2) throws POJOException
   {
      return "method5";
   }
}