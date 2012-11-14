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
package org.jboss.test.aop.callerargs;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class PreparedPOJOCaller
{
   public PreparedPOJOCaller()
   {
      int ret = NotAdvisedPOJO.intStaticArgsMethod(3);
      if (ret != 6) throw new RuntimeException("Expected 6 as return value");
      
      NotAdvisedPOJO pojo2 = new NotAdvisedPOJO(10, 100);
      if(pojo2.i != 10) throw new RuntimeException("Expected pojo.i to be 10");
      if(pojo2.j != 100) throw new RuntimeException("Expected pojo.i to be 100");
      
      ret = pojo2.intArgsMethod(20, 100);
      if (ret != 2000)throw new RuntimeException("Expected 2000 as return value");
   }
   
   public void method()
   {
      int ret = NotAdvisedPOJO.intStaticArgsMethod(3);
      if (ret != 6) throw new RuntimeException("Expected 6 as return value");
      
      NotAdvisedPOJO pojo2 = new NotAdvisedPOJO(10, 100);
      if(pojo2.i != 10) throw new RuntimeException("Expected pojo.i to be 10");
      if(pojo2.j != 100) throw new RuntimeException("Expected pojo.i to be 100");
      
      ret = pojo2.intArgsMethod(20, 100);
      if (ret != 2000)throw new RuntimeException("Expected 2000 as return value");
   }
   
   public static void staticMethod()
   {
      int ret = NotAdvisedPOJO.intStaticArgsMethod(3);
      if (ret != 6) throw new RuntimeException("Expected 6 as return value");
      
      NotAdvisedPOJO pojo2 = new NotAdvisedPOJO(10, 100);
      if(pojo2.i != 10) throw new RuntimeException("Expected pojo.i to be 10");
      if(pojo2.j != 100) throw new RuntimeException("Expected pojo.i to be 100");
      
      ret = pojo2.intArgsMethod(20, 100);
      if (ret != 2000)throw new RuntimeException("Expected 2000 as return value");
   }
}
