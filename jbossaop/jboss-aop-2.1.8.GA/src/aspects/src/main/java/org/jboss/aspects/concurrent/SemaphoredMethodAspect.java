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
public class SemaphoredMethodAspect
{
   protected Semaphore semaphore;
   protected SemaphoredMethod props;

   public SemaphoredMethodAspect(SemaphoredMethod m)
   {
      props = m;
      semaphore = new Semaphore(m.permits(), m.isFair());
   }

   public Object acquire(Invocation invocation) throws Throwable
   {
      if (props.timeout() == -1)
      {
            blockIndefinately();
      }
      else if (props.timeout() == 0)
      {
         tryLock();
      }
      else
      {
         tryLockWithTimeout();
      }

      try
      {
         return invocation.invokeNext();
      }
      finally
      {
         semaphore.release();
      }
   }

   protected void blockIndefinately()
   {
      try
      {
         semaphore.acquire();
      }
      catch (InterruptedException e)
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @SemaphoredMethod", e);
      }
   }

   protected void tryLock()
   {
      if (!semaphore.tryAcquire())
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @SemaphoredMethod");
      }
   }

   protected void tryLockWithTimeout()
   {
      try
      {
         if (!semaphore.tryAcquire(props.permits(), props.timeout(), props.unit()))
         {
            throw new LockAcquisitionFailureException("Failed to acquire permit for @SemaphoredMethod.  Timeout reached.");
         }
      }
      catch (InterruptedException e)
      {
         throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class", e);
      }
   }
}
