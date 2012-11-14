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
package org.jboss.aop.metadata;

/**
 * An extension of SimpleClassMetadataLoader, allowing us to add metadata without resorting to XML.
 * Intention is for dynamically adding metadata at runtime
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class DynamicSimpleClassMetadataLoader extends SimpleClassMetaDataLoader
{
   SimpleClassMetaDataBinding binding;
   String tag;
   
   public DynamicSimpleClassMetadataLoader(String name, String tag, String classExpr)
   {
      String bname = name;
      if (bname == null)
      {
         //Make 100% sure the name is unique
         long millis = System.currentTimeMillis();
         long random = (long)(Math.random() * millis);
         bname = tag + ":" + classExpr + millis + ":" + random;
      }
      binding = new SimpleClassMetaDataBinding(this, bname, tag, classExpr);
   }
   
   public DynamicSimpleClassMetadataLoader(String tag, String classExpr)
   {
      this(null, tag, classExpr);
   }
   
   public ClassMetaDataBinding getClassMetaDataBinding()
   {
      return binding;
   }
   
   public void addDefaultMetaData(String attr, Object val)
   {
      binding.addDefaultMetaData(binding.getTag(), attr, val);
   }
   
   public void addClassMetaData(String attr, Object val)
   {
      binding.addClassMetaData(binding.getTag(), attr, val);
   }

   public void addMethodMetaData(String methodExpr, String attr, Object val)
   {
      binding.queueMethodMetaData(methodExpr, binding.getTag(), attr, val);
   }
   
   public void addFieldMetaData(String fieldName, String attr, Object val)
   {
      binding.queueFieldMetaData(fieldName, binding.getTag(), attr, val);
   }
   
   public void addConstructorMetaData(String constructorExpr, String attr, Object val)
   {
      binding.queueConstructorMetaData(constructorExpr, binding.getTag(), attr, val);
   }
}
