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
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * Intermediate class containing commonly needed functionality for the new classpools. I don't want to
 * modify AOPClassPool too much
 *  
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class BaseClassPool extends AOPClassPool
{
   private IsLocalResourcePlugin isLocalResourcePlugin;
   
   public BaseClassPool(ClassLoader cl, ClassPool parent, ScopedClassPoolRepository repository)
   {
      super(cl, parent, repository);
      isLocalResourcePlugin = IsLocalResourcePluginFactoryRegistry.getPluginFactory(cl).create(this);
      if (logger.isTraceEnabled()) logger.trace(this + " isLocalResourcePlugin:" + isLocalResourcePlugin);
   }

   public BaseClassPool(ClassLoader cl, ClassPool parent, ScopedClassPoolRepository repository, Class<? extends AOPCLassPoolSearchStrategy> searchStrategy)
   {
      super(cl, parent, repository, searchStrategy);
      isLocalResourcePlugin = IsLocalResourcePluginFactoryRegistry.getPluginFactory(cl).create(this);
      if (logger.isTraceEnabled()) logger.trace(this + " isLocalResourcePlugin:" + isLocalResourcePlugin);
   }
   
   @Override
   public CtClass createCtClass(String classname, boolean useCache)
   {
      CtClass clazz = super.createCtClass(classname, useCache);
      if (clazz != null)
      {
         lockInCache(clazz);
      }
      return clazz;
   }

   protected CtClass createParentCtClass(String classname, boolean useCache, boolean trace)
   {
      if (parent == null)
      {
         return null;
      }

      //Make parent create class
      if (parent instanceof BaseClassPool)
      {
         return ((AOPClassPool)parent).createCtClass(classname, useCache);
      }
      else
      {
         return plainParentGet(classname);
      }
   }
   
   private CtClass plainParentGet(String classname)
   {
      try
      {
         return parent.get(classname);
      }
      catch (NotFoundException e)
      {
      }
      return null;
   }

   public ClassPool getParent()
   {
      return parent;
   }
   
   @Override
   protected boolean isLocalClassLoaderResource(String classResourceName)
   {
      return isLocalResourcePlugin.isMyResource(classResourceName);
   }

   @Override
   public final CtClass get(String classname) throws NotFoundException 
   {
      boolean trace = logger.isTraceEnabled();
      if (trace) logger.trace(this + " initiating get of " + classname);

      CtClass clazz = super.get(classname);
      if (trace)
      {
         logger.trace(this + " completed get of " + classname + " " + getClassPoolLogStringForClass(clazz));
      }
      return clazz;
   }
   
   @Override
   public CtClass get0(String classname, boolean useCache) throws NotFoundException
   {
      return super.get0(classname, useCache);
   }
   
   @Override
   public void close()
   {
      super.close();

      if (logger.isTraceEnabled()) logger.trace(this + " closing");
   }

}
