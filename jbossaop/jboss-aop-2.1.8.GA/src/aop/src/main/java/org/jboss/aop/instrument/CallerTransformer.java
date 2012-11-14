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
import java.util.HashMap;
import java.util.List;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.util.Advisable;
import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.logging.AOPLogger;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

/**
 * Instruments Caller pointcuts
 *
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */

public abstract class CallerTransformer
{
   private static final AOPLogger logger = AOPLogger.getLogger(CallerTransformer.class);
   
   public static final String CON_BY_CON_INFO_CLASS_NAME = org.jboss.aop.ConByConInfo.class.getName();
   public static final String CON_BY_METHOD_INFO_CLASS_NAME = org.jboss.aop.ConByMethodInfo.class.getName();
   public static final String METHOD_BY_CON_INFO_CLASS_NAME = org.jboss.aop.MethodByConInfo.class.getName();
   public static final String METHOD_BY_METHOD_INFO_CLASS_NAME = org.jboss.aop.MethodByMethodInfo.class.getName();
   
   Instrumentor instrumentor;
   boolean optimize;
   AspectManager manager;
   CallerInfoAdder callerInfoAdder;

   // Static --------------------------------------------------------
		 
   // Constructors --------------------------------------------------
   protected CallerTransformer(
         Instrumentor instrumentor, 
         AspectManager manager, 
         boolean optimize,
         CallerInfoAdder callerInfoAdder)
   {
      this.instrumentor = instrumentor;
      this.optimize = optimize;
      this.manager = manager;
      this.callerInfoAdder = callerInfoAdder;
   }

   protected abstract CallerExprEditor callerExprEditorFactory(ClassAdvisor advisor, CtClass clazz);
   
   public boolean applyCallerPointcuts(CtClass clazz, ClassAdvisor advisor) throws CannotCompileException
   {
      if (!advisor.getManager().isWithin() && !advisor.getManager().isCall() && !advisor.getManager().isWithincode())
      {
         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("There are no caller pointcuts!");
         return false;
      }
      CallerExprEditor expr = callerExprEditorFactory(advisor, clazz);
      CtMethod[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++)
      {
         if (Advisable.isAdvisable(methods[i]))
         {
            methods[i].instrument(expr);
         }
      }

      CtConstructor[] cons = clazz.getDeclaredConstructors();
      for (int i = 0; i < cons.length; i++)
      {
         cons[i].instrument(expr);
      }

      return expr.appliedCallerBinding;
   }

