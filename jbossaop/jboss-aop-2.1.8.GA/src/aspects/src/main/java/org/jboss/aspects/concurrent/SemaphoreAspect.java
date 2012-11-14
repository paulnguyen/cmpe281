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

import org.jboss.aop.joinpoint.Invocation;

import java.util.concurrent.Semaphore;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class SemaphoreAspect
{
   protected SemaphoreLocked props;

   public SemaphoreAspect(SemaphoreLocked locked)
   {
      this.props = locked;
   }

   public Object acquire(Invocation invocation) throws Throwable
   {
      SemaphoredObject obj = (SemaphoredObject)invocation.getTargetObject();

      if (props.timeout() == -1)
      {
            blockIndefinately(obj.getSemaphore());
      }
      else if (props.timeout() == 0)
      {
         tryLock(obj.getSemaphore());
      }
      else
      {
         tryLockWithTimeout(obj.getSemaphore());
      }

      try
      {
         return invocation.invokeNext();
      }
      finally
      {
         obj.getSemaphore().release(props.permits());
      }
   }

   protected void blockIndefinately(Semaphore semaphore)
   {
      try
      {
         semaphore.acquire(props.permits());
      }
      catch (InterruptedException e)
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class", e);
      }
   }

   protected void tryLock(Semaphore semaphore)
   {
      if (!semaphore.tryAcquire(props.permits()))
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class");
      }
   }

   protected void tryLockWithTimeout(Semaphore semaphore)
   {
      try
      {
         if (!semaphore.tryAcquire(props.timeout(), props.unit()))
         {
            throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class.  Timeout reached.");
         }
      }
      catch (InterruptedException e)
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class", e);
      }
   }
}
