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

import java.lang.reflect.Constructor;
import java.util.Map;
import org.jboss.aop.Advisor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.metadata.MetaDataResolver;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 *
 **/
public class ConstructionInvocationWrapper extends ConstructorInvocation
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 7005423783330774114L;

   ConstructionInvocation wrapped;

   public ConstructionInvocationWrapper(ConstructionInvocation wrapped, Interceptor[] interceptors)
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

   public Invocation copy()
   {
      ConstructionInvocationWrapper invocation = new ConstructionInvocationWrapper((ConstructionInvocation)wrapped.copy(), interceptors);
      invocation.currentInterceptor = this.currentInterceptor;
      return invocation;
   }

   public Constructor<?> getConstructor()
   {
      return wrapped.getConstructor();
   }

//   public void setConstructor(Constructor<?> constructor)
//   {
//      wrapped.setConstructor(constructor);
//   }

   public Map<Object, Object> getResponseContextInfo()
   {
      return wrapped.getResponseContextInfo();
   }

   public void setResponseContextInfo(Map<Object, Object> responseContextInfo)
   {
      wrapped.setResponseContextInfo(responseContextInfo);
   }

   public void addResponseAttachment(Object key, Object val)
   {
      wrapped.addResponseAttachment(key, val);
   }

   public Object getResponseAttachment(Object key)
   {
      return wrapped.getResponseAttachment(key);
   }

   public SimpleMetaData getMetaData()
   {
      return wrapped.getMetaData();
   }

   public void setMetaData(SimpleMetaData data)
   {
      wrapped.setMetaData(data);
   }

   public Advisor getAdvisor()
   {
      return wrapped.getAdvisor();
   }

   public Object getTargetObject()
   {
      return wrapped.getTargetObject();
   }

   public void setTargetObject(Object targetObject)
   {
      wrapped.setTargetObject(targetObject);
   }

}
