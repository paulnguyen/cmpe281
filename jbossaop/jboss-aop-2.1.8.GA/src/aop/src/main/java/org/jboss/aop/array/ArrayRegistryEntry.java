/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.array;

import java.lang.ref.WeakReference;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class ArrayRegistryEntry
{
   /** WeakReference to the object or array containing the reference */
   WeakReference<Object> owner;
   
   /** WeakReference to the referenced array */
   WeakReference<Object> array;
   
   /** True if the object referencing the array is a "normal" object, false if it ia an array */
   boolean ownerIsRoot;
   
   ArrayRegistryEntry(Object owner, boolean ownerIsRoot, Object array)
   {
      this.owner = new WeakReference<Object>(owner);
      this.array = new WeakReference<Object>(array);
      this.ownerIsRoot = ownerIsRoot;
      if (ownerIsRoot && owner.getClass().isArray()) {
         throw new RuntimeException("Owner is root and an array was passed in");
      }
      if (!ownerIsRoot && !owner.getClass().isArray()) {
         throw new RuntimeException("Owner is not root and no array was passed in");
      }
   }

   public Object getArray()
   {
      if (array != null)
      {
         return array.get();
      }
      return null;
   }

   public Object getOwner()
   {
      if (owner != null)
      {
         return owner.get();
      }
      return null;
   }

   public boolean isOwnerRoot()
   {
      return ownerIsRoot;
   }
}
