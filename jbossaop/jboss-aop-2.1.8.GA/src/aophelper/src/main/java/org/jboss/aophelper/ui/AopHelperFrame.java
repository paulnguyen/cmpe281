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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.jboss.aophelper.ui.compile.CompilerPane;
import org.jboss.aophelper.ui.run.RunPane;

/**
 * A AopHelperFrame.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopHelperFrame extends JFrame
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;
   private JFileChooser fc;
   private CompilerPane compilerPane;
   private RunPane runPane;
   
   
   public AopHelperFrame()
   {
      super("JBoss AOP Helper");
      AopHelperMediator.instance().setMainPane(this);
      
      setup();
   }
   
   private void setup()
   {
      // override the default, and call mediator's quit()
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            AopHelperMediator.instance().quit();
         }
      });

      compilerPane = new CompilerPane();
      runPane = new RunPane();
//      setContentPane(compilerPane);
      AopHelperMediator.instance().setMenuBar(new AopHelperMenuBar());
      setJMenuBar(AopHelperMediator.instance().getMenuBar());
      
      setSize(1024, 768);
      
      setLocation();
      setVisible(true);

      AopHelperMediator.instance().setMainPane(this);
      fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      fc.setMultiSelectionEnabled(true);
      
      setCompilerMode();
   }
   
   public void setLocation() {
      // Get the size of the screen
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      // Determine the new location of the window
      int w = this.getSize().width;
      int h = this.getSize().height;
      int x = (dim.width - w)/2;
      int y = (dim.height - h)/2;

      // Move the window
      this.setLocation(x, y);
    }

    /** 
     * Get the midpoint based on the main location
     * 
     * @param width 
     * @param height 
     * @return 
     */
    public Point getMidPoint(int width, int height ) {
      Point loc = this.getLocation();
      Dimension mainSize = this.getSize();

      return new Point( (int) (mainSize.getWidth() / 2) - (width / 2) + (int) loc.getX(), 
                        (int) (mainSize.getHeight() / 2) - (height / 2) + (int) loc.getY() );
    }

    public File[] createFileCooser()
    {
       int returnVal = fc.showOpenDialog(this);
       if (returnVal == JFileChooser.APPROVE_OPTION) 
       {
           File[] files = fc.getSelectedFiles();
          return files;
       } 
       else 
       {
          System.out.println("Save command cancelled by user.");
          return new File[0];
       }
    }
    
    public boolean createConfirmWindow(String text)
    {
//       JOptionPane optionPane = new JOptionPane(this, text, "Click Ok", JOptionPane.OK_CANCEL_OPTION);
      int retval = JOptionPane.showConfirmDialog(this, text, "Click Ok", JOptionPane.OK_CANCEL_OPTION);
       
      if(retval == JOptionPane.YES_OPTION)
      {
         System.out.println("User said yes!");
         return true;
      }
      return false;
    }

    public void setRunMode()
    {
       setContentPane(runPane);
       AopHelperMediator.instance().getMenuBar().setRunMode();
       pack();
    }
    
    public void setCompilerMode()
    {
       setContentPane(compilerPane);
       AopHelperMediator.instance().getMenuBar().setCompileMode();
       pack();
    }
    
    public void displayMessageDialog(String msg)
    {
       JOptionPane.showMessageDialog(this, msg);
    }
    
    public static void main(String[] args)
    {
       new AopHelperFrame();
    }
    
}
