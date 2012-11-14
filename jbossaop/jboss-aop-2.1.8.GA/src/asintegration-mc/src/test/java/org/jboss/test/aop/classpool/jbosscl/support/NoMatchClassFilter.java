/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.aop.classpool.jbosscl.support;

import org.jboss.classloader.plugins.ClassLoaderUtils;
import org.jboss.classloader.spi.filter.ClassFilter;

/**
 * NoMatchClassFilter.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class NoMatchClassFilter implements ClassFilter
{
   String className;
   
   public boolean filtered = false;
   
   public NoMatchClassFilter(String classname)
   {
      this.className = classname;
   }
   
   public boolean matchesClassName(String className)
   {
      if (this.className.equals(className))
      {
         filtered = true;
         return false;
      }
      return true;
   }
   
   public boolean matchesResourcePath(String resourcePath)
   {
      if (ClassLoaderUtils.classNameToPath(this.className).equals(resourcePath))
      {
         filtered = true;
         return false;
      }
      return true;
   }
   
   public boolean matchesPackageName(String packageName)
   {
      if (ClassLoaderUtils.getClassPackageName(this.className).equals(packageName))
         return false;
      return true;
   }
}
