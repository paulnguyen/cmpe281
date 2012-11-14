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
package org.jboss.aop.hook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Generate the instrumented version of the classloader and store it in the filesystem.
 *
 * @author kevin
 */
public class GeneratePluggableInstrumentedClassLoader
{
   private static void declare5(CtClass clazz, CtMethod method) throws NotFoundException, CannotCompileException
   {
      method.setName("wrappedDefineClass");
      CtMethod wrapper = CtNewMethod.make(Modifier.PROTECTED, method.getReturnType(), "defineClass", method.getParameterTypes(), method.getExceptionTypes(), null, clazz);

      String code = "{"
              + "  byte[] newBytes = org.jboss.aop.hook.JDK14TransformerManager.transform($0, $1, $2) ;"
              + "  if (newBytes != (byte[])null) {"
              + "    return wrappedDefineClass($1, newBytes, 0, newBytes.length, $5); "
              + "  } else {"
              + "    return wrappedDefineClass($1, $2, $3, $4, $5); "
              + "  }"
              + "}";
      wrapper.setBody(code);
      clazz.addMethod(wrapper);

   }

   /**
    * Get the instrumented version of the class loader.
    *
    * @return The instrumented version of the class loader.
    * @throws javassist.NotFoundException if the class loader cannot be found.
    * @throws javassist.CannotCompileException
    *                                     if the class cannot be compiled.
    * @throws java.io.IOException                 If the class bytecodes cannot be obtained.
    */
   public static byte[] getInstrumentedClassLoader()
           throws NotFoundException, IOException, CannotCompileException
   {
      ClassPool classpool = ClassPool.getDefault();
      classpool = ClassPool.getDefault();
      ClassLoader cl = SecurityActions.getContextClassLoader();
      classpool.insertClassPath(new LoaderClassPath(cl));

      final CtClass clazz = classpool.get(ClassLoader.class.getName());
      CtMethod[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++)
      {
         if (methods[i].getName().equals("defineClass"))
         {
            if (methods[i].getParameterTypes().length == 5
                     && methods[i].getParameterTypes()[1].isArray())
            {
               declare5(clazz, methods[i]);
            }
         }
      }
      return clazz.toBytecode();
   }

   /**
    * Store the instrumented classloader in the filesystem.
    *
    * @param args The filename as the first argument.
    */
   public static void main(final String[] args)
   {
      if (args.length != 1)
      {
         System.err.println("Usage: java " + GeneratePluggableInstrumentedClassLoader.class.getName() + " <output directory>");
         System.exit(1);
      }
      final String filename = args[0] + File.separatorChar +
              "java" + File.separatorChar +
              "lang" + File.separatorChar +
              "ClassLoader.class";
      final File file = new File(filename);
      if (file.exists())
      {
         if (!file.canWrite())
         {
            System.err.println("Cannot write to existing file: " + file.getAbsolutePath());
            System.exit(2);
         }
      }
      else
      {
         final File dir = file.getParentFile();
         if (!dir.exists())
         {
            dir.mkdirs();
         }
         if (!dir.canWrite())
         {
            System.err.println("Cannot write to parent directory: " + dir.getAbsolutePath());
         }
      }

      final byte[] bytes;
      try
      {
         bytes = getInstrumentedClassLoader();
      }
      catch (final Throwable th)
      {
         System.err.println("Unexpected exception caught during instrumentation: " + th.getMessage());
         th.printStackTrace(System.err);
         System.exit(5);
         return;
      }

      try
      {
         final FileOutputStream fos = new FileOutputStream(file);
         fos.write(bytes);
         fos.close();
      }
      catch (final IOException ioe)
      {
         System.err.println("Unexpected exception caught while writing class file: " + ioe.getMessage());
         ioe.printStackTrace(System.err);
         System.exit(6);
      }
   }
}
