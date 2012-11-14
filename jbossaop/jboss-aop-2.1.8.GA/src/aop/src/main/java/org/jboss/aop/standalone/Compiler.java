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
package org.jboss.aop.standalone;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javassist.bytecode.ClassFile;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Deployment;
import org.jboss.aop.instrument.TransformationException;

/**
 * takes jar or class files and adds needed jboss bytecode
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 79707 $
 */
public class Compiler
{
   private FileFilter classFileFilter = new FileFilter()
   {
      public boolean accept(File pathname)
      {
         return pathname.getName().endsWith(".class");
      }
   };

   private FileFilter directoryFilter = new FileFilter()
   {
      public boolean accept(File pathname)
      {
         return pathname.isDirectory();
      }
   };

   public boolean verbose = false;
   public boolean suppress = true;
   public boolean optimized = true;

   public boolean isJarFile(File src)
   {
      return (src.isFile()
              && (src.getName().toLowerCase().endsWith(".jar")
              || src.getName().toLowerCase().endsWith(".zip"))
              );
   }

   public static void main(String[] args) throws Exception
   {
      long start = System.currentTimeMillis();
      Compiler c = new Compiler();
      try
      {
         c.compile(args);
      }
      catch (Exception e)
      {
         if (c.verbose) throw e;

         if (e instanceof TransformationException)
         {
            System.exit(1);
         }
         throw e;
      }
      System.out.println("Build Successful: " + (System.currentTimeMillis() -start) + " ms");
   }

   public void usage()
   {
      System.err.println("Usage: aopc [-cp <classpath>] [-classpath <classpath>] [-report] [-noopt] [-verbose] [-aoppath <xml files>] <dir>+");
   }

   // Make public and static so that transformers can locate it to do work
   // transformers may generate class files and they need to determine
   // file locations and such.  This will also be used as a flag to tell
   // transformers whether they are in compile or load-time mode.
   public static URLClassLoader loader;

