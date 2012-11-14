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

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;

public class AOPProxyFactoryMixin implements Serializable
{
   private static final long serialVersionUID = 1L;
   
   private String construction;
   private WeakReference<Class<?>> mixinClassRef;
   private WeakReference<Class<?>>[] interfaceClassRefs;
   private int hashcode;

   public AOPProxyFactoryMixin(Class<?> mixin, Class<?>[] interfaces)
   {
      mixinClassRef = new WeakReference<Class<?>>(mixin);
      interfaceClassRefs = ContainerCacheUtil.getSortedWeakReferenceForInterfaces(interfaces);
   }

   public AOPProxyFactoryMixin(Class<?> mixin, Class<?>[] interfaces, String parameters)
   {
      this(mixin, interfaces);
      StringBuffer construction = new StringBuffer(" new ");
      construction.append(mixin.getName());
      
      if (!parameters.startsWith("("))
      {
         construction.append("(");
      }
      
      construction.append(parameters);
      
      if (!parameters.endsWith(")"))
      {
         construction.append(")");
      }
      this.construction = construction.toString();
   }

   public String getConstruction()
   {
      return construction;
   }


   public Class<?>[] getInterfaces()
   {
      if (interfaceClassRefs != null)
      {
         Class<?>[] interfaces = new Class[interfaceClassRefs.length];
         for (int i = 0 ; i < interfaces.length ; i++)
         {
            interfaces[i] = interfaceClassRefs[i].get();
         }
         return interfaces;
      }
      return null;
   }

   public Class<?> getMixin()
   {
      return mixinClassRef.get();
   }

   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      
      if (!(obj instanceof AOPProxyFactoryMixin))
      {
         return false;
      }
      
      AOPProxyFactoryMixin other = (AOPProxyFactoryMixin)obj;
      
      if (!compareConstruction(other))
      {
         return false;
      }
      if (!ContainerCacheUtil.compareClassRefs(this.mixinClassRef, other.mixinClassRef))
      {
         return false;
      }
      if (!ContainerCacheUtil.compareInterfaceRefs(this.interfaceClassRefs, other.interfaceClassRefs))
      {
         return false;
      }
      return true;
   }

   public int hashCode()
   {
      if (hashcode == 0)
      {
         
         Class<?> clazz = mixinClassRef.get();
         StringBuffer sb = new StringBuffer();
         
         if (clazz != null)
         {
            sb.append(clazz.getName());
         }
         
         if (interfaceClassRefs != null)
         {
            for (int i = 0 ; i < interfaceClassRefs.length ; i++)
            {
               sb.append(";");
               sb.append((interfaceClassRefs[i].get()).getName());
            }
         }
         hashcode = sb.toString().hashCode();
         if (construction != null)
         {
            hashcode += construction.hashCode();
         }
      }
      
      return hashcode;
   }

   public String toString()
   {
      return super.toString();
   }
   
   private boolean compareConstruction(AOPProxyFactoryMixin other)
   {
      if (this.construction == null && other.construction != null)
      {
         return false;
      }
      if (this.construction != null && other.construction == null)
      {
         return false;
      }
      if (this.construction != null)
      {
         if (!this.construction.equals(other.construction))
         {
            return false;
         }
      }
      return true;
   }
   
   private void writeObject(java.io.ObjectOutputStream out) throws IOException
   {
      out.writeObject(construction);
      out.writeObject(mixinClassRef.get());
      out.writeObject(getInterfaces());
      out.writeInt(hashCode());
   }
   
   private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
   {
      construction = (String)in.readObject();
      mixinClassRef = new WeakReference<Class<?>>((Class<?>)in.readObject());
      Class<?>[] ifs = (Class[])in.readObject();
      interfaceClassRefs = new WeakReference[ifs.length];
      for (int i = 0 ; i < ifs.length ; i++)
      {
         interfaceClassRefs[i] = new WeakReference<Class<?>>(ifs[i]);
      }
      hashcode = in.readInt();
   }
   
}