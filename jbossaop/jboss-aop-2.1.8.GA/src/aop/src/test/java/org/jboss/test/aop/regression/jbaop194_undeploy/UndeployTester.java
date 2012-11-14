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
package org.jboss.test.aop.regression.jbaop194_undeploy;

import java.net.URL;

import org.jboss.aop.AspectXmlLoader;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 57781 $
 */
public class UndeployTester extends AOPTestWithSetup
{
   
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(UndeployTester.class);
   }

   /**
    * Constructor for UndeployTester.
    * @param arg0
    */
   public UndeployTester(String arg0)
   {
      super(arg0);
   }
   
   public void testUndeploy() throws Exception
   {
      POJO pojo = new POJO();
      TestInterceptor.intercepted = false;
      pojo.method();
      assertTrue(TestInterceptor.intercepted);

      URL url = getURLRelativeToProjectRoot("/src/resources/test/regression/jboss-aop.xml");
      
      System.out.println("Undeploying first time");
      AspectXmlLoader.undeployXML(url);
      TestInterceptor.intercepted = false;
      pojo.method();
      assertFalse(TestInterceptor.intercepted);

      //Try to undeploy again and make sure it is graceful
      System.out.println("Undeploying again");
      AspectXmlLoader.undeployXML(url);
      TestInterceptor.intercepted = false;
      pojo.method();
      assertFalse(TestInterceptor.intercepted);
   }

}
