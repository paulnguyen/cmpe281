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
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * Creates aspect instances.
 * <p>
 * This interface must be implemented by all aspect factories. Declare aspect
 * factories instead of the real aspect class when you want:
 * <ul>
 * <li> to perform some specific action during aspect instance creation  (like some
 *      initialization that could not be performed by the aspect constructor,
 *      for example);</li>
 * <li> to create instances of different classes, according to the context where
 *      the aspect will be invoked.</li>
 * <li> or to do anything else that could not be done without implementing a factory;
 * </li>
 * </ul>
 * <p>
 * This interface provides different methods. However, only the one(s) correspondent
 * to the {@link Scope} of the aspect will be called. Hence, when writing an aspect
 * factory whose scope is known (and is not intended to change), the implemention of
 * the other methods can be empty. However, we advise throwing a runtime exception
 * from those methods instead of simply returning {@code null}, to alert for
 * unpredicted scenarios in case the {@code Scope} value is not the expected.  
 * <p>
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 73173 $
 */
public interface AspectFactory
{
   /**
    * Creates an aspect with scope value {@link Scope#PER_VM}.
    * 
    * @return the single aspect instance that will be invoked for all applicable
    *         joinpoints during  Java VM execution. If {@code null}, the aspect
    *         represented by this factory is ignored, resulting in no interception.
    */
   Object createPerVM();
   
   /**
    * Creates an aspect with scope value {@link Scope#PER_CLASS}.
    * 
    * @param advisor manages all the interceptions that should occur during execution
    *                of a specific class
    * @return        the aspect instance that will be invoked for all applicable
    *                joinpoints contained in the class managed by {@code advisor}. If
    *                {@code null}, the aspect represented by this factory is ignored,
    *                resulting in no interception of the joinpoints contained in the
    *                referred class.
    * @see Advisor#getClazz() 
    */
   Object createPerClass(Advisor advisor);
   
   /**
    * Creates an aspect with scope value {@link Scope#PER_INSTANCE}.
    * 
    * @param advisor         manages all the interceptions that should occur during
    *                        execution of a specific class
    * @param instanceAdvisor manages all the interceptions that should occur during
    *                        execution of a specific instance. The instance it
    *                        manages is an object of the class managed by
    *                        {@code advisor}
    * @return                the aspect instance that will be invoked for all
    *                        applicable joinpoints contained in the instance managed
    *                        by {@code instanceAdvisor}. If {@code null}, the aspect
    *                        represented by this factory is ignored, resulting in no
    *                        interception of the joinpoints contained in the referred
    *                        instance.
    * @see Advisor#getClazz()
    * @see InstanceAdvisor#getInstance()
    */
   Object createPerInstance(Advisor advisor, InstanceAdvisor instanceAdvisor);
   
   /**
    * Creates an aspect with scope value {@link Scope#PER_CLASS_JOINPOINT} or
    * {@link Scope#PER_JOINPOINT}.
    * <br>
    * In case the scope value is {@code PER_CLASS_JOINPOINT}, this method will always
    * be invoked to create the aspect instance. On the other hand, if the scope value
    * is {@code PER_JOINPOINT}, this method is called only if the joinpoint
    * to be intercepted is in a static context (like a static method, a static field
    * access, or a constructor execution).
    * 
    * @param advisor manages all the interceptions that should occur during
    *                execution of a specific class
    * @param jp      the joinpoint to be intercepted by the created instance.
    *                This joinpoint is contained in the class managed by
    *                {@code advisor}
    * @return        the aspect instance that will be invoked only to intercept
    *                {@code jp}. If {@code null}, the aspect represented by this
    *                factory is ignored, resulting in no interception of {@code jp}.
    * @see Advisor#getClazz()
    * @see Joinpoint
    */
   Object createPerJoinpoint(Advisor advisor, Joinpoint jp);
   
   /**
    * Creates an aspect with scope value or {@link Scope#PER_JOINPOINT}.
    * <br>
    * This method is called only if the joinpoint to be intercepted is not in a
    * static context (like a non-static method, for example).
    * 
    * @param advisor         manages all the interceptions that should occur
    *                        during execution of a specific class
    * @param instanceAdvisor manages all the interceptions that should occur during
    *                        execution of a specific instance. The instance it
    *                        manages is an object of the class managed by
    *                        {@code advisor}
    * @param jp              the joinpoint to be intercepted by the created instance.
    *                        This joinpoint is contained in the class managed by
    *                        {@code advisor}
    * @return                the aspect instance that will be invoked only to
    *                        intercept {@code jp} when it happens on the instance
    *                        managed by {@code instanceAdvisor}. If {@code null}, the
    *                        aspect represented by this factory is ignored, resulting
    *                        in no interception of {@code jp} when it is executed in
    *                        the context of the referred instance.
    * @see Advisor#getClazz()
    * @see InstanceAdvisor#getInstance()
    * @see Joinpoint
    */
   Object createPerJoinpoint(Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp);

   /**
    * The name that identifies the aspect in its domain. Typically, the name is the
    * name of the class.
    * 
    * @return the name that identifies the aspect in its domain.
    * @see org.jboss.aop.Domain
    */
   String getName();
}