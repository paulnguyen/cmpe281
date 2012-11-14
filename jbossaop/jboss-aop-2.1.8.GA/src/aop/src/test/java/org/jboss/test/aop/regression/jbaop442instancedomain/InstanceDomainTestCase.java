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
package org.jboss.test.aop.regression.jbaop442instancedomain;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.InstanceAdvised;
import org.jboss.aop.InstanceAdvisor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unused")
public class InstanceDomainTestCase extends TestCase
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("InstanceDomainTestCase");
      suite.addTestSuite(InstanceDomainTestCase.class);
      return suite;
   }

   public InstanceDomainTestCase(String name)
   {
      super(name);
   }

   public void testClassAdvisorAndNotInstanceAdvisor()
   {
      POJO pojo = new POJO();
      InstanceAdvisor ia = ((InstanceAdvised)pojo)._getInstanceAdvisor();
      
      Advisor advisor = AspectManager.instance().getAdvisor(POJO.class);
      assertFalse(advisor instanceof InstanceAdvisor);
   }
}
