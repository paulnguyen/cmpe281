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

import java.io.StringReader;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class PointcutExpression implements Pointcut
{
   protected String name;
   protected String expr;
   protected ASTStart ast;

   protected PointcutStats stats;

   public PointcutExpression(String name, String expr) throws ParseException
   {
      this.name = name;
      this.expr = expr;


      ast = new PointcutExpressionParser(new StringReader(expr)).Start();
   }

   public void setManager(AspectManager manager)
   {
      if (stats == null)
      {
         stats = new PointcutStats(ast, manager);
         stats.matches();
      }
   }

   public PointcutStats getStats()
   {
      return stats;
   }

   public String getName()
   {
      return name;
   }

   public String getExpr()
   {
      return expr;
   }

   public boolean softMatch(Advisor advisor)
   {
      SoftClassMatcher matcher = new SoftClassMatcher(advisor, advisor.getName(), ast);
      return matcher.matches();
   }

   public boolean matchesCall(Advisor callingAdvisor, MethodCall methodCall) throws NotFoundException
   {
      if (stats == null || stats.isWithin() || stats.isWithincode() || stats.isCall())
      {
         MethodCallMatcher matcher = new MethodCallMatcher(callingAdvisor, methodCall, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesCall(Advisor callingAdvisor, NewExpr methodCall) throws NotFoundException
   {
      if (stats == null || stats.isWithin() || stats.isWithincode() || stats.isCall())
      {
         NewExprMatcher matcher = new NewExprMatcher(callingAdvisor, methodCall, ast);
         return matcher.matches();
      }
      return false;
   }

   public PointcutMethodMatch matchesExecution(Advisor advisor, Method m)
   {
      if (stats == null || stats.isExecution())
      {
         ExecutionMethodMatcher matcher = new ExecutionMethodMatcher(advisor, m, ast);
         boolean match = matcher.matches();
         
         if (match)
         {
            return new PointcutMethodMatch(match, matcher.getMatchedClass(), matcher.getMatchLevel(), matcher.isInstanceOf());
         }
      }
      return null;
   }

   public boolean matchesExecution(Advisor advisor, Constructor<?> c)
   {
      if (stats == null || stats.isExecution())
      {
         ExecutionConstructorMatcher matcher = new ExecutionConstructorMatcher(advisor, c, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesConstruction(Advisor advisor, Constructor<?> c)
   {
      if (stats == null || stats.isConstruction())
      {
         ConstructionMatcher matcher = new ConstructionMatcher(advisor, c, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesGet(Advisor advisor, Field f)
   {
      if (stats == null || stats.isGet())
      {
         FieldMatcher matcher = new FieldGetMatcher(advisor, f, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesSet(Advisor advisor, Field f)
   {
      if (stats == null || stats.isSet())
      {
         FieldMatcher matcher = new FieldSetMatcher(advisor, f, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesExecution(Advisor advisor, CtMethod m) throws NotFoundException
   {
      try
      {
         if (stats == null || stats.isExecution())
         {
            ExecutionMethodMatcher matcher = new ExecutionMethodMatcher(advisor, m, ast);
            boolean match =  matcher.matches();
            return match;
         }
         return false;
      }
      catch (Exception e)
      {
         throw new RuntimeException("Error parsing " + expr, e);
      }
   }

   public boolean matchesExecution(Advisor advisor, CtConstructor c) throws NotFoundException
   {
      if (stats == null || stats.isExecution())
      {
         ExecutionConstructorMatcher matcher = new ExecutionConstructorMatcher(advisor, c, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesConstruction(Advisor advisor, CtConstructor c) throws NotFoundException
   {
      if (stats == null || stats.isConstruction())
      {
         ConstructionMatcher matcher = new ConstructionMatcher(advisor, c, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesGet(Advisor advisor, CtField f) throws NotFoundException
   {
      if (stats == null || stats.isGet())
      {
         FieldMatcher matcher = new FieldGetMatcher(advisor, f, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesSet(Advisor advisor, CtField f) throws NotFoundException
   {
      if (stats == null || stats.isSet())
      {
         FieldMatcher matcher = new FieldSetMatcher(advisor, f, ast);
         return matcher.matches();
      }
      return false;
   }

   public boolean matchesCall(Advisor advisor, AccessibleObject within, Class<?> calledClass, Method calledMethod)
   {
      if (stats == null || stats.isWithin() || stats.isWithincode() || stats.isCall())
      {
         CallMatcher matcher = new CallMatcher(advisor, within, calledClass, calledMethod, ast);
         return matcher.matches();
      }
      return false;
   }


   public boolean matchesCall(Advisor advisor, AccessibleObject within, Class<?> calledClass, Constructor<?> calledCon)
   {
      if (stats == null || stats.isWithin() || stats.isWithincode() || stats.isCall())
      {
         ConstructorCallMatcher matcher = new ConstructorCallMatcher(advisor, within, calledClass, calledCon, ast);
         return matcher.matches();
      }
      return false;
   }

   public String toString()
   {
      return "Pointcut[name=" + name + "; expr=" + expr + "]";
   }
}
