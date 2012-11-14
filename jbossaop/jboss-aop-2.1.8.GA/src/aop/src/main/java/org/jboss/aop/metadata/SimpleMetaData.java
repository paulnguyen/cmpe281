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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.util.MarshalledValue;
import org.jboss.aop.util.PayloadKey;

/**
 * Contains all the metadata associated with an
 * {@link org.jboss.aop.joinpoint.Invocation} instance.
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 */
public class SimpleMetaData implements MetaDataResolver, java.io.Externalizable
{
   static final long serialVersionUID = -3873275588469743345L;
   
   /**
    * Contains all the metadata entries.
    */
   protected HashMap<Object, HashMap<Object, MetaDataValue>> metaData =
         new HashMap<Object, HashMap<Object, MetaDataValue>>();

   /**
    * Contains the value of a metadata attribute. 
    */
   public class MetaDataValue implements java.io.Serializable
   {
      static final long serialVersionUID = -8024138149680591337L;
      public final PayloadKey type;
      public Object value;

      /**
       * Constructs a metada data entry with the initial value {@code value}.
       * 
       * @param type  payload type
       * @param value the initial value of this entry
       */
      public MetaDataValue(PayloadKey type, Object value)
      {
         this.type = type;
         this.value = value;
      }

      /**
       * Returns the value of this entry.
       * 
       * @return the value of this metadata entry.
       * @throws IOException            may be thrown during unmarshalling proccess
       *                                (only if payload key type is
       *                                {@link PayloadKey#MARSHALLED}) 
       * @throws ClassNotFoundException may be thrown during unmarshalling proccess
       *                                (only if payload key type is
       *                                {@link PayloadKey#MARSHALLED})
       */
      public Object get() throws IOException, ClassNotFoundException
      {
         if (value instanceof MarshalledValue)
         {
            value = ((MarshalledValue) value).get();
         }
         return value;
      }
      
      @Override
      public String toString()
      {
         StringBuffer sb = new StringBuffer(100);
         sb.append("[");
         sb.append("type=").append(type);
         sb.append("value=").append(value);
         sb.append("]");
         return sb.toString();
      }

   }

   /**
    * Returns the number of items contained in this simple metadata instance.
    * 
    * @return the number of items contained in this simple metadata instance.
    */
   public synchronized int size()
   {
      return metaData.size();
   }

   /**
    * Returns the tags that identify the metadata elements contained in this
    * instance.
    *  
    * @return a set containing all tags of the metadata elements contained in this
    *         instance.
    */
   public synchronized HashSet<Object> tags()
   {
      return new HashSet<Object>(metaData.keySet());
   }

   /**
    * Returns the metadata tagged with {@code name}.
    * 
    * @param name tag of the queried metadata
    * @return a map containing all the metadata tagged with {@code name}
    */
   public synchronized HashMap<Object, MetaDataValue> tag(String name)
   {
      HashMap<Object, MetaDataValue> map = metaData.get(name);
      if (map == null) return null;
      return (HashMap<Object, MetaDataValue>) map.clone();
   }

   /**
    * Indicates whether this instance contains metadata tagged with {@code name}.
    *  
    * @param name tag of the queried metadata
    * @return {@code true} only if there is metadata tagged with {@code name} in this
    *         simple metadata instance
    */
   public synchronized boolean hasTag(String name)
   {
      return metaData.get(name) != null;
   }

   /**
    * Tags metadata to structure.  Use for tags with no attributes (i.e.
    * &#64;Singleton, etc...)
    *
    * @param tag the tag that represents the no-atribute metadata to be added
    */
   public void tag(Object tag)
   {
      addMetaData(tag, EMPTY_TAG, new Object(), PayloadKey.TRANSIENT);
   }

   /**
    * Adds a metadata attribute/value pair to this instance.
    * 
    * @param tag   identifies the metadata attribute to be added
    * @param attr  the name of an attribute
    * @param value the value of {@code attr} attribute
    */
   public void addMetaData(Object tag, Object attr, Object value)
   {
      addMetaData(tag, attr, value, PayloadKey.MARSHALLED);
   }

   /**
    * Adds a metadata attribute/value pair to this instance.
    * 
    * @param tag   identifies the metadata attribute to be added
    * @param attr  the name of an attribute
    * @param value the value of {@code attr} attribute
    * @param type  the payload type
    */
   public synchronized void addMetaData(Object tag, Object attr, Object value, PayloadKey type)
   {
      HashMap<Object, MetaDataValue> groupData = metaData.get(tag);
      if (groupData == null)
      {
         groupData = new HashMap<Object, MetaDataValue>();
         metaData.put(tag, groupData);
      }
      MetaDataValue val = new MetaDataValue(type, value);
      groupData.put(attr, val);
   }

