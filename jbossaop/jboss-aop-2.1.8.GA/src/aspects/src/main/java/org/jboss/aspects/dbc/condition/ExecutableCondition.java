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
 * A pre or post consition
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public abstract class ExecutableCondition extends Condition
{
   public ExecutableCondition(String condExpr, Class<?> clazz, boolean isStatic)
   {
      super(condExpr, clazz, isStatic);
   }

   public void evaluateCondition(DesignByContractAspect aspect, Invocation inv, Object[] args, Object ret)
   {
      try
      {
         if (DesignByContractAspect.verbose) System.out.println("[dbc] Evaluate condition : '" + originalExpr + "' for: " + executableObject());
   
         Interpreter interpreter = new Interpreter();
         for (Iterator<String> it = parameterLookup.keySet().iterator() ; it.hasNext() ; )
         {
            String beanshellname = it.next();
            String originalname = parameterLookup.get(beanshellname);
            Object value = getValueForToken(inv, args, ret, originalname);
            interpreter.set(beanshellname, value);
            if (DesignByContractAspect.verbose) System.out.println("[dbc] Setting $" + originalname + " to " + value + 
                  ((value != null) ? " (type: " + value.getClass().getName() + ")" : "")) ;
         }
   
         Boolean eval = (Boolean)interpreter.eval(condExpr);
   
         if (!eval.booleanValue())
         {
            System.out.println("CONDITION BROKEN: "+ originalExpr + " - " + executableObject());
            throw new RuntimeException("Condition " + originalExpr + " was broken " + "for: " + executableObject());
         }
      }
      catch (EvalError e)
      {
         throw new RuntimeException("There was an error in the expression: " + originalExpr, e);
      }
   }
   
   public String toString()
   {
      return executableObject() + ":" + super.toString();
   }
   
   protected Object getValueForToken(Invocation invocation, Object[] args, Object rtn, String token)
   {
      if (isPredefinedToken(token))
      {
      	return getValueForPredefinedToken(invocation, rtn, token);
      }
     
      try
      {
         int i = Integer.parseInt(token);
         return args[i];
      }
      catch(NumberFormatException e)
      {
         throw new RuntimeException("Invalid token '$" + token + "' in condition: " + originalExpr);
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
         throw new RuntimeException("invalid parameter number '$" + token + "' in condition: "
               + originalExpr + ". " + executableObject() + " takes " + parameterTypes().length);
      }
   }
   
   protected abstract boolean isPredefinedToken(String token);
   protected abstract Object getValueForPredefinedToken(Invocation invocation, Object rtn, String token);
   protected abstract Object executableObject();
   protected abstract Class<?>[] parameterTypes();
   
}
