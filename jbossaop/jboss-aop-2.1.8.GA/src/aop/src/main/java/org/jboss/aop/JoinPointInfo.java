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
package org.jboss.aop;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jboss.aop.advice.GeneratedAdvisorInterceptor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.Joinpoint;

public abstract class JoinPointInfo implements JoinPointBean
{
   private ReentrantReadWriteLock interceptorChainLock = new ReentrantReadWriteLock();
   
   /** @deprecated Use the accessors. Only public for backwards compatiblity with EJB3*/
   public Interceptor[] interceptors;

   private ArrayList<Interceptor> interceptorChain = new ArrayList<Interceptor>();
   
   private WeakReference<Advisor> advisor;
   
   protected volatile Joinpoint joinpoint;
   
   protected WeakReference<Class<?>> clazz;
   
   private String adviceString;

   protected JoinPointInfo()
   {
      this.clazz = new WeakReference<Class<?>>(null);
   }
   
   protected JoinPointInfo(Advisor advisor, Class<?> clazz)
   {
      this.clazz = new WeakReference<Class<?>>(clazz); 
      setAdvisor(advisor);
   }
   
   /*
    * For copying
    */
   @SuppressWarnings("deprecation")
   protected JoinPointInfo(JoinPointInfo other)
   {
      this.advisor = other.advisor;
      this.clazz = other.clazz;
      if (other.interceptors != null)
      {
         this.interceptors = new Interceptor[other.interceptors.length];
         System.arraycopy(other.interceptors, 0, this.interceptors, 0, other.interceptors.length);
      }
      if (other.interceptorChain != null)this.interceptorChain.addAll(interceptorChain);
   }

   @SuppressWarnings("deprecation")
   protected void clear()
   {
      interceptorChain.clear();
      interceptors = null;
      interceptors = new Interceptor[0];
   }
   
   protected void resetChainKeepInterceptors()
   {
      interceptorChain.clear();
   }
   
   protected void resetInterceptors()
   {
      interceptors = new Interceptor[0];
   }
   
   public Advisor getAdvisor() 
   {
      if (advisor == null)
      {
         return null;
      }
      return advisor.get();
   }

   public Class<?> getClazz()
   {
      return clazz.get(); 
   }
   
   public void setAdvisor(Advisor advisor) 
   {
      this.advisor = new WeakReference<Advisor>(advisor);
      if (getClazz() == null && advisor != null)
      {
         this.clazz = new WeakReference<Class<?>>(advisor.getClazz());
      }
   }

   public boolean hasAdvices()
   {
      this.interceptorChainLock.readLock().lock();
      try
      {
         return (interceptors != null && interceptors.length > 0);
      }
      finally
      {
         this.interceptorChainLock.readLock().unlock();
      }
   }
   
   public boolean equalChains(Interceptor[] otherInterceptors)
   {
      this.interceptorChainLock.readLock().lock();
      try
      {
         if (this.interceptors == null && otherInterceptors == null) return true;
         if (!(this.interceptors != null && otherInterceptors != null))return false;
         if (this.interceptors.length != otherInterceptors.length) return false;
      
         for (int i = 0 ; i < this.interceptors.length ; i++)
         {
            if(!this.interceptors[i].equals(otherInterceptors[i])) return false;
         }

         return true;
      }
      finally
      {
         this.interceptorChainLock.readLock().unlock();
      }
   }
   
   public Joinpoint getJoinpoint()
   {
      if (joinpoint == null)
      {
         joinpoint = internalGetJoinpoint();
      }
      return joinpoint;
   }
   
   public ArrayList<Interceptor> getInterceptorChain() {
      this.interceptorChainLock.readLock().lock();
      try
      {
         return interceptorChain;
      }
      finally
      {
         this.interceptorChainLock.readLock().unlock();
      }
   }

   @SuppressWarnings("deprecation")
   public Interceptor[] getInterceptors() {
      this.interceptorChainLock.readLock().lock();
      try
      {
         return interceptors;
      }
      finally
      {
         this.interceptorChainLock.readLock().unlock();
      }
   }

   @SuppressWarnings("deprecation")
   public void setInterceptors(Interceptor[] interceptors) {
      this.interceptorChainLock.writeLock().lock();
      adviceString = null;
      this.interceptors = interceptors;
      this.interceptorChainLock.writeLock().unlock();
   }

   protected abstract Joinpoint internalGetJoinpoint();
   public abstract JoinPointInfo copy();
   
   public Object resolveClassMetaData(Object key, Object attr)
   {
      return getAdvisor().getClassMetaData().getMetaData(key, attr);
   }
   
   public <T extends Annotation> T resolveClassAnnotation(Class<T> annotation)
   {
      Advisor advisor = getAdvisor();
      if (advisor != null)
      {
         return advisor.resolveTypedAnnotation(annotation);
      }
      return null;
   }
   
   public <T extends Annotation> T  resolveAnnotation(Class<T> annotation)
   {
      return null;
   }
   
   @SuppressWarnings("deprecation")
   public void cloneChains(JoinPointInfo other)
   {
      this.interceptorChainLock.writeLock().lock();
      other.interceptorChainLock.readLock().lock();
      try
      {
         interceptorChain = (ArrayList<Interceptor>) other.interceptorChain.clone();
         if (other.interceptors == null)
         {
            interceptors = null;
         }
         else
         {
            interceptors = other.interceptors.clone();
         }
      }
      finally
      {
         this.interceptorChainLock.writeLock().unlock();
         other.interceptorChainLock.readLock().unlock();
      }
   }
   
   @SuppressWarnings("deprecation")
   public String getAdviceString()
   {
      if (adviceString == null)
      {
         if (interceptors == null || interceptors.length == 0)
         {
            return "";
         }
         
         StringBuffer buf = new StringBuffer();
         for (int i = 0 ; i < interceptors.length ; i++)
         {
            if (i > 0)
            {
               buf.append(",");
            }
            
            GeneratedAdvisorInterceptor icptr = (GeneratedAdvisorInterceptor)interceptors[i];
            buf.append(icptr.getAdviceString());
         }
         
         return buf.toString();
      }
      
      return adviceString; 
   }
   
   public final ReentrantReadWriteLock getInterceptorChainReadWriteLock()
   {
      return this.interceptorChainLock;
   }
}
