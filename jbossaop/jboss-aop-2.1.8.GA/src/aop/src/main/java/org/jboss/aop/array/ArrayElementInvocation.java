/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.array;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.jboss.aop.Advisor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.InvocationBase;
import org.jboss.aop.metadata.MetaDataResolver;
import org.jboss.util.NotImplementedException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class ArrayElementInvocation extends InvocationBase
{
   private static final long serialVersionUID = 1L;
   int index;
   
   public ArrayElementInvocation(Interceptor[] interceptors, Object array, int index)
   {
      super(interceptors);
      super.setTargetObject(array);
      this.index = index;
   }

   public int getIndex()
   {
      return index;
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

   public abstract Object invokeTarget();


   @Override
   public void addResponseAttachment(Object key, Object val)
   {
      throw new NotImplementedException();
   }

   @Override
   public Advisor getAdvisor()
   {
      throw new NotImplementedException();
   }

   @Override
   public int getCurrentInterceptor()
   {
      throw new NotImplementedException();
   }

   @Override
   public Object getResponseAttachment(Object key)
   {
      throw new NotImplementedException();
   }

   @Override
   public Map<Object, Object> getResponseContextInfo()
   {
      throw new NotImplementedException();
   }

   @Override
   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      throw new NotImplementedException();
   }

   @Override
   public Object resolveAnnotation(Class<? extends Annotation>[] annotations)
   {
      throw new NotImplementedException();
   }

   @Override
   public Object resolveClassAnnotation(Class<? extends Annotation> annotation)
   {
      throw new NotImplementedException();
   }

   @Override
   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      throw new NotImplementedException();
   }

   @Override
   public <T extends Annotation> T resolveTypedAnnotation(Class<T>[] annotations)
   {
      throw new NotImplementedException();
   }

   @Override
   public <T extends Annotation> T resolveTypedClassAnnotation(Class<T> annotation)
   {
      throw new NotImplementedException();
   }


   @Override
   public Object resolveClassMetaData(Object key, Object attr)
   {
      throw new NotImplementedException();
   }

   @Override
   public void setAdvisor(Advisor advisor)
   {
      throw new NotImplementedException();
   }

   @Override
   public void setInstanceResolver(MetaDataResolver instanceResolver)
   {
      throw new NotImplementedException();
   }

   @Override
   public void setResponseContextInfo(Map<Object, Object> responseContextInfo)
   {
      throw new NotImplementedException();
   }

   public Invocation copy()
   {
      throw new NotImplementedException();
   }

   public Invocation getWrapper(Interceptor[] newchain)
   {
      throw new NotImplementedException();
   }
   
   public List<ArrayReference> getArrayOwners()
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      return registry.getArrayOwners(targetObject);
   }
}
