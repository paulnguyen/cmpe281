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
package org.jboss.test.aop.jdk15annotateddeclare;

import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.Aspect;
import org.jboss.aop.DeclareError;
import org.jboss.aop.DeclareWarning;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 46162 $
 */
@Aspect (scope=org.jboss.aop.advice.Scope.PER_VM)
public class DeclareAspect
{
   @DeclareError (expr="call(* org.jboss.test.aop.jdk15annotated.declare.POJO->someMethod()) AND withincode(* org.jboss.test.aop.jdk15annotated.declare.POJO->otherMethod())", msg="Should not happen")
   Pointcut error;

   @DeclareWarning (expr="call(* org.jboss.test.aop.jdk15annotated.declare.POJO->otherMethod()) AND withincode(* org.jboss.test.aop.jdk15annotated.declare.POJO->someMethod())", msg="Expected annotated warning")
   Pointcut warning;
}
