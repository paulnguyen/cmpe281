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
package org.jboss.aop.joinpoint;

import org.jboss.aop.Advisor;
import org.jboss.aop.advice.Interceptor;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 60466 $
 */
public abstract class CallerInvocation extends InvocationBase
{   
   private static final long serialVersionUID = -6040602776303974658L;
   
   protected Object callingObject;
   protected Object[] arguments;
   
   public CallerInvocation(Advisor advisor, Object callingObject, Interceptor[] interceptors)
   {
      super(interceptors);
      this.callingObject = callingObject;
      super.advisor = advisor;
   }

   public CallerInvocation(Object callingObject, Interceptor[] interceptors)
   {
      super(interceptors);
      this.callingObject = callingObject;
   }
   
   /**
    * Returns the caller object.
    *
    * @return the caller object
    */
   public Object getCallingObject()
   {
      return this.callingObject;
   }
   
   /**
    * Returns a non-null array containing all call arguments.
    * <p>
    * The returned array can be changed by the advice or interceptor accordingly. All
    * changes are reflected on joinpoint execution, and are noticed as well by
    * other advices and interceptors that are executed after the current one.
    * <br>
    * However, changes to this array are limited to the scope of current advice
    * execution, and must be performed before execution of {@link #invokeNext()},
    * {@link #invokeNext(Interceptor[])}, or {@link #invokeTarget()} method.
    * Otherwise, inconsistency on joinpoint argument values may be noticed.
    *
    * @return the call arguments
    */
   public Object[] getArguments()
   {
      return arguments;
   }

   /**
    * Replaces call argument values by the ones contained in <code>
    * arguments</code>.
    * <p>
    * Advices and interceptors must be aware that, for performance reasons,
    * this array does not get copied across; its reference is directly used instead.
    * Hence, changes to <code>arguments</code> array after this method being called
    * are forbidden. Otherwise, inconsistency on joinpoint argument values may be
    * noticed. 
    *  
    * @param arguments a non-null array containing the new values of call arguments.
    *                  The size of this array must be the same as the one of 
    *                  {@link #getArguments()}, as well as the element types.
    */
   public void setArguments(Object[] arguments)
   {
      this.arguments = arguments;
   }
}