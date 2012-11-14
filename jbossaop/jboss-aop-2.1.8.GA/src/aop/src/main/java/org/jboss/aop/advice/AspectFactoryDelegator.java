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
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Element;

/**
 * Lazy loading of AspectFactory
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70830 $
 */
public class AspectFactoryDelegator extends AspectFactoryWithClassLoaderSupport
{
   private AspectFactory factory;
   private String factoryClass;
   private Element element;

   public AspectFactoryDelegator(String factory, Element element)
   {
      this.factoryClass = factory;
      this.element = element;
   }

   public String getName()
   {
      return factoryClass;
   }

   public Element getElement()
   {
      return element;
   }

   public void setElement(Element element)
   {
      this.element = element;
   }

   public synchronized AspectFactory getFactory()
   {
      if (factory == null)
      {
         try
         {
            Class<?> clazz = super.loadClass(factoryClass);
            factory = (AspectFactory) clazz.newInstance();
            if (XmlLoadable.class.isAssignableFrom(factory.getClass()))
            {
               ((XmlLoadable) factory).importXml(element);
            }
            if (AspectFactoryWithClassLoader.class.isAssignableFrom(factory.getClass()))
            {
               if (super.getLoader() != null)
               {
                  ((AspectFactoryWithClassLoader)factory).setClassLoader(super.getLoader());
               }
            }
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
         catch (InstantiationException e)
         {
            throw new RuntimeException(e);
         }
         catch (IllegalAccessException e)
         {
            throw new RuntimeException(e);
         }
      }
      return factory;
   }

   public Object createPerVM()
   {
      Object aspect = getFactory().createPerVM();
      return aspect;
   }

   public Object createPerClass(Advisor advisor)
   {
      Object aspect = getFactory().createPerClass(advisor);
      return aspect;
   }

   public Object createPerInstance(Advisor advisor, InstanceAdvisor instanceAdvisor)
   {
      Object aspect = getFactory().createPerInstance(advisor, instanceAdvisor);
      return aspect;
   }

   public Object createPerJoinpoint(Advisor advisor, Joinpoint jp)
   {
      Object aspect = getFactory().createPerJoinpoint(advisor, jp);
      return aspect;
   }

   public Object createPerJoinpoint(Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp)
   {
      Object aspect = getFactory().createPerJoinpoint(advisor, instanceAdvisor, jp);
      return aspect;
   }

}
