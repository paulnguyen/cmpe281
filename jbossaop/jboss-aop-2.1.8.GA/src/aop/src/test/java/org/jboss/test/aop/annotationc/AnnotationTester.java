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
package org.jboss.test.aop.annotationc;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.annotation.factory.AnnotationCreator;
import org.jboss.aop.util.ConstructorComparator;
import org.jboss.test.aop.AOPTestWithSetup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Tests an annotated introduction
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class AnnotationTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotationTester");
      suite.addTestSuite(AnnotationTester.class);
      return suite;
   }
   // Constants ----------------------------------------------------
   // Attributes ---------------------------------------------------

   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   //     * @@org.jboss.test.aop.annotationc.complex(ch='a', string="hello world", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@single("hello"), array={"hello", "world"), clazz=java.lang.String)

   public AnnotationTester(String name)
   {
      super(name);
   }

   public void testCreateAnnotation() throws Exception
   {
      String expr = "@org.jboss.test.aop.annotationc.complex (ch='a', string=\"hello world\", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.annotationc.single(\"hello\"), array={\"hello\", \"world\"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.annotationc.MyEnum.ONE)";
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

   public void testClassAnnotation()
   {
      empty e = (empty) AnnotationElement.getAnyAnnotation(BytecodePOJO.class, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single) AnnotationElement.getAnyAnnotation(BytecodePOJO.class, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      complex c = (complex) AnnotationElement.getAnyAnnotation(BytecodePOJO.class, complex.class);
      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.ONE) throw new RuntimeException("@complex.enumVal has wrong value");
   }

   public void testClassGetVisibleAnnotations()
   {
      empty e = null;
      single s = null;
      complex c = null;
      try
      {
         Object[] annotations = AnnotationElement.getVisibleAnnotations(BytecodePOJO.class);
         for (int i = 0 ; i < annotations.length ; i++)
         {
            if (complex.class.isAssignableFrom(annotations[i].getClass()))
            {
               c = (complex)annotations[i];
            }
            else if (empty.class.isAssignableFrom(annotations[i].getClass()))
            {
               e = (empty)annotations[i];
            }
            else if (single.class.isAssignableFrom(annotations[i].getClass()))
            {
               s = (single)annotations[i];
            }
         }
      }
      catch (Exception e1)
      {
         // AutoGenerated
         throw new RuntimeException(e1);
      }

      assertNotNull(c);
      assertNotNull(e);
      assertNotNull(s);         

      if (e == null) throw new RuntimeException("failed to get @empty");
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.ONE) throw new RuntimeException("@complex.enumVal has wrong value");
      
   }

   
   public void testConstructorAnnotation()
   {
      Constructor con = BytecodePOJO.class.getConstructors()[0];
      empty e = (empty) AnnotationElement.getAnyAnnotation(con, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single) AnnotationElement.getAnyAnnotation(con, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      complex c = (complex) AnnotationElement.getAnyAnnotation(con, complex.class);
      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.TWO) throw new RuntimeException("@complex.enumVal has wrong value");
   }

   public void testConstructorGetVisibleAnnotations() throws Exception
   {
      Constructor con = BytecodePOJO.class.getConstructors()[0];
      empty e = null;
      complex c = null;
      single s = null;
      
      try
      {
         Object[] annotations = AnnotationElement.getVisibleAnnotations(con);
         for (int i = 0 ; i < annotations.length ; i++)
         {
            if (complex.class.isAssignableFrom(annotations[i].getClass()))
            {
               c = (complex)annotations[i];
            }
            else if (empty.class.isAssignableFrom(annotations[i].getClass()))
            {
               e = (empty)annotations[i];
            }
            else if (single.class.isAssignableFrom(annotations[i].getClass()))
            {
               s = (single)annotations[i];
            }
         }
      }
      catch (Exception e1)
      {
         throw new RuntimeException(e1);
      }
      assertNotNull(c);
      assertNotNull(e);
      assertNotNull(s);         

      if (e == null) throw new RuntimeException("failed to get @empty");
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.TWO) throw new RuntimeException("@complex.enumVal has wrong value");
      
   }
   
   public void testMethodAnnotation() throws Exception
   {
      Method con = BytecodePOJO.class.getMethod("method", new Class[0]);
      empty e = (empty) AnnotationElement.getAnyAnnotation(con, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single) AnnotationElement.getAnyAnnotation(con, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      complex c = (complex) AnnotationElement.getAnyAnnotation(con, complex.class);
      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.TWO) throw new RuntimeException("@complex.enumVal has wrong value");
   }

   public void testMethodGetVisibleAnnotations() throws Exception
   {
      Method con = BytecodePOJO.class.getMethod("method", new Class[0]);
      empty e = null;
      complex c = null;
      single s = null;
      
      try
      {
         Object[] annotations = AnnotationElement.getVisibleAnnotations(con);
         for (int i = 0 ; i < annotations.length ; i++)
         {
            if (complex.class.isAssignableFrom(annotations[i].getClass()))
            {
               c = (complex)annotations[i];
            }
            else if (empty.class.isAssignableFrom(annotations[i].getClass()))
            {
               e = (empty)annotations[i];
            }
            else if (single.class.isAssignableFrom(annotations[i].getClass()))
            {
               s = (single)annotations[i];
            }
         }
      }
      catch (Exception e1)
      {
         throw new RuntimeException(e1);
      }
      assertNotNull(c);
      assertNotNull(e);
      assertNotNull(s);         

      if (e == null) throw new RuntimeException("failed to get @empty");
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.TWO) throw new RuntimeException("@complex.enumVal has wrong value");
   }

   public void testPrimitiveClassAnnotationCreation() throws Exception
   {
      testPrimitiveClassCreate("void.class", void.class);
      testPrimitiveClassCreate("int.class", int.class);
      testPrimitiveClassCreate("short.class", short.class);
      testPrimitiveClassCreate("long.class", long.class);
      testPrimitiveClassCreate("double.class", double.class);
      testPrimitiveClassCreate("float.class", float.class);
      testPrimitiveClassCreate("char.class", char.class);
      testPrimitiveClassCreate("boolean.class", boolean.class);
   }

   private void testPrimitiveClassCreate(String sPrimitive, Class primitive)
   throws Exception
   {
      String expr = "@org.jboss.test.aop.annotationc.ClassAnnotation (" + sPrimitive + ")";
      ClassAnnotation c = (ClassAnnotation) AnnotationCreator.createAnnotation(expr, ClassAnnotation.class);
      if (!c.value().equals(primitive)) throw new RuntimeException("failed to match " + primitive.getName());
   }

   public void testPrimitiveClassMemberAnnotation() throws Exception
   {
      Class clazz = BytecodePOJO.class;
      Class[] types = {};
      primitive(clazz.getMethod("someVoid", types), void.class);
      primitive(clazz.getMethod("someInt", types), int.class);
      primitive(clazz.getMethod("someShort", types), short.class);
      primitive(clazz.getMethod("someLong", types), long.class);
      primitive(clazz.getMethod("someDouble", types), double.class);
      primitive(clazz.getMethod("someFloat", types), float.class);
      primitive(clazz.getMethod("someChar", types), char.class);
      primitive(clazz.getMethod("someBoolean", types), boolean.class);
   }

   private void primitive(Method method, Class primitive)
   {
      ClassAnnotation ca = (ClassAnnotation) AnnotationElement.getAnyAnnotation(method, ClassAnnotation.class);
      if (ca == null) throw new RuntimeException("Failed to get ClassAnnotation for: " + method);
      if (!ca.value().equals(primitive)) throw new RuntimeException("failed to match " + primitive.getName() + "  for method: " + method);
   }

   public void testFieldAnnotation() throws Exception
   {
      Field con = BytecodePOJO.class.getField("field");
      empty e = (empty) AnnotationElement.getAnyAnnotation(con, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single) AnnotationElement.getAnyAnnotation(con, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      complex c = (complex) AnnotationElement.getAnyAnnotation(con, complex.class);
      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.ONE) throw new RuntimeException("@complex.enumVal has wrong value");
   }
   
   public void testFieldGetVisibleAnnotations() throws Exception
   {
      Field con = BytecodePOJO.class.getField("field");
      empty e = null;
      complex c = null;
      single s = null;
      
      try
      {
         Object[] annotations = AnnotationElement.getVisibleAnnotations(con);
         for (int i = 0 ; i < annotations.length ; i++)
         {
            if (complex.class.isAssignableFrom(annotations[i].getClass()))
            {
               c = (complex)annotations[i];
            }
            else if (empty.class.isAssignableFrom(annotations[i].getClass()))
            {
               e = (empty)annotations[i];
            }
            else if (single.class.isAssignableFrom(annotations[i].getClass()))
            {
               s = (single)annotations[i];
            }
         }
      }
      catch (Exception e1)
      {
         throw new RuntimeException(e1);
      }
      assertNotNull(c);
      assertNotNull(e);
      assertNotNull(s);         

      if (e == null) throw new RuntimeException("failed to get @empty");
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("hello world")) throw new RuntimeException("@single.value mismatch: " + s.value());

      if (c == null) throw new RuntimeException("failed to get @complex");
      if (c.ch() != 'a') throw new RuntimeException("@complex.ch has wrong value");
      if (!c.string().equals("hello world")) throw new RuntimeException("@complex.string has wrong value");
      if (c.flt() != 5.5) throw new RuntimeException("@complex.flt has wrong value");
      if (c.dbl() != 6.6) throw new RuntimeException("@complex.dbl has wrong value");
      if (c.shrt() != 5) throw new RuntimeException("@complex.shrt has wrong value");
      if (c.lng() != 6) throw new RuntimeException("@complex.lng has wrong value");
      if (c.integer() != 7) throw new RuntimeException("@complex.integer has wrong value");
      if (c.bool() == false) throw new RuntimeException("@complex.bool has wrong value");
      s = c.annotation();
      if (s == null) throw new RuntimeException("@complex.annotation is null");
      if (!s.value().equals("hello")) throw new RuntimeException("@complex.annotation has wrong value");
      if (!c.array()[0].equals("hello")) throw new RuntimeException("@complex.array[0] has wrong value");
      if (!c.array()[1].equals("world")) throw new RuntimeException("@complex.array[1] has wrong value");
      if (!java.lang.String.class.equals(c.clazz())) throw new RuntimeException("@complex.clazz has wrong value");
      if (c.enumVal() != MyEnum.ONE) throw new RuntimeException("@complex.enumVal has wrong value");
   }
   
   public void testInnerClass() throws Exception
   {
      //Check class
      empty e = (empty)AnnotationElement.getAnyAnnotation(BytecodePOJO.InnerClass.class, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single)AnnotationElement.getAnyAnnotation(BytecodePOJO.InnerClass.class, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("inner")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Non static inner classes get an extra ctor param containing the outer class, do sorting here to get right order between
      //default and other ctor
      Constructor<?>[] ctors = BytecodePOJO.InnerClass.class.getDeclaredConstructors();
      Arrays.sort(ctors, ConstructorComparator.INSTANCE);

      System.out.println(ctors[0]);
      System.out.println(ctors[1]);
      //Check default ctor
      Constructor def = ctors[0];
      e = (empty)AnnotationElement.getAnyAnnotation(def, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(def, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("inner ctor")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Check ctor
      Constructor con = ctors[1];
      e = (empty)AnnotationElement.getAnyAnnotation(con, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(con, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("inner ctor 2")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Check method
      Method m = BytecodePOJO.InnerClass.class.getDeclaredMethod("method", new Class[] {Integer.TYPE});
      e = (empty)AnnotationElement.getAnyAnnotation(m, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(m, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("inner method")) throw new RuntimeException("@single.value mismatch: " + s.value());

      //check field
      Field f = BytecodePOJO.InnerClass.class.getDeclaredField("field");
      e = (empty)AnnotationElement.getAnyAnnotation(f, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(f, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("inner field")) throw new RuntimeException("@single.value mismatch: " + s.value());
   }

   public void testStaticInnerClass() throws Exception
   {
      //Check class
      empty e = (empty)AnnotationElement.getAnyAnnotation(BytecodePOJO.StaticInnerClass.class, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      single s = (single)AnnotationElement.getAnyAnnotation(BytecodePOJO.StaticInnerClass.class, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("static inner")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Check default ctor
      Constructor def = BytecodePOJO.StaticInnerClass.class.getDeclaredConstructor(new Class[0]);
      e = (empty)AnnotationElement.getAnyAnnotation(def, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(def, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("static inner ctor")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Check ctor
      Constructor con = BytecodePOJO.StaticInnerClass.class.getDeclaredConstructor(new Class[] {Integer.TYPE});
      e = (empty)AnnotationElement.getAnyAnnotation(con, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(con, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("static inner ctor 2")) throw new RuntimeException("@single.value mismatch: " + s.value());
      
      //Check method
      Method m = BytecodePOJO.StaticInnerClass.class.getDeclaredMethod("method", new Class[] {Integer.TYPE});
      e = (empty)AnnotationElement.getAnyAnnotation(m, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(m, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("static inner method")) throw new RuntimeException("@single.value mismatch: " + s.value());

      //check field
      Field f = BytecodePOJO.StaticInnerClass.class.getDeclaredField("field");
      e = (empty)AnnotationElement.getAnyAnnotation(f, empty.class);
      if (e == null) throw new RuntimeException("failed to get @empty");
      s = (single)AnnotationElement.getAnyAnnotation(f, single.class);
      if (s == null) throw new RuntimeException("failed to get @single");
      if (!s.value().equals("static inner field")) throw new RuntimeException("@single.value mismatch: " + s.value());
   }
}

