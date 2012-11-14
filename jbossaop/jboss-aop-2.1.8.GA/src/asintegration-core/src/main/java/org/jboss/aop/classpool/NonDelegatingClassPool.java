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

import javassist.ClassPool;
import javassist.CtClass;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * ClassPool for class loaders not backed by a repository/classloading domain
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class NonDelegatingClassPool extends BaseClassPool
{
   public NonDelegatingClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, boolean parentFirst)
   {
      super(cl, src, repository, AOPClassPool.SEARCH_LOCAL_ONLY_STRATEGY);
      super.childFirstLookup = !parentFirst;
   }

   @Override
   public CtClass createCtClass(String classname, boolean useCache)
   {
      boolean trace = logger.isTraceEnabled();
      CtClass clazz = null;
      if (!childFirstLookup)
      {
         if (trace)logger.trace(this + " attempting to create " + classname + " in parent pool (parentFirst)");
         clazz = createParentCtClass(classname, useCache, trace);
      }
      if (clazz == null && isLocalResource(getResourceName(classname), trace))
      {
         clazz = super.createCtClass(classname, useCache);
      }
      if (childFirstLookup && clazz == null)
      {
         if (trace)logger.trace(this + " attempting to create " + classname + " in parent pool (parentLast)");
         clazz = createParentCtClass(classname, useCache, trace);
      }
      
      if (trace)logger.trace(this + " created " + classname + " " + getClassPoolLogStringForClass(clazz));
      return clazz;
   }
}
