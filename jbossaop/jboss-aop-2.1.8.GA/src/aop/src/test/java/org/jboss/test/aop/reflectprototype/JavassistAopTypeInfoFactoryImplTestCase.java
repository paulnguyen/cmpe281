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
package org.jboss.test.aop.reflectprototype;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.reflectprototype.JavassistAopTypeInfoFactoryImpl;
import org.jboss.reflect.plugins.javassist.JavassistTypeInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A JavassistAopTypeInfoFactoryImplTestCase.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistAopTypeInfoFactoryImplTestCase extends AOPTestWithSetup
{

   public JavassistAopTypeInfoFactoryImplTestCase(String name)
   {
      super(name);
   }
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite("JavassistAopTypeInfoFactoryImplTestCase");
      suite.addTestSuite(JavassistAopTypeInfoFactoryImplTestCase.class);
      return suite;
   }
   

   @Override
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void testJavassistCtClass()
   {
      ClassPool pool = ClassPool.getDefault();
      try
      {
         CtClass pojo = pool.get("org.jboss.test.aop.reflectprototype.POJO");
         
         assertFalse("POJO shouldnt be frozen", pojo.isFrozen());
         
         JavassistAopTypeInfoFactoryImpl typeInfoFactory = new JavassistAopTypeInfoFactoryImpl();
         ClassInfo pojoTypeInfo = (JavassistTypeInfo) typeInfoFactory.get(pojo);
         
         MethodInfo[] methodInfos = pojoTypeInfo.getDeclaredMethods();
         assertEquals("It should be one method in POJO", methodInfos.length, 1);
         
//         CtClass pojo2 = pool.get("org.jboss.test.aop.reflectprototype.POJO");
         try
         {
            CtMethod bar = CtNewMethod.make("public void bar() {}", pojo);
            pojo.addMethod(bar);
         }
         catch (CannotCompileException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         ClassInfo pojoTypeInfo2 = (JavassistTypeInfo) typeInfoFactory.get(pojo);
         assertEquals("It should be two methods in POJO", pojoTypeInfo2.getDeclaredMethods().length, 2);
         
         assertFalse("POJO shouldnt be frozen", pojo.isFrozen());
         
      }
      catch (NotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
