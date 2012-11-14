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
package org.jboss.aop.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
public class ReflectUtils
{
   public static Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
   private static Pattern accessMethodPattern = Pattern.compile("access[$](\\d)+");
   
   public static Method[] getMethodsWithName(Class<?> clazz, String name)
   {
      Method[] methods = clazz.getMethods();
      return getMethodsWithName(methods, name);
   }
   
   public static Method[] getDeclaredMethodsWithName(Class<?> clazz, String name)
   {
      Method[] methods = clazz.getDeclaredMethods();
      return getMethodsWithName(methods, name);
   }
   
   private static Method[] getMethodsWithName(Method[] methods, String name)
   {
      ArrayList<Method> foundMethods = new ArrayList<Method>();
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (methods[i].getName().equals(name))
         {
            foundMethods.add(methods[i]);
         }
      }
      return foundMethods.toArray(new Method[foundMethods.size()]);
   }
   
   /**
    * Java adds a few static void methods called access$0, access$1 etc. when inner classes are used
    *
    * @return false if this method is static, void, has package access and has a name like access$0, access$1 etc.
    */
   public static boolean isNotAccessMethod(Method m)
   {

      //TODO: Normally access methods should return void, but having optimised field invocation
      //it seems that javassist occasionally creates these methods with other return types
      if (Modifier.isStatic(m.getModifiers()))
      {
         Matcher match = accessMethodPattern.matcher(m.getName());
         if (match.matches())
         {
            return false;
         }
      }

      return true;
   }
   
}
