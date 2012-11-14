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

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * A GenericEditTableModel.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public abstract class GenericEditTableModel extends AbstractTableModel
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;
   private int numColumns = 1;
   private int rows = 0;
   
//   private List<String> data;
   
   public GenericEditTableModel()
   {
//      data = new ArrayList<String>();
   }
   
   public abstract List<String> getData();
   
   
   public int getColumnCount()
   {
     return numColumns;
   }

   public int getRowCount()
   {
     return rows;
   }

   public String getValueAt(int rowIndex, int columnIndex)
   {
      return getData().get(rowIndex);
   }
   
   @Override
   public String getColumnName(int column) 
   {
      return "Classpath";
   } 
   
   /**
    * JTable uses this method to determine the default renderer/
    * editor for each cell.  
    */
   @Override
   public Class<?> getColumnClass(int c) 
   {
     return getValueAt(0, c).getClass();
   }

   /**
    * Set all cells noneditable
    *
    * @return false;
    */
   @Override
   public boolean isCellEditable(int row, int col) 
   {
     return false;
   }

   public void addRow(String cp) 
   {
      System.out.println("adding row: "+cp);
      
      if(cp != null && !getData().contains(cp)) 
      {
        rows++;
        getData().add(cp);
        fireTableRowsInserted(rows, rows);
      }
    }
   
   public void removeRow(String row)
   {
      if(row != null)
      {
         System.out.println("removing row: "+row);
         getData().remove(row);
         rows--;
         fireTableDataChanged();
      }
   }
   
   public void addRows(List<String> newRows)
   {
      for(String row : newRows)
      {
         addRow(row);
      }
   }
   
   public void clearRows()
   {
      getData().clear();
      rows = 0;
      fireTableDataChanged();
   }


}
