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

import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 70842 $
 */
public class DeclareDef
{
   String name;
   String expr;
   boolean warning;
   String msg;
   ASTStart ast;
   boolean pointcut;
   
   public DeclareDef(String name, String expr, boolean warning, String msg) throws ParseException
   {
      this.name = name;
      this.expr = expr;
      this.warning = warning;
      this.msg = msg;
      
      try
      {
         ast = new PointcutExpressionParser(new StringReader(expr)).Start();
         pointcut = true;
      } 
      catch (ParseException pe)
      {
         try
         {
            ast = new TypeExpressionParser(new StringReader(expr)).Start();
         }
         catch (ParseException te)
         {
            StringBuffer sb = new StringBuffer("The expression '" + 
                  expr + "' resolves to neither a pointcut nor a type expression");
            sb.append("\n\nPointcut parse error:\n");
            sb.append(pe.getMessage());
            sb.append("\n\nType expression parse error:\n");
            sb.append(te.getMessage());
            throw new ParseException(sb.toString());
         }
      }
   }
   

   public ASTStart getAst()
   {
      return ast;
   }
   
   public String getExpr()
   {
      return expr;
   }
   
   public String getName()
   {
      return name;
   }
   
   public boolean getWarning()
   {
      return warning;
   }
   
   public String getMsg()
   {
      return msg;
   }
   
   /**
    * @return true if a pointcut expression, false if a type expression
    */
   public boolean isPointcut()
   {
      return pointcut;
   }
   
   public boolean matches(Advisor advisor, CtClass clazz)
   {
      if (pointcut)return false;
      DeclareTypeMatcher matcher = new DeclareTypeMatcher(advisor, clazz);
      return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      if (pointcut)return false;
      DeclareTypeMatcher matcher = new DeclareTypeMatcher(advisor, clazz);
      return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matchesCall(Advisor callingAdvisor, MethodCall methodCall) throws NotFoundException
   {
      if (!pointcut)return false;
      MethodCallMatcher matcher = new MethodCallMatcher(callingAdvisor, methodCall, ast);
      return matcher.matches();
   }

   public boolean matchesCall(Advisor callingAdvisor, NewExpr methodCall) throws NotFoundException
   {
      if (!pointcut)return false;
      NewExprMatcher matcher = new NewExprMatcher(callingAdvisor, methodCall, ast);
      return matcher.matches();
   }

   
}
