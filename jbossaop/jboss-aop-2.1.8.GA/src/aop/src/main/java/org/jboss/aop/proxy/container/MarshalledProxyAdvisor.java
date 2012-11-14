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
package org.jboss.aop.proxy.container;

import gnu.trove.TLongObjectHashMap;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * The advisor used by a container proxy that is unmarshalled in a remote jvm
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class MarshalledProxyAdvisor extends Advisor implements InstanceAdvisor
{
   TLongObjectHashMap methodInfos = new TLongObjectHashMap();
   Object instance;
   
   public MarshalledProxyAdvisor(String name, AspectManager manager)
   {
      super(name, manager);
   }

   public void setClazz(Class<?> clazz)
   {
      super.clazz = clazz;
   }
   
   public void addMethodInfo(MethodInfo info)
   {
      methodInfos.put(info.getHash(), info);
   }
   
   public void setInstance(Object instance)
   {
      this.instance = instance;
   }
   
   @Override
   public void addClassMetaData(ClassMetaDataBinding data)
   {
      throw new RuntimeException("Not yet implemented");
   }

   @Override
   protected void rebuildInterceptors()
   {
      //Noop, all interceptors are added via addMethodInfo
   }
   
   @Override
   protected void rebuildInterceptorsForAddedBinding(AdviceBinding binding)
   {
      //Noop, all interceptors are added via addMethodInfo
   }
   
   @Override
   protected  void rebuildInterceptorsForRemovedBinding(AdviceBinding removedBinding)
   {
      //Noop, all interceptors are added via addMethodInfo
   }
   
   @Override
   public void removeClassMetaData(ClassMetaDataBinding data)
   {
      throw new RuntimeException("Not yet implemented");
   }
   
   @Override
   public MethodInfo getMethodInfo(long hash)
   {
      MethodInfo info = (MethodInfo)methodInfos.get(hash);
      return info;
   }
   
   public MethodInfo[] getMethodInfos()
   {
      Object[] vals = methodInfos.getValues();
      MethodInfo[] infos = new MethodInfo[vals.length];
      System.arraycopy(vals, 0, infos, 0, vals.length);
      return infos;
   }

   public void appendInterceptor(Interceptor interceptor)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void appendInterceptor(int index, Interceptor interceptor) throws IndexOutOfBoundsException
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void appendInterceptorStack(String stackName)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public Domain getDomain()
   {
      return null;
   }

   public Object getInstance()
   {
      return instance;
   }

   public Interceptor[] getInterceptors()
   {
      throw new RuntimeException("Not yet implemented");
   }

   public Interceptor[] getInterceptors(Interceptor[] baseChain)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public SimpleMetaData getMetaData()
   {
      return null;
   }

   public Object getPerInstanceAspect(String aspectName)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public Object getPerInstanceAspect(AspectDefinition def)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public Object getPerInstanceJoinpointAspect(Joinpoint joinpoint, AspectDefinition def)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public boolean hasInterceptors()
   {
      return false;
   }

   public void insertInterceptor(Interceptor interceptor)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void insertInterceptor(int index, Interceptor interceptor) throws IndexOutOfBoundsException
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void insertInterceptorStack(String stackName)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void removeInterceptor(String name)
   {
      throw new RuntimeException("Not yet implemented");
   }

   public void removeInterceptorStack(String name)
   {
      throw new RuntimeException("Not yet implemented");
   }
}
