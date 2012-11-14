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
import org.jboss.aop.metadata.MetaDataResolver;
import org.jboss.aop.metadata.SimpleMetaData;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * This wrapper class allows you to insert a chain of interceptors into the middle of a call stack.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 *
 **/
public class MethodInvocationWrapper extends MethodInvocation
{
   private static final long serialVersionUID = 6399861718513077210L;

   MethodInvocation wrapped;

   public MethodInvocationWrapper(MethodInvocation wrapped, org.jboss.aop.advice.Interceptor[] interceptors)
   {
      super(interceptors);
      this.wrapped = wrapped;
   }

   public Object getMetaData(Object group, Object attr)
   {
      return wrapped.getMetaData(group, attr);
   }

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
      try
      {
         return wrapped.invokeNext();
      }
      finally
      {
         responseContextInfo = wrapped.getResponseContextInfo();
      }
   }

   public MetaDataResolver getInstanceResolver()
   {
      return wrapped.getInstanceResolver();
   }

   public Object[] getArguments()
   {
      return wrapped.getArguments();
   }

   public void setArguments(Object[] args)
   {
      wrapped.setArguments(args);
   }
   public Object getTargetObject()
   {
      return wrapped.getTargetObject();
   }

   public Invocation copy()
   {
      MethodInvocationWrapper invocation = new MethodInvocationWrapper((MethodInvocation)wrapped.copy(), interceptors);
      invocation.currentInterceptor = this.currentInterceptor;
      return invocation;
   }

   public Method getMethod()
   {
      return wrapped.getMethod();
   }

   public Method getActualMethod()
   {
      return wrapped.getActualMethod();
   }

   public long getMethodHash()
   {
      return wrapped.getMethodHash();
   }

   public Advisor getAdvisor()
   {
      return wrapped.getAdvisor();
   }

   public Map<Object, Object> getResponseContextInfo()
   {
      return wrapped.getResponseContextInfo();
   }

   public void setResponseContextInfo(Map<Object, Object> responseContextInfo)
   {
      wrapped.setResponseContextInfo(responseContextInfo);
   }

   public Object getResponseAttachment(Object key)
   {
      return wrapped.getResponseAttachment(key);
   }

   public void addResponseAttachment(Object key, Object val)
   {
      wrapped.addResponseAttachment(key, val);
   }

   public SimpleMetaData getMetaData()
   {
      return wrapped.getMetaData();
   }

   public void setMetaData(SimpleMetaData data)
   {
      wrapped.setMetaData(data);
   }

   public void setTargetObject(Object targetObject)
   {
      wrapped.setTargetObject(targetObject);
   }

}
