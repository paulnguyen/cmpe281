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
import org.jboss.aop.MethodByConInfo;
import org.jboss.aop.advice.Interceptor;

import java.lang.reflect.Constructor;
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
public class MethodCalledByConstructorInvocation extends CallerInvocation
{ 
   private static final long serialVersionUID = -1903092605231217830L;
   
   //info fields
   protected Constructor<?> calling;
   protected Method method;
   
   public MethodCalledByConstructorInvocation(MethodByConInfo info, Object callingObject, Object target, Object[] args, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingConstructor(), info.getMethod(), callingObject, target, args, interceptors);
   }

   public MethodCalledByConstructorInvocation(MethodByConInfo info, Object callingObject, Object target, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingConstructor(), info.getMethod(), callingObject, target, null, interceptors);
   }
   
   public MethodCalledByConstructorInvocation(Advisor advisor, Constructor<?> calling, Method method, Object callingObject, Object target, Object[] args, Interceptor[] interceptors)
   {
      super(advisor, callingObject, interceptors);
      this.calling = calling;
      this.method = method;
      setTargetObject(target);
      this.arguments = args;
   }

   public MethodCalledByConstructorInvocation(Object callingObject, Interceptor[] interceptors)
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
    *
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
      MethodCalledByConstructorInvocationWrapper wrapper = new MethodCalledByConstructorInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    * 
    * @return a copy of this instance
    */
   public Invocation copy()
   {
      MethodCalledByConstructorInvocation wrapper = new MethodCalledByConstructorInvocation(
            advisor, calling, method, callingObject, targetObject, arguments, interceptors);
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.metadata = this.metadata;
      wrapper.instanceResolver = this.instanceResolver;
      return wrapper;
   }

   /**
    * The constructor that is calling the method
    *
    * @return The constructor that is calling the method
    */
   public Constructor<?> getCalling()
   {
      return calling;
   }

   /**
    *
    * @return the method that is being called
    */
   public Method getCalledMethod()
   {
      return method;
   }
}