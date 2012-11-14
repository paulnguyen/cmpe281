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
package org.jboss.aop.hook;

import org.jboss.aop.AspectManager;

import com.bea.jvm.ClassLibrary;
import com.bea.jvm.ClassPreProcessor;
import com.bea.jvm.JVMFactory;

public class JRockitClassPreProcessor implements ClassPreProcessor
{

   static
   {
      //pre-load necessary classes
      Class clazz = JDK14TransformerManager.class;
      clazz = AspectManager.class;
   }

   public JRockitClassPreProcessor()
   {
      ClassLibrary lib = JVMFactory.getJVM().getClassLibrary();
      lib.setClassPreProcessor(this);
   }

   public byte[] preProcess(ClassLoader loader, String classname, byte[] bytes)
   {
      classname = classname.replace('/', '.');
      if (JDK14TransformerManager.isNonAdvisableClassName(classname))
      {
         return bytes;
      }

      try
      {
         byte[] result = AspectManager.instance().translate(classname, loader, bytes);
         return result == null? bytes: result;
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}