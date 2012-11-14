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
package org.jboss.aop.ant;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

/**
 * Ant task.  Copied a bunch of shit from javac task
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 74708 $
 */
public class AopC extends MatchingTask
{
   final static String SYS_OPTIMIZED = "jboss.aop.optimized";
   final static String SYS_INSTRUMENTOR = "jboss.aop.instrumentor";
   private final static String MSG_PREFIX = "\n[aopc] ";
   private static String lSep = System.getProperty("line.separator");
   private int maxSrc = 1000;

   private String instrumentor;
   private String jvm;
   private Path classpath;
   private Path aoppath;
   private Path aopclasspath;
   private Path compilerClasspath;
   private Path compileSourcepath;
   private boolean verbose = false;
   private boolean suppress = true;
   private boolean report = false;
   private boolean optimized = true;
   private String maxmemory = null;
   ArrayList<Environment.Variable> sysproperties = new ArrayList<Environment.Variable>();

   File sourceFiles;
   /**
    * flag to control action on execution trouble
    */
   protected boolean failOnError = true;

   /**
    * Set the verbose level of the compiler
    */
   public void setVerbose(boolean i)
   {
      verbose = i;
   }

   public boolean getVerbose()
   {
      return verbose;
   }

   public String getInstrumentor()
   {
      return instrumentor;
   }

   public void setInstrumentor(String instrumentor)
   {
      this.instrumentor = instrumentor;
   }

   /**
    * Set the verbose level of the compiler
    */
   public void setSuppress(boolean i)
   {
      suppress = i;
   }

   public boolean getSupress()
   {
      return suppress;
   }

   public void setReport(boolean i)
   {
      report = i;
   }

   public boolean getReport()
   {
      return report;
   }

   public String getJvm()
   {
      return jvm;
   }

   public void setJvm(String jvm)
   {
      this.jvm = jvm;
   }

   public void setOptimized(boolean optimized)
   {
      this.optimized = optimized;
   }

   public boolean getOptimized()
   {
      return optimized;
   }

   /**
    * Set the maxmemory of the Java task forked to apply the AOP
    */
   public void setMaxmemory(String maxmemory)
   {
      this.maxmemory = maxmemory;
   }

   public String getMaxmemory()
   {
      return maxmemory;
   }


   /**
    * Whether or not the build should halt if compilation fails.
    * Defaults to <code>true</code>.
    */
   public void setFailonerror(boolean fail)
   {
      failOnError = fail;
   }

   /**
    * Gets the failonerror flag.
    */
   public boolean getFailonerror()
   {
      return failOnError;
   }

   /**
    * Set the classpath to be used for this compilation.
    */
   public void setClasspath(Path cp)
   {
      if (classpath == null)
      {
         classpath = cp;
      }
      else
      {
         classpath.append(cp);
      }
   }

   public void setMaxSrc(int maxSrc)
   {
      this.maxSrc = maxSrc;
   }

   public int getMaxSrc()
   {
      return maxSrc;
   }

   /**
    * Adds a path to the classpath.
    */
   public Path createClasspath()
   {
      if (classpath == null)
      {
         classpath = new Path(getProject());
      }
      return classpath.createPath();
   }

   /**
    * Adds a reference to a classpath defined elsewhere
    */
   public void setClasspathRef(Reference r)
   {
      createClasspath().setRefid(r);
   }

   public Path getClasspath()
   {
      return classpath;
   }

   /**
    * Adds a reference to a classpath defined elsewhere
    */
   public void setCompilerClasspathRef(Reference r)
   {
      if (compilerClasspath == null) compilerClasspath = new Path(getProject());
      compilerClasspath.setRefid(r);
   }

   /**
    * Set the classpath to be used to find this compiler adapter
    */
   public void setCompilerclasspath(Path cp)
   {
      if (compilerClasspath == null)
      {
         compilerClasspath = cp;
      }
      else
      {
         compilerClasspath.append(cp);
      }
   }

   /**
    * get the classpath used to find the compiler adapter
    */
   public Path getCompilerclasspath()
   {
      return compilerClasspath;
   }

   /**
    * Support nested compiler classpath, used to locate compiler adapter
    */
   public Path createCompilerclasspath()
   {
      if (compilerClasspath == null)
      {
         compilerClasspath = new Path(getProject());
      }
      return compilerClasspath.createPath();
   }

   /**
    * Adds a path for source compilation.
    *
    * @return a nested src element.
    */
   public Path createSrc()
   {
      if (compileSourcepath == null)
      {
         compileSourcepath = new Path(getProject());
      }
      return compileSourcepath.createPath();
   }

   public Path createAoppath()
   {
      if (aoppath == null)
      {
         aoppath = new Path(getProject());
      }
      //return aoppath.createPath();
      Path path = aoppath.createPath();
      return path;
   }

   public Path createAopclasspath()
   {
      if (aopclasspath == null)
      {
         aopclasspath = new Path(getProject());
      }
      return aopclasspath.createPath();
   }
   
   public void addSysproperty(Environment.Variable property)
   {
      sysproperties.add(property);
   }

