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

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * Tests that sub classes can override advice bindings for methods
 * overridden from a superclass.
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class OverrideTestCase extends AOPTestWithSetup
{
   POJO pojo = new POJO();
   SubPOJO subPojo = new SubPOJO();
   SubSubPOJO subSubPojo = new SubSubPOJO();
   
   public OverrideTestCase(String name)
   {
      super(name);
   }
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite("OverrideTestCase");
      suite.addTestSuite(OverrideTestCase.class);
      return suite;
   }
   
   /**
    * Base class metadata
    */
   public void testPOJOSuperOnlyMetadata() throws Exception
   {
      System.out.println("TEST POJO SUPERONLY METADATA");
      MetadataInterceptor.metadata = null;
      pojo.superOnly();
      
      assertEquals("pojo.superOnly", MetadataInterceptor.metadata);
   }

   /**
    * Base class chain
    */
   public void testPOJOSuperOnlyChain() throws Exception
   {
      System.out.println("TEST POJO SUPERONLY CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      pojo.superOnly();
      
      assertEquals(4, CountingInterceptor.interceptions);
      assertEquals(2, CountingAspect.interceptions);
   }

   /**
    * Base class metadata
    */
   public void testPOJOSuperOnlyNoSubMetadata() throws Exception
   {
      System.out.println("TEST POJO SUPERONLYNOSUB METADATA");
      MetadataInterceptor.metadata = null;
      pojo.superOnlyNoSub();
      
      assertEquals("pojo.superOnlyNoSub", MetadataInterceptor.metadata);
   }

   /**
    * Base class chain
    */
   public void testPOJOSuperOnlyNoSubChain() throws Exception
   {
      System.out.println("TEST POJO SUPERONLYNOSUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      pojo.superOnlyNoSub();
      
      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);
   }

   /**
    * Sub class inheriting method and metadata
    */
   public void testSubSuperOnlyNoSubMetadata() throws Exception
   {
      System.out.println("TEST SUB SUPERONLYNOSUB METADATA");
      MetadataInterceptor.metadata = null;
      subPojo.superOnlyNoSub();
      
      assertEquals("pojo.superOnlyNoSub", MetadataInterceptor.metadata);
   }

   /**
    * Sub sub class inheriting method and metadata
    */
   public void testSubSubSuperOnlyNoSubMetadata() throws Exception
   {
      System.out.println("TEST SUB SUB SUPERONLYNOSUB METADATA");
      MetadataInterceptor.metadata = null;
      subSubPojo.superOnlyNoSub();
      
      assertEquals("pojo.superOnlyNoSub", MetadataInterceptor.metadata);
   }

   /**
    * Sub class inheriting method and chain
    */
   public void testSubSuperOnlyNoSubChain() throws Exception
   {
      System.out.println("TEST SUB SUPERONLYNOSUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.superOnlyNoSub();
      
      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);
   }

   /**
    * Sub class inheriting method and chain
    */
   public void testSubSubSuperOnlyNoSubChain() throws Exception
   {
      System.out.println("TEST SUB SUB SUPERONLYNOSUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.superOnlyNoSub();
      
      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);
   }

   /**
    * Base class metadata
    */
   public void testPOJOOverriddenMetadata() throws Exception
   {
      System.out.println("TEST POJO OVERRIDDEN METADATA");
      MetadataInterceptor.metadata = null;
      pojo.overridden();
      
      assertEquals("pojo.overridden", MetadataInterceptor.metadata);
   }

   /**
    * Base class chain
    */
   public void testPOJOOverriddenChain() throws Exception
   {
      System.out.println("TEST POJO OVERRIDDEN CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      pojo.overridden();
      
      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(3, CountingAspect.interceptions);
   }

   /**
    * Sub class overriding method and metadata
    */
   public void testSubOverriddenMetadata() throws Exception
   {
      System.out.println("TEST SUB OVERRIDDEN METADATA");
      MetadataInterceptor.metadata = null;
      subPojo.overridden();
      
      assertEquals("subpojo.overridden", MetadataInterceptor.metadata);
   }

   /**
    * Sub class overriding method and metadata
    */
   public void testSubSubOverriddenMetadata() throws Exception
   {
      System.out.println("TEST SUB SUB OVERRIDDEN METADATA");
      MetadataInterceptor.metadata = null;
      subSubPojo.overridden();
      
      assertEquals("subsubpojo.overridden", MetadataInterceptor.metadata);
   }


   /**
    * Sub class overriding method and chain
    */
   public void testSubOverriddenChain() throws Exception
   {
      System.out.println("TEST SUB OVERRIDDEN CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.overridden();
      
      assertEquals(1, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);
   }

   /**
    * Sub class overriding method and chain
    */
   public void testSubSubOverriddenChain() throws Exception
   {
      System.out.println("TEST SUB SUB OVERRIDDEN CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subSubPojo.overridden();
      
      assertEquals(2, CountingInterceptor.interceptions);
      assertEquals(1, CountingAspect.interceptions);
   }

   /**
    * Base class metadata
    */
   public void testPOJOOverriddenNoSubMetadata() throws Exception
   {
      System.out.println("TEST POJO OVERRIDDEN NO SUB METADATA");
      MetadataInterceptor.metadata = null;
      pojo.overriddenNoSub();
      
      assertEquals("pojo.overriddenNoSub", MetadataInterceptor.metadata);
   }

   /**
    * Base class chain
    */
   public void testPOJOOverriddenNoSubChain() throws Exception
   {
      System.out.println("TEST POJO OVERRIDDEN NO SUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      pojo.overriddenNoSub();
      
      assertEquals(5, CountingInterceptor.interceptions);
      assertEquals(3, CountingAspect.interceptions);
   }

   /**
    * Sub class overriding method but not metadata
    */
   public void testSubOverriddenNoSubMetadata() throws Exception
   {
      System.out.println("TEST SUB OVERRIDDEN NO SUB METADATA");
      MetadataInterceptor.metadata = null;
      subPojo.overriddenNoSub();
      
      assertEquals(null, MetadataInterceptor.metadata);
   }

   /**
    * Sub class overriding method but not metadata
    */
   public void testSubSubOverriddenNoSubMetadata() throws Exception
   {
      System.out.println("TEST SUB SUB OVERRIDDEN NO SUB METADATA");
      MetadataInterceptor.metadata = null;
      subSubPojo.overriddenNoSub();
      
      assertEquals(null, MetadataInterceptor.metadata);
   }

   /**
    * Sub class overriding method but not chain
    */
   public void testSubOverriddenNoSubChain() throws Exception
   {
      System.out.println("TEST SUB OVERRIDDEN NO SUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.overriddenNoSub();
      
      assertEquals(0, CountingInterceptor.interceptions);
      assertEquals(0, CountingAspect.interceptions);
   }

   /**
    * SubSub class overriding method but not chain
    */
   public void testSubSubOverriddenNoSubChain() throws Exception
   {
      System.out.println("TEST SUB SUB OVERRIDDEN NO SUB CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subSubPojo.overriddenNoSub();
      
      assertEquals(0, CountingInterceptor.interceptions);
      assertEquals(0, CountingAspect.interceptions);
   }

   
   /**
    * Sub class with its own method and chain
    */
   public void testSubSubOnlyChain() throws Exception
   {
      System.out.println("TEST SUB SUB ONLY CHAIN");
      CountingInterceptor.reset();
      CountingAspect.reset();
      subPojo.subOnly();
      
      assertEquals(3, CountingInterceptor.interceptions);
      assertEquals(2, CountingAspect.interceptions);
   }

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }
}
