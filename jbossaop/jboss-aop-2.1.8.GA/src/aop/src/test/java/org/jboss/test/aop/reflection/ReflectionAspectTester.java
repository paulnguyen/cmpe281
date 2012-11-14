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

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.reflection.ReflectionAspect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */

@SuppressWarnings("unchecked")
public class ReflectionAspectTester extends ReflectionAspect
{

   public static Constructor constructor;
   public static Field field;
   public static Object[] args;
   public static Object retObj;

   public static void clear()
   {
      ReflectionAspectTester.constructor = null;
      ReflectionAspectTester.field = null;
      ReflectionAspectTester.args = null;
      ReflectionAspectTester.retObj = null;
   }

   protected Object interceptConstructor(Invocation invocation,
                                         Constructor constructor,
                                         Object[] args)
           throws Throwable
   {

      clear();
      ReflectionAspectTester.constructor = constructor;
      ReflectionAspectTester.args = args;

      ReflectionAspectTester.retObj = super.interceptConstructor(invocation, constructor, args);
      return ReflectionAspectTester.retObj;
   }

   protected Object interceptFieldRead(Invocation invocation,
                                       Field field,
                                       Object instance)
           throws Throwable
   {

      clear();
      ReflectionAspectTester.field = field;
      ReflectionAspectTester.retObj = super.interceptFieldRead(invocation, field, instance);
      return ReflectionAspectTester.retObj;
   }

   protected Object interceptFieldWrite(Invocation invocation,
                                        Field field,
                                        Object instance,
                                        Object arg)
           throws Throwable
   {

      clear();
      ReflectionAspectTester.field = field;
      ReflectionAspectTester.args = new Object[]{arg};
      ReflectionAspectTester.retObj = super.interceptFieldWrite(invocation, field, instance, arg);
      return ReflectionAspectTester.retObj;
   }
}
