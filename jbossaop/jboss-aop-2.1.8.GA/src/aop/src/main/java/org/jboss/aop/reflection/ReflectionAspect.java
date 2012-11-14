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
package org.jboss.aop.reflection;

import gnu.trove.TLongObjectHashMap;
import org.jboss.aop.Advised;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.ConByConInfo;
import org.jboss.aop.ConByMethodInfo;
import org.jboss.aop.ConstructorInfo;
import org.jboss.aop.FieldInfo;
import org.jboss.aop.MethodByConInfo;
import org.jboss.aop.MethodByMethodInfo;
import org.jboss.aop.instrument.Untransformable;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.util.MethodHashing;
import org.jboss.aop.util.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 *         <p/>
 *         Aspect to intercept calls to the reflection API. Calls to
 *         Class.newInstance(), Field.setXXX(), Field.getXXX(), Constructor.newInstance()
 *         don't get intercepted even though the underlying constructor/field
 *         might have an advice associated with it. This class adds advices to help intercept these
 *         calls and attach to the original chains on the caller or target object. Method.invoke()
 *         works for chains on the target object "out of the box", but bypasses caller chains.
 *         <p/>
 *         <BR>
 *         <BR>
 *         Also, Class.getMethods(), Class.getDeclaredMethods(), Class.getInterfaces(),
 *         and Class.getDeclaredFields() return extra methods/interfaces/fields added
 *         by the AOP framework. The aspects contained here cleans this information.
 */
