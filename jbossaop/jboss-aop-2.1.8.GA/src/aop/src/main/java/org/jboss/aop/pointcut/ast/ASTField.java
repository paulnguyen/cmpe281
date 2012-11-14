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
public class ASTField extends SimpleNode
{
   public ASTField(int id)
   {
      super(id);
   }

   public ASTField(PointcutExpressionParser p, int id)
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

   String typeExpression;
   ClassExpression type;
   String classExpr;
   ClassExpression clazz;
   String fieldExpr;
   IdentifierExpression fieldIdentifier;
   ArrayList attributes = new ArrayList();

   public void jjtAddChild(Node n, int i)
   {
      if (n instanceof ASTAttribute) attributes.add(n);
   }

   public void setTypeExpression(String type)
   {
      this.typeExpression = type;
      this.type = new ClassExpression(type);
   }

   public String getTypeExpression()
   {
      return typeExpression;
   }

   public String getClassExpr()
   {
      return classExpr;
   }

   public String getFieldExpr()
   {
      return fieldExpr;
   }

   public void setClassExpr(String classExpr)
   {
      this.classExpr = classExpr;
      clazz = new ClassExpression(classExpr);
   }

   public void setFieldExpr(String fieldExpr)
   {
      this.fieldExpr = fieldExpr;
      fieldIdentifier = new IdentifierExpression(fieldExpr);
   }

   public ArrayList getAttributes()
   {
      return attributes;
   }

   public ClassExpression getType()
   {
      return type;
   }

   public ClassExpression getClazz()
   {
      return clazz;
   }

   public IdentifierExpression getFieldIdentifier()
   {
      return fieldIdentifier;
   }
}
