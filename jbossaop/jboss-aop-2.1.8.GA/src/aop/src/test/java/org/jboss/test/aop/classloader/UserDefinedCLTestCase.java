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
package org.jboss.test.aop.classloader;

import java.lang.reflect.Method;
import java.net.URL;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.test.aop.AOPTestWithSetup;


/**
 * Tests interception on a "user defined class loader"-loaded scenario. Both
 * interceptor and intercepted class are loaded by this specific loader and
 * interception is checked for correction.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class UserDefinedCLTestCase extends AOPTestWithSetup
{
   
   private URL jarURL;
   private final static String USER_DEFINED_PACKAGE =
         UserDefinedCLTestCase.class.getPackage().getName() + ".scenario";
   private final static String POJO_CLASS = USER_DEFINED_PACKAGE + ".POJO";
   private final static String INTERCEPTOR_CLASS = USER_DEFINED_PACKAGE +
      ".POJOInterceptor";
   private final static String SCENARIO_TO_LOAD = 
         "/target/test-classes/org/jboss/test/aop/classloader/scenario.jar";
   
   public UserDefinedCLTestCase(String name)
   {
      super(name);
   }

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(UserDefinedCLTestCase.class);
   }
 
   public void setUp() throws Exception
   {
      super.setUp();
      
      jarURL = getURLRelativeToProjectRoot(SCENARIO_TO_LOAD);
   }
   
   public void testURLLoadedInterception() throws Exception
   {
      Thread currentThread = Thread.currentThread();
      ClassLoader classLoader = new UserDefinedCL(jarURL,
               currentThread.getContextClassLoader());
      currentThread.setContextClassLoader(classLoader);
      Class<?> clazz = Class.forName(INTERCEPTOR_CLASS, false, classLoader);
      
      AdviceBinding binding = new AdviceBinding("userdefinedclbinding",
               "execution(* *->*(..))", null);
      binding.addInterceptor(clazz);
      AspectManager.instance().addBinding(binding);

      Method resetStatus = clazz.getDeclaredMethod("resetInvokeStatus");
      Method getStatus = clazz.getDeclaredMethod("getInvoked");
      
      Class<?> pojoClass = Class.forName(POJO_CLASS, false, classLoader);
      Object pojoInstance = pojoClass.newInstance();
      resetStatus.invoke(null);
      Method pojoMethod = pojoClass.getDeclaredMethod("execute");
      pojoMethod.invoke(pojoInstance);
      assertTrue((Boolean) getStatus.invoke(null));
   }
}
