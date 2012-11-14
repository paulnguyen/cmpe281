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

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTAllParameter;
import org.jboss.aop.pointcut.ast.ASTAnd;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTBoolean;
import org.jboss.aop.pointcut.ast.ASTClass;
import org.jboss.aop.pointcut.ast.ASTComposite;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTException;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTNot;
import org.jboss.aop.pointcut.ast.ASTOr;
import org.jboss.aop.pointcut.ast.ASTParameter;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ASTSub;
import org.jboss.aop.pointcut.ast.Node;
import org.jboss.aop.pointcut.ast.SimpleNode;
import org.jboss.aop.pointcut.ast.TypeExpressionParserVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Takes a Javassist object and sees if the expression
 * pertains to that Javassist object (CtMethod, CtField, CtClass, CtConstructor)
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class AnnotationMatcher implements TypeExpressionParserVisitor
{
   private Advisor advisor;
   private Object element;

   public AnnotationMatcher(Advisor advisor, Object element)
   {
      this.advisor = advisor;
      this.element = element;
   }

   public Object visit(ASTStart node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTBoolean node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTComposite node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTNot node, Object data)
   {

      Boolean bool = (Boolean) node.jjtGetChild(0).jjtAccept(this, data);
      boolean val = bool.booleanValue();
      return val ? Boolean.FALSE : Boolean.TRUE;
   }

   public Object visit(ASTSub node, Object data)
   {
      for (int i = 0; i < node.jjtGetNumChildren(); i++)
      {
         data = node.jjtGetChild(i).jjtAccept(this, data);
      }
      return data;
   }

   public Object visit(ASTAnd node, Object left)
   {
      Node andChild = node.jjtGetChild(0); // should only have one child
      boolean val = ((Boolean) left).booleanValue();
      return new Boolean(val && ((Boolean) andChild.jjtAccept(this, Boolean.FALSE)).booleanValue());
   }

   public Object visit(ASTOr node, Object left)
   {
      Node orChild = node.jjtGetChild(0); // should only have one child
      boolean val = ((Boolean) left).booleanValue();
      return new Boolean(val || ((Boolean) orChild.jjtAccept(this, Boolean.FALSE)).booleanValue());
   }


   public Object visit(SimpleNode node, Object data)
   {
      return null;
   }

   public CtClass getDeclaringClass()
   {
      if (element instanceof CtClass) return (CtClass) element;
      if (element instanceof CtMethod) return ((CtMethod) element).getDeclaringClass();
      if (element instanceof CtField) return ((CtField) element).getDeclaringClass();
      if (element instanceof CtConstructor) return ((CtConstructor) element).getDeclaringClass();
      return null;
   }

   public Class<?> getJavaDeclaringClass()
   {
      if (element instanceof Class) return (Class<?>) element;
      if (element instanceof Method) return ((Method) element).getDeclaringClass();
      if (element instanceof Field) return ((Field) element).getDeclaringClass();
      if (element instanceof Constructor) return ((Constructor<?>) element).getDeclaringClass();
      return null;
   }

   public Object visit(ASTHas node, Object data)
   {
      Node n = node.jjtGetChild(0);
      if (getDeclaringClass() != null)
      {
         CtClass clazz = getDeclaringClass();
         if (n instanceof ASTMethod)
         {
            return new Boolean(Util.has(clazz, (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(clazz, (ASTConstructor) n, advisor));
         }
      }
      else
      {
         Class<?> clazz = getJavaDeclaringClass();
         if (n instanceof ASTMethod)
         {
            return new Boolean(Util.has(clazz, (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(clazz, (ASTConstructor) n, advisor));
         }
      }

   }

   public Object visit(ASTHasField node, Object data)
   {
      if (getDeclaringClass() != null)
      {
         CtClass clazz = getDeclaringClass();
         ASTField f = (ASTField) node.jjtGetChild(0);
         return new Boolean(Util.has(clazz, f, advisor));
      }
      else
      {
         Class<?> clazz = getJavaDeclaringClass();
         ASTField f = (ASTField) node.jjtGetChild(0);
         return new Boolean(Util.has(clazz, f, advisor));
      }
   }

   public Object visit(ASTClass node, Object data)
   {
      if (!(element instanceof CtClass) && !(element instanceof Class)) return Boolean.FALSE;
      if (element instanceof CtClass)
      {
         CtClass clazz = (CtClass) element;
         return new Boolean(Util.matchesClassExpr(node.getClazz(), clazz, advisor));
      }
      else
      {
         Class<?> clazz = (Class<?>) element;
         return new Boolean(Util.matchesClassExpr(node.getClazz(), clazz, advisor));
      }

   }

   public Object visit(ASTMethod node, Object data)
   {
      if (!(element instanceof CtMethod) && !(element instanceof Method)) return Boolean.FALSE;
      if (element instanceof CtMethod)
      {
         MethodMatcher methodMatcher = null;
         methodMatcher = new MethodMatcher(advisor, (CtMethod) element, null);
         return methodMatcher.matches(node);
      }
      else
      {
         MethodMatcher methodMatcher = null;
         methodMatcher = new MethodMatcher(advisor, (Method) element, null);
         return methodMatcher.matches(node);
      }
   }

   public Object visit(ASTConstructor node, Object data)
   {
      if (!(element instanceof CtConstructor) && !(element instanceof Constructor)) return Boolean.FALSE;
      if (element instanceof CtConstructor)
      {
         ConstructorMatcher conMatcher = null;
         try
         {
            conMatcher = new ConstructorMatcher(advisor, (CtConstructor) element, null);
         }
         catch (NotFoundException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
         return conMatcher.matches(node);
      }
      else
      {
         ConstructorMatcher conMatcher = null;
         conMatcher = new ConstructorMatcher(advisor, (Constructor<?>) element, null);
         return conMatcher.matches(node);
      }

   }

   public Object visit(ASTField node, Object data)
   {
      if (!(element instanceof CtField) && !(element instanceof Field)) return Boolean.FALSE;
      if (element instanceof CtField)
      {
         FieldMatcher fieldMatcher = null;
         try
         {
            fieldMatcher = new FieldMatcher(advisor, (CtField) element, null);
         }
         catch (NotFoundException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
         return node.jjtAccept(fieldMatcher, null);
      }
      else
      {
         FieldMatcher fieldMatcher = null;
         fieldMatcher = new FieldMatcher(advisor, (Field) element, null);
         return node.jjtAccept(fieldMatcher, null);
      }

   }

   public Object visit(ASTAttribute node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTParameter node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTAllParameter node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTException node, Object data)
   {
      return Boolean.FALSE;
   }
   
}
