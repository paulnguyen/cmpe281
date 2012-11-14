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

import org.jboss.aop.InstanceAdvisor;
import org.jboss.util.id.GUID;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77246 $
 */
public class MarshalledInterfaceProxy implements Serializable
{
   static final long serialVersionUID = -7939451715003985857L;

   private Class<?>[] interfaces;
   private ProxyMixin[] mixins;
   private InstanceAdvisor advisor;
   private GUID guid;

   protected MarshalledInterfaceProxy()
   {
   }

   public MarshalledInterfaceProxy(GUID guid, Class<?>[] intfs, ProxyMixin[] mixins, InstanceAdvisor advisor)
   {
      this.guid = guid;
      this.advisor = advisor;
      this.interfaces = intfs;
      this.mixins = mixins;
   }

   public Object readResolve() throws ObjectStreamException
   {
      try
      {
         ClassLoader cl = null;
         if (advisor.getInstance() != null)
         {
            cl = SecurityActions.getClassLoader(advisor.getInstance().getClass());
         }
         //FIXME: When will instance not be null?
         if (cl == null)
         {
            //Fall back to the context classloader
            cl = SecurityActions.getContextClassLoader();
         }
         return ProxyFactory.createInterfaceProxy(guid, cl, interfaces, mixins, advisor);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}
