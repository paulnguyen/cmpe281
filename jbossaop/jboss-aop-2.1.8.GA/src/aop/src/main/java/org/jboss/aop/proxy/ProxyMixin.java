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

import java.io.Serializable;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71276 $
 */
public class ProxyMixin implements Serializable
{
   static final long serialVersionUID = -6150046185716362835L;

   private Object mixin;
   private Class<?>[] interfaces;

   protected ProxyMixin()
   {
   }

   public ProxyMixin(Object mixin, Class<?>[] interfaces)
   {
      this.interfaces = interfaces;
      this.mixin = mixin;
   }

   public Object getMixin()
   {
      return mixin;
   }

   public Class<?>[] getInterfaces()
   {
      return interfaces;
   }

}