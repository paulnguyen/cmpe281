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
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.Loader;
import org.jboss.classloading.spi.RealClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ScopedJBoss5ClassPool extends JBoss5ClassPool
{
   ThreadLocal<ClassPool> lastPool = new ThreadLocal<ClassPool>();
   WeakReference<ClassLoaderDomain> domainRef;
   /** The classpool representing the parent domain of this one */
   ClassPool parentDomainPool;

   public ScopedJBoss5ClassPool(ClassLoader cl, ClassPool src, ClassPool parentDomainPool, ScopedClassPoolRepository repository, 
         URL tmpURL, boolean parentFirst, ClassLoaderDomain domain)
   {
      super(cl, src, repository, tmpURL);
      super.childFirstLookup = !parentFirst;
      this.parentDomainPool = parentDomainPool;
      this.domainRef = new WeakReference<ClassLoaderDomain>(domain);
      
      logger.debug("Created new ScopedJBoss5ClasPool for " + cl + ", with parent: " + src + ", parentDomain: " + parentDomainPool + ", parentFirst: " + parentFirst);
   }

   private URL getResourceUrlForClass(String resourcename)
   {
      ClassLoaderDomain domain = domainRef.get();
      return domain.getResource(resourcename);
   }

   private boolean isMine(URL myURL, String resourceName)
   {
      if (myURL == null)
      {
         return false;
      }
      
      ClassLoaderDomain domain = domainRef.get();
      Loader parent = domain.getParent();
      URL parentURL = parent.getResource(resourceName);
      
      if (parentURL == null)
      {
         return true;
      }
      
      if (!myURL.equals(parentURL))
      {
         return true;
      }
      
      return false;
   }
   

   @Override
   protected synchronized CtClass get0(String classname, boolean useCache) throws NotFoundException
   {
      CtClass clazz = null;
      if (useCache)
      {
         clazz = getCached(classname);
         if (clazz != null)
            return clazz;
      }

      if (!childFirstLookup)
      {
         if (parentDomainPool != null)
         {
            clazz = performGet(parentDomainPool, classname);
            if (clazz != null)
            {
               return clazz;
            }
         }
         if (parent != null)
         {
            clazz = performGet(parent, classname);
            if (clazz != null)
            {
               return clazz;
            }
         }
      }

      clazz = createCtClass(classname, useCache);
      if (clazz != null)
      {
         // clazz.getName() != classname if classname is "[L<name>;".
         if (useCache)
            cacheCtClass(clazz.getName(), clazz, false);

         return clazz;
      }

      if (childFirstLookup)
      {
         if (parent != null)
         {
            clazz = performGet(parent, classname);
            if (clazz != null)
            {
               return clazz;
            }
         }
         if (parentDomainPool != null)
         {
            clazz = performGet(parentDomainPool, classname);
            if (clazz != null)
            {
               return clazz;
            }
         }
      }

      return clazz;
   }

   private CtClass performGet(ClassPool pool, String classname)
   {
      try
      {
         //Want to avoid calling get() if possible since that creates NotFoundExceptions
         //on misses which is expensive
         if (pool instanceof AOPClassPool)
         {
            return ((AOPClassPool)pool).internalGet0(classname, true);
         }
         else
         {
            return pool.get(classname);
         }
      }
      catch (NotFoundException e)
      {
         return null;
      }
   }
   
   
   public CtClass getCached(String classname)
   {
      boolean trace = logger.isTraceEnabled();
      
      if (trace)
      {
         logger.trace("getCached() " + classname);
      }
      
      if (classname == null)
      {
         if (trace)
         {
            logger.trace("getCached() returning null (classname == null)");
         }
         return null;
      }
      if (isUnloadedClassLoader())
      {
         if (trace)
         {
            logger.trace("getCached() returning null (unloaded)");
         }
         return null;
      }

      if (generatedClasses.get(classname) != null)
      {
         //It is a new class, and this callback is probably coming from the frozen check when creating a new nested class
         if (trace)
         {
            logger.trace("getCached() In generated classes - check super");
         }
         return super.getCached(classname);
      }
      
      //Is this from the scoped classloader itself of from the parent?
      String resourcename = getResourceName(classname);
      URL url = getResourceUrlForClass(resourcename);
      
      if (isMine(url, resourcename))
      {
         if (super.childFirstLookup)
         {
            //Parent delegation is false, attempt to get this class out of ourselves
            CtClass clazz = super.getCachedLocally(classname);
            if (clazz == null)
            {
               if (trace)
               {
                  logger.trace("getCached() Creating my class " + classname);
               }
               clazz = createCtClass(classname, false);
               if (clazz != null)
               {
                  lockInCache(clazz);
               }
            }
            if (clazz != null)
            {
               if (trace)
               {
                  logger.trace("getCached() Returning my class " + classname);
               }
               return clazz;
            }
         }
         if (trace)
         {
            logger.trace("getCached() Checking super for my class " + classname);
         }
         return super.getCached(classname);
      }
      else if (url == null)
      {
         if (trace)
         {
            logger.trace("getCached() Checking super for my class " + classname + " (no url)");
         }
         return super.getCached(classname);
      }
      

      try
      {
         ClassPool pool = getCorrectPoolForResource(classname, resourcename, url, trace);
         if (trace)
         {
            logger.trace("getCached() Found pool for class " + classname + " " + pool);
         }
         if (pool != lastPool.get())
         {
            lastPool.set(pool);
            CtClass found = pool.get(classname);
            if (trace)
            {
               logger.trace("getCached() Found clazz " + classname + " in " + pool + " : " + found);
            }
            return found;
         }
      }
      catch (NotFoundException e)
      {
      }
      catch(StackOverflowError e)
      {
         throw e;
      }
      finally
      {
         lastPool.set(null);
      }

      return null;
   }
   
   @Override
   protected boolean includeInGlobalSearch()
   {
      return false;
   } 
   
   private ClassPool getCorrectPoolForResource(String classname, String resourceName, URL url, boolean trace)
   {
      synchronized(AspectManager.getRegisteredCLs())
      {
         //JBoss 5 has an extra NoAnnotationURLCLassLoader that is not on the default path, make sure that that is checked at the end
         //FIXME This needs revisiting/removing once the 
         ArrayList<ClassPool> noAnnotationURLClassLoaderPools = null;
                 
         for(Iterator<ClassPool> it = AspectManager.getRegisteredCLs().values().iterator() ; it.hasNext() ; )
         {
            AOPClassPool candidate = (AOPClassPool)it.next();
            if (candidate.isUnloadedClassLoader())
            {
               AspectManager.instance().unregisterClassLoader(candidate.getClassLoader());
               continue;
            }
            
            if (candidate.getClassLoader() instanceof RealClassLoader)
            {
               //Sometimes the ClassLoader is a proxy for MBeanProxyExt?!
               RealClassLoader bcl = (RealClassLoader)candidate.getClassLoader();
               URL foundUrl = bcl.getResourceLocally(resourceName);
               if (foundUrl != null)
               {
                  if (url.equals(foundUrl))
                  {
                     if (trace)
                     {
                        logger.trace("getCorrectPoolForResource() Candidate classloader " + bcl + " has local resource " + foundUrl);
                     }
                     return candidate;
                  }
               }
            }
            //FIXME Remove once we have the JBoss 5 version of pool
            else if (isInstanceOfNoAnnotationURLClassLoader(candidate.getClassLoader()))
            {
               if (noAnnotationURLClassLoaderPools == null)
               {
                  noAnnotationURLClassLoaderPools = new ArrayList<ClassPool>(); 
               }
               noAnnotationURLClassLoaderPools.add(candidate);
            }
         }
         
         //FIXME Remove once we have the JBoss 5 version of pool
         if (noAnnotationURLClassLoaderPools != null)
         {
            for (ClassPool pool : noAnnotationURLClassLoaderPools)
            {
               try
               {
                  pool.get(classname);
                  if (trace)
                  {
                     logger.trace("getCorrectPoolForResource(() Found  " + classname + " (no url)");
                  }
                  return pool;
               }
               catch(NotFoundException ignoreTryNext)
               {
               }
            }
         }
      }
      return createTempPool();
   }
   
   private ClassPool createTempPool()
   {
      //Rememeber that the stuff in jboss5/lib goes in a child classloader of the default classloader. We need
      //to make this the parent of the temp classloader
      ClassLoader aopLoader = AspectManager.class.getClassLoader();
      ClassPool pool = AspectManager.instance().registerClassLoader(aopLoader);
      return AOPClassPool.createAOPClassPool(pool, AOPClassPoolRepository.getInstance());
   }
   
   /**
    * NoAnnotationURLCLassLoader lives in different packages in JBoss 4 and 5
    */
   private boolean isInstanceOfNoAnnotationURLClassLoader(ClassLoader loader)
   {
      if (loader == null)
      {
         return false;
      }
      Class<?> parent = loader.getClass();
      while (parent != null)
      {
         if ("NoAnnotationURLClassLoader".equals(parent.getSimpleName()))
         {
            return true;
         }
         parent = parent.getSuperclass();
      }
      return false;
   }
   
}
