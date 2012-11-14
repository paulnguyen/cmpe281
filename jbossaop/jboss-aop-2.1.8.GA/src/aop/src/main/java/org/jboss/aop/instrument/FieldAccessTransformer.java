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
import java.util.Iterator;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.util.Advisable;

/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */
public abstract class FieldAccessTransformer implements CodeConversionObserver
{
   // Constants -----------------------------------------------------
   
   final static String FIELD_INFO_CLASS_NAME = "org.jboss.aop.FieldInfo";
   
   // Attributes ----------------------------------------------------
   Instrumentor instrumentor;
   boolean optimize;
   private Codifier codifier;
   private JoinpointClassifier classifier;
   
   // Static --------------------------------------------------------
   // there are two transformations types available to a field: get and set
   protected static final String[] transformations = new String[] {"get", "set"};
   protected static final int GET_INDEX = 0;
   protected static final int SET_INDEX = 1;
   protected static final WrapperTransformer wrapper = new WrapperTransformer(transformations);
   
   // Constructors --------------------------------------------------
   protected FieldAccessTransformer(Instrumentor instrumentor)
   {
      this.instrumentor = instrumentor;
      this.optimize = AspectManager.optimize;
      this.codifier = new Codifier();
      this.classifier = instrumentor.joinpointClassifier;
   }

   // Public --------------------------------------------------------

   protected void buildFieldWrappers(CtClass clazz, ClassAdvisor advisor, boolean shouldReplaceArrayAccess) throws NotFoundException, CannotCompileException
   {
      List<CtField> fields = Instrumentor.getAdvisableFields(clazz);

      int fieldIndex = fieldOffset(clazz.getSuperclass());
      boolean skipFieldInterception = true;
      if (fields.size() > 0)
      {
         Iterator<CtField> it = fields.iterator();
         for (int index = 0; it.hasNext(); index++, fieldIndex++)
         {
            CtField field = it.next();
            JoinpointClassification classificationGet = instrumentor.joinpointClassifier.classifyFieldGet(field, advisor); 
            JoinpointClassification classificationSet = instrumentor.joinpointClassifier.classifyFieldSet(field, advisor); 
            if (!isPrepared(classificationGet) && !isPrepared(classificationSet))
            {
               continue;
            }
            
            if (!javassist.Modifier.isPrivate(field.getModifiers()))
            {
               skipFieldInterception = false;
            }
            
            doBuildFieldWrappers(clazz, field, fieldIndex, shouldReplaceArrayAccess, classificationGet, classificationSet);
         }
      }
      
      if (skipFieldInterception)
      {
         //Need to check if any of the superclass fields are advised, since we may need to replace access to them
         if (superClassHasAdvisedFields(clazz.getSuperclass()))
         {
            skipFieldInterception = false;
         }
      }
      
      final ClassLoader cl = clazz.getClassPool().getClassLoader();
      if (skipFieldInterception)
      {
         advisor.getManager().getInterceptionMarkers(cl).skipFieldAccess(clazz.getName());
      }
      else
      {
         advisor.getManager().getInterceptionMarkers(cl).addFieldInterceptionMarker(clazz.getName());
      }
         
   }
   
