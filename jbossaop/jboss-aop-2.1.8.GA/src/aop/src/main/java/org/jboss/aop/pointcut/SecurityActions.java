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
package org.jboss.aop.pointcut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 74538 $
 */
class SecurityActions
{
   static Class<?> loadClass(String name)
   {
      if (System.getSecurityManager() == null)
      {
         return LoadClassAction.NON_PRIVILEGED.loadClass(name);
      }
      else
      {
         return LoadClassAction.PRIVILEGED.loadClass(name);
      }
   }

   interface LoadClassAction
   {
      Class<?> loadClass(String name);
      
      LoadClassAction PRIVILEGED = new LoadClassAction()
      {
         public Class<?> loadClass(final String name)
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>()
               {
                  public Class<?> run() throws Exception
                  {
                     return Thread.currentThread().getContextClassLoader().loadClass(name);
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw new RuntimeException("Unable to load class " + name, e.getException());
            }
         }
      };

      LoadClassAction NON_PRIVILEGED = new LoadClassAction()
      {
         public Class<?> loadClass(String name)
         {
            try
            {
               return Thread.currentThread().getContextClassLoader().loadClass(name);
            }
            catch (ClassNotFoundException e)
            {
               throw new RuntimeException("Unable to load class " + name, e);
            }
         }
      };
   }
   
   public static class GetContextClassLoaderAction implements PrivilegedAction<ClassLoader>
   {
      public static GetContextClassLoaderAction INSTANCE = new GetContextClassLoaderAction();
      
      public ClassLoader run()
      {
         return Thread.currentThread().getContextClassLoader();
      }
   }

   static ClassLoader getContextClassLoader()
   {
      if (System.getSecurityManager() == null)
         return Thread.currentThread().getContextClassLoader();
      else
         return AccessController.doPrivileged(GetContextClassLoaderAction.INSTANCE);
   }
   
   interface GetDeclaredConstructorsAction 
   {
      Constructor<?>[] getDeclaredConstructors(Class<?> clazz);
      
      GetDeclaredConstructorsAction NON_PRIVILEGED = new GetDeclaredConstructorsAction() {

         public Constructor<?>[] getDeclaredConstructors(Class<?> clazz)
         {
            return clazz.getDeclaredConstructors();
         }};

         GetDeclaredConstructorsAction PRIVILEGED = new GetDeclaredConstructorsAction() {

            public Constructor<?>[] getDeclaredConstructors(final Class<?> clazz)
            {
               return AccessController.doPrivileged(new PrivilegedAction<Constructor<?>[]>() {

                  public Constructor<?>[] run()
                  {
                     return clazz.getDeclaredConstructors();
                  }});
            }};
   }
   
   static Constructor<?>[] getDeclaredConstructors(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return GetDeclaredConstructorsAction.NON_PRIVILEGED.getDeclaredConstructors(clazz);
      }
      else
      {
         return GetDeclaredConstructorsAction.PRIVILEGED.getDeclaredConstructors(clazz);
      }
   }
   
   interface GetDeclaredMethodsAction 
   {
      Method[] getDeclaredMethods(Class<?> clazz);
      
      GetDeclaredMethodsAction NON_PRIVILEGED = new GetDeclaredMethodsAction() {

         public Method[] getDeclaredMethods(Class<?> clazz)
         {
            return clazz.getDeclaredMethods();
         }};

         GetDeclaredMethodsAction PRIVILEGED = new GetDeclaredMethodsAction() {

            public Method[] getDeclaredMethods(final Class<?> clazz)
            {
               return AccessController.doPrivileged(new PrivilegedAction<Method[]>() {

                  public Method[] run()
                  {
                     return clazz.getDeclaredMethods();
                  }});
            }};
   }
   
   static Method[] getDeclaredMethods(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return GetDeclaredMethodsAction.NON_PRIVILEGED.getDeclaredMethods(clazz);
      }
      else
      {
         return GetDeclaredMethodsAction.PRIVILEGED.getDeclaredMethods(clazz);
      }
   }
   
   interface GetDeclaredFieldsAction 
   {
      Field[] getDeclaredFields(Class<?> clazz);
      
      GetDeclaredFieldsAction NON_PRIVILEGED = new GetDeclaredFieldsAction() {

         public Field[] getDeclaredFields(Class<?> clazz)
         {
            return clazz.getDeclaredFields();
         }};

         GetDeclaredFieldsAction PRIVILEGED = new GetDeclaredFieldsAction() {

            public Field[] getDeclaredFields(final Class<?> clazz)
            {
               return AccessController.doPrivileged(new PrivilegedAction<Field[]>() {

                  public Field[] run()
                  {
                     return clazz.getDeclaredFields();
                  }});
            }};
   }
   
   static Field[] getDeclaredFields(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return GetDeclaredFieldsAction.NON_PRIVILEGED.getDeclaredFields(clazz);
      }
      else
      {
         return GetDeclaredFieldsAction.PRIVILEGED.getDeclaredFields(clazz);
      }
   }

   
   interface GetClassLoaderAction 
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
   
   static ClassLoader getClassLoader(Class<?> clazz)
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
}
