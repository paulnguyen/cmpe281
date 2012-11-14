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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTAll;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTException;
import org.jboss.aop.pointcut.ast.ASTStart;

import javassist.CtConstructor;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class ConstructorMatcher extends MatcherHelper
{
   protected Advisor advisor;
   protected CtConstructor ctCon;
   protected Constructor<?> refCon;
   protected int conModifiers;
   protected String classname;

   public ConstructorMatcher(Advisor advisor, CtConstructor con, ASTStart start) throws NotFoundException
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.start = start;
      this.conModifiers = con.getModifiers();
      this.classname = con.getDeclaringClass().getName();
      this.ctCon = con;
   }

   public ConstructorMatcher(Advisor advisor, Constructor<?> con, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.start = start;
      this.conModifiers = con.getModifiers();
      this.classname = con.getDeclaringClass().getName();
      this.refCon = con;
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      throw new RuntimeException("SHOULD NOT BE CALLED");
   }

   public Object visit(ASTConstructor node, Object data)
   {
      return matches(node);
   }

   public Boolean matches(ASTConstructor node)
   {
      if (node.getAttributes().size() > 0)
      {
         for (int i = 0; i < node.getAttributes().size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.getAttributes().get(i);
            if (!Util.matchModifiers(attr, conModifiers)) return Boolean.FALSE;
         }
      }

      if (ctCon != null)
      {
         if (!Util.matchesClassExpr(node.getClazz(), ctCon.getDeclaringClass(), advisor)) return Boolean.FALSE;
      }
      else
      {
         if (!Util.matchesClassExpr(node.getClazz(), refCon.getDeclaringClass(), advisor)) return Boolean.FALSE;
      }


      if (node.getConstructorAnnotation() != null)
      {
         String sub = node.getConstructorAnnotation().getOriginal().substring(1);
         if (ctCon != null)
         {
            if (!advisor.getConstructorMetaData().hasGroup(ctCon, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  if (!advisor.hasAnnotation(ctCon, sub)) return Boolean.FALSE;
               }
            }
         }
         else
         {
            if (!advisor.getConstructorMetaData().hasTag(refCon, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  try
                  {
                     if (!advisor.hasAnnotation(refCon, sub)) return Boolean.FALSE;
                  }
                  catch (Exception e)
                  {
                     throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
                  }
               }
            }
         }
      }
      
      //Match exceptions
      ArrayList<ASTException> nodeExceptions = node.getExceptions();
      if (nodeExceptions.size() > 0)
      {
         if (ctCon != null)
         {
            try
            {
               if (!Util.matchExceptions(nodeExceptions, ctCon.getExceptionTypes()))
               {
                  return Boolean.FALSE;
               }
            }
            catch (NotFoundException e)
            {
               throw new RuntimeException(e);
            }
         }
         else
         {
            if (!Util.matchExceptions(nodeExceptions, refCon.getExceptionTypes()))
            {
               return Boolean.FALSE;
            }
         }
      }

      if (ctCon != null)
      {
         if (!Util.matchesParameters(advisor, node, ctCon)) return Boolean.FALSE;
      }
      else
      {
         if (!Util.matchesParameters(advisor, node, refCon)) return Boolean.FALSE;
      }
      return Boolean.TRUE;
   }

   public Object visit(ASTAll node, Object data)
   {
      if (node.getClazz().isAnnotation())
      {
         String sub = node.getClazz().getOriginal().substring(1);
         if (ctCon != null)
         {
            if (!advisor.getConstructorMetaData().hasGroup(ctCon, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  if (!advisor.hasAnnotation(ctCon, sub))
                  {
                     return Boolean.FALSE;
                  }
               }
            }
         }
         else
         {
            if (!advisor.getConstructorMetaData().hasTag(refCon, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  try
                  {
                     if (!advisor.hasAnnotation(refCon, sub)) return Boolean.FALSE;
                  }
                  catch (Exception e)
                  {
                     throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
                  }
               }
            }
         }
      }
      else if (node.getClazz().isInstanceOf())
      {
         if (ctCon != null)
         {
            if (!Util.subtypeOf(ctCon.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
         }
         else if (!Util.subtypeOf(refCon.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;

      }
      else if (node.getClazz().isTypedef())
      {
         if (ctCon != null)
         {
            try
            {
               if (!Util.matchesTypedef(ctCon.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);
            }
         }
         else if (!Util.matchesTypedef(refCon.getDeclaringClass(), node.getClazz(), advisor)) return Boolean.FALSE;
      }
      else if (!node.getClazz().matches(classname))
      {
         return Boolean.FALSE;
      }

      return Boolean.TRUE;
   }
}
