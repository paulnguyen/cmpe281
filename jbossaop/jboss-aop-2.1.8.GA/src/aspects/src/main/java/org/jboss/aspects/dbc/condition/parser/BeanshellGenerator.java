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
package org.jboss.aspects.dbc.condition.parser;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class BeanshellGenerator implements ExpressionVisitor
{
   Expression expr;
   StringBuffer code;
   int indent;
   //int level;
   
   public BeanshellGenerator(Expression expr)
   {
      this.expr = expr;
   }
   
   public String createBeanshellCode()
   {
      code = new StringBuffer();
      //code.append("boolean " + RET_NAME + " = false;\n");
      expr.accept(this);
      return code.toString();
   }
   
   public void visit(Expression expr)
   {
      expr.accept(this);
   }

   public void visit(BooleanExpression expr)
   {
      if (expr.isTopLevel())
      {
         code.append("return (" + expr.getExpression() + ");\n");
      }
      else
      {
         code.append(expr.getExpression());
      }
   }

   public void visit(ForAllExpression expr)
   {
      createLoopCode(expr, false);
   }

   public void visit(ExistsExpression expr)
   {
      createLoopCode(expr, true);
   }

   public void visit(ImpliesExpression expr)
   {
      indent();
      code.append("if (");
      expr.getLhs().accept(this);
      code.append("){\n");
      indent++;
      indent();
      code.append("if(!(");
      expr.getRhs().accept(this);
      code.append(")){\n");
      indent++;
      indent();
      code.append("return false;\n");
      indent--;
      indent();
      code.append("}\n");
      
      indent--;
      indent();
      code.append("}\n");
      
      if (expr.isTopLevel())
      {
         code.append("return true;\n");         
      }
   }
   
   public void visit(JavaExpression expr)
   {
      code.append(expr.getJava());
      code.append("\nreturn true;\n");         
   }
   
   private void indent()
   {
      for (int i = 0 ; i < indent * 2 ; i++)
      {
         code.append(" ");
      }
   }

   private void createLoopCode(LoopExpression expr, boolean breakType)
   {
      String declaration = expr.getDeclaration();
      String collection = expr.getCollection();
      Expression body = expr.getBody();
      
      indent();
      code.append("for (" + declaration + " : " + collection + "){\n");
      indent++;

      if (body instanceof BooleanExpression)
      {
         indent();
         code.append("if (");
         
         if (!breakType) 
         {
            code.append("!");
         }

         code.append("(");         
       	body.accept(this);
         code.append(")){\n");

         indent++;
         indent();
         code.append("return "+ breakType + ";\n");
         
         indent--;
         indent();         
         code.append("}\n");
      }
      else if (body instanceof ImpliesExpression)
      {
         body.accept(this);
      }
      else
      {
       	body.accept(this);
      }
      indent--;
      indent();
      code.append("}\n");
      if (expr.isTopLevel())
      {
         indent();
         code.append("return "+ !breakType + ";\n");         
      }
   }
}
