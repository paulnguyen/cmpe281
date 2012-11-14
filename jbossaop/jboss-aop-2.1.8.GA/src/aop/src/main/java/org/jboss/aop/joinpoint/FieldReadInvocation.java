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

import org.jboss.aop.FieldInfo;
import org.jboss.aop.advice.Interceptor;

import java.lang.reflect.Field;


/**
 * This is a helper wrapper class for an Invocation object.
 * It is used to add or get values or metadata that pertains to
 * an AOP method invocation.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 46058 $
 */
public class FieldReadInvocation extends FieldInvocation
{
   private static final long serialVersionUID = 3337041720097890861L;

   public FieldReadInvocation(Field field, int index, Interceptor[] interceptors)
   {
      super(field, index, interceptors);
   }

   protected FieldReadInvocation(Interceptor[] interceptors)
   {
      super(interceptors);
   }
   
   protected FieldReadInvocation(FieldInfo info, Interceptor[] interceptors)
   {
      super(info, interceptors);
   }

   /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    */
   public Object invokeNext() throws Throwable
   {
      if (interceptors != null && currentInterceptor < interceptors.length)
      {
         try
         {
            return interceptors[currentInterceptor++].invoke(this);
         }
         finally
         {
            // so that interceptors like clustering can reinvoke down the chain
            currentInterceptor--;
         }
      }

      return invokeTarget();
   }

   /**
    * Invokes the target joinpoint for this invocation skipping any subsequent
    * interceptors in the chain.
    */
   public Object invokeTarget() throws Throwable
   {
      return field.get(getTargetObject());
   }

   /**
    * Get a wrapper invocation object that can insert a new chain of interceptors
    * at runtime to the invocation flow.  CFlow makes use of this.
    * When the wrapper object finishes its invocation chain it delegates back to
    * the wrapped invocation.
    * @param newchain
    * @return
    */
   public Invocation getWrapper(Interceptor[] newchain)
   {
      FieldReadInvocationWrapper wrapper = new FieldReadInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    * @return
    */
   public Invocation copy()
   {
      FieldReadInvocation wrapper = new FieldReadInvocation(field, index, interceptors);
      wrapper.setAdvisor(this.getAdvisor());
      wrapper.setTargetObject(this.getTargetObject());
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.metadata = this.metadata;
      wrapper.instanceResolver = this.instanceResolver;
      return wrapper;
   }
}
