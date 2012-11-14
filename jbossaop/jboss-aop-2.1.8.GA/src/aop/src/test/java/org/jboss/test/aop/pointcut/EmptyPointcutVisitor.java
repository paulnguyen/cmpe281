/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.pointcut;

import org.jboss.aop.pointcut.ast.ASTAll;
import org.jboss.aop.pointcut.ast.ASTAllParameter;
import org.jboss.aop.pointcut.ast.ASTAnd;
import org.jboss.aop.pointcut.ast.ASTAndCFlow;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTBoolean;
import org.jboss.aop.pointcut.ast.ASTCFlow;
import org.jboss.aop.pointcut.ast.ASTCFlowBoolean;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ASTCall;
import org.jboss.aop.pointcut.ast.ASTComposite;
import org.jboss.aop.pointcut.ast.ASTCompositeCFlow;
import org.jboss.aop.pointcut.ast.ASTConstruction;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTException;
import org.jboss.aop.pointcut.ast.ASTExecution;
import org.jboss.aop.pointcut.ast.ASTExecutionOnly;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTFieldExecution;
import org.jboss.aop.pointcut.ast.ASTGet;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTNot;
import org.jboss.aop.pointcut.ast.ASTNotCFlow;
import org.jboss.aop.pointcut.ast.ASTOr;
import org.jboss.aop.pointcut.ast.ASTOrCFlow;
import org.jboss.aop.pointcut.ast.ASTParameter;
import org.jboss.aop.pointcut.ast.ASTPointcut;
import org.jboss.aop.pointcut.ast.ASTSet;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ASTSub;
import org.jboss.aop.pointcut.ast.ASTSubCFlow;
import org.jboss.aop.pointcut.ast.ASTWithin;
import org.jboss.aop.pointcut.ast.ASTWithincode;
import org.jboss.aop.pointcut.ast.PointcutExpressionParserVisitor;
import org.jboss.aop.pointcut.ast.SimpleNode;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class EmptyPointcutVisitor implements PointcutExpressionParserVisitor
{
   public Object visit(SimpleNode node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTStart node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTExecutionOnly node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTBoolean node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTComposite node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTNot node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTSub node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTAnd node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTOr node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTCFlowExpression node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTCFlowBoolean node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTNotCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTCompositeCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTSubCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTAndCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTOrCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTPointcut node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTCFlow node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTAll node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTCall node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTWithin node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTWithincode node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTExecution node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTConstruction node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTHas node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTHasField node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTGet node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTSet node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTFieldExecution node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTMethod node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTException node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTAttribute node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTConstructor node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTParameter node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTAllParameter node, Object data)
   {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTField node, Object data)
   {
      return node.childrenAccept(this, data);
   } 

}
