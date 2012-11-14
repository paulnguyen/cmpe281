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

import javassist.CtConstructor;
import javassist.NotFoundException;

import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70826 $
 *
 **/
public class ConstructorExpression
{
   protected static final Pattern PATTERN = Pattern.compile("(.*)\\((.*)\\)");
   public String originalExpression;
   public ParameterExpression params;
   public Pattern classExpr;
   public String className;

   public ConstructorExpression(String ex)
   {
      Matcher m = PATTERN.matcher(ex);
      if (!m.matches()) throw new IllegalStateException("ConstructorExpression is invalid: " + ex);
      className = m.group(1);
      params = new ParameterExpression(m.group(2));
      className = className.replaceAll("\\.", "\\\\.");
      className = className.replaceAll("\\*", ".*");
      classExpr = Pattern.compile(className);
   }


   public boolean matches(Constructor<?> con)
   {
      Matcher cm = classExpr.matcher(con.getDeclaringClass().getName());
      if (cm.matches() == false) return false;

      return params.matches(con.getParameterTypes());
   }

   public boolean matchParamsOnly(Constructor<?> con)
   {
      return params.matches(con.getParameterTypes());
   }

   public boolean matchParamsOnly(CtConstructor con) throws NotFoundException
   {
      return params.matches(con.getParameterTypes());
   }

   public boolean matches(CtConstructor con) throws NotFoundException
   {
      Matcher cm = classExpr.matcher(con.getDeclaringClass().getName());
      if (cm.matches() == false) return false;

      return params.matches(con.getParameterTypes());
   }
}
