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

import org.jboss.aop.metadata.SimpleMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 83907 $
 */
public class AOPProxyFactoryParameters
{
   private Class<?> proxiedClass;
   private Object target;
   private MetaData metaData;
   private boolean metaDataHasInstanceLevelData;
   private Class<?>[] interfaces;
   private AOPProxyFactoryMixin[] mixins; 
   private boolean objectAsSuperClass;
   private SimpleMetaData simpleMetaData;
   private ContainerCache containerCache;
   private Ctor ctor;
   private ClassLoader classLoader;
   
   public AOPProxyFactoryParameters()
   {
   }

   /**
    * This constructor cannot be changed for backwards compatibility
    */
   public AOPProxyFactoryParameters(
         Class<?> proxiedClass, 
         Object target, 
         Class<?>[] interfaces,
         AOPProxyFactoryMixin[] mixins,
         MetaData metaData, 
         boolean metaDataHasInstanceLevelData,
         boolean objectAsSuperClass,
         SimpleMetaData simpleMetaData,
         ContainerCache containerCache,
         Class<?>[] ctorSignature,
         Object[] ctorArguments)
   {
      this.proxiedClass = proxiedClass;
      this.target = target;
      this.interfaces = interfaces;
      this.mixins = mixins;
      this.metaData = metaData;
      this.metaDataHasInstanceLevelData = metaDataHasInstanceLevelData;
      this.objectAsSuperClass = objectAsSuperClass;
      this.simpleMetaData = simpleMetaData;
      this.containerCache = containerCache;
      setCtor(ctorSignature, ctorArguments);      
   }

   public Class<?>[] getInterfaces()
   {
      return interfaces;
   }

   public void setInterfaces(Class<?>[] interfaces)
   {
      this.interfaces = interfaces;
   }

   public MetaData getMetaData()
   {
      return metaData;
   }

   public void setMetaData(MetaData metaData)
   {
      this.metaData = metaData;
   }

   public boolean isObjectAsSuperClass()
   {
      return objectAsSuperClass;
   }

   public void setObjectAsSuperClass(boolean objectAsSuperClass)
   {
      this.objectAsSuperClass = objectAsSuperClass;
   }

   public Class<?> getProxiedClass()
   {
      return proxiedClass;
   }

   public void setProxiedClass(Class<?> proxiedClass)
   {
      this.proxiedClass = proxiedClass;
   }

   public SimpleMetaData getSimpleMetaData()
   {
      return simpleMetaData;
   }

   public void setSimpleMetaData(SimpleMetaData simpleMetaData)
   {
      this.simpleMetaData = simpleMetaData;
   }

   public Object getTarget()
   {
      return target;
   }

   public void setTarget(Object target)
   {
      this.target = target;
   }
   
   public AOPProxyFactoryMixin[] getMixins()
   {
      return mixins;
   }

   public void setMixins(AOPProxyFactoryMixin[] mixins)
   {
      this.mixins = mixins;
   }

   public ContainerCache getContainerCache()
   {
      return containerCache;
   }

   public void setContainerCache(ContainerCache containerCache)
   {
      this.containerCache = containerCache;
   }

   public Ctor getCtor()
   {
      return ctor;
   }

   public boolean getMetaDataHasInstanceLevelData()
   {
      return metaDataHasInstanceLevelData;
   }

   public void setMetaDataHasInstanceLevelData(boolean metaDataHasInstanceLevelData)
   {
      this.metaDataHasInstanceLevelData = metaDataHasInstanceLevelData;
   }

   public void setCtor(Class<?>[] ctorSignature, Object[] ctorArguments)
   {
      boolean haveSig = (ctorSignature != null && ctorSignature.length > 0);
      boolean haveArgs = (ctorArguments != null && ctorArguments.length > 0);
      
      if (haveSig && haveArgs)
      {
         ctor = new Ctor(ctorSignature, ctorArguments);
      }
      else if (!haveSig && !haveArgs)
      {
         ctor = null;
      }
      else
      {
         throw new RuntimeException("If specifying either constructor arguments or signature, you must specify the other");
      }
   }

   public ClassLoader getClassLoader()
   {
      return classLoader;
   }

   public void setClassLoader(ClassLoader classLoader)
   {
      this.classLoader = classLoader;
   }

   public static class Ctor
   {
      Class<?>[] signature;
      Object[] arguments;
      
      public Ctor(Class<?>[] signature, Object[] arguments)
      {
         this.signature = signature;
         this.arguments = arguments;
      }

      public Object[] getArguments()
      {
         return arguments;
      }

      public Class<?>[] getSignature()
      {
         return signature;
      }
   }
}
