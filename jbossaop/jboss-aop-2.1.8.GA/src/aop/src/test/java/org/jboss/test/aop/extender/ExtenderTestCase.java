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
package org.jboss.test.aop.extender;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision: 
 */
@SuppressWarnings({"unused"})
public class ExtenderTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(ExtenderTestCase.class);
   }

   /**
    * Constructor for HierarchyTestCase.
    * @param arg0
    */
   public ExtenderTestCase(String arg0)
   {
      super(arg0);
   }
   
   public void testMethod() throws Exception
   {
      
      ChildBase childB = new ChildBase();
      childB.updateBase();
      assertTrue(ExtenderInterceptor.method);
      assertFalse(ExtenderInterceptor.field);
      
      ExtenderInterceptor.method = false;      
      Base base = new SubBase();
      base.setBase(1);
      assertTrue(ExtenderInterceptor.method);
      
      ExtenderInterceptor.method = false;
      ChildExtender ext = new ChildExtender();
      ext.updateExtender();
      assertTrue(ExtenderInterceptor.method);
      
      ExtenderInterceptor.method = false;
      InfantBase infant = new InfantBase();
      infant.infantize(3);
      assertTrue(ExtenderInterceptor.method);
      
    
   }

   public void testSuperCall() throws Exception
   {
      SuperCallAspect.interceptions = 0;
      SubBase sub = new SubBase();
      sub.superCall();
      assertEquals(3, SuperCallAspect.interceptions);
      assertEquals(3, SuperCallAspect.methodClasses.size());
      
      assertEquals(SubBase.class, SuperCallAspect.methodClasses.get(0));
      assertEquals(Base.class, SuperCallAspect.methodClasses.get(1));
      assertEquals(Base.class, SuperCallAspect.methodClasses.get(2));
   }
   
   public void testConstruction() throws Exception
   {
      ConstructionInterceptor.interceptions.clear();
      SubBase child = new SubBase();
      assertEquals(2, ConstructionInterceptor.interceptions.size());
      assertEquals(Base.class, ConstructionInterceptor.interceptions.get(0));
      assertEquals(SubBase.class, ConstructionInterceptor.interceptions.get(1));
   }
}