   /**
    * Returns the value of a metadata attribute.
    * 
    * @param tag   identifies the metadata attribute to be retrieved
    * @param attr  the name of an attribute
    * @return      the value of {@code attr} attribute
    */
   public synchronized Object getMetaData(Object tag, Object attr)
   {
      try
      {
         HashMap<Object, MetaDataValue> groupData = metaData.get(tag);
         if (groupData == null) return null;
         MetaDataValue val = groupData.get(attr);
         if (val == null) return null;
         return val.get();
      }
      catch (IOException ioex)
      {
         throw new RuntimeException("failed on MarshalledValue", ioex);
      }
      catch (ClassNotFoundException ex)
      {
         throw new RuntimeException("failed on MarshalledValue", ex);
      }
   }

   /**
    * Removes the metadata attribute from this instance.
    * 
    * @param tag   identifies the metadata attribute to be removed
    * @param attr  name of the attribute to be removed
    */
   public synchronized void removeMetaData(Object tag, Object attr)
   {
      HashMap<Object, MetaDataValue> groupData = metaData.get(tag);
      if (groupData != null)
      {
         groupData.remove(attr);
      }
   }

   /**
    * Removes all attributes identified by the tag {@code group}.
    * 
    * @param group identifies the attributes to be removed
    */
   public synchronized void removeGroupData(Object group)
   {
      metaData.remove(group);
   }

   /**
    * Erases all the metadata contained in this instance.
    *
    */
   public synchronized void clear()
   {
      metaData.clear();
   }

   /**
    * Merges incoming data.
    * If there is a crash in tags and attributes names, incoming data will override
    * the existing data.
    * 
    * @param data incoming that that should be merged to this instance.
    */
   public synchronized void mergeIn(SimpleMetaData data)
   {
      Iterator<Object> it = data.metaData.keySet().iterator();
      while (it.hasNext())
      {
         Object tag = it.next();
         HashMap<Object, MetaDataValue> attrs = data.metaData.get(tag);
         HashMap<Object, MetaDataValue> map = metaData.get(tag);
         if (map == null)
         {
            map = new HashMap<Object, MetaDataValue>();
            this.metaData.put(tag, map);
         }
         map.putAll(attrs);
      }
   }

   public synchronized Object resolve(Invocation invocation, Object tag, Object attr)
   {
      return getMetaData(tag, attr);
   }

   public SimpleMetaData getAllMetaData(Invocation invocation)
   {
      return this;
   }

   public void writeExternal(ObjectOutput out) throws IOException
   {
      //System.out.println("******** marshalling metadata");
      Iterator<Object> it = metaData.keySet().iterator();
      while (it.hasNext())
      {
         Object group = it.next();
         HashMap<Object, MetaDataValue> map = metaData.get(group);
         //System.out.println("******** marshalling group " + group + " size = " + map.size());
         if (map != null && map.size() > 0)
         {
            boolean groupWritten = false;
            Iterator<Object> attrs = map.keySet().iterator();
            while (attrs.hasNext())
            {
               Object attr = attrs.next();
               //System.out.println("******** marshalling attr: " + group + "." + attr);
               MetaDataValue value = map.get(attr);
               if (value.type == PayloadKey.TRANSIENT) continue;
               if (!groupWritten)
               {
                  groupWritten = true;
                  out.writeObject(group);
               }
               out.writeObject(attr);
               if (value.type == PayloadKey.AS_IS)
               {
                  out.writeObject(value.value);
               }
               else
               {
                  out.writeObject(new MarshalledValue(value.value));
               }
            }
            if (groupWritten) out.writeObject(null); // placeholder for end of attributes
         }
      }
      out.writeObject(null); // place holder for end of marshall
   }

   /**
    * {@inheritDoc}
    */
   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
   {
      //System.out.println("******** unmarshalling metadata");
      metaData = new HashMap<Object, HashMap<Object, MetaDataValue>>();
      Object group;
      while ((group = in.readObject()) != null)
      {
         //System.out.println("******** unmarshalling group: " + group);
         HashMap<Object, MetaDataValue> map = new HashMap<Object, MetaDataValue>();
         metaData.put(group, map);
         Object attr;
         while ((attr = in.readObject()) != null)
         {
            //System.out.println("******** unmarshalling attr: " + group + "." + attr);
            Object obj = in.readObject();
            if (obj instanceof MarshalledValue)
            {
               map.put(attr, new MetaDataValue(PayloadKey.MARSHALLED, obj));
            }
            else
            {
               map.put(attr, new MetaDataValue(PayloadKey.AS_IS, obj));
            }
         }
      }
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("metaData=").append(metaData);
      sb.append("]");
      return sb.toString();
   }
}
