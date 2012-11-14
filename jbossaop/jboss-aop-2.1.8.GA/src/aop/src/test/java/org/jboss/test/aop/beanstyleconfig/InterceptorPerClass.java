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
package org.jboss.test.aop.beanstyleconfig;

import org.jboss.aop.Advisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class InterceptorPerClass implements Interceptor
{
   private int intAttr;
   private String stringAttr;
   private Advisor advisor;
   private InstanceAdvisor instanceAdvisor;
   private Joinpoint joinpoint;


   /**
    * @return Returns the advisor.
    */
   public Advisor getAdvisor()
   {
      return advisor;
   }

   /**
    * @param advisor The advisor to set.
    */
   public void setAdvisor(Advisor advisor)
   {
      this.advisor = advisor;
   }

   /**
    * @return Returns the instanceAdvisor.
    */
   public InstanceAdvisor getInstanceAdvisor()
   {
      return instanceAdvisor;
   }

   /**
    * @param instanceAdvisor The instanceAdvisor to set.
    */
   public void setInstanceAdvisor(InstanceAdvisor instanceAdvisor)
   {
      this.instanceAdvisor = instanceAdvisor;
   }

   /**
    * @return Returns the intAttr.
    */
   public int getIntAttr()
   {
      return intAttr;
   }

   /**
    * @param intAttr The intAttr to set.
    */
   public void setIntAttr(int intAttr)
   {
      this.intAttr = intAttr;
   }

   /**
    * @return Returns the joinpoint.
    */
   public Joinpoint getJoinpoint()
   {
      return joinpoint;
   }

   /**
    * @param joinpoint The joinpoint to set.
    */
   public void setJoinpoint(Joinpoint joinpoint)
   {
      this.joinpoint = joinpoint;
   }

   /**
    * @return Returns the stringAttr.
    */
   public String getStringAttr()
   {
      return stringAttr;
   }

   /**
    * @param stringAttr The stringAttr to set.
    */
   public void setStringAttr(String stringAttr)
   {
      this.stringAttr = stringAttr;
   }

   public String getName()
   {
      return this.getClass().getName();
   }

   /* (non-Javadoc)
    * @see org.jboss.aop.advice.Interceptor#invoke(org.jboss.aop.joinpoint.Invocation)
    */
   public Object invoke(Invocation invocation) throws Throwable
   {
      InvokedConfigs.addInvokedConfig(this.getClass().getName(), advisor, instanceAdvisor, joinpoint, intAttr, stringAttr);
      return invocation.invokeNext();
   }

}
