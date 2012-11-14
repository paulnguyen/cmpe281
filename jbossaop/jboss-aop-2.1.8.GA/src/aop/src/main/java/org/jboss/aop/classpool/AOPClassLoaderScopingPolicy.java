/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;

/**
 * AOPClassLoaderScopingPolicy.<p>
 * 
 * This is a temporary abstraction to replace AOPScopedClassLoaderHelper
 * until a better deployment/context resolution mechanism is used rather
 * than using the classloader to decide things.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface AOPClassLoaderScopingPolicy
{
   /**
    * Get the domain for classloader
    * 
    * @param classLoader the classloader
    * @param parent the parent (isn't this always the top level aspect manager?)
    * @return any scoped domain or the null if not scoped
    */
   Domain getDomain(ClassLoader classLoader, AspectManager parent);

   /**
    * Get the top level domain
    * 
    * @param parent the parent (isn't this always the top level aspect manager?)
    * @return any scoped domain or the null if not scoped
    */
   Domain getTopLevelDomain(AspectManager parent);
}
