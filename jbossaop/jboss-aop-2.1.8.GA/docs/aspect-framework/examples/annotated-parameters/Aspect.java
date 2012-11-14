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
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.Caller;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Return;
import org.jboss.aop.advice.annotation.Target;

import org.jboss.aop.joinpoint.CallerInvocation;
import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.CurrentInvocation;
import org.jboss.aop.joinpoint.JoinPointBean;


public class Aspect
{
   // @JoinPoint
   
   public void beforeJoinPoint(@JoinPoint JoinPointBean joinPoint)
   {
      System.out.println(">>> beforeJoinPoint: JoinPointBean " + joinPoint);
   }
   
   public Object aroundJoinPoint(@JoinPoint CallerInvocation invocation) throws Throwable
   {
      System.out.println(">>> aroundJoinPoint: CallerInvocation " + invocation);
      return invocation.invokeNext();
   }
   
   public void afterJoinPoint(@JoinPoint ConstructorExecution joinPoint)
   {
      System.out.println(">>> afterJoinPoint: ConstructorExecution " + joinPoint);
   }
   
   // @Target
   
   public void beforeTarget(@Target Object target)
   {
      System.out.println(">>> beforeTarget: Object " + target);
   }
   
   public Object aroundTarget(@Target POJO target) throws Throwable
   {
      System.out.println(">>> aroundTarget: POJO " + target);
      return CurrentInvocation.proceed();
   }
   
   public void afterTarget(@Target POJO target) throws Throwable
   {
      System.out.println(">>> afterTarget: POJO " + target);
   }
   
   // @Caller
   
   public void beforeCaller(@Caller POJO caller)
   {
      System.out.println(">>> beforeCaller: POJO " + caller);
   }
   
   public Object aroundCaller(@Caller Driver target) throws Throwable
   {
      System.out.println(">>> aroundCaller: Driver " + target);
      return CurrentInvocation.proceed();
   }
   
   public void afterCaller(@Caller Object target)
   {
      System.out.println(">>> afterCaller: Object " + target);
   }
   
   // @Return
   
   public void afterFieldReturn(@Return Object joinPointReturn)
   {
      System.out.println(">>> afterFieldReturn: Object " + joinPointReturn);
   }
   
   public void afterMethodReturn(@Return boolean joinPointReturn)
   {
      System.out.println(">>> afterMethodReturn: boolean " + joinPointReturn);
   }
   
   // @Arg
   
   public void beforeConstructorArg(@Arg Object argument)
   {
      System.out.println(">>> beforeConstructorArg: Object \"" + argument + "\"");
   }
   
   public Object aroundConstructorArg(@Arg String argument) throws Throwable
   {
      System.out.println(">>> aroundConstructorArg: String \"" + argument + "\"");
      return CurrentInvocation.proceed();
   }
   
   public void afterArg(@Arg int argument)
   {
      System.out.println(">>> afterArg: int " + argument);
   }
   
   public void beforeMethodArg(@Arg long argument)
   {
      System.out.println(">>> beforeMethodArg: long " + argument + "L");
   }
   
   public void beforeMethodArg2(@Arg(index=2) long argument)
   {
      System.out.println(">>> beforeMethodArg2: long " + argument + "L");
   }
   
   // @Args
   
   public void beforeArgs(@Args Object[] arguments)
   {
      System.out.print(">>> beforeArgs changing arguments: from ");
      printArgs(arguments);
      arguments[3] = "overridenString";
      arguments[1] = Integer.valueOf(((Integer)arguments[1]).intValue() - 50);
      System.out.print("                                   to ");
      printArgs(arguments);
   }
   
   public Object aroundArgs(@Args Object[] arguments) throws Throwable
   {
      System.out.print(">>> aroundArgs: arguments ");
      printArgs(arguments);
      return CurrentInvocation.proceed();
   }
   
   public void afterArgs(@Args Object[] arguments)
   {
      System.out.print(">>> afterArgs: arguments ");
      printArgs(arguments);
   }
   
   private void printArgs(Object[] arguments)
   {
      if (arguments == null)
      {
         System.out.println(arguments);
         return;
      }
      System.out.print("[");
      if (arguments.length > 0)
      {
         System.out.print(arguments[0]);
         for (int i = 1; i < arguments.length; i++)
         {
            System.out.print(", " + arguments[i]);
         }
      }
      System.out.println("]");
   }
   
   // empty parameter list
   
   public void beforeNoParameters()
   {
      System.out.println(">>> beforeNoParameters");
   }
   
   public Object aroundNoParameters() throws Throwable
   {
      System.out.println(">>> aroundNoParameters");
      return CurrentInvocation.proceed();
   }
   
   public void afterNoParameters()
   {
      System.out.println(">>> afterNoParameters");
   }
}