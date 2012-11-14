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

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTAll;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTException;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ClassExpression;

import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class MethodMatcher extends MatcherHelper
{
   protected Advisor advisor;
   protected CtMethod ctMethod;
   protected Method refMethod;
   protected int methodModifiers;
   protected String classname;
   protected String methodName;
   protected Class<?> matchedClass;
   protected int matchLevel;//0 if matches the exact class, 1 if the direct superclass etc.
   protected boolean isInstanceof;

   public MethodMatcher(Advisor advisor, CtMethod method, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.start = start;
      this.methodModifiers = method.getModifiers();
      this.classname = method.getDeclaringClass().getName();
      this.ctMethod = method;
      this.methodName = ctMethod.getName();
   }

   public MethodMatcher(Advisor advisor, Method method, ASTStart start)
   {
      super(start, advisor.getManager());
      this.advisor = advisor;
      this.start = start;
      this.methodModifiers = method.getModifiers();
      this.classname = method.getDeclaringClass().getName();
      this.refMethod = method;
      this.methodName = refMethod.getName();
   }

   public Class<?> getMatchedClass()
   {
      return matchedClass;
   }
 
   public int getMatchLevel()
   {
      return matchLevel;
   }
   
   public boolean isInstanceOf()
   {
      return isInstanceof;
   }
 
   protected Boolean resolvePointcut(Pointcut p)
   {
      throw new RuntimeException("SHOULD NOT BE CALLED");
   }

   public Object visit(ASTMethod node, Object data)
   {
      return matches(node);
   }

   public Boolean matches(ASTMethod node)
   {
      if (!matchesModifiers(node)) return Boolean.FALSE;
      if (!matchesClass(node)) return Boolean.FALSE;
      if (!matchesIdentifier(node))return Boolean.FALSE;
      if (!matchesExceptions(node))return Boolean.FALSE;
      if (!matchesReturnType(node)) return Boolean.FALSE;
      if (!matchesParameters(node)) return Boolean.FALSE;

      return Boolean.TRUE;
   }

   public Object visit(ASTAll node, Object data)
   {
      Boolean matches = Boolean.FALSE;
      ClassExpression expr = node.getClazz();
      
      if (ctMethod != null)
      {
         return classMatchesAll(expr);
      }
      else
      {
         Class<?> declaringClass = MatcherStrategy.getMatcher(advisor).getDeclaringClass(advisor, refMethod);
         if (!advisor.chainOverridingForInheritedMethods())
         {
            matches = classMatchesAll(expr);
         }
         else
         {
            Class<?> advisedClass = advisor.getClazz();
            matchedClass = advisedClass;
            while (matchedClass != null)
            {
               if (classMatchesAll(node.getClazz()).booleanValue())
               {
                  if (node.getClazz().isInstanceOf())
                  {
                     isInstanceof = true;
                  }
                  return Boolean.TRUE;
               }
               
               if (matchedClass == declaringClass)
               {
                  break;
               }
               matchedClass = matchedClass.getSuperclass();
               matchLevel++;
            }
         }
      }      
      return matches;
   }
   
   public Boolean classMatchesAll(ClassExpression expr)
   {
      if (expr.isAnnotation())
      {
         String sub = expr.getOriginal().substring(1);
         if (ctMethod != null)
         {
            if (!advisor.getMethodMetaData().hasGroup(ctMethod, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  if (!advisor.hasAnnotation(ctMethod, sub)) return Boolean.FALSE;
               }
            }
         }
         else
         {
            if (!advisor.getMethodMetaData().hasTag(refMethod, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  try
                  {
                     if (!advisor.hasAnnotation(refMethod, sub)) return Boolean.FALSE;
                  }
                  catch (Exception e)
                  {
                     throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
                  }
               }
            }
         }
      }
      else if (expr.isInstanceOf())
      {
         if (ctMethod != null)
         {
            if (!Util.subtypeOf(ctMethod.getDeclaringClass(), expr, advisor)) return Boolean.FALSE;
         }
         else if (!Util.subtypeOf(refMethod.getDeclaringClass(), expr, advisor)) return Boolean.FALSE;

      }
      else if (expr.isTypedef())
      {
         if (ctMethod != null)
         {
            try
            {
               if (!Util.matchesTypedef(ctMethod.getDeclaringClass(), expr, advisor)) return Boolean.FALSE;
            }
            catch (Exception e)
            {
               throw new RuntimeException(e);
            }
         }
         else if (!Util.matchesTypedef(refMethod.getDeclaringClass(), expr, advisor)) return Boolean.FALSE;
      }
      else if (!expr.matches(classname))
      {
         return Boolean.FALSE;
      }

      return Boolean.TRUE;
   }
   
   protected boolean matchesModifiers(ASTMethod node)
   {
      if (node.getAttributes().size() > 0)
      {
         for (int i = 0; i < node.getAttributes().size(); i++)
         {
            ASTAttribute attr = (ASTAttribute) node.getAttributes().get(i);
            if (!Util.matchModifiers(attr, methodModifiers)) return false;
         }
      }
      return true;
   }

   protected boolean matchesClass(ASTMethod node)
   {
      if (ctMethod != null)
      {
         if (!Util.matchesClassExpr(node.getClazz(), ctMethod.getDeclaringClass(), advisor)) return false;
      }
      else
      {
         Class<?> declaringClass = MatcherStrategy.getMatcher(advisor).getDeclaringClass(advisor, refMethod);
         if (!advisor.chainOverridingForInheritedMethods())
         {
            if (Util.matchesClassExpr(node.getClazz(), declaringClass, advisor))
            {
               matchedClass = declaringClass;
               return true;
            }
            return false;
         }
         else
         {
            Class<?> advisedClass = advisor.getClazz();
            if (advisor.getClazz() == null)
            {
               throw new RuntimeException("Advisor is null");
            }
            matchedClass = advisedClass;
            while (matchedClass != null)
            {
               if (Util.matchesClassExpr(node.getClazz(), matchedClass, advisor))
               {
                  if (node.getClazz().isInstanceOf())
                  {
                     isInstanceof = true;
                  }
                  return true;
               }
               
               if (matchedClass == declaringClass)
               {
                  break;
               }
               matchedClass = matchedClass.getSuperclass();
               matchLevel++;
            }
            return false;
         }
      }
      return true;
   }
   
   protected boolean matchesIdentifier(ASTMethod node)
   {
      if (node.getMethodIdentifier().isAnnotation())
      {
         if (advisor == null) return false;
         String sub = node.getMethodIdentifier().getOriginal().substring(1);
         if (ctMethod != null)
         {
            if (!advisor.getMethodMetaData().hasGroup(ctMethod, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  if (!advisor.hasAnnotation(ctMethod, sub)) return false;
               }
            }
         }
         else
         {
            if (!advisor.getMethodMetaData().hasTag(refMethod, sub))
            {
               if (!advisor.getDefaultMetaData().hasTag(sub))
               {
                  try
                  {
                     if (!advisor.hasAnnotation(refMethod, sub)) return false;
                  }
                  catch (Exception e)
                  {
                     throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
                  }
               }
            }
         }
      }
      else if (node.getMethodIdentifier().isImplements() || node.getMethodIdentifier().isImplementing())
      {
         try
         {
            boolean exactSuper = node.getMethodIdentifier().isImplements(); //Implementing will check all super classes
            ClassExpression implemented = node.getMethodIdentifier().getImplementsExpression();
            if (ctMethod != null)
            {
               if (Util.methodExistsInSuperClassOrInterface(ctMethod, implemented, exactSuper))
               {
                  return true;
               }
            }
            else
            {
               if (Util.methodExistsInSuperClassOrInterface(refMethod, implemented, exactSuper, advisor))
               {
                  return true;
               }
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
         return false;
      }
      else
      {
         if (!node.getMethodIdentifier().matches(methodName)) return false;
      }
      return true;
   }
      
   protected boolean matchesExceptions(ASTMethod node)
   {
      //Match exceptions
      ArrayList<ASTException> nodeExceptions = node.getExceptions();
      if (nodeExceptions.size() > 0)
      {
         if (ctMethod != null)
         {
            try
            {
               if (!Util.matchExceptions(nodeExceptions, ctMethod.getExceptionTypes()))
               {
                  return false;
               }
            }
            catch (NotFoundException e)
            {
               throw new RuntimeException(e);
            }
         }
         else
         {
            if (!Util.matchExceptions(nodeExceptions, refMethod.getExceptionTypes()))
            {
               return false;
            }
         }
      }
      return true;
   }
   
   protected boolean matchesReturnType(ASTMethod node)
   {
      try
      {
         if (ctMethod != null)
         {
            if (!Util.matchesClassExpr(node.getReturnType(), ctMethod.getReturnType(), advisor)) return false;
         }
         else
         {
            if (!Util.matchesClassExpr(node.getReturnType(), refMethod.getReturnType(), advisor)) return false;
         }
      }
      catch (NotFoundException nfe)
      {
         throw new RuntimeException(nfe);
      }
      
      return true;
   }
   
   protected boolean matchesParameters(ASTMethod node)
   {
      if (ctMethod != null)
      {
         return Util.matchesParameters(advisor, node, ctMethod);
      }
      else
      {
         return Util.matchesParameters(advisor, node, refMethod);
      }
   }
}
