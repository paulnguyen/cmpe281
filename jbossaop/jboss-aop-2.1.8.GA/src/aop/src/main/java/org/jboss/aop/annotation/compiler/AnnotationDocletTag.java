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

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;

import org.jboss.annotation.factory.ast.ASTAnnotation;
import org.jboss.annotation.factory.ast.ASTStart;
import org.jboss.annotation.factory.ast.AnnotationParser;
import org.jboss.annotation.factory.ast.ParseException;

import java.io.StringReader;
import java.util.Map;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 *
 **/
public class AnnotationDocletTag implements DocletTag
{
   private static final long serialVersionUID = 1L;
   
   private String name;
   private final String value;
   private final int lineNumber;
   private ASTAnnotation ast;

   private AbstractJavaEntity owner;

   public AnnotationDocletTag(String name, String value, int lineNumber)
   {
      this.name = name;
      this.value = value;
      this.lineNumber = lineNumber;
      if (name.startsWith("@"))
      {
         if (name.indexOf('(') != -1)
         {
            throw new RuntimeException("illegal annotation syntax for doclet at line number " + lineNumber + ".  You should have a space after the tag name otherwise the compiler messes up. " + name + " ***value=" + value);
            /* Can't do this because there may be a space in a string @annotation("hello world")
            if (value == null) value = "";
            String full = name + value;
            int index = full.indexOf('(');
            name = full.substring(0, index);
            value = full.substring(index);
            */
         }
         AnnotationParser parser = new AnnotationParser(new StringReader(name + value));
         try
         {
            ASTStart start = parser.Start();
            ast = (ASTAnnotation) start.jjtGetChild(0);
         }
         catch (ParseException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
         this.name = name.substring(1);
      }
   }

   public AnnotationDocletTag(String name, String value)
   {
      this(name, value, 0);
   }

   public ASTAnnotation getAnnotation()
   {
      return ast;
   }

   public String getName()
   {
      return name;
   }

   public String getValue()
   {
      return value;
   }

   public String[] getParameters()
   {
      return null;
   }

   @SuppressWarnings("unchecked")
   public Map getNamedParameterMap()
   {
      return null;
   }

   public String getNamedParameter(String key)
   {
      return null;
   }

   public int getLineNumber()
   {
      return lineNumber;
   }

   public final AbstractJavaEntity getContext()
   {
      return owner;
   }

   public void setContext(AbstractJavaEntity owner)
   {
      this.owner = owner;
   }

}
