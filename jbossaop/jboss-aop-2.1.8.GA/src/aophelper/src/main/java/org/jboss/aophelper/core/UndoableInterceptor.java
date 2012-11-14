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
package org.jboss.aophelper.core;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aophelper.annotation.Undoable;

/**
 * A UndoableInterceptor.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class UndoableInterceptor implements Interceptor
{

   public String getName()
   {
      return "SettingInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      MethodInvocation mi = (MethodInvocation) invocation;
      Undoable undoable = (Undoable) invocation.resolveAnnotation(org.jboss.aophelper.annotation.Undoable.class);
      System.out.println("callback: "+undoable.callback());
      
      System.out.println("Class: "+mi.getTargetObject().getClass().getName());
      System.out.println("Method: "+mi.getMethod().getName());
      
      Object[] args = mi.getArguments();
      if(args != null && args.length > 0)
      {
         UndoableManager.instance().addUndoable(mi.getTargetObject(), mi.getMethod(), args[0]);
         for(Object o : args)
         System.out.println("args: "+o.toString());
      }

      
      return invocation.invokeNext();
      
   }
   
   

}
