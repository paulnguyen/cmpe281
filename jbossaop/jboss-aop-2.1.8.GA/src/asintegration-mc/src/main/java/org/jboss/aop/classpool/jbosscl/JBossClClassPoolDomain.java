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
package org.jboss.aop.classpool.jbosscl;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.jboss5.DomainRegistry;
import org.jboss.aop.classpool.BaseClassPool;
import org.jboss.aop.classpool.BaseClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.DelegatingClassPool;
import org.jboss.aop.util.ClassLoaderUtils;
import org.jboss.classloader.spi.ParentPolicy;
import org.jboss.classloading.spi.dependency.Module;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossClClassPoolDomain extends BaseClassPoolDomain
{
   Map<String, Set<DelegatingClassPool>> poolsByPackage = new HashMap<String, Set<DelegatingClassPool>>();
   final static List<DelegatingClassPool> EMPTY_LIST = Collections.unmodifiableList(Collections.EMPTY_LIST);
   
   AspectManager manager = AspectManager.instance();
   DomainRegistry registry;
   
   public JBossClClassPoolDomain(String domainName, ClassPoolDomain parent, ParentPolicy parentPolicy, DomainRegistry registry)
   {
      super(domainName, 
            new JBossClParentDelegationStrategy(
                  parent, 
                  parentPolicy, 
                  JBossClClassPoolToClassPoolDomainAdaptorFactory.getInstance())
      );
      this.registry = registry;
   }

   synchronized void setupPoolsByPackage(DelegatingClassPool pool)
   {
      if (pool instanceof JBossClDelegatingClassPool == false)
      {
         throw new IllegalStateException("Not an instance of JBossClDelegatingClassPool: " + pool.getClass().getName());
      }
      
      Module module = getModuleForPool(pool);
      boolean trace = logger.isTraceEnabled();
      
      for (String pkg : module.getPackageNames())
      {
         Set<DelegatingClassPool> pools = poolsByPackage.get(pkg);
         if (pools == null)
         {
            pools = new LinkedHashSet<DelegatingClassPool>();
            poolsByPackage.put(pkg, pools);
         }
         pools.add(pool);
         if (trace) logger.trace(this + " adding package " + pkg + " for pool " + pool);
      }
   }
   
   @Override
   protected synchronized void removeClassPool(DelegatingClassPool pool)
   {
      super.removeClassPool(pool);
   
      Module module = getModuleForPool(pool);
      boolean trace = logger.isTraceEnabled();

      for (String pkg : module.getPackageNames())
      {
         Set<DelegatingClassPool> pools = poolsByPackage.get(pkg);
         if (pools != null)
         {
            pools.remove(pool);
            if (pools.size() == 0)
            {
               poolsByPackage.remove(pkg);
               if (trace) logger.trace(this + " removing package " + pkg + " for pool " + pool);
            }
         }
      }
   }
 
   @Override
   protected CtClass getCachedOrCreate(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace)
   {
      if (trace) logger.trace(this + " looking for " + classname);
      
      CtClass clazz = getCheckPrimitive(classname);
      if (clazz != null)
      {
         if (trace) logger.trace(this + " isPrimitive " + classname);
      }
      else
      {
         Module module = getModuleForPool(initiatingPool);
         if (module != null && module.isImportAll())
         {
            //Use the old "big ball of mud" model
            if (trace) logger.trace(this + " isImportAll");
            return super.getCachedOrCreate(initiatingPool, classname, resourceName, create, trace);
         }
         
         //Attempt OSGi style loading
         if (isParentBefore(classname))
         {
            if (trace) logger.trace(this + " checking parent first for " + classname);
            clazz = getCachedOrCreateFromParent(null, classname, resourceName, create, trace);
         }
         
         //Check imports first
         if (clazz == null && module != null)
         {
            if (trace) logger.trace(this + " checking imports for " + classname);
            clazz = getCtClassFromModule(module, classname, trace);
         }
         
         //Try to check the initiating pool itself
         if (clazz == null && initiatingPool != null)
         {
            if (trace) logger.trace(this + " checking pool " + initiatingPool + " locally for " + classname);
            clazz = initiatingPool.loadLocally(classname, resourceName, create);
         }
         
         if (clazz == null && isParentAfter(classname))
         {
            if (trace) logger.trace(this + " checking parent last for " + classname);
            clazz = getCachedOrCreateFromParent(null, classname, resourceName, create, trace);
         }
      }
      if (trace) logger.trace(this + " found " + classname + " in " + (clazz == null ? "null" : clazz.getClassPool()));
      return clazz;
   }
   
   private CtClass getCheckPrimitive(String classname)
   {
      if (ClassLoaderUtils.isPrimitiveType(classname))
      {
         try
         {
            return ClassPool.getDefault().get(classname);
         }
         catch(NotFoundException e)
         {
            throw new RuntimeException(classname + " should have been a primitive", e);
         }
      }
      return null;
   }
   
   private Module getModuleForPool(DelegatingClassPool pool)
   {
      if (pool == null)
      {
         return null;
      }
      Module module = ((JBossClDelegatingClassPool)pool).getModule();
      if (logger.isTraceEnabled()) logger.trace(this + " got module " + module + " for " + pool);
      return module;
   }
   
   @Override
   protected List<DelegatingClassPool> getPoolsForClassName(String classname)
   {
      String packageName = ClassLoaderUtils.getPackageName(classname);
      Set<DelegatingClassPool> poolSet = poolsByPackage.get(packageName);
      if (poolSet == null)
      {
         return EMPTY_LIST;
      }
      return new ArrayList<DelegatingClassPool>(poolSet);
   }

   private CtClass getCtClassFromModule(final Module module, final String classname, boolean trace)
   {
      Module found = null;
      try
      {
         found = AccessController.doPrivileged(new PrivilegedExceptionAction<Module>()
         {
         
            public Module run() throws Exception
            {
               return module.getModuleForClass(ClassLoaderUtils.stripArrayFromClassName(classname));
            }
         });
      }
      catch (PrivilegedActionException e)
      {
         if (e.getCause() instanceof ClassNotFoundException == false)
         {
            throw new RuntimeException(e);
         }
      }

      if (trace) logger.trace(this + " module for " + classname + " " + found);

      if (found == null || found == module)
      {
         return null;
      }
      ClassLoader foundLoader = registry.getClassLoader(found);
      ClassPool pool = manager.findClassPool(foundLoader);
      try
      {
         if (pool instanceof BaseClassPool)
         {
            return getCachedOrCreateFromPool((BaseClassPool)pool, classname, true, trace);
         }
         return pool.get(classname);
      }
      catch(NotFoundException e)
      {
      }
      return null;
   }
}
