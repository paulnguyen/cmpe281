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
package org.jboss.injbossaop.lib;

import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.PointcutDef;
import org.jboss.aop.TypeDef;
import org.jboss.aop.Bind;
import org.jboss.aop.InterceptorDef;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.Typedef;

/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
@InterceptorDef
@Bind (pointcut="org.jboss.injbossaop.lib.SimpleInterceptor.valueConstructors OR org.jboss.injbossaop.lib.SimpleInterceptor.valueMessage OR org.jboss.injbossaop.lib.SimpleInterceptor.service OR org.jboss.injbossaop.lib.SimpleInterceptor.sessionValue OR org.jboss.injbossaop.lib.SimpleInterceptor.mbeans")
public class SimpleInterceptor implements Interceptor
{
   @PointcutDef ("execution(org.jboss.injbossaop.lib.ExampleValue->new(..))")
   public static Pointcut valueConstructors;

   @PointcutDef ("execution(* org.jboss.injbossaop.lib.ExampleValue->getMessage())")
   public static Pointcut valueMessage;

   @TypeDef ("class($instanceof{javax.servlet.http.HttpServlet}) AND class(org.jboss.injbossaop.web.*)")
   public static Typedef servlets;

   @PointcutDef ("execution(* $typedef{org.jboss.injbossaop.lib.SimpleInterceptor.servlets}->service(..))")
   public static Pointcut service;

   @TypeDef ("class($instanceof{javax.ejb.SessionBean}) AND class(org.jboss.injbossaop.ejb.*)")
   public static Typedef sessionBeans;

   @PointcutDef ("execution(* $typedef{org.jboss.injbossaop.lib.SimpleInterceptor.sessionBeans}->getValue(..))")
   public static Pointcut sessionValue;

   @PointcutDef ("all(org.jboss.injbossaop.mbean.*)")
   public static Pointcut mbeans;

   public String getName() { return "SimpleInterceptor"; }

   public Object invoke(Invocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< Entering SimpleInterceptor: " + invocationInfo(invocation));
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving SimpleInterceptor");
      }
   }

   private String invocationInfo(Invocation invocation)
   {
      StringBuffer info = new StringBuffer("\n\tinvocation class: " + invocation.getClass().getName());

      if (invocation instanceof MethodInvocation)
      {
         MethodInvocation mi = (MethodInvocation)invocation;
         info.append("\n\ttype: Method Invocation");
         info.append("\n\tmethod: " + mi.getMethod().getName());
         info.append("\n\tClass containing method: " + mi.getTargetObject().getClass().getName());
      }
      else if (invocation instanceof ConstructorInvocation)
      {
         ConstructorInvocation ci = (ConstructorInvocation)invocation;
         info.append("\n\ttype: Constructor Invocation");
         info.append("\n\tconstructor: " + ci.getConstructor());
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         FieldWriteInvocation fi = (FieldWriteInvocation)invocation;
         info.append("\n\ttype: Field Write Invocation");
         info.append("\n\tfield: " + fi.getField());

      }
      else if (invocation instanceof FieldReadInvocation)
      {
         FieldReadInvocation fi = (FieldReadInvocation)invocation;
         info.append("\n\ttype: Field Write Invocation");
         info.append("\n\tfield: " + fi.getField());
      }

      return info.toString();
   }
}
