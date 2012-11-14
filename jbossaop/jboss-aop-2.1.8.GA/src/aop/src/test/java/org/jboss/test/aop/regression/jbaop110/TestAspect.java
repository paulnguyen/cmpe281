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
package org.jboss.test.aop.regression.jbaop110;

import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;


/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 40295 $
 */
public class TestAspect 
{
   public static boolean constructor;
   public static boolean normalRead;
   public static boolean normalWrite;
   public static boolean staticRead;
   public static boolean staticWrite;
   public static boolean method;
   
   public static void clear()
   {
      constructor = false;
      normalRead = false;
      normalWrite = false;
      staticRead = false;
      staticWrite = false;
      method = false;
   }
   
   public Object invoke(MethodInvocation invocation) throws Throwable
   {
      method = true;
      System.out.println("Invoking method " + invocation.getMethod());
      return invocation.invokeNext();
   }


   public Object invoke(ConstructorInvocation invocation) throws Throwable
   {
      constructor = true;
      System.out.println("Invoking ctor " + invocation.getConstructor());
      return invocation.invokeNext();
   }


   public Object invoke(FieldReadInvocation invocation) throws Throwable
   {
      System.out.println("Invoking field read " + invocation.getField());
      if (invocation.getField().getType().getName().indexOf("NormalType") >= 0)
      {
         normalRead = true;
      }
      else if (invocation.getField().getType().getName().indexOf("StaticType") >= 0)
      {
         staticRead = true;
      }
      return invocation.invokeNext();
   }


   public Object invoke(FieldWriteInvocation invocation) throws Throwable
   {
      System.out.println("Invoking field write " + invocation.getField());
      if (invocation.getField().getType().getName().indexOf("NormalType") >= 0)
      {
         normalWrite = true;
      }
      else if (invocation.getField().getType().getName().indexOf("StaticType") >= 0)
      {
         staticWrite = true;
      }
      return invocation.invokeNext();
   }

}
