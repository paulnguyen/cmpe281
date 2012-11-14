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
package org.jboss.aop.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassInstanceAdvisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.instrument.Instrumentor;
import org.jboss.aop.instrument.TransformerCommon;
import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.reference.MethodPersistentReference;
import org.jboss.util.collection.WeakValueHashMap;
import org.jboss.util.id.GUID;

import java.lang.reflect.Field;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Map;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 85938 $
 */
public class ProxyFactory
{
   private static long counter = 0;
   private static WeakValueHashMap<GUID, Class<?>> proxyCache = new WeakValueHashMap<GUID, Class<?>>();

   public static final String GENERATED_PROXIES_PACKAGE = ClassProxyFactory.GENERATED_PROXIES_PACKAGE;

   public static Proxy createInterfaceProxy(ClassLoader loader, Class<?>[] interfaces, ProxyMixin[] mixins, InstanceAdvisor advisor) throws Exception
   {
      Class<?> clazz = createProxyClass(loader, mixins, interfaces);

      Proxy instance = (Proxy) clazz.newInstance();
      instance.instanceAdvisor = advisor;
      instance.mixins = mixins;
      instance.interfaces = interfaces;
      instance.guid = new GUID();

      synchronized (proxyCache)
      {
         proxyCache.put(instance.guid, clazz);
      }

      return instance;
   }

   public static Class<?> getProxyClass(GUID guid)
   {
      synchronized (proxyCache)
      {
         return proxyCache.get(guid);
      }
   }

   public static Proxy createInterfaceProxy(GUID guid, ClassLoader loader, Class<?>[] interfaces) throws Exception
   {
      return createInterfaceProxy(guid, loader, interfaces, null, new ClassInstanceAdvisor());
   }

   public static Proxy createInterfaceProxy(GUID guid, ClassLoader loader, Class<?>[] interfaces, ProxyMixin[] mixins, InstanceAdvisor advisor) throws Exception
   {
      Class<?> clazz = getProxyClass(guid);
      boolean wasFound = true;
      if (clazz == null)
      {
         wasFound = false;
         clazz = createProxyClass(loader, mixins, interfaces);
      }

      Proxy instance = (Proxy) clazz.newInstance();
      instance.instanceAdvisor = advisor;
      instance.mixins = mixins;
      instance.interfaces = interfaces;
      instance.guid = guid;

      if (!wasFound)
      {
         synchronized (proxyCache)
         {
            proxyCache.put(guid, clazz);
         }
      }

      return instance;
   }

   private static Class<?> createProxyClass(ClassLoader loader, ProxyMixin[] mixins, Class<?>[] interfaces)
   throws Exception
   {
      CtClass proxy = createProxyCtClass(loader, mixins, interfaces);
      // Choose the first non-null ProtectionDomain
      ProtectionDomain pd = null;
      for(int n = 0; n < interfaces.length && pd == null; n ++)
      {
        pd = interfaces[n].getProtectionDomain(); 
      }
      Class<?> clazz = TransformerCommon.toClass(proxy, loader, pd);
      Map<Long, MethodPersistentReference> methodmap = ClassProxyFactory.getMethodMap(clazz);
      Field field = clazz.getDeclaredField("methodMap");
      SecurityActions.setAccessible(field);
      field.set(null, methodmap);
      return clazz;
   }

   public static GUID generateProxyClass(ClassLoader loader, ProxyMixin[] mixins, Class<?>[] interfaces) throws Exception
   {
      Class<?> clazz = createProxyClass(loader, mixins, interfaces);
      GUID guid = new GUID();

      synchronized (proxyCache)
      {
         proxyCache.put(guid, clazz);
      }

      return guid;
   }

