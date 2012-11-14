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
package org.jboss.aop.asintegration;

import java.io.File;

import javassist.scopedpool.ScopedClassPoolFactory;

import org.jboss.aop.ClassLoaderValidation;
import org.jboss.aop.classpool.AOPClassLoaderScopingPolicy;

/**
 * AOPIntegration.<p>
 * 
 * This class is intended to identify all the integration
 * points AOP is making with the JBoss appserver.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface JBossIntegration extends ClassLoaderValidation
{
   /**
    * Create the AOPClassLoaderScopingPolicy
    * 
    * @return the policy
    */
   AOPClassLoaderScopingPolicy createAOPClassLoaderScopingPolicy();

   /**
    * Create a scoped classpool factory
    *
    * TODO JBAOP-107 need to review whether ScopedClassPool should also be replaced with
    *      some other policy, e.g. javassist ClassPath notion is closer to new classloader?
    * @param tmpDir the temporary directory for classes
    * @return the factory
    * @throws Exception for any error
    */
   ScopedClassPoolFactory createScopedClassPoolFactory(File tmpDir) throws Exception;
   
   /**
    * Attach the depreacted translator
    */
   void attachDeprecatedTranslator();

   /**
    * Detach the deprecated translator
    */
   void detachDeprecatedTranslator();
}
