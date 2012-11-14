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
package org.jboss.aop.pointcut.ast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70830 $
 */
public class ClassExpression
{
   private String original;
   private Pattern classPattern;
   private boolean isAnnotation = false;
   private boolean isInstanceOf = false;
   private boolean isTypedef = false;
   private boolean isPackage = false;

   private boolean isInstanceOfAnnotated = false;

   public ClassExpression(String expr)
   {
      original = expr;
      if (expr.startsWith("@"))
      {
         isAnnotation = true;
      }
      else
      {
         expr = original;
         if (expr.startsWith("$instanceof{"))
         {
            isInstanceOf = true;
            expr = expr.substring(12, expr.lastIndexOf("}"));
            isInstanceOfAnnotated = (expr.startsWith("@"));
         }
         else if (expr.startsWith("$typedef{"))
         {
            isTypedef = true;
            expr = expr.substring(9, expr.lastIndexOf("}"));
         }
         else if (expr.endsWith(".."))
         {
            isPackage = true;
            expr = expr.substring(0, expr.lastIndexOf(".."));
         }
         
         if (!isAnnotation)
         {
	         expr = expr.replaceAll("\\.", "\\\\.");
	         expr = expr.replaceAll("\\*", ".*");
	         expr = expr.replaceAll("\\[", "\\\\[");
	         expr = expr.replaceAll("]", "\\\\]");
	         expr = expr.replaceAll("\\$", "\\\\\\$");
	         classPattern = Pattern.compile(expr);
         }
      }

   }

   public boolean isSimple()
   {
      return !(isAnnotation || isInstanceOf || isTypedef || isInstanceOfAnnotated);
   }

   public boolean matches(String classname)
   {
      if (isAnnotation) return false;
      if (isPackage())
      {
         int index = classname.lastIndexOf(".");
         boolean matches = classPattern.toString().equals(".*");
         if (!matches && index != -1)
         {
            String candidate = classname.substring(0, index);
            java.util.regex.Matcher m = classPattern.matcher(candidate);
            matches = m.matches();
         }
         return matches;
      }
      else
      {
         Matcher m = classPattern.matcher(classname);
         return m.matches();
      }
   }

   public boolean isAnnotation()
   {
      return this.isAnnotation;
   }
   
   public boolean isPackage()
   {
      return this.isPackage;
   }

   public boolean isInstanceOf()
   {
      return this.isInstanceOf;
   }

   public boolean isTypedef()
   {
      return this.isTypedef;
   }

   public boolean isInstanceOfAnnotated()
   {
      return this.isInstanceOfAnnotated;
   }

   public String getInstanceOfAnnotation()
   {
      if (!isInstanceOfAnnotated)
      {
         return null;
      }
      return original.substring(12, original.lastIndexOf("}"));
   }

   public String getOriginal()
   {
      return original;
   }

   public static String simpleType(Class<?> type)
   {
      Class<?> ret = type;
      if (ret.isArray())
      {
         Class<?> arr = ret;
         String array = "";
         while (arr.isArray())
         {
            array += "[]";
            arr = arr.getComponentType();
         }
         return arr.getName() + array;
      }
      return ret.getName();
   }
}
