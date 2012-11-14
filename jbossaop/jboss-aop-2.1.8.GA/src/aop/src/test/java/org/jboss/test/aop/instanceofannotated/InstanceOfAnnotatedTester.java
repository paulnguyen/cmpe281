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
package org.jboss.test.aop.instanceofannotated;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 70849 $
 */
@SuppressWarnings("unchecked")
public class InstanceOfAnnotatedTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("InstanceOfAnnotatedTester");
      suite.addTestSuite(InstanceOfAnnotatedTester.class);
      return suite;
   }

   public InstanceOfAnnotatedTester(String name)
   {
      super(name);
   }

   public void testInstanceOfAnnotations()throws Exception
   {
      POJO pojo = new POJO();
      
      CountingInterceptor.count = 0;
      pojo.annotatedMethod();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("annotatedMethod did not intercept " + CountingInterceptor.count );
      }
      
      CountingInterceptor.count = 0;
      pojo.otherAnnotatedMethod();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("otherAnnotatedMethod did not intercept " + CountingInterceptor.count );
      }
      
      CountingInterceptor.count = 0;
      pojo.superClassMethod();       
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("superClassMethod did not intercept " + CountingInterceptor.count );
      }
      
      AnnotatedSuperClass superClass = new AnnotatedSuperClass();
      CountingInterceptor.count = 0;
      superClass.superClassMethod();       
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("AnnotatedSuperClass.superClassMethod did not intercept " + CountingInterceptor.count );
      }
      
      try
      {
         EmptyInterface ei = (EmptyInterface)pojo;
         System.out.println("Cast POJO to Empty interface " + ei);
      }
      catch (RuntimeException e)
      {
         throw new RuntimeException("POJO does not implement EmptyInterface");
      }
   }

   public void testInstanceOfWildcards()throws Exception
   {
      CountingInterceptor.count = 0;
      WildcardPOJO pojo = new WildcardPOJO();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("Did not intercept constructor" + CountingInterceptor.count );
      }

      CountingInterceptor.count = 0;
      pojo.someMethod();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("Did not intercept someMethod()" + CountingInterceptor.count );
      }
      
      CountingInterceptor.count = 0;
      pojo.otherMethod();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("Did not intercept otherMethod()" + CountingInterceptor.count );
      }

      CountingInterceptor.count = 0;
      pojo.anotherMethod();
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("Did not intercept anotherMethod()" + CountingInterceptor.count );
      }
   }

   public void testReturnTypeAndParams() throws Exception
   {
      CountingInterceptor.count = 0;
      POJO pojo = new POJO(new Type(3), new Type(4), 5);
      if (CountingInterceptor.count != 2)
      {
         throw new RuntimeException("Did not intercept constructor:" + CountingInterceptor.count );
      }
      
      CountingInterceptor.count = 0;
      pojo.paramsAndInstanceofReturn(new Type(3), new Type(4), 5);
      if (CountingInterceptor.count != 1)
      {
         throw new RuntimeException("Did not intercept method with instanceof return:" + CountingInterceptor.count );
      }
      
      CountingInterceptor.count = 0;
      pojo.paramsAndTypedefReturn(new Type(4), new Type(5), 6);
      if (CountingInterceptor.count != 2)
      {
         throw new RuntimeException("Did not intercept method with typedef return:" + CountingInterceptor.count );
      }
  }
   
  public void testFieldTypes()throws Exception
  {
     CountingInterceptor.count = 0;
     POJO pojo = new POJO();
     pojo.instanceofField = new Type(5);
     if (CountingInterceptor.count != 1)
     {
        throw new RuntimeException("Did not intercept instanceof field:" + CountingInterceptor.count );
     }
     
     CountingInterceptor.count = 0;
     pojo.typedefField = new Type(5);
     if (CountingInterceptor.count != 1)
     {
        throw new RuntimeException("Did not intercept typedef field:" + CountingInterceptor.count );
     }
     
  }

  
  public void testTypeExpressions() throws Exception
  {
     Class introducedPOJO = IntroducedPOJO.class;
     ClassAdvisor advisor = AspectManager.instance().getAdvisor(introducedPOJO);

     Introduced introduced = advisor.resolveTypedAnnotation(Introduced.class);
     assertNotNull("Class did not have the @Introduced annotation", introduced);
     
     Constructor con = introducedPOJO.getConstructors()[0];
     introduced = advisor.resolveTypedAnnotation(con, Introduced.class);
     assertNotNull("Constructor did not have the @Introduced annotation", introduced);
     Introduced2 introduced2 = advisor.resolveTypedAnnotation(con, Introduced2.class);
     assertNotNull("Constructor did not have the @Introduced2 annotation", introduced2);
     Introduced3 introduced3 = advisor.resolveTypedAnnotation(con, Introduced3.class);
     assertNull("Constructor has the @Introduced3 annotation", introduced3);
     
     Field field = introducedPOJO.getField("field");
     introduced = advisor.resolveTypedAnnotation(field, Introduced.class);
     assertNotNull("Field did not have the @Introduced annotation", introduced);
     introduced2 = advisor.resolveTypedAnnotation(field, Introduced2.class);
     assertNotNull("Field did not have the @Introduced2 annotation", introduced2);
     
     Method method = introducedPOJO.getMethod("someMethod", new Class[]{Type.class, Type.class});
     introduced = advisor.resolveTypedAnnotation(method, Introduced.class);
     assertNotNull("Method did not have the @Introduced annotation", introduced);
     introduced2 = advisor.resolveTypedAnnotation(method, Introduced2.class);
     assertNotNull("Method did not have the @Introduced2 annotation", introduced2);
     introduced3 = advisor.resolveTypedAnnotation(method, Introduced3.class);
     assertNull("Method has the @Introduced3 annotation", introduced3);
 }
 
}