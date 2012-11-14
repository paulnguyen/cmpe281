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
package org.jboss.test.aop.regression.arraymethodparam;

import org.jboss.aop.expressions.ConstructorExpression;
import org.jboss.aop.expressions.MethodExpression;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class ArrayMethodParamTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      junit.textui.TestRunner.run(ArrayMethodParamTestCase.class);
   }

   public ArrayMethodParamTestCase(String arg0)
   {
      super(arg0);
   }
   
   public void testMethodExpressionArray()
   {
      new MethodExpression("byte[] *->foo(byte[])");
   }
   
   public void testConstructorExpressionArray()
   {
      new ConstructorExpression("*->new(byte[])");
   }

   public void testMetaDataArrayParameter() throws Exception
   {
      POJO pojo = new POJO();
      String[] s = new String[] {"a", "b"};
      pojo.someMethod(s);
      
      assertEquals("Hello", MyInterceptor.metadata);
   }

}
