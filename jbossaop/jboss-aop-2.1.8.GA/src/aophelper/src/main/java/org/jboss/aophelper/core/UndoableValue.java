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
package org.jboss.aophelper.core;

import java.lang.reflect.Method;

/**
 * A UndoableValue.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class UndoableValue
{
   private Object executionObject;
   private Method method;
   private Object value;

   /**
    * Create a new UndoableValue.
    * 
    * @param o
    * @param m
    */
   public UndoableValue(Object o, Method m)
   {
      setExecutionObject(o);
      setMethod(m);
   }

   /**
    * Create a new UndoableValue.
    * 
    * @param o
    * @param m
    * @param value
    */
   public UndoableValue(Object o, Method m, Object value)
   {
      setExecutionObject(o);
      setMethod(m);
      setValue(value);
   }

   /**
    * Get the executionObject.
    * 
    * @return the executionObject.
    */
   public Object getExecutionObject()
   {
      return executionObject;
   }

   /**
    * Set the executionObject.
    * 
    * @param executionObject The executionObject to set.
    */
   public void setExecutionObject(Object executionObject)
   {
      this.executionObject = executionObject;
   }

   /**
    * Get the method.
    * 
    * @return the method.
    */
   public Method getMethod()
   {
      return method;
   }

   /**
    * Set the method.
    * 
    * @param method The method to set.
    */
   public void setMethod(Method method)
   {
      this.method = method;
   }

   /**
    * Get the data.
    * 
    * @return the data.
    */
   public Object getValue()
   {
      return value;
   }

   /**
    * Set the data.
    * 
    * @param data The data to set.
    */
   public void setValue(Object value)
   {
      this.value = value;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if(!(o instanceof UndoableValue))
         return false;
      UndoableValue uv = (UndoableValue) o;
      if(uv.getMethod().getName().equals(getMethod().getName()) &&
            uv.getExecutionObject().getClass().getName().equals(getExecutionObject().getClass().getName()))
            return true;
      else
         return false;
  }
   public boolean equals(Object o, Method m)
   {
      if(m.getName().equals(getMethod().getName()) &&
            o.getClass().getName().equals(getExecutionObject().getClass().getName()))
            return true;
      else
         return false;
  }
   
   @Override
   public int hashCode()
   {
      return getMethod().getName().hashCode() + getExecutionObject().getClass().getName().hashCode();
   }
   
   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("Execution class: ").append(getExecutionObject().getClass().getName());
      sb.append(", Method: ").append(getMethod().getName());
      return sb.toString();
   }

}
