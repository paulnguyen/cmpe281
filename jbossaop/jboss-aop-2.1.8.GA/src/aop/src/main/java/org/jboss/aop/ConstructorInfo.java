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
package org.jboss.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.jboss.aop.joinpoint.ConstructorJoinpoint;
import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.util.MethodHashing;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class ConstructorInfo extends JoinPointInfo implements ConstructorExecution
{
   private Method wrapper;
   private Constructor<?> constructor;
   private int index;
   
   public ConstructorInfo()
   {
   }
   
   public ConstructorInfo(Class<?> clazz, int index, long wrapperHash, long constructorHash, Advisor advisor)
   {
      super(advisor, clazz);
      try
      {
         this.index = index;
         this.wrapper = MethodHashing.findMethodByHash(clazz, wrapperHash);
         this.constructor = MethodHashing.findConstructorByHash(clazz, constructorHash);
         this.setAdvisor(advisor);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
   /*
    * For copying
    */
   private ConstructorInfo(ConstructorInfo other)
   {
      super(other);
   }
   
   protected Joinpoint internalGetJoinpoint()
   {
      return new ConstructorJoinpoint(constructor);
   }   
   
   public JoinPointInfo copy()
   {
      return new ConstructorInfo(this);
   }
   
   public String toString()
   {
      StringBuffer sb = new StringBuffer("Constructor");
      sb.append("[");
      sb.append("constructor=" + constructor);
      sb.append("]");
      return sb.toString();
   }

   public void setWrapper(Method wrapper)
   {
      this.wrapper = wrapper;
   }

   public Method getWrapper()
   {
      return wrapper;
   }

   public void setConstructor(Constructor<?> constructor)
   {
      this.constructor = constructor;
   }

   public Constructor<?> getConstructor()
   {
      return constructor;
   }

   public void setIndex(int index)
   {
      this.index = index;
   }

   public int getIndex()
   {
      return index;
   }
   

   public <T extends Annotation> T resolveAnnotation(Class<T> annotation)
   {
      T val = super.resolveAnnotation(annotation);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveTypedAnnotation(constructor, annotation);
         if (val != null) return val;
      }

      return null;
   }

}
