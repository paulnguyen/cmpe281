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
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jboss.aop.classpool.AOPClassPool;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedConstructorInvocations extends
      OptimizedBehaviourInvocations
{
   /**
    * Returns the name of the optimized Invocation class.
    * @param declaringClazz the class that contains the constructor.
    * @param constructorIndex the index of the constructor.
    * @return the name of the optimized Invocation class.
    */
   protected static String getOptimizedInvocationClassName(CtClass declaringClazz, int constructorIndex)
   {
      return declaringClazz.getName() + ".AOP" + constructorIndex + "OptimizedConstructorInvocation";
   }

   protected static String createOptimizedInvocationClass(Instrumentor instrumentor,
         CtClass clazz, CtConstructor con, int index)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool pool = (AOPClassPool) instrumentor.getClassPool();
      CtClass conInvocation = pool.get("org.jboss.aop.joinpoint.ConstructorInvocation");

      ////////////////
      //Create the class
      String className = getOptimizedInvocationClassName(clazz, index);
      boolean makeInnerClass = true;//!Modifier.isPublic(con.getModifiers());
      CtClass invocation = makeInvocationClassNoCtors(pool, makeInnerClass,
            clazz, className, conInvocation);
      
      CtConstructor template = conInvocation.getDeclaredConstructors()[0];
      CtConstructor icon = CtNewConstructor.make(template.getParameterTypes(),
            template.getExceptionTypes(), invocation);
      invocation.addConstructor(icon);

      ////////////////
      //Add typed fields
      CtClass[] params = con.getParameterTypes();
      addArgumentFieldsAndAccessors(pool, invocation, params, false);
      
      /////////
      //Create invokeTarget() body
      addDispatch(invocation, INVOKE_TARGET, con);
      
      ////////////////
      //Create copy() method
      addCopy(invocation, con.getParameterTypes());
      
      /////////
      //Compile/Load
      TransformerCommon.compileOrLoadClass(clazz, invocation);
      
      //Return fully qualified name of class (may be an inner class)
      return invocation.getName();
   }

   /**
    * Creates a method that dispatches execution to a constructor joinpoint,
    * and adds this method to <code>invocation</code> class.
    * 
    * @param invocation   invocation class
    * @param methodName   name of method to create
    * @param constructor  constructor to be executed on dispatch
    * 
    * @throws NotFoundException
    * @throws CannotCompileException
    */
   public static final void addDispatch(CtClass invocation, String methodName,
         CtConstructor constructor)
   throws NotFoundException, CannotCompileException
   {
      StringBuffer dispatchLine = new StringBuffer("   result = new ");
      dispatchLine.append(constructor.getDeclaringClass().getName());
      OptimizedBehaviourInvocations.addDispatch(invocation,
            methodName, constructor.getParameterTypes(), dispatchLine.toString(),
            "Object result = null;", "   setTargetObject(result);   return result;");
   }
   
   private static void addCopy(CtClass invocation, CtClass[] params)
   throws CannotCompileException, NotFoundException
   {
      CtMethod template = invocation.getSuperclass().getDeclaredMethod("copy");

      StringBuffer code = new StringBuffer("{    ");
      code.append(invocation.getName()).append(" wrapper = new ");
      code.append(invocation.getName()).append("(this.interceptors); ");
      code.append("   wrapper.constructor = this.constructor; ");
      code.append("   wrapper.arguments = this.arguments; ");
      code.append("   wrapper.metadata = this.metadata; ");
      code.append("   wrapper.currentInterceptor = this.currentInterceptor; ");
      
      for (int i = 0; i < params.length; i++)
      {
         code.append("   wrapper.arg" + i + " = this.arg" + i + "; ");
      }
      code.append("   return wrapper; }");

      CtMethod copy = CtNewMethod.make(template.getReturnType(), "copy",
            template.getParameterTypes(), template.getExceptionTypes(),
            code.toString(), invocation);
      copy.setModifiers(template.getModifiers());
      invocation.addMethod(copy);
   }
}