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
package org.jboss.aop.contrib;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.classpool.AOPClassPool;

/**
 * @author Marshall
 *         <p/>
 *         This is a class pool factory used by JBossIDE to create an AOP Class Pool
 */
public class IDEClassPoolFactory implements ScopedClassPoolFactory
{

   private ArrayList<URL> classPaths;

   public IDEClassPoolFactory()
   {
      classPaths = new ArrayList<URL>();
   }

   public ScopedClassPool create(ClassLoader loader, ClassPool pool, ScopedClassPoolRepository repository)
   {
      SimpleClassPool classPool = new SimpleClassPool(loader, pool, repository);
      return classPool;
   }

   public ScopedClassPool create(ClassPool pool, ScopedClassPoolRepository repository)
   {
      SimpleClassPool classPool = new SimpleClassPool(pool, repository);
      return classPool;
   }

   public void insertClasspath(String path)
   {
      try
      {
         classPaths.add(new URL(path));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   private class SimpleClassPool extends AOPClassPool
   {
      private Loader loader;

      public SimpleClassPool(ClassLoader loader, ClassPool pool, ScopedClassPoolRepository repository)
      {
         super(loader, pool, repository);
         loadClasspath();
      }

      public SimpleClassPool(ClassPool pool, ScopedClassPoolRepository repository)
      {
         super(pool, repository);
         loadClasspath();

      }

      private void loadClasspath()
      {
         childFirstLookup = true;
         
         AccessController.doPrivileged(new PrivilegedAction<Object>()
         {
            public Object run()
            {
               URL urlPaths[] = classPaths.toArray(new URL[classPaths.size()]);
               loader = new Loader(urlPaths, Thread.currentThread().getContextClassLoader());
               //setClassLoader(loader); this is set by the constructor?

               Thread.currentThread().setContextClassLoader(loader);
               return null;
            }
         });

         classPath = new LoaderClassPath(loader);
         insertClassPath(classPath);
      }

      public Class<?> toClass(CtClass cc)
              throws CannotCompileException
      {
         try
         {
            return loader.loadClass(cc.getName());
         }
         catch (Exception e)
         {
            try
            {
               return loader.loadClass(cc.getName(), cc.toBytecode());
            }
            catch (IOException e2)
            {
               throw new CannotCompileException(e2);
            }
         }
      }
   }

   private class Loader extends URLClassLoader
   {
      public Loader(URL urls[], ClassLoader src)
      {
         super(urls, src);
      }

      public Class<?> loadClass(String name, byte[] classfile)
              throws ClassFormatError
      {
         Class<?> c = defineClass(name, classfile, 0, classfile.length);

         resolveClass(c);
         return c;
      }
   }

   public ClassLoader getTCLIfScoped()
   {
      return null;
   }
}
