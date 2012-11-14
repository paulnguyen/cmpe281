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

import java.util.ArrayList;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
public class JavassistUtils
{
   public static CtClass[] EMPTY_CTCLASS_ARRAY = new CtClass[0];
   
   public static CtMethod[] getMethodsWithName(CtClass clazz, String name)
   {
      CtMethod[] methods = clazz.getMethods();
      return getMethodsWithName(methods, name);
   }
   
   public static CtMethod[] getDeclaredMethodsWithName(CtClass clazz, String name)
   {
      CtMethod[] methods = clazz.getDeclaredMethods();
      return getMethodsWithName(methods, name);
   }
   
   private static CtMethod[] getMethodsWithName(CtMethod[] methods, String name)
   {
      ArrayList<CtMethod> foundMethods = new ArrayList<CtMethod>();
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (methods[i].getName().equals(name))
         {
            foundMethods.add(methods[i]);
         }
      }
      return foundMethods.toArray(new CtMethod[foundMethods.size()]);
   }
   
   public static boolean isSubclassOrImplements(CtClass clazz, CtClass lookingFor) throws NotFoundException
   {
      if (clazz == null) return false;
      if (clazz.equals(lookingFor)) return true;
      if (clazz.getName().equals("java.lang.Object")) return false;
      
      if (clazz.isPrimitive()) return false;
      
      CtClass[] interfaces = clazz.getInterfaces();
      for (int i = 0 ; i < interfaces.length ; i++)
      {
         if (isSubclassOrImplements(interfaces[i], lookingFor)) return true;
      }
      
      if (isSubclassOrImplements(clazz.getSuperclass(), lookingFor)) return true;
      
      return false;
   }
   
   public static String getNullOrZeroInitialiser(CtClass clazz)
   {
      if (clazz.isPrimitive())
      {
         if (clazz.equals(CtClass.booleanType)) return "false";
         else if (clazz.equals(CtClass.charType)) return "'\\0'";
         else if (clazz.equals(CtClass.byteType)) return "(byte)0";
         else if (clazz.equals(CtClass.shortType)) return "(short)0";
         else if (clazz.equals(CtClass.intType)) return "(int)0";
         else if (clazz.equals(CtClass.longType)) return "0L";
         else if (clazz.equals(CtClass.floatType)) return "0.0f";
         else if (clazz.equals(CtClass.doubleType)) return "0.0d";
      }
      else
      {
         return "null";
      }
      return null;
   }
}
