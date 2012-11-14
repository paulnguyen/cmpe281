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

import java.util.Iterator;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aspects.dbc.DesignByContractAspect;

import bsh.EvalError;
import bsh.Interpreter;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class InvariantCondition extends Condition
{
   public InvariantCondition(Class<?> clazz, String condExpr, boolean isStatic)
   {
      super(condExpr, clazz, isStatic);
   }

   public void evaluateCondition(Invocation inv, boolean staticCall, boolean constructor, Object ret)
   {
      try
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Evaluate condition : '" + originalExpr + "' for class: " + clazz);
         
         if (!isStatic && staticCall)
         {
            System.out.println("[dbc] Ignoring non-static invariant for static call");
            return;
         }
   
         Interpreter interpreter = new Interpreter();
         Object target = (constructor) ? ret : getTarget(inv, staticCall);
         
         for (Iterator<String> it = parameterLookup.keySet().iterator() ; it.hasNext() ; )
         {
            String bsname = it.next();
            String originalname = parameterLookup.get(bsname); 
            if (originalname.equals(Condition.TARGET))
            {
               interpreter.set(bsname, target);
               if (DesignByContractAspect.verbose) System.out.println("[dbc] Setting $" + originalname + " to " + target +" (type: " + target.getClass().getName() + ")");
            }
            else
            {
               System.out.println("INVARIANT CONDITION BROKEN: "+ originalExpr + " - " + clazz);
               throw new RuntimeException("Invalid marker '" + originalname + "' in expression: " + originalExpr);
            }
         }
         
         Boolean eval = (Boolean)interpreter.eval(condExpr);
   
         if (!eval.booleanValue())
         {
            throw new RuntimeException("Invariant condition " + originalExpr + " was broken " + "for class: " + clazz);
         }
      }
      catch (EvalError e)
      {
         throw new RuntimeException("There was an error in the expression: " + originalExpr, e);
      }
   }
   
   public boolean equals(Object o)
   {
      if (o instanceof InvariantCondition)
      {
         return super.equals(o);
      }
      return false;
   }
   
}
