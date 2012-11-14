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

import org.jboss.aspects.asynchronous.AsynchronousTask;
import org.jboss.aspects.asynchronous.ThreadManagerResponse;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 37406 $</tt>
 */

public class AsynchronousEmptyTask implements AsynchronousTask
{

   protected String id;

   protected ThreadManagerResponse threadManagerResponse;

   public AsynchronousEmptyTask(String id,

                                int code,

                                Object e,

                                String reason,

                                long startingTime)
   {

      this.id = id;

      threadManagerResponse =

      new ThreadManagerResponseImpl(id,

      code,

      reason,

      e,

      startingTime,

      startingTime);

   }

   public ThreadManagerResponse getResponse()
   {

      return threadManagerResponse;

   }

   public boolean isDone()
   {

      return true;

   }

   public String getId()
   {

      return id;

   }

}

