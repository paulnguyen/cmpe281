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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import org.jboss.aop.ConstructionInfo;
import org.jboss.aop.advice.Interceptor;

/**
 * This is a helper wrapper class for an Invocation object.
 * It is used to add or get values or metadata that pertains to
 * an AOP Constructor interception.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
public class ConstructionInvocation extends InvocationBase
{ 
   /** The serialVersionUID */
   private static final long serialVersionUID = -6040602776303875808L;

   protected Object[] arguments = null; // MARSHALLED
   protected transient Constructor<?> constructor = null;

   public ConstructionInvocation(Interceptor[] interceptors, Constructor<?> con, Object[] args)
   {
      super(interceptors);
      this.constructor = con;
      this.arguments = args;
   }


   public ConstructionInvocation(Interceptor[] interceptors, Constructor<?> con)
   {
      super(interceptors);
      this.constructor = con;
   }

   public ConstructionInvocation(ConstructionInfo info, Interceptor[] interceptors)
   {
      super(interceptors);
      this.constructor = info.getConstructor();
      super.advisor = info.getAdvisor();
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
      return null;
   }


   /**
    * This method resolves an annotation based on the context of the invocation.
    *
    */
   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(annotation);
   }

   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      T val = super.resolveTypedAnnotation(annotation);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveTypedAnnotation(constructor, annotation);
         if (val != null) return val;
      }

      return null;
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

      if (getAdvisor() != null)
      {
         val = getAdvisor().getConstructorMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

      if (getAdvisor() != null)
      {
         val = getAdvisor().getDefaultMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

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
      ConstructionInvocationWrapper wrapper = new ConstructionInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    * 
    * @return an copy of this instance
    */
   public Invocation copy()
   {
      ConstructionInvocation wrapper = new ConstructionInvocation(interceptors, constructor, arguments);
      wrapper.setAdvisor(this.getAdvisor());
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.metadata = this.metadata;
      return wrapper;
   }

   /**
    * Returns an array containing all arguments of constructor.
    * Any changes on this TODO
    *   
    * @return TODO
    */
   /**
    * Returns a non-null array containing all constructor arguments.
    * <p>
    * The returned array can be changed by the advice or interceptor accordingly. All
    * changes will be noticed by construction advices and interceptors that are
    * executed after the current one.
    * <br>
    * However, changes to this array are limited to the scope of current advice
    * execution, and must be performed before execution of {@link #invokeNext()},
    * {@link #invokeNext(Interceptor[])}, or {@link #invokeTarget()} method.
    * Otherwise, inconsistency on joinpoint argument values may be noticed.
    *
    * @return the constructor arguments
    */
   public Object[] getArguments()
   {
      return arguments;
   }

   /**
    * Replaces constructor argument values by the ones contained in <code>
    * arguments</code>. Since this invocation is executed after construction, the
    * changes performed on <code>arguments</code> will not be applied on the
    * construction itself. However, these changes will be noticeable to all
    * subsequent construction advices.
    * <p>
    * Advices and interceptors must be aware that, for performance reasons,
    * this array does not get copied across; its reference is directly used instead.
    * Hence, changes to <code>arguments</code> array after this method being called
    * are forbidden. Otherwise, inconsistency on joinpoint argument values may be
    * noticed.
    *  
    * @param arguments a non-null array containing the new values of constructor
    *                  arguments. The size of this array must be the same as the one
    *                  of {@link #getArguments()}, as well as the element types.
    */
   public void setArguments(Object[] arguments)
   {
      this.arguments = arguments;
   }

   public Constructor<?> getConstructor()
   {
      return constructor;
   }

   public void setConstructor(Constructor<?> constructor)
   {
      this.constructor = constructor;
   }
}
