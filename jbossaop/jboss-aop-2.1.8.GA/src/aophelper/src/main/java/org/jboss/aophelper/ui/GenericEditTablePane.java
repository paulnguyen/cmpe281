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

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * A GenericEditTablePane.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public abstract class GenericEditTablePane extends JPanel
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private JTable table;
   private JScrollPane scrollPane;
   private int selectedRow;
   private String selected;

   public GenericEditTablePane()
   {
      init();

   }
   
   protected void init()
   {

      GenericEditTableModel tableModel = getTableModel();
      table = new JTable(tableModel);

      table.setColumnSelectionAllowed(false);
      table.setCellSelectionEnabled(false);
      table.setRowSelectionAllowed(true);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.getTableHeader().setReorderingAllowed(false);

      scrollPane = new JScrollPane(table);

      //Add the scroll pane to this window.
      setLayout(new BorderLayout());
      add(scrollPane, BorderLayout.CENTER);

      table.addMouseListener( new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            //int selRow = tree.getRowForLocation(e.getX(), e.getY());
            if(e.getClickCount() == 1) {
               Point p = new Point(e.getX(), e.getY());
               System.out.println("Point: "+p.toString());

               //selectedRow = table.rowAtPoint(new Point(e.getX(), e.getY() ));
               selectedRow = table.rowAtPoint( new Point(e.getX(), e.getY()));
               table.setRowSelectionInterval(selectedRow, selectedRow);

             try { 
             String item = getTableModel().getValueAt(selectedRow, 0);
             notifyAction();
             setSelectedItem(item);
             }
             catch(Exception ex) {
             System.err.println("ERROR: when selecting an item");
             }

               if(e.getButton() == MouseEvent.BUTTON3) {
                  //System.out.println("right click");

//                createSelectMenu(e.getX(), 
//                e.getY() - scrollPane.getVerticalScrollBar().getValue());
               }

            }
            /*
       else if(e.getClickCount() == 2) {
       }
             */
         }
      });

      
//      CompileMediator.instance().setClasspathModel(tableModel);
//      CompileMediator.instance().setClasspathTable(this);
      setMediatorTableModel();
      setMediatorJPanel();

   }
   
   public abstract void setMediatorTableModel();
   
   public abstract void setMediatorJPanel();
   
   public abstract GenericEditTableModel getTableModel();
   
   public abstract void notifyAction();
   
   private  void setSelectedItem(String item)
   {
      System.out.println("selected item is: "+item);
      selected = item;
   }
   
   public String getSelectedItem()
   {
      return selected;
   }
   
   public void clearSelectedItem()
   {
      selected = null;
   }

}
