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

import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;

import org.jboss.aop.util.ClassLoaderUtils;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class BaseClassPoolDomain extends AbstractClassPoolDomain
{
   private String domainName;
   
   private List<DelegatingClassPool> delegatingPools = new ArrayList<DelegatingClassPool>();
   
   private ParentDelegationStrategy parentDelegationStrategy;

   public BaseClassPoolDomain(String domainName, ClassPoolDomain parent, boolean parentFirst)
   {
      this(domainName, 
            new DefaultParentDelegationStrategy(
                  parent, 
                  parentFirst, 
                  DefaultClassPoolToClassPoolDomainAdaptorFactory.getInstance())
      );
   }
   
   protected BaseClassPoolDomain(String domainName, ParentDelegationStrategy parentDelegationStrategy)
   {
      this.domainName = domainName;
      this.parentDelegationStrategy = parentDelegationStrategy;
      parentDelegationStrategy.setDomain(this);
      if (logger.isTraceEnabled()) logger.trace("Created " + this + " parentDelegationStrategy:" + parentDelegationStrategy);
   }
   
   @Override
   synchronized void addClassPool(DelegatingClassPool pool)
   {
      if (!delegatingPools.contains(pool))
      {
         if (logger.isTraceEnabled()) logger.trace(this + " adding pool " + pool);
         delegatingPools.add(pool);
      }
   }
   
   @Override
   protected synchronized void removeClassPool(DelegatingClassPool pool)
   {
      if (logger.isTraceEnabled()) logger.trace(this + " removing pool " + pool);
      delegatingPools.remove(pool);
   }
   
   @Override
   synchronized CtClass getCachedOrCreate(DelegatingClassPool initiatingPool, String classname, boolean create)
   {
      boolean trace = logger.isTraceEnabled();
      String resourceName = ClassLoaderUtils.getResourceName(classname);
      
      CtClass clazz = getCachedOrCreate(initiatingPool, classname, resourceName, create, trace);
      
      if (clazz == null)
      {
         clazz = getCachedOrCreateFromPoolParent(initiatingPool, classname, create, trace);
      }
      return clazz;
   }
   
   @Override
   protected CtClass getCachedOrCreate(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace)
   {
      if (trace) logger.trace(this + " looking for " + classname);
         
      CtClass clazz = null;
      if (isParentBefore(classname))
      {
         if (trace) logger.trace(this + " checking parent first for " + classname);
         clazz = getCachedOrCreateFromParent(initiatingPool, classname, resourceName, create, trace);
      }
      if (clazz == null)
      {
         List<DelegatingClassPool> pools = getPoolsForClassName(classname);
         if (pools.size() > 0)
         {
            for (DelegatingClassPool pool : pools)
            {
               clazz = pool.loadLocally(classname, resourceName, create);
               if (clazz != null)
               {
                  break;
               }
            }
         }
      }
      if (clazz == null && isParentAfter(classname))
      {
         if (trace) logger.trace(this + " checking parent last for " + classname);
         clazz = getCachedOrCreateFromParent(initiatingPool, classname, resourceName, create, trace);
      }
      if (trace) logger.trace(this + " found " + classname + " in " + (clazz == null ? "null" : clazz.getClassPool()));
      return clazz;
   }

   @Override
   protected CtClass getCachedOrCreateFromParent(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace)
   {
      return parentDelegationStrategy.getCachedOrCreateFromParent(initiatingPool, classname, resourceName, create, trace);
   }
   
   public String toString()
   {
      return "[" + super.toString() + " name:" + domainName + "]";
   }

   protected boolean isParentBefore(String classname)
   {
      return parentDelegationStrategy.isParentBefore(classname);
   }
   
   protected boolean isParentAfter(String classname)
   {
      return parentDelegationStrategy.isParentAfter(classname);
   }
   
   protected List<DelegatingClassPool> getPoolsForClassName(String classname)
   {
      return delegatingPools;
   }
}
