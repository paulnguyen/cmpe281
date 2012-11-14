/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.aop.regression.jbaop217typedefredeploy;


import java.net.URL;

import org.jboss.aop.AspectXmlLoader;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests that static is used correctly
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 79661 $
 */
public class TypeDefRedeployTestCase extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("TypeDefRedeployTestCase");
      suite.addTestSuite(TypeDefRedeployTestCase.class);
      return suite;
   }

   public TypeDefRedeployTestCase(String name)
   {
      super(name);
   }

   public void testRedeployWithTypeDef() throws Exception
   {
      POJO pojo = new POJO();
      
      TestInterceptor.intercepted = false;
      pojo.method();
      assertTrue(TestInterceptor.intercepted);
    
      URL url = getUrl();
      AspectXmlLoader.undeployXML(url);
      
      TestInterceptor.intercepted = false;
      pojo.method();
      assertFalse(TestInterceptor.intercepted);
      
      AspectXmlLoader.deployXML(url);
      
      TestInterceptor.intercepted = false;
      pojo.method();
      assertTrue(TestInterceptor.intercepted);
   }

   private URL getUrl() throws Exception
   {
      URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
      System.out.println("class url: " + url);
      String location = url.toString();
      int index = location.indexOf("/target/");
      location = location.substring(0, index);
      
      location = location + "/src/resources/test/regression/jboss-aop.xml";
      url = new URL(location);
      System.out.println("xml url:   " + url);
      return url;
   }
}

