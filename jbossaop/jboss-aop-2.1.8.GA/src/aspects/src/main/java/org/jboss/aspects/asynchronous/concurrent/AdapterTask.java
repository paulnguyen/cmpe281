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

import org.jboss.aspects.asynchronous.AsynchronousConstants;
import org.jboss.aspects.asynchronous.AsynchronousParameters;
import org.jboss.aspects.asynchronous.AsynchronousResponse;
import org.jboss.aspects.asynchronous.AsynchronousUserTask;
import org.jboss.aspects.asynchronous.ProcessingTime;
import org.jboss.aspects.asynchronous.ThreadManagerResponse;
import org.jboss.aspects.asynchronous.common.ThreadManagerResponseImpl;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 71280 $</tt>
 */

@SuppressWarnings("unchecked")
final class AdapterTask

implements  Callable, AsynchronousConstants, ProcessingTime
{

   private AsynchronousParameters _inputParametersImpl = null;

   private AsynchronousUserTask _taskImpl = null;

   private String id = null;

   private long startingTime = -1;

   private long endingTime = -1;

   AdapterTask(String id,

               AsynchronousParameters inputParametersImpl,

               AsynchronousUserTask taskImpl)
   {

      _inputParametersImpl = inputParametersImpl;

      _taskImpl = taskImpl;

      this.id = id;

   }

   public void cleanup()
   {

      _taskImpl.cleanup(_inputParametersImpl);

   }

   public java.lang.Object call()
   {

      try
      {

         startingTime = System.currentTimeMillis();

         AsynchronousResponse taskResult =

         _taskImpl.process(_inputParametersImpl);

         endingTime = System.currentTimeMillis();

         ThreadManagerResponse myResult =

         new ThreadManagerResponseImpl(getId(),

         OK,

         null,

         taskResult,

         startingTime,

         endingTime);

         return myResult;

      }
      catch (Exception e)
      {

         try
         {

            endingTime = System.currentTimeMillis();

            return new ThreadManagerResponseImpl(getId(),

            UNKNOWN,

            e.getMessage(),

            e);

         }
         catch (Exception ee)
         {

            endingTime = System.currentTimeMillis();

            return new ThreadManagerResponseImpl(getId(),

            UNKNOWN,

            e.getMessage(),

            ee);

         }

      }

   }

   public String getId()
   {

      return id;

   }

   public long getStartingTime()
   {

      return startingTime;

   }

   public long getEndingTime()
   {

      return endingTime;

   }

}

