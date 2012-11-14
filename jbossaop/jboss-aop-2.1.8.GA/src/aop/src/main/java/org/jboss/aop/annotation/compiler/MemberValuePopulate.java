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
import javassist.bytecode.annotation.MemberValueVisitor;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class MemberValuePopulate implements MemberValueVisitor
{
   private String value;

   public MemberValuePopulate(String value)
   {
      this.value = value;
   }

   public void visitAnnotationMemberValue(AnnotationMemberValue annotationMemberValue)
   {
      throw new RuntimeException("NOT IMPLEMENTED");
   }

   public void visitArrayMemberValue(ArrayMemberValue arrayMemberValue)
   {
      throw new RuntimeException("NOT IMPLEMENTED");
   }

   public void visitBooleanMemberValue(BooleanMemberValue booleanMemberValue)
   {
      Boolean bool = new Boolean(value);
      booleanMemberValue.setValue(bool.booleanValue());
   }

   public void visitByteMemberValue(ByteMemberValue byteMemberValue)
   {
      byteMemberValue.setValue(Byte.parseByte(value));
   }

   public void visitCharMemberValue(CharMemberValue charMemberValue)
   {
      charMemberValue.setValue(value.charAt(0));
   }

   public void visitDoubleMemberValue(DoubleMemberValue doubleMemberValue)
   {
      doubleMemberValue.setValue(Double.parseDouble(value));
   }

   public void visitEnumMemberValue(EnumMemberValue enumMemberValue)
   {
      int index = value.lastIndexOf('.');
      if (index == -1) throw new RuntimeException("Enum must be fully qualified: " + value);
      String className = value.substring(0, index);
      String en = value.substring(index + 1);
      enumMemberValue.setType(className);
      enumMemberValue.setValue(en);
   }

   public void visitFloatMemberValue(FloatMemberValue floatMemberValue)
   {
      floatMemberValue.setValue(Float.parseFloat(value));
   }

   public void visitIntegerMemberValue(IntegerMemberValue integerMemberValue)
   {
      integerMemberValue.setValue(Integer.parseInt(value));
   }

   public void visitLongMemberValue(LongMemberValue longMemberValue)
   {
      longMemberValue.setValue(Long.parseLong(value));
   }

   public void visitShortMemberValue(ShortMemberValue shortMemberValue)
   {
      shortMemberValue.setValue(Short.parseShort(value));
   }

   public void visitStringMemberValue(StringMemberValue stringMemberValue)
   {
      stringMemberValue.setValue(value);
   }

   public void visitClassMemberValue(ClassMemberValue classMemberValue)
   {
      if (value.endsWith(".class"))
      {
         value = value.substring(0, value.indexOf(".class"));
      }
      classMemberValue.setValue(value);
   }
}
