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
package org.jboss.aop.annotation.compiler;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.MemberValueVisitor;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 *
 **/
public class MemberValueCreation implements MemberValueVisitor
{
   public MemberValue value;
   private ConstPool cp;

   public MemberValueCreation(ConstPool cp)
   {
      this.cp = cp;
   }

   public void visitAnnotationMemberValue(AnnotationMemberValue annotationMemberValue)
   {
      value = new AnnotationMemberValue(cp);
   }

   public void visitArrayMemberValue(ArrayMemberValue arrayMemberValue)
   {
      throw new RuntimeException("NOT IMPLEMENTED");
   }

   public void visitBooleanMemberValue(BooleanMemberValue booleanMemberValue)
   {
      value = new BooleanMemberValue(cp);
   }

   public void visitByteMemberValue(ByteMemberValue byteMemberValue)
   {
      value = new ByteMemberValue(cp);
   }

   public void visitCharMemberValue(CharMemberValue charMemberValue)
   {
      value = new CharMemberValue(cp);
   }

   public void visitDoubleMemberValue(DoubleMemberValue doubleMemberValue)
   {
      value = new DoubleMemberValue(cp);
   }

   public void visitEnumMemberValue(EnumMemberValue enumMemberValue)
   {
      value = new EnumMemberValue(cp);
   }

   public void visitFloatMemberValue(FloatMemberValue floatMemberValue)
   {
      value = new FloatMemberValue(cp);
   }

   public void visitIntegerMemberValue(IntegerMemberValue integerMemberValue)
   {
      value = new IntegerMemberValue(cp);
   }

   public void visitLongMemberValue(LongMemberValue longMemberValue)
   {
      value = new LongMemberValue(cp);
   }

   public void visitShortMemberValue(ShortMemberValue shortMemberValue)
   {
      value = new ShortMemberValue(cp);
   }

   public void visitStringMemberValue(StringMemberValue stringMemberValue)
   {
      value = new StringMemberValue(cp);
   }

   public void visitClassMemberValue(ClassMemberValue classMemberValue)
   {
      value = new ClassMemberValue(cp);
   }
}
