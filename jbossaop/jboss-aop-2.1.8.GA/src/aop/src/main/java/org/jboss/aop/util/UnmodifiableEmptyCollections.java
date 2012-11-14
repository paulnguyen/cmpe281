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
package org.jboss.aop.util;

import gnu.trove.TLongObjectHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.util.collection.ConcurrentSet;
import org.jboss.util.collection.WeakValueHashMap;


/**
 * Implementation of different types of maps, lists etc. that do not support modification.
 * Collections.unmodifiableMap() returns an instanceof Collections$UnmodifiableMap
 * which can only be cast to the Map interface and the same goes for lists
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class UnmodifiableEmptyCollections
{
   @SuppressWarnings("unchecked") public static final LinkedHashMap EMPTY_LINKED_HASHMAP = new LockedLinkedHashMap();
   @SuppressWarnings("unchecked") public static final HashMap EMPTY_HASHMAP = new LockedHashMap();
   @SuppressWarnings("unchecked") public static final WeakHashMap EMPTY_WEAK_HASHMAP = new LockedWeakHashMap();
   @SuppressWarnings("unchecked") public static final WeakValueHashMap EMPTY_WEAK_VALUE_HASHMAP = new LockedWeakValueHashMap();
   @SuppressWarnings("unchecked") public static final ArrayList EMPTY_ARRAYLIST = new LockedArrayList();
   @SuppressWarnings("unchecked") public static final ConcurrentSet EMPTY_COPYONWRITE_ARRAYSET = new LockedConcurrentSet();
   public static final TLongObjectHashMap EMPTY_TLONG_OBJECT_HASHMAP = new LockedTLongObjectHashMap();
   @SuppressWarnings("unchecked") public static final ConcurrentHashMap EMPTY_CONCURRENT_HASHMAP = new LockedConcurrentHashMap();
   @SuppressWarnings("unchecked") public static final ConcurrentSet EMPTY_CONCURRENT_SET = new ConcurrentSet();

   private static class LockedHashMap<K,V> extends HashMap<K,V>
   {
      private static final long serialVersionUID = 1L;

      @Override
      public V put(K key, V value) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void putAll(Map<? extends K, ? extends V> t) 
      {
         throw new UnsupportedOperationException();
      }
   }

   private static class LockedLinkedHashMap<K,V> extends LinkedHashMap<K,V>
   {
      private static final long serialVersionUID = 1L;

      @Override
      public V put(K key, V value) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void putAll(Map<? extends K, ? extends V> t) 
      {
         throw new UnsupportedOperationException();
      }
   }

   private static class LockedWeakHashMap<K,V> extends WeakHashMap<K,V>
   {
      private static final long serialVersionUID = 1L;
      @Override
      public V put(K key, V value) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void putAll(Map<? extends K, ? extends V> t) 
      {
         throw new UnsupportedOperationException();
      }
   }
   
   
   private static class LockedWeakValueHashMap<K, V> extends WeakValueHashMap<K, V>
   {
      private static final long serialVersionUID = 1L;
      @Override
      public V put(K key, V value) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void putAll(Map<? extends K, ? extends V> t) 
      {
         throw new UnsupportedOperationException();
      }
   }
   
   private static class LockedArrayList<E> extends ArrayList<E>
   {
      private static final long serialVersionUID = 1L;
      
      @Override
      public E set(int index, E element) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void add(int index, E element) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public boolean addAll(int index, Collection<? extends E> c) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public boolean add(E o)
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public boolean addAll(Collection<? extends E> c)
      {
         throw new UnsupportedOperationException();
      }
   }
   
   private static class LockedConcurrentSet<E> extends ConcurrentSet<E>
   {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean add(E arg0)
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public boolean addAll(Collection<? extends E> arg0)
      {
         throw new UnsupportedOperationException();
      }
   }
   
   private static class LockedTLongObjectHashMap extends TLongObjectHashMap
   {
      private static final long serialVersionUID = 1L;
      @Override
      public Object put(long arg0, Object arg1)
      {
         throw new UnsupportedOperationException();
      }
   }
   
   private static class LockedConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V>
   {
      private static final long serialVersionUID = 1L;

      @Override
      public V put(K key, V value) 
      {
         throw new UnsupportedOperationException();
      }
      @Override
      public void putAll(Map<? extends K, ? extends V> t) 
      {
         throw new UnsupportedOperationException();
      }
   }
   
}
