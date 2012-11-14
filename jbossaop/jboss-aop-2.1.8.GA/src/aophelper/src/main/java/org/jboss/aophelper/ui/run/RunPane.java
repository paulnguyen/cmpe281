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
package org.jboss.aophelper.ui.run;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jboss.aophelper.ui.run.classpath.RunClasspathPane;
import org.jboss.aophelper.ui.run.options.RunOptionsPane;
import org.jboss.aophelper.ui.run.xml.RunXmlPane;

/**
 * A RunPane.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RunPane extends JPanel
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   public RunPane()
   {
//      Dimension size = new Dimension(1024, 768);
//      this.setPreferredSize(size);
//      this.setLayout(new FlowLayout());

      Dimension size = new Dimension(1024, 768);
      this.setPreferredSize(size);
      this.setLayout(new FlowLayout());
      
      JPanel setupPanel = new JPanel();
      setupPanel.setPreferredSize(new Dimension(1024,450));
      
    
      JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, setupPanel, new RunOutputPane());
      mainSplitPane.setPreferredSize(size);
      
      //setupPanel
      JPanel tablePane = new JPanel(new BorderLayout());
      JSplitPane setupSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePane, new RunOptionsPane());
      setupPanel.add(setupSplit, BorderLayout.NORTH);
      setupSplit.setPreferredSize(new Dimension(1024, 450));
      JSplitPane tableSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new RunClasspathPane(), new RunXmlPane());
      tablePane.add(tableSplit, BorderLayout.NORTH);
      tableSplit.setPreferredSize(new Dimension(500, 450));
      
      add(mainSplitPane);
   }
}
