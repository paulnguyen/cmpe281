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

import java.util.HashMap;
import java.util.WeakHashMap;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.metadata.SimpleMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 75539 $
 */
public class ContainerCache
{
   private static volatile int counter;
   public static final Object mapLock = new Object();
   private static WeakHashMap<Class<?>, HashMap<String, ClassProxyContainer>> containerCache = new WeakHashMap<Class<?>, HashMap<String, ClassProxyContainer>>();

   private AspectManager manager;
   private ContainerProxyCacheKey key;
   /** This will be ClassAdvisor if class has been woven i.e implements Advised, or ClassProxyContainer*/
   private Advisor classAdvisor;
   private InstanceProxyContainer instanceContainer;
   boolean isClassProxyContainer;
   Class<?>[] interfaces;
   AOPProxyFactoryMixin[] mixins;

   MetaData metaData;
   boolean metaDataHasInstanceLevelData;
   SimpleMetaData simpleMetaData;

   private ContainerCache(AspectManager manager, Class<?> proxiedClass, Class<?>[] interfaces, AOPProxyFactoryMixin[] mixins, MetaData metaData, boolean metaDataHasInstanceLevelData, SimpleMetaData simpleMetaData)
   {
      this.manager = manager;
      this.interfaces = interfaces;
      this.mixins = mixins;
      this.metaData = metaData;
      this.simpleMetaData = simpleMetaData;
      this.metaDataHasInstanceLevelData = metaDataHasInstanceLevelData;
      key = new ContainerProxyCacheKey(manager.getManagerFQN(), proxiedClass, interfaces, mixins, metaData);
   }

   public static ContainerCache initialise(AspectManager manager, Class<?> proxiedClass, MetaData metaData, boolean metaDataHasInstanceLevelData)
   {
      return initialise(manager, proxiedClass, null, null, metaData, metaDataHasInstanceLevelData, null);
   }

   public static ContainerCache initialise(AspectManager manager, AOPProxyFactoryParameters params)
   {
      return initialise(
            manager,
            params.getProxiedClass(),
            params.getInterfaces(),
            params.getMixins(),
            params.getMetaData(),
            params.getMetaDataHasInstanceLevelData(),
            params.getSimpleMetaData());
   }

   private static ContainerCache initialise(AspectManager manager, Class<?> proxiedClass, Class<?>[] interfaces, AOPProxyFactoryMixin[] mixins, MetaData metaData, boolean metaDataHasInstanceLevelData, SimpleMetaData simpleMetaData)
   {
      ContainerCache factory = new ContainerCache(manager, proxiedClass, interfaces, mixins, metaData, metaDataHasInstanceLevelData, simpleMetaData);
      synchronized (mapLock)
      {
         factory.initClassContainer();
         factory.initInstanceContainer();
      }

      return factory;
   }

   public ContainerProxyCacheKey getKey()
   {
      return key;
   }

   public Advisor getAdvisor()
   {
      return (instanceContainer != null) ? instanceContainer : classAdvisor;
   }

   public Advisor getClassAdvisor()
   {
      return classAdvisor;
   }

   public InstanceProxyContainer getInstanceContainer()
   {
      return instanceContainer;
   }

   public boolean hasAspects()
   {
      if (instanceContainer != null)
      {
         return instanceContainer.hasAspects();
      }
      return classAdvisor.hasAspects();
   }
   
   public boolean hasIntroductionsForClassAdvisor()
   {
      return classAdvisor.getInterfaceIntroductions().size() > 0; 
   }

   public boolean requiresInstanceAdvisor()
   {
      return hasInterfaceIntroductions() || hasMixins() || (metaData!= null && metaDataHasInstanceLevelData) || simpleMetaData != null;
   }

   public boolean isAdvised()
   {
      return Advised.class.isAssignableFrom(key.getClazz());
   }

   private boolean hasInterfaceIntroductions()
   {
      return interfaces != null && interfaces.length > 0;
   }

   private boolean hasMixins()
   {
      return mixins != null && mixins.length > 0;
   }

   private void initClassContainer()
   {
      if (Advised.class.isAssignableFrom(key.getClazz()))
      {
         classAdvisor = AspectManager.instance().getAdvisor(key.getClazz());
      }
      else
      {
         classAdvisor = getCachedContainer(manager);
         if (classAdvisor == null)
         {
            classAdvisor = createAndCacheContainer();
         }
      }
   }

