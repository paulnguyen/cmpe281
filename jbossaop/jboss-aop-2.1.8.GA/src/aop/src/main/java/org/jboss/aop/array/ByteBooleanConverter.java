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
package org.jboss.aop.array;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ByteBooleanConverter
{
   private static final byte BOOLEAN_FALSE = 0;
   private static final byte BOOLEAN_TRUE = 1;
   
   public static byte toByte(boolean b)
   {
      return (b) ? BOOLEAN_TRUE : BOOLEAN_FALSE;
   }
   
   public static boolean toBoolean(byte b)
   {
      if (b == BOOLEAN_FALSE) return false;
      else if (b == BOOLEAN_TRUE) return true;
      
      throw new RuntimeException("Invalid byte " + b + " cannot be converted to a boolean");
   }
}
