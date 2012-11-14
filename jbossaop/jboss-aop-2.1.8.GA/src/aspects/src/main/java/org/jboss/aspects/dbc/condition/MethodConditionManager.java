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
package org.jboss.aspects.dbc.condition;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javassist.Modifier;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aspects.dbc.DesignByContractAspect;
import org.jboss.aspects.dbc.PostCond;
import org.jboss.aspects.dbc.PreCond;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class MethodConditionManager extends ConditionManager
{
   
   public static synchronized ExecutableCondition[] getPreConditions(Method method)
   {
      ExecutableCondition[] pre = preConditions.get(method); 
      if (pre != null)
      {
         return pre;
      }
      
      initialise(method);
      return preConditions.get(method); 
   }
   
   public static synchronized ExecutableCondition[] getPostConditions(Method method)
   {
      ExecutableCondition[] post = postConditions.get(method); 
      if (post != null)
      {
         return post;
      }
      
      initialise(method);
      return postConditions.get(method); 
   }
   
   public static synchronized InvariantCondition[] getInvariants(Method method)
   {
      return getInvariants(method.getDeclaringClass()); 
   }

   private static void initialise(Method method)
   {
      if (DesignByContractAspect.verbose) System.out.println("[dbc] ===== Intitalising method: " + method);
      ArrayList<ExecutableCondition> preConds = new ArrayList<ExecutableCondition>();
      ArrayList<ExecutableCondition> postConds = new ArrayList<ExecutableCondition>();
      
      
      //Need @PreCond and @PostCond for this method, and all the super 
      //declarations of the method.
      //Likewise we need the @Invariant for this class and the super classes
      boolean first = true;
      
      Class<?> clazz = method.getDeclaringClass();
      Class<?> curClazz = clazz;
      Method superMethod = method;
      
      while (curClazz != null)
      {
         if (first)
         {
            first = false;
         }
         else
         {
            superMethod = findMethodInClass(curClazz, method);
         }
         
         if (superMethod != null)
         {
            addMethodConditions(method, superMethod, preConds, postConds);
         }
         
         addMethodConditionsForInterfaces(preConds, postConds, curClazz, method);

         curClazz = curClazz.getSuperclass();
      }
      
      ExecutableCondition[] pre = preConds.toArray(new ExecutableCondition[preConds.size()]);
      preConditions.put(method, pre);
      
      ExecutableCondition[] post = postConds.toArray(new ExecutableCondition[postConds.size()]);
      postConditions.put(method, post);
   }
   
   private static void addMethodConditionsForInterfaces(ArrayList<ExecutableCondition> preConds, ArrayList<ExecutableCondition> postConds, Class<?> clazz, Method method)
   {
      Class<?>[] interfaces = clazz.getInterfaces();
      for (int i = 0 ; i < interfaces.length ; i++)
      {
         //System.out.println("Checking interface: " + interfaces[i]);
         Method foundMethod = findMethodInClass(interfaces[i], method);
         
         if (foundMethod != null)
         {
            //System.out.println("Found method: " + foundMethod);
            addMethodConditions(method, foundMethod, preConds, postConds);
         }
      }
      
   }
   private static void addMethodConditions(Method realMethod, Method currentMethod, ArrayList<ExecutableCondition> preConds, ArrayList<ExecutableCondition> postConds)
   {
      PreCond pre = (PreCond)AnnotationElement.getAnyAnnotation(currentMethod, PreCond.class);
      if (pre != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found preconditions in method: " + currentMethod);
         addMethodConditions(realMethod, preConds, pre.value());
      }
      
      PostCond post = (PostCond)AnnotationElement.getAnyAnnotation(currentMethod, PostCond.class);
      if (post != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found postconditions in method: " + currentMethod);
         addMethodConditions(realMethod, postConds, post.value());
      }
   }
   

   private static ArrayList<ExecutableCondition> addMethodConditions(Method realMethod, ArrayList<ExecutableCondition> conditions, String[] exprs)
   {
      if (exprs == null)
      {
         return conditions;
      }
      
      boolean staticCall = Modifier.isStatic(realMethod.getModifiers());

      for (int i = 0 ; i < exprs.length ; i++)
      {
         conditions.add(new MethodCondition(realMethod, exprs[i], staticCall));
      }
      
      return conditions;
   }
   
   private static Method findMethodInClass(Class<?> clazz, Method method)
   {
      String name = method.getName();
      Method[] methods = clazz.getDeclaredMethods();
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (methods[i].getName().equals(name))
         {
            Class<?>[] soughtParams = method.getParameterTypes();
            Class<?>[] foundParams = methods[i].getParameterTypes();
            
            if (soughtParams.length == foundParams.length)
            {
               for (int j = 0 ; j < soughtParams.length ; j++)
               {
                  if (soughtParams[j] != foundParams[j])
                  {
                     break;
                  }
               }
               
               return methods[i];
            }
         }
      }
      return null;
   }


   
   
}
