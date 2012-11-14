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
package org.jboss.aophelper.ui;

import org.jboss.aophelper.ui.compile.OutputPane;
import org.jboss.aophelper.ui.compile.classpath.ClasspathTableModel;
import org.jboss.aophelper.ui.compile.classpath.ClasspathTablePane;
import org.jboss.aophelper.ui.compile.options.CompileOptionsPane;
import org.jboss.aophelper.ui.compile.xml.XmlTableModel;
import org.jboss.aophelper.ui.compile.xml.XmlTablePane;
import org.jboss.aophelper.ui.run.RunOutputPane;
import org.jboss.aophelper.ui.run.classpath.RunClasspathTableModel;
import org.jboss.aophelper.ui.run.classpath.RunClasspathTablePane;
import org.jboss.aophelper.ui.run.options.RunOptionsPane;
import org.jboss.aophelper.ui.run.xml.RunXmlTableModel;
import org.jboss.aophelper.ui.run.xml.RunXmlTablePane;

/**
 * A AopHelperUiMediator.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopHelperUiMediator
{
   private static final AopHelperUiMediator mediator = new AopHelperUiMediator();

   private RunClasspathTablePane runClasspathTable;
   private RunClasspathTableModel runClasspathModel;
   
   private RunXmlTablePane runXmlTable;
   private RunXmlTableModel runXmlModel;
   
   private RunOptionsPane runOptionsPane;
   private RunOutputPane runOutputPane;
   
   private ClasspathTablePane classpathTable;
   private ClasspathTableModel classpathModel;
  
   private XmlTablePane xmlTable;
   private XmlTableModel xmlModel;
   
   private CompileOptionsPane compileOptionsPane;
   private OutputPane outputPane;
   
   
   private AopHelperUiMediator()
   {
   }
   
   public static AopHelperUiMediator instance()
   {
      return mediator;
   }

   public void setClasspathTable(ClasspathTablePane table)
   {
      this.classpathTable = table;
   }
   
   public ClasspathTablePane getClasspathTable()
   {
      return classpathTable;
   }

   /**
    * Get the classpathModel.
    * 
    * @return the classpathModel.
    */
   public ClasspathTableModel getClasspathModel()
   {
      return classpathModel;
   }

   /**
    * Set the classpathModel.
    * 
    * @param classpathModel The classpathModel to set.
    */
   public void setClasspathModel(ClasspathTableModel classpathModel)
   {
      this.classpathModel = classpathModel;
   }

   /**
    * 
    * @param tableModel
    */
   public void setXmlModel(XmlTableModel tableModel)
   {
      xmlModel = tableModel;
   }
   
   public XmlTableModel getXmlModel()
   {
      return xmlModel;
   }
   
   public void setXmlTable(XmlTablePane tableP)
   {
      xmlTable = tableP;
   }
   
   public XmlTablePane getXmlTable()
   {
      return xmlTable;
   }

   /**
    * Get the compileOptionsPane.
    * 
    * @return the compileOptionsPane.
    */
   public CompileOptionsPane getCompileOptionsPane()
   {
      return compileOptionsPane;
   }

   /**
    * Set the compileOptionsPane.
    * 
    * @param compileOptionsPane The compileOptionsPane to set.
    */
   public void setCompileOptionsPane(CompileOptionsPane compileOptionsPane)
   {
      this.compileOptionsPane = compileOptionsPane;
   }

   /**
    * Get the outputPane.
    * 
    * @return the outputPane.
    */
   public OutputPane getOutputPane()
   {
      return outputPane;
   }

   /**
    * Set the outputPane.
    * 
    * @param outputPane The outputPane to set.
    */
   public void setOutputPane(OutputPane outputPane)
   {
      this.outputPane = outputPane;
   }
   

   /**
    * FIXME Comment this
    * 
    * @param tableModel
    */
   public void setRunClasspathModel(RunClasspathTableModel tableModel)
   {
      runClasspathModel = tableModel;
   }
   
   public RunClasspathTableModel getRunClasspathModel()
   {
      return runClasspathModel;
   }

   /**
    * FIXME Comment this
    * 
    * @param runClasspathTablePane
    */
   public void setRunClasspathTable(RunClasspathTablePane classpathTablePane)
   {
      runClasspathTable = classpathTablePane;
   }
   
   public RunClasspathTablePane getRunClasspathTable()
   {
      return runClasspathTable;
   }

   /**
    * Get the runXmlTable.
    * 
    * @return the runXmlTable.
    */
   public RunXmlTablePane getRunXmlTable()
   {
      return runXmlTable;
   }

   /**
    * Set the runXmlTable.
    * 
    * @param runXmlTable The runXmlTable to set.
    */
   public void setRunXmlTable(RunXmlTablePane runXmlTable)
   {
      this.runXmlTable = runXmlTable;
   }

   /**
    * Get the runXmlModel.
    * 
    * @return the runXmlModel.
    */
   public RunXmlTableModel getRunXmlModel()
   {
      return runXmlModel;
   }

   /**
    * Set the runXmlModel.
    * 
    * @param runXmlModel The runXmlModel to set.
    */
   public void setRunXmlModel(RunXmlTableModel runXmlModel)
   {
      this.runXmlModel = runXmlModel;
   }

   /**
    * Get the runOptionsPane.
    * 
    * @return the runOptionsPane.
    */
   public final RunOptionsPane getRunOptionsPane()
   {
      return runOptionsPane;
   }

   /**
    * Set the runOptionsPane.
    * 
    * @param runOptionsPane The runOptionsPane to set.
    */
   public final void setRunOptionsPane(RunOptionsPane runOptionsPane)
   {
      this.runOptionsPane = runOptionsPane;
   }

   /**
    * Get the runOutputPane.
    * 
    * @return the runOutputPane.
    */
   public final RunOutputPane getRunOutputPane()
   {
      return runOutputPane;
   }

   /**
    * Set the runOutputPane.
    * 
    * @param runOutputPane The runOutputPane to set.
    */
   public final void setRunOutputPane(RunOutputPane runOutputPane)
   {
      this.runOutputPane = runOutputPane;
   }
   
   public void refresh()
   {
      classpathTable.refresh();
      xmlTable.refresh();
      compileOptionsPane.refresh();
      runClasspathTable.refresh();
      runXmlTable.refresh();
      runOptionsPane.refresh();
   }
   
   public void clearSelected()
   {
      xmlTable.clearSelectedItem();
      classpathTable.clearSelectedItem();
      runXmlTable.clearSelectedItem();
      runClasspathTable.clearSelectedItem();
   }

   


}
