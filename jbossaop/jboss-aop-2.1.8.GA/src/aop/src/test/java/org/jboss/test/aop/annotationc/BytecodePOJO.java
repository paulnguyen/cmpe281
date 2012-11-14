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

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37824 $
 * @@org.jboss.test.aop.annotationc.empty
 * @@org.jboss.test.aop.annotationc.single ("hello world")
 * @@org.jboss.test.aop.annotationc.complex (ch='a', string="hello world", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.annotationc.single("hello"), array={"hello", "world"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.annotationc.MyEnum.ONE)
 */
public class BytecodePOJO
{
   /**
    * @@org.jboss.test.aop.annotationc.empty
    * @@org.jboss.test.aop.annotationc.single ("hello world")
    * @@org.jboss.test.aop.annotationc.complex (ch='a', string="hello world", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.annotationc.single("hello"), array={"hello", "world"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.annotationc.MyEnum.TWO)
    */
   public BytecodePOJO()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.empty
    * @@org.jboss.test.aop.annotationc.single ("hello world")
    * @@org.jboss.test.aop.annotationc.complex (ch='a', string="hello world", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.annotationc.single("hello"), array={"hello", "world"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.annotationc.MyEnum.ONE)
    */
   public int field;

   /**
    * @@org.jboss.test.aop.annotationc.empty
    * @@org.jboss.test.aop.annotationc.single ("hello world")
    * @@org.jboss.test.aop.annotationc.complex (ch='a', string="hello world", flt=5.5, dbl=6.6, shrt=5, lng=6, integer=7, bool=true, annotation=@org.jboss.test.aop.annotationc.single("hello"), array={"hello", "world"}, clazz=java.lang.String, enumVal=org.jboss.test.aop.annotationc.MyEnum.TWO)
    */
   public void method()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (void.class)
    */
   public void someVoid()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (int.class)
    */
   public void someInt()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (short.class)
    */
   public void someShort()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (long.class)
    */
   public void someLong()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (double.class)
    */
   public void someDouble()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (float)
    */
   public void someFloat()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (char)
    */
   public void someChar()
   {
   }

   /**
    * @@org.jboss.test.aop.annotationc.ClassAnnotation (boolean)
    */
   public void someBoolean()
   {
   }
   
   /**
    * @@org.jboss.test.aop.annotationc.empty
    * @@org.jboss.test.aop.annotationc.single ("inner")
    */
   public class InnerClass
   {
      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("inner ctor")
       */
      public InnerClass()
      {
         
      }
      
      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("inner ctor 2")
       */
      public InnerClass(int i)
      {
         
      }

      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("inner method")
       */
      public void method(int i)
      {
         
      }

      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("inner field")
       */
      int field;
   }
   
   /**
    * @@org.jboss.test.aop.annotationc.empty
    * @@org.jboss.test.aop.annotationc.single ("static inner")
    */
   public static class StaticInnerClass
   {
      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("static inner ctor")
       */
      public StaticInnerClass()
      {
         
      }

      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("static inner ctor 2")
       */
      public StaticInnerClass(int i)
      {
         
      }

      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("static inner method")
       */
      public void method(int i)
      {
         
      }

      /**
       * @@org.jboss.test.aop.annotationc.empty
       * @@org.jboss.test.aop.annotationc.single ("static inner field")
       */
      int field;
   }
   
   public class NotAnnotated
   {
      
   }
}
