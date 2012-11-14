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
package org.jboss.test.aop.annotationproperty;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.aop.AOPTestDelegate;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
public class AnnotationPropertyReplacementTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AOPTester");
      suite.addTestSuite(AnnotationPropertyReplacementTestCase.class);
      return suite;
   }

   public AnnotationPropertyReplacementTestCase(String name)
   {
      super(name);
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      //Don't use security with this test
      return new AOPTestDelegate(clazz);
   }
   
   public void testPropertyReplacement() throws Exception
   {
      AccessController.doPrivileged(new PrivilegedAction<Object>() {

         public Object run()
         {
            //Properties for the @SimpleValue
            System.setProperty("value1", "Test");
            
            //Properties for the @Complex
            System.setProperty("ch", "a");
            System.setProperty("string", "Test123");
            System.setProperty("flt", "9.9");
            System.setProperty("dbl", "123456789.99");
            System.setProperty("shrt", "1");
            System.setProperty("lng", "987654321");
            System.setProperty("integer", "123");
            System.setProperty("bool", "true");
            System.setProperty("annotation", "@org.jboss.test.aop.annotationproperty.SimpleValue(\"Yes\")");
            System.setProperty("array", "{\"Test\", \"123\"}");
            System.setProperty("clazz", "java.lang.Long.class");
            System.setProperty("enumVal", "org.jboss.test.aop.annotationproperty.MyEnum.TWO");
            return null;
         }});
      
      URL url = getURLRelativeToProjectRoot("/src/resources/test/annotationproperty/annotations-aop.xml");
      AspectXmlLoader.deployXML(url);
      
      POJO pojo = new POJO();
      Advisor advisor = ((Advised)pojo)._getAdvisor();

      SimpleValue value = advisor.resolveTypedAnnotation(SimpleValue.class);
      assertNotNull(value);
      assertEquals(SimpleValue.class, value.annotationType());
      assertEquals("Test", value.value());

      Complex complex = advisor.resolveTypedAnnotation(Complex.class);
      assertNotNull(complex);
      assertEquals(Complex.class, complex.annotationType());
      assertEquals('a', complex.ch());
      assertEquals("Test123", complex.string());
      assertEquals(9,9, complex.flt());
      assertEquals(123456789.99, complex.dbl());
      assertEquals(1, complex.shrt());
      assertEquals(987654321, complex.lng());
      assertEquals(123, complex.integer());
      assertEquals(true, complex.bool());
      assertEquals(Long.class, complex.clazz());
      assertEquals("Yes", complex.annotation().value());
      assertEquals(new String[]{"Test", "123"}, complex.array());
   }
}

