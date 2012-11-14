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

package org.jboss.aspects.asynchronous.aspects;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 71280 $</tt>
 */

public class Utils
{

   static public Object returnInitObject(Class<?> returnType)
   {

      if (!returnType.isPrimitive())

         return null;

      else if (returnType.equals(Void.TYPE))

         return null;

      else if (returnType.equals(Boolean.TYPE))
      {

         return Boolean.FALSE;

      }
      else if (returnType.equals(Integer.TYPE))
      {

         return new Integer(0);

      }
      else if (returnType.equals(Long.TYPE))
      {

         return new Long(0);

      }
      else if (returnType.equals(Float.TYPE))
      {

         return new Float(0);

      }
      else if (returnType.equals(Double.TYPE))
      {

         return new Double(0);

      }
      else if (returnType.equals(Character.TYPE))
      {

         return new Character('\0');

      }
      else if (returnType.equals(Byte.TYPE))
      {

         return new Byte((byte) 0);

      }
      else if (returnType.equals(Short.TYPE))
      {

         return new Short((short) 0);

      }
      else

         return null;

   }

}
