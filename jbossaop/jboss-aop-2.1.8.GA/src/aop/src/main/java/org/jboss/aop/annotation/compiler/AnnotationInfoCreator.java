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

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

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

import java.util.Set;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class AnnotationInfoCreator implements AnnotationParserVisitor
{
   private ClassPool pool;
   private ConstPool cp;
   private javassist.bytecode.annotation.Annotation base;

   public AnnotationInfoCreator(ClassPool pool, ConstPool cp, javassist.bytecode.annotation.Annotation base)
   {
      this.pool = pool;
      this.cp = cp;
      this.base = base;
   }

   public Object visit(ASTMemberValuePairs node, Object data)
   {
      node.childrenAccept(this, data);
      return null;
   }

   public Object visit(ASTMemberValuePair node, Object data)
   {
      String name = node.getIdentifier().getValue();
      MemberValue mv = base.getMemberValue(name);
      if (mv == null)
      {
         throw new RuntimeException("member value is null for created annotation: " + base.getTypeName() + " for name: " + name);
      }
      node.getValue().jjtAccept(this, mv);
      return data;
   }

   public Object visit(ASTSingleMemberValue node, Object data)
   {
      if (base.getMemberNames().size() > 1) throw new RuntimeException("single value expected from annotation: " + base.getTypeName());

      Set<String> set = base.getMemberNames();
      MemberValue mv = base.getMemberValue(set.iterator().next());
      node.getValue().jjtAccept(this, mv);
      return data;
   }

   public Object visit(ASTIdentifier node, Object data)
   {
      MemberValue mv = (MemberValue) data;
      MemberValuePopulate mvc = new MemberValuePopulate(node.getValue());
      mv.accept(mvc);
      return null;
   }

   public Object visit(ASTString node, Object data)
   {
      StringMemberValue mv = (StringMemberValue) data;
      mv.setValue(node.getValue());
      return null;
   }

   public Object visit(ASTChar node, Object data)
   {
      CharMemberValue mv = (CharMemberValue) data;
      mv.setValue(node.getValue());
      return null;
   }


   public Object visit(ASTMemberValueArrayInitializer node, Object data)
   {
      ArrayMemberValue mv = (ArrayMemberValue) data;
      int size = node.jjtGetNumChildren();
      MemberValue[] elements = new MemberValue[size];
      MemberValueCreation mvc = new MemberValueCreation(cp);
      for (int i = 0; i < size; i++)
      {
         mv.getType().accept(mvc);
         elements[i] = mvc.value;
         node.jjtGetChild(i).jjtAccept(this, elements[i]);
      }
      mv.setValue(elements);
      return null;
   }

   public Object visit(ASTAnnotation node, Object data)
   {
      try
      {
         CtClass annotation = pool.get(node.getIdentifier());
         javassist.bytecode.annotation.Annotation info = new javassist.bytecode.annotation.Annotation(cp, annotation);
         AnnotationInfoCreator creator = new AnnotationInfoCreator(pool, cp, info);
         node.childrenAccept(creator, data);
         AnnotationMemberValue mv = (AnnotationMemberValue) data;
         mv.setValue(info);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return null;
   }

   // Unneeded

   public Object visit(SimpleNode node, Object data)
   {
      return null;
   }

   public Object visit(ASTStart node, Object data)
   {
      return null;
   }

   public static javassist.bytecode.annotation.Annotation createAnnotationInfo(ClassPool pool, ConstPool cp, ASTAnnotation node) throws Exception
   {
      CtClass annotation = pool.get(node.getIdentifier());
      javassist.bytecode.annotation.Annotation info = new javassist.bytecode.annotation.Annotation(cp, annotation);
      AnnotationInfoCreator creator = new AnnotationInfoCreator(pool, cp, info);
      if (node.jjtGetNumChildren() > 0)
      {
         node.jjtGetChild(0).jjtAccept(creator, null);
      }
      return info;
   }

}
