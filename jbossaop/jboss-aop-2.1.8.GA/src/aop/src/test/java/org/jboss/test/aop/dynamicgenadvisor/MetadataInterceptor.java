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
package org.jboss.test.aop.dynamicgenadvisor;

import java.util.ArrayList;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
@SuppressWarnings({"unchecked"})
public class MetadataInterceptor implements Interceptor
{
   //For instances
   public static ArrayList lastDefaultMetadata = new ArrayList();
   public static ArrayList lastClassMetadata = new ArrayList();
   public static ArrayList lastMethodMetadata = new ArrayList();
   public static ArrayList lastFieldMetadata = new ArrayList();
   
   public static boolean intercepted;

   public String getName()
   {
      return "MetadataInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      System.out.println("MetadataInterceptor Intercepting");
      intercepted = true;
      
      Object meta = invocation.getMetaData("TEST", "default");
      if (meta != null) lastDefaultMetadata.add(meta);
      
      meta = invocation.resolveClassMetaData("TEST", "class");
      if (meta != null) lastClassMetadata.add(meta);

      if (invocation instanceof FieldInvocation)
      {
         meta = invocation.getMetaData("TEST", "fm");
         if (meta != null) lastFieldMetadata.add(meta);
      }
      else if (invocation instanceof MethodInvocation)
      {
         meta = invocation.getMetaData("TEST", "fm");
         if (meta != null) lastMethodMetadata.add(meta);
      }
      
      return invocation.invokeNext();
   }
   
   public static void clear()
   {
      lastClassMetadata.clear();
      lastDefaultMetadata.clear();
      lastFieldMetadata.clear();
      lastMethodMetadata.clear();
      intercepted = false;
   }

}
