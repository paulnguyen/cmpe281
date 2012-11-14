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
 * Interface implemented by all classes or interfaces
 * that are AOP enabled (those are also known as advised classes/interfaces).
 * <br>
 * In other words, every class that is weaved by JBoss AOP automatically implements
 * this interface, which allows the class and instance advisor retrieval.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 68585 $
 */
public interface Advised extends InstanceAdvised
{
   /**
    * Returns the manager, also known as advisor, of the weaved class. 
    * 
    * @return the advisor of the weaved class. Usually this just returns
    * a {@link ClassAdvisor}.
    */
   public Advisor _getAdvisor();
}