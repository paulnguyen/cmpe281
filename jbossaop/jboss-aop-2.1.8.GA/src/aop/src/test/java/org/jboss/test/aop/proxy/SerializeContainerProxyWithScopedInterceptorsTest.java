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
package org.jboss.test.aop.proxy;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 64431 $
 */
public abstract class SerializeContainerProxyWithScopedInterceptorsTest extends AbstractSerializeContainerTest
{
   public SerializeContainerProxyWithScopedInterceptorsTest(String name, ProxyFileCreator creator)
   {
      super(name, creator);
   }

   public void testContainerProxy() throws Exception
   {
      try
      {
         SomeInterface si = getSerializedProxy();
         
         TestInterceptor.invoked = false;
         TestInterceptor2.invoked = false;
         TestInterceptor3.invoked = false;
         TestInterceptor4.invoked = false;
         TestInterceptor5.invoked = false;
         si.helloWorld();
         assertTrue(TestInterceptor.invoked);
         assertTrue(TestInterceptor2.invoked);
         assertTrue(TestInterceptor3.invoked);
         assertTrue(TestInterceptor4.invoked);
         assertTrue(TestInterceptor5.invoked);
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw e;
      }
   }
}
