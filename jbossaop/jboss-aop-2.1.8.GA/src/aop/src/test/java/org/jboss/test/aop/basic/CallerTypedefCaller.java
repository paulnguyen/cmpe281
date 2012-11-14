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
package org.jboss.test.aop.basic;


/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */
public class CallerTypedefCaller
{

   public void call()
   {
      System.out.println("intercept class constructor");
      CallerInterceptor.called = false;
      CallerTypedefPOJO pojo = new CallerTypedefPOJO();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept class field read");
      int i = pojo.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO.field1 read " + i);
      CallerInterceptor.called = false;

      System.out.println("intercept class field write");
      pojo.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept class field read");
      i = pojo.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept class field write");
      pojo.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept class method Caller");
      pojo.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO.method() execution");
      CallerInterceptor.called = false;
      
      System.out.println("intercept instanceof constructor");
      CallerTypedefPOJO2 pojo2 = new CallerTypedefPOJO2();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2 constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field read");
      i = pojo2.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2.field1 read");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field write");
      pojo2.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field read");
      i = pojo2.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof field write");
      pojo2.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept instanceof method Caller");
      pojo2.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO2 method execution");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation constructor");
      CallerTypedefPOJO3 pojo3 = new CallerTypedefPOJO3();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3 constructor");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field read");
      i = pojo3.field1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3.field1 read");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field write");
      pojo3.field1 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3.field1 write");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field read");
      i = pojo3.field2;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3.field2 read");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation field write");
      pojo3.field2 = 1;
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3.field2 write");
      CallerInterceptor.called = false;

      System.out.println("intercept annotation method Caller");
      pojo3.method();
      if (!CallerInterceptor.called) throw new RuntimeException("Did not intercept CallerTypedefPOJO3 method execution");

   }
}
