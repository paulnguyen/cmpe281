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
package org.jboss.aop.pointcut;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.jboss.aop.Advisor;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public interface Pointcut
{
   String getName();

   boolean softMatch(Advisor advisor);

   boolean matchesExecution(Advisor advisor, CtMethod m) throws NotFoundException;
   boolean matchesExecution(Advisor advisor, CtConstructor c) throws NotFoundException;
   boolean matchesConstruction(Advisor advisor, CtConstructor c) throws NotFoundException;
   boolean matchesGet(Advisor advisor, CtField f) throws NotFoundException;
   boolean matchesSet(Advisor advisor, CtField f) throws NotFoundException;
   boolean matchesCall(Advisor callingAdvisor, MethodCall methodCall) throws NotFoundException;
   boolean matchesCall(Advisor callingAdvisor, NewExpr methodCall) throws NotFoundException;

   PointcutMethodMatch matchesExecution(Advisor advisor, Method m);
   boolean matchesExecution(Advisor advisor, Constructor<?> c);
   boolean matchesConstruction(Advisor advisor, Constructor<?> c);
   boolean matchesGet(Advisor advisor, Field f);
   boolean matchesSet(Advisor advisor, Field f);
   boolean matchesCall(Advisor advisor, AccessibleObject within, Class<?> calledClass, Method calledMethod);
   boolean matchesCall(Advisor advisor, AccessibleObject within, Class<?> calledClass, Constructor<?> calledCon);

   String getExpr();

}
