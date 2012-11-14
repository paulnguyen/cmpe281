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

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.dependency.Module;
import org.jboss.deployers.structure.spi.DeploymentUnit;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class AOPClassLoaderInitializer
{
   public static Domain initializeForUnit(DeploymentUnit unit)
   {
      AOPClassLoaderScopingPolicyWithRegistry policy = (AOPClassLoaderScopingPolicyWithRegistry)AspectManager.getClassLoaderScopingPolicy();
      registerLoaders(policy, unit);
      Domain domain = getDomain(policy.getRegistry(), unit);
      
      return domain;
   }

   public static void unregisterLoaders(AspectManager manager, DeploymentUnit unit)
   {
      AOPClassLoaderScopingPolicyWithRegistry policy = (AOPClassLoaderScopingPolicyWithRegistry)AspectManager.getClassLoaderScopingPolicy();
      DomainRegistry registry = policy.getRegistry();
      if (unit.isTopLevel() || unit.getParent().getClassLoader() != unit.getClassLoader())
      {
         registry.cleanupLoader(unit.getClassLoader());
         manager.unregisterClassLoader(unit.getClassLoader());
      }
   }
   
   private static void registerLoaders(AOPClassLoaderScopingPolicyWithRegistry policy, DeploymentUnit unit)
   {
      DomainRegistry registry = policy.getRegistry();
      if (!unit.isTopLevel())
      {
         registerLoaders(policy, unit.getParent());
      }

      if (unit.isTopLevel() || unit.getParent().getClassLoader() != unit.getClassLoader())
      {
         //Only bother doing all this if we are a different loader from the parent unit 
         Module module = getModuleRecursively(unit);
         
         if (module == null)
         {
            throw new IllegalStateException("No " + Module.class.getName() + 
                  " attachment could be found in the following deployment unit or its parents: " + unit);
         }
         
         ScopedVFSClassLoaderDomain domain = getDomain(registry, unit); //THis might be wrong
         if (domain == null)
         {
            domain = createDomain(registry, unit.getClassLoader(), module, unit);
         }
         
         ClassLoader parentUnitLoader = unit.isTopLevel() ? null : unit.getParent().getClassLoader();
         registry.initMapsForLoader(unit.getClassLoader(), module, domain, parentUnitLoader);

         policy.registerClassLoader(module, unit.getClassLoader());
      }
   }
   
   /**
    * A unit that is a child will not automatically pick up the parent module
    */
   private static Module getModuleRecursively(DeploymentUnit unit)
   {
      if (unit == null)
      {
         return null;
      }
      Module module = unit.getAttachment(Module.class);
      if (module == null)
      {
          return getModuleRecursively(unit.getParent());
      }
      return module;
   }
   
   
   private static ScopedVFSClassLoaderDomain getDomain(DomainRegistry registry, DeploymentUnit unit)
   {
      return (ScopedVFSClassLoaderDomain)registry.getRegisteredDomain(unit.getClassLoader());
   }
   
   private static ScopedVFSClassLoaderDomain createDomain(DomainRegistry registry, ClassLoader loader, Module module, DeploymentUnit unit)
   {
      if (!module.getDeterminedDomainName().equals(ClassLoaderSystem.DEFAULT_DOMAIN_NAME))
      {
         ClassLoaderSystem system = registry.getSystem();
         String domainName = module.getDeterminedDomainName();
         ClassLoaderDomain domain = system.getDomain(domainName);
   
         boolean parentDelegation = module.isJ2seClassLoadingCompliance();
         String name = String.valueOf(System.identityHashCode(loader));
         
         ScopedVFSClassLoaderDomain parentDomain = unit.isTopLevel() ? null : getDomain(registry, unit.getParent());
         AspectManager parent = parentDomain != null ? parentDomain : AspectManager.getTopLevelAspectManager();

         return new ScopedVFSClassLoaderDomain(loader, name, parentDelegation, parent, false, domain, registry);
      }
      return null;
   }

}
