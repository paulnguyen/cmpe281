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
package org.jboss.test.aop.stress.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.IntBuffer;
import java.sql.Blob;

import javassist.NotFoundException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class LargePojo
{
   //Add loads of fields and methods to try to make this slow to load up so we get synchronization issues 
   private String f1;
   private Integer f2;
   private Long f3;
   private InputStream f4;
   private OutputStream f5;
   private Boolean f6;
   private Character f7;
   private CharSequence f8;
   private Double f9;
   private Float f10;
   private PrintWriter f11;
   private URL f12;
   private Byte f13;
   private Blob f14;
   private IntBuffer f15;
   private Exception f16;
   private IOException f17;
   private NotFoundException f18;
   private IllegalAccessException f19;
   private IllegalAccessError f20;
   private IllegalArgumentException f21;
   private IllegalMonitorStateException f22;
   private IllegalThreadStateException f23;
   private NoClassDefFoundError f24;
   private NoSuchFieldError f25;
   private NoSuchMethodError f26;
   
   public NoSuchMethodError field; //This is the one we will check for

   public void method( 
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21,
         IllegalMonitorStateException f22,
         IllegalThreadStateException f23,
         NoClassDefFoundError f24,
         NoSuchFieldError f25,
         NoSuchMethodError f26
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21,
         IllegalMonitorStateException f22,
         IllegalThreadStateException f23,
         NoClassDefFoundError f24,
         NoSuchFieldError f25
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21,
         IllegalMonitorStateException f22,
         IllegalThreadStateException f23,
         NoClassDefFoundError f24
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21,
         IllegalMonitorStateException f22,
         IllegalThreadStateException f23
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21,
         IllegalMonitorStateException f22
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20,
         IllegalArgumentException f21
         )
   {
      
   }
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19,
         IllegalAccessError f20
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18,
         IllegalAccessException f19
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17,
         NotFoundException f18
         )
   {
      
   }

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16,
         IOException f17
         )
   {
      
   }
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15,
         Exception f16
         )
   {
      
   }
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14,
         IntBuffer f15
         )
   {
      
   }   
   
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13,
         Blob f14
         )
   {
      
   }   
   
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12,
         Byte f13
         )
   {
      
   }   
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11,
         URL f12
         )
   {
      
   }  
   

   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10,
         PrintWriter f11
         )
   {
      
   }   
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9,
         Float f10
         )
   {
      
   }   
 
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8,
         Double f9
         )
   {
      
   }   
 
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7,
         CharSequence f8
         )
   {
      
   }   
      
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6,
         Character f7
         )
   {
      
   }   
      
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5,
         Boolean f6
         )
   {
      
   }   
 
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4,
         OutputStream f5
         )
   {
      
   }   
   
   public void method(
         String f1,
         Integer f2,
         Long f3,
         InputStream f4
         )
   {
      
   }
   
   public void method(
         String f1,
         Integer f2,
         Long f3
         )
   {
      
   }   
      
   public void method(
         String f1,
         Integer f2
         )
   {
      
   }   
 
   public void method(
         String f1
         )
   {
      
   }   
   
   //This is the one we will check for
   public void method()
   {
      
   }   
   
}