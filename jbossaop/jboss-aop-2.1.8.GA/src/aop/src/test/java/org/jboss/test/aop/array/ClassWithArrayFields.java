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
package org.jboss.test.aop.array;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassWithArrayFields
{
   public Object[] objects = new Object[] {"1", "2", "3"};
   public String[] strings = new String[] {"1", "2", "3"};
   public byte[] bytes = new byte[] {1, 2, 3};
   public boolean[] booleans = new boolean[] {true, true, true};
   public char[] chars = new char[] {'a', 'b', 'c'};
   public double[] doubles = new double[] {1.5d, 1.9d, 6.9d};
   public float[] floats = new float[] {1.5f, 1.9f, 6.9f};
   public int[] ints = new int[] {0, 0, 0};
   public int[][][] ints3d = new int[][][] {
         new int[][] {
               new int[] {1,2,3}, 
               new int[] {4,5,6},
               new int[] {7,8,9}
         },
         new int[][] {
               new int[] {1,2,3}, 
               new int[] {4,5,6},
               new int[] {7,8,9}
         },         
   };
   public long[] longs = new long[] {1L, 2L, 3L};
   public short[] shorts = new short[] {1,2,3};
   public Object objectField = new int[] {1, 2, 3};
   
   public ClassWithArrayFields()
   {

   }
}
