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
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 57778 $
 */
public class DomainDefinition
{

   private String name;
   private AspectManager manager;

   public DomainDefinition(String name, AspectManager parent, boolean parentFirst, boolean inheritDefs, boolean inheritBindings)
   {
      this.name = name;
      Domain domain = new Domain(parent, name, parentFirst);
      domain.setInheritsBindings(inheritBindings);
      domain.setInheritsDeclarations(inheritDefs);
      manager = domain;
   }

   public String getName()
   {
      return name;
   }

   public AspectManager getManager()
   {
      return manager;
   }


}
