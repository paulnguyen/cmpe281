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
package org.jboss.test.aop.unit.classpool.util;

import org.jboss.aop.util.ClassLoaderUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoaderUtilsTestCase extends TestCase
{
   public static Test suite()
   {
      return new TestSuite(ClassLoaderUtilsTestCase.class);
   }

   public ClassLoaderUtilsTestCase(String name)
   {
      super(name);
   }

   public void testClassResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test");
      assertEquals("org/acme/Test.class", name);
   }
   
   public void testInnerClassResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test$Inner");
      assertEquals("org/acme/Test$Inner.class", name);
   }

   public void testClassArrayResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test[]");
      assertEquals("org/acme/Test.class", name);
   }
   
   public void testInnerClassArrayResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test$Inner[]");
      assertEquals("org/acme/Test$Inner.class", name);
   }

   public void testClass2DArrayResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test[][]");
      assertEquals("org/acme/Test.class", name);
   }
   
   public void testInnerClass2DArrayResourceName()
   {
      String name = ClassLoaderUtils.getResourceName("org.acme.Test$Inner[][]");
      assertEquals("org/acme/Test$Inner.class", name);
   }

   public void testClassPackageName()
   {
      String name = ClassLoaderUtils.getPackageName("org.acme.Test");
      assertEquals("org.acme", name);
   }
   
   public void testInnerClassPackageName()
   {
      String name = ClassLoaderUtils.getPackageName("org.acme.Test$Inner");
      assertEquals("org.acme", name);
   }
}
