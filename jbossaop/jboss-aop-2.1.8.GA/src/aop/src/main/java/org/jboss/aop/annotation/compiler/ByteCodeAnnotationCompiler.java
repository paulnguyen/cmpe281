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
package org.jboss.aop.annotation.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;

/**
 * Convert doclet tags to JBoss AOP annotation XML
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class ByteCodeAnnotationCompiler
{
   ClassPool pool;

   public void usage()
   {
      System.err.println("Usage: annotationc <files>+");
   }

   public void compile(String[] args) throws Exception
   {
      if (args.length == 0)
      {
         usage();
         System.exit(1);
         return;
      }
      JavaDocBuilder builder = new JavaDocBuilder(new AnnotationDocletTagFactory());
      pool = new ClassPool();
      pool.appendSystemPath();
      pool.appendClassPath(new LoaderClassPath(SecurityActions.getContextClassLoader()));
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals("-bytecode"))
         {
            continue;
         }
         if (args[i].equals("-o")) continue;
         if (args[i].equals("-xml")) continue;
         File f = new File(args[i]).getCanonicalFile();
         try
         {
            builder.addSource(new FileReader(f));
         }
         catch (RuntimeException e)
         {
            throw new RuntimeException("Error adding source for file " + f, e);
         }
      }

      for (int i = 0; i < builder.getSources().length; i++)
      {
         JavaSource src = builder.getSources()[i];
         for (int j = 0; j < src.getClasses().length; j++)
         {
            JavaClass clazz = src.getClasses()[j];
            try
            {
               compileClass(clazz);
            }
            catch (Exception e)
            {
               throw new RuntimeException("failed to compile class: " + clazz.getFullyQualifiedName(), e);
            }
         }
      }
   }

   private CtMethod getJavassistMethod(JavaMethod method, CtClass clazz) throws Exception
   {
      CtMethod methods[] = clazz.getDeclaredMethods();
      ArrayList<CtMethod> possible = new ArrayList<CtMethod>();
      for (int i = 0; i < methods.length; i++)
      {
         if (methods[i].getName().equals(method.getName()))
         {
            if (methods[i].getParameterTypes().length == method.getParameters().length)
            {
               possible.add(methods[i]);
            }
         }
      }
      if (possible.size() == 0) throw new RuntimeException("cannot resolve method" + method.toString());
      if (possible.size() == 1) return possible.get(0);

      for (int i = 0; i < possible.size(); i++)
      {
         CtMethod ctMethod = possible.get(i);
         CtClass[] params = ctMethod.getParameterTypes();
         boolean bad = false;
         for (int k = 0; k < params.length; k++)
         {
            if (!params[k].getName().equals(method.getParameters()[k].getType().toString()))
            {
               bad = true;
               break;
            }
         }
         if (!bad) return ctMethod;
      }
      throw new RuntimeException("cannot resolve method" + method.toString());
   }

   private CtConstructor getJavassistConstructor(JavaMethod method, CtClass clazz, boolean isInnerClass) throws Exception
   {
      CtConstructor cons[] = clazz.getDeclaredConstructors();
      ArrayList<CtConstructor> possible = new ArrayList<CtConstructor>();
      for (int i = 0; i < cons.length; i++)
      {
         //non-static inner classes get wrapping class as an "invisible" constructor param 
         if(isInnerClass)
         {
            if (cons[i].getParameterTypes().length == method.getParameters().length+1)
            {
               possible.add(cons[i]);
            }
         }
         else
         {
            if (cons[i].getParameterTypes().length == method.getParameters().length)
            {
               possible.add(cons[i]);
            }
         }
      }
      
      if (possible.size() == 0) throw new RuntimeException("cannot resolve constructor" + method.toString());
      if (possible.size() == 1) return possible.get(0);

      for (int i = 0; i < possible.size(); i++)
      {
         CtConstructor ctCon = possible.get(i);
         CtClass[] params = ctCon.getParameterTypes();
         boolean bad = false;
         for (int k = 0; k < params.length; k++)
         {
            if (!params[k].getName().equals(method.getParameters()[k].getType().toString()))
            {
               bad = true;
               break;
            }
         }
         if (!bad) return ctCon;
      }
      throw new RuntimeException("cannot resolve constructor" + method.toString());
   }

   private void compileClass(JavaClass clazz) throws Exception
   {
      CtClass ctClass = pool.get(clazz.getFullyQualifiedName());
      boolean modified = false;
      for (int i = 0; i < clazz.getTags().length; i++)
      {
         AnnotationDocletTag tag = (AnnotationDocletTag) clazz.getTags()[i];
         if (tag.getAnnotation() == null) continue;
         modified = true;
         javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(pool, ctClass.getClassFile().getConstPool(), tag.getAnnotation());
         AnnotationsAttribute visible = getVisibleAnnotationsAttribute(ctClass);
         visible.addAnnotation(info);
         visible.getAnnotations();
      }
      for (int i = 0; i < clazz.getMethods().length; i++)
      {
         JavaMethod method = clazz.getMethods()[i];
         for (int j = 0; j < method.getTags().length; j++)
         {
            AnnotationDocletTag tag = (AnnotationDocletTag) method.getTags()[j];
            if (tag.getAnnotation() == null) continue;
            modified = true;
            if (method.isConstructor())
            {
               //if its an inner final class then it wont get a 
               //"hidden" param in the constructor.
               if(clazz.isInner() && !clazz.isStatic())
               {
                  compileConstructor(method, tag, ctClass, true);
               }
               else
               {
                  compileConstructor(method, tag, ctClass, false);
               }
            }
            else
            {
               compileMethod(method, tag, ctClass);
            }
         }
      }
      for (int i = 0; i < clazz.getFields().length; i++)
      {
         JavaField field = clazz.getFields()[i];
         for (int j = 0; j < field.getTags().length; j++)
         {
            AnnotationDocletTag tag = (AnnotationDocletTag) field.getTags()[j];
            if (tag.getAnnotation() == null) continue;
            modified = true;
            compileField(field, tag, ctClass);
         }
      }
      
      //if there are inner classes: fetch them and call compileClass recursive.
      for (int i = 0; i < clazz.getNestedClasses().length; i++)
      {
         JavaClass innerClass = clazz.getNestedClasses()[i];
         compileClass(innerClass);
      }
      
      if (modified)
      {
         String classFile = ctClass.getName().replace('.', '/') + ".class";
         URL url = SecurityActions.getContextClassLoader().getResource(classFile);
         if (!url.getProtocol().equals("file"))
         {
            throw new RuntimeException(".class file must not be in a jar: " + url.toString());
         }
         byte[] byteCode = ctClass.toBytecode();
         String path = URLDecoder.decode(url.getFile(), "UTF-8");
         File fp = new File(path);
         FileOutputStream os = new FileOutputStream(fp); 
         os.write(byteCode);
         os.close();
         System.out.println("[compiled] " + fp);
      }


   }

   private AnnotationsAttribute getVisibleAnnotationsAttribute(CtClass ctClass)
   {
      AnnotationsAttribute visible = (AnnotationsAttribute) ctClass.getClassFile().getAttribute(AnnotationsAttribute.visibleTag);
      if (visible == null)
      {
         ClassFile cf = ctClass.getClassFile();
         visible = new AnnotationsAttribute(cf.getConstPool(), AnnotationsAttribute.visibleTag);
         cf.addAttribute(visible);
      }
      return visible;
   }

   private void compileMethod(JavaMethod method, AnnotationDocletTag tag, CtClass clazz) throws Exception
   {
      CtMethod ctMethod = getJavassistMethod(method, clazz);
      MethodInfo minfo = ctMethod.getMethodInfo();
      javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(pool, minfo.getConstPool(), tag.getAnnotation());
      AnnotationsAttribute visible = (AnnotationsAttribute) minfo.getAttribute(AnnotationsAttribute.visibleTag);
      if (visible == null)
      {
         visible = new AnnotationsAttribute(minfo.getConstPool(), AnnotationsAttribute.visibleTag);
         minfo.addAttribute(visible);
      }
      visible.addAnnotation(info);

   }

   private void compileField(JavaField field, AnnotationDocletTag tag, CtClass clazz) throws Exception
   {
      CtField ctField = clazz.getDeclaredField(field.getName());
      FieldInfo minfo = ctField.getFieldInfo();
      javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(pool, minfo.getConstPool(), tag.getAnnotation());
      AnnotationsAttribute visible = (AnnotationsAttribute) minfo.getAttribute(AnnotationsAttribute.visibleTag);
      if (visible == null)
      {
         visible = new AnnotationsAttribute(minfo.getConstPool(), AnnotationsAttribute.visibleTag);
         minfo.addAttribute(visible);
      }
      visible.addAnnotation(info);
   }

   private void compileConstructor(JavaMethod method, AnnotationDocletTag tag, CtClass clazz, boolean isInnerClass) throws Exception
   {
      CtConstructor ctMethod = getJavassistConstructor(method, clazz, isInnerClass);
      MethodInfo minfo = ctMethod.getMethodInfo();
      javassist.bytecode.annotation.Annotation info = AnnotationInfoCreator.createAnnotationInfo(pool, minfo.getConstPool(), tag.getAnnotation());
      AnnotationsAttribute visible = (AnnotationsAttribute) minfo.getAttribute(AnnotationsAttribute.visibleTag);
      if (visible == null)
      {
         visible = new AnnotationsAttribute(minfo.getConstPool(), AnnotationsAttribute.visibleTag);
         minfo.addAttribute(visible);
      }
      visible.addAnnotation(info);
   }
}
