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

import org.jboss.aop.instrument.Untransformable;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTExecution;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import org.jboss.aop.pointcut.ast.SimpleNode;

import java.io.StringReader;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77245 $
 *
 **/
public class CFlow
{
   public static int NOT_FOUND = -2;

   private String original;
   private SimpleNode point;
   private boolean not;

   public CFlow(String expr, boolean not)
   {
      original = expr;
      expr = "execution(" + expr + ")";
      ASTExecution exc = null;
      try
      {
         exc = new PointcutExpressionParser(new StringReader(expr)).execution();
      }
      catch (ParseException e)
      {
         throw new RuntimeException("Illegal cflow expression: " + original, e);
      }
      point = (SimpleNode) exc.jjtGetChild(0);
      this.not = not;
   }

   public int matches(StackTraceElement[] stack, int index, ClassLoader cl)
   {
      int found = NOT_FOUND;
      if (point instanceof ASTMethod)
      {
         found = matches((ASTMethod) point, stack, index, cl);
      }
      else
      {
         found = matches((ASTConstructor) point, stack, index, cl);
      }
      if ((found == NOT_FOUND) && not) return index;
      if (found > NOT_FOUND && not) return NOT_FOUND;
      return found;
   }

   private int matches(ASTMethod method, StackTraceElement[] stack, int index, ClassLoader cl)
   {
      for (int i = index; i >= 0; i--)
      {
         ClassExpression expr = method.getClazz();
         if (!matchesClass(expr, stack[i], cl)) continue;
         
         if (method.getMethodIdentifier().matches(stack[i].getMethodName()))
         {
            return i - 1;
         }
      }
      return -2;
   }
   
   private int matches(ASTConstructor con, StackTraceElement[] stack, int index, ClassLoader cl)
   {
      for (int i = index; i >= 0; i--)
      {
         ClassExpression expr = con.getClazz(); 
         if (!matchesClass(expr, stack[i], cl)) continue;

         if (stack[i].getMethodName().equals("<init>"))
         {
            return i - 1; // match
         }
      }
      return -2;
   }
   
   private boolean matchesClass(ClassExpression expr, StackTraceElement element, ClassLoader cl)
   {
      if (expr.isSimple())
      {
         if (!expr.matches(element.getClassName()))
         {
            return false;
         }
      }
      else
      {
         Class<?> clazz = SecurityActions.loadClass(element.getClassName());
         
         if (Untransformable.class.isAssignableFrom(clazz))
         {
            //Invocation classes should not be checked (they are not in the cache at runtime)
            return false;
         }
         if (!Util.matchesClassExpr(expr, clazz))
         {
            return false;
         }
      }
      
      return true;
   }
}
