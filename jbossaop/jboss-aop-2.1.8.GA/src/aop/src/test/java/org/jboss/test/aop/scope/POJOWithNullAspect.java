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
package org.jboss.test.aop.scope;

/**
 * Plain old java object for testing of {@code NullAspectFactory}.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class POJOWithNullAspect
{
   // for get and set joinpoints
   public int field;
   public static int staticField;
   
   // for execution and construction joinpoints
   public POJOWithNullAspect() {}
   
   // for execution joinpoints
   public void method() {}
   public static void staticMethod() {}
   
   // for call joinpoints
   // callers:
   public POJOWithNullAspect(int value1, int value2)
   {
      POJOWithNullAspect pojo = new POJOWithNullAspect(1);
      pojo.calledMethod1();
      calledMethod2();
   }
   public void callerMethod1()
   {
      POJOWithNullAspect pojo = new POJOWithNullAspect(1);
      pojo.calledMethod1();
      calledMethod2();
   }
   public static void callerMethod2()
   {
      POJOWithNullAspect pojo = new POJOWithNullAspect(2);
      pojo.calledMethod1();
      calledMethod2();
   }
   // called:
   public POJOWithNullAspect(int value) {}
   public void calledMethod1() {}
   public static void calledMethod2() {}
   
   // method that will be intercepted by both normal factories and
   // NullAspectFactories
   public void mixedMethod(){}
}