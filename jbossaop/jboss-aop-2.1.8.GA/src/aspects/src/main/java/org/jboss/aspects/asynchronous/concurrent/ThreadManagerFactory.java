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

import org.jboss.aspects.asynchronous.AsynchronousParameters;
import org.jboss.aspects.asynchronous.AsynchronousUserTask;
import org.jboss.aspects.asynchronous.ThreadManager;
import org.jboss.aspects.asynchronous.ThreadManagerRequest;
import org.jboss.aspects.asynchronous.common.ThreadManagerRequestImpl;

import java.util.ResourceBundle;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 63393 $</tt>
 */

public class ThreadManagerFactory
{

   protected ThreadManager threadManager;

   protected String DEFAULT_MIN_THREAD = "10";

   protected String DEFAULT_MAX_THREAD = "100";

   protected String DEFAULT_KEEP_ALIVE = "5000";

   protected String DEFAULT_IS_WAITING_WHEN_POOLSIZE_FULL = "FALSE";

   protected String DEFAULT_IS_POOLING = "TRUE";

   static protected String MIN_POOL_SIZE = "threadManager.minimumPoolsize";

   static protected String MAX_POOL_SIZE = "threadManager.maximumPoolsize";

   static protected String KEEP_ALIVE = "threadManager.keepAlive";

   static protected String IS_WAITING_WHEN_POOLSIZE_FULL =

   "threadManager.isWaitingWhenPoolSize";

   static protected String IS_POOLING =

   "threadManager.isPooling";

   static protected String PROPERTY_FILE =

   "org.jboss.aspects.asynchronous.threadManager";

   protected static final ThreadManagerFactory threadManagerFactory =

   new ThreadManagerFactory();

   static protected ResourceBundle rb;

   static void initResourceBundle()
   {

      try
      {

         rb = ResourceBundle.getBundle(PROPERTY_FILE);

      }
      catch (Exception e)
      {

         //System.err.println(e.getMessage());

      }

   }

   private static String getString(String key, String defaultValue)
   {

      try
      {

         if (rb == null)

            return defaultValue;

         String value = rb.getString(key);

         if (value == null)

            return defaultValue;

         else

            return value;

      }
      catch (Exception e)
      {}

      return defaultValue;

   }

   protected ThreadManagerFactory()
   {

      initResourceBundle();

      threadManager = new ThreadManagerImpl();

      threadManager.setMinimumPoolSize(Integer.parseInt(getString(MIN_POOL_SIZE, DEFAULT_MIN_THREAD)));

      threadManager.setMaximumPoolSize(Integer.parseInt(getString(MAX_POOL_SIZE, DEFAULT_MAX_THREAD)));

      threadManager.setKeepAliveTime(Integer.parseInt(getString(KEEP_ALIVE, DEFAULT_KEEP_ALIVE)));

      threadManager.setWaitWhenPoolSizeIsFull(Boolean

      .valueOf(getString(IS_WAITING_WHEN_POOLSIZE_FULL,

      DEFAULT_IS_WAITING_WHEN_POOLSIZE_FULL))

      .booleanValue());


      threadManager.setPooling(Boolean

      .valueOf(getString(IS_POOLING,

      DEFAULT_IS_POOLING))

      .booleanValue());

   }

   public static ThreadManager getThreadManager()
   {
      return threadManagerFactory.threadManager;
   }

   static public ThreadManagerRequest createNewThreadManagerRequest(String id,

                                                                    AsynchronousParameters inputParameters,

                                                                    AsynchronousUserTask taskClassNameImpl,

                                                                    long timeout)
   {

      return new ThreadManagerRequestImpl(id,

      inputParameters,

      taskClassNameImpl,

      timeout);

   }

}
