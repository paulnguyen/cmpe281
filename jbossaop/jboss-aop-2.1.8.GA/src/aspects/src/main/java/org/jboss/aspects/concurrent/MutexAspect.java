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

import java.util.concurrent.locks.ReentrantLock;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class MutexAspect
{
   public static class BlockingMutex
   {
      protected MutexLocked props;

      public Object acquire(Invocation invocation) throws Throwable
      {
         MutexedObject obj = (MutexedObject) invocation.getTargetObject();
         ReentrantLock lock = obj.getMutex();
         try
         {
            lock.lockInterruptibly();
         }
         catch (InterruptedException e)
         {
            throw new LockAcquisitionFailureException("Failed to lock for @Mutexed class", e);
         }


         try
         {
            return invocation.invokeNext();
         }
         finally
         {
            lock.unlock();
         }
      }
   }

   public static class TryLockMutex
   {
      protected MutexLocked props;

      public Object acquire(Invocation invocation) throws Throwable
      {
         MutexedObject obj = (MutexedObject) invocation.getTargetObject();
         ReentrantLock lock = obj.getMutex();
         if (!lock.tryLock())
         {
            throw new LockAcquisitionFailureException("Failed to lock for @Mutexed class");
         }

         try
         {
            return invocation.invokeNext();
         }
         finally
         {
            lock.unlock();
         }
      }
   }


   public static class TimeoutMutex
   {
      protected MutexLocked props;

      public TimeoutMutex(MutexLocked props)
      {
         this.props = props;
      }

      public Object acquire(Invocation invocation) throws Throwable
      {
         MutexedObject obj = (MutexedObject) invocation.getTargetObject();
         ReentrantLock lock = obj.getMutex();
         try
         {
            if (!lock.tryLock(props.timeout(), props.unit()))
            {
               throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class.  Timeout reached.");
            }
         }
         catch (InterruptedException e)
         {
            throw new LockAcquisitionFailureException("Failed to acquire permit for @Semaphored class", e);
         }


         try
         {
            return invocation.invokeNext();
         }
         finally
         {
            lock.unlock();
         }
      }
   }


}
