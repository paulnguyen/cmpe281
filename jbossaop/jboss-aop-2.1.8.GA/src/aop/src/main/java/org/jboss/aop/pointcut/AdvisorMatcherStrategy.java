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

import java.lang.reflect.Method;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ClassExpression;

/**
 * Strategy to allow for different handling for the pointcut matchers for different types of advisor
 * This one uses the "default" strategy, used for normal advisors
 *  
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70842 $
 */
public class AdvisorMatcherStrategy extends MatcherStrategy
{
   public AdvisorMatcherStrategy()
   {
   }

   protected boolean checkIntroductions(Class<?> clazz, ClassExpression instanceOf, Advisor advisor)
   {
      return false;
   }
  
   public Class<?> getDeclaringClass(Advisor advisor, Method m)
   {
      return m.getDeclaringClass();
   }

   
}
