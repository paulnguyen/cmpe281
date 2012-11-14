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
package org.jboss.aop.advice;


/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 88328 $
 */
public class PrecedenceDefEntry
{
   String interceptorClass;
   String adviceMethod;
   
   public PrecedenceDefEntry(String interceptorClass,
         String adviceMethod)
   {
      this.interceptorClass = interceptorClass;
      this.adviceMethod = adviceMethod;
   }
   
   public String getAdviceMethod()
   {
      return adviceMethod;
   }
   
   public String getInterceptorClass()
   {
      return interceptorClass;
   }
   
   
   public boolean equals(Object o)
   {
      if (o instanceof PrecedenceDefEntry)
      {
         PrecedenceDefEntry pde = (PrecedenceDefEntry)o;
         
         if (this.interceptorClass.equals(pde.interceptorClass))
         {
            if (this.adviceMethod == null)
            {
               return pde.adviceMethod == null;
            }
            else if (pde.adviceMethod != null)
            {
               return this.adviceMethod.equals(pde.adviceMethod);
            }
         }
      }
      
      return false;
   }
   
   public int hashCode()
   {
      int result = 17;
      if (interceptorClass != null)
      {
         result = 37 * result + interceptorClass.hashCode();
      }
      if (adviceMethod != null)
      {
         result = 37 * result + adviceMethod.hashCode();
      }
      return result;
   }
   
   public String toString()
   {
      return "Entry: interceptorClass=" + interceptorClass + "; adviceMethod=" + adviceMethod;
   }
   
}
