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

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jboss.aspects.asynchronous.ProcessingTime;


/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 71280 $</tt>
 */
@SuppressWarnings("unchecked")
public class TimedCallableImpl implements Callable, ProcessingTime
{

   private final AdapterTask function;

   private final long millis;

   private long endingTime = -1;

   private long startingTime = -1;


   public TimedCallableImpl(AdapterTask function, long millis)
   {

      this.function = function;

      this.millis = millis;

   }

   public Object call() throws Exception
   {

      FutureTask result = new FutureTask(function);


      Thread thread = Executors.defaultThreadFactory().newThread(result);

      thread.start();

      try
      {

         startingTime = System.currentTimeMillis();

         Object obj = result.get(millis, TimeUnit.MILLISECONDS);

         endingTime = System.currentTimeMillis();

         return obj;

      }
      catch(TimeoutException ex)
      {

         endingTime = System.currentTimeMillis();

         function.cleanup();

         thread.interrupt();

         throw ex;

      }
      
      

   }


   public long getEndingTime()
   {

      return endingTime;

   }

   public long getStartingTime()
   {

      return startingTime;

   }

}

