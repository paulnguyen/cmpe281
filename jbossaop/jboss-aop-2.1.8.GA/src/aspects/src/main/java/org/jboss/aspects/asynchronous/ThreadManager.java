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

package org.jboss.aspects.asynchronous;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 37406 $</tt>
 */

public interface ThreadManager
{

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

   public abstract void setWaitWhenPoolSizeIsFull(boolean value);

   /**
    * return the policy when the pool is full
    */

   public abstract boolean getWaitWhenPoolSizeIsFull();

   /**
    * Set the maximum number of threads to use.
    */

   public abstract void setMaximumPoolSize(int maximumPoolSize);

   /**
    * Return the maximum number of threads to use.
    */

   public abstract int getMaximumPoolSize();

   /**
    * Set the minimum number of threads to use.
    */

   public abstract void setMinimumPoolSize(int minimumPoolSize);

   /**
    * Return the maximum number of threads to simultaneously execute
    */

   public abstract int getMinimumPoolSize();

   /**
    * Set the number of milliseconds to keep threads alive waiting for
    * <p/>
    * new commands. A negative value means to wait forever. A zero
    * <p/>
    * value means not to wait at all.
    */

   public abstract void setKeepAliveTime(long time);

   /**
    * Return the number of milliseconds to keep threads alive waiting
    * <p/>
    * for new commands. A negative value means to wait forever. A zero
    * <p/>
    * value means not to wait at all.
    */

   public abstract long getKeepAliveTime();

   /**
    * Return the current number of active threads in the pool.
    */

   public abstract long getPoolSize();

   /**
    * Return the response from an asynchronous task
    * <p/>
    * The call returns within the timeout defined
    * <p/>
    * in the process method
    */

   public abstract ThreadManagerResponse waitForResponse(AsynchronousTask input);

   /**
    * Return an array of responses from an array of asynchronous task
    * <p/>
    * The call returns within the maximum timeout from the array of tasks
    */

   public abstract ThreadManagerResponse[] waitForResponses(AsynchronousTask input[]);

   /**
    * Create, start and return a new asynchronous task from
    * <p/>
    * <p/>
    * <p/>
    * <p><b>ppmRequest</b> class instance defining the task parameters.
    */

   public abstract AsynchronousTask process(ThreadManagerRequest ppmRequest);


   public abstract boolean isPooling();

   public abstract void setPooling(boolean isPooling);

}
