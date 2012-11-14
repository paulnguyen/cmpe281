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
package org.jboss.test.aop.classpool.ucl.test;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import javassist.ClassPool;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.ucl.JBossUclDelegatingClassPoolFactory;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.loading.RepositoryClassLoader;
import org.jboss.mx.loading.UnifiedClassLoader3;
import org.jboss.mx.util.MBeanServerLocator;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.aop.classpool.ucl.support.ParentLastURLClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class UclClassPoolTest extends AbstractTestCaseWithSetup
{
   public final static URL JAR_A_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-a1.jar");
   public final static URL JAR_A_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-a2.jar");
   public final static URL JAR_B_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-b1.jar");
   public final static URL JAR_B_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-b2.jar");
   public final static URL JAR_C_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-c1.jar");
   public final static URL JAR_C_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-jmx-test-classpool-c2.jar");

   public final static String PACKAGE_A = "org.jboss.test.aop.classpool.ucl.support.excluded.a.";
   public final static String PACKAGE_B = "org.jboss.test.aop.classpool.ucl.support.excluded.b.";
   public final static String PACKAGE_C = "org.jboss.test.aop.classpool.ucl.support.excluded.c.";
   
   public final static String CLASS_A = PACKAGE_A + "A";
   public final static String CLASS_B = PACKAGE_B + "B";
   public final static String CLASS_C = PACKAGE_C + "C";

   private MBeanServer server; 
   private LoaderRepository globalRepository;

   //Keep a strong reference to the classloaders so that they are not garbage collected
   final static Set<ClassLoader> registeredClassLoaders = new HashSet<ClassLoader>();
   
   final static ObjectName MAIN_LOADER_REPOSITORY_OBJECT_NAME;
   static
   {
      try
      {
         MAIN_LOADER_REPOSITORY_OBJECT_NAME = new ObjectName("JMImplementation:name=Default,service=LoaderRepository");
         AspectManager.setClassPoolFactory(new JBossUclDelegatingClassPoolFactory(new File(".")));
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   
   public UclClassPoolTest(String name)
   {
      super(name);
      System.setProperty("javax.management.builder.initial", "org.jboss.mx.server.MBeanServerBuilderImpl");
      server = MBeanServerFactory.createMBeanServer();
      MBeanServerLocator.setJBoss(server);
      try
      {
         globalRepository = (LoaderRepository)server.invoke(MAIN_LOADER_REPOSITORY_OBJECT_NAME, "getInstance", new Object[0], new String[0]);
      }
      catch(Exception e)
      {
         
      }
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      AbstractTestDelegate delegate = new UclClassPoolTestDelegate(clazz);
      String property = System.getProperty("jboss.aop.secure", "true");
      boolean enableSecurity = Boolean.valueOf(property).booleanValue();
      delegate.enableSecurity = enableSecurity;
      return delegate;
   }
   
   protected static URL getURLRelativeToProjectRoot(String relativePath)
   {
      try
      {
         URL url = UclClassPoolTest.class.getProtectionDomain().getCodeSource().getLocation();
         String location = url.toString();
         int index = location.lastIndexOf("/asintegration-jmx/") + "/asintegration-jmx/".length();
         location = location.substring(0, index);
         
         location = location + relativePath;
         return new URL(location);
      }
      catch (MalformedURLException e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
      enableTrace("org.jboss.aop.classpool");
      enableTrace("org.jboss.aop.classpool.ucl");
   }

   public MBeanServer getServer()
   {
      return server;
   }


   public LoaderRepository getGlobalRepository()
   {
      return globalRepository;
   }
   
   protected ClassLoader createGlobalClassLoader(URL url) throws Exception
   {
      ClassLoader cl = globalRepository.newClassLoader(url, true);
      registeredClassLoaders.add(cl);
      return cl;
   }
   
   protected ClassPool createGlobalClassPool(URL url) throws Exception
   {
      ClassLoader loader = createGlobalClassLoader(url);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassLoader createGlobalClassLoaderWithParent(URL url, ClassLoader parent) throws Exception
   {
      ClassLoader cl = new UnifiedClassLoader3(url, null, parent, globalRepository);
      globalRepository.addClassLoader(cl);
      registeredClassLoaders.add(cl);
      return cl;
   }

   protected ClassPool createGlobalClassPoolWithParent(URL url, ClassPool parent) throws Exception
   {
      ClassLoader parentLoader = parent != null ? parent.getClassLoader() : null;
      ClassLoader loader = createGlobalClassLoaderWithParent(url, parentLoader);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected void removeClassLoaderFromRepository(ClassLoader cl)
   {
      if (cl != null)
      {
         if (cl instanceof RepositoryClassLoader)
         {
            LoaderRepository repository = ((RepositoryClassLoader)cl).getLoaderRepository();
            repository.removeClassLoader(cl);
         }
      }
   }
   
   protected void removeClassPool(ClassPool pool)
   {
      if (pool != null)
      {
         ClassLoader cl = pool.getClassLoader();
         if (cl != null)
         {
            removeClassLoaderFromRepository(cl);
            AspectManager.instance().unregisterClassLoader(cl);
            registeredClassLoaders.remove(cl);
            //Delete the temporary directory since running in Eclipse does not do this automatically
            deleteTempDir();
         }
      }
   }
   
   private void deleteTempDir()
   {
      File dir = new File(".");
      String[] uclFiles = dir.list(
            new FilenameFilter() 
            {
               public boolean accept(File dir, String name) 
               {
                  return name.startsWith("ucl");
                     
               }
            });
      for (String uclFile : uclFiles)
      {
         deleteRecursively(dir, uclFile);
      }
   }
   
   private void deleteRecursively(File dir, String name)
   {
      File file = new File(dir, name);
      if (file.isDirectory())
      {
         String[] fileNames = file.list();
         for (String fileName : fileNames)
         {
            deleteRecursively(file, fileName);
         }
      }
      file.delete();
   }
   
   protected void assertCannotLoadClass(ClassLoader cl, String className)
   {
      try
      {
         cl.loadClass(className);
         fail("Should not have been able to load " + className);
      }
      catch(Exception expected)
      {
      }
   }
   
   protected void assertCannotLoadCtClass(ClassPool pool, String className)
   {
      try
      {
         pool.get(className);
      }
      catch(Exception e)
      {
      }
   }
   
   protected void assertCannotLoadClass(LoaderRepository repository, String className)
   {
      try
      {
         repository.loadClass(className);
         fail("Should not have been able to load " + className);
      }
      catch(Exception expected)
      {
      }
   }
   
   protected ClassLoader createChildClassLoader(URL url, boolean parentFirst) throws Exception
   {
      HeirarchicalLoaderRepository3 repository = new HeirarchicalLoaderRepository3(getServer(), MAIN_LOADER_REPOSITORY_OBJECT_NAME);
      repository.setUseParentFirst(parentFirst);
      ClassLoader cl = repository.newClassLoader(url, true);
      registeredClassLoaders.add(cl);
      return cl;
   }
   
   protected ClassPool createChildClassPool(URL url, boolean parentFirst) throws Exception
   {
      ClassLoader loader = createChildClassLoader(url, parentFirst);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassLoader createChildURLClassLoader(ClassLoader parent, URL url)
   {
      ClassLoader cl = new URLClassLoader(new URL[] {url}, parent);
      registeredClassLoaders.add(cl);
      return cl;
   }
   
   protected ClassLoader createChildURLClassLoaderParentLast(ClassLoader parent, URL url)
   {
      ClassLoader cl = new ParentLastURLClassLoader(new URL[] {url}, parent);
      registeredClassLoaders.add(cl);
      return cl;
   }
   
   protected ClassPool createChildURLClassPool(ClassPool parent, URL url)
   {
      ClassLoader parentLoader = parent != null ? parent.getClassLoader() : null;
      ClassLoader cl = createChildURLClassLoader(parentLoader, url);
      registeredClassLoaders.add(cl);
      return AspectManager.instance().registerClassLoader(cl);
   }   
   
   protected ClassPool createChildURLClassPoolParentLast(ClassPool parent, URL url)
   {
      ClassLoader parentLoader = parent != null ? parent.getClassLoader() : null;
      ClassLoader cl = createChildURLClassLoaderParentLast(parentLoader, url);
      registeredClassLoaders.add(cl);
      ClassPool pool = AspectManager.instance().registerClassLoader(cl);
      pool.childFirstLookup = true;
      return pool;
   }   

   /**
    * The test classes should not be on the launcher classpath
    */
   public void testClassesNotOnClasspath()
   {
      assertCannotLoadClass(this.getClass().getClassLoader(), CLASS_A);
      assertCannotLoadClass(this.getClass().getClassLoader(), CLASS_B);
      assertCannotLoadClass(this.getClass().getClassLoader(), CLASS_C);
   }
}
