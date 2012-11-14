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
package org.jboss.aop.asintegration.jboss4;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
class SecurityActions
{
   interface GetParentAction
   {
      ClassLoader getParent(ClassLoader loader);
      
      GetParentAction NON_PRIVILEGED = new GetParentAction()
      {
         public ClassLoader getParent(ClassLoader loader)
         {
            return loader.getParent();
         }
      };
      
      GetParentAction PRIVILEGED = new GetParentAction()
      {
         public ClassLoader getParent(final ClassLoader loader)
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<ClassLoader>()
               {
                  public ClassLoader run() throws Exception
                  {
                     return loader.getParent();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw new RuntimeException(e.getException());
            }
         }
      };
   }
   
   public static ClassLoader getParent(ClassLoader loader)
   {
      if (loader == null)
      {
         return null;
      }
      if (System.getSecurityManager() == null)
      {
         return GetParentAction.NON_PRIVILEGED.getParent(loader);
      }
      else
      {
         return GetParentAction.PRIVILEGED.getParent(loader);
      }
   }
}
