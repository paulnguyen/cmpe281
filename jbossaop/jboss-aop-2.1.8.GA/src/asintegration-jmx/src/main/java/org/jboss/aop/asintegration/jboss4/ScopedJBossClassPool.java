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
package org.jboss.aop.asintegration.jboss4;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.aop.asintegration.jboss4.LoaderRepositoryUrlUtil.UrlInfo;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.loading.RepositoryClassLoader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * A classpool in JBoss backed by a scoped (HierarchicalLoaderRepository) loader repository
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ScopedJBossClassPool extends JBossClassPool 
{
   final static LoaderRepositoryUrlUtil LOADER_REPOSITORY_UTIL = new LoaderRepositoryUrlUtil();
   
   private UrlInfo urlInfo;
   private ThreadLocal<ClassPool> lastPool = new ThreadLocal<ClassPool>();

   public ScopedJBossClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, File tmp, URL tmpURL)
   {
      super(cl, src, repository, tmp, tmpURL);
      
      boolean parentFirst = false;
      LoaderRepository loaderRepository = null;
      ClassLoader prnt = cl;
      while (prnt != null)
      {
         if (prnt instanceof RepositoryClassLoader)
         {
            loaderRepository = ((RepositoryClassLoader)prnt).getLoaderRepository();
            if (loaderRepository instanceof HeirarchicalLoaderRepository3)
            {
               parentFirst = ((HeirarchicalLoaderRepository3)loaderRepository).getUseParentFirst();
            }
            break;
         }
         prnt = SecurityActions.getParent(cl);
      }
      
      super.childFirstLookup = !parentFirst;
   }
   

   private HeirarchicalLoaderRepository3 getRepository()
   {
      ClassLoader cl = getClassLoader0();
      if (cl != null)
      {
         return (HeirarchicalLoaderRepository3)((RepositoryClassLoader)cl).getLoaderRepository();
      }
      return null;
   }

   private URL getResourceUrlForClass(String resourcename)
   {
      HeirarchicalLoaderRepository3 repo = getRepository();
      return repo.getResource(resourcename, super.getClassLoader());
   }
   
   private boolean isMine(URL url)
   {
      HeirarchicalLoaderRepository3 repo = getRepository();
      if (repo != null)
      {
         //The URL of the class loaded with my scoped classloader
         if (url != null)
         {
            urlInfo = LOADER_REPOSITORY_UTIL.getURLInfo(getRepository(), urlInfo);
            
            URL[] myUrls = urlInfo.getLocalUrls();
            String resource = url.toString();
            for (int i = 0 ; i < myUrls.length ; i++)
            {
               if (resource.indexOf(myUrls[i].toString()) >= 0)
               {
                  return true;
               }
            }
            return false;
         }
      }
      return true;
   }

   public CtClass getCached(String classname)
   {
      if (classname == null)
      {
         return null;
      }
      if (isUnloadedClassLoader())
      {
         return null;
      }

      if (generatedClasses.get(classname) != null)
      {
         //It is a new class, and this callback is probably coming from the frozen check when creating a new nested class
         return super.getCached(classname);
      }
      
      //Is this from the scoped classloader itself of from the parent?
      String resourcename = getResourceName(classname);
      URL url = getResourceUrlForClass(resourcename);
      boolean isMine = isMine(url);
      
      if (isMine)
      {
         if (super.childFirstLookup)
         {
            //Parent delegation is false, attempt to get this class out of ourselves
            CtClass clazz = super.getCachedLocally(classname);
            if (clazz == null)
            {
               clazz = createCtClass(classname, false);
               if (clazz != null)
               {
                  lockInCache(clazz);
               }
            }
            if (clazz != null)
            {
               return clazz;
            }
         }
         return super.getCached(classname);
      }
      

      try
      {
         ClassPool pool = getCorrectPoolForResource(classname, url);
         if (pool != lastPool.get())
         {
            lastPool.set(pool);
            return pool.get(classname);
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
   
   private ClassPool getCorrectPoolForResource(String classname, URL url)
   {
      synchronized(AspectManager.getRegisteredCLs())
      {
         //JBoss 5 has an extra NoAnnotationURLCLassLoader that is not on the default path, make sure that that is checked at the end
         //FIXME This needs revisiting/removing once the 
         ArrayList<AOPClassPool> noAnnotationURLClassLoaderPools = null;
         String resource = url.toString();
         for(ClassPool pool : AspectManager.getRegisteredCLs().values())
         {
            AOPClassPool candidate = (AOPClassPool)pool;
            if (candidate.isUnloadedClassLoader())
            {
               AspectManager.instance().unregisterClassLoader(candidate.getClassLoader());
               continue;
            }
            
            if (candidate.getClassLoader() instanceof RepositoryClassLoader)
            {
               //Sometimes the ClassLoader is a proxy for MBeanProxyExt?!
               RepositoryClassLoader rcl = (RepositoryClassLoader)candidate.getClassLoader();
               URL[] urls = rcl.getClasspath();
               
               for (int i = 0 ; i < urls.length ; i++)
               {
                  if (resource.indexOf(urls[i].getFile()) >= 0)
                  {
                     return candidate;
                  }
               }
            }
            //FIXME Remove once we have the JBoss 5 version of pool
            else if (isInstanceOfNoAnnotationURLClassLoader(candidate.getClassLoader()))
            {
               if (noAnnotationURLClassLoaderPools == null)
               {
                  noAnnotationURLClassLoaderPools = new ArrayList<AOPClassPool>(); 
               }
               noAnnotationURLClassLoaderPools.add(candidate);
            }
         }
         
         //FIXME Remove once we have the JBoss 5 version of pool
         if (noAnnotationURLClassLoaderPools != null)
         {
            for (AOPClassPool pool : noAnnotationURLClassLoaderPools)
            {
               try
               {
                  pool.get(classname);
                  return pool;
               }
               catch(NotFoundException ignoreTryNext)
               {
               }
            }
         }
      }

      return AOPClassPool.createAOPClassPool(ClassPool.getDefault(), AOPClassPoolRepository.getInstance());
   }
   
   /**
    * NoAnnotationURLCLassLoader lives in different packages in JBoss 4 and 5
    */
   private boolean isInstanceOfNoAnnotationURLClassLoader(ClassLoader loader)
   {
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
