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
package org.jboss.test.aop.classpool.ucl.test;

import java.util.ArrayList;

import org.jboss.aop.proxy.container.AOPProxyFactoryParameters;
import org.jboss.aop.proxy.container.AspectManaged;
import org.jboss.aop.proxy.container.ContainerProxyFactory;
import org.jboss.aop.proxy.container.GeneratedAOPProxyFactory;

import javassist.ClassPool;
import javassist.CtClass;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class UclDelegatingClassPoolTestCase extends UclClassPoolTest
{

   public UclDelegatingClassPoolTestCase(String name)
   {
      // FIXME SimpleUclDelegatingClassPoolTestCase constructor
      super(name);
   }

   public static Test suite()
   {
      return new TestSuite(UclDelegatingClassPoolTestCase.class);
   }

   
   public void testGlobalScope() throws Exception
   {
      ClassPool globalA = null;
      ClassPool globalB = null;
      try
      {
         try
         {
            globalA = createGlobalClassPool(JAR_A_1);
            globalB = createGlobalClassPool(JAR_B_1);
   
            //Call twice to ensure that we hit the cache as well as creating the CtClases
            accessGlobalScope(globalA, globalB);
            accessGlobalScope(globalA, globalB);
         }
         finally
         {
            removeClassPool(globalA);
         }
         assertCannotLoadCtClass(globalB, CLASS_A);
      }
      finally
      {
         removeClassPool(globalB);
      }
   }
   
   private void accessGlobalScope(ClassPool globalA, ClassPool globalB) throws Exception
   {
      CtClass aFromA = globalA.get(CLASS_A);
      assertNotNull(aFromA);
      assertEquals(CLASS_A, aFromA.getName());
      CtClass bFromA = globalA.get(CLASS_B);
      assertNotNull(bFromA);
      assertEquals(CLASS_B, bFromA.getName());
      CtClass aFromB = globalB.get(CLASS_A);
      assertNotNull(aFromB);
      assertSame(aFromA, aFromB);
      CtClass bFromB = globalB.get(CLASS_B);
      assertNotNull(bFromB);
      assertSame(bFromA, bFromB);
   }
   
   public void testChildDomain() throws Exception
   {
      ClassPool poolA = null;
      ClassPool poolB = null;
      ClassPool poolC = null;
      try
      {
         poolA = createGlobalClassPool(JAR_A_1);
         poolB = createGlobalClassPool(JAR_B_1);
         poolC = createChildClassPool(JAR_C_1, false);

         //Call twice to ensure that we hit the cache as well as creating the CtClases
         accessChildDomain(poolA, poolB, poolC);
         accessChildDomain(poolA, poolB, poolC);
      }
      finally
      {
         removeClassPool(poolA);
         removeClassPool(poolB);
         removeClassPool(poolC);
      }
   }
   
   private void accessChildDomain(ClassPool poolA, ClassPool poolB, ClassPool poolC) throws Exception
   {
      CtClass aFromA = poolA.get(CLASS_A);
      assertNotNull(aFromA);
      CtClass bFromB = poolB.get(CLASS_B);
      assertNotNull(bFromB);
      CtClass cFromC = poolC.get(CLASS_C);
      assertNotNull(cFromC);
      CtClass aFromC = poolC.get(CLASS_A);
      assertNotNull(aFromC);
      CtClass bFromC = poolC.get(CLASS_B);
      assertNotNull(bFromC);
      
      assertSame(aFromA, aFromC);
      assertSame(bFromB, bFromC);
      assertSame(poolA, aFromA.getClassPool());
      assertSame(poolB, bFromB.getClassPool());
      assertSame(poolC, cFromC.getClassPool());
      
      assertCannotLoadCtClass(poolA, CLASS_C);
      assertCannotLoadCtClass(poolB, CLASS_C);
   }
   
   public void testSiblingDomains() throws Exception
   {
      ClassPool poolA = createChildClassPool(JAR_A_1, true);
      ClassPool poolB = createChildClassPool(JAR_B_1, false);
      
      try
      {
         accessSiblingDomains(poolA, poolB);
      }
      finally
      {
         removeClassPool(poolA);
         removeClassPool(poolB);
      }
   }

   private void accessSiblingDomains(ClassPool poolA, ClassPool poolB) throws Exception
   {
      CtClass clazzA = poolA.get(CLASS_A);
      assertSame(poolA, clazzA.getClassPool());
      CtClass clazzB = poolB.get(CLASS_B);
      assertSame(poolB, clazzB.getClassPool());
      assertCannotLoadCtClass(poolA, CLASS_B);
      assertCannotLoadCtClass(poolB, CLASS_A);
   }
   
   public void testChildWithNewClassesInParent() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         scopedPool = createChildClassPool(JAR_B_1, true);
         assertCannotLoadCtClass(scopedPool, CLASS_A);
         
         globalPool = createGlobalClassPool(JAR_A_1);
         CtClass aFromChild = scopedPool.get(CLASS_A);
         assertNotNull(aFromChild);
         CtClass aFromParent = globalPool.get(CLASS_A);
         assertNotNull(aFromParent);
         assertSame(aFromChild, aFromParent);
         assertSame(globalPool, aFromParent.getClassPool());
         
         CtClass aFromChild2 = scopedPool.get(CLASS_A);
         assertSame(aFromChild, aFromChild2);
      }
      finally
      {
         removeClassPool(globalPool);
         removeClassPool(scopedPool);
      }
   }
   
   public void testChildOverrideWithParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         globalPool = createGlobalClassPool(JAR_A_1);
         scopedPool = createChildClassPool(JAR_A_2, true);
         CtClass aFromParent = globalPool.get(CLASS_A);
         assertNotNull(aFromParent);
         CtClass aFromChild = scopedPool.get(CLASS_A);
         assertNotNull(aFromChild);
         assertSame(aFromParent, aFromChild);
         assertSame(globalPool, aFromParent.getClassPool());
      }
      finally
      {
         removeClassPool(globalPool);
         removeClassPool(scopedPool);
      }
   }

   public void testChildOverrideWithNoParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         globalPool = createGlobalClassPool(JAR_A_1);
         scopedPool = createChildClassPool(JAR_A_2, false);
         CtClass aFromParent = globalPool.get(CLASS_A);
         assertNotNull(aFromParent);
         CtClass aFromChild = scopedPool.get(CLASS_A);
         assertNotNull(aFromChild);
         assertNotSame(aFromParent, aFromChild);
         assertSame(globalPool, aFromParent.getClassPool());
         assertSame(scopedPool, aFromChild.getClassPool());
      }
      finally
      {
         removeClassPool(globalPool);
         removeClassPool(scopedPool);
      }
   }
   
   public void testURLChildOfGlobalUclParentFirst() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool childA = null;
      ClassPool childB = null;
      try
      {
         globalPool = createGlobalClassPool(JAR_A_1);
         childA = createChildURLClassPool(globalPool, JAR_B_1);
         
         //Try loading A from child first
         CtClass aFromA = childA.get(CLASS_A);
         assertSame(globalPool, aFromA.getClassPool());
         
         //Try loading A from global
         CtClass aFromGlobal = globalPool.get(CLASS_A);
         assertSame(aFromA, aFromGlobal);
         
         //Try loading B from A
         CtClass bFromA = childA.get(CLASS_B);
         assertSame(childA, bFromA.getClassPool());
         
         //Should get the cached copy from the parent
         childB = createChildURLClassPool(globalPool, JAR_A_2);
         CtClass aFromB = childB.get(CLASS_A);
         assertSame(globalPool, aFromB.getClassPool());
      }
      finally
      {
         removeClassPool(globalPool);
         removeClassPool(childA);
         removeClassPool(childB);
      }
   }   

   
   public void testURLChildOfGlobalUcParentLast() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool childA = null;
      ClassPool childB = null;
      try
      {
         globalPool = createGlobalClassPool(JAR_A_1);
         childA = createChildURLClassPoolParentLast(globalPool, JAR_B_1);
         
         //Try loading A from child first
         CtClass aFromA = childA.get(CLASS_A);
         assertSame(globalPool, aFromA.getClassPool());
         
         //Try loading A from global
         CtClass aFromGlobal = globalPool.get(CLASS_A);
         assertSame(aFromA, aFromGlobal);
         
         //Try loading B from A
         CtClass bFromA = childA.get(CLASS_B);
         assertSame(childA, bFromA.getClassPool());
         
         //Should get the child copy
         childB = createChildURLClassPoolParentLast(globalPool, JAR_A_2);
         CtClass aFromB = childB.get(CLASS_A);
         assertSame(childB, aFromB.getClassPool());
      }
      finally
      {
         removeClassPool(globalPool);
         removeClassPool(childA);
         removeClassPool(childB);
      }
   }   

   public void testURLChildOfGlobalUclParentFirstWithClassInGlobalSibling() throws Exception
   {
      ClassPool globalPoolA = null;
      ClassPool globalPoolB = null;
      ClassPool childC = null;
      ClassPool childB = null;
      try
      {
         globalPoolA = createGlobalClassPool(JAR_A_1);
         globalPoolB = createGlobalClassPool(JAR_B_1);
         childC = createChildURLClassPool(globalPoolA, JAR_C_1);
         
         //Try loading A from child first
         CtClass bFromC = childC.get(CLASS_B);
         assertSame(globalPoolB, bFromC.getClassPool());
         
         //Try loading A from global
         CtClass bFromGlobalA = globalPoolA.get(CLASS_B);
         assertSame(bFromC, bFromGlobalA);
         
         //Try loading B from A
         CtClass cFromC = childC.get(CLASS_C);
         assertSame(childC, cFromC.getClassPool());
         
         //Should get the cached copy from the parent
         childB = createChildURLClassPool(globalPoolA, JAR_B_2);
         CtClass bFromB = childB.get(CLASS_B);
         assertSame(globalPoolB, bFromB.getClassPool());
      }
      finally
      {
         removeClassPool(globalPoolA);
         removeClassPool(globalPoolB);
         removeClassPool(childC);
         removeClassPool(childB);
      }
   }   

   public void testUndeploySibling() throws Exception
   {
      ClassPool poolA = null;
      ClassPool poolB = null;
      try
      {
         try
         {
            poolA = createGlobalClassPool(JAR_A_1);
            assertCannotLoadCtClass(poolA, CLASS_B);
            
            poolB = createGlobalClassPool(JAR_B_1);
            CtClass bFromA = poolA.get(CLASS_B);
            assertSame(poolB, bFromA.getClassPool());
         }
         finally
         {
            removeClassPool(poolB);
         }
         assertCannotLoadCtClass(poolA, CLASS_B);
      }
      finally
      {
         removeClassPool(poolA);
         removeClassPool(poolB);
      }
   }

   
