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
package org.jboss.aophelper.ui.compile.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jboss.aophelper.annotation.AopHelperAction;
import org.jboss.aophelper.core.Action;
import org.jboss.aophelper.core.AopHandler;
import org.jboss.aophelper.core.AopOption;
import org.jboss.aophelper.core.AopState;
import org.jboss.aophelper.ui.AopHelperUiMediator;

/**
 * A CompileOptionsPane.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class CompileOptionsPane extends JPanel
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private JCheckBox verbose, suppress, noopt;
   private JTextField workingdir;
   public CompileOptionsPane()
   {
      init();
   }
   
   private void init()
   {
      AopHelperUiMediator.instance().setCompileOptionsPane(this);
      setLayout(new BorderLayout());
      
//      JLabel heading = new JLabel("Settings");
//      heading.setFont(new Font("SansSerif", 1, 24));
//      add(heading, BorderLayout.NORTH);
      
      verbose = new JCheckBox("Verbose");
      verbose.setSelected(true);
      suppress = new JCheckBox("Suppress");
      noopt = new JCheckBox("Not optimize");
      verbose.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent ie)
         {
            if(ie.getStateChange() == ItemEvent.SELECTED)
               addVerbose();
            else
               removeVerbose();
         }
      });
      
      suppress.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent ie)
         {
            if(ie.getStateChange() == ItemEvent.SELECTED)
               addSuppress();
            else
               removeSuppress();
         }
      });
      
      noopt.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent ie)
         {
            if(ie.getStateChange() == ItemEvent.SELECTED)
               addNoopt();
            else
               removeNoopt();
         }
      });
      
     JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
     JLabel checkHeading = new JLabel("Optional Settings:");
     checkBoxPanel.setBorder(BorderFactory.createLineBorder(Color.black));
     checkBoxPanel.add(checkHeading);
     checkBoxPanel.add(verbose);
     checkBoxPanel.add(suppress);
     checkBoxPanel.add(noopt);
      
     add(checkBoxPanel, BorderLayout.NORTH);
//     Dimension minSize = new Dimension(300, 300);
//     setMinimumSize(minSize);
     
     JButton compile = new JButton("Compile!");
     compile.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) 
        {
           System.out.println("COMPILING!");
           compile();
        }
     });
     
     JPanel workingdirPanel = new JPanel(new FlowLayout());
     workingdir = new JTextField("must select the working director", 20);
     workingdir.setEditable(false);
     JLabel workingLabel = new JLabel("Set the working directory");
     workingLabel.setLabelFor(workingdir);
     workingdirPanel.add(workingLabel);
     workingdirPanel.add(workingdir);
     JButton workingdirButton = new JButton("Select working directory");
     workingdirButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event)
        {
           setWorkingDir();
        }
     });
     workingdirPanel.add(workingdirButton);
     
     add(workingdirPanel, BorderLayout.CENTER);
     
     
     add(compile, BorderLayout.SOUTH);
   }
   
   @AopHelperAction(
         action=Action.ADD, 
         state=AopState.COMPILE, 
         option=AopOption.VERBOSE)
   private void addVerbose()
   {
   }
   
   @AopHelperAction(
         action=Action.REMOVE, 
         state=AopState.COMPILE, 
         option=AopOption.VERBOSE)
   private void removeVerbose()
   {
   }
   
   @AopHelperAction(
         action=Action.ADD, 
         state=AopState.COMPILE, 
         option=AopOption.SUPPRESS)
   private void addSuppress()
   {
   }
   
   @AopHelperAction(
         action=Action.REMOVE, 
         state=AopState.COMPILE, 
         option=AopOption.SUPPRESS)
   private void removeSuppress()
   {
   }
   
   @AopHelperAction(
         action=Action.ADD, 
         state=AopState.COMPILE, 
         option=AopOption.NOOPT)
   private void addNoopt()
   {
   }
   
   @AopHelperAction(
         action=Action.REMOVE, 
         state=AopState.COMPILE, 
         option=AopOption.NOOPT)
   private void removeNoopt()
   {
   }
   
   @AopHelperAction(
         action=Action.SET, 
         state=AopState.COMPILE, 
         option=AopOption.WORKINGDIR)
   private void setWorkingDir()
   {
   }
   
   @AopHelperAction(
         action=Action.RUN, 
         state=AopState.COMPILE,
         option=AopOption.UNSPECIFIED)
   private void compile()
   {
   }
   
   public void setWorkingDir(String dir)
   {
      workingdir.setText(dir);
   }
   
   
   public void refresh()
   {
      setWorkingDir(AopHandler.instance().getRun().getWorkingdir());
      verbose.setSelected(AopHandler.instance().getRun().isVerbose());
      noopt.setSelected(AopHandler.instance().getRun().isNoopt());
      suppress.setSelected(AopHandler.instance().getRun().isSuppress());
   }


}
