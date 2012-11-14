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
package org.jboss.aop.asintegration.jboss4;

import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.loading.RepositoryClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @autor adrian@jboss.org
 * @version $Revision$
 */
public class ScopedRepositoryClassLoaderHelper
{
   public static boolean isScopedClassLoader(ClassLoader loader)
   {
      boolean scoped = false;
      if (loader instanceof RepositoryClassLoader)
      {
         LoaderRepository repository = ((RepositoryClassLoader)loader).getLoaderRepository();
         if (repository instanceof HeirarchicalLoaderRepository3)
         {
            scoped = true;
            //HeirarchicalLoaderRepository3 hlr = (HeirarchicalLoaderRepository3)repository;
            //boolean parentFirst = hlr.getUseParentFirst();
         }
      }
      return scoped;
   }
}
