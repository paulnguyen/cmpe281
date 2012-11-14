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
package org.jboss.aspects;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71280 $
 */
public class NullOrZero
{
   private static final Long LONG_ZERO = new Long(0);
   private static final Short SHORT_ZERO = new Short((short) 0);
   private static final Integer INT_ZERO = new Integer(0);
   private static final Float FLOAT_ZERO = new Float(0.0);
   private static final Double DOUBLE_ZERO = new Double(0.0);
   private static final Character CHAR_ZERO = new Character('\0');
   private static final Byte BYTE_ZERO = new Byte((byte) 0);
   private static final Boolean BOOLEAN_ZERO = Boolean.FALSE;


   public static Object nullOrZero(Class<?> type)
   {
      if (!type.isPrimitive()) return null;
      if (type.equals(void.class)) return null;
      if (type.equals(long.class)) return LONG_ZERO;
      if (type.equals(short.class)) return SHORT_ZERO;
      if (type.equals(int.class)) return INT_ZERO;
      if (type.equals(float.class)) return FLOAT_ZERO;
      if (type.equals(double.class)) return DOUBLE_ZERO;
      if (type.equals(char.class)) return CHAR_ZERO;
      if (type.equals(byte.class)) return BYTE_ZERO;
      if (type.equals(boolean.class)) return BOOLEAN_ZERO;

      return null;
   }
}
