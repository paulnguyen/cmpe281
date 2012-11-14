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

import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.pointcut.ast.ParseException;

/**
 * Allows you to match an Invocation to a given joinpoint.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 46033 $
 */
public class JoinPointMatcher
{
   Pointcut p;

   /**
    * @param expr a pointcut expression to match against
    * @throws ParseException
    */
   public JoinPointMatcher(String expr) throws ParseException
   {
      p = new PointcutExpression("test", expr);
   }

   public boolean matches(Invocation invocation)
   {
      if (invocation instanceof MethodInvocation)
      {
         MethodInvocation mi = (MethodInvocation) invocation;
         PointcutMethodMatch pmatch = p.matchesExecution(mi.getAdvisor(), mi.getMethod());
         if (pmatch == null)
         {
            return false;
         }
         return pmatch.isMatch();
      }
      else if (invocation instanceof ConstructorInvocation)
      {
         ConstructorInvocation mi = (ConstructorInvocation) invocation;
         return p.matchesExecution(mi.getAdvisor(), mi.getConstructor());
      }
      else if (invocation instanceof FieldReadInvocation)
      {
         FieldReadInvocation mi = (FieldReadInvocation) invocation;
         return p.matchesGet(mi.getAdvisor(), mi.getField());
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         FieldWriteInvocation mi = (FieldWriteInvocation) invocation;
         return p.matchesSet(mi.getAdvisor(), mi.getField());
      }
      else if (invocation instanceof MethodCalledByMethodInvocation)
      {
         MethodCalledByMethodInvocation mi = (MethodCalledByMethodInvocation) invocation;
         return p.matchesCall(mi.getAdvisor(), mi.getCallingMethod(), mi.getCalledMethod().getDeclaringClass(), mi.getCalledMethod());
      }
      else if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         MethodCalledByConstructorInvocation mi = (MethodCalledByConstructorInvocation) invocation;
         return p.matchesCall(mi.getAdvisor(), mi.getCalling(), mi.getCalledMethod().getDeclaringClass(), mi.getCalledMethod());
      }
      else if (invocation instanceof ConstructorCalledByConstructorInvocation)
      {
         ConstructorCalledByConstructorInvocation mi = (ConstructorCalledByConstructorInvocation) invocation;
         return p.matchesCall(mi.getAdvisor(), mi.getCallingConstructor(), mi.getCalledConstructor().getDeclaringClass(), mi.getCalledConstructor());
      }
      else if (invocation instanceof ConstructorCalledByMethodInvocation)
      {
         ConstructorCalledByMethodInvocation mi = (ConstructorCalledByMethodInvocation) invocation;
         return p.matchesCall(mi.getAdvisor(), mi.getCallingMethod(), mi.getCalledConstructor().getDeclaringClass(), mi.getCalledConstructor());
      }
      throw new RuntimeException("UNKNOWN JOINPOINT TYPE: " + invocation.getClass().getName());
   }
}
