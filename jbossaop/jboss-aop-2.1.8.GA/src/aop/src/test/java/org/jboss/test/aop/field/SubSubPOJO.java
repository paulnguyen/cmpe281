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
package org.jboss.test.aop.field;

/**
 *
 * @author <a href="mailto:stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision
 */
public class SubSubPOJO extends SubPOJO
{
   public int field;
   public int mine;
   
   public SubSubPOJO(int i)
   {
      super(i);
      field = i * 2;
   }
   
   public int getSubSubPOJOField()
   {
      return field;
   }
   
   
   public int useMine()
   {
      return mine;
   }
   
   public void updateMine(int i)
   {
      mine = i;
   }
   
   public int useSubPojoInherited()
   {
      return subpojoInherited;
   }
   
   public void updateSubPojoInherited(int i)
   {
      subpojoInherited = i;
   }
   
   public int usePojoInherited()
   {
      return pojoInherited;
   }
   
   public void updatePojoInherited(int i)
   {
      pojoInherited = i;
   }
}
