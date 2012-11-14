/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.inheritanceacrosspackages;

import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.inheritanceacrosspackages.base.Base;
import org.jboss.test.aop.inheritanceacrosspackages.child.Child;

/**
 * To test JBAOP-660
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class InheritanceAcrossPackagesTestCase extends AOPTestWithSetup
{
   public InheritanceAcrossPackagesTestCase(String arg0)
   {
      super(arg0);
   }

   public void testBaseClass() throws Exception
   {
      TestInterceptor.intercepted = false;
      Base base = new Base();
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      base.field = 0;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      base.field = base.field -1;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      base.base();
      assertTrue(TestInterceptor.intercepted);
   }

   public void testChildClass() throws Exception
   {
      TestInterceptor.intercepted = false;
      Child child = new Child();
      assertTrue(TestInterceptor.intercepted);
      ((org.jboss.aop.Advised)child)._getInstanceAdvisor();
      TestInterceptor.intercepted = false;
      child.field = 20;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      child.field = child.field * 5;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      child.field2 = 40;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      child.field2 = child.field2 * 10;
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      child.base();
      assertTrue(TestInterceptor.intercepted);
      TestInterceptor.intercepted = false;
      child.child();
      assertTrue(TestInterceptor.intercepted);
   }
}
