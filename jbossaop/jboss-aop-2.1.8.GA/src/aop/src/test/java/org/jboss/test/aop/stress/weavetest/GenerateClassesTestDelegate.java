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
package org.jboss.test.aop.stress.weavetest;

import java.util.List;

import org.jboss.test.aop.stress.ScenarioTestDelegate;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class GenerateClassesTestDelegate extends ScenarioTestDelegate
{
   private GenerateClassesScenario setupScenario;
   private Factory[] wovenFactories;
   private Factory[] vanillaFactories;
   
   public GenerateClassesTestDelegate(Class<?> clazz)
   {
      super(clazz);
   }

   @Override
   public void setUp() throws Exception
   {
      super.setUp();
      System.out.println("Seeing up delegate");
      setupScenario = new GenerateClassesScenario();
      super.getRunner().executeScenario(setupScenario, null);
   }
   
   public Factory[] getWovenFactories()
   {
      if (wovenFactories == null)
      {
         List<Factory> facs = setupScenario.getWovenFactories();
         //loops are one-based while arrays are zero-based so add a first null element to avoid having to do arithmetic in the tests
         facs.add(0, null);
         wovenFactories = facs.toArray(new Factory[0]);
      }
      return wovenFactories;
   }
   
   public Factory[] getVanillaFactories()
   {
      if (vanillaFactories == null)
      {
         List<Factory> facs = setupScenario.getVanillaFactories();
         //loops are one-based while arrays are zero-based so add a first null element to avoid having to do arithmetic in the tests
         facs.add(0, null);
         vanillaFactories = facs.toArray(new Factory[0]);
      }
      return vanillaFactories;
   }
}
