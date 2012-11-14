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
package org.jboss.aop.domain;


import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.loading.RepositoryClassLoader;


/**
 * A domain that is used for scoped classloaders
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author adrian@jboss.org
 * @version $Revision: 1.1 $
 */
public class ScopedRepositoryClassLoaderDomain extends ScopedClassLoaderDomain
{
   public ScopedRepositoryClassLoaderDomain(ClassLoader loader, String name, boolean parentDelegation, AspectManager manager, boolean parentFirst)
   {
      super(loader, name, parentDelegation, manager, parentFirst);
   }

   // FIXME: JBAOP-107 REMOVE THIS HACK
   public boolean isValid()
   {
      RepositoryClassLoader cl = (RepositoryClassLoader) getClassLoader();
      if (cl == null)
         return false;
      return cl.getLoaderRepository() != null;
   }

   protected Object getPerVmAspectWithNoParentDelegation(String def)
   {
      Object aspect = myPerVMAspects.get(def);
      if (aspect != null)
      {
         return aspect;
      }

      aspect = super.getSuperPerVmAspect(def);
      if (aspect != null)
      {
         synchronized(myPerVMAspects)
         {
            LoaderRepository loadingRepository = getAspectRepository(aspect);
            LoaderRepository myRepository = getScopedRepository();
            if (loadingRepository == myRepository)
            {
               //The parent does not load this class
               myPerVMAspects.put(def, aspect);
            }
            else
            {
               //The class has been loaded by a parent classloader, find out if we also have a copy
               try
               {
                  Class<?> clazz = myRepository.loadClass(aspect.getClass().getName());
                  if (clazz == aspect.getClass())
                  {
                     notMyPerVMAspects.put(def, Boolean.TRUE);
                  }
                  else
                  {
                     //We have a different version of the class deployed
                     AspectDefinition aspectDefinition = getAspectDefinition(def);
                     //Override the classloader to create the aspect instance
                     aspect = createPerVmAspect(def, aspectDefinition, getClassLoader());
                     myPerVMAspects.put(def, aspect);
                  }
               }
               catch (ClassNotFoundException e)
               {
                  throw new RuntimeException(e);
               }
            }
         }
      }
      
      return aspect;
   }
   
   
   
   private LoaderRepository getAspectRepository(Object aspect)
   {
      ClassLoader cl = aspect.getClass().getClassLoader();
      ClassLoader parent = cl;
      while (parent != null)
      {
         if (parent instanceof RepositoryClassLoader)
         {
            return ((RepositoryClassLoader)parent).getLoaderRepository();
         }
         parent = parent.getParent();
      }
      return null;
   }
   
   private HeirarchicalLoaderRepository3 getScopedRepository()
   {
      ClassLoader classloader = getClassLoader();
      if (classloader == null)
         throw new IllegalStateException("ClassLoader no longer exists: " + classLoaderString);
      if (classloader instanceof RepositoryClassLoader == false)
         throw new IllegalStateException("ClassLoader is not an instanceof RepositoryClassLoader " + classLoaderString);
      RepositoryClassLoader repositoryClassLoader = (RepositoryClassLoader) classloader;
      LoaderRepository loaderRepository = repositoryClassLoader.getLoaderRepository();
      if (loaderRepository == null)
         throw new IllegalStateException("ClassLoader has been undeployed: " + classLoaderString);
      if (loaderRepository instanceof HeirarchicalLoaderRepository3 == false)
         throw new IllegalStateException("Repository " + loaderRepository + " for classlaoder " + classLoaderString + " is not an HeirarchicalLoaderRepository3");
      return (HeirarchicalLoaderRepository3) loaderRepository;
   }
}
