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
package org.jboss.aop.asintegration.jboss5;


import java.lang.ref.WeakReference;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.domain.ScopedClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderDomain;


/**
 * A domain that is used for scoped classloaders with the new JBoss 5 classloaders 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author adrian@jboss.org
 * @version $Revision: 1.1 $
 */
public class ScopedVFSClassLoaderDomain extends ScopedClassLoaderDomain
{
   WeakReference<ClassLoaderDomain> classLoaderDomainRef;
   DomainRegistry registry;
   
   public ScopedVFSClassLoaderDomain(ClassLoader loader, String name, boolean parentDelegation, AspectManager manager, boolean parentFirst, 
         ClassLoaderDomain classLoaderDomain, DomainRegistry registry)
   {
      super(loader, name, parentDelegation, manager, parentFirst);
      classLoaderDomainRef = new WeakReference<ClassLoaderDomain>(classLoaderDomain);
      this.registry = registry;
   }

   private ClassLoaderDomain getClassLoaderDomain()
   {
      if (classLoaderDomainRef != null)
      {
         return classLoaderDomainRef.get();
      }
      return null;
   }
   
   // FIXME: JBAOP-107 REMOVE THIS HACK
   public boolean isValid()
   {
      ClassLoader cl = getClassLoader();
      if (cl == null)
         return false;
    
      ClassLoaderDomain domain = getClassLoaderDomain();
      if (domain == null)
         return false;
      
      if (domain.hasClassLoaders())
      {
         return true;
      }
      
      return false;
//      return cl.getLoaderRepository() != null;
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
         Class<?> superAspectClass = aspect.getClass();
         ClassLoaderDomain loadingDomain = getAspectRepository(superAspectClass);

         ClassLoaderDomain myDomain = getClassLoaderDomain();
         
         if (loadingDomain == myDomain)
         {
            //The parent does not load this class
            myPerVMAspects.put(def, aspect);
         }
         else
         {
            //The class has been loaded by a parent domain, find out if we also have a copy
            Class<?> myAspectClazz = myDomain.loadClass(aspect.getClass().getName());
            
            if (myAspectClazz == superAspectClass)
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
      }
      
      return aspect;
   }
   
   
   
   private ClassLoaderDomain getAspectRepository(Class<?> clazz)
   {
      ClassLoader cl = clazz.getClassLoader();
      ClassLoaderDomain domain = registry.getClassLoaderDomainForLoader(cl);
      return domain;
   }
}
