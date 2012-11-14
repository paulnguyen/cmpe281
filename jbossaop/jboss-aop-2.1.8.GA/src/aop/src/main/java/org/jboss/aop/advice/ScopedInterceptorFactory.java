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
 * @version $Revision: 74378 $
 */
public class ScopedInterceptorFactory implements InterceptorFactory
{
   private AspectDefinition aspect;

   public ScopedInterceptorFactory(AspectDefinition aspect)
   {
      this.aspect = aspect;
   }

   public AspectDefinition getAspect()
   {
      return aspect;
   }

   public String getAdvice()
   {
      return "invoke";
   }
   
   public AdviceType getType()
   {
      return AdviceType.AROUND;
   }

   public boolean isDeployed()
   {
      return aspect.isDeployed();
   }

   public Interceptor create(Advisor advisor, Joinpoint joinpoint)
   {
      if (aspect.getScope() == Scope.PER_VM)
      {
         return (Interceptor) advisor.getManager().getPerVMAspect(aspect);
      }
      else if (aspect.getScope() == Scope.PER_CLASS)
      {
         Interceptor interceptor = advisor.getAdviceInterceptor(aspect, "invoke", null);
         if (interceptor != null) return interceptor;
         advisor.addPerClassAspect(aspect);
         interceptor = (Interceptor)advisor.getPerClassAspect(aspect);
         advisor.addAdviceInterceptor(aspect, "invoke", interceptor, null);
         return interceptor;
         
      }
      else if (aspect.getScope() == Scope.PER_INSTANCE)
      {
         return new PerInstanceInterceptor(aspect, advisor);
      }
      else if (aspect.getScope() == Scope.PER_JOINPOINT)
      {
         try
         {
            return PerJoinpointInterceptor.createInterceptor(advisor, joinpoint, aspect);
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      else if (aspect.getScope() == Scope.PER_CLASS_JOINPOINT)
      {
         if (advisor instanceof GeneratedClassAdvisor)
         {
            //Generated advisors handle PER_CLASS_JOINPOINT slightly differently
            Object icptr = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(aspect, joinpoint);
            if (icptr == null)
            {
               ((GeneratedClassAdvisor)advisor).addPerClassJoinpointAspect(aspect, joinpoint);
               icptr = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(aspect, joinpoint);
            }
            return (Interceptor)icptr;
         }
         else if (joinpoint instanceof FieldJoinpoint)
         {
            FieldJoinpoint field = (FieldJoinpoint) joinpoint;
            ClassAdvisor classAdvisor = (ClassAdvisor) advisor;
            return (Interceptor) classAdvisor.getFieldAspect(field, aspect);
         }
         
         return (Interceptor) aspect.getFactory().createPerJoinpoint(advisor, joinpoint);
      }
      else
      {
         //if (aspect.getScope() == null) System.err.println("scope is null: " + aspect.getName() + "." + advice);
      }
      return null;
   }

   public String getName()
   {
      return aspect.getName();
   }
   
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (!(obj instanceof ScopedInterceptorFactory)) return false;
      
      AspectDefinition otherAspect = ((ScopedInterceptorFactory)obj).getAspect();
      
      if (!this.aspect.getName().equals(otherAspect.getName())) return false;
      if (!this.aspect.getFactory().getName().equals(otherAspect.getFactory().getName()))return false;

      return true;
   }
}
