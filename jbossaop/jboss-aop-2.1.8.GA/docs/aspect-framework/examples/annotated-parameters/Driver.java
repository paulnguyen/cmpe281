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
   public static void main(String[] args) throws Exception
   {
      
      System.out.println("\nCalling POJO constructor");
      System.out.println("========================");
      POJO pojo = new POJO("Driver");
      
      System.out.println("\nSetting POJO->field with \"text\" value");
      System.out.println("=====================================");
      pojo.field = "text";
      
      System.out.println("\nReading POJO->field value");
      System.out.println("=========================");
      Object myValue = pojo.field;
      
      System.out.println("\nCalling POJO->method(int)");
      System.out.println("=========================");
      pojo.method(17);
      
      System.out.println("\nCalling POJO->method(long, int, long, String)");
      System.out.println("=============================================");
      pojo.method(20L,2, 1000L, "Driver");
      
      
      System.out.println("\nCalling POJO->someMethod(int)");
      System.out.println("=============================");
      if(pojo.someMethod(10))
      {
         System.out.println("\nCalling POJO->callMethod()");
         System.out.println("==========================");
         pojo.callMethod();
      }
   }
}