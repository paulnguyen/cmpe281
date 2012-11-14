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

import org.jboss.aspects.asynchronous.AsynchronousConstants;
import org.jboss.aspects.asynchronous.ThreadManagerResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 37406 $</tt>
 */

public class ThreadManagerResponseImpl

extends AsynchronousResponseImpl

implements ThreadManagerResponse, AsynchronousConstants
{

   private long _startingTime = -1;

   private long _endingTime = -1;

   private String id = null;

   static private SimpleDateFormat formatter =

   new SimpleDateFormat("HH:mm:ss.SSS ");

   public ThreadManagerResponseImpl(String id,

                                    int code,

                                    String reason,

                                    Object obj)
   {

      super(code, reason, obj);

      this.id = id;

   }

   public ThreadManagerResponseImpl(String id,

                                    int code,

                                    String reason,

                                    Object obj,

                                    long startTime)
   {

      super(code, reason, obj);

      _startingTime = startTime;

      this.id = id;

   }

   public ThreadManagerResponseImpl(String id,

                                    int code,

                                    String reason,

                                    Object obj,

                                    long startTime,

                                    long endTime)
   {

      super(code, reason, obj);

      _startingTime = startTime;

      _endingTime = endTime;

      this.id = id;

   }

   public long getStartingTime()
   {

      return _startingTime;

   }

   public long getEndingTime()
   {

      return _endingTime;

   }

   public String toString()
   {

      return new StringBuffer("Id:")

      .append(getId())

      .append(" Rc:")

      .append(getResponseCode())

      .append(getReason() != null ? " Re:" : "")

      .append(getReason() != null ? getReason() : "")

      .append(" St:")

      .append(formatter.format(new Date(getStartingTime())))

      .append(" Du:")

      .append(getEndingTime() - getStartingTime())

      .append(getResponseCode() != AsynchronousConstants.OK ? " Rs:" : "")

      .append(getResult())

      .toString();

   }

   public String getId()
   {

      return id;

   }

}

