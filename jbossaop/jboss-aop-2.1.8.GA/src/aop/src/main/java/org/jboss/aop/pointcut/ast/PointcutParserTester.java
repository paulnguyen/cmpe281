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

import java.io.StringReader;

public class PointcutParserTester implements PointcutExpressionParserVisitor
{
   private int indent = 0;

   private String indentString()
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < indent; ++i)
      {
         sb.append(" ");
      }
      return sb.toString();
   }



   public Object visit(SimpleNode node, Object data)
   {
      System.out.println(indentString() + node +
              ": acceptor not unimplemented in subclass?");
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTStart node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTHas node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTHasField node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTExecutionOnly node, Object data)
   {
      return null;
   }

   public Object visit(ASTBoolean node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTField node, Object data)
   {
      System.out.print(indentString());
      if (node.attributes != null)
      {
         for (int i = 0; i < node.attributes.size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.attributes.get(i);
            System.out.print(attr.attribute + " ");
         }
      }
      System.out.print(node.getTypeExpression() + " ");
      System.out.println(node.getClassExpr() + "." + node.getFieldExpr());
      return data;
   }

   public Object visit(ASTMethod node, Object data)
   {
      System.out.print(indentString());
      System.out.print(node.getReturnTypeExpression() + " ");
      if (node.attributes != null)
      {
         for (int i = 0; i < node.attributes.size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.attributes.get(i);
            System.out.print(attr.attribute + " ");
         }
      }
      System.out.print(node.getClassExpr() + "." + node.getMethodExpr() + "(");
      if (node.parameters != null && !node.isAnyParameters())
      {
         for (int i = 0; i < node.parameters.size(); i++)
         {
            if (i > 0) System.out.print(", ");
            System.out.print(node.parameters.get(i));
         }
      }
      else if (node.isAnyParameters())
      {
         System.out.print("..");
      }
      System.out.println(")");
      return data;
   }

   public Object visit(ASTConstructor node, Object data)
   {
      System.out.print(indentString());
      if (node.attributes != null)
      {
         for (int i = 0; i < node.attributes.size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.attributes.get(i);
            System.out.print(attr.attribute + " ");
         }
      }
      System.out.print(node.getClassExpr() + "(");
      if (node.parameters != null && !node.isAnyParameters())
      {
         for (int i = 0; i < node.parameters.size(); i++)
         {
            if (i > 0) System.out.print(", ");
            System.out.print(node.parameters.get(i));
         }
      }
      else if (node.isAnyParameters())
      {
         System.out.print("..");
      }

      System.out.println(")");
      return data;
   }

   public Object visit(ASTParameter node, Object data)
   {
      return null;
   }

   public Object visit(ASTComposite node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTCFlowExpression node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }
   public Object visit(ASTCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTSub node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTAnd node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTOr node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTNot node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTNotCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTCFlowBoolean node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTCompositeCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTOrCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTAndCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTSubCFlow node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTAll node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      System.out.println(indentString() + node.getClasseExpression());
      --indent;
      return data;
   }

   public Object visit(ASTFieldExecution node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTCall node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.getBehavior().jjtAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTExecution node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTConstruction node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTGet node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTSet node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTPointcut node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTWithin node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }

   public Object visit(ASTWithincode node, Object data)
   {
      System.out.println(indentString() + node);
      ++indent;
      data = node.childrenAccept(this, data);
      --indent;
      return data;
   }
   public Object visit(ASTAttribute node, Object data)
   {
      return null;
   }

   public Object visit(ASTException node, Object data)
   {
      return null;
   }

   public Object visit(ASTAllParameter node, Object data)
   {
      return null;
   }

   public static void main(String args[])
   {
      args = new String[1];
      args[0] = "execution(* $instanceof{com.blah.Test}->$implements{@IF}(..) throws Exception)";
      System.out.println("----" + args[0]);
      StringReader reader = new StringReader(args[0]);
      PointcutExpressionParser t = new PointcutExpressionParser(reader);
      //System.out.println("Reading from stdin");
      //PointcutExpressionParser t = new PointcutExpressionParser(System.in);
      try
      {
         ASTStart n = t.Start();
         PointcutExpressionParserVisitor v = new PointcutParserTester();
         n.jjtAccept(v, null);
      }
      catch (Exception e)
      {
         System.out.println("Oops.");
         System.out.println(e.getMessage());
         e.printStackTrace();
      }
   }
}

/*end*/
