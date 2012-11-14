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

import org.jboss.aop.util.JavassistMethodHashing;

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
public class GeneratedAdvisorFieldAccessTransformer extends FieldAccessTransformer
{

   public GeneratedAdvisorFieldAccessTransformer(Instrumentor instrumentor)
   {
      super(instrumentor);
   }

   protected void doBuildFieldWrappers(CtClass clazz, CtField field, int index, boolean shouldReplaceArrayAccess, JoinpointClassification classificationGet, JoinpointClassification classificationSet) throws NotFoundException, CannotCompileException
   {
      instrumentor.setupBasics(clazz);
      boolean wrappedGet = classificationGet.equals(JoinpointClassification.WRAPPED);
      boolean wrappedSet = classificationSet.equals(JoinpointClassification.WRAPPED);
      int mod = field.getModifiers();

      //Create placeholder static wrappers, since without these methods replaceFieldAccessInternally()
      //will not compile.
      //If we add the actual static wrappers before calling replaceFieldAccessInternally()
      //field access done in the inner invocation classes as well as in the static wrappers
      //is replaced with a call to the wrapper instead, which means infinite recursion
      buildWrapperPlaceHolders(clazz,
            field,
            isPrepared(classificationGet),
            isPrepared(classificationSet),
            mod);

      try
      {
         if (isPrepared(classificationGet))
         {
            addFieldReadInfoFieldToGeneratedAdvisor(field, index);
            // prepareForWrapping
            wrapper.prepareForWrapping(field, GET_INDEX);
         }

         if (isPrepared(classificationSet))
         {
            addFieldWriteInfoFieldToGeneratedAdvisor(field, index);
            // prepareForWrapping
            wrapper.prepareForWrapping(field, SET_INDEX);
         }
      }
      catch (Exception e)
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
      replaceFieldAccessInternally(clazz, field, wrappedGet, wrappedSet, index);
      buildWrappers(clazz, field, shouldReplaceArrayAccess, wrappedGet, wrappedSet, index);
   }

   protected String addFieldReadInfoFieldToGeneratedAdvisor(CtField field, int index)throws NotFoundException, CannotCompileException
   {
      CtClass genadvisor = getGenadvisor();
      String finame = addFieldReadInfoFieldWithAccessors(
            Modifier.PUBLIC,
            genadvisor,
            field);

      addReadJoinPoint(field, finame, index);

      long wrapperHash = JavassistMethodHashing.methodHash(
            field.getDeclaringClass().getDeclaredMethod(fieldRead(field.getName())));
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseFieldReadInfoField(finame, index, field.getName(), wrapperHash);
      return finame;
   }

   @Override
   protected boolean addInfoAsWeakReference()
   {
      return false;
   }

   @Override
   protected boolean markInfoAsSynthetic()
   {
      return false;
   }

   private void addReadJoinPoint(CtField field, String finame, int index) throws CannotCompileException, NotFoundException
   {
      CtClass joinpoint = createReadJoinPointClass(field, finame, index);
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
      CtField jpfield = new CtField(
            joinpoint,
            FieldJoinPointGenerator.getGeneratedJoinPointFieldName(field.getName(), true),
            genadvisor);
      jpfield.setModifiers(Modifier.PUBLIC);
      genadvisor.addField(jpfield);
   }

   private CtClass createReadJoinPointClass(CtField field, String finame, int index) throws CannotCompileException, NotFoundException
   {
      return FieldJoinPointGenerator.createReadJoinpointBaseClass((GeneratedAdvisorInstrumentor)instrumentor,
            field.getDeclaringClass(),
            field,
            finame,
            index);
   }

   protected String addFieldWriteInfoFieldToGeneratedAdvisor(CtField field, int index)throws NotFoundException, CannotCompileException
   {
      CtClass genadvisor = getGenadvisor();

      String finame = addFieldWriteInfoField(
            Modifier.PUBLIC,
            genadvisor,
            field);

      addWriteJoinPoint(field, finame, index);

      long wrapperHash = JavassistMethodHashing.methodHash(
            field.getDeclaringClass().getDeclaredMethod(fieldWrite(field.getName())));
      ((GeneratedAdvisorInstrumentor)instrumentor).initialiseFieldWriteInfoField(finame, index, field.getName(), wrapperHash);

      return finame;
   }

   private void addWriteJoinPoint(CtField field, String finame, int index) throws CannotCompileException, NotFoundException
   {
      CtClass joinpoint = createWriteJoinPointClass(field, finame, index);
      CtClass genadvisor = ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
      CtField jpfield = new CtField(
            joinpoint,
            FieldJoinPointGenerator.getGeneratedJoinPointFieldName(field.getName(), false),
            genadvisor);
      jpfield.setModifiers(Modifier.PUBLIC);
      genadvisor.addField(jpfield);
   }

