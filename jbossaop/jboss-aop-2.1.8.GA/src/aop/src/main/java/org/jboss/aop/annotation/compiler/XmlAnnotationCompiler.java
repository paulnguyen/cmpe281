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

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Convert doclet tags to JBoss AOP annotation XML
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class XmlAnnotationCompiler
{
   public static void main(String[] args) throws Exception
   {
      XmlAnnotationCompiler c = new XmlAnnotationCompiler();
      c.compile(args);
   }

   public void usage()
   {
      System.err.println("Usage: annotationc <files>+");
   }

   public static void indenter(PrintWriter pw, int indent)
   {
      for (int i = 0; i < indent * 3; i++) pw.print(" ");
   }


   public void compile(String[] args) throws Exception
   {
      if (args.length == 0)
      {
         usage();
         System.exit(1);
         return;
      }
      String outputFile = "metadata-aop.xml";
      JavaDocBuilder builder = new JavaDocBuilder(new AnnotationDocletTagFactory());
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals("-o"))
         {
            outputFile = args[++i];
            continue;
         }
         else if (args[i].equals("-xml"))
         {
            continue;
         }
         File f = new File(args[i]).getCanonicalFile();
         builder.addSource(new FileReader(f));
      }

      FileOutputStream os = new FileOutputStream(outputFile);
      PrintWriter pw = new PrintWriter(os);
      pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      pw.println("<aop>");
      for (int i = 0; i < builder.getSources().length; i++)
      {
         JavaSource src = builder.getSources()[i];
         for (int j = 0; j < src.getClasses().length; j++)
         {
            JavaClass clazz = src.getClasses()[j];
            compileClass(clazz, pw);
         }
      }
      pw.println("</aop>");
      pw.close();
      os.close();
   }

   private void compileClass(JavaClass clazz, PrintWriter pw) throws Exception
   {
      int indent = 1;
      for (int i = 0; i < clazz.getTags().length; i++)
      {
         AnnotationDocletTag tag = (AnnotationDocletTag) clazz.getTags()[i];
         if (tag.getAnnotation() == null) continue;

         indenter(pw, indent);
         pw.println("<metadata tag=\"" + tag.getName() + "\" class=\"" + clazz.getFullyQualifiedName() + "\">");
         indent++;
         indenter(pw, indent);
         pw.println("<class>");
         indent++;
         XmlAnnotationVisitor visitor = new XmlAnnotationVisitor(indent, pw);
         if (tag.getAnnotation().jjtGetNumChildren() > 0) tag.getAnnotation().jjtGetChild(0).jjtAccept(visitor, null);
         indent--;
         indenter(pw, indent);
         pw.println("</class>");
         indent--;
         indenter(pw, indent);
         pw.println("</metadata>");
      }
      for (int i = 0; i < clazz.getMethods().length; i++)
      {
         JavaMethod method = clazz.getMethods()[i];
         for (int j = 0; j < method.getTags().length; j++)
         {
            AnnotationDocletTag tag = (AnnotationDocletTag) method.getTags()[j];
            if (tag.getAnnotation() == null) continue;
            indenter(pw, indent);
            pw.println("<metadata tag=\"" + tag.getName() + "\" class=\"" + clazz.getFullyQualifiedName() + "\">");
            indent++;
            if (method.isConstructor())
            {
               indent = printConstructor(pw, method, indent, tag);
            }
            else
            {
               indent = printMethod(pw, method, indent, tag);
            }
            indent--;
            indenter(pw, indent);
            pw.println("</metadata>");
         }
      }
      for (int i = 0; i < clazz.getFields().length; i++)
      {
         JavaField field = clazz.getFields()[i];
         for (int j = 0; j < field.getTags().length; j++)
         {
            AnnotationDocletTag tag = (AnnotationDocletTag) field.getTags()[j];
            if (tag.getAnnotation() == null) continue;
            indenter(pw, indent);
            pw.println("<metadata tag=\"" + tag.getName() + "\" class=\"" + clazz.getFullyQualifiedName() + "\">");
            indent++;
            printField(pw, field, indent, tag);
            indent--;
            indenter(pw, indent);
            pw.println("</metadata>");
         }
      }


   }

   private int printMethod(PrintWriter pw, JavaMethod method, int indent, AnnotationDocletTag tag) throws Exception
   {
      indenter(pw, indent);
      pw.print("<method expr=\"");
      pw.print(method.getReturns().toString());
      pw.print(" " + method.getName() + "(");
      boolean first = true;
      for (int k = 0; k < method.getParameters().length; k++)
      {
         JavaParameter param = method.getParameters()[k];
         if (!first)
            pw.print(", ");
         else
            first = false;
         pw.print(param.getType().toString());
      }
      pw.println(")\">");
      indent++;
      XmlAnnotationVisitor visitor = new XmlAnnotationVisitor(indent, pw);
      if (tag.getAnnotation().jjtGetNumChildren() > 0) tag.getAnnotation().jjtGetChild(0).jjtAccept(visitor, null);
      indent--;
      indenter(pw, indent);
      pw.println("</method>");
      return indent;
   }

   private int printField(PrintWriter pw, JavaField field, int indent, AnnotationDocletTag tag) throws Exception
   {
      indenter(pw, indent);
      pw.println("<field name=\"" + field.getName() + "\">");
      indent++;
      XmlAnnotationVisitor visitor = new XmlAnnotationVisitor(indent, pw);
      if (tag.getAnnotation().jjtGetNumChildren() > 0) tag.getAnnotation().jjtGetChild(0).jjtAccept(visitor, null);
      indent--;
      indenter(pw, indent);
      pw.println("</field>");
      return indent;
   }

   private int printConstructor(PrintWriter pw, JavaMethod method, int indent, AnnotationDocletTag tag) throws Exception
   {
      indenter(pw, indent);
      pw.print("<constructor expr=\"");
      pw.print(method.getName() + "(");
      boolean first = true;
      for (int k = 0; k < method.getParameters().length; k++)
      {
         JavaParameter param = method.getParameters()[k];
         if (!first)
            pw.print(", ");
         else
            first = false;
         pw.print(param.getType().toString());
      }
      pw.println(")\">");
      indent++;
      XmlAnnotationVisitor visitor = new XmlAnnotationVisitor(indent, pw);
      if (tag.getAnnotation().jjtGetNumChildren() > 0) tag.getAnnotation().jjtGetChild(0).jjtAccept(visitor, null);
      indent--;
      indenter(pw, indent);
      pw.println("</constructor>");
      return indent;
   }
}
