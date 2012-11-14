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

import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.util.JavassistToReflect;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OptimizedFieldInvocations extends OptimizedInvocations
{

   protected static String getOptimizedInvocationClassName(CtClass clazz, CtField field, boolean get) throws Exception
   {
      StringBuffer sb = new StringBuffer(clazz.getName());
      sb.append(".")
      .append(field.getName());
   
      if (get)
      {
         sb.append("_Get");
      }
      else
      {
         sb.append("_Set");
      }
   
      return sb.toString();
   }

   protected static void addCopy(ClassPool pool, CtClass invocation, boolean isStatic, boolean isGet) throws Exception
   {
      CtClass fieldInvocation =
              isGet
              ? pool.get("org.jboss.aop.joinpoint.FieldReadInvocation")
              : pool.get("org.jboss.aop.joinpoint.FieldWriteInvocation");
      CtMethod template = fieldInvocation.getDeclaredMethod("copy");
   
      String newExpr =
              (isGet)
              ? invocation.getName()
                + " wrapper = new "
                + invocation.getName()
                + "(this.field, this.index, this.interceptors); "
              : invocation.getName()
                + " wrapper = new "
                + invocation.getName()
                + "(this.field, this.index, this.value, this.interceptors); ";
   
      String code =
              "{ "
              + "   "
              + newExpr
              + "   wrapper.metadata = this.metadata; "
              + "   wrapper.currentInterceptor = this.currentInterceptor; "
              + "   wrapper.instanceResolver = this.instanceResolver; ";
      if (!isStatic)
      {
         code += "   wrapper.typedTargetObject = this.typedTargetObject; ";
         code += "   wrapper.targetObject = this.targetObject; ";
      }
   
      code += "   return wrapper; }";
   
      CtMethod copy =
         CtNewMethod.make(template.getReturnType(),
                          "copy",
                          template.getParameterTypes(),
                          template.getExceptionTypes(),
                          code,
                          invocation);
      copy.setModifiers(template.getModifiers());
      invocation.addMethod(copy);
   
   }

   protected static String createOptimizedInvocationClass(Instrumentor instrumentor, CtClass clazz, CtField field, boolean get) throws Exception
   {
      AOPClassPool pool = (AOPClassPool) instrumentor.getClassPool();
      CtClass fieldInvocation =
              get
              ? pool.get("org.jboss.aop.joinpoint.FieldReadInvocation")
              : pool.get("org.jboss.aop.joinpoint.FieldWriteInvocation");
   
      String className = getOptimizedInvocationClassName(clazz, field, get);
      boolean makeInnerClass = true; //!Modifier.isPublic(field.getModifiers());
   
      try
      {
         //In some cases we get a class frozen exception. I guess if the invocation class 
         //existed, method was unwrapped and the wrapped again
         CtClass existing = pool.get(className);
         if (existing.isFrozen())
         {
            existing.defrost();
         }
      }
      catch (NotFoundException e)
      {
         //Ignore, we are creating the class the first time
      }
      
      CtClass invocation = makeInvocationClass(pool, makeInnerClass, clazz, className, fieldInvocation);
      invocation.stopPruning(true);
   
      boolean isStatic = javassist.Modifier.isStatic(field.getModifiers());
      if (!isStatic)
      {
         CtField target = new CtField(field.getDeclaringClass(), "typedTargetObject", invocation);
         target.setModifiers(Modifier.PUBLIC);
         invocation.addField(target);
      }
      addCopy(pool, invocation, isStatic, get);
      
      setInvocationInvokeCode(invocation, field, get);
   
      TransformerCommon.compileOrLoadClass(field.getDeclaringClass(), invocation);
   
      //Return fully qualified name of class (may be an inner class)
      return invocation.getName();
   }

   /**
    * Creates the optimized invoke method.
    * @param instrumentor TODO
    * @param clazz the class declaring <code>field</code>
    * @param field the field whose interception invoke method will be redefined.
    * @param get indicates if the invoke is applied to the <code>field</code> read
    * or to the <code>field</code> write joinpoint.
    */
   private static void setInvocationInvokeCode(CtClass invocation, CtField field, boolean get) throws CannotCompileException, NotFoundException
   {
      CtMethod in = invocation.getSuperclass().getDeclaredMethod("invokeTarget");
   
      String code = "{";
   
      String ref = Modifier.isStatic(field.getModifiers()) ? field.getDeclaringClass().getName() + "." : " typedTargetObject.";
   
      if (get)
      {
         code += "return ($w) " + ref + field.getName() + ";";
      }
      else
      {
         //TODO: Must be a better way to do the cast of the value that what is done by castInvocationValueToTypeString()?
         CtClass type = field.getType();
         code += ref + field.getName() + " = " + JavassistToReflect.castInvocationValueToTypeString(type) + ";   return null;";
      }
   
      code += "}";
      CtMethod invokeTarget =
         CtNewMethod.make(in.getReturnType(),
                          in.getName(),
                          in.getParameterTypes(),
                          in.getExceptionTypes(),
                          code,
                          invocation);
      invokeTarget.setModifiers(in.getModifiers());
      invocation.addMethod(invokeTarget);         
   }


}
