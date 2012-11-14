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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.classloading.spi.dependency.Module;
import org.jboss.logging.Logger;

/**
 * Use with org.jboss.aop.classpool.jbosscl.JBossClDelegatingClassPoolFactory
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class RegisterModuleCallback
{
   Logger logger = Logger.getLogger(this.getClass());
   
   private Set<Module> registeredModules = new HashSet<Module>();
   private Set<Module> unregisteredModules = new HashSet<Module>();

   public synchronized void addModule(Module module)
   {
      logger.debug("Adding module " + module);
      unregisteredModules.add(module);
   }

   public synchronized void removeModule(Module module)
   {
      logger.debug("Removing module " + module);
      registeredModules.remove(module);
      unregisteredModules.remove(module);
   }
   
   public synchronized Set<Module> getUnregisteredModules()
   {
      return Collections.unmodifiableSet(new HashSet<Module>(unregisteredModules));
   }
   
   public synchronized void registerModule(Module module)
   {
      logger.debug("Registering module " + module);
      registeredModules.add(module);
      unregisteredModules.remove(module);
   }
}
