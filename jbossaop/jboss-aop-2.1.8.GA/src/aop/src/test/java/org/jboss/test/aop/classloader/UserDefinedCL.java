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
package org.jboss.test.aop.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * User-defined class loader.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class UserDefinedCL extends URLClassLoader
{
   public UserDefinedCL(URL url, ClassLoader parent)
   {
      super(new URL[]{url}, parent);
   }
   
   @Override
   public URL  getResource(String name)
   {
      System.out.println("GET RESOURCE: " + name);
      new Exception().printStackTrace();
      return null;
   }
   
   @Override
   public InputStream getResourceAsStream(String name)
   {
      return null;
   }
   
   @Override
   public Enumeration<URL> getResources(String name) throws IOException
   {
      return null;
   }
   
   @Override
   public URL  findResource(String name)
   {
      return null;
   }
   public Enumeration<URL>    findResources(String name)
   {
      return null;
   }
   
   @Override 
   public Class<?> loadClass(String name) throws ClassNotFoundException
   {
      return super.loadClass(name);
   }
}