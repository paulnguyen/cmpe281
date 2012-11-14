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
package org.jboss.test.aop.annotatedAdviceParams;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.aop.Advised;
import org.jboss.aop.joinpoint.Construction;
import org.jboss.aop.joinpoint.ConstructorCallByConstructor;
import org.jboss.aop.joinpoint.ConstructorCallByMethod;
import org.jboss.aop.joinpoint.ConstructorExecution;
import org.jboss.aop.joinpoint.FieldAccess;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.joinpoint.MethodCallByConstructor;
import org.jboss.aop.joinpoint.MethodCallByMethod;
import org.jboss.aop.joinpoint.MethodExecution;
import org.jboss.aop.util.MethodHashing;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of @JoinPoint parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings(value={"unchecked", "unused"})
public class JoinPointTestCase extends AOPTestWithSetup
{
   private JoinPointPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("JoinPointTestCase");
      suite.addTestSuite(JoinPointTestCase.class);
      return suite;
   }
   
   public JoinPointTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      JoinPointAspect.clear();
      this.pojo = new JoinPointPOJO();
   }
   
   public void tearDown() throws Exception
   {
      JoinPointBean joinPoint = JoinPointAspect.beforeJoinPoint;
      if (joinPoint == null)
      {
         joinPoint = JoinPointAspect.afterJoinPoint;
         if (joinPoint == null)
         {
            joinPoint = JoinPointAspect.throwingJoinPoint;
            if (joinPoint == null)
            {
               joinPoint = JoinPointAspect.finallyJoinPoint;
            }
         }
      }
      if (joinPoint != null)
      {
         assertSame(((Advised) pojo)._getAdvisor(), joinPoint.getAdvisor());
         assertSame(JoinPointPOJO.class, joinPoint.getClazz());
      }
      super.tearDown();
   }
   
   public void testFieldWrite1()
   {
      pojo.number = 0;
      assertEquals("before1", JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertEquals("after1", JoinPointAspect.afterAdvice);
      assertNotNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertTrue(JoinPointAspect.afterJoinPoint instanceof FieldAccess);
      FieldAccess fieldAccess = (FieldAccess) JoinPointAspect.afterJoinPoint;
      assertEquals("number", fieldAccess.getField().getName());
      assertFalse(fieldAccess.isRead());
   }
   
   public void testFieldWrite2()
   {
      pojo.text = "test2";
      assertEquals("before2", JoinPointAspect.beforeAdvice);
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertEquals("finally1", JoinPointAspect.finallyAdvice);
      assertNotNull(JoinPointAspect.finallyJoinPoint);
      
      assertSame(JoinPointAspect.beforeJoinPoint,
            JoinPointAspect.finallyJoinPoint);
      assertTrue(JoinPointAspect.beforeJoinPoint instanceof FieldAccess);
      FieldAccess fieldAccess = (FieldAccess) JoinPointAspect.beforeJoinPoint;
      assertEquals("text", fieldAccess.getField().getName());
      assertFalse(fieldAccess.isRead());
   }
   
   public void testFieldWrite3()
   {
      String text = pojo.text;
      assertNull(JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertEquals("after5", JoinPointAspect.afterAdvice);
      assertNotNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertTrue(JoinPointAspect.afterJoinPoint instanceof FieldAccess);
      FieldAccess fieldAccess = (FieldAccess) JoinPointAspect.afterJoinPoint;
      assertEquals("text", fieldAccess.getField().getName());
      assertTrue(fieldAccess.isRead());
   }
   
   public void testMethodExecution1() throws Exception
   {
      pojo.method1();
      assertEquals("before3", JoinPointAspect.beforeAdvice);
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertEquals("after3", JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertTrue(JoinPointAspect.beforeJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution)JoinPointAspect.beforeJoinPoint;
      assertEquals("method1", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());
   }
   
   public void testMethodExecution2() throws Exception
   {
      pojo.method2(false);
      assertEquals("before4", JoinPointAspect.beforeAdvice);
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertEquals("after4", JoinPointAspect.afterAdvice);
      assertNotNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertSame(JoinPointAspect.beforeJoinPoint, JoinPointAspect.afterJoinPoint);
      assertTrue(JoinPointAspect.beforeJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution) JoinPointAspect.beforeJoinPoint;
      assertEquals("method2", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());
   }
   
   public void testMethodExecutionException1() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.method2(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertEquals("before4", JoinPointAspect.beforeAdvice);
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertEquals("throwing1", JoinPointAspect.throwingAdvice);
      assertNotNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertSame(JoinPointAspect.beforeJoinPoint,
            JoinPointAspect.throwingJoinPoint);
      assertTrue(JoinPointAspect.beforeJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution) JoinPointAspect.beforeJoinPoint;
      assertEquals("method2", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());
   }
   
   public void testMethodExecutionException2() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.method3();
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertEquals("before4", JoinPointAspect.beforeAdvice);
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertEquals("finally2", JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertTrue(JoinPointAspect.beforeJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution) JoinPointAspect.beforeJoinPoint;
      assertEquals("method3", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());
   }
   
   public void testMethodExecutionException3() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.method4();
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertNull(JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertEquals("throwing3", JoinPointAspect.throwingAdvice);
      assertNotNull(JoinPointAspect.throwingJoinPoint);
      assertEquals("finally3", JoinPointAspect.finallyAdvice);
      assertNotNull(JoinPointAspect.finallyJoinPoint);
      
      assertSame(JoinPointAspect.throwingJoinPoint,
            JoinPointAspect.finallyJoinPoint);
      assertTrue(JoinPointAspect.throwingJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution) JoinPointAspect.throwingJoinPoint;
      assertEquals("method4", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());
   }
   
   public void testMethodExecutionException4()
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.method5();
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertNull(JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertEquals("throwing4", JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
   }
   
   public void testMethodExecutionException5() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.method6();
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertNull(JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertEquals("throwing5", JoinPointAspect.throwingAdvice);
      assertNotNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
      
      assertTrue(JoinPointAspect.throwingJoinPoint instanceof MethodExecution);
      MethodExecution joinPoint = (MethodExecution) JoinPointAspect.throwingJoinPoint;
      assertEquals("method6", joinPoint.getMethod().getName());
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getHash());

   }
   
   public void testConstructorCallByConstructor() throws POJOException
   {
      new JoinPointPOJO(11, false);
      
      assertFullInterception(ConstructorCallByConstructor.class, "before6", "after4",
            "finally5", false);
      
      ConstructorCallByConstructor joinPoint = (ConstructorCallByConstructor)
         JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(int.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
   }
   
   public void testConstructorCallByConstructorException()
   {
      boolean exceptionThrown = false;
      try
      {
         new JoinPointPOJO(12, true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(ConstructorCallByConstructor.class, "before6",
            "throwing6", "finally5", exceptionThrown);
      
      ConstructorCallByConstructor joinPoint = (ConstructorCallByConstructor)
      JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(int.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
   }
   
   public void testConstructorCallByMethod() throws Exception
   {
      pojo.callConstructor(false);
      assertFullInterception(ConstructorCallByMethod.class, "before3", "after5",
            "finally6", false);
      
      ConstructorCallByMethod joinPoint = (ConstructorCallByMethod)
      JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callConstructor", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testConstructorCallByMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.callConstructor(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(ConstructorCallByMethod.class, "before3", "throwing7",
            "finally6", true);
      
      ConstructorCallByMethod joinPoint = (ConstructorCallByMethod)
      JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callConstructor", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testConstructorCallByStaticMethod() throws Exception
   {
      JoinPointPOJO.staticCallConstructor(false);
      
      assertFullInterception(ConstructorCallByMethod.class, "before3", "after5",
            "finally6", false);
      
      ConstructorCallByMethod joinPoint = (ConstructorCallByMethod)
      JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallConstructor", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testConstructorCallByStaticMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         JoinPointPOJO.staticCallConstructor(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(ConstructorCallByMethod.class, "before3", "throwing7",
            "finally6", exceptionThrown);
      
      ConstructorCallByMethod joinPoint = (ConstructorCallByMethod)
      JoinPointAspect.beforeJoinPoint;
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallConstructor", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testMethodCallByConstructor() throws Exception
   {
      new JoinPointPOJO(false, false);
      assertFullInterception(MethodCallByConstructor.class, "before7", "after6",
            "finally3", false);
      
      MethodCallByConstructor joinPoint = (MethodCallByConstructor)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
   }
   
   public void testMethodCallByConstructorException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         new JoinPointPOJO(true, true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByConstructor.class, "before7", "throwing8",
            "finally3", exceptionThrown);

      MethodCallByConstructor joinPoint = (MethodCallByConstructor)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
   }
   
   public void testMethodCallByMethod() throws Exception
   {
      pojo.callMethod(false);
      assertFullInterception(MethodCallByMethod.class, "before8", "after6",
            "finally7", false);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testMethodCallByMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.callMethod(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "throwing8",
            "finally7", exceptionThrown);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testMethodCallByStaticMethod() throws Exception
   {
      JoinPointPOJO.staticCallMethod(pojo, false);
      assertFullInterception(MethodCallByMethod.class, "before8", "after6",
            "finally7", false);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
      JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(JoinPointPOJO.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testMethodCallByStaticMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         JoinPointPOJO.staticCallMethod(pojo, true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "throwing8",
            "finally7", exceptionThrown);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(JoinPointPOJO.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testStaticMethodCallByConstructor() throws Exception
   {
      new JoinPointPOJO('a', false);
      
      assertFullInterception(MethodCallByConstructor.class, "before7", "after6",
            "finally3", false);
      
      MethodCallByConstructor joinPoint = (MethodCallByConstructor)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(char.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
   }
   
   public void testStaticMethodCallByConstructorException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         new JoinPointPOJO('b', true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByConstructor.class, "before7", "throwing8",
            "finally3", exceptionThrown);
      
      MethodCallByConstructor joinPoint = (MethodCallByConstructor)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingConstructor().getDeclaringClass());
      Class[] callerParameters = joinPoint.getCallingConstructor().getParameterTypes();
      assertEquals(2, callerParameters.length);
      assertSame(char.class, callerParameters[0]);
      assertSame(boolean.class, callerParameters[1]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
   }
   
   public void testStaticMethodCallByMethod() throws Exception
   {
      pojo.callStaticMethod(false);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "after6",
            "finally7", false);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callStaticMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testStaticMethodCallByMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         pojo.callStaticMethod(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "throwing8",
            "finally7", exceptionThrown);

      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("callStaticMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testStaticMethodCallByStaticMethod() throws Exception
   {
      JoinPointPOJO.staticCallStaticMethod(false);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "after6",
            "finally7", false);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallStaticMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testStaticMethodCallByStaticMethodException() throws Exception
   {
      boolean exceptionThrown = false;
      try
      {
         JoinPointPOJO.staticCallStaticMethod(true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(MethodCallByMethod.class, "before8", "throwing8",
            "finally7", exceptionThrown);
      
      MethodCallByMethod joinPoint = (MethodCallByMethod)
         JoinPointAspect.beforeJoinPoint;
      assertSame("calledStaticMethod", joinPoint.getMethod().getName());
      Class[] parameters = joinPoint.getMethod().getParameterTypes();
      assertEquals(1, parameters.length);
      assertSame(boolean.class, parameters[0]);
      assertSame(JoinPointPOJO.class, joinPoint.getMethod().getDeclaringClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCalledClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingClass());
      assertSame(JoinPointPOJO.class, joinPoint.getCallingMethod().getDeclaringClass());
      assertSame("staticCallStaticMethod", joinPoint.getCallingMethod().getName());
      Class[] callerParameters = joinPoint.getCallingMethod().getParameterTypes();
      assertEquals(1, callerParameters.length);
      assertSame(boolean.class, callerParameters[0]);
      assertEquals(MethodHashing.methodHash(joinPoint.getMethod()),
            joinPoint.getCalledMethodHash());
      assertEquals(MethodHashing.methodHash(joinPoint.getCallingMethod()),
            joinPoint.getCallingMethodHash());
   }
   
   public void testConstructorExecution() throws POJOException
   {
      new JoinPointPOJO((short) 5, false);
      
      assertFullInterception(ConstructorExecution.class, "before2", "after7",
            "finally7", false);
      
      ConstructorExecution joinPoint = (ConstructorExecution)
         JoinPointAspect.beforeJoinPoint;
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(2, parameters.length);
      assertSame(short.class, parameters[0]);
      assertSame(boolean.class, parameters[1]);
   }
   
   public void testConstructorExecutionException()
   {
      boolean exceptionThrown = false;
      try
      {
         new JoinPointPOJO((short) 15, true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertFullInterception(ConstructorExecution.class, "before2", "throwing2",
            "finally7", exceptionThrown);

      ConstructorExecution joinPoint = (ConstructorExecution)
         JoinPointAspect.beforeJoinPoint;
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(2, parameters.length);
      assertSame(short.class, parameters[0]);
      assertSame(boolean.class, parameters[1]);
   }
   
   public void testConstruction() throws POJOException
   {
      new JoinPointPOJO((float) 5.0, false);
      
      assertFullInterception(Construction.class, "before3", "after5", "finally8",
            false);
      
      Construction joinPoint = (Construction) JoinPointAspect.beforeJoinPoint;
      assertSame(JoinPointPOJO.class, joinPoint.getConstructor().getDeclaringClass());
      Class[] parameters = joinPoint.getConstructor().getParameterTypes();
      assertEquals(2, parameters.length);
      assertSame(float.class, parameters[0]);
      assertSame(boolean.class, parameters[1]);
   }
   
   public void testConstructionException()
   {
      boolean exceptionThrown = false;
      try
      {
         new JoinPointPOJO((float) 51.0, true);
      }
      catch (POJOException e)
      {
         exceptionThrown = true;
      }
      assertTrue(exceptionThrown);
      
      assertNull(JoinPointAspect.beforeAdvice);
      assertNull(JoinPointAspect.beforeJoinPoint);
      assertNull(JoinPointAspect.afterAdvice);
      assertNull(JoinPointAspect.afterJoinPoint);
      assertNull(JoinPointAspect.throwingAdvice);
      assertNull(JoinPointAspect.throwingJoinPoint);
      assertNull(JoinPointAspect.finallyAdvice);
      assertNull(JoinPointAspect.finallyJoinPoint);
   }
   
   private void assertFullInterception(Class<? extends JoinPointBean> expectedType,
         String beforeAdvice, String afterAdvice, String finallyAdvice,
         boolean exceptionThrown)
   {
      JoinPointBean afterJoinPoint = null;
      
      assertEquals(beforeAdvice, JoinPointAspect.beforeAdvice);
      if (exceptionThrown)
      {
         assertNull(JoinPointAspect.afterAdvice);
         assertNull(JoinPointAspect.afterJoinPoint);
         assertEquals(afterAdvice, JoinPointAspect.throwingAdvice);
         afterJoinPoint = JoinPointAspect.throwingJoinPoint;
      }
      else
      {
         assertEquals(afterAdvice, JoinPointAspect.afterAdvice);
         afterJoinPoint = JoinPointAspect.afterJoinPoint;
         assertNull(afterAdvice, JoinPointAspect.throwingAdvice);
         assertNull(JoinPointAspect.throwingJoinPoint);
      }
      assertEquals(finallyAdvice, JoinPointAspect.finallyAdvice);
      
      assertNotNull(JoinPointAspect.beforeJoinPoint);
      assertTrue(expectedType.isAssignableFrom(JoinPointAspect.beforeJoinPoint.getClass()));
      assertSame(JoinPointAspect.beforeJoinPoint, afterJoinPoint);
      assertSame(afterJoinPoint, JoinPointAspect.finallyJoinPoint);
   }
}