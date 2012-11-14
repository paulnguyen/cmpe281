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
package org.jboss.aop.proxy;

import org.jboss.aop.InstanceAdvised;
import org.jboss.aop.util.reference.MethodPersistentReference;
import org.jboss.util.id.GUID;

import java.io.ObjectStreamException;
import java.util.Map;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71276 $
 */
public abstract class Proxy implements InstanceAdvised, java.io.Serializable, MethodMapped
{
   private static final long serialVersionUID = -6040609385293748583L;

   public org.jboss.aop.InstanceAdvisor instanceAdvisor;
   public org.jboss.aop.proxy.ProxyMixin[] mixins;
   public Class<?>[] interfaces;
   public GUID guid;

   public org.jboss.aop.InstanceAdvisor _getInstanceAdvisor()
   {
      return instanceAdvisor;
   }

   public void _setInstanceAdvisor(org.jboss.aop.InstanceAdvisor newAdvisor)
   {
      instanceAdvisor = newAdvisor;
   }

   public org.jboss.aop.joinpoint.InvocationResponse _dynamicInvoke(org.jboss.aop.joinpoint.Invocation invocation)
   throws Throwable
   {
      ((org.jboss.aop.joinpoint.InvocationBase) invocation).setInstanceResolver(instanceAdvisor.getMetaData());
      org.jboss.aop.advice.Interceptor[] aspects = instanceAdvisor.getInterceptors();
      return new org.jboss.aop.joinpoint.InvocationResponse(invocation.invokeNext(aspects));
   }

   public Object writeReplace() throws ObjectStreamException
   {
      return new MarshalledInterfaceProxy(guid, interfaces, mixins, instanceAdvisor);
   }

   public abstract Map<Long, MethodPersistentReference> getMethodMap();

}
