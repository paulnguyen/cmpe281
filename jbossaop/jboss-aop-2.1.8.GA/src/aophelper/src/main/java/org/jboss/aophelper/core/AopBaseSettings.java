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
package org.jboss.aophelper.core;

import java.io.File;
import java.util.List;

/**
 * A AopBaseSettings.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopBaseSettings
{

   protected List<String> aopXml;
   protected List<String> classpath;
   protected List<String> transformPath;
   
   /** Set the verbose level of the compiler */
   protected boolean verbose;
   protected boolean suppress;
   protected boolean noopt;
   protected boolean report;
   protected String workingdir;

   /**
    * Create a new AopBaseSettings.
    * 
    */
   public AopBaseSettings()
   {
      super();
   }

   
   public void removeClasspath(String cp)
   {
      if(classpath.contains(cp))
         classpath.remove(cp);
   }
   
   public void addClasspath(String cp)
   {
      if(!classpath.contains(cp))
         classpath.add(cp);
   }

   
   public void removeXml(String cp)
   {
      if(aopXml.contains(cp))
         aopXml.remove(cp);
   }
   
   public void addXml(String cp)
   {
      if(!aopXml.contains(cp))
         aopXml.add(cp);
   }

   /**
    * Get the aopXml.
    * 
    * @return the aopXml.
    */
   public List<String> getAopXml()
   {
      return aopXml;
   }
   
   public String getAopXmlAsString()
   {
      StringBuffer sb = new StringBuffer();
      if(aopXml != null)
      {
         for(String aoppath : aopXml)
         {
            if(aoppath != null)
            {
               if(sb.length() > 0)
                  sb.append(File.pathSeparator);
               sb.append(aoppath);
            }
         }
         return sb.toString();
      }
      else
         return null;
   }

   /**
    * Set the aopXml.
    * 
    * @param aopXml The aopXml to set.
    */
   public void setAopXml(List<String> aopXml)
   {
      this.aopXml = aopXml;
   }

   /**
    * Get the classpath.
    * 
    * @return the classpath.
    */
   public List<String> getClasspath()
   {
      return classpath;
   }

   /**
    * Set the classpath.
    * 
    * @param classpath The classpath to set.
    */
   public void setClasspath(List<String> classpath)
   {
      this.classpath = classpath;
   }

   /**
    * Get the transformPath.
    * 
    * @return the transformPath.
    */
   public List<String> getTransformPath()
   {
      return transformPath;
   }

   /**
    * Set the transformPath.
    * 
    * @param transformPath The transformPath to set.
    */
   public void setTransformPath(List<String> transformPath)
   {
      this.transformPath = transformPath;
   }

   /**
    * Get the verbose.
    * 
    * @return the verbose.
    */
   public boolean isVerbose()
   {
      return verbose;
   }

   /**
    * Set the verbose.
    * 
    * @param verbose The verbose to set.
    */
   public void setVerbose(boolean verbose)
   {
      this.verbose = verbose;
   }

   /**
    * Get the suppress.
    * 
    * @return the suppress.
    */
   public boolean isSuppress()
   {
      return suppress;
   }

   /**
    * Set the suppress.
    * 
    * @param suppress The suppress to set.
    */
   public void setSuppress(boolean suppress)
   {
      this.suppress = suppress;
   }

   /**
    * Get the noopt.
    * 
    * @return the noopt.
    */
   public boolean isNoopt()
   {
      return noopt;
   }

   /**
    * Set the noopt.
    * 
    * @param noopt The noopt to set.
    */
   public void setNoopt(boolean noopt)
   {
      this.noopt = noopt;
   }

   /**
    * Get the report.
    * 
    * @return the report.
    */
   public boolean isReport()
   {
      return report;
   }

   /**
    * Set the report.
    * 
    * @param report The report to set.
    */
   public void setReport(boolean report)
   {
      this.report = report;
   }

   /**
    * Get the workingdir.
    * 
    * @return the workingdir.
    */
   public String getWorkingdir()
   {
      return workingdir;
   }

   /**
    * Set the workingdir.
    * 
    * @param workingdir The workingdir to set.
    */
   public void setWorkingdir(String workingdir)
   {
      this.workingdir = workingdir;
   }

}