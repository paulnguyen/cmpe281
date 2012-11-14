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
import org.jboss.aop.pointcut.ast.Node;
import org.jboss.aop.pointcut.ast.PointcutExpressionParserVisitor;
import org.jboss.aop.pointcut.ast.SimpleNode;
import org.jboss.aop.pointcut.ast.ASTConstruction;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 73687 $
 */
public class PointcutStats implements PointcutExpressionParserVisitor
{
   protected ASTStart start;
   protected AspectManager manager;
   protected boolean execution = false;
   protected boolean methodExecution = false;
   protected boolean constructorExecution = false;
   protected boolean construction = false;
   protected boolean call = false;
   protected boolean methodCall = false;
   protected boolean constructorCall = false;
   protected boolean within = false;
   protected boolean get = false;
   protected boolean set = false;
   protected boolean withincode = false;


   public PointcutStats(ASTStart start, AspectManager manager)
   {
      this.start = start;
      this.manager = manager;
   }

   public boolean isExecution()
   {
      return execution;
   }

   public boolean isMethodExecution()
   {
      return methodExecution;
   }

   public boolean isConstructorExecution()
   {
      return constructorExecution;
   }

   public boolean isConstruction()
   {
      return construction;
   }

   public boolean isCall()
   {
      return call;
   }
   
   public boolean isMethodCall()
   {
      return methodCall;
   }

   public boolean isConstructorCall()
   {
      return constructorCall;
   }

   public boolean isWithin()
   {
      return within;
   }

   public boolean isGet()
   {
      return get;
   }

   public boolean isSet()
   {
      return set;
   }

   public boolean isWithincode()
   {
      return withincode;
   }


   public boolean matches()
   {
      return ((Boolean) visit(start, null)).booleanValue();
   }

   public Object visit(ASTStart node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTExecutionOnly node, Object data)
   {
      throw new RuntimeException("SHOULD NEVER BE CALLED!");
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
      andChild.jjtAccept(this, Boolean.FALSE);
      return Boolean.FALSE;
   }

   public Object visit(ASTOr node, Object left)
   {
      Node orChild = node.jjtGetChild(0); // should only have one child
      orChild.jjtAccept(this, Boolean.FALSE);
      return Boolean.FALSE;
   }

   private void setAllTrue()
   {
      // can't be sure so set all
      execution = true;
      methodExecution = true;
      constructorExecution = true;
      call = true;
      methodCall = true;
      constructorCall = true;
      within = true;
      get = true;
      set = true;
      withincode = true;
   }

   public Object visit(ASTPointcut node, Object data)
   {
      if (manager == null)
      {
         setAllTrue();
      }
      Pointcut p = manager.getPointcut(node.getPointcutName());
      if (p instanceof PointcutExpression)
      {
         PointcutExpression expr = (PointcutExpression) p;
         PointcutStats stats = expr.getStats();
         if (stats != null)
         {
            execution |= stats.isExecution();
            methodExecution |= stats.isMethodExecution();
            constructorExecution |= stats.isConstructorExecution();
            call |= stats.isCall();
            methodCall |= stats.isMethodCall();
            constructorCall |= stats.isConstructorCall();
            within |= stats.isWithin();
            get |= stats.isGet();
            set |= stats.isSet();
            withincode |= stats.isWithincode();
         }
         else
         {
            setAllTrue();
         }
      }
      else
      {
         setAllTrue();

      }
      return Boolean.FALSE;
   }

   public Object visit(ASTAll node, Object data)
   {
      execution = true;
      get = true;
      set = true;
      methodExecution = true;
      constructorExecution = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTCFlowExpression node, Object data)
   {
      return Boolean.FALSE;
   }


   public Object visit(ASTCall node, Object data)
   {
      call = true;
      //For ASTCall the expression lives in behaviour rather than in chilren
      //node.childrenAccept(this, node);
      node.getBehavior().jjtAccept(this, node);
      return Boolean.FALSE;
   }

   public Object visit(ASTWithin node, Object data)
   {
      within = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTWithincode node, Object data)
   {
      withincode = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTExecution node, Object data)
   {
      execution = true;
      node.childrenAccept(this, node);
      return Boolean.FALSE;
   }

   public Object visit(ASTConstruction node, Object data)
   {
      construction = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTConstructor node, Object data)
   {
      Object parent = data; //Passed in if parent node was ASTCall or ASTExecution
      if (parent instanceof ASTExecution)
      {
         execution = constructorExecution = true;
      }
      else if (parent instanceof ASTCall)
      {
         call = constructorCall = true;
      }
      return Boolean.FALSE;
   }

   public Object visit(ASTMethod node, Object data)
   {
      Object parent = data; //Passed in if parent node was ASTCall or ASTExecution
      if (parent instanceof ASTExecution)
      {
         execution = methodExecution = true;
      }
      else if (parent instanceof ASTCall)
      {
         call = methodCall = true;
      }
      return Boolean.FALSE;
   }

   public Object visit(ASTParameter node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTGet node, Object data)
   {
      get = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTSet node, Object data)
   {
      set = true;
      return Boolean.FALSE;
   }

   public Object visit(ASTFieldExecution node, Object data)
   {
      get = set = true;
      return Boolean.FALSE;
   }

/////////////


   public Object visit(SimpleNode node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTCFlowBoolean node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTNotCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTCompositeCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTSubCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTAndCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTOrCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTCFlow node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTAttribute node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTAllParameter node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTField node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTException node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTHas node, Object data)
   {
      return Boolean.FALSE;
   }

   public Object visit(ASTHasField node, Object data)
   {
      return Boolean.FALSE;
   }


}
