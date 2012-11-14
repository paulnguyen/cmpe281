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
package org.jboss.test.aop.jdk15.dynamic.common;

import org.jboss.test.aop.AOPTestWithSetup;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * TestCase that checks the jboss aop behaviour in dynamic aop operations
 * performed using a class processed by aopc. This class (POJO class)
 * was configured to have all of its joinpoints prepared.
 * @author Flavia Rainone
 */
@SuppressWarnings("unused")
public class ClassPoolTester extends AOPTestWithSetup
{
   public ClassPoolTester(String name)
   {
      super(name);
   }

   public void testClassPool() throws Exception
   {
      ClassPool classPool = new ClassPool();
      CtClass outerClass = classPool.get(Class1.class.getName());
      String innerClassName = "AnyClass".substring("AnyClass".lastIndexOf('.') + 1);
   
      //Only static nested classes are supported
      boolean classStatic = true;
      CtClass invocation = outerClass.makeNestedClass(innerClassName, classStatic); 
      //invocation.setSuperclass(classPool.get
      //optimizedInvocation = getOptimizedInvocationClassName(clazz, field, true);
      //instrumentor.getClassPool().get(optimizedInvocation).defrost();
            String method = "public void doMethod() {" +
      
        " System.out.println( ";
   }
}

class Class1 {}

class Class2 {
   public String getString() {
      return "helloWorld";
   }
}