   private boolean superClassHasAdvisedFields(CtClass superClass) throws NotFoundException
   {
      if (superClass == null || superClass.getName().indexOf("java.") == 0)
      {
         return false;
      }

      ClassAdvisor advisor;
      try
      {
         //TODO Would ideally like to be able to use the existing advisor if class already exists
         advisor = instrumentor.getManager().getTempClassAdvisor(superClass);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
      
      List<CtField> fields = Instrumentor.getAdvisableFields(superClass);
      if (fields.size() > 0)
      {
         for (Iterator<CtField> it = fields.iterator(); it.hasNext(); )
         {
            CtField field = it.next();
            if (javassist.Modifier.isPrivate(field.getModifiers()))
            {
               continue;
            }
            
            JoinpointClassification classificationGet = instrumentor.joinpointClassifier.classifyFieldGet(field, advisor); 
            if (isPrepared(classificationGet))
            {
               return true;
            }
            
            JoinpointClassification classificationSet = instrumentor.joinpointClassifier.classifyFieldSet(field, advisor);
            if (isPrepared(classificationSet))
            {
               return true;
            }
         }
      }
      
      //We had no advised fields, check superclass again
      if (superClassHasAdvisedFields(superClass.getSuperclass()))
      {
         return true;
      }
      
      return false;
   }
      
   protected boolean isPrepared(JoinpointClassification classification)
   {
      return classification != JoinpointClassification.NOT_INSTRUMENTED;
   }
   
   protected abstract void doBuildFieldWrappers(CtClass clazz, CtField field, int index, boolean shouldReplaceArrayAccess, JoinpointClassification classificationGet, JoinpointClassification classificationSet) throws NotFoundException, CannotCompileException;
   
   protected String getArrayWriteRegistration(boolean shouldReplaceArrayAccess, String target, CtField field, String oldValue, String newValue) throws NotFoundException
   {
      if (shouldReplaceArrayAccess && (field.getType().isArray() || field.getType().getName().equals("java.lang.Object")))
      {
         return "org.jboss.aop.array.ArrayAdvisor.updateArrayField(" + target + ", \"" + field.getName() + "\", " + oldValue + ", " + newValue + ");";
      }
      return "";
   }

   /**
    * replace field access for possible public/protected fields that are intercepted
    * don't need to replace access for private fields.
    *
    * @param clazz
    * @param fieldsAdvisor
    * @return
    * @throws NotFoundException
    */
   public boolean replaceFieldAccess(List<CtField> fields, CtClass clazz, ClassAdvisor fieldsAdvisor) throws NotFoundException
   {
      CodeConverter converter = instrumentor.getCodeConverter();
      boolean converted = false;
      for (CtField field : fields)
      {
         if (!Modifier.isPrivate(field.getModifiers()) && Advisable.isAdvisable(field))
         {
            JoinpointClassification fieldGetClassification = classifier.classifyFieldGet(field, fieldsAdvisor);

            if (fieldGetClassification.equals(JoinpointClassification.WRAPPED))
            {
               converted = true;
               converter.replaceFieldRead(field, clazz, fieldRead(field.getName()));
            }
            JoinpointClassification fieldSetClassification = classifier.classifyFieldSet(field, fieldsAdvisor);
            if (fieldSetClassification.equals(JoinpointClassification.WRAPPED))
            {
               converted = true;
               converter.replaceFieldWrite(field, clazz, fieldWrite(field.getName()));
            }
         }
      }
      return converted;
   }

   /**
    * Wraps the field joinpoints contained in <code>fieldsGet</code> and <code>fieldsSet</code>.
    * @param clazz the class being instrumented.
    * @param fieldsGet a collection of <code>java.lang.Integer</code> indentifying
    * the field reads to be wrapped.
    * @param fieldsSet a collection of <code>java.lang.Integer</code> indentifying
    * the field writes to be wrapped.
    */
   public void wrap(CtClass clazz, Collection<Integer> fieldsGet, Collection<Integer> fieldsSet) throws CannotCompileException, NotFoundException
   {
      List<CtField> advisableFields = Instrumentor.getAdvisableFields(clazz);
      CtField[] fields = new CtField[advisableFields.size()];
      fields = advisableFields.toArray(fields);
      for (int fieldIndex : fieldsGet)
      {
         CtField field = fields[fieldIndex];
         if (wrapper.isNotPrepared(field, GET_INDEX))
         {
            continue;
         }
         // wrap
         wrapper.wrap(field, GET_INDEX);
         // execute wrapping
         String code = "{" + field.getType().getName() + " var; return var;}";
         CtMethod method = getWrapperReadMethod(field, clazz);
         method.setBody(code);
         code = getWrapperBody(clazz, field, true, fieldIndex);
         if (!Modifier.isPrivate(field.getModifiers()))
         {
            instrumentor.converter.replaceFieldRead(field, clazz, fieldRead(field.getName()));
            codifier.addPendingCode(method, code);
         }
         else
         {
            replaceFieldAccessInternally(clazz, field, true, false, fieldIndex);
            method.setBody(code);
         }
      }
      for (int fieldIndex : fieldsSet)
      {
         CtField field = fields[fieldIndex];
         if (wrapper.isNotPrepared(field, SET_INDEX))
         {
            continue;
         }
         // wrap
         wrapper.wrap(field, SET_INDEX);
         // executeWrapping
         String code = "{  }";
         CtMethod method = getWrapperWriteMethod(field, clazz);
         method.setBody(code);
         code = getWrapperBody(clazz, field, false, fieldIndex);
         if (!Modifier.isPrivate(field.getModifiers()))
         {
            instrumentor.converter.replaceFieldWrite(field, clazz, fieldWrite(field.getName()));
            codifier.addPendingCode(method, code);
         }
         else
         {
            replaceFieldAccessInternally(clazz, field, false, true, fieldIndex);
            method.setBody(code);
         }
      }
   }

   
   protected CtMethod getWrapperReadMethod(CtField field, CtClass clazz)throws NotFoundException
   {
      return clazz.getDeclaredMethod(fieldRead(field.getName()));
   }
   
   protected CtMethod getWrapperWriteMethod(CtField field, CtClass clazz)throws NotFoundException
   {
      return clazz.getDeclaredMethod(fieldWrite(field.getName()));
   }
   
   /**
    * Unwraps the field joinpoints contained in <code>fieldsGet</code> and <code>fieldsSet</code>.
    * @param clazz the class being instrumented.
    * @param fieldsGet a collection of <code>java.lang.Integer</code> indentifying
    * the field reads to be unwrapped.
    * @param fieldsSet a collection of <code>java.lang.Integer</code> indentifying
    * the field writes to be unwrapped.
    */
   public void unwrap(CtClass clazz, Collection<Integer> fieldsGet, Collection<Integer> fieldsSet) throws CannotCompileException, NotFoundException
   {
      List<CtField> advisableFields = Instrumentor.getAdvisableFields(clazz);
      CtField[] fields = new CtField[advisableFields.size()];
      fields = advisableFields.toArray(fields);
      // unwrapping field gets
      for (int fieldIndex : fieldsGet)
      {
         CtField field = fields[fieldIndex];
         if (wrapper.isNotPrepared(field, GET_INDEX))
         {
            continue;
         }
         // unwrap
         wrapper.unwrap(field, GET_INDEX);
         // execute unwrapping
         CtMethod method = clazz.getDeclaredMethod(fieldRead(field.getName()));
         String target = Modifier.isStatic(field.getModifiers())? clazz.getName(): "((" + clazz.getName() + ")$1)";
         method.setBody("return " + target + "." + field.getName() + ";");
      }
      
      for (int fieldIndex : fieldsSet)
      {
         CtField field = fields[fieldIndex];
         if (wrapper.isNotPrepared(field, SET_INDEX))
         {
            continue;
         }
         // unwrap
         wrapper.unwrap(field, SET_INDEX);
         // execute unwrapping
         // create field trapWrite memthod
         CtMethod method = clazz.getDeclaredMethod(fieldWrite(field.getName()));
         String target = Modifier.isStatic(field.getModifiers())? clazz.getName(): "((" + clazz.getName() + ")$1)";
         method.setBody(target + "." + field.getName() + "=$2" + ";");
      }
   }
   
   /**
    * Notifies this transformer that the code conversion is done.
    */
   public void codeConverted() throws NotFoundException, CannotCompileException {
      codifier.codifyPending();
   }
   
   protected int fieldOffset(CtClass clazz) throws NotFoundException
   {
      if (clazz == null)
         return 0;
      if (clazz.getName().equals("java.lang.Object"))
         return 0;
      int offset = fieldOffset(clazz.getSuperclass());

      CtField[] fields = clazz.getDeclaredFields();
      for (int i = 0; i < fields.length; i++)
      {
         if (Advisable.isAdvisable(fields[i]))
         {
            offset++;
         }
      }
      return offset;
   }

   protected String addFieldReadInfoFieldWithAccessors(int modifiers, CtClass addTo, CtField field) throws NotFoundException, CannotCompileException
   {
      return addFieldReadInfoFieldWithAccessors(modifiers, addTo, field, null);
   }
   
   /**
    * Adds a FieldInfo field to the passed in class
    */
   protected String addFieldReadInfoFieldWithAccessors(int modifiers, CtClass addTo, CtField field, CtField.Initializer init) throws NotFoundException, CannotCompileException
   {
      String name = getFieldReadInfoFieldName(field.getName());
      TransformerCommon.addInfoField(instrumentor, FIELD_INFO_CLASS_NAME, name, modifiers, addTo, addInfoAsWeakReference(), init, markInfoAsSynthetic());
      return name;
   }
   
   protected String addFieldWriteInfoField(int modifiers, CtClass addTo, CtField field) throws NotFoundException, CannotCompileException
   {
      return addFieldWriteInfoField(modifiers, addTo, field, null);
   }
   
   /**
    * Adds a FieldInfo field to the passed in class
    */
   protected String addFieldWriteInfoField(int modifiers, CtClass addTo, CtField field, CtField.Initializer init) throws NotFoundException, CannotCompileException
   {
      String name = getFieldWriteInfoFieldName(field.getName());
      TransformerCommon.addInfoField(instrumentor, FIELD_INFO_CLASS_NAME, name, modifiers, addTo, addInfoAsWeakReference(), init, markInfoAsSynthetic());
      return name;
   }
   
   protected boolean addInfoAsWeakReference()
   {
      return true;
   }

   protected boolean markInfoAsSynthetic()
   {
      return true;
   }

   public static String getFieldReadInfoFieldName(String fieldName)
   {
      return "aop$FieldInfo_r_" + fieldName;
   }

   public static String getFieldWriteInfoFieldName(String fieldName)
   {
      return "aop$FieldInfo_w_" + fieldName;
   }

   public static String fieldRead(String fieldName)
   {
      return fieldName + "_r_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;
   }

   public static String fieldWrite(String fieldName)
   {
      return fieldName + "_w_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX;
   }

   protected static String fieldInfoFromWeakReference(String localName, String fieldInfoName)
   {
      return TransformerCommon.infoFromWeakReference(FIELD_INFO_CLASS_NAME, localName, fieldInfoName);
   }
   
   protected int getStaticModifiers(CtField field)
   {
      int mod = Modifier.STATIC;
      if ((field.getModifiers() & Modifier.PUBLIC) != 0)
      {
         mod |= Modifier.PUBLIC;
      }
      else if ((field.getModifiers() & Modifier.PROTECTED) != 0)
      {
         mod |= Modifier.PROTECTED;
      }
      else if ((field.getModifiers() & Modifier.PRIVATE) != 0)
      {
         mod |= Modifier.PRIVATE;
      }
      else
      {
         mod |= Modifier.PUBLIC;
      }

      return mod;
   }

   /**
    * This function replaces internal field accesses with bytecode hooks into framework
    * todo this must do it for inherited protected/public fields as well
    *
    * @param clazz
    * @param field
    * @param doGet
    * @param doSet
    * @param index
    * @throws javassist.CannotCompileException
    *
    */
   protected abstract void replaceFieldAccessInternally(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index) throws CannotCompileException;
   
   /**
    * 
    * Generate the wrapper place holders.
    * <p> PS: Removed from inside inner classes to avoid code repetition.</p>
    */
   protected void buildWrapperPlaceHolders(CtClass clazz, CtField field, boolean doGet, boolean doSet, int mod)
   throws NotFoundException, CannotCompileException
   {
      if (doGet)
      {
         buildReadWrapperPlaceHolder(clazz, field, fieldRead(field.getName()), mod);
      }
      if (doSet)
      {
         buildWriteWrapperPlaceHolder(clazz, field, fieldWrite(field.getName()), mod);
      }
   }

   /**
    * Builds the read wrapper place holder for preparation of <code>field</code> read
    * joinpoint.
    * @param clazz the <code>clazz</code> to add wrapper to
    * @param field the <code>field</code> whose read joinpoint wrapper will be generated.
    * @param wrapperName The name of the field wrapper to be generated
    * @param mod the modifiers of the generated wrapper.
    */
   protected CtMethod buildReadWrapperPlaceHolder(CtClass clazz, CtField field, String wrapperName, int mod)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool classPool = (AOPClassPool) instrumentor.getClassPool();

      CtClass ftype = field.getType();
      CtClass[] readParam = new CtClass[]{classPool.get("java.lang.Object")};
      
      //Doesn't matter what we put in as long as it compiles, 
      //body will be replaced when the field read gets wrapped
      String code = "{" + ftype.getName() +  " var = ";
      if (ftype.isPrimitive())
      {
         if (ftype == CtClass.booleanType)
         {
            code += false;
         }
         else if (ftype == CtClass.byteType)
         {
            code += "(byte)0";
         }
         else if (ftype == CtClass.charType)
         {
            code += "(char)0";
         }
         else if (ftype == CtClass.doubleType)
         {
            code += "0.0";
         }
         else if (ftype == CtClass.floatType)
         {
            code += "(float)0.0";
         }
         else if (ftype == CtClass.intType)
         {
            code += "0";
         }
         else if (ftype == CtClass.longType)
         {
            code += "(long)0";
         }
         else if (ftype == CtClass.shortType)
         {
            code += "(short)0";
         }  
      }
      else
      {
         code += "null";
      }
      code += "; return var;}";
      CtMethod rmethod = CtNewMethod.make(ftype, wrapperName, readParam, null, code, clazz);
      rmethod.setModifiers(mod);
      Instrumentor.addSyntheticAttribute(rmethod);
      clazz.addMethod(rmethod);
      
      return rmethod;
   }

