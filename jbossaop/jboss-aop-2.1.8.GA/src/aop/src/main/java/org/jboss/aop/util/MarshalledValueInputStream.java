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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * An ObjectInputStream subclass used by the MarshalledValue class to
 * ensure the classes and proxies are loaded using the thread context
 * class loader.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 71276 $
 */
public class MarshalledValueInputStream
   extends ObjectInputStream
{
   /**
    * Creates a new instance of MarshalledValueOutputStream
    */
   public MarshalledValueInputStream(InputStream is) throws IOException
   {
      super(is);
   }

   /**
    * Use the thread context class loader to resolve the class
    * 
    * @throws java.io.IOException   Any exception thrown by the underlying OutputStream.
    */
   protected Class<?> resolveClass(ObjectStreamClass v)
      throws IOException, ClassNotFoundException
   {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      String className = v.getName();
      return loader.loadClass(className);
   }

   protected Class<?> resolveProxyClass(String[] interfaces)
      throws IOException, ClassNotFoundException
   {
      // Load the interfaces from the thread context class loader
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Class<?>[] ifaceClasses = new Class[interfaces.length];
      for (int i = 0; i < interfaces.length; i++)
      {
         ifaceClasses[i] = loader.loadClass(interfaces[i]);
      }
      
      return java.lang.reflect.Proxy.getProxyClass(loader, ifaceClasses);
   }
}
