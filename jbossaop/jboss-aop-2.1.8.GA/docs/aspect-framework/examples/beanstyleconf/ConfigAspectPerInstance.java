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

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class ConfigAspectPerInstance
{
   String[] attr1;
   Advisor advisor;
   InstanceAdvisor instanceAdvisor;
   Joinpoint jp;

   public ConfigAspectPerInstance()
   {
   }

   public void setAttr(String[] attr1)
   {
      this.attr1 = attr1;
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

   public Object methodAdvice(Invocation invocation) throws Throwable
   {
      try
      {
         System.out.println("<<< Enter ConfigAspectPerInstance");

         StringBuffer sb = new StringBuffer();
         for (int i = 0 ; i < attr1.length ; i++)
         {
            if (i > 0)
            {
               sb.append(", ");
            }
            sb.append("index " + i + "=" + attr1[i]);
         }
         String attr1ArrayAsString = sb.toString();

         System.out.println("      attr1: " + attr1ArrayAsString);
         System.out.println("      has advisor: " + (advisor != null));
         System.out.println("      has instanceAdvisor: " + (instanceAdvisor != null));
         System.out.println("      has joinpoint: " + (jp != null));
         return invocation.invokeNext();
      }
      finally
      {
         System.out.println(">>> Leave ConfigAspectPerInstance");
      }
   }

}
