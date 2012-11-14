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

import java.util.Iterator;
import java.util.List;

public class Driver
{
   public static void main(String[] args) throws Exception
   {
      POJO pojo = new POJO();
      
      System.out.println("\nCalling POJO->getIntValue()");
      System.out.println("===========================");
      System.out.println("RECEIVED type:int value:" + pojo.getIntValue());;
      
      System.out.println("\nCalling POJO->getList()");
      System.out.println("=======================");
      List list = pojo.getList();
      System.out.println("RECEIVED type:" + list.getClass() + " value:" + list); 
 
      System.out.println("\nCalling POJO->doNothing()");
      System.out.println("=========================");
      pojo.doNothing();
      System.out.println("RECEIVED nothing");
   }
}