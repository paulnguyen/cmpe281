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
package org.jboss.aop.pointcut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aop.annotation.PortableAnnotationElement;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.pointcut.ast.ASTAttribute;
import org.jboss.aop.pointcut.ast.ASTConstructor;
import org.jboss.aop.pointcut.ast.ASTException;
import org.jboss.aop.pointcut.ast.ASTField;
import org.jboss.aop.pointcut.ast.ASTMethod;
import org.jboss.aop.pointcut.ast.ASTParameter;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.util.JavassistMethodHashing;
import org.jboss.aop.util.MethodHashing;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 88427 $
 */
public class Util
{
   public static boolean matchesClassExpr(ClassExpression classExpr, CtClass clazz, Advisor advisor)
   {
      try
      {
         if (classExpr.isAnnotation())
         {
            String sub = classExpr.getOriginal().substring(1);
            if (advisor != null)
            {
               if (advisor.getClassMetaData().hasTag(sub)) return true;
               return advisor.hasAnnotation(clazz, sub);
            }
            else
            {
               return AnnotationElement.isAnyAnnotationPresent(clazz, sub);
            }
         }
         else if (classExpr.isInstanceOf())
         {
            return Util.subtypeOf(clazz, classExpr, advisor);
         }
         else if (classExpr.isTypedef())
         {
            return Util.matchesTypedef(clazz, classExpr, advisor);
         }
         else
         {
            return classExpr.matches(clazz.getName());
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }

   }

   public static boolean matchesClassExpr(ClassExpression classExpr, Class<?> clazz)
   {
      return matchesClassExpr(classExpr, clazz, null);
   }
   
   public static boolean matchesClassExpr(ClassExpression classExpr, Class<?> clazz, Advisor advisor)
   {
      try
      {
         if (classExpr.isAnnotation())
         {
            String sub = classExpr.getOriginal().substring(1);
            if (advisor != null)
            {
               if (advisor.getClassMetaData().hasTag(sub)) return true;
               return advisor.hasAnnotation(clazz, sub);
            }
            else
            {
               return AnnotationElement.isAnyAnnotationPresent(clazz, sub);
            }
         }
         else if (classExpr.isInstanceOf())
         {
            return Util.subtypeOf(clazz, classExpr, advisor);
         }
         else if (classExpr.isTypedef())
         {
            return Util.matchesTypedef(clazz, classExpr, advisor);
         }
         else
         {            
            return(classExpr.matches(clazz.getName()));
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }

   }

   /**
    * @param method Method we are looking for
    * @param target ClassExpression with the class/interface we are looking for the method in
    */
   public static boolean methodExistsInSuperClassOrInterface(Method method, ClassExpression target, boolean exactSuper, Advisor advisor) throws Exception
   {
      long hash = MethodHashing.calculateHash(method);
      boolean exists = methodExistsInSuperClassOrInterface(hash, target, method.getDeclaringClass(), exactSuper);
      if (!exists)
      {
         exists = checkMethodExistsInIntroductions(hash, target, exactSuper, advisor);
      }
      return exists;
   }
   
   private static boolean methodExistsInSuperClassOrInterface(long hash, ClassExpression expr, Class<?> clazz, boolean exactSuper) throws Exception
   {
      if (clazz == null) return false;
      
      if (expr.isAnnotation())
      {
         String sub = expr.getOriginal().substring(1);
          if (AnnotationElement.isAnyAnnotationPresent(clazz, sub))
          {
             if (classHasMethod(clazz, hash, exactSuper)) return true;
          }
       }
       else if (expr.matches(clazz.getName()))
       {
          if (classHasMethod(clazz, hash, exactSuper)) return true;
       }

       Class<?>[] interfaces = clazz.getInterfaces();
       for (int i = 0; i < interfaces.length; i++)
       {
          if (methodExistsInSuperClassOrInterface(hash, expr, interfaces[i], exactSuper)) return true;
       }
       
       if (clazz.isInterface()) return false; // we are done
   
       return methodExistsInSuperClassOrInterface(hash, expr, clazz.getSuperclass(), exactSuper);
   }
   
   /**
    * When using the SystemClassLoader, trying to load up classes when using loadtime weaving gives ClassCircularityErrors,
    * so do this with javassist
    */
   private static boolean checkMethodExistsInIntroductions(long hash, ClassExpression target, boolean exactSuper, Advisor advisor) throws Exception
   {
      if (advisor != null)
      {
         ArrayList<InterfaceIntroduction> intros = advisor.getInterfaceIntroductions();
         if (intros.size() > 0)
         {
            ClassLoader tcl = Thread.currentThread().getContextClassLoader();
            ClassPool pool = advisor.getManager().findClassPool(tcl);
            HashSet<String> doneClasses = new HashSet<String>();
            for (InterfaceIntroduction intro : intros)
            {
               String[] ifs = intro.getInterfaces();
               for (int i = 0 ; ifs != null && i < ifs.length ; i++)
               {
                  if (!doneClasses.contains(ifs[i]))
                  {
                     doneClasses.add(ifs[i]);
                     if (methodExistsInSuperClassOrInterface(pool, hash, target, ifs[i], exactSuper)) return true;
                  }
               }
               
               ArrayList<InterfaceIntroduction.Mixin> mixins = intro.getMixins();
               if (mixins != null && mixins.size() > 0)
               {
                  for (InterfaceIntroduction.Mixin mixin : mixins)
                  {
                     String[] mifs = mixin.getInterfaces();
                     for (int i = 0 ; mifs != null && i < mifs.length ; i++)
                     {
                        if (!doneClasses.contains(mifs[i]))
                        {
                           doneClasses.add(mifs[i]);
                           if (methodExistsInSuperClassOrInterface(pool, hash, target, mifs[i], exactSuper)) return true;
                        }
                     }
                  }
               }
            }
         }
      }      
      return false;
   }
   
   private static boolean classHasMethod(Class<?> clazz, long hash, boolean exactSuper)throws Exception
   {
      Method m = MethodHashing.findMethodByHash(clazz, hash);
      if (m != null)
      {
         if (exactSuper)
         {
            //MethodHashing will check all super classes so make sure it is on the class itself
            return clazz == m.getDeclaringClass();
         }
         else
         {
            return true;
         }
      }
      
      return false;
   }

   /**
    * @param method CtMethod we are looking for
    * @param target ClassExpression with the class/interface we are looking for the method in
    */
   public static boolean methodExistsInSuperClassOrInterface(CtMethod method, ClassExpression target, boolean exactSuper) throws Exception
   {
      long hash = JavassistMethodHashing.methodHash(method);
      return methodExistsInSuperClassOrInterface(hash, target, method.getDeclaringClass(), exactSuper);
   }
   
   private static boolean methodExistsInSuperClassOrInterface(ClassPool pool, long hash, ClassExpression expr, String className, boolean exactSuper) throws Exception
   {
      CtClass clazz = pool.get(className);
      return methodExistsInSuperClassOrInterface(hash, expr, clazz, exactSuper);
   }
   
   private static boolean methodExistsInSuperClassOrInterface(long hash, ClassExpression expr, CtClass clazz, boolean exactSuper) throws Exception
   {
      if (clazz == null) return false;
      
      if (expr.isAnnotation())
      {
         String sub = expr.getOriginal().substring(1);
         if (AnnotationElement.isAnyAnnotationPresent(clazz, sub))
         {
            if (classHasMethod(clazz, hash, exactSuper)) return true;
         }
      }
      else if (expr.matches(clazz.getName()))
      {
         if (classHasMethod(clazz, hash, exactSuper)) return true;
      }
      
      CtClass[] interfaces = clazz.getInterfaces();
      for (int i = 0; i < interfaces.length; i++)
      {
         if (methodExistsInSuperClassOrInterface(hash, expr, interfaces[i], exactSuper)) return true;
      }
      if (clazz.isInterface()) return false; // we are done
      
      return methodExistsInSuperClassOrInterface(hash, expr, clazz.getSuperclass(), exactSuper);
   }
   
   private static boolean classHasMethod(CtClass clazz, long hash, boolean exactSuper)throws Exception
   {
      HashMap<Long, CtMethod> methods = JavassistMethodHashing.getMethodMap(clazz);
      CtMethod m = methods.get(new Long(hash));
      if (m != null)
      {
         if (exactSuper)
         {
            //If a class, JavassistMethodHashing will check all super classes so make sure it is on the class itself
            return clazz == m.getDeclaringClass();
         }
         else
         {
            return true;
         }
      }
      
      if (clazz.isInterface() && !exactSuper)
      {
         CtClass[] interfaces = clazz.getInterfaces();
         for (int i = 0 ; i < interfaces.length ; i++)
         {
            if (classHasMethod(interfaces[i], hash, exactSuper)) return true;
         }
      }
      return false;
   }
   
   public static boolean subtypeOf(CtClass clazz, ClassExpression instanceOf, Advisor advisor)
   {
      try
      {
	      if (clazz == null) return false;
	      if (instanceOf.isInstanceOfAnnotated())
	      {
            if (clazz.isPrimitive()) return false;
            String sub = instanceOf.getInstanceOfAnnotation().substring(1);
            if (PortableAnnotationElement.isAnyAnnotationPresent(clazz, sub)) return true;
	      }
	      else if (instanceOf.matches(clazz.getName()))
	      {
	         return true;
	      }
	      CtClass[] interfaces = clazz.getInterfaces();
	      for (int i = 0; i < interfaces.length; i++)
	      {
	         if (subtypeOf(interfaces[i], instanceOf, advisor)) return true;
	      }
	      if (clazz.isInterface()) return false; // we are done
	
         if (checkIntroductions(clazz, instanceOf, advisor))
         {
            return true;
         }
         
	      return subtypeOf(clazz.getSuperclass(), instanceOf, advisor);
      } 
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   private static boolean checkIntroductions(CtClass clazz, ClassExpression instanceOf, Advisor advisor)
   {
      try
      {
         if (advisor != null)
         {
            ClassLoader cl = advisor.getClassLoader();
            ArrayList<InterfaceIntroduction> intros = advisor.getInterfaceIntroductions();
            if (intros.size() > 0)
            {
               for (InterfaceIntroduction intro : intros)
               {
                  String[] introductions = intro.getInterfaces();
                  if (introductions != null)
                  {
                     for (int i = 0 ; i < introductions.length ; i++)
                     {
                        Class<?> iface = cl.loadClass(introductions[i]);
                        if (subtypeOf(iface, instanceOf, advisor)) return true;
                     }
                  }
                  ArrayList<InterfaceIntroduction.Mixin> mixins = intro.getMixins();
                  if (mixins.size() > 0)
                  {
                     for (InterfaceIntroduction.Mixin mixin : mixins)
                     {
                        String[] mixinInterfaces = mixin.getInterfaces();
                        if (mixinInterfaces != null)
                        {
                           for (int i = 0 ; i < mixinInterfaces.length ; i++)
                           {
                              Class<?> iface = cl.loadClass(mixinInterfaces[i]);
                              if (subtypeOf(iface, instanceOf, advisor)) return true;                              
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      
      return false;
   }
   
   public static boolean subtypeOf(Class<?> clazz, ClassExpression instanceOf, Advisor advisor)
   {
      return MatcherStrategy.getMatcher(advisor).subtypeOf(clazz, instanceOf, advisor);
   }
   
   public static boolean has(CtClass target, ASTMethod method, Advisor advisor)
   {
      return has(target, method, advisor, true);
   }
   
   public static boolean has(CtClass target, ASTMethod method, Advisor advisor, boolean checkSuper)
   {
      try
      {
         CtMethod[] methods = target.getDeclaredMethods();
         for (int i = 0; i < methods.length; i++)
         {
            MethodMatcher matcher = new MethodMatcher(advisor, methods[i], null);
            if (matcher.matches(method).booleanValue()) return true;
         }
         
         if (checkSuper)
         {
            CtClass superClass = target.getSuperclass();
            if (superClass != null) return has(superClass, method, advisor, checkSuper);
         }
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   public static boolean has(CtClass target, ASTField field, Advisor advisor)
   {
      return has(target, field, advisor, true);
   }
   
   public static boolean has(CtClass target, ASTField field, Advisor advisor, boolean checkSuper)
   {
      try
      {
         CtField[] fields = target.getDeclaredFields();
         for (int i = 0; i < fields.length; i++)
         {
            FieldGetMatcher matcher = new FieldGetMatcher(advisor, fields[i], null);
            if (((Boolean) field.jjtAccept(matcher, null)).booleanValue()) return true;
         }
         
         if (checkSuper)
         {
            CtClass superClass = target.getSuperclass();
            if (superClass != null) return has(superClass, field, advisor, checkSuper);
         }
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   public static boolean has(CtClass target, ASTConstructor con, Advisor advisor)
   {
      try
      {
         CtConstructor[] cons = target.getDeclaredConstructors();
         for (int i = 0; i < cons.length; i++)
         {
            ConstructorMatcher matcher = new ConstructorMatcher(advisor, cons[i], null);
            if (matcher.matches(con).booleanValue()) return true;
         }
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
      }
      return false;
   }

   public static boolean has(Class<?> target, ASTMethod method, Advisor advisor)
   {
      return has(target, method, advisor, true);
   }
   
   public static boolean has(Class<?> target, ASTMethod method, Advisor advisor, boolean checkSuper)
   {
      Method[] methods = advisor.getAllMethods();
      if (methods == null)
         methods = SecurityActions.getDeclaredMethods(target);
      for (int i = 0; i < methods.length; i++)
      {
         MethodMatcher matcher = new MethodMatcher(advisor, methods[i], null);
         if (matcher.matches(method).booleanValue()) return true;
      }
      
      if (checkSuper)
      {
         Class<?> superClass = target.getSuperclass();
         if (superClass != null) return has(superClass, method, advisor, checkSuper);
      }
      return false;
   }

   public static boolean has(Class<?> target, ASTField field, Advisor advisor)
   {
      return has(target, field, advisor, true);
   }
   
   public static boolean has(Class<?> target, ASTField field, Advisor advisor, boolean checkSuper)
   {
      Field[] fields = SecurityActions.getDeclaredFields(target);
      for (int i = 0; i < fields.length; i++)
      {
         FieldGetMatcher matcher = new FieldGetMatcher(advisor, fields[i], null);
         if (((Boolean) field.jjtAccept(matcher, null)).booleanValue()) return true;
      }
      
      if (checkSuper)
      {
         Class<?> superClass = target.getSuperclass();
         if (superClass != null) return has(superClass, field, advisor, checkSuper);
      }
      return false;
   }

   public static boolean has(Class<?> target, ASTConstructor con, Advisor advisor)
   {
      Constructor<?>[] cons = SecurityActions.getDeclaredConstructors(target);
      for (int i = 0; i < cons.length; i++)
      {
         ConstructorMatcher matcher = new ConstructorMatcher(advisor, cons[i], null);
         if (matcher.matches(con).booleanValue()) return true;
      }
      return false;
   }

   public static boolean matchesTypedef(CtClass clazz, ClassExpression classExpr, Advisor advisor)
   {
      String original = classExpr.getOriginal();
      String typedefName = original.substring("$typedef{".length(), original.lastIndexOf("}"));
      AspectManager manager = (advisor != null) ? advisor.getManager() : AspectManager.instance(); 
      Typedef typedef = manager.getTypedef(typedefName);
      if (typedef == null) return false;
      return typedef.matches(advisor, clazz);
   }

   public static boolean matchesTypedef(Class<?> clazz, ClassExpression classExpr, Advisor advisor)
   {
      String original = classExpr.getOriginal();
      String typedefName = original.substring("$typedef{".length(), original.lastIndexOf("}"));
      AspectManager manager = (advisor != null) ? advisor.getManager() : AspectManager.instance(); 
      Typedef typedef = manager.getTypedef(typedefName);
      if (typedef == null) return false;
      return typedef.matches(advisor, clazz);
   }

   public static boolean matchModifiers(ASTAttribute need, int have)
   {
      switch (need.attribute)
      {
         case Modifier.ABSTRACT:
            return Modifier.isAbstract(have) != need.not;
         case Modifier.FINAL:
            return Modifier.isFinal(have) != need.not;
         case Modifier.INTERFACE:
            return Modifier.isInterface(have) != need.not;
         case Modifier.NATIVE:
            return Modifier.isNative(have) != need.not;
         case Modifier.PRIVATE:
            return Modifier.isPrivate(have) != need.not;
         case Modifier.PROTECTED:
            return Modifier.isProtected(have) != need.not;
         case Modifier.PUBLIC:
            return Modifier.isPublic(have) != need.not;
         case Modifier.STATIC:
            return Modifier.isStatic(have) != need.not;
         case Modifier.STRICT:
            return Modifier.isStrict(have) != need.not;
         case Modifier.SYNCHRONIZED:
            return Modifier.isSynchronized(have) != need.not;
         case Modifier.TRANSIENT:
            return Modifier.isTransient(have) != need.not;
         case Modifier.VOLATILE:
            return Modifier.isVolatile(have) != need.not;
         default:
            throw new RuntimeException("Unexpected modifier value: " + need.attribute);
      }
   }

   /**
    * @param nodeExceptions  ArrayList of ASTException entries for a given ASTMethod or ASTConstructor
    * @param foundExceptions Array of Exceptions found for a method/constructor
    */
   public static boolean matchExceptions(ArrayList<ASTException> nodeExceptions, CtClass[] foundExceptions)
   {
      if (nodeExceptions.size() > foundExceptions.length) return false;
      if (nodeExceptions.size() > 0)
      {
         for (ASTException ex : nodeExceptions)
         {
            boolean found = false;
            for (int i = 0; i < foundExceptions.length; i++)
            {
               if (ex.getType().matches(foundExceptions[i].getName()))
               {
                  found = true;
                  break;
               }
            }
   
            if (!found) return false;
         }
      }

      return true;
   }

   /**
    * @param nodeExceptions  ArrayList of ASTException entries for a given ASTMethod or ASTConstructor
    * @param foundExceptions Array of Exceptions found for a method/constructor
    */
   public static boolean matchExceptions(ArrayList<ASTException> nodeExceptions, Class<?>[] foundExceptions)
   {
      if (nodeExceptions.size() > foundExceptions.length) return false;
      for (Iterator<ASTException> it = nodeExceptions.iterator(); it.hasNext();)
      {
         boolean found = false;
         ASTException ex = it.next();
         for (int i = 0; i < foundExceptions.length; i++)
         {
            if (ex.getType().matches(foundExceptions[i].getName()))
            {
               found = true;
               break;
            }
         }

         if (!found) return false;
      }

      return true;
   }

   public static boolean matchesParameters(Advisor advisor, ASTMethod node, CtMethod ctMethod)
   {
      if (node.isAnyParameters()) return true;
      try
      {
         return Util.matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), ctMethod.getParameterTypes());
      }
      catch (NotFoundException e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

   public static boolean matchesParameters(Advisor advisor, ASTConstructor node, CtConstructor ctConstructor)
   {
      if (node.isAnyParameters()) return true;
      try
      {
         // JBAOP-722
         CtClass enclosingClass = ctConstructor.getDeclaringClass().getDeclaringClass();
         if (enclosingClass != null && ctConstructor.getParameterTypes().length == 1 && ctConstructor.getParameterTypes()[0].getName().startsWith(enclosingClass.getName() + '$'))
         {
            return Util.matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), new CtClass[0]);
         }
         CtClass[] params = ctConstructor.getParameterTypes();
         return Util.matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), params);
      }
      catch (NotFoundException e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

   public static boolean matchesParameters(Advisor advisor, ASTMethod node, Method method) 
   {
      if (node.isAnyParameters()) return true;
      return matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), method.getParameterTypes());
   }

   public static boolean matchesParameters(Advisor advisor, ASTConstructor node, Constructor<?> con) 
   {
      if (node.isAnyParameters()) return true;
      // JBAOP-722
      Class<?> enclosingClass = con.getDeclaringClass().getDeclaringClass();
      if (enclosingClass != null && con.getParameterTypes().length == 1 && con.getParameterTypes()[0].getName().startsWith(enclosingClass.getName()))
      {
         Class<?> parameterType = con.getParameterTypes()[0];
         if (parameterType.getDeclaringClass().equals(enclosingClass))
         {
            return Util.matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), new CtClass[0]);
         }
      }
      return matchesParameters(advisor, node.hasAnyZeroOrMoreParameters(), node.getParameters(), con.getParameterTypes());
   }

   private static boolean matchesParameters(Advisor advisor, boolean hasAnyZeroOrMoreParameters, ArrayList<ASTParameter> parameters, Class<?>[] params)
   {
      RefParameterMatcher matcher = new RefParameterMatcher(advisor, parameters, params);
      return matcher.matches();
   }

   private static boolean matchesParameters(Advisor advisor, boolean hasAnyZeroOrMoreParameters, ArrayList<ASTParameter> parameters, CtClass[] params)
   {
      CtParameterMatcher matcher = new CtParameterMatcher(advisor, parameters, params);
      return matcher.matches();
   }
   
   private static abstract class ParameterMatcher
   {
      Advisor advisor;
      ArrayList<ASTParameter> astParameters;
      final long paramsLength;
      private int asti;
      private int actuali;
      
      ParameterMatcher(Advisor advisor, ArrayList<ASTParameter> parameters, Object[] params)
      {
         this.advisor = advisor;
         this.astParameters = parameters;
         paramsLength = params.length;
      }

      boolean matches()
      {
         return matches(0, 0);
      }
      
      private boolean matches(int ast, int actual)
      {
         boolean match = true;
         for ( ; match && ast < astParameters.size() && actual < paramsLength  ; ast++)
         {
            if (isAnyZeroOrMoreParameters(ast))
            {
               asti = ast;
               actuali = actual;
               match = wildcard();//ast, cls);
               ast = asti;
               actual = actuali;
               ast--;
            }
            else
            {
               match = doMatch(ast, actual);
               actual++;
            }
         }
         
         while (match && ast < astParameters.size() && isAnyZeroOrMoreParameters(ast))
         {
            ast++;
         }
         return (match && ast == astParameters.size() && paramsLength == actual);
      }
      
      private boolean isAnyZeroOrMoreParameters(int index)
      {
         return astParameters.get(index).isAnyZeroOrMoreParameters();
      }
      
      abstract boolean doMatch(int astIndex, int actualIndex);
      
      private boolean wildcard()
      {
         // skip sequence of wildcards until the first non-wildcard parameter is
         // found
         do
         {
            asti++;
         } while (asti < astParameters.size() && isAnyZeroOrMoreParameters(asti));
         
         // no actual parameters to compare
         if (actuali == paramsLength)
         {
            // Didn't skip any actual parameter... so, if there are still
            // non-wildcard astParameters to compare, return false
            if (asti < astParameters.size())
            {
               return false;
            }
            //got to the end of both param list: return true
            else
            {
               return true;
            }
         }
         // one or more actual parameters to compare
         else
         {
            // backtracking: try to compare as is; if result is false, use wildcard
            // to skip unmatched parameters
            int currentAsti = asti;
            int currentActuali = actuali;
            while(!matches(asti, actuali) && actuali < paramsLength)
            {
               asti = currentAsti;
               actuali = currentActuali;
               do
               {
                  actuali++;
               }
               // avoid extra backtracking steps here: the param expressions won't
               // match
               while (actuali < paramsLength && asti < astParameters.size() && !doMatch(asti, actuali));
               currentAsti = asti;
               currentActuali = actuali;
            }
            return true;
         }
      }
   }
   
   private static class RefParameterMatcher extends ParameterMatcher
   {
      Class<?>[] params;
      public RefParameterMatcher(Advisor advisor, ArrayList<ASTParameter> parameters, Class<?>[] params)
      {
         super(advisor, parameters, params);
         this.params = params;
      }
      
      boolean doMatch(int astIndex, int actualIndex)
      {
         ASTParameter ast = astParameters.get(astIndex);
         ClassExpression exp = ast.getType();

         if (exp.isSimple())
         {
            String asString = ClassExpression.simpleType(params[actualIndex]);
            if (!exp.matches(asString)) return false;
         }
         else
         {
            if (!Util.matchesClassExpr(exp, params[actualIndex], advisor)) return false;
         }
         
         return true;
      }
   }

   private static class CtParameterMatcher extends ParameterMatcher
   {
      CtClass[] params;
      public CtParameterMatcher(Advisor advisor, ArrayList<ASTParameter> parameters, CtClass[] params)
      {
         super(advisor, parameters, params);
         this.params = params;
      }
      
      boolean doMatch(int astIndex, int actualIndex)
      {
         ASTParameter ast = astParameters.get(astIndex);
         ClassExpression exp = ast.getType();
         if (!matchesClassExpr(exp, params[actualIndex], advisor)) return false;
         return true;
      }
   }
}
