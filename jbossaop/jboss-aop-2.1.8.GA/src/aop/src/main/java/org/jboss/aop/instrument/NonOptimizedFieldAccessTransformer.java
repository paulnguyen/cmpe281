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
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.FieldAccess;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class NonOptimizedFieldAccessTransformer extends FieldAccessTransformer
{

   public NonOptimizedFieldAccessTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void doBuildFieldWrappers(CtClass clazz, CtField field, int fieldIndex, boolean shouldReplaceArrayAccess, JoinpointClassification classificationGet, JoinpointClassification classificationSet)
   throws NotFoundException, CannotCompileException
   {
      instrumentor.setupBasics(clazz);
      boolean wrappedGet = classificationGet.equals(JoinpointClassification.WRAPPED);
      boolean wrappedSet = classificationSet.equals(JoinpointClassification.WRAPPED);
      int mod = getStaticModifiers(field);

      //Create placeholder static wrappers, since without these methods replaceFieldAccessInternally()
      //will not compile.
      //If we add the actual static wrappers before calling replaceFieldAccessInternally()
      //field access done in the inner invocation classes as well as in the static wrappers
      //is replaced with a call to the wrapper instead, which means infinite recursion
      buildWrapperPlaceHolders(clazz, field, isPrepared(classificationGet), isPrepared(classificationSet), mod);
      try {
         if (isPrepared(classificationGet))
         {
            addFieldReadInfoFieldWithAccessors(Modifier.PRIVATE | Modifier.STATIC, clazz, field);
            OptimizedFieldInvocations.createOptimizedInvocationClass(instrumentor, clazz, field, true);
            // prepareForWrapping
            wrapper.prepareForWrapping(field, GET_INDEX);
         }

         if (isPrepared(classificationSet))
         {
            addFieldWriteInfoField(Modifier.PRIVATE | Modifier.STATIC, clazz, field);
            OptimizedFieldInvocations.createOptimizedInvocationClass(instrumentor, clazz, field, false);
            // prepareForWrapping
            wrapper.prepareForWrapping(field, SET_INDEX);
         }
      } catch (Exception e)
      {
         throw new CannotCompileException(e);
      }

      // wrap
      if (wrappedGet)
      {
         wrapper.wrap(field, GET_INDEX);
         if (classificationGet.equals(JoinpointClassification.DYNAMICALY_WRAPPED))
         {
            instrumentor.dynamicTransformationObserver.fieldReadDynamicalyWrapped(field);
         }
      }
      if (wrappedSet)
      {
         wrapper.wrap(field, SET_INDEX);
         if (classificationSet.equals(JoinpointClassification.DYNAMICALY_WRAPPED))
         {
            instrumentor.dynamicTransformationObserver.fieldWriteDynamicalyWrapped(field);
         }
      }

      // executeWrapping
      replaceFieldAccessInternally(clazz, field, wrappedGet, wrappedSet, fieldIndex);
      buildWrappers(clazz, field, shouldReplaceArrayAccess, wrappedGet, wrappedSet, fieldIndex);

   }

   private void buildWrappers(CtClass clazz, CtField field, boolean shouldReplaceArrayAccess, boolean doGet, boolean doSet, int index)
   throws NotFoundException, CannotCompileException
   {
      if (doGet)
      {
         String code = getReadWrapperBody(clazz, field, index);
         CtMethod method = clazz.getDeclaredMethod(fieldRead(field.getName()));
         method.setBody(code);
      }
      if (doSet)
      {
         String code = getWriteWrapperBody(clazz, field, shouldReplaceArrayAccess, index);
         CtMethod method = clazz.getDeclaredMethod(fieldWrite(field.getName()));
         method.setBody(code);
      }
   }

   protected String getWrapperBody(CtClass clazz, CtField field, boolean get, int index) throws NotFoundException, CannotCompileException
   {
      if (get)
      {
         return getReadWrapperBody(clazz, field, index);
      }
//    TODO: set replaceArrayAccess=false as default, must be verified.
      return getWriteWrapperBody(clazz, field, false, index);
   }

   private String getReadWrapperBody(CtClass clazz, CtField field, int index)
   throws NotFoundException, CannotCompileException
   {
      String access = "";
      String instanceCheck = "";
      String name = field.getName();
      boolean isStatic = (javassist.Modifier.isStatic(field.getModifiers()));
      if (!isStatic)
      {
         access = "((" + clazz.getName() + ")$1).";
         instanceCheck = " || ((org.jboss.aop.ClassInstanceAdvisor)((org.jboss.aop.InstanceAdvised)$1)._getInstanceAdvisor()).hasInstanceAspects";
      }

      // read wrapper

         return
            "{ " +
            "    if (" + Instrumentor.HELPER_FIELD_NAME + ".hasAspects() " + instanceCheck + " ) " +
            "    { " +
            "       return ($r)" + Instrumentor.HELPER_FIELD_NAME + ".invokeRead($1, (int)" + (index) + "); " +
            "    } " +
            "    return " + access + name + "; " +
            "}";

   }

   private String getWriteWrapperBody(CtClass clazz, CtField field, boolean shouldReplaceArrayAccess, int index)
     throws NotFoundException, CannotCompileException
   {
      String name = field.getName();
      boolean isStatic = (javassist.Modifier.isStatic(field.getModifiers()));
      String access = "";
      String instanceCheck = "";
      String targetString;
      String fieldString;
      if (!isStatic)
      {
         access = "((" + clazz.getName() + ")$1).";
         instanceCheck = " || ((org.jboss.aop.ClassInstanceAdvisor)((org.jboss.aop.InstanceAdvised)$1)._getInstanceAdvisor()).hasInstanceAspects";
          targetString = "((" + clazz.getName() + ")$1)";
          fieldString = targetString + "." + field.getName();
      }
      else
      {
          targetString = "java.lang.Class.forName(\"" + clazz.getName() + "\")";
          fieldString = clazz.getName() +  "." + field.getName();
      }
      // write wrapper
      return
      "{ " +
      "    " + getArrayWriteRegistration(shouldReplaceArrayAccess, targetString, field, fieldString, "$2") +
      "    if (" + Instrumentor.HELPER_FIELD_NAME + ".hasAspects() " + instanceCheck + " ) " +
      "    { " +
      "       " + Instrumentor.HELPER_FIELD_NAME + ".invokeWrite($1, (int)" + (index) + ", ($w)$2); " +
      "    } " +
      "    else " +
      "    { " +
      "       " + access + name + " = $2; " +
      "    } " +
      "}";
   }


   protected void replaceFieldAccessInternally(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index) throws CannotCompileException
   {
      NonOptimizedFieldAccessExprEditor expr = new NonOptimizedFieldAccessExprEditor(clazz, field, doGet, doSet, index);
      clazz.instrument(expr);
   }


   protected class NonOptimizedFieldAccessExprEditor extends FieldAccessExprEditor
   {
      public NonOptimizedFieldAccessExprEditor(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index)
      {
         super(clazz, field, doGet, doSet, index);
      }

      protected void replaceRead(FieldAccess fieldAccess) throws CannotCompileException
      {
         if (fieldAccess.isStatic())
         {
            String code =
                    "    { " +
                    "       $_ = ($r)" + fieldRead(field.getName()) + "(null);" +
                    "    } " +
                     "";
            fieldAccess.replace(code);
         }
         else
         {
            String code =
                    "    { " +
                    "       $_ = ($r)" + fieldRead(field.getName()) + "($0);" +
                    "    } " +
                    "";
            fieldAccess.replace(code);
         }
      }

      protected void replaceWrite(FieldAccess fieldAccess) throws CannotCompileException
      {
         String fieldWrite = fieldWrite(field.getName());
         if (fieldAccess.isStatic())
         {
            String code =
                    "    { " +
                    "       " + fieldWrite + "(null, $1);" +
                    "    } " +
                    "";
            fieldAccess.replace(code);

         }
         else
         {
            String code =
                    "    { " +
                    "       " + fieldWrite + "($0, $1);" +
                    "    } " +
                    "";
            fieldAccess.replace(code);
         }
      }

   }//End Inner class FieldAccessExprEditor

}
