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
import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class Interceptions
{
   private static ArrayList interceptions = new ArrayList();
   
   public static void printInterceptions()
   {
      System.out.println(interceptions);
   }
   
   public static void clear()
   {
      interceptions.clear();
   }
   
   public static boolean isEmpty()
   {
      return interceptions.isEmpty();
   }
   
   public static int size()
   {
      return interceptions.size();
   }
   
   public static String get(int index)
   {
      return (String)interceptions.get(index);
   }
   
   public static void addConstructorInterception(Class aspect, Constructor c)
   {
      interceptions.add(getConstructorName(getClassName(aspect), c));
   }

   public static void addConstructionInterception(Class aspect, Constructor c)
   {
      interceptions.add(getConstructionName(getClassName(aspect), c));
   }
   
   public static void addMethodInterception(Class aspect, Method m)
   {
      interceptions.add(getMethodName(getClassName(aspect), m));
   }
   
   public static void addFieldReadInterception(Class aspect, Field f)
   {
      interceptions.add(getFieldReadName(getClassName(aspect), f));
   }
   
   public static void addFieldWriteInterception(Class aspect, Field f)
   {
      interceptions.add(getFieldWriteName(getClassName(aspect), f));
   }

   public static String getConstructorName(String aspect, String clazz)
   {
      return aspect + " : " + clazz + ".new";
   }
   
   public static String getConstructionName(String aspect, String clazz)
   {
      return aspect + " : " + clazz + ".new...";
   }
   
   public static String getMethodName(String aspect, String clazz, String m)
   {
      return aspect + " : " + clazz + "." + m;
   }
   
   public static String getFieldReadName(String aspect, String clazz, String f)
   {
      return aspect + " : " + clazz + ".r_" + f;
   }
   
   public static String getFieldWriteName(String aspect, String clazz, String f)
   {
      return aspect + " : " + clazz + ".w_" + f;
   }
   
   private static String getConstructorName(String aspect, Constructor con)
   {
      return getConstructorName(aspect, getClassName(con));
   }
   
   private static String getConstructionName(String aspect, Constructor con)
   {
      return getConstructionName(aspect, getClassName(con));
   }
   
   private static String getMethodName(String aspect, Method m)
   {
      return getMethodName(aspect, getClassName(m), m.getName());
   }
   
   private static String getFieldReadName(String aspect, Field f)
   {
      return getFieldReadName(aspect, getClassName(f), f.getName());
   }
   
   private static String getFieldWriteName(String aspect, Field f)
   {
      return getFieldWriteName(aspect, getClassName(f), f.getName());
   }

   private static String getClassName(Constructor c)
   {
      return getClassName(c.getDeclaringClass().getName());
   }
   
   private static String getClassName(Method m)
   {
      return getClassName(m.getDeclaringClass().getName());
   }
   
   private static String getClassName(Field f)
   {
      return getClassName(f.getDeclaringClass().getName());
   }
   
   private static String getClassName(Class clazz)
   {
      return getClassName(clazz.getName());
   }
   
   private static String getClassName(String s)
   {
      return s.substring(s.lastIndexOf('.') + 1);
   }
   
   public static String getToString()
   {
      return interceptions.toString();
   }
}
