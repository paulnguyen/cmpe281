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

import org.jboss.aop.joinpoint.Invocation;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71280 $
 */
public class ConstructorCondition extends ExecutableCondition
{
   Constructor<?> constructor;
   
   public ConstructorCondition(Constructor<?> constructor, String condExpr)
   {
      super(condExpr, constructor.getClass(), false);
      this.constructor = constructor;
   }
   
   public boolean equals(Object o)
   {
      if (o instanceof ConstructorCondition)
      {
         ConstructorCondition cc = (ConstructorCondition)o;
         if (cc.constructor.equals(constructor))
         {
            return super.equals(o);
         }
      }
      return false;
   }

   // ExecutableCondition implementation -------------------------------------
   protected Object executableObject()
   {
      return constructor;
   }

   protected Class<?>[] parameterTypes()
   {
      return constructor.getParameterTypes();
   }
   
   protected boolean isPredefinedToken(String token)
   {
      return token.equals(Condition.TARGET);
   }
   
   protected Object getValueForPredefinedToken(Invocation invocation, Object rtn, String token)
   {
      if (token.equals(Condition.TARGET))
      {
         //For constructors the $tgt token corresponds to the return of the method
         return rtn;
      }

      return null;
   }
}
