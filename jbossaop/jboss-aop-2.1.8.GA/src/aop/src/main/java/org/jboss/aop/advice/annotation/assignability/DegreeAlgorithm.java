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
package org.jboss.aop.advice.annotation.assignability;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import org.jboss.aop.advice.SecurityActions;
import org.jboss.aop.advice.annotation.AdviceMethodFactory;

/**
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class DegreeAlgorithm
{
   public static final short NOT_ASSIGNABLE_DEGREE = Short.MAX_VALUE;
   public static final short MAX_DEGREE = NOT_ASSIGNABLE_DEGREE - 1;
   
   private static final DegreeAlgorithm INSTANCE = new DegreeAlgorithm();
   
   public static DegreeAlgorithm getInstance()
   {
      return INSTANCE;
   }
   
   private DegreeAlgorithm(){}
   
   public short getAssignabilityDegree(Type type, Type fromType)
   {
      if (type == null || fromType == null)
      {
         return DegreeAlgorithm.MAX_DEGREE;
      }
       Class<?> clazz = getClassType(type);
       if (clazz == null)
       {
          return MAX_DEGREE;
       }
       Class<?> fromClass = getClassType(fromType);
       if (fromClass == null)
       {
          return MAX_DEGREE;
       }
       return getAssignabilityDegree(fromClass, clazz);
   }
   
   private Class<?> getClassType(Type type)
   {
      if (type instanceof Class)
      {
         return (Class<?>) type;
      }
      if (type instanceof ParameterizedType)
      {
         return (Class<?>) ((ParameterizedType) type).getRawType();
      }
      if (type instanceof TypeVariable)
      {
         return null;
      }
      Type componentType = ((GenericArrayType) type).getGenericComponentType();
      Class<?> componentClass = getClassType(componentType);
      try
      {
         return SecurityActions.getClassLoader(componentClass).loadClass(
               componentClass.getName() + "[]");
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   /**
    * Returns the assignability degree from <code>fromType</code> to </code>toType</code>. 
    * <p>
    * The assignability degree is the distance in class and interface hierarchy between
    * <code>fromType</code> and </code>toType</code>. If <code>toType</code> is an
    * interface implemented by <code>fromType</code>, then the degree is 1. Otherwise,
    * the degree is exactly the number of hierarchical levels between <code>fromType
    * </code> and <code>toType</code>.
    * 
    * @param fromType the type from which <code>toType</code> is supposedly assignable.
    * @param toType   the type to which <code>fromType</code> can be converted without
    *                 type casting.
    * @return {@link AdviceMethodFactory#NOT_ASSIGNABLE_DEGREE if <code>toType</code> is
    *         not assignable from <code>fromType</code>; the hierarchical distance between
    *         <code>fromType</code> and <code>toType</code> otherwise.
    *         
    * @see java.lang.Class#isAssignableFrom(Class)
    */
   private short getAssignabilityDegree(Class<?> fromType, Class<?> toType)
   {
      // they're the same
      if (fromType == toType)
      {
         return 0;
      }
      if (toType.isInterface())
      {
         if (fromType.isInterface())
         {
            // assignability degree on interface inheritance
            return getInterfaceInheritanceAD(fromType, toType, (short) 0);
         }
         else
         {
            // assignability degree on interface implementation
            return getImplementationAD(fromType, toType);
         }
      }
      if (fromType.isInterface())
      {
         // if is object, the degree is the size of this interface hierarchy + 1
         if (toType.getName() == "java.lang.Object")
         {
            ArrayList<Class<?>[]> list1 = new ArrayList<Class<?>[]>();
            ArrayList<Class<?>[]> list2 = new ArrayList<Class<?>[]>();
            Class<?>[] fromTypeInterfaces = fromType.getInterfaces();
            if (fromTypeInterfaces.length == 0)
            {
               return 1;
            }
            list1.add(fromTypeInterfaces);
            short degree = 2;
            while (true)
            {
               for (Class<?>[] interfaces : list1)
               {
                  for (int i = 0; i < interfaces.length; i++)
                  {
                     Class<?>[] superInterfaces = interfaces[i].getInterfaces();
                     if (superInterfaces.length == 0)
                     {
                        return degree;
                     }
                     list2.add(superInterfaces);
                  }
               }
               degree ++;
               list1.clear();
               ArrayList<Class<?>[]> temp = list1;
               list1 = list2;
               list2 = temp;
            }
         }
         // you can't get to a class (except Object) from an interface
         return DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      }
      // assignability degree on class inheritance
      return getClassInheritanceAD(fromType.getSuperclass(), toType, (short) 1);
   }
   
   /**
    * Returns the assignability degree between <code>fromClassType</code> and <code>
    * toInterfaceType</code>.
    * 
    * @param fromClassType   a class type that supposedly implements <code>
    *                        toInterfaceType</code>
    * @param toInterfaceType an interface type that is supposedly implemented by <code>
    *                        fromClassType</code>
    * @return {@link AdviceMethodFactory#NOT_ASSIGNABLE_DEGREE} if <code>fromClassType
    *         </code> does not implement <code>toInterfaceType</code>; otherwise, if
    *         <code>fromType</code> implements a subinterface of <code>toInterfaceType
    *         </code>, returns 1 + the assignability degree between this subinterface and
    *         <code>toType</code>; otherwhise, returns 1.
    *         
    */
   private short getImplementationAD(Class<?> fromClassType, Class<?> toInterfaceType)
   {
      if (fromClassType == null)
      {
         return DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      }
      
      Class<?>[] interfaces = fromClassType.getInterfaces();
      for (int i = 0 ; i < interfaces.length ; i++)
      {
         if(interfaces[i] == toInterfaceType)
         {
            return 1;
         }
      }
      short currentDegree = DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      for (int i = 0 ; i < interfaces.length ; i++)
      {
         currentDegree = (short) Math.min(getInterfaceInheritanceAD(interfaces[i],
               toInterfaceType, (short) 1), currentDegree);
      }
      if (currentDegree == DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE)
      {
         return getImplementationAD(fromClassType.getSuperclass(), toInterfaceType);
      }
      return currentDegree;
   }
   
   /**
    * Recursive method that returns the assignability degree on an interface inheritance.
    * 
    * @param fromInterfaceType  the interface that supposedly inherits (directly or
    *                           indirectly <code>toInterfaceType</code>.
    * @param toInterfaceType    the interface which is supposedly assignable from <code>
    *                           fromInterfaceType</code>. The type <code>
    *                           toInterfaceType</code> is not the same as <code>
    *                           fromInterfaceType</code>.
    * @param currentDegree      the current assignability degree
    * @return                   {@link AdviceMethodFactory#NOT_ASSIGNABLE_DEGREE} if
    *                           <code>toInterfaceType</code> is not assignable from <code>
    *                           fromInterfaceType</code>; <code>currentDegree + </code>
    *                           the assignability degree from <code>fromInterfaceType
    *                           </code> to <code>toInterfaceType</code>.
    */
   private short getInterfaceInheritanceAD(Class<?> fromInterfaceType,
         Class<?> toInterfaceType, short currentDegree)
   {
      Class<?>[] interfaces = fromInterfaceType.getInterfaces();
      currentDegree ++;
      for (int i = 0; i < interfaces.length; i++)
      {
         if(interfaces[i] == toInterfaceType)
         {
            return currentDegree;
         }
      }
      short bestDegree = DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      for (int i = 0; i < interfaces.length; i++)
      {
         bestDegree = (short) Math.min(getInterfaceInheritanceAD(interfaces[i],
               toInterfaceType, currentDegree), bestDegree);
      }
      return bestDegree;
   }
   
   /**
    * Recursive method that returns the assignability degree on an class inheritance.
    * 
    * @param fromClassType  the class that supposedly inherits (directly or
    *                       indirectly <code>toClassType</code>.
    * @param toClassType    the class which is supposedly assignable from <code>
    *                       fromInterfaceType</code>. The type <code>toClassType</code> is
    *                       not the same as <code>fromClassType</code>.
    * @param currentDegree  the current assignability degree
    * @return               {@link AdviceMethodFactory#NOT_ASSIGNABLE_DEGREE} if <code>
    *                       toClassType</code> is not assignable from <code>fromClassType
    *                       </code>; <code>currentDegree + </code> the assignability
    *                       degree from <code>fromClassType</code> to <code>toClassType
    *                       </code>.
    */
   private short getClassInheritanceAD(Class<?> fromClassType, Class<?> toClassType,
         short currentDegree)
   {
      if (fromClassType == null)
      {
         return DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      }
      if (fromClassType == toClassType)
      {
         return currentDegree;
      }
      return getClassInheritanceAD(fromClassType.getSuperclass(), toClassType,
            ++currentDegree);
   }
}