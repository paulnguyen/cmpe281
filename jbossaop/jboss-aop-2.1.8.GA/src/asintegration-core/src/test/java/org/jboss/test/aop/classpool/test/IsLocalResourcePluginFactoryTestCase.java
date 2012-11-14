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
package org.jboss.test.aop.classpool.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javassist.ClassPool;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.aop.classpool.BaseClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.DefaultClassLoaderIsLocalResourcePlugin;
import org.jboss.aop.classpool.DefaultClassLoaderIsLocalResourcePluginFactory;
import org.jboss.aop.classpool.DelegatingClassPool;
import org.jboss.aop.classpool.IsLocalResourcePlugin;
import org.jboss.aop.classpool.IsLocalResourcePluginFactory;
import org.jboss.aop.classpool.IsLocalResourcePluginFactoryRegistry;
import org.jboss.aop.classpool.TranslatableClassLoaderIsLocalResourcePlugin;
import org.jboss.aop.classpool.TranslatableClassLoaderIsLocalResourcePluginFactory;
import org.jboss.aop.classpool.URLClassLoaderIsLocalResourcePlugin;
import org.jboss.aop.classpool.URLClassLoaderIsLocalResourcePluginFactory;
import org.jboss.util.loading.Translatable;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class IsLocalResourcePluginFactoryTestCase extends TestCase
{
   final static ClassPoolDomain DOMAIN = new BaseClassPoolDomain("Test", null, true);
   final static URL[] URLS;
   static 
   {
      try
      {
         URLS = new URL[] {new URL("http://www.blah.com")};
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   public static Test suite()
   {
      return new TestSuite(IsLocalResourcePluginFactoryTestCase.class);
   }

   public IsLocalResourcePluginFactoryTestCase(String name)
   {
      super(name);
   }

   public void testDefaultClassLoader() 
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new DefaultClassLoader(), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, DefaultClassLoaderIsLocalResourcePluginFactory.class, DefaultClassLoaderIsLocalResourcePlugin.class);
   }
   
   public void testURLClassLoaderDirect()
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new URLClassLoader(URLS), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, URLClassLoaderIsLocalResourcePluginFactory.class, URLClassLoaderIsLocalResourcePlugin.class);
   }

   public void testURLClassLoaderSubClass()
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new URLCLassLoaderSubClass(URLS), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, URLClassLoaderIsLocalResourcePluginFactory.class, URLClassLoaderIsLocalResourcePlugin.class);
   }

   public void testTranslatableClassLoaderImplementation()
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new TranslatableImplementation(), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, TranslatableClassLoaderIsLocalResourcePluginFactory.class, TranslatableClassLoaderIsLocalResourcePlugin.class);
   }
   
   public void testTranslatableClassLoaderImplementationAndURLClassLoaderSubClass()
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new TranslatableImplementationAndURLClassLoaderSubClass(URLS), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, TranslatableClassLoaderIsLocalResourcePluginFactory.class, TranslatableClassLoaderIsLocalResourcePlugin.class);
   }
   
   public void testTranslatable2ClassLoaderImplementationAndURLClassLoaderSubClass()
   {
      DelegatingClassPool pool = new DelegatingClassPool(DOMAIN, new Translatable2ImplementationAndURLClassLoaderSubClass(URLS), ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
      assertFactoryAndPlugin(pool, TranslatableClassLoaderIsLocalResourcePluginFactory.class, TranslatableClassLoaderIsLocalResourcePlugin.class);
   }
   
   
   private void assertFactoryAndPlugin(DelegatingClassPool pool, Class<?> factoryClass, Class<?> pluginClass)
   {
      IsLocalResourcePluginFactory factory = IsLocalResourcePluginFactoryRegistry.getPluginFactory(pool.getClassLoader());
      assertNotNull(factory);
      assertEquals(factoryClass, factory.getClass());
      IsLocalResourcePlugin plugin = factory.create(pool);
      assertEquals(pluginClass, plugin.getClass());
   }

   static class DefaultClassLoader extends ClassLoader
   {
   }
   
   static class URLCLassLoaderSubClass extends URLClassLoader
   {
      public URLCLassLoaderSubClass(URL[] urls)
      {
         super(urls);
      }
      
   }
   
   static class TranslatableImplementation extends ClassLoader implements Translatable
   {
      public URL getResourceLocally(String name)
      {
         return null;
      }
      
   }
   
   static class TranslatableImplementationAndURLClassLoaderSubClass extends URLClassLoader implements Translatable
   {

      public TranslatableImplementationAndURLClassLoaderSubClass(URL[] urls)
      {
         super(urls);
      }

      public URL getResourceLocally(String name)
      {
         return null;
      }
      
   }
   
   interface Translatable2 extends Translatable
   {
      
   }
   
   static class Translatable2ImplementationAndURLClassLoaderSubClass extends URLClassLoader implements Translatable2
   {

      public Translatable2ImplementationAndURLClassLoaderSubClass(URL[] urls)
      {
         super(urls);
      }

      public URL getResourceLocally(String name)
      {
         return null;
      }
   }
   
   
}
