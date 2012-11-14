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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;

import org.jboss.aop.ClassAdvisor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 46049 $
 */
public class Advisable
{
   /**
    * Is the method advisable?
    */
   public static boolean isAdvisable(CtMethod method)
   {
      return isAdvisableMethod(method.getModifiers(), method.getName());
   }

   public static boolean isAdvisable(Method method)
   {
      return isAdvisableMethod(method.getModifiers(), method.getName());
   }

   public static boolean isAdvisableMethod(int modifiers, String name)
   {
      return
      (modifiers & Modifier.ABSTRACT) == 0 &&
      (modifiers & Modifier.NATIVE) == 0 &&
      !name.endsWith(ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX) &&
      !name.equals("_getAdvisor") &&
      !name.equals("_getClassAdvisor") &&
      !name.equals("_getInstanceAdvisor") &&
      !name.equals("_setInstanceAdvisor") &&
      !name.equals("class$") &&
      !name.equals("");
   }
   /**
    * Is the field advisable?
    */
   public static boolean isAdvisable(CtField field)
   {
      return isAdvisableField(field.getModifiers(), field.getName());
   }

   public static boolean isAdvisable(Field field)
   {
      return isAdvisableField(field.getModifiers(), field.getName());
   }

   public static boolean isAdvisableField(int modifiers, String name)
   {
      return (modifiers & Modifier.FINAL) == 0 &&
      (!name.endsWith(ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX)) &&
      !name.equals("_instanceAdvisor") &&
      !name.endsWith("$aop$mixin") &&
      (name.indexOf('$') == -1);
   }
}
