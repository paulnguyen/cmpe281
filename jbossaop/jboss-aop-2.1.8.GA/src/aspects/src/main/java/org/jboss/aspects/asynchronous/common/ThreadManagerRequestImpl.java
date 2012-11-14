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

package org.jboss.aspects.asynchronous.common;

import org.jboss.aspects.asynchronous.AsynchronousParameters;
import org.jboss.aspects.asynchronous.AsynchronousUserTask;
import org.jboss.aspects.asynchronous.ThreadManagerRequest;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independant Consultant}</a>.
 * @version <tt>$Revision: 37406 $</tt>
 */

public class ThreadManagerRequestImpl implements ThreadManagerRequest
{

   protected AsynchronousUserTask taskClassImpl = null;

   protected AsynchronousParameters inputParameters = null;

   protected long timeout = 0;

   protected String id = null;

   public ThreadManagerRequestImpl() {}

   public ThreadManagerRequestImpl(String id,

                                   AsynchronousParameters inputParameters,

                                   AsynchronousUserTask taskClassImpl,

                                   long timeout)
   {

      this.inputParameters = inputParameters;

      this.taskClassImpl = taskClassImpl;

      this.timeout = timeout;

      this.id = id;

   }

   public void setInputParameters(AsynchronousParameters inputParameters)
   {

      this.inputParameters = inputParameters;

   }

   public AsynchronousParameters getInputParameters()
   {

      return inputParameters;

   }

   public void setTimeout(long timeout)
   {

      this.timeout = timeout;

   }

   public long getTimeout()
   {

      return timeout;

   }

   public String getId()
   {

      return id;

   }

   public void setId(String string)
   {

      id = string;

   }

   public AsynchronousUserTask getTaskClassImpl()
   {

      return taskClassImpl;

   }

   public void setTaskClassImpl(AsynchronousUserTask task)
   {

      taskClassImpl = task;

   }

}

