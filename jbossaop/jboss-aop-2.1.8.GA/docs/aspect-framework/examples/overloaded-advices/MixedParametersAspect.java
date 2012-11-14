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

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Caller;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Target;

import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodCall;

public class MixedParametersAspect
{
   public int overloadedAdvice(@Target POJO target)
   {
      System.out.println(">>> overloadedAdvice: int(Target POJO)");
      return 0;
   }

   public void overloadedAdvice(@JoinPoint ConstructorExecution joinPoint)
   {
      System.out.println(">>> overloadedAdvice: (JoinPoint ConstructorExecution)");
   }

   public void overloadedAdvice(@Target Object target)
   {
      System.out.println(">>> overloadedAdvice: (Target Object)");
   }

   public void overloadedAdvice(@JoinPoint MethodCall joinPoint, @Caller Driver driver)
   {
      System.out.println(">>> overloadedAdvice: (JoinPoint MethodCall, Caller Driver)");
   }

   public void overloadedAdvice(@JoinPoint JoinPointBean joinPoint, @Arg String arg)
   {
      System.out.println(">>> overloadedAdvice: JoinPoint JoinPointBean, Arg String");
   }
}