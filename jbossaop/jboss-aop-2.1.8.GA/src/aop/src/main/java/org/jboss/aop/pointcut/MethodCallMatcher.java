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

import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
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
public class MethodCallMatcher extends MatcherHelper
{
   MethodCall call;
   Advisor advisor;

   public MethodCallMatcher(Advisor advisor, MethodCall call, ASTStart start) throws NotFoundException
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.call = call;
   }

   public Object visit(ASTCall node, Object data)
   {
      try
      {
         if (!(node.getBehavior() instanceof ASTMethod)) return Boolean.FALSE;

         ASTMethod astMethod = (ASTMethod) node.getBehavior();

         // do simple checks to avoid loading CtClasses
         if (astMethod.getClazz().isSimple())
         {
            if (!astMethod.getClazz().matches(call.getClassName())) return Boolean.FALSE;
            if (!astMethod.getMethodIdentifier().isAnnotation())
            {
               if (!astMethod.getMethodIdentifier().matches(call.getMethodName())) return Boolean.FALSE;
            }
         }
         CtMethod calledMethod = call.getMethod();
         Advisor calledAdvisor = AspectManager.instance().getTempClassAdvisor(calledMethod.getDeclaringClass());
         MethodMatcher methodMatcher = new MethodMatcher(calledAdvisor, calledMethod, null);
         return methodMatcher.matches(astMethod);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTHas node, Object data)
   {
      try
      {
         Node n = node.jjtGetChild(0);
         CtMethod method = call.getMethod();
         if (n instanceof ASTMethod)
         {
            return new Boolean(Util.has(method.getDeclaringClass(), (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(method.getDeclaringClass(), (ASTConstructor) n, advisor));
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
      CtMethod method = null;
      try
      {
         method = call.getMethod();
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return new Boolean(Util.has(method.getDeclaringClass(), f, advisor));
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

}
