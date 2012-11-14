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
import org.jboss.aop.ConByConInfo;
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
public class ConstructorCalledByConstructorInvocation extends CallerInvocation
{
   private static final long serialVersionUID = -1569257745454443521L;

   //info fields
   protected Constructor<?> calling;
   protected Constructor<?> constructor;
   protected Method wrappingMethod;
   
   public ConstructorCalledByConstructorInvocation(ConByConInfo info, Object callingObject, Object[] args, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingConstructor(), info.getConstructor(), info.getWrappingMethod(), callingObject, args, interceptors);
   }
   
   public ConstructorCalledByConstructorInvocation(ConByConInfo info, Object callingObject, Interceptor[] interceptors)
   {
      this(info.getAdvisor(), info.getCallingConstructor(), info.getConstructor(), info.getWrappingMethod(), callingObject, null, interceptors);
   }
   
   public ConstructorCalledByConstructorInvocation(Advisor advisor, Constructor<?> calling, Constructor<?> constructor, Method wrappingMethod, Object callingObject, Object[] args, Interceptor[] interceptors)
   {
      super(advisor, callingObject, interceptors);
      this.calling = calling;
      this.constructor = constructor;
      this.wrappingMethod = wrappingMethod;
      this.arguments = args;
   }
   
   public ConstructorCalledByConstructorInvocation(Object callingObject, Interceptor[] interceptors)
   {
      super(callingObject, interceptors);
   }
   
   /**
    *
    * @return the constructor that is calling the called constructor
    */
   public Constructor<?> getCallingConstructor()
   {
      return calling;
   }
   /**
    *
    * @return the constructor call being executed by the calling method
    */
   public Constructor<?> getCalledConstructor() { return constructor; }

   /**
    * Is the called constructor aspectized?  If so then there is a wrapping
    * method that must be called.
    * 
    * @return <code>true</code> if the constructor is wrapped
    */
   public boolean isWrapped() { return wrappingMethod != null; }

   /**
    * Is the called constructor aspectized?  If so then this method
    * returns the method that wraps the constructor.
    * 
    * @return the method that wraps the constructor if {@link #isWrapped()} returns
    *         <code>true</code>; <code>null</code> otherwise.
    */
   public Method getWrappingMethod() { return wrappingMethod; }

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
      if (wrappingMethod != null)
      {
         try
         {
            setTargetObject(wrappingMethod.invoke(null, arguments));
            return getTargetObject();
         }
         catch (InvocationTargetException e)
         {
            throw e.getTargetException();
         }
      }
      else
      {
         try
         {
            return constructor.newInstance(arguments);
         }
         catch (InstantiationException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
         catch (IllegalAccessException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
         catch (InvocationTargetException e)
         {
            throw e.getCause();
         }
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
   
    * @param newchain chain of interceptors to be inserted on invocation
    * @return an invocation wrapper
    */
   public Invocation getWrapper(Interceptor[] newchain)
   {
      ConstructorCalledByConstructorInvocationWrapper wrapper = new ConstructorCalledByConstructorInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    * 
    * @return a copy of this instance
    */
   public Invocation copy()
   {
      ConstructorCalledByConstructorInvocation wrapper = new ConstructorCalledByConstructorInvocation(advisor, calling, constructor, wrappingMethod, callingObject, arguments, interceptors);
      wrapper.setAdvisor(this.getAdvisor());
      wrapper.setTargetObject(this.getTargetObject());
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.instanceResolver = this.instanceResolver;
      wrapper.metadata = this.metadata;
      return wrapper;
   }
}