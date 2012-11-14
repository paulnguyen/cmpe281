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
package org.jboss.test.aop.regression.inheritedfield;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
public class InheritedFieldTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(InheritedFieldTestCase.class);
   }

   public InheritedFieldTestCase(String arg0)
   {
      super(arg0);
   }
   
   public void testMetaDataArrayParameter() throws Exception
   {
      SubPOJO pojo = new SubPOJO();
      
      TestInterceptor.intercepted = null;
      pojo.i = 10;
      assertNull(TestInterceptor.intercepted);
      
      TestInterceptor.intercepted = null;
      pojo.x = 100;
      assertNotNull(TestInterceptor.intercepted);
      assertEquals("x", TestInterceptor.intercepted.getName());
      
      TestInterceptor.intercepted = null;
      pojo.y = 1000;
      assertNull(TestInterceptor.intercepted);

      TestInterceptor.intercepted = null;
      assertEquals(10, pojo.i);
      assertNull(TestInterceptor.intercepted);
      
      TestInterceptor.intercepted = null;
      assertEquals(100, pojo.x);
      assertNotNull(TestInterceptor.intercepted);
      assertEquals("x", TestInterceptor.intercepted.getName());
      
      TestInterceptor.intercepted = null;
      assertEquals(1000, pojo.y);
      assertNull(TestInterceptor.intercepted);
      
      
   }

}
