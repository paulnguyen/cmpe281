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
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.array.ArrayElementReadInvocation;
import org.jboss.aop.array.ArrayElementWriteInvocation;
import org.jboss.aop.array.IntArrayElementWriteInvocation;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class ArrayInterceptor implements Interceptor
{
   public String getName() { return "ArrayInterceptor"; }

   public Object invoke(Invocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< Entering ArrayInterceptor type: " + invocation.getClass().getName());
         if (invocation instanceof ArrayElementReadInvocation)
         {
            return invoke((ArrayElementReadInvocation)invocation);
         }
         else if (invocation instanceof ArrayElementWriteInvocation)
         {
            return invoke((ArrayElementWriteInvocation)invocation);
         }
         throw new RuntimeException("This interceptor is for arrays");
      }
      finally
      {
         System.out.println(">>> Leaving ArrayInterceptor");
      }
   }
   
   private Object invoke(ArrayElementReadInvocation inv) throws Throwable
   {
      System.out.println("<<< We have an array element read invocation of type: " + inv.getClass().getName());
      Object o = inv.invokeNext();
      System.out.println(">>> Returned value was " + o);
      return o;
   }
   
   private Object invoke(ArrayElementWriteInvocation inv) throws Throwable
   {
      System.out.println("<<< We have an array element write invocation of type: " + inv.getClass().getName());
      System.out.println("New value for index " + inv.getIndex() + " of " + inv.getTargetObject() + " is " + inv.getValue());
      if (inv instanceof IntArrayElementWriteInvocation)
      {
         System.out.println("Typed value is " + ((IntArrayElementWriteInvocation)inv).getIntValue());
      }
      return inv.invokeNext();
   }
}
