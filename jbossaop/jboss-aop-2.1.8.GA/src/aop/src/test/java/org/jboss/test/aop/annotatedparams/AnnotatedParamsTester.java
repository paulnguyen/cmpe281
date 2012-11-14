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
package org.jboss.test.aop.annotatedparams;


import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests an annotated introduction
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 45977 $
 */
public class AnnotatedParamsTester extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotatedParamsTester");
      suite.addTestSuite(AnnotatedParamsTester.class);
      return suite;
   }

   public AnnotatedParamsTester(String name)
   {
      super(name);
   }

   public void testCreateAnnotation() throws Exception
   {
      ParamType param = new ParamType();
      
      SimpleInterceptor.intercepted = false;
      POJO pojo = new POJO(param);
      if (!SimpleInterceptor.intercepted)throw new RuntimeException("Did not intercept constructor with annotated parameters");
      
      SimpleInterceptor.intercepted = false;
      pojo.method(param);
      if (!SimpleInterceptor.intercepted)throw new RuntimeException("Did not intercept method with annotated parameters and return type");
   }
}