   public void execute()
           throws BuildException
   {
      CommandlineJava cmd = new CommandlineJava();
      if (compileSourcepath == null)
      {
         throw new BuildException(MSG_PREFIX + "'src' path element not found");
      }
      
      if (classpath == null)
      {
         throw new BuildException(MSG_PREFIX + "classpath undefined (use 'classpath' path elements and/or the 'classpathref' attribute)");
      }
      
      if (verbose)
         cmd.createArgument().setValue("-verbose");
      if (suppress)
         cmd.createArgument().setValue("-suppress");
      if (!optimized)
         cmd.createArgument().setValue("-noopt");
      if (report)
         cmd.createArgument().setValue("-report");
      if (aoppath != null && aoppath.size() > 0)
      {
         cmd.createArgument().setValue("-aoppath");
         cmd.createArgument().setValue(aoppath.toString());
      }
      if (aopclasspath != null && aopclasspath.size() > 0)
      {
         cmd.createArgument().setValue("-aopclasspath");
         cmd.createArgument().setValue(aopclasspath.toString());
      }
      logAndAddFilesToCompile(cmd);
      
      try
      {
         // Create an instance of the compiler, redirecting output to
         // the project log
         classpath.append(compilerClasspath);
         Java java = (Java) (getProject().createTask("java"));
         if (jvm != null && jvm.length() > 0)
         {
            java.setJvm(jvm);
         }
         if (getClasspath() != null)
         {
            getProject().log("using user supplied classpath: "
                    + getClasspath(), Project.MSG_DEBUG);
            java.setClasspath(getClasspath()
                    .concatSystemClasspath("ignore"));
         }
         else
         {
            Path classpath = new Path(getProject());
            classpath = classpath.concatSystemClasspath("only");
            getProject().log("using system classpath: " + classpath,
                    Project.MSG_DEBUG);
            java.setClasspath(classpath);
         }
         java.setDir(getProject().getBaseDir());
         java.setClassname("org.jboss.aop.standalone.Compiler");
         //this is really irritating; we need a way to set stuff
         String args[] = cmd.getJavaCommand().getArguments();
         for (int i = 0; i < args.length; i++)
         {
            java.createArg().setValue(args[i]);
         }
         java.setFailonerror(getFailonerror());
         //we are forking here to be sure that if JspC calls
         //System.exit() it doesn't halt the build
         java.setFork(true);
         java.setTaskName("aopc");

         Environment.Variable optimize = new Environment.Variable();
         optimize.setKey(SYS_OPTIMIZED);
         optimize.setValue(String.valueOf(optimized));
         java.addSysproperty(optimize);

         for (Environment.Variable var : sysproperties)
         {
            java.addSysproperty(var);
         }
         
         if (maxmemory != null)
         {
            java.setMaxmemory(maxmemory);
         }
         java.execute();
      }
      catch (Exception ex)
      {
         if (ex instanceof BuildException)
         {
            throw (BuildException) ex;
         }
         else
         {
            throw new BuildException(MSG_PREFIX + "error running aopc compiler",
                    ex, getLocation());
         }
      }
      finally
      {
         if (sourceFiles != null)
         {
            //deleteOnExit does not seem to work so do it explicitly here
            sourceFiles.delete();
         }
      }
   }

   protected void logAndAddFilesToCompile(CommandlineJava cmd)
   {
      // scan source directories and dest directory to build up
      // compile lists
      String[] list = compileSourcepath.list();
      ArrayList<String> compilingFiles = new ArrayList<String>();
      for (int i = 0; i < list.length; i++)
      {
         File srcDir = getProject().resolveFile(list[i]);
         if (!srcDir.exists())
         {
            throw new BuildException(MSG_PREFIX + "srcdir \""
                    + srcDir.getPath()
                    + "\" does not exist!", getLocation());
         }

         DirectoryScanner ds = this.getDirectoryScanner(srcDir);
         String[] files = ds.getIncludedFiles();
         for (int j = 0; j < files.length; j++)
         {
            if (files[j].endsWith(".class"))
            {
               File f = new File(srcDir, files[j]);
               compilingFiles.add(f.getAbsolutePath());
            }
         }
      }

      int length = 0;
      for (int i = 0; i < compilingFiles.size(); i++)
      {
         //Add 1 to allow for the spaces between args
         length += compilingFiles.get(i).length() + 1;

         if (length > maxSrc)
         {
            break;
         }
      }

      if (length < maxSrc)
      {
         StringBuffer niceSourceList = new StringBuffer("Files\n");
         for (int i = 0; i < compilingFiles.size(); i++)
         {
            String file = compilingFiles.get(i);
            cmd.createArgument().setValue(file);
            niceSourceList.append("    " + file + lSep);
         }
      }
      else
      {
         for (Environment.Variable var : sysproperties)
         {
            if (var.getKey().equals("java.io.tmpdir"))
            {
               System.setProperty("java.io.tmpdir", var.getValue());
            }
         }
         BufferedWriter writer = null;
         try
         {
            sourceFiles = File.createTempFile("src", ".tmp");
            if (verbose)
            {
               System.out.println("[info] Total length of filenames to be compiled is greater than "
                       + maxSrc + ", listing files in --SOURCEPATH: " + sourceFiles.getAbsolutePath());
            }

            sourceFiles.deleteOnExit();
            writer = new BufferedWriter(new FileWriter(sourceFiles));

            for (int i = 0; i < compilingFiles.size(); i++)
            {
               writer.write(compilingFiles.get(i));
               writer.newLine();
            }
            writer.flush();
            cmd.createArgument().setValue("--SOURCEPATH");
            cmd.createArgument().setValue(sourceFiles.getAbsolutePath());
         }
         catch (IOException e)
         {
            if (writer != null)
            {
               try
               {
                  writer.close();
               }
               catch (IOException e1)
               {
               }
            }
            throw new RuntimeException(e);
         }
      }
   }

}