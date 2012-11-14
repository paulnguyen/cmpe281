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
import org.jboss.aop.joinpoint.ConstructorCall;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70817 $
 *
 **/
public abstract class CallerConstructorInfo extends JoinPointInfo implements ConstructorCall
{
   private final Constructor<?> constructor;
   private final Class<?> callingClass;
   private final Method wrappingMethod;
   private final long calledConHash;
   private final Class<?> calledClass;
   
   public CallerConstructorInfo(Advisor advisor, Class<?> calledClass, Constructor<?> called, long calledConHash, Method wrappingMethod, Interceptor[] in, Class<?> clazz)
   {
      super(advisor, clazz);
      this.calledClass = calledClass;
      super.setInterceptors(in);
      this.constructor = called;
      this.calledConHash = calledConHash;
      this.callingClass = advisor.getClazz();
      this.wrappingMethod = wrappingMethod;
   }
   
   /*
    * For copying 
    */
   protected CallerConstructorInfo(CallerConstructorInfo other)
   {
      super(other);
      this.constructor = other.constructor;
      this.callingClass = other.callingClass;
      this.wrappingMethod = other.wrappingMethod;
      this.calledConHash = other.getCalledConHash();
      this.calledClass = other.calledClass;
   }

   public Constructor<?> getConstructor()
   {
      return constructor;
   }

   public Class<?> getCallingClass()
   {
      return callingClass;
   }

   public Method getWrappingMethod()
   {
      return wrappingMethod;
   }

   public long getCalledConHash()
   {
      return calledConHash;
   }

   public Class<?> getCalledClass()
   {
      return calledClass;
   }
}
