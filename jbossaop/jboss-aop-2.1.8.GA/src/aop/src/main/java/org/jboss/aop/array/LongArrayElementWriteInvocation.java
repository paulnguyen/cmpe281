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

import org.jboss.aop.advice.Interceptor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class LongArrayElementWriteInvocation extends ArrayElementWriteInvocation
{
   private static final long serialVersionUID = -609480261599580470L;
   long[] array;
   long value;
   
   public LongArrayElementWriteInvocation(Interceptor[] interceptors, long[] target, int index, long value)
   {
      super(interceptors, target, index);
      this.value = value;
      this.array = target;
   }

   @Override
   public Object invokeTarget()
   {
      array[index] = value;
      return null;
   }

   @Override
   public Object getValue()
   {
      return new Long(value);
   }

   public long getLongValue()
   {
      return value;
   }
}
