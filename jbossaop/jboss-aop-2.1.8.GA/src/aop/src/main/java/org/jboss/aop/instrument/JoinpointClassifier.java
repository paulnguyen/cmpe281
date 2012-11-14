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

import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutInfo;

/**
 * This class performs the joinpoint classifications. It is responsible for classifying
 * a joinpoint into:<ul>
 * <li> NOT_INSTRUMENTED: the joinpoint mustn't be instrumented.</li>
 * <li> PREPARED: means that the joinpoint containing class must be instrumented (class
 * signature change) before it gets loaded. This step makes the runtime wrapping instrumentation
 * feasible.</li>
 * <li> WRAPPED: the joinpoint must be submitted
 * to wrapping instrumentation, i.e., it must be wrapped inside a block code responsible for
 * intercepting the joinpoint.</li>
 * <li> DYNAMICALY_WRAPPED: equivalent to the previous one, except that this classification indicates
 * the joinpoint will be wrapped due only to bindings added dynamicaly..</li>
 * <ul>
 * @author Flavia Rainone
 * @see org.jboss.aop.instrument.JoinpointClassification
 */
public abstract class JoinpointClassifier
{
   /**
    * This interface encapsulates a matching method. This allows us to use instances
    * of <code>Matcher</code> as references to matching methods.
    */
   protected interface Matcher
   {
      /**
       * Checks if <code>pointcut</code> matches a joinpoint. The type of joinpoint this method
       * matches is an implementation detail.
       * 
       * @param pointcut the pointcut whose matching will be tested.
       * @param advisor advisor associated with the class declaring <code>member</code>.
       * @param member class member related to the joinpoint.
       * @return <code>true</code> if the <code>pointcut</code> matches the joinpoint.
       * @throws NotFoundException thrown if the matching of pointcut fails.
       */
      boolean matches(Pointcut pointcut, Advisor advisor, CtMember member) throws NotFoundException;
      
      /**
       * Returns a description of the joinpoint represented by {@code member}.
       *  
       * @param member a member to be matched by this matcher
       * @return       a description of the joinpoint to be matched by this matcher using
       *               {@code member} as a parameter
       * @see #matches(Pointcut, Advisor, CtMember)
       */
      String getJoinpointDescription(CtMember member);
   }
   
   /**
    * Matches a field reading joinpoint against a pointcut expression.
    */
   private Matcher fieldGetMatcher = new Matcher()
   {
      public boolean matches(Pointcut pointcut, Advisor advisor, CtMember member) throws NotFoundException
      {
         return pointcut.matchesGet(advisor, (CtField) member);
      }
      
      public String getJoinpointDescription(CtMember member)
      {
         return member + " field read joinpoint";
      }
   };
   
   /**
    * Matches a field writing joinpoint against a pointcut expression.
    */
   private Matcher fieldSetMatcher = new Matcher()
   {
      public boolean matches(Pointcut pointcut, Advisor advisor, CtMember member) throws NotFoundException
      {
         return pointcut.matchesSet(advisor, (CtField) member);
      }
      
      public String getJoinpointDescription(CtMember member)
      {
         return member + " field write joinpoint";
      }
   };
   
   /**
    * Matches a constructor execution joinpoint against a pointcut expression.
    */
   private Matcher constructorMatcher = new Matcher()
   {
      public boolean matches(Pointcut pointcut, Advisor advisor, CtMember member) throws NotFoundException
      {
         return pointcut.matchesExecution(advisor, (CtConstructor) member);
      }
      
      public String getJoinpointDescription(CtMember member)
      {
         return member + " constructor execution joinpoint";
      }
   };

   /**
    * Matches a pointcut with a method execution joinpoint.
    */
   private Matcher methodMatcher = new Matcher()
   {
      public boolean matches(Pointcut pointcut, Advisor advisor, CtMember member) throws NotFoundException
      {
         return pointcut.matchesExecution(advisor, (CtMethod) member);
      }
      
      public String getJoinpointDescription(CtMember member)
      {
         return member + " method execution joinpoint";
      }
   };

   protected interface BindingCollectionAccessor
   {
      Collection<Pointcut> getPointcuts(Advisor advisor);
      Collection<PointcutInfo> getPointcutInfos(Advisor advisor);
   }
   
