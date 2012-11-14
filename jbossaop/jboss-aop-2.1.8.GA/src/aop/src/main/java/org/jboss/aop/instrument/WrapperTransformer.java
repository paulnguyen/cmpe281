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

import javassist.CtMember;

/**
 * This class is responsible for wrapping joinpoints.
 * In this context, wrapping means just changing a joinpoint status according
 * to it's wrapping state.
 * A joinpoint is identified by a class member and a transformation text, because
 * there may be more than one joinpoint associated with a classe member, and
 * whe have to differentiate them (pick, for exemple, the field read and field write
 * joinpoints; in this case we have two joinponts associated with a single field).
 * When more than a joinpont is associated with a class member, we say that there is
 * more than one transformation type available to be applied to this class member.
 * The transformation types available to a member are identified by a <code>String</code>
 * array passed to <code>WrapperTransformer</code> constructor.
 * There must be a <code>WrapperTransformer</code> instance for each transformer. That
 * means that when we talk about transformation types available to a class member, we mean
 * the member associated with the transformer (for exemple, in the case of <code>
 * ConstructorExecutionTransformer</code>, the member is a constructor).
 * @author Flavia Rainone
 */
public class WrapperTransformer
{

   private static final byte UNWRAPPED = (byte) 0;

   private static final byte WRAPPED = (byte) 1;

   private static final byte NOT_PREPARED = (byte) 2;

   private static final String WRAPPER_STATUS_ATTRIBUTE =
      "aop$wrapperStatus$aop";

   /**
    * May be used as the <code>transformations</code> array in the constructor, if a class
    * member will be affected by only one type of transformation. In this case, a joinpoint
    * can be identified only by a class member.
    */
   protected static final String[] SINGLE_TRANSFORMATION = new String[] {WRAPPER_STATUS_ATTRIBUTE};
   /**
    * Index that identifies the single transformation contained in <code>SINGLE_TRANSFORMATION</code>.
    * If <code>SINGLE_TRANSFORMATION</code> was passed as the <code>transformations</code>
    * argument to the constructor, this variable has to be used as the transformation index value
    * in the <code>WrapperTransformer</code> methods calls. 
    */
   protected static final int SINGLE_TRANSFORMATION_INDEX = 0;
   
   private String[] transformationsStatus;
   
   /**
    * Creates a wrapper transformer.
    * @param transformations list of all transformations that may be applied
    * to a joinpoint. It may be <code>SINGLE_TRANSFORMATION</code> if
    * a class member will be affected by only one type of transformation (i. e. if
    * a joinpont can be identified only by the class member).
    */
   public WrapperTransformer(String[] transformations)
   {
      if (transformations == SINGLE_TRANSFORMATION)
      {
         this.transformationsStatus = transformations;
      }
      else
      {
         this.transformationsStatus = new String[transformations.length];
         for (int i = 0; i < transformations.length; i++)
         {
            transformationsStatus[i] = WRAPPER_STATUS_ATTRIBUTE + "$" + transformations[i];
         }
      }
   }
   
   /**
    * Prepares the joinpoint for wrapping.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint to be prepared.
    */
   public void prepareForWrapping(final CtMember member, final int transformationIndex) {
      if (getWrapperStatusAttribute(member, transformationIndex) != NOT_PREPARED)
      {
         throw new RuntimeException("Member already prepared:" + member);
      }
      this.setWrapperStatusAttribute(member, transformationIndex, UNWRAPPED);
   }

   /**
    * Wraps the joinpoint.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint to be wrapped.
    */
   public void wrap(CtMember member, int transformationIndex) {
      byte wrapperStatus = getWrapperStatusAttribute(member, transformationIndex);
      if (wrapperStatus != UNWRAPPED)
      {
         String errorMessage = "Member not unwrapped: " + member.getName() + "." +
         member.getDeclaringClass().getName();
         errorMessage += "\nMember status: ";
         errorMessage += (wrapperStatus == NOT_PREPARED)? "NOT_PREPARED" : "WRAPPED";
         
         throw new RuntimeException(errorMessage);
      }
      setWrapperStatusAttribute(member, transformationIndex, WRAPPED);
   }
   
   /**
    * Unwraps the joinpoint.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint to be unwrapped.
    */
   public void unwrap(CtMember member, int transformationIndex) {
      byte wrapperStatus = getWrapperStatusAttribute(member, transformationIndex);
      if (wrapperStatus != WRAPPED)
      {
         String errorMessage = "Member not wrapped: " + member.getName() + "." +
         member.getDeclaringClass().getName();
         errorMessage += "\nMember status: ";
         errorMessage += (wrapperStatus == NOT_PREPARED)? "NOT_PREPARED" : "UNWRAPPED";
         throw new RuntimeException(errorMessage);
      }
      setWrapperStatusAttribute(member, transformationIndex, UNWRAPPED);
   }
   
   /**
    * Checks if the joinpoint is not prepared.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint whose status will be checked.
    */
   public boolean isNotPrepared(CtMember member, int transformationIndex)
   {
      byte wrapperStatus = getWrapperStatusAttribute(member, transformationIndex);
      return wrapperStatus == NOT_PREPARED;
   }

   /**
    * Checks if the joinpoint is wrapped.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint whose status will be checked.
    */
   public boolean isWrapped(CtMember member, int transformationIndex)
   {
      byte wrapperStatus = getWrapperStatusAttribute(member, transformationIndex);
      return wrapperStatus == WRAPPED;
   }
   
   /**
    * Returns the joinpoint wrapper status, which may be <code>WRAPPED
    * </code>,<code>UNWRAPPED</code> or <code>NOT_PREPARED</code>.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint whose status will be returned.
    * @return the joinpoint wrapper status.
    */
   private byte getWrapperStatusAttribute(CtMember member, int transformationIndex)
   {
      byte[] attributeValue = member.getAttribute(transformationsStatus[transformationIndex]);
      if (attributeValue == null)
      {
         return NOT_PREPARED;
      }
      return attributeValue[0];
   }

   /**
    * Sets the joinpoint wrapper status.
    * @param member the member associated with the joinpoint.
    * @param transformationIndex index of the transformation text which, along with
    * <code>member</code>, identifies the joinpoint whose status will be changed.
    * @param status the new joinpoint wrapper status, which may be <code>WRAPPED
    * </code>, <code>UNWRAPPED</code> or <code>NOT_PREPARED</code>.
    */
   private void setWrapperStatusAttribute(CtMember member, int transformationIndex, byte status)
   {
      member.setAttribute(transformationsStatus[transformationIndex], new byte[] {status});
   }
}