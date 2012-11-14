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
package org.jboss.aop.metadata;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.util.PayloadKey;


/**
 * Metadata can be associated with a Thread of execution.
 * This class manages this thread metadata.  It is a singleton
 * instance wrapped by a ThreadLocal.  Invocations usually
 * have this repository as part of their MetaDataResolver chain
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 *
 */
public class ThreadMetaData implements MetaDataResolver
{
   ThreadLocal<SimpleMetaData> metaData = new ThreadLocal<SimpleMetaData>();

   public void addMetaData(Object tag, Object attr, Object value)
   {
      addMetaData(tag, attr, value, PayloadKey.MARSHALLED);
   }

   public void addMetaData(Object tag, Object attr, Object value, PayloadKey type)
   {
      SimpleMetaData data = metaData.get();
      if (data == null)
      {
         data = new SimpleMetaData();
         metaData.set(data);
      }
      data.addMetaData(tag, attr, value, type);
   }

   public Object getMetaData(Object tag, Object attr)
   {
      SimpleMetaData data = metaData.get();
      if (data == null) return null;
      return data.getMetaData(tag, attr);
   }

   public synchronized void removeMetaData(Object tag, Object attr)
   {
      SimpleMetaData data = metaData.get();
      if (data == null) return;
      data.removeMetaData(tag, attr);
   }

   public synchronized void removeGroupData(Object tag)
   {
      SimpleMetaData data = metaData.get();
      if (data == null) return;
      data.removeGroupData(tag);
   }

   public Object resolve(Invocation invocation, Object tag, Object attr)
   {
      return getMetaData(tag, attr);
   }

   public SimpleMetaData getAllMetaData(Invocation invocation)
   {
      return metaData.get();
   }
   public void clear()
   {
      metaData.set(null);
   }


   private static final ThreadMetaData me = new ThreadMetaData();
   public static ThreadMetaData instance() { return me; }
}
