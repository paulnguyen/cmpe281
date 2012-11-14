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

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Utility class to work around IBM JDKs broken Class.getDeclaringClass() implementation
 * when called on a generated class.
 * See https://jira.jboss.org/jira/browse/JBPAPP-569 for a description
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class GetDeclaringClassUtil
{
   static final boolean isIBMJVM;
   static
   {
      String vendor = getSystemProperty("java.vm.vendor");
      isIBMJVM = vendor.toUpperCase().contains("IBM");
   }
   
   public static Class<?> getDeclaringClass(Class<?> clazz)
   {
      if (clazz == null)
      {
         throw new IllegalArgumentException("Null class");
      }
      Class<?> declaring = clazz.getDeclaringClass();
      
      if (declaring == null && isIBMJVM)
      {
         String name = clazz.getName();
         int last = name.lastIndexOf('$');
         if (last >= 0)
         {
            ClassLoader cl = getClassLoader(clazz);
            while (declaring == null && last >= 0)
            {
               try
               {
                  declaring = cl.loadClass(clazz.getName().substring(0, last));
               }
               catch(ClassNotFoundException e)
               {
                  last = name.lastIndexOf('$', last -1);
               }
            }
            if (declaring == null)
            {
               throw new RuntimeException("No declaring class could be determined for " + clazz.getName());
            }
         }
      }
      
      return declaring;
   }
   
   private interface GetClassLoaderAction 
   {
      ClassLoader getClassLoader(Class<?> clazz);
      
      GetClassLoaderAction NON_PRIVILEGED = new GetClassLoaderAction() {

         public ClassLoader getClassLoader(Class<?> clazz)
         {
            return clazz.getClassLoader();
         }};

     GetClassLoaderAction PRIVILEGED = new GetClassLoaderAction() {

         public ClassLoader getClassLoader(final Class<?> clazz)
         {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {

               public ClassLoader run()
               {
                  return clazz.getClassLoader();
               }});
         }};
   }
   
   private static ClassLoader getClassLoader(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return GetClassLoaderAction.NON_PRIVILEGED.getClassLoader(clazz);
      }
      else
      {
         return GetClassLoaderAction.PRIVILEGED.getClassLoader(clazz);
      }
   }

   private interface GetSystemPropertyAction 
   {
      String getSystemProperty(String key);
      
      GetSystemPropertyAction NON_PRIVILEGED = new GetSystemPropertyAction() {

         public String getSystemProperty(String key)
         {
            return System.getProperty(key);
         }};

      GetSystemPropertyAction PRIVILEGED = new GetSystemPropertyAction() {

         public String getSystemProperty(final String key)
         {
            return AccessController.doPrivileged(new PrivilegedAction<String>() {

               public String run()
               {
                  return System.getProperty(key);
               }});
         }};
   }
   
   private static String getSystemProperty(String key)
   {
      if (System.getSecurityManager() == null)
      {
         return GetSystemPropertyAction.NON_PRIVILEGED.getSystemProperty(key);
      }
      else
      {
         return GetSystemPropertyAction.PRIVILEGED.getSystemProperty(key);
      }
   }
}
