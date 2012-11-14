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
package org.jboss.test.aop.regression.jbaop110;


import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.regression.jbaop110.Type.NormalType;
import org.jboss.test.aop.regression.jbaop110.Type.StaticType;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class InnerClassTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(InnerClassTestCase.class);
   }

   public InnerClassTestCase(String arg0)
   {
      super(arg0);
   }

   public void testPOJO()
   {
      TestAspect.clear();
      POJO pojo = new POJO(Type.type.normalType, Type.type.staticType);
      
      pojo.nt = Type.type.normalType;
      NormalType ntype = pojo.nt;
      
      pojo.st = Type.type.staticType;
      StaticType stype = pojo.st;
      
      NormalType type = pojo.method(Type.type.staticType, Type.type.normalType);
      
      assertTrue(TestAspect.constructor);
      assertTrue(TestAspect.staticRead);
      assertTrue(TestAspect.staticWrite);
      assertTrue(TestAspect.normalRead);
      assertTrue(TestAspect.normalWrite);
      assertTrue(TestAspect.method);
   }
   
   public void testNormalInnerClassFromOuterClass()
   {
      TestAspect.clear();
      POJO pojo = new POJO();
      assertFalse(TestAspect.constructor);
      TestAspect.clear();
      pojo.executeNormal();

      //assertTrue(TestAspect.constructor);
      assertTrue(TestAspect.staticRead);
      assertTrue(TestAspect.staticWrite);
      assertTrue(TestAspect.normalRead);
      assertTrue(TestAspect.normalWrite);
      assertTrue(TestAspect.method);
}
   
   public void testStaticInnerClassFromOuterClass()
   {
      TestAspect.clear();
      POJO pojo = new POJO();
      assertFalse(TestAspect.constructor);
      TestAspect.clear();
      pojo.executeStatic();

      assertTrue(TestAspect.constructor);
      assertTrue(TestAspect.staticRead);
      assertTrue(TestAspect.staticWrite);
      assertTrue(TestAspect.normalRead);
      assertTrue(TestAspect.normalWrite);
      assertTrue(TestAspect.method);
}
}
