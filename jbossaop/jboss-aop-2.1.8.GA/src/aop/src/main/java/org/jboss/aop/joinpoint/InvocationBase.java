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
import org.jboss.aop.InstanceAdvised;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.metadata.MetaDataResolver;
import org.jboss.aop.metadata.SimpleMetaData;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a generic object that is used in intercepted invocations
 * on field access, constructor, and methods
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
abstract public class InvocationBase implements java.io.Serializable, Invocation
{
   static final long serialVersionUID = -4880246394729260729L;

   protected org.jboss.aop.metadata.SimpleMetaData metadata = null;


   protected transient int currentInterceptor = 0;
   protected transient org.jboss.aop.advice.Interceptor[] interceptors = null;
   protected transient Advisor advisor = null;
   protected transient Map<Object, Object> responseContextInfo = null;

   protected transient Object targetObject = null;

   // todo need to refactor this as ClassProxyTemplate still needs this for remoting
   protected transient MetaDataResolver instanceResolver;
   
   public Map<Object, Object> getResponseContextInfo()
   {
      return responseContextInfo;
   }

   public void setResponseContextInfo(Map<Object, Object> responseContextInfo)
   {
      this.responseContextInfo = responseContextInfo;
   }

   public void addResponseAttachment(Object key, Object val)
   {
      if (responseContextInfo == null) responseContextInfo = new HashMap<Object, Object>(1);
      responseContextInfo.put(key, val);
   }

   public Object getResponseAttachment(Object key)
   {
      if (responseContextInfo == null) return null;
      return responseContextInfo.get(key);
   }

   /**
    * Return all the contextual data attached to this invocation
    */
   public SimpleMetaData getMetaData()
   {
      if (metadata == null) metadata = new SimpleMetaData();
      return metadata;
   }

   /**
    * Set all the contextual data attached to this invocation
    */
   public void setMetaData(SimpleMetaData data)
   {
      this.metadata = data;
   }

   public Object resolveClassMetaData(Object key, Object attr)
   {
      return getAdvisor().getClassMetaData().getMetaData(key, attr);
   }
   
   public int getCurrentInterceptor()
   {
      return currentInterceptor;
   }

   /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    */
   public Object invokeNext() throws Throwable
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

   /**
    * Invokes the target joinpoint for this invocation skipping any subsequent
    * interceptors in the chain.
    */
   public Object invokeTarget() throws Throwable
   {
      return null;
   }

   /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    * <p/>
    * The Invocation will use a new set of interceptors to do the invocation
    */
   public Object invokeNext(Interceptor[] newInterceptors) throws Throwable
   {
      // Save the old stack position
      org.jboss.aop.advice.Interceptor[] oldInterceptors = interceptors;
      int oldCurrentInterceptor = currentInterceptor;

      // Start the new stack
      interceptors = newInterceptors;
      currentInterceptor = 0;

      // Invoke the new stack
      try
      {
         return invokeNext();
      }
      finally
      {
         // Restore the old stack
         interceptors = oldInterceptors;
         currentInterceptor = oldCurrentInterceptor;
      }
   }

   public InvocationBase(org.jboss.aop.advice.Interceptor[] interceptors)
   {
      this(interceptors, null);
   }

   public InvocationBase(org.jboss.aop.advice.Interceptor[] interceptors, org.jboss.aop.metadata.SimpleMetaData meta)
   {
      // We expect a copy of the interceptor chain so that it can't change
      // in the middle of an invocation.  This is so that
      // we can redeploy interceptor chains, yet not effect
      // currently running invocations.
      this.interceptors = interceptors;
      this.metadata = meta;
   }

   /**
    * Copy constructor.
    */
   public InvocationBase(Invocation invocation)
   {
      this.interceptors = invocation.getInterceptors();
      setTargetObject(invocation.getTargetObject());
   }

   public InvocationBase()
   {
      // for externalization
   }

   /**
    * This used to be final, but I had to get rid of that since I need to 
    * lazily initialise the interceptors from the generated joinpoint/invocation classes
    */
   public org.jboss.aop.advice.Interceptor[] getInterceptors()
   {
      return interceptors;
   }


   public Object resolveClassAnnotation(Class<? extends Annotation> annotation)
   {
      return resolveTypedClassAnnotation(annotation);
   }
   
   public <T extends Annotation> T resolveTypedClassAnnotation(Class<T> annotation)
   {
      if (advisor != null) return advisor.resolveTypedAnnotation(annotation);
      return null;      
   }

   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      // todo need to add hooks for invocation and thread metadata.
      return null;
   }
   
   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      return null;
   }
   
   public Object resolveAnnotation(Class<? extends Annotation>[] annotations)
   {
      // todo need to add hooks for invocation and thread metadata.
      return null;
   }

   public <T extends Annotation> T resolveTypedAnnotation(Class<T>[] annotations)
   {
      return null;
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
      Object val = null;
      if (this.metadata != null)
      {
         val = this.metadata.resolve(this, group, attr);
         if (val != null) return val;
      }

      val = org.jboss.aop.metadata.ThreadMetaData.instance().resolve(this, group, attr);
      if (val != null) return val;

      MetaDataResolver resolver = getInstanceResolver();
      if (resolver != null)
      {
         val = getInstanceResolver().resolve(this, group, attr);
         if (val != null) return val;
      }

      return null;
   }

   public MetaDataResolver getInstanceResolver()
   {
      if (instanceResolver != null) return instanceResolver;
      if (getTargetObject() != null)
      {
         if (getTargetObject() instanceof InstanceAdvised)
         {
            InstanceAdvisor ia = ((InstanceAdvised) getTargetObject())._getInstanceAdvisor();
            if (ia != null)
            {

               instanceResolver = ia.getMetaData();
               return instanceResolver;
            }
         }
      }
      return null;
   }

   public Advisor getAdvisor()
   {
      return advisor;
   }

   public Object getTargetObject()
   {
      return targetObject;
   }

   public void setTargetObject(Object targetObject)
   {
      this.targetObject = targetObject;
   }

   public void setAdvisor(Advisor advisor)
   {
      this.advisor = advisor;
   }

   public void setInstanceResolver(MetaDataResolver instanceResolver)
   {
      this.instanceResolver = instanceResolver;
   }

}
