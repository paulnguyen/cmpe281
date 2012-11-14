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

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTAll;
import org.jboss.aop.pointcut.ast.ASTAllParameter;
import org.jboss.aop.pointcut.ast.ASTAndCFlow;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTCFlow;
import org.jboss.aop.pointcut.ast.ASTCFlowBoolean;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ASTCall;
import org.jboss.aop.pointcut.ast.ASTCompositeCFlow;
import org.jboss.aop.pointcut.ast.ASTConstruction;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTExecution;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTFieldExecution;
import org.jboss.aop.pointcut.ast.ASTGet;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTNotCFlow;
import org.jboss.aop.pointcut.ast.ASTOrCFlow;
import org.jboss.aop.pointcut.ast.ASTParameter;
import org.jboss.aop.pointcut.ast.ASTSet;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ASTSubCFlow;
import org.jboss.aop.pointcut.ast.ASTWithin;
import org.jboss.aop.pointcut.ast.ASTWithincode;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.pointcut.ast.Node;
import org.jboss.aop.pointcut.ast.SimpleNode;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class SoftClassMatcher extends MatcherHelper
{
   protected Advisor advisor;
   protected String classname;
   protected boolean match = false;
   protected Class<?> clazz;

   public SoftClassMatcher(Advisor advisor, String classname, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.classname = classname;
      this.start = start;
      clazz = advisor.getClazz();
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      return new Boolean(p.softMatch(advisor));
   }

   public Object visit(ASTCall node, Object data)
   {
      return node.getBehavior().jjtAccept(this, null);
   }

   public Object visit(ASTAll node, Object data)
   {
      return matches(node.getClazz());
   }


   public Boolean matches(ClassExpression expr)
   {
      if (expr.isAnnotation())
      {
         if (advisor == null) return Boolean.TRUE;
         if (clazz == null) return Boolean.TRUE;
         String sub = expr.getOriginal().substring(1);
         if (!advisor.getClassMetaData().hasTag(sub))
         {
            try
            {
               if (clazz == null) return Boolean.TRUE;
               if (!advisor.hasAnnotation(sub)) return Boolean.FALSE;
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
            }
         }
      }
      else if (expr.isInstanceOf())
      {
         if (clazz == null) return Boolean.TRUE;
         if (!Util.subtypeOf(clazz, expr, advisor)) return Boolean.FALSE;

      }
      else if (expr.isTypedef())
      {
         if (clazz == null) return Boolean.TRUE; //Is this return correct?
         if (!Util.matchesTypedef(clazz, expr, advisor)) return Boolean.FALSE;
      }
      else
      {
         if (!expr.matches(classname)) return Boolean.FALSE;
      }
      return Boolean.TRUE;
   }

   public Object visit(ASTWithin node, Object data)
   {
      return matches(node.getClazz());
   }

   public Object visit(ASTWithincode node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTExecution node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTConstruction node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTGet node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTSet node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTFieldExecution node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTMethod node, Object data)
   {
      ClassExpression classExpression = node.getClazz();
      return matchClass(classExpression);
   }

   private Object matchClass(ClassExpression classExpression)
   {
      if (classExpression.isAnnotation())
      {
         String sub = classExpression.getOriginal().substring(1);
         if (advisor.getClassMetaData().hasTag(sub)) return Boolean.TRUE;
         return new Boolean(advisor.hasAnnotation(clazz, sub));
      }
      else if (classExpression.isInstanceOf())
      {
         if (clazz == null) return Boolean.TRUE;
         if (Util.subtypeOf(clazz, classExpression, advisor)) return Boolean.TRUE;
      }
      else if (classExpression.isTypedef())
      {
         if (Util.matchesTypedef(clazz, classExpression, advisor)) return Boolean.TRUE;
      }
      else
      {
         return new Boolean(classExpression.matches(this.classname));
      }
      return Boolean.FALSE;
   }

   public Object visit(ASTConstructor node, Object data)
   {
      ClassExpression classExpression = node.getClazz();
      return matchClass(classExpression);
   }

   
   public Object visit(ASTField node, Object data)
   {
      ClassExpression classExpression = node.getClazz();
      return matchClass(classExpression);
   }

   public Object visit(ASTHas node, Object data)
   {
      if (clazz == null) return Boolean.TRUE;
      Node n = node.jjtGetChild(0);
      if (n instanceof ASTMethod)
      {
         return new Boolean(Util.has(clazz, (ASTMethod) n, advisor));
      }
      else
      {
         return new Boolean(Util.has(clazz, (ASTConstructor) n, advisor));
      }
   }

   public Object visit(ASTHasField node, Object data)
   {
      if (clazz == null) return Boolean.TRUE;
      ASTField f = (ASTField) node.jjtGetChild(0);
      return new Boolean(Util.has(clazz, f, advisor));
   }

   //----- NOT VISITED -----

   public Object visit(ASTAttribute node, Object data)
   {
      return data;
   }

   public Object visit(ASTParameter node, Object data)
   {
      return data;
   }

   public Object visit(ASTAllParameter node, Object data)
   {
      return data;
   }

   public Object visit(ASTCFlowExpression node, Object data)
   {
      return data;
   }

   public Object visit(ASTCFlowBoolean node, Object data)
   {
      return data;
   }

   public Object visit(ASTNotCFlow node, Object data)
   {
      return data;
   }

   public Object visit(ASTCompositeCFlow node, Object data)
   {
      return data;
   }

   public Object visit(ASTSubCFlow node, Object data)
   {
      return data;
   }

   public Object visit(ASTAndCFlow node, Object data)
   {
      return data;
   }

   public Object visit(ASTOrCFlow node, Object data)
   {
      return data;
   }

   public Object visit(ASTCFlow node, Object data)
   {
      return data;
   }

   public Object visit(SimpleNode node, Object data)
   {
      return data;
   }

}
