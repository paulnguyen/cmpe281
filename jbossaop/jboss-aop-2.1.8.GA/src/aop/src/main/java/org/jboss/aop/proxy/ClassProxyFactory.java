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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.SerialVersionUID;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.ClassInstanceAdvisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.instrument.Instrumentor;
import org.jboss.aop.instrument.TransformerCommon;
import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.reference.MethodPersistentReference;
import org.jboss.aop.util.reference.PersistentReference;
import org.jboss.util.collection.WeakValueHashMap;


/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 85938 $
 */
public class ClassProxyFactory
{
   private static Object maplock = new Object();
   private static WeakValueHashMap<String, Class<?>> classnameMap = new WeakValueHashMap<String, Class<?>>();
   private static WeakHashMap<ClassLoader, WeakHashMap<Class<?>, WeakReference<Class<?>>>> proxyCache = new WeakHashMap<ClassLoader, WeakHashMap<Class<?>, WeakReference<Class<?>>>>();
   private static WeakHashMap<Class<?>, Map<Long, MethodPersistentReference>> methodMapCache = new WeakHashMap<Class<?>, Map<Long, MethodPersistentReference>>();
   
   public static final String GENERATED_PROXIES_PACKAGE = "org.jboss.aop.generatedproxies";

   public static ClassProxy newInstance(Class<?> clazz) throws Exception
   {
      return newInstance(clazz, null);
   }

   public static ClassProxy newInstance(Class<?> clazz, ProxyMixin[] mixins) throws Exception
   {
      return newInstance(clazz, mixins, new ClassInstanceAdvisor());
   }

   public static ClassProxy newInstance(Class<?> clazz, ProxyMixin[] mixins, boolean interceptWriteReplace) throws Exception
   {
      return newInstance(clazz, mixins, new ClassInstanceAdvisor(), interceptWriteReplace);
   }

   private static Class<?> getProxyClass(Class<?> clazz, ProxyMixin[] mixins, boolean interceptWriteReplace)
   throws Exception
   {
      // Don't make a proxy of a proxy !
      if (ClassProxy.class.isAssignableFrom(clazz)) clazz = clazz.getSuperclass();

      ClassPool pool = AspectManager.instance().findClassPool(clazz);
      if (pool == null) throw new NullPointerException("Could not find ClassPool");

      
      Class<?> proxyClass = null;
      synchronized (maplock)
      {
         WeakHashMap<Class<?>, WeakReference<Class<?>>> proxiesForLoader = proxyCache.get(pool.getClassLoader());
         if (proxiesForLoader == null)
         {
            proxiesForLoader = new WeakHashMap<Class<?>, WeakReference<Class<?>>>();
            proxyCache.put(pool.getClassLoader(), proxiesForLoader);
         }
         if (proxiesForLoader != null)
         {
            WeakReference<Class<?>> ref = proxiesForLoader.get(pool.getClassLoader());
            if (ref != null)
            {
               proxyClass = ref.get();
            }
         }
         if (proxyClass == null)
         {
            proxyClass = generateProxy(pool, clazz, mixins, interceptWriteReplace);
            classnameMap.put(clazz.getName(), proxyClass);
            proxiesForLoader.put(clazz, new WeakReference<Class<?>>(proxyClass));
            HashMap<Long, MethodPersistentReference> map = methodMap(clazz);
            methodMapCache.put(proxyClass, map);
         }
      }
      return proxyClass;
   }

   public static ClassProxy newInstance(Class<?> clazz, ProxyMixin[] mixins, InstanceAdvisor advisor) throws Exception
   {
      return newInstance(clazz, mixins, advisor, false);

   }

   public static ClassProxy newInstance(Class<?> clazz, ProxyMixin[] mixins, InstanceAdvisor advisor, boolean interceptWriteReplace) throws Exception
   {
      Class<?> proxyClass = getProxyClass(clazz, mixins, interceptWriteReplace);
      ClassProxy proxy = (ClassProxy) proxyClass.newInstance();
      proxy.setMixins(mixins);
      proxy._setInstanceAdvisor(advisor);
      return proxy;

   }

