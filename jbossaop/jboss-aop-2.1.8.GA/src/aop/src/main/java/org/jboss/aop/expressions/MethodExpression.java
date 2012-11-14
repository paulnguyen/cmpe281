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

import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 *
 **/
public class MethodExpression
{
   protected static final Pattern PATTERN = Pattern.compile("(.*)\\s+(.*)\\((.*)\\)");
   public String originalExpression;
   public ParameterExpression params;
   public Pattern methodNameExpr;
   public String methodName;
   public String returnString;
   public Pattern returnExpr;

   public MethodExpression(String ex)
   {
      originalExpression = ex;
      Matcher m = PATTERN.matcher(ex);
      if (!m.matches()) throw new IllegalStateException("MethodExpression is invalid: " + ex);
      returnString = m.group(1);
      returnString = returnString.replaceAll("\\.", "\\\\.");
      returnString = returnString.replaceAll("\\*", ".*");
      returnString = returnString.replaceAll("\\[", "\\\\[");
      returnString = returnString.replaceAll("\\]", "\\\\]");
      returnExpr = Pattern.compile(returnString);
      methodName = m.group(2);
      methodName = methodName.replaceAll("\\.", "\\\\.");
      methodName = methodName.replaceAll("\\*", ".*");
      methodName = methodName.replaceAll("\\[", "\\\\[");
      methodName = methodName.replaceAll("\\]", "\\\\]");
      methodNameExpr = Pattern.compile(methodName);
      params = new ParameterExpression(m.group(3));
   }


   public boolean matches(Method method)
   {
      String returnType = ParameterExpression.simpleType(method.getReturnType());
      Matcher ret = returnExpr.matcher(returnType);
      if (!ret.matches()) return false;

      Matcher cm = methodNameExpr.matcher(method.getName());
      if (cm.matches() == false) return false;

      return params.matches(method.getParameterTypes());
   }

   public boolean matches(CtMethod method) throws NotFoundException
   {
      String returnType = method.getReturnType().getName();
      Matcher ret = returnExpr.matcher(returnType);
      if (!ret.matches()) return false;

      Matcher cm = methodNameExpr.matcher(method.getName());
      if (cm.matches() == false) return false;

      return params.matches(method.getParameterTypes());
   }
   
   
}
