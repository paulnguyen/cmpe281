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
package org.jboss.aop.advice.annotation.assignability;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class VariableHierarchy
{
   private Map<String, VariableNode> map;
   private int boundComparation;
   private int realBoundComparation;
   
   public VariableHierarchy()
   {
      this.map = new HashMap<String, VariableNode>();
   }

   public void reset()
   {
      this.map.clear();
      this.boundComparation = 0;
      this.realBoundComparation = 0;
   }
   
   VariableNode getVariableNode(TypeVariable<?> typeVariable)
   {
      String key = typeVariable.getName();
      if (map.containsKey(key))
      {
         return map.get(key);
      }
      VariableNode node = new VariableNode(typeVariable, this);
      map.put(key, node);
      return node;
   }
   
   void startBoundComparation()
   {
      this.boundComparation ++;
   }
   
   void finishBoundComparation()
   {
      this.boundComparation --;
   }
   
   boolean isBoundComparation()
   {
      return this.boundComparation > 0;
   }
   
   void startRealBoundComparation()
   {
      this.realBoundComparation ++;
   }
   
   void finishRealBoundComparation()
   {
      this.realBoundComparation --;
   }
   
   boolean isRealBoundComparation()
   {
      return this.realBoundComparation > 0;
   }
}