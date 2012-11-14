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
package org.jboss.test.aop.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 */

@SuppressWarnings("unchecked")
public class ReflectionPOJO
{

   final static Package LANG_PACKAGE = Package.getPackage("java.lang");

   public ReflectionPOJO()
   {
      
   }
   
   public ReflectionPOJO(int x)
   {
      try
      {
         //Sanity checks
         System.out.println("*** reflection (from constructor): Sanity checks");
         
         SimplePerVmInterceptor.reset();
         ReflectionAopPOJO pojo = new ReflectionAopPOJO(8);
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor");
         }
         if (pojo.j != 8)
         {
            throw new RuntimeException("POJO.j was not 8");            
         }

         SimplePerVmInterceptor.reset();
         pojo.method(10);
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method call");
         }
         
         SimplePerVmInterceptor.reset();
         pojo.j = 5;
         if (SimplePerVmInterceptor.fieldWriteIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept field write");
         }
         
         SimplePerVmInterceptor.reset();
         int i = pojo.j;
         System.out.println("i=" + i);
         if (SimplePerVmInterceptor.fieldReadIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept field read");
         }
         
         CallerInterceptor.reset();
         pojo = new ReflectionAopPOJO(false);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept construction");
         }
         
         CallerInterceptor.reset();
         pojo.method(false);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(boolean) call");
         }
         
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         pojo = new ReflectionAopPOJO(100L);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept constructor(long) call");
         }
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor(long) call");
         }

         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         pojo.otherMethod(200L);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(long) call");
         }
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method(long) call");
         }
         
         //Check Class.newInstance() interception
         System.out.println("*** reflection (from constructor): Class.newInstance");
         Class clazz = ReflectionAopPOJO.class;
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         pojo = (ReflectionAopPOJO)clazz.newInstance();
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.constructor == null) throw new RuntimeException("Not intercepted");
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor not invoked for reflected default constructor");
         }

         //Check Constructor.newInstance() interception
         System.out.println("*** reflection (from constructor): Constructor.newInstance");
         Constructor constructor = clazz.getConstructor(new Class[]{Integer.TYPE});
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{new Integer(4)});
         System.out.println("reflection call - end");
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor not invoked for reflected constructor(int)");
         }
         if (pojo.j != 4)
         {
            throw new RuntimeException("POJO.j was not 8 following reflection");            
         }
         
         //Check Method.invoke() interception (Not handled by ReflectionAspect, 
         //should work out of the box as wrapper is method itself
         System.out.println("*** reflection (from constructor): Method.invoke");
         Method method = clazz.getMethod("method", new Class[]{Integer.TYPE});
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         method.invoke(pojo, new Object[]{new Integer(55)});
         System.out.println("reflection call - end");
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected method call");
         }
         
         //Check Field.setInt() interception
         System.out.println("*** reflection (from constructor): Field.setInt");
         Field field = clazz.getField("j");
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         field.setInt(pojo, 10);
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.field == null
                 || ((Integer) ReflectionAspectTester.args[0]).intValue() != 10)
         {
            throw new RuntimeException("Not intercepted");
         }
         if (SimplePerVmInterceptor.fieldWriteIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected field write");
         }
               
         
         //Check Field.getInt() interception
         System.out.println("*** reflection (from constructor): Field.getInt");
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         int j = field.getInt(pojo);
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.field == null
                 || j != 10)
         {
            throw new RuntimeException("Not intercepted");
         }
         if (SimplePerVmInterceptor.fieldReadIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected field read");
         }
         
         //These should not be intercepted
         System.out.println("*** reflection (from constructor): Checking not advised constructors, fields and methods");
         SimplePerVmInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{String.class});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{"x"});
         method = clazz.getMethod("method", new Class[0]);
         method.invoke(pojo, new Object[0]);
         field = clazz.getField("k");
         field.setInt(pojo, 5);
         field.getInt(pojo);
         if (SimplePerVmInterceptor.hasIntercepted())
         {
            throw new RuntimeException("SimplePerVmInterceptor intercepted something that should have been left alone");
         }
         
         System.out.println("*** reflection (from constructor): Checking not advised constructor");
         Class notAdvisedClazz = NotAdvisedPOJO.class;
         constructor = notAdvisedClazz.getConstructor(new Class[0]);
         NotAdvisedPOJO naPojo = (NotAdvisedPOJO)constructor.newInstance(new Object[0]);

         System.out.println("*** reflection (from constructor): Checking not advised method");
         method = notAdvisedClazz.getMethod("method", new Class[0]);
         method.invoke(naPojo, new Object[0]);
         
         System.out.println("*** reflection (from constructor): Checking not advised field set");
         field = notAdvisedClazz.getField("i");
         field.setInt(naPojo, 1);
         
         System.out.println("*** reflection (from constructor): Checking not advised field get");
         field.getInt(naPojo);

         System.out.println("*** reflection (from constructor): Checking constructor caller");
         CallerInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{Boolean.TYPE});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{Boolean.TRUE});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept reflected construction");
         }
         
         System.out.println("*** reflection (from constructor): Checking method caller");
         CallerInterceptor.reset();
         method = clazz.getMethod("method", new Class[]{Boolean.TYPE});
         method.invoke(pojo, new Object[]{Boolean.FALSE});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept reflected method(boolean) call");
         }
         
         //These have both caller and execution pointcuts
         System.out.println("*** reflection (from constructor): Checking constructor caller and execution");
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{Long.TYPE});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{new Long(100L)});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept constructor(long) call");
         }
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor(long) call");
         }

         System.out.println("*** reflection (from constructor): Checking method caller and execution");
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         method = clazz.getMethod("otherMethod", new Class[]{Long.TYPE});
         method.invoke(pojo, new Object[]{new Long(100L)});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(long) call");
         }
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method(long) call");
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }


   public void testCreationAndFieldAccess()
   {
      try
      {
         //Sanity checks
         System.out.println("*** reflection (from method): Sanity checks");
         
         SimplePerVmInterceptor.reset();
         ReflectionAopPOJO pojo = new ReflectionAopPOJO(8);
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor");
         }
         if (pojo.j != 8)
         {
            throw new RuntimeException("POJO.j was not 8");            
         }

         SimplePerVmInterceptor.reset();
         pojo.method(10);
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method call");
         }
         
         SimplePerVmInterceptor.reset();
         pojo.j = 5;
         if (SimplePerVmInterceptor.fieldWriteIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept field write");
         }
         
         SimplePerVmInterceptor.reset();
         int i = pojo.j;
         System.out.println("i=" + i);
         if (SimplePerVmInterceptor.fieldReadIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept field read");
         }
         
         CallerInterceptor.reset();
         pojo = new ReflectionAopPOJO(false);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept construction");
         }
         
         CallerInterceptor.reset();
         pojo.method(false);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(boolean) call");
         }
         
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         pojo = new ReflectionAopPOJO(100L);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept constructor(long) call");
         }
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor(long) call");
         }

         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         pojo.otherMethod(200L);
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(long) call");
         }
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method(long) call");
         }
         
         //Check Class.newInstance() interception
         System.out.println("*** reflection (from method): Class.newInstance");
         Class clazz = ReflectionAopPOJO.class;
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         pojo = (ReflectionAopPOJO)clazz.newInstance();
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.constructor == null) throw new RuntimeException("Not intercepted");
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor not invoked for reflected default constructor");
         }

         //Check Constructor.newInstance() interception
         System.out.println("*** reflection (from method): Constructor.newInstance");
         Constructor constructor = clazz.getConstructor(new Class[]{Integer.TYPE});
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{new Integer(4)});
         System.out.println("reflection call - end");
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor not invoked for reflected constructor(int)");
         }
         if (pojo.j != 4)
         {
            throw new RuntimeException("POJO.j was not 8 following reflection");            
         }
         
         //Check Method.invoke() interception (Not handled by ReflectionAspect, 
         //should work out of the box as wrapper is method itself
         System.out.println("*** reflection (from method): Method.invoke");
         Method method = clazz.getMethod("method", new Class[]{Integer.TYPE});
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         method.invoke(pojo, new Object[]{new Integer(55)});
         System.out.println("reflection call - end");
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected method call");
         }
         
         //Check Field.setInt() interception
         System.out.println("*** reflection (from method): Field.setInt");
         Field field = clazz.getField("j");
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         field.setInt(pojo, 10);
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.field == null
                 || ((Integer) ReflectionAspectTester.args[0]).intValue() != 10)
         {
            throw new RuntimeException("Not intercepted");
         }
         if (SimplePerVmInterceptor.fieldWriteIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected field write");
         }
               
         
         //Check Field.getInt() interception
         System.out.println("*** reflection (from method): Field.getInt");
         SimplePerVmInterceptor.reset();
         System.out.println("reflection call");
         int j = field.getInt(pojo);
         System.out.println("reflection call - end");
         if (ReflectionAspectTester.field == null
                 || j != 10)
         {
            throw new RuntimeException("Not intercepted");
         }
         if (SimplePerVmInterceptor.fieldReadIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept reflected field read");
         }
         
         //These should not be intercepted
         SimplePerVmInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{String.class});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{"x"});
         method = clazz.getMethod("method", new Class[0]);
         method.invoke(pojo, new Object[0]);
         field = clazz.getField("k");
         field.setInt(pojo, 5);
         field.getInt(pojo);
         
         if (SimplePerVmInterceptor.hasIntercepted())
         {
            throw new RuntimeException("SimplePerVmInterceptor intercepted something that should have been left alone");
         }
         
         System.out.println("*** reflection (from method): Checking not advised constructor");
         Class notAdvisedClazz = NotAdvisedPOJO.class;
         constructor = notAdvisedClazz.getConstructor(new Class[0]);
         NotAdvisedPOJO naPojo = (NotAdvisedPOJO)constructor.newInstance(new Object[0]);

         System.out.println("*** reflection (from method): Checking not advised method");
         method = notAdvisedClazz.getMethod("method", new Class[0]);
         method.invoke(naPojo, new Object[0]);
         
         System.out.println("*** reflection (from method): Checking not advised field set");
         field = notAdvisedClazz.getField("i");
         field.setInt(naPojo, 1);
         
         System.out.println("*** reflection (from method): Checking not advised field get");
         field.getInt(naPojo);

         System.out.println("*** reflection (from method): Checking constructor caller");
         CallerInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{Boolean.TYPE});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{Boolean.TRUE});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept reflected construction");
         }
         
         System.out.println("*** reflection (from method): Checking method caller");
         CallerInterceptor.reset();
         method = clazz.getMethod("method", new Class[]{Boolean.TYPE});
         method.invoke(pojo, new Object[]{Boolean.FALSE});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept reflected method(boolean) call");
         }
         
         //These have both caller and execution pointcuts
         System.out.println("*** reflection (from constructor): Checking constructor caller and execution");
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         constructor = clazz.getConstructor(new Class[]{Long.TYPE});
         pojo = (ReflectionAopPOJO)constructor.newInstance(new Object[]{new Long(100L)});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept constructor(long) call");
         }
         if (SimplePerVmInterceptor.constructorIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept constructor(long) call");
         }

         System.out.println("*** reflection (from constructor): Checking method caller and execution");
         CallerInterceptor.reset();
         SimplePerVmInterceptor.reset();
         method = clazz.getMethod("otherMethod", new Class[]{Long.TYPE});
         method.invoke(pojo, new Object[]{new Long(100L)});
         if (CallerInterceptor.intercepted != 1)
         {
            throw new RuntimeException("CallerInterceptor did not intercept method(long) call");
         }
         if (SimplePerVmInterceptor.methodIntercepted != 1)
         {
            throw new RuntimeException("SimplePerVmInterceptor did not intercept method(long) call");
         }
         
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   public void testCleanGetMethods()
   {
      System.out.println("*** reflection (from method): Class.getMethods()");
      Class pojoClass = ReflectionAopPOJO.class;
      Class pojoRoot = ReflectionAopRootPOJO.class;
      try
      {
         //helloWorld() is introduced
         Method[] check = new Method[]{
            pojoClass.getMethod("method", new Class[0]),
            pojoClass.getMethod("method", new Class[]{Integer.TYPE}),
            pojoClass.getMethod("method", new Class[]{Boolean.TYPE}),
            pojoClass.getMethod("method", new Class[]{Integer.TYPE, Long.TYPE}),
            pojoRoot.getMethod("method", new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE}),
            pojoClass.getMethod("otherMethod", new Class[]{Long.TYPE}),
            pojoClass.getMethod("helloWorld", new Class[]{String.class})};

         Method[] methods = pojoClass.getMethods();

         ArrayList methodList = new ArrayList();

         for (int i = 0; i < methods.length; i++)
         {
            if (!methods[i].getDeclaringClass().getPackage().equals(LANG_PACKAGE))
            {
               methodList.add(methods[i]);
            }
         }


         for (int i = 0; i < check.length; i++)
         {
            if (!methodList.remove(check[i]))
            {
               throw new RuntimeException("\"Cleaned\" Class.getMethods() did not return all expected methods: " + check[i]);
            }
         }

         if (methodList.size() > 0)
         {
            StringBuffer sb = new StringBuffer("The following methods should not "
                    + "have ben returned by \"Cleaned\" Class.getMethods():\n");

            for (Iterator it = methodList.iterator(); it.hasNext();)
            {
               sb.append(it.next());
            }

            throw new RuntimeException(sb.toString());
         }
         
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException(e);
      }
   }

   public void testCleanGetDeclaredMethods()
   {

      System.out.println("*** reflection (from method): Class.getDeclaredMethods()");
      Class clazz = ReflectionAopPOJO.class;
      try
      {
         //helloWorld() is introduced
         Method[] check = new Method[]{
            clazz.getDeclaredMethod("method", new Class[0]),
            clazz.getDeclaredMethod("method", new Class[]{Integer.TYPE}),
            clazz.getDeclaredMethod("method", new Class[]{Boolean.TYPE}),
            clazz.getMethod("method", new Class[]{Integer.TYPE, Long.TYPE}),
            clazz.getMethod("otherMethod", new Class[]{Long.TYPE}),
            clazz.getDeclaredMethod("privateMethod", new Class[0]),
            clazz.getMethod("helloWorld", new Class[]{String.class})};

         Method[] methods = clazz.getDeclaredMethods();

         ArrayList methodList = new ArrayList();

         for (int i = 0; i < methods.length; i++)
         {
            methodList.add(methods[i]);
         }


         for (int i = 0; i < check.length; i++)
         {
            if (!methodList.remove(check[i]))
            {
               throw new RuntimeException("\"Cleaned\" Class.getDeclaredMethods() did not return all expected methods: " + check[i]);
            }
         }

         if (methodList.size() > 0)
         {
            StringBuffer sb = new StringBuffer("The following methods should not "
                    + "have ben returned by \"Cleaned\" Class.getDeclaredMethods():\n");

            for (Iterator it = methodList.iterator(); it.hasNext();)
            {
               sb.append(it.next());
            }

            throw new RuntimeException(sb.toString());
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException(e);
      }
   }

   public void testCleanGetDeclaredFields()
   {

      System.out.println("*** reflection (from method): Class.getDeclaredFields()");
      Class clazz = ReflectionAopPOJO.class;
      try
      {
         Field[] check = new Field[]{
            clazz.getDeclaredField("i"),
            clazz.getDeclaredField("j"),
            clazz.getDeclaredField("k")};

         Field[] fields = clazz.getDeclaredFields();

         ArrayList fieldList = new ArrayList();

         for (int i = 0; i < fields.length; i++)
         {
            fieldList.add(fields[i]);
         }


         for (int i = 0; i < check.length; i++)
         {
            if (!fieldList.remove(check[i]))
            {
               throw new RuntimeException("\"Cleaned\" Class.getDeclaredFields() did not return all expected fields: " + check[i]);
            }
         }

         if (fieldList.size() > 0)
         {
            StringBuffer sb = new StringBuffer("The following methods should not "
                    + "have ben returned by \"Cleaned\" Class.getDeclaredFields():\n");

            for (Iterator it = fieldList.iterator(); it.hasNext();)
            {
               sb.append(it.next());
            }

            throw new RuntimeException(sb.toString());
         }
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException(e);
      }
   }


   public void testCleanGetInterfaces()
   {

      System.out.println("*** reflection (from method): Class.getInterfaces()");
      ReflectionAopPOJO pojo = new ReflectionAopPOJO();
      System.out.println("pojo=" + pojo);
      Class clazz = ReflectionAopPOJO.class;
      Class[] interfaces = clazz.getInterfaces();

      if (interfaces.length != 1)
      {
         throw new RuntimeException("Expected 1 interface, got " + interfaces.length);
      }

      if (!interfaces[0].getName().equals("org.jboss.test.aop.reflection.Introduction"))
      {
         throw new RuntimeException("Did not get expected interface org.jboss.test.aop.reflection.Introduction");
      }
   }

   public void testCleanGetDeclaredMethod()
   {

      System.out.println("*** reflection (from method): Class.getDeclaredMethod()");
      Class pojoClass = ReflectionAopPOJO.class;
      Class pojoRoot = ReflectionAopRootPOJO.class;

      try
      {
         Method m = pojoClass.getDeclaredMethod("method", new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE});
         throw new RuntimeException("Should not be here: " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine, it is declared in ReflectionAopRootPOJO
      }

      try
      {
         Method m = pojoRoot.getDeclaredMethod("method", new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopRootPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE)
                 || !m.getParameterTypes()[1].equals(Long.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = pojoClass.getDeclaredMethod("method", new Class[]{Integer.TYPE, Long.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE)
                 || !m.getParameterTypes()[1].equals(Long.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = pojoClass.getDeclaredMethod("method", new Class[]{Integer.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = pojoClass.getDeclaredMethod("method", new Class[0]);

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO"))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = pojoClass.getDeclaredMethod("privateMethod", new Class[0]);

         if (!m.getName().equals("privateMethod")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO"))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = pojoClass.getMethod("notThere", new Class[0]);
         throw new RuntimeException("Method should not be there " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine
      }

      try
      {
         Method m = pojoClass.getMethod("_getAdvisor", new Class[0]);
         throw new RuntimeException("Method should have been cleaned " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine, this is the cleaning bit
      }

      try
      {
         Method m = pojoRoot.getMethod("_getAdvisor", new Class[0]);
         throw new RuntimeException("Method should have been cleaned " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine, this is the cleaning bit
      }

   }

   public void testCleanGetMethod()
   {

      System.out.println("*** reflection (from method): Class.getMethod()");
      Class clazz = ReflectionAopPOJO.class;

      try
      {
         Method m = clazz.getMethod("method", new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopRootPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE)
                 || !m.getParameterTypes()[1].equals(Long.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = clazz.getMethod("method", new Class[]{Integer.TYPE, Long.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE)
                 || !m.getParameterTypes()[1].equals(Long.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = clazz.getMethod("method", new Class[]{Integer.TYPE});

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !m.getParameterTypes()[0].equals(Integer.TYPE))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = clazz.getMethod("method", new Class[0]);

         if (!m.getName().equals("method")
                 || !m.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO"))
         {
            throw new RuntimeException("Wrong method got");
         }
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Method m = clazz.getMethod("notThere", new Class[0]);
         throw new RuntimeException("Method should not be there " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine
      }

      try
      {
         Method m = clazz.getMethod("_getAdvisor", new Class[0]);
         throw new RuntimeException("Method should have been cleaned " + m);
      }
      catch (NoSuchMethodException e)
      {
         //Fine, this is the cleaning bit
      }

   }

   public void testCleanGetDeclaredField()
   {

      System.out.println("*** reflection (from method): Class.getDeclaredField()");
      Class clazz = ReflectionAopPOJO.class;
      try
      {
         Field f = clazz.getDeclaredField("i");

         if (!f.getName().equals("i")
                 || !f.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !f.getType().equals(Integer.TYPE))
         {
            throw new RuntimeException("Wrong field got");
         }
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException("Expected method not there");
      }

      try
      {
         Field f = clazz.getDeclaredField("j");

         if (!f.getName().equals("j")
                 || !f.getDeclaringClass().getName().equals("org.jboss.test.aop.reflection.ReflectionAopPOJO")
                 || !f.getType().equals(Integer.TYPE))
         {
            throw new RuntimeException("Wrong field got");
         }
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException("Expected method not there");
      }
   }

   public void testCleanGetClasses()
   {
      //No extra public classes are added by AOP, but include this test in case stuff changes in future
      System.out.println("*** reflection (from method): Class.getClasses()");
      HashSet classSet = new HashSet();
      classSet.add("org.jboss.test.aop.reflection.ReflectionAopPOJO$AopPOJOInner");
      classSet.add("org.jboss.test.aop.reflection.ReflectionAopRootPOJO$AopRootPOJOInner");
      Class clazz = ReflectionAopPOJO.class;

      Class[] classes = clazz.getClasses();

      for (int i = 0; i < classes.length; i++)
      {
         String name = classes[i].getName();
         if (!classSet.contains(name))
         {
            throw new RuntimeException(name + " was in the list of classes and should have been removed");
         }
      }

      if (classes.length != classSet.size())
      {
         throw new RuntimeException("Not all the declared classes were returned");
      }
   }

   public void testCleanGetDeclaredClasses()
   {
      System.out.println("*** reflection (from method): Class.getDeclaredClasses()");
      HashSet declaredClasses = new HashSet();
      declaredClasses.add("org.jboss.test.aop.reflection.ReflectionAopPOJO$AopPOJOInner");

      Class clazz = ReflectionAopPOJO.class;
      Class[] classes = clazz.getDeclaredClasses();

      for (int i = 0; i < classes.length; i++)
      {
         String name = classes[i].getName();
         if (!declaredClasses.contains(name))
         {
            throw new RuntimeException(name + " was in the list of declared classes and should have been removed");
         }
      }

      if (classes.length != declaredClasses.size())
      {
         throw new RuntimeException("Not all the declared classes were returned");
      }
   }
}
