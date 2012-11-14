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
package org.jboss.aop;

import javassist.CtClass;

import org.jboss.aop.instrument.DynamicTransformationObserver;
import org.jboss.aop.instrument.JoinpointClassifier;


/**
 * Strategy for dealing with dynamic aop related operations.
 * Decides what must be done when a dynamic operation changes an
 * interceptor chain.
 * For dynamic operation we mean the addition or removal of:<ul>
 * <li> an <code>org.jboss.aop.advice.AdviceBinding</code>
 * to <code>org.jboss.aop.AspectManager</code>. </li>
 * <li> an interceptor or interceptor stack to <code>
 * org.jboss.aop.InstanceAdvisor</code>.</li>
 * </ul>
 * 
 * @author Flavia Rainone
 */
public interface DynamicAOPStrategy
{

   /**
    * Return an interceptor chain observer for <code>clazz</code>.
    * This observer is notified of any changes to an interceptor chain
    * related to <code>clazz</code>.
    * @param clazz the <code>clazz</code> whose interceptor chains will
    * be observed by the observer returned.
    * @return the interceptor chain observer for <code>clazz</code>. May
    * return null if the strategy doesn't require an observer at all.
    */
   InterceptorChainObserver getInterceptorChainObserver(Class<?> clazz);
   
   /**
    * Notifies the strategy that one or more interceptor chains in the
    * system may have been updated. It is up to this method to take
    * appropriate actions when this situation occurs.
    */
   void interceptorChainsUpdated();
   
   /**
    * Returns a joinpoint classifier compatible to the strategy.
    * This joinpoint classifier is the one that should be used by
    * <code>org.jboss.aop.instrument.Instrumentor</code> instances
    * to classify joinpoints. This classification is fundamental to
    * instrumentation algorithm to decide what to do to a joinpoint:
    * prepare for future interception instrumentation or
    *  instrument for interception.
    * @return an instance of <code>org.jboss.aop.instrument.JoinpointClassifier</code>.
    */
   JoinpointClassifier getJoinpointClassifier();
   
   /**
    * Returns a dynamic transformation observer to be notified of the dynamic
    * events during the <code>clazz</code> transformation.
    * @param clazz the clazz whose transformation must be observed.
    * @see DynamicTransformationObserver
    */
    DynamicTransformationObserver getDynamicTransformationObserver(CtClass clazz);
}