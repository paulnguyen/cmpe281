/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.beforeafterthrowingscoped;

import org.jboss.aop.advice.annotation.Thrown;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class PerJoinpointAspect
{
   public static PerJoinpointAspect before;
   public static PerJoinpointAspect after;
   public static PerJoinpointAspect throwing;
   public static PerJoinpointAspect finaly;
   
   public void before()
   {
      before = this;
   }
   
   public void after()
   {
      after = this;
   }
   
   public void throwing(@Thrown Throwable e)
   {
      throwing = this;
   }
   
   public void finaly()
   {
      finaly = this;
   }
   
   public static void reset()
   {
      before = null;
      after = null;
      throwing = null;
      finaly = null;
   }
}
