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
package org.jboss.aop.advice.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Use this annotation on the advice parameter that receives the complete list of
 * joinpoint argument values.<br>
 * The annotated parameter must be of type <code>Object[]</code> and there
 * should not be any other advice parameter annotated either with {@link Arg} or
 * with <code>Args</code> itself.<br>
 * For procedure executions and calls, this parameter will contain the arguments
 * of the execution or call, in the same order they occur in the intercepted
 * code. For interception of field write joinpoints, it will
 * contain a single element: the value of the field write. For field read joinpoints,
 * this parameter value will always be <code>null</code>.
 * <br>
 * Notice that primitive argument values are converted to their respective wrappers.
 * <br>
 * Any change that the advice performs on this parameter will be reflected on the
 * next advices in the stack, and on the joinpoint itself (unless the advice performs
 * a change after the joinpoint execution). Such a change must be compatible to
 * the joinpoint argument types.
 * 
 * @author Flavia Rainone
 */
@Target (ElementType.PARAMETER) @Retention (RetentionPolicy.RUNTIME)
public @interface Args {}