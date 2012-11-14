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

import org.jboss.aop.pointcut.DynamicCFlow;
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Element;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77243 $
 */
public class DynamicCFlowDefinition
{
   private String name;
   private String className;
   private Element element;
   private Class<?> pClass;

   public DynamicCFlowDefinition(Element element, String className, String name)
   {
      this.className = className;
      this.element = element;
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public String getClassName()
   {
      return className;
   }

   public DynamicCFlow create(ClassLoader cl)
   {
      if (pClass == null)
      {
         try
         {
            pClass = cl.loadClass(className);
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException("dynamic cflow class not found: " + className);
         }
      }
      try
      {
         DynamicCFlow cflow = (DynamicCFlow) pClass.newInstance();
         if (cflow instanceof XmlLoadable)
         {
            ((XmlLoadable) cflow).importXml(element);
         }
         return cflow;
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

}
