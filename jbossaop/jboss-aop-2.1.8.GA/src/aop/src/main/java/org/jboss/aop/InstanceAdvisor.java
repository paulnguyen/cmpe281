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

import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * Holds an object instance's metadata and attached interceptor chain.
 * <br>
 * The interceptor chain attached to an advised object contains interceptors that are
 * applied to every method execution and field access joinpoint of that object.
 * At startup, the instance interceptor chain is always empty. This chain is
 * specially tailored for per-instance dynamic AOP operations, allowing the addition
 * and removal of interceptors at runtime. The order in which the interceptors are
 * invoked depends on which method was used to add them to the chain (for more
 * information, see {@link #insertInterceptor } and {@link #appendInterceptor}
 * methods). 
 * <br>
 * Notice that every interceptor chain operation provided by an instance
 * advisor affects only the advised object interceptor chain, and hence it will not
 * affect other advised instances, regardless of whether they belong to the same
 * class or not. 
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 68585 $
 */
public interface InstanceAdvisor
{
   /**
    * Returns the metadata bound to the advised instance.
    * 
    * @return the metadata bound to the advised instance.
    */
   public SimpleMetaData getMetaData();

   /**
    * Returns the domain where this advisor belongs. The domain contains all the
    * configured AOP features (like bindings, aspects, and introductions) that should
    * be applied to the advised instance.
    * 
    * @return the domain bound to this advisor
    */
   public Domain getDomain();
   
   /**
    * Returns the advised instance managed by this advisor.
    * 
    * @return the advised instance
    */
   public Object getInstance();
   
   /**
    * Indicates if the advised instance interceptor chain is not empty.
    * 
    * @return {@code true} if the advised instance interceptor chain contains one
    *         or more elements.
    */
   public boolean hasInterceptors(); 
   
   /**
    * Indicates whether there are instance aspects bound to the instance advised.
    * In other words, this method returns the same as {@link #hasInterceptors()}.
    * 
    * @return {@code true} if there is one or more aspects bound to this advised
    *         instance.
    */
   public boolean hasAspects();

   /**
    * Inserts an interceptor at the beginning of the instance's interceptor chain.
    * 
    * @param interceptor the interceptor to be added to the instance's chain.
    */
   public void insertInterceptor(Interceptor interceptor);

   /**
    * Inserts an interceptor at position {@code index} of the inserted instance's
    * interceptor chain.
    * 
    * @param index the position where to insert {@code interceptor}. This value must
    *        not be greater than or equal to the number of inserted interceptors in
    *        the chain.
    * @param interceptor the interceptor to be added to the instance's chain.
    * @throws IndexOutOfBoundsException if {@code index} is greater than or equal to
    *         the total number of inserted interceptors contained in this advisor, or
    *         if {@code index} is a negative value
    */
   void insertInterceptor(int index, Interceptor interceptor)
      throws IndexOutOfBoundsException;

   /**
    * Inserts an interceptor stack to the beginning of the instance's interceptor
    * chain.
    * 
    * @param stackName the name that identifies the interceptor stack to be inserted
    */
   public void insertInterceptorStack(String stackName);

   /**
    * Appends an interceptor to the end of the instance's interceptor chain.
    * 
    * @param interceptor the interceptor to be appended to the instance's chain
    */
   public void appendInterceptor(Interceptor interceptor);

   /**
    * Appends an interceptor at position {@code index} of the appended instance's
    * interceptor chain.
    * 
    * @param index the position where to insert {@code interceptor}. This value must
    *        not be greater than or equal to the number of appended interceptors in
    *        the chain.
    * @param interceptor the interceptor to be added to the instance's chain.
    * @throws IndexOutOfBoundsException if {@code index} is greater than or equal to
    *         the total number of appended interceptors contained in this advisor, or
    *         if {@code index} is a negative value
    */
   void appendInterceptor(int index, Interceptor interceptor)
      throws IndexOutOfBoundsException;

   /**
    * Appends an interceptor chain to the end of the instance's interceptor chain.
    * 
    * @param stackName the name that identifies the interceptor stack to be appended
    */
   public void appendInterceptorStack(String stackName);

   /**
    * Removes an interceptor from instance's interceptor chain.
    * 
    * @param name name of the interceptor to be removed from the chain.
    * @see Interceptor#getName()
    */
   public void removeInterceptor(String name);

   /**
    * Removes an interceptor stack from the instance's interceptor chain.
    * 
    * @param name the name that identifies the interceptor stack to be removed
    */
   public void removeInterceptorStack(String name);

   /**
    * Returns the interceptor chain of the advised instance.
    * <p>
    * <i>For internal use only.</i>
    * 
    * @return the interceptor chain of the advised instance.
    */
   public Interceptor[] getInterceptors();

   /**
    * Merges the advised instance interceptor chain with {@code baseChain}.
    * <br>
    * The result of the merge will contain first all the inserted interceptors,
    * then {@code baseChain}, and finally all the appended interceptors.
    * <p>
    * <i>For internal use only.</i>
    * 
    * @param  baseChain the chain to be joined with the advised instance's chain.
    * @return a new interception chain as a result of the merge of {@code baseChain}
    *         with the instance's chain. May return {@code baseChain} if the last one
    *         is empty.
    */
   public Interceptor[] getInterceptors(Interceptor[] baseChain);

   /**
    * Returns a per instance aspect object identified by {@code aspectName}.
    * <br>
    * Notice that the aspect objects are created by JBoss AOP, either by using an
    * aspect factory or by using the constructor of an aspect class. 
    * <p><i>For internal use only.</i>
    * 
    * @param  aspectName name of the queried aspect
    * @return the per instance aspect named {@code aspectName}
    */
   public Object getPerInstanceAspect(String aspectName);

   /**
    * Returns a per instance aspect object defined by {@code def}.
    * <br>
    * Notice that the aspect objects are created by JBoss AOP, either by using an
    * aspect factory or by using the constructor of an aspect class. 
    * <p><i>For internal use only.</i>
    * 
    * @param  def definition of the queried aspect 
    * @return the per instance aspect defined by {@code def}
    */
   public Object getPerInstanceAspect(AspectDefinition def);

   /**
    * Returns the per instance joinpoint aspect object defined by {@code def} to be
    * applied at {@code joipoint}.
    * <br>
    * Notice that the aspect objects are created by JBoss AOP, either by using an
    * aspect factory or by using the constructor of an aspect class. 
    * <p><i>For internal use only.</i>
    * 
    * @param joinpoint the joinpoint to which the aspect object will be applied.
    * @param  def      definition of the queried aspect 
    * @return the per instance joinpoint aspect defined by {@code def} to be applied
    *             at {@code joinpoint}
    */
   public Object getPerInstanceJoinpointAspect(Joinpoint joinpoint, AspectDefinition def);
}