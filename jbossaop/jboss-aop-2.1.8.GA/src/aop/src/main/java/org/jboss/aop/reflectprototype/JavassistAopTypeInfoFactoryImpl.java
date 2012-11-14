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
package org.jboss.aop.reflectprototype;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.AnnotationAttributeImpl;
import org.jboss.reflect.plugins.EnumConstantInfoImpl;
import org.jboss.reflect.plugins.javassist.JavassistAnnotationInfo;
import org.jboss.reflect.plugins.javassist.JavassistArrayInfoImpl;
import org.jboss.reflect.plugins.javassist.JavassistEnumInfo;
import org.jboss.reflect.plugins.javassist.JavassistTypeInfo;
import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactoryImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.NumberInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * A JavassistAopTypeInfoFactoryImpl.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unchecked")
public class JavassistAopTypeInfoFactoryImpl extends JavassistTypeInfoFactoryImpl
{
   
   private ClassPoolFactory poolFactory = new AopClassPoolFactory();
   
   /**
    * Get the information for a class
    * 
    * @param name the name
    * @param cl the classloader
    * @return the info
    * @throws ClassNotFoundException when the class cannot be found
    */
   @Override
   public Object get(String name, ClassLoader cl) throws ClassNotFoundException
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      if (cl == null)
         throw new IllegalArgumentException("Null classloader");
      try
      {
         CtClass clazz = poolFactory.getPoolForLoader(cl).get(name);

         return get(clazz);
      }
      catch(NotFoundException nfe)
      {
         throw new ClassNotFoundException(nfe.getMessage());
      }
   }
   
   /**
    * Get the information for a class
    * 
    * @param clazz the class
    * @return the info
    */
   public Object get(CtClass clazz)
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null class");
      
      Map classLoaderCache = getClassLoaderCache(clazz.getClassPool().getClassLoader());

      WeakReference weak = (WeakReference) classLoaderCache.get(clazz.getName());
      if (weak != null)
      {
         Object result = weak.get();
         if (result != null)
         {
            if(compare(clazz, (ClassInfo) result))
               return result;
            else
            {
               classLoaderCache.remove(clazz.getName());
            }
         }
      }

      Object result = instantiate(clazz);
      
      weak = new WeakReference(result);
      classLoaderCache.put(clazz.getName(), weak);
      
//      we just ignore generate(..) since it doesnt do anything atm
//      generate(clazz, result);
      
      return result;
   }
   
   /**
    * Get the information for a class
    * 
    * @param clazz the class
    * @return the info
    */
   @Override
   public Object get(Class clazz)
   {
      throw new RuntimeException("This method is void!!!");
   }
   
   protected Object instantiate(CtClass ctClass)
   {
      try
      {
         if (ctClass.isArray())
         {
            TypeInfo componentType = getTypeInfo(ctClass.getComponentType());
            
            Class[] types = new Class[] { JavassistTypeInfoFactoryImpl.class, CtClass.class, Class.class, TypeInfo.class };
            Constructor con = JavassistArrayInfoImpl.class.getDeclaredConstructor(types);
            
            Object[] args = new Object[] {this, ctClass, null, componentType};
            con.setAccessible(true);
            return con.newInstance(args);
         }

         if (ctClass.isAnnotation())
         {
            JavassistAnnotationInfo result = new JavassistAnnotationInfo(this, ctClass, null);
            CtMethod[] methods = ctClass.getDeclaredMethods();
            AnnotationAttributeImpl[] atttributes = new AnnotationAttributeImpl[methods.length];
            for (int i = 0 ; i < methods.length ; i++)
            {
               atttributes[i] = new AnnotationAttributeImpl(methods[i].getName(), getTypeInfo(methods[i].getReturnType()), null);
            }
            result.setAttributes(atttributes);
            return result;

         }
         else if (ctClass.isEnum())
         {
            JavassistEnumInfo enumInfo = new JavassistEnumInfo(this, ctClass, null);
            CtField[] fields = ctClass.getFields();
            EnumConstantInfoImpl[] constants = new EnumConstantInfoImpl[fields.length];
            int i = 0;
            for (CtField field : fields)
            {
               AnnotationValue[] annotations = getAnnotations(field);
               constants[i++] = new EnumConstantInfoImpl(field.getName(), enumInfo, annotations);
            }
            enumInfo.setEnumConstants(constants);
            return enumInfo;
         }

         Class[] types = new Class[] { JavassistTypeInfoFactoryImpl.class, CtClass.class, Class.class};
         Constructor con = JavassistTypeInfo.class.getDeclaredConstructor(types);
         Object[] args = new Object[] {this, ctClass, null};
         con.setAccessible(true);
         return  con.newInstance(args);

      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (IllegalArgumentException e)
      {
         e.printStackTrace();
      }
      catch (InstantiationException e)
      {
         e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
         e.printStackTrace();
      }
      catch (InvocationTargetException e)
      {
         e.printStackTrace();
      }
      catch (SecurityException e)
      {
         e.printStackTrace();
      }
      catch (NoSuchMethodException e)
      {
         e.printStackTrace();
      }
      
      return null;
   }

   @Override
   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      if (name == null)
         throw new IllegalArgumentException("Null class name");
      if (cl == null)
         cl = Thread.currentThread().getContextClassLoader();

      TypeInfo primitive = PrimitiveInfo.valueOf(name);
      if (primitive != null)
         return primitive;

      NumberInfo number = NumberInfo.valueOf(name);
      if (number != null)
      {
         synchronized (number)
         {
            if (number.getPhase() != NumberInfo.Phase.INITIALIZING)
            {
               if (number.getPhase() != NumberInfo.Phase.COMPLETE)
               {
                  number.initializing();
                  try
                  {
                     number.setDelegate((TypeInfo)get(poolFactory.getPoolForLoader(cl).get(name)));
                  }
                  catch (NotFoundException e)
                  {
                     throw new ClassNotFoundException(e.getMessage());
                  }
//                  number.setDelegate((TypeInfo)get(Class.forName(name, false, cl)));
               }
               return number;
            }
         }
      }

//      Class<?> clazz = Class.forName(name, false, cl);
      CtClass clazz = null;
      try
      {
         clazz = poolFactory.getPoolForLoader(cl).get(name);
      }
      catch (NotFoundException e)
      {
         throw new ClassNotFoundException(e.getMessage());
      }
      return getTypeInfo(clazz);
   }
   
   @Override
   public TypeInfo getTypeInfo(CtClass clazz)
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null class");

      TypeInfo primitive = PrimitiveInfo.valueOf(clazz.getName());
      if (primitive != null)
         return primitive;

      NumberInfo number = NumberInfo.valueOf(clazz.getName());
      if (number != null)
      {
         synchronized (number)
         {
            if (number.getPhase() != NumberInfo.Phase.INITIALIZING)
            {
               if (number.getPhase() != NumberInfo.Phase.COMPLETE)
               {
                  number.initializing();
                  number.setDelegate((TypeInfo)get(clazz));
               }
               return number;
            }
         }
      }

      return (TypeInfo) get(clazz);
   }
   
   private boolean compare(CtClass clazz, ClassInfo info)
   {
      if(clazz.getDeclaredMethods().length == info.getDeclaredMethods().length &&
            clazz.getDeclaredConstructors().length == info.getDeclaredConstructors().length &&
            clazz.getDeclaredFields().length == info.getDeclaredFields().length)
         return true;
      else
         return false;
   }

}
