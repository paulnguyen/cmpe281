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

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.HashMap;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aspects.dbc.DesignByContractAspect;
import org.jboss.aspects.dbc.Invariant;
import org.jboss.aspects.dbc.StaticInvariant;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class ConditionManager
{
   protected static HashMap<AccessibleObject, ExecutableCondition[]> preConditions = new HashMap<AccessibleObject, ExecutableCondition[]>();
   
   protected static HashMap<AccessibleObject, ExecutableCondition[]> postConditions = new HashMap<AccessibleObject, ExecutableCondition[]>();
   
   protected static HashMap<Class<?>, InvariantCondition[]> invariants = new HashMap<Class<?>, InvariantCondition[]>();
   
   public static synchronized InvariantCondition[] getInvariants(Class<?> clazz)
   {
      InvariantCondition[] inv = invariants.get(clazz); 
      if (inv != null)
      {
         return inv;
      }
      
      initialise(clazz);
      return invariants.get(clazz); 
   }
   
   
   protected static void initialise(Class<?> clazz)
   {
      ArrayList<InvariantCondition> invConds = new ArrayList<InvariantCondition>();
      
      if (invariants.get(clazz) != null)
      {
         if (DesignByContractAspect.verbose)System.out.println("[dbc] Already have invariants for class: " + clazz);
      }
      
      if (DesignByContractAspect.verbose)System.out.println("[dbc] ===== Initialising invariants for class: " + clazz);

      //We need the @Invariant for this class and the super classes
      Class<?> curClazz = clazz;

      while (curClazz != null)
      {
         addInvariantConditions(invConds, curClazz);
			
			Class<?>[] interfaces = curClazz.getInterfaces();
			for (int i = 0; i < interfaces.length ; i++)
			{
			   addInvariantConditions(invConds, interfaces[i]);
			}

         curClazz = curClazz.getSuperclass();
      }
      
      InvariantCondition[] inv = invConds.toArray(new InvariantCondition[invConds.size()]);
      invariants.put(clazz, inv);
   }

   private static void addInvariantConditions(ArrayList<InvariantCondition> conditions, Class<?> clazz)
   {
      Invariant inv = (Invariant)AnnotationElement.getAnyAnnotation(clazz, Invariant.class);
      if (inv != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found non-static invariants in class: " + clazz);
         String[] exprs = inv.value();
         if (exprs != null)
         {
            for (int i = 0 ; i < exprs.length ; i++)
            {
               conditions.add(new InvariantCondition(clazz, exprs[i], false));
            }
         }
      }

      StaticInvariant statinv = (StaticInvariant)AnnotationElement.getAnyAnnotation(clazz, StaticInvariant.class);
      if (statinv != null)
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Found static invariants in class: " + clazz);
         String[] exprs = statinv.value();
         if (exprs != null)
         {
            for (int i = 0 ; i < exprs.length ; i++)
            {
               conditions.add(new InvariantCondition(clazz, exprs[i], true));
            }
         }
      }
   }
}
