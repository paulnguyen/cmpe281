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
package org.jboss.aop.joinpoint;

import org.jboss.aop.FieldInfo;
import org.jboss.aop.advice.Interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * This is a helper wrapper class for an Invocation object.
 * It is used to add or get values or metadata that pertains to
 * an AOP field access or field update.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
public abstract class FieldInvocation extends InvocationBase
{
   private static final long serialVersionUID = -6040602776393748583L;
   
   protected transient Field field = null;
   protected int index;

   public FieldInvocation(Field field, int fieldIndex, Interceptor[] interceptors)
   {
      super(interceptors);
      this.field = field;
      this.index = fieldIndex;
   }

   protected FieldInvocation(Interceptor[] interceptors)
   {
      super(interceptors);
   }

   protected FieldInvocation(FieldInfo info, Interceptor[] interceptors)
   {
      this(info.getField(), info.getIndex(), interceptors);
      this.advisor = info.getAdvisor();
   }
   
   /**
    * This method resolves an annotation based on the context of the invocation.
    *
    */
   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(annotation);
   }

   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      T val = super.resolveTypedAnnotation(annotation);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveTypedAnnotation(field, annotation);
         if (val != null) return val;
      }

      return null;
   }
   
   /**
    * This method resolves metadata based on the context of the invocation.
    * It iterates through its list of MetaDataResolvers to find out the
    * value of the metadata desired.
    *
    * This list usually is ThreadMetaData, InstanceAdvisor.getMetaData
    * ClassAdvisor.getMethodMetaData (or field, or constructor)
    * ClassAdvisor.getDefaultMetaData
    */
   public Object getMetaData(Object group, Object attr)
   {
      Object val = super.getMetaData(group, attr);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().getFieldMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

      if (getAdvisor() != null)
      {
         val = getAdvisor().getDefaultMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

      return null;
   }

	/**
	 * This is the field the invocation is accessing
	 *
	 * @return
	 */
	public Field getField()
	{
		return field;
	}

   /**
    * This is an index into the Field[] array accessed through the ClassAdvisor
    *
    * @return
    */
   public int getIndex()
   {
      return index;
   }

}
