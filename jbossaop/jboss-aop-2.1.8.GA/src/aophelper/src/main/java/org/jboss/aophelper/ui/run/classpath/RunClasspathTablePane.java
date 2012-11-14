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
package org.jboss.aophelper.ui.run.classpath;

import org.jboss.aophelper.annotation.AopHelperAction;
import org.jboss.aophelper.core.Action;
import org.jboss.aophelper.core.AopHandler;
import org.jboss.aophelper.core.AopOption;
import org.jboss.aophelper.core.AopState;
import org.jboss.aophelper.ui.AopHelperUiMediator;
import org.jboss.aophelper.ui.GenericEditTableModel;
import org.jboss.aophelper.ui.GenericEditTablePane;

/**
 * A RunClasspathTablePane.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RunClasspathTablePane extends GenericEditTablePane
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private RunClasspathTableModel tableModel;

   public RunClasspathTablePane(String classpaths)
   {
      init();
      insertClasspaths(classpaths);
   }

   
   @Override
   public void setMediatorTableModel()
   {
      if(tableModel == null)
         tableModel = new RunClasspathTableModel();
      System.out.println("setting tablemodel to the mediator: "+tableModel.getClass().getName());
      AopHelperUiMediator.instance().setRunClasspathModel(tableModel);
   }
   
   @Override
   public void setMediatorJPanel()
   {
      AopHelperUiMediator.instance().setRunClasspathTable(this);
   }
   
   @Override
   public GenericEditTableModel getTableModel()
   {
      return tableModel;
   }
   
   @Override
   @AopHelperAction(action=Action.SET, state=AopState.RUN, option=AopOption.CLASSPATH)
   public void notifyAction()
   {
      
   }

   private void insertClasspaths(String classpaths)
   {
      String[] cp = classpaths.split(System.getProperty("path.separator"));
      for(String path : cp)
      {
         tableModel.addRow(path);
         AopHandler.instance().getRun().addClasspath(path);
      }
      
   }
   
   public void refresh()
   {
      tableModel.clearRows();
      tableModel.addRows(AopHandler.instance().getRun().getClasspath());
   }

}