   private static CtClass createProxyCtClass(ClassLoader loader, ProxyMixin[] mixins, Class<?>[] interfaces)
   throws Exception
   {
      ClassPool pool = AspectManager.instance().findClassPool(loader);
      if (pool == null) throw new NullPointerException("Could not find ClassPool");


      String classname = null;
      synchronized (ProxyFactory.class)
      {
         classname = GENERATED_PROXIES_PACKAGE + ".AOPProxy$" + counter++;
      }

      CtClass base = pool.get("org.jboss.aop.proxy.Proxy");
      CtClass proxy = TransformerCommon.makeClass(pool, classname, base);
      proxy.addInterface(pool.get("org.jboss.aop.instrument.Untransformable"));
      CtClass map = pool.get("java.util.Map");
      CtField methodMap = new CtField(map, "methodMap", proxy);
      methodMap.setModifiers(Modifier.PRIVATE | Modifier.STATIC);
      Instrumentor.addSyntheticAttribute(methodMap);
      proxy.addField(methodMap);
      CtMethod getMethodMap = CtNewMethod.getter("getMethodMap", methodMap);
      getMethodMap.setModifiers(Modifier.PUBLIC);
      Instrumentor.addSyntheticAttribute(getMethodMap);
      proxy.addMethod(getMethodMap);

      HashSet<String> addedInterfaces = new HashSet<String>();
      HashSet<Long> addedMethods = new HashSet<Long>();
      if (mixins != null)
      {
         for (int i = 0; i < mixins.length; i++)
         {
            HashSet<Long> mixinMethods = new HashSet<Long>();
            Class<?>[] mixinf = mixins[i].getInterfaces();
            ClassPool mixPool = AspectManager.instance().findClassPool(mixins[i].getMixin().getClass());
            CtClass mixClass = mixPool.get(mixins[i].getMixin().getClass().getName());
            for (int j = 0; j < mixinf.length; j++)
            {
               if (addedInterfaces.contains(mixinf[j].getName())) throw new Exception("2 mixins are implementing the same interfaces");
               ClassPool mixIntfPool = AspectManager.instance().findClassPool(mixinf[j]);
               CtClass intfClass = mixIntfPool.get(mixinf[j].getName());
               CtMethod[] methods = intfClass.getMethods();
               for (int m = 0; m < methods.length; m++)
               {
                  if (methods[m].getDeclaringClass().getName().equals("java.lang.Object")) continue;
                  Long hash = new Long(JavassistMethodHashing.methodHash(methods[m]));
                  if (mixinMethods.contains(hash)) continue;
                  if (addedMethods.contains(hash)) throw new Exception("More than one mixin has same method");
                  mixinMethods.add(hash);
                  addedMethods.add(hash);
                  String returnStr = (methods[m].getReturnType().equals(CtClass.voidType)) ? "" : "return ";
                  String code = "{" +
                  "   " + mixClass.getName() + " mixin = (" + mixClass.getName() + ")mixins[" + i + "].getMixin();" +
                  "   " + returnStr + " mixin." + methods[m].getName() + "($$);" +
                  "}";
                  CtMethod newMethod = CtNewMethod.make(methods[m].getReturnType(), methods[m].getName(), methods[m].getParameterTypes(), methods[m].getExceptionTypes(), code, proxy);
                  newMethod.setModifiers(Modifier.PUBLIC);
                  proxy.addMethod(newMethod);
               }

               proxy.addInterface(intfClass);
               addedInterfaces.add(intfClass.getName());
            }
         }
      }

      for (int i = 0; i < interfaces.length; i++)
      {
         if (addedInterfaces.contains(interfaces[i].getName())) continue;
         ClassPool mixPool = AspectManager.instance().findClassPool(interfaces[i]);
         CtClass intfClass = mixPool.get(interfaces[i].getName());
         CtMethod[] methods = intfClass.getMethods();
         for (int m = 0; m < methods.length; m++)
         {
            if (methods[m].getDeclaringClass().getName().equals("java.lang.Object")) continue;
            Long hash = new Long(JavassistMethodHashing.methodHash(methods[m]));
            if (addedMethods.contains(hash)) continue;
            addedMethods.add(hash);
            String aopReturnStr = (methods[m].getReturnType().equals(CtClass.voidType)) ? "" : "return ($r)";
            String args = "null";
            if (methods[m].getParameterTypes().length > 0) args = "$args";
            String code = "{   " +
            "    org.jboss.aop.advice.Interceptor[] aspects = instanceAdvisor.getInterceptors(); " +
            "    org.jboss.aop.MethodInfo mi = new org.jboss.aop.MethodInfo(); " +
            "    mi.setHash(" + hash.longValue() + "L);" +
            "    org.jboss.aop.proxy.ProxyMethodInvocation invocation = new org.jboss.aop.proxy.ProxyMethodInvocation(this, mi, aspects); " +
            "    invocation.setInstanceResolver(instanceAdvisor.getMetaData()); " +
            "    invocation.setArguments(" + args + "); " +
            "    " + aopReturnStr + " invocation.invokeNext(); " +
            "}";
            CtMethod newMethod = CtNewMethod.make(methods[m].getReturnType(), methods[m].getName(), methods[m].getParameterTypes(), methods[m].getExceptionTypes(), code, proxy);
            newMethod.setModifiers(Modifier.PUBLIC);
            proxy.addMethod(newMethod);
         }
         proxy.addInterface(intfClass);
      }
      return proxy;
   }
   
}
