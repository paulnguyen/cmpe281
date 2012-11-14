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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jboss.aophelper.util.xml.AntBuildCreator;
import org.jboss.aophelper.util.xml.BaseConfigParser;

/**
 * A AopHelperMediator.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AopHelperMediator
{

   private static final AopHelperMediator mediator = new AopHelperMediator();
   private AopHelperMenuBar menuBar;
   private AopHelperFrame mainPane;
   
   private AopHelperMediator()
   {
   }
   
   public static AopHelperMediator instance()
   {
      return mediator;
   }
   
   public void quit()
   {
      System.exit(0);
   }

   /**
    * Get the menuBar.
    * 
    * @return the menuBar.
    */
   public AopHelperMenuBar getMenuBar()
   {
      return menuBar;
   }

   /**
    * Set the menuBar.
    * 
    * @param menuBar The menuBar to set.
    */
   public void setMenuBar(AopHelperMenuBar menuBar)
   {
      this.menuBar = menuBar;
   }

   /**
    * Get the mainPane.
    * 
    * @return the mainPane.
    */
   public AopHelperFrame getMainPane()
   {
      return mainPane;
   }

   /**
    * Set the mainPane.
    * 
    * @param mainPane The mainPane to set.
    */
   public void setMainPane(AopHelperFrame mainPane)
   {
      this.mainPane = mainPane;
   }

   /**
    * 
    * 
    */
   public void loadSettings()
   {
     File[] file = mainPane.createFileCooser();
     
     if(file[0] != null)
     {
        BaseConfigParser.parse(file[0]);
        AopHelperUiMediator.instance().refresh();
     }
      
   }

   /**
    * 
    * 
    */
   public void saveSettings()
   {

      File[] file = mainPane.createFileCooser();
      if(file[0] != null && !file[0].isDirectory())
      {
         System.out.println("saving to file: "+file[0].getAbsoluteFile());
         String output = BaseConfigParser.getSettingsAsString();
         try 
         {
            BufferedWriter out = new BufferedWriter(new FileWriter(file[0].getAbsoluteFile()));
            out.write(output);
            out.close();
         } 
         catch (IOException e)
         {
         }
      }
   }
   
   public void saveAntSettings()
   {
      
      if(mainPane.createConfirmWindow("You have to set the correct sourcepath for the ant file to work"))
      {

         File[] file = mainPane.createFileCooser();
         if(file[0] != null && !file[0].isDirectory())
         {
            System.out.println("saving to file: "+file[0].getAbsoluteFile());
            String output = AntBuildCreator.getAntSettingsAsString();
            try 
            {
               BufferedWriter out = new BufferedWriter(new FileWriter(file[0].getAbsoluteFile()));
               out.write(output);
               out.close();
            } 
            catch (IOException e)
            {
            }
         }
      }
   }
   
   
}
