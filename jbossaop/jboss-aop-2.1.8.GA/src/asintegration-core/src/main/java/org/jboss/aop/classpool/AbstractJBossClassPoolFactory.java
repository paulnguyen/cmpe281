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
package org.jboss.aop.classpool;

import org.jboss.logging.Logger;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractJBossClassPoolFactory implements ScopedClassPoolFactory
{
   protected final Logger log = Logger.getLogger(this.getClass().getName());
   
   protected ClassPool getCreateParentClassPools(final ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      //Make sure that we get classpools for all the parent classloaders
      if (cl == null)
      {
         return ClassPool.getDefault();
      }
      ClassLoader parent = SecurityActions.getParent(cl);

      if (parent != null)
      {
         return repository.registerClassLoader(parent);
      }
      return src;
   }
   
   public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository)
   {
      return new TempJBossClassPool(src, repository);
   }


}