   public static HashMap<Long, MethodPersistentReference> getMethodMap(String classname)
   {
      synchronized (maplock)
      {
         Class<?> clazz = classnameMap.get(classname);
         if (clazz == null) return null;
         return (HashMap<Long, MethodPersistentReference>) methodMapCache.get(clazz);
      }
   }

   public static HashMap<Long, MethodPersistentReference> getMethodMap(Class<?> clazz)
   {
      HashMap<Long, MethodPersistentReference> map = getMethodMap(clazz.getName());
      if (map != null) return map;
      try
      {
         return methodMap(clazz);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
   }

   private static int counter = 0;

   private static CtClass createProxyCtClass(ClassPool pool, ProxyMixin[] mixins, Class<?> clazz, boolean interceptWriteReplace)
   throws Exception
   {
      String packageName = clazz.getPackage().getName();
      if (packageName.startsWith("java.") || packageName.startsWith("sun."))
      {
         packageName = GENERATED_PROXIES_PACKAGE;
      }
      String classname = null;
      synchronized (ClassProxyFactory.class)
      {
         classname = packageName + ".AOPClassProxy$" + counter++;
      }

      CtClass template = pool.get("org.jboss.aop.proxy.ClassProxyTemplate");
      CtClass superclass = pool.get(clazz.getName());
      
      CtField mixinField = template.getField("mixins");
      CtField instanceAdvisor = template.getField("instanceAdvisor");
      
      CtClass proxy = TransformerCommon.makeClass(pool, classname, superclass);
      
      mixinField = new CtField(mixinField.getType(), "mixins", proxy);
      mixinField.setModifiers(Modifier.PRIVATE);
      Instrumentor.addSyntheticAttribute(mixinField);
      proxy.addField(mixinField);
      instanceAdvisor = new CtField(instanceAdvisor.getType(), "instanceAdvisor", proxy);
      instanceAdvisor.setModifiers(Modifier.PRIVATE);
      proxy.addField(instanceAdvisor);

      CtMethod writeEx = CtNewMethod.make("   public void writeExternal(java.io.ObjectOutput out)\n" +
                                          "   throws java.io.IOException\n" +
                                          "   {\n" +
                                          "   }", proxy);
      Instrumentor.addSyntheticAttribute(writeEx);
      CtMethod readEx = CtNewMethod.make("   public void readExternal(java.io.ObjectInput in)\n" +
                                         "   throws java.io.IOException, ClassNotFoundException\n" +
                                         "   {\n" +
                                         "   }", proxy);
      Instrumentor.addSyntheticAttribute(readEx);
      CtMethod getInstanceAdvisor = CtNewMethod.make("   public org.jboss.aop.InstanceAdvisor _getInstanceAdvisor()\n" +
                                                     "   {\n" +
                                                     "      return instanceAdvisor;\n" +
                                                     "   }", proxy);
      Instrumentor.addSyntheticAttribute(getInstanceAdvisor);
      CtMethod setInstanceAdvisor = CtNewMethod.make("   public void _setInstanceAdvisor(org.jboss.aop.InstanceAdvisor newAdvisor)\n" +
                                                     "   {\n" +
                                                     "      instanceAdvisor = (org.jboss.aop.ClassInstanceAdvisor) newAdvisor;\n" +
                                                     "   }", proxy);
      Instrumentor.addSyntheticAttribute(setInstanceAdvisor);
      CtMethod dynamicInvoke = CtNewMethod.make("   public org.jboss.aop.joinpoint.InvocationResponse _dynamicInvoke(org.jboss.aop.joinpoint.Invocation invocation)\n" +
                                                "   throws Throwable\n" +
                                                "   {\n" +
                                                "      ((org.jboss.aop.joinpoint.InvocationBase) invocation).setInstanceResolver(instanceAdvisor.getMetaData());\n" +
                                                "      org.jboss.aop.advice.Interceptor[] aspects = instanceAdvisor.getInterceptors();\n" +
                                                "      return new org.jboss.aop.joinpoint.InvocationResponse(invocation.invokeNext(aspects));\n" +
                                                "   }", proxy);
      Instrumentor.addSyntheticAttribute(dynamicInvoke);
      CtMethod setMixins = CtNewMethod.make("   public void setMixins(org.jboss.aop.proxy.ProxyMixin[] mixins)\n" +
                                            "   {\n" +
                                            "      this.mixins = mixins;\n" +
                                            "   }", proxy);
      Instrumentor.addSyntheticAttribute(setMixins);
      CtMethod writeReplace = CtNewMethod.make("   public Object writeReplace() throws java.io.ObjectStreamException\n" +
                                               "   {\n" +
                                               "      return new org.jboss.aop.proxy.MarshalledClassProxy(this.getClass().getSuperclass(), mixins, instanceAdvisor);\n" +
                                               "   }", proxy);
      Instrumentor.addSyntheticAttribute(writeReplace);

      proxy.addMethod(writeEx);
      proxy.addMethod(readEx);
      proxy.addMethod(getInstanceAdvisor);
      proxy.addMethod(setInstanceAdvisor);
      proxy.addMethod(dynamicInvoke);
      proxy.addMethod(setMixins);
      if (!interceptWriteReplace)
         proxy.addMethod(writeReplace);

      /*
      CtMethod writeEx = template.getDeclaredMethod("writeExternal");
      CtMethod readEx = template.getDeclaredMethod("readExternal");
      CtMethod getInstanceAdvisor = template.getDeclaredMethod("_getInstanceAdvisor");
      CtMethod setInstanceAdvisor = template.getDeclaredMethod("_setInstanceAdvisor");
      CtMethod dynamicInvoke = template.getDeclaredMethod("_dynamicInvoke");
      CtMethod setMixins = template.getDeclaredMethod("setMixins");
      CtMethod writeReplace = template.getDeclaredMethod("writeReplace");




      proxy.addMethod(CtNewMethod.copy(writeEx, proxy, null));
      proxy.addMethod(CtNewMethod.copy(readEx, proxy, null));
      proxy.addMethod(CtNewMethod.copy(getInstanceAdvisor, proxy, null));
      proxy.addMethod(CtNewMethod.copy(setInstanceAdvisor, proxy, null));
      proxy.addMethod(CtNewMethod.copy(dynamicInvoke, proxy, null));
      proxy.addMethod(CtNewMethod.copy(setMixins, proxy, null));
      proxy.addMethod(CtNewMethod.copy(writeReplace, proxy, null));
      */


      proxy.addInterface(pool.get("org.jboss.aop.proxy.ClassProxy"));
      proxy.addInterface(pool.get("java.io.Externalizable"));
      proxy.addInterface(pool.get("org.jboss.aop.instrument.Untransformable"));
      proxy.addInterface(pool.get("org.jboss.aop.proxy.MethodMapped"));

      CtClass map = pool.get("java.util.Map");
      CtField methodMap = new CtField(map, "methodMap", proxy);
      methodMap.setModifiers(Modifier.PRIVATE | Modifier.STATIC);
      proxy.addField(methodMap);
      CtMethod getMethodMap = CtNewMethod.getter("getMethodMap", methodMap);
      getMethodMap.setModifiers(Modifier.PUBLIC);
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

      HashMap<Long, CtMethod> allMethods = JavassistMethodHashing.getMethodMap(superclass);
      
      if (interceptWriteReplace)
         allMethods.put(JavassistMethodHashing.methodHash(writeReplace), writeReplace);
     
      for (Map.Entry<Long, CtMethod> entry : allMethods.entrySet())
      {
         CtMethod m = entry.getValue();
         if (!Modifier.isPublic(m.getModifiers()) || Modifier.isStatic(m.getModifiers())) continue;

         Long hash = entry.getKey();
         if (addedMethods.contains(hash)) continue;
         addedMethods.add(hash);
         String aopReturnStr = (m.getReturnType().equals(CtClass.voidType)) ? "" : "return ($r)";
         String args = "null";
         if (m.getParameterTypes().length > 0) args = "$args";
         String code = "{   " +
         "    org.jboss.aop.advice.Interceptor[] aspects = instanceAdvisor.getInterceptors(); " +
         "    org.jboss.aop.MethodInfo mi = new org.jboss.aop.MethodInfo(); " +
         "    mi.setHash(" + hash.longValue() + "L);" +
         "    org.jboss.aop.proxy.ProxyMethodInvocation invocation = new org.jboss.aop.proxy.ProxyMethodInvocation(this, mi, aspects); " +
         "    invocation.setInstanceResolver(instanceAdvisor.getMetaData()); " +
         "    invocation.setArguments(" + args + "); " +
         "    " + aopReturnStr + " invocation.invokeNext(); " +
         "}";
         CtMethod newMethod = CtNewMethod.make(m.getReturnType(), m.getName(), m.getParameterTypes(), m.getExceptionTypes(), code, proxy);
         newMethod.setModifiers(Modifier.PUBLIC);
         proxy.addMethod(newMethod);
      }
      SerialVersionUID.setSerialVersionUID(proxy);
      return proxy;
   }

   private static Class<?> generateProxy(ClassPool pool, Class<?> clazz, ProxyMixin[] mixins, boolean interceptWriteReplace) throws Exception
   {
      CtClass proxy = createProxyCtClass(pool, mixins, clazz, interceptWriteReplace);
      ProtectionDomain pd = clazz.getProtectionDomain();
      Class<?> proxyClass = TransformerCommon.toClass(proxy, pd);
      Map<Long, MethodPersistentReference> methodmap = ClassProxyFactory.getMethodMap(proxyClass); 
      Field field = proxyClass.getDeclaredField("methodMap");
      SecurityActions.setAccessible(field);
      field.set(null, methodmap);
      return proxyClass;
   }

   private static void populateMethodTables(HashMap<Long, MethodPersistentReference> advised, List<Long> ignoredHash, Class<?> superclass)
   throws Exception
   {
      if (superclass == null) return;
      if (superclass.getName().equals("java.lang.Object")) return;

      Method[] declaredMethods = superclass.getDeclaredMethods();
      for (int i = 0; i < declaredMethods.length; i++)
      {
         if (ClassAdvisor.isAdvisable(declaredMethods[i])) 
         {   
            //if a method is marked as a "volatile/bridge" method, we need to
            //ignore it and check that other implementations of that method
            // (in superclasses) are not added either.
            if(!java.lang.reflect.Modifier.isVolatile( declaredMethods[i].getModifiers()))
            {
               long hash = org.jboss.aop.util.MethodHashing.methodHash(declaredMethods[i]);
               if(!ignoredHash.contains(new Long(hash)))
                  advised.put(new Long(hash), new MethodPersistentReference(declaredMethods[i], PersistentReference.REFERENCE_WEAK));
            }
            else
            {
               long hash = org.jboss.aop.util.MethodHashing.methodHash(declaredMethods[i]);
               ignoredHash.add(new Long(hash));
            }
         }
      }
      
      populateMethodTables(advised, ignoredHash, superclass.getSuperclass());

   }

   public static HashMap<Long, MethodPersistentReference> methodMap(Class<?> clazz)
   throws Exception
   {
      HashMap<Long, MethodPersistentReference> methods = new HashMap<Long, MethodPersistentReference>();
      List<Long> ignoredHash = new ArrayList<Long>();
      populateMethodTables(methods, ignoredHash, clazz);
      return methods;
   }

}
