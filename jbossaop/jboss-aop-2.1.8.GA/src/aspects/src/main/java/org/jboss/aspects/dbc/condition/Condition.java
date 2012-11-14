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

import java.util.HashMap;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aspects.dbc.DesignByContractAspect;
import org.jboss.aspects.dbc.condition.parser.BeanshellGenerator;
import org.jboss.aspects.dbc.condition.parser.Expression;
import org.jboss.aspects.dbc.condition.parser.ExpressionParser;

import bsh.Capabilities;

/**
 * Stores a 
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public abstract class Condition
{
   public final static String TARGET = "tgt";
   public final static String RETURN = "rtn";
   
   static
   {
      //Make sure that the beanshell can access private fields and variables
      Capabilities.setAccessibility(true);      
   }

   //Map of valid java identifiers to the original $tgt, $ret, $0, $1, $2 etc. 
   //used for the condition strings in the annotations 
   protected HashMap<String, String> parameterLookup = new HashMap<String, String>();
   protected String originalExpr;
   protected String condExpr;
   protected Class<?> clazz;
   
   //If this condition is for a static method or is a static invariant condition
   protected boolean isStatic;
   
   public Condition(String condExpr, Class<?> clazz, boolean isStatic)
   {
      this.isStatic = isStatic;
      
      if (DesignByContractAspect.verbose) System.out.println("[dbc] Initialising condition: " + condExpr);
      originalExpr = condExpr;
      condExpr += " ";

      StringBuffer newcond = new StringBuffer();
      StringBuffer param = null;

      for (int i  = 0 ; i < condExpr.length() ; i++)
      {
         char c = condExpr.charAt(i);
         if (c == '$')
         {
            param = new StringBuffer();
            continue;
         }
         else if (param != null && (c == '.' || c == ' ' || c == '=' || c == '>' || c == '<' 
            || c == ')' || c == '}' || c == ';' || c == '[' || c == ']'))
         {
            //Get param name as it was (without the leading '$')
            String prm = param.toString();
            
            if (prm.equals(TARGET) && isStatic)
            {
               //For a static method or static condition, use the class name instead of a parameter
               newcond.append(clazz.getName());
            }
            else
            {
               //Get param name for beanshell
               String bsparam = "p" + i;
               
               newcond.append(bsparam);
               parameterLookup.put(bsparam, prm);               
            }
               
            param = null;
         }

         if (param == null)
         {
            newcond.append(c);
         }
         else
         {
            param.append(c);
         }
      }

      Expression expr = ExpressionParser.parseExpression(newcond.toString());
      BeanshellGenerator gen = new BeanshellGenerator(expr);
      this.condExpr = gen.createBeanshellCode();
      
      if (DesignByContractAspect.verbose) System.out.println("[dbc] Expanded to Java code:\n" + this.condExpr);
      
      this.clazz = clazz;
   }

   public String toString()
   {
      return originalExpr;
   }
   
   public boolean equals(Object o){
      if (o instanceof Condition)
      {
         Condition c = (Condition)o;
         return c.clazz.equals(clazz) && originalExpr.equals(c.originalExpr);
      }
      
      return false;
   }
   
   public int hashCode()
   {
      // TODO Auto-generated method stub
      return originalExpr.hashCode();
   }
   
   protected Object getTarget(Invocation invocation, boolean isStatic)
   {
      if (!isStatic)
      {
         return invocation.getTargetObject();
      }
      else 
      {
         return clazz;
      }      
   }   
}
