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

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.aop.Advisor;
import org.jboss.aop.ConstructorInfo;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.Interceptor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JoinPointComparator
{
   public static long[] mergeMethodInfoKeys(long[] myKeys, long[] otherKeys)
   {
      long[] allKeys = null;
      
      if (myKeys == null || myKeys.length == 0)
      {
         allKeys = otherKeys;
         Arrays.sort(allKeys);
      }
      else if (otherKeys == null || otherKeys.length == 0)
      {
         allKeys = myKeys;
         Arrays.sort(allKeys);
      }
      else
      {
         allKeys = new long[myKeys.length + otherKeys.length];
         System.arraycopy(myKeys, 0, allKeys, 0, myKeys.length);
         System.arraycopy(otherKeys, 0, allKeys, myKeys.length, otherKeys.length);
         Arrays.sort(allKeys);
         
         boolean haveDups = false;
         int i = 0;
         int peek = 1;
         int dup = allKeys.length;
         for ( ; peek < allKeys.length; i++, peek++)
         {
            if (allKeys[i] == allKeys[peek])
            {
               if (!haveDups)
               {
                  dup = peek;
                  haveDups = true;
               }  
            }
            else if (haveDups)
            {
                  allKeys[dup++] = allKeys[peek];
            }
         }
         
         if (dup != allKeys.length)
         {
            long[] deduped = new long[dup];
            System.arraycopy(allKeys, 0, deduped, 0, dup);
            allKeys = deduped;
         }
      }
      
      return allKeys;
   }   
   
   public static int hasSameInterceptorLengths(JoinPointInfo myInfo, JoinPointInfo otherInfo)
   {
      if (myInfo != null && otherInfo != null)
      {
         if (myInfo.getInterceptors() != null && otherInfo.getInterceptors() != null)
         {
            int i = compareLengths(getLengthNullAsZero(myInfo), getLengthNullAsZero(otherInfo));
            if (i != 0)
            {
               return i;
            }
         }
      }
      else if (myInfo == null && otherInfo == null)
      {
         //same
      }
      else
      {
         if (myInfo == null)
         {
            if (getLengthNullAsZero(otherInfo) > 0)
            {
               return 1;
            }
         }
         else
         {
            if (getLengthNullAsZero(myInfo) > 0)
            {
               return -1;
            }
         }
      }
      return 0;
   }
   
   public static boolean hasSameMethodAspectLength(long[] myKeys, long[] otherKeys, Advisor myAdvisor, Advisor otherAdvisor)
   {
      long[] keys = JoinPointComparator.mergeMethodInfoKeys(myKeys, otherKeys);
      
      for (int i = 0 ; i < keys.length ; i++)
      {
         MethodInfo myInfo = myAdvisor.getMethodInfo(keys[i]);
         MethodInfo otherInfo = otherAdvisor.getMethodInfo(keys[i]);
         
         if (JoinPointComparator.hasSameInterceptorLengths(myInfo, otherInfo) != 0)
         {
            return false;
         }
         
      }
      return true;

   }
   
   
   public static boolean hasSameConstructorAspectLength(ConstructorInfo[] myInfos, ConstructorInfo[] otherInfos)
   {
      //TODO: GeneratedAdvisor weaving might only be including infos for the woven constructors? 
      Map<Constructor<?>, ConstructorInfo> myMap = createConstructorInfoMap(myInfos);
      Map<Constructor<?>, ConstructorInfo> otherMap = createConstructorInfoMap(otherInfos);
      
      if (myMap.size() == 0 && otherMap.size() == 0)
      {
         return true;
      }
      else if (myMap.size() == 0 && otherMap.size() > 0)
      {
         if (hasNonNullInfos(otherInfos))
         {
            return false;
         }
      }
      else if (myMap.size() > 0 && otherMap.size() == 0)
      {
         if (hasNonNullInfos(myInfos))
         {
            return false;
         }
      }
      else
      {
         Set<ConstructorInfo> nonMatches = new HashSet<ConstructorInfo>();
         for (Constructor<?> ctor : myMap.keySet())
         {
            ConstructorInfo myInfo = myMap.get(ctor);
            ConstructorInfo otherInfo = otherMap.remove(ctor);
            if (otherInfo != null)
            {
               int i = compareLengths(getLengthNullAsZero(myInfo), getLengthNullAsZero(otherInfo));
               if (i != 0)
               {
                  return false;
               }  
            }
            else
            {
               nonMatches.add(myInfo);
            }
         }
         if (otherMap.size() > 0 && hasNonNullInfos(otherMap.values()))
         {
            return false;
         }
         if (nonMatches.size() > 0 && hasNonNullInfos(nonMatches))
         {
            return false;
         }
      }

      return true;
   }
   
   private static Map<Constructor<?>, ConstructorInfo> createConstructorInfoMap(ConstructorInfo[] infos)
   {
      if (infos == null || infos.length == 0)
      {
         return UnmodifiableEmptyCollections.EMPTY_HASHMAP;
      }
      Map<Constructor<?>, ConstructorInfo> map = new HashMap<Constructor<?>, ConstructorInfo>(infos.length);
      for (ConstructorInfo info : infos)
      {
         map.put(info.getConstructor(), info);
      }
      return map;
   }
   
   private static boolean hasNonNullInfos(JoinPointInfo[] infos)
   {
      if (infos.length > 0)
      {
         for (JoinPointInfo info : infos)
         {
            if (getLengthNullAsZero(info) > 0)
            {
               return true;
            }
               
         }
      }
      return false;
   }
   
   private static boolean hasNonNullInfos(Collection<ConstructorInfo> infos)
   {
      if (infos.size() > 0)
      {
         for (JoinPointInfo info : infos)
         {
            if (getLengthNullAsZero(info) > 0)
            {
               return true;
            }
               
         }
      }
      return false;
   }
   
   private static int compareLengths(int mine, int other)
   {
      if (mine > other) return -1;
      if (mine == other) return 0;
      return 1;
   }
   
   private static int getLengthNullAsZero(JoinPointInfo info)
   {
      Interceptor[] icptrs = info.getInterceptors(); 
      if (icptrs == null)
      {
         return 0;
      }
      return icptrs.length;
   }
}
