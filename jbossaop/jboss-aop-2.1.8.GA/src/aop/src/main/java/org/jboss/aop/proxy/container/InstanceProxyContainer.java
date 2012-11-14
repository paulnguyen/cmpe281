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
package org.jboss.aop.proxy.container;

import org.jboss.aop.Advisor;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.Domain;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.InstanceAdvisorDelegate;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.metadata.SimpleMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * The InstanceAdvisor returned by ClassProxyContainer
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 73394 $
 */
public class InstanceProxyContainer extends ClassProxyContainer implements InstanceAdvisor
{
   Advisor classAdvisor;
   InstanceAdvisorDelegate delegate;

   public InstanceProxyContainer(String name, ProxyAdvisorDomain instanceDomain, Advisor classAdvisor, MetaData metaData)
   {
      super(name, instanceDomain);
      this.classAdvisor = classAdvisor;
      instanceDomain.setAdvisor(this);
      setMetadata(metaData);
      delegate = new InstanceAdvisorDelegate(classAdvisor, this);
      delegate.initialize();
      initialise(classAdvisor.getClazz());
   }

   public static InstanceProxyContainer createInstanceProxyContainer(Advisor classAdvisor, InterfaceIntroduction introduction, MetaData metaData)
   {
      String name = Domain.getDomainName(classAdvisor.getClazz(), true);
      ProxyAdvisorDomain domain = new ProxyAdvisorDomain(classAdvisor.getManager(), name, classAdvisor.getClazz(), true);
      if (introduction != null)
      {
         domain.addInterfaceIntroduction(introduction);
      }

      InstanceProxyContainer ia = new InstanceProxyContainer(classAdvisor.getName(), domain, classAdvisor, metaData);

      return ia;
   }

   public Advisor getClassAdvisor()
   {
      return classAdvisor;
   }


   public Object getPerInstanceAspect(String aspectName)
   {
      return delegate.getPerInstanceAspect(aspectName);
   }

   public Object getPerInstanceAspect(AspectDefinition def)
   {
      return delegate.getPerInstanceAspect(def);
   }

   public Object getPerInstanceJoinpointAspect(Joinpoint joinpoint, AspectDefinition def)
   {
      return delegate.getPerInstanceJoinpointAspect(joinpoint, def);
   }

   public SimpleMetaData getMetaData()
   {
      return delegate.getMetaData();
   }

   public Domain getDomain()
   {
      return (Domain)super.getManager();
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public boolean hasInterceptors()
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public Interceptor[] getInterceptors()
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public Interceptor[] getInterceptors(Interceptor[] baseChain)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void insertInterceptor(Interceptor interceptor)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void removeInterceptor(String name)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void appendInterceptor(Interceptor interceptor)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void insertInterceptorStack(String stackName)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void removeInterceptorStack(String name)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void appendInterceptorStack(String stackName)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void insertInterceptor(int index, Interceptor interceptor)
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * Not implemented InstanceAdvisor method, we should be using the domain
    */
   public void appendInterceptor(int index, Interceptor interceptor)
   {
      throw new RuntimeException("Not implemented");
   }

   public void removeFromDomain()
   {
      ProxyAdvisorDomain domain = (ProxyAdvisorDomain)super.manager;
      domain.removeAdvisor(this);
   }

   public Object getInstance()
   {
      throw new RuntimeException("Not implemented");
   }

   /**
    * If the target instance is advised, make sure that we set the unadvised method to the one stored by the class advisor
    * so that we don't get double invocations.
    */
   @Override
   public MethodInfo getMethodInfo(long hash)
   {
      MethodInfo info = super.getMethodInfo(hash);
      if (classAdvisor instanceof ClassAdvisor && info.getMethod().equals(info.getUnadvisedMethod()))
      {
         MethodInfo superInfo = classAdvisor.getMethodInfo(hash);
         if (superInfo != null)
         {
            info.setUnadvisedMethod(superInfo.getUnadvisedMethod());
         }
      }

      return info;
   }

   @Override
   protected Advisor getParentAdvisor()
   {
      return classAdvisor;
   }

}
