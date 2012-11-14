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
package org.jboss.test.aop.standalone;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.AspectManager;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A PackageTestCase.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class PackageTestCase extends AOPTestWithSetup
{
   
   public PackageTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("PackageTestCase");
      suite.addTestSuite(PackageTestCase.class);
      return suite;
   }
   
   public void testPackage()
   {
      POJO pojo = new POJO();
      pojo.foo();
      assertTrue(PackageInterceptor.invoked);
      
      org.jboss.aop.standalone.Package pkg = org.jboss.aop.standalone.Package.aopClassMap();
      System.out.println("Package size: "+pkg.packages.size());
      assertEquals(1,pkg.packages.size());
      
//      System.out.println("Number of advisors: "+AspectManager.getTopLevelAspectManager().getAdvisors().size());
//      System.out.println("Number of advisors: "+AspectManager.instance().getAdvisors().size());
//      System.out.println("Number of advisors: "+AspectManager.instance().getSubDomainsPerClass2().size());
//      
//      Map<Class<?>, WeakReference<Domain>> domains = AspectManager.instance().getSubDomainsPerClass2();
//      
//      System.out.println("domains: "+domains.toString());
      
      
      System.out.println("Number of aspects: "+AspectManager.instance().getAspectDefinitions().size());
      
   }
}
