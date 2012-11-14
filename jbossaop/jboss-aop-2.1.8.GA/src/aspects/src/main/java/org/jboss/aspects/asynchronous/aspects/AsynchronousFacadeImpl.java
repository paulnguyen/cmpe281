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

package org.jboss.aspects.asynchronous.aspects;

import org.jboss.aspects.asynchronous.AsynchronousConstants;
import org.jboss.aspects.asynchronous.AsynchronousResponse;
import org.jboss.aspects.asynchronous.AsynchronousTask;
import org.jboss.aspects.asynchronous.ThreadManagerResponse;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 37406 $</tt>
 */

public class AsynchronousFacadeImpl

implements AsynchronousFacade, AsynchronousConstants
{

   private AsynchronousFacadeFields asynchronousFacadeFields = null;

   public AsynchronousFacadeImpl()
   {

      asynchronousFacadeFields =

      new AsynchronousFacadeFieldsThreadLocalImpl();

   }

   private AsynchronousFacadeFields getAsynchronousFacadeFields()
   {

      return asynchronousFacadeFields;

   }

   public AsynchronousTask getAsynchronousTask()
   {

      return getAsynchronousFacadeFields().getAsynchronousTask();

   }

   public void setTimeout(long timeout)
   {

      getAsynchronousFacadeFields().setTimeout(timeout);

   }

   public long getTimeout()
   {

      return getAsynchronousFacadeFields().getTimeout();

   }

   public void setAsynchronousTask(AsynchronousTask asynchronousTask)
   {

      getAsynchronousFacadeFields().setAsynchronousTask(asynchronousTask);

   }

   public void setId(String id)
   {

      getAsynchronousFacadeFields().setId(id);

   }

   public String getId()
   {

      return getAsynchronousFacadeFields().getId();

   }

   public ThreadManagerResponse getThreadManagerResponse()
   {

      return getAsynchronousTask().getResponse();

   }

   public AsynchronousResponse waitForResponse()
   {

      return waitForResponse(getAsynchronousTask());

   }

   public Object getReturnValue()
   {

      return getReturnValue(getAsynchronousTask());

   }

   public boolean isDone()
   {

      return isDone(getAsynchronousTask());

   }

   public int getResponseCode()
   {

      return getResponseCode(getAsynchronousTask());

   }

   public AsynchronousResponse waitForResponse(AsynchronousTask asynchronousTask)
   {

      if (asynchronousTask.getResponse().getResponseCode()

      == AsynchronousConstants.OK)
      {

         AsynchronousResponse atask =

         (AsynchronousResponse) asynchronousTask

         .getResponse()

         .getResult();

         return atask;

      }

      return asynchronousTask.getResponse();

   }

   public Object getReturnValue(AsynchronousTask asynchronousTask)
   {

      if (asynchronousTask == null)

         return null;

      AsynchronousResponse asynchronousResponse =

      waitForResponse(asynchronousTask);

      if (asynchronousResponse.getResponseCode() == OK)

         return asynchronousResponse.getResult();

      else

         return null;

   }

   public boolean isDone(AsynchronousTask synchronousTask)
   {

      if (synchronousTask == null)

         return false;

      else

         return synchronousTask.isDone();

   }

   public int getResponseCode(AsynchronousTask synchronousTask)
   {

      return (waitForResponse(synchronousTask).getResponseCode());

   }

}

