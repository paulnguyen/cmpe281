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
package org.jboss.aop.advice;

import org.jboss.aop.Domain;
import org.jboss.aop.joinpoint.Invocation;

/**
 * Intercepts one or more joinpoints. <br>
 * The {@code Interceptor} is a specific type of aspect, that contains only
 * one, well defined advice, the {@code invoke} method. Because of that, an
 * {@code Interceptor} is also referred to as an advice.
 */
public interface Interceptor
{
   /**
    * Returns the name of this interceptor. This name is unique inside the
    * {@link Domain domain}.
    * 
    * @return name the name that identifies this interceptor in its
    *         {@link Domain domain}
    */
   public String getName();
   
   /**
    * The single advice contained in the {@code Interceptor}.
    * <br>
    * To call the next interceptor or advice in the chain, this method must call
    * {@code invocation.invokeNext()} method (if there are not elements left to
    * be called in the chain, this method invokes the joinpoint itself). Not doing
    * so means halting the execution of the interceptor chain and, hence, avoiding
    * the execution of the joinpoint. This should be done only when the
    * interceptor must replace the joinpoint execution.
    * 
    * @param invocation represents the joinpoint to be intercepted
    * @return           the result value. This value will be returned to the previous
    *                   interceptor/advice as a result of
    *                   {@link Invocation#invokeNext()}. In case this is the first
    *                   interceptor in the chain, this value will replace the
    *                   joinpoint return value in the basis system. 
    * @throws Throwable may throw any exceptions declared by the joinpoint itself.
    *                   If this exception is not declared and is not a runtime
    *                   exception, it will be encapsulated in a
    *                   {@link RuntimeException} before being thrown to the basis
    *                   system.
    */
   public Object invoke(Invocation invocation) throws Throwable;
}