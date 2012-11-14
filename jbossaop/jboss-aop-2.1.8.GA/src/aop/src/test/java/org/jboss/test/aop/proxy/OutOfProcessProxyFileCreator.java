/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.proxy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class OutOfProcessProxyFileCreator implements ProxyFileCreator
{
   String proxyCreatorClass;
   
   public OutOfProcessProxyFileCreator(String proxyCreatorClass)
   {
      this.proxyCreatorClass = proxyCreatorClass;
   }

   public File createProxyFile() throws Exception
   {
      return runExternalProcess();
   }

   private File runExternalProcess() throws Exception
   {
      Properties props = System.getProperties();
      
      String classPath = props.getProperty("java.class.path");      
      String libraryPath = props.getProperty("sun.boot.library.path") + File.separator + "java";
      String java = findJava(libraryPath);
   
      File proxyFile = File.createTempFile("proxy", "err");
      proxyFile.deleteOnExit();
      
      boolean debugFlag = System.getProperty("jboss.aop.debug.classes", "false").equals("true"); 
      String debug = debugFlag ? "-Djboss.aop.debug.classes=true " : "";

      if (classPath.contains(" "))
      {
         throw new RuntimeException("Classpath should not contain the space character for this test. " + classPath);
      }
      
      String run = java + 
         " -classpath " + classPath + " " + 
         debug + 
         proxyCreatorClass + " " + 
         proxyFile.getAbsolutePath().replace('\\', '/');
      
      Process proc = Runtime.getRuntime().exec(run);
      
      int result = proc.waitFor();

      InputStream sysout = proc.getInputStream();
      InputStream syserr = proc.getErrorStream();

      outputStream("System.out", sysout);
      outputStream("System.err", syserr);
      
      switch (result)
      {
         case ProxyFileCreatorDelegate.FEW_ARGS:
            throw new RuntimeException("Too few args passed in");
         case ProxyFileCreatorDelegate.MANY_ARGS:
            throw new RuntimeException("Too many args passed in");
         case ProxyFileCreatorDelegate.NO_SUCH_FILE:
            throw new RuntimeException("No file found " + proxyFile);
         case ProxyFileCreatorDelegate.GENERAL_ERROR:
            String externalException = getExternalException(proxyFile);
            throw new RuntimeException(externalException);
      }
      
      return proxyFile;
   }
   
   private void outputStream(String type, InputStream in) throws IOException
   {
      System.out.println("= = = = = = = = = = Other process " + type);
      int i = in.read();
      while(i != -1)
      {
         System.out.print((char)i);
         i = in.read();
      }
      System.out.println("= = = = = = = = = = END Other process " + type);
   }
   
   private String getExternalException(File proxyFile)
   {
      Reader reader = null;
      StringBuffer sb = new StringBuffer();
      try
      {
         reader = new FileReader(proxyFile);
         int r = reader.read();
         while (r != -1)
         {
            sb.append((char)r);
            r = reader.read();
         }
      }
      catch(Exception e)
      {
      }
      finally
      {
         try
         {
            reader.close();
         }
         catch(Exception e)
         {
         }
      }
      return sb.toString();
   }
   
   private String findJava(String classPath)
   {
      String java = null;
      StringTokenizer tok = new StringTokenizer(classPath, File.pathSeparator);
      while (tok.hasMoreTokens())
      {
         String path = tok.nextToken();
         if (path.endsWith(".jar"))
         {
            continue;
         }
         java = getJavaPath(path);
         if (java != null)
         {
            return java;
         }
      }
      return null;
   }
   
   private String getJavaPath(String dirName)
   {
      if (File.separatorChar == '/')
      {
         //Probably on Linux
         if (dirName.contains("jre"))
         {
            dirName = dirName.substring(0, dirName.indexOf(File.separatorChar + "jre"));
         }
         String file = getFile(dirName + File.separator + "bin" + File.separator + "java");
         if (file != null)
         {
            return file;
         }
         file = getFile(dirName);
         if (file != null)
         {
            return file;
         }
      }
      else if (File.separatorChar == '\\')
      {
         //We're probably on windows
         String file = getFile(dirName + File.separator + "java.exe");
         if (file != null)
         {
            return file;
         }
         file = getFile(dirName);
         if (file != null)
         {
            return file;
         }
         file = getFile(dirName + ".exe");
         if (file != null)
         {
            return file;
         }
      }
      else
      {
         throw new RuntimeException("Cannot figure out OS");
      }
      return null;
   }
   
   private String getFile(String java)
   {
      if (java != null)
      {
         File file = new File(java);
         
         if (file.exists() && !file.isDirectory())
         {
            System.out.println("Using Java executable: " + java);
            return file.getAbsolutePath();
         }
      }
      return null;
   }
}
