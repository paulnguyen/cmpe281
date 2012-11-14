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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * A HelperMenuBar.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopHelperMenuBar extends JMenuBar
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;
   private JMenuItem compile, run;
   private JMenuItem about;

   public AopHelperMenuBar()
   {
      init();
   }
   
   private void init()
   {
      add(createFileMenu());
      add(createEditMenu());
      add(Box.createHorizontalGlue());
      add(createHelpMenu());
      
   }
   
   /**
    * 
    * @return
    */
   private JMenu createHelpMenu()
   {
     JMenu helpMenu = new JMenu("Help");
     helpMenu.setMnemonic('H');
     
      about = new JMenuItem("About", 'A');
     about.setAccelerator(KeyStroke.getKeyStroke('A',
           Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
     helpMenu.add(about);
     about.addActionListener(new ActionListener() {
        @SuppressWarnings("deprecation")
      public void actionPerformed(ActionEvent event) 
        {
           System.out.println("About..");
          showDialog();
        }
     });
     
     return helpMenu;
   }
   
   private void showDialog()
   {
      JOptionPane.showMessageDialog(this, "JBoss AOP Helper is intendend to \n help users easily test and run their aop apps\n  without going through ant/maven.\n\n NOTE: Aophelper is still in beta stage.");
   }

   private JMenu createFileMenu()
   {
      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic('F');

      compile = new JMenuItem("AOP Compile mode", 'C');
      compile.setAccelerator(KeyStroke.getKeyStroke('C',
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      fileMenu.add(compile);

      run = new JMenuItem( "AOP Run mode", 'R');
      run.setAccelerator(KeyStroke.getKeyStroke('R',
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      fileMenu.add(run);
//      run.setEnabled(false);

      final JMenuItem exit = new JMenuItem( "Exit", 'Q');
      exit.setAccelerator(KeyStroke.getKeyStroke('Q',
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      fileMenu.add(exit);

      fileMenu.insertSeparator(2);
      
      
      ActionListener menuListener = new ActionListener() 
      {
        public void actionPerformed(ActionEvent event) 
        {
          if(event.getSource() == exit) {
            AopHelperMediator.instance().quit();
          }
          if(event.getSource() == compile) 
          {
             AopHelperMediator.instance().getMainPane().setCompilerMode();
          }

          if(event.getSource() == run) 
          {
             AopHelperMediator.instance().getMainPane().setRunMode();
          }
        }
      };

      compile.addActionListener(menuListener);
      run.addActionListener(menuListener);
      exit.addActionListener(menuListener);
      
      return fileMenu;
   }
   
   private JMenu createEditMenu()
   {
      JMenu editMenu = new JMenu("Edit");
      editMenu.setMnemonic('E');

      JMenuItem save = new JMenuItem("Save settings", 'S');
      save.setAccelerator(KeyStroke.getKeyStroke('S',
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      save.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event)
         {
            AopHelperMediator.instance().saveSettings();
         }
      });
      editMenu.add(save);

      JMenuItem load = new JMenuItem( "Load settings", 'L');
      load.setAccelerator(KeyStroke.getKeyStroke('L',
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      load.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event)
         {
            AopHelperMediator.instance().loadSettings();
         }
      });
      editMenu.add(load);
      
      JMenuItem saveAnt = new JMenuItem("Save settings as ant build file", 'A');
//      saveAnt.setAccelerator(KeyStroke.getKeyStroke('S',
//            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
      saveAnt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event)
         {
            AopHelperMediator.instance().saveAntSettings();
         }
      });
      editMenu.add(saveAnt);
      
      return editMenu;
   }
   
   public void setCompileMode()
   {
      compile.setEnabled(false);
      run.setEnabled(true);
   }
   public void setRunMode()
   {
      run.setEnabled(false);
      compile.setEnabled(true);
   }
}