   private boolean isTargetConstructorAdvised(CtConstructor calledConstructor)
   {
      try
      {
         ClassAdvisor adv = manager.getTempClassAdvisor(calledConstructor.getDeclaringClass());
         return ConstructorExecutionTransformer.isAdvisableConstructor(calledConstructor, adv);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
      

   private static String getHashString(long hash)
   {
      if (hash < 0)
      {
         return "_N_" + (-1 * hash);
      }
      else
      {
         return "_" + hash;
      }
   }
   
   protected static String getUniqueInvocationFieldname(long callingHash, String classname, long calledHash)
   {
      classname = classname.replace('.', '_');
      classname = classname.replace('/', '_');

      return getHashString(callingHash) + classname + getHashString(calledHash);
   }

   protected static String getConByConInfoName(long callingIndex, String classname, long calledHash)
   {
      return "aop$constructorCall_con_" + getUniqueInvocationFieldname(callingIndex, classname, calledHash);
   }

   protected static String getConByMethodInfoName(long callingHash, String classname, long calledHash)
   {
      return "aop$constructorCall_" + getUniqueInvocationFieldname(callingHash, classname, calledHash);
   }
   
   protected static String getMethodByConInfoName(int index, String classname, long calledHash)
   {
      return "aop$methodCall_con_" + getUniqueInvocationFieldname(index, classname, calledHash);
   }
   
   protected static String getMethodByMethodInfoName(long callingHash, String classname, long calledHash)
   {
      return "aop$methodCall_" + getUniqueInvocationFieldname(callingHash, classname, calledHash);
   }

   protected static String conByConInfoFromWeakReference(String localName, String infoName)
   {
      return TransformerCommon.infoFromWeakReference(CON_BY_CON_INFO_CLASS_NAME, localName, infoName);
   }

   protected static String conByMethodInfoFromWeakReference(String localName, String infoName)
   {
      return TransformerCommon.infoFromWeakReference(CON_BY_METHOD_INFO_CLASS_NAME, localName, infoName);
   }

   protected static String methodByMethodInfoFromWeakReference(String localName, String infoName)
   {
      return TransformerCommon.infoFromWeakReference(METHOD_BY_METHOD_INFO_CLASS_NAME, localName, infoName);
   }

   protected static String methodByConInfoFromWeakReference(String localName, String infoName)
   {
      return TransformerCommon.infoFromWeakReference(METHOD_BY_CON_INFO_CLASS_NAME, localName, infoName);
   }

   
   protected class ConstructorDetail
   {
      MethodCall call;
      CtConstructor con;
      int callingIndex;
      long calledHash;
      String callerInfoField;
      CtMethod calledMethod;
      String classname;
      
      ConstructorDetail(CallerExprEditor editor, MethodCall call, String classname)throws NotFoundException
      {
         this.call = call;
         con = (CtConstructor) call.where();
         callingIndex = editor.constructors.indexOf(con);
         calledHash = JavassistMethodHashing.methodHash(call.getMethod());
         callerInfoField = getMethodByConInfoName(callingIndex, classname, calledHash);
         calledMethod = call.getMethod();
         this.classname = classname;
      }
   }

   protected class MethodDetail
   {
      MethodCall call;
      CtMethod where;
      long callingHash;
      long calledHash;
      String callerInfoField;
      CtMethod calledMethod;
      String classname;
      
      MethodDetail(CallerExprEditor editor, MethodCall call, String classname) throws NotFoundException
      {
         this.call = call;
         where = (CtMethod) call.where();
         callingHash = JavassistMethodHashing.methodHash((where));
         calledHash = JavassistMethodHashing.methodHash(call.getMethod());
         callerInfoField = getMethodByMethodInfoName(callingHash, classname, calledHash);
         calledMethod = call.getMethod();
         
         this.classname = classname;
      }
   }

   protected class ConByMethodDetail
   {
      NewExpr call;
      boolean isTgtConAdvised;
      CtMethod where;
      long callingHash;
      long calledHash;
      String callerInfoField;
      CtConstructor calledConstructor;
      String classname;
   
      ConByMethodDetail(CallerExprEditor editor, NewExpr call, String classname)throws NotFoundException
      {
         this.call = call;
         where = (CtMethod) call.where();
         callingHash = JavassistMethodHashing.methodHash(where);
         calledHash = JavassistMethodHashing.constructorHash(call.getConstructor());
         callerInfoField = getConByMethodInfoName(callingHash, classname, calledHash);
         calledConstructor = call.getConstructor();
         this.classname = classname;
         isTgtConAdvised = isTargetConstructorAdvised(calledConstructor);
      }
   }

   protected class ConByConDetail
   {
      NewExpr call;
      boolean isTgtConAdvised;
      CtConstructor con;
      int callingIndex;
      long calledHash;
      String callerInfoField;
      CtConstructor calledConstructor;
      String classname;
   
      ConByConDetail(CallerExprEditor editor, NewExpr call, String classname)throws NotFoundException
      {
         this.call = call;
         con = (CtConstructor) call.where();
         callingIndex = editor.constructors.indexOf(con);
         calledHash = JavassistMethodHashing.constructorHash(call.getConstructor());
         callerInfoField = getConByConInfoName(callingIndex, classname, calledHash);
         calledConstructor = call.getConstructor();
         this.classname = classname;
         isTgtConAdvised = isTargetConstructorAdvised(calledConstructor);
      }
   }

   abstract class CallerExprEditor extends ExprEditor
   {
      CtClass callingClass;
      ClassAdvisor advisor;
      List<CtConstructor> constructors;
      public boolean appliedCallerBinding = false;

      HashMap<String, String> callerInfos = new HashMap<String, String>();
      int invocationCounter = 0;

      public CallerExprEditor(ClassAdvisor advisor, CtClass callingClass)
      {
         this.advisor = advisor;
         this.callingClass = callingClass;
         this.constructors = instrumentor.getConstructors(callingClass);
      }

      private synchronized String getUniqueInvocationClassname(String classname)
      {
         return classname + "_" + (++invocationCounter) + "_";
      }

      protected String getOptimizedConCalledByMethodInvocationClassName(long callingHash, String classname, long calledHash)
      {
         return getUniqueInvocationClassname(classname) + "ConByMInvocation";
      }

      protected String getOptimizedConCalledByConInvocationClassName(long callingIndex, String classname, long calledHash)
      {
         return getUniqueInvocationClassname(classname) + "ConByConInvocation";
      }

      protected String getOptimizedMethodCalledByMethodClassName(long callingHash, String classname, long calledHash)
      {
         return getUniqueInvocationClassname(classname) + "MByMInvocation";
      }

      protected String getOptimizedMethodCalledByConstructorClassName(int callingIndex, String classname, long calledHash)
      {
         return getUniqueInvocationClassname(classname) + "MByConInvocation";
      }

      public void edit(MethodCall call) throws CannotCompileException
      {
         String classname = call.getClassName();
         String methodName = call.getMethodName();
         try
         {
            if (ClassAdvisor.isWithoutAdvisement(methodName)
            || methodName.startsWith("_")
            || classname.startsWith("org.jboss.aop.")
            || call.getMethodName().equals("class$") // todo not sure why this is part of the method call
            || !Instrumentor.isTransformable(callingClass)
            )
            {
               return;
            }

            CtBehavior behavior = call.where();

            boolean hasPointcut = false;

            DeclareChecker.checkDeclares(manager, call, advisor);
            
            Collection<Pointcut> pointcuts = manager.getBindingCollection().getMethodCallPointcuts();
            for (Pointcut p : pointcuts)
            {
               if (p.matchesCall(advisor, call))
               {
                  hasPointcut = true;
                  break;
               }
               else
               {
                  if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("MethodCall does not match: " + p.getExpr());
               }
            }

            if (hasPointcut)
            {
               if (behavior instanceof CtMethod)
                  modifyMethod(call, classname);
               else if (behavior instanceof CtConstructor) modifyConstructor(call, classname);
            }
         }
         catch (Exception ex)
         {
            logger.error("error getting:" + classname + ". '" + methodName + "'");
            ex.printStackTrace();
            throw new CannotCompileException(ex);
         }
      }

      
      private void modifyConstructor(MethodCall call, String classname) throws NotFoundException, CannotCompileException
      {
         instrumentor.setupBasics(callingClass);
         ConstructorDetail cd = new ConstructorDetail(this, call, classname);
         setupConstructor(cd);
         replaceMethodCallInCon(cd);
         appliedCallerBinding = true;
      }
      
      /** Replaces constructor call with standard non optimized invocation code. Up to subclasses to override this behaviour
       */
      protected void replaceMethodCallInCon(ConstructorDetail cd)throws CannotCompileException, NotFoundException
      {
         String replaced =
            methodByConInfoFromWeakReference("info", cd.callerInfoField) +
            "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
            "$_ = ($r)aop$classAdvisor$aop.invokeConstructorCaller(info, this, $0, $args);" + 
            //+ cd.callingIndex + ", $0, $args, " + cd.callerInfoField + "); " +
            "} else { " +
            "$_ = $proceed($$); " +
            "}";
            cd.call.replace(replaced);
      }

      private void modifyMethod(MethodCall call, String classname) throws NotFoundException, CannotCompileException
      {
         instrumentor.setupBasics(callingClass);
         MethodDetail md = new MethodDetail(this, call, classname);
         setupMethod(md);
         replaceMethodCallInMethod(md);
         appliedCallerBinding = true;
      }

      /** Replaces method call with standard non optimized invocation code. Up to subclasses to override this behaviour
       */
      protected void replaceMethodCallInMethod(MethodDetail md)throws NotFoundException, CannotCompileException
      {
         String callingObject = ", null";
         if (!Modifier.isStatic(md.where.getModifiers()))
         {
            callingObject = ", this";
         }
         String replaced =
            methodByMethodInfoFromWeakReference("info", md.callerInfoField) +
            "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
            "$_ = ($r)aop$classAdvisor$aop.invokeCaller(info" + callingObject + ", $0, $args);" +
            //+ md.callingHash + "L, $0, $args, " + md.callerInfoField + callingObject + "); " +
            "} else { " +
            "$_ = $proceed($$); " +
            "}";
            md.call.replace(replaced);
      }
      
      
      public void edit(NewExpr call) throws CannotCompileException
      {
         try
         {
            String classname = call.getClassName();
            if (classname.startsWith("org.jboss.aop.") || !Instrumentor.isTransformable(callingClass))
            {
               return;
            }
            
            DeclareChecker.checkDeclares(manager, call, advisor);
            
            boolean hasPointcut = false;

            Collection<Pointcut> pointcuts = manager.getBindingCollection().getConstructorCallPointcuts();
            for (Pointcut p : pointcuts)
            {
               if (p.matchesCall(advisor, call))
               {
                  hasPointcut = true;
                  break;
               }
            }
            if (hasPointcut)
            {
               CtBehavior behavior = call.where();
               if (behavior instanceof CtMethod)
                  modifyMethod(call, classname);
               else if (behavior instanceof CtConstructor) modifyConstructor(call, classname);
            }
         }
         catch (Exception ex)
         {
            logger.error(ex.getMessage());
            throw new CannotCompileException(ex);
         }
      }

      private void modifyMethod(NewExpr call, String classname) throws Exception, NotFoundException, CannotCompileException
      {
         instrumentor.setupBasics(callingClass);
         ConByMethodDetail cd = new ConByMethodDetail(this, call, classname);
         setupMethod(cd);
         replaceConCallInMethod(cd);
         appliedCallerBinding = true;
      }

      /** Replaces constructor call with standard non optimized invocation code. Up to subclasses to override this behaviour
       */
      protected void replaceConCallInMethod(ConByMethodDetail cd) throws NotFoundException, CannotCompileException
      {
         String callingObject = "null";
         if (!Modifier.isStatic(cd.where.getModifiers()))
         {
            callingObject = "this";
         }

         String replaced =
            conByMethodInfoFromWeakReference("info", cd.callerInfoField) +
            "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
            "java.lang.Object callingObject = " + callingObject + "; " +
            "$_ = ($r)aop$classAdvisor$aop.invokeConCalledByMethod(info, " + callingObject + ", $args);" +
            //+ cd.callingHash + "L, $args, " + cd.callerInfoField + ", callingObject); " +
            "} else { " +
            "$_ = $proceed($$); " +
            "}";

            cd.call.replace(replaced);
      }

      private void modifyConstructor(NewExpr call, String classname) throws Exception, NotFoundException, CannotCompileException
      {
         instrumentor.setupBasics(callingClass);
         ConByConDetail cd = new ConByConDetail(this, call, classname);
         setupConstructor(cd);
         replaceConCallInCon(cd);
         appliedCallerBinding = true;
      }

      /** Replaces constructor call with standard non optimized invocation code. Up to subclasses to override this behaviour
       */
      protected void replaceConCallInCon(ConByConDetail cd)throws CannotCompileException, NotFoundException
      {
         String replaced =
            conByConInfoFromWeakReference("info", cd.callerInfoField) +
            "if (info.getInterceptors() != (org.jboss.aop.advice.Interceptor[])null) { " +
            "$_ = ($r)aop$classAdvisor$aop.invokeConCalledByCon(info, this, $args);" +
            //+ cd.callingIndex + ", $args, " + cd.callerInfoField + "); " +
            "} else { " +
            "$_ = $proceed($$); " +
            "}";

            cd.call.replace(replaced);
      }
      
      protected abstract void setupConstructor(ConstructorDetail cd)throws NotFoundException, CannotCompileException;
      protected abstract void setupMethod(MethodDetail md) throws NotFoundException, CannotCompileException;
      protected abstract void setupMethod(ConByMethodDetail cd) throws NotFoundException, CannotCompileException;
      protected abstract void setupConstructor(ConByConDetail cd)throws NotFoundException, CannotCompileException;
   
   }
}
