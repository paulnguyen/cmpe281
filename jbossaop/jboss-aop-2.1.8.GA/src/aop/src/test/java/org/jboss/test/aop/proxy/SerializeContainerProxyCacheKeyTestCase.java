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

import java.io.Externalizable;
import java.rmi.MarshalledObject;
import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.proxy.container.AOPProxyFactoryMixin;
import org.jboss.aop.proxy.container.ContainerProxyCacheKey;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.repository.basic.BasicMetaDataRepository;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SerializeContainerProxyCacheKeyTestCase extends AOPTestWithSetup
{

   public SerializeContainerProxyCacheKeyTestCase(String arg0)
   {
      // FIXME SerialixzeContainerProxyCacheKeyTestCase constructor
      super(arg0);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("SerializeContainerProxyCacheKeyTestCase");
      suite.addTestSuite(SerializeContainerProxyCacheKeyTestCase.class);
      return suite;
   }

   
   public void testSerializeKeyNoMetaData() throws Exception
   {
      ContainerProxyCacheKey original1 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            null);

      ContainerProxyCacheKey original2 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class}, 
            getMixins(),
            null);
      
      assertFalse(original1.equals(original2));
      
      MarshalledObject mo1 = new MarshalledObject(original1);
      MarshalledObject mo2 = new MarshalledObject(original2);
      
      ContainerProxyCacheKey deserialized1 = (ContainerProxyCacheKey)mo1.get();
      ContainerProxyCacheKey deserialized2 = (ContainerProxyCacheKey)mo2.get();
      
      assertEquals(original1, deserialized1);
      assertEquals(deserialized1, original1);
      assertEquals(original2, deserialized2);
      assertEquals(deserialized2, original2);
      assertFalse(deserialized1.equals(deserialized2));
   }
   
   public void testEqualsMetaDataNotSerialized()
   {
      MetaData md = getMetaData("A", "testEqualsMetaDataNotSerialized");

      ContainerProxyCacheKey original1 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            md);

      ContainerProxyCacheKey original2 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            md);
      
      assertEquals(original1, original2);
      assertEquals(original2, original1);
      
      ContainerProxyCacheKey original3 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            null);

      assertFalse(original1.equals(original3));
      
      MetaData md2 = getMetaData("A", "testEqualsMetaDataNotSerialized2");
      ContainerProxyCacheKey original4 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            md2);
      
      assertFalse(original1.equals(original4));
   }
   
   public void testSerializeKeyWithMetaData() throws Exception
   {
      MetaData md = getMetaData("A", "testSerializeKeyWithMetaData");
      ContainerProxyCacheKey original1 = new ContainerProxyCacheKey(
            "/", 
            SerializeContainerProxyCacheKeyTestCase.class, 
            new Class[] {Externalizable.class, SomeInterface.class}, 
            getMixins(),
            md);
      
      MarshalledObject mo1 = new MarshalledObject(original1);
      
      ContainerProxyCacheKey deserialized1 = (ContainerProxyCacheKey)mo1.get();
      
      assertEquals(original1, deserialized1);
      assertEquals(deserialized1, original1);
   }
   
   private MetaData getMetaData(String app, String instance)
   {
      MutableMetaDataRepository repository = new BasicMetaDataRepository();
      ArrayList<Scope> scopes = new ArrayList<Scope>();
      scopes.add(new Scope(CommonLevels.APPLICATION, app));
      scopes.add(new Scope(CommonLevels.INSTANCE, instance));
      ScopeKey scopeKey = new ScopeKey(scopes);
      
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader(scopeKey);
      repository.addMetaDataRetrieval(loader);
      
      ((MutableMetaData)loader).addAnnotation(new AnnotationImpl());
      
      MetaData md = repository.getMetaData(scopeKey);
      
      return md;
   }

   private AOPProxyFactoryMixin[] getMixins()
   {
      AOPProxyFactoryMixin[] mixins = new AOPProxyFactoryMixin[2];
      mixins[0] = new AOPProxyFactoryMixin(Mixin.class, new Class[] {MixinInterface.class}, "abc");
      mixins[1] = new AOPProxyFactoryMixin(OtherMixin.class, new Class[] {OtherMixinInterface.class});
      return mixins;
   }
   
}
