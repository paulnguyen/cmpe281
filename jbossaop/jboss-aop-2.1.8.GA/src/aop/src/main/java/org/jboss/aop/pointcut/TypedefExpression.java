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
package org.jboss.aop.pointcut;

import java.io.StringReader;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;
import javassist.CtClass;


/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */
public class TypedefExpression implements Typedef
{
   protected String name;
   protected String expr;
   protected ASTStart ast;

   public TypedefExpression(String name, String expr) throws ParseException
   {
      this.name = name;
      this.expr = expr;

      ast = new TypeExpressionParser(new StringReader(expr)).Start();
   }

   public String getName()
   {
      return name;
   }

   public String getExpr()
   {
      return expr;
   }

   public boolean matches(Advisor advisor, CtClass clazz)
   {
      TypeMatcher matcher = new TypeMatcher(advisor, clazz);
      return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      TypeMatcher matcher = new TypeMatcher(advisor, clazz);
      return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
   }

}
