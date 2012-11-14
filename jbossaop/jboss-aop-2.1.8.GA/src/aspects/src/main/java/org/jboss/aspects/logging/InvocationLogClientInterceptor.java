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

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.jboss.aop.util.PayloadKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An interceptor that tests dumps any logging into the
 * invocation response
 *
 * @author  <a href="mailto:adrian@jboss">Adrian Brock</a>.
 * @version $Revision: 71280 $
 */
public class InvocationLogClientInterceptor
   implements org.jboss.aop.advice.Interceptor, Serializable
{
   // Constants -----------------------------------------------------

   private static final long serialVersionUID = 9191894622629072033L;

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // Interceptor Implementation ------------------------------------

   public String getName()
   {
      return "InvocationLogClientInterceptor";
   }

   /**
    * @todo remove the hardwiring of the meta data (needs next invocation metadata)
    */
   public Object invoke(org.jboss.aop.joinpoint.Invocation invocation)
      throws Throwable
   {
      // TODO: This should be set by the caller when required
      invocation.getMetaData().addMetaData(ThreadLocalAppender.LOG, ThreadLocalAppender.LOG, "Log", PayloadKey.AS_IS);

      // Perform the invocation and dump any attached log
      Object response = invocation.invokeNext();
      if (response != null)
         dumpLog(invocation);
      return response;
   }

   @SuppressWarnings("unchecked")
   public void dumpLog(org.jboss.aop.joinpoint.Invocation invocation)
   {
      Logger root = Logger.getRootLogger();
      ArrayList list = (ArrayList) invocation.getResponseAttachment(ThreadLocalAppender.LOG);
      for (Iterator i = list.iterator(); i.hasNext();)
          root.callAppenders((LoggingEvent) i.next());
   }

   // Y Overrides ---------------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner Classes -------------------------------------------------
}
