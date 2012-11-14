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
package org.jboss.test.aop.invoketarget;

import junit.framework.Assert;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 46058 $
 */
public class CallingPOJO
{
   public CallingPOJO()
   {
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      CalledPOJO pojo = new CalledPOJO("Test");
      Assert.assertEquals("Test", pojo.plain); //not advised
      
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.test();
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      String s = pojo.echo("Hello");
      Assert.assertEquals("Hello", s);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      int i = pojo.echo(100);
      Assert.assertEquals(100, i);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
   }

   public static void testStatic()
   {
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      CalledPOJO pojo = new CalledPOJO("Test");
      Assert.assertEquals("Test", pojo.plain); //not advised
      
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.test();
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      String s = pojo.echo("Hello");
      Assert.assertEquals("Hello", s);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      int i = pojo.echo(100);
      Assert.assertEquals(100, i);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
   }

   public void test()
   {
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      CalledPOJO pojo = new CalledPOJO("Test");
      Assert.assertEquals("Test", pojo.plain); //not advised
      
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      pojo.test();
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      String s = pojo.echo("Hello");
      Assert.assertEquals("Hello", s);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
      
      BypassInterceptor.invocations = 0;
      CountingInterceptor.invocations = 0;
      int i = pojo.echo(100);
      Assert.assertEquals(100, i);
      Assert.assertEquals(1, BypassInterceptor.invocations);
      Assert.assertEquals(1, CountingInterceptor.invocations);
   }

}
