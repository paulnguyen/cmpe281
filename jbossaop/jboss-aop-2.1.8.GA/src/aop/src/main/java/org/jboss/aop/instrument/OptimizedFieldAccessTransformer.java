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
public class OptimizedFieldAccessTransformer extends FieldAccessTransformer
{

   public OptimizedFieldAccessTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void doBuildFieldWrappers(CtClass clazz, CtField field, int fieldIndex, boolean shouldReplaceArrayAccess, JoinpointClassification classificationGet, JoinpointClassification classificationSet) throws NotFoundException, CannotCompileException
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

   /**
    * Returns the body of the optimized wrapper method.
    * @param clazz the class declaring <code>field</code>.
    * @param field the field whose joinpoint wrapper body will be returned.
    * @param get indicates if the wrapper is a field read wrapper or a field
    * write wrapper.
    * @param index the <code>field</code> index.
    * @return the optimized wrapper body.
    */
   protected String getWrapperBody(CtClass clazz, CtField field, boolean get, int index)
           throws NotFoundException, CannotCompileException
   {
      if (get)
      {
         return getReadWrapperBody(clazz, field, index);
      }
      //TODO: set replaceArrayAccess=false as default, must be verified.
      return getWriteWrapperBody(clazz, field, false, index);
   }

   /**
    * Returns the body of the optimized wrapper method.
    * @param clazz the class declaring <code>field</code>.
    * @param field the field whose read joinpoint wrapper body will be returned.
    * @param index the <code>field</code> index.
    * @return the optimized wrapper body.
    */
   private String getReadWrapperBody(CtClass clazz, CtField field, int index)
           throws NotFoundException, CannotCompileException
   {
      String wrappedName = field.getName();

      String optimizedInvocation;
      try
      {
         optimizedInvocation = OptimizedFieldInvocations.getOptimizedInvocationClassName(clazz, field, true);
               optimizedInvocation = optimizedInvocation.substring(optimizedInvocation.lastIndexOf('.') + 1);
               optimizedInvocation = clazz.getName() + "$" + optimizedInvocation;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new CannotCompileException(e);
      }

      String infoName = getFieldReadInfoFieldName(field.getName());
      boolean isStatic = javassist.Modifier.isStatic(field.getModifiers());

      String code;

      if (!isStatic)
      {
         code =
         "{ "
         + "   " + fieldInfoFromWeakReference("info", infoName)
         + "    org.jboss.aop.ClassInstanceAdvisor instAdv = (org.jboss.aop.ClassInstanceAdvisor)((org.jboss.aop.InstanceAdvised)$1)._getInstanceAdvisor();"
         + "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors(); "
         + "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null || (instAdv != null && instAdv.hasInstanceAspects))"
         + "    { "
         + "       if (instAdv != null) "
         + "       { "
         + "          interceptors = instAdv.getInterceptors(interceptors); "
         + "       } "
         + "       " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(" + Instrumentor.HELPER_FIELD_NAME + ".getAdvisedFields()[" + index + "]," + index + ", interceptors); "
         + "       invocation.setTargetObject($1); "
         + "       invocation.typedTargetObject = (" + clazz.getName() + ")$1; "
         + "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); "
         + "       return ($r)invocation.invokeNext(); "
         + "    } "
         + "    else "
         + "    {"
         + "       return ((" + clazz.getName() + ")$1)." + wrappedName + ";"
         + "    }"
         + "}";
      }
      else
      {
         code =
         "{ "
         + "    org.jboss.aop.advice.Interceptor[] interceptors = " + Instrumentor.HELPER_FIELD_NAME + ".getFieldReadInfos()[" + index + "].getInterceptors(); "
         + "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null) "
         + "    { "
         + "    " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(" + Instrumentor.HELPER_FIELD_NAME + ".getAdvisedFields()[" + index + "]," + index + ", interceptors); "
         + "       invocation.setTargetObject($1); "
         + "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); "
         + "       return ($r)invocation.invokeNext(); "
         + "    } "
         + "    else "
         + "    {"
         + "       return " + clazz.getName() + "." + wrappedName + ";"
         + "    }"
         + "}";
      }
      return code;
   }

