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
import javassist.NotFoundException;

/**
 * Adds CallerInfo fields to advised class
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class ClassicCallerInfoAdder extends CallerInfoAdder
{

   public ClassicCallerInfoAdder(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void addMethodByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "aop$classAdvisor$aop.resolveCallerMethodInfoAsWeakReference(" + callingHash + "L, \"" + classname + "\", " + calledHash + "L)";
      addMethodByMethodInfoField(addTo, fieldName, init);
   }

   protected void addConByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "aop$classAdvisor$aop.resolveCallerConstructorInfoAsWeakReference(" + callingHash + "L, \"" + classname + "\", " + calledHash + "L)";
      addConByMethodInfoField(addTo, fieldName, init);
   }

   protected void addConByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "aop$classAdvisor$aop.resolveConstructorCallerConstructorInfoAsWeakReference(" + addTo.getName() +  ".class, " + callingIndex + ", \"" + classname + "\", " + calledHash + "L)";
      addConByConInfoField(addTo, fieldName, init);
   }

   protected void addMethodByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "aop$classAdvisor$aop.resolveConstructorCallerMethodInfoAsWeakReference(" + addTo.getName() + ".class, " + callingIndex + ", \"" + classname + "\", " + calledHash + "L)";
      addMethodByConInfoField(addTo, fieldName, init);
   }

   protected boolean addInfoAsWeakReference()
   {
      return true;
   }
   
   protected boolean markInfoAsSynthetic()
   {
      return true;
   }
}
