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
package org.jboss.test.aop.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.proxy.container.AOPProxyFactory;
import org.jboss.aop.proxy.container.AOPProxyFactoryMixin;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SerializeContainerProxyWithInterceptorsDelegate extends ProxyFileCreatorDelegate
{
   public static void main (String[] args)
   {
      SerializeContainerProxyWithInterceptorsDelegate serializer = new SerializeContainerProxyWithInterceptorsDelegate();
      serializer.createAndSerializeProxy(args);
   }
   
   public void createAndSerializeProxy(File file) throws Exception
   {
      AspectManager manager = AspectManager.instance();
      addInterceptorBinding(manager, 
            1, 
            Scope.PER_VM, 
            TestInterceptor.class.getName(), 
            "execution(* $instanceof{" + SomeInterface.class.getName() + "}->helloWorld(..))");

      
      addInterceptorBinding(manager, 
            2, 
            Scope.PER_VM, 
            TestInterceptor2.class.getName(),
            "execution(* $instanceof{" + SomeInterface.class.getName() + "}->otherWorld(..))");
         
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setInterfaces(new Class[] {SomeInterface.class});
      params.setMixins(new AOPProxyFactoryMixin[] {
            new AOPProxyFactoryMixin(OtherMixin.class, new Class[] {OtherMixinInterface.class, OtherMixinInterface2.class}, "20")
      });
      
      params.setTarget(new SerializablePOJO());
      AOPProxyFactory factory = new GeneratedAOPProxyFactory();
      SomeInterface si = (SomeInterface)factory.createAdvisedProxy(params);
      
      TestInterceptor.invoked = false;
      TestInterceptor2.invoked = false;
      si.helloWorld();
      assertTrue(TestInterceptor.invoked);
      assertFalse(TestInterceptor2.invoked);
      
      TestInterceptor.invoked = false;
      TestInterceptor2.invoked = false;
      si.otherWorld();
      assertFalse(TestInterceptor.invoked);
      assertTrue(TestInterceptor2.invoked);
      
      ObjectOutputStream out = null;
      try
      {
         out = new ObjectOutputStream(new FileOutputStream(file));
         out.writeObject(si);
      }
      finally
      {
         try
         {
            out.close();
         }
         catch(Exception e)
         {
         }
      }
   }
}
