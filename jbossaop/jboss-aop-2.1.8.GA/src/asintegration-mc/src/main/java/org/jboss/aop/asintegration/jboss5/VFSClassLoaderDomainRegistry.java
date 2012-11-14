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
package org.jboss.aop.asintegration.jboss5;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.aop.Domain;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.dependency.Module;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class VFSClassLoaderDomainRegistry implements DomainRegistry
{
   final static ClassLoaderDomain domain = new ClassLoaderDomain("NOT_USED_PLACEHOLDER");
   
   private ClassLoaderSystem system;
   
   private ClassLoaderDomain defaultDomain;
   
   /** classloader domains by their classloaders */
   private Map<ClassLoader, WeakReference<ClassLoaderDomain>> classLoaderDomainsByLoader = new WeakHashMap<ClassLoader, WeakReference<ClassLoaderDomain>>();

   /** aopDomains by classloader domain */
   private Map<ClassLoaderDomain, ScopedVFSClassLoaderDomain> aopDomainsByClassLoaderDomain = new WeakHashMap<ClassLoaderDomain, ScopedVFSClassLoaderDomain>();
   
   /** parent deployment unit classloaders indexed by children */
   private Map<ClassLoader, WeakReference<ClassLoader>> classLoaderUnitParents = new WeakHashMap<ClassLoader, WeakReference<ClassLoader>>(); 
   
   /** Modules by classloader */
   private Map<ClassLoader, WeakReference<Module>> classLoaderModules = new WeakHashMap<ClassLoader, WeakReference<Module>>();
   
   /** classloaders by module */
   private Map<Module, WeakReference<ClassLoader>> moduleClassLoaders = new WeakHashMap<Module, WeakReference<ClassLoader>>();

   private Map<ClassLoaderDomain, Integer> classLoaderDomainReferenceCounts = new WeakHashMap<ClassLoaderDomain, Integer>();
   
   /**
    * Needed for the tests, not expected to be needed in production
    */
   public void setSystem(ClassLoaderSystem system)
   {
      this.system = system;
   }
   
   /**
    * Needed for the tests, not expected to be needed in production
    */
   public void setDefaultDomain(ClassLoaderDomain domain)
   {
      this.defaultDomain = domain;
   }
   
   public synchronized ClassLoaderSystem getSystem()
   {
      if (system == null)
      {
         system = ClassLoaderSystem.getInstance();
      }
      return system;
   }
   
   public synchronized ClassLoaderDomain getDefaultDomain()
   {
      if (defaultDomain == null)
      {
         defaultDomain = getSystem().getDefaultDomain();
      }
      return defaultDomain;
   }

   public synchronized boolean initMapsForLoader(ClassLoader loader, Module module, ScopedVFSClassLoaderDomain domain, ClassLoader parentUnitLoader)
   {
      if (loader == parentUnitLoader)
      {
         throw new IllegalArgumentException("initMapsForLoader() should only be called if parentUnitLoader is different from loader");
      }
      ClassLoaderSystem system = getSystem();
      
      String domainName = module.getDeterminedDomainName();
      ClassLoaderDomain clDomain = system.getDomain(domainName);
      boolean ret = false;
      if (!classLoaderDomainsByLoader.containsKey(loader))
      {
         Integer count = classLoaderDomainReferenceCounts.get(clDomain);
         int cnt = count == null ? 0 : count.intValue();
         classLoaderDomainReferenceCounts.put(clDomain, cnt);
         
         classLoaderDomainsByLoader.put(loader, new WeakReference<ClassLoaderDomain>(clDomain));
         classLoaderUnitParents.put(loader, new WeakReference<ClassLoader>(parentUnitLoader));
         classLoaderModules.put(loader, new WeakReference<Module>(module));
         moduleClassLoaders.put(module, new WeakReference<ClassLoader>(loader));
         ret = true;
         if (domain != null)
         {
            aopDomainsByClassLoaderDomain.put(clDomain, domain);
         }
      }
      
      
      return ret;
   }
   
   public synchronized void cleanupLoader(ClassLoader loader)
   {
      WeakReference<ClassLoaderDomain> clDomainRef = classLoaderDomainsByLoader.remove(loader);
      ClassLoaderDomain clDomain = clDomainRef == null ? null : clDomainRef.get();
      if (clDomain != null)
      {
         Integer count =  classLoaderDomainReferenceCounts.get(clDomain);
         int cnt = count == null ? 0 : count.intValue();
         if (cnt > 0)
         {
            cnt--;
         }
         if (cnt == 0)
         {
            aopDomainsByClassLoaderDomain.remove(clDomain);
            classLoaderDomainReferenceCounts.remove(clDomain);
         }
         else
         {
            classLoaderDomainReferenceCounts.put(clDomain, ++cnt);
         }
         classLoaderUnitParents.remove(loader);
         WeakReference<Module> moduleRef = classLoaderModules.remove(loader);
         if (moduleRef != null)
         {
            Module module = moduleRef.get();
            if (module != null)
            {
               moduleClassLoaders.remove(module);
            }
         }
      }
   }

   public synchronized Domain getRegisteredDomain(ClassLoader cl)
   {
      ClassLoaderDomain clDomain = getClassLoaderDomainForLoader(cl);
      if (clDomain != null)
      {
         return aopDomainsByClassLoaderDomain.get(clDomain);
      }
      return null;
   }
   
   public synchronized ClassLoaderDomain getClassLoaderDomainForLoader(ClassLoader cl)
   {
      WeakReference<ClassLoaderDomain> clDomainRef = classLoaderDomainsByLoader.get(cl);
      if (clDomainRef != null)
      {
         return clDomainRef.get();
      }
      
      ClassLoader parent = SecurityActions.getParent(cl);
      if (parent != null)
      {
         ClassLoaderDomain domain = getClassLoaderDomainForLoader(parent);
         if (domain != null)
         {
            classLoaderDomainsByLoader.put(parent, new WeakReference<ClassLoaderDomain>(domain));
            return domain;
         }
      }
      return null;
   }
   
   public synchronized ClassLoader getParentUnitLoader(ClassLoader loader)
   {
      WeakReference<ClassLoader> parentRef = classLoaderUnitParents.get(loader);
      if (parentRef != null)
      {
         return parentRef.get();
      }
      return null;
   }
   
   public synchronized Module getModule(ClassLoader loader)
   {
      WeakReference<Module> moduleRef = classLoaderModules.get(loader);
      if (moduleRef != null)
      {
         return moduleRef.get();
      }
      return null;
   }
   
   public synchronized ClassLoader getClassLoader(Module module)
   {
      WeakReference<ClassLoader> loaderRef = moduleClassLoaders.get(module);
      if (loaderRef != null)
      {
         return loaderRef.get();
      }
      return null;
   }
}
