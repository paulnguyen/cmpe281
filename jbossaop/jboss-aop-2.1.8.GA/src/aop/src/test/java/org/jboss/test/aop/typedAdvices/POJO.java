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
package org.jboss.test.aop.typedAdvices;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 65215 $
 */
public class POJO
{
   static boolean joinPointRun = false;
   int i;
   
   SubValue subValue;
   SuperValue superValue;
   
   public POJO()
   {
      joinPointRun = true;
   }
   
   public POJO(boolean error, int i, long l, String s) throws TestException
   {
      joinPointRun = true;
      if (error) throw new TestException();
   }
   
   public POJO(SuperValue superValue, int i)
   {
      joinPointRun = true;
   }

   public int method1(boolean error)throws TestException
   {
      joinPointRun = true;
      if (error) throw new TestException();
      
      return 1;
   }
   
   public int method2(boolean error)throws TestException
   {
      joinPointRun = true;
      if (error) throw new TestException();
      
      return 1;
   }
   
   public int method(boolean error, int i, long l, String s) throws TestException
   {
      joinPointRun = true;
      if (error) throw new TestException();
      
      return i;
   }
   
   public POJO method(boolean error, int i, long l, String s, int i2) throws TestException
   {
      joinPointRun = true;
      if (error) throw new TestException();
      
      return this;
   }
   
   public SuperValue method(SubValue sup, SubValue sub)
   {
      joinPointRun = true;
      return sub;
   }
   
   public void method(SuperValue sup, SubValue sub)
   {
      joinPointRun = true;
   }
   
   public SubValue method(SubValue subValue, int i)
   {
      joinPointRun = true;
      return subValue;
   }
   
   public SuperValue method(SuperValue superValue, int i)
   {
      joinPointRun = true;
      return superValue;
   }
}