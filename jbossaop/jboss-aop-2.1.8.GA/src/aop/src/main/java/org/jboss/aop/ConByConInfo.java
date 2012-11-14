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
package org.jboss.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.ConstructorCallByConstructor;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorJoinpoint;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70817 $
 */
public class ConByConInfo extends CallerConstructorInfo implements ConstructorCallByConstructor
{
   private final int callingIndex;
   private final Constructor<?> calling;
   
   public ConByConInfo(Advisor advisor, Class<?> calledClass, Class<?> callingClass, int callingIndex, Constructor<?> called, long calledConHash, Method wrappingMethod, Interceptor[] in)
   {
      super(advisor, calledClass, called, calledConHash, wrappingMethod, in, callingClass);
      this.callingIndex = callingIndex;
      Advisor tempAdvisor = advisor;
      if (advisor.getClazz() != callingClass)
      {
         tempAdvisor = advisor.manager.getAdvisor(callingClass);
      }
      this.calling = tempAdvisor.constructors[callingIndex];
   }
   
   /*
    * For copying
    */
   private ConByConInfo(ConByConInfo other)
   {
      super(other);
      this.callingIndex = other.callingIndex;
      this.calling = other.getCallingConstructor();
   }
   
   protected Joinpoint internalGetJoinpoint()
   {
      return new ConstructorCalledByConstructorJoinpoint(getCallingConstructor(), getConstructor());
   }   

   public JoinPointInfo copy()
   {
      return new ConByConInfo(this);
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer("Constructor called by Constructor");
      sb.append("[");
      sb.append("calling=" + getCallingConstructor());
      sb.append(",called=" + getConstructor());
      sb.append("]");
      return sb.toString();
   }

   public int getCallingIndex()
   {
      return callingIndex;
   }

   //Use getCallingConstructor instead
   @Deprecated()
   public Constructor<?> getCalling()
   {
      return calling;
   }
   
   public Constructor<?> getCallingConstructor()
   {
      return calling;
   }
}
