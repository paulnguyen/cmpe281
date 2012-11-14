/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.array;

import java.util.List;

/**
 * Contains information about one reference to a particular array. 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public interface ArrayReference
{
   /**
    * @return The object containing the field with the array reference. If the root field is non static this 
    * will be the reference of the instance, if the field is static it will be the class
    */
   Object getRootObject();

   /**
    * @return The field with the array reference 
    */
   String getRootField();
   
   /**
    * If the root field is a direct reference to the array this will be null. If the field is an array and the reference is from within 
    * a nested array, this will be a list of the indexes from the top to get to the array.<BR/>
    * 
    * So if we have:
    * <pre>
    * class POJO{
    *   int[] i;
    * }
    * POJO pojo = new POJO();
    * int[] i arr = new int[]{1};
    * pojo.i = arr;
    * </pre>
    *  getNestedArrayIndices will be null for arr.If we have:
    * <pre>
    * class POJO{
    *   int[][][] i;
    * }
    * POJO pojo = new POJO();
    * int[] arr = new int[]{1};
    * pojo.i = new int[][][]{new int[][]{new int[]{1}}, new int[][]{new int[]{2}, new int[]{3}, arr}};
    * </pre>
    *  getNestedArrayIndices will {1,2} for arr.

    * @ return the nested indices of the array.  
    */
   List<Integer> getNestedArrayIndices();
}
