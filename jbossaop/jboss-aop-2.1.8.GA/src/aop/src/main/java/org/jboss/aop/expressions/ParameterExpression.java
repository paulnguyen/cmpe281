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
package org.jboss.aop.expressions;

import javassist.CtClass;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70826 $
 *
 **/
public class ParameterExpression
{
   protected static final Pattern ALL;
   protected static final Pattern PARAMS;

   static
   {
      ALL = Pattern.compile("\\s*\\.\\.\\s*");
      PARAMS = Pattern.compile("\\s*(.*)\\s*");
   }


   public String originalExpression;
   public Pattern[] parameterPatterns;
   public boolean isAll = false;

   protected static String simpleType(Class<?> type)
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
   /**
    *
    * @param expression should be (..) or comma delimited (int, .*, java.lang.String)
    */
   public ParameterExpression(String expression)
   {
      originalExpression = expression;
      Matcher all = ALL.matcher(expression);
      isAll = all.matches();
      if (!isAll)
      {
         Matcher params = PARAMS.matcher(expression);
         if (!params.matches())
         {
            throw new RuntimeException("Illegal parameter list expression: " + expression);
         }
         StringTokenizer tokenizer = new StringTokenizer(expression, ",");
         ArrayList<Pattern> paramList = new ArrayList<Pattern>();
         while (tokenizer.hasMoreTokens())
         {
            String token = tokenizer.nextToken().trim();
            token = token.replaceAll("\\.", "\\\\.");
            token = token.replaceAll("\\*", ".*");
            token = token.replaceAll("\\[", "\\\\[");
            token = token.replaceAll("\\]", "\\\\]");
            paramList.add(Pattern.compile(token));
         }
         parameterPatterns = paramList.toArray(new Pattern[paramList.size()]);
      }
   }

   public boolean matches(Class<?>[] params)
   {
      if (isAll)
      {
         return true;
      }

      if (params.length != parameterPatterns.length) return false;

      for (int i = 0; i < params.length; i++)
      {
         String asString = simpleType(params[i]);
         Matcher m = parameterPatterns[i].matcher(asString);
         if (!m.matches()) return false;
      }
      return true;
   }

   public boolean matches(CtClass[] params)
   {
      if (isAll) return true;

      if (params.length != parameterPatterns.length) return false;

      for (int i = 0; i < params.length; i++)
      {
         String asString = params[i].getName();
         Matcher m = parameterPatterns[i].matcher(asString);
         if (!m.matches()) return false;
      }
      return true;
   }
}
