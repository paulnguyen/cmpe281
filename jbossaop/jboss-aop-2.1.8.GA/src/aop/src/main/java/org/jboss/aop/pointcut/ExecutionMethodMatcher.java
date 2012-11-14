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
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTExecution;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.Node;

import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 46033 $
 */
public class ExecutionMethodMatcher extends MethodMatcher
{
   public ExecutionMethodMatcher(Advisor advisor, CtMethod method, ASTStart start) throws NotFoundException
   {
      super(advisor, method, start);
   }

   public ExecutionMethodMatcher(Advisor advisor, Method method, ASTStart start)
   {
      super(advisor, method, start);
   }

   public Object visit(ASTExecution node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      try
      {
         if (refMethod != null) 
         { 
            PointcutMethodMatch pmatch = p.matchesExecution(advisor, refMethod);
            if (pmatch != null && pmatch.isMatch())
            {
               this.matchedClass = pmatch.getMatchedClass();
               this.matchLevel = pmatch.getMatchLevel();
               this.isInstanceof = pmatch.isInstanceOf();
               return Boolean.TRUE;
            }
            return Boolean.FALSE; 
         }
         return new Boolean(p.matchesExecution(advisor, ctMethod));
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public Object visit(ASTHas node, Object data)
   {
      Node n = node.jjtGetChild(0);
      if (n instanceof ASTMethod)
      {
         if (ctMethod != null)
         {
            return new Boolean(Util.has(ctMethod.getDeclaringClass(), (ASTMethod) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(refMethod.getDeclaringClass(), (ASTMethod) n, advisor));

         }
      }
      else
      {
         if (ctMethod != null)
         {
            return new Boolean(Util.has(ctMethod.getDeclaringClass(), (ASTConstructor) n, advisor));
         }
         else
         {
            return new Boolean(Util.has(refMethod.getDeclaringClass(), (ASTConstructor) n, advisor));

         }
      }
   }

   public Object visit(ASTHasField node, Object data)
   {
      ASTField f = (ASTField) node.jjtGetChild(0);
      if (ctMethod != null)
      {
         return new Boolean(Util.has(ctMethod.getDeclaringClass(), f, advisor));
      }
      else
      {
         return new Boolean(Util.has(refMethod.getDeclaringClass(), f, advisor));
      }

   }


}
