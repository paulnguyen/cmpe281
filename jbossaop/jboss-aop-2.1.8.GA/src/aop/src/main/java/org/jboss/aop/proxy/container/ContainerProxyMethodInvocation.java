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

import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * MethodInvocation allowing storage of the generated proxy
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 44253 $
 */
public class ContainerProxyMethodInvocation extends MethodInvocation
{
   AspectManaged proxy;
   static final Object[] NO_ARGS = new Object[0];
   
   public ContainerProxyMethodInvocation(MethodInfo info, Interceptor[] interceptors, AspectManaged proxy)
   {
      super(info, interceptors);
      this.proxy = proxy;
   }

   public AspectManaged getProxy()
   {
      return proxy;
   }

   public Object[] getArguments()
   {
      Object[] args = super.getArguments();
      if (args != null)
      {
         return args;
      }
      return NO_ARGS;
   }

}
