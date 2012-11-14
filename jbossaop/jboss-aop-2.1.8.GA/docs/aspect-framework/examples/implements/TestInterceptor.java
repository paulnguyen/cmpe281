/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.advice.Interceptor;
/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 44138 $
 */
public class TestInterceptor implements Interceptor
{
   public String getName() { return "TestInterceptor"; }


   public Object invoke(Invocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< TestInterceptor intercepting");
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving Trace");
      }
   }
}
