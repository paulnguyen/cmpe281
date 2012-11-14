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

import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtField;

import org.jboss.aop.instrument.DynamicTransformationObserver;
import org.jboss.aop.instrument.JoinpointClassifier;
import org.jboss.aop.instrument.JoinpointSimpleClassifier;


/**
 * Dynamic AOP Strategy that instruments prepared classes code
 * for interception before they get loaded in the system.
 * This way, no change to a class code has to be made during runtime. 
 * @author Flavia Rainone
 */
class LoadInterceptedClassesStrategy implements DynamicAOPStrategy
{
   /**
    * Dummy DynamicTransformationObserver. No action is need when a dynamic
    * transformation occurs.
    */
   private DynamicTransformationObserver dynamicTransformationObserver = 
      new DynamicTransformationObserver(){
      public void fieldReadDynamicalyWrapped(CtField field) {}
      public void fieldWriteDynamicalyWrapped(CtField field) {}
      public void constructorDynamicalyWrapped() {}
      public void transformationFinished(CtClass clazz, CodeConverter converter) {}
   };
   
   /**
    * Simple classifier to be used during transformation.
    */
   JoinpointClassifier joinpointClassifier = new JoinpointSimpleClassifier();

   /**
    * Returns <code>null</code> because, as long as no runtime action is required
    * in this strategy, no observer is required.
    * @see org.jboss.aop.DynamicAOPStrategy#getInterceptorChainObserver(Class)
    */
   public InterceptorChainObserver getInterceptorChainObserver(Class<?> clazz)
   {
      return null;
   }
   
   /**
    * Notifies the strategy that one or more interceptor chains in the
    * system may have been updated.
    * This method does nothing because no runtime action is required.
    * @see org.jboss.aop.DynamicAOPStrategy#interceptorChainsUpdated()
    */
   public void interceptorChainsUpdated() { }
   
   /**
    * Returns a <code>org.jboss.aop.instrument.JoinpointSimpleClassifier</code>
    * instance. This classifier assures that prepared classes will be fully instrumented
    * for interception before they get loaded in the system.
    * @see org.jboss.aop.DynamicAOPStrategy#getJoinpointClassifier()
    */
   public JoinpointClassifier getJoinpointClassifier()
   {
      return this.joinpointClassifier;
   }
   
   /**
    * Returns a dynamic transformation observer to be notified of the dynamic
    * events during the <code>clazz</code> transformation. This observer does nothing
    * because no action is required when a dynamic transformation happens.
    * @see org.jboss.aop.DynamicAOPStrategy#getJoinpointClassifier()
    */
   public DynamicTransformationObserver getDynamicTransformationObserver(CtClass clazz)
   {
      return this.dynamicTransformationObserver; 
   }
}