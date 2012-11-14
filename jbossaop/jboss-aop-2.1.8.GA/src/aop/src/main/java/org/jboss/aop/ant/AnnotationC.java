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


import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import java.io.File;
import java.util.ArrayList;

/**
 * Ant task.  Copied a bunch of shit from javac task
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 *
 **/
public class AnnotationC extends MatchingTask
{
   private static String lSep = System.getProperty("line.separator");

   private Path classpath;
   private Path output;
   private Path compilerClasspath;
   private Path compileSourcepath;
   boolean xml = false;
   boolean bytecode = false;

   /**
    *  flag to control action on execution trouble
    */
   protected boolean failOnError = true;

   public void setXml(boolean i)
   {
      xml = i;
   }

   public boolean getXml()
   {
      return xml;
   }

   public void setBytecode(boolean i)
   {
      bytecode = i;
   }

   public boolean getBytecode()
   {
      return xml;
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

   public Path createOutput()
   {
      if (output == null)
      {
         output = new Path(getProject());
      }
      return output.createPath();
   }

   /**
    * Set the classpath to be used to find this compiler adapter
    */
   public void setOutput(Path cp)
   {
      if (output == null)
      {
         output = cp;
      }
      else
      {
         output.append(cp);
      }
   }

   /**
    * get the classpath used to find the compiler adapter
    */
   public Path getOutput()
   {
      return output;
   }

   public void execute()
           throws BuildException
   {
      CommandlineJava cmd = new CommandlineJava();

      if (output != null)
      {
         cmd.createArgument().setValue("-o");
         cmd.createArgument().setValue(output.toString());
      }
      if (xml)
      {
         cmd.createArgument().setValue("-xml");
      }
      if (bytecode)
      {
         cmd.createArgument().setValue("-bytecode");
      }
      logAndAddFilesToCompile(cmd);
      try
      {
         // Create an instance of the compiler, redirecting output to
         // the project log
         classpath.append(compilerClasspath);
         Java java = (Java) (getProject().createTask("java"));
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
         java.setClassname("org.jboss.aop.annotation.compiler.AnnotationCompiler");
         //this is really irritating; we need a way to set stuff
         String args[] = cmd.getJavaCommand().getArguments();
         for (int i = 0; i < args.length; i++)
         {
            java.createArg().setValue(args[i]);
         }
         java.setFailonerror(getFailonerror());
         //we are forking here to be sure that if JspC calls
         //System.exit() it doesn't halt the build
         java.setFork(false);
         java.setTaskName("annotationc");
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
            throw new BuildException("Error running aopc compiler: ",
                    ex, getLocation());
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
            throw new BuildException("srcdir \""
                    + srcDir.getPath()
                    + "\" does not exist!", getLocation());
         }

         DirectoryScanner ds = this.getDirectoryScanner(srcDir);
         String[] files = ds.getIncludedFiles();
         for (int j = 0; j < files.length; j++)
         {
            if (files[j].endsWith(".java"))
            {
               File f = new File(srcDir, files[j]);
               compilingFiles.add(f.getAbsolutePath());
            }
         }
      }


      StringBuffer niceSourceList = new StringBuffer("File");
      for (int i = 0; i < compilingFiles.size(); i++)
      {
         String file = compilingFiles.get(i);
         cmd.createArgument().setValue(file);
         niceSourceList.append("    " + file + lSep);
      }
      //getProject().log(niceSourceList.toString());
   }

}