   private CtClass createWriteJoinPointClass(CtField field, String finame, int index) throws CannotCompileException, NotFoundException
   {
      return FieldJoinPointGenerator.createWriteJoinpointBaseClass((GeneratedAdvisorInstrumentor)instrumentor,
            field.getDeclaringClass(),
            field,
            finame,
            index);
   }

   protected void buildWrapperPlaceHolders(CtClass clazz, CtField field, boolean doGet, boolean doSet, int mod)
   throws NotFoundException, CannotCompileException
   {
      super.buildWrapperPlaceHolders(clazz, field, doGet, doSet, getStaticModifiers(field));

      CtClass genadvisor = getGenadvisor();
      if (doGet)
      {
         @SuppressWarnings("unused")
         CtMethod rmethod = super.buildReadWrapperPlaceHolder(
               genadvisor,
               field,
               advisorFieldRead(genadvisor, field.getName()),
               Modifier.PROTECTED);

      }
      if (doSet)
      {
         @SuppressWarnings("unused")
         CtMethod wmethod = super.buildWriteWrapperPlaceHolder(
               genadvisor,
               field,
               advisorFieldWrite(genadvisor, field.getName()),
               Modifier.PROTECTED);

      }
   }

   public static String advisorFieldRead(CtClass genadvisor, String fieldName)
   {
      return genadvisor.getSimpleName() + "$" + fieldRead(fieldName);
   }

   public static String advisorFieldWrite(CtClass genadvisor, String fieldName)
   {
      return genadvisor.getSimpleName() + "$" + fieldWrite(fieldName);
   }

   protected String getWrapperBody(CtClass clazz, CtField field, boolean get, int fieldIndex) throws NotFoundException, CannotCompileException
   {
      if (get)
      {
         return getMainReadWrapperBody(clazz, field, fieldIndex);
      }
      return getMainWriteWrapperBody(clazz, field, fieldIndex);
   }

   protected void replaceFieldAccessInternally(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index) throws CannotCompileException
   {
      GeneratedAdvisorFieldAccessExprEditor expr = new GeneratedAdvisorFieldAccessExprEditor(clazz, field, doGet, doSet, index);
      clazz.instrument(expr);
   }

