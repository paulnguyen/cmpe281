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

import org.jboss.aop.Advisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.advice.AspectFactory;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 *  This interceptor handles call logging.
 *
 *  @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 *  @version $Revision: 37406 $
 */
public class CallLoggingInterceptorFactory implements AspectFactory
{
   private static CallLoggingInterceptor instance = new CallLoggingInterceptor();

   public Object createPerVM()
   {
      return instance;
   }

   public Object createPerClass(Advisor advisor)
   {
      return instance;
   }

   public Object createPerInstance(Advisor advisor, InstanceAdvisor instanceAdvisor)
   {
      return instance;
   }

   public Object createPerJoinpoint(Advisor advisor, Joinpoint jp)
   {
      return instance;
   }

   public Object createPerJoinpoint(Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp)
   {
      return instance;
   }

   public String getName()
   {
      return getClass().getName();
   }
}
