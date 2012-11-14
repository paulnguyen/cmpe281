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
   public static void main(String[] args)
   {
      System.out.println("--- new POJO(); ---");
      POJO pojo = new POJO();

      System.out.println("--- pojo.field1 = 10; ---");
      pojo.field1 = 10;
      System.out.println("--- get pojo.field1;---");
      int i = pojo.field1;
      System.out.println("--- pojo.method(); ---");
      pojo.method();

      System.out.println("\r\n=======================\r\n");

      System.out.println("--- new POJO2(); ---");
      POJO2 pojo2 = new POJO2();

      System.out.println("--- pojo2.field1 = 10; ---");
      pojo2.field1 = 10;
      System.out.println("--- get pojo2.field1; ---");
      i = pojo2.field1;
      System.out.println("--- pojo2.method(); ---");
      pojo2.method();

      System.out.println("\r\n=======================\r\n");

      System.out.println("--- new POJO3(); ---");
      POJO3 pojo3 = new POJO3();

      System.out.println("--- pojo3.field1 = 10; ---");
      pojo3.field1 = 10;
      System.out.println("--- get pojo3.field1; ---");
      i = pojo3.field1;
      System.out.println("--- pojo3.method(); ---");
      pojo3.method();
   }
}
