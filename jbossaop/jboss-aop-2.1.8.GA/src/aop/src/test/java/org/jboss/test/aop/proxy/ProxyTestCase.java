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

import java.io.Externalizable;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.rmi.MarshalledObject;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassInstanceAdvisor;
import org.jboss.aop.InstanceDomain;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.GenericAspectFactory;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.proxy.ClassProxyFactory;
import org.jboss.aop.proxy.Proxy;
import org.jboss.aop.proxy.ProxyFactory;
import org.jboss.aop.proxy.ProxyMixin;
import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.AspectManaged;
import org.jboss.aop.proxy.container.ClassProxyContainer;
import org.jboss.aop.proxy.container.ContainerCache;
import org.jboss.aop.proxy.container.ContainerProxyCacheKey;
import org.jboss.aop.proxy.container.ContainerProxyFactory;
import org.jboss.aop.proxy.container.Delegate;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.repository.basic.BasicMetaDataRepository;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 75539 $
 */
public class ProxyTestCase extends AOPTestWithSetup
{

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ProxyTester");
      suite.addTestSuite(ProxyTestCase.class);
      return suite;
   }

   public ProxyTestCase(String name)
   {
      super(name);
   }

   public void testProxy() throws Exception
   {
      Class<?>[] mixIntfs = {MixinInterface.class};
      ProxyMixin mixin = new ProxyMixin(new Mixin(), mixIntfs);
      ProxyMixin[] mixins = {mixin};
      Class<?>[] intfs = {SomeInterface.class};
      ClassInstanceAdvisor advisor = new ClassInstanceAdvisor();
      advisor.insertInterceptor(new EchoInterceptor());

      /*
      CtClass clazz = ProxyFactory.createProxyCtClass(Thread.currentThread().getContextClassLoader(), mixins, intfs);
      FileOutputStream fo = new FileOutputStream(clazz.getName() + ".class");
      DataOutputStream dos = new DataOutputStream(fo);
      clazz.toBytecode(dos);
      dos.close();

      System.out.println("**************");
      */

      Proxy proxy = ProxyFactory.createInterfaceProxy(Thread.currentThread().getContextClassLoader(), intfs, mixins, advisor);

      MixinInterface mi = (MixinInterface) proxy;
      assertEquals(mi.hello("mixin"), "mixin");
      SomeInterface si = (SomeInterface) proxy;
      assertEquals(si.helloWorld(), "echoed");

      MarshalledObject mo = new MarshalledObject(proxy);
      proxy = (Proxy) mo.get();
      mi = (MixinInterface) proxy;
      assertEquals(mi.hello("mixin"), "mixin");
      si = (SomeInterface) proxy;
      assertEquals(si.helloWorld(), "echoed");
   }

   public void testClassProxy() throws Exception
   {
      Class<?>[] mixIntfs = {MixinInterface.class};
      ProxyMixin mixin = new ProxyMixin(new Mixin(), mixIntfs);
      ProxyMixin[] mixins = {mixin};
      ClassInstanceAdvisor advisor = new ClassInstanceAdvisor();
      advisor.insertInterceptor(new EchoInterceptor());
      POJO proxy = (POJO) ClassProxyFactory.newInstance(POJO.class, mixins, advisor);

      /*
      CtClass clazz = ClassProxyFactory.createProxyCtClass(mixins, POJO.class);
      FileOutputStream fo = new FileOutputStream(clazz.getName() + ".class");
      DataOutputStream dos = new DataOutputStream(fo);
      clazz.toBytecode(dos);
      dos.close();
      */


      MixinInterface mi = (MixinInterface) proxy;
      assertEquals(mi.hello("mixin"), "mixin");

      MarshalledObject mo = new MarshalledObject(proxy);
      proxy = (POJO) mo.get();
      mi = (MixinInterface) proxy;
      assertEquals(mi.hello("mixin"), "mixin");
      assertEquals(proxy.helloWorld(), "echoed");
   }

   public void testContainerProxyCacheKey() throws Exception
   {
      ContainerProxyCacheKey key1 = new ContainerProxyCacheKey("/", this.getClass(), new Class[] {Serializable.class, InputStream.class, Externalizable.class}, null);
      ContainerProxyCacheKey key2 = new ContainerProxyCacheKey("/", this.getClass(), new Class[] {Serializable.class, Externalizable.class}, null);
      ContainerProxyCacheKey key3 = new ContainerProxyCacheKey("/", this.getClass(), new Class[] {Externalizable.class, InputStream.class, Serializable.class}, null);
      ContainerProxyCacheKey key4 = new ContainerProxyCacheKey("/some/fqn", this.getClass(), new Class[] {Serializable.class, Externalizable.class}, null);
      ContainerProxyCacheKey key5 = new ContainerProxyCacheKey("/some/fqn", this.getClass(), new Class[] {Serializable.class, Externalizable.class}, null);
      
      assertFalse(key1.equals(key2));
      assertTrue(key1.equals(key3));
      assertFalse(key2.equals(key4));
      assertTrue(key4.equals(key5));
      
      assertFalse(key1.hashCode() == key2.hashCode());
      assertTrue(key1.hashCode() == key3.hashCode());
      assertFalse(key2.hashCode() == key4.hashCode());
      assertTrue(key4.hashCode() == key5.hashCode());
   }
   
   public void testContainerCacheClassAdvisor() throws Exception
   {
      AspectManager manager = AspectManager.instance();
      ContainerCache cache1 = ContainerCache.initialise(manager, String.class, null, true);
      ContainerCache cache2 = ContainerCache.initialise(manager, String.class, null, true);
      assertSame(cache1.getClassAdvisor(), cache2.getClassAdvisor());

      ContainerCache cache3 = ContainerCache.initialise(manager, Integer.class, null, true);
      assertNotSame(cache1.getClassAdvisor(), cache3.getClassAdvisor());
   }
   
   public void testContainerProxy() throws Exception
   {
      InstanceDomain domain = new InstanceDomain(AspectManager.instance(), "blah", false);

      
      InterfaceIntroduction intro = new InterfaceIntroduction("intro", "*", null);
      String[] intfs = {MixinInterface.class.getName()};
      InterfaceIntroduction.Mixin mixin = new InterfaceIntroduction.Mixin(Mixin.class.getName(), intfs, null, false);
      intro.getMixins().add(mixin);
      domain.addInterfaceIntroduction(intro);

      
      AspectDefinition def = new AspectDefinition("aspect", Scope.PER_VM, new GenericAspectFactory(EchoInterceptor.class.getName(), null));
      domain.addAspectDefinition(def);
      AdviceFactory advice = new AdviceFactory(def, "invoke");
      domain.addInterceptorFactory(advice.getName(), advice);
      //PointcutExpression pointcut = new PointcutExpression("pointcut", "execution(java.lang.String $instanceof{" + POJO.class.getName() + "}->helloWorld(..))");
      {
      PointcutExpression pointcut = new PointcutExpression("pointcut", "execution(java.lang.String " + POJO.class.getName() + "->helloWorld(..))");
      domain.addPointcut(pointcut);
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("pojo-binding", pointcut, null, null, interceptors);
      domain.addBinding(binding);
      }

      {
      PointcutExpression pointcut = new PointcutExpression("mixin-pointcut", "execution(java.lang.String $instanceof{" + MixinInterface.class.getName() + "}->intercepted(..))");
      domain.addPointcut(pointcut);
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("mixin-binding", pointcut, null, null, interceptors);
      domain.addBinding(binding);
      }

      Class<?> proxyClass = ContainerProxyFactory.getProxyClass(POJO.class, domain);
      ClassProxyContainer container = new ClassProxyContainer("test", domain);
      domain.setAdvisor(container);
      container.setClass(proxyClass);
      container.initializeClassContainer();
      POJO proxy = (POJO) proxyClass.newInstance();
      AspectManaged cp = (AspectManaged)proxy;
      cp.setAdvisor(container);
      Delegate delegate = (Delegate)cp;
      delegate.setDelegate(new POJO());

      MixinInterface mi = (MixinInterface) proxy;
      System.out.println("--- mixin");
      assertEquals(mi.hello("mixin"), "mixin");
      System.out.println("--- hw");
      assertEquals("echoed", proxy.helloWorld());
      System.out.println("--- icptd");
      assertEquals("echoed", mi.intercepted("error"));

   }
   
   public void testContainerProxyWithFinalMethods() throws Exception
   {
      InstanceDomain domain = new InstanceDomain(AspectManager.instance(), "test", false);
      AspectDefinition def = new AspectDefinition("aspect", Scope.PER_VM, new GenericAspectFactory(EchoInterceptor.class.getName(), null));
      domain.addAspectDefinition(def);
      AdviceFactory advice = new AdviceFactory(def, "invoke");
      domain.addInterceptorFactory(advice.getName(), advice);
      {
      PointcutExpression pointcut = new PointcutExpression("pointcut", "execution(* " + POJOWithFinalMethods.class.getName() + "->*(..))");
      domain.addPointcut(pointcut);
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("pojo-binding", pointcut, null, null, interceptors);
      domain.addBinding(binding);
      }

      Class<?> proxyClass = ContainerProxyFactory.getProxyClass(POJOWithFinalMethods.class, domain);
      ClassProxyContainer container = new ClassProxyContainer("test", domain);
      domain.setAdvisor(container);
      container.setClass(proxyClass);
      container.initializeClassContainer();
      POJOWithFinalMethods proxy = (POJOWithFinalMethods) proxyClass.newInstance();
      AspectManaged cp = (AspectManaged)proxy;
      cp.setAdvisor(container);
      Delegate delegate = (Delegate)cp;
      delegate.setDelegate(new POJOWithFinalMethods());
      
      EchoInterceptor.intercepted = false;
      proxy.method();
      assertTrue(EchoInterceptor.intercepted);
      
      EchoInterceptor.intercepted = false;
      proxy.finalMethod();
      assertFalse(EchoInterceptor.intercepted);
   }
   
   
   public void testHollowProxyWithInterfaceContainingObjectMethods() throws Exception
   {
      //Here to verify that we do not "crash" with methods already in the proxy class (toString(), equals() and hashCode() all exist in the template)
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setObjectAsSuperClass(true);
      params.setInterfaces(new Class[] {OverrideObjectInterface.class});
      
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      Object o = factory.createAdvisedProxy(params);
      assertTrue(o instanceof OverrideObjectInterface);

      assertTrue(o.equals(o));
      assertEquals(o.hashCode(), o.hashCode());
      assertEquals(o.toString(), o.toString());
      
      OverrideObjectClass tgt = new OverrideObjectClass();
      assertNotNull(tgt);
      ((Delegate)o).setDelegate(tgt);
      Object target = ((Delegate)o).getDelegate();
      assertSame(tgt, target);
      
      assertFalse(tgt.invokedEquals);
      assertFalse(tgt.invokedHashCode);
      assertFalse(tgt.invokedToString);

      assertTrue(o.equals(o));
      assertTrue(tgt.invokedEquals);
      assertEquals(o.hashCode(), o.hashCode());
      assertTrue(tgt.invokedHashCode);
      assertEquals(o.toString(), o.toString());
      assertTrue(tgt.invokedToString);
   }
   
   public void testProxyWithPerInstanceAspects() throws Exception
   {
      AspectManager manager = AspectManager.instance();
      AspectDefinition def = new AspectDefinition("perinstanceaspect", Scope.PER_INSTANCE, new GenericAspectFactory(TestInterceptor.class.getName(), null));
      AdviceFactory advice = new AdviceFactory(def, "invoke");
      PointcutExpression pointcut = new PointcutExpression("perinstancepointcut", "execution(* $instanceof{" + SomeInterface.class.getName() + "}->*(..))");
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("perinstancebinding", pointcut, null, null, interceptors);
      try
      {
         manager.addAspectDefinition(def);
         manager.addInterceptorFactory(advice.getName(), advice);
         manager.addPointcut(pointcut);
         manager.addBinding(binding);
         
         AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
         params.setInterfaces(new Class[] {SomeInterface.class});
         params.setTarget(new POJO());
         
         GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
         SomeInterface si = (SomeInterface)factory.createAdvisedProxy(params);
         
         si.helloWorld();
         
         assertTrue(TestInterceptor.invoked);
      }
      finally
      {
         manager.removeBinding("perinstancebinding");
         manager.removePointcut("perinstancepointcut");
         manager.removeInterceptorFactory("perinstanceaspect");
      }
   }

   public void testProxyWithPerJoinpointAspects() throws Exception
   {
      AspectManager manager = AspectManager.instance();
      AspectDefinition def = new AspectDefinition("perinstanceaspect", Scope.PER_JOINPOINT, new GenericAspectFactory(TestInterceptor.class.getName(), null));
      AdviceFactory advice = new AdviceFactory(def, "invoke");
      PointcutExpression pointcut = new PointcutExpression("perinstancepointcut", "execution(* $instanceof{" + SomeInterface.class.getName() + "}->*(..))");
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("perinstancebinding", pointcut, null, null, interceptors);
      try
      {
         manager.addAspectDefinition(def);
         manager.addInterceptorFactory(advice.getName(), advice);
         manager.addPointcut(pointcut);
         manager.addBinding(binding);
         
         AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
         params.setInterfaces(new Class[] {SomeInterface.class});
         params.setTarget(new POJO());
         
         GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
         SomeInterface si = (SomeInterface)factory.createAdvisedProxy(params);
         
         si.helloWorld();
         
         assertTrue(TestInterceptor.invoked);
      }
      finally
      {
         manager.removeBinding("perinstancebinding");
         manager.removePointcut("perinstancepointcut");
         manager.removeInterceptorFactory("perinstanceaspect");
      }
   }
   
   @SuppressWarnings("unchecked")
   public void testAnnotationsExistInProxy() throws Exception
   {
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setInterfaces(new Class[]{SomeInterface.class});
      params.setTarget(new AnnotatedPOJO());
      
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      AnnotatedPOJO pojo = (AnnotatedPOJO)factory.createAdvisedProxy(params);
      
      Class proxyClass = pojo.getClass();
      assertNotSame(AnnotatedPOJO.class, proxyClass);
      assertEquals(AnnotatedPOJO.class, proxyClass.getSuperclass());
      
      checkExpectedAnnotations(AnnotatedPOJO.class);
      checkExpectedAnnotations(proxyClass);
   }
   
   public void testProxyWithMetaData() throws Exception
   {
      AspectManager manager = AspectManager.instance();
      AspectDefinition def = new AspectDefinition("aspect", Scope.PER_INSTANCE, new GenericAspectFactory(TestInterceptor.class.getName(), null));
      AdviceFactory advice = new AdviceFactory(def, "invoke");
      PointcutExpression pointcut = new PointcutExpression("pointcut", "execution(* " + Annotation.class.getName() + "->*(..))");
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("binding", pointcut, null, null, interceptors);
      try
      {
         manager.addAspectDefinition(def);
         manager.addInterceptorFactory(advice.getName(), advice);
         manager.addPointcut(pointcut);
         manager.addBinding(binding);
         
         AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
         params.setTarget(new POJO());
         
         GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
         POJO plain = (POJO)factory.createAdvisedProxy(params);
         
         assertFalse(plain instanceof AspectManaged);
         
         MetaData someAnnMD = setupMetaData(POJO.class, new SomeAnnotationImpl());
         params.setMetaData(someAnnMD);
         params.setMetaDataHasInstanceLevelData(true);
         params.setContainerCache(null);
         POJO instanceAnnotationWithNoBindings = (POJO)factory.createAdvisedProxy(params);
         assertFalse(instanceAnnotationWithNoBindings instanceof AspectManaged);
         
         MetaData annMD = setupMetaData(POJO.class, new AnnotationImpl());
         params.setMetaData(annMD);
         params.setMetaDataHasInstanceLevelData(true);
         params.setContainerCache(null);
         POJO instanceAnnotationWithBindings = (POJO)factory.createAdvisedProxy(params);
         assertFalse(instanceAnnotationWithBindings instanceof AspectManaged);
         assertTrue(TestInterceptor.invoked);

         InterfaceIntroduction intro = new InterfaceIntroduction("intro", "@" + SomeAnnotation.class.getName(), new String[] {SomeInterface.class.getName()});
         manager.addInterfaceIntroduction(intro);
         params.setMetaData(someAnnMD);
         params.setContainerCache(null);
         POJO introduced = (POJO)factory.createAdvisedProxy(params);
         assertTrue(introduced instanceof AspectManaged);
         assertTrue(introduced instanceof SomeInterface);
      }
      finally
      {
         manager.removeBinding("binding");
         manager.removePointcut("pointcut");
         manager.removeInterceptorFactory("aspect");
         manager.removeInterfaceIntroduction("intro");
      }
   }
   
   public void testProxyWithOnlyIntroductions() throws Exception
   {
      AspectManager manager = AspectManager.instance();
      InterfaceIntroduction intro = new InterfaceIntroduction("intro", POJOWithIntroduction.class.getName(), new String[] {Serializable.class.getName()});
      try
      {
         manager.addInterfaceIntroduction(intro);
         
         AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
         params.setTarget(new POJOWithIntroduction());
         
         GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
         POJOWithIntroduction pojo = (POJOWithIntroduction)factory.createAdvisedProxy(params);
         assertTrue(pojo instanceof AspectManaged);
         
         assertInstanceOf(pojo, Serializable.class);
      }
      finally
      {
         manager.removeInterfaceIntroduction(intro.getName());
      }
   }
   
   private MetaData setupMetaData(Class<?> clazz, Annotation...annotations) 
   {
      MutableMetaDataRepository repository = new BasicMetaDataRepository();
      
      ScopeKey scopeKey = ScopeKey.DEFAULT_SCOPE.clone();
      scopeKey.addScope(CommonLevels.INSTANCE, "Test");
      scopeKey.addScope(CommonLevels.CLASS, clazz.getName());
      scopeKey.addScope(CommonLevels.WORK, String.valueOf(hashCode()));
      ScopeKey mutableScope = new ScopeKey(CommonLevels.INSTANCE, "Test".toString());
      MemoryMetaDataLoader mutable = new MemoryMetaDataLoader(mutableScope);
      repository.addMetaDataRetrieval(mutable);
      addClassAnnotations(clazz, mutable, annotations);
      
      MetaData metadata = new MetaDataRetrievalToMetaDataBridge(mutable); 
      
      return metadata;
   }
   
   private void addClassAnnotations(Class<?> clazz, MemoryMetaDataLoader mutable, Annotation[] extraAnnotations)
   {
      Annotation[] anns = clazz.getAnnotations();
      for (Annotation ann : anns)
      {
         mutable.addAnnotation(ann);
      }
      for (Annotation ann : extraAnnotations)
      {
         mutable.addAnnotation(ann);
      }
   }
   
   private void checkExpectedAnnotations(Class<?> clazz) throws Exception
   {
      org.jboss.test.aop.proxy.Annotation ann = clazz.getAnnotation(org.jboss.test.aop.proxy.Annotation.class);
      assertNotNull(ann);

      Method getter = clazz.getMethod("getX");
      assertNotNull(getter.getAnnotation(org.jboss.test.aop.proxy.Annotation.class));
      
      Method setter = clazz.getMethod("setX", Object.class);
      assertNotNull(setter.getAnnotation(org.jboss.test.aop.proxy.Annotation.class));
      assertEquals(1, setter.getParameterTypes().length);
      assertEquals(1, setter.getParameterAnnotations().length);
      assertEquals(1, setter.getParameterAnnotations()[0].length);
      assertEquals(org.jboss.test.aop.proxy.Annotation.class, setter.getParameterAnnotations()[0][0].annotationType());
   }
}
