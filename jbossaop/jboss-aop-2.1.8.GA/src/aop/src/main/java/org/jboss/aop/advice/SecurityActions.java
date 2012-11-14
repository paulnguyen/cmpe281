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
package org.jboss.aop.advice;

import java.beans.PropertyEditorManager;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.jboss.util.propertyeditor.ClassArrayEditor;
import org.jboss.util.propertyeditor.IntArrayEditor;
import org.jboss.util.propertyeditor.StringArrayEditor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 81450 $
 */
public class SecurityActions
{
   private interface InitPropertyEditorsAction
   {
      void initEditors();
      
      InitPropertyEditorsAction PRIVILEGED = new InitPropertyEditorsAction()
      {
         public void initEditors() 
         {
            try
            {
               AccessController.doPrivileged(new PrivilegedExceptionAction<Object>()
               {
                  public Object run() throws Exception
                  {
                     doInitEditors();
                     return null;
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw new RuntimeException(e.getException());
            }
         }
      };

      InitPropertyEditorsAction NON_PRIVILEGED = new InitPropertyEditorsAction()
      {
         public void initEditors() 
         {
            doInitEditors();
         }
      };
   }

   private static void doInitEditors()
   {
      String[] currentPath = PropertyEditorManager.getEditorSearchPath();
      int length = currentPath != null ? currentPath.length : 0;
      String[] newPath = new String[length+2];
      System.arraycopy(currentPath, 0, newPath, 2, length);
      // Put the JBoss editor path first
      // The default editors are not very flexible
      newPath[0] = "org.jboss.util.propertyeditor";
      newPath[1] = "org.jboss.mx.util.propertyeditor";
      PropertyEditorManager.setEditorSearchPath(newPath);

      /* Register the editor types that will not be found using the standard
      class name to editor name algorithm. For example, the type String[] has
      a name '[Ljava.lang.String;' which does not map to a XXXEditor name.
      */
      Class<String[]> strArrayType = String[].class;
      PropertyEditorManager.registerEditor(strArrayType, StringArrayEditor.class);
      
      @SuppressWarnings("unchecked")
      Class<Class[]> clsArrayType = Class[].class;
      PropertyEditorManager.registerEditor(clsArrayType, ClassArrayEditor.class);
      
      Class<int[]> intArrayType = int[].class;
      PropertyEditorManager.registerEditor(intArrayType, IntArrayEditor.class);
   }


   static void initEditors()
   {
      if (System.getSecurityManager() == null)
      {
         InitPropertyEditorsAction.NON_PRIVILEGED.initEditors();
      }
      else
      {
         InitPropertyEditorsAction.PRIVILEGED.initEditors();
      }
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
   
   public static ClassLoader getClassLoader(Class<?> clazz)
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