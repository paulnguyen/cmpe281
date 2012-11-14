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
import org.jboss.aop.pointcut.PointcutInfo;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * This class fully classifies joinpoints as <code>JoinpointClassification.PREPARED</code>
 * as <code>JoinpointClasification.WRAPPED</code> and as <code>
 * JoinpointClassification.DYNAMICALY_WRAPPED</code> according
 * to the pointcuts registered in <code>AspectManager</code>.
 * @see JoinpointClassifier
 * @see JoinpointClassification
 * @author Flavia Rainone
 */
public class JoinpointFullClassifier extends JoinpointClassifier
{
   private static final AOPLogger logger = AOPLogger.getLogger(JoinpointSimpleClassifier.class);
   
   /**
    * Classifies the execution of a joinpoint. The joinpoint being classified
    * is identified by <code>matcher</code>.
    * If the joinpoint is matched only with pointcuts not associated with bindings, then
    * the joinpoint is classified as <code>JoinpointClassification.PREPARED</code>. If it
    * is matched with one or more binding associated pointcuts, then it is classified
    * as <code>JoinpointClassification.WRAPPED</code>. If it is matched by only dynamicaly
    * added binding pointcuts, it is classified  as <code>
    * JoinpointClassification.DYNAMICALY_WRAPPED</code>. On the other
    * hand, if it is not matched by any pointcut at all, the joinpoint is classified as
    * <code>NOT_INSTRUMENTED</code>.
    * @see org.jboss.aop.instrument.JoinpointClassifier#classifyJoinpoint(javassist.CtMember, org.jboss.aop.Advisor, org.jboss.aop.instrument.JoinpointClassifier.Matcher)
    */
   protected JoinpointClassification classifyJoinpoint(CtMember member, Advisor advisor, Matcher joinpointMatcher, BindingCollectionAccessor bindingCollectionAccessor) throws NotFoundException
   {
      JoinpointClassification classification = JoinpointClassification.NOT_INSTRUMENTED;
      Collection<PointcutInfo> pointcuts = bindingCollectionAccessor.getPointcutInfos(advisor);
      for (PointcutInfo pointcutInfo : pointcuts)
      {
         // won't check matching of preparation pointcuts unnecessarily
         if (classification == JoinpointClassification.PREPARED && pointcutInfo.getBinding() == null)
         {
            continue;
         }
         Pointcut pointcut = pointcutInfo.getPointcut();
         if (joinpointMatcher.matches(pointcut, advisor, member)) {
            if (AspectManager.verbose && logger.isDebugEnabled())
            {
               logger.debug(joinpointMatcher.getJoinpointDescription(member) + " matches pointcut: " + pointcut.getExpr());
            }
            // only prepare if pointcut isn't associated with a binding
            if (pointcutInfo.getBinding() == null)
            {
               classification = JoinpointClassification.PREPARED;
            }
            else if (pointcutInfo.isDynamicAop())
            {
               classification = JoinpointClassification.DYNAMICALY_WRAPPED;
               synchronized (pointcutInfo)
               {
                  pointcutInfo.setDynamicAop(false);
               }
            } 
            else
            {
               classification = JoinpointClassification.WRAPPED;
               // break. We can't get any better than this classification
               break;
            }
         }
      }
      if (AspectManager.verbose && logger.isDebugEnabled() && classification == JoinpointClassification.NOT_INSTRUMENTED)
      {
         logger.debug(joinpointMatcher.getJoinpointDescription(member)+ " matches no pointcuts");
      }
      return classification;
   }
}