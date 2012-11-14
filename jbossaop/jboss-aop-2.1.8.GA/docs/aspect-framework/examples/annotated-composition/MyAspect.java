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
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.Bind;
import org.jboss.aop.Aspect;
import org.jboss.aop.PointcutDef;
import org.jboss.aop.pointcut.Pointcut;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
@Aspect (scope=Scope.PER_VM)
public class MyAspect
{
   @PointcutDef("execution(POJO->new(..))")
   public static Pointcut pojoConstructors;

   @PointcutDef("get(* POJO->*)")
   public static Pointcut pojoFieldReads;

   @PointcutDef("set(* POJO->*)")
   public static Pointcut pojoFieldWrites;

   @PointcutDef("execution(* POJO->*(..))")
   public static Pointcut pojoMethods;

   @PointcutDef("MyAspect.pojoFieldReads OR MyAspect.pojoFieldWrites")
   public static Pointcut pojoFields;

   @Bind(pointcut = "MyAspect.pojoFields OR MyAspect.pojoMethods OR MyAspect.pojoConstructors")
   public Object anotherPOJOAdvice(Invocation invocation) throws Throwable
   {
      try
      {
         if (invocation instanceof ConstructorInvocation)
         {
            System.out.println("<<< MyAspect.anotherPOJOAdvice - calling constructor");
         }
         else if (invocation instanceof MethodInvocation)
         {
            System.out.println("<<< MyAspect.anotherPOJOAdvice - calling method");
         }
         else if (invocation instanceof FieldReadInvocation)
         {
            System.out.println("<<< MyAspect.anotherPOJOAdvice - reading field");
         }
         else if (invocation instanceof FieldWriteInvocation)
         {
            System.out.println("<<< MyAspect.anotherPOJOAdvice - writing field");
         }
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving MyAspect.anotherPOJOAdvice");
      }
   }

}
