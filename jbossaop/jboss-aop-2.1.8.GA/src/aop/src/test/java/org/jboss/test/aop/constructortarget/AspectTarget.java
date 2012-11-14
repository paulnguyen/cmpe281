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
package org.jboss.test.aop.constructortarget;

import org.jboss.aop.joinpoint.ConstructorInvocation;

/** 
 * 
 * 
 * @author stale w. pedersen(stalep@conduct.no)
 * @version 
 */
public class AspectTarget
{
   static boolean intercepted;
   public Object constructor(ConstructorInvocation ci) throws Throwable
   {
      intercepted = true;
      if(ci.getTargetObject() != null) 
      {
         throw new Throwable("TargetObject should be null");
      }
      Object o = ci.invokeNext();

      if(ci.getTargetObject() != o) 
      {
         throw new Throwable("TargetObject should be null");
      }

      return o;
   }
}