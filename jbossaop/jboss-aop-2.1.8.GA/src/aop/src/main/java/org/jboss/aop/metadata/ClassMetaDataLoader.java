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

import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import org.jboss.aop.Advisor;
import org.w3c.dom.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <description>
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 * @see <related>
 */
public interface ClassMetaDataLoader
{
   // Public --------------------------------------------------------
   public ClassMetaDataBinding importMetaData(Element element, String name, String tag, String classExpr) throws Exception;

   /**
    * This is a prebind of metadata so that loader/compiler can work with metadata as strings
    *
    * @param advisor
    * @param data
    * @param methods
    * @param fields
    * @param constructors
    * @throws Exception
    */
   public void bind(Advisor advisor, ClassMetaDataBinding data, CtMethod[] methods, CtField[] fields, CtConstructor[] constructors) throws Exception;

   /**
    * This is a full bind of metadata.  It can work with real objects unlike the bind with Javassist types
    *
    * @param advisor
    * @param data
    * @param methods
    * @param fields
    * @param constructors
    * @throws Exception
    */
   public void bind(Advisor advisor, ClassMetaDataBinding data, Method[] methods, Field[] fields, Constructor<?>[] constructors) throws Exception;
}