   public void compile(String[] args) throws Exception
   {
      if (args.length == 0)
      {
         usage();
         System.exit(1);
         return;
      }
      ArrayList<URL> paths = new ArrayList<URL>();
      ArrayList<File> files = new ArrayList<File>();
      boolean report = false;
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals("-verbose"))
         {
            verbose = true;
            continue;
         }
         else if (args[i].equals("-suppress"))
         {
            suppress = true;
            continue;
         }
         else if (args[i].equals("-noopt"))
         {
            optimized = false;
            continue;
         }
         else if (args[i].equals("-report"))
         {
            report = true;
            continue;
         }
         else if (args[i].equals("-cp") || args[i].equals("-classpath"))
         {
            if (i + 1 > args.length - 1)
            {
               usage();
               System.exit(1);
               return;
            }
            i++;
            StringTokenizer tokenizer = new StringTokenizer(args[i], File.pathSeparator);
            while (tokenizer.hasMoreTokens())
            {
               String cpath = tokenizer.nextToken();
               File f = new File(cpath);
               // use toURI().toURL() because of bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4273532
               paths.add(f.toURI().toURL());
            }
            continue;
         }
         else if (args[i].equals("-aoppath"))
         {
            System.setProperty("jboss.aop.path", args[++i]);
            continue;
         }
         else if (args[i].equals("-aopclasspath"))
         {
            System.setProperty("jboss.aop.class.path", args[++i]);
            continue;
         }
         else if (args[i].equals("--SOURCEPATH"))
         {
            addFilesFromSourcePathFile(files, args[++i]);
            continue;
         }
         File f = new File(args[i]).getCanonicalFile();
         files.add(f);
      }


      URL[] urls = paths.toArray(new URL[paths.size()]);
      loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

      Thread.currentThread().setContextClassLoader(loader);

      Deployment.searchClasspath = true; // turn on dynamic finding of DDs
      AspectManager.verbose = verbose;
      AspectManager.suppressReferenceErrors = suppress;
      AspectManager.optimize = optimized;
      AspectManager.instance();

      if (report)
      {
         for (int i = 0; i < files.size(); i++)
         {
            File f = files.get(i);
            if (f.isDirectory())
            {
               loadDirectory(f);
            }
            else if (classFileFilter.accept(f))
            {
               loadFile(f);
            }
            else
            {
               if (verbose) System.out.println("[aopc] " + f + " is neither a java class or a directory");
            }
         }
         FileOutputStream reportFile = new FileOutputStream("aop-report.xml");
         reportFile.write(XmlReport.toXml().getBytes());
         reportFile.close();
      }
      else
      {
         //Add all the classes to compile
         for (int i = 0 ; i < files.size() ; i++)
         {
            File f = files.get(i);
            if (f.isDirectory())
            {
               addDirectory(f);
            }
            else if (classFileFilter.accept(f)) 
            {
               addFile(f);
            }
            else
            {
               if (verbose) System.out.println("[aopc] " + f + " is neither a java class or a directory");
            }
         }

         //Compile each class
         for (String className : classesToCompile.keySet())
         {
            CompilerClassInfo info = classesToCompile.get(className);
            compileFile(info);
         }
      }
   }

   private HashMap<String, CompilerClassInfo> classesToCompile = new HashMap<String, CompilerClassInfo>();
   
   private void addDirectory(File dir) throws Exception
   {
      File[] directories = dir.listFiles(directoryFilter);
      File[] classFiles = dir.listFiles(classFileFilter);
      for (int i = 0; i < classFiles.length; i++)
      {
         addFile(classFiles[i]);
      }
      for (int i = 0; i < directories.length; i++)
      {
          addDirectory(directories[i]);
      }
   }
   
   private void loadDirectory(File dir) throws Exception
   {
      File[] directories = dir.listFiles(directoryFilter);
      File[] classFiles = dir.listFiles(classFileFilter);
      for (int i = 0; i < classFiles.length; i++)
      {
         loadFile(classFiles[i]);
      }
      for (int i = 0; i < directories.length; i++)
      {
          loadDirectory(directories[i]);
      }
   }
   
   private void addFile(File file)throws Exception
   {
      int index = file.getName().indexOf('$');
      if (index != -1)
      {
         String fileName = file.getName().substring(0, index) + ".class";
         File superClassFile = new File(fileName);
         // checking last modified date is not enough
         // because these values can differ in some miliseconds sometimes
         if (superClassFile.lastModified() > file.lastModified()
         // so, check this class has been recompiled and, hence,
         // is not Advised anymore
               && !loadFile(superClassFile))
         {
            file.delete();
            return;
         }
      }
      ClassFile cf = createClassFile(file);
      String className = cf.getName();
      String superClassName = cf.getSuperclass();
      CompilerClassInfo info = new CompilerClassInfo(file, className, superClassName);
      classesToCompile.put(className, info);
   }
   
   private ClassFile createClassFile(final File file) throws Exception{
      DataInputStream is = new DataInputStream(new FileInputStream(file));
      ClassFile cf = new ClassFile(is);
      is.close();
      return cf;
   }
   
   private void addFilesFromSourcePathFile(ArrayList<File> files, String sourcePathFile)
   {
      BufferedReader reader = null;

      try
      {
         reader = new BufferedReader(new FileReader(new File(sourcePathFile).getCanonicalFile()));

         String fileName = reader.readLine();
         while (fileName != null)
         {
            files.add(new File(fileName).getCanonicalFile());
            fileName = reader.readLine();
         }
      }
      catch (Exception e)
      {
         try
         {
            reader.close();
         }
         catch (IOException e1)
         {
         }
         throw new RuntimeException(e);
      }
   }

   /**
    * Loads the file and, if it is an advised class, sets its advisor field as
    * accessible.
    * @param file the file of the class to be loaded
    * @return {@code true} is {@code file} contains an advised class.
    */
   public boolean loadFile(File file) throws Exception
   {
      DataInputStream is = new DataInputStream(new FileInputStream(file));
      ClassFile cf = new ClassFile(is);
      is.close();
      Class<?> clazz = loader.loadClass(cf.getName());
      if (org.jboss.aop.Advised.class.isAssignableFrom(clazz))
      {
         Field f = clazz.getDeclaredField("aop$classAdvisor$aop");
         f.setAccessible(true);
         f.get(null);
         return true;
      }
      return false;
   }

   public void compileFile(CompilerClassInfo info) throws Exception
   {
      if (info.isCompiled())
      {
         return;
      }
      
      if (info.getSuperClassName() != null)
      {
         CompilerClassInfo superInfo = classesToCompile.get(info.getSuperClassName());
         if (superInfo != null)
         {
            compileFile(superInfo);
         }
      }
      //System.err.println("[classname] " + className);
      URL classUrl = loader.getResource(info.getClassName().replace('.', '/') + ".class");
      if (classUrl == null)
      {
         System.out.println("[warning] Unable to find " + info.getFile() + " within classpath.  Make sure all transforming classes are within classpath.");
         return;
      }
      
      File classUrlFile = new File(URLDecoder.decode(classUrl.getFile(), "UTF-8"));
      File infoFile = new File(URLDecoder.decode(info.getFile().toString(), "UTF-8"));

      if (!classUrlFile.equals(infoFile))
      {
         System.out.println("[warning] Trying to compile " + info.getFile() + " and found it also within " + classUrl.getFile() + " will not proceed. ");
         return;
      }
      byte[] bytes = AspectManager.instance().transform(loader, info.getClassName(), null, null, null);
      if (bytes == null)
      {
         if (verbose) System.out.println("[no comp needed] " + info.getFile());
         return;
      }
      FileOutputStream os = new FileOutputStream(infoFile);
      os.write(bytes);
      os.close();
      info.setCompiled(true);
      if (verbose) System.out.println("[compiled] " + info.getFile());
   }

   private class CompilerClassInfo
   {
      File file;
      String className;
      String superClassName;
      boolean compiled;
      
      CompilerClassInfo(File file, String className, String superClassName)
      {
         this.file = file;
         this.className = className;
         this.superClassName = superClassName;
      }

      public File getFile()
      {
         return file;
      }

      public boolean isCompiled()
      {
         return compiled;
      }

      public void setCompiled(boolean compiled)
      {
         this.compiled = compiled;
      }

      public String getClassName()
      {
         return className;
      }

      public String getSuperClassName()
      {
         return superClassName;
      }
      
      
   }
}
