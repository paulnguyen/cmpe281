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
package org.jboss.test.aop.field;

import java.lang.reflect.Field;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.joinpoint.FieldJoinpoint;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 *
 * @author <a href="mailto:stalep@conduct.no">Stale W. Pedersen</a>
 * @version $Revision
 */
@SuppressWarnings({"unused"})
public class FieldTestCase extends AOPTestWithSetup
{
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("FieldTestCase");
      suite.addTestSuite(FieldTestCase.class);
      return suite;
   }

   public FieldTestCase(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void testField()
   {
      System.out.println("*** testField");
      SubPOJO spojo = new SubPOJO(4);
      assertEquals("Field is not set correctly", spojo.getPOJOField(), (spojo.getSubPOJOField()/2));  
   }

   public void testField2()
   {
      System.out.println("*** testField2");
      SubSubPOJO spojo = new SubSubPOJO(4);
      assertEquals("Field is not set correctly", spojo.getSubSubPOJOField()/2, (spojo.getSubPOJOField()));  
   }
   
   public void testFieldInheritance()
   {
      System.out.println("*** testFieldInheritance");
      SubSubPOJO pojo = new SubSubPOJO(4);

      TraceInterceptor.intercepted = false;
      pojo.mine = 5;
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(5, pojo.mine);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      pojo.pojoInherited = 6;
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(6, pojo.pojoInherited);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      pojo.subpojoInherited = 7;
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(7, pojo.subpojoInherited);
      assertTrue(TraceInterceptor.intercepted);
   }

   public void testFieldInheritanceInSubClass()
   {
      SubSubPOJO pojo = new SubSubPOJO(5);
      
      TraceInterceptor.intercepted = false;
      pojo.updateMine(5);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(5, pojo.useMine());
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      pojo.updatePojoInherited(6);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(6, pojo.usePojoInherited());
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      pojo.updateSubPojoInherited(7);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(7, pojo.useSubPojoInherited());
      assertTrue(TraceInterceptor.intercepted);
   }
   
   
    public void testSuperPrivateField()
   {
      POJO pojo = new POJO();

      TraceInterceptor.intercepted = false;
      pojo.setPrivate(5);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(5, pojo.getPrivate());
      assertTrue(TraceInterceptor.intercepted);

      SubPOJO sub = new SubPOJO();

      TraceInterceptor.intercepted = false;
      sub.setPrivate(5);
      assertTrue(TraceInterceptor.intercepted);

      TraceInterceptor.intercepted = false;
      assertEquals(5, sub.getPrivate());
      assertTrue(TraceInterceptor.intercepted);
   }
   
   public void testPerJoinpoint() throws Exception
   {
      ScopedPojo pojo1 = new ScopedPojo();
      ScopedPojo pojo2 = new ScopedPojo();

      FieldPerJoinpointInterceptor.last = null;
      pojo1.field1 = 10;
      assertNotNull(FieldPerJoinpointInterceptor.last);
      FieldPerJoinpointInterceptor fieldWrite1 = FieldPerJoinpointInterceptor.last;

      FieldPerJoinpointInterceptor.last = null;
      int x = pojo1.field1;
      assertNotNull(FieldPerJoinpointInterceptor.last);
      FieldPerJoinpointInterceptor fieldRead1 = FieldPerJoinpointInterceptor.last;
      
      assertSame(fieldRead1, fieldWrite1);
      
      FieldPerJoinpointInterceptor.last = null;
      pojo2.field1 = 10;
      assertNotNull(FieldPerJoinpointInterceptor.last);
      FieldPerJoinpointInterceptor fieldWrite2 = FieldPerJoinpointInterceptor.last;

      assertNotSame(fieldRead1, fieldWrite2);
      
      FieldPerJoinpointInterceptor.last = null;
      pojo1.field2 = 10;
      assertNotNull(FieldPerJoinpointInterceptor.last);
      FieldPerJoinpointInterceptor field2Write1 = FieldPerJoinpointInterceptor.last;
      
      assertNotSame(field2Write1, fieldRead1);
      
      FieldPerJoinpointInterceptor.last = null;
      ScopedPojo.staticField = 10;
      assertNotNull(FieldPerJoinpointInterceptor.last);
      FieldPerJoinpointInterceptor staticWrite = FieldPerJoinpointInterceptor.last;
      FieldPerJoinpointInterceptor.last = null;
      x = ScopedPojo.staticField;
      assertEquals(staticWrite, FieldPerJoinpointInterceptor.last);
      

      Field field1 = pojo1.getClass().getField("field1"); 
      Field field2 = pojo1.getClass().getField("field2");
      Field staticField = pojo1.getClass().getField("staticField");
      
      AspectDefinition def = AspectManager.instance().getAspectDefinition("field");
      assertNotNull(def);
      InstanceAdvisor ia1 = ((Advised)pojo1)._getInstanceAdvisor();
      InstanceAdvisor ia2 = ((Advised)pojo2)._getInstanceAdvisor();
      FieldPerJoinpointInterceptor ia1Field1 = (FieldPerJoinpointInterceptor)ia1.getPerInstanceJoinpointAspect(new FieldJoinpoint(field1), def);
      assertNotNull(ia1Field1);
      FieldPerJoinpointInterceptor ia2Field1 = (FieldPerJoinpointInterceptor)ia2.getPerInstanceJoinpointAspect(new FieldJoinpoint(field1), def);
      assertNotNull(ia2Field1);
      FieldPerJoinpointInterceptor ia1Field2 = (FieldPerJoinpointInterceptor)ia1.getPerInstanceJoinpointAspect(new FieldJoinpoint(field2), def); 
      assertNotNull(ia1Field2);
      
      assertSame(fieldRead1, ia1Field1);
      assertSame(fieldWrite2, ia2Field1);
      assertSame(field2Write1, ia1Field2);
      
      AspectDefinition statDef = AspectManager.instance().getAspectDefinition("staticField");
      assertNotNull(statDef);
      FieldPerJoinpointInterceptor advStatic = (FieldPerJoinpointInterceptor)((ClassAdvisor)((Advised)pojo1)._getAdvisor()).getFieldAspect(new FieldJoinpoint(staticField), statDef);
      assertSame(advStatic, staticWrite);
      
   }
   
   public void testSetOnly() throws Exception
   {
      SetOrGetOnlyPOJO pojo = new SetOrGetOnlyPOJO();
      TraceInterceptor.intercepted = false;
      pojo.setOnly = 10;
      assertTrue(TraceInterceptor.intercepted);
      
      TraceInterceptor.intercepted = false;
      assertEquals(10, pojo.setOnly);
      assertFalse(TraceInterceptor.intercepted);
   }
   
   public void testGetOnly() throws Exception
   {
      SetOrGetOnlyPOJO pojo = new SetOrGetOnlyPOJO();
      TraceInterceptor.intercepted = false;
      pojo.getOnly = 10;
      assertFalse(TraceInterceptor.intercepted);
      
      TraceInterceptor.intercepted = false;
      assertEquals(10, pojo.getOnly);
      assertTrue(TraceInterceptor.intercepted);
   }

   public void testFieldsReplacedInSubClass()
   {
      C c = new C();
      //Sanity
      assertEquals("intercepted", c.inheritedFieldInSubClassFieldA);
      //These are the real purpose of this test
      assertEquals("intercepted", c.useField());
      assertEquals("intercepted", AccessFieldViaB.accessField(c));
      assertEquals("intercepted", AccessFieldViaC.accessField(c));
   }
}
