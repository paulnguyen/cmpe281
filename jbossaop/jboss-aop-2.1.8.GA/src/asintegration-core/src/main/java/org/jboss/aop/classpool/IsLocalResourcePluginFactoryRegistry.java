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
package org.jboss.aop.classpool;

import java.net.URLClassLoader;
import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.util.loading.Translatable;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class IsLocalResourcePluginFactoryRegistry
{
   private static Map<Class<?>, IsLocalResourcePluginFactory> plugins = new WeakHashMap<Class<?>, IsLocalResourcePluginFactory>();
   
   static
   {
      addPluginFactory(ClassLoader.class, new DefaultClassLoaderIsLocalResourcePluginFactory());
      addPluginFactory(URLClassLoader.class, new URLClassLoaderIsLocalResourcePluginFactory());
      addPluginFactory(Translatable.class, new TranslatableClassLoaderIsLocalResourcePluginFactory());
   }
   
   public static synchronized void addPluginFactory(Class<?> classLoaderClass, IsLocalResourcePluginFactory plugin)
   {
      plugins.put(classLoaderClass, plugin);
   }
   
   public static synchronized void removePluginFactory(Class<?> classLoaderClass, IsLocalResourcePluginFactory plugin)
   {
      plugins.remove(classLoaderClass);
   }

   public static synchronized IsLocalResourcePluginFactory getPluginFactory(ClassLoader classLoader)
   {
      if (classLoader != null)
      {
         return getPluginFactory(classLoader.getClass());
      }
      return getPluginFactory(ClassLoader.class);
   }
   
   public static synchronized IsLocalResourcePluginFactory getPluginFactory(Class<?> classLoaderClass)
   {
      IsLocalResourcePluginFactory plugin = plugins.get(classLoaderClass);
      if (plugin == null)
      {
         Class<?>[] interfaces = classLoaderClass.getInterfaces();
         if (interfaces.length > 0)
         {
            for (Class<?> iface : interfaces)
            {
               plugin = getPluginFactory(iface);
               if (plugin != null)
               {
                  return plugin;
               }
            }
         }
         Class<?> superClass = classLoaderClass.getSuperclass();
         if (superClass != null && superClass != Object.class)
         {
            return getPluginFactory(superClass);
         }
      }
      return plugin;
   }
}
