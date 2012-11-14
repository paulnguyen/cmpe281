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

enum CallType {CONSTRUCTOR, METHOD, STATIC_METHOD}

/**
 * Plain old java object used on both @Target and @Caller parameter tests.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class TargetCallerPOJO
{
   public TargetCallerPOJO(){}
   
   public TargetCallerPOJO(int x){}
   
   public TargetCallerPOJO(int x, String y) throws POJOException
   {
      throw new POJOException();
   }
   
   // test constructor calls
   public TargetCallerPOJO(CallType callType, boolean throwException) throws POJOException
   {
      switch(callType)
      {
         case CONSTRUCTOR:
            if (throwException)
            {
               new TargetCallerPOJO2(0, null);
            }
            new TargetCallerPOJO2(5);
            break;
         case METHOD:
            TargetCallerPOJO2 pojo2 = new TargetCallerPOJO2();
            if (throwException)
            {
               pojo2.method3_();
            }
            pojo2.method1_();
            break;
         case STATIC_METHOD:
            if (throwException)
            {
               TargetCallerPOJO2.method4_();
            }
            TargetCallerPOJO2.method2_();
            break;
      }
   }
   
   public int field1;
   public static int field2;
   
   public void method1(){}
   public static void method2(){}
   
   public void method3() throws POJOException
   {
      throw new POJOException();
   }
   
   public static void method4() throws POJOException
   {
      throw new POJOException();
   }
      
   public void method5()
   {
      new TargetCallerPOJO2(3);
   }
   
   public void method6() throws POJOException
   {
      new TargetCallerPOJO2(3, "method6");
   }
   
   public void method7()
   {
      TargetCallerPOJO2 pojo2 = new TargetCallerPOJO2();
      pojo2.method1_();
   }
   
   public void method8()
   {
      TargetCallerPOJO2.method2_();
   }
   
   public void method9() throws POJOException
   {
      TargetCallerPOJO2 pojo2 = new TargetCallerPOJO2();
      pojo2.method3_();
   }
   
   public void method10() throws POJOException
   {
      TargetCallerPOJO2.method4_();
   }
   
   public static void method11(){
      new TargetCallerPOJO2(3);
   }
   
   public static void method12()
   {
      TargetCallerPOJO2 pojo2 = new TargetCallerPOJO2();
      pojo2.method1_();
   }
   
   public static void method13()
   {
      TargetCallerPOJO2.method2_();
   }
   
   public static void method14() throws POJOException
   {
      TargetCallerPOJO2 pojo2 = new TargetCallerPOJO2();
      pojo2.method3_();
   }
   
   public static void method15() throws POJOException
   {
      TargetCallerPOJO2.method4_();
   }
}

class TargetCallerPOJO2 extends TargetCallerPOJO
{
   public TargetCallerPOJO2(){}
   
   public TargetCallerPOJO2(int x){}
   
   public TargetCallerPOJO2(int x, String y) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method1_(){}
   public static void method2_(){}
   public void method3_() throws POJOException
   {
      throw new POJOException();
   }
   public static void method4_() throws POJOException
   {
      throw new POJOException();
   }
}