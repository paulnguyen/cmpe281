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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;

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
 * @version $Revision: 70842 $
 */
public class ConstructorCallMatcher extends MatcherHelper
{
   Advisor advisor;
   AccessibleObject within;
   Class<?> calledClass;
   Constructor<?> calledConstructor;

   public ConstructorCallMatcher(Advisor advisor, AccessibleObject within, Class<?> calledClass, Constructor<?> calledCon, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.within = within;
      this.calledClass = calledClass;
      this.calledConstructor = calledCon;
   }

   public Object visit(ASTCall node, Object data)
   {
      try
      {
         if (!(node.getBehavior() instanceof ASTConstructor)) return Boolean.FALSE;
         ASTConstructor astCon = (ASTConstructor) node.getBehavior();
         Advisor calledAdvisor = AspectManager.instance().getTempClassAdvisorIfNotExist(calledClass);
         ConstructorMatcher constructorMatcher = new ConstructorMatcher(calledAdvisor, calledConstructor, null);
         return constructorMatcher.matches(astCon);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTHas node, Object data)
   {
      Node n = node.jjtGetChild(0);
      if (n instanceof ASTMethod)
      {
         return new Boolean(Util.has(calledClass, (ASTMethod) n, advisor));
      }
      else
      {
         return new Boolean(Util.has(calledClass, (ASTConstructor) n, advisor));
      }
   }

   public Object visit(ASTHasField node, Object data)
   {
      ASTField f = (ASTField) node.jjtGetChild(0);
      return new Boolean(Util.has(calledClass, f, advisor));
   }

   public Object visit(ASTWithin node, Object data)
   {
      WithinMatcher visitor = new WithinMatcher(advisor, within, null);
      return node.jjtAccept(visitor, null);
   }

   public Object visit(ASTWithincode node, Object data)
   {
      WithinMatcher visitor = new WithinMatcher(advisor, within, null);
      return node.jjtAccept(visitor, null);
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      return new Boolean(p.matchesCall(advisor, within, calledClass, calledConstructor));
   }

}
