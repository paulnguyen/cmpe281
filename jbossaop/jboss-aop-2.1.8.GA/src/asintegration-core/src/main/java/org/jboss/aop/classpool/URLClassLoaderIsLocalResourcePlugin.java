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

import java.net.URL;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class URLClassLoaderIsLocalResourcePlugin extends AbstractIsLocalResourcePlugin 
{
   public URLClassLoaderIsLocalResourcePlugin(BaseClassPool pool)
   {
      super(pool);
   }
   
   public boolean isMyResource(String classResourceName)
   {
      //TODO This should be moved into URLClassLoaderIsLocalResourcePlugin, and the -core tests should be updated to
      //not use the same urls
      ClassLoader myLoader = getPool().getClassLoader();
      URL myURL = myLoader.getResource(classResourceName); 
      if (myURL == null)
      {
         return false;
      }
      if (isSameInParent(classResourceName, myURL))
      {
         return false;
      }
      return true;
   }
}
