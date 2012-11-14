/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.aop;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.metadata.ClassMetaDataBinding;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class InstanceDomain extends Domain 
{
   protected Advisor advisor;

   public InstanceDomain(AspectManager manager, String name, boolean parentFirst)
   {
      super(manager, name, parentFirst);
      setInheritsBindings(true);
   }

   public Advisor getAdvisor()
   {
      return advisor;
   }

   public void setAdvisor(Advisor advisor)
   {
      this.advisor = advisor;
   }

   public void addBinding(AdviceBinding binding)
   {
      lock.lockWrite();
      try
      {
         removeBinding(binding.getName());
         bindingCollection.add(binding, this);
         super.addPointcut(binding.getPointcut());
         if (advisor != null) advisor.newBindingAdded();
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public void addClassMetaData(ClassMetaDataBinding meta)
   {
      removeClassMetaData(meta.getName());
      if (advisor != null)
      {
         if (meta.matches(advisor, advisor.getClazz()))
         {
            meta.addAdvisor(advisor);
         }
      }
   }

   /**
    * internal to jboss aop.  Do not call
    * <p/>
    * This is overriden so that AdviceBinding can clear an advisor that is created per-instance.
    *
    * @param advisor
    * @return
    */
   public boolean isAdvisorRegistered(Advisor advisor)
   {
      return advisor == advisor;
   }
}
