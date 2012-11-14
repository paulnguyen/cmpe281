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

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.InterceptionMarkers;
import org.jboss.aop.advice.AspectDefinition;


/**
 * A domain that is used for scoped classloaders
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author adrian@jboss.org
 * @version $Revision: 1.1 $
 */
public abstract class ScopedClassLoaderDomain extends Domain
{
   
   private WeakReference<ClassLoader> loader;
   protected boolean parentDelegation;
   protected ConcurrentHashMap<String, Object> myPerVMAspects = new ConcurrentHashMap<String, Object>();
   protected ConcurrentHashMap<String, Boolean> notMyPerVMAspects = new ConcurrentHashMap<String, Boolean>();
   protected InterceptionMarkers interceptionMarkers = new InterceptionMarkers();
   protected String classLoaderString;
   
   public ScopedClassLoaderDomain(ClassLoader loader, String name, boolean parentDelegation, AspectManager manager, boolean parentFirst)
   {
      super(manager, name, parentFirst);
      if (loader == null)
         throw new IllegalArgumentException("Null classloader");
      this.loader = new WeakReference<ClassLoader>(loader);
      this.parentDelegation = parentDelegation;
      classLoaderString = loader.toString();
      super.inheritsBindings = true;
      super.inheritsDeclarations = true;
   }

   protected ClassLoader getClassLoader()
   {
      ClassLoader cl = loader.get();
      if (cl != null)
      {
         return cl;
      }
      return null;
   }
   
   // FIXME: JBAOP-107 REMOVE THIS HACK
   @Override
   public abstract boolean isValid();

   @Override
   public void removeAspectDefinition(String name)
   {
      AspectDefinition def = super.internalRemoveAspectDefintion(name);
      if (def != null)
      {
         myPerVMAspects.remove(name);
      }
   }

   @Override
   public Object getPerVMAspect(AspectDefinition def)
   {
      return getPerVMAspect(def.getName());
   }

// These are indexed by classloader now, so no need to manage them explicitly here
//   @Override
//   public InterceptionMarkers getInterceptionMarkers(ClassLoader loader)
//   {
//      return interceptionMarkers;
//   }

   @Override
   public Object getPerVMAspect(String def)
   {
      if (parentDelegation == true)
      {
         //We will alway be loading up the correct class
         Object aspect = super.getPerVMAspect(def);
         return aspect;
      }
      else
      {
         return getPerVmAspectWithNoParentDelegation(def);
      }
   }
   
   @Override
   protected Object createPerVmAspect(String def, AspectDefinition adef, ClassLoader scopedClassLoader)
   {
      return super.createPerVmAspect(def, adef, getClassLoader());
   }
   
   protected Object getSuperPerVmAspect(String def)
   {
      return super.getPerVMAspect(def);
   }
   
   protected abstract Object getPerVmAspectWithNoParentDelegation(String def);
   
}