   private CtClass getGenadvisor()
   {
      return ((GeneratedAdvisorInstrumentor)instrumentor).getGenadvisor();
   }

//   private CtClass getGenInstanceAdvisor()
//   {
//      return ((GeneratedAdvisorInstrumentor)instrumentor).getGenInstanceadvisor();
//   }
//
   private String getAdvisorReadWrapperBody(CtClass clazz, CtField field, int index)
   throws NotFoundException, CannotCompileException
   {
      boolean isStatic = Modifier.isStatic(field.getModifiers());
      String code = null;
      String joinpointName = FieldJoinPointGenerator.getGeneratedJoinPointFieldName(field.getName(), true);
      String infoName = getFieldReadInfoFieldName(field.getName());
      if (isStatic)
      {
         code =
            "{" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
            "   try" +
            "   {" +
            "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "      {" +
            "         if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "         {" +
            "            super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
            "         }" +
            "      }" +
            "      if (" + joinpointName + " == null)" +
            "      { " +
            "         return " + clazz.getName() + "." + field.getName() + ";" +
            "      }" +
            "      else" +
            "      {" +
            "       " + MethodExecutionTransformer.getAopReturnStr(false) + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "();" +
            "      }" +
            "   } finally {" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
            "   }" +
            "}";
      }
      else
      {
         code =
            "{" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
            "   try" +
            "   {" +
            "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "      {" +
            "         if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "         {" +
            "            super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
            "         }" +
            "      }" +
            "      if (" + joinpointName + " == null)" +
            "      { " +
            "         return ((" + clazz.getName() + ")$1)." + field.getName() + ";" +
            "      }" +
            "      else" +
            "      {" +
            "       " + MethodExecutionTransformer.getAopReturnStr(false) + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "((" + clazz.getName() + ")$1);" +
            "      }" +
            "   } finally {" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
            "   }" +
            "}";
      }

      return code;
   }

   private String getAdvisorWriteWrapperBody(CtClass clazz, CtField field, int index, boolean shouldReplaceArrayAccess)
   throws NotFoundException, CannotCompileException
   {
      boolean isStatic = Modifier.isStatic(field.getModifiers());
      String code = null;
      String joinpointName = FieldJoinPointGenerator.getGeneratedJoinPointFieldName(field.getName(), false);
      String infoName = getFieldWriteInfoFieldName(field.getName());
      if (isStatic)
      {
         String targetString = "java.lang.Class.forName(\"" + clazz.getName() + "\")";
         String fieldString = clazz.getName() +  "." + field.getName();
         code =
            "{" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
            "   try" +
            "   {" +
            "      " + getArrayWriteRegistration(shouldReplaceArrayAccess, targetString, field, fieldString, "$2") +
            "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "      {" +
            "         if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "         {" +
            "            super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
            "         }" +
            "      }" +
            "      if (" + joinpointName + " == null)" +
            "      { " +
            "      " + fieldString + " = $2;" +
            "      }" +
            "      else" +
            "      {" +
            "      " + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "($2);" +
            "      }" +
            "   } finally {" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
            "   }" +
            "}";
      }
      else
      {
         String targetString = "((" + clazz.getName() + ")$1)";
         String fieldString = targetString + "." + field.getName();
         code =
            "{" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainLockCode(infoName) +
            "   try" +
            "   {" +
            "      " + getArrayWriteRegistration(shouldReplaceArrayAccess, targetString, field, fieldString, "$2") +
            "      if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "      {" +
            "         if (" + joinpointName + " == null && " + infoName + " != null && " + infoName + ".hasAdvices())" +
            "         {" +
            "            super." + JoinPointGenerator.GENERATE_JOINPOINT_CLASS + "(" + infoName + ");" +
            "         }" +
            "      }" +
            "      if (" + joinpointName + " == null)" +
            "      { " +
            "         " + fieldString + " = $2;" +
            "      }" +
            "      else" +
            "      {" +
            "      " + MethodExecutionTransformer.getAopReturnStr(false) + joinpointName + "." + JoinPointGenerator.INVOKE_JOINPOINT + "((" + clazz.getName() + ")$1, $2);" +
            "      }" +
            "   } finally {" +
            GeneratedAdvisorInstrumentor.generateInterceptorChainUnlockCode(infoName) +
            "   }" +
            "}";
      }

      return code;
   }


   private String getMainReadWrapperBody(CtClass clazz, CtField field, int index)
   throws NotFoundException, CannotCompileException
   {
      //Just delegate to method in advisor
      boolean isStatic = Modifier.isStatic(field.getModifiers());

      String advisor = isStatic ?
            "((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(clazz) + ")" + Instrumentor.HELPER_FIELD_NAME + ")" :
               "((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(clazz) + ")((" + clazz.getName() + ")$1)." + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")";

      return "return " + advisor + "." + advisorFieldRead(getGenadvisor(), field.getName()) + "($$);";
   }

   private String getMainWriteWrapperBody(CtClass clazz, CtField field, int index)
      throws NotFoundException, CannotCompileException
   {
      //Just delegate to method in advisor
      boolean isStatic = Modifier.isStatic(field.getModifiers());

      String advisor = isStatic ?
            "((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(clazz) + ")" + Instrumentor.HELPER_FIELD_NAME + ")" :
            "((" + GeneratedAdvisorInstrumentor.getAdvisorFQN(clazz) + ")((" + clazz.getName() + ")$1)." + GeneratedAdvisorInstrumentor.GET_CURRENT_ADVISOR + ")";

      return advisor + "." + advisorFieldWrite(getGenadvisor(), field.getName()) + "($$);";
   }



   private void buildWrappers(CtClass clazz, CtField field,  boolean shouldReplaceArrayAccess, boolean doGet, boolean doSet, int index) throws NotFoundException, CannotCompileException
   {
      if (doGet)
      {
         //Set wrapper code in advisor
         String code = getAdvisorReadWrapperBody(clazz, field, index);
         CtMethod method = getGenadvisor().getDeclaredMethod(advisorFieldRead(getGenadvisor(), field.getName()));
         try
         {
            method.setBody(code);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Field " + field + " code: " + code + " in Method " + method, e);
         }

         //Make main class wrapper delegate to advisor wrapper
         String mcode = getMainReadWrapperBody(clazz, field, index);
         CtMethod mmethod = clazz.getDeclaredMethod(fieldRead(field.getName()));

         mmethod.setBody(mcode);
      }
      if (doSet)
      {
         //Set wrapper code in advisor
         String code = getAdvisorWriteWrapperBody(clazz, field, index, shouldReplaceArrayAccess);
         CtMethod method = getGenadvisor().getDeclaredMethod(advisorFieldWrite(getGenadvisor(), field.getName()));
         try
         {
            method.setBody(code);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Field " + field + " code: " + code + " in Method " + method, e);
         }

         //Make main class wrapper delegate to advisor wrapper
         String mcode = getMainWriteWrapperBody(clazz, field, index);
         CtMethod mmethod = clazz.getDeclaredMethod(fieldWrite(field.getName()));

         mmethod.setBody(mcode);
      }
   }

//   private ArrayList<PendingFieldInfo> pendingFieldWriteInfos = new ArrayList<PendingFieldInfo>();
//   private ArrayList<PendingFieldInfo> pendingFieldReadInfos = new ArrayList<PendingFieldInfo>();
//
//   private class PendingFieldInfo
//   {
//      CtField field;
//      int index;
//      public PendingFieldInfo(CtField field, int index)
//      {
//         this.field = field;
//         this.index = index;
//      }
//      public CtField getField()
//      {
//         return field;
//      }
//      public int getIndex()
//      {
//         return index;
//      }
//   }

   protected class GeneratedAdvisorFieldAccessExprEditor extends FieldAccessExprEditor
   {
      public GeneratedAdvisorFieldAccessExprEditor(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index)
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