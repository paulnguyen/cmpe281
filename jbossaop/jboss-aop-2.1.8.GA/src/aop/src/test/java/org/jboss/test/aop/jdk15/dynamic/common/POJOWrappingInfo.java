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
package org.jboss.test.aop.jdk15.dynamic.common;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.instrument.ConstructorExecutionTransformer;
import org.jboss.aop.joinpoint.InvocationBase;
/**
 * Class that represents the POJO joinpoints wrapping state in a specific moment
 * of the test execution.
 * @author Flavia Rainone
 *
 */
public class POJOWrappingInfo
{
   /**
    * Finds out if a method is wrapped.
    */
   private class POJOMethodIntrospector extends ExprEditor
   {
      private String notAdvisedMethod;
      /**
       * Constructor.
       * @param notAdvisedMethod the not advised name of the method whose execution
       * joinpoint must be found out.
       */
      public POJOMethodIntrospector(String notAdvisedMethod)
      {
         this.notAdvisedMethod = notAdvisedMethod;
      }
      public void edit(MethodCall methodCall) throws CannotCompileException
      {
         if (methodCall.where().getName().endsWith("$aop"))
            return;
         if (methodCall.getClassName().equals(getClass().getPackage().getName() + ".scenario.POJO") && 
               methodCall.getMethodName().equals(notAdvisedMethod))
         {
            methodWrapped = true;
         }
      }
   }
   
   /**
    * Introspects a pojo client to find out if the constructor and field accesses
    * joinpoints are wrapped.
    */
   private class POJOClientIntrospector extends ExprEditor
   {
      public void edit(NewExpr newExpr)
      {
         // pojo constructor is being invoked.
         if (newExpr.getClassName().equals(getClass().getPackage().getName() + "scenario.POJO"))
         {
            constructorWrapped = false;
         }
      }
      
      public void edit(FieldAccess fieldAccess)
      {
         if (fieldAccess.getFieldName().equals("counter") &&
               fieldAccess.getClassName().equals(getClass().getPackage().getName() + "scenario.POJO"))
         {
            // pojo field value is being read
            if (fieldAccess.isReader())
            {
               fieldReadWrapped = false;
            }
            // pojo field value is being written
            else
            {  
               fieldWriteWrapped = false;
            }
         }
      }
   }
   
   /**
    * Inspects a wrapper to find out if it is calling any class advisor method.
    * @author fla
    *
    */
   private class WrapperIntrospector extends ExprEditor
   {
      boolean wrapperEmpty = true; // indicates that the wrapper doesn't call interceptors
      
      public void edit(MethodCall call)
      {
         if (call.getClassName().equals(ClassAdvisor.class.getName())
         || call.getClassName().equals(InvocationBase.class.getName()))
         {
            wrapperEmpty = false;
         }
      }
   }

   private boolean constructorWrapped;
   private boolean fieldReadWrapped;
   private boolean fieldWriteWrapped;
   private boolean methodWrapped;
   public POJOWrappingInfo(){}
   
   /**
    * Constructor. Retrieves the POJO wrapping state and store this information
    * at the instance being created. 
    * @throws CannotCompileException
    * @throws NotFoundException 
    */
   public POJOWrappingInfo(CtClass pojoClass, CtField field, CtMethod method,
         String notAdvisedMethod, CtClass pojoClient) throws CannotCompileException, NotFoundException
   {
      this.constructorWrapped = true;
      this.fieldReadWrapped = true;
      this.fieldWriteWrapped = true;
      this.methodWrapped = false;
      if (pojoClass.isFrozen())
      {
         pojoClass.defrost();
      }
      method.instrument(new POJOMethodIntrospector(notAdvisedMethod));
      if (pojoClient.isFrozen())
      {
         pojoClient.defrost();
      }
      pojoClient.instrument(new POJOClientIntrospector());
      if (this.constructorWrapped)
      {
         // check if the wrapper calls interceptors: if it does not, constructor
         // is unwrapped after being previously wrapped
         String constructorWrapper = ConstructorExecutionTransformer.constructorFactory(pojoClass.getSimpleName()); 
         constructorWrapped = !isWrapperEmpty(pojoClass, constructorWrapper);
      }
      if (this.fieldReadWrapped)
      {
         // check if the wrapper calls interceptors: if it does not, field read
         // is unwrapped after being previously wrapped
         String fieldWrapper = field.getName() + "_r_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX; 
         fieldReadWrapped = !isWrapperEmpty(pojoClass, fieldWrapper);
      }
      if (this.fieldWriteWrapped)
      {
         // check if the wrapper calls interceptors: if it does not, field write
         // is unwrapped after being previously wrapped
         String fieldWrapper = field.getName() + "_w_" + ClassAdvisor.NOT_TRANSFORMABLE_SUFFIX; 
         fieldWriteWrapped = !isWrapperEmpty(pojoClass,fieldWrapper);
      }
   }

   /**
    * Checks if the wrapper calls advisor methods. If it does not, 
    * wrapper target (the joinpoint) is unwrapped.
    * @param wrapperName the name of the POJO wrapper method.
    * @return <code>true</code> if the wrapper is empty.
    */
   private boolean isWrapperEmpty(CtClass pojoClass, String wrapperName) throws CannotCompileException
   {
      CtMethod wrapper;
      try {
          wrapper = pojoClass.getDeclaredMethod(wrapperName);
      } catch (NotFoundException e)
      {
         return true;
      }
      WrapperIntrospector introspector = new WrapperIntrospector();
      wrapper.instrument(introspector);
      return introspector.wrapperEmpty;
   }
   
   /**
    * Returns <code>true</code> if the pojo constructor execution joinpoint is wrapped.
    */
   public boolean isConstructorWrapped()
   {
      return this.constructorWrapped;
   }
   
   /**
    * Returns <code>true</code> if the pojo "counter" field read joinpoint is wrapped.
    */
   public boolean isFieldReadWrapped()
   {
      return this.fieldReadWrapped;
   }

   /**
    * Returns <code>true</code> if the pojo "counter" field write joinpoint is wrapped.
    */
   public boolean isFieldWriteWrapped()
   {
      return this.fieldWriteWrapped;
   }
   
   /**
    * Returns <code>true</code> if the pojo "someMethod" method execution joinpoint is wrapped.
    */
   public boolean isMethodWrapped()
   {
      return this.methodWrapped;
   }
}