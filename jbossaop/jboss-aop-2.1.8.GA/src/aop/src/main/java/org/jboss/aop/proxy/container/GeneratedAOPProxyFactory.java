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

import java.lang.reflect.Constructor;

import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.instrument.Untransformable;
import org.jboss.aop.util.logging.AOPLogger;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 83907 $
 */
public class GeneratedAOPProxyFactory implements AOPProxyFactory
{
   private static final AOPLogger logger = AOPLogger.getLogger(GeneratedAOPProxyFactory.class);
   /**
    * Create a proxy
    *
    * @param <T> the expected type
    * @param target the target
    * @param interfaceClass the interface class
    * @return the proxy
    */
   public static <T> T createProxy(T target, Class<T> interfaceClass)
   {
      if (target == null)
         return null;

      GeneratedAOPProxyFactory proxyFactory = new GeneratedAOPProxyFactory();
      AOPProxyFactoryParameters params = new AOPProxyFactoryParameters();
      params.setInterfaces(new Class[] { interfaceClass });
      params.setObjectAsSuperClass(true);
      params.setTarget(target);
      Object proxy = proxyFactory.createAdvisedProxy(params);
      if( AspectManager.verbose  && logger.isDebugEnabled())
         logger.debug("Created proxy: "+proxy.getClass()+"@"+System.identityHashCode(proxy)+" target: "+target.getClass());
      return interfaceClass.cast(proxy);
   }

   public Object createAdvisedProxy(AOPProxyFactoryParameters params)
   {
      if (params.getTarget() != null)
      {
         if (params.getProxiedClass() != null)
         {
            if (params.getProxiedClass().isAssignableFrom(params.getTarget().getClass()) == false)
            {
               throw new RuntimeException("Specified class type " + params.getProxiedClass().getName() + " and target " + params.getTarget().getClass().getName() + " are not compatible");
            }
         }
         else
         {
            params.setProxiedClass(params.getTarget().getClass());
         }
      }
      else if (params.getProxiedClass() == null)
      {
         params.setProxiedClass(Object.class);
      }

      return getProxy(params);
   }

   private Object getProxy(AOPProxyFactoryParameters params)
   {
      try
      {
         Class<?> proxyClass = null;

         boolean isAdvised = Advised.class.isAssignableFrom(params.getProxiedClass());

         if (params.getTarget() instanceof Untransformable || (isAdvised && params.getInterfaces() == null && params.getMixins() == null && (params.getMetaData() == null || !params.getMetaDataHasInstanceLevelData()) && params.getSimpleMetaData() == null))
         {
            return params.getTarget();
         }


         synchronized (ContainerCache.mapLock)
         {
            ContainerCache cache = params.getContainerCache();
            if (cache == null)
            {
               cache = ContainerCache.initialise(AspectManager.instance(), params); 
               params.setContainerCache(cache); 
            }

            boolean hasMoreAdvicesOrIntroductionsForInstance = cache.hasMoreAdvicesOrIntroductionsForInstance();
            
            if (!cache.hasAspects() && !cache.hasIntroductionsForClassAdvisor() && !hasMoreAdvicesOrIntroductionsForInstance)
            {
               return params.getTarget();
            }
            else
            {
               boolean needsProxy = false;
               if (isAdvised && hasMoreAdvicesOrIntroductionsForInstance)
               {
                  needsProxy = true;
               }
               else
               {
                  needsProxy = true;
               }
               if (needsProxy)
               {
                  proxyClass = generateProxy(params);
               }
            }
         }

         return instantiateAndConfigureProxy(proxyClass, params);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   private Class<?> generateProxy(AOPProxyFactoryParameters params) throws Exception
   {
      Class<?> proxyClass = ContainerProxyFactory.getProxyClass(params.isObjectAsSuperClass(), params.getClassLoader(), params.getContainerCache().getKey(), params.getContainerCache().getAdvisor());

      return proxyClass;
   }

   private Object instantiateAndConfigureProxy(Class<?> proxyClass, AOPProxyFactoryParameters params) throws Exception
   {
      AspectManaged proxy;
      if (params.getCtor() != null)
      {
         Constructor<?> ctor = proxyClass.getConstructor(params.getCtor().getSignature());
         proxy = (AspectManaged)ctor.newInstance(params.getCtor().getArguments());
      }
      else
      {
         proxy = (AspectManaged)proxyClass.newInstance();
      }

      proxy.setAdvisor(params.getContainerCache().getClassAdvisor());

      if (params.getContainerCache().getInstanceContainer() != null)
      {
         proxy.setInstanceAdvisor(params.getContainerCache().getInstanceContainer());
      }

      if (params.getSimpleMetaData() != null)
      {
         proxy.setMetadata(params.getSimpleMetaData());
      }

      if (params.getTarget() != null)
      {
         ((Delegate)proxy).setDelegate(params.getTarget());
      }
      else
      {
         ((Delegate)proxy).setDelegate(new Object());
      }
      ((Delegate)proxy).setContainerProxyCacheKey(params.getContainerCache().getKey());

      return proxy;
   }
}
