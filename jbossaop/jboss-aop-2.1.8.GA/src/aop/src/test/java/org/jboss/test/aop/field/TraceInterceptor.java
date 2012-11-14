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
package org.jboss.test.aop.field;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;

/**
 *
 * @author <a href="mailto:stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision
 */
public class TraceInterceptor implements Interceptor
{
   public static boolean intercepted;
   public String getName()
   {
      return "TraceInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      intercepted = true;
      String msg = null;
      if (invocation instanceof FieldReadInvocation)
      {
         msg = "read field name: " + ((FieldReadInvocation)invocation).getField();
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         msg = "write field name: " + ((FieldWriteInvocation)invocation).getField();
      }

      try
      {
         System.out.println("<<< Trace : " + msg);
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving Trace");
      }
   }

}
