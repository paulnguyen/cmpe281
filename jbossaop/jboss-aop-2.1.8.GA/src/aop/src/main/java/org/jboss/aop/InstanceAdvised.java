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
package org.jboss.aop;

/**
 * Automatically implemented by every AOP-generated proxy and weaved class.
 *<br>
 * This allows the retrieval of the instance advisor, so you can dynamically bind
 * per-instance interceptors and metadata.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 68585 $
 * @see InstanceAdvisor
 */
public interface InstanceAdvised
{
   /**
    * Returns the {@link Advisor} associated with this weaved instance.
    * 
    * @return the advisor or manager of this weaved object.
    */
   public InstanceAdvisor _getInstanceAdvisor();
   
   /**
    * Sets the instance advisor associated with this weaved instance.
    * 
    * @param newAdvisor the new advisor or manager of this weaved object.
    */
   public void _setInstanceAdvisor(InstanceAdvisor newAdvisor);
}