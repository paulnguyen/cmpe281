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
package org.jboss.aop.classpool;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.AspectManager;
import org.jboss.aop.util.ClassLoaderUtils;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 86859 $
 */
public class AOPClassPool extends ScopedClassPool
{
   protected final Logger logger = Logger.getLogger(this.getClass());
   
   /** Classnames of classes that will be created - we do not want to look for these in other pools.
    * The main use for this is when a class is created in a parent pool, and we then want to 
    * create a class with the same name in a parent-last child pool. As part of the create process
    * javassist.ClassPool will check if that class is frozen (which in turn will call getCached()
    * and get0()). If the classname exists in this map, get0() and getCached() should return null;   
    */
   protected final ConcurrentHashMap<String, String> generatedClasses = new ConcurrentHashMap<String, String>();

   protected final ConcurrentHashMap<String, Boolean> localResources = new ConcurrentHashMap<String, Boolean>();

   /** Classnames of classes that have been loaded, but were not woven */
   protected final ConcurrentHashMap<String, Boolean> loadedButNotWovenClasses = new ConcurrentHashMap<String, Boolean>();

   /** Causes the AOPClassPool.getCached() method to search all ClassPools registered in the repository */
   public static final Class<SearchAllRegisteredLoadersSearchStrategy> SEARCH_ALL_STRATEGY = SearchAllRegisteredLoadersSearchStrategy.class;

   /** Causes the AOPClassPool.getCached() method to search only itself */
   public static final Class<SearchLocalLoaderLoaderSearchStrategy> SEARCH_LOCAL_ONLY_STRATEGY = SearchLocalLoaderLoaderSearchStrategy.class;
   
   private final AOPCLassPoolSearchStrategy searchStrategy;
   
   private final Map<AOPClassPool, Boolean> children = new WeakHashMap<AOPClassPool, Boolean>();
   
   static
   {
      ClassPool.doPruning = false;
      ClassPool.releaseUnmodifiedClassFile = false;
   }

