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
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.joinpoint.FieldJoinpoint;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71763 $
 */
public class AdviceFactory implements InterceptorFactory
{
   private String advice;
   private AspectDefinition aspect;
   private AdviceType type;
   
   /**
    * Creates a factory that generates advice interceptors.
    * Use this constructor if you want to record an advice of the 
    * (@link {@link AdviceType#AROUND default type}.
    * 
    * @param aspect     definition of the aspect that contains the advice method
    * @param adviceName name of the advice method
    */
   public AdviceFactory(AspectDefinition aspect, String adviceName)
   {
      this(aspect, adviceName, AdviceType.AROUND);
   }
   
   /**
    * Creates a factory that generates advice interceptors.
    * Use this constructor if you want specify the advice type.
    * 
    * @param aspect     definition of the aspect that contains the advice method
    * @param adviceName name of the advice method
    * @param type       the type of the advice
    */
   public AdviceFactory(AspectDefinition aspect, String adviceName, AdviceType type)
   {
      this.aspect = aspect;
      this.advice = adviceName;
      this.type = type;
   }
   
   /**
    * Returns the definition of the aspect that contains this advice method.
    */
   public AspectDefinition getAspect()
   {
      return aspect;
   }

   /**
    * Returns the name of the advice method.
    */
   public String getAdvice()
   {
      return advice;
   }

   /**
    * Returns the type of the advice.
    */
   public AdviceType getType()
   {
      return type;
   }
   
   
   public boolean isDeployed()
   {
      return aspect.isDeployed();
   }

   public Interceptor create(Advisor advisor, Joinpoint joinpoint)
   {
      if (aspect.getScope() == Scope.PER_VM)
      {
         try
         {
            return PerVmAdvice.generateOptimized(joinpoint, advisor.getManager(), advice, aspect);
         }
         catch (Exception e)
         {
            if (e instanceof RuntimeException)
            {
               throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
         }
      }
      else if (aspect.getScope() == Scope.PER_CLASS)
      {
         try
         {
            return PerClassAdvice.generate(joinpoint, advisor, advice, aspect);
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      else if (aspect.getScope() == Scope.PER_INSTANCE)
      {
         return new PerInstanceAdvice(advice, aspect, advisor);
      }
      else if (aspect.getScope() == Scope.PER_JOINPOINT)
      {
         try
         {
            return PerJoinpointAdvice.createInterceptor(advisor, joinpoint, aspect, advice);
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      else if (aspect.getScope() == Scope.PER_CLASS_JOINPOINT)
      {
         Object instance = null;
         
         if (advisor instanceof GeneratedClassAdvisor)
         {
            //Generated advisors handle PER_CLASS_JOINPOINT slightly differently
            instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(aspect, joinpoint);
            if (instance == null)
            {
               ((GeneratedClassAdvisor)advisor).addPerClassJoinpointAspect(aspect, joinpoint);
               instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(aspect, joinpoint);
            }
         }
         else if (joinpoint instanceof FieldJoinpoint)
         {
            FieldJoinpoint field = (FieldJoinpoint) joinpoint;
            ClassAdvisor classAdvisor = (ClassAdvisor) advisor;
            instance = classAdvisor.getFieldAspect(field, aspect);
         }
         else
         {
            instance = aspect.getFactory().createPerJoinpoint(advisor, joinpoint);   
         }
         
         try
         {
            return PerVmAdvice.generateInterceptor(joinpoint, instance, advice);
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }
      else
      {
         //if (aspect.getScope() == null) System.err.println("scope is null: " + aspect.getName() + "." + advice);
      }
      return null;
   }

   public String getName()
   {
      return aspect.getName() + "." + advice;
   }

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (!(obj instanceof AdviceFactory)) return false;
      
      AspectDefinition otherAspect = ((AdviceFactory)obj).getAspect();
      
      if (!this.aspect.getName().equals(otherAspect.getName())) return false;
      if (!this.aspect.getFactory().getName().equals(otherAspect.getFactory().getName()))return false;
      if (!this.advice.equals(((AdviceFactory)obj).advice))return false;

      return true;
   }
}