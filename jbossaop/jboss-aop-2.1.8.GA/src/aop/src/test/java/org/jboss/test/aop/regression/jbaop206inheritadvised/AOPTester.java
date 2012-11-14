/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.aop.regression.jbaop206inheritadvised;


import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests that static is used correctly
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 45977 $
 */
public class AOPTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("AOPTester");
      suite.addTestSuite(AOPTester.class);
      return suite;
   }

   public AOPTester(String name)
   {
      super(name);
   }

   public void testRegression()
   {
      new Sub();
      assertTrue(TestInterceptor.intercepted);
   }

}

