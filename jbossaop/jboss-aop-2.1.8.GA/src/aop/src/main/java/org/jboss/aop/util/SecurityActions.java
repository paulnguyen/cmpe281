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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
class SecurityActions
{
   interface GetDeclaredMethodsAction
   {
      Method[] getDeclaredMethods(Class<?> clazz);
      
      GetDeclaredMethodsAction PRIVILEGED = new GetDeclaredMethodsAction()
      {
         public Method[] getDeclaredMethods(final Class<?> clazz)
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Method[]>()
               {
                  public Method[] run() throws Exception
                  {
                     return clazz.getDeclaredMethods();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw new RuntimeException(e);
            }
         }
      };

      GetDeclaredMethodsAction NON_PRIVILEGED = new GetDeclaredMethodsAction()
      {
         public Method[] getDeclaredMethods(Class<?> clazz)
         {
            return clazz.getDeclaredMethods();
         }
      };
   }
   
   interface GetDeclaredConstructorsAction
   {
      Constructor<?>[] getDeclaredConstructors(Class<?> clazz);
      
      GetDeclaredConstructorsAction PRIVILEGED = new GetDeclaredConstructorsAction()
      {
         public Constructor<?>[] getDeclaredConstructors(final Class<?> clazz)
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>[]>()
               {
                  public Constructor<?>[] run() throws Exception
                  {
                     return clazz.getDeclaredConstructors();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw new RuntimeException(e);
            }
         }
      };

      GetDeclaredConstructorsAction NON_PRIVILEGED = new GetDeclaredConstructorsAction()
      {
         public Constructor<?>[] getDeclaredConstructors(Class<?> clazz)
         {
            return clazz.getDeclaredConstructors();
         }
      };
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
}
