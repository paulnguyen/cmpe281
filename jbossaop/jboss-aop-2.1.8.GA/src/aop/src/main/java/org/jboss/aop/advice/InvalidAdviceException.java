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

/**
 * Exception thrown when an advice method does not comply with one or more rules
 * regardless of the joinpoint to be intercepted.
 * <p>
 * If your application is throwing this exception, the solution is to fix the
 * advice method so that it follows the required rule.
 * 
 * @author Flavia Rainone
 */
public class InvalidAdviceException extends RuntimeException
{
   private static final long serialVersionUID = -7761721818447236098L;

   /**
    * Constructor.
    * <p>
    * @param message a message describing why the advice method is considered
    *                invalid. Message should specify clearly the rule that is broken,
    *                the advice method that is invalid, and its type (if the rule
    *                depends on the type).
    */
   public InvalidAdviceException(String message)
   {
      super(message);
   }
}