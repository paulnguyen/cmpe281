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

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;

/**
 * An appender that logs into a thread local array list.
 *
 * @author  <a href="mailto:adrian@jboss.org">Adrian Brock</a>.
 * @version $Revision: 71280 $
 */
public class ThreadLocalAppender
   extends AppenderSkeleton
{
   // Constants -----------------------------------------------------

   public static final String LOG = "InvocationLog";

   private static ThreadLocal<ArrayList<LoggingEvent>> loggingTL = new ThreadLocal<ArrayList<LoggingEvent>>(); 

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   /**
    * Change the theadlocal list
    * 
    * @param list the new list
    */
   public static ArrayList<LoggingEvent> getList()
   {
      return loggingTL.get();
   }

   /**
    * Change the theadlocal list
    * 
    * @param list the new list
    */
   public static void setList(ArrayList<LoggingEvent> list)
   {
      loggingTL.set(list);
   }

   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // Appender Implementation ---------------------------------------

   protected void append(LoggingEvent event)
   {
      ArrayList<LoggingEvent> logging = getList();
      if (logging == null)
         return;

      logging.add(event);
   }

   public boolean requiresLayout()
   {
      return false;
   }

   public void close()
   {
   }

   // Y Overrides ---------------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner Classes -------------------------------------------------
}
