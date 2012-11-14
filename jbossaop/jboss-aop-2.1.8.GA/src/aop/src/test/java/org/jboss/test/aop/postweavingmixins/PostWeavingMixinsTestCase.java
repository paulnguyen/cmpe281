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
package org.jboss.test.aop.postweavingmixins;

import java.net.URL;

import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.aop.AspectXmlLoader;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test for JBAOP-365. Only for use with compile-time weaving, and then checking that when making
 * mixins/introductions available at runtime we do not fail
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class PostWeavingMixinsTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(PostWeavingMixinsTestCase.class);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("PostWeavingMixinsTestCase");
      suite.addTestSuite(PostWeavingMixinsTestCase.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public PostWeavingMixinsTestCase(String name)
   {
      super(name);
   }

   public void testMixinsInRuntimeConfig() throws Exception
   {
      //Deploy the mixins-aop.xml file before loading our woven pojo classs
      //we deploy it programatically simply to keep the build.xml tidy
      URL url = getUrl();
      AspectXmlLoader.deployXML(url);

      WovenPOJO pojo;
      try
      {
         pojo = new WovenPOJO();
      }
      catch (Throwable e)
      {
         throw new RuntimeException("CLass could not be loaded", e);
      }

      SanityInterceptor.invoked = false;
      pojo.method();
      assertTrue(SanityInterceptor.invoked);

      if (pojo instanceof MarkerInterface)
      {
         fail("WovenPOJO should not implement MarkerInterface. This test should ONLY be run with compile time weaving");
      }

      if (pojo instanceof MixinInterface)
      {
         fail("WovenPOJO should not implement MarkerInterface. This test should ONLY be run with compile time weaving");
      }
   }

   private URL getUrl() throws Exception
   {
      URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
      System.out.println("class url: " + url);
      String location = url.toString();
      int index = location.indexOf("/target/test-classes");
      location = location.substring(0, index);

      location = location + "/src/resources/test/postweavingmixins/mixins-aop.xml";
      url = new URL(location);
      System.out.println("xml url:   " + url);
      return url;
   }

}