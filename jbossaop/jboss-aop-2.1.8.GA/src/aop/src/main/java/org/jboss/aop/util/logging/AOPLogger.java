/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.util.logging;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A thin wrapper around the jboss logging framework, so that if a proper logger is not installed
 * we get the output redirected to System.out.
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class AOPLogger
{
   private static final ConcurrentMap<Class<?>, AOPLogger> loggers = new ConcurrentHashMap<Class<?>, AOPLogger>();
   
   public static AOPLogger getLogger(final Class<?> clazz)
   {
      AOPLogger logger = loggers.get(clazz);
      
      if (logger == null)
      {
         logger = new AOPLogger(clazz);
         
         AOPLogger oldLogger = loggers.putIfAbsent(clazz, logger);
         
         if (oldLogger != null)
         {
            logger = oldLogger;
         }
      }      
      
      return logger;
   }

   private final org.jboss.logging.Logger logger;

   private AOPLogger(final Class<?> clazz)
   {
      org.jboss.logging.Logger log;
      log = org.jboss.logging.Logger.getLogger(clazz);
      if(log.getLoggerPlugin().getClass().getName().equals( org.jboss.logging.NullLoggerPlugin.class.getName()))
      {
         org.jboss.logging.Logger.setPluginClassName(SystemOutLoggerPlugin.class.getName());
         log = org.jboss.logging.Logger.getLogger(clazz);
         // setting the logger back to NullLogger since we only want jboss classes to use AOPLogger
         org.jboss.logging.Logger.setPluginClassName(org.jboss.logging.NullLoggerPlugin.class.getName());
      }
      
      logger = log;
   }

   @SuppressWarnings("deprecation")
   public boolean isInfoEnabled()
   {
      return logger.isInfoEnabled();
   }

   @SuppressWarnings("deprecation")
   public boolean isDebugEnabled()
   {
      return logger.isDebugEnabled();
   }

   public boolean isTraceEnabled()
   {
      return logger.isTraceEnabled();
   }
   
   public void fatal(final Object message)
   {
      logger.fatal(message);
   }
   
   public void fatal(final Object message, final Throwable t)
   {
      logger.fatal(message, t);
   }
   
   public void error(final Object message)
   {
      logger.error(message);
   }
   
   public void error(final Object message, final Throwable t)
   {
      logger.error(message, t);
   }
   
   public void warn(final Object message)
   {
      logger.warn(message);
   }
   
   public void warn(final Object message, final Throwable t)
   {
      logger.warn(message, t);
   }
   
   public void info(final Object message)
   {
      logger.info(message);
   }
   
   public void info(final Object message, final Throwable t)
   {
      logger.info(message, t);
   }
   
   public void debug(final Object message)
   {
      logger.debug(message);
   }
   
   public void debug(final Object message, final Throwable t)
   {
      logger.debug(message, t);
   }
   
   public void trace(final Object message)
   {
      logger.trace(message);
   }
   
   public void trace(final Object message, final Throwable t)
   {
      logger.trace(message, t);
   }
   
}
