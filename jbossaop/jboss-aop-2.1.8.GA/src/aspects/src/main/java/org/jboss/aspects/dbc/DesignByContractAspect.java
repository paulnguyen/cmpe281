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
package org.jboss.aspects.dbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aspects.dbc.condition.ConstructorConditionManager;
import org.jboss.aspects.dbc.condition.ExecutableCondition;
import org.jboss.aspects.dbc.condition.InvariantCondition;
import org.jboss.aspects.dbc.condition.MethodConditionManager;

/**
 * TODO:
 * -Once version of beanshell in cvs allows importObject() (2.0, currently in beta),
 * make invariants use that, so don't have to use $tgt for class invariants
 * -Add $old funtionality
 *  
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class DesignByContractAspect
{
   public static boolean verbose;

   ExecutableCondition[] preconditions;
   ExecutableCondition[] postconditions;
   InvariantCondition[] invariants;
   
   Method method;
   Constructor<?> constructor;
   boolean isPublic;
   boolean isStatic;
   boolean isSynchronized;
   
   boolean initialised;
   
   //Keep track of if this checks have already been made for this joinpoint 
   //as the result of previous checks on the stack 
   ThreadLocal<Boolean> done = new ThreadLocal<Boolean>();
   
   public DesignByContractAspect()
   {
      done.set(Boolean.FALSE);
   }
   
   public void setVerbose(boolean vbs)
   {
      verbose = vbs;
   }
   
   public boolean getVerbose()
   {
      return verbose;
   }
   
   public Object invoke(MethodInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getMethod());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }

   public Object invoke(MethodCalledByMethodInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getCalledMethod());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }

   public Object invoke(MethodCalledByConstructorInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getCalledMethod());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }

   public Object invoke(ConstructorInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getConstructor());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }

   public Object invoke(ConstructorCalledByMethodInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getCalledConstructor());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }

   public Object invoke(ConstructorCalledByConstructorInvocation invocation) throws Throwable
   {
      if (!initialised)
      {
         initialise(invocation.getCalledConstructor());
      }
      Object[] args = invocation.getArguments();
      return invoke(invocation, args);
   }


   private Object invoke(Invocation invocation, Object[] args) throws Throwable
   {
      if (isSynchronized)
      {
         if (isStatic)
         {
            synchronized (method.getDeclaringClass())
            {
               return doInvoke(invocation, args);
            }
         }
         else
         {
            synchronized (invocation.getTargetObject())
            {
               return doInvoke(invocation, args);
            }
         }
      }
      return doInvoke(invocation, args);
   }

   private Object doInvoke(Invocation invocation, Object[] args) throws Throwable
   {
      logStart();
      Object ret = null;
      boolean wasDone = done.get().booleanValue();

      try
      {
         done.set(Boolean.TRUE);
         if (!wasDone && isPublic && (constructor == null))
         {
            checkInvariants(invocation, null);
         }
         if (!wasDone)
         {
            checkPreConditions(invocation, args);
         }
            
         ret = invocation.invokeNext();

         if (!wasDone && isPublic)
         {
            checkInvariants(invocation, ret);
            checkPostConditions(invocation, args, ret);
         }
      }
      finally
      {
         if (!wasDone)
         {
            done.set(Boolean.FALSE);
         }
      }
      
      logEnd();
      return ret;
   }
   
   
   private void logStart()
   {
      if (verbose)
      {
         if (method != null)
         {
            System.out.println("[dbc] ======= Invoking method: " + method);
         }
         else
         {
            System.out.println("[dbc] ======= Invoking constructor: " + constructor);
         }
      }
   }
   
   private void logEnd()
   {
      if (verbose)
      {
         if (method != null)
         {
            System.out.println("[dbc] ======= Invoked method: " + method);
         }
         else
         {
            System.out.println("[dbc] ======= Invoked constructor: " + constructor);
         }
      }
   }
   
   private void initialise(Method m)
   {
      method = m;
      int modifiers = m.getModifiers();
      initialise(modifiers);
      
      preconditions = MethodConditionManager.getPreConditions(m);
      postconditions = MethodConditionManager.getPostConditions(m);
      
      if (isPublic)
      {
         invariants = MethodConditionManager.getInvariants(m);
      }
   }
   
   private void initialise(Constructor<?> c)
   {
      constructor = c;
      int modifiers = c.getModifiers();
      initialise(modifiers);

      preconditions = ConstructorConditionManager.getPreConditions(c);
      postconditions = ConstructorConditionManager.getPostConditions(c);
      
      if (isPublic)
      {
         invariants = ConstructorConditionManager.getInvariants(c);
      }
   }
   
   private void initialise(int modifiers)
   {
      isSynchronized = Modifier.isSynchronized(modifiers);
      isStatic = Modifier.isStatic(modifiers);
      isPublic = Modifier.isPublic(modifiers);
      
      initialised = true;      
   }
   
   private void checkPreConditions(Invocation invocation, Object[] args)
   {
      if (verbose) System.out.println("[dbc] === checkPreconditions() for " + ((method != null) ? method.toString() : constructor.toString()));
      for (int i = 0 ; i < preconditions.length ; i++)
      {
         preconditions[i].evaluateCondition(this, invocation, args, null);
     }
   }

   private void checkPostConditions(Invocation invocation, Object[] args, Object ret)
   {
      if (verbose) System.out.println("[dbc] === checkPostconditions() for " + ((method != null) ? method.toString() : constructor.toString()));
      for (int i = 0 ; i < postconditions.length ; i++)
      {
         postconditions[i].evaluateCondition(this, invocation, args, ret);
      }
   }

   private void checkInvariants(Invocation invocation, Object ret)
   {
      if (verbose) System.out.println("[dbc] === checkInvariants() for " + ((method != null) ? method.toString() : constructor.toString()));
      boolean isConstructor = (constructor != null);
      for (int i = 0 ; i < invariants.length ; i++)
      {
         invariants[i].evaluateCondition(invocation, isStatic, isConstructor, ret);
      }
   }
}
