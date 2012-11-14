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
package org.jboss.aop.instrument;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedCallerTransformer extends CallerTransformer
{
   public OptimizedCallerTransformer(Instrumentor instrumentor, AspectManager manager)
   {
      super(instrumentor, manager, true, new ClassicCallerInfoAdder(instrumentor));
   }

   protected CallerExprEditor callerExprEditorFactory(ClassAdvisor advisor, CtClass clazz)
   {
      return new OptimizedCallerExprEditor(advisor, clazz);
   }

   class OptimizedCallerExprEditor extends CallerExprEditor
   {

      public OptimizedCallerExprEditor(ClassAdvisor advisor, CtClass callingClass)
      {
         super(advisor, callingClass);
      }
      
      protected void setupConstructor(ConstructorDetail cd)throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            String createName = getOptimizedMethodCalledByConstructorClassName(cd.callingIndex, callingClass.getName(), cd.calledHash);
            String invocationClassName = OptimizedCallerInvocations.createOptimizedMethodCalledByConInvocationClass(instrumentor, createName, callingClass, cd.calledMethod, cd.callingIndex, cd.calledHash);

            callerInfos.put(cd.callerInfoField, invocationClassName);
            callerInfoAdder.addMethodByConInfoField(callingClass, cd.callerInfoField, callingClass.getName(), cd.callingIndex, cd.classname, cd.calledHash);
         }
      }

      protected void setupMethod(MethodDetail md) throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(md.callerInfoField) == null)
         {
            String createName = getOptimizedMethodCalledByMethodClassName(md.callingHash, callingClass.getName(), md.calledHash);
            String invocationClassName = OptimizedCallerInvocations.createOptimizedMethodCalledByMethodInvocationClass(instrumentor, createName, callingClass, md.calledMethod, md.callingHash, md.calledHash);
                  
            callerInfos.put(md.callerInfoField, invocationClassName);
            callerInfoAdder.addMethodByMethodInfoField(callingClass, md.callerInfoField, md.callingHash, md.classname, md.calledHash);
         }         
      }

      protected void setupMethod(ConByMethodDetail cd) throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            //If target constructor is advisable we do not want to create an optimized invocation class
            //since the interceptor chain for its execution pointcut does not get invoked. 
            if (!cd.isTgtConAdvised)
            {
               String createName = getOptimizedConCalledByMethodInvocationClassName(cd.callingHash, callingClass.getName(), cd.calledHash);
               String invocationClassName = OptimizedCallerInvocations.createOptimizedConCalledByMethodInvocationClass(instrumentor, createName, callingClass, cd.calledConstructor, cd.callingHash, cd.calledHash);
               callerInfos.put(cd.callerInfoField, invocationClassName);
            }
            else
            {
               callerInfos.put(cd.callerInfoField, NonOptimizedCallerTransformer.PLACEHOLDER);
            }

            callerInfoAdder.addConByMethodInfoField(callingClass, cd.callerInfoField, cd.callingHash, cd.classname, cd.calledHash);
         }
      }
      
      protected void setupConstructor(ConByConDetail cd)throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            //If target constructor is advisable we do not want to create an optimized invocation class
            //since the interceptor chain for its execution pointcut does not get invoked. 
            if (!cd.isTgtConAdvised)
            {
               String createName = getOptimizedConCalledByConInvocationClassName(cd.callingIndex, callingClass.getName(), cd.calledHash);
               String invocationClassName = OptimizedCallerInvocations.createOptimizedConCalledByConInvocationClass(instrumentor, createName, callingClass, cd.calledConstructor, cd.callingIndex, cd.calledHash);
               callerInfos.put(cd.callerInfoField, invocationClassName);
            }
            else
            {
               callerInfos.put(cd.callerInfoField, NonOptimizedCallerTransformer.PLACEHOLDER);
            }
            callerInfoAdder.addConByConInfoField(callingClass, cd.callerInfoField, callingClass.getName(), cd.callingIndex, cd.classname, cd.calledHash);
         }
      }

      protected void replaceMethodCallInCon(ConstructorDetail cd)throws CannotCompileException, NotFoundException
      {
         String invocationClassName = callerInfos.get(cd.callerInfoField);
         String typedTargetObject = javassist.Modifier.isStatic(cd.calledMethod.getModifiers()) ? "" : "invocation.typedTargetObject=$0;";
   
         String replaced =
         methodByConInfoFromWeakReference("info", cd.callerInfoField) +
         "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
         invocationClassName + " invocation = new "
         + invocationClassName + "    (info, this, $0, $args, info.getInterceptors());" +
         typedTargetObject +
         OptimizedBehaviourInvocations.setArguments(cd.calledMethod.getParameterTypes().length) +
         "invocation.setTargetObject($0);" +
         "$_ = ($r)invocation.invokeNext();" +
         "} else { " +
         "$_ = $proceed($$); " +
         "}";
         
         cd.call.replace(replaced);
      }      
   
      protected void replaceMethodCallInMethod(MethodDetail md)throws NotFoundException, CannotCompileException
      {
         String callingObject = ", null";
         if (!Modifier.isStatic(md.where.getModifiers()))
         {
            callingObject = ", this";
         }

         String invocationClassName = callerInfos.get(md.callerInfoField);
         String typedTargetObject = javassist.Modifier.isStatic(md.calledMethod.getModifiers()) ? "" : "invocation.typedTargetObject=$0;";

         String replaced =
         methodByMethodInfoFromWeakReference("info", md.callerInfoField) +
         "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
         invocationClassName + " invocation = new "
         + invocationClassName + "    (info" + callingObject + ", $0, $args, info.getInterceptors());" +
         OptimizedBehaviourInvocations.setArguments(md.calledMethod.getParameterTypes().length) +
         typedTargetObject +
         "invocation.setTargetObject($0);" +
         "$_ = ($r)invocation.invokeNext();" +
         "} else { " +
         "$_ = $proceed($$); " +
         "}";

         md.call.replace(replaced);
      }

      protected void replaceConCallInMethod(ConByMethodDetail cd) throws NotFoundException, CannotCompileException
      {
         //If target constructor is advisable we do not want to create an optimized invocation class
         //since the interceptor chain for its execution pointcut does not get invoked. 
         if (cd.isTgtConAdvised)
         {
            super.replaceConCallInMethod(cd);
            return;
         }
         String callingObject = "null";
         if (!Modifier.isStatic(cd.where.getModifiers()))
         {
            callingObject = "this";
         }

         String invocationClassName = callerInfos.get(cd.callerInfoField);
         String replaced =
         conByMethodInfoFromWeakReference("info", cd.callerInfoField) +
         "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
         "java.lang.Object callingObject = " + callingObject + "; " +
         invocationClassName + " invocation = new "
         + invocationClassName + "    (info," + callingObject + ", $args, info.getInterceptors());" +
         OptimizedBehaviourInvocations.setArguments(cd.calledConstructor.getParameterTypes().length) +
         "$_ = ($r)invocation.invokeNext();" +
         "} else { " +
         "$_ = $proceed($$); " +
         "}";

         cd.call.replace(replaced);
      }   

      protected void replaceConCallInCon(ConByConDetail cd)throws CannotCompileException, NotFoundException
      {
         //If target constructor is advisable we do not want to create an optimized invocation class
         //since the interceptor chain for its execution pointcut does not get invoked. 
         if (cd.isTgtConAdvised)
         {
            super.replaceConCallInCon(cd);
            return;
         }
         String invocationClassName = callerInfos.get(cd.callerInfoField);

         String replaced =
         conByConInfoFromWeakReference("info", cd.callerInfoField) +
         "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
         invocationClassName + " invocation = new " 
         + invocationClassName + "(info, this, $args, info.getInterceptors());" +
         OptimizedBehaviourInvocations.setArguments(cd.calledConstructor.getParameterTypes().length) +
         "$_ = ($r)invocation.invokeNext();" +
         "} else { " +
         "$_ = $proceed($$); " +
         "}";

         cd.call.replace(replaced);
      }
   }

}
