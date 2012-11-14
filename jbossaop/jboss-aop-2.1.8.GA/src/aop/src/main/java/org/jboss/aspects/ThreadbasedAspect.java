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

import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;

/**
 * This aspect should be scoped PER_JOINPOINT
 * It allows a field to be like a ThreadLocal
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71279 $
 */
public class ThreadbasedAspect
{
   private ThreadLocal<Object> threadbased = new ThreadLocal<Object>();

   public Object access(FieldReadInvocation invocation) throws Throwable
   {
      // just in case we have a primitive, we can't return null
      if (threadbased.get() == null) return invocation.invokeNext();
      return threadbased.get();
   }

   public Object access(FieldWriteInvocation invocation) throws Throwable
   {
      threadbased.set(invocation.getValue());
      return null;
   }
}
