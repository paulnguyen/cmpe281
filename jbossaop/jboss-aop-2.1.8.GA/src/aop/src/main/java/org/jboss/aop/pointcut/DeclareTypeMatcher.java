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

import javassist.CtClass;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTHas;
import org.jboss.aop.pointcut.ast.ASTHasField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.Node;

/** 
 * Same as TypeMatcher apart from that hasfield() and has() only check for existence 
 * of field/method/constructor on the class itself, and do not check the superclass.
 * Used by DeclareDef to enforce implementation of methods etc.
 *  
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 70842 $
 */
public class DeclareTypeMatcher extends TypeMatcher
{
   public DeclareTypeMatcher(Advisor advisor, Class<?> clazz)
   {
      super(advisor, clazz);
   }

   public DeclareTypeMatcher(Advisor advisor, CtClass ctClass)
   {
      super(advisor, ctClass);
   }
   
   public Object visit(ASTHas node, Object data)
   {
      Node n = node.jjtGetChild(0);
      if (n instanceof ASTMethod)
      {
         boolean checkSuper = false;
         if (clazz != null) return new Boolean(Util.has(clazz, (ASTMethod) n, advisor, checkSuper));
         if (ctClass != null) return new Boolean(Util.has(ctClass, (ASTMethod) n, advisor, checkSuper));
      }
      else
      {
         //Constructors only get checked on the class itself
         if (clazz != null) return new Boolean(Util.has(clazz, (ASTConstructor) n, advisor));
         if (ctClass != null) return new Boolean(Util.has(ctClass, (ASTConstructor) n, advisor));
      }
      return Boolean.FALSE;
   }

   public Object visit(ASTHasField node, Object data)
   {
      boolean checkSuper = false;
      ASTField f = (ASTField) node.jjtGetChild(0);
      if (clazz != null)
         return new Boolean(Util.has(clazz, f, advisor, checkSuper));
      else
         return new Boolean(Util.has(ctClass, f, advisor, checkSuper));
   }

}
