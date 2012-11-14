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
package org.jboss.aop.annotation.util;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FindAnnotations
{
   public static ClassPool pool;

   public static void main(String[] args) throws Exception
   {
      pool = ClassPool.getDefault();
      pool.appendSystemPath();
      pool.insertClassPath(".");
      CtClass clazz = pool.get(args[0]);
      clazz.getClassFile2().getConstPool().print();
      printClassAttributes(clazz);
   }

   public static void printAnnotations(Annotation[] annotations)
   {
      for (int i = 0; i < annotations.length; i++)
      {
         printAnnotation(annotations[i]);
      }
   }

   public static void printClassAttributes(CtClass clazz) throws Exception
   {
      ClassFile cf = clazz.getClassFile();
      AnnotationsAttribute invisible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.invisibleTag);
      if (invisible != null)
      {
         System.out.println("** invisible class annotations **");
         Annotation[] annotations = invisible.getAnnotations();
         printAnnotations(annotations);
      }

      AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
      if (visible != null)
      {
         System.out.println("** visible class annotations **");
         Annotation[] annotations = visible.getAnnotations();
         printAnnotations(annotations);
      }

      System.out.println("**** method annotations ****");
      List<MethodInfo> methods = cf.getMethods();
      for (int i = 0; i < methods.size(); i++)
      {
         MethodInfo mi = methods.get(i);
         System.out.println("method: " + mi.getName());
         System.out.println("CONST POOL: ");
         mi.getConstPool().print();
         System.out.println("-------------");
         invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
         if (invisible != null)
         {
            System.out.println("** invisible method annotations **");
            Annotation[] annotations = invisible.getAnnotations();
            printAnnotations(annotations);
         }

         visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
         if (visible != null)
         {
            System.out.println("** visible method annotations **");
            Annotation[] annotations = visible.getAnnotations();
            printAnnotations(annotations);
         }

         System.out.println("----");
      }

      System.out.println("**** field annotations ****");
      List<FieldInfo> fields = cf.getFields();
      for (int i = 0; i < fields.size(); i++)
      {
         FieldInfo mi = fields.get(i);
         System.out.println("field: " + mi.getName());
         System.out.println("CONST POOL: ");
         mi.getConstPool().print();
         System.out.println("field: " + mi.getName());
         invisible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.invisibleTag);
         if (invisible != null)
         {
            System.out.println("** invisible method annotations **");
            Annotation[] annotations = invisible.getAnnotations();
            printAnnotations(annotations);
         }

         visible = (AnnotationsAttribute) mi.getAttribute(AnnotationsAttribute.visibleTag);
         if (visible != null)
         {
            System.out.println("** visible method annotations **");
            Annotation[] annotations = visible.getAnnotations();
            printAnnotations(annotations);
         }

         System.out.println("----");
      }
   }

   public static void printAnnotation(Annotation info)
   {
      System.out.print("@" + info.getTypeName());
      Set<String> members = info.getMemberNames();
      if (members != null)
      {
         System.out.print("(");
         Iterator<String> mit = members.iterator();
         while (mit.hasNext())
         {
            String name = mit.next();
            System.out.print(name + "=" + info.getMemberValue(name).toString());
            if (mit.hasNext()) System.out.print(", ");
         }
         System.out.print(")");
      }
      System.out.println("");
   }
}

