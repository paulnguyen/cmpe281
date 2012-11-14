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
package org.jboss.aop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/**
 * A simple replacement for the RMI MarshalledObject that uses the thread
 * context class loader for resolving classes and proxies. This currently does
 * not support class annotations and dynamic class loading.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 74557 $
 */
public class MarshalledValue
   implements java.io.Externalizable
{
   /** Serial Version Identifier. */
   private static final long serialVersionUID = -1527598981234110311L;

   /**
    * The serialized form of the value. If <code>serializedForm</code> is
    * <code>null</code> then the object marshalled was a <code>null</code>
    * reference.
    */
   private byte[] serializedForm = null;
   
   /**
    * The RMI MarshalledObject hash of the serializedForm array
    */
   private int hashCode;
   private boolean isHashComputed = false;
   
   private ByteArrayOutputStream baos = null;

   /**
    * Exposed for externalization.
    */
   public MarshalledValue()
   {
      super();
   }

   public MarshalledValue(Object obj) throws IOException
   {
      baos = new ByteArrayOutputStream();
      MarshalledValueOutputStream mvos = new MarshalledValueOutputStream(baos);
      mvos.writeObject(obj);
      mvos.flush();

      serializedForm = baos.toByteArray();
      
      isHashComputed = false;
   }

   public Object get() throws IOException, ClassNotFoundException
   {
      if (serializedForm == null)
         return null;

      ByteArrayInputStream bais = new ByteArrayInputStream(serializedForm);
      MarshalledValueInputStream mvis = new MarshalledValueInputStream(bais);
      return mvis.readObject();
   }

   /**
    * Returns the serialized form of this value.
    * 
    * @return the serialized form of this value
    */
   public byte[] toByteArray()
   {
      return serializedForm;
   }

   /**
    * Returns the length of this value's serialized form.
    * 
    * @return the length of {@link #toByteArray()}
    */
   public int size()
   {
      int size = serializedForm != null ? serializedForm.length : 0;
      return size;
   }

   /**
    * Return a hash code for the serialized form of the value.
    *
    * @return the serialized form value hash.
    */
   public int hashCode()
   {
      // lazy computing of hash: we don't need it most of the time
      //
      if (!isHashComputed)
      {
         int hash = 0;
         for (int i = 0; i < serializedForm.length; i++)
         {
            hash = 31 * hash + serializedForm[i];
         }
         
         hashCode = hash;
      }

      return hashCode;
   }

   /**
    * Compares {@code obj} with this instance for equality.
    * Returns {@code true} if and only if {@code obj} is also a marshalled value,
    * whose value is the same as the one contained in this instance.
    * 
    * @param obj the object to be compared for equality with this instance
    * @return {@code true} if {@code obj} is considered equal to this instance
    */
   public boolean equals(Object obj)
   {
      if( this == obj )
         return true;

      boolean equals = false;
      if( obj instanceof MarshalledValue )
      {
         MarshalledValue mv = (MarshalledValue) obj;
         if( serializedForm == mv.serializedForm )
         {
            equals = true;
         }
         else
         {
            equals = Arrays.equals(serializedForm, mv.serializedForm);
         }
      }
      return equals;
   }
   
   /**
    * The object implements the readExternal method to restore its
    * contents by calling the methods of DataInput for primitive
    * types and readObject for objects, strings and arrays.  The
    * readExternal method must read the values in the same sequence
    * and with the same types as were written by writeExternal.
    *
    * @param in the stream to read data from in order to restore the object
    * 
    * @throws java.io.IOException              if I/O errors occur
    * @throws java.lang.ClassNotFoundException   If the class for an object being
    *                                  restored cannot be found.
    */
   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
   {
      int length = in.readInt();
      serializedForm = null;
      if( length > 0 )
      {
         serializedForm = new byte[length];
         in.readFully(serializedForm);
      }
      isHashComputed = false;
   }

   /**
    * The object implements the writeExternal method to save its contents
    * by calling the methods of DataOutput for its primitive values or
    * calling the writeObject method of ObjectOutput for objects, strings,
    * and arrays.
    *
    * @serialData Overriding methods should use this tag to describe
    *            the data layout of this Externalizable object.
    *            List the sequence of element types and, if possible,
    *            relate the element to a public/protected field and/or
    *            method of this Externalizable class.
    *
    * @param out    the stream to write the object to
    * 
    * @throws java.io.IOException   Includes any I/O exceptions that may occur
    */
   public void writeExternal(ObjectOutput out) throws IOException
   {
      out.writeInt(baos.size());
      out.write(baos.toByteArray()); 
   }
}
