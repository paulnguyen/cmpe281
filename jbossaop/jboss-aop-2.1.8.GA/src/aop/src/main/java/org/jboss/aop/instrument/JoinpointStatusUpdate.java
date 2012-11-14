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
package org.jboss.aop.instrument;

import java.util.ArrayList;
import java.util.Collection;

import org.jboss.aop.MethodInfo;

import javassist.CtClass;

/**
 * This class contains the status update of
 * all joinpoints associated to a class.
 * @author Flavia Rainone
 */
public class JoinpointStatusUpdate
{
   /**
    * The class whose joinpoints are related to this update.
    */
   public CtClass clazz;
   
   /**
    * Joinpoints which became advised recently.
    */
   public ClassJoinpoints newlyAdvisedJoinpoints;
   
   /**
    * Joinpoints which became unadvised recently.
    */
   public ClassJoinpoints newlyUnadvisedJoinpoints;
   
   /**
    * Returns <code>true</code> if this object contains no joinpoints.
    */
   public boolean isEmpty()
   {
      return newlyAdvisedJoinpoints.isEmpty() && newlyUnadvisedJoinpoints.isEmpty();
   }
   
   /**
    * Collection of the joinponts of a class.
    */
   public static class ClassJoinpoints {
      
      /**
       * The field read joinpoints.
       * A collection of <code>java.lang.Integer</code>.
       */
      public Collection<Integer> fieldReads;

      /**
       * The field write joinponts.
       * A collection of <code>java.lang.Integer</code>.
       */
      public Collection<Integer> fieldWrites;
      
      /**
       * The constructor execution joinpoints.
       * A collection of <code>java.lang.Integer</code>.
       */
      public Collection<Integer> constructorExecutions;
      
      /**
       * The method execution joinpoints.
       * A collection of <code>org.jboss.aop.MethodInfo</code>.
       */
      public Collection<MethodInfo> methodExecutions;
      
      /**
       * Constructor.
       * @param fields the maximum number of field reads and field writes that this
       * instance may contain.
       * @param constructors the maximum number of constructor executions that this
       * instance may contain.
       * @param methods the maximum number of method executions that this
       * instance may contain.
       */
      public ClassJoinpoints(int fields, int constructors, int methods) {
         this.fieldReads = new ArrayList<Integer>(fields);
         this.fieldWrites = new ArrayList<Integer>(fields);
         this.constructorExecutions = new ArrayList<Integer>(constructors);
         this.methodExecutions = new ArrayList<MethodInfo>(methods);
      }     
      
      /**
       * Returns <code>true</code> if this object contains no joinpoints.
       */
      public boolean isEmpty()
      {
         return fieldReads.isEmpty() && fieldWrites.isEmpty() &&
            constructorExecutions.isEmpty() && methodExecutions.isEmpty();
      }
   }
}