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
package org.jboss.aspects.patterns.observable;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.Invocation;

/**
 * A Subject Interceptor. Traps changes to the object
 * and fires change notifications.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37406 $
 */
public class SubjectInterceptor implements Interceptor
{
   public String getName()
   {
      return "Observerable";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      FieldInvocation fi = (FieldInvocation) invocation;
      Object before = fi.getField().get(fi.getTargetObject());
      Object result = invocation.invokeNext();
      Object after = fi.getField().get(fi.getTargetObject());
      if ((before == null && after != null) || (before != null && before.equals(after) == false))
      {
         Subject observable = (Subject) fi.getTargetObject();
         observable.notifyObservers();
      }
      return result;
   }
}
