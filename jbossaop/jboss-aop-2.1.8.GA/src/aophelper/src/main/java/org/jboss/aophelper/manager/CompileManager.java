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
package org.jboss.aophelper.manager;

import java.io.File;

import org.jboss.aophelper.core.Action;
import org.jboss.aophelper.core.AopHandler;
import org.jboss.aophelper.core.AopOption;
import org.jboss.aophelper.ui.AopHelperMediator;
import org.jboss.aophelper.ui.AopHelperUiMediator;
import org.jboss.aophelper.util.AopCompileCommand;

/**
 * A CompileManager.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class CompileManager
{
   private AopHelperUiMediator mediator;
   
   public CompileManager()
   {
      mediator = AopHelperUiMediator.instance();
   }

   public void performAction(Action a, AopOption to)
   {
      if(a.equals(Action.ADD))
      {
         if(to.equals(AopOption.CLASSPATH))
            addClasspath();
         else if(to.equals(AopOption.AOPXML))
            addXml();
         else if(to.equals(AopOption.SUPPRESS))
            setSuppress(true);
         else if(to.equals(AopOption.VERBOSE))
            setVerbose(true);
         else if(to.equals(AopOption.NOOPT))
            setNoopt(true);
      }
      else if(a.equals(Action.REMOVE))
      {
         if(to.equals(AopOption.CLASSPATH))
            removeClasspath();
         else if(to.equals(AopOption.AOPXML))
            removeXml();
         else if(to.equals(AopOption.SUPPRESS))
            setSuppress(false);
         else if(to.equals(AopOption.VERBOSE))
            setVerbose(false);
         else if(to.equals(AopOption.NOOPT))
            setNoopt(false);
      }
      else if(a.equals(Action.SET))
      {
         if(to.equals(AopOption.WORKINGDIR))
            setWorkingdir();
      }
      else if(a.equals(Action.RUN))
      {
        compile();
      }
      
   }
   
   private void addClasspath()
   {
      File[] files = AopHelperMediator.instance().getMainPane().createFileCooser();   
      for(File f : files)
      {
         AopHandler.instance().getRun().addClasspath(f.getAbsolutePath());
      }
      mediator.refresh();
   }
   
   private void removeClasspath()
   {
      String selected = mediator.getClasspathTable().getSelectedItem();
      if(selected != null)
      {
         AopHandler.instance().getRun().removeClasspath(selected);
         mediator.clearSelected();
      }
      mediator.refresh();
   }

   private void addXml()
   {
      File[] files = AopHelperMediator.instance().getMainPane().createFileCooser();
      for(File f : files)
      {
         AopHandler.instance().getRun().addXml(f.getAbsolutePath());
      }
      mediator.refresh();
   }
   
   private void removeXml()
   {
      String selected = mediator.getXmlTable().getSelectedItem();
      if(selected != null)
      {
         System.out.println("removing Xml");
         AopHandler.instance().getRun().removeXml(selected);
         mediator.clearSelected();
      }
      mediator.refresh();
   }
   
   private void setVerbose(boolean verbose)
   {
      AopHandler.instance().getRun().setVerbose(verbose);
   }
   
   private void setSuppress(boolean suppress)
   {
      AopHandler.instance().getRun().setSuppress(suppress);
   }
   
   private void setNoopt(boolean noopt)
   {
      AopHandler.instance().getRun().setNoopt(noopt);
   }
   
   private void setWorkingdir()
   {
      File[] files = AopHelperMediator.instance().getMainPane().createFileCooser();
      for(File f : files)
      {
         AopHandler.instance().getRun().setWorkingdir(f.getAbsolutePath());
      }
      mediator.refresh();
   }

   private void compile()
   {
      if(AopHandler.instance().getRun().getWorkingdir() == null)
         AopHelperMediator.instance().getMainPane().displayMessageDialog("Must set working directory");
      else
      {
         AopCompileCommand compile = new AopCompileCommand();
         String[] out = compile.execute();
         mediator.getOutputPane().setText(out[0]);
         mediator.getOutputPane().setError(out[1]);
         System.out.println("output: "+out[0]);
      }
   }
   
   
   
   
   
}
