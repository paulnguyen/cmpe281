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
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.util.PayloadKey;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 *
 */
public class FieldMetaData implements MetaDataResolver
{
   HashMap<String, SimpleMetaData> fieldMetaData = new HashMap<String, SimpleMetaData>();

   public boolean hasTag(String tag)
   {
      for (SimpleMetaData map : fieldMetaData.values())
      {
         if (map.hasTag(tag)) return true;
      }
      return false;
   }
   public boolean hasTag(Field field, String tag)
   {
      return hasTag(field.getName(), tag);
   }



   public synchronized boolean hasTag(String fieldName, String tag)
   {
      SimpleMetaData meta = getFieldMetaData(fieldName);
      if (meta == null) return false;
      return meta.hasTag(tag);
   }

   public void tagField(Field field, Object tag)
   {
      addFieldMetaData(field, tag, EMPTY_TAG, new Object(), PayloadKey.TRANSIENT);
   }

   public void tagField(String field, Object tag)
   {
      addFieldMetaData(field, tag, EMPTY_TAG, new Object(), PayloadKey.TRANSIENT);
   }

   public void addFieldMetaData(Field field, Object tag, Object attr, Object value)
   {
      addFieldMetaData(field, tag, attr, value, PayloadKey.MARSHALLED);
   }

   public void addFieldMetaData(Field field, Object tag, Object attr, Object value, PayloadKey type)
   {
      addFieldMetaData(field.getName(), tag, attr, value, type);
   }
   public void addFieldMetaData(String key, Object tag, Object attr, Object value)
   {
      addFieldMetaData(key, tag, attr, value, PayloadKey.MARSHALLED);
   }
   public synchronized void addFieldMetaData(String key, Object tag, Object attr, Object value, PayloadKey type)
   {
      String fieldName = key;
      SimpleMetaData fieldData = fieldMetaData.get(fieldName);
      if (fieldData == null)
      {
         fieldData = new SimpleMetaData();
         fieldMetaData.put(fieldName, fieldData);
      }
      fieldData.addMetaData(tag, attr, value, type);
   }

   public Object getFieldMetaData(Field field, Object tag, Object attr)
   {
      SimpleMetaData data = getFieldMetaData(field.getName());
      if (data == null) return null;
      return data.getMetaData(tag, attr);
   }

   public synchronized Object getFieldMetaData(String fieldName, Object tag, Object attr)
   {
      SimpleMetaData fieldData = fieldMetaData.get(fieldName);
      if (fieldData == null) return null;
      return fieldData.getMetaData(tag, attr);
   }

   public synchronized Iterator<String> getFields()
   {
      return fieldMetaData.keySet().iterator();
   }

   public synchronized SimpleMetaData getFieldMetaData(String field)
   {
      return fieldMetaData.get(field);
   }

   public synchronized void clear()
   {
      fieldMetaData.clear();
   }

   public Object resolve(Invocation invocation, Object tag, Object attr)
   {
      Field field = ((FieldInvocation)invocation).getField();
      return getFieldMetaData(field, tag, attr);
   }

   public synchronized SimpleMetaData getAllMetaData(Invocation invocation)
   {
      Field field = ((FieldInvocation)invocation).getField();
      return fieldMetaData.get(field);
   }
}
