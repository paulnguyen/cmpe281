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
package org.jboss.aop.asintegration.jboss4;

import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.classpool.AOPClassLoaderScopingPolicy;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.aop.domain.ScopedRepositoryClassLoaderDomain;
import org.jboss.logging.Logger;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.RepositoryClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class RepositoryClassLoaderScopingPolicy implements AOPClassLoaderScopingPolicy
{
   Logger log = Logger.getLogger(RepositoryClassLoaderScopingPolicy.class);
   
   /** A map of domains by loader repository, maintaned by the top level AspectManager */
   private Map<Object, Domain> scopedClassLoaderDomains = new WeakHashMap<Object, Domain>();

   public synchronized Domain getDomain(ClassLoader classLoader, AspectManager parent)
   {
      ClassLoader scopedClassLoader = ifScopedDeploymentGetScopedParentUclForCL(classLoader);
      if (scopedClassLoader != null)
      {
         Domain scopedManager = null;
         synchronized (AOPClassPoolRepository.getInstance().getRegisteredCLs())
         {
            Object loaderRepository = getLoaderRepository(classLoader);
            scopedManager = scopedClassLoaderDomains.get(loaderRepository);
            
            // FIXME: JBAOP-107 REMOVE THIS HACK
            if (scopedManager != null && scopedManager.isValid() == false)
            {
               scopedClassLoaderDomains.remove(loaderRepository);
               scopedManager = null;
            }
            
            if (scopedManager == null)
            {
               scopedManager = getScopedClassLoaderDomain(scopedClassLoader, parent);
               log.debug("Created domain " + scopedManager + " for scoped deployment on: " +
                        classLoader + "; identifying scoped ucl: " + scopedClassLoader);
               scopedManager.setInheritsBindings(true);
               scopedManager.setInheritsDeclarations(true);
               
               scopedClassLoaderDomains.put(loaderRepository, scopedManager);
            }
            return scopedManager;
         }
      }
      return null;
   }

   public Domain getTopLevelDomain(AspectManager parent)
   {
      ClassLoader classLoader = getTopLevelJBossClassLoader();
      return getDomain(classLoader, parent);
   }
   
//////////
   
   private ClassLoader ifScopedDeploymentGetScopedParentUclForCL(ClassLoader loader)
   {
      ClassLoader parent = loader;
      //The web classloader will be a child of the unified classloader - find out if that is scoped
      while (parent != null)
      {
         if (ScopedRepositoryClassLoaderHelper.isScopedClassLoader(parent))
         {
            return parent;
         }
         if (parent instanceof RepositoryClassLoader)
         {
            //We were a repository classloader, but not scoped - ignore the parents like a sulky teenager
            return null;
         }
         parent = parent.getParent();
      }
      return null;
   }
   
   private ClassLoader getTopLevelJBossClassLoader()
   {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      RepositoryClassLoader topRcl = null;
      while (loader != null)
      {
         if (loader instanceof RepositoryClassLoader)
         {
            topRcl = (RepositoryClassLoader)loader;
         }
         loader = loader.getParent();
      }
      return topRcl;
   }

   private Domain getScopedClassLoaderDomain(ClassLoader cl, AspectManager parent)
   {
      boolean parentDelegation = true;
      if (cl instanceof RepositoryClassLoader)
      {
         HeirarchicalLoaderRepository3 repository = (HeirarchicalLoaderRepository3)((RepositoryClassLoader)cl).getLoaderRepository();
         parentDelegation = repository.getUseParentFirst();
      }
      String name = String.valueOf(System.identityHashCode(cl));
      return new ScopedRepositoryClassLoaderDomain(cl, name, parentDelegation, parent, false);
   }

   private Object getLoaderRepository(ClassLoader loader)
   {
      ClassLoader cl = ifScopedDeploymentGetScopedParentUclForCL(loader);
      if (cl != null)
      {
         return ((RepositoryClassLoader)cl).getLoaderRepository();
      }
      return null;
   }

}
