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
package org.jboss.aop.advice;

import org.jboss.aop.instrument.Untransformable;
import org.jboss.aop.joinpoint.ConstructionInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70826 $
 */
public abstract class AbstractAdvice implements Interceptor, Untransformable
{
   private static final Class<?>[] INVOCATION_SIGNATURE = {Invocation.class};
   private static final Class<?>[] METHOD_SIGNATURE = {MethodInvocation.class};
   private static final Class<?>[] CONSTRUCTOR_SIGNATURE = {ConstructorInvocation.class};
   private static final Class<?>[] CONSTRUCTION_SIGNATURE = {ConstructionInvocation.class};
   private static final Class<?>[] FIELD_SIGNATURE = {FieldInvocation.class};
   private static final Class<?>[] FIELD_READ_SIGNATURE = {FieldReadInvocation.class};
   private static final Class<?>[] FIELD_WRITE_SIGNATURE = {FieldWriteInvocation.class};
   private static final Class<?>[] METHOD_CALLED_BY_METHOD_SIGNATURE = {MethodCalledByMethodInvocation.class};
   private static final Class<?>[] METHOD_CALLED_BY_CONSTRUCTOR_SIGNATURE = {MethodCalledByConstructorInvocation.class};
   private static final Class<?>[] CON_CALLED_BY_METHOD_SIGNATURE = {ConstructorCalledByMethodInvocation.class};
   private static final Class<?>[] CON_CALLED_BY_CONSTRUCTOR_SIGNATURE = {ConstructorCalledByConstructorInvocation.class};
   protected Method invocationAdvice;
   protected Method methodAdvice;
   protected Method constructorAdvice;
   protected Method constructionAdvice;
   protected Method fieldAdvice;
   protected Method fieldReadAdvice;
   protected Method fieldWriteAdvice;
   protected Method methodCalledByMethodAdvice;
   protected Method methodCalledByConstructorAdvice;
   protected Method conCalledByMethodAdvice;
   protected Method conCalledByConstructorAdvice;
   protected Class<?> aspectClass;
   protected String adviceName;

   protected void init(String advice, Class<?> aspectClass)
   {
      if (aspectClass == null)
      {
         this.aspectClass = aspectClass;
      }
      if (adviceName == null)
      {
         this.adviceName = advice;
      }
      invocationAdvice = findByInvocation(adviceName, aspectClass);
      if (invocationAdvice == null)
      {
         methodAdvice = findByMethodInvocation(adviceName, aspectClass);
         constructorAdvice = findByConstructorInvocation(adviceName, aspectClass);
         constructionAdvice = findByConstructionInvocation(adviceName, aspectClass);
         fieldAdvice = findByFieldInvocation(adviceName, aspectClass);
         if (fieldAdvice == null)
         {
            fieldReadAdvice = findByFieldReadInvocation(adviceName, aspectClass);
            fieldWriteAdvice = findByFieldWriteInvocation(adviceName, aspectClass);
         }
         methodCalledByMethodAdvice = findByMethodCalledByMethodInvocation(adviceName, aspectClass);
         methodCalledByConstructorAdvice = findByMethodCalledByConstructorInvocation(adviceName, aspectClass);
         conCalledByMethodAdvice = findByConstructorCalledByMethodInvocation(adviceName, aspectClass);
         conCalledByConstructorAdvice = findByConstructorCalledByConstructorInvocation(adviceName, aspectClass);
      }
   }

   protected static Method findByInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, INVOCATION_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByMethodInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, METHOD_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByFieldInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, FIELD_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByFieldReadInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, FIELD_READ_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByFieldWriteInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, FIELD_WRITE_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByConstructorInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, CONSTRUCTOR_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByConstructionInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, CONSTRUCTION_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByMethodCalledByMethodInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, METHOD_CALLED_BY_METHOD_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByMethodCalledByConstructorInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, METHOD_CALLED_BY_CONSTRUCTOR_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByConstructorCalledByMethodInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, CON_CALLED_BY_METHOD_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected static Method findByConstructorCalledByConstructorInvocation(String adviceName, Class<?> clazz)
   {
      try
      {
         return clazz.getMethod(adviceName, CON_CALLED_BY_CONSTRUCTOR_SIGNATURE);
      }
      catch (NoSuchMethodException e)
      {
         // complete
      }
      return null;
   }

   protected Method resolveAdvice(Invocation invocation)
   {
      if (invocationAdvice != null) return invocationAdvice;
      if (invocation instanceof MethodInvocation)
      {
         if (methodAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve MethodInvocation advice " + getName());
         }
         return methodAdvice;
      }
      if (invocation instanceof FieldInvocation)
      {
         if (fieldAdvice != null) return fieldAdvice;
         if (invocation instanceof FieldReadInvocation)
         {
            if (fieldReadAdvice == null)
            {
               throw new IllegalStateException("Unable to resolve FieldReadInvocation advice " + getName());
            }
            return fieldReadAdvice;
         }
         if (invocation instanceof FieldWriteInvocation)
         {
            if (fieldWriteAdvice == null)
            {
               throw new IllegalStateException("Unable to resolve FieldWriteInvocation advice " + getName());
            }
            return fieldWriteAdvice;
         }
      }
      if (invocation instanceof ConstructorInvocation)
      {
         if (constructorAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve ConstructorInvocation advice " + getName());
         }
         return constructorAdvice;
      }
      if (invocation instanceof ConstructionInvocation)
      {
         if (constructionAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve ConstructionInvocation advice " + getName());
         }
         return constructionAdvice;
      }
      if (invocation instanceof MethodCalledByMethodInvocation)
      {
         if (methodCalledByMethodAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve MethodCalledByMethodInvocation advice " + getName());
         }
         return methodCalledByMethodAdvice;
      }
      if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         if (methodCalledByConstructorAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve MethodCalledByConstructorInvocation advice " + getName());
         }
         return methodCalledByConstructorAdvice;
      }
      if (invocation instanceof ConstructorCalledByMethodInvocation)
      {
         if (conCalledByMethodAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve ConstructorCalledByMethodInvocation advice " + getName());
         }
         return conCalledByMethodAdvice;
      }
      if (invocation instanceof ConstructorCalledByConstructorInvocation)
      {
         if (conCalledByMethodAdvice == null)
         {
            throw new IllegalStateException("Unable to resolve ConstructorCalledByConstructorInvocation advice " + getName());
         }
         return conCalledByConstructorAdvice;
      }
      throw new RuntimeException("Should Be Unreachable, but unable to discover Advice");

   }
   
   public String getAdviceName()
   {
      return adviceName;
   }
   
   public abstract Object getAspectInstance();
}
