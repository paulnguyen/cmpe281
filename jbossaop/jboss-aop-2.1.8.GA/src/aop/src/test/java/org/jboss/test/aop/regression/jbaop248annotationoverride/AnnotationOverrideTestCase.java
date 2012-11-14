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
package org.jboss.test.aop.regression.jbaop248annotationoverride;

import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
public class AnnotationOverrideTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotationOverrideTestCase");
      suite.addTestSuite(AnnotationOverrideTestCase.class);
      return suite;
   }

   public AnnotationOverrideTestCase(String name)
   {
      super(name);
   }

   public void testAnnotationOverrides()throws Exception
   {
      checkAnnotations(Override.class);
   }

   public void testAnnotationIntroductions()throws Exception
   {
      checkAnnotations(Introduced.class);
   }

   private void checkAnnotations(Class<?> annotation) throws Exception
   {
      TestInterceptor.annotation = annotation;
      
      TestInterceptor.reset();
      POJO pojo = new POJO();
      checkValue(TestInterceptor.classAnnotation, TestInterceptor.annotation, 1);
      checkValue(TestInterceptor.joinpointAnnotation, TestInterceptor.annotation, 10);
      
      TestInterceptor.reset();
      pojo.field = 6;
      checkValue(TestInterceptor.classAnnotation, TestInterceptor.annotation, 1);
      checkValue(TestInterceptor.joinpointAnnotation, TestInterceptor.annotation, 20);
      
      TestInterceptor.reset();
      assertEquals(6, pojo.field);
      checkValue(TestInterceptor.classAnnotation, TestInterceptor.annotation, 1);
      checkValue(TestInterceptor.joinpointAnnotation, TestInterceptor.annotation, 20);
      
      TestInterceptor.reset();
      pojo.method();
      checkValue(TestInterceptor.classAnnotation, TestInterceptor.annotation, 1);
      checkValue(TestInterceptor.joinpointAnnotation, TestInterceptor.annotation, 30);
      
      TestInterceptor.reset();
      pojo.notAnnotatedMethod();
      checkValue(TestInterceptor.classAnnotation, TestInterceptor.annotation, 1);
      assertNull(TestInterceptor.joinpointAnnotation);
      
   }
   
   private void checkValue(Object annotation, Class<?> expectedClass, int expectedValue) throws Exception
   {
      assertNotNull(annotation);
      assertTrue(expectedClass.isAssignableFrom(annotation.getClass()));
      
      if (expectedClass.equals(Override.class))
      {
         assertEquals(expectedValue, ((Override)annotation).value());
      }
      else if (expectedClass.equals(Introduced.class))
      {
         assertEquals(expectedValue, ((Introduced)annotation).value());
      }
      else
      {
         fail("Should not happen");
      }

   }
}
