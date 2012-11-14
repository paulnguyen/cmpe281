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
package org.jboss.aop.advice;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javassist.CtClass;
import javassist.NotFoundException;

import org.jboss.aop.joinpoint.JoinPointBean;

/** Contains the properties of an advice method that we want to find.
 * Once found it is populated with the arguments
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70826 $
 */
public class AdviceMethodProperties
{
   public static final int JOINPOINT_ARG = -1;
   public static final int INVOCATION_ARG = -2;
   public static final int TARGET_ARG = -3;
   public static final int RETURN_ARG = -4;
   public static final int THROWABLE_ARG = -5;
   public static final int ARGS_ARG = -6;
   public static final int CALLER_ARG = -7;
   public static final int ARG_ARG = -8;
   
   public static final CtClass[] EMPTY_PARAMETERS = {};
   
   public static enum OptionalParameters {TARGET, TARGET_CALLER}
   
   // joinpoint this properties is associated with
   private JoinPointBean joinPoint;
   
   //find properties
   private Class<?> aspectClass;
   private String adviceName;
   private Class<?> thrownType;
   private Class<?> joinPointBeanType;
   private Class<?> invocationType;
   private Type target;
   private Type caller;
   private Type joinpointReturnType;
   private Type[] joinpointParameters;
   private Class<?>[] joinpointParameterClassTypes;
   private Type[] joinpointExceptions;
   private OptionalParameters optionalParameters;
   private boolean targetAvailable;
   private boolean callerAvailable;
   
   //found properties
   private Method adviceMethod;
   private int[] args;
   
   private boolean overloadedMethod;
   // TODO remove
   public AdviceMethodProperties(
         JoinPointBean joinPoint,
         Class<?> aspectClass,
         String adviceName,
         Class<?> joinPointBeanType,
         Class<?> invocationType,
         Type joinpointReturnType,
         Type[] joinpointParameters,
         Class<?>[] joinpointParameterClassTypes,
         Type[] joinpointExceptions,
         Type target,
         boolean targetAvailable)
   {
      this(joinPoint, aspectClass, adviceName, null, joinPointBeanType, invocationType,
            joinpointReturnType, joinpointParameters, joinpointParameterClassTypes,
            joinpointExceptions, target, targetAvailable);
   }
   
   public AdviceMethodProperties(
         JoinPointBean joinPoint,
         Class<?> aspectClass,
         String adviceName,
         Class<?> thrownType,
         Class<?> joinPointBeanType,
         Class<?> invocationType,
         Type joinpointReturnType,
         Type[] joinpointParameters,
         Class<?>[] joinpointParameterClassTypes,
         Type[] joinpointExceptions,
         Type target,
         boolean targetAvailable)
   {
      this.joinPoint = joinPoint;
      this.aspectClass = aspectClass;
      this.adviceName = adviceName;
      this.joinPointBeanType = joinPointBeanType;
      this.invocationType = invocationType;
      this.joinpointReturnType = joinpointReturnType;
      this.joinpointParameters = joinpointParameters;
      this.joinpointParameterClassTypes = joinpointParameterClassTypes;
      this.joinpointExceptions = joinpointExceptions;
      this.target = target;
      this.targetAvailable = targetAvailable;
      this.optionalParameters = OptionalParameters.TARGET;
   }
   // TODO remove
   public AdviceMethodProperties(
         JoinPointBean joinPoint,
         Class<?> aspectClass,
         String adviceName,
         Class<?> joinPointBeanType,
         Class<?> invocationType,
         Type joinpointReturnType,
         Type[] joinpointParameters,
         Class<?>[] joinpointParameterClassTypes,
         Type[] joinpointExceptions,
         Type target,
         boolean targetAvailable,
         Type caller,
         boolean callerAvailable)
   {
      this (joinPoint, aspectClass, adviceName, null, joinPointBeanType, invocationType,
            joinpointReturnType, joinpointParameters,
            joinpointParameterClassTypes, joinpointExceptions, target,
            targetAvailable, caller, callerAvailable);
   }
   
   public AdviceMethodProperties(
         JoinPointBean joinPoint,
         Class<?> aspectClass,
         String adviceName,
         Class<?> thrownType,
         Class<?> joinPointBeanType,
         Class<?> invocationType,
         Type joinpointReturnType,
         Type[] joinpointParameters,
         Class<?>[] joinpointParameterClassTypes,
         Type[] joinpointExceptions,
         Type target,
         boolean targetAvailable,
         Type caller,
         boolean callerAvailable)
   {
      this (joinPoint, aspectClass, adviceName, thrownType, joinPointBeanType, invocationType,
            joinpointReturnType, joinpointParameters,
            joinpointParameterClassTypes, joinpointExceptions, target, targetAvailable);
      this.caller = caller;
      this.callerAvailable = callerAvailable;
      this.optionalParameters = OptionalParameters.TARGET_CALLER;
   }

   public void setFoundProperties(Method adviceMethod, int[] args)
   {
      this.adviceMethod = adviceMethod;
      this.args = args;
   }

   public JoinPointBean getJoinPoint()
   {
      return this.joinPoint;
   }
   
   public String getAdviceName()
   {
      return adviceName;
   }


   public Class<?> getAspectClass()
   {
      return aspectClass;
   }

   public Class<?> getThrownType()
   {
      return this.thrownType;
   }

   public Class<?> getJoinPointBeanType()
   {
      return joinPointBeanType;
   }


   public Class<?> getInvocationType()
   {
      return invocationType;
   }


   public Type[] getJoinpointExceptions()
   {
      return joinpointExceptions;
   }


   public Type[] getJoinpointParameters()
   {
      return joinpointParameters;
   }

   public Class<?>[] getJoinpointParameterClassTypes()
   {
      return joinpointParameterClassTypes;
   }

   public Type getJoinpointReturnType()
   {
      return joinpointReturnType;
   }
   
   public boolean isAdviceVoid() throws NotFoundException
   {
      return adviceMethod.getReturnType().equals(void.class);
   }

   public Method getAdviceMethod()
   {
      return adviceMethod;
   }

   public int[] getArgs()
   {
      return args;
   }
   
   public Type getTargetType()
   {
      return this.target;
   }

   public boolean isTargetAvailable()
   {
      return this.targetAvailable;
   }
   
   public Type getCallerType()
   {
      return this.caller;
   }
   
   public boolean isCallerAvailable()
   {
      return this.callerAvailable;
   }
   
   public OptionalParameters getOptionalParameters()
   {
      return this.optionalParameters;
   }
   
   public void setOptionalParameters(OptionalParameters optionalParameters)
   {
      this.optionalParameters = optionalParameters;
   }
   
   public boolean isAdviceOverloaded()
   {
      return this.overloadedMethod;
   }
   
   public void setAdviceOverloaded(boolean overloaded)
   {
      this.overloadedMethod = overloaded;
   }
}