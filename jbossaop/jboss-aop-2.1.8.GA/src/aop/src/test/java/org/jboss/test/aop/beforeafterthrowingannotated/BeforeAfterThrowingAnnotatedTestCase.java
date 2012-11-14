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
package org.jboss.test.aop.beforeafterthrowingannotated;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class BeforeAfterThrowingAnnotatedTestCase extends AOPTestWithSetup
{
   public BeforeAfterThrowingAnnotatedTestCase(String arg0)
   {
      super(arg0);
   }

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("BeforeAfterThrowingAnnotatedTestCase");
      suite.addTestSuite(BeforeAfterThrowingAnnotatedTestCase.class);
      return suite;
   }

   public void testAspects()
   {
      POJO pojo = new POJO();
      assertEquals(0, TestAspect.interceptions.size());
      
      pojo.method();
      assertEquals(3, TestAspect.interceptions.size());
      assertEquals("BEFORE", TestAspect.interceptions.get(0));
      assertEquals("AFTER", TestAspect.interceptions.get(1));
      assertEquals("FINALLY", TestAspect.interceptions.get(2));
      
      TestAspect.interceptions.clear();
      try
      {
         pojo.method(true);
         fail("expected exception");
      }
      catch(TestException expected)
      {
      }
      assertEquals(3, TestAspect.interceptions.size());
      assertEquals("BEFORE", TestAspect.interceptions.get(0));
      assertEquals("THROWING", TestAspect.interceptions.get(1));
      assertEquals("FINALLY", TestAspect.interceptions.get(2));
   }
   
}
