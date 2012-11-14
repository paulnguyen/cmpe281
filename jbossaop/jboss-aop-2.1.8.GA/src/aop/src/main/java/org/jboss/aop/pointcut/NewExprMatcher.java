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

import javassist.CtConstructor;
import javassist.NotFoundException;
import javassist.expr.NewExpr;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.pointcut.ast.ASTCall;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ASTWithin;
import org.jboss.aop.pointcut.ast.ASTWithincode;
import org.jboss.aop.pointcut.ast.Node;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class NewExprMatcher extends MatcherHelper
{
   NewExpr call;
   Advisor advisor;

   public NewExprMatcher(Advisor advisor, NewExpr call, ASTStart start) throws NotFoundException
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.call = call;
   }

   public Object visit(ASTCall node, Object data)
   {
      try
      {
         if (!(node.getBehavior() instanceof ASTConstructor)) return Boolean.FALSE;
         ASTConstructor astCon = (ASTConstructor) node.getBehavior();

         // do simple checks to avoid loading CtClasses
         if (astCon.getClazz().isSimple())
         {
            if (!astCon.getClazz().matches(call.getClassName())) return Boolean.FALSE;
         }
         CtConstructor calledCon = call.getConstructor();
         ClassAdvisor calledAdvisor = AspectManager.instance().getTempClassAdvisor(calledCon.getDeclaringClass());
         ConstructorMatcher conMatcher = new ConstructorMatcher(calledAdvisor, calledCon, null);
         return conMatcher.matches(astCon);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTWithin node, Object data)
   {
      WithinMatcher within = null;
      try
      {
         within = new WithinMatcher(advisor, call.where(), null);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return node.jjtAccept(within, null);
   }

   public Object visit(ASTWithincode node, Object data)
   {
      WithinMatcher within = null;
      try
      {
         within = new WithinMatcher(advisor, call.where(), null);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return node.jjtAccept(within, null);
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      try
      {
         return new Boolean(p.matchesCall(advisor, call));
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTHas node, Object data)
   {
      try
      {
         Node n = node.jjtGetChild(0);
         CtConstructor con = call.getConstructor();
         if (n instanceof ASTMethod)
         {
            return new Boolean(Util.has(con.getDeclaringClass(), (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(con.getDeclaringClass(), (ASTConstructor) n, advisor));
         }
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTHasField node, Object data)
   {
      ASTField f = (ASTField) node.jjtGetChild(0);
      CtConstructor con = null;
      try
      {
         con = call.getConstructor();
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return new Boolean(Util.has(con.getDeclaringClass(), f, advisor));
   }
}
