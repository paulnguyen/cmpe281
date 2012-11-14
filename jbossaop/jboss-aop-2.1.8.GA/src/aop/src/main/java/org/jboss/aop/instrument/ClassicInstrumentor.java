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
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.classpool.AOPClassPool;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class ClassicInstrumentor extends Instrumentor
{
   /**
    * Name of helper class.
    */
   public static final String HELPER_CLASS_NAME =
           Instrumentor.AOP_PACKAGE + ".ClassAdvisor";
   
   public ClassicInstrumentor(AOPClassPool pool, AspectManager manager, JoinpointClassifier joinpointClassifier, DynamicTransformationObserver observer)
   {
      super(pool, 
            manager, 
            joinpointClassifier, 
            observer);
   }

   public ClassicInstrumentor(AspectManager manager, JoinpointClassifier joinpointClassifier)
   {
      super(manager, joinpointClassifier);
   }

   protected void intitialiseTransformers()
   {
      
      if (AspectManager.optimize)
      {
         methodExecutionTransformer = new OptimizedMethodExecutionTransformer(this);
         constructorExecutionTransformer = new OptimizedConstructorExecutionTransformer(this);
         fieldAccessTransformer = new OptimizedFieldAccessTransformer(this);
         constructionTransformer = new OptimizedConstructionTransformer(this);
         callerTransformer = new OptimizedCallerTransformer(this, manager);
      }
      else
      {
         methodExecutionTransformer = new NonOptimizedMethodExecutionTransformer(this);
         constructorExecutionTransformer = new NonOptimizedConstructorExecutionTransformer(this);
         fieldAccessTransformer = new NonOptimizedFieldAccessTransformer(this);
         constructionTransformer = new NonOptimizedConstructionTransformer(this);
         callerTransformer = new NonOptimizedCallerTransformer(this, manager);
      }
   }
   
   protected CtMethod createMixinInvokeMethod(CtClass clazz, CtClass mixinClass, String initializer, CtMethod method, long hash)
   throws CannotCompileException, NotFoundException, Exception
   {
      String code = null;
      String aopReturnStr = (method.getReturnType().equals(CtClass.voidType)) ? "" : "return ($r)";
      String returnStr = (method.getReturnType().equals(CtClass.voidType)) ? "" : "return ";
      String args = "null";
      if (method.getParameterTypes().length > 0) args = "$args";
      //If a call to a method introduced is intercepted, this original code uses the mixin class as the target
      //object. This causes a ClassCastException in PerInstanceAdvice since the mixin class does not implement
      //Advised
      //  code =
      //          "{ " +
      //          "   if (" + mixinFieldName(mixinClass) + " == null) { " +
      //          "      this." + mixinFieldName(mixinClass) + " = " + initializer + "; " +
      //          "   } " +
      //          "   if (" + HELPER_FIELD_NAME + ".doesHaveAspects || (_instanceAdvisor != null && _instanceAdvisor.hasInstanceAspects)) " +
      //          "   { " +
      //          "      Object[] ags = " + args + "; " +
      //          "      " + aopReturnStr  + HELPER_FIELD_NAME + ".invokeMethod(_instanceAdvisor, this." + mixinFieldName(mixinClass) + ", " + hash + "L, ags); " +
      //          "   } " +
      //          "   else " +
      //          "   {" +
      //          "      " + returnStr + " " + mixinFieldName(mixinClass) + "." + method.getName() + "($$); " +
      //          "   }" +
      //          "}";
      
      
      
      //1) add a not advised method that performs the call to the mixin class
      code =
      "{ " +
      "" + returnStr + " " + mixinFieldName(mixinClass) + "." + method.getName() + "($$); " +
      "}";
      String wrappedName = ClassAdvisor.notAdvisedMethodName(clazz.getName(), method.getName());
      CtMethod nmethod = CtNewMethod.make(method.getReturnType(), wrappedName, method.getParameterTypes(),
                                        method.getExceptionTypes(), code, clazz);
      int modifier = method.getModifiers();
      if ((modifier & AccessFlag.ABSTRACT) == AccessFlag.ABSTRACT)
      {
         modifier &= ~AccessFlag.ABSTRACT; 
      }
      nmethod.setModifiers(modifier);
      addSyntheticAttribute(nmethod);
      clazz.addMethod(nmethod);
      
      
      
      //2 make the wrapped invocation call through to the helper method, using this as the target object
      code =
      "{ " +
      "   if (" + mixinFieldName(mixinClass) + " == null) { " +
      "      this." + mixinFieldName(mixinClass) + " = " + initializer + "; " +
      "   } " +
      "   org.jboss.aop.ClassInstanceAdvisor instAdv = (org.jboss.aop.ClassInstanceAdvisor)_getInstanceAdvisor();" +
      "   if (" + HELPER_FIELD_NAME + ".hasAspects() || (instAdv != null && instAdv.hasInstanceAspects)) " +
      "   { " +
      "      Object[] ags = " + args + "; " +
      "      " + aopReturnStr + HELPER_FIELD_NAME + ".invokeMethod(instAdv, this, " + hash + "L, ags); " +
      "   } " +
      "   else " +
      "   {" +
      "      " + returnStr + " " + mixinFieldName(mixinClass) + "." + method.getName() + "($$); " +
      "   }" +
      "}";
      
      CtMethod wmethod = CtNewMethod.make(method.getReturnType(), method.getName(), method.getParameterTypes(),
               method.getExceptionTypes(), code, clazz);
         wmethod.setModifiers(modifier);
      clazz.addMethod(wmethod);
      
      return wmethod;
   
   }
   
   protected void doSetupBasics(CtClass clazz) throws CannotCompileException, NotFoundException
   {
      // add aop helper class.
      addHelperClass(clazz);

      if (isBaseClass(clazz))
      {
         addBaseElements(clazz);
      }
   }
   
   private void addHelperField(CtClass clazz) throws CannotCompileException, NotFoundException
   {
      addStaticField(clazz,
                     ClassicInstrumentor.HELPER_FIELD_NAME,
                     ClassicInstrumentor.HELPER_CLASS_NAME,
                     CtField.Initializer.byExpr(ASPECT_MANAGER_CLASS_NAME + ".instance().getAdvisor(java.lang.Class#forName(\"" +
                                                clazz.getName() + "\"))"));
   }

   /**
    * Adds a static helper class instance to passed class.
    */
   private void addHelperClass(CtClass clazz)
           throws CannotCompileException, NotFoundException
   {
      addHelperField(clazz);
      // To make sure that correct Advisor gets called
      CtMethod getter = CtNewMethod.make("public org.jboss.aop.Advisor _getAdvisor()" +
                                         "{ " +
                                         "    return " + ClassicInstrumentor.HELPER_FIELD_NAME + ";" +
                                         "}",
                                         clazz);
      addSyntheticAttribute(getter);
      clazz.addMethod(getter);
   }
   
   private void addBaseElements(CtClass clazz)
   throws NotFoundException, CannotCompileException
   {
      addProtectedField(clazz,
                "_instanceAdvisor",
                "org.jboss.aop.ClassInstanceAdvisor", null
                //CtField.Initializer.byExpr("new org.jboss.aop.ClassInstanceAdvisor(this)")
         );

         CtMethod getter = CtNewMethod.make("public org.jboss.aop.InstanceAdvisor _getInstanceAdvisor()" +
                                 "{ " +
                                 "    synchronized(this) {" +
                                 "       if (_instanceAdvisor == null) { _instanceAdvisor = new org.jboss.aop.ClassInstanceAdvisor(this); }" +
                                 "       return _instanceAdvisor;" +
                                 "    } " +
                                 "}",
                                 clazz);
         addSyntheticAttribute(getter);
         clazz.addMethod(getter);

         CtMethod setter = CtNewMethod.make("public void _setInstanceAdvisor(org.jboss.aop.InstanceAdvisor newAdvisor)" +
                                 "{ " +
                                 "    synchronized(this) {" +
                                 "       _instanceAdvisor = (org.jboss.aop.ClassInstanceAdvisor)newAdvisor;" +
                                 "    } " +
                                 "}",
                                 clazz);
         addSyntheticAttribute(setter);
         clazz.addMethod(setter);

}

   
   
}
