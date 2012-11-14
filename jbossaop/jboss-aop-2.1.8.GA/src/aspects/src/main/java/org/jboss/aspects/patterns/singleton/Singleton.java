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
package org.jboss.aspects.patterns.singleton;

import org.jboss.aop.joinpoint.ConstructorInvocation;

/**
 * A simple implementation of a singleton.<p>
 * 
 * This advice should be PER_CLASS scoped<p>
 * 
 * e.g.
 * <pre>  
 * <aspect class="Singleton" scope="PER_CLASS"/>
 *
 * <bind pointcut="execution(POJO->new())">
 *     <advice name="constructorAdvice" aspect="Singleton"/>
 * </bind>
 * </pre>
 *
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37406 $
 */
public class Singleton
{
   private Object singleton;

   public synchronized Object constructorAdvice(ConstructorInvocation invocation) throws Throwable
   {
      if (singleton == null)
         singleton = invocation.invokeNext();
      return singleton;
   }
}
