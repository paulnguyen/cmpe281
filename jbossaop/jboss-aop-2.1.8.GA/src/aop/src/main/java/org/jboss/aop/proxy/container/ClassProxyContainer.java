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
package org.jboss.aop.proxy.container;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javassist.runtime.Inner;

import org.jboss.aop.Advisor;
import org.jboss.aop.ClassContainer;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.util.ConstructorComparator;
import org.jboss.aop.util.MethodHashing;

/**
 * Extension of ClassContainer needed because of Mixins
 * we want to be able to match pointcut expressions on the base class of the delegate
 * we also want to be able to match pointcuts of instanceof{} of the Mixin interfaces.
 *
 * We also want to create constructor tables based on the constructor of the delegate so we can intercept
 * construction
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class ClassProxyContainer extends ClassContainer
{
   public ClassProxyContainer(String name, AspectManager manager)
   {
      super(name, manager);
   }

   protected void createConstructorTables()
   {
      Class<?> useClass = clazz;
      if (clazz.getName().startsWith(ContainerProxyFactory.PROXY_NAME_PREFIX) && clazz != null && clazz.getSuperclass() != null)
      {
         useClass = clazz.getSuperclass();
      }
      if (useClass != null)
      {
         final Class<?> theUseClass = useClass;
         AccessController.doPrivileged(new PrivilegedAction<Object>()
         {
            public Object run()
            {
               constructors = theUseClass.getDeclaredConstructors();
               // remove javassist generated constructor for inner class
               if (clazz.isMemberClass())
               {
                  for (int i = 0; i < constructors.length; i++)
                  {
                     if (constructors[i].getParameterTypes().length == 1 &&
                        constructors[i].getParameterTypes()[0].getName().
                        equals(Inner.class.getName()))
                     {
                        Constructor<?>[] newConstructors = new Constructor<?>[constructors.length - 1];
                        System.arraycopy(constructors, 0, newConstructors, 0, i);
                        System.arraycopy(constructors, i+1, newConstructors, i, constructors.length -i - 1);
                        constructors = newConstructors;
                        break;
                     }
                  }
               }
               for (int i = 0; i < constructors.length; i++)
               {
                  constructors[i].setAccessible(true);
               }
               return null;
            }
         });
         Arrays.sort(constructors, ConstructorComparator.INSTANCE);
      }
   }


   protected void createMethodMap()
   {
      initAdvisedMethodsMap();
      try
      {
         Method[] declaredMethods = clazz.getMethods();

         Class<?> superclass = clazz.getSuperclass();
         for (int i = 0; i < declaredMethods.length; i++)
         {
            Method method = declaredMethods[i];
            if (ClassAdvisor.isAdvisable(method))
            {
               long hash = MethodHashing.methodHash(method);
               try
               {
                  if (method.getDeclaringClass().getName().indexOf(ContainerProxyFactory.PROXY_NAME_PREFIX) >= 0 && superclass != null)
                     method = superclass.getMethod(method.getName(), method.getParameterTypes());
               }
               catch (NoSuchMethodException ignored)
               {
                  // this is a mixin method or a proxy method
               }
               advisedMethods.put(hash, method);
            }
         }

         for (int i = 0; i < interfaceIntroductions.size(); ++i)
         {
            InterfaceIntroduction ii = interfaceIntroductions.get(i);
            String[] intf = ii.getInterfaces();
            addMethodsFromInterfaces(intf);

            ArrayList<InterfaceIntroduction.Mixin> mixins = ii.getMixins();
            if (mixins.size() > 0)
            {
               for (InterfaceIntroduction.Mixin mixin : mixins)
               {
                  String[] mintf = mixin.getInterfaces();
                  addMethodsFromInterfaces(mintf);
               }
            }
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   private void addMethodsFromInterfaces(String[] intf) throws Exception
   {
      ClassLoader cl = SecurityActions.getClassLoader(clazz);
      if (cl == null)
      {
         //Fall back to context classloader
         cl = SecurityActions.getContextClassLoader();
      }
      for (int j = 0; intf != null && j < intf.length; ++j)
      {
         Class<?> iface = cl.loadClass(intf[j]);
         Method[] ifaceMethods = iface.getMethods();
         for (int k = 0; k < ifaceMethods.length; k++)
         {
            long hash = MethodHashing.methodHash(ifaceMethods[k]);

            if (advisedMethods.get(hash) == null)
            {
               advisedMethods.put(hash, ifaceMethods[k]);
            }
         }
      }
   }

   public InstanceProxyContainer createInstanceProxyContainer()
   {
      //This seems to get thrown away with every invocation
      ProxyAdvisorDomain domain = new ProxyAdvisorDomain(manager, null, clazz, false);
      domain.setInheritsBindings(true);
      domain.setInheritsDeclarations(true);

      InstanceProxyContainer ia = new InstanceProxyContainer(super.getName(), domain, this, null);

      return ia;
   }

   @SuppressWarnings("deprecation")
   public void initialise(Class<?> proxiedClass)
   {
      
      setClass(proxiedClass);
      ((ProxyAdvisorDomain)manager).attachAdvisor();
      initializeInterfaceIntroductions(proxiedClass);
      super.initializeClassContainer();
   }

   protected Advisor getParentAdvisor()
   {
      return null;
   }

   @Override
   public void addPerClassAspect(AspectDefinition def)
   {
      Advisor parentAdvisor = getParentAdvisor();
      if (parentAdvisor != null)
      {
         parentAdvisor.addPerClassAspect(def);
         return;
      }
      super.addPerClassAspect(def);
   }

   /**
    * If this is an instance advisor, will check with parent advisor if the aspect
    * is already registered. If so, we should use the one from the parent advisor
    */
   @Override
   public Object getPerClassAspect(AspectDefinition def)
   {
      Advisor parentAdvisor = getParentAdvisor();

      if (parentAdvisor != null)
      {
         Object aspect = parentAdvisor.getPerClassAspect(def);
         if (aspect != null) return aspect;
      }

      return super.getPerClassAspect(def);
   }

   /**
    * @return true always
    * @see Advisor#chainOverridingForInheritedMethods()
    */
   @Override
   public boolean chainOverridingForInheritedMethods()
   {
      return true;
   }

   public MethodInfo[] getMethodInfos()
   {
      long[] keys = methodInfos.keys();
      MethodInfo[] minfos = new MethodInfo[keys.length];
      for (int i = 0 ; i < keys.length ; i++)
      {
         minfos[i] = methodInfos.getMethodInfo(keys[i]);
         
      }
      return minfos;
   }
}
