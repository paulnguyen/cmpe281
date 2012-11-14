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

package org.jboss.aspects.asynchronous.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.aspects.asynchronous.AsynchronousConstants;
import org.jboss.aspects.asynchronous.AsynchronousParameters;
import org.jboss.aspects.asynchronous.AsynchronousTask;
import org.jboss.aspects.asynchronous.AsynchronousUserTask;
import org.jboss.aspects.asynchronous.ThreadManager;
import org.jboss.aspects.asynchronous.ThreadManagerRequest;
import org.jboss.aspects.asynchronous.ThreadManagerResponse;
import org.jboss.aspects.asynchronous.common.AsynchronousEmptyTask;
import org.jboss.aspects.asynchronous.common.ThreadManagerResponseImpl;


/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 63393 $</tt>
 */

public class ThreadManagerImpl
implements AsynchronousConstants, ThreadManager
{

   ThreadPoolExecutor _pooledExecutor;

   protected boolean waitWhenPoolSizeIsFull = true;

   protected boolean isPooling = true;

   /**
    * Create a new pool with all default settings
    */

   public ThreadManagerImpl()
   {

      _pooledExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool();

      setWaitWhenPoolSizeIsFull(false);

   }

   /**
    * Create a new pool with all default settings except
    * <p/>
    * for maximum pool size.
    */

   public ThreadManagerImpl(int maximumPoolSize)
   {

      _pooledExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(maximumPoolSize);

      setWaitWhenPoolSizeIsFull(false);

   }

   /**
    * Set the minimum number of threads to use.
    *
    * @throws IllegalArgumentException if less than zero. (It is not
    *                                  <p/>
    *                                  considered an error to set the minimum to be greater than the
    *                                  <p/>
    *                                  maximum. However, in this case there are no guarantees about
    *                                  <p/>
    *                                  behavior.)
    */

   public void setMaximumPoolSize(int maximumPoolSize)
   {

      _pooledExecutor.setMaximumPoolSize(maximumPoolSize);

   }

   /**
    * Set the policy for blocked execution to be to wait until a thread
    * <p/>
    * is available.
    * <p/>
    * <p/>
    * <p/>
    * OR
    * <p/>
    * <p/>
    * <p/>
    * Set the policy for blocked execution to be to
    * <p/>
    * throw a RuntimeException.
    */

   public void setWaitWhenPoolSizeIsFull(boolean value)
   {

      if (value)

         _pooledExecutor.setRejectedExecutionHandler(new WaitWhenBlockedPolicy());

      else

         _pooledExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

      waitWhenPoolSizeIsFull = value;

   }

   /**
    * return the policy when the pool is full
    */

   public boolean getWaitWhenPoolSizeIsFull()
   {

      return waitWhenPoolSizeIsFull;

   }

   /**
    * Return the maximum number of threads to simultaneously execute
    */

   public int getMaximumPoolSize()
   {

      return _pooledExecutor.getMaximumPoolSize();

   }

   /**
    * Set the minimum number of threads to use.
    *
    * @throws IllegalArgumentException if less than zero. (It is not
    *                                  <p/>
    *                                  considered an error to set the minimum to be greater than the
    *                                  <p/>
    *                                  maximum. However, in this case there are no guarantees about
    *                                  <p/>
    *                                  behavior.)
    */

   public void setMinimumPoolSize(int minimumPoolSize)
   {

      _pooledExecutor.setCorePoolSize(minimumPoolSize);

   }

   /**
    * Return the minimum number of threads to simultaneously execute.
    * <p/>
    * (Default value is 1).  If fewer than the mininum number are
    * <p/>
    * running upon reception of a new request, a new thread is started
    * <p/>
    * to handle this request.
    */

   public int getMinimumPoolSize()
   {

      return _pooledExecutor.getCorePoolSize();

   }

   /**
    * Set the number of milliseconds to keep threads alive waiting for
    * <p/>
    * new commands. A negative value means to wait forever. A zero
    * <p/>
    * value means not to wait at all.
    */

   public void setKeepAliveTime(long time)
   {

      _pooledExecutor.setKeepAliveTime(time, TimeUnit.MILLISECONDS);

   }

   /**
    * Return the number of milliseconds to keep threads alive waiting
    * <p/>
    * for new commands. A negative value means to wait forever. A zero
    * <p/>
    * value means not to wait at all.
    */

   public long getKeepAliveTime()
   {

      return _pooledExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS);

   }

   /**
    * Return the current number of active threads in the pool.  This
    * <p/>
    * number is just a snaphot, and may change immediately upon
    * <p/>
    * returning
    */

   public long getPoolSize()
   {

      return _pooledExecutor.getPoolSize();

   }

   /**
    * Return the response from an asynchronous task
    * <p/>
    * The call returns within the timeout defined
    * <p/>
    * in the process method
    */

   public ThreadManagerResponse waitForResponse(AsynchronousTask input)
   {

      AsynchronousTask[] tTask = {input};

      return waitForResponses(tTask)[0];

   }

   /**
    * Return an array of responses from an array of asynchronous task
    * <p/>
    * The call returns within the maximum timeout from the array of tasks
    */

   public ThreadManagerResponse[] waitForResponses(AsynchronousTask[] inputImpl)
   {

      if (inputImpl == null)
      {

         System.err.println("PPMImpl:waitForResponses NULL PARAMETER");

         return null;

      }

      ThreadManagerResponse[] response =

      new ThreadManagerResponseImpl[inputImpl.length];

      for (int i = 0; i < inputImpl.length; i++)
      {

         AsynchronousTask fr = inputImpl[i];

         response[i] = fr.getResponse();

      }

      return response;

   }

   public AsynchronousTask process(ThreadManagerRequest ppmRequest)
   {

      return process(ppmRequest.getId(),

      ppmRequest.getTaskClassImpl(),

      ppmRequest.getInputParameters(),

      ppmRequest.getTimeout());

   }

   /**
    * Create, start and return a new asynchronous task from :
    * <p/>
    * <p/>
    * <p/>
    * <p><b>taskImpl</b> class instance defining the task to process
    * <p/>
    * <p><b>inputParametersImpl</b> class instance defining the input parameters
    * <p/>
    * <p><b>timeout</b> defined the given time limit to process the task
    */

   private AsynchronousTask process(String id,

                                    AsynchronousUserTask taskImpl,

                                    AsynchronousParameters inputParametersImpl,

                                    long timeout)
   {

      try
      {


         if (this.getPoolSize() + 1 > this.getMaximumPoolSize())

            System.err.println("process: The maximum pool size defined at "

            + this.getMaximumPoolSize()

            + " is reached before processing task["

            + id

            + "] !");

         org.jboss.aspects.asynchronous.concurrent.AsynchronousTask ft =

         new AsynchronousTaskImpl(id,

         taskImpl,

         inputParametersImpl,

         timeout);

         Runnable cmd = ft.add();

         if (isPooling())

            _pooledExecutor.execute(cmd);

         else
         {

            Thread thread = Executors.defaultThreadFactory().newThread(cmd);

            thread.start();

         }

         Thread.yield();

         Thread.sleep(0);

         Thread.yield();

         return ft;

      }
      catch (Exception e)
      {


         return new AsynchronousEmptyTask(id,

         AsynchronousConstants.CAN_NOT_PROCESS,

         e,

         e.getMessage(),

         System.currentTimeMillis());

      }

   }

   public boolean isPooling()
   {

      return isPooling;

   }

   public void setPooling(boolean isPooling)
   {

      this.isPooling = isPooling;

   }

   private static class WaitWhenBlockedPolicy implements RejectedExecutionHandler
   {
      public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) throws RejectedExecutionException 
      {
         try 
         {
            executor.getQueue().put(r);
         }
         catch (InterruptedException e) 
         {
            throw new RejectedExecutionException(e);
         }
      }
   }
}

