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

import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * Aspect class used by get/setArguments tests.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ArgumentsAspect
{
   public static Object[] arguments1 = null;
   public static Object[] arguments2 = null;
   
   public static final void clear()
   {
      arguments1 = null;
      arguments2 = null;
   }
   
   public Object getAndChangeArgumentsAdvice(Invocation invocation) throws Throwable
   {
      Object[] arguments = getArguments(invocation);
      if (arguments != null && arguments.length == 1)
      {
         if (arguments[0] instanceof Integer)
         {
            arguments[0] = Integer.valueOf(((Integer) arguments[0]).intValue() /2);
         }
         else if (arguments[0] instanceof Long)
         {
            arguments[0] = Long.valueOf(((Long) arguments[0]).intValue() /2);
         }
      }
      return invocation.invokeNext();
   }
   
   public Object getArgumentsAdvice1(Invocation invocation) throws Throwable
   {
      Object[] arguments = getArguments(invocation);
      if (arguments != null && arguments.length == 1)
      {
         arguments1 = arguments;
      }
      return invocation.invokeNext();
   }
   
   public Object getArgumentsAdvice2(Invocation invocation) throws Throwable
   {
      Object[] arguments = getArguments(invocation);
      if (arguments != null && arguments.length == 1)
      {
         arguments2 = arguments;
      }
      return invocation.invokeNext();
   }
   
   public Object setArgumentsAdvice(Invocation invocation) throws Throwable
   {
      Object[] oldArguments = getArguments(invocation);
      Object[] arguments = new Object[] {Integer.valueOf(37)};
      if (oldArguments != null && oldArguments.length == 1 &&
            oldArguments[0] instanceof Long)
      {
         arguments[0] = Long.valueOf(37);
      }
      if (invocation instanceof ConstructorInvocation &&
            // avoid constructors that receive an intance of Call enum
            ((ConstructorInvocation) invocation).getArguments().length == 1)
      {
         ((ConstructorInvocation) invocation).setArguments(arguments);
      }
      else if (invocation instanceof MethodInvocation)
      {
         ((MethodInvocation) invocation).setArguments(arguments);
      }
      else if (invocation instanceof ConstructorCalledByConstructorInvocation &&
            // avoid constructors that receive an intance of Call enum
            ((ConstructorCalledByConstructorInvocation) invocation).getArguments().length == 1)
      {
         ((ConstructorCalledByConstructorInvocation) invocation).setArguments(arguments);
      }
      else if (invocation instanceof ConstructorCalledByMethodInvocation &&
            // avoid constructors that receive an intance of Call enum
            ((ConstructorCalledByMethodInvocation) invocation).getArguments().length == 1)
      {
         ((ConstructorCalledByMethodInvocation) invocation).setArguments(arguments);
      }
      else if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         ((MethodCalledByConstructorInvocation) invocation).setArguments(arguments);
      }
      else if (invocation instanceof MethodCalledByMethodInvocation)
      {
         ((MethodCalledByMethodInvocation) invocation).setArguments(arguments);
      }
      return invocation.invokeNext();
   }
   
   private Object[] getArguments(Invocation invocation)
   {
      if (invocation instanceof ConstructorInvocation)
      {
         return ((ConstructorInvocation) invocation).getArguments();
      }
      if (invocation instanceof MethodInvocation)
      {
         return ((MethodInvocation) invocation).getArguments();
      }
      if (invocation instanceof ConstructorCalledByConstructorInvocation)
      {
         return ((ConstructorCalledByConstructorInvocation) invocation).getArguments();
      }
      if (invocation instanceof ConstructorCalledByMethodInvocation)
      {
         return ((ConstructorCalledByMethodInvocation) invocation).getArguments();
      }
      if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         return ((MethodCalledByConstructorInvocation) invocation).getArguments();
      }
      if (invocation instanceof MethodCalledByMethodInvocation)
      {
         return ((MethodCalledByMethodInvocation) invocation).getArguments();
      }
      return null;
   }
}