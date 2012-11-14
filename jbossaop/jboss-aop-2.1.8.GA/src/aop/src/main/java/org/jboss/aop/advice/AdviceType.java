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
package org.jboss.aop.advice;

import org.jboss.aop.advice.annotation.AdviceMethodFactory;

/**
 * Indicates whether the type of an advice is <i>before</i>, <i>around</i>,
 * <i>after</i>, <i>throwing</i> or <i>finally</i>.
 * <br>
 * Notice that interceptors are a special type of <i>around</i> advices.
 * <p><i>For internal use only.</i>
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public enum AdviceType
{
   /**
    * Advice will be invoked before the joinpoint execution.
    */
   BEFORE("before", AdviceMethodFactory.BEFORE, true, "before"),
   
   /**
    * Advice will be invoked around the joinpoint execution, and as such is
    * responsible for invoking the next advice in the chain and returning the
    * joinpoint result value.
    */
   AROUND("around", AdviceMethodFactory.AROUND, false, "around"),
   
   /**
    * Advice will be invoked after the joinpoint execution, only if it returns
    * normally.
    */
   AFTER("after", AdviceMethodFactory.AFTER, false, "after"),
   
   /**
    * Advice will be invoked after the joinpoint execution, only if it throws an
    * exception.
    */
   THROWING("throwing", AdviceMethodFactory.THROWING, false, "after-throwing"),
   
   /**
    * Advice will be invoked after the joinpoint execution, regardless of how it
    * returns.
    */
   FINALLY("finally", AdviceMethodFactory.FINALLY, false, "finally");
   
   private String name;
   private String description;
   private String accessor;
   private AdviceMethodFactory factory;
   private boolean generatedOnly;
   
   AdviceType(String name, AdviceMethodFactory factory, boolean generatedOnly,
         String description)
   {
      this.name = name;
      this.accessor = "get" + Character.toUpperCase(name.charAt(0))
         + name.substring(1);
      this.factory = factory;
      this.factory.setAdviceType(this);
      this.generatedOnly = generatedOnly;
      this.description = description;
   }
   
   /**
    * Returns a lower case name of this type.
    */
   public final String getName()
   {
      return this.name;
   }
   
   /**
    * Returns an accessor string for this type.
    * <br>
    * This accessor is built by concatenating <code>"get"</code> with the
    * the {@link #getName() description} starting with an upper case.
    * 
    * @return an accessor string for this type.
    */
   public final String getAccessor()
   {
      return this.accessor;
   }
   
   /**
    * An advice method factory for this type. Notice this factory is for use on
    * generated mode only.
    * 
    * @return an advice method factory
    */
   public final AdviceMethodFactory getFactory()
   {
      return this.factory;
   }
   
   /**
    * Indicates if the use of this advice type is restrictive to the generated
    * advisor mode, or if it can be used on all instrumentation modes.
    * 
    * @return <code>true</code> only if this advice type must be used on generated
    *         advisor mode.
    */
   public final boolean isGeneratedOnly()
   {
      return this.generatedOnly;
   }
   
   /**
    * Returns the verbose description of this type (for use on output).
    * 
    * @return the lower-case verbose description of this type
    */
   public String toString()
   {
      return this.description;
   }
}