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

import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.classpool.AOPClassPool;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedMethodInvocations extends OptimizedBehaviourInvocations
{
   static String getOptimizedInvocationClassName(CtClass clazz, CtMethod method) 
   {
      long hash = org.jboss.aop.util.JavassistMethodHashing.methodHash(method);
      StringBuffer sb = new StringBuffer(clazz.getName());
      sb.append(".")
      .append(method.getName())
      .append("_")
      .append(Long.toString(hash).replace('-', 'N'));
      return sb.toString();
   }

   public static String getOptimizedInvocationClassName(Method method) throws Exception
   {
      long hash = org.jboss.aop.util.MethodHashing.methodHash(method);
      StringBuffer sb = new StringBuffer(method.getDeclaringClass().getName());
      sb.append(".")
      .append(method.getName())
      .append("_")
      .append(Long.toString(hash).replace('-', 'N'));
      return sb.toString();
   }

   protected static String createOptimizedInvocationClass(Instrumentor instrumentor,
         CtClass clazz, CtMethod method, CtMethod notAdvisedMethod)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool pool = (AOPClassPool) instrumentor.getClassPool();
      CtClass methodInvocation = pool.get("org.jboss.aop.joinpoint.MethodInvocation");
   
      ////////////////
      //Create the class
      String className = getOptimizedInvocationClassName(clazz, method);
      boolean makeInnerClass = true; //!Modifier.isPublic(method.getModifiers());
      CtClass invocation = makeInvocationClass(pool, makeInnerClass, clazz, className, methodInvocation);
      
      ////////////////
      //Add typed fields
      CtClass[] params = method.getParameterTypes();
      addArgumentFieldsAndAccessors(pool, invocation, params, true);
      boolean isStatic = javassist.Modifier.isStatic(method.getModifiers());
      if (!isStatic)
      {
         CtField target = new CtField(method.getDeclaringClass(), "typedTargetObject", invocation);
         target.setModifiers(Modifier.PUBLIC);
         invocation.addField(target);
      }
   
      /////////
      //Create invokeTarget() body
      addDispatch(invocation, INVOKE_TARGET, notAdvisedMethod, isStatic);
      
      ////////////////
      //Create copy() method      
      addCopy(invocation, method.getParameterTypes(), isStatic);
   
      /////////
      //Compile/Load
      TransformerCommon.compileOrLoadClass(method.getDeclaringClass(), invocation);
   
      //Return fully qualified name of class (may be an inner class)
      return invocation.getName();
   }

   /**
    * Creates a method that dispatches execution to a method joinpoint,
    * and adds this method to <code>invocation</code> class.
    * 
    * @param invocation  invocation class
    * @param methodName  name of method to create
    * @param method      method to be executed on dispatch
    * 
    * @throws NotFoundException
    * @throws CannotCompileException
    */
   static final void addDispatch(CtClass invocation, String methodName,
         CtMethod method, boolean isStatic)
   throws NotFoundException, CannotCompileException
   {
      StringBuffer dispatchLine = new StringBuffer();
      boolean isVoid = method.getReturnType().equals(CtClass.voidType);
      if (!isVoid)
      {
         dispatchLine.append("return ($w)");
      }
      dispatchLine.append((isStatic? method.getDeclaringClass().getName():
         " typedTargetObject"));
      dispatchLine.append('.');
      dispatchLine.append(method.getName());
      addDispatch(invocation, methodName, method.getParameterTypes(),
            dispatchLine.toString(), "", isVoid?"  return null;": "");
   }

   static void addCopy(CtClass invocation, CtClass[] params, boolean isStatic)
   throws NotFoundException, CannotCompileException
   {
      CtMethod template = invocation.getSuperclass().getDeclaredMethod("copy");
   
      StringBuffer code = new StringBuffer("{");
      code.append("   ").append(invocation.getName()).append(" wrapper = new ");
      code.append(invocation.getName());
      code.append("(this.interceptors, methodHash, advisedMethod, unadvisedMethod, advisor); ");
      code.append("   wrapper.arguments = this.arguments; ");
      code.append("   wrapper.metadata = this.metadata; ");
      code.append("   wrapper.currentInterceptor = this.currentInterceptor; ");
      code.append("   wrapper.instanceResolver = this.instanceResolver; ");
      if (!isStatic)
      {
         code.append("   wrapper.typedTargetObject = this.typedTargetObject; ");
         code.append("   wrapper.targetObject = this.targetObject; ");
      }
      for (int i = 0; i < params.length; i++)
      {
         code.append("   wrapper.arg").append(i).append(" = this.arg").append(i).append("; ");
      }
      code.append("   return wrapper; }");
   
      CtMethod copy = CtNewMethod.make(template.getReturnType(), "copy",
            template.getParameterTypes(), template.getExceptionTypes(),
            code.toString(), invocation);
      copy.setModifiers(template.getModifiers());
      invocation.addMethod(copy);
   }
}