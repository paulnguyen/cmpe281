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

import java.util.HashMap;
import java.util.Map;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.classpool.ExtraClassPoolFactoryParameters;
import org.jboss.classloading.spi.dependency.Module;
import org.jboss.logging.Logger;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class VFSClassLoaderScopingPolicy implements AOPClassLoaderScopingPolicyWithRegistry
{
   static Logger log = Logger.getLogger(VFSClassLoaderScopingPolicy.class);
   DomainRegistry registry = new VFSClassLoaderDomainRegistry();
   
   public DomainRegistry getRegistry()
   {
      return registry;
   }

   public void registerClassLoader(Module module, ClassLoader loader)
   {
      //Need to pass some data through to the classpoolfactory here
      Map<Object, Object> properties = new HashMap<Object, Object>();
      //The module is needed by the JBoss5ClassPoolFactory, the legacy JBossClassPoolFactory will ignore this
      properties.put(Module.class, module);
      ExtraClassPoolFactoryParameters.pushThreadProperties(properties);
      try
      {
         AspectManager.instance().registerClassLoader(loader); //Ends up in classpool factory create method
      }
      finally
      {
         ExtraClassPoolFactoryParameters.popThreadProperties();
      }
   }
   
   public Domain getDomain(ClassLoader classLoader, AspectManager parent)
   {
      //Check the stored domains
      Domain domain = registry.getRegisteredDomain(classLoader);
      if (domain != null)
      {
         return domain;
      }
      
      return null;
   }

   public Domain getTopLevelDomain(AspectManager parent)
   {
      Thread.currentThread().getContextClassLoader();
      return null;
   }
}
   
