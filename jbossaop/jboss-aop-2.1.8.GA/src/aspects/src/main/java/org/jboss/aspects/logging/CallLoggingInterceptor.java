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
package org.jboss.aspects.logging;

import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.logging.Logger;

import java.util.Arrays;

/** 
 * Logs invocations.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>.
 * @version $Revision: 37406 $
 */
public final class CallLoggingInterceptor
   implements org.jboss.aop.advice.Interceptor, LoggingConstants
{
   protected Logger log = Logger.getLogger(this.getClass());

   public String getName() { return "CallLoggingInterceptor"; }

   public Object invoke(org.jboss.aop.joinpoint.Invocation invocation) throws Throwable
   {
      boolean callLogging = log.isDebugEnabled();
      if (callLogging)
         callLogging = Boolean.valueOf((String)invocation.getMetaData(LOGGING, CALL_LOGGING)).booleanValue();

      if (callLogging)
         log.debug("Invoking: " + dumpInvocation(invocation));

      Object response = null;
      try
      {
         response = invocation.invokeNext();
         return response;
      }
      catch (Throwable t)
      {
         if (callLogging)
            log.debug("Throwing: " + dumpInvocation(invocation), t);
         throw t;
      }
      finally
      {
         if (callLogging)
            log.debug("Response: " + dumpInvocationResponse(response) + " for " + dumpInvocation(invocation));
      }
   }

   /**
    * Display useful information about the invocation
    * @todo improve this, probably controlled by meta data
    * @param invocation the invocation
    */
   public String dumpInvocation(org.jboss.aop.joinpoint.Invocation invocation)
   {
      StringBuffer buffer = new StringBuffer();
      if (invocation instanceof MethodInvocation)
      {
         org.jboss.aop.joinpoint.MethodInvocation methodInvocation = (org.jboss.aop.joinpoint.MethodInvocation)invocation;
         buffer.append("Method@").append(System.identityHashCode(invocation)).append("{");
         buffer.append("method=").append(methodInvocation.getMethod());
         // TODO fix this, null arguments screws this up 
         // buffer.append(" args=").append(Arrays.asList(methodInvocation.arguments));
      }
      else if (invocation instanceof FieldReadInvocation)
      {
         org.jboss.aop.joinpoint.FieldReadInvocation fieldInvocation = (org.jboss.aop.joinpoint.FieldReadInvocation)invocation;
         buffer.append("FieldRead@").append(System.identityHashCode(invocation)).append("{");
         buffer.append("field=").append(fieldInvocation.getField());
      }
      else if (invocation instanceof FieldWriteInvocation)
      {
         org.jboss.aop.joinpoint.FieldWriteInvocation fieldInvocation = (org.jboss.aop.joinpoint.FieldWriteInvocation)invocation;
         buffer.append("FieldWrite@").append(System.identityHashCode(invocation)).append("{");
         buffer.append("field=").append(fieldInvocation.getField());
         buffer.append(" value=").append(fieldInvocation.getValue());
      }
      else if (invocation instanceof ConstructorInvocation)
      {
         org.jboss.aop.joinpoint.ConstructorInvocation cInvocation = (org.jboss.aop.joinpoint.ConstructorInvocation)invocation;
         buffer.append("Construct@").append(System.identityHashCode(invocation)).append("{");
         buffer.append("constructor=").append(cInvocation.getConstructor());
         buffer.append(" args=").append(Arrays.asList(cInvocation.getArguments()));
      }
      else
      {
         return "Unknown " + invocation;
      }
      buffer.append("}");
      return buffer.toString();
   }

   /**
    * Display useful information about the invocation response
    * @todo improve this, probably controlled by meta data
    * @param invocation the invocation
    */
   public String dumpInvocationResponse(Object response)
   {
      if (response == null)
         return "null";
      StringBuffer buffer = new StringBuffer();
      buffer.append(response);
      return buffer.toString();
   }
}