   private BindingCollectionAccessor fieldGetAccessor = new BindingCollectionAccessor()
   {
      public Collection<PointcutInfo> getPointcutInfos(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getFieldReadPointcutInfos();
      }

      public Collection<Pointcut> getPointcuts(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getFieldReadPointcuts();
      }
   };

   private BindingCollectionAccessor fieldSetAccessor = new BindingCollectionAccessor()
   {
      public Collection<PointcutInfo> getPointcutInfos(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getFieldWritePointcutInfos();
      }

      public Collection<Pointcut> getPointcuts(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getFieldWritePointcuts();
      }
   };
   
   private BindingCollectionAccessor constructorAccessor = new BindingCollectionAccessor()
   {
      public Collection<PointcutInfo> getPointcutInfos(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getConstructorExecutionPointcutInfos();
      }

      public Collection<Pointcut> getPointcuts(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getConstructorExecutionPointcuts();
      }
   };

   private BindingCollectionAccessor methodAccessor = new BindingCollectionAccessor()
   {
      public Collection<PointcutInfo> getPointcutInfos(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getMethodExecutionPointcutInfos();
      }

      public Collection<Pointcut> getPointcuts(Advisor advisor)
      {
         return advisor.getManager().getBindingCollection().getMethodExecutionPointcuts();
      }
   };
   
   /**
    * Classifies a joinpoint.
    * Subclasses must implement this method, which contains the
    * joinpoint classification algorithm.
    * @param member the member associated with the joinpoint to be classified.
    * @param advisor the advisor associated with <code>member</code> declaring class.
    * @param joinpointMatcher this matcher must be used to find out if a 
    * pointcut matches the joinpoint.
    * @return the joinpoint classification.
    * @throws NotFoundException thrown if the matching of pointcuts fails.
    */
   protected abstract JoinpointClassification classifyJoinpoint(CtMember member, Advisor advisor, Matcher joinpointMatcher, BindingCollectionAccessor bindingCollectionAccessor) throws NotFoundException;
   
   /**
    * Classifies the reading of <code>field</code> value.
    * @param field the field whose reading joinpoint is being classified.
    * @param advisor the advisor related to the class declaring <code>field</code>.
    * @return the classification of <code>field</code> read.
    * @throws NotFoundException thrown if the matching of pointcuts fails.
    */
   public JoinpointClassification classifyFieldGet(CtField field, Advisor advisor) throws NotFoundException
   {
      return this.classifyJoinpoint(field, advisor, fieldGetMatcher, fieldGetAccessor);
   }
   
   /**
    * Classifies the writing of <code>field</code> value.
    * @param field the field whose writing joinpoint is being classified.
    * @param advisor the advisor related to the class declaring <code>field</code>.
    * @return the classification of <code>field</code> write.
    * @throws NotFoundException thrown if the matching of pointcuts fails.
    */
   public JoinpointClassification classifyFieldSet(CtField field, Advisor advisor) throws NotFoundException
   {
      return this.classifyJoinpoint(field, advisor, fieldSetMatcher, fieldSetAccessor);
   }
   
   /**
    * Classifies the execution of <code>cons</code>.
    * @param cons the constructor whose execution joinpoint is being classified.
    * @param advisor the advisor related to the class declaring <code>cons</code>.
    * @return the classification of <code>cons</code> execution.
    * @throws NotFoundException thrown if the matching of pointcuts fails.
    */
   public JoinpointClassification classifyConstructorExecution(CtConstructor cons, Advisor advisor) throws NotFoundException
   {
      return this.classifyJoinpoint(cons, advisor, constructorMatcher, constructorAccessor);
   }

   /**
    * Classifies the execution of <code>method</code>.
    * @param method the method whose execution joinpoint is being classified.
    * @param advisor the advisor related to the class declaring <code>method</code>.
    * @return the classification of <code>method</code> execution.
    * @throws NotFoundException thrown if the matching of pointcuts fails.
    */
   public JoinpointClassification classifyMethodExecution(CtMethod method, Advisor advisor) throws NotFoundException
   {
      return this.classifyJoinpoint(method, advisor, methodMatcher, methodAccessor);
   }
}