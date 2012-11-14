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
package org.jboss.test.aop.inforesolve;

import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.joinpoint.JoinPointBean;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("static-access")
public class ResolveAnnotationAspect
{
   public static JoinPointBean joinPoint;
   public static TestAnnotation classAnnotation;
   public static TestAnnotation joinpointAnnotation;
   
   public static void clear()
   {
      joinPoint = null;
      classAnnotation = null;
      joinpointAnnotation = null;
   }
   
   public void before(@JoinPoint JoinPointBean joinPoint)
   {
      this.joinPoint = joinPoint;
      if (joinPoint == null)
      {
         //This is an error but will be picked up by the test
         return;
      }
         
      classAnnotation = joinPoint.resolveClassAnnotation(TestAnnotation.class);
      joinpointAnnotation = joinPoint.resolveAnnotation(TestAnnotation.class);
   }
}
