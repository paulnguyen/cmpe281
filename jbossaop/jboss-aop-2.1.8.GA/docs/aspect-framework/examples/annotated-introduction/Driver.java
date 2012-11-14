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

import java.io.Serializable;

public class Driver
{
   public static void main(String[] args) throws Exception
   {
      System.out.println("--- POJO ---");
      POJO pojo = new POJO();
      pojo.stuff = "hello world";
      java.rmi.MarshalledObject mo = new java.rmi.MarshalledObject(pojo);
      pojo = (POJO)mo.get();
      System.out.println("deserialized pojo.stuff: " + pojo.stuff);

      System.out.println("--- POJO2 ---");
      POJO2 pojo2 = new POJO2();
      pojo2.stuff2 = "hello world";
      java.rmi.MarshalledObject mo2 = new java.rmi.MarshalledObject(pojo2);
      pojo2 = (POJO2)mo2.get();
      System.out.println("deserialized pojo2.stuff2: " + pojo2.stuff2);

      System.out.println("--- POJO3 ---");
      POJO3 pojo3 = new POJO3();
      Serializable s = (Serializable)pojo3;
      System.out.println("pojo3 introduction expression worked");

      System.out.println("--- POJO4 ---");
      POJO4 pojo4 = new POJO4();
      s = (Serializable)pojo4;
      System.out.println("pojo4 introduction expression worked");
      
   }
}
