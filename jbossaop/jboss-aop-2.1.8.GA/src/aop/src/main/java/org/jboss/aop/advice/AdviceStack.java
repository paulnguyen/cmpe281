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
import org.jboss.aop.joinpoint.Joinpoint;

import java.util.ArrayList;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 73173 $
 */
public class AdviceStack
{
   protected String name;
   protected ArrayList<InterceptorFactory> interceptorFactories;

   public AdviceStack(String name, ArrayList<InterceptorFactory> factories)
   {
      this.name = name;
      interceptorFactories = factories;
   }

   public String getName() { return name; }

   public ArrayList<InterceptorFactory> getInterceptorFactories() { return interceptorFactories; }


   public Interceptor[] createInterceptors(Advisor advisor, Joinpoint jp)
   {
      ArrayList<Interceptor> interceptors = new ArrayList<Interceptor>();
      for (int i = 0; i < interceptorFactories.size(); i++)
      {
         InterceptorFactory factory = interceptorFactories.get(i);
         if (factory.isDeployed())
         {
            Interceptor interceptor = factory.create(advisor, jp);
            if (interceptor != null)
            {
               interceptors.add(interceptor);
            }
         }
      }
      return interceptors.toArray(new Interceptor[interceptors.size()]);
   }

   public Interceptor[] createInterceptors()
   {
      return createInterceptors(null, null);
   }
}
