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

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation on advice parameters that should contain values of 
 * advised joinpoint arguments.
 * 
 * What are the arguments of a joinpoint depends on the type of the joinpoint itself.
 * For instance, if the intercepted joinpoint is a method execution, its arguments
 * are the method arguments.
 * 
 * If the optional <code>index</code> attribute is not defined, JBoss AOP will match
 * the <code>@Arg</code> annotated parameter with the joinpoint argument closest with
 * the closest type.
 * 
 * Look at the following example:
 * 
 * <code>
 * </code>
 * 
 * In this scenario, JBoss AOP will match
 * 
 * To learn how JBoss AOP will match Arg annotated parameters with joinpoint
 * arguments in more complex scenarios, please, refer to the Reference Manual
 * documentation.
 * 
 * @author Flavia Rainone
 */
@Target (ElementType.PARAMETER) @Retention(RetentionPolicy.RUNTIME)
public @interface Arg
{
   /**
    * Indicates which joinpoint argument this advice parameter refers to.
    * When this value is not set, JBoss AOP will select the first joinpoint
    * argument of the same type (or, if there is not such argument, the first
    * joinpoint argument of an assignable type to the advice parameter).
    */
   int index() default -1;
}