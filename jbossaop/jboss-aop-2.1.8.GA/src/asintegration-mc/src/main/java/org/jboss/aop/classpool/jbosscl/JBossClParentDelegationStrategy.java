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
package org.jboss.aop.classpool.jbosscl;

import org.jboss.aop.classpool.AbstractParentDelegationStrategy;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolToClassPoolDomainAdaptorFactory;
import org.jboss.aop.util.ClassLoaderUtils;
import org.jboss.classloader.spi.ParentPolicy;
import org.jboss.classloader.spi.filter.ClassFilter;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
class JBossClParentDelegationStrategy extends AbstractParentDelegationStrategy
{
   ParentPolicy parentPolicy;
   
   JBossClParentDelegationStrategy(ClassPoolDomain parent, ParentPolicy parentPolicy, ClassPoolToClassPoolDomainAdaptorFactory adaptorFactory)
   {
      super(parent, adaptorFactory);
      if (parentPolicy == null)
      {
         throw new IllegalStateException("Null parent policy");
      }
      this.parentPolicy = parentPolicy;
   }

   public boolean isParentAfter(String classname)
   {
      ClassFilter filter = parentPolicy.getAfterFilter();
      boolean isParentAfter = filter.matchesClassName(ClassLoaderUtils.stripArrayFromClassName(classname));
      if (logger.isTraceEnabled()) logger.trace(this + " " + getDomain() + " isParentAfter " + isParentAfter);      
      return isParentAfter;
   }

   public boolean isParentBefore(String classname)
   {
      ClassFilter filter = parentPolicy.getBeforeFilter();
      boolean isParentBefore = filter.matchesClassName(ClassLoaderUtils.stripArrayFromClassName(classname));
      if (logger.isTraceEnabled()) logger.trace(this + " " + getDomain() + " isParentBefore " + isParentBefore);
      return isParentBefore;
   }
}
