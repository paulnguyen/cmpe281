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
package org.jboss.test.aop.packagedotdot;

import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.packagedotdot.all.All;
import org.jboss.test.aop.packagedotdot.callee.Callee;
import org.jboss.test.aop.packagedotdot.caller.Caller;
import org.jboss.test.aop.packagedotdot.sub.POJO;
import org.jboss.test.aop.packagedotdot.sub.nested.Nested;
import org.jboss.test.aop.packagedotdot.type.Type;
import org.jboss.test.aop.packagedotdot.type.WrongType;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Here to test that a package expression works. So far this has only been implemented for the class part 
 * of field, constructor and method expressions.
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
@SuppressWarnings({"unused"})
public class PackageTestCase extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("PackageTestCase");
      suite.addTestSuite(PackageTestCase.class);
      return suite;
   }

   public PackageTestCase(String name)
   {
      super(name);
   }

   public void testConstructors() throws Exception
   {
      //Matched by: org.jboss.test.aop.packagedotdot..
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      POJOA pojoA = new POJOA(1);
      assertTrue(ConstructionInterceptor.intercepted);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      POJOB pojoB = new POJOB(1);
      assertTrue(ConstructionInterceptor.intercepted);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      POJO pojo = new POJO(1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      Nested nested = new Nested(1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      //Matched by: *.packagedotdot..
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojoA = new POJOA(1, 1);
      assertTrue(ConstructionInterceptor.intercepted);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojoB = new POJOB(1, 1);
      assertTrue(ConstructionInterceptor.intercepted);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojo = new POJO(1, 1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      nested = new Nested(1, 1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      //Matched by: org.jboss.test.aop.packagedotdot.sub.*..
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojoA = new POJOA(1, 1, 1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojoB = new POJOB(1, 1, 1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      pojo = new POJO(1, 1, 1);
      assertFalse(ConstructionInterceptor.intercepted);
      assertFalse(NotConstructionInterceptor.intercepted);

      ConstructionInterceptor.intercepted = false;
      NotConstructionInterceptor.intercepted = false;
      nested = new Nested(1, 1, 1);
      assertTrue(ConstructionInterceptor.intercepted);
      assertTrue(NotConstructionInterceptor.intercepted);
   }
   
   public void testMethods() throws Exception
   {
      POJOA pojoA = new POJOA(1);
      POJOB pojoB = new POJOB(1);
      POJO pojo = new POJO(1);
      Nested nested = new Nested(1);

      //Matched by: org.jboss.test.aop.packagedotdot..
      NotConstructionInterceptor.intercepted = false;
      pojoA.method(1);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.method(1);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.method(1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.method(1);
      assertFalse(NotConstructionInterceptor.intercepted);

      //Matched by: *.packagedotdot..
      NotConstructionInterceptor.intercepted = false;
      pojoA.method(1, 1);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.method(1, 1);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.method(1, 1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.method(1, 1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      //Matched by: org.jboss.test.aop.packagedotdot.sub.*..
      NotConstructionInterceptor.intercepted = false;
      pojoA.method(1, 1, 1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.method(1, 1, 1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.method(1, 1, 1);
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.method(1, 1, 1);
      assertTrue(NotConstructionInterceptor.intercepted);
   }
   
   public void testFields()
   {
      POJOA pojoA = new POJOA(1);
      POJOB pojoB = new POJOB(1);
      POJO pojo = new POJO(1);
      Nested nested = new Nested(1);

      //Matched by: org.jboss.test.aop.packagedotdot..
      NotConstructionInterceptor.intercepted = false;
      pojoA.ifield = 1;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.ifield = 1;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.ifield = 1;
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.ifield = 1;
      assertFalse(NotConstructionInterceptor.intercepted);
      
      //Matched by: *.packagedotdot..
      NotConstructionInterceptor.intercepted = false;
      pojoA.lfield = 1;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.lfield = 1;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.lfield = 1;
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.lfield = 1;
      assertFalse(NotConstructionInterceptor.intercepted);
      
      //Matched by: org.jboss.test.aop.packagedotdot.sub.*..
      NotConstructionInterceptor.intercepted = false;
      pojoA.sfield = "z";
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojoB.sfield = "z";
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      pojo.sfield = "z";
      assertFalse(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      nested.sfield = "z";
      assertTrue(NotConstructionInterceptor.intercepted);
   }
   
   public void testAll()
   {
      NotConstructionInterceptor.intercepted = false;
      All all = new All();
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      all.field = 10;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      int z = all.field;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      all.method();
      assertTrue(NotConstructionInterceptor.intercepted);
   }
   
   public void testCallAndWithin()
   {
      NotConstructionInterceptor.intercepted = false;
      Callee callee = new Callee();
      assertFalse(NotConstructionInterceptor.intercepted);

      NotConstructionInterceptor.intercepted = false;
      callee.method();
      assertFalse(NotConstructionInterceptor.intercepted);

      NotConstructionInterceptor.intercepted = false;
      Caller caller = new Caller();
      assertTrue(NotConstructionInterceptor.intercepted);

      NotConstructionInterceptor.intercepted = false;
      caller.method();
      assertTrue(NotConstructionInterceptor.intercepted);
   }
   
   public void testTypeExpressions()
   {
      NotConstructionInterceptor.intercepted = false;
      Type type = new Type(1);
      assertTrue(NotConstructionInterceptor.intercepted);

      NotConstructionInterceptor.intercepted = false;
      int i = type.field;
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      type.method(5);
      assertTrue(NotConstructionInterceptor.intercepted);
      
      NotConstructionInterceptor.intercepted = false;
      WrongType wt = new WrongType();
      assertFalse(NotConstructionInterceptor.intercepted);
   }
}

