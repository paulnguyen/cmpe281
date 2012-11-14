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
package org.jboss.test.aop.typedAdvices;

/**
 * POJO whose joinpoints execution will throw a NoMatchingAdviceException, due to
 * the fact that the advice bound to it does not exist.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class InvalidAdviceNamePOJO {}

/**
 * POJO whose joinpoint executions will throw a NoMatchingAdviceException, due to
 * the fact that there is no advice that matches the joinpoint.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class InvalidPOJO
{
   int i;
   SuperValue superValue;
   SubValue subValue;
   
   public InvalidPOJO() {}
   public InvalidPOJO(short arg) {}
   public InvalidPOJO(long arg) {}
   // constructor that is not bound to any advice
   public InvalidPOJO(String arg) {}
   
   public void methodBefore() {}
   public void methodBefore(boolean arg) {}
   public void methodAfter() {}
   public void methodAfter(boolean arg) {}
   public void methodAfter(SubValue arg1, int arg2) {}
}