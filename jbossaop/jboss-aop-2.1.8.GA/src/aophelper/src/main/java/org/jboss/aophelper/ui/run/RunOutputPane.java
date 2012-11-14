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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.jboss.aophelper.ui.AopHelperUiMediator;

/**
 * A RunOutputPane.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RunOutputPane extends JPanel
{
   
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private JTextArea outputArea;
   private JTextArea errorArea;
   
   public RunOutputPane()
   {
      init();
   }
   
   private void init()
   {
      AopHelperUiMediator.instance().setRunOutputPane(this);
      
      setLayout(new FlowLayout());
      
      outputArea = new JTextArea();
      outputArea.setText("logs from the execution will be seen here");
      outputArea.setLineWrap(true);
      outputArea.setWrapStyleWord(true);
      outputArea.setEditable(false);
      JScrollPane outputScroll = new JScrollPane(outputArea);
      outputScroll.setVisible(true);
      
      errorArea = new JTextArea();
      errorArea.setText("errors from the execution will be seen here");
      errorArea.setLineWrap(true);
      errorArea.setWrapStyleWord(true);
      errorArea.setEditable(false);
      JScrollPane errorScroll = new JScrollPane(errorArea);
      errorScroll.setVisible(true);
      
      JTabbedPane tPane = new JTabbedPane();
      tPane.addTab("Output", outputScroll);
      tPane.addTab("Error", errorScroll);
      tPane.setPreferredSize(new Dimension(1020, 270));
      
      add(tPane, BorderLayout.CENTER);

   }
   
   public void setText(String text)
   {
      outputArea.setText(text);
   }

   public void setError(String error)
   {
      errorArea.setText(error);
   }

}
