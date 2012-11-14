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

import org.jboss.aop.AspectManager;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.pointcut.ast.ASTAndCFlow;
import org.jboss.aop.pointcut.ast.ASTCFlow;
import org.jboss.aop.pointcut.ast.ASTCFlowBoolean;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ASTCompositeCFlow;
import org.jboss.aop.pointcut.ast.ASTNotCFlow;
import org.jboss.aop.pointcut.ast.ASTOrCFlow;
import org.jboss.aop.pointcut.ast.ASTSubCFlow;
import org.jboss.aop.pointcut.ast.Node;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77243 $
 */
public class CFlowMatcher extends MatcherHelper
{
   StackTraceElement[] stack;
   Invocation invocation;

   public CFlowMatcher()
   {
      super(null, null);
   }

   public boolean matches(ASTCFlowExpression expr, Invocation invocation)
   {
      this.invocation = invocation;
      Boolean rtn = (Boolean) expr.jjtAccept(this, null);
      return rtn.booleanValue();
   }

   private StackTraceElement[] getStack()
   {
      stack = new Throwable().getStackTrace();
      return stack;
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      throw new RuntimeException("SHOULD NOT BE REACHABLE");
   }


   public Object visit(ASTCFlowExpression node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTCFlowBoolean node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTNotCFlow node, Object data)
   {
      Boolean bool = (Boolean) node.jjtGetChild(0).jjtAccept(this, data);
      boolean val = bool.booleanValue();
      return val ? Boolean.FALSE : Boolean.TRUE;
   }

   public Object visit(ASTCompositeCFlow node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTSubCFlow node, Object data)
   {
      for (int i = 0; i < node.jjtGetNumChildren(); i++)
      {
         data = node.jjtGetChild(i).jjtAccept(this, data);
      }
      return data;
   }

   public Object visit(ASTAndCFlow node, Object left)
   {
      Node andChild = node.jjtGetChild(0); // should only have one child
      boolean val = ((Boolean) left).booleanValue();
      return new Boolean(val && ((Boolean) andChild.jjtAccept(this, Boolean.FALSE)).booleanValue());
   }

   public Object visit(ASTOrCFlow node, Object left)
   {
      Node orChild = node.jjtGetChild(0); // should only have one child
      boolean val = ((Boolean) left).booleanValue();
      return new Boolean(val || ((Boolean) orChild.jjtAccept(this, Boolean.FALSE)).booleanValue());
   }

   public Object visit(ASTCFlow node, Object data)
   {
      AspectManager manager = null;
      if (invocation.getAdvisor() == null)
         manager = AspectManager.instance();
      else
         manager = invocation.getAdvisor().getManager();
      CFlowStack cflow = manager.getCFlowStack(node.getPointcutName());
      
      //Use the current advisor to guess the classloader
      ClassLoader cl = invocation.getAdvisor().getClassLoader();
      if (cl == null)
      {
         //Fall back to context classloader if null
         cl = SecurityActions.getContextClassLoader();
      }
      
      if (cflow != null) return new Boolean(cflow.matches(getStack(), cl));

      DynamicCFlow dcflow = manager.getDynamicCFlow(node.getPointcutName(), cl);
      return new Boolean(dcflow.shouldExecute(invocation));
   }
}
