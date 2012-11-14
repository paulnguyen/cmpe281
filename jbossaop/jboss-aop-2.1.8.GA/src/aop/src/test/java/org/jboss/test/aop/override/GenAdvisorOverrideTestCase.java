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
package org.jboss.test.aop.override;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * These tests only work with generated advisors, and not old skool weaving
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 46033 $
 */
public class GenAdvisorOverrideTestCase extends OverrideTestCase
{
   //Have the same bindings as SubPOJO and SubSubPOJO, but override the methods to see that we get the
   //same behaviour for bindings applied to inherited methods
   SubPOJOOverrideAll subControl = new SubPOJOOverrideAll();
   SubSubPOJOOverrideAll subSubControl = new SubSubPOJOOverrideAll();

   public GenAdvisorOverrideTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("OverrideTestCase");
      suite.addTestSuite(GenAdvisorOverrideTestCase.class);
      return suite;
   }

   /**
    * Sub class overriding metadata for inherited method.
    *
    * While the metadata gets populated the same, this does not work with traditional weaving since the
    * class advisor is a static field within each advised class, and the method wrappers use that directly.
    * So even though SubPOJO's advisor has the correct metadata in the advisor, the wrapper lives in POJO
    * (since SubPOJO is inheriting the method) and will use POJO's advisor.
    */
   public void testSubSuperOnlyMetadata() throws Exception
   {
      System.out.println("TEST SUB SUPERONLY METADATA");
      MetadataInterceptor.metadata = null;
      subPojo.superOnly();

      assertEquals("subpojo.superOnly", MetadataInterceptor.metadata);
   }

   public void testSubSubSuperOnlyMetadata() throws Exception
   {
      System.out.println("TEST SUB SUB SUPERONLY METADATA");
      MetadataInterceptor.metadata = null;
      subSubPojo.superOnly();

      assertEquals("subsubpojo.superOnly", MetadataInterceptor.metadata);
   }

   /**
    * Sub class adding to chain for inherited method.
    *
    * For generated advisors, each advisor has a reference to its MethodInfo structures and those of its superadvisors,
    * so when inheriting a method we still populate the MethodInfo for that via initialising the super class of the advisor
    * (i.e the advisor for the super class). These advisors get passed in to the adviors when populating the chains so even if that
    * JoinPoint field cannot be found in the SubPOJO advisor declared fields, the chains still get set properly since the advisor is
    * responsible for passing them in.
    *
    * In traditional weaving we only have a static field for a JoinPointInfo per advised class, so when Advisor.resolveMethodPointcut()
    * tries to set the field it does not exist there.
    */
   public void testSubSuperOnlyChain() throws Exception
   {
      System.out.println("TEST SUB SUPERONLY CHAIN");
      
      CountingInterceptor.reset();
      CountingAspect.reset();
      subControl.superOnly();

      assertEquals(1, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);

      //Test the inherited methods
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.superOnly();

      assertEquals(1, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);

   }

   public void testSubSubSuperOnlyChain() throws Exception
   {
      System.out.println("TEST SUB SUB SUPERONLY CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subSubControl.superOnly();

      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(2, CountingAspect.interceptions);

      //Test the inherited methods
      CountingInterceptor.reset();
      CountingAspect.reset();
      subSubPojo.superOnly();

      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(2, CountingAspect.interceptions);

   }}
