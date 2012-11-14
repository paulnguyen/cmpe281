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
package org.jboss.aspects.concurrent;

import org.jboss.aop.advice.AspectFactory;
import org.jboss.aop.Advisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.joinpoint.MethodJoinpoint;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class MutexAspectFactory implements AspectFactory
{
   public Object createPerVM()
   {
      throw new IllegalStateException("THIS SCOPE NOT USABLE");
   }

   public Object createPerClass(Advisor advisor)
   {
      throw new IllegalStateException("THIS SCOPE NOT USABLE");
   }

   public Object createPerInstance(Advisor advisor, InstanceAdvisor instanceAdvisor)
   {
      throw new IllegalStateException("THIS SCOPE NOT USABLE");
   }

   public Object createPerJoinpoint(Advisor advisor, Joinpoint jp)
   {
      MethodJoinpoint mj = (MethodJoinpoint)jp;
      MutexLocked props = advisor.resolveTypedAnnotation(mj.getMethod(), MutexLocked.class);
      if (props.timeout() < 0)
      {
         return new MutexAspect.BlockingMutex();
      }
      else if (props.timeout() == 0)
      {
         return new MutexAspect.TryLockMutex();
      }
      else
      {
         return new MutexAspect.TimeoutMutex(props);
      }
   }

   public Object createPerJoinpoint(Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp)
   {
      throw new IllegalStateException("THIS SCOPE NOT USABLE");
   }

   public String getName()
   {
      return MutexAspectFactory.class.getName();
   }
}