   public AOPClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      this(cl, src, repository, false);
   }

   protected AOPClassPool(ClassPool src, ScopedClassPoolRepository repository)
   {
      this(null, src, repository, true);
   }

   private AOPClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, boolean isTemp)
   {
      this(cl, src, repository, SEARCH_ALL_STRATEGY, isTemp);
   }

   public AOPClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, Class<? extends AOPCLassPoolSearchStrategy> searchStrategy)
   {
      this(cl, src, repository, searchStrategy, false);
   }
   
   public AOPClassPool(ClassPool src, ScopedClassPoolRepository repository, Class<? extends AOPCLassPoolSearchStrategy> searchStrategy)
   {
      this(null, src, repository, searchStrategy, true);
   }
   
   private AOPClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, Class<? extends AOPCLassPoolSearchStrategy> searchStrategy, boolean isTemp)
   {
      super(cl, src, repository, isTemp);
      if (searchStrategy == SEARCH_ALL_STRATEGY)
      {
         this.searchStrategy = new SearchAllRegisteredLoadersSearchStrategy();
      }
      else if (searchStrategy == SEARCH_LOCAL_ONLY_STRATEGY)
      {
         this.searchStrategy = new SearchLocalLoaderLoaderSearchStrategy();
      }
      else
      {
         try
         {
            this.searchStrategy = searchStrategy.newInstance();
         }
         catch (Exception e)
         {
            throw new RuntimeException("Error instantiating search strategy class " + searchStrategy, e);
         }
      }
      if (logger.isTraceEnabled()) logger.trace(this + " creating pool for loader " + cl + " searchStrategy:" + this.searchStrategy + " isTemp:" + isTemp);

      registerWithParent();
   }
   
   private void registerWithParent()
   {
      if (parent != null && parent instanceof AOPClassPool)
      {
         ((AOPClassPool)parent).children.put(this, Boolean.TRUE);
      }
   }
   
   private void unregisterWithParent()
   {
      if (parent != null && parent instanceof AOPClassPool)
      {
         ((AOPClassPool)parent).children.remove(this);
      }
   }
   
   public void registerGeneratedClass(String className)
   {
      generatedClasses.put(className, className);
   }

   public boolean isGeneratedClass(String className)
   {
      return generatedClasses.containsKey(className);
   }
   
   public void doneGeneratingClass(String className)
   {
      generatedClasses.remove(className);
   }

   public void close()
   {
      super.close();
      unregisterWithParent();
      AOPClassPoolRepository.getInstance().perfomUnregisterClassLoader(getClassLoader());
      for (Iterator<AOPClassPool> it = children.keySet().iterator() ; it.hasNext() ; )
      {
         AOPClassPool child = it.next();
         it.remove();
         ClassLoader loader = child.getClassLoader();
         if (loader != null)
         {
            AspectManager.instance().unregisterClassLoader(loader);
         }
      }
   }

   public CtClass getCached(String classname)
   {
      return searchStrategy.getCached(classname);
   }
   
   /**
    * Make createCtClass public so that we can override it 
    */
   @Override
   public CtClass createCtClass(String classname, boolean useCache)
   {
      boolean trace = logger.isTraceEnabled();
      
      if (trace) logger.trace(this + " attempting to create CtClass " + classname);
      CtClass clazz = super.createCtClass(classname, useCache);
      if (trace) logger.trace(this + " created CtClass " + getClassPoolLogStringForClass(clazz));
      
      return clazz;
   }

   @Override
   public void cacheCtClass(String classname, CtClass c, boolean dynamic)
   {
      boolean trace = logger.isTraceEnabled();
      if (trace) logger.trace(this + " caching " + classname);
      super.cacheCtClass(classname, c, dynamic);
      if (dynamic)
      {
         if (trace) logger.trace(this + " registering dynamic class " + classname);
         doneGeneratingClass(classname);
         String resourcename = getResourceName(classname);
         localResources.put(resourcename, Boolean.TRUE);
      }
   }

   protected boolean includeInGlobalSearch()
   {
      return true;
   }

   protected String getResourceName(String classname)
   {
      return ClassLoaderUtils.getResourceName(classname);
   }

   protected final boolean isLocalResource(String resourceName, boolean trace)
   {
      String classResourceName = resourceName;
      Boolean isLocal = localResources.get(classResourceName);
      if (isLocal != null)
      {
         if (trace) logger.trace(this + " " + resourceName + " is local " + isLocal);
      
         return isLocal.booleanValue();
      }
      boolean localResource = isLocalClassLoaderResource(classResourceName);
      localResources.put(classResourceName, localResource ? Boolean.TRUE : Boolean.FALSE);
      
      if (trace) logger.trace(this + " " + resourceName + " is local " + localResource);
      
      return localResource;
   }

   protected boolean isLocalClassLoaderResource(String classResourceName)
   {
      return getClassLoader().getResource(classResourceName) != null;
   }
   
   @Override
   public synchronized CtClass getLocally(String classname)
           throws NotFoundException
   {
      boolean trace = logger.isTraceEnabled();
      if (trace) logger.trace(this + " attempting local get for " + classname);
      softcache.remove(classname);
      CtClass clazz = (CtClass) classes.get(classname);
      if (trace && clazz != null)
      {
         logger.trace(this + " found " + classname + " in cache");
      }
      if (clazz == null)
      {
         clazz = createCtClass(classname, true);
         if (clazz == null) throw new NotFoundException(classname);
         lockInCache(clazz);//Avoid use of the softclasscache
      }

      return clazz;
   }

   @Override
   protected CtClass getCachedLocally(String classname)
   {
      if (logger.isTraceEnabled()) logger.trace(this + " checking local cache for " + classname);
      return super.getCachedLocally(classname);
   }

   @Override
   public void lockInCache(CtClass c)
   {
      if (logger.isTraceEnabled()) logger.trace(this + " locking in cache " + c.getName());
      super.lockInCache(c);
   }

   public void setClassLoadedButNotWoven(String classname)
   {
      loadedButNotWovenClasses.put(classname, Boolean.TRUE);
   }

   public boolean isClassLoadedButNotWoven(String classname)
   {
      return loadedButNotWovenClasses.get(classname) == Boolean.TRUE;
   }

   public static AOPClassPool createAOPClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      return (AOPClassPool)AspectManager.getClassPoolFactory().create(cl, src, repository);
   }

   public static AOPClassPool createAOPClassPool(ClassPool src, ScopedClassPoolRepository repository)
   {
      return (AOPClassPool)AspectManager.getClassPoolFactory().create(src, repository);
   }

   public String toString()
   {
      ClassLoader cl = null;
      try
      {
         cl = getClassLoader();
      }
      catch(IllegalStateException ignore)
      {
      }
      return this.getClass().getName() + "@" + System.identityHashCode(this) + " " + super.toString() + " - dcl:" + cl;
   }

   public CtClass internalGet0(String classname, boolean useCache) throws NotFoundException
   {
      return super.get0(classname, useCache);
   }
   
   protected String getClassPoolLogStringForClass(CtClass clazz)
   {
      if (clazz == null)
      {
         return null;
      }
      if (clazz.getClassPool() == null)
      {
         return null;
      }
      return clazz.getClassPool().toString();
   }

   /**
    * Contains the original AOPClassPool.getCached()
    * 
    */
   private class SearchAllRegisteredLoadersSearchStrategy implements AOPCLassPoolSearchStrategy
   {
      Logger logger = Logger.getLogger(this.getClass());
      public CtClass getCached(String classname)
      {
         boolean trace = logger.isTraceEnabled();
         
         if (trace) logger.trace(this + " " + AOPClassPool.this + " searching all pools for " + classname);
         
         CtClass clazz = getCachedLocally(classname);
         if (clazz == null)
         {
            //TODO is this check really necessary 
            boolean isLocal = false;

            ClassLoader cl = getClassLoader0();

            if (cl != null)
            {
               isLocal = isLocalResource(getResourceName(classname), trace);
            }

            if (!isLocal)
            {
               Object o = generatedClasses.get(classname);
               if (o == null)
               {
                  Map<ClassLoader, ClassPool> registeredCLs = AspectManager.getRegisteredCLs();
                  synchronized (registeredCLs)
                  {
                     for(ClassPool pl : AspectManager.getRegisteredCLs().values())
                     {
                        AOPClassPool pool = (AOPClassPool) pl;
                        if (pool.isUnloadedClassLoader())
                        {
                           if (trace) logger.trace(this + " pool is unloaded " + pool);
                           AspectManager.instance().unregisterClassLoader(pool.getClassLoader());
                           continue;
                        }

                        //Do not check classpools for scoped classloaders
                        if (!pool.includeInGlobalSearch())
                        {
                           if (trace) logger.trace(this + " pool is scoped " + pool);
                           continue;
                        }

                        if (trace) logger.trace(this + " " + AOPClassPool.this + " searching for " + classname + " in " + pool);
                        clazz = pool.getCachedLocally(classname);
                        if (clazz != null)
                        {
                           break;
                        }
                     }
                  }
               }
            }
         }
         if (trace) logger.trace(this + " " + AOPClassPool.this + " found " + classname + " in pool" + getClassPoolLogStringForClass(clazz));
         return clazz;
      }
   }
   
   /**
    * Checks only the AOPClassPool's cache 
    */
   private class SearchLocalLoaderLoaderSearchStrategy implements AOPCLassPoolSearchStrategy
   {
      Logger logger = Logger.getLogger(this.getClass());

      public CtClass getCached(String classname)
      {
         boolean trace = logger.isTraceEnabled();
         
         if (trace) logger.trace(this + " " + AOPClassPool.this + " searching just this pool for " + classname);
         CtClass clazz = getCachedLocally(classname);
         if (trace) logger.trace(this + " " + AOPClassPool.this + " found " + classname + " in pool" + getClassPoolLogStringForClass(clazz));
         return clazz;
      }
   }
}
