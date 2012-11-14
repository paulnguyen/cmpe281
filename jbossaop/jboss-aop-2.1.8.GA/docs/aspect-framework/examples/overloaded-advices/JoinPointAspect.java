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

import org.jboss.aop.advice.annotation.JoinPoint;

import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.FieldAccess;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.MethodExecution;
import org.jboss.aop.joinpoint.MethodInvocation;

public class JoinPointAspect
{
   public Object aroundAdvice(ConstructorInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundAdvice on constructor of class: " +
            invocation.getConstructor().getDeclaringClass().getName());
      return invocation.invokeNext();
   }
   
   public Object aroundAdvice(MethodInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundAdvice on method execution: " +
            invocation.getMethod().getName());
      return invocation.invokeNext();
   }
   
   public Object aroundAdvice(FieldReadInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundAdvice on field read: " +
            invocation.getField().getName());
      return invocation.invokeNext();
   }
   
   public Object aroundAdvice(FieldWriteInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundAdvice on field write: " +
            invocation.getField().getName());
      return invocation.invokeNext();
   }
   
   public void otherTypeOfAdvice(@JoinPoint ConstructorExecution joinPoint)
   {
      System.out.println(">>> otherTypeOfAdvice on constructor of class: " +
            joinPoint.getConstructor().getDeclaringClass().getName());
   }
   
   public void otherTypeOfAdvice(@JoinPoint MethodExecution joinPoint)
   {
      System.out.println(">>> otherTypeOfAdvice on method execution: " +
            joinPoint.getMethod().getName());
   }
      
   public void otherTypeOfAdvice(@JoinPoint FieldAccess joinPoint)
   {
      System.out.println(">>> otherTypeOfAdvice on field" +
         (joinPoint.isRead()? "read: ": "write: ") +
         joinPoint.getField().getName());
   }
}