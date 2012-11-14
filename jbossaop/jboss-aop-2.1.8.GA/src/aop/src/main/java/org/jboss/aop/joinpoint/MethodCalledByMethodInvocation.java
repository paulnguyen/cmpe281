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
import org.jboss.aop.MethodByMethodInfo;
import org.jboss.aop.advice.Interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * This is a helper wrapper class for an Invocation object.
 * It is used to add or get values or metadata that pertains to
 * an AOP method invocation.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
public class MethodCalledByMethodInvocation extends CallerInvocation
{
   private static final long serialVersionUID = -156920151151728318L;
   
   //info fields
   Class<?> callingClass;
   Method callingMethod;
   Method method;
   
   public MethodCalledByMethodInvocation(MethodByMethodInfo info, Object callingObject, Object targetObject, Object[] arguments, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingClass(), info.getCallingMethod(), info.getMethod(), callingObject, targetObject, arguments, interceptors);
   }

   public MethodCalledByMethodInvocation(MethodByMethodInfo info, Object callingObject, Object targetObject, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingClass(), info.getCallingMethod(), info.getMethod(), callingObject, targetObject, null, interceptors);
   }

   public MethodCalledByMethodInvocation(Advisor advisor, Class<?> callingClass, 
         Method callingMethod, Method method, Object callingObject, Object targetObject, Object[] args, Interceptor[] interceptors)
   {
      super(advisor, callingObject, interceptors);
      this.callingClass = callingClass;
      this.callingMethod = callingMethod;
      this.method = method;
      setTargetObject(targetObject);
      this.arguments = args;
   }
   
   public MethodCalledByMethodInvocation(Object callingObject, Interceptor[] interceptors)
   {
      super(callingObject, interceptors);
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
      try
      {
         return method.invoke(getTargetObject(), arguments);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
   }

   
   /**
    * This method resolves metadata based on the context of the invocation.
    * It iterates through its list of MetaDataResolvers to find out the
    * value of the metadata desired.
    * <p/>
    * This list usually is ThreadMetaData, InstanceAdvisor.getMetaData
    * ClassAdvisor.getMethodMetaData (or field, or constructor)
    * ClassAdvisor.getDefaultMetaData
    */
   public Object getMetaData(Object group, Object attr)
   {
      Object val = super.getMetaData(group, attr);
      if (val != null) return val;

      // todo what to do for caller side metadata?
      return null;
   }

   /**
    * Returns a wrapper invocation object that can insert a new chain of interceptors
    * at runtime to the invocation flow.  CFlow makes use of this.
    * When the wrapper object finishes its invocation chain it delegates back to
    * the wrapped invocation.
    *
    * @param newchain chain of interceptors to be inserted on invocation
    * @return an invocation wrapper
    */
   public Invocation getWrapper(Interceptor[] newchain)
   {
      MethodCalledByMethodInvocationWrapper wrapper = new MethodCalledByMethodInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    *
    * @return a copy of this instance
    */
   public Invocation copy()
   {
      MethodCalledByMethodInvocation wrapper = new MethodCalledByMethodInvocation(advisor, callingClass, 
            callingMethod, method, callingObject, targetObject, arguments, interceptors);
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.instanceResolver = this.instanceResolver;
      wrapper.metadata = this.metadata;
      return wrapper;
   }

   /**
    * @return The class that is making the call on the method
    */
   public Class<?> getCallingClass()
   {
      return callingClass;
   }

   /**
    * @return The method that is making the call on the method
    */
   public Method getCallingMethod()
   {
      return callingMethod;
   }

   public Method getCalledMethod()
   {
      return method;
   }
}