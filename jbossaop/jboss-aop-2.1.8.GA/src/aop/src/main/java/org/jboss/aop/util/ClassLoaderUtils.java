/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javassist.CtClass;
import javassist.bytecode.Descriptor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoaderUtils
{
   private static final Set<String> primitiveNames;
   static
   {
      Set<String> temp = new HashSet<String>();
      temp.add(CtClass.booleanType.getName());
      temp.add(CtClass.byteType.getName());
      temp.add(CtClass.charType.getName());
      temp.add(CtClass.doubleType.getName());
      temp.add(CtClass.floatType.getName());
      temp.add(CtClass.intType.getName());
      temp.add(CtClass.longType.getName());
      temp.add(CtClass.shortType.getName());
      temp.add(CtClass.voidType.getName());
      primitiveNames = Collections.unmodifiableSet(temp); 
   }
   
   public static String getResourceName(String classname)
   {
      final String name = stripArrayFromClassName(classname);
      final int lastIndex = name.lastIndexOf('$');
      if (lastIndex < 0)
      {
         return name.replaceAll("[\\.]", "/") + ".class";
      }
      else
      {
         return name.substring(0, lastIndex).replaceAll("[\\.]", "/") + name.substring(lastIndex) + ".class";
      }
   }

   public static String getPackageName(String classname)
   {
      final int last = classname.lastIndexOf('.');
      if (last < 0)
      {
         return "";
      }
      else
      {
         return classname.substring(0, last);
      }
   }
   
   public static String stripArrayFromClassName(String classname)
   {
      if (classname.charAt(0) == '[')
      {
         return Descriptor.toClassName(classname);
      }
      if (classname.endsWith("[]"))
      {
         return classname.substring(0, classname.indexOf("[]"));
      }
      return classname;
   }
   
   public static boolean isPrimitiveType(String classname)
   {
      final String name = stripArrayFromClassName(classname);
      return primitiveNames.contains(name);
   }
}
