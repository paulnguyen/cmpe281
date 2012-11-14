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
package org.jboss.test.aop.inforesolve;

import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.FieldAccess;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodExecution;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class InfoResolveAnnotationTestCase extends AOPTestWithSetup
{
   public InfoResolveAnnotationTestCase(String arg0)
   {
      super(arg0);
   }

   public void testResolveAnnotationsFromInfo() throws Exception
   {
      ResolveAnnotationAspect.clear();
      POJO pojo = new POJO();
      assertNotNull(pojo);
      check(ConstructorExecution.class, "class", "ctor");
      
      ResolveAnnotationAspect.clear();
      pojo.field = 100;
      check(FieldAccess.class, "class", "field");
      
      ResolveAnnotationAspect.clear();
      assertEquals(100, pojo.field);
      check(FieldAccess.class, "class", "field");
      
      ResolveAnnotationAspect.clear();
      POJO.staticField = 101;
      check(FieldAccess.class, "class", "staticField");
      
      ResolveAnnotationAspect.clear();
      assertEquals(101, POJO.staticField);
      check(FieldAccess.class, "class", "staticField");
      
      ResolveAnnotationAspect.clear();
      assertEquals(201, pojo.method(200));
      check(MethodExecution.class, "class", "method");
      
      ResolveAnnotationAspect.clear();
      assertEquals(302, pojo.staticMethod(300));
      check(MethodExecution.class, "class", "staticMethod");
   }
   
   private void check(Class<? extends JoinPointBean> expectedInfoClazz, String expectedClassString, String expectedJoinPointString)
   {
      assertNotNull(ResolveAnnotationAspect.joinPoint);
      assertTrue(expectedInfoClazz.isAssignableFrom(ResolveAnnotationAspect.joinPoint.getClass()));
      assertNotNull(ResolveAnnotationAspect.classAnnotation);
      assertEquals(expectedClassString, ResolveAnnotationAspect.classAnnotation.value());
      assertNotNull(ResolveAnnotationAspect.joinpointAnnotation);
      assertEquals(expectedJoinPointString, ResolveAnnotationAspect.joinpointAnnotation.value());
   }
}