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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AbstractAdvice;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.PerInstanceAdvice;
import org.jboss.aop.advice.PerInstanceInterceptor;
import org.jboss.aop.advice.PerJoinpointAdvice;
import org.jboss.aop.advice.PerJoinpointInterceptor;
import org.jboss.aop.instrument.Untransformable;
import org.jboss.aop.metadata.SimpleMetaData;
import org.jboss.aop.util.MethodHashing;
import org.jboss.util.id.GUID;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class MarshalledContainerProxy implements Serializable
{
   private static final long serialVersionUID = 1L;

   //Fields to check if we are unmarshalling in the same JVM
   public final static GUID GUID = new GUID();
   
   private transient AspectManaged proxyInstance;
   
   //Fields from the proxy, used when unmarshalling in the same JVM
   private String proxyClassName;
   private ContainerProxyCacheKey key;
   private Object mixins[];
   private Object delegate;
   private Class<?> clazz;
   private SimpleMetaData metadata;
   
   //Interfaces resulting from a mixin or interface introduction
   private String[] introducedInterfaces;
   
   //Interfaces implemented by mixins that are already implemented by the target class
   private Set<String> targetInterfaces = new HashSet<String>();
   
   //The interceptor chains for each method used when unmarshalling in a different JVM
   MarshalledInterceptors marshalledInterceptors;

   //The instanceAdvisor domain name used when unmarshalling in a different JVM
   private String instanceAdvisorDomainName;
   
   public MarshalledContainerProxy(AspectManaged proxyInstance, ContainerProxyCacheKey key, Object[] mixins, Object delegate, Advisor currentAdvisor, SimpleMetaData metadata)
   {
      this.proxyInstance = proxyInstance;
      Class<?> proxyClass = proxyInstance.getClass();
      this.proxyClassName = proxyClass.getName();
      this.key = key;
      this.mixins = mixins;
      this.delegate = delegate;
      this.clazz = currentAdvisor.getClazz();

      checkInstanceAdvisor(currentAdvisor);
      this.metadata = metadata;

      marshalledInterceptors = new MarshalledInterceptors(currentAdvisor, mixins);
      
      Class<?>[] proxyInterfaces = proxyClass.getInterfaces();
      ArrayList<String> ifs = new ArrayList<String>();
      for (int i = 0 ; i < proxyInterfaces.length ; i++)
      {
         String name = proxyInterfaces[i].getName();
         if (name.equals(Untransformable.class.getName()) || 
               name.equals(Delegate.class.getName()) ||
               name.equals(AspectManaged.class.getName()))
         {
            continue;
         }
         if (proxyInterfaces[i].isAssignableFrom(clazz))
         {
            targetInterfaces.add(clazz.getName());
            continue;
         }
         ifs.add(proxyInterfaces[i].getName());
      }
      introducedInterfaces = ifs.toArray(new String[ifs.size()]);
   }
   
   private void checkInstanceAdvisor(Advisor advisor)
   {
      if (advisor instanceof InstanceProxyContainer)
      {
         AspectManager manager = advisor.getManager();
         instanceAdvisorDomainName = manager.getManagerFQN();
      }
   }
   
   public Object readResolve() throws ObjectStreamException
   {
      try
      {
         if (isLocal())
         {
            return localReadResolve();
         }
         return remoteReadResolve();
      }
      catch (Exception e)
      {
         InvalidObjectException ex = new InvalidObjectException(e.getMessage());
         ex.setStackTrace(e.getStackTrace());
         ex.initCause(e);
         throw ex;
      }
   }   
   
   private Object localReadResolve() throws Exception
   {
      ClassLoader tcl = SecurityActions.getContextClassLoader();
      Class<?> proxyClass = tcl.loadClass(proxyClassName);
      Object proxy = proxyClass.newInstance();
      Delegate delegate = (Delegate)proxy;
      delegate.localUnmarshal(this);
      return proxy;
   }
   
   private Object remoteReadResolve() throws Exception
   {
      if (marshalledInterceptors.getException() != null)
      {
         throw new Exception("Could not read object, an error happened when writing it on the server", marshalledInterceptors.getException());
      }
      
      MarshalledProxyAdvisor advisor = marshalledInterceptors.getMarshalledAdvisor();
      advisor.setClazz(clazz);

      boolean objectAsSuper = key.getClazz().equals(Object.class);
      Class<?> proxyClass = ContainerProxyFactory.getProxyClass(objectAsSuper, key, advisor, this);
   
      Delegate proxy = (Delegate)proxyClass.newInstance();
      proxy.remoteUnmarshal(this, advisor);
      return proxy;
   }
   
   private boolean isLocal()
   {
      return key.getGuid().equals(GUID);
   }

   public ContainerProxyCacheKey getKey()
   {
      return key;
   }
   
   public Object[] getMixins()
   {
      return mixins;
   }

   public Object getDelegate()
   {
      return delegate;
   }

   public Class<?> getClazz()
   {
      return clazz;
   }

   public SimpleMetaData getMetadata()
   {
      return metadata;
   }

   public String getInstanceAdvisorDomainName()
   {
      return instanceAdvisorDomainName;
   }

   protected String[] getIntroducedInterfaces()
   {
      return introducedInterfaces;
   }

   protected Set<String> getTargetInterfaces()
   {
      return targetInterfaces;
   }
   
   private class MarshalledInterceptors implements Serializable
   {
      private static final long serialVersionUID = 1L;
      transient Advisor currentAdvisor;
      Object[] mixins;
      private Exception exception;

      public MarshalledInterceptors(Advisor currentAdvisor, Object[] mixins)
      {
         this.currentAdvisor = currentAdvisor;
         this.mixins = mixins;
      }
      
      public Exception getException()
      {
         return exception;
      }
      
      public MarshalledProxyAdvisor getMarshalledAdvisor()
      {
         return (MarshalledProxyAdvisor)currentAdvisor;
      }
      
      private void writeObject(ObjectOutputStream out) throws IOException
      {
         ObjectOutputStream test = new ObjectOutputStream(new ByteArrayOutputStream());
         try
         {
            MethodInfo[] methodInfos = getMethodInfos();
            MarshalledMethodInfo[] marshalledInfos = new MarshalledMethodInfo[methodInfos.length];

            for (int i = 0 ; i < methodInfos.length ; i++)
            {
               MarshalledMethodInfo info = new MarshalledMethodInfo(MarshalledContainerProxy.this, methodInfos[i]);

               marshalledInfos[i] = info;
               try
               {
                  test.writeObject(info);
                  continue;
               }
               catch (Exception e)
               {
                  IOException ex = new IOException("An error happened serializing the info for " + getExceptionExpression(methodInfos[i]));
                  ex.initCause(e);
                  out.writeObject(ex);
                  return;
               }
            }
            out.writeObject(marshalledInfos);
         }
         finally
         {
            try
            {
               test.close();
            }
            catch (Exception e)
            {
            }
         }
      }
      
      private MethodInfo[] getMethodInfos()
      {
         if (currentAdvisor instanceof MarshalledProxyAdvisor)
         {
            return ((MarshalledProxyAdvisor)currentAdvisor).getMethodInfos();
         }
         return ((ClassProxyContainer)currentAdvisor).getMethodInfos();
      }
      
      private String getExceptionExpression(MethodInfo info)
      {
         Method m = info.getMethod();
         StringBuilder expr = new StringBuilder();
         if (m == null)
         {
            expr.append("a method");
         }
         else
         {
            expr.append(m.getDeclaringClass().getName());
            expr.append(".");
            expr.append(m.getName());
            expr.append("(");
            boolean first = true;
            for (Class<?> c : m.getParameterTypes())
            {
               if (first)
               {
                  first = false;
               }
               else
               {
                  expr.append(", ");
               }
               expr.append(c.getName());
            }
            expr.append(")");
         }
         return expr.toString();
      }
      
      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
      {
          Object o = in.readObject();
          if (o instanceof IOException)
          {
             exception = (IOException)o;
             return;
          }
          
          MarshalledProxyAdvisor advisor = new MarshalledProxyAdvisor("Unmarshalled", AspectManager.getTopLevelAspectManager());
          MarshalledMethodInfo[] marshalledInfos = (MarshalledMethodInfo[])o;
          MethodInfo[] methodInfos = new MethodInfo[marshalledInfos.length];
          for (int i = 0 ; i < marshalledInfos.length ; i++)
          {
             methodInfos[i] = marshalledInfos[i].getMethodInfo(advisor);
             advisor.addMethodInfo(methodInfos[i]);
          }
          currentAdvisor = advisor;
      }
   }
      
      
   private static class MarshalledMethodInfo implements Serializable
   {
      private static final long serialVersionUID = 1L;
      transient MarshalledContainerProxy proxy;
      long advisedHash;
      long unadvisedHash;
      Interceptor[] interceptors;
      Class<?> clazz;
      transient boolean requiresInstanceAdvisor;
      
      public MarshalledMethodInfo(MarshalledContainerProxy proxy, MethodInfo info) throws IOException
      {
         this.proxy = proxy;
         try
         {
            this.advisedHash = MethodHashing.methodHash(info.getMethod());
            this.unadvisedHash = MethodHashing.methodHash(info.getUnadvisedMethod());
         }
         catch (Exception e)
         {
            throw new MethodHashingException(e);
         }
         clazz = info.getMethod().getDeclaringClass();
         populateInterceptors(info);
      }
      
      public boolean getRequiresInstanceAdvisor()
      {
         return requiresInstanceAdvisor;
      }
      
      private void populateInterceptors(MethodInfo info)
      {
         Interceptor[] icptrs = info.getInterceptors();
         if (icptrs != null)
         {
            ArrayList<Interceptor> allIcptrs = new ArrayList<Interceptor>(icptrs.length);
            
            for (int i = 0 ; i < icptrs.length ; i++)
            {
               if (icptrs[i] instanceof AbstractAdvice == false)
               {
                  Interceptor icptr = null;
                  if (icptrs[i] instanceof PerInstanceInterceptor && !Modifier.isStatic(info.getMethod().getModifiers()))
                  {
                     requiresInstanceAdvisor = true;
                     InstanceAdvisor ia = getProxyInstanceAdvisor();
                     icptr = ((PerInstanceInterceptor)icptrs[i]).getAspectInstance(ia);
                  }
                  else if (icptrs[i] instanceof PerJoinpointInterceptor && !Modifier.isStatic(info.getMethod().getModifiers()))
                  {
                     requiresInstanceAdvisor = true;
                     InstanceAdvisor ia = getProxyInstanceAdvisor();
                     icptr = ((PerJoinpointInterceptor)icptrs[i]).getAspectInstance(ia);
                  }
                  else
                  {
                     icptr = icptrs[i];
                  }
                  if (icptr != null)
                  {
                     allIcptrs.add(icptr);
                  }
               }
               else
               {
                  AbstractAdvice advice = (AbstractAdvice)icptrs[i];
                  Object aspectInstance = null;
                  if (icptrs[i] instanceof PerInstanceAdvice && !Modifier.isStatic(info.getMethod().getModifiers()))
                  {
                     requiresInstanceAdvisor = true;
                     InstanceAdvisor ia = getProxyInstanceAdvisor();
                     aspectInstance = ((PerInstanceAdvice)advice).getAspectInstance(ia);
                  }
                  else if (icptrs[i] instanceof PerJoinpointAdvice && !Modifier.isStatic(info.getMethod().getModifiers()))
                  {
                     requiresInstanceAdvisor = true;
                     InstanceAdvisor ia = getProxyInstanceAdvisor();
                     aspectInstance = ((PerJoinpointAdvice)advice).getAspectInstance(ia);
                  } 
                  else 
                  {
                     aspectInstance = advice.getAspectInstance();
                  }
                  
                  if (aspectInstance != null)
                  {
                     MarshalledAdvice ma = new MarshalledAdvice(aspectInstance, icptrs[i].getName(), advice.getAdviceName());
                     allIcptrs.add(ma);
                  }
               }
            }
            interceptors = allIcptrs.toArray(new Interceptor[allIcptrs.size()]);
         }
      }
      
      private InstanceAdvisor getProxyInstanceAdvisor()
      {
         InstanceAdvisor ia = proxy.proxyInstance.getInstanceAdvisor();
         proxy.checkInstanceAdvisor((InstanceProxyContainer)ia);
         return ia;
      }
      
      public MethodInfo getMethodInfo(Advisor advisor)
      {
         MethodInfo info = new MethodInfo(clazz, advisedHash, unadvisedHash, advisor);
         info.setInterceptors(interceptors);
         return info;
      }
      
      public String toString()
      {
         return advisedHash + " " + ((interceptors == null) ? "null" : Arrays.asList(interceptors));
      }
   }
      
   private static class MethodHashingException extends IOException
   {
      private static final long serialVersionUID = 1L;

      MethodHashingException(Exception e)
      {
         super("Error hashing method");
         super.initCause(e);
      }
   }

}