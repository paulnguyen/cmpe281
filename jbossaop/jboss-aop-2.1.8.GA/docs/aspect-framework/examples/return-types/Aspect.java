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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Return;

import org.jboss.aop.joinpoint.MethodInvocation;

public class Aspect
{
   public Object aroundDefaultSignature(MethodInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundDefaultSignature...");
      Object returnValue = invocation.invokeNext();
      System.out.println("<<< aroundDefaultSignature: returning invocation.invokeNext()");
      return returnValue;
   }
   
   public int aroundAnnotatedParam(@JoinPoint MethodInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundAnnotatedParam...");
      Object result = invocation.invokeNext();
      System.out.println("<<< aroundAnnotatedParam: returning 5000");
      return  5000;
   }
   
   public int afterOverwriteReturnValue(@Return int returnValue)
   {
      System.out.println(">>> afterOverwriteReturnValue: returning " + returnValue + "/5");
      return returnValue/5;
   }
   
   public void afterVoid()
   {
      System.out.println(">>> afterVoid");
   }
   
   public ArrayList aroundArrayList(@JoinPoint MethodInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundArrayList...");
      ArrayList list = (ArrayList) invocation.invokeNext();
      list.add("aroundCollection advice added this");
      System.out.println("<<< aroundArrayList: returning invocation.invokeNext() with new element added");
      return list;
   }
   
   public List afterUnmodifiableList(@Return List returnValue)
   {
      System.out.println(">>> afterUnmodifiableList: returning list transformed as UnmodifiableList");
      return Collections.unmodifiableList(returnValue);
   }
   
   public void aroundVoid(@JoinPoint MethodInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundVoid...");
      invocation.invokeNext();
      System.out.println("<<< aroundVoid");
   }
}