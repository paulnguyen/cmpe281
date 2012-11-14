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

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aspects.dbc.DesignByContractAspect;
import org.jboss.aspects.dbc.PostCond;
import org.jboss.aspects.dbc.PreCond;


/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class ConstructorConditionManager extends ConditionManager
{
   public static synchronized ExecutableCondition[] getPreConditions(Constructor<?> constructor)
   {
      ExecutableCondition[] pre = preConditions.get(constructor); 
      if (pre != null)
      {
         return pre;
      }
      
      initialise(constructor);
      return preConditions.get(constructor); 
   }
   
   public static synchronized ExecutableCondition[] getPostConditions(Constructor<?> constructor)
   {
      ExecutableCondition[] post = postConditions.get(constructor); 
      if (post != null)
      {
         return post;
      }
      
      initialise(constructor);
      return postConditions.get(constructor); 
   }
   
   public static synchronized InvariantCondition[] getInvariants(Constructor<?> constructor)
   {
      return getInvariants(constructor.getDeclaringClass()); 
   }
   
   private static void initialise(Constructor<?> constructor)
   {
      if (DesignByContractAspect.verbose) System.out.println("[dbc] ===== Intitalising constructor: " + constructor);
      ArrayList<ExecutableCondition> preConds = new ArrayList<ExecutableCondition>();
      ArrayList<ExecutableCondition> postConds = new ArrayList<ExecutableCondition>();
      
      
      //Need @PreCond and @PostCond for this constructor, and all the super 
      //declarations of the constructor.
      boolean first = true;
      
      Class<?> clazz = constructor.getDeclaringClass();
      Class<?> curClazz = clazz;
      Constructor<?> superConstructor = constructor;
      
      while (curClazz != null)
      {
         if (first)
         {
            first = false;
         }
         else
         {
            superConstructor = findConstructorInClass(curClazz, constructor);
         }
         
         if (superConstructor != null)
         {
            addConstructorConditions(constructor, superConstructor, preConds, postConds);
         }
         
         curClazz = curClazz.getSuperclass();
      }
      
      ExecutableCondition[] pre = preConds.toArray(new ExecutableCondition[preConds.size()]);
      preConditions.put(constructor, pre);
      
      ExecutableCondition[] post = postConds.toArray(new ExecutableCondition[postConds.size()]);
      postConditions.put(constructor, post);
   }
   
   private static void addConstructorConditions(Constructor<?> realConstructor, Constructor<?> currentConstructor, ArrayList<ExecutableCondition> preConds, ArrayList<ExecutableCondition> postConds)
   {
      PreCond pre = (PreCond)AnnotationElement.getAnyAnnotation(currentConstructor, PreCond.class);
      if (pre != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found preconditions in method: " + currentConstructor);
         addConstructorConditions(realConstructor, preConds, pre.value());
      }
      
      PostCond post = (PostCond)AnnotationElement.getAnyAnnotation(currentConstructor, PostCond.class);
      if (post != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found postconditions in method: " + currentConstructor);
         addConstructorConditions(realConstructor, postConds, post.value());
      }
   }
   

   private static ArrayList<ExecutableCondition> addConstructorConditions(Constructor<?> realConstructor, ArrayList<ExecutableCondition> conditions, String[] exprs)
   {
      if (exprs == null)
      {
         return conditions;
      }
      
      for (int i = 0 ; i < exprs.length ; i++)
      {
         conditions.add(new ConstructorCondition(realConstructor, exprs[i]));
      }
      
      return conditions;
   }
   
   private static Constructor<?> findConstructorInClass(Class<?> clazz, Constructor<?> constructor)
   {
      String name = constructor.getName();
      Constructor<?>[] constructors = clazz.getDeclaredConstructors();
      for (int i = 0 ; i < constructors.length ; i++)
      {
         if (constructors[i].getName().equals(name))
         {
            Class<?>[] soughtParams = constructor.getParameterTypes();
            Class<?>[] foundParams = constructors[i].getParameterTypes();
            
            if (soughtParams.length == foundParams.length)
            {
               for (int j = 0 ; j < soughtParams.length ; j++)
               {
                  if (soughtParams[j] != foundParams[j])
                  {
                     break;
                  }
               }
               
               return constructors[i];
            }
         }
      }
      return null;
   }

}
