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

import org.jboss.aop.Advisor;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * This SHOULD NOT be inherited by application code!!!!
 * This interface is used solely by JBoss AOP internally
 */
public interface InterceptorFactory
{
   /**
    * Returns the aspect definition, a record of the class that contains
    * the interceptor method.
    */
   public AspectDefinition getAspect();
   
   /**
    * Returns the advice method name.
    */
   public String getAdvice();
   
   /**
    * Creates an interceptor that represents this advice and that delegates
    * execution to this advice.
    * 
    * @param advisor   advisor, indicates an instance or class where the interception
    *                  will occur
    * @param joinpoint the joinpoint that will be intercepted
    * @return          an interceptor. Notice this can be an instance of the aspect
    *                  class itself, if this class is an interceptor.
    */
   public Interceptor create(Advisor advisor, Joinpoint joinpoint);
   
   /**
    * Indicates whether this interceptor/advice is deployed.
    */
   public boolean isDeployed();
   
   /**
    * Returns the name that identifies this interceptor/advice.
    * 
    * @return
    */
   public String getName();
   
   /**
    * Returns the type of this advice.
    */
   public AdviceType getType();   
}