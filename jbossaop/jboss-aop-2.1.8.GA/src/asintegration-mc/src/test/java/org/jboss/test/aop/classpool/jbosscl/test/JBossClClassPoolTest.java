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
package org.jboss.test.aop.classpool.jbosscl.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import junit.framework.Test;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.jboss5.RegisterModuleCallback;
import org.jboss.aop.asintegration.jboss5.VFSClassLoaderDomainRegistry;
import org.jboss.aop.classpool.jbosscl.JBossClDelegatingClassPoolFactory;
import org.jboss.beans.metadata.spi.BeanMetaDataFactory;
import org.jboss.classloader.plugins.filter.CombiningClassFilter;
import org.jboss.classloader.plugins.filter.PatternClassFilter;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloader.spi.ParentPolicy;
import org.jboss.classloader.spi.filter.ClassFilter;
import org.jboss.classloader.spi.filter.PackageClassFilter;
import org.jboss.classloader.test.support.IsolatedClassLoaderTestHelper;
import org.jboss.classloading.spi.dependency.ClassLoading;
import org.jboss.classloading.spi.dependency.Module;
import org.jboss.kernel.plugins.deployment.AbstractKernelDeployment;
import org.jboss.kernel.spi.deployment.KernelDeployment;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.aop.classpool.jbosscl.support.BundleInfoBuilder;
import org.jboss.test.aop.classpool.jbosscl.support.Result;
import org.jboss.test.aop.classpool.jbosscl.support.TestVFSClassLoaderFactory;
import org.jboss.test.aop.classpool.jbosscl.support.TestVFSClassLoaderFactoryFactory;
import org.jboss.test.kernel.junit.MicrocontainerTest;
import org.jboss.virtual.VFS;

