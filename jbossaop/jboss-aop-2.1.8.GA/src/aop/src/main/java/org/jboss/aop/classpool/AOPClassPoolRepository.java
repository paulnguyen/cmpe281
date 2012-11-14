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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.SecurityActions;
import org.jboss.aop.instrument.Instrumentor;
import org.jboss.aop.util.logging.AOPLogger;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;
import javassist.scopedpool.ScopedClassPoolRepositoryImpl;

/**
 * Singleton classpool repository used by aop
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 81450 $
 */
public class AOPClassPoolRepository implements ScopedClassPoolRepository
{
   private static final AOPLogger logger = AOPLogger.getLogger(AOPClassPoolRepository.class);
   
   private final static AOPClassPoolRepository instance = new AOPClassPoolRepository();
   
   /** The classes per classppol */
   protected final HashMap<ClassLoader, HashSet<Class<?>>> ucl2classes = new HashMap<ClassLoader, HashSet<Class<?>>>();

   /** The top-level AspectManager this pool belongs to */
   AspectManager manager;
   
   ScopedClassPoolRepository delegate;

   public static AOPClassPoolRepository getInstance()
   {
      return instance;
   }
   
   private AOPClassPoolRepository()
   {
      this.delegate = ScopedClassPoolRepositoryImpl.getInstance();
      //This was needed when jboss-aop.jar was deployed in the jboss/lib directory since the AspectManager bean had no chance to initialise it
      delegate.setClassPoolFactory(new AOPClassPoolFactory());
//      if (!setJBossSpecificStartupVariables())
//      {
//         delegate.setClassPoolFactory(new AOPClassPoolFactory());
//      }
   }

//   private boolean setJBossSpecificStartupVariables()
//   {
//      //We are running within JBoss 5, let's default to the JBossClassPoolFactory and set the ScopedClassPoolHelper
//      //so that we get correct behaviour before the AspectManager service has been deployed
//      try
//      {
//         Class jbcpf = Class.forName("org.jboss.aop.deployment.JBossClassPoolFactory");
//         ScopedClassPoolFactory factory = (ScopedClassPoolFactory)jbcpf.newInstance();
//         
//         Class hlpr = Class.forName("org.jboss.aop.deployment.JBossScopedClassLoaderHelper");
//         AOPScopedClassLoaderHelper helper = (AOPScopedClassLoaderHelper)hlpr.newInstance();
//         delegate.setClassPoolFactory(factory);
//         AspectManager.scopedCLHelper = helper;
//         return true;
//      }
//      catch (Exception e)
//      {
//      }
//      return false;//Not running in JBoss probably
//   }
   
   public void setClassPoolFactory(ScopedClassPoolFactory factory)
   {
      delegate.setClassPoolFactory(factory);
   }
   
   public ScopedClassPoolFactory getClassPoolFactory()
   {
      return delegate.getClassPoolFactory();
   }

   public boolean isPrune()
   {
      return delegate.isPrune();
   }

   public void setPrune(boolean prune)
   {
      delegate.setPrune(prune);
   }

   public ScopedClassPool createScopedClassPool(ClassLoader cl, ClassPool src)
   {
      return delegate.createScopedClassPool(cl, src);
   }

   public ClassPool findClassPool(ClassLoader cl)
   {
      return delegate.findClassPool(cl);
   }

   public void setAspectManager(AspectManager manager)
   {
      this.manager = manager;
   }
   
   /**
    * Get the registered classloaders
    * 
    * @return the registered classloaders
    */
   public Map<ClassLoader, ClassPool> getRegisteredCLs()
   {
      return delegate.getRegisteredCLs();
   }

   /**
    * This method will check to see if a register classloader has been undeployed (as in JBoss)
    */
   public void clearUnregisteredClassLoaders()
   {
      delegate.clearUnregisteredClassLoaders();
   }
   
   public ClassPool registerClassLoader(ClassLoader ucl)
   {
      return delegate.registerClassLoader(ucl);
   }

   public void unregisterClassLoader(ClassLoader cl)
   {
      delegate.unregisterClassLoader(cl);
   }
   
   public void registerClass(Class<?> clazz)
   {
      ClassLoader classLoader = SecurityActions.getClassLoader(clazz);
      HashSet<Class<?>> classes = ucl2classes.get(classLoader);
      if (classes == null)
      {
         classes = new HashSet<Class<?>>();
         ucl2classes.put(classLoader, classes);
      }
      classes.add(clazz);
   }

   public void perfomUnregisterClassLoader(ClassLoader cl)
   {
      if (System.getSecurityManager() == null)
      {
         UnregisterClassLoaderAction.NON_PRIVILEGED.unregister(this, cl);
      }
      else
      {
         UnregisterClassLoaderAction.PRIVILEGED.unregister(this, cl);
      }
   }
   
   private void doUnregisterClassLoader(ClassLoader cl)
   {
      synchronized (delegate.getRegisteredCLs())
      {
         HashSet<Class<?>> classes = ucl2classes.remove(cl);
         if (classes != null)
         {
            for (Class<?> clazz : classes)
            {
               synchronized (manager.getAdvisors())
               {
                  WeakReference<Advisor> ref = manager.getAdvisors().get(clazz);
                  if (ref != null)
                  {
                     Advisor advisor = ref.get();
                     manager.getAdvisors().remove(clazz);
                     if (advisor != null)
                     {
                        advisor.cleanup();
                     }
                  }
                  Class<?> advisedClass = clazz;
                  try
                  {
                     //The static advisor field should be the only remaining hard reference to the advisor
                     Field f = advisedClass.getDeclaredField(Instrumentor.HELPER_FIELD_NAME);
                     f.setAccessible(true);
                     f.set(null, null);
                  }
                  catch(NoSuchFieldException e)
                  {
                     logger.warn("Error unsetting advisor for " + advisedClass.getName() + " " + e);
                  }
                  catch(IllegalAccessException e)
                  {
                     logger.warn("Error unsetting advisor for " + advisedClass.getName() + " " + e);
                  }
               }
            }
         }
      }
   }

   
   interface UnregisterClassLoaderAction
   {
      void unregister(AOPClassPoolRepository repository, ClassLoader loader);
      
      UnregisterClassLoaderAction PRIVILEGED = new UnregisterClassLoaderAction()
      {
         public void unregister(final AOPClassPoolRepository repository, final ClassLoader loader)
         {
            try
            {
               AccessController.doPrivileged(new PrivilegedExceptionAction<Object>()
               {
                  public Object run()
                  {
                     repository.doUnregisterClassLoader(loader);
                     return null;
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception ex = e.getException();
               if (ex instanceof RuntimeException) 
               { 
                  throw (RuntimeException)ex;
               }
               throw new RuntimeException(ex);
            }
         }
      };

      UnregisterClassLoaderAction NON_PRIVILEGED = new UnregisterClassLoaderAction()
      {
         public void unregister(AOPClassPoolRepository repository, ClassLoader loader)
         {
            repository.doUnregisterClassLoader(loader);
         }
      };
   }
}
