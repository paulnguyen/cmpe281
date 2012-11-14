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
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedCallerInvocations extends OptimizedBehaviourInvocations
{
   private static final AOPLogger logger = AOPLogger.getLogger(OptimizedBehaviourInvocations.class);

   protected static String createOptimizedMethodCalledByConInvocationClass(
         Instrumentor instrumentor, 
         String className, 
         CtClass callingClass, 
         CtMethod method, 
         int callingIndex, 
         long calledHash) throws NotFoundException, CannotCompileException
   {
      return createOptimizedMethodCalledInvocationClass(
            instrumentor, className, callingClass, method,
            "org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation",
            "this.calling, ");
   }
   
   protected static String createOptimizedMethodCalledByMethodInvocationClass(
         Instrumentor instrumentor, 
         String className, 
         CtClass callingClass, 
         CtMethod method, 
         long callingHash, 
         long calledHash)  throws NotFoundException, CannotCompileException
   {
      return createOptimizedMethodCalledInvocationClass(
            instrumentor, className, callingClass, method,
            "org.jboss.aop.joinpoint.MethodCalledByMethodInvocation",
            "this.callingClass, this.callingMethod, ");
   }

   protected static String createOptimizedConCalledByConInvocationClass(
         Instrumentor instrumentor, 
         String className, 
         CtClass callingClass, 
         CtConstructor con, 
         int callingIndex, 
         long calledHash)  throws NotFoundException, CannotCompileException
   {
      return createOptimizedConCalledInvocationClass(
            instrumentor, className, callingClass, con,
            "org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation",
            "this.calling, ");
   }
   
   protected static String createOptimizedConCalledByMethodInvocationClass(
         Instrumentor instrumentor, 
         String className, 
         CtClass callingClass, 
         CtConstructor con, 
         long callingHash, 
         long calledHash) throws NotFoundException, CannotCompileException
   {
      return createOptimizedConCalledInvocationClass(
            instrumentor, className, callingClass, con,
            "org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation",
            "this.callingClass, this.callingMethod, ");
   }
   
   private static String createOptimizedMethodCalledInvocationClass(
         Instrumentor instrumentor, String className, CtClass callingClass,
         CtMethod method, String invocationClassName, String callerDescription)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool pool = (AOPClassPool) instrumentor.getClassPool();
      CtClass methodInvocation = pool.get(invocationClassName);
   
      ////////////////
      //Create the class
      CtClass invocation = makeInvocationClass(pool, 
            true /*Modifier.isPrivate(method.getModifiers())*/, callingClass, className,
                  methodInvocation);
   
      ////////////////
      //Add typed fields
      CtClass[] params = method.getParameterTypes();
      addArgumentFieldsAndAccessors(pool, invocation, params, false);
      boolean isStatic = javassist.Modifier.isStatic(method.getModifiers());
      if (!isStatic)
      {
         CtField target = new CtField(
               method.getDeclaringClass(), "typedTargetObject", invocation);
         target.setModifiers(Modifier.PUBLIC);
         invocation.addField(target);
      }

      /////////
      //Create invokeTarget() body
      OptimizedMethodInvocations.addDispatch(invocation, INVOKE_TARGET, method,
            isStatic);
      
      ////////////////
      //Create copy() method
      String copy = "";
      if (!Modifier.isStatic(method.getModifiers()))
      {
         copy = "wrapper.typedTargetObject = typedTargetObject;";
      }
      addCopyMethod(invocation, callerDescription,
            "this.method, this.callingObject, this.targetObject, ", copy,
            params.length);
      
      /////////
      //Compile/Load
      TransformerCommon.compileOrLoadClass(callingClass, invocation);
      
      //Return fully qualified name of class (may be an inner class)
      return invocation.getName();
   }
   
   private static String createOptimizedConCalledInvocationClass(
         Instrumentor instrumentor, String className, CtClass callingClass,
         CtConstructor con, String invocationClassName, String callerDescription)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool pool = (AOPClassPool) instrumentor.getClassPool();
      CtClass conInvocation = pool.get(invocationClassName);
   
      ////////////////
      //Create the class
      CtClass invocation = makeInvocationClass(pool,
            /*Modifier.isPrivate(con.getModifiers())*/ true, callingClass, className,
            conInvocation);
   
      ////////////////
      //Add typed fields
      CtClass[] params = con.getParameterTypes();
      addArgumentFieldsAndAccessors(pool, invocation, params, false);

      /////////
      //Create invokeTarget() body
      OptimizedConstructorInvocations.addDispatch(invocation, "invokeTarget", con);
      
      ////////////////
      //Create copy() method
      addCopyMethod(invocation, callerDescription,
            "this.constructor, this.wrappingMethod, this.callingObject, ", "",
            params.length);

      ////////////////
      //Compile/Load
      TransformerCommon.compileOrLoadClass(callingClass, invocation);

      //Return fully qualified name of class (may be an inner class)
      return invocation.getName();
    }
   

   private static void addCopyMethod(CtClass invocation, String callerDescription,
         String calledDescription, String copyText, int paramsLength)
   throws NotFoundException, CannotCompileException
   {
      CtMethod copyTemplate = invocation.getSuperclass().getDeclaredMethod("copy");

      String copyCode = "{ "
         + "   "
         + invocation.getName()
         + " wrapper = new "
         + invocation.getName()
         + "(this.advisor, " + callerDescription + calledDescription + "this.arguments, this.interceptors);"
         + "   wrapper.metadata = this.metadata; "
         + "   wrapper.currentInterceptor = this.currentInterceptor; "
         + "   wrapper.instanceResolver = this.instanceResolver; "
         + "   wrapper.targetObject = this.targetObject; "
         + "   wrapper.responseContextInfo = this.responseContextInfo; "
         + copyText;
      
      for (int i = 0; i < paramsLength; i++)
      {
         copyCode += "   wrapper.arg" + i + " = this.arg" + i + "; ";
      }
      copyCode += "   return wrapper; }";
      
      CtMethod copy = null;
      try
      {
         copy = CtNewMethod.make(
               copyTemplate.getReturnType(), 
               "copy",
               copyTemplate.getParameterTypes(), 
               copyTemplate.getExceptionTypes(), 
               copyCode, 
               invocation);
      }
      catch (CannotCompileException e)
      {
         logger.error(copyCode);
         throw e;
      }
      copy.setModifiers(copyTemplate.getModifiers());
      invocation.addMethod(copy);
   }
}