   /**
    * Builds the write wrapper place holder for preparation of <code>field</code> read
    * joinpoint.
    * @param clazz the class the wrapper will be added to 
    * @param field the <code>field</code> whose write joinpoint wrapper will be generated.
    * @param wrapperName The name of the field wrapper to be generated
    * @param mod the modifiers of the generated wrapper.
    */
   protected CtMethod buildWriteWrapperPlaceHolder(CtClass clazz, CtField field, String wrapperName, int mod)
   throws NotFoundException, CannotCompileException
   {
      AOPClassPool classPool = (AOPClassPool) instrumentor.getClassPool();

      CtClass ftype = field.getType();

      // create field trapWrite memthod
      CtClass[] writeParam = new CtClass[2];
      writeParam[0] = classPool.get("java.lang.Object");
      writeParam[1] = ftype;

      //Doesn't matter what we put in as long as it compiles, 
      //body will be replaced when we the field write gets wrapped

      CtMethod wmethod = CtNewMethod.make(CtClass.voidType, wrapperName, writeParam, null, "{}", clazz);
      wmethod.setModifiers(mod);
      Instrumentor.addSyntheticAttribute(wmethod);
      clazz.addMethod(wmethod);
      
      SignatureAttribute ai = (SignatureAttribute) field.getFieldInfo2().getAttribute(SignatureAttribute.tag);
      if (ai != null)
      {
         MethodInfo wrapperInfo = wmethod.getMethodInfo2();
         SignatureAttribute methodAtt = new SignatureAttribute(wrapperInfo.getConstPool(), "(" + ai.getSignature() + ")V");
         wrapperInfo.addAttribute(methodAtt);
      }
      
      return wmethod;
   }

