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

public class POJO
{
   public Object field;
   
   public POJO(String argument) 
   {
      System.out.println("RUNNING new POJO(\"" + argument + "\")");
   }

   public boolean someMethod(int argument)
   {
      System.out.println("RUNNING POJO->someMethod()");
      return true;
   }
   
   public void callMethod()
   {
      System.out.println("RUNNING POJO->callMethod()");
      this.calledMethod();
   }
   
   public void calledMethod()
   {
      System.out.println("RUNNING POJO->calledMethod()");
   }
   
   public void method(int arg0)
   {
      System.out.println("RUNNING POJO->method(" + arg0 + ")");
   }
   
   public void method(long arg0, int arg1, long arg2, String arg3)
   {
      System.out.println("RUNNING POJO->method(" + arg0 + "L, " + arg1 + ", " +
            arg2 + "L, \"" + arg3 + "\")");
   }
}