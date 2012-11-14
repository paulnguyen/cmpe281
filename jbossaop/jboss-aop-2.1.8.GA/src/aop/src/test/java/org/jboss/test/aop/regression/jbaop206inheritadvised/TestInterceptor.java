/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.aop.regression.jbaop206inheritadvised;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;

/**
 * A TestInterceptor.
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 41764 $
 */
public class TestInterceptor implements Interceptor
{
   public static boolean intercepted;
   public String getName()
   {
      return "TestInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      intercepted = true;
      return invocation.invokeNext();
   }

}
