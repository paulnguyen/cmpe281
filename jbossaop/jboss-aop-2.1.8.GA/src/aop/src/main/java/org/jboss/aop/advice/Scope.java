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
 * Defines the lifecycle of an aspect or interceptor instance.
 * <br>
 * When not specified, the default scope of an aspect or interceptor is
 * {@code PER_VM}.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 68585 $
 */
public enum Scope
{
   /**
    * A single instance of the aspect will be created to intercept any joinpoints
    * during the Java VM execution.
    */
   PER_VM,
   
   /**
    * An instance of the aspect will be created for each advised class.
    */
   PER_CLASS,
   
   /**
    * An instance of the aspect will be created for each advised instance.
    */
   PER_INSTANCE,
   
   /**
    * An instance of the aspect will be created for each advised joinpoint execution.
    */
   PER_JOINPOINT,
   
   /**
    * An instance of the aspect will be created for each advised joinpoint. Notice
    * that the same instance will be used for all executions of the same joinpoint.
    */
   PER_CLASS_JOINPOINT
}