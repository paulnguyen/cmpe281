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
package org.jboss.test.aop.regression.jbaop137;

import org.jboss.aop.InstanceAdvised;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
public class ExtenderTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(ExtenderTestCase.class);
   }

   /**
    * Constructor for ExtenderTestCase.
    * @param arg0
    */
   public ExtenderTestCase(String arg0)
   {
      super(arg0);
   }
   
   public void testMethod() throws Exception
   {
      Extender extender = new Extender();
      InstanceAdvisor ia = ((InstanceAdvised)extender)._getInstanceAdvisor();
      ia.appendInterceptor(new MyInterceptor());

      MyInterceptor.method = false;
      MyInterceptor.field = false;
      extender.getExtender();
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
      
      MyInterceptor.method = false;
      MyInterceptor.field = false;
      extender.setExtender(6);
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
      
      MyInterceptor.method = false;
      MyInterceptor.field = false;
      extender.getBase();
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
      
      MyInterceptor.method = false;
      MyInterceptor.field = false;
      extender.setBase(5);
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
      
      AnExtender anextender = new AnExtender();
      InstanceAdvisor ia2 = ((InstanceAdvised)anextender)._getInstanceAdvisor();
      ia2.appendInterceptor(new MyInterceptor());

      MyInterceptor.method = false;
      MyInterceptor.field = false;
      anextender.getExtender();
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);

      MyInterceptor.method = false;
      MyInterceptor.field = false;
      anextender.setExtender(5);
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);

      
      MyInterceptor.method = false;
      MyInterceptor.field = false;
      anextender.getBase();
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
      
      MyInterceptor.method = false;
      MyInterceptor.field = false;
      anextender.setBase(6);
      assertTrue(MyInterceptor.method);
      assertTrue(MyInterceptor.field);
   }

}
