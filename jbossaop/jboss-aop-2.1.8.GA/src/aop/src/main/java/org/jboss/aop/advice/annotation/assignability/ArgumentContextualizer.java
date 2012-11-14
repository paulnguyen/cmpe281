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
package org.jboss.aop.advice.annotation.assignability;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Argument contextualizer. Performs contextualization of arguments through hierarchy
 * 
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class ArgumentContextualizer
{
   public static final Type[] getContextualizedArguments(ParameterizedType paramType,
         Class<?> rawType, Class<?> desiredType)
   {
      ArgumentContextualizer contextualizedArguments = getContextualizedArgumentsInternal(
            desiredType, rawType);
      if (contextualizedArguments == null)
      {
         return null;
      }
      if (paramType != null)
      {
         contextualizedArguments.contextualizeVariables(null, paramType);
      }
      return contextualizedArguments.getArguments();
   }

   
   private static final ArgumentContextualizer getContextualizedArgumentsInternal(
         Class<?> desiredType, Class<?> classType)
   {
      Type superType = null;
      if (desiredType.isInterface())
      {
         for (Type superInterface : classType.getGenericInterfaces())
         {
            if ((superInterface instanceof Class && desiredType
                  .isAssignableFrom((Class<?>) superInterface))
                  || (superInterface instanceof ParameterizedType &&
                        desiredType.isAssignableFrom((Class<?>)
                              ((ParameterizedType) superInterface).getRawType())))
            {
               superType = superInterface;
               break;
            }
         }
      }
      if (superType == null)
      {
         superType = classType.getGenericSuperclass();
      }
      ArgumentContextualizer result = null;
      if (superType instanceof Class)
      {
         if (superType == desiredType)
         {
            return null;
         }
         result = getContextualizedArgumentsInternal(desiredType,
               (Class<?>) superType);
      }
      else
      {
         ParameterizedType superParamType = (ParameterizedType) superType;
         Class<?> superClassType = (Class<?>) superParamType.getRawType();
         if (superClassType == desiredType)
         {
            return new ArgumentContextualizer(superParamType
                  .getActualTypeArguments(), classType);
         }
         else
         {
            result = getContextualizedArgumentsInternal(desiredType,
                  superClassType);
         }
      }
      if (result == null
            || !result.contextualizeVariables(classType, superType))
      {
         return null;
      }
      return result;
   }
   
   
   private Type[] arguments;
   private LinkedList<VariableReplacer> variableReplacements;

   // declaring class extends queried class (DeclaringClass<A, B, C> extends Queried<X, Y, Z, ..., W>,
   // where X, Y, Z...W, are a list of types for which we need to map (contextualize)
   // variables
   // A, B, C... D are variables of DeclaringClass, that may be used in the contextualization proccess
   /**
    * Constructor.
    * 
    * @param arguments      the set of arguments in their original context (the
    *                       extends or implements declaration)
    * @param declaringClass the class that declared those arguments. This class
    *                       must be the same class that extends/implements a
    *                       queried parameterized type.
    */
   private ArgumentContextualizer(Type[] arguments, Class<?> declaringClass)
   {
      this.arguments = arguments;
      for (int i = 0; i < arguments.length; i++)
      {
         Type newArgument = processArgument(arguments, i, declaringClass,
               replacementCreator, this);
         if (newArgument != null)
         {
            this.arguments[i] = newArgument;
         }
      }
   }

   // newDeclaringClass extends/implements oldDeclaringType
   // returns false = warning (work with raw type hence)
   /**
    * Performs a contextualization step.
    * 
    * @param subClass  a class that declares to extend/implement <code>superType
    *                  </code> 
    * @param superType the super type of <code>subClass</code>
    * 
    * @return <code>true</code> if the contextualization process was successfully
    *         performed; </code>false</code> otherwise.
    */
   private boolean contextualizeVariables(Class<?> subClass, Type superType)
   {
      if (!initialized || variableReplacements.isEmpty())
      {
         initialized = false;
         return true;
      }
      if (superType instanceof Class)
      {
         return false; // warning: variables lost in hierarchy
      }
      ParameterizedType superParamType = (ParameterizedType) superType;
      for (iterator = variableReplacements.listIterator(); iterator.hasNext(); )
      {
         if(iterator.next().replace(superParamType, subClass))
         {
            iterator.remove();
         }
      }
      iterator = null;
      return true; 
   }
   
   /**
    * Process an argument, definining whether it needs to be replaced during
    * contextualization process or not.
    * 
    * @param <O>
    * 
    * @param argumentContainer the array that contains the argument to be
    *                          processed 
    * @param argumentIndex     the index of the argument in <code>
    *                          argumentContainer</code>
    * @param declaringClass    the class that declares the argument
    * @param recorder          object responsible for recording future
    *                          replacements to be performed during
    *                          contextualization
    * @param outer             
    * @return the argument processed. This return value may be null if no
    *         processement of <code>argumentContainer[argumentIndex]</code> is
    *         rerquired, or an equivalent type otherwise.
    */
   private static <O> Type processArgument(Type[] argumentContainer,
         int argumentIndex, Class<?> declaringClass,
         ReplacementScheduler<O> recorder, O outer)
   {
      Type argument = argumentContainer[argumentIndex];
      if (argument instanceof Class)
      {
         return null;
      }
      if (argument instanceof ParameterizedType)
      {
         ParameterizedType paramType = (ParameterizedType) argument;
         ParameterizedType_ newParamType = null;
         Type[] arguments = paramType.getActualTypeArguments();
         for (int i = 0; i < arguments.length; i++)
         {
            Type newType = processArgument(arguments, i, declaringClass, recorder,
                  outer);
            if (newType != null)
            {
               if (newParamType == null)
               {
                  newParamType = new ParameterizedType_(paramType);
               }
               newParamType.getActualTypeArguments()[i] = newType;
            }
         }
         return newParamType;
      }
      //else if (bounds[i] instanceof TypeVariable)
      if (declaringClass == null)
      {
         return null;
      }
      String paramName = ((TypeVariable<?>) argument).getName();
      int index = 0;
      TypeVariable<?>[] typeVariables = declaringClass.getTypeParameters();
      for (index = 0; index < typeVariables.length; index ++)
      {
         if (typeVariables[index].getName().equals(paramName)){
            break;
         }
      }
      recorder.scheduleReplacement(argumentContainer, argumentIndex, index, outer);
      return argument; 
   }

   boolean initialized = false;

   private void initialize()
   {
      if (!initialized)
      {
         Type[] oldResult = this.arguments;
         this.arguments = new Type[oldResult.length];
         System.arraycopy(oldResult, 0, this.arguments, 0, this.arguments.length);
         this.variableReplacements = new LinkedList<VariableReplacer>();
         initialized = true;
      }
   }
   private ListIterator<VariableReplacer> iterator;

   /**
    * Returns contextualized arguments.
    * 
    * @return contextualized arguments
    */
   public Type[] getArguments()
   {
      return this.arguments;
   }

   /**
    * Creates a variable replacement. This replacement should take place during the
    * contextualization steps, where the variable will be replaced by its value.
    * Notice this value may or may not be another variable.

    * @param argumentContainer array on which the replacement will be executed 
    * @param argumentIndex     the index that indicates a position of the variable
    *                          to be replaced in <code>argumentContainer</code>
    *                          array
    * @param variableIndex     index of the variable value in the next argument
    *                          context array. This index shall be used on the
    *                          replacement, during contextualization process.
    */
   private void createVariableReplacement(Type[] argumentContainer, int argumentIndex,
         int variableIndex)
   {
      if (iterator != null)
      {
         iterator.add(new VariableReplacer(argumentContainer, argumentIndex,
               variableIndex));
      }
      else
      {
         this.variableReplacements.add(new VariableReplacer(
               argumentContainer, argumentIndex, variableIndex));
      }
   }
   
   /**
    * Replaces a variable by another type according to an argument context.
    *  
    * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
    */
   class VariableReplacer
   {
      private Type[] arguments;
      private int argumentIndex;
      private int valueIndex;
      private boolean pendingExecution;

      /**
       * Constructor.
       * 
       * @param arguments     array on which the replacement will be executed 
       * @param argumentIndex the index that indicates a position of the variable
       *                      to be replaced in <code>argumentContainer</code> array
       * @param valueIndex    index of the variable value in the next argument
       *                      context array. This index shall be used on the
       *                      replacement, during contextualization process.
       */
      public VariableReplacer(Type[] arguments, int argumentIndex, int valueIndex)
      {
         this.valueIndex = valueIndex;
         this.arguments = arguments;
         this.argumentIndex = argumentIndex;
         this.pendingExecution = true;
      }

      // return true if replacement has been done for good
      /**
       * Performs replacemen of a variable by a new type.
       * 
       * @param paramType      parameterized type that contains the context to be
       *                       used during replacement proccess.
       * @param declaringClass the class that declares the the variables used in the
       *                       arguments of <code>paramType</code>. This class must
       *                       extend/implement the generic type
       *                       <code>paramType</code>.
       */
      public boolean replace(ParameterizedType paramType, Class<?> declaringClass)
      {
         arguments[argumentIndex] = paramType.getActualTypeArguments()[valueIndex];
         this.pendingExecution = false;
         Type newType = ArgumentContextualizer.processArgument(arguments, argumentIndex,
               declaringClass, updater, this);
         if (newType != null)
         {
            arguments[argumentIndex] = newType;
            return false;
         }
         return true;
      }

      /**
       * Returns the contextualizer that scheduled this replacement.
       * 
       * @return the contextualizer.
       */
      ArgumentContextualizer getContextualizer()
      {
         return ArgumentContextualizer.this;
      }
   }

   /**
    * Parameterized type implementation. Allows replacement of arguments without
    * affecting reflection information.
    * 
    * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
    */
   private static final class ParameterizedType_ implements ParameterizedType
   {
      private Type[] arguments;
      private Type ownerType;
      private Type rawType;

      ParameterizedType_(ParameterizedType type)
      {
         Type[] actualArguments = type.getActualTypeArguments();
         this.arguments = new Type[actualArguments.length];
         System.arraycopy(actualArguments, 0, arguments, 0, actualArguments.length);
         this.ownerType = type.getOwnerType();
         this.rawType = type.getRawType();
      }


      public Type[] getActualTypeArguments()
      {
         return this.arguments;
      }

      public Type getOwnerType()
      {
         return this.ownerType;
      }

      public Type getRawType()
      {
         return this.rawType;
      }

      public boolean equals(Object obj)
      {
         if (!(obj instanceof ParameterizedType))
         {
            return false;
         }
         ParameterizedType other = (ParameterizedType) obj;
         if (!this.ownerType.equals(other.getOwnerType()) ||
               !this.rawType.equals(other.getRawType()))
         {
            return false;
         }
         Type[] otherArguments = other.getActualTypeArguments();
         for (int i = 0; i < arguments.length; i++)
         {
            if (!arguments[i].equals(otherArguments[i]))
            {
               return false;
            }
         }
         return true;
      }
   }

   /**
    * Schedules replacements to be performed during contextualization.
    * 
    * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
    *
    * @param <C> the scheduler client 
    */
   interface ReplacementScheduler<C>
   {
      /**
       * Schedules a variable replacement to be performed on next contextualization
       * step.
       * 
       * @param argumentContainer array on which the replacement will be executed 
       * @param argumentIndex     the index that indicates a position of the variable
       *                          to be replaced in <code>argumentContainer</code>
       *                          array
       * @param variableIndex     index of the variable value in the next argument
       *                          context array. This index shall be used on the
       *                          replacement, during contextualization process.
       * @param client            the client of recorder
       */
      public void scheduleReplacement(Type[] argumentContainer, int argumentIndex,
            int variableIndex, C client);
   }
   
   /**
    * Schedules variable replacement when required by contextualizer.
    * The effect of this scheduling process is the creation of variable replacement.
    */
   private static ReplacementScheduler<ArgumentContextualizer> replacementCreator =
      new ReplacementScheduler<ArgumentContextualizer>()
   {
      public void scheduleReplacement(Type[] replacementTarget, int targetIndex,
            int variableIndex, ArgumentContextualizer outer)
      {
         outer.initialize();
         outer.createVariableReplacement(outer.arguments, targetIndex, variableIndex);
      }
   };
   
   /**
    * Schedules variable replacement when required by a <code>VariableReplacement
    * </code> object.
    * Generally, the effect of this schedule process will be an update in
    * the client object, unless this one already has a pending replacement scheduled.
    */
   private static ReplacementScheduler<VariableReplacer> updater =
      new ReplacementScheduler<VariableReplacer>()
   {
      public void scheduleReplacement(Type[] replacementTarget, int targetIndex,
            int variableIndex, VariableReplacer replacer)
      {
         if (replacer.pendingExecution) // replacer is already busy
         {
            replacer.getContextualizer().createVariableReplacement(replacementTarget,
                  targetIndex, variableIndex);
         }
         else
         {
            replacer.valueIndex = variableIndex;
            replacer.arguments = replacementTarget;
            replacer.argumentIndex = targetIndex;
            replacer.pendingExecution = true;
         }
      }
   };
}