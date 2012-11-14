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

import org.jboss.aop.util.ClassLoaderUtils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * Base class for classpools backed by a domain
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class DelegatingClassPool extends BaseClassPool
{
   private final AbstractClassPoolDomain domain;
   
   private volatile boolean closed;
   
   public DelegatingClassPool(ClassPoolDomain domain, ClassLoader cl, ClassPool parent, ScopedClassPoolRepository repository)
   {
      super(cl, parent, repository);
      if (domain == null)
      {
         throw new IllegalArgumentException("Domain was null");
      }
      if (domain instanceof AbstractClassPoolDomain == false)
      {
         throw new IllegalArgumentException("Domain must implement AbstractClassPoolDomain");
      }
      this.domain = (AbstractClassPoolDomain)domain;
      if (logger.isTraceEnabled()) logger.trace(this + " domain:" + this);
      this.domain.addClassPool(this);
   }

   public CtClass loadLocally(String classname, String resourceName, boolean create)
   {
      boolean trace = logger.isTraceEnabled();
      if (trace) logger.trace(this + " attempt to load locally " + classname);
         
      CtClass clazz = null;
      if (isLocalResource(resourceName, trace))
      {
         clazz = getCachedLocally(classname);
         if (clazz == null && create)
         {
            clazz = createCtClass(classname, true);
         }
      }
      if (trace) logger.trace(this + " loaded locally " + classname + " " + getClassPoolLogStringForClass(clazz));
      return clazz;
   }
   
   /**
    * Overrides ClassPool.get0() so that we can look up classes without caching them in the initiating pool.
    * The DelgatingClassPool + DomainClassPool handle the caching in the correct pool + handles the 
    * parentFirst functionality
    */
   @Override
   public synchronized CtClass get0(String classname, boolean useCache) throws NotFoundException
   {
      if (isGeneratedClass(classname))
      {
         return null;
      }
      CtClass clazz = domain.getCachedOrCreate(this, classname, true);
      if (clazz == null)
      {
         //It is probably a generated proxy. The package name can be different (for example if it is
         //a proxy for a java system class) than the package names exported by the loaders.
         //TODO It should be possible to remove this once these have been done:
         //https://jira.jboss.org/jira/browse/JBCL-25
         //https://jira.jboss.org/jira/browse/JBCL-92
         clazz = loadLocally(classname, ClassLoaderUtils.getResourceName(classname), true);
      }
      return clazz;
   }
   
   @Override
   public boolean isUnloadedClassLoader()
   {
      return closed;
   }

   @Override
   public void close()
   {
      closed = true;
      super.close();
      domain.removeClassPool(this);
   }

   @Override
   public CtClass getCached(String classname)
   {
      //TODO Not 100 sure this is correct, but it seems to do the job where repeated calls to get() on a pool in a hierarchy returns a different instance of the class
      CtClass clazz = super.getCachedLocally(classname);
      if (clazz != null)
         return clazz;  
      
      if (isGeneratedClass(classname))
      {
         return null;
      }
      return domain.getCachedOrCreate(this, classname, false);
   }
   
   @Override
   public String toString()
   {
      return "[" + super.toString() + " domain: " + domain + "]";
   }
}
