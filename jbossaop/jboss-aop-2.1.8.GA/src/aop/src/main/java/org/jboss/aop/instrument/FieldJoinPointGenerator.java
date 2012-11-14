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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jboss.aop.FieldInfo;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.FieldAccess;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.FieldWriteInvocation;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.util.JavassistToReflect;
import org.jboss.aop.util.ReflectToJavassist;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class FieldJoinPointGenerator extends JoinPointGenerator
{
   public static final String WRITE_JOINPOINT_FIELD_PREFIX = JOINPOINT_FIELD_PREFIX + "w_";
   public static final String READ_JOINPOINT_FIELD_PREFIX = JOINPOINT_FIELD_PREFIX + "r_";
   public static final String WRITE_JOINPOINT_CLASS_PREFIX = JOINPOINT_CLASS_PREFIX + "w_";
   public static final String READ_JOINPOINT_CLASS_PREFIX = JOINPOINT_CLASS_PREFIX + "r_";
   private static final Class<FieldAccess> JOINPOINT_TYPE = FieldAccess.class;
   private static final Class<FieldReadInvocation> READ_INVOCATION_TYPE = FieldReadInvocation.class;
   private static final Class<FieldWriteInvocation> WRITE_INVOCATION_TYPE = FieldWriteInvocation.class;
   private static final CtClass READ_INVOCATION_CT_TYPE;
   private static final CtClass WRITE_INVOCATION_CT_TYPE;
   private static final String TYPED_VALUE_FIELD = "typedValue";
   static
   {
      try
      {
         READ_INVOCATION_CT_TYPE = ReflectToJavassist.classToJavassist(READ_INVOCATION_TYPE);
         WRITE_INVOCATION_CT_TYPE = ReflectToJavassist.classToJavassist(WRITE_INVOCATION_TYPE);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   WeakReference<Class<?>> returnClassType;
   WeakReference<Type> returnType;
   boolean read;
   boolean hasTargetObject;
   
   public FieldJoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info)
   {
      super(advisor, info, getParameters((FieldInfo) info),
            ((FieldInfo) info).isRead()? 0: 1, ((FieldInfo) info).isRead());

      if (((FieldInfo)info).isRead())
      {
         read = true;
         returnClassType = new WeakReference<Class<?>>(((FieldInfo)info).getField().getType());
         returnType = new WeakReference<Type>(((FieldInfo)info).getField().getGenericType());
      }
      hasTargetObject = !Modifier.isStatic(((FieldInfo)info).getField().getModifiers());
   }

   private static JoinPointParameters getParameters(FieldInfo info)
   {
      if (Modifier.isStatic(info.getField().getModifiers()))
      {
         return JoinPointParameters.ONLY_ARGS;
      }
      return JoinPointParameters.TARGET_ARGS;
   }
   
   protected void initialiseJoinPointNames(JoinPointInfo info)
   {
      FieldInfo finfo = (FieldInfo)info;
      joinpointClassName =
         getGeneratedJoinPointClassName(fieldName(finfo), finfo.isRead());

      joinpointFieldName =
         getGeneratedJoinPointFieldName(fieldName(finfo), finfo.isRead());
   }
   
   /**
    * This method will be called only when an @Arg-annotated parameter is accepted.
    * This happens only on FieldWrite interceptions, and index value will be zero.
    * 
    * @param index zero value
    */
   protected String getJoinPointArg(int index)
   {
      return "this." + TYPED_VALUE_FIELD;
   }

   private String fieldName(FieldInfo info)
   {
      return info.getField().getName();
   }

   protected boolean isVoid()
   {
      return !read;
   }

   protected Class<?> getReturnClassType()
   {
      if (returnClassType != null)
      {
         return returnClassType.get();
      }
      return null;
   }

   protected AdviceMethodProperties getAdviceMethodProperties(JoinPointBean joinPoint, AdviceSetup setup)
   {
      FieldAccess fieldAccess = (FieldAccess)joinPoint;
      Field field = fieldAccess.getField();
      return new AdviceMethodProperties(
            joinPoint,
            setup.getAspectClass(),
            setup.getAdviceName(),
            JOINPOINT_TYPE,
            (fieldAccess.isRead()) ? READ_INVOCATION_TYPE : WRITE_INVOCATION_TYPE,
            (fieldAccess.isRead()) ? field.getGenericType() : Void.TYPE,
            (fieldAccess.isRead()) ? new Type[] {} : new Type[] {field.getGenericType()},
            (fieldAccess.isRead()) ? new Class[] {} : new Class[] {field.getType()},
             null,
             field.getDeclaringClass(),
             hasTargetObject());
   }

//   protected CtClass[] getJoinpointParameters() throws NotFoundException
//   {
//      if (isVoid()) return new CtClass[0];
//
//      CtClass type = ReflectToJavassist.fieldToJavassist(((FieldInfo)info).getAdvisedField()).getType();
//      return new CtClass[] {type};
//   }

   protected boolean hasTargetObject()
   {
      return hasTargetObject;
   }

   protected static String getGeneratedJoinPointFieldName(String fieldName, boolean read)
   {
      if (read)
      {
         return READ_JOINPOINT_FIELD_PREFIX + fieldName;
      }
      else
      {
         return WRITE_JOINPOINT_FIELD_PREFIX + fieldName;
      }
   }

   private static String getGeneratedJoinPointClassName(String fieldName, boolean read)
   {
      if (read)
      {
         return READ_JOINPOINT_CLASS_PREFIX + fieldName;
      }
      else
      {
         return WRITE_JOINPOINT_CLASS_PREFIX + fieldName;
      }
   }

   protected static CtClass createReadJoinpointBaseClass(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass advisedClass,
         CtField advisedField,
         String finame,
         int index)throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator factory = new ReadBaseClassGenerator(instrumentor, advisedClass, advisedField, finame, index);
      return factory.generate();
   }

   protected static CtClass createWriteJoinpointBaseClass(
         GeneratedAdvisorInstrumentor instrumentor,
         CtClass advisedClass,
         CtField advisedField,
         String finame,
         int index)throws NotFoundException, CannotCompileException
   {
      BaseClassGenerator factory = new WriteBaseClassGenerator(instrumentor, advisedClass, advisedField, finame, index);
      return factory.generate();
   }


   static abstract class BaseClassGenerator
   {
      GeneratedAdvisorInstrumentor instrumentor;
      CtClass advisedClass;
      CtField advisedField;
      String finame;
      boolean hasTargetObject;

      CtClass jp;
      CtClass fieldType;
      CtClass fieldInfoClass;
      boolean read;

      BaseClassGenerator(GeneratedAdvisorInstrumentor instrumentor,  CtClass advisedClass,
                        CtField advisedField, String finame, int index, boolean read) throws NotFoundException
      {
         this.instrumentor = instrumentor;
         this.advisedClass = advisedClass;
         this.advisedField = advisedField;
         this.finame = finame;
         this.fieldType = advisedField.getType();
         this.read = read;
         fieldInfoClass = instrumentor.forName(FieldAccessTransformer.FIELD_INFO_CLASS_NAME);
         hasTargetObject = !Modifier.isStatic(advisedField.getModifiers());
      }

      protected CtClass generate() throws CannotCompileException, NotFoundException
      {
         jp = setupClass();
         if (hasTargetObject)
         {
            addTypedTargetField();
         }
         addArgumentFieldAndAccessor();
         addInvokeJoinpointMethod();
         addFieldInfoField();
         addPublicConstructor();
         addProtectedConstructors();
         addDispatchMethods();

         TransformerCommon.compileOrLoadClass(advisedClass, jp);
         return jp;
      }

      protected void addArgumentFieldAndAccessor() throws CannotCompileException, NotFoundException
      {
         CtField argumentsField = new CtField(
               instrumentor.forName("java.lang.Object[]"), ARGUMENTS, jp);
         argumentsField.setModifiers(Modifier.PROTECTED);
         jp.addField(argumentsField);
         CtMethod getArguments = CtNewMethod.make(createGetArgumentsBody(), jp);
         getArguments.setModifiers(Modifier.PUBLIC);
         jp.addMethod(getArguments);
         CtMethod setArguments = CtNewMethod.make(createSetArgumentsBody(), jp);
         setArguments.setModifiers(Modifier.PUBLIC);
         jp.addMethod(setArguments);
      }

      protected abstract String createGetArgumentsBody();
      protected abstract String createSetArgumentsBody();
      
//      private static String debugFields(CtClass clazz) throws NotFoundException
//      {
//         StringBuffer sb = new StringBuffer();
//         sb.append(clazz.getName());
//         CtField[] fields = clazz.getFields();
//         for (int i = 0 ; i < fields.length ; i++)
//         {
//            sb.append("\n\t\t\t\t" + Modifier.toString(fields[i].getModifiers()) + " " + fields[i].getName() + " " + fields[i].getType());
//         }
//         
//         return sb.toString();
//      }

      protected CtClass setupClass()throws NotFoundException, CannotCompileException
      {
         String className = getGeneratedJoinPointClassName(advisedField.getName(), read);

         //Create inner joinpoint class in advised class, super class is
         CtClass superClass = (read) ? READ_INVOCATION_CT_TYPE : WRITE_INVOCATION_CT_TYPE;
         jp = TransformerCommon.makeNestedClass(advisedClass, className, true, Modifier.PUBLIC | Modifier.STATIC, superClass);
         addUntransformableInterface(instrumentor, jp);
         return jp;
      }

      protected abstract CtClass getSuperClass() throws NotFoundException;

      private void addTypedTargetField()throws CannotCompileException
      {
         CtField targetField = new CtField(advisedClass, TYPED_TARGET_FIELD, jp);
         jp.addField(targetField);
         targetField.setModifiers(Modifier.PROTECTED);
      }

      /**
       * This constructor is used by the advisor when we have regenerated the joinpoint.
       * This just creates a generic JoinPoint instance with no data specific to the
       * method call
       */
      private void addPublicConstructor() throws CannotCompileException
      {
         CtConstructor publicConstructor = CtNewConstructor.make(
               new CtClass[] {fieldInfoClass},
               new CtClass[0],
               "{super($1, $1.getInterceptors()); this." + INFO_FIELD + " = $1;}",
               jp);

         jp.addConstructor(publicConstructor);
      }

      /**
       * These constructors will be called by invokeJoinpoint in the generated
       * subclass when we need to instantiate a joinpoint containing target and args
       */
      protected void addProtectedConstructors() throws CannotCompileException, NotFoundException
      {
         CtClass[] ctorParams1 = (hasTargetObject) ? new CtClass[2] : new CtClass[1];
         CtClass[] ctorParams2 = (hasTargetObject) ? new CtClass[3] : new CtClass[2];
         
         ctorParams1[0] = ctorParams2[0] = jp;
         if (hasTargetObject) ctorParams1[1] = ctorParams2[1] = advisedClass;
         ctorParams2[ctorParams2.length - 1] = getArgumentType();
         
         StringBuffer body = new StringBuffer();
         body.append("{");
         body.append("   this($1." + INFO_FIELD + ");");

         if (hasTargetObject)
         {
            body.append("   this." + TYPED_TARGET_FIELD + " = $2;");
            body.append("   super.setTargetObject($2);");
         }
         
         if (getArgumentType() != null)
         {
            CtConstructor protectedConstructor = CtNewConstructor.make(
                  ctorParams1,
                  new CtClass[0],
                  body.toString() + "}",
                  jp);
            protectedConstructor.setModifiers(Modifier.PROTECTED);
            jp.addConstructor(protectedConstructor);
         }
         else
         {
            ctorParams2 = ctorParams1;
         }
         String setArguments = createSetValue();
         CtConstructor protectedConstructor = CtNewConstructor.make(
               ctorParams2,
               new CtClass[0],
               body.toString() + setArguments + "}",
               jp);
         protectedConstructor.setModifiers(Modifier.PROTECTED);
         jp.addConstructor(protectedConstructor);
         
         
      }

      protected abstract CtClass getArgumentType() throws NotFoundException;
      protected abstract String createSetValue();
      protected abstract CtClass[] getInvokeJoinPointParams() throws NotFoundException;
      
      /**
       * Add an empty invokeJoinpoint() method. This method will be overridden by generated subclasses,
       * when the interceptors are rebuilt
       */
      protected abstract CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException;

      private void addFieldInfoField()throws CannotCompileException
      {
         CtField infoField = new CtField(fieldInfoClass, INFO_FIELD, jp);
         infoField.setModifiers(javassist.Modifier.PROTECTED);//Make visible to classes in child classloaders
         jp.addField(infoField);
      }

      private void addDispatchMethods() throws CannotCompileException, NotFoundException
      {
         addInvokeNextDispatchMethod();
         addInvokeJoinPointDispatchMethod();
         addInvokeTargetMethod();
      }

      private void addInvokeNextDispatchMethod() throws CannotCompileException, NotFoundException
      {
         //This dispatch method will be called by the invokeNext() methods for around advice

         String body = createInvokeNextDispatchMethodBody();

         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  (read) ? advisedField.getType() : CtClass.voidType,
                  JoinPointGenerator.DISPATCH,
                  EMPTY_CTCLASS_ARRAY,
                  EMPTY_CTCLASS_ARRAY,
                  body,
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, EMPTY_CTCLASS_ARRAY), e);
         }
      }

      protected abstract String createInvokeNextDispatchMethodBody() throws NotFoundException;

      protected void addInvokeJoinPointDispatchMethod() throws NotFoundException, CannotCompileException
      {
         CtClass[] params = getInvokeJoinPointParams();
         if (params.length == 0)
         {
            return;
         }

         //This dispatch method will be called by the invokeJoinPoint() method if the joinpoint has no around advices

         String body = createInvokeJoinPointDispatchMethodBody();

         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  (read) ? advisedField.getType() : CtClass.voidType,
                  JoinPointGenerator.DISPATCH,
                  params,
                  new CtClass[0],
                  body,
                  jp);
            dispatch.setModifiers(Modifier.PROTECTED);
            jp.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(jp, JoinPointGenerator.DISPATCH, params), e);
         }
      }

      protected abstract String createInvokeJoinPointDispatchMethodBody() throws NotFoundException;
      
      private void addInvokeTargetMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod template = READ_INVOCATION_CT_TYPE.getDeclaredMethod(INVOKE_TARGET);
         
         String body = (read) ? "{return ($w)dispatch();}" : "{dispatch(); return null;}" ;
         
         CtMethod invokeTarget = CtNewMethod.make(
               template.getReturnType(),
               template.getName(),
               template.getParameterTypes(),
               template.getExceptionTypes(),
               body,
               jp);
         jp.addMethod(invokeTarget);
      }
      
   }

   private static class ReadBaseClassGenerator extends BaseClassGenerator
   {
      ReadBaseClassGenerator(GeneratedAdvisorInstrumentor instrumentor,  CtClass advisedClass,
            CtField advisedField, String finame, int index) throws NotFoundException
      {
         super(instrumentor, advisedClass, advisedField, finame, index, true);
      }

      protected CtClass getSuperClass() throws NotFoundException
      {
         return READ_INVOCATION_CT_TYPE;
      }

      protected CtClass getArgumentType()
      {
         return null;
      }
      
      protected String createSetValue()
      {
         return "";
      }
      
      protected CtClass[] getInvokeJoinPointParams() throws NotFoundException
      {
         return (hasTargetObject) ? new CtClass[] {advisedClass} : new CtClass[0];
      }

      protected CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod invokeJoinpointMethod  = CtNewMethod.make(
               advisedField.getType(),
               INVOKE_JOINPOINT,
               getInvokeJoinPointParams(),
               THROWS_THROWABLE,
               null,
               jp);
         invokeJoinpointMethod.setModifiers(Modifier.PROTECTED);
         jp.addMethod(invokeJoinpointMethod);

         return invokeJoinpointMethod;
      }

      protected String createInvokeNextDispatchMethodBody()
      {
         return (hasTargetObject) ?
            "{return "  + TYPED_TARGET_FIELD + "." + advisedField.getName() + ";}" :
            "{return " + advisedClass.getName() + "." + advisedField.getName() + ";}";
      }

      protected String createInvokeJoinPointDispatchMethodBody()
      {
         return (hasTargetObject) ?
            "{return "  + "$1." + advisedField.getName() + ";}" :
            "{return " + advisedClass.getName() + "." + advisedField.getName() + ";}";
      }
      
      protected String createGetArgumentsBody()
      {
         StringBuffer code = new StringBuffer("public java.lang.Object[] ");
         code.append(OptimizedBehaviourInvocations.GET_ARGUMENTS);
         code.append("(){ ");
         code.append("   return ");
         code.append(ARGUMENTS);
         code.append("; ");
         code.append("}");
         return code.toString();
      }
      
      protected String createSetArgumentsBody()
      {
         StringBuffer code = new StringBuffer(
         "public void setArguments(java.lang.Object[] args)");
         code.append("{   ");
         code.append("if (args != null){ throw new RuntimeException(\"Arguments array of field read must be null\");}");
         code.append(ARGUMENTS);
         code.append("=args;");
         code.append("}");
         return code.toString();
      }
   }

   private static class WriteBaseClassGenerator extends BaseClassGenerator
   {
      private static final String GET_VALUE = "getValue";
      private static final String SET_VALUE = "setValue";
      
      WriteBaseClassGenerator(GeneratedAdvisorInstrumentor instrumentor,  CtClass advisedClass,
            CtField advisedField, String finame, int index) throws NotFoundException
      {
         super(instrumentor, advisedClass, advisedField, finame, index, false);
      }

      protected CtClass setupClass()throws NotFoundException, CannotCompileException
      {
         CtClass setUp = super.setupClass();
         CtField valueField = new CtField(getArgumentType(), TYPED_VALUE_FIELD, setUp);
         jp.addField(valueField);
         CtMethod oldGetValue = WRITE_INVOCATION_CT_TYPE.getDeclaredMethod(GET_VALUE);
         CtMethod getValue = CtNewMethod.make(oldGetValue.getReturnType(),
               GET_VALUE, oldGetValue.getParameterTypes(),
               oldGetValue.getExceptionTypes(), "{return ($w)" + TYPED_VALUE_FIELD + ";}",
               setUp);
         setUp.addMethod(getValue);
         CtMethod oldSetValue = WRITE_INVOCATION_CT_TYPE.getDeclaredMethod(SET_VALUE);
         CtMethod setValue = CtNewMethod.make(oldSetValue.getReturnType(),
               GET_VALUE, oldSetValue.getParameterTypes(),
               oldSetValue.getExceptionTypes(), "{" + TYPED_VALUE_FIELD + " = " +
               JavassistToReflect.castInvocationValueToTypeString(getArgumentType(),
                     "$1") + ";}", setUp);
         setUp.addMethod(setValue);
         return setUp;
      }
      
      protected CtClass getSuperClass() throws NotFoundException
      {
         return WRITE_INVOCATION_CT_TYPE;
      }
      
      protected CtClass getArgumentType() throws NotFoundException
      {
         return advisedField.getType();
      }

      protected String createSetValue()
      {
         if (hasTargetObject)
         {
            return TYPED_VALUE_FIELD + " = $3;";
         }
         return TYPED_VALUE_FIELD + " = $2;";
      }

      protected CtClass[] getInvokeJoinPointParams() throws NotFoundException
      {
         return (hasTargetObject) ? new CtClass[] {advisedClass, advisedField.getType()} : new CtClass[] {advisedField.getType()};
      }

      protected CtMethod addInvokeJoinpointMethod() throws CannotCompileException, NotFoundException
      {
         CtMethod invokeJoinpointMethod  = CtNewMethod.make(
               CtClass.voidType,
               JoinPointGenerator.INVOKE_JOINPOINT,
               getInvokeJoinPointParams(),
               THROWS_THROWABLE,
               null,
               jp);
         invokeJoinpointMethod.setModifiers(Modifier.PROTECTED);
         jp.addMethod(invokeJoinpointMethod);

         return invokeJoinpointMethod;
      }

      protected String createInvokeNextDispatchMethodBody() throws NotFoundException
      {
         return
            "{" +
            ((hasTargetObject) ?
                  TYPED_TARGET_FIELD + "." + advisedField.getName() + " = " +  TYPED_VALUE_FIELD:
                     advisedClass.getName() + "." + advisedField.getName() + " = " +  TYPED_VALUE_FIELD) +

            ((hasTargetObject) ?
                  "; return "  + TYPED_TARGET_FIELD + "." + advisedField.getName() + ";" :
                     "; return " + advisedClass.getName() + "." + advisedField.getName() + ";") +
            "}";
      }

      protected String createInvokeJoinPointDispatchMethodBody()
      {
         return (hasTargetObject) ?
            "{$1." + advisedField.getName() + " = $2;}" :
            "{" + advisedClass.getName() + "." + advisedField.getName() + " = $1;}";
      }
      
      protected void addArgumentFieldAndAccessor() throws CannotCompileException, NotFoundException
      {
         // duplicated code (See OptimizedBehaviour class)
         CtField inconsistentArgs = new CtField(CtClass.booleanType, "inconsistentArgs",
               jp);
         jp.addField(inconsistentArgs, CtField.Initializer.byExpr("false"));
         super.addArgumentFieldAndAccessor();
         CtMethod enforceArgsConsistency = CtNewMethod.make(
               createEnforceArgsConsistencyBody(), jp);
         enforceArgsConsistency.setModifiers(Modifier.PROTECTED);
         jp.addMethod(enforceArgsConsistency);
      }
      
      protected String createGetArgumentsBody()
      {
         StringBuffer code = new StringBuffer("public java.lang.Object[] ");
         code.append(OptimizedBehaviourInvocations.GET_ARGUMENTS);
         code.append("(){ inconsistentArgs = true;   ");
         code.append("   if(");
         code.append(ARGUMENTS);
         code.append("  == null)");
         code.append("   {");
         code.append("      ");
         code.append(ARGUMENTS);
         code.append(" = new java.lang.Object[]{");
         code.append(GET_VALUE);
         code.append("()};");
         code.append("   }");
         code.append("   return ");
         code.append(ARGUMENTS);
         code.append("; ");
         code.append("}");
         return code.toString();
      }
      
      protected String createSetArgumentsBody()
      {
         StringBuffer code = new StringBuffer(
         "public void setArguments(java.lang.Object[] args)");
         code.append("{   inconsistentArgs = true;   ");
         code.append("if (args == null || args.length != 1)");
         code.append("{throw new RuntimeException(");
         code.append("\"Field write arguments must be a non-null array containing");
         code.append(" a single element: the value of the field write\");}");
         code.append(ARGUMENTS);
         code.append("=args;");
         code.append("}");
         return code.toString();
      }
      
      protected String createEnforceArgsConsistencyBody()
      {
         StringBuffer code = new StringBuffer("public void ");
         code.append(OptimizedBehaviourInvocations.ENFORCE_ARGS_CONSISTENCY);
         // duplicated code (See OptimizedBehaviour class)
         code.append("() {if(inconsistentArgs) { this.");
         code.append(SET_VALUE).append("(").append(ARGUMENTS).append("[0]);}}");
         return code.toString();
      }
   }
}
