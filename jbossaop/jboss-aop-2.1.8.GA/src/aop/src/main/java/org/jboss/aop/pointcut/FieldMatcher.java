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

import java.lang.reflect.Field;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTAll;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTFieldExecution;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.pointcut.ast.Node;
import javassist.CtField;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 44925 $
 */
public class FieldMatcher extends MatcherHelper
{
   protected Advisor advisor;
   protected String classname;
   protected String fieldName;
   protected int fieldModifiers;
   protected CtField ctField;
   protected Field refField;

   public FieldMatcher(Advisor advisor, CtField field, ASTStart start) throws NotFoundException
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.classname = field.getDeclaringClass().getName();
      this.start = start;
      this.fieldName = field.getName();
      this.fieldModifiers = field.getModifiers();
      this.ctField = field;
   }

   public FieldMatcher(Advisor advisor, Field field, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.classname = field.getDeclaringClass().getName();
      this.start = start;
      this.fieldName = field.getName();
      this.fieldModifiers = field.getModifiers();
      this.refField = field;
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      throw new RuntimeException("SHOULD NOT BE CALLED");
   }

   public Object visit(ASTField node, Object data)
   {

      if (node.getAttributes().size() > 0)
      {
         for (int i = 0; i < node.getAttributes().size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.getAttributes().get(i);
            if (!Util.matchModifiers(attr, fieldModifiers))
            {
               return Boolean.FALSE;
            }
         }
      }
      
      try
      {
         ClassExpression type = node.getType();
         if (ctField != null)
         {
            if (!Util.matchesClassExpr(type, ctField.getType(), advisor)) return Boolean.FALSE;
            if (!Util.matchesClassExpr(node.getClazz(), ctField.getDeclaringClass(), advisor)) return Boolean.FALSE;
         }
         else
         {
            if (!Util.matchesClassExpr(type, refField.getType(), advisor)) return Boolean.FALSE;
            if (!Util.matchesClassExpr(node.getClazz(), refField.getDeclaringClass(), advisor)) return Boolean.FALSE;
         }
      } 
      catch (NotFoundException nfe)
      {
         throw new RuntimeException(nfe);
      }
      
      if (node.getFieldIdentifier().isAnnotation())
      {
         String sub = node.getFieldIdentifier().getOriginal().substring(1);
         if (advisor.getFieldMetaData().hasTag(fieldName, sub) || advisor.getDefaultMetaData().hasTag(sub))
         {
            return Boolean.TRUE;
         }
         try
         {
            if (ctField != null)
            {
               if (advisor.hasAnnotation(ctField, sub))
               {
                  return Boolean.TRUE;
               }
            }
            else
            {
               if (advisor.hasAnnotation(refField, sub))
               {
                  return Boolean.TRUE;
               }
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      else
      {
         if (node.getFieldIdentifier().matches(fieldName))
         {
            return Boolean.TRUE;
         }
      }
      return Boolean.FALSE;
   }

   public Object visit(ASTFieldExecution node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTAll node, Object data)
   {
      if (node.getClazz().isAnnotation())
      {
         String sub = node.getClazz().getOriginal().substring(1);
         if (!advisor.getFieldMetaData().hasTag(fieldName, sub))
         {
            if (!advisor.getDefaultMetaData().hasTag(sub))
            {
               if (ctField != null)
               {
                  if (!advisor.hasAnnotation(ctField, sub)) return Boolean.FALSE;
               }
               else
               {
                  try
                  {
                     if (!advisor.hasAnnotation(refField, sub)) return Boolean.FALSE;
                  }
                  catch (Exception e)
                  {
                     throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
                  }
               }
            }
         }
      }
      else if (node.getClazz().isInstanceOf())
      {
         if (ctField != null)
         {
            if (!Util.subtypeOf(ctField.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
         }
         else if (!Util.subtypeOf(refField.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;

      }
      else if (node.getClazz().isTypedef())
      {
         if (ctField != null)
         {
            try
            {
               if (!Util.matchesTypedef(ctField.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);
            }
         }
         else if (!Util.matchesTypedef(refField.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
      }
      else if (!node.getClazz().matches(classname))
      {
         return Boolean.FALSE;
      }

      return Boolean.TRUE;
   }

   public Object visit(ASTHasField node, Object data)
   {
      ASTField f = (ASTField) node.jjtGetChild(0);
      if (ctField != null)
         return new Boolean(Util.has(ctField.getDeclaringClass(), f, advisor));
      else
         return new Boolean(Util.has(refField.getDeclaringClass(), f, advisor));
   }

   public Object visit(ASTHas node, Object data)
   {
      Node n = node.jjtGetChild(0);
      if (n instanceof ASTMethod)
      {
         if (ctField != null)
         {
            return new Boolean(Util.has(ctField.getDeclaringClass(), (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(refField.getDeclaringClass(), (ASTMethod) n, advisor));

         }
      }
      else
      {
         if (refField != null)
         {
            return new Boolean(Util.has(ctField.getDeclaringClass(), (ASTConstructor) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(refField.getDeclaringClass(), (ASTConstructor) n, advisor));

         }
      }
   }
}
