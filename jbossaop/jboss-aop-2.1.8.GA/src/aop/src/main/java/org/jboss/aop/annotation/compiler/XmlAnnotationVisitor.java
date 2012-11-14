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
package org.jboss.aop.annotation.compiler;

import org.jboss.annotation.factory.ast.ASTAnnotation;
import org.jboss.annotation.factory.ast.ASTChar;
import org.jboss.annotation.factory.ast.ASTIdentifier;
import org.jboss.annotation.factory.ast.ASTMemberValueArrayInitializer;
import org.jboss.annotation.factory.ast.ASTMemberValuePair;
import org.jboss.annotation.factory.ast.ASTMemberValuePairs;
import org.jboss.annotation.factory.ast.ASTSingleMemberValue;
import org.jboss.annotation.factory.ast.ASTStart;
import org.jboss.annotation.factory.ast.ASTString;
import org.jboss.annotation.factory.ast.AnnotationParserVisitor;
import org.jboss.annotation.factory.ast.SimpleNode;

import java.io.PrintWriter;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 63589 $
 *
 **/
public class XmlAnnotationVisitor implements AnnotationParserVisitor
{
   private int indent;
   PrintWriter pw;

   public XmlAnnotationVisitor(int indent, PrintWriter pw)
   {
      this.indent = indent;
      this.pw = pw;
   }

   public Object visit(ASTMemberValuePairs node, Object data)
   {
      node.childrenAccept(this, data);
      return null;
   }


   public Object visit(ASTMemberValuePair node, Object data)
   {
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.print("<" + node.getIdentifier().getValue() + ">");
      node.getValue().jjtAccept(this, null);
      pw.println("</" + node.getIdentifier().getValue() + ">");
      return null;
   }

   public Object visit(ASTSingleMemberValue node, Object data)
   {
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.print("<value>");
      node.getValue().jjtAccept(this, null);
      pw.println("</value>");
      return null;
   }

   public Object visit(ASTAnnotation node, Object data)
   {
      pw.println("");
      indent++;
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.println("<annotation tag=\"" + node.getIdentifier() + "\">");
      indent++;
      node.childrenAccept(this, data);
      indent--;
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.println("</annotation>");
      indent--;
      XmlAnnotationCompiler.indenter(pw, indent);
      return null;
   }

   public Object visit(ASTMemberValueArrayInitializer node, Object data)
   {
      pw.println("");
      indent++;
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.println("<array>");
      indent++;
      int size = node.jjtGetNumChildren();
      for (int i = 0; i < size; i++)
      {
         XmlAnnotationCompiler.indenter(pw, indent);
         pw.print("<value>");
         node.jjtGetChild(i).jjtAccept(this, null);
         pw.println("</value>");
      }
      indent--;
      XmlAnnotationCompiler.indenter(pw, indent);
      pw.println("</array>");
      indent--;
      XmlAnnotationCompiler.indenter(pw, indent);
      return null;
   }

   public Object visit(ASTIdentifier node, Object data)
   {
      pw.print(node.getValue());
      return null;
   }

   public Object visit(ASTString node, Object data)
   {
      pw.print(node.getValue());
      return null;
   }

   public Object visit(ASTChar node, Object data)
   {
      pw.print(node.getValue());
      return null;
   }
   public Object visit(SimpleNode node, Object data)
   {
      return null;
   }

   public Object visit(ASTStart node, Object data)
   {
      return null;
   }

}
