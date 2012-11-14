/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop;

import org.jboss.aop.advice.AdviceType;
import org.jboss.aop.advice.PrecedenceDefEntry;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;

/**
 * Default implementation of AspectAnnotationLoaderStrategy.
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class AspectAnnotationLoaderStrategySupport implements AspectAnnotationLoaderStrategy
{

   public void deployAnnotationIntroduction(AspectAnnotationLoader loader, String expr, String annotation,
         boolean invisible)
   {
   }

   public void deployAspect(AspectAnnotationLoader loader, boolean isFactory, String name, Scope scope)
   {
   }

   public void deployAspectMethodBinding(AspectAnnotationLoader loader, AdviceType internalAdviceType,
         String aspectDefName, String methodName, String bindingName, String pointcutString, String cflow,
         ASTCFlowExpression cflowExpression) throws Exception
   {
   }

   public void deployCFlow(AspectAnnotationLoader loader, CFlowStackInfo stack)
   {
   }

   public void deployDeclare(AspectAnnotationLoader loader, String name, String expr, boolean warning, String msg)
         throws Exception
   {
   }

   public void undeployDeclare(AspectAnnotationLoader loader, String name)
   throws Exception
   {
   }

   public void deployDynamicCFlow(AspectAnnotationLoader loader, String name, String clazz)
   {
   }

   public void deployInterceptor(AspectAnnotationLoader loader, boolean isFactory, String name, Scope scope)
   {
   }

   public void deployInterceptorBinding(AspectAnnotationLoader loader, String name, String pointcutString,
         String cflow, ASTCFlowExpression cflowExpression) throws Exception
   {
   }

   public void deployInterfaceIntroduction(AspectAnnotationLoader loader, InterfaceIntroductionInfo introduction)
   {
   }

   public void deployPointcut(AspectAnnotationLoader loader, String name, String expr) throws Exception
   {
   }

   public void deployPrecedence(AspectAnnotationLoader loader, String name, PrecedenceDefEntry[] pentries)
   {
   }

   public void deployTypedef(AspectAnnotationLoader loader, String name, String expr) throws Exception
   {
   }

   public void undeployAnnotationIntroduction(AspectAnnotationLoader loader, String expr, String annotation,
         boolean invisible)
   {
   }

   public void undeployAspect(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployAspectMethodBinding(AspectAnnotationLoader loader, String bindingName, String className,
         String methodName)
   {
   }

   public void undeployCFlow(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployDynamicCFlow(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployInterceptor(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployInterceptorBinding(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployInterfaceIntroduction(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployPointcut(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployPrecedence(AspectAnnotationLoader loader, String name)
   {
   }

   public void undeployTypedef(AspectAnnotationLoader loader, String name)
   {
   }

}
