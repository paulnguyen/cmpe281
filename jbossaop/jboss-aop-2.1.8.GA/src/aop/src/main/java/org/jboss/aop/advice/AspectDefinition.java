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

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.GeneratedClassAdvisor;

/**
 * Definition of an aspect or interceptor.
 * <br>
 * This class is used by JBoss AOP to manage all configured informations regarding
 * aspects and interceptors, and can be used to define new aspects and interceptors
 * dynamically.
 * 
 * 
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70826 $
 * @see AspectManager#addAspectDefinition(AspectDefinition)
 */
public class AspectDefinition
{
   /**
    * Name of the aspect. Identifies this aspect in its {@link Domain domain}.
    */
   protected String name;
   
   /**
    * Scope of the aspect, defines how many times it must be created during
    * execution.
    */
   protected Scope scope = Scope.PER_VM;
   
   /**
    * Aspect's factory, responsible for creating the aspect instances.
    */
   protected AspectFactory factory;
   
   /**
    * Indicates whether this definition has been deployed in the
    * {@link Domain domain}.
    */
   protected boolean deployed = true;

   /**
    * @deprecated should not call this directly
    */
   public Map<Advisor, Boolean> advisors = new WeakHashMap<Advisor, Boolean>();

   /**
    * Creates an aspect definition.
    * 
    * @param name    the name of the aspect. This name is used by the domain to
    *                identify the aspect, so it must be unique in the AOP
    *                {@link Domain domain}.
    * @param scope   the aspect scope, indicates how many aspects instances must
    *                be created during execution. Defaults to PER_VM if {@code null}.
    * @param factory factory responsible for creating the aspect instances
    * @see AspectFactory
    * @see GenericAspectFactory
    */
   public AspectDefinition(String name, Scope scope, AspectFactory factory)
   {

      this.name = name;
      this.scope = scope;
      this.factory = factory;
      if (this.scope == null) this.scope = Scope.PER_VM;
      if (this.name == null) this.name = this.factory.getName();
   }

   public AspectDefinition() {}

   /**
    * Undeploys the aspect definition from its domain.
    *
    */
   @SuppressWarnings("deprecation")
   public synchronized void undeploy()
   {
      if (advisors.size() > 0)
      {
         for (Iterator<Advisor> it = advisors.keySet().iterator() ; it.hasNext() ; )
         {
            Advisor advisor = it.next();
            if (advisor != null)
            {
               it.remove();
               if (scope == Scope.PER_INSTANCE)
               {
                  advisor.removePerInstanceAspect(this);
               }
               else if (scope == Scope.PER_JOINPOINT)
               {
                  advisor.removePerInstanceJoinpointAspect(this);
               }
               else if (scope == Scope.PER_CLASS)
               {
                  advisor.removePerClassAspect(this);
                  if (advisor instanceof GeneratedClassAdvisor)
                  {
                     //If it was a PER_JOINPOINT aspect for a static member, it might be listed under PER_CLASS_JOINPOINT as well
                     ((GeneratedClassAdvisor)advisor).removePerClassJoinpointAspect(this);
                  }
               }
               else if (scope == Scope.PER_CLASS_JOINPOINT)
               {
                  if (advisor instanceof GeneratedClassAdvisor)
                  {
                     //GeneratedClassAdvisors handle PER_CLASS_JOINPOINT aspects slightly differently
                     ((GeneratedClassAdvisor)advisor).removePerClassJoinpointAspect(this);
                  }
               }
            }
         }
         advisors.clear();
      }
      this.deployed = false;
   }
   
   /**
    * Returns {@code true} if this aspect definition is deployed in its
    * {@link Domain domain}.An aspect definition is considered to be deployed if it
    * is active in the domain, and can intercept joinpoints. It is not deployed when it
    * is inactive and won't intercept any joinpoints.
    * 
    * @return {@code true} if this aspect definition is active in its domain
    */
   public boolean isDeployed()
   {
      return deployed;
   }

   /**
    * Sets the name of this aspect definition. This name must be unique inside the
    * {@link Domain domain}.
    * 
    * @param name the new name of this aspect definition.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * Sets the scope of this aspect definition. The scope defines the lifecycle
    * of the aspect instances.
    * 
    * @param scope the new scope of this aspect definition.
    */
   public void setScope(Scope scope)
   {
      this.scope = scope;
   }

   /**
    * Sets the factory of this aspect definition, responsible for providing the
    * instances at runtime.
    * 
    * @param factory the new factory of this aspect definition
    */
   public void setFactory(AspectFactory factory)
   {
      this.factory = factory;
   }

   /**
    * Returns the factory of this aspect definition, responsible for providing
    * the instances at runtime.
    * 
    * @return the factory of this aspect definition
    */
   public AspectFactory getFactory()
   {
      return factory;
   }

   /**
    * Returns the name of this aspect definition. This name is unique inside the
    * {@link Domain domain}.
    * 
    * @return name the name that identifies this definition in its
    *             {@link Domain domain}
    */
   public String getName()
   {
      return name;
   }

   /**
    * Registers {@code advisor} as being a client of this definition. This means
    * that {@code advisor} uses an instance of the defined aspect for interception
    * of one or more joinpoints.
    * <p><i>For internal use only</i>
    * 
    * @param advisor an advisor responsible for managing joinpoints and their
    *                interception execution
    */
   @SuppressWarnings("deprecation")
   public synchronized void registerAdvisor(Advisor advisor)
   {
      advisors.put(advisor, Boolean.TRUE);
   }

   /**
    * Unregisters {@code advisor} as being a client of this definition. This means
    * that {@code advisor} no more uses an instance of the defined aspect for
    * interception.
    * <p><i>For internal use only</i>
    * 
    * @param advisor responsible for managing a set of joinpoints and their
    *                interception execution
    */
   @SuppressWarnings("deprecation")
   public synchronized void unregisterAdvisor(Advisor advisor)
   {
      advisors.remove(advisor);
   }
   
   /**
    * Returns the scope of this aspect definition. The scope defines how many
    * instances of the aspect are necessary at runtime.
    * 
    * @return scope the scope of this aspect definition.
    */
   public Scope getScope()
   {
      return scope;
   }

   @Override
   public int hashCode()
   {
      return name.hashCode();
   }

   /**
    * Compares this aspect definition with {@code obj} for equality. Returns
    * {@code true} if and only if {@code obj} is an aspect definition with the same
    * {@link #getName() name} as this one.
    * 
    * @param obj the obj for comparison.
    * @return    {@code true} if {@code obj} is an aspect definition with the same
    *            {@link #getName() name} as this one.
    */
   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof AspectDefinition)) return false;
      return name.equals(((AspectDefinition) obj).name);
   }
}