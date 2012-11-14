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
 * HotSwapper represents a class capable of hot swapping code.
 * Every hot swap technique to be applied at JBoss AOP must implement this interface.
 * @author Flavia Rainone
 */
public interface HotSwapper
{
   /**
    * This method register class byte codes changes.
    * Every time the implementation of a class has to be hot swapped,
    * this method must called.
    * When the client is done registering all the bytecode changes,
    * it must call <code>hotSwap</code> in order to perform the hot swap
    * of the registered changes.
    * @param clazz the class whose byte codes will be hot swapped.
    * @param classCode the new byte codes of <code>clazz</code>.
    * @see hotSwap
    */
   void registerChange(Class<?> clazz, byte[] classCode);
   
   /**
    * Performs the hot swap of classes previously registered through
    * <code>registerChange</code> method.
    * @see register
    */
   void hotSwap();
}