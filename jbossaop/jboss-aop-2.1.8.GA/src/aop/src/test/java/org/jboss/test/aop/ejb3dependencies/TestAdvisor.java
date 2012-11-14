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
package org.jboss.test.aop.ejb3dependencies;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassContainer;

/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class TestAdvisor<T> extends ClassContainer
{
   public TestAdvisor(String name, AspectManager manager)
   {
      super(name, manager);
      super.annotations = new TestAnnotationRepository();
   }

   org.jboss.aop.Advisor advisor = null;
   Method m;
   Constructor<?> c;
   Field f;
   
   @SuppressWarnings("unused")
   public void test()
   {
      Class<?> clazz = advisor.getClazz();
   }
   
   public org.jboss.aop.Advisor getAdvisor()
   {
      return advisor;
   }
   
   protected Class<? extends T> getBeanClass()
   {
      return getAdvisor().getClazz();
   }

   @SuppressWarnings("unused")
   public void testResolveAnnotation(Class<? extends Annotation> annotation)
   {
      Object o = advisor.resolveAnnotation(annotation);
      o = advisor.resolveAnnotation(c, annotation);
      o = advisor.resolveAnnotation(m, annotation);
      advisor.resolveAnnotation(f, annotation);
   }
}
