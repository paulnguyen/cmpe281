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
package org.jboss.aop.pointcut;

import java.util.ArrayList;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77243 $
 *
 **/
public class CFlowStack
{
   private String name;
   private ArrayList<CFlow> cflows = new ArrayList<CFlow>();

   public CFlowStack(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public void addCFlow(CFlow cflow)
   {
      cflows.add(cflow);
   }

   public boolean matches(StackTraceElement[] stack, ClassLoader cl)
   {
      int stackIndex = stack.length - 1;
      for (int i = 0; i < cflows.size(); i++)
      {
         CFlow cflow = cflows.get(i);
         stackIndex = cflow.matches(stack, stackIndex, cl);
         if (stackIndex == org.jboss.aop.pointcut.CFlow.NOT_FOUND)
         {
            return false;
         }
         if (stackIndex == -1 && i + 1 < cflows.size()) return false;
      }
      return true;
   }

}
