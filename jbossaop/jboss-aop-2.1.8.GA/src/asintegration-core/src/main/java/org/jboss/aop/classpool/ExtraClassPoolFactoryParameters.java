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
package org.jboss.aop.classpool;

import java.util.Map;

/**
 * This class is used by the AS5 integration. Do not delete!!!! 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ExtraClassPoolFactoryParameters
{
   /** A map of properties that can be used to configure the classpool factories further */
   private static ThreadLocal<Map<Object, Object>> properties = new ThreadLocal<Map<Object, Object>>();
   
   public static void pushThreadProperties(Map<Object, Object> props)
   {
      properties.set(props);
   }
   
   public static Map<Object, Object> peekThreadProperties()
   {
      return properties.get();
   }
   
   public static Map<Object, Object> popThreadProperties()
   {
      Map<Object, Object> props = properties.get();
      properties.remove();
      return props;
   }

}
