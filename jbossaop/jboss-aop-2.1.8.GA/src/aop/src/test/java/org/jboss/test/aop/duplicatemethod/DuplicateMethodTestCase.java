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
package org.jboss.test.aop.duplicatemethod;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.ProxyFactory;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.Advised;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A DuplicateMethodTestCase.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"unchecked"})
public class DuplicateMethodTestCase extends AOPTestWithSetup
{

   public DuplicateMethodTestCase(String name)
   {
      super(name);
   }
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite("DuplicateMethodTestCase");
      suite.addTestSuite(DuplicateMethodTestCase.class);
      return suite;
   }
   
   public void testDupe() throws Exception
   {
      TestDupe testDupe = new TestDupe();
      assertTrue(testDupe instanceof Advised);
      
      System.out.println("Generating proxy");
      ProxyFactory f = new ProxyFactory();
      f.setSuperclass(TestDupe.class);
      f.setFilter(new MethodFilter() {
         public boolean isHandled(Method m) {
            // ignore finalize()
            return !m.getName().equals("finalize");
         }
      });
      
      System.out.println("Create");
      Class c = f.createClass();
      System.out.println("Created");

      TestDupe td = (TestDupe) c.newInstance();
      TestDupeInterceptor.invoked = false;
      td.foo();
      //If it fails on this line - it probably means the proxy was not woven correctly
      assertTrue(TestDupeInterceptor.invoked);
   }
}
