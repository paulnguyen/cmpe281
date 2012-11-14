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

import java.io.File;
import java.io.IOException;

import org.jboss.aophelper.core.AopHandler;
import org.jboss.aophelper.core.AopRun;

/**
 * A AopCompileCommand.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopCompileCommand
{
   
   public String[] execute()
   {
      String execute = setup();
      try
      {

         String[] emptyArray = new String[0];
         Process process = Runtime.getRuntime().exec( execute, emptyArray,  new File(AopHandler.instance().getRun().getWorkingdir()));

         return CommandUtil.analyzeProcess(process);
      }
      catch ( IOException ie )
      {
         System.out.println("Error while aopcompiling: "+ie.getMessage());
         ie.printStackTrace();
       
         return null;
      }
   }
   
   
   private String setup()
   {
      AopRun options = AopHandler.instance().getRun();
      
      StringBuilder execute = new StringBuilder();
      execute.append("java -cp ").append(getClasspath());
      execute.append(" org.jboss.aop.standalone.Compiler ");
      if(options.isVerbose())
         execute.append("-verbose ");
      if(options.isNoopt())
         execute.append("-noopt ");
      if(options.isSuppress())
         execute.append("-suppress ");
      
      if(options.getAopXml().size() > 0)
      {
         execute.append("-aoppath ");
         String pathSep = System.getProperty("path.separator");
         StringBuffer xmlPaths = new StringBuffer();
         for(String xml : options.getAopXml())
         {
            if(xmlPaths.length() > 0)
               xmlPaths.append(pathSep);
            System.out.println("appending xml: "+xml);
            xmlPaths.append(xml);
         }
         execute.append(xmlPaths.toString()).append(" ");
      }
      
      execute.append(options.getWorkingdir());
      
//      execute.append("\"");
      System.out.println("EXECUTING: "+execute.toString());
      
      
      return execute.toString();
      
   }
   
   private String getClasspath()
   {
      StringBuilder sb = new StringBuilder();
      String pathSeparator = System.getProperty("path.separator");
      for(String path : AopHandler.instance().getRun().getClasspath())
      {
         if(sb.length() > 0)
            sb.append(pathSeparator);
         sb.append(path);
      }
      if(sb.length() > 0)
         sb.append(pathSeparator);
      sb.append(AopHandler.instance().getRun().getWorkingdir());
      
      return sb.toString();
   }

}