   /**
    * Returns the wrapper body of the <code>field</code> joinpoint.
    * @param clazz the class declaring <code>field</code>.
    * @param field the field whose joinpoint wrapper code will be generated.
    * @param get indicates if the wrapper is a field read wrapper or a field
    * write wrapper.
    * @param fieldIndex the index of <code>field</code>.
    * @return teh wrapper body code.
    */
   protected abstract String getWrapperBody(CtClass clazz, CtField field, boolean get, int fieldIndex) throws NotFoundException, CannotCompileException;

   protected abstract class FieldAccessExprEditor extends ExprEditor
   {
      CtClass clazz;
      CtField field;
      boolean doGet;
      boolean doSet;
      int fieldIndex;

      public FieldAccessExprEditor(CtClass clazz, CtField field, boolean doGet, boolean doSet, int index)
      {
         this.clazz = clazz;
         this.field = field;
         this.doGet = doGet;
         this.doSet = doSet;
         this.fieldIndex = index;
      }

      public void edit(FieldAccess fieldAccess) throws CannotCompileException
      {
         if (!fieldAccess.getClassName().equals(clazz.getName())) return;
         if (!fieldAccess.getFieldName().equals(field.getName())) return;
         if (calledByInvocationClass(fieldAccess))return;

         if (fieldAccess.isReader() && doGet)
         {
            replaceRead(fieldAccess);
         }
         if (fieldAccess.isWriter() && doSet)
         {
            replaceWrite(fieldAccess);
         }
      }

      private boolean calledByInvocationClass(FieldAccess fieldAccess)
      {
         try
         {
            return isInvocationClass(fieldAccess.where().getDeclaringClass());
         }
         catch (RuntimeException e)
         {
            //This means it is one of the access$xxx methods, 
            //This occurs when the field access is from one of the invocation classes, which we want to skip 
            return true;
         }
      }
      
      private boolean isInvocationClass(CtClass superClazz)
      {
         try
         {
            if (superClazz == null)
            {
               return false;
            }
            if (superClazz.getName().equals("java.lang.Object"))
            {
               return false;
            }
            if (superClazz.getName().equals("org.jboss.aop.joinpoint.Invocation"))
            {
               return true;
            }
            return (isInvocationClass(superClazz.getSuperclass()));
         }
         catch (NotFoundException e)
         {
            throw new RuntimeException(e.getMessage(), e);
         }
      }
      
      protected abstract void replaceRead(FieldAccess fieldAccess) throws CannotCompileException;
      protected abstract void replaceWrite(FieldAccess fieldAccess) throws CannotCompileException;
   }//End Inner class FieldAccessExprEditor


}
