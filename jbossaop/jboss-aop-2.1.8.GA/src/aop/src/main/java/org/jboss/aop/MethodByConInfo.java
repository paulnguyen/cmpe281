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
import org.jboss.aop.joinpoint.MethodCallByConstructor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.joinpoint.MethodCalledByConstructorJoinpoint;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 77240 $
 */
public class MethodByConInfo extends CallerMethodInfo implements MethodCallByConstructor
{

   private final int callingIndex;
   private final Constructor<?> calling;
   
   public MethodByConInfo(Advisor advisor, Class<?> calledClass, Class<?> callingClass, int callingIndex, Method m, long calledMethodHash, Interceptor[] in)
   {
      super(advisor, calledClass, m, calledMethodHash, in, callingClass);
      this.callingIndex = callingIndex;
      Advisor tempAdvisor = advisor;
      if (advisor.getClazz() != callingClass)
      {
	      tempAdvisor = advisor.manager.getAdvisor(callingClass);
      }
      calling = tempAdvisor.constructors[callingIndex];
   }

   /*
    * For copying
    */
   private MethodByConInfo(MethodByConInfo other)
   {
      super(other);
      this.callingIndex = other.callingIndex;
      this.calling = other.calling;
   }
   
   protected Joinpoint internalGetJoinpoint()
   {
      return new MethodCalledByConstructorJoinpoint(calling, getMethod());
   }
   
   public JoinPointInfo copy()
   {
      return new MethodByConInfo(this);
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer("Method called by Constructor");
      sb.append("[");
      sb.append("calling=" + calling);
      sb.append(",called=" + getMethod());
      sb.append("]");
      return sb.toString();
   }

   public int getCallingIndex()
   {
      return callingIndex;
   }

   @Deprecated
   public Constructor<?> getCalling()
   {
      return calling;
   }

   public Constructor<?> getCallingConstructor()
   {
      return calling;
   }
}
