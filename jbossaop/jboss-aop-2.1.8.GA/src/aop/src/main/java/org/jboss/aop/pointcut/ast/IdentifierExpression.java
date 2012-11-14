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
 * @version $Revision: 38812 $
 *
 **/
public class IdentifierExpression
{
   private String original;
   private Pattern namePattern;
   private boolean isAnnotation;
   
   private boolean isImplements;
   private boolean isImplementing;
   private ClassExpression implementsExpr;

   public IdentifierExpression(String expr)
   {
      original = expr;
      if (expr.startsWith("@"))
      {
         isAnnotation = true;
      }
      else if (expr.startsWith("$implements{"))
      {
         isImplements = true;
         expr = expr.substring(12, expr.length() - 1); 
         implementsExpr = new ClassExpression(expr.trim());
      }
      else if (expr.startsWith("$implementing{"))
      {
         isImplementing = true;
         expr = expr.substring(14, expr.length() - 1); 
         implementsExpr = new ClassExpression(expr.trim());
      }
      else
      {
         expr = expr.replaceAll("\\*", ".*");
         namePattern = Pattern.compile(expr);
      }
   }

   public boolean matches(String name)
   {
      if (isAnnotation) return false;
      Matcher m = namePattern.matcher(name);
      return m.matches();
   }

   public boolean matchesAnnotation(String annotation)
   {
      if (!isAnnotation) return false;
      Matcher m = namePattern.matcher(annotation);
      return m.matches();
   }

   public String getOriginal()
   {
      return original;
   }

   public boolean isAnnotation()
   {
      return this.isAnnotation;
   }

   public boolean isImplements()
   {
      return this.isImplements;
   }
   
   public boolean isImplementing()
   {
      return this.isImplementing;
   }
   
   public ClassExpression getImplementsExpression()
   {
      return implementsExpr;
   }
}
