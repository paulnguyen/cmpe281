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
import org.jboss.aop.joinpoint.ConstructorCalledByMethodJoinpoint;
import org.jboss.aop.joinpoint.ConstructorCallByMethod;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70817 $
 */
public class ConByMethodInfo extends CallerConstructorInfo implements ConstructorCallByMethod
{
   private final long callingMethodHash;
   private final Method callingMethod;

   /**
    * Create a new ConByMethodJoinPont.
    * 
    * @param c
    * @param in
    */
   public ConByMethodInfo(Advisor advisor, Class<?> calledClass, Method callingMethod, long callingMethodHash, Constructor<?> c, long calledConHash, Method wrappingMethod, Interceptor[] in)
   {
      super(advisor, calledClass, c, calledConHash, wrappingMethod, in, callingMethod.getDeclaringClass());
      this.callingMethodHash = callingMethodHash;
      this.callingMethod = callingMethod;
   }

   /*
    * For copying
    */
   protected ConByMethodInfo(ConByMethodInfo other)
   {
      super(other);
      this.callingMethodHash = other.callingMethodHash;
      this.callingMethod = other.callingMethod;
   }
   
   protected Joinpoint internalGetJoinpoint()
   {
      return new ConstructorCalledByMethodJoinpoint(callingMethod, getConstructor());
   }
   
   public JoinPointInfo copy()
   {
      return new ConByMethodInfo(this);
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer("Constructor called by Method");
      sb.append("[");
      sb.append("calling=" + callingMethod);
      sb.append(",called=" + getConstructor());
      sb.append("]");
      return sb.toString();
   }

   public long getCallingMethodHash()
   {
      return callingMethodHash;
   }

   public Method getCallingMethod()
   {
      return callingMethod;
   }
}
