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

import java.io.Serializable;
import java.util.ArrayList;

import org.jboss.aophelper.annotation.Undoable;

/**
 * A AopRun.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopRun extends AopBaseSettings implements Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private boolean loadtime;
   
   private String executionClass;
   private String srcPath;

   public AopRun()
   {
      aopXml = new ArrayList<String>();
      classpath =  new ArrayList<String>();
      transformPath =  new ArrayList<String>();
      setVerbose(true);
   }
 
   /**
    * Get the executionClass.
    * 
    * @return the executionClass.
    */
   public final String getExecutionClass()
   {
      return executionClass;
   }

   /**
    * Set the executionClass.
    * 
    * @param executionClass The executionClass to set.
    */
   @Undoable(callback="this_is_callback_method")
   public final void setExecutionClass(String executionClass)
   {
      this.executionClass = executionClass;
   }
   
   public final String calculateCorrectExecutionClass()
   {      
      if(getExecutionClass().startsWith(getWorkingdir()))
      {
        String rest = getExecutionClass().split(getWorkingdir())[1];
        if(rest.startsWith("/"))
          rest = rest.substring(1);
        rest = rest.replaceAll("/",".");
        int index = rest.lastIndexOf(".class");
        rest = rest.substring(0,index);
        System.out.println("rest="+rest);

        return rest;
      }
      else
         return null;
      
   }

   /**
    * Get the runtime.
    * 
    * @return the runtime.
    */
   public final boolean isLoadtime()
   {
      return loadtime;
   }

   /**
    * Set the runtime.
    * 
    * @param runtime The runtime to set.
    */
   public final void setLoadtime(boolean runtime)
   {
      this.loadtime = runtime;
   }

   /**
    * Get the srcPath.
    * 
    * @return the srcPath.
    */
   public String getSrcPath()
   {
      return srcPath;
   }

   /**
    * Set the srcPath.
    * 
    * @param srcPath The srcPath to set.
    */
   public void setSrcPath(String srcPath)
   {
      this.srcPath = srcPath;
   }
   
   
}
