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
package org.jboss.aop.proxy.container;

import java.io.ObjectStreamException;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71276 $
 */
public class ProxyTemplate implements Delegate, AspectManaged
{

   public ProxyTemplate()
   {
   }

   private transient Advisor classAdvisor;
   private transient InstanceAdvisor instanceAdvisor;
   protected volatile transient Advisor currentAdvisor;
   
   private Object delegate;
   private Object[] mixins; // Do not remove this
   private SimpleMetaData metadata;
   private ContainerProxyCacheKey key;

   public Object getDelegate()
   {
      return delegate;
   }

   public void setDelegate(Object delegate)
   {
      this.delegate = delegate;
   }

   public void setContainerProxyCacheKey(ContainerProxyCacheKey key)
   {
      this.key = key;
   }
   
   public Advisor getAdvisor()
   {
      return currentAdvisor;
   }

   public void setAdvisor(Advisor advisor)
   {
      this.classAdvisor = advisor;
      this.currentAdvisor = classAdvisor;
   }

   public void setMetadata(SimpleMetaData metadata)
   {
      this.metadata = metadata;
   }

   public void setInstanceAdvisor(InstanceAdvisor ia)
   {
      synchronized (this)
      {
         if (this.instanceAdvisor != null)
         {
            throw new RuntimeException("InstanceAdvisor already set");
         }
         
         if (!(ia instanceof org.jboss.aop.proxy.container.InstanceProxyContainer))
         {
            throw new RuntimeException("Wrong type for instance advisor: " + instanceAdvisor);
         }
         this.instanceAdvisor = ia;
         
         currentAdvisor = (org.jboss.aop.proxy.container.InstanceProxyContainer)ia;
      }
   }

   public InstanceAdvisor getInstanceAdvisor()
   {
      synchronized(this)
      {
         if (instanceAdvisor == null)
         {
            org.jboss.aop.proxy.container.InstanceProxyContainer ipc = ((org.jboss.aop.proxy.container.ClassProxyContainer)currentAdvisor).createInstanceProxyContainer();
            setInstanceAdvisor(ipc);
         }
      }
      return instanceAdvisor;
   }
   
   public boolean equals(Object obj)
   {
      if (delegate != null)
      {
         if (obj != null && obj instanceof Delegate)
            obj = ((Delegate) obj).getDelegate();
         return delegate.equals(obj);
      }
      else
         return super.equals(obj);
   }
   
   public int hashCode()
   {
      if (delegate != null)
         return delegate.hashCode();
      else
         return super.hashCode();
   }
   
   public String toString()
   {
      if (delegate != null)
         return delegate.toString() + " (proxied by " + this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)) + ")";
      else
         return super.toString() + "(empty proxy of " + this.getClass().getSuperclass().getName() + ")";
   }
   
   private Object writeReplace() throws ObjectStreamException
   {
      return new MarshalledContainerProxy(
            this, 
            this.key,
            this.mixins, 
            this.delegate, 
            this.currentAdvisor,
            this.metadata);
   }
   
   public void localUnmarshal(MarshalledContainerProxy proxy) 
   {
      this.delegate = proxy.getDelegate();
      this.mixins = proxy.getMixins();
      this.metadata = proxy.getMetadata();
      this.key = proxy.getKey();

      this.classAdvisor = ContainerCache.getCachedContainer(this.key);
      this.currentAdvisor = classAdvisor;
      
      if (proxy.getInstanceAdvisorDomainName() != null)
      {
         ProxyAdvisorDomain domain = (ProxyAdvisorDomain)AspectManager.getTopLevelAspectManager().findManagerByName(proxy.getInstanceAdvisorDomainName());
         this.currentAdvisor = domain.getAdvisor();
      }
   }
   
   public void remoteUnmarshal(MarshalledContainerProxy proxy, MarshalledProxyAdvisor advisor)
   {
      this.delegate = proxy.getDelegate();
      this.mixins = proxy.getMixins();
      this.metadata = proxy.getMetadata();
      this.key = proxy.getKey();
      
      this.classAdvisor = advisor;
      this.currentAdvisor = advisor;
      this.instanceAdvisor = advisor;
   }
}
