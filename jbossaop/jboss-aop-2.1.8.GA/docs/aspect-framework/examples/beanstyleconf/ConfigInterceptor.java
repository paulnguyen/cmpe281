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
import org.jboss.aop.Advisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.advice.Interceptor;

/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class ConfigInterceptor implements Interceptor
{
   String attr1;
   int attr2;
   Advisor advisor;
   InstanceAdvisor instanceAdvisor;
   Joinpoint jp;

   public void setAttr1(String s)
   {
      attr1 = s;
      System.out.println("setAttr1: " + s);
   }
      
   public void setAttr2(int i)
   {
      attr2 = i;
      System.out.println("setAttr2: " + i);
   }

   public void setMyAdvisor(Advisor advisor)
   {
      this.advisor = advisor;
   }
   
   public void setMyInstanceAdvisor(InstanceAdvisor instanceAdvisor)
   {
      this.instanceAdvisor = instanceAdvisor;
   }
   
   public void setMyJoinpoint(Joinpoint jp)
   {
      this.jp = jp;
   }

   public String getName() { return "ConfigInterceptor"; }

   public Object invoke(Invocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< Entering ConfigInterceptor type: " + this);
         System.out.println("      attr1:" + attr1 + "; attr2:" + attr2);
	      System.out.println("      has advisor: " + (advisor != null));
	      System.out.println("      has instanceAdvisor: " + (instanceAdvisor != null));
	      System.out.println("      has joinpoint: " + (jp != null));
         
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leaving ConfigInterceptor");
      }
   }
}
