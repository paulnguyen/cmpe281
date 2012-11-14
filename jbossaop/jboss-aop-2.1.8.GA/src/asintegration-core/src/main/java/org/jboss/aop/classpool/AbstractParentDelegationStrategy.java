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

import javassist.CtClass;

import org.jboss.logging.Logger;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractParentDelegationStrategy implements ParentDelegationStrategy
{
   protected final Logger logger = Logger.getLogger(this.getClass().getName());
   private AbstractClassPoolDomain domain;
   private AbstractClassPoolDomain parent;
   
   protected AbstractParentDelegationStrategy(ClassPoolDomain parent, ClassPoolToClassPoolDomainAdaptorFactory adaptorFactory)
   {
      if (parent == null)
      {
         if (adaptorFactory == null)
         {
            throw new IllegalStateException("Null parent and null adaptorFactory");
         }
         this.parent = adaptorFactory.createAdaptor();
      }
      else 
      {
         if (parent instanceof AbstractClassPoolDomain == false)
         {
            throw new IllegalArgumentException("Parent must implement AbstractClassPoolDomain");         
         }
         this.parent = (AbstractClassPoolDomain)parent;
      }
      
      if (this.parent == null)
      {
         throw new IllegalStateException("Parent was not set");
      }
   }
   
   protected boolean hasParent()
   {
      if (logger.isTraceEnabled()) logger.trace(this + " " + getDomain() + " hasParent " + parent != null);
      return parent != null;
   }
   
   protected AbstractClassPoolDomain getDomain()
   {
      return domain;
   }
   
   public CtClass getCachedOrCreateFromParent(DelegatingClassPool initiatingPool, String classname, String resourceName, boolean create, boolean trace)
   {
      return parent.getCachedOrCreate(initiatingPool, classname, resourceName, create, trace);
   }
   
   public void setDomain(AbstractClassPoolDomain domain)
   {
      if (domain == null)
      {
         throw new IllegalArgumentException("Null domain");
      }
      if (this.domain != null)
      {
         throw new IllegalArgumentException("Cannot change domain");
      }
      this.domain = domain;
   }
}
