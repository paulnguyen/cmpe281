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
package org.jboss.test.aop.scope;

import java.util.ArrayList;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unchecked")
public class Interceptions
{
   static ArrayList interceptions = new ArrayList();

   public static ArrayList getInterceptions()
   {
      return interceptions;
   }
   
   public static void clear()
   {
      interceptions.clear();
   }
   
   public static void add(Object o)
   {
      interceptions.add(o);
   }
   
   public static int size()
   {
      return interceptions.size();
   }
   
   public static Object get(int i)
   {
      return interceptions.get(i);
   }
}