//   public void testUndeployParentDomainClassLoader() throws Exception
//   {
//      ClassLoader globalA = null;
//      ClassLoader globalB = null;
//      ClassLoader child = null;
//      try
//      {
//         try
//         {
//            globalA = createGlobalClassLoader(JAR_A_1);
//            assertCannotLoadClass(globalA, CLASS_B);
//            
//            child = createChildClassLoader(JAR_C_1, true);
//            assertCannotLoadClass(child, CLASS_B);
//            
//            globalB = createGlobalClassLoader(JAR_B_1);
//            Class<?> bFromChild = child.loadClass(CLASS_B);
//            Class<?> bFromA = globalA.loadClass(CLASS_B);
//            assertSame(globalB, bFromA.getClassLoader());
//            assertSame(bFromA, bFromChild);
//         }
//         finally
//         {
//            removeClassLoaderFromRepository(globalB);
//         }
//         assertCannotLoadClass(child, CLASS_B);
//      }
//      finally
//      {
//         removeClassLoaderFromRepository(globalA);
//      }
//   }
   
   public void testUclWithParentClassLoader() throws Exception
   {
      ClassPool parent = null;
      ClassPool global = null;
      try
      {
         parent = createChildURLClassPool(null, JAR_B_1);
         global = createGlobalClassPoolWithParent(JAR_A_1, parent);

         CtClass aFromGlobal = global.get(CLASS_A);
         assertSame(global, aFromGlobal.getClassPool());
         CtClass aFromGlobal2 = global.get(CLASS_A);
         assertSame(aFromGlobal, aFromGlobal2);
         
         CtClass bFromGlobal = global.get(CLASS_B);
         assertSame(parent, bFromGlobal.getClassPool());
         CtClass bFromGlobal2 = global.get(CLASS_B);
         assertSame(bFromGlobal, bFromGlobal2);
         
         CtClass bFromParent = parent.get(CLASS_B);
         assertSame(parent, bFromParent.getClassPool());
         assertSame(bFromGlobal, bFromParent);
         CtClass bFromParent2 = parent.get(CLASS_B);
         assertSame(bFromParent, bFromParent2);
      }
      finally
      {
         removeClassPool(global);
         removeClassPool(parent);
      }
   }

   public void testUclWithParentClassLoaderAndSameClassInDomain() throws Exception
   {
      ClassPool parent = null;
      ClassPool globalA = null;
      ClassPool globalB = null;
      try
      {
         parent = createChildURLClassPool(null, JAR_B_1);
         globalA = createGlobalClassPoolWithParent(JAR_A_1, parent);

         CtClass aFromGlobal = globalA.get(CLASS_A);
         assertSame(globalA, aFromGlobal.getClassPool());
         CtClass aFromGlobal2 = globalA.get(CLASS_A);
         assertSame(aFromGlobal, aFromGlobal2);

         globalB = createGlobalClassPool(JAR_B_2);
         CtClass bFromGlobalA = globalA.get(CLASS_B);
         assertSame(globalB, bFromGlobalA.getClassPool());
         CtClass bFromGlobalA2 = globalA.get(CLASS_B);
         assertSame(bFromGlobalA, bFromGlobalA2);
         
         
         CtClass bFromParent = parent.get(CLASS_B);
         assertSame(parent, bFromParent.getClassPool());
         assertNotSame(bFromGlobalA, bFromParent);
         CtClass bFromParent2 = parent.get(CLASS_B);
         assertSame(bFromParent, bFromParent2);
      }
      finally
      {
         removeClassPool(globalA);
         removeClassPool(globalB);
         removeClassPool(parent);
      }
   }
   
   public void testGeneratingClassInNonDelegatingPool() throws Exception
   {
      ClassPool parent = null;
      ClassPool global = null;
      ClassPool child = null;
      try
      {
         final String PARENT = "parent.Parent";
         final String CHILD = "child.Child";
         parent = createChildURLClassPool(null, JAR_B_1);
         global = createGlobalClassPoolWithParent(JAR_A_1, parent);
         child = createChildURLClassPool(global, JAR_C_1);
         
         assertCannotLoadCtClass(parent, PARENT);
         assertCannotLoadCtClass(global, PARENT);
         assertCannotLoadCtClass(child, PARENT);
         assertCannotLoadCtClass(child, CHILD);
         
         CtClass parentClass = parent.makeClass(PARENT);
         
         CtClass childClass = child.makeClass(CHILD);
         childClass.setSuperclass(parentClass);
         
         CtClass parentFromParent = parent.get(PARENT);
         assertSame(parent, parentFromParent.getClassPool());
         assertSame(parentClass, parentFromParent);
         
         CtClass childFromChild = child.get(CHILD);
         assertSame(child, childFromChild.getClassPool());
         assertSame(childClass, childFromChild);
         
         assertCannotLoadCtClass(global, CHILD);
         
         CtClass parentFromChildA = childClass.getSuperclass();
         assertSame(parentClass, parentFromChildA);
       
         CtClass parentFromChildB = child.get(PARENT);
         assertSame(parentClass, parentFromChildB);
         
         Class<?> parentClazz = parentClass.toClass();
         assertSame(parent.getClassLoader(), parentClazz.getClassLoader());
         
         Class<?> childClazz = childClass.toClass();
         assertSame(child.getClassLoader(), childClazz.getClassLoader());
         
         Class<?> parentClazzFromParent = parent.getClassLoader().loadClass(PARENT);
         assertSame(parentClazz, parentClazzFromParent);
         
         Class<?> parentClazzFromChild = parent.getClassLoader().loadClass(PARENT);
         assertSame(parentClazz, parentClazzFromChild);
         
         Class<?> childClazzFromChild = child.getClassLoader().loadClass(CHILD);
         assertSame(childClazz, childClazzFromChild);
         

         child.getClassLoader().loadClass(CLASS_A);
      }
      finally
      {
         removeClassPool(parent);
         removeClassPool(global);
         removeClassPool(child);
      }
   }
   
   public void testGeneratingClassInDelegatingPool() throws Exception
   {
      ClassPool globalA = null;
      ClassPool globalB = null;
      ClassPool child = null;
      try
      {
         globalA = createGlobalClassPool(JAR_A_1);
         globalB = createGlobalClassPool(JAR_B_1);
         child = createChildURLClassPool(globalA, JAR_C_1);
         
         final String A_CLASS = "a.Clazz";
         final String B_CLASS = "b.Clazz";
         
         assertCannotLoadCtClass(globalA, A_CLASS);
         assertCannotLoadCtClass(globalB, A_CLASS);
         assertCannotLoadCtClass(child, A_CLASS);
         assertCannotLoadCtClass(globalA, B_CLASS);
         assertCannotLoadCtClass(globalB, B_CLASS);
         assertCannotLoadCtClass(child, B_CLASS);
         
         CtClass a = globalA.makeClass(A_CLASS);
         CtClass b = globalB.makeClass(B_CLASS);
         
         CtClass aFromA = globalA.get(A_CLASS);
         assertSame(a, aFromA);
         assertSame(globalA, aFromA.getClassPool());
         CtClass aFromB = globalB.get(A_CLASS);
         assertSame(a, aFromB);
         CtClass bFromA = globalA.get(B_CLASS);
         assertSame(b, bFromA);
         assertSame(globalB, bFromA.getClassPool());
         CtClass bFromB = globalB.get(B_CLASS);
         assertSame(b, bFromB);
         CtClass aFromChild = child.get(A_CLASS);
         assertSame(a, aFromChild);
         CtClass bFromChild = child.get(B_CLASS);
         assertSame(b, bFromChild);
         
         Class<?> clazzA = a.toClass();
         assertSame(globalA.getClassLoader(), clazzA.getClassLoader());
         
         Class<?> clazzB = b.toClass();
         assertSame(globalB.getClassLoader(), clazzB.getClassLoader());
         
         Class<?> clazzAFromA = globalA.getClassLoader().loadClass(A_CLASS);
         assertSame(clazzA, clazzAFromA);
         Class<?> clazzAFromB = globalB.getClassLoader().loadClass(A_CLASS);
         assertSame(clazzA, clazzAFromB);
         Class<?> clazzAFromChild = child.getClassLoader().loadClass(A_CLASS);
         assertSame(clazzA, clazzAFromChild);
         
         Class<?> clazzBFromA = globalA.getClassLoader().loadClass(B_CLASS);
         assertSame(clazzB, clazzBFromA);
         Class<?> clazzBFromB = globalB.getClassLoader().loadClass(B_CLASS);
         assertSame(clazzB, clazzBFromB);
         Class<?> clazzBFromChild = child.getClassLoader().loadClass(B_CLASS);
         assertSame(clazzB, clazzBFromChild);
      }
      finally
      {
         removeClassPool(globalA);
         removeClassPool(globalB);
         removeClassPool(child);
      }
   }
   
   public void testUclLoaderOrdering() throws Exception
   {
      ClassPool globalA = null;
      ClassPool globalB = null;
      ClassPool globalC = null;
      try
      {
         globalA = createGlobalClassPool(JAR_A_1);
         globalB = createGlobalClassPool(JAR_A_1);
         globalC = createGlobalClassPool(JAR_A_1);
         
         CtClass aFromA = globalA.get(CLASS_A);
         CtClass aFromB = globalB.get(CLASS_A);
         CtClass aFromC = globalC.get(CLASS_A);
         assertSame(aFromA, aFromB);
         assertSame(aFromA, aFromC);
         assertSame(globalA, aFromA.getClassPool());
      }
      finally
      {
         removeClassPool(globalA);
         removeClassPool(globalB);
         removeClassPool(globalC);
      }
   }

   public void testMakeContainerProxyInSamePackage() throws Exception
   {
      ClassPool globalPoolA = null;
      ClassPool globalPoolB = null;
      try
      {
         globalPoolA = createGlobalClassPool(JAR_A_1);
         globalPoolB = createGlobalClassPool(JAR_B_1);
         
         ClassLoader loaderA = globalPoolA.getClassLoader();
         ClassLoader loaderB = globalPoolB.getClassLoader();

         Object proxyA = assertMakeContainerProxy(loaderA.loadClass(CLASS_A), loaderA);
         Object proxyB = assertMakeContainerProxy(loaderB.loadClass(CLASS_B), loaderB);
         
         Class<?> clazzA = proxyA.getClass();
         Class<?> clazzB = proxyB.getClass();
         
         assertTrue(AspectManaged.class.isAssignableFrom(clazzA));
         assertTrue(AspectManaged.class.isAssignableFrom(clazzB));
         assertTrue(clazzA.getName().startsWith(PACKAGE_A));
         assertTrue(clazzB.getName().startsWith(PACKAGE_B));
         
         assertSame(clazzA, loaderA.loadClass(clazzA.getName()));
         assertSame(clazzB, loaderA.loadClass(clazzB.getName()));
         assertSame(clazzA, loaderB.loadClass(clazzA.getName()));
         assertSame(clazzB, loaderB.loadClass(clazzB.getName()));
      }
      finally
      {
         removeClassPool(globalPoolA);
         removeClassPool(globalPoolB);
      }
   }
   
   public void testMakeContainerProxyForSystemClass() throws Exception
   {
      ClassPool globalPoolA = null;
      ClassPool globalPoolB = null;
      try
      {
         globalPoolA = createGlobalClassPool(JAR_A_1);
         globalPoolB = createGlobalClassPool(JAR_B_1);
         
         ClassLoader loaderA = globalPoolA.getClassLoader();
         ClassLoader loaderB = globalPoolB.getClassLoader();

         Object proxyA = assertMakeContainerProxy(ArrayList.class, loaderA);
         Object proxyB = assertMakeContainerProxy(ArrayList.class, loaderB);
         
         Class<?> clazzA = proxyA.getClass();
         Class<?> clazzB = proxyB.getClass();
         
         assertTrue(AspectManaged.class.isAssignableFrom(clazzA));
         assertTrue(AspectManaged.class.isAssignableFrom(clazzB));
         assertTrue(clazzA.getName().startsWith(ContainerProxyFactory.PROXY_CLASSES_DEFAULT_PACKAGE));
         assertTrue(clazzB.getName().startsWith(ContainerProxyFactory.PROXY_CLASSES_DEFAULT_PACKAGE));
         
         assertSame(clazzA, loaderA.loadClass(clazzA.getName()));
         assertSame(clazzB, loaderA.loadClass(clazzB.getName()));
         assertSame(clazzA, loaderB.loadClass(clazzA.getName()));
         assertSame(clazzB, loaderB.loadClass(clazzB.getName()));
      }
      finally
      {
         removeClassPool(globalPoolA);
         removeClassPool(globalPoolB);
      }
   }
   
   private Object assertMakeContainerProxy(Class<?> parent, ClassLoader loader) throws Exception
   {
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setProxiedClass(parent);
      params.setTarget(parent.newInstance());
      params.setClassLoader(loader);
      params.setInterfaces(new Class<?>[] {java.io.Serializable.class});
      GeneratedAOPProxyFactory factory = new GeneratedAOPProxyFactory();
      Object proxy = factory.createAdvisedProxy(params);
      assertFalse(parent.getName() == proxy.getClass().getName());
      assertSame(loader, proxy.getClass().getClassLoader());
      return proxy;
   }
}
