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
import javassist.CtClass;
import javassist.NotFoundException;



/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractClassPoolDomain implements ClassPoolDomain
{
   protected final Logger logger = Logger.getLogger(this.getClass());
   
   abstract CtClass getCachedOrCreate(DelegatingClassPool initiatingPool, String classname, boolean create);
   
   abstract CtClass getCachedOrCreate(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace);
   
   abstract CtClass getCachedOrCreateFromParent(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace);
   
   abstract void addClassPool(DelegatingClassPool pool);
   
   abstract void removeClassPool(DelegatingClassPool pool);

   
   protected CtClass getCachedOrCreateFromPoolParent(BaseClassPool initiatingPool, String classname, boolean create, boolean trace)
   {
      if (initiatingPool == null)
      {
         if (trace) logger.trace(this + " get cached or create " + classname + " from pool parent - no initiating pool");
         return null;
      }
      ClassPool parentPool = initiatingPool.getParent();
      if (parentPool == null)
      {
         if (trace) logger.trace(this + " get cached or create " + classname + " from pool parent - no parent pool");
         return null;
      }
       
      return getCachedOrCreateFromPool(parentPool, classname, create, trace);
   }

   
   protected CtClass getCachedOrCreateFromPool(ClassPool pool, String classname, boolean create, boolean trace)
   {
      if (pool instanceof BaseClassPool)
      {
         return getCachedOrCreateFromPool((BaseClassPool)pool, classname, create, trace);
      }
      try
      {
         //This will check the parents
         if (trace) logger.trace(this + " get cached or create " + classname + " from non-BaseClassPool pool " + pool);
         CtClass clazz = pool.get(classname);
         if (trace) logger.trace(this + " got cached or create " + classname + " from non-BaseClassPool pool " + clazz.getClassPool());
         return clazz;
      }
      catch(NotFoundException e)
      {
         return null;
      }
   }

   protected CtClass getCachedOrCreateFromPool(BaseClassPool pool, String classname, boolean create, boolean trace)
   {
      if (pool == null)
      {
         if (trace) logger.trace(this + " get cached or create " + classname + " from BaseClassPool - no pool");
         return null;
      }
      
      CtClass clazz = null;
      if (!pool.childFirstLookup)
      {
         if (trace) logger.trace(this + " get cached or create " + classname + " from BaseClassPool - checking parent (parentFirst)");
         clazz = getCachedOrCreateFromPoolParent(pool, classname, create, trace); 
      }
      
      if (clazz == null)
      {
         //We can use the exposed methods directly to avoid the overhead of NotFoundException
         if (trace) logger.trace(this + " get cached or create " + classname + " from BaseClassPool - checking cache");
         clazz = pool.getCached(classname);
         if (clazz == null && create)
         {
            if (trace) logger.trace(this + " get cached or create " + classname + " from BaseClassPool - creating");
            clazz = pool.createCtClass(classname, true);
         }
      }
      
      if (clazz == null && pool.childFirstLookup)
      {
         if (trace) logger.trace(this + " get cached or create " + classname + " from BaseClassPool - checking parent (parentLast)");
         clazz = getCachedOrCreateFromPoolParent(pool, classname, create, trace); 
      }
      return clazz;
   }
}
