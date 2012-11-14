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

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Strategy for adding caller info fields to class
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class CallerInfoAdder
{
   Instrumentor instrumentor;
   int infoModifier = Modifier.PRIVATE | Modifier.STATIC;
   
   protected CallerInfoAdder(Instrumentor instrumentor)
   {
      this.instrumentor = instrumentor;
   }
   
   protected CallerInfoAdder(Instrumentor instrumentor, int mod)
   {
      this.instrumentor = instrumentor;
      infoModifier = mod;
   }
   
   protected abstract void addMethodByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException;
   
   protected abstract void addConByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException;

   protected abstract void addConByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException;

   protected abstract void addMethodByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException;
   
   
   protected void addConByConInfoField(CtClass addTo, String fieldName, String init) throws NotFoundException, CannotCompileException
   {
      addField(addTo, "org.jboss.aop.ConByConInfo", fieldName, init);
   }

   protected void addConByMethodInfoField(CtClass addTo, String fieldName, 
         String init) throws NotFoundException, CannotCompileException
   {
      addField(addTo, "org.jboss.aop.ConByMethodInfo", fieldName, init);
   }

   protected void addMethodByConInfoField(CtClass addTo, String fieldName, 
         String init) throws NotFoundException, CannotCompileException
   {
      addField(addTo, "org.jboss.aop.MethodByConInfo", fieldName, init);
   }

   protected void addMethodByMethodInfoField(CtClass addTo, String fieldName, 
         String init) throws NotFoundException, CannotCompileException
   {
      addField(addTo, "org.jboss.aop.MethodByMethodInfo", fieldName, init);
   }
   
   private void addField(CtClass addTo, String typeName, String fieldName, 
         String init) throws NotFoundException, CannotCompileException
   {
      CtField.Initializer initializer = (init != null ) ? CtField.Initializer.byExpr(init) : null;
      TransformerCommon.addInfoField(instrumentor, typeName, fieldName, infoModifier, addTo, addInfoAsWeakReference(), initializer, markInfoAsSynthetic());
   }

   protected abstract boolean addInfoAsWeakReference();
   
   protected abstract boolean markInfoAsSynthetic();
}
