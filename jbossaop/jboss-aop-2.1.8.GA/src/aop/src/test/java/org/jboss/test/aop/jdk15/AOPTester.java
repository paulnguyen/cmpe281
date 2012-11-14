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
package org.jboss.test.aop.jdk15;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.annotation.factory.AnnotationCreator;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 63589 $
 */
public class AOPTester extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   private void printComplex(complex c)
   {
      System.out.print("@complex (ch='" + c.ch() + "', ");
      System.out.print("\"" + c.string() + "\", ");
      System.out.print("flt=" + c.flt() + ", ");
      System.out.print("enumVal=" + c.enumVal() + ", ");
      System.out.println("dbl=" + c.dbl() + ", ...blah blah blah YOU GET THE IDEA...");

   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AOPTester");
      suite.addTestSuite(AOPTester.class);
      return suite;
   }

   public AOPTester(String name)
   {
      super(name);
   }

   // Public -------------------------------------------------------

   public void testCreateAnnotation() throws Exception
   {
      String expr = "@org.jboss.test.aop.jdk15.complex (ch='a', string=\"hello world\", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.jdk15.single(\"hello\"), array={\"hello\", \"world\"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.jdk15.MyEnum.ONE)";
      complex c = (complex) AnnotationCreator.createAnnotation(expr, complex.class);
      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      single s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.ONE) throw new RuntimeException("@complex.enumVal has wrong value");
   }

   public void testAnnotation()
   {
      System.out.println("RUNNING TEST BASIC");

      SimpleInterceptor.intercepted = false;
      AnnotatedPOJO pojo = new AnnotatedPOJO();
      if (!SimpleInterceptor.intercepted) throw new RuntimeException("failed to intercept tagged constructor");

      complex c = (complex) AnnotationElement.getAnyAnnotation(AnnotatedPOJO.class, complex.class);
      printComplex(c);

      SimpleInterceptor.intercepted = false;
      pojo = new AnnotatedPOJO("no interception");
      if (SimpleInterceptor.intercepted) throw new RuntimeException("should not intercept non-tagged constructor");

      pojo.someMethod();
      if (!SimpleInterceptor.intercepted) throw new RuntimeException("failed to intercept tagged method");

      SimpleInterceptor.intercepted = false;
      pojo.nontraceableMethod();
      if (SimpleInterceptor.intercepted) throw new RuntimeException("should not intercept non-tagged method");

      pojo.field = 5;
      if (!SimpleInterceptor.intercepted) throw new RuntimeException("failed to intercept tagged field");

      SimpleInterceptor.intercepted = false;
      pojo.nontraceable = 25;
      if (SimpleInterceptor.intercepted) throw new RuntimeException("should not intercept non-tagged field");

      Introduction intro = (Introduction) pojo;
      intro.helloWorld("hello");
   }
}

