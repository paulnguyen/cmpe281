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
package org.jboss.test.aop.dynamicgenadvisor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.ConstructionInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

@SuppressWarnings({"unused", "unchecked"})
public class YourInterceptor implements Interceptor
{

   public String getName()
   {
      return "YourInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      System.out.println("YourInterceptor.invoke()");
      if (invocation instanceof ConstructorInvocation)
      {
         ConstructorInvocation ci = (ConstructorInvocation)invocation;
         Interceptions.addConstructorInterception(this.getClass(), ci.getConstructor());
      }
      if (invocation instanceof ConstructionInvocation)
      {
         ConstructionInvocation ci = (ConstructionInvocation)invocation;
         Interceptions.addConstructionInterception(this.getClass(), ci.getConstructor());
      }
      else if (invocation instanceof MethodInvocation)
      {
         MethodInvocation mi = (MethodInvocation)invocation;
         Interceptions.addMethodInterception(this.getClass(), mi.getMethod());
      }
      else if (invocation instanceof FieldReadInvocation)
      {
         FieldInvocation fi = (FieldReadInvocation)invocation;
         Interceptions.addFieldReadInterception(this.getClass(), fi.getField());
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         FieldWriteInvocation fi = (FieldWriteInvocation)invocation;
         Interceptions.addFieldWriteInterception(this.getClass(), fi.getField());
      }
      
      return invocation.invokeNext();
   }
   
   private String getClassName(Constructor c)
   {
      return getClassName(c.getDeclaringClass().getName());
   }
   
   private String getClassName(Method m)
   {
      return getClassName(m.getDeclaringClass().getName());
   }
   
   private String getClassName(Field f)
   {
      return getClassName(f.getDeclaringClass().getName());
   }
   
   private String getClassName(String s)
   {
      return s.substring(s.lastIndexOf('.') + 1);
   }

}
