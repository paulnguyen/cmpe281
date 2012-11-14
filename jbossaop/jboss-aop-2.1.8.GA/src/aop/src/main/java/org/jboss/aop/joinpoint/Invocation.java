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
import org.jboss.aop.metadata.SimpleMetaData;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
public interface Invocation
{
   Map<Object, Object> getResponseContextInfo();

   void setResponseContextInfo(Map<Object, Object> responseContextInfo);

   void addResponseAttachment(Object key, Object val);

   Object getResponseAttachment(Object key);

   /**
    * Return all the contextual untyped data attached to this invocation
    */
   SimpleMetaData getMetaData();

   /**
    * Set all the contextual untyped data attached to this invocation
    */
   void setMetaData(SimpleMetaData data);

   /**
    * Resolve class level untyped metadata based on a key  and the attribute of the key
    *
    * @param key
    * @param attr
    * @return
    */
   Object resolveClassMetaData(Object key, Object attr);

   
   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Class
    *
    * @param annotation
    * @return
    */
   Object resolveClassAnnotation(Class<? extends Annotation> annotation);

   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Class
    *
    * @param annotation
    * @return
    */
   <T extends Annotation> T resolveTypedClassAnnotation(Class<T> annotation);

   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Method, Constructor, Field, etc.
    *
    * @param annotation
    * @return
    */
   Object resolveAnnotation(Class<? extends Annotation> annotation);
   
   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Method, Constructor, Field, etc.
    *
    * @param annotation
    * @return
    */
   <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation);

   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Method, Constructor, Field, etc.
    *
    * @param annotations
    * @return
    */
   Object resolveAnnotation(Class<? extends Annotation>[] annotations);

   /**
    * Abstraction for resolving an annotation so that
    * it can be overriden from the Method, Constructor, Field, etc.
    *
    * @param annotations
    * @return
    */
   <T extends Annotation> T resolveTypedAnnotation(Class<T>[] annotations);

    /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    */
   Object invokeNext() throws Throwable;
   
   /**
    * Invokes the target joinpoint for this invocation skipping any subsequent
    * interceptors in the chain.
    */
   Object invokeTarget() throws Throwable;

   /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    * <p/>
    * The Invocation will use a new set of interceptors to do the invocation
    */
   Object invokeNext(Interceptor[] newInterceptors) throws Throwable;

   Interceptor[] getInterceptors();

   /**
    * This method resolves untyped metadata based on the context of the invocation.
    * It iterates through its list of MetaDataResolvers to find out the
    * value of the metadata desired.
    * <p/>
    * This list usually is ThreadMetaData, InstanceAdvisor.getMetaData
    * ClassAdvisor.getMethodMetaData (or field, or constructor)
    * ClassAdvisor.getDefaultMetaData
    */
   Object getMetaData(Object group, Object attr);

   Object getTargetObject();

   void setTargetObject(Object targetObject);

   /**
    * Get a wrapper invocation object that can insert a new chain of interceptors
    * at runtime to the invocation flow.  CFlow makes use of this.
    * When the wrapper object finishes its invocation chain it delegates back to
    * the wrapped invocation.
    *
    * @param newchain
    * @return
    */
   Invocation getWrapper(Interceptor[] newchain);

   /**
    * Copies complete state of Invocation object so that it could possibly
    * be reused in a spawned thread.
    *
    * @return
    */
   public Invocation copy();

   public Advisor getAdvisor();
}
