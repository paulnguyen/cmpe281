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
import org.jboss.aop.Bind;
import org.jboss.aop.InterceptorDef;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.advice.Interceptor;


/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
@InterceptorDef
@Bind (pointcut = "all(POJO)")//All constructors and methods
//@Bind (pointcut = "execution(* POJO->*(..))")//All methods
//@Bind (pointcut = "execution(POJO->new(..))")//All constructors
public class SimpleInterceptor implements Interceptor
{
   public String getName() { return "SimpleInterceptor"; }

   public Object invoke(Invocation invocation) throws Throwable
   {
      try
      {
         if (invocation instanceof MethodInvocation)
         {
            MethodInvocation mi = (MethodInvocation)invocation;
            System.out.println("<<< Entering SimpleInterceptor for: " + mi.getMethod().toString());
         }
         else if (invocation instanceof ConstructorInvocation)
         {
            ConstructorInvocation ci = (ConstructorInvocation)invocation;
            System.out.println("<<< Entering SimpleInterceptor for: " + ci.getConstructor().toString());
         }
         else if (invocation instanceof ConstructorInvocation)
         {
            FieldInvocation fi = (FieldInvocation)invocation;
            System.out.println("<<< Entering SimpleInterceptor for: " + fi.getField().toString());
         }
         else
         {
            System.out.println("<<< Entering SimpleInterceptor for a caller invocation");
         }

         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving SimpleInterceptor");
      }
   }
}
