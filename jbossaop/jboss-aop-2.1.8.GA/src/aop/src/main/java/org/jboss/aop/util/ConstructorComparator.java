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
package org.jboss.aop.util;

import java.lang.reflect.Constructor;
import java.util.Comparator;


/**
 * Compares Constructors. This is used to make sure the constructor IDs line up between
 * the advisor and the instrumentor.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71276 $
 */
public class ConstructorComparator implements Comparator<Constructor<?>>
{
   private ConstructorComparator() {}

   public static final java.util.Comparator<Constructor<?>> INSTANCE = new ConstructorComparator();
        
   public int compare(Constructor<?> m1, Constructor<?> m2) {
      try {
         Class<?>[] args1 = m1.getParameterTypes();
         Class<?>[] args2 = m2.getParameterTypes();
         if (args1.length < args2.length) return -1;
         if (args1.length > args2.length) return 1;
         for (int i = 0; i < args1.length; i++) {
            int result = args1[i].getName().compareTo(
               args2[i].getName());
            if (result != 0) return result;
         }
      } 
      catch (Exception e) {
         throw new RuntimeException(e);
      }
      // unreachable.
      throw new Error();
   }
}
