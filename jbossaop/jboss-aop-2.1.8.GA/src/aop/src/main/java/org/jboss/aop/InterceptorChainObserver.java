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


/**
 * Observes all the interceptor chains related to a class. 
 * There is only one <code>InterceptorChainObserver</code>
 * per advisor.
 * @author Flavia Rainone
 */
interface InterceptorChainObserver
{
   /**
    * This method must be called before any other notification method is invoked.
    * It notifies the initial state of the interceptor chains.
    * @param fieldReadInterceptors interceptor chains to be applied at fields' reads.
    * @param fieldWriteInterceptors interceptor chains to be applied at fields' writes.
    * @param constructorInterceptors interceptor chains to be applied at constructors' executions.
    * @param methodInterceptors interceptor chains to be applied at methods' executions.
    * @param clazz the reflection class whose joinpoints the interceptor chains will be applied to.
    */
   public void initialInterceptorChains(Class<?> clazz, FieldInfo[] fieldReadInfos, FieldInfo[] fieldWriteInfos,
         ConstructorInfo[] constructorInfos, MethodInterceptors methodInterceptors);

   /**
    * Notifies the observer that the class interceptor chains were updated.
    * @param newFieldReadInterceptors new interceptor chains to be applied at fields' reads.
    * @param newFieldWriteInterceptors new interceptor chains to be applied at fields' writes.
    * @param newConstructorInterceptors new interceptor chains to be applied at constructors' executions.
    * @param newMethodInterceptors new interceptor chains to be applied at methods' executions.
    */
   public void interceptorChainsUpdated(FieldInfo[] fieldReadInfos, FieldInfo[] fieldWriteInfos,
         ConstructorInfo[] constructorInfos, MethodInterceptors methodInterceptors);
   
   /**
    * Notifies that an interceptor was added to an instance of
    * the class associated to this observer.
    * @param instanceAdvisor <code>org.jboss.aop.InstanceAdvisor</code> of the intercepted instance.
    */
   public void instanceInterceptorAdded(InstanceAdvisor instanceAdvisor);

   /**
    * Notifies that <code>howMany</code> interceptors were added to an instance of
    * the class associated to this observer.
    * @param instanceAdvisor <code>org.jboss.aop.InstanceAdvisor</code> of the intercepted instance.
    * @param howMany the number of added interceptors.
    */
   public void instanceInterceptorsAdded(InstanceAdvisor instanceAdvisor, int howMany);
   
   /**
    * Notifies that an interceptor was removed from an instance of
    * the class associated to this observer.
    * @param instanceAdvisor <code>org.jboss.aop.InstanceAdvisor</code> of the intercepted instance.
    */
   public void instanceInterceptorRemoved(InstanceAdvisor instanceAdvisor);
   
   /**
    * Notifies that <code>howMany</code> interceptors were removed from an instance of
    * the class associated to this observer.
    * @param instanceAdvisor <code>org.jboss.aop.InstanceAdvisor</code> of the intercepted instance.
    * @param howMany the number of removed interceptors
    */
   public void instanceInterceptorsRemoved(InstanceAdvisor instanceAdvisor, int howMany);
   
   /**
    * Notifies that all interceptors of an instance of
    * the class associated to this observer were removed.
    * @param instanceAdvisor <code>org.jboss.aop.InstanceAdvisor</code> of the intercepted instance.
    */
   public void allInstanceInterceptorsRemoved(InstanceAdvisor instanceAdvisor);
}