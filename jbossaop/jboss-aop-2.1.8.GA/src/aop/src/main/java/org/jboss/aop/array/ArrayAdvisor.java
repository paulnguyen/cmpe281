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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.GeneratedAdvisorInterceptor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PerVmAdvice;
import org.jboss.aop.advice.PrecedenceSorter;
import org.jboss.aop.advice.ScopedInterceptorFactory;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ArrayAdvisor
{
   static HashSet<ArrayBinding> bindings = new LinkedHashSet<ArrayBinding>();
   static boolean updated;
   
   public static void addBinding(ArrayBinding arrayBinding)
   {
      ChainCreator.addBinding(arrayBinding);
   }
   
   public static void removeBinding(ArrayBinding arrayBinding)
   {
      ChainCreator.removeBinding(arrayBinding);
   }
   
   public static Interceptor[] getReadInterceptors()
   {
      return ChainCreator.getReadInterceptors();
   }
   
   public static Interceptor[] getWriteInterceptors()
   {
      return ChainCreator.getWriteInterceptors();
   }
   
   public static void updateArrayField(Object target, String fieldName, Object oldValue, Object newValue)
   {
      ArrayRegistry registry = ArrayRegistry.getInstance();
      registry.removeFieldReference(target, fieldName, oldValue);
      registry.addFieldReference(target, fieldName, newValue);
   }

   public static void arrayWriteObject(Object array, int index, Object value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      ArrayRegistry registry = ArrayRegistry.getInstance();
      if (registry.isRegistered(array))
      {
         //The old value might be an array, remove references to that
         Object oldValue = ((Object[])array)[index];
         boolean ignoreUpdate = (oldValue == value);
         if (!ignoreUpdate)
         {
            registry.removeElementReference(array, index, oldValue);
            
            //The new value might be an array
            if (value != null && value.getClass().isArray())
            {
               registry.addElementReference(array, index, value);
            }
            if (interceptors != null)
            {
               ObjectArrayElementWriteInvocation invocation = new ObjectArrayElementWriteInvocation(interceptors, (Object[])array, index, value);
               invocation.invokeNext();
               return;
            }
         }
      }
      ((Object[])array)[index] = value;
   }
   
   public static void arrayWriteInt(Object array, int index, int value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((int[])array)[index] != value)
      {
         IntArrayElementWriteInvocation invocation = new IntArrayElementWriteInvocation(interceptors, ((int[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((int[])array)[index] = value;
      }
   }

   public static void arrayWriteByteOrBoolean(Object array, int index, byte value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         if (array instanceof boolean[])
         {
            if (((boolean[])array)[index] != ByteBooleanConverter.toBoolean(value))
            
            {
               BooleanArrayElementWriteInvocation invocation = new BooleanArrayElementWriteInvocation(interceptors, ((boolean[])array), index, ByteBooleanConverter.toBoolean(value));
               invocation.invokeNext();
               return;
            }
            ((boolean[])array)[index] = ByteBooleanConverter.toBoolean(value);
         }
         else 
         {
            if (((byte[])array)[index] != value)
            {
               ByteArrayElementWriteInvocation invocation = new ByteArrayElementWriteInvocation(interceptors, ((byte[])array), index, value);
               invocation.invokeNext();
               return;
            }
            ((byte[])array)[index] = value;
         }
      }
   }

   public static void arrayWriteChar(Object array, int index, char value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((char[])array)[index] != value)
      {
         CharArrayElementWriteInvocation invocation = new CharArrayElementWriteInvocation(interceptors, ((char[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((char[])array)[index] = value;
      }
   }

   public static void arrayWriteDouble(Object array, int index, double value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((double[])array)[index] != value)
      {
         DoubleArrayElementWriteInvocation invocation = new DoubleArrayElementWriteInvocation(interceptors, ((double[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((double[])array)[index] = value;
      }
   }

   public static void arrayWriteShort(Object array, int index, short value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((short[])array)[index] != value)
      {
         ShortArrayElementWriteInvocation invocation = new ShortArrayElementWriteInvocation(interceptors, ((short[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((short[])array)[index] = value;
      }
   }

   public static void arrayWriteFloat(Object array, int index, float value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((float[])array)[index] != value)
      {
         FloatArrayElementWriteInvocation invocation = new FloatArrayElementWriteInvocation(interceptors, ((float[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((float[])array)[index] = value;
      }
   }

   public static void arrayWriteLong(Object array, int index, long value) throws Throwable
   {
      Interceptor[] interceptors = getWriteInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array) && ((long[])array)[index] != value)
      {
         LongArrayElementWriteInvocation invocation = new LongArrayElementWriteInvocation(interceptors, ((long[])array), index, value);
         invocation.invokeNext();
      }
      else
      {
         ((long[])array)[index] = value;
      }
   }

   public static Object arrayReadObject(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         ObjectArrayElementReadInvocation invocation = new ObjectArrayElementReadInvocation(interceptors, (Object[])array, index);
         return invocation.invokeNext();
      }
      else
      {
         return ((Object[])array)[index];
      }
   }

   public static int arrayReadInt(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         IntArrayElementReadInvocation invocation = new IntArrayElementReadInvocation(interceptors, (int[])array, index);
         return ((Integer)invocation.invokeNext()).intValue();
      }
      else
      {
         return ((int[])array)[index];
      }
   }

   public static byte arrayReadByteOrBoolean(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         if (array instanceof boolean[])
         {
            BooleanArrayElementReadInvocation invocation = new BooleanArrayElementReadInvocation(interceptors, (boolean[])array, index);
            boolean b = ((Boolean)invocation.invokeNext()).booleanValue();
            return ByteBooleanConverter.toByte(b);
         }
         else
         {
            ByteArrayElementReadInvocation invocation = new ByteArrayElementReadInvocation(interceptors, (byte[])array, index);
            return ((Byte)invocation.invokeNext()).byteValue();
         }
      }
      else
      {
         if (array instanceof boolean[])
         {
            return ByteBooleanConverter.toByte(((boolean[])array)[index]);
         }
         else
         {
            return ((byte[])array)[index];
         }
      }
   }

   public static char arrayReadChar(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         CharArrayElementReadInvocation invocation = new CharArrayElementReadInvocation(interceptors, (char[])array, index);
         return ((Character)invocation.invokeNext()).charValue();
      }
      else
      {
         return ((char[])array)[index];
      }
   }

   public static double arrayReadDouble(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         DoubleArrayElementReadInvocation invocation = new DoubleArrayElementReadInvocation(interceptors, (double[])array, index);
         return ((Double)invocation.invokeNext()).doubleValue();
      }
      else
      {
         return ((double[])array)[index];
      }
   }

   public static float arrayReadFloat(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         FloatArrayElementReadInvocation invocation = new FloatArrayElementReadInvocation(interceptors, (float[])array, index);
         return ((Float)invocation.invokeNext()).floatValue();
      }
      else
      {
         return ((float[])array)[index];
      }
   }

   public static long arrayReadLong(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         LongArrayElementReadInvocation invocation = new LongArrayElementReadInvocation(interceptors, (long[])array, index);
         return ((Long)invocation.invokeNext()).longValue();
      }
      else
      {
         return ((long[])array)[index];
      }
   }

   public static short arrayReadShort(Object array, int index) throws Throwable
   {
      Interceptor[] interceptors = getReadInterceptors();
      if (interceptors != null && ArrayRegistry.getInstance().isRegistered(array))
      {
         ShortArrayElementReadInvocation invocation = new ShortArrayElementReadInvocation(interceptors, (short[])array, index);
         return ((Short)invocation.invokeNext()).shortValue();
      }
      else
      {
         return ((short[])array)[index];
      }
   }
   
   private static class ChainCreator
   {
      private static ReadWriteLock lock = new ReentrantReadWriteLock(); 
      static Interceptor[] readInterceptors;
      static Interceptor[] writeInterceptors;
      public static void addBinding(ArrayBinding arrayBinding)
      {
         Lock writeLock = lock.writeLock();
         writeLock.lock();
         try
         {
            bindings.add(arrayBinding);
            updated = true;
         }
         finally
         {
            writeLock.unlock();
         }
      }
      
      public static void removeBinding(ArrayBinding arrayBinding)
      {
         Lock writeLock = lock.writeLock();
         writeLock.lock();
         try
         {
            bindings.remove(arrayBinding);
            updated = true;
         }
         finally
         {
            writeLock.unlock();
         }
      }

      public static Interceptor[] getReadInterceptors()
      {
         return getInterceptors(true);
      }
      
      public static Interceptor[] getWriteInterceptors()
      {
         return getInterceptors(false);
      }
      
      private static Interceptor[] getInterceptors(boolean read)
      {
         Lock readLock = lock.readLock();
         readLock.lock();
         boolean lockedRead = true;
         try
         {
            if (updated)
            {
               readLock.unlock();
               lockedRead = false;
               Lock writeLock = lock.writeLock();
               writeLock.lock();
               try
               {
                  if (updated)
                  {
                     //Create the interceptor chains
                     ArrayList<Interceptor> newReadInterceptors = new ArrayList<Interceptor>();
                     ArrayList<Interceptor> newWriteInterceptors = new ArrayList<Interceptor>();
                     for (ArrayBinding binding : bindings)
                     {
                        InterceptorFactory[] factories = binding.getInterceptorFactories();
                        for (int i = 0; i < factories.length; i++)
                        {
                           Interceptor icptr = createInterceptor(factories[i]);
                           if (icptr != null)
                           {
                              if (binding.isWrite())
                              {
                                 newWriteInterceptors.add(icptr);
                              }
                              if (binding.isRead())
                              {
                                 newReadInterceptors.add(icptr);
                              }
                           }
                        }
                     }
                     readInterceptors = finalizeChain(newReadInterceptors);
                     writeInterceptors = finalizeChain(newWriteInterceptors);
                     updated = false;
                     return (read) ? readInterceptors : writeInterceptors;
                  }
               }
               finally
               {
                  writeLock.unlock();               
               }
            }
            return (read) ? readInterceptors : writeInterceptors;
         }
         finally
         {
            if (lockedRead)
            {
               readLock.unlock();
            }
         }
      }
      
      private static Interceptor[] finalizeChain(ArrayList<Interceptor> newinterceptors)
      {
         Interceptor[] interceptors = (newinterceptors.size() > 0) ? newinterceptors.toArray(new Interceptor[newinterceptors.size()]) : null;
         if (interceptors != null)
         {
            if (interceptors[0] instanceof GeneratedAdvisorInterceptor)
            {
               
            }
            interceptors = PrecedenceSorter.applyPrecedence(interceptors, AspectManager.instance());
         }
         return interceptors;
      }
      
      private static Interceptor createInterceptor(InterceptorFactory factory)
      {
         //TODO: must be fixed!
//         if (factory instanceof GeneratedOnly)
//         {
//            throw new RuntimeException("Before/After/Throwing is only supported for Generated Advisors");
//         }

         try
         {
            if (factory.isDeployed())
            {
               if (factory instanceof AdviceFactory)
               {
                  return PerVmAdvice.generateOptimized(null, AspectManager.instance(), factory.getAdvice(), factory.getAspect());
               }
               else if (factory instanceof ScopedInterceptorFactory)
               {
                  return (Interceptor) AspectManager.instance().getPerVMAspect(factory.getAspect());
               }
            }
            
            return null;
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }
   }
}
