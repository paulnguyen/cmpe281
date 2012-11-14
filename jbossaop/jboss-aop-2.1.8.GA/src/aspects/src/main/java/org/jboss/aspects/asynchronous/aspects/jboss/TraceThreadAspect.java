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

package org.jboss.aspects.asynchronous.aspects.jboss;

import org.jboss.logging.Logger;
import org.jboss.aop.joinpoint.MethodInvocation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 71280 $</tt>
 */

public class TraceThreadAspect
{

   static private final Logger log = Logger.getLogger(TraceThreadAspect.class);

   static private SimpleDateFormat formatter =

   new SimpleDateFormat("hh:mm:ss:SSS aaa z");

   public TraceThreadAspect() {}

   public Object trace(MethodInvocation methodInvocation) throws Throwable
   {

      String className = null;

      if (methodInvocation.getTargetObject() != null)

         className = methodInvocation.getTargetObject().getClass().getName();

      else

         className = methodInvocation.getMethod().getDeclaringClass().getName();

      StringBuffer txt =

      new StringBuffer(Thread.currentThread().toString())

      .append(" ")

      .append(methodInvocation.getMethod().getReturnType().getName())

      .append(" ")

      .append(className)

      .append(".")

      .append(methodInvocation.getMethod().getName())

      .append("(");

      Class<?>[] parameterTypes =

      methodInvocation.getActualMethod().getParameterTypes();

      for (int i = 0; i < parameterTypes.length; i++)
      {

         txt.append(parameterTypes[i].getName());

         if (i + 1 < parameterTypes.length)

            txt.append(",");

      }

      txt.append(")");

      log.info(new StringBuffer("TRACE ENTER: ").append(getCurrentDate()).append(" ").append(txt));

      Object rsp = methodInvocation.invokeNext();

      log.info(new StringBuffer("TRACE EXIT: ").append(getCurrentDate()).append(" ").append(txt));

      return rsp;

   }

   static public String getCurrentDate()
   {

      return formatter.format(new Date(System.currentTimeMillis()));

   }

}
