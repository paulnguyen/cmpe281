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
package org.jboss.test.aop.classpool.jbosscl.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossClClassPoolDelegatingTestSuite extends TestSuite
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("JBoss-cl Pool Tests");
      
      suite.addTest(ClassPoolWithRepositoryTestCase.suite());
      suite.addTest(ClassPoolWithModuleDependencyTestCase.suite());
      suite.addTest(ClassPoolWithPackageDependencyTestCase.suite());
      suite.addTest(ClassPoolWithReExportModuleTestCase.suite());
      suite.addTest(ClassPoolWithReExportPackageTestCase.suite());
      suite.addTest(ClassPoolWithUsesPackageTestCase.suite());
      suite.addTest(ClassPoolWithHierarchicalDomainTestCase.suite());
      suite.addTest(ClassPoolWithHierarchicalParentLoaderTestCase.suite());
      suite.addTest(ClassPoolWithReplaceReferencesTestCase.suite());
      return suite;
   }

}
