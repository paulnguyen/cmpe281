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
package org.jboss.test.aop.regression.jbaop316annotationsinwrapperonly;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jboss.aop.Advised;
import org.jboss.aop.annotation.PortableAnnotationElement;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


/**
 * http://jira.jboss.com/jira/browse/JBAOP-316
 * 
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
@SuppressWarnings("unchecked")
public class AnnotationsInWrapperMethodOnlyTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotationsInWrapperMethodOnlyTestCase");
      suite.addTestSuite(AnnotationsInWrapperMethodOnlyTestCase.class);
      return suite;
   }

   public AnnotationsInWrapperMethodOnlyTestCase(String name)
   {
      super(name);
   }

   public void testAnnotationNotInInternalCopy()throws Exception
   {
      Method[] methods = POJO.class.getMethods();
      
      ArrayList hasAnnotation = new ArrayList();
      ArrayList hasParameterAnnotation = new ArrayList();
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (PortableAnnotationElement.isAnyAnnotationPresent(methods[i], TestAnnotation.class))
         {
            hasAnnotation.add(methods[i].getName());
         }
         
         Annotation[][] paramAnnotations = methods[i].getParameterAnnotations();
         for (int p = 0 ; p < paramAnnotations.length ; p++)
         {
            if (paramAnnotations[p].length > 0)
            {
               hasParameterAnnotation.add(methods[i].getName() + "1");
            }
         }
      }
      
      assertFalse("Expected to find annotation for 'method'", hasAnnotation.size() == 0);
      assertFalse("Expected to find parameter annotation for 'method'", hasParameterAnnotation.size() == 0);
      assertTrue("Expected to find annotation for 'method' only, it was found for " + hasAnnotation, hasAnnotation.size() == 1);
      assertTrue("Expected to find parameter annotation for 'method' only, it was found for " + hasParameterAnnotation, hasAnnotation.size() == 1);
      assertEquals("method", hasAnnotation.get(0));
      assertEquals("method1", hasParameterAnnotation.get(0));
      assertTrue("Class was not woven", Advised.class.isAssignableFrom(POJO.class));
   }
}
