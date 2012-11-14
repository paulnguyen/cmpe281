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
import java.security.PrivilegedAction;
import java.util.Set;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.jboss5.DomainRegistry;
import org.jboss.aop.asintegration.jboss5.RegisterModuleCallback;
import org.jboss.aop.classpool.AbstractJBossDelegatingClassPoolFactory;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolDomainRegistry;
import org.jboss.aop.classpool.ClassPoolToClassPoolDomainAdapter;
import org.jboss.aop.classpool.NonDelegatingClassPool;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.RealClassLoader;
import org.jboss.classloading.spi.dependency.ClassLoading;
import org.jboss.classloading.spi.dependency.Module;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossClDelegatingClassPoolFactory extends AbstractJBossDelegatingClassPoolFactory implements ScopedClassPoolFactory
{
   private final DomainRegistry registry;
   
   private final RegisterModuleCallback registerModuleCallback;
   
   public JBossClDelegatingClassPoolFactory(DomainRegistry registry, RegisterModuleCallback registerModuleCallback)
   {
      this.registry = registry;
      this.registerModuleCallback = registerModuleCallback;
   }
   
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      ClassPool parent = getCreateParentClassPools(cl, src, repository);
      
      if (cl instanceof RealClassLoader)
      {
         Module module = registry.getModule(cl);
         if (module == null)
         {
            module = getModuleForClassLoader(cl);
         }
         registerModuleCallback.registerModule(module);
         registerBootstrapLoaders();
         ClassPoolDomain domain = getDomain(module, cl);  
         return new JBossClDelegatingClassPool(domain, cl, parent, repository, module, registerModuleCallback);
      }
      
      return new NonDelegatingClassPool(cl, parent, repository, true);
   }

   private synchronized ClassPoolDomain getDomain(Module module, ClassLoader cl)
   {
      ClassLoaderDomain domain = null;
      ClassLoaderSystem sys = registry.getSystem();
      if (module != null && module.getDeterminedParentDomainName() != null)
      {
         //It is scoped
         domain = sys.getDomain(module.getDeterminedDomainName());
      }
      
      if (domain == null)
      {
         domain = registry.getDefaultDomain();
      }
      
      ClassPoolDomain poolDomain = ClassPoolDomainRegistry.getInstance().getDomain(domain);
      if (poolDomain == null)
      {
         String parentDomainName = domain.getParentDomainName();
         ClassPoolDomain parentPoolDomain = null;
         if (parentDomainName != null)
         {
            ClassLoaderDomain parentDomain = sys.getDomain(parentDomainName);
            if (parentDomain == null)
            {
               throw new RuntimeException("No domain found called: " + parentDomainName);
            }
            parentPoolDomain = ClassPoolDomainRegistry.getInstance().getDomain(parentDomain);
         }
         if (parentDomainName == null)
         {
            ClassLoader parentUnitLoader = registry.getParentUnitLoader(cl);
            if (parentUnitLoader != null)
            {
               ClassPool parentUnitPool = AspectManager.getTopLevelAspectManager().registerClassLoader(parentUnitLoader);
               parentPoolDomain = new ClassPoolToClassPoolDomainAdapter(parentUnitPool);
            }
         }
         poolDomain = new JBossClClassPoolDomain(domain.getName(), parentPoolDomain, domain.getParentPolicy(), registry);
         
         ClassPoolDomainRegistry.getInstance().addClassPoolDomain(domain, poolDomain);
      }
      return poolDomain;
   }
   
   @Override
   protected ClassPool getCreateParentClassPools(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      ClassPool parent = super.getCreateParentClassPools(cl, src, repository);
      if (parent == ClassPool.getDefault())
      {
         //In AS BaseClassLoader seems to normally have a null parent
         return null;
      }
      return parent;
   }
   
   private void registerBootstrapLoaders()
   {
      Set<Module> unregistered = registerModuleCallback.getUnregisteredModules();
      if (unregistered.size() > 0)
      {
         for (Module module : unregistered)
         {
            ClassLoader loader = getClassLoaderForModule(module);
            log.debug("Registering loader for module " + module + ": " + loader);
            AspectManager.instance().registerClassLoader(loader);
         }
      }
   }
   
   private ClassLoader getClassLoaderForModule(final Module module)
   {
      return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>()
      {
      
         public ClassLoader run()
         {
            return ClassLoading.getClassLoaderForModule(module);
         }
      });
   }
   
   private Module getModuleForClassLoader(final ClassLoader classLoader)
   {
      return AccessController.doPrivileged(new PrivilegedAction<Module>()
      {
         public Module run()
         {
            return ClassLoading.getModuleForClassLoader(classLoader);
         }
      });
   }
}
