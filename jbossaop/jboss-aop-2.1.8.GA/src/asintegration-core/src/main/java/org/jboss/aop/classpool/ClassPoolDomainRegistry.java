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

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolDomainRegistry
{
   final static ClassPoolDomainRegistry INSTANCE = new ClassPoolDomainRegistry();
   
   final Map<Object, WeakReference<ClassPoolDomain>> domains = new WeakHashMap<Object, WeakReference<ClassPoolDomain>>();
   
   public static ClassPoolDomainRegistry getInstance()
   {
      return INSTANCE;
   }
   
   public synchronized ClassPoolDomain getDomain(Object key)
   {
      WeakReference<ClassPoolDomain> ref = domains.get(key);
      if (ref == null)
      {
         return null;
      }
      return ref.get();
   }
   
   public synchronized void addClassPoolDomain(Object key, ClassPoolDomain domain)
   {
      domains.put(key, new WeakReference<ClassPoolDomain>(domain));
   }
   
}
