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


/**
 * This class represents the classification of a joinpoint.
 * <code>JoinpointClassification</code> is an implementation of the
 * Enum Type Pattern, described by Joshua Block on his
 * book entitled "Effective Java Programming Language Guide".
 * @author Flavia Rainone
 */
public final class JoinpointClassification
{
   /**
    * Indicates that the joinpoint mustn't be instrumented.
    */
   public static JoinpointClassification NOT_INSTRUMENTED = new JoinpointClassification("not instrumented");
   
   /**
    * Indicates that the joinpoint must be prepared for runtime wrapping.
    */
   public static JoinpointClassification PREPARED = new JoinpointClassification("prepared");
   
   /**
    * Indicates that the joinpoint must be wrapped inside a code block for interception.
    */
   public static JoinpointClassification WRAPPED = new JoinpointClassification("wrapped");
   
   /**
    * Indicates that the joinpoint must be wrapped inside a code block for interception due 
    * only to dynamic bindings.
    * This status is necessary to diferentatiate from WRAPPED.
    * When a joinpoint will be wrapped due to a dynamic aop operation, considering
    * the class containing the joinpoint is being loaded after the dynamic operation was
    * performed, and the joinpoint is, for example, a field read, we know we will have to
    * replace fiel reads in classes already loaded by the field read wrapper. So, this
    * status is a flag telling: "you may have to replace the joinpoint executions by the
    * joinpoint wrappers in already loaded classes (only in hot swap enabled case). 
    */
   public static JoinpointClassification DYNAMICALY_WRAPPED = new JoinpointClassification(WRAPPED, "dynamicaly wrapped");


   /** The classification description */
   private String description;
   //private boolean dynamicAop;
   private JoinpointClassification counterPart;
   
   /**
    * Private constructor (singleton class).
    * @param description classification description.
    */
   private JoinpointClassification(String description) {
      this.description = description;
      //this.dynamicAop = false;
   }

   /**
    * Private constructor (singleton class).
    * @param description classification description.
    */
   private JoinpointClassification(JoinpointClassification counterpart, String description) {
      this(description);
      this.counterPart = counterpart;
      //this.dynamicAop = true;
   }
   
   /**
    *  Returns a description of the joinpoint classification. 
    */
   public String toString() {
      return this.description;
   }
   
   public boolean equals(Object other)
   {
      if (other == counterPart)
      {
         return true;
      }
      return super.equals(other);
   }
}