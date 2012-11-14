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

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

import org.jboss.aop.AspectManager;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistToReflect
{
   public static String getParameterClassArrayString(String arrayName, CtBehavior behavior)throws NotFoundException
   {
      CtClass[] params = behavior.getParameterTypes();
      return getParameterClassArrayString(params, arrayName);
   }
   
   public static String getParameterClassArrayString(CtClass[] params, String arrayName) throws NotFoundException
   {
      StringBuffer arr = new StringBuffer(" new java.lang.Class[" + params.length + "];");
      
      for (int i = 0 ; i < params.length ; i++)
      {
         arr.append(arrayName + "["+ i + "] = ");
         
         if (params[i].isArray())
         {
            arr.append(getClassObjectString(params[i], true));
         }
         else
         {
            arr.append(getClassObjectString(params[i]));
         }
         arr.append(";");
      }
      return arr.toString();
   }
   
   public static String getClassObjectString(CtClass clazz)
   {
      //Cavaj crashes when trying to use bytecode using stuff like
      //   POJO.class
      //so get hold of the class from its name here
      return getClassObjectString(clazz, false);
   }
   
   public static String getClassObjectString(CtClass clazz, boolean isArray)
   {
      //Cavaj crashes when trying to use bytecode using stuff like
      //   POJO.class
      //so get hold of the class from its name here if we are in debug mode
      
      if (!isArray)
      {
         if (clazz.isPrimitive())
         {
            return getClassName(clazz);
         }
         else
         {
            return "java.lang.Class.forName(\"" + getClassName(clazz) + "\")";
         }
      }
      else
      {
         //Array initialisers are not supported
         if (AspectManager.debugClasses)
         {
            return "new " + getArrayClassName(clazz) + "[0].getClass()";
         }
         else
         {
            return getArrayClassName(clazz) + "[].class";
         }
      }
   }
   
   public static String getArrayClassName(CtClass clazz)
   {
         String name = clazz.getName();
         int ind = name.indexOf("[");
         return name.substring(0, ind);
   }

   public static String getClassName(CtClass clazz)
   {
      if (!clazz.isPrimitive())
      {
         return clazz.getName(); 
      }
      else
      {
         if (clazz == CtClass.booleanType)    return "java.lang.Boolean.TYPE";
         else if (clazz == CtClass.byteType)  return "java.lang.Byte.TYPE";
         else if (clazz == CtClass.charType)  return "java.lang.Character.TYPE";
         else if (clazz == CtClass.doubleType)return "java.lang.Double.TYPE";
         else if (clazz == CtClass.floatType) return "java.lang.Float.TYPE";
         else if (clazz == CtClass.intType)   return "java.lang.Integer.TYPE";
         else if (clazz == CtClass.longType)  return "java.lang.Long.TYPE";
         else if (clazz == CtClass.shortType) return "java.lang.Short.TYPE";
         else throw new RuntimeException("Unsupported type " + clazz.getName());
      }
   }
   
   public static String castInvocationValueToTypeString(CtClass type)
   {
      return castInvocationValueToTypeString(type, "value");  
   }

   public static String castInvocationValueToTypeString(CtClass type, String valueName)
   {
      String cast = null;
      if (type.isPrimitive())
      {
         if (type.equals(CtClass.booleanType))
         {
            cast = "((Boolean)" + valueName +").booleanValue()";
         }
         else if (type.equals(CtClass.byteType))
         {
            cast = "((Byte)" + valueName +").byteValue()";
         }
         else if (type.equals(CtClass.charType))
         {
            cast = "((Character)" + valueName +").charValue()";
         }
         else if (type.equals(CtClass.doubleType))
         {
            cast = "((Double)" + valueName +").doubleValue()";
         }
         else if (type.equals(CtClass.floatType))
         {
            cast = "((Float)" + valueName +").floatValue()";
         }
         else if (type.equals(CtClass.intType))
         {
            cast = "((Integer)" + valueName +").intValue()";
         }
         else if (type.equals(CtClass.longType))
         {
            cast = "((Long)" + valueName +").longValue()";
         }
         else if (type.equals(CtClass.shortType))
         {
            cast = "((Short)" + valueName +").shortValue()";
         }
      }
      else if (type.isArray())
      {
         cast = "(" + type.getName() + ")" + valueName;
      }
      else
      {
         cast = "(" + type.getName() + ")" + valueName;
      }

      return cast;
   }
   
}
