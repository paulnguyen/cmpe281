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

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.MethodCall;

import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70817 $
 *
 **/
public abstract class CallerMethodInfo extends JoinPointInfo implements MethodCall
{
   private final Class<?> callingClass;
   private final Class<?> calledClass;
   private final Method method;
   private final long calledMethodHash;

   public CallerMethodInfo(Advisor advisor, Class<?> calledClass, Method m, long calledMethodHash, Interceptor[] in, Class<?> clazz)
   {
      super(advisor, clazz);
      this.calledClass = calledClass;
      super.setInterceptors(in);
      this.method = m;
      this.calledMethodHash = calledMethodHash;
      this.callingClass = advisor.getClazz();
   }

   /*
    * For copying
    */
   protected CallerMethodInfo(CallerMethodInfo other)
   {
      super(other);
      this.callingClass = other.callingClass;
      this.calledClass = other.calledClass;
      this.method = other.method;
      this.calledMethodHash = other.calledMethodHash;
   }

   public Class<?> getCallingClass()
   {
      return callingClass;
   }

   public Class<?> getCalledClass()
   {
      return calledClass;
   }

   public Method getMethod()
   {
      return method;
   }

   public long getCalledMethodHash()
   {
      return calledMethodHash;
   }
   
   
}
