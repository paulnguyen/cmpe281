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
package org.jboss.aop.proxy.container;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70849 $
 */
public class ContainerCacheUtil
{

   /**
    * Takes a Class[] containing interface classes, and returns an array of weak references to thos class
    * objects, sorted alphabetically by name.
    */
   public static WeakReference<Class<?>>[] getSortedWeakReferenceForInterfaces(Class<?>[] ifaces)
   {
      if (ifaces == null)
      {
         return null;
      }
      
      WeakReference<Class<?>>[] interfaces = new WeakReference[ifaces.length];
      
      for (int i = 0 ; i < ifaces.length ; i++)
      {
         interfaces[i] = new WeakReference<Class<?>>(ifaces[i]);
      }
      
      Arrays.sort(interfaces, Alphabetical.singleton);
      return interfaces;
   }

   public static boolean compareClassRefs(WeakReference<Class<?>> my, WeakReference<Class<?>> other)
   {
      Class<?> myClass = my.get();
      Class<?> otherClass = other.get(); 
      
      if (myClass == null || otherClass == null)
      {
         return false;
      }
      
      if (!myClass.equals(otherClass))
      {
         return false;
      }
      return true;
   }
   
   public static boolean compareInterfaceRefs(WeakReference<Class<?>>[] my, WeakReference<Class<?>>[] other)
   {
      if ((my == null && other != null) ||
            (my == null && other != null))
      {
         return false;
      }
      
      if (my != null && my != null)
      {
         if (my.length != other.length)
         {
            return false;
         }
         
         for (int i = 0 ; i < my.length ; i++)
         {
            Class<?> myIf = my[i].get();
            Class<?> otherIf = other[i].get();
            
            if (!myIf.equals(otherIf))
            {
               return false;
            }
         }
      }
      
      return true;
   }

   static class Alphabetical implements Comparator<WeakReference<Class<?>>>
   {
      static Alphabetical singleton = new Alphabetical();
      
      public int compare(WeakReference<Class<?>> o1, WeakReference<Class<?>> o2)
      {
         String name1 = o1.get().getName();
         String name2 = o2.get().getName();
         return (name1).compareTo(name2);
      }
   }
   
}
