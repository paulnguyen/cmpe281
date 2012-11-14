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
package org.jboss.aop.pointcut;

import org.jboss.aop.advice.AdviceBinding;

/**
 * Contains information about a pointcut. 
 * @author Flavia Rainone
 */
public class PointcutInfo
{
   
   private Pointcut pointcut;
   private AdviceBinding binding;
   private boolean dynamicAop;

   /**
    * This constructor must be called only when a pointcut
    * is declared inside a binding.
    * @param pointcut the pointcut.
    * @param binding the binding associated with <code>pointcut</code>. 
    * @param dynamicAop if <code>true</code>, indicates that <code>binding</code>
    * was added during a dynamic aop operation (i.e., in runtime).
    */
   public PointcutInfo(Pointcut pointcut, AdviceBinding binding, boolean dynamicAop)
   {
      this(pointcut, dynamicAop);
      this.binding = binding;
   }
   
   /**
    * Constructor that must be called only when a pointcut is declared
    * outside a binding. 
    * @param pointcut the pointcut.
    * @param dynamicAop if <code>true</code>, indicates that <code>binding</code>
    * was added during a dynamic aop operation (i.e., in runtime).
    */
   public PointcutInfo(Pointcut pointcut, boolean dynamicAop)
   {
      this.pointcut = pointcut;
      this.dynamicAop = dynamicAop;
   }
   
   /**
    * Returns the pointcut associated with this information.
    * @return the pointcut.
    */
   public Pointcut getPointcut()
   {
      return this.pointcut;
   }
   
   /**
    * Returns the <code>AdviceBinding</code> associated with the
    * pointcut.
    * @return an instance of <code>org.jboss.aop.advice.AdviceBinding</code>.
    * May be null if this pointcut isn't associated with a binding.
    */
   public AdviceBinding getBinding()
   {
      return this.binding;
   }
   
   /**
    * Indicates if this pointcut was added to the system through a dynamic aop
    * operation. (i.e., through a AspectManager.addBinding() invocation made during
    * system runtime).
    */
   public boolean isDynamicAop()
   {
      return this.dynamicAop;
   }
   
   public void setDynamicAop(boolean ok)
   {
      this.dynamicAop = ok;
   }
}