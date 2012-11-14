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

import org.jboss.aop.classpool.AOPClassPool;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * The temporary classpool used by the instrumentor. It's main job is to delegate to the parent classpool
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class TempJBossClassPool extends AOPClassPool
{
   boolean isParentPoolAOP;
   public TempJBossClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      super(cl, src, repository);
      if (src instanceof AOPClassPool)
      {
         isParentPoolAOP = true;
      }
   }

   public TempJBossClassPool(ClassPool src, ScopedClassPoolRepository repository)
   {
      super(src, repository);
      if (src instanceof AOPClassPool)
      {
         isParentPoolAOP = true;
      }
   }

   public CtClass getCached(String classname)
   {
      CtClass clazz = null;
      if (isParentPoolAOP)
      {
         clazz = ((AOPClassPool)parent).getCached(classname);
      }
      if (clazz == null)
      {
         clazz = super.getCached(classname);
      }
      return clazz;
   }
}
