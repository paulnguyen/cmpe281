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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ArrayRegistry
{
   private final static ArrayRegistryEntryFactory ELEMENT_ARRAY_REGISTRY_ENTRY_FACTORY = new ElementArrayRegistryEntryFactory();
   private final static ArrayRegistryEntryFactory FIELD_ARRAY_REGISTRY_ENTRY_FACTORY = new FieldArrayRegistryEntryFactory();
   private ArrayReferenceBuilder referenceBuilder = new ArrayReferenceBuilder();
   private static ArrayRegistry singleton = new ArrayRegistry();
   private ReadWriteLock lock = new ReentrantReadWriteLock();
   private WeakHashMap<Object, WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>>> cache = new WeakHashMap<Object, WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>>>();
   
   public static ArrayRegistry getInstance()
   {
      return singleton;
   }
   
   private ArrayRegistry()
   {
      
   }
   
   public void addFieldReference(Object owner, String fieldName, Object array)
   {
      if (array == null)
      {
         return;
      }
      addReference(owner, fieldName, array, FIELD_ARRAY_REGISTRY_ENTRY_FACTORY);
   }

   public void addElementReference(Object owner, int index, Object array)
   {
      if (array == null)
      {
         return;
      }
      addReference(owner, new Integer(index), array, ELEMENT_ARRAY_REGISTRY_ENTRY_FACTORY);
   }
   
   public void addReference(Object owner, Object qualifier, Object array, ArrayRegistryEntryFactory factory)
   {
      if (array == null)
      {
         return;
      }
      Lock writeLock = lock.writeLock();
      writeLock.lock();
      try
      {
         WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>> arrayReferences = cache.get(array);
         if (arrayReferences == null)
         {
            arrayReferences = new WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>>();
            cache.put(array, arrayReferences);
         }
         
         HashMap<Object, ArrayRegistryEntry> ownerReferences = arrayReferences.get(owner);
         if (ownerReferences == null)
         {
            ownerReferences = new HashMap<Object, ArrayRegistryEntry>();
            arrayReferences.put(owner, ownerReferences);
         }
         
         ArrayRegistryEntry regEntry = ownerReferences.get(qualifier);
         if (regEntry == null || regEntry.getArray() != array)
         {
            ArrayRegistryEntry entry = factory.createArrayRegistryEntry(owner, qualifier, factory);
            ownerReferences.put(qualifier, entry);
         }
         addNestedArrays(array);
      }
      finally
      {
         writeLock.unlock();
      }
   }
   
   public void removeFieldReference(Object owner, String fieldName, Object array)
   {
      if (array == null)
      {
         return;
      }

      removeReference(owner, fieldName, array);
   }

   public void removeElementReference(Object owner, int index, Object array)
   {
      if (array == null)
      {
         return;
      }

      removeReference(owner, new Integer(index), array);
   }

   public void removeReference(Object owner, Object qualifier, Object array)
   {
      if (array == null)
      {
         return;
      }

      Lock writeLock = lock.writeLock();
      writeLock.lock();
      try
      {
         WeakHashMap<Object, HashMap<Object,ArrayRegistryEntry>> arrayReferences = cache.get(array);
         if (arrayReferences != null)
         {
            HashMap<Object,ArrayRegistryEntry> ownerReferences = arrayReferences.get(owner);
            if (ownerReferences != null)
            {
               ArrayRegistryEntry regEntry = ownerReferences.remove(qualifier);
               if (regEntry != null)
               {
                  if (ownerReferences.size() == 0)
                  {
                     arrayReferences.remove(owner);
                     
                     if (arrayReferences.size() == 0)
                     {
                        cache.remove(array);
                     }
                  }
                  ///Should this be here or below?
                  removeNestedArrays(array);
               }
               
            }
         }
//         removeNestedArrays(owner, array);
      }
      finally
      {
         writeLock.unlock();
      }
   }

   public boolean isRegistered(Object array)
   {
      Lock readLock = lock.readLock();
      readLock.lock();
      
      try
      {
         WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>> arrayReferences = cache.get(array);
         if (arrayReferences == null)
         {
            return false;
         }
         return true;
      }
      finally
      {
         readLock.unlock();
      }
   }

   public List<ArrayReference> getArrayOwners(Object array)
   {
      Lock readLock = lock.readLock();
      readLock.lock();
      
      try
      {
         return referenceBuilder.getArrayReferences(array);
      }
      finally
      {
         readLock.unlock();
      }
   }
   
   private void addNestedArrays(Object array)
   {
      if (array == null)
      {
         return;
      }
      ArrayType type = isArray(array);
      if (type == ArrayType.MULTIDIM_ARRAY)
      {
         for (int i = 0 ; i < ((Object[])array).length ; i++)
         {
            addElementReference(array, i, ((Object[])array)[i]);
            addNestedArrays(((Object[])array)[i]);
         }
      }
      else if (type == ArrayType.OBJECT_ARRAY)
      {
         for (int i = 0 ; i < ((Object[])array).length ; i++)
         {
            Object val = ((Object[])array)[i];
            if (val != null && val.getClass().isArray())
            {
               addElementReference(array, i, ((Object[])array)[i]);
               addNestedArrays(((Object[])array)[i]);
            }
         }
      }
   }
   
   private void removeNestedArrays(Object array)
   {
      if (array == null)
      {
         return;
      }
      ArrayType type = isArray(array);
      if (type == ArrayType.MULTIDIM_ARRAY)
      {
         for (int i = 0 ; i < ((Object[])array).length ; i++)
         {
            removeElementReference(array, i, ((Object[])array)[i]);
            removeNestedArrays(((Object[])array)[i]);
         }
      }
      else if (type == ArrayType.OBJECT_ARRAY)
      {
         for (int i = 0 ; i < ((Object[])array).length ; i++)
         {
            Object val = ((Object[])array)[i];
            if (val != null && val.getClass().isArray())
            {
               removeElementReference(array, i, ((Object[])array)[i]);
               removeNestedArrays(((Object[])array)[i]);
            }
         }
      }
   }
   
   private ArrayType isArray(Object arrayCandidate)
   {
      Class<?> candidateClass = arrayCandidate.getClass();
      if (candidateClass.isArray())
      {
         Class<?> componentType = candidateClass.getComponentType();
         if (componentType.isArray())
         {
            return ArrayType.MULTIDIM_ARRAY;
         }
         if (componentType == Object.class)
         {
            return ArrayType.OBJECT_ARRAY;
         }
      }
      return ArrayType.NOT_ARRAY;
   }
   
   private interface ArrayRegistryEntryFactory
   {
      ArrayRegistryEntry createArrayRegistryEntry(Object owner, Object qualifier, Object array);
   }
   
   private static class FieldArrayRegistryEntryFactory implements ArrayRegistryEntryFactory
   {
      public ArrayRegistryEntry createArrayRegistryEntry(Object owner, Object qualifier, Object array)
      {
         return new FieldArrayRegistryEntry(owner, (String)qualifier, array);
      }
   }

   private static class ElementArrayRegistryEntryFactory implements ArrayRegistryEntryFactory
   {
      public ArrayRegistryEntry createArrayRegistryEntry(Object owner, Object qualifier, Object array)
      {
         return new ElementArrayRegistryEntry(owner, (Integer)qualifier, array);
      }
   }
   
   private class ArrayReferenceBuilder
   {
      private List<ArrayReference> getArrayReferences(Object array)
      {
         List<ArrayReference> references = null;
         WeakHashMap<Object, HashMap<Object, ArrayRegistryEntry>> arrayReferences = cache.get(array);
         if (arrayReferences != null && arrayReferences.size() > 0)
         {
            for (Object owner : arrayReferences.keySet())
            {
               HashMap<Object, ArrayRegistryEntry> ownerReferences = arrayReferences.get(owner);
               for (Object qualifier : ownerReferences.keySet())
               {
                  ArrayRegistryEntry regEntry = ownerReferences.get(qualifier);
                  
                  if (regEntry.isOwnerRoot())
                  {
                     ArrayReference reference = getRootReference((FieldArrayRegistryEntry)regEntry);
                     if (reference != null)
                     {
                        if (references == null)
                        {
                           references = new ArrayList<ArrayReference>();
                        }
                        references.add(reference);
                     }
                  }
                  else
                  {
                     List<ArrayReference> parentReferences = getElementReferences((ElementArrayRegistryEntry)regEntry);
                     if (parentReferences != null)
                     {
                        if (references == null)
                        {
                           references = new ArrayList<ArrayReference>();
                        }
                        references.addAll(parentReferences);
                     }
                  }
               }
            }
         }
         return references;
      }
      
      private ArrayReference getRootReference(FieldArrayRegistryEntry regEntry)
      {
         Object root = regEntry.getOwner();
         if (root != null)
         {
            String fieldName = regEntry.getFieldName();
            return new ArrayReferenceImpl(root, fieldName);
         }
         return null;
      }
      
      private List<ArrayReference> getElementReferences(ElementArrayRegistryEntry regEntry)
      {
         Object ownerArray = regEntry.getOwner();
         if (ownerArray != null)
         {
            List<ArrayReference> references = getArrayReferences(ownerArray);
            if (references != null && references.size() > 0)
            {
               for (ArrayReference reference : references)
               {
                  ((ArrayReferenceImpl)reference).addNestedArrayIndex(regEntry.getIndex());
               }
            }
            return references;
         }
         return null;
      }
   }
   
   private enum ArrayType
   {
      NOT_ARRAY,MULTIDIM_ARRAY,OBJECT_ARRAY; 
   }
}
