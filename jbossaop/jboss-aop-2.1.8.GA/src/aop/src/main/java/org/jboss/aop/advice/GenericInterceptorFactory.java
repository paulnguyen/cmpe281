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

import java.lang.ref.WeakReference;

import org.jboss.aop.Advisor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Element;

public class GenericInterceptorFactory implements InterceptorFactory
{
   private WeakReference<Class<?>> classRef = null;
   private String classname;
   private Element element;
   private boolean deployed = true;
   private String name;

   private static volatile int counter = 0;

   public GenericInterceptorFactory(String classname, Element element)
   {
      this.name = classname + (counter++);
      this.classname = classname;
      this.element = element;
   }

   public GenericInterceptorFactory(Class<?> clazz)
   {
      this.classRef = new WeakReference<Class<?>>(clazz);
      this.classname = clazz.getName();
   }

   public String getName()
   {
      return name;
   }

   public AspectDefinition getAspect()
   {
      return null;
   }

   public String getAdvice()
   {
      return "invoke";
   }
   public void undeploy() { deployed = false; }

   public boolean isDeployed()
   {
      return deployed;
   }

   public String getClassName() { return classname; }

   public Interceptor create(Advisor advisor, Joinpoint joinpoint)
   {
      try
      {
         Class<?> clazz = null;
         synchronized (this)
         {
            if (classRef != null)
            {
               clazz = classRef.get();
            }
            if (clazz != null)
            {
               ClassLoader cl = advisor.getClassLoader();
               if (cl == null)
               {
                  //Fall back to context classloader
                  cl = SecurityActions.getContextClassLoader();
               }
               clazz = cl.loadClass(classname);
               classRef = new WeakReference<Class<?>>(clazz);
            }
         }
         Interceptor interceptor = (Interceptor)clazz.newInstance();
         if (interceptor instanceof XmlLoadable && element != null)
         {
            ((XmlLoadable)interceptor).importXml(element);
         }
         return interceptor;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException)re;
         throw new RuntimeException(re);
      }
   }

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (!(obj instanceof GenericInterceptorFactory)) return false;
      if (!this.classname.equals(((GenericInterceptorFactory)obj).classname)) return false;
      
      return true;
   }

   public AdviceType getType()
   {
      return AdviceType.AROUND;
   }
}