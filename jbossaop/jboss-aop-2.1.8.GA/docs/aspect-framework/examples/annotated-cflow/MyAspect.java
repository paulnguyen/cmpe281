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
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.Bind;
import org.jboss.aop.Aspect;
import org.jboss.aop.CFlowDef;
import org.jboss.aop.CFlowStackDef;
import org.jboss.aop.pointcut.CFlowStack;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
@Aspect (scope=Scope.PER_VM)
public class MyAspect
{
   @CFlowStackDef (cflows={@CFlowDef(expr = "void POJO->method1()", called=true), @CFlowDef(expr = "void POJO->method2()", called=true), @CFlowDef(expr = "void POJO->method3()", called=true)})
   public static CFlowStack cf123Before;

   @CFlowStackDef (cflows={@CFlowDef(expr = "POJO->new()", called=true), @CFlowDef(expr = "void POJO->method3()", called=true)})
   public static CFlowStack cf1ConAnd3;

   @Bind (pointcut="execution(void POJO->method4())", cflow="(MyAspect.cf123Before OR MyAspect.cf123Before)")
   public Object methodAdvice(MethodInvocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< MyAdvice.methodAdvice accessing: " + invocation.getMethod().toString());
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving MyAdvice.methodAdvice");
      }
   }

   @CFlowStackDef (cflows={@CFlowDef(expr = "void POJO->recursive(int)", called=false), @CFlowDef(expr = "void POJO->recursive(int)", called=true), @CFlowDef(expr = "void POJO->recursive(int)", called=false)})
   public static CFlowStack cf2Recursions;

   @Bind (pointcut="execution(void POJO->recursive(int))", cflow="MyAspect.cf2Recursions")
   public Object recursiveAdvice(MethodInvocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< MyAdvice.recursiveAdvice accessing: " + invocation.getMethod().toString());
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving MyAdvice.recursiveAdvice");
      }
   }

}
