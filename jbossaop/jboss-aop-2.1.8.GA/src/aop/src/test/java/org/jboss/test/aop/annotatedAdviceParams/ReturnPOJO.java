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

import java.util.List;

/**
 * Plain old java object used both on @Return parameter tests, and on advice return
 * type tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ReturnPOJO
{
   public String field1 = "field1";
   public String field2 = "field2";
   public String field3 = "field3";
   public String field4 = "field4";
   public String field5 = "field5";
   public String field6 = "field6";
   
   public SubValue field7 = new SubValue(7);
   public SuperValue field9 = new SuperValue(9);
   public SuperValue field10 = new SuperValue(10);
   
   public List<SuperValue> fieldGenerics;
   
   public void method1() {}

   public String method2()
   {
      return "method2";
   }
   
   public String method3()
   {
      return "method3";
   }
   
   public String method4()
   {
      return "method4";
   }
   
   public String method5()
   {
      return "method5";
   }
   
   public String method6()
   {
      return "method6";
   }
   
   public String method7()
   {
      return "method7";
   }
   
   public SubValue method8()
   {
      return new SubValue(8);
   }
   
   public SuperValue method10()
   {
      return new SuperValue(10);
   }
   
   public SuperValue method11()
   {
      return new SuperValue(11);
   }
   
   public SubValue method12() throws POJOException
   {
      throw new POJOException();
   }
   
   public SubValue method13() throws POJOException
   {
      throw new POJOException();
   }
   
   public List<SuperValue> methodGenericsExecution(boolean throwException)
   throws POJOException
   {
      if (throwException)
      {
         throw new POJOException();
      }
      return null;
   }
   
   public List<SuperValue> methodGenericsCall(boolean throwException)
   throws POJOException
   {
      if (throwException)
      {
         throw new POJOException();
      }
      return null;
   }
}