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
package org.jboss.aspects.logging;

import java.util.ArrayList;

import org.apache.log4j.spi.LoggingEvent;

/**
 * An interceptor that tests whether logging should be done to
 * the invocation response.
 *
 * @author  <a href="mailto:adrian@jboss">Adrian Brock</a>.
 * @version $Revision: 71280 $
 */
public class InvocationLogInterceptor
   implements org.jboss.aop.advice.Interceptor
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // Interceptor Implementation ------------------------------------

   public String getName()
   {
      return "InvocationLogInterceptor";
   }

   public Object invoke(org.jboss.aop.joinpoint.Invocation invocation)
      throws Throwable
   {
      Object started = invocation.getMetaData(ThreadLocalAppender.LOG, ThreadLocalAppender.LOG);

      ArrayList<LoggingEvent> log = null;
      if (started != null)
      {
         // Some earlier invocation started the logging
         if (ThreadLocalAppender.getList() != null)
            started = null;
         else
         {
            // We are the first, start the logging
            log = new ArrayList<LoggingEvent>();
            ThreadLocalAppender.setList(log);
         }
      }

      try
      {
         // Perform the invocation and attach the log when we started it
         Object response = invocation.invokeNext();
         if (started != null)
            invocation.addResponseAttachment(ThreadLocalAppender.LOG, log);
         return response;
      }
      finally
      {
         // If we started the logging, stop it
         if (started != null)
            ThreadLocalAppender.setList(null);
      }
   }

   // Y Overrides ---------------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner Classes -------------------------------------------------
}
