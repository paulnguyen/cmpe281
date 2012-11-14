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
package org.jboss.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.InvocationResponse;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.proxy.ClassProxy;
import org.jboss.aop.proxy.ClassProxyFactory;
import org.jboss.aop.proxy.Proxy;
import org.jboss.aop.util.reference.MethodPersistentReference;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71279 $
 */

public class Dispatcher
{
   public static final String DISPATCHER = "DISPATCHER";
   public static final String OID = "OID";
   public static final Dispatcher singleton = new Dispatcher();

   Map<Object, Object> targetMap = new ConcurrentHashMap<Object, Object>();


   public boolean isRegistered(Object oid)
   {
      return targetMap.containsKey(oid);
   }

   /**
    * Register an Object ID with an actual target object
    */
   public void registerTarget(Object oid, Object target)
   {
      targetMap.put(oid, target);
   }

   public void unregisterTarget(Object oid)
   {
      targetMap.remove(oid);
   }

   public Object getRegistered(Object oid)
   {
      return targetMap.get(oid);
   }


   public InvocationResponse invoke(Invocation invocation) throws NotFoundInDispatcherException, Throwable
   {
      Object oid = invocation.getMetaData(DISPATCHER, OID);

      Object target = null;
      target = targetMap.get(oid);

      if (target == null)
      {
         throw new NotFoundInDispatcherException(oid);
      }

      if (target instanceof ClassProxy)
      {
         ClassProxy proxy = (ClassProxy) target;
         return proxy._dynamicInvoke(invocation);
      }
      else if (target instanceof Proxy)
      {
         Proxy proxy = (Proxy) target;
         return proxy._dynamicInvoke(invocation);
      }
      else if (target instanceof Advised)
      {
         Advisor advisor = ((Advised) target)._getAdvisor();
         return advisor.dynamicInvoke(target, invocation);
      }
      else if (target instanceof Advisor)
      {
         Advisor advisor = (Advisor) target;
         return advisor.dynamicInvoke(null, invocation);
      }
      else
      {
         if (invocation instanceof MethodInvocation)
         {
            MethodInvocation methodInvocation = (MethodInvocation) invocation;
            // For non-advised methods, we can only do public method invocations
            long methodHash = methodInvocation.getMethodHash();
            HashMap<Long, MethodPersistentReference> methodMap = ClassProxyFactory.getMethodMap(target.getClass());
            MethodPersistentReference ref = methodMap.get(new Long(methodHash));
            Method method = (Method)ref.get();
            Object[] args = methodInvocation.getArguments();
            try
            {
               return new InvocationResponse(method.invoke(target, args));
            }
            catch (InvocationTargetException ex)
            {
               throw ex.getTargetException();
            }
         }
         else
         {
            throw new RuntimeException("field invocations not implemented");
         }
      }
   }
}
