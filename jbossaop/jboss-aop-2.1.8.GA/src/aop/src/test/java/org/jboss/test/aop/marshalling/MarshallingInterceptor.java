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
package org.jboss.test.aop.marshalling;

import java.rmi.MarshalledObject;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 38811 $
 */
public class MarshallingInterceptor implements Interceptor
{

   public String getName()
   {
      return "MarshallingInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      Object[] args = ((MethodInvocation)invocation).getArguments();
      
      MarshalledObject mo = new MarshalledObject(invocation);
      MethodInvocation inv = (MethodInvocation)mo.get();
      
      Object[] margs = inv.getArguments();
      
      if (args != null)
      {
         if (margs == null) throw new RuntimeException("Unmarshalled args were null");
         if (margs.length != args.length) throw new RuntimeException("Wrong length, expected " + args.length + ", actual " + margs.length);
         for (int i = 0 ; i < args.length ; i++)
         {
            if (!args[i].equals(margs[i]))
            {
               throw new RuntimeException(i + " expcted " + args[i] + ", actual " + margs[i]);
            }
         }
      }
      
      return invocation.invokeNext();
   }
   
}
