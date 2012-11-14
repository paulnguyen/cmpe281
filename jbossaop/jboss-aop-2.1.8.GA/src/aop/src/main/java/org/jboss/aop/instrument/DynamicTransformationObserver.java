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
package org.jboss.aop.instrument;

import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtField;

/**
 * Observer of the transformation process of a class (the transformation <i>target class</i>).
 * When a field read, field write, or constructor execution becomes wrapped due to only
 * dynamicaly added bindings, it may be necessary to hot swap the code of classes previously loaded.
 * Any class that executes the dynamicaly wrapped joinpoint must be changed to invoke the
 * joinpoint wrapped instead.
 * The implementation of this observer that must be used is dependent of the dynamic aop
 * strategy.
 * @see org.jboss.aop.DynamicAOPStrategy
 * @author Flavia Rainone
 */
public interface DynamicTransformationObserver
{
   /**
    * Notifies that during the transformation of the <i>target class</i>, the <code>
    * field</code> read joinpoint was wrapped due only to bindings added
    * dynamicaly. This means that callers of <code>field</code> read joinpoint
    * may need to be hotswapped to invoke the <code>field</code> read wrapper.
    * @param field the field whose read joinpoint was dynamicaly wrapped.
    */
   void fieldReadDynamicalyWrapped(CtField field);
   
   /**
    * Notifies that during the transformation of <i>target class</i>, the <code>
    * field</code> write joinpoint was wrapped due only to bindings added
    * dynamicaly. This means that callers of <code>field</code> write joinpoint
    * may need to be hotswapped to invoke the <code>field</code> write wrapper.
    * @param field the field whose write joinpoint was dynamicaly wrapped.
    */
   void fieldWriteDynamicalyWrapped(CtField field);
   
   /**
    * Notifies that during the transformation of <i>target class</i>, its <code>
    * constructor</code> execution joinpoints were wrapped due only to bindings added
    * dynamicaly. This means that callers of the constructor execution joinpoints
    * may need to be hotswapped to invoke the constructor execution wrappers.
    */
   void constructorDynamicalyWrapped();
   
   /**
    * Notifies the observer that the transformation of the <i>target class<i> is
    * finished.
    */
   void transformationFinished(CtClass clazz, CodeConverter converter);
}