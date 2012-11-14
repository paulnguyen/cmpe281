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

import java.util.Collection;

import javassist.CtMember;
import javassist.NotFoundException;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * This joinpoint classifier is anaware of differences between <code>
 * PREPARED</code> and <code>WRAPPED</code> classifications.
 * It classifies a joinpoint either as something that must be instrumented
 * or something that mustn't, without caring about preparation.
 * Whenever a joinpoint must be instrumented, it is classified as <code>
 * WRAPPED</code>; by the other hand, whenever
 * it is should not be instrumented, it is classified as <code>NOT_INSTRUMENTED
 * </code>
 * @see JoinpointClassifier
 * @see JoinpointClassification
 * @author Flavia Rainone
 */
public class JoinpointSimpleClassifier extends JoinpointClassifier
{
   private static final AOPLogger logger = AOPLogger.getLogger(JoinpointSimpleClassifier.class);
   /**
    * Classifies the execution of a joinpoint. The joinpoint being classified
    * is identified by <code>matcher</code>.
    * If the joinpoint is matched by one or more pointcuts, then
    * it is classified as <code>JoinpointClassification.WRAPPED</code>. Otherwise,
    * it is classified as <code>JoinpointClassification.NOT_INSTRUMENTED</code>.
    * @see org.jboss.aop.instrument.JoinpointClassifier#classifyJoinpoint(javassist.CtMember, org.jboss.aop.Advisor, org.jboss.aop.instrument.JoinpointClassifier.Matcher)
    */
   protected JoinpointClassification classifyJoinpoint(CtMember member, Advisor advisor, Matcher joinpointMatcher, BindingCollectionAccessor bindingCollectionAccessor) throws NotFoundException
   {
      Collection<Pointcut> pointcuts = bindingCollectionAccessor.getPointcuts(advisor);
      for (Pointcut pointcut : pointcuts)
      {
         if (joinpointMatcher.matches(pointcut, advisor, member))
         {
            if (AspectManager.verbose && logger.isDebugEnabled())
            {
               logger.debug(joinpointMatcher.getJoinpointDescription(member) + " matches pointcut: " + pointcut.getExpr());
            }
            return JoinpointClassification.WRAPPED;
         }
      }
      if (AspectManager.verbose && logger.isDebugEnabled())
      {
         logger.debug(joinpointMatcher.getJoinpointDescription(member)+ " matches no pointcuts");
      }
      return JoinpointClassification.NOT_INSTRUMENTED;
   }
}
