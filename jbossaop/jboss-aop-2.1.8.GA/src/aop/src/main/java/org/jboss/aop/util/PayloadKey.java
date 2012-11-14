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

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Type safe enumeration used for to identify the payloads. 
 */
public final class PayloadKey implements Serializable
{

   static final long serialVersionUID = 7688683906350419871L;

   // these fields are used for serialization
   private static int nextOrdinal = 0;

   private static final ArrayList<PayloadKey> values = new ArrayList<PayloadKey>(3);

   /** Put me in the transient map, not part of payload. */
   public final static PayloadKey TRANSIENT = new PayloadKey("TRANSIENT");

   /** Do not serialize me, part of payload as is. */
   public final static PayloadKey AS_IS = new PayloadKey("AS_IS");

   /** Put me in the payload map. */
   public final static PayloadKey MARSHALLED = new PayloadKey("MARSHALLED");

   private final transient String name;

   // this is the only value serialized
   private final int ordinal;

   private PayloadKey(String name)
   {
      this.name = name;
      this.ordinal = nextOrdinal++;
      values.add(this);
   }

   @Override
   public String toString()
   {
      return name;
   }

   Object readResolve() throws ObjectStreamException
   {
      return values.get(ordinal);
   }
}