public class ReflectionAspect
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------
   
   private static Pattern fieldGetPattern =
   Pattern.compile("get(|Boolean|Byte|Char|Double|Float|Int|Long|Short)?");

   private static Pattern fieldSetPattern =
   Pattern.compile("set(|Boolean|Byte|Char|Double|Float|Int|Long|Short)?");

   // Constructors --------------------------------------------------
   
   public ReflectionAspect()
   {
   }
   
   // Public --------------------------------------------------------

   /**
    * Advice for calls to Class.newInstance() and Constructor.newInstance(). Intended use is for
    * caller pointcuts. If you wish to handle the intercepted calls, override interceptConstructor.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptConstructor(Invocation, Constructor, Object[])
    */
   public Object interceptNewInstance(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptNewInstance(invocation, reflectionMethod, targetObject, args);
   }

   /**
    * Advice for calls to Class.newInstance() and Constructor.newInstance(). Intended use is for
    * caller pointcuts. If you wish to handle the intercepted calls, override interceptConstructor.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptConstructor(Invocation, Constructor, Object[])
    */
   public Object interceptNewInstance(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptNewInstance(invocation, reflectionMethod, targetObject, args);
   }


   /**
    * Advice for calls to Method.invoke(). Intended use is for
    * caller pointcuts. If you wish to handle the intercepted calls, override interceptMethod.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptMethod(Invocation, Object, Method, Object[])
    */
   public Object interceptMethodInvoke(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptMethodInvoke(invocation, reflectionMethod, targetObject, args);
   }

   /**
    * Advice for calls to Class.newInstance() and Constructor.newInstance(). Intended use is for
    * caller pointcuts. If you wish to handle the intercepted calls, override interceptMethod.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptMethod(Invocation, Object, Method, Object[])
    */
   public Object interceptMethodInvoke(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptMethodInvoke(invocation, reflectionMethod, targetObject, args);
   }


   /**
    * Advice for calls to Field.setXXX(). Intended use is for caller pointcuts. If you wish to handle
    * the intercepted calls, override interceptFieldWrite.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptFieldWrite(Invocation, Field, Object, Object)
    */
   public Object interceptFieldSet(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptFieldSet(invocation, reflectionMethod, targetObject, args);
   }

   /**
    * Advice for calls to Field.setXXX(). Intended use is for caller pointcuts. If you wish to handle
    * the intercepted calls, override interceptFieldWrite.
    *
    * @param invocation
    * @return result of invocation
    * @throws Throwable
    * @see ReflectionAspect#interceptFieldWrite(Invocation, Field, Object, Object)
    */
   public Object interceptFieldSet(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptFieldSet(invocation, reflectionMethod, targetObject, args);
   }

   /**
    * Advice for calls to Field.getXXX(). Intended use is for caller pointcuts. If you wish to handle
    * the intercepted calls, override interceptFieldRead.
    *
    * @param invocation
    * @return The value of the field (or whatever you choose)
    * @throws Throwable
    * @see ReflectionAspect#interceptFieldRead(Invocation, Field, Object)
    */
   public Object interceptFieldGet(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptFieldGet(invocation, reflectionMethod, targetObject, args);
   }


   /**
    * Advice for calls to Field.getXXX(). Intended use is for caller pointcuts. If you wish to handle
    * the intercepted calls, override interceptFieldRead.
    *
    * @param invocation
    * @return The value of the field (or whatever you choose)
    * @throws Throwable
    * @see ReflectionAspect#interceptFieldRead(Invocation, Field, Object)
    */
   public Object interceptFieldGet(MethodCalledByMethodInvocation invocation) throws Throwable
   {


      Method reflectionMethod = invocation.getCalledMethod();
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();

      return interceptFieldGet(invocation, reflectionMethod, targetObject, args);
   }


   /**
    * Advice for calls to Class.getDeclaredMethods(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method[] containing the declared methods of the class
    * @throws Throwable
    * @see Class#getDeclaredMethods()()
    */
   public final Object interceptGetDeclaredMethods(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredMethods((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredMethods(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method[] containing the declared methods of the class
    * @throws Throwable
    * @see Class#getDeclaredMethods()()
    */
   public final Object interceptGetDeclaredMethods(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredMethods((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredMethod(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method the declared method
    * @throws Throwable
    * @see Class#getDeclaredMethod()()
    */
   public final Object interceptGetDeclaredMethod(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetDeclaredMethod((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getDeclaredMethod(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method the declared method
    * @throws Throwable
    * @see Class#getDeclaredMethod()()
    */
   public final Object interceptGetDeclaredMethod(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetDeclaredMethod((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getDeclaredMethod(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method[] The methods of the class
    * @throws Throwable
    * @see Class#getDeclaredMethod()()
    */
   public final Object interceptGetMethods(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetMethods((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getMethods(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method[] The methods of the class
    * @throws Throwable
    * @see Class#getMethods()()
    */
   public final Object interceptGetMethods(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetMethods((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getMethod(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method The method
    * @throws Throwable
    * @see Class#getDeclaredMethod()()
    */
   public final Object interceptGetMethod(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetMethod((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getMethod(). Cleans methods that get added to the
    * class by the AOP framework (Methods introduced by introductions/mixin classes will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Method The method
    * @throws Throwable
    * @see Class#getDeclaredMethod()()
    */
   public final Object interceptGetMethod(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetMethod((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getInterfaces(). Cleans interfaces that get added to the
    * class by the AOP framework. (Interfaces introduced by introductions will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the interfaces of the class
    * @throws Throwable
    * @see Class#getInterfaces()
    */
   public final Object interceptGetInterfaces(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetInterfaces((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getInterfaces(). Cleans interfaces that get added to the
    * class by the AOP framework. (Interfaces introduced by introductions will
    * still be returned). Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the interfaces of the class
    * @throws Throwable
    * @see Class#getInterfaces()
    */
   public final Object interceptGetInterfaces(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetInterfaces((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredClasses(). Cleans inner classes that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the Class objects representing inner classes of the class
    * @throws Throwable
    * @see Class#getDeclaredClasses()
    */
   public final Object interceptGetDeclaredClasses(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredClasses((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredClasses(). Cleans inner classes that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the Class objects representing inner classes of the class
    * @throws Throwable
    * @see Class#getDeclaredClasses()
    */
   public final Object interceptGetDeclaredClasses(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredClasses((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getClasses(). Cleans inner classes that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    * Extra stuff only seems to get returned when using JRockit 
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the Class objects representing inner classes of the class
    * @throws Throwable
    * @see Class#getClasses()
    */
   public final Object interceptGetClasses(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetClasses((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getClasses(). Cleans inner classes that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    * Extra stuff only seems to get returned when using JRockit 
    *
    * @param invocation The invocation
    * @return java.lang.Class[] containing the Class objects representing inner classes of the class
    * @throws Throwable
    * @see Class#getDeclaredClasses()
    */
   public final Object interceptGetClasses(MethodCalledByMethodInvocation invocation) throws Throwable
   {
      Object targetObject = invocation.getTargetObject();
      return interceptGetClasses((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredFields(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field[] containing the Fields of the class
    * @throws Throwable
    * @see Class#getDeclaredClasses()
    */
   public final Object interceptGetDeclaredFields(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredFields((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredFields(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field[] containing the Fields of the class
    * @throws Throwable
    * @see Class#getDeclaredFields()
    */
   public final Object interceptGetDeclaredFields(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      return interceptGetDeclaredFields((Class<?>) targetObject);
   }

   /**
    * Advice for calls to Class.getDeclaredField(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field The Field
    * @throws Throwable
    * @see Class#getDeclaredField()
    */
   public final Object interceptGetDeclaredField(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetDeclaredField((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getDeclaredField(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field[] The Field
    * @throws Throwable
    * @see Class#getDeclaredField()
    */
   public final Object interceptGetDeclaredField(MethodCalledByMethodInvocation invocation) throws Throwable
   {
      Object targetObject = invocation.getTargetObject();
      Object[] args = invocation.getArguments();
      return interceptGetDeclaredField((Class<?>) targetObject, args);
   }

   /**
    * Advice for calls to Class.getFields(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field[] containing the Fields of the class
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetFields(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getFields(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Field[] containing the Fields of the class
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetFields(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getDeclaredConstructors(). Cleans constructors that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Constructor[] containing the Constructors of the class
    * @throws Throwable
    * @see Class#getDeclaredConstructors()
    */
   public final Object interceptGetDeclaredConstructors(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getDeclaredConstructors(). Cleans constructors that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Constructor[] containing the Constructors of the class
    * @throws Throwable
    * @see Class#getDeclaredConstructors()
    */
   public final Object interceptGetDeclaredConstructors(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getDeclaredConstructor(). Cleans constructors that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Constructor[] The constructor
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetDeclaredConstructor(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getDeclaredConstructor(). Cleans constructors that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.reflect.Constructor[] The constructor
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetDeclaredConstructor(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getConstructors(). Cleans fields that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Constructor[] containing the Constructors of the class
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetConstructors(MethodCalledByConstructorInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }

   /**
    * Advice for calls to Class.getConstructors(). Cleans constructors that get added to the
    * class by the AOP framework. Intended use is for caller pointcuts.
    *
    * @param invocation The invocation
    * @return java.lang.Constructor[] containing the Constructors of the class
    * @throws Throwable
    * @see Class#getFields()
    */
   public final Object interceptGetConstructors(MethodCalledByMethodInvocation invocation) throws Throwable
   {

      //Nothing seems to get added
      return invocation.invokeNext();
   }
   

   // Z implementation ----------------------------------------------
   
   // Y overrides ---------------------------------------------------
   
   // Package protected ---------------------------------------------
   
   // Protected -----------------------------------------------------

   
   /**
    * Overridable advice for a Constructor.newInstance() or Class.newInstance() call.
    * Default behaviour is to first try to attach to any caller advice chain. If that
    * does not exist, try to attach to the advice chains on the target object. And
    * if that does not exist to invoke the reflected constructor.
    *
    * @param invocation  The invocation
    * @param constructor The field on which we are calling set
    * @param args        The arguments for the constructor
    * @return The new instance
    * @throws Throwable
    */
   protected Object interceptConstructor(Invocation invocation, Constructor<?> constructor, Object[] args) throws Throwable
   {
      return invokeOriginalChainIfExists(invocation, constructor, args);
   }

   /**
    * Overridable advice for a Field.setXXX() call.
    * Default behaviour is to first try to attach to the advice chains on the target object. And
    * if that does not exist to invoke the reflected field read.
    *
    * @param invocation The invocation
    * @param field      The field on which we are calling set
    * @param instance   The instance on which we want to write a field
    * @return The value of the field (or whatever you choose)
    * @throws Throwable
    */
   protected Object interceptFieldRead(Invocation invocation, Field field, Object instance) throws Throwable
   {
      return invokeOriginalChainIfExists(invocation, field, instance);
   }

   /**
    * Overridable advice for a Field.setXXX() call.
    * Default behaviour is to first try attach to the advice chains on the target object. And
    * if that does not exist to invoke the reflected field write.
    *
    * @param invocation The invocation
    * @param field      The field on which we are calling set
    * @param instance   The instance on which we want to read a field
    * @param arg        The value we want to set the field to
    * @return result of invocation
    * @throws Throwable
    */
   protected Object interceptFieldWrite(Invocation invocation, Field field, Object instance, Object arg) throws Throwable
   {
      return invokeOriginalChainIfExists(invocation, field, instance, arg);
   }

   /**
    * Overridable advice for a Method.invoke() call.
    * Default behaviour is to first try to attach to any caller advice chain. If that
    * does not exist, try to attach to the advice chains on the target object. And
    * if that does not exist to invoke the reflected method.
    *
    * @param invocation The invocation
    * @param method     The method on which we are calling set
    * @param instance   The instance on which we want to read a field
    * @param arg        The value we want to set the field to
    * @return result of invocation
    * @throws Throwable
    */
   protected Object interceptMethod(Invocation invocation, Method method, Object instance, Object[] args) throws Throwable
   {
      return invokeOriginalChainIfExists(invocation, method, instance, args);
   }
   
   
   // Private -------------------------------------------------------
   
   private Object interceptNewInstance(Invocation invocation,
                                       Method reflectionMethod,
                                       Object targetObject,
                                       Object[] args) throws Throwable
   {
      Class<?> reflectionClass = targetObject.getClass();

      if (reflectionClass.equals(Class.class))
      {

         //For our purposes Class.newInstance() can be made into a call to the empty constructor
         Constructor<?> constructor = ((Class<?>) targetObject).getConstructor(new Class[0]);
         return interceptConstructor(invocation, constructor, args);
      }
      else if (reflectionClass.equals(Constructor.class))
      {

         if (reflectionMethod.getName().equals("newInstance"))
         {
            //Object newObject = args[0];

            Object[] constructorArgs;

            int length = args.length;

            if (length < 1)
            {
               constructorArgs = new Object[0];
            }
            else
            {
               constructorArgs = (Object[]) args[0];
            }

            Constructor<?> constructor = (Constructor<?>) targetObject;
            return interceptConstructor(invocation, constructor, constructorArgs);
         }
      }

      return invocation.invokeNext();
   }

   private Object interceptMethodInvoke(Invocation invocation,
                                        Method reflectionMethod,
                                        Object targetObject,
                                        Object[] args) throws Throwable
   {
      Method method = (Method) invocation.getTargetObject();
      if (reflectionMethod.getName().equals("invoke"))
      {
         Object instance = args[0];
         return interceptMethod(invocation, method, instance, (Object[]) args[1]);
      }
      return invocation.invokeNext();
   }

   private Class<?>[] interceptGetInterfaces(Class<?> clazz)
   {
      Class<?>[] interfaces = clazz.getInterfaces();
      ArrayList<Class<?>> cleanedInterfaces = new ArrayList<Class<?>>(interfaces.length);

      for (int i = 0; i < interfaces.length; i++)
      {
         if (!interfaces[i].equals(Advised.class))
         {
            cleanedInterfaces.add(interfaces[i]);
         }
      }

      return cleanedInterfaces.toArray(new Class[cleanedInterfaces.size()]);
   }

   private Object interceptFieldSet(Invocation invocation, Method reflectionMethod, Object targetObject, Object[] args) throws Throwable
   {
      Field field = (Field) invocation.getTargetObject();
      Matcher m = fieldSetPattern.matcher(reflectionMethod.getName());
      if (m.matches())
      {
         Object instance = args[0];
         return interceptFieldWrite(invocation, field, instance, args[1]);
      }
      return invocation.invokeNext();
   }

   private Object interceptFieldGet(Invocation invocation, Method reflectionMethod, Object targetObject, Object[] args) throws Throwable
   {
      Field field = (Field) invocation.getTargetObject();
      Matcher m = fieldGetPattern.matcher(reflectionMethod.getName());
      if (m.matches())
      {
         Object instance = args[0];
         return interceptFieldRead(invocation, field, instance);
      }
      return invocation.invokeNext();
   }

   private Method[] interceptGetDeclaredMethods(Class<?> clazz)
   {
      ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);

      if (advisor == null)
      {
         return getDeclaredMethods(clazz);
      }
      else
      {
         Object[] advisedMethods = advisor.getAdvisedMethods().getValues();

         ArrayList<Method> methods = new ArrayList<Method>(advisedMethods.length);

         for (int i = 0; i < advisedMethods.length; i++)
         {
            Method m = (Method) advisedMethods[i];
            if (clazz.equals(m.getDeclaringClass()) && ReflectUtils.isNotAccessMethod(m) && isNotJavassistWrappedMethod(m))
            {
               methods.add(m);
            }
         }

         return methods.toArray(new Method[methods.size()]);
      }
   }

   private Method interceptGetDeclaredMethod(Class<?> clazz, Object[] args) throws NoSuchMethodException
   {

      ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);
      Method method = getDeclaredMethod(clazz, (String) args[0], (Class[]) args[1]);

      if (advisor == null)
      {
         return method;
      }
      else
      {
         Object[] advisedMethods = advisor.getAdvisedMethods().getValues();

         for (int i = 0; i < advisedMethods.length; i++)
         {
            Method m = (Method) advisedMethods[i];
            if (m.equals(method) && ReflectUtils.isNotAccessMethod(m) && isNotJavassistWrappedMethod(m))
            {
               return method;
            }
         }
      }

      throw new NoSuchMethodException();
   }

   private boolean isNotJavassistWrappedMethod(Method m)
   {
      if (Modifier.isPrivate(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
      {
         if (m.getName().startsWith("_added_m$"))
         {
            return false;
         }
      }
      
      return true;
   }

   private Method[] interceptGetMethods(Class<?> clazz)
   {

      ArrayList<Method> methods = new ArrayList<Method>();

      //These are the methods that have already been added. Since we are
      //using Class.getDeclaredMethods(), we don't want to add super
      //implementations of overrriden methods
      GetMethodsAlreadyFound methodsFound = new GetMethodsAlreadyFound();

      while (clazz != null)
      {
         ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);
         Object[] foundMethods;

         if (advisor == null)
         {
            foundMethods = getDeclaredMethods(clazz);
         }
         else
         {
            foundMethods = advisor.getAdvisedMethods().getValues();
         }

         for (int i = 0; i < foundMethods.length; i++)
         {
            Method m = (Method) foundMethods[i];
            if (clazz.equals(m.getDeclaringClass()) && Modifier.isPublic(m.getModifiers()))
            {

               if (!methodsFound.existsMethod(m))
               {
                  methods.add(m);
                  methodsFound.addMethod(m);
               }
            }
         }

         clazz = clazz.getSuperclass();
      }

      return methods.toArray(new Method[methods.size()]);
   }

   private Method interceptGetMethod(Class<?> clazz, Object[] args) throws NoSuchMethodException
   {
      Method method = clazz.getMethod((String) args[0], (Class[]) args[1]);
      Class<?> declaringClass = method.getDeclaringClass();

      while (clazz != null)
      {
         //Go up the inheritance hierachy looking for the class that declares this method
         if (clazz.equals(declaringClass))
         {
            ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);

            if (advisor == null)
            {
               return method;
            }
            else
            {
               Object[] methods = advisor.getAdvisedMethods().getValues();

               for (int i = 0; i < methods.length; i++)
               {
                  Method m = (Method) methods[i];
                  if (m.equals(method))
                  {
                     return method;
                  }
               }
            }
         }

         clazz = clazz.getSuperclass();
      }

      throw new NoSuchMethodException();
   }


   private Field[] interceptGetDeclaredFields(Class<?> clazz)
   {
      ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);

      if (advisor == null)
      {
         return getDeclaredFields(clazz);
      }
      else
      {
         Field[] advisedFields = advisor.getAdvisedFields();

         ArrayList<Field> fields = new ArrayList<Field>(advisedFields.length);

         for (int i = 0; i < advisedFields.length; i++)
         {
            Field f = advisedFields[i];
            if (clazz.equals(f.getDeclaringClass()))
            {
               fields.add(f);
            }
         }

         return fields.toArray(new Field[fields.size()]);
      }

   }

   /** Sun JVMs do not return any extra info from Class.getClasses(),
    * JRockit does, so make it got through the same cleaning procedure 
    * here
    */
   private Class<?>[] interceptGetClasses(Class<?> clazz) throws Throwable
   {
      Class<?>[] classes = clazz.getClasses();
      return cleanClasses(classes);
   }
   
   private Class<?>[] interceptGetDeclaredClasses(Class<?> clazz) throws Throwable
   {
      Class<?>[] classes = getDeclaredClasses(clazz);
      return cleanClasses(classes);
   }
   
   private Class<?>[] cleanClasses(Class<?>[] classes)
   {
      ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();

      for (int i = 0; i < classes.length; i++)
      {
         Class<?> innerClass = classes[i];

         //Check if implements Untransformable
         Class<?>[] interfaces = classes[i].getInterfaces();
         boolean implUntransformable = false;

         for (int j = 0; j < interfaces.length; j++)
         {
            if (interfaces[j].equals(Untransformable.class))
            {
               implUntransformable = true;
               break;
            }
         }

         if (!implUntransformable)
         {
            //Check if class implements Invocation at any stage
            boolean isInvocationImpl = false;
            Class<?> superclass = innerClass.getSuperclass();
            while (superclass != null)
            {

               interfaces = classes[i].getInterfaces();

               for (int j = 0; j < interfaces.length; j++)
               {
                  if (interfaces[j].equals(Invocation.class))
                  {
                     isInvocationImpl = true;
                     break;
                  }
               }
               superclass = superclass.getSuperclass();
            }

            if (!isInvocationImpl)
            {
               clazzes.add(innerClass);
            }
         }
      }

      return clazzes.toArray(new Class<?>[clazzes.size()]);
   }

   private Field interceptGetDeclaredField(Class<?> clazz, Object[] args) throws NoSuchFieldException
   {

      ClassAdvisor advisor = AspectManager.instance().getAdvisorIfAdvised(clazz);
      Field field = getDeclaredField(clazz, (String) args[0]);

      if (advisor == null)
      {
         return field;
      }
      else
      {
         Field[] advisedFields = advisor.getAdvisedFields();

         for (int i = 0; i < advisedFields.length; i++)
         {
            Field f = advisedFields[i];
            if (f.equals(field))
            {
               return field;
            }
         }
      }

      throw new NoSuchFieldException();
   }

   /**
    * First attempt to invoke the original caller interceptor chains in the
    * calling class, then attempt to invoke the original interceptor chain
    * attached to the constructor in the target class.
    * For now we don't bother attempting to use optimized invocations since we
    * are using reflection anyway
    */
   private Object invokeOriginalChainIfExists(Invocation invocation, Constructor<?> constructor, Object[] args) throws Throwable
   {
      //Try to attach to the caller chain
      if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         MethodCalledByConstructorInvocation inv = (MethodCalledByConstructorInvocation) invocation;
         Constructor<?> callingCon = inv.getCalling();
         Class<?> callingClass = callingCon.getDeclaringClass();
         if (isAdvised(callingClass))
         {
            ClassAdvisor advisor = AspectManager.instance().getAdvisor(callingClass);
            if (advisor != null)
            {
               int index = advisor.getConstructorIndex(callingCon);
               if (index >= 0)
               {
                  HashMap<String, TLongObjectHashMap> calledClassesMap = advisor.getConCalledByConInterceptors()[index];
                  if (calledClassesMap != null)
                  {
                     TLongObjectHashMap calledCons = calledClassesMap.get(constructor.getDeclaringClass().getName());
                     if (calledCons != null)
                     {
                        long calledHash = MethodHashing.constructorHash(constructor);
                        ConByConInfo info = (ConByConInfo) calledCons.get(calledHash);

                        if (info != null && info.hasAdvices())
                        {
                           return advisor.invokeConCalledByCon(info, inv.getCallingObject(), args);
                        }
                     }
                  }
               }
            }
         }
      }
      else if (invocation instanceof MethodCalledByMethodInvocation)
      {
         MethodCalledByMethodInvocation inv = (MethodCalledByMethodInvocation) invocation;
         Method callingMethod = inv.getCallingMethod();
         if (isAdvised(callingMethod.getDeclaringClass()))
         {
            ClassAdvisor advisor = AspectManager.instance().getAdvisor(callingMethod.getDeclaringClass());
            if (advisor != null)
            {
               long callingMethodHash = MethodHashing.calculateHash(callingMethod);
               long calledHash = MethodHashing.constructorHash(constructor);

               @SuppressWarnings("unchecked")
               HashMap calledClassesMap = (HashMap) advisor.getConCalledByMethodInterceptors().get(callingMethodHash);
               if (calledClassesMap != null)
               {
                  TLongObjectHashMap calledCons = (TLongObjectHashMap) calledClassesMap.get(constructor.getDeclaringClass().getName());
                  if (calledCons != null)
                  {
                     //CallerConstructorInfo info = (CallerConstructorInfo) calledCons.get(calledHash);
                     ConByMethodInfo info = (ConByMethodInfo) calledCons.get(calledHash);

                     if (info != null && info.hasAdvices())
                     {
                        //return advisor.invokeConCalledByMethod(callingMethodHash, args, info, inv.getCallingObject());
                        return advisor.invokeConCalledByMethod(info, inv.getCallingObject(), args);
                     }
                  }
               }
            }
         }
      }
      
      //Try to attach to chain on target object 
      Class<?> calledClass = constructor.getDeclaringClass();
      if (isAdvised(calledClass))
      {
         ClassAdvisor advisor = AspectManager.instance().getAdvisor(constructor.getDeclaringClass());

         if (advisor != null && advisor.hasAspects())
         {
            int index = advisor.getConstructorIndex(constructor);
            if (index >= 0)
            {
               ConstructorInfo jp = advisor.getConstructorInfos()[index];
               return jp.getWrapper().invoke(null, args);
            }
         }
      }
      return invocation.invokeNext();
   }


   /**
    * Attempt to invoke the original interceptor chain attached to the
    * field read in the target class.
    * For now we don't bother attempting to use optimized invocations since we
    * are using reflection anyway
    */
   private Object invokeOriginalChainIfExists(Invocation invocation, Field field, Object targetObject) throws Throwable
   {
      //Don't attach to caller chain, since we don't have caller interception for fields

      //Try to attach to chain on target object 
      ClassAdvisor advisor = AspectManager.instance().getAdvisor(field.getDeclaringClass());

      Class<?> calledClass = field.getDeclaringClass();
      if (isAdvised(calledClass))
      {
         if (advisor != null && advisor.hasAspects())
         {
            int index = advisor.getFieldIndex(field);
            if (index >= 0)
            {
               FieldInfo jp = advisor.getFieldReadInfos()[index];
               return jp.getWrapper().invoke(null, new Object[] {targetObject});
            }
         }
      }
      return invocation.invokeNext();
   }

   /**
    * Attempt to invoke the original interceptor chain attached to the
    * field write in the target class.
    * For now we don't bother attempting to use optimized invocations since we
    * are using reflection anyway
    */
   private Object invokeOriginalChainIfExists(Invocation invocation, Field field, Object targetObject, Object value) throws Throwable
   {
      //Don't attach to caller chain, since we don't have caller interception for fields

      //Try to attach to chain on target object 
      ClassAdvisor advisor = AspectManager.instance().getAdvisor(field.getDeclaringClass());

      Class<?> calledClass = field.getDeclaringClass();
      if (isAdvised(calledClass))
      {
         if (advisor != null && advisor.hasAspects())
         {
            int index = advisor.getFieldIndex(field);
            if (index >= 0)
            {
               FieldInfo jp = advisor.getFieldWriteInfos()[index];
               return jp.getWrapper().invoke(null, new Object[] {targetObject, value});
            }
         }
      }
      return invocation.invokeNext();
   }

   /**
    * Attempt to invoke the original interceptor chain attached to the
    * method call in the caller class.
    * For now we don't bother attempting to use optimized invocations since we
    * are using reflection anyway
    */
   private Object invokeOriginalChainIfExists(Invocation invocation, Method method, Object targetObject, Object[] args) throws Throwable
   {
      //Try to attach to the caller chain
      if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         MethodCalledByConstructorInvocation inv = (MethodCalledByConstructorInvocation) invocation;
         Constructor<?> callingCon = inv.getCalling();
         Class<?> callingClass = callingCon.getDeclaringClass();
         if (isAdvised(callingClass))
         {
            ClassAdvisor advisor = AspectManager.instance().getAdvisor(callingClass);
            if (advisor != null)
            {
               int index = advisor.getConstructorIndex(callingCon);
               if (index >= 0)
               {
                  HashMap<String, TLongObjectHashMap> calledClassesMap = advisor.getMethodCalledByConInterceptors()[index];
                  if (calledClassesMap != null)
                  {
                     TLongObjectHashMap calledMethods = calledClassesMap.get(method.getDeclaringClass().getName());
                     if (calledMethods != null)
                     {
                        long calledHash = MethodHashing.calculateHash(method);
                        //CallerMethodInfo info = (CallerMethodInfo) calledMethods.get(calledHash);
                        MethodByConInfo info = (MethodByConInfo) calledMethods.get(calledHash);

                        if (info != null && info.hasAdvices())
                        {
                           //return advisor.invokeConstructorCaller(index, targetObject, args, info);
                           return advisor.invokeConstructorCaller(info, inv.getCallingObject(), targetObject, args);
                        }
                     }
                  }
               }
            }
         }
      }
      else if (invocation instanceof MethodCalledByMethodInvocation)
      {
         MethodCalledByMethodInvocation inv = (MethodCalledByMethodInvocation) invocation;
         Method callingMethod = inv.getCallingMethod();
         if (isAdvised(callingMethod.getDeclaringClass()))
         {
            ClassAdvisor advisor = AspectManager.instance().getAdvisor(callingMethod.getDeclaringClass());
            if (advisor != null)
            {
               long callingMethodHash = MethodHashing.calculateHash(callingMethod);
               long calledHash = MethodHashing.calculateHash(method);

               @SuppressWarnings("unchecked")
               HashMap calledClassesMap = (HashMap) advisor.getMethodCalledByMethodInterceptors().get(callingMethodHash);
               if (calledClassesMap != null)
               {
                  TLongObjectHashMap calledMethods = (TLongObjectHashMap) calledClassesMap.get(method.getDeclaringClass().getName());
                  if (calledMethods != null)
                  {
                     //CallerMethodInfo info = (CallerMethodInfo) calledMethods.get(calledHash);
                     MethodByMethodInfo info = (MethodByMethodInfo) calledMethods.get(calledHash);

                     if (info != null  && info.hasAdvices())
                     {
                        //return advisor.invokeCaller(callingMethodHash, targetObject, args, info, inv.getCallingObject());
                        return advisor.invokeCaller(info, inv.getCallingObject(), targetObject, args);
                     }
                  }
               }
            }
         }
      }
      
      //Don't try to attach to chain on target object, since this already is handled for us
      //(Wrapper is method itself)
      return invocation.invokeNext();
   }

   private boolean isAdvised(Class<?> clazz)
   {
      Class<?>[] interfaces = clazz.getInterfaces();

      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].equals(Advised.class))
         {
            return true;
         }
      }
      
      //Is this recursive check needed
      Class<?> superClass = clazz.getSuperclass();
      if (superClass != null)
      {
         return isAdvised(superClass);
      }

      return false;
   }
   // Inner classes -------------------------------------------------   
   
   /* Used by interceptGetMethods()
    */
   class GetMethodsAlreadyFound
   {
      HashMap<String, ArrayList<Method>> methodMap = new HashMap<String, ArrayList<Method>>();

      public void addMethod(Method m)
      {
         String methodName = m.getName();
         ArrayList<Method> methods = methodMap.get(methodName);

         if (methods == null)
         {
            methods = new ArrayList<Method>();
            methodMap.put(methodName, methods);
         }

         methods.add(m);
      }

      public boolean existsMethod(Method method)
      {
         ArrayList<Method> methods = methodMap.get(method.getName());

         if (methods == null)
         {
            return false;
         }

         Class<?>[] methodParamTypes = method.getParameterTypes();

         for (Method found : methods)
         {
            Class<?>[] foundParamTypes = found.getParameterTypes();

            if (methodParamTypes.length == foundParamTypes.length)
            {

               boolean same = true;
               for (int i = 0; i < methodParamTypes.length; i++)
               {
                  if (!methodParamTypes[i].equals(foundParamTypes[i]))
                  {
                     same = false;
                  }
               }

               if (same)
               {
                  return true;
               }
            }
         }

         return false;
      }
   }
   
   private interface SecurityAction
   {
      Method[] getDeclaredMethods(Class<?> clazz);
      Field[] getDeclaredFields(Class<?> clazz);
      Class<?>[] getDeclaredClasses(Class<?> clazz);
      Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException;
      Method getDeclaredMethod(Class<?> clazz, String name, Class<?>[] paramTypes) throws NoSuchMethodException;

      SecurityAction NON_PRIVILEGED = new SecurityAction()
      {
         public Field[] getDeclaredFields(Class<?> clazz)
         {
            return clazz.getDeclaredFields();
         }

         public Method[] getDeclaredMethods(Class<?> clazz)
         {
            return clazz.getDeclaredMethods();
         }

         public Class<?>[] getDeclaredClasses(Class<?> clazz)
         {
            return clazz.getDeclaredClasses();
         }

         public Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException
         {
            return clazz.getDeclaredField(name);
         }

         public Method getDeclaredMethod(Class<?> clazz, String name, Class<?>[] paramTypes) throws NoSuchMethodException
         {
            return clazz.getDeclaredMethod(name, paramTypes);
         }
      };
      

      SecurityAction PRIVILEGED = new SecurityAction()
      {
         public Field[] getDeclaredFields(final Class<?> clazz)
         {
            return AccessController.doPrivileged(new PrivilegedAction<Field[]>(){
               public Field[] run()
               {
                  return clazz.getDeclaredFields();
               }
            });
         }

         public Method[] getDeclaredMethods(final Class<?> clazz)
         {
            return AccessController.doPrivileged(new PrivilegedAction<Method[]>(){
               public Method[] run()
               {
                  return clazz.getDeclaredMethods();
               }
            });
         }

         public Class<?>[] getDeclaredClasses(final Class<?> clazz)
         {
            return AccessController.doPrivileged(new PrivilegedAction<Class<?>[]>(){
               public Class<?>[] run()
               {
                  return clazz.getDeclaredClasses();
               }
            });
         }

         public Field getDeclaredField(final Class<?> clazz, final String name) throws NoSuchFieldException 
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Field>(){
                  public Field run() throws Exception
                  {
                     return clazz.getDeclaredField(name);
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception ex = e.getException();
               if (ex instanceof NoSuchFieldException)
               {
                  throw (NoSuchFieldException)ex;
               }
               throw new RuntimeException(ex);
            }
         }

         public Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?>[] paramTypes) throws NoSuchMethodException 
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){
                  public Method run() throws Exception
                  {
                     return clazz.getDeclaredMethod(name, paramTypes);
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception ex = e.getException();
               if (ex instanceof NoSuchMethodException)
               {
                  throw (NoSuchMethodException)ex;
               }
               throw new RuntimeException(ex);
            }
         }
      };
   }
   
   private static Method[] getDeclaredMethods(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return SecurityAction.NON_PRIVILEGED.getDeclaredMethods(clazz);
      }
      else
      {
         return SecurityAction.PRIVILEGED.getDeclaredMethods(clazz);
      }
   }

   private static Field[] getDeclaredFields(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return SecurityAction.NON_PRIVILEGED.getDeclaredFields(clazz);
      }
      else
      {
         return SecurityAction.PRIVILEGED.getDeclaredFields(clazz);
      }
   }

   private static Class<?>[] getDeclaredClasses(Class<?> clazz)
   {
      if (System.getSecurityManager() == null)
      {
         return SecurityAction.NON_PRIVILEGED.getDeclaredClasses(clazz);
      }
      else
      {
         return SecurityAction.PRIVILEGED.getDeclaredClasses(clazz);
      }
   }

   private static Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException
   {
      if (System.getSecurityManager() == null)
      {
         return SecurityAction.NON_PRIVILEGED.getDeclaredField(clazz, name);
      }
      else
      {
         return SecurityAction.PRIVILEGED.getDeclaredField(clazz, name);
      }
   }

   private static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>[] paramTypes) throws NoSuchMethodException
   {
      if (System.getSecurityManager() == null)
      {
         return SecurityAction.NON_PRIVILEGED.getDeclaredMethod(clazz, name, paramTypes);
      }
      else
      {
         return SecurityAction.PRIVILEGED.getDeclaredMethod(clazz, name, paramTypes);
      }
   }
}
