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
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Adds CallerInfo fields to generated advisor 
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class GeneratedAdvisorCallerInfoAdder extends CallerInfoAdder
{
   public GeneratedAdvisorCallerInfoAdder(Instrumentor instrumentor)
   {
      super(instrumentor, Modifier.PROTECTED);
   }

   protected void addMethodByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      // addTo is the advisor class
      String init = "resolveCallerMethodInfo(" + callingHash + "L, \"" + classname + "\", " + calledHash + "L)";
      addMethodByMethodInfoField(addTo, fieldName, null);
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseCallerInfoField(fieldName, init);
   }
   
   protected void addConByMethodInfoField(CtClass addTo, String fieldName, long callingHash, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "resolveCallerConstructorInfo(" + callingHash + "L, \"" + classname + "\", " + calledHash + "L)";
      addConByMethodInfoField(addTo, fieldName, null);
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseCallerInfoField(fieldName, init);
   }

   protected void addConByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "resolveConstructorCallerConstructorInfo(" + callingClassName +  ".class, " + callingIndex + ", \"" + classname + "\", " + calledHash + "L)";
      addConByConInfoField(addTo, fieldName, null);
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseCallerInfoField(fieldName, init);
   }

   protected void addMethodByConInfoField(CtClass addTo, String fieldName, String callingClassName, int callingIndex, String classname, long calledHash) throws NotFoundException, CannotCompileException
   {
      String init = "resolveConstructorCallerMethodInfo(" + callingClassName +  ".class, " + callingIndex + ", \"" + classname + "\", " + calledHash + "L)";
      addMethodByConInfoField(addTo, fieldName, null);
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseCallerInfoField(fieldName, init);
   }

   protected boolean addInfoAsWeakReference()
   {
      return false;
   }

   
   protected boolean markInfoAsSynthetic()
   {
      return true;
   }
}
