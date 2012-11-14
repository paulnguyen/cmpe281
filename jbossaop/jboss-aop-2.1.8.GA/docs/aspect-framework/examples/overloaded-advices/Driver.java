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

public class Driver
{
   public void runExample()
   {
      System.out.println("\nCalling POJO constructor");
      System.out.println("========================");
      POJO pojo = new POJO();
      
      System.out.println("\nSetting POJO->intField with 1751 value");
      System.out.println("======================================");
      pojo.intField = 1751;
      
      System.out.println("\nReading POJO->intField value");
      System.out.println("============================");
      int myIntValue = pojo.intField;
      
      System.out.println("\nSetting POJO->stringField with \"text\" value");
      System.out.println("===========================================");
      pojo.stringField = "text";
      
      System.out.println("\nReading POJO->stringField value");
      System.out.println("===============================");
      String myStringValue = pojo.stringField;
      
      System.out.println("\nCalling POJO->voidMethod()");
      System.out.println("==========================");
      pojo.voidMethod();
      
      System.out.println("\nCalling POJO->methodWithStringArg()");
      System.out.println("===================================");
      pojo.methodWithStringArg("stringArg");
   }
   
   public static void main(String[] args) throws Exception
   {
      Driver driver = new Driver();
      driver.runExample();
   }
}