   public static ClassProxyContainer getCachedContainer(ContainerProxyCacheKey key)
   {
      HashMap<String, ClassProxyContainer> managerContainers = containerCache.get(key.getClazz());
      if (managerContainers != null)
      {
         return managerContainers.get(key.getManagerFQN());
      }
      return null;
   }
   
   private ClassProxyContainer getCachedContainer(AspectManager manager)
   {
      HashMap<String, ClassProxyContainer> managerContainers = containerCache.get(key.getClazz());
      if (managerContainers != null)
      {
         return managerContainers.get(manager.getManagerFQN());
      }
      return null;
   }

   private ClassProxyContainer createAndCacheContainer()
   {
      ClassProxyContainer container = createContainer();
      cacheContainer(key, container);
      return container;
   }

   private ClassProxyContainer createContainer()
   {
      String name = Domain.getDomainName(key.getClazz(), false);
      ProxyAdvisorDomain domain = new ProxyAdvisorDomain(manager, name, key.getClazz(), false);
      String classname = (key.getClazz() != null) ? key.getClazz().getName() : "AOP$Hollow";
      ClassProxyContainer container = new ClassProxyContainer(classname /*+ " ClassProxy" + (counter++)*/, domain);
      domain.setAdvisor(container);
      container.initialise(key.getClazz());

      return container;
   }

   private void cacheContainer(ContainerProxyCacheKey key, ClassProxyContainer container)
   {
      HashMap<String, ClassProxyContainer> managerContainers = containerCache.get(key.getClazz());
      if (managerContainers == null)
      {
         managerContainers = new HashMap<String, ClassProxyContainer>();
         containerCache.put(key.getClazz(), managerContainers);
      }
      managerContainers.put(key.getManagerFQN(), container);
   }

   private InterfaceIntroduction getInterfaceIntroduction()
   {
      int introductionLength = (hasInterfaceIntroductions()) ? interfaces.length : 0;
      int mixinLength = (hasMixins()) ? mixins.length : 0;

      if (introductionLength == 0 && mixinLength == 0)
      {
         return null;
      }

      Class<?> proxiedClass = classAdvisor.getClazz();
      if (proxiedClass == null)
      {
         proxiedClass = Object.class;
      }
      String[] introducedNames = getClassNames(interfaces);
      InterfaceIntroduction intro = new InterfaceIntroduction("Introduction" + counter++, proxiedClass.getName(), introducedNames);

      if (mixinLength > 0)
      {
         addMixins(intro);
      }

      return intro;
   }

   private void addMixins(InterfaceIntroduction intro)
   {
      for (int i = 0 ; i < mixins.length && mixins != null; i++)
      {
         Class<?>[] mixinInterfaces = mixins[i].getInterfaces();
         Class<?> mixinClass = mixins[i].getMixin();

         if (mixinInterfaces == null)
         {
            throw new RuntimeException("When defining a mixin, interfaces must be defined");
         }
         if (mixinClass == null)
         {
            throw new RuntimeException("When defining a mixin, the mixin must be defined");
         }

         String[] mixinInterfaceNames = getClassNames(mixinInterfaces);
         InterfaceIntroduction.Mixin mixin = new InterfaceIntroduction.Mixin(mixinClass.getName(), mixinInterfaceNames, mixins[i].getConstruction(), false);
         intro.addMixin(mixin);
      }
   }

   private String[] getClassNames(Class<?>[] classes)
   {
      if (classes == null)
      {
         return null;
      }

      String[] names = new String[classes.length];
      for (int i = 0 ; i < classes.length ; i++)
      {
         names[i] = classes[i].getName();
      }
      return names;
   }

   private void initInstanceContainer()
   {
      if (requiresInstanceAdvisor())
      {
         InterfaceIntroduction introduction = null;
         if (hasInterfaceIntroductions() || hasMixins())
         {
            introduction = getInterfaceIntroduction();
         }

         instanceContainer = InstanceProxyContainer.createInstanceProxyContainer(classAdvisor, introduction, metaData);
      }
   }
   
   public boolean hasMoreAdvicesOrIntroductionsForInstance()
   {
      if (classAdvisor == null)
      {
         throw new IllegalStateException("initialise() must be called first");
      }
      
      if (instanceContainer == null)
      {
         return false;
      }
      
      if (!classAdvisor.hasSameMethodAspectLength(instanceContainer))
      {
         return true;
      }
      
      if (!classAdvisor.hasSameConstructorAspectLength(instanceContainer))
      {
         return true;
      }
      
      if (instanceContainer.getInterfaceIntroductions().size() > classAdvisor.getInterfaceIntroductions().size())
      {
         return true;
      }
      return false;
   }
   
}




