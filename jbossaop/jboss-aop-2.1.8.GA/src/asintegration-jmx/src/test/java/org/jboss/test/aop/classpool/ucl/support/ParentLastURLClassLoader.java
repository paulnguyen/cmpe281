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
package org.jboss.test.aop.classpool.ucl.support;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * An attempt to emulate the web classloader with parent last search order in AS
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ParentLastURLClassLoader extends URLClassLoader
{
   ClassLoader parent;
   URLClassLoader delegate;

   public ParentLastURLClassLoader(URL[] urls, ClassLoader parent)
   {
      super(urls, parent);
      delegate = new URLClassLoader(urls);
      this.parent = parent;
   }

   @Override
   public URL findResource(String name)
   {
      URL url = delegate.findResource(name);
      if (url == null && parent instanceof URLClassLoader)
      {
         url = ((URLClassLoader)parent).findResource(name);
      }
      return url;
   }

   @Override
   public URL getResource(String name)
   {
      URL url = delegate.getResource(name);
      if (url == null)
      {
         url = parent.getResource(name);
      }
      return url;
   }

   @Override
   public Class<?> loadClass(String name) throws ClassNotFoundException
   {
      try
      {
         return delegate.loadClass(name);
      }
      catch(ClassNotFoundException e)
      {
      }
      return parent.loadClass(name);
   }

   
   
}
