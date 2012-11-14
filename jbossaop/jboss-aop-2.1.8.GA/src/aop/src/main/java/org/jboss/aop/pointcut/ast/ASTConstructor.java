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

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ASTConstructor extends SimpleNode
{
   public ASTConstructor(int id)
   {
      super(id);
   }

   public ASTConstructor(PointcutExpressionParser p, int id)
   {
      super(p, id);
   }


   /** Accept the visitor. **/
   public Object jjtAccept(PointcutExpressionParserVisitor visitor, Object data)
   {
      return visitor.visit(this, data);
   }
   /** Accept the visitor. **/
   public Object jjtAccept(TypeExpressionParserVisitor visitor, Object data)
   {
      return visitor.visit(this, data);
   }

   String classExpr;
   ClassExpression clazz;
   IdentifierExpression annotation;
   ArrayList attributes = new ArrayList();
   ArrayList parameters = new ArrayList();
   boolean anyParameters = false;
   boolean hasAnyZeroOrMoreParameters = false;
   ArrayList exceptions = new ArrayList();

   public void jjtAddChild(Node n, int i)
   {
      if (n instanceof ASTAttribute) attributes.add(n);
      else if (n instanceof ASTException) exceptions.add(n);
      else if (n instanceof ASTAllParameter) anyParameters = true;
      else if (n instanceof ASTParameter && !anyParameters) 
      {
         parameters.add(0,n);
         if (!hasAnyZeroOrMoreParameters && ((ASTParameter)n).isAnyZeroOrMoreParameters())
         {
            hasAnyZeroOrMoreParameters = true;
         }
      }
      else super.jjtAddChild(n, i);
   }
   public void setClassExpression(String expression)
   {
      classExpr = expression;
      clazz = new ClassExpression(classExpr);
   }

   public void setNewExpression(String expr)
   {
      if (expr.startsWith("@")) annotation = new IdentifierExpression(expr);
   }

   public String getClassExpr()
   {
      return classExpr;
   }

   public ArrayList getAttributes()
   {
      return attributes;
   }
   
   public ArrayList getExceptions()
   {
      return exceptions;
   }

   public ArrayList getParameters()
   {
      return parameters;
   }

   public boolean isAnyParameters()
   {
      return anyParameters;
   }

   public boolean hasAnyZeroOrMoreParameters()
   {
      return hasAnyZeroOrMoreParameters;
   }
   
   public ClassExpression getClazz()
   {
      return clazz;
   }

   /**
    *
    * @return NULL if a constructor specific annotation was not defined
    */
   public IdentifierExpression getConstructorAnnotation()
   {
      return annotation;
   }
}
