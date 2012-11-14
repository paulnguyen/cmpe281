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
package org.jboss.test.aop.reflection;

import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * @author <a href="mailto:bill@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class SimplePerVmInterceptor implements org.jboss.aop.advice.Interceptor
{
   public static int constructorIntercepted;
   public static int fieldReadIntercepted;
   public static int fieldWriteIntercepted;
   public static int methodIntercepted;
   
   public String getName()
   {
      return "SimplePerVmInterceptor";
   }

   public Object invoke(org.jboss.aop.joinpoint.Invocation invocation) throws Throwable
   {
      System.out.println("SimplePerVmInterceptor interception: " + invocation.getClass().getName());
      if (invocation instanceof MethodInvocation)
      {
         methodIntercepted++;
         System.out.println("methodIntercepted: " + methodIntercepted);
      }
      else if (invocation instanceof FieldReadInvocation)
      {
         fieldReadIntercepted++;
         System.out.println("fieldReadIntercepted: " + fieldReadIntercepted);
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         fieldWriteIntercepted++;
         System.out.println("fieldWriteIntercepted: " + fieldWriteIntercepted);
      }
      else if (invocation instanceof ConstructorInvocation)
      {
         constructorIntercepted++;
         System.out.println("constructorIntercepted:" + constructorIntercepted);
      }
      return invocation.invokeNext();
   }
   
   public static boolean hasIntercepted()
   {
      return (constructorIntercepted > 0 || fieldReadIntercepted > 0 || fieldWriteIntercepted > 0 ||methodIntercepted> 0);
   }
   
   public static void reset()
   {
      constructorIntercepted = 0;
      fieldReadIntercepted = 0;
      fieldWriteIntercepted = 0;
      methodIntercepted = 0;
   }
}

