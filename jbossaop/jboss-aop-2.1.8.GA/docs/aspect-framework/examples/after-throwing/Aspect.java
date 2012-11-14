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
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Thrown;

import org.jboss.aop.joinpoint.MethodExecution;

public class Aspect
{
   public void afterThrowing(@Thrown Throwable thrown)
   {
      System.out.println(">>> afterThrowing: " + thrown);
   }
   
   public void afterThrowingJoinPoint(@JoinPoint MethodExecution methodExecution, @Thrown POJOException thrown)
   {
      System.out.println(">>> afterThrowingJoinPoint: " + thrown);
   }
   
   public void afterThrowingArg(@Thrown RuntimeException thrown, @Arg String argument)
   {
      System.out.println(">>> afterThrowingArg: " + thrown);
   }
   
   public void afterThrowingJoinPointArg(@Thrown NullPointerException thrown, @JoinPoint MethodExecution methodExecution, @Arg String argument)
   {
      System.out.println(">>> afterThrowingJoinPointArg: " + thrown);
   }
}