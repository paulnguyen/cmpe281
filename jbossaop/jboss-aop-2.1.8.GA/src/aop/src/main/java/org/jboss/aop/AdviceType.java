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
package org.jboss.aop;

/**
 * Indicates whether the type of an advice is <i>before</i>, <i>around</i>,
 * <i>after</i>, <i>throwing</i> or <i>finally</i>.
 * <br>
 * Notice that interceptors are a special type of <i>around</i> advices.
 * <br>Use this class to specify the type of the advice on a
 * {@link org.jboss.aop.Bind} annotation.
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 * @see org.jboss.aop.Bind
 */
public enum AdviceType {
   /**
    * Advice will be invoked before the joinpoint execution.
    */
   AROUND,
   
   /**
    * Advice will be invoked around the joinpoint execution, and as such is
    * responsible for invoking the next advice in the chain and returning the
    * joinpoint result value.
    */
   BEFORE,
   
   /**
    * Advice will be invoked after the joinpoint execution, only if it returns
    * normally.
    */
   AFTER,
   
   /**
    * Advice will be invoked after the joinpoint execution, only if it throws an
    * exception.
    */
   THROWING,
   
   /**
    * Advice will be invoked after the joinpoint execution, regardless of how it
    * returns.
    */
   FINALLY;
}