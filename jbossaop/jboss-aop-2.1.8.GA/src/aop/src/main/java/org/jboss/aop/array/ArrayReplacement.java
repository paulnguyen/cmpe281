/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.array;

import javassist.CtClass;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.TypeMatcher;
import org.jboss.aop.pointcut.Util;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ClassExpression;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ArrayReplacement
{
   private String name;
   protected ClassExpression classExpr;
   protected ASTStart ast;

   public ArrayReplacement(String name, String exp)
   {
      this.name = name;
      this.classExpr = new ClassExpression(exp);
   }

   public ArrayReplacement(String name, ASTStart ast)
   {
      this.name = name;
      this.ast = ast;
   }

   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof ArrayReplacement)) return false;
      return ((ArrayReplacement) obj).getName().equals(name);
   }

   public int hashCode()
   {
      return name.hashCode();
   }

   public String getName()
   {
      return name;
   }

   public String getClassExpr()
   {
      if (classExpr == null) return null;
      return classExpr.getOriginal();
   }

   public ASTStart getAst()
   {
      return ast;
   }

   public boolean matches(Advisor advisor, CtClass clazz) throws Exception
   {
      if (classExpr != null)
         return Util.matchesClassExpr(classExpr, clazz, advisor);
      else
      {
         TypeMatcher matcher = new TypeMatcher(advisor, clazz);
         return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
      }
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      if (classExpr != null)
         return Util.matchesClassExpr(classExpr, clazz, advisor);
      else
      {
         TypeMatcher matcher = new TypeMatcher(advisor, clazz);
         return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
      }
   }
}
