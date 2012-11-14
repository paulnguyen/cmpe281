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
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.util.JavassistToReflect;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class OptimizedBehaviourInvocations extends OptimizedInvocations
{
   private static final AOPLogger logger = AOPLogger.getLogger(OptimizedBehaviourInvocations.class);
   
   /**
    * Name of method that enforces consistency between the values contained in typed
    * argument fields and those contained in arguments array.
    * This method is added to all optimized invocations that contain arguments field.
    */
   public static final String ENFORCE_ARGS_CONSISTENCY = "enforceArgsConsistency";
   
   protected static final String INVOKE_TARGET = "invokeTarget";
   
   static final String GET_ARGUMENTS = "getArguments";
   private static final String SET_ARGUMENTS = "setArguments";
   
   /**
    * Returns a piece of code that sets all typed argument fields to the
    * parameter values of current behaviour (i.e., arg0 = $1; arg1 = $2...).
    * 
    * @param length total number of parameters
    * @return code that sets all argument fields to the values of current
    *         behaviour parameters
    */
   protected static String setArguments(int length)
   {
      return setArguments("invocation", length, 0);
   }

   /**
    * Adds typed argument fields to <code>invocation</code> and overwrites its
    * <code>arguments</code> field accessor methods accordingly. 
    * 
    * @param pool                    class pool that contains <code>invocation<code>
    * @param invocation              invocation class to which fields and methods
    *                                will be added
    * @param params                  list of the parameter types
    * @param hasMarshalledArguments  indicates whether <code>invocation</code>has a
    *                                marshalled arguments field
    */
   protected static void addArgumentFieldsAndAccessors(ClassPool pool,
         CtClass invocation, CtClass[] params, boolean hasMarshalledArguments)
      throws NotFoundException, CannotCompileException
   {
      addArgumentFieldsToInvocation(invocation, params);
      addGetArguments(pool, invocation, params, hasMarshalledArguments);
      addSetArguments(pool, invocation, params);
      addEnforceArgsConsistency(invocation, params);
   }
   
   /**
    * Creates a method that dispatches execution to a joinpoint, and adds this method
    * to <code>invocation</code> class.
    * <br>
    * Except for its name, the generated method is constrained to have the same
    * signature as {@link org.jboss.aop.joinpoint.InvocationBase#invokeTarget()}.
    * 
    * @param invocation     optimized invocation class
    * @param methodName     name of the generated method
    * @param dispatchLine   line that dispatches the execution to joinpoint. This
    *                       line must not cointain <code>';'</code> nor brackets or
    *                       the arguments list.
    * @param params         joinpoint parameters type
    * @param beforeDispatch one or more lines of code that should be executed before
    *                       <code>dispatchLine</code> (this code must be complete,
    *                       without compilation errors)
    * @param afterDispatch  one or more lines of code that should be executed after
    *                       <code>dispatchLine</code>  (this code must be complete,
    *                       without compilation errors)
    * 
    * @throws NotFoundException
    * @throws CannotCompileException
    * 
    * @see org.jboss.aop.joinpoint.InvocationBase#invokeTarget()
    */
   protected static void addDispatch(CtClass invocation, String methodName,
         CtClass[] params, String dispatchLine, String beforeDispatch,
         String afterDispatch) throws NotFoundException, CannotCompileException
   {
      StringBuffer sb = new StringBuffer("{");
      sb.append(beforeDispatch);
      sb.append(ENFORCE_ARGS_CONSISTENCY).append("();");
      sb.append(dispatchLine);
      if (params.length == 0)
      {
         sb.append("();");
      }
      else
      {
         sb.append("(arg0");
         for (int i = 1; i < params.length; i++)
         {
            sb.append(", ");
            sb.append("arg");
            sb.append(i);
         }
         sb.append(");");
      }
      sb.append(afterDispatch);
      sb.append("}");
      CtMethod dispatch = null;
      CtMethod in = invocation.getSuperclass().getDeclaredMethod("invokeTarget");
      try
      {
         dispatch = CtNewMethod.make(
               in.getReturnType(), methodName,
               in.getParameterTypes(),
               in.getExceptionTypes(), sb.toString(),
               invocation);
      }
      catch (CannotCompileException e)
      {
         logger.error(sb.toString());
         throw e;
      }
      dispatch.setModifiers(in.getModifiers());
      invocation.addMethod(dispatch);
   }
   
   private static String setArguments(String inv, int length, int offset)
   {
      StringBuffer sb = new StringBuffer("");
      for (int i = 0 ; i < length ; i++)
      {
         sb.append(inv + ".arg" + (i) + " = $" + (i + 1 + offset) + "; ");
      }
      return sb.toString();
   }
   
   
   private static void addSetArguments(ClassPool pool, CtClass invocation, CtClass[] params)throws NotFoundException, CannotCompileException 
   {
      if (params.length == 0) return;
      CtClass methodInvocation = pool.get(MethodInvocation.class.getName());
      CtMethod template = methodInvocation.getDeclaredMethod(SET_ARGUMENTS);
   
      StringBuffer code = new StringBuffer("{");
      code.append("   inconsistentArgs = true;");
      code.append("   arguments = $1; ");
      code.append("}");
      CtMethod setArguments = null;
      try
      {
         setArguments = CtNewMethod.make(
            template.getReturnType(), template.getName(),
            template.getParameterTypes(),
            template.getExceptionTypes(), code.toString(),
            invocation);
      }
      catch(CannotCompileException e)
      {
         logger.error(code.toString());
         throw e;
      }
      setArguments.setModifiers(template.getModifiers());
      invocation.addMethod(setArguments);
   }

   private static void addGetArguments(ClassPool pool, CtClass invocation, CtClass[] params, boolean hasMarshalledArguments) throws CannotCompileException
   {
      try {
         CtClass methodInvocation = pool.get(MethodInvocation.class.getName());
         CtMethod template = methodInvocation.getDeclaredMethod(GET_ARGUMENTS);
         
         StringBuffer code = new StringBuffer();
         code.append("{ ");
         if (params.length != 0)
         {
            code.append("   inconsistentArgs = true;");
         }
         
         if (hasMarshalledArguments)
         {
            code.append("   if (super.marshalledArguments != null)");
            code.append("   {");
            code.append("      Object[] args = super.").append(GET_ARGUMENTS);
            code.append("();      ");
            code.append(SET_ARGUMENTS).append("(args);");
            code.append("      return args;");
            code.append("   }");
         }
         
         code.append("   if (arguments != (Object[])null) { return (Object[])arguments; } ");
         code.append("   arguments = new Object[" + params.length + "]; ");
         for (int i = 0; i < params.length; i++)
         {
            code.append("   arguments[" + i + "] = ($w)arg" + i + "; ");
         }
   
         code.append("   return arguments; }");
         CtMethod getArguments = null;
         try
         {
            getArguments = CtNewMethod.make(
               template.getReturnType(), template.getName(),
               template.getParameterTypes(),
               template.getExceptionTypes(), code.toString(),
               invocation);
         }
         catch(CannotCompileException e)
         {
            logger.error(code.toString());
            throw e;
         }
         
         getArguments.setModifiers(template.getModifiers());
         invocation.addMethod(getArguments);
      } catch (NotFoundException e) {
        //Field invocations don't have a getArguments() method, that's fine
      } 
   }
   
   /**
    * Creates a method called <code>enforceArgsConsistency</code>, and adds this
    * method to <code>invocation</code>
    * <p>
    * This method is responsible for updating typed argument fields with the current
    * values of <code>arguments</code>, if they are different.
    * 
    * @param invocation optimized invocation class
    * @param params     joinpoint parameter types
    * 
    * @throws CannotCompileException
    */
   private static void addEnforceArgsConsistency(CtClass invocation, CtClass[] params)
      throws CannotCompileException
   {
      StringBuffer code = new StringBuffer();
      code.append("{ ");
      if (params.length != 0)
      {
         code.append("if(inconsistentArgs) {");
         code.append("arg0 = ");
         code.append(JavassistToReflect.castInvocationValueToTypeString(params[0],
               "arguments[0]"));
         for (int i = 1; i < params.length; i++)
         {
            code.append("; arg").append(i).append('=');
            code.append(JavassistToReflect.castInvocationValueToTypeString(params[i],
                  "arguments[" + i + "]"));
         }
         code.append("; }");
      }
      code.append('}');
      
      CtMethod enforceArgsConsistency = null;
      try
      {
         enforceArgsConsistency = CtNewMethod.make(
               CtClass.voidType, ENFORCE_ARGS_CONSISTENCY,
               new CtClass[0],
               new CtClass[0], code.toString(),
               invocation);
      }
      catch(CannotCompileException e)
      {
         logger.error(code.toString());
         throw e;
      }
      enforceArgsConsistency.setModifiers(javassist.Modifier.FINAL);
      invocation.addMethod(enforceArgsConsistency); 
   }

   /** Adds fields arg0, arg1 etc. to the invocation class for storing the parameters for a method
    * 
    * @param invocation The invocation we want to add 
    * @param params Array of the types of the parameters
    * @throws CannotCompileException 
    */
   private static void addArgumentFieldsToInvocation(CtClass invocation, CtClass[] params)throws CannotCompileException
   {
      if (params.length == 0)
      {
         return;
      }
      CtField inconsistentArgs = new CtField(CtClass.booleanType, "inconsistentArgs",
            invocation);
      invocation.addField(inconsistentArgs, CtField.Initializer.byExpr("false"));
      
      for (int i = 0 ; i < params.length ; i++)
      {
         CtField field = new CtField(params[i], "arg" + i, invocation);
         field.setModifiers(Modifier.PUBLIC);
         invocation.addField(field);
      }
   }
}
