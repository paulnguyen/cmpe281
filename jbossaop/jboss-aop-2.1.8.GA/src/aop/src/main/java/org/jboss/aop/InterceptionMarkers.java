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
package org.jboss.aop;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class InterceptionMarkers
{
   protected final ConcurrentHashMap<String, String> convertableReference = new ConcurrentHashMap<String, String>();
   protected final ConcurrentHashMap<String, String> hasFieldInterception = new ConcurrentHashMap<String, String>();
   protected final ConcurrentHashMap<String, String> hasConstructorInterception = new ConcurrentHashMap<String, String>();

   protected final ConcurrentHashMap<String, String> skipConvertableReference = new ConcurrentHashMap<String, String>();
   protected final ConcurrentHashMap<String, String> skipFieldInterception = new ConcurrentHashMap<String, String>();
   protected final ConcurrentHashMap<String, String> skipConstructorInterception = new ConcurrentHashMap<String, String>();

   public void addConstructionInterceptionMarker(String classname)
   {
      skipConstructorInterception.remove(classname);
      skipConvertableReference.remove(classname);
      hasConstructorInterception.put(classname, classname);
      convertableReference.put(classname, classname);
   }

   public void addFieldInterceptionMarker(String classname)
   {
      skipFieldInterception.remove(classname);
      skipConvertableReference.remove(classname);
      hasFieldInterception.put(classname, classname);
      convertableReference.put(classname, classname);
   }

   public void skipReference(String classname)
   {
      skipConvertableReference.put(classname, classname);
   }

   public boolean shouldSkipConstruction(String classname)
   {
      return !(hasConstructorInterception.containsKey(classname) || !skipConstructorInterception.containsKey(classname));
      //return false;
   }

   public boolean shouldSkipFieldAccess(String classname)
   {
      return !(hasFieldInterception.containsKey(classname) || !skipFieldInterception.containsKey(classname));
      //return false;
   }

   public void skipConstruction(String classname)
   {
      skipConstructorInterception.put(classname, classname);
   }

   public void skipFieldAccess(String classname)
   {
      skipFieldInterception.put(classname, classname);
   }

   public boolean convertReference(String classname)
   {
      return !skipConvertableReference.containsKey(classname) || convertableReference.containsKey(classname);
      //return true;
   }


}
