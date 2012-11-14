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
import org.jboss.aop.advice.InvalidAdviceException;
import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of @JoinPoint parameters with invalid advices.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings("unused")
public class JoinPointInvalidTestCase extends AOPTestWithSetup
{
   private JoinPointInvalidPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("JoinPointTestCase");
      suite.addTestSuite(JoinPointInvalidTestCase.class);
      return suite;
   }
   
   public JoinPointInvalidTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      JoinPointAspect.clear();
      this.pojo = new JoinPointInvalidPOJO();
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
      boolean thrown = false;
      try
      {
         pojo.numberBefore9 = 0;
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testFieldWrite2()
   {
      boolean thrown = false;
      try
      {
         pojo.textAfter2 = "test2";
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      
      thrown = false;
      try
      {
         pojo.textAfter8 = "test2";
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
   }
   
   public void testFieldRead()
   {
      boolean thrown = false;
      try
      {
         String text = pojo.textBefore5;
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testMethodExecution1()
   {
      boolean thrown = false;
      try
      {
         pojo.method1Finally9();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testMethodExecutionException1() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method3Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testMethodExecutionException2() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method5Around();
      }
      catch (InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method5Finally4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testConstructorCallByMethod() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.callConstructorThrowing9(false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.callConstructorThrowing10(false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testConstructorCallByMethodException() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.callConstructorThrowing9(true);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.callConstructorThrowing10(true);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testConstructorCallByStaticMethod() throws POJOException
   {
      boolean thrown = false;
      try
      {
         JoinPointInvalidPOJO.staticCallConstructorThrowing9(false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         JoinPointInvalidPOJO.staticCallConstructorThrowing10(false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testConstructorCallByStaticMethodException() throws POJOException
   {
      boolean thrown = false;
      try
      {
         JoinPointInvalidPOJO.staticCallConstructorThrowing9(true);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         JoinPointInvalidPOJO.staticCallConstructorThrowing10(true);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testMethodCallByConstructor() throws Exception
   {
      boolean thrown = false;
      try
      {
         new JoinPointInvalidPOJO(false, false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         new JoinPointInvalidPOJO(0l, false);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
}