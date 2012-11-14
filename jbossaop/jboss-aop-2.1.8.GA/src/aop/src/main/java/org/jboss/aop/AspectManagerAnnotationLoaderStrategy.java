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

import java.io.StringReader;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.AspectFactory;
import org.jboss.aop.advice.AspectFactoryDelegator;
import org.jboss.aop.advice.AspectFactoryWithClassLoader;
import org.jboss.aop.advice.DynamicCFlowDefinition;
import org.jboss.aop.advice.GenericAspectFactory;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceDef;
import org.jboss.aop.advice.PrecedenceDefEntry;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.advice.ScopedInterceptorFactory;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.pointcut.CFlow;
import org.jboss.aop.pointcut.CFlowStack;
import org.jboss.aop.pointcut.DeclareDef;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.pointcut.Typedef;
import org.jboss.aop.pointcut.TypedefExpression;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class AspectManagerAnnotationLoaderStrategy implements AspectAnnotationLoaderStrategy
{
   
   public void deployAspect(AspectAnnotationLoader loader, boolean isFactory, String name, Scope scope)
   {
      AspectFactory factory = null;
      if (isFactory)
      {
         factory = new AspectFactoryDelegator(name, null);
         ((AspectFactoryWithClassLoader)factory).setClassLoader(loader.getClassLoader());
      }
      else
      {
         factory = new GenericAspectFactory(name, null);
         ((AspectFactoryWithClassLoader)factory).setClassLoader(loader.getClassLoader());
      }
      AspectDefinition def = new AspectDefinition(name, scope, factory);
      loader.getAspectManager().addAspectDefinition(def);
   }
   
   public void deployAspectMethodBinding(
         AspectAnnotationLoader loader, 
         org.jboss.aop.advice.AdviceType internalAdviceType, 
         String aspectDefName, 
         String methodName, 
         String bindingName,
         String pointcutString,
         String cflow,
         ASTCFlowExpression cflowExpression)throws Exception
   {
      AspectDefinition def = loader.getAspectManager().getAspectDefinition(aspectDefName);
      AdviceFactory factory = null;
      if (internalAdviceType == org.jboss.aop.advice.AdviceType.AROUND)
      {
         factory = new AdviceFactory(def, methodName);
      }
      else
      {
         factory = new AdviceFactory(def, methodName, internalAdviceType);
      }
      
      loader.getAspectManager().addInterceptorFactory(factory.getName(), factory);
      InterceptorFactory[] fact = {factory};
      PointcutExpression pointcut = new PointcutExpression(bindingName, pointcutString);
      AdviceBinding abinding = new AdviceBinding(bindingName, pointcut, cflowExpression, cflow, fact);
      loader.getAspectManager().addBinding(abinding);
   }
   
   public void undeployAspect(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removeAspectDefinition(name);
   }
   
   public void undeployAspectMethodBinding(AspectAnnotationLoader loader, String bindingName, String className, String methodName)
   {
      String adviceName = className + "." + methodName;
      loader.getAspectManager().removeInterceptorFactory(adviceName);
      loader.getAspectManager().removePointcut(bindingName);
      loader.getAspectManager().removeBinding(bindingName);
   }
   
   public void deployInterceptor(AspectAnnotationLoader loader, boolean isFactory, String name, Scope scope)
   {
      deployAspect(loader, isFactory, name, scope);
      AspectDefinition def = loader.getAspectManager().getAspectDefinition(name);
      ScopedInterceptorFactory factory = new ScopedInterceptorFactory(def);
      loader.getAspectManager().addInterceptorFactory(factory.getName(), factory);
   }
   
   public void deployInterceptorBinding(AspectAnnotationLoader loader, String name, String pointcutString, String cflow, ASTCFlowExpression cflowExpression) throws Exception
   {
      InterceptorFactory factory = loader.getAspectManager().getInterceptorFactory(name);
      InterceptorFactory[] inters = {factory};
      Pointcut p = null;
      p = new PointcutExpression(name, pointcutString);
      AdviceBinding binding = new AdviceBinding(name, p, cflowExpression, cflow, inters);
      loader.getAspectManager().addBinding(binding);

   }
   
   public void undeployInterceptor(AspectAnnotationLoader loader, String name)
   {
      undeployAspect(loader, name);
      loader.getAspectManager().removeInterceptorFactory(name);
   }
   
   public void undeployInterceptorBinding(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removePointcut(name);
      loader.getAspectManager().removeBinding(name);
   }
   
   public void deployDynamicCFlow(AspectAnnotationLoader loader, String name, String clazz)
   {
      loader.getAspectManager().addDynamicCFlow(name, new DynamicCFlowDefinition(null, clazz, name));
   }
   
   public void undeployDynamicCFlow(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removeDynamicCFlow(name);
   }
   
   public void deployPointcut(AspectAnnotationLoader loader, String name, String expr) throws Exception
   {
      Pointcut p = new PointcutExpression(name, expr);
      loader.getAspectManager().addPointcut(p);
   }
   
   public void undeployPointcut(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removePointcut(name);
   }
   
   public void deployPrecedence(AspectAnnotationLoader loader, String name, PrecedenceDefEntry[] pentries)
   {
      PrecedenceDef precedenceDef = new PrecedenceDef(name, pentries);
      loader.getAspectManager().addPrecedence(precedenceDef);
   }
   
   public void undeployPrecedence(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removePrecedence(name);
   }

   public void deployTypedef(AspectAnnotationLoader loader, String name, String expr) throws Exception
   {
      Typedef typedef = new TypedefExpression(name, expr);
      loader.getAspectManager().addTypedef(typedef);
   }
   
   public void undeployTypedef(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removeTypedef(name);
   }
   
   public void deployDeclare(AspectAnnotationLoader loader, String name, String expr, boolean warning, String msg) throws Exception
   {
      DeclareDef def = new DeclareDef(name, expr, warning, msg);
      loader.getAspectManager().addDeclare(def);
   }
   
   public void undeployDeclare(AspectAnnotationLoader loader, String name) throws Exception
   {
      loader.getAspectManager().removeDeclare(name);
   }

   public void deployAnnotationIntroduction(AspectAnnotationLoader loader, String expr, String annotation, boolean invisible)
   {
      AnnotationIntroduction annIntro = AnnotationIntroduction.createComplexAnnotationIntroduction(expr, annotation, invisible);
      loader.getAspectManager().addAnnotationIntroduction(annIntro);
   }
   
   public void undeployAnnotationIntroduction(AspectAnnotationLoader loader, String expr, String annotation, boolean invisible)
   {
      AnnotationIntroduction annIntro = AnnotationIntroduction.createComplexAnnotationIntroduction(expr, annotation, invisible);
      loader.getAspectManager().removeAnnotationIntroduction(annIntro);
   }
   
   public void deployCFlow(AspectAnnotationLoader loader, CFlowStackInfo info)
   {
      CFlowStack stack = new CFlowStack(info.getName());
      for (CFlowInfo cinfo : info.getCFlows())
      {
         stack.addCFlow(new CFlow(cinfo.getExpr(), cinfo.isNot()));
      }
      loader.getAspectManager().addCFlowStack(stack);
   }
   
   public void undeployCFlow(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removeCFlowStack(name);
   }
   
   public void deployInterfaceIntroduction(AspectAnnotationLoader loader, AspectAnnotationLoaderStrategy.InterfaceIntroductionInfo introduction) throws Exception
   {
      InterfaceIntroduction intro = null;
      if (introduction.getTarget() != null)
      {
         intro = new InterfaceIntroduction(introduction.getName(), introduction.getTarget(), introduction.getInterfaces(), introduction.getConstructorClass(), introduction.getConstructorMethod());
      }
      else
      {
         ASTStart start = new TypeExpressionParser(new StringReader(introduction.getExpr())).Start();
         intro = new InterfaceIntroduction(introduction.getName(), start, introduction.getInterfaces(), introduction.getConstructorClass(), introduction.getConstructorMethod());
      }

      if (introduction.getMixins() != null)
      {
         for (AspectAnnotationLoaderStrategy.InterfaceIntroductionMixinInfo mixin : introduction.getMixins())
         {
            intro.getMixins().add(new InterfaceIntroduction.Mixin(mixin.getClassname(), mixin.getInterfaces(), mixin.getConstruction(), mixin.isTrans()));
         }
      }
      
      loader.getAspectManager().addInterfaceIntroduction(intro);
   }
   
   public void undeployInterfaceIntroduction(AspectAnnotationLoader loader, String name)
   {
      loader.getAspectManager().removeInterfaceIntroduction(name);
   }
}