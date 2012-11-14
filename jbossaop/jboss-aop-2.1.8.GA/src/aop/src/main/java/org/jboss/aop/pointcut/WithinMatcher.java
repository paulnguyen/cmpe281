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

import javassist.CtBehavior;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ASTWithin;
import org.jboss.aop.pointcut.ast.ASTWithincode;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class WithinMatcher extends MatcherHelper
{
   CtBehavior behavior;
   Advisor advisor;
   AccessibleObject accessible;

   public WithinMatcher(Advisor advisor, CtBehavior behavior, ASTStart start) throws NotFoundException
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.behavior = behavior;
   }

   public WithinMatcher(Advisor advisor, AccessibleObject behavior, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.accessible = behavior;
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      throw new RuntimeException("NOT REACHABLE");
   }

   public Class<?> getDeclaringClass()
   {
      if (accessible instanceof Constructor)
         return ((Constructor<?>) accessible).getDeclaringClass();
      else if (accessible instanceof Method) return ((Method) accessible).getDeclaringClass();
      return null;
   }

   public Object visit(ASTWithin node, Object data)
   {
      if (behavior != null)
      {
         if (!Util.matchesClassExpr(node.getClazz(), behavior.getDeclaringClass(), advisor)) return Boolean.FALSE;
      }
      else
      {
         if (!Util.matchesClassExpr(node.getClazz(), getDeclaringClass(), advisor)) return Boolean.FALSE;
      }
      return Boolean.TRUE;
   }

   public Object visit(ASTWithincode node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   public Object visit(ASTMethod node, Object data)
   {
      if (behavior != null)
      {
         if (behavior instanceof CtConstructor) return Boolean.FALSE;
         MethodMatcher matcher = new MethodMatcher(advisor, (CtMethod) behavior, null);
         return matcher.matches(node);
      }
      if (accessible instanceof Constructor) return Boolean.FALSE;
      MethodMatcher matcher = new MethodMatcher(advisor, (Method) accessible, null);
      return matcher.matches(node);

   }

   public Object visit(ASTConstructor node, Object data)
   {
      if (behavior != null)
      {
         try
         {
            if (behavior instanceof CtMethod) return Boolean.FALSE;
            ConstructorMatcher matcher = new ConstructorMatcher(advisor, (CtConstructor) behavior, null);
            return matcher.matches(node);
         }
         catch (NotFoundException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      if (accessible instanceof Method) return Boolean.FALSE;
      ConstructorMatcher matcher = new ConstructorMatcher(advisor, (Constructor<?>) accessible, null);
      return matcher.matches(node);

   }

}
