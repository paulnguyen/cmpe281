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
package org.jboss.test.aop.arguments;


/**
 * Plain old java object used by get/setArguments tests.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class ArgumentsPOJO1
{
   int arg = 0;
   
   public ArgumentsPOJO1() {}
   
   public ArgumentsPOJO1(int arg)
   {
      this.arg = arg;
   }
   
   public ArgumentsPOJO1(Call call, int arg)
   {
      this.arg = call.perform(arg);
   }
   
   public ArgumentsPOJO1(Call call, long arg)
   {
      this.arg = call.perform((int)arg);
   }
   
   public int method(int arg)
   {
      return arg * 3;
   }
   
   public int staticMethod(int arg)
   {
      return arg + 20;
   }
   
   public CalledPOJO createPOJO(int arg)
   {
      return new CalledPOJO(arg);
   }
   
   public int callPOJO(int arg)
   {
      CalledPOJO pojo = new CalledPOJO();
      return pojo.method(arg);
   }
   
   public int callPOJOStatic(int arg)
   {
      return CalledPOJO.staticMethod(arg);
   }
   
   public int callPOJOStatic(long arg)
   {
      return CalledPOJO.staticMethod(arg);
   }
}

/**
 * Plain old java object used by get/setArguments tests.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class ArgumentsPOJO2
{
   int arg = 0;
   
   public ArgumentsPOJO2() {}
   
   public ArgumentsPOJO2(int arg)
   {
      this.arg = arg;
   }
   
   public ArgumentsPOJO2(Call call, int arg)
   {
      this.arg = call.perform(arg);
   }
   
   public int method(int arg)
   {
      return arg * 3;
   }
   
   public int staticMethod(int arg)
   {
      return arg + 20;
   }
   
   public CalledPOJO createPOJO(int arg)
   {
      return new CalledPOJO(arg);
   }
   
   public int callPOJO(int arg)
   {
      CalledPOJO pojo = new CalledPOJO();
      return pojo.method(arg);
   }
   
   public int callPOJOStatic(int arg)
   {
      return CalledPOJO.staticMethod(arg);
   }
   
   public int callPOJOStatic(long arg)
   {
      return CalledPOJO.staticMethod(arg);
   }
   
}

enum Call
{
   CONSTRUCTOR
   {
      public int perform(int arg)
      {
         CalledPOJO pojo = new CalledPOJO(arg);
         return pojo.arg;
      }
   },
   
   METHOD
   {
      public int perform(int arg)
      {
         CalledPOJO pojo = new CalledPOJO();
         return pojo.method(arg);
      }
   },
   
   STATIC_METHOD
   {
      public int perform(int arg)
      {
         return CalledPOJO.staticMethod(arg);
      }
   },
   
   STATIC_METHOD_2
   {
      public int perform(int arg)
      {
         return CalledPOJO.staticMethod((long) arg);
      }
   };

   public abstract int perform(int arg);
}

/**
 * Plain old java object used by get/setArguments tests.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class CalledPOJO
{
   int arg = 0;
 
   CalledPOJO() {}
   
   CalledPOJO(int arg)
   {
      this.arg = arg;
   }
   
   public int method(int arg)
   {
      return arg * 7;
   }
   
   public static int staticMethod(int arg)
   {
      return arg - 1;
   }
   
   public static int staticMethod(long arg)
   {
      return (int) arg - 1;
   }
}