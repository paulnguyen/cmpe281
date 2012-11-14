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
package org.jboss.test.aop.stress;

import java.util.Properties;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class ScenarioPropertyReader
{
   public static final String WARMUP = "warmup";
   public static final String LOOPS = "loops";
   public static final String THREADS = "threads";
   public static final String RANDOM_SLEEP_INTERVAL = "random_sleep_interval";
   public static final String SLEEPTIME_MILLIS = "sleeptime_millis";
   public static final String LOGGING = "logging";
   
   Properties properties;
   ScenarioPropertyReader next;
   
   void setNext(ScenarioPropertyReader next)
   {
      this.next = next;
   }
   
   ScenarioPropertyReader getNext()
   {
      return next;
   }
   
   int getIntProperty(String key)
   {
      return Integer.parseInt(getProperty(key));  
   }
   
   boolean getBooleanProperty(String key)
   {
      return "true".equals(getProperty(key));
   }
   
   String getProperty(String key)
   {
      String val = null;

      if (properties == null)
      {
         this.properties = loadProperties();
      }
      
      if (properties != null)
      {
         val = (String)properties.get(key);
      }
      
      if (val != null)
      {
         return val;
      }
      
      if (next != null)
      {
         val = next.getProperty(key);
         return val;
      }
      else
      {
         throw new RuntimeException("Invalid property '" + key + "' could not be found");
      }      
   }
 
   abstract Properties loadProperties();
}
