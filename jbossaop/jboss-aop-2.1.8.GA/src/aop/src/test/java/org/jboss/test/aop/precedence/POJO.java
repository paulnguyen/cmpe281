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
package org.jboss.test.aop.precedence;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 88400 $
 */
public class POJO
{
   public int var;
   
   public POJO()
   {
      System.out.println("*** POJO constructor");
   }
   
   public POJO(String arg)
   {
      System.out.println("*** POJO called constructor");
   }
   
   public static POJO factoryMethod()
   {
      return new POJO("factory");
   }
   
   public void oneMethod()
   {
      System.out.println("*** POJO oneMethod");
   }
   
   public void twoMethod()
   {
      System.out.println("*** POJO twoMethod");
   }
   
   public void threeMethod()
   {
      System.out.println("*** POJO threeMethod");
   }
   
   public void fourMethod()
   {
      System.out.println("*** POJO fourMethod");
      this.fiveMethod();
   }
   
   public void fiveMethod()
   {
      System.out.println("*** POJO fiveMethod");
   }
   
   public void sixMethod()
   {
      System.out.println("*** POJO sixMethod");
   }
   
   public void sevenMethod()
   {
      System.out.println("*** POJO sevenMethod");
   }
}