   /**
    * Returns the body of the optimized wrapper method.
    * @param clazz the class declaring <code>field</code>.
    * @param field the field whose write joinpoint wrapper body will be returned.
    * @param index the <code>field</code> index.
    * @return the optimized wrapper body.
    */
   private String getWriteWrapperBody(CtClass clazz, CtField field, boolean shouldReplaceArrayAccess, int index)
           throws NotFoundException, CannotCompileException
   {
      String wrappedName = field.getName();

      String optimizedInvocation;
      try
      {
         optimizedInvocation = OptimizedFieldInvocations.getOptimizedInvocationClassName(clazz, field, false);
            optimizedInvocation = optimizedInvocation.substring(optimizedInvocation.lastIndexOf('.') + 1);
            optimizedInvocation = clazz.getName() + "$" + optimizedInvocation;
      }
      catch (Exception e)
      {
         throw new CannotCompileException(e);
      }

      final String infoName = getFieldWriteInfoFieldName(field.getName());
      final boolean isStatic = javassist.Modifier.isStatic(field.getModifiers());
      String code;
      if (!isStatic)
      {
         String targetString = "((" + clazz.getName() + ")$1)";
         String fieldString = targetString + "." + field.getName();
         code =
         "{ "
         + "   " + getArrayWriteRegistration(shouldReplaceArrayAccess, targetString, field, fieldString, "$2")
         + "   " + fieldInfoFromWeakReference("info", infoName)
         + "    org.jboss.aop.ClassInstanceAdvisor instAdv = (org.jboss.aop.ClassInstanceAdvisor)((org.jboss.aop.InstanceAdvised)$1)._getInstanceAdvisor();"
         + "    org.jboss.aop.advice.Interceptor[] interceptors = info.getInterceptors();"
         + "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null || (instAdv != null && instAdv.hasInstanceAspects)) "
         + "    { "
         + "       if (instAdv != null) "
         + "       { "
         + "          interceptors = instAdv.getInterceptors(interceptors); "
         + "       } "
         + "       " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(" + Instrumentor.HELPER_FIELD_NAME + ".getAdvisedFields()[" + index + "]," + index + ", ($w)$2" + ", interceptors); "
         + "       invocation.setTargetObject($1); "
         + "       invocation.typedTargetObject = (" + clazz.getName() + ")$1; "
         + "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); "
         + "       invocation.invokeNext(); "
         + "    } "
         + "    else "
         + "    {"
         + "       ((" + clazz.getName() + ")$1)." + wrappedName + "=$2" + ";"
         + "    }"
         + "}";
      }
      else
      {
         String targetString = "java.lang.Class.forName(\"" + clazz.getName() + "\")";
         String fieldString = clazz.getName() +  "." + field.getName();
         code =
         "{ "
         + "    " + getArrayWriteRegistration(shouldReplaceArrayAccess, targetString, field, fieldString, "$2")
         + "    org.jboss.aop.advice.Interceptor[] interceptors = " + Instrumentor.HELPER_FIELD_NAME + ".getFieldWriteInfos()[" + index + "].getInterceptors(); "
         + "    if (interceptors != (org.jboss.aop.advice.Interceptor[])null) "
         + "    { "
         + "       " + optimizedInvocation + " invocation = new " + optimizedInvocation + "(" + Instrumentor.HELPER_FIELD_NAME + ".getAdvisedFields()[" + index + "]," + index + ", ($w)$2" + ", interceptors); "
         + "       invocation.setTargetObject($1); "
         + "       invocation.setAdvisor(" + Instrumentor.HELPER_FIELD_NAME + "); "
         + "       invocation.invokeNext(); "
         + "    } "
         + "    else "
         + "    {"
         + "       " + clazz.getName() + "." + wrappedName + "=$2" + ";"
         + "    }"
         + "}";
      }
      return code;
   }
   
   
   private void buildWrappers(CtClass clazz, CtField field, boolean shouldReplaceArrayAccess, boolean doGet, boolean doSet, int index) throws NotFoundException, CannotCompileException
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
   
   protected void replaceFieldAccessInternally(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index) throws CannotCompileException
   {
      OptimizedFieldAccessExprEditor expr = new OptimizedFieldAccessExprEditor(clazz, field, doGet, doSet, index);
      clazz.instrument(expr);
   }

   protected class OptimizedFieldAccessExprEditor extends FieldAccessExprEditor
   {
      public OptimizedFieldAccessExprEditor(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index)
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
   }

   
}
