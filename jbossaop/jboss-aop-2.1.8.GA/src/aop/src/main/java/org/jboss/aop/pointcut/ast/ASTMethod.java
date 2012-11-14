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
public class ASTMethod extends SimpleNode
{
   public ASTMethod(int id)
   {
      super(id);
   }

   public ASTMethod(PointcutExpressionParser p, int id)
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


   String returnTypeExpr;
   ClassExpression returnType;
   String classExpr;
   ClassExpression clazz;
   String methodExpr;
   IdentifierExpression methodIdentifier;
   boolean anyParameters = false;
   boolean hasAnyZeroOrMoreParameters = false;
   ArrayList parameters = new ArrayList();
   ArrayList attributes = new ArrayList();
   ArrayList exceptions = new ArrayList();

   public void jjtAddChild(Node n, int i)
   {
      if (n instanceof ASTAttribute) attributes.add(n);
      else if (n instanceof ASTException) exceptions.add(n);
      else if (n instanceof ASTAllParameter) anyParameters = true;
      else if (n instanceof ASTParameter && !anyParameters)
      {
         parameters.add(0, n);
         if (!hasAnyZeroOrMoreParameters && ((ASTParameter)n).isAnyZeroOrMoreParameters())
         {
            hasAnyZeroOrMoreParameters = true;
         }
      }
      else super.jjtAddChild(n, i);
   }
   public void setMethodExpression(String expression)
   {
      methodExpr = expression;
      methodIdentifier = new IdentifierExpression(expression);
   }

   public void setReturnTypeExpression(String exp)
   {
      returnTypeExpr = exp;
      returnType = new ClassExpression(exp);
   }

   public void setClassExpression(String exp)
   {
      classExpr = exp;
      clazz = new ClassExpression(exp);
   }

   public String getReturnTypeExpression()
   {
      return returnTypeExpr;
   }

   public String getClassExpr()
   {
      return classExpr;
   }

   public String getMethodExpr()
   {
      return methodExpr;
   }

   public boolean isAnyParameters()
   {
      return anyParameters;
   }

   public boolean hasAnyZeroOrMoreParameters()
   {
      return hasAnyZeroOrMoreParameters;
   }
   
   public ArrayList getParameters()
   {
      return parameters;
   }

   public ArrayList getExceptions()
   {
      return exceptions;
   }

   public ArrayList getAttributes()
   {
      return attributes;
   }

   public ClassExpression getReturnType()
   {
      return returnType;
   }

   public ClassExpression getClazz()
   {
      return clazz;
   }

   public IdentifierExpression getMethodIdentifier()
   {
      return methodIdentifier;
   }

}
