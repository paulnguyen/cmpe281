/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.aop.advice.InterceptorFactory;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ArrayBinding 
{
   protected String name;
   protected Type type;

   protected InterceptorFactory[] interceptorFactories = new InterceptorFactory[0];

   public ArrayBinding(String name, InterceptorFactory[] factories, Type type)
   {
      this.name = name;
      interceptorFactories = factories;
      this.type = type;
   }

   public void addInterceptorFactory(InterceptorFactory factory)
   {
      List<InterceptorFactory> list = Arrays.asList(interceptorFactories);
      list = new ArrayList<InterceptorFactory>(list);
      list.add(factory);
      interceptorFactories = list.toArray(new InterceptorFactory[list.size()]);
   }


   public String getName()
   {
      return name;
   }

   public InterceptorFactory[] getInterceptorFactories()
   {
      return interceptorFactories;
   }

   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof ArrayBinding)) return false;
      return ((ArrayBinding) obj).getName().equals(name);
   }

   public int hashCode()
   {
      return name.hashCode();
   }
   
   public boolean isRead()
   {
      return type == Type.READ_ONLY || type == Type.READ_WRITE;
   }

   public boolean isWrite()
   {
      return type == Type.WRITE_ONLY || type == Type.READ_WRITE;
   }
}
