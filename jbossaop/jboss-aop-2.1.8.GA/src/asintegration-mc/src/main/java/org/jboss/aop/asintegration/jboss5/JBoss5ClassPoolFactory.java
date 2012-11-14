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

import java.net.URL;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AbstractJBossClassPoolFactory;
import org.jboss.classloader.spi.ClassLoaderDomain;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.RealClassLoader;
import org.jboss.classloading.spi.dependency.Module;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 64815 $
 **/
public class JBoss5ClassPoolFactory extends AbstractJBossClassPoolFactory implements ScopedClassPoolFactory
{
   private DomainRegistry registry;
   
   public JBoss5ClassPoolFactory(DomainRegistry registry)
   {
      this.registry = registry;
   }
   
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {      
      ClassPool parent = getCreateParentClassPools(cl, src, repository);

      ScopedClassPool pool = null;
      
      if (cl instanceof RealClassLoader)
      {
         Module module = registry.getModule(cl);
         if (module != null && module.getDeterminedParentDomainName() != null)
         {
            //It is scoped
            ClassLoaderSystem sys = registry.getSystem();
            ClassLoaderDomain domain = sys.getDomain(module.getDeterminedDomainName());
            boolean parentFirst = module.isJ2seClassLoadingCompliance();
            ClassPool parentDomainPool = getParentUnitClassPool(cl); 
            pool = new ScopedJBoss5ClassPool(cl, parent, parentDomainPool, repository, getTempURL(module), parentFirst, domain);
         }
         else
         {
            pool =  new JBoss5ClassPool(cl, parent, repository, getTempURL(module));
         }
      }
      
      if (pool == null)
      {
         pool = new AOPClassPool(cl, parent, repository);
      }
      log.debug("Created pool " + pool + " for loader " + cl);
      
      return pool;
   }

   private ClassPool getParentUnitClassPool(ClassLoader cl)
   {
      ClassLoader parent = registry.getParentUnitLoader(cl);
      return AspectManager.instance().registerClassLoader(parent);
   }
   

   private URL getTempURL(Module module)
   {
      if (module == null)
         return null;
      
      return module.getDynamicClassRoot();
   }
}