/**
 * Base class for testing the new JBoss classloaders
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossClClassPoolTest extends MicrocontainerTest
{
   public final static URL JAR_A_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-a1.jar");
   public final static URL JAR_A_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-a2.jar");
   public final static URL JAR_B_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-b1.jar");
   public final static URL JAR_B_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-b2.jar");
   public final static URL JAR_C_1 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-c1.jar");
   public final static URL JAR_C_2 = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-c2.jar");

   public static final String PACKAGE_ROOT = "org.jboss.test.aop.classpool.jbosscl.support.excluded";
   public static final String PACKAGE_A = PACKAGE_ROOT + ".a";
   public static final String PACKAGE_B = PACKAGE_ROOT + ".b";
   public static final String PACKAGE_C = PACKAGE_ROOT + ".c";

   public final static String CLASS_A = PACKAGE_A + ".A";
   public final static String CLASS_B = PACKAGE_B + ".B";
   public final static String CLASS_C = PACKAGE_C + ".C";
   
   //Keep a strong reference to the URL classloaders so that they are not garbage collected
   final static Set<URLClassLoader> registeredURLClassLoaders = new HashSet<URLClassLoader>();

   private Map<ClassLoader, ClassLoaderDomain> scopedChildDomainsByLoader = new WeakHashMap<ClassLoader, ClassLoaderDomain>();
   
   private LoaderNameDeploymentRegistry loaderNameDeploymentRegistry = new LoaderNameDeploymentRegistry();
   
   /** The classloader helper */
   protected static final ClassLoaderSystem system = ClassLoaderSystem.getInstance();
   private static boolean initialisedDefaultDomain;
   private static final ClassFilter aopFilter;
   static
   {
      String[] classPatterns = new String[] {
            "org\\.jboss\\.aop\\..+", 
            "org\\.jboss\\.classloading\\..+", 
            "org\\.jboss\\.classloader\\..+", 
            "org\\.jboss\\.virtual\\..+", 
            "org\\.jboss\\.test\\.aop\\.classpool\\.jbosscl\\..+\\..+", 
            "org\\.jboss\\.metadata\\..+"};
      String[] resourcePatterns = new String[] {
            "org/jboss/aop/.+", 
            "org/jboss/classloading/.+", 
            "org/jboss/classloader/.+", 
            "org/jboss/virtual/.+", 
            "org/jboss/test/aop/classpool/jbosscl/.+\\..+", 
            "org/jboss/metadata/.+"};
      aopFilter = new PatternClassFilter(classPatterns, resourcePatterns, null);
   }
   
   protected static VFSClassLoaderDomainRegistry domainRegistry;
   
   ClassLoading classLoading = new ClassLoading();

   static
   {
      domainRegistry = new VFSClassLoaderDomainRegistry();
      
      AspectManager.setClassPoolFactory(new JBossClDelegatingClassPoolFactory(domainRegistry, new RegisterModuleCallback()));
      VFS.init();
   }


   public static ClassLoaderSystem getSystem()
   {
      if (!initialisedDefaultDomain)
      {
         ClassLoaderDomain defaultDomain = system.getDefaultDomain();
         
         Set<String> parentPackages = IsolatedClassLoaderTestHelper.getParentPackages();
         String[] parentPkgs = parentPackages.toArray(new String[parentPackages.size()]);
         PackageClassFilter filter = new PackageClassFilter(parentPkgs);
         filter.setIncludeJava(true);
         CombiningClassFilter beforeFilter = CombiningClassFilter.create(filter, aopFilter);
         ParentPolicy parentPolicy = new ParentPolicy(beforeFilter, ClassFilter.NOTHING);
         
         defaultDomain.setParentPolicy(parentPolicy);
      }
      return system;
   }
   
   public static Test suite(Class<?> clazz)
   {
      return AbstractTestCaseWithSetup.suite(clazz);
   }
   
   public JBossClClassPoolTest(String name)
   {
      super(name);
   }


   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      return new JBossClClassPoolTestDelegate(clazz);
   }
   

   protected static URL getURLRelativeToProjectRoot(String relativePath)
   {
      try
      {
         URL url = JBossClClassPoolTest.class.getProtectionDomain().getCodeSource().getLocation();
         String location = url.toString();
         int index = location.lastIndexOf("/asintegration-mc/") + "/asintegration-mc/".length();
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
   
   protected ClassLoaderDomain getDefaultDomain()
   {
      return getSystem().getDefaultDomain();
   }

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
//      enableTrace("org.jboss.aop.classpool");
//      enableTrace("org.jboss.aop.classpool.jbosscl");
      deploy("/org/jboss/test/aop/classpool/jbosscl/Common.xml");
   }


   @Override
   protected void tearDown() throws Exception
   {
      undeploy("/org/jboss/test/aop/classpool/jbosscl/Common.xml");
      super.tearDown();
   }

   protected String array(String name)
   {
      return name + "[]";
   }

   protected ClassLoader createClassLoader(String name, boolean importAll, URL... urls) throws Exception
   {
      return createClassLoader(null, name, importAll, urls);
   }
   
   protected ClassLoader createClassLoader(Result result, String name, boolean importAll, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = TestVFSClassLoaderFactoryFactory.createClassLoaderFactory(name, importAll, urls);
      return createClassLoader(result, factory);
   }
   
   protected ClassLoader createClassLoader(String name, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassLoader(null, name, builder, urls);
   }
   
   protected ClassLoader createClassLoader(Result result, String name, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassLoader(result, name, false, builder, urls);
   }
      
   protected ClassLoader createClassLoader(String name, boolean importAll, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassLoader(null, name, importAll, builder, urls);
   }
      
   protected ClassLoader createClassLoader(Result result, String name, boolean importAll, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = TestVFSClassLoaderFactoryFactory.createClassLoaderFactory(name, importAll, builder, urls);
      return createClassLoader(result, factory);
   }
      
   protected ClassLoader createChildDomainParentFirstClassLoader(String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader((Result)null, name, domainName, importAll, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(Result result, String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader(result, name, domainName, importAll, null, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(String name, String domainName, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader(null, name, domainName, importAll, parent, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(Result result, String name, String domainName, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, null, true, importAll, parent, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader(null, name, domainName, parentDomainName, importAll, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(Result result, String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, parentDomainName, true, importAll, null, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader((Result)null, name, domainName, importAll, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(Result result, String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader(result, name, domainName, importAll, null, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(String name, String domainName, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader(null, name, domainName, importAll, parent, urls);
   }

   protected ClassLoader createChildDomainParentLastClassLoader(Result result, String name, String domainName, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, null, false, importAll, parent, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader(null, name, domainName, parentDomainName, importAll, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(Result result, String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, parentDomainName, false, importAll, null, urls);
   }

   protected ClassLoader createChildDomainClassLoader(String name, String domainName, String parentDomainName, boolean parentFirst, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(null, name, domainName, parentDomainName, parentFirst, importAll, parent, urls);
   }
   
   protected ClassLoader createChildDomainClassLoader(Result result, String name, String domainName, String parentDomainName, boolean parentFirst, boolean importAll, ClassLoader parent, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = TestVFSClassLoaderFactoryFactory.createClassLoaderFactory(name, importAll, domainName, parentDomainName, parentFirst, urls);
      
      ClassLoader classLoader = createClassLoader(result, factory, parent);

      registerDomainAndLoader(classLoader, domainName);
      return classLoader;
   }

   protected ClassLoader createChildDomainParentFirstClassLoader(String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader((Result)null, name, domainName, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(Result result, String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader(result, name, domainName, null, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassLoader(null, name, domainName, parentDomainName, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentFirstClassLoader(Result result, String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, parentDomainName, builder, true, urls);
   }
      
   protected ClassLoader createChildDomainParentLastClassLoader(String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader((Result)null, name, domainName, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(Result result, String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader(result, name, domainName, null, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassLoader(null, name, domainName, parentDomainName, builder, urls);
   }
   
   protected ClassLoader createChildDomainParentLastClassLoader(Result result, String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(result, name, domainName, parentDomainName, builder, false, urls);
   }
   
   protected ClassLoader createChildDomainClassLoader(String name, String domainName, String parentDomainName, BundleInfoBuilder builder, boolean parentFirst, URL... urls) throws Exception
   {
      return createChildDomainClassLoader(null, name, domainName, parentDomainName, builder, parentFirst, urls);
   }
   
   protected ClassLoader createChildDomainClassLoader(Result result, String name, String domainName, String parentDomainName, BundleInfoBuilder builder, boolean parentFirst, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = TestVFSClassLoaderFactoryFactory.createClassLoaderFactory(name, domainName, parentDomainName, builder, parentFirst, urls);

      ClassLoader classLoader = createClassLoader(result, factory);

      registerDomainAndLoader(classLoader, domainName);
      return classLoader;
   }
   
   private void registerDomainAndLoader(ClassLoader classLoader, String domainName)
   {
      ClassLoaderDomain domain = getSystem().getDomain(domainName);
      scopedChildDomainsByLoader.put(classLoader, domain);
   }
   
   protected ClassLoader createChildURLClassLoader(ClassLoader parent, URL url)
   {
      URLClassLoader cl = new URLClassLoader(new URL[] {url}, parent);
      registeredURLClassLoaders.add(cl);
      return cl;
   }
   
   private ClassLoader createClassLoader(Result result, TestVFSClassLoaderFactory factory) throws Exception
   {
      return createClassLoader(result, factory, null);
   }
   
   private ClassLoader createClassLoader(Result result, TestVFSClassLoaderFactory factory, ClassLoader parent) throws Exception
   {
      if (parent != null)
      {
         factory.setParent(parent);
      }
      
      KernelDeployment deployment = install(factory);
      loaderNameDeploymentRegistry.registerDeployment(factory.getName(), deployment);
      if (result != null)
      {
         result.setFactory(factory);
      }
      ClassLoader loader = assertClassLoader(factory, parent);

      return loader;
   }

   /**
    * Here since we cannot access this via the classloading api
    */
   protected ClassLoaderDomain getChildDomainForLoader(ClassLoader loader)
   {
      return scopedChildDomainsByLoader.get(loader);
   }
   
   protected ClassLoaderDomain getChildDomainForPool(ClassPool pool)
   {
      return getChildDomainForLoader(pool.getClassLoader());
   }
   
   protected void unregisterDomain(ClassLoaderDomain domain)
   {
      if (domain != null)
      {
         ClassLoaderDomain registeredDomain = getSystem().getDomain(domain.getName());
         if (registeredDomain == null)
            throw new IllegalStateException("Domain is not registered: " + domain.getName());
         if (registeredDomain != domain)
            throw new IllegalStateException(domain + " is not the same as " + registeredDomain);
         getSystem().unregisterDomain(domain);
      }
   }
   
   protected void unregisterDomain(ClassLoader loader)
   {
      if (loader != null)
      {
         ClassLoaderDomain domain = getChildDomainForLoader(loader);
         unregisterDomain(domain);
      }
   }

   protected void unregisterDomain(ClassPool pool)
   {
      if (pool != null)
      {
         ClassLoaderDomain domain = getChildDomainForPool(pool);
         unregisterDomain(domain);
      }
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
   
   protected void assertCannotLoadClass(String className, ClassLoader cl)
   {
      assertCannotLoadClass(cl, className);
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

   protected void assertCannotLoadCtClass(String className, ClassPool pool)
   {
      assertCannotLoadCtClass(pool, className);
   }

   protected void assertCannotLoadClass(ClassLoaderDomain domain, String className)
   {
      try
      {
         Class<?> clazz = domain.loadClass(className);
         if (clazz == null)
         {
            return;
         }
         fail("Should not have been able to load " + className);
      }
      catch(Exception expected)
      {
      }
   }

   protected ClassPool createClassPool(String name, boolean importAll, URL... urls) throws Exception
   {
      return createClassPool(null, name, importAll, urls);
   }
   
   protected ClassPool createClassPool(Result result, String name, boolean importAll, URL... urls) throws Exception
   {
      ClassLoader loader = createClassLoader(result, name, importAll, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassPool createClassPool(String name, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassPool(null, name, builder, urls);
   }
   
   protected ClassPool createClassPool(Result result, String name, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassPool(result, name, false, builder, urls);
   }
   
   protected ClassPool createClassPool(String name, boolean importAll, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassPool(null, name, importAll, builder, urls);
   }

   protected ClassPool createClassPool(Result result, String name, boolean importAll, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = TestVFSClassLoaderFactoryFactory.createClassLoaderFactory(name, importAll, builder, urls);
      ClassLoader loader = createClassLoader(result, factory);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildDomainParentFirstClassPool(String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassPool((Result)null, name, domainName, importAll, urls);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(Result result, String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentFirstClassLoader(result, name, domainName, importAll, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(String name, String domainName, boolean importAll, ClassPool parent, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassPool(null, name, domainName, importAll, parent, urls);
   }

   protected ClassPool createChildDomainParentFirstClassPool(Result result, String name, String domainName, boolean importAll, ClassPool parent, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentFirstClassLoader(result, name, domainName, importAll, parent.getClassLoader(), urls);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassPool(null, name, domainName, parentDomainName, importAll, urls);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(Result result, String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentFirstClassLoader(result, name, domainName, parentDomainName, importAll, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildDomainParentLastClassPool(String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassPool((Result)null, name, domainName, importAll, urls);
   }
   
   protected ClassPool createChildDomainParentLastClassPool(Result result, String name, String domainName, boolean importAll, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentLastClassLoader(result, name, domainName, importAll, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildDomainParentLastClassPool(String name, String domainName, boolean importAll, ClassPool parent, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassPool(null, name, domainName, importAll, parent, urls);
   }

   protected ClassPool createChildDomainParentLastClassPool(Result result, String name, String domainName, boolean importAll, ClassPool parent, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentLastClassLoader(result, name, domainName, importAll, parent.getClassLoader(), urls);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassPool createChildDomainParentLastClassPool(String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassPool(null, name, domainName, parentDomainName, importAll, urls);
   }
   
   protected ClassPool createChildDomainParentLastClassPool(Result result, String name, String domainName, String parentDomainName, boolean importAll, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentLastClassLoader(result, name, domainName, parentDomainName, importAll, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassPool(null, name, domainName, parentDomainName, builder, urls);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(Result result, String name, String domainName, String parentDomainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentFirstClassLoader(result, name, domainName, parentDomainName, builder, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildDomainParentFirstClassPool(String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentFirstClassPool((Result)null, name, domainName, builder, urls);
   }
   
   protected ClassPool createChildDomainParentFirstClassPool(Result result, String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentFirstClassLoader(result, name, domainName, builder, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildDomainParentLastClassPool(String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createChildDomainParentLastClassPool(null, name, domainName, builder, urls);
   }

   protected ClassPool createChildDomainParentLastClassPool(Result result, String name, String domainName, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      ClassLoader loader = createChildDomainParentLastClassLoader(result, name, domainName, builder, urls);
      return AspectManager.instance().registerClassLoader(loader);
   }

   protected ClassPool createChildURLClassPool(ClassPool parent, URL url)
   {
      ClassLoader parentLoader = null;
      if (parent != null)
      {
         parentLoader = parent.getClassLoader();
      }
      ClassLoader loader = createChildURLClassLoader(parentLoader, url);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected void registerModule(ClassLoader loader, Module module)
   {
      registerModule(loader, module, null);
   }
   
   protected void registerModule(ClassLoader loader, Module module, ClassLoader parent)
   {
      if (getSystem() != domainRegistry.getSystem())
      {
         domainRegistry.setSystem(getSystem());
      }
      //TODO I have just hacked the domain here so this might cause problems
      //with the tests if we try to do weaving. However, it should be fine while just testing pools
      //and loaders
      domainRegistry.initMapsForLoader(loader, module, null, parent);
   }
   
   protected void unregisterModule(ClassLoader loader)
   {
      domainRegistry.cleanupLoader(loader);
   }
   
   protected void assertModule(ClassLoader loader)
   {
      ClassLoaderDomain domainForLoader = scopedChildDomainsByLoader.get(loader);
      if (domainForLoader == null)
      {
         //domainForLoader = helper.getDomain();
         domainForLoader = getSystem().getDefaultDomain();
      }
      assertNotNull(domainForLoader);
      
      ClassLoaderDomain domainForModule = domainRegistry.getClassLoaderDomainForLoader(loader);
      assertNotNull(domainForModule);
      assertSame(domainForLoader, domainForModule);
      
      Module module = domainRegistry.getModule(loader);
      assertNotNull(module);
      assertEquals(domainForModule.getName(), module.getDomainName());
      assertEquals(domainForModule.getParentDomainName(), module.getParentDomainName());
   }
  
   
   protected void assertNoClassLoader(Result result) throws Exception
   {
      if (result == null)
      {
         throw new IllegalStateException("Null result");
      }
      assertNoClassLoader(getContextName(result.getFactory()));
   }
  
   protected void assertNoClassPool(Result result) throws Exception
   {
      if (result == null)
      {
         throw new IllegalStateException("Null result");
      }
      assertNoClassLoader(getContextName(result.getFactory()));
   }
   
   protected void assertNoClassLoader(String name) throws Exception
   {
      try
      {
         Object bean = getBean(name, null);
         if (bean != null)
            fail("Should not be here: " + bean);
      }
      catch (Throwable t)
      {
         checkThrowable(IllegalStateException.class, t);
      }
   }

   protected void unregisterClassPool(ClassPool pool) throws Exception
   {
      if (pool != null)
      {
         ClassLoader loader = pool.getClassLoader();
         AspectManager.instance().unregisterClassLoader(loader);
         if (loader != null)
         {
            unregisterClassLoader(loader);
         }
      }
   }

   protected void unregisterClassLoader(ClassLoader classLoader) throws Exception
   {
      if (classLoader != null)
      {
         if (registeredURLClassLoaders.remove(classLoader) == false)
         {
            domainRegistry.cleanupLoader(classLoader);
            KernelDeployment deployment = loaderNameDeploymentRegistry.unregisterDeployment(classLoader);
            unregisterDeployment(deployment);
         }
      }
   }
   
   protected void unregisterClassLoader(String name) throws Exception
   {
      KernelDeployment deployment = loaderNameDeploymentRegistry.unregisterDeployment(name);
      unregisterDeployment(deployment);
   }
   
   private void unregisterDeployment(KernelDeployment deployment)
   {
      if (deployment != null)
      {
         undeploy(deployment);
      }
   }

   /**
    * Unregister a domain
    * 
    * @param name the domain name
    */
   protected void unregisterDomain(String name)
   {
      if (name != null)
      {
         ClassLoaderDomain registeredDomain = getSystem().getDomain(name);
         unregisterDomain(registeredDomain);
      }
   }
   
   protected KernelDeployment install(TestVFSClassLoaderFactory metaData) throws Exception
   {
      AbstractKernelDeployment deployment = new AbstractKernelDeployment();
      deployment.setName(metaData.getName() + ":" + metaData.getVersion());
      deployment.setBeanFactories(Collections.singletonList((BeanMetaDataFactory) metaData));
      deploy(deployment);
      return deployment;
   }

   protected String getContextName(TestVFSClassLoaderFactory factory)
   {
      String contextName = factory.getContextName();
      if (contextName == null)
         contextName = factory.getName() + ":" + factory.getVersion();
      return contextName;
   }

   protected ClassLoader assertClassLoader(TestVFSClassLoaderFactory factory) throws Exception
   {
      return assertClassLoader(factory, null);
   }
   
   protected ClassLoader assertClassLoader(TestVFSClassLoaderFactory factory, ClassLoader parent) throws Exception
   {
      try
      {
         Object obj = getBean(getContextName(factory));
         ClassLoader loader = assertInstanceOf(obj, ClassLoader.class);
         
         Module module = assertModule(getContextName(factory));
         registerModule(loader, module, parent);
         loaderNameDeploymentRegistry.registerLoaderName(factory.getName(), loader);
         
         return loader;
      }
      catch (IllegalStateException e)
      {
         throw new NoSuchClassLoaderException(e);
      }
   }
   
   protected ClassPool assertClassPool(TestVFSClassLoaderFactory factory) throws Exception
   {
      ClassLoader loader = assertClassLoader(factory);
      return AspectManager.instance().registerClassLoader(loader);
   }
   
   protected Class<?> assertLoadClass(String name, ClassLoader initiating) throws Exception
   {
      return assertLoadClass(name, initiating, initiating);
   }
   
   protected Class<?> assertLoadClass(String name, ClassLoader initiating, ClassLoader expected) throws Exception
   {
      Class<?> clazz = initiating.loadClass(name);
      if (expected != null)
      {
         assertSame(expected, clazz.getClassLoader());
      }
      return clazz;
   }

   protected CtClass assertLoadCtClass(String name, ClassPool initiating) throws Exception
   {
      return assertLoadCtClass(name, initiating, initiating);
   }
   
   protected CtClass assertLoadCtClass(String name, ClassPool initiating, ClassPool expected) throws Exception
   {
      CtClass clazz = initiating.get(name);
      if (expected != null)
      {
         assertSame(expected, clazz.getClassPool());
      }
      
      //Load twice to test both create and cache
      clazz = initiating.get(name);
      if (expected != null)
      {
         assertSame(expected, clazz.getClassPool());
      }
      
      assertLoadCtClassArray(name, clazz, initiating, expected);
      
      return clazz;
   }
   
   private void assertLoadCtClassArray(String name, CtClass clazz, ClassPool initiating, ClassPool expected) throws Exception
   {
      assertLoadCtClassArray(name, clazz, 1, initiating, expected);
      assertLoadCtClassArray(name, clazz, 2, initiating, expected);
   }
   
   private void assertLoadCtClassArray(String name, CtClass clazz, int dimensions, ClassPool initiating, ClassPool expected) throws Exception
   {
      String arrayName = name;
      for (int i = 0 ; i < dimensions ; i++)
      {
         arrayName = array(arrayName);
      }
      CtClass array = initiating.get(arrayName);
      
      if (expected != null)
      {
         assertSame(expected, array.getClassPool());
      }
      
      assertSame(clazz.getClassPool(), array.getClassPool());
      
      CtClass type = array;
      for (int i = 0 ; i < dimensions ; i++)
      {
         type = type.getComponentType();
      }
      assertSame(type, clazz);
   }

   protected Module assertModule(String contextName)
   {
      return assertBean(contextName + "$MODULE", Module.class);
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
   
   static class NoSuchClassLoaderException extends Exception
   {
      private static final long serialVersionUID = 1L;

      public NoSuchClassLoaderException(Exception e)
      {
         super(e);
      }
   }
   
   private static class LoaderNameDeploymentRegistry
   {
      private Map<String, KernelDeployment> deploymentsByName = new HashMap<String, KernelDeployment>();
      
      private Map<ClassLoader, String> namesByLoader = new HashMap<ClassLoader, String>();

      private void registerDeployment(String name, KernelDeployment deployment)
      {
         if (!deploymentsByName.containsKey(name))
         {
            deploymentsByName.put(name, deployment);
         }
      }
      
      private void registerLoaderName(String name, ClassLoader loader)
      {
         if (loader != null)
         {
            namesByLoader.put(loader, name);
         }
      }
      
      private KernelDeployment unregisterDeployment(String name)
      {
         return deploymentsByName.remove(name);
      }
      
      private KernelDeployment unregisterDeployment(ClassLoader loader)
      {
         String name = namesByLoader.remove(loader);
         return unregisterDeployment(name);
      }
   }

}
