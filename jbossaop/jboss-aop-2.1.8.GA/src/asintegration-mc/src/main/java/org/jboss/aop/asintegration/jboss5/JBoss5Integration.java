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

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.JBossIntegration;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.RealClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBoss5Integration implements JBossIntegration, ScopedClassPoolFactory
{
   /** The delegate classpool factory */
   private ScopedClassPoolFactory delegateClassPoolFactory;
   
   private AOPClassLoaderScopingPolicyWithRegistry policy;
 
   
   private static final Set<ClassLoader> bootstrapLoaders;
   static 
   {
      final Set<ClassLoader> loaders = new HashSet<ClassLoader>();
      
      AccessController.doPrivileged(new PrivilegedAction<Object>() {

         public Object run()
         {
            ClassLoader loader = JBoss5Integration.class.getClassLoader();
            while (loader != null)
            {
               loaders.add(loader);
               loader = loader.getParent();
            }
            return null;
         }});
      
      bootstrapLoaders = Collections.unmodifiableSet(loaders);
   }

   public void start()
   {
   }

   public void stop()
   {
   }
   
   public boolean isValidClassLoader(ClassLoader loader)
   {
      if(loader instanceof RealClassLoader)
      {
         if (((RealClassLoader) loader).isValid())
         {
            return true;
         }
      }
      
      if (loader == null)
      {
         //Bootstrap classloader
         return true;
      }
      
      if (bootstrapLoaders.contains(loader))
      {
         return true;
      }
      
      return false;
   }

   public void setAopClassLoaderScopingPolicy(AOPClassLoaderScopingPolicyWithRegistry policy)
   {
      this.policy = policy;
   }
   
   public synchronized AOPClassLoaderScopingPolicyWithRegistry createAOPClassLoaderScopingPolicy()
   {
      if (policy == null)
      {
         //AS versions after 5.0.0 will want to configure the AOPClassLoaderScopingPolicy themselves
         //Create a default one for AS 5.0.0
         policy = new VFSClassLoaderScopingPolicy();
      }
      return policy;
   }
   
   public DomainRegistry getDomainRegistry()
   {
      return policy.getRegistry();
   }
   
   public void setClassPoolFactory(ScopedClassPoolFactory factory)
   {
      this.delegateClassPoolFactory = factory;
   }

   public synchronized ScopedClassPoolFactory createScopedClassPoolFactory(File tmpDir) throws Exception
   {
      if (delegateClassPoolFactory == null)
      {
         delegateClassPoolFactory = new JBoss5ClassPoolFactory(getDomainRegistry());
      }
      return this;
   }
   
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      return delegateClassPoolFactory.create(cl, src, repository);
   }

   public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository)
   {
      return delegateClassPoolFactory.create(src, repository);
   }
   
   public void attachDeprecatedTranslator()
   {
      ClassLoaderSystem.getInstance().setTranslator(new DefaultTranslator(AspectManager.getTopLevelAspectManager()));      
   }

   public void detachDeprecatedTranslator()
   {
      ClassLoaderSystem.getInstance().setTranslator(null);
   }
}
