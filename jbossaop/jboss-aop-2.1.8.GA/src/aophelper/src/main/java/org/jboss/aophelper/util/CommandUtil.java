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
package org.jboss.aophelper.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A CommandUtil.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class CommandUtil
{
   
   public static String getAopPaths()
   {
      StringBuilder aopPaths = new StringBuilder();
      String pathSeparator = System.getProperty("path.separator");
      String[] paths = System.getProperty("java.class.path").split(pathSeparator);
      for(String p : paths)
      {
         if(p.contains("jboss-aop"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("trove"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("javassist"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-common-core"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-logging-spi"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-container"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-common-logging-spi"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-mdr"))
            aopPaths.append(p).append(pathSeparator);
         else if(p.contains("jboss-reflect"))
            aopPaths.append(p).append(pathSeparator);
         
      }
//      aopPaths.append(".");
      
      return aopPaths.toString();
   }
   

   public static String[] analyzeProcess(Process process)
   {
      BufferedReader br = new BufferedReader( new InputStreamReader( process.getInputStream() ) );

      String line; 

      StringBuffer output = new StringBuffer();
      StringBuffer error = new StringBuffer();
      

      try
      {
         while ( ( line = br.readLine() ) != null )
         {
            System.out.println("line: "+line);
            output.append("\n").append(line);
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      BufferedReader errorBr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      try
      {
         while(( line = errorBr.readLine()) != null)
         {
            System.out.println("ERROR: "+line);
            error.append("\n").append(line);
         }
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
      
      System.out.println("INPUTSTREAM: "+output.toString());

      String[] out = new String[2];
      out[0] = output.toString();
      out[1] = error.toString();
      
      return out;
      
   }
      
    

}
