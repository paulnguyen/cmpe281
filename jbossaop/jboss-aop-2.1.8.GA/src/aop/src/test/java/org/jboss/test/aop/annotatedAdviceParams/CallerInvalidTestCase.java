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

import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of @Caller parameters with invalid advices.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class CallerInvalidTestCase extends AOPTestWithSetup
{
   private TargetCallerInvalidPOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("CallerTestCase");
      suite.addTestSuite(CallerInvalidTestCase.class);
      return suite;
   }
   
   public CallerInvalidTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      CallerAspect.clear();
      this.pojo = new TargetCallerInvalidPOJO();
   }
   
   public void test1()
   {
      boolean thrown = false;
      try
      {
         pojo.method5Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method5Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method5After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method5Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method5Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test2() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method6Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method6Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method6After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method6Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method6Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test3()
   {
      boolean thrown = false;
      try
      {
         pojo.method7Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method7Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method7After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method7Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method7Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test4()
   {
      boolean thrown = false;
      try
      {
         pojo.method8Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method8Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method8After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method8Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method8Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test5() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method9Before4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method9Around3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method9After3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method9Throwing2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method9Finally3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test6() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method10Before4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method10Around3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method10After3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method10Throwing2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method10Finally3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test7()
   {
      boolean thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method11Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method11Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method11After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method11Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method11Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test8()
   {
      boolean thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method12Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method12Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method12After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method12Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method12Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test9()
   {
      boolean thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method13Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method13Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method13After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method13Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method13Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test10() throws POJOException
   {
      boolean thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method14Before4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method14Around3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method14After3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method14Throwing2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method14Finally3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test11() throws POJOException
   {
      boolean thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method15Before4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method15Around3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method15After3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method15Throwing2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         TargetCallerInvalidPOJO.method15Finally3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test12() throws POJOException
   {
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_BEFORE, false);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_AROUND, false);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_AFTER, false);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_THROWING, false);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_FINALLY, false);
   }
   
   public void test13() throws POJOException
   {
      testConstructorCaller(InvalidCallType.METHOD_BEFORE, false);
      testConstructorCaller(InvalidCallType.METHOD_AROUND, false);
      testConstructorCaller(InvalidCallType.METHOD_AFTER, false);
      testConstructorCaller(InvalidCallType.METHOD_THROWING, false);
      testConstructorCaller(InvalidCallType.METHOD_FINALLY, false);
   }
   
   public void test14() throws POJOException
   {
      testConstructorCaller(InvalidCallType.STATIC_METHOD_BEFORE, false);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_AROUND, false);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_AFTER, false);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_THROWING, false);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_FINALLY, false);
   }
   
   public void test15() throws POJOException
   {
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_BEFORE, true);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_AROUND, true);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_AFTER, true);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_THROWING, true);
      testConstructorCaller(InvalidCallType.CONSTRUCTOR_FINALLY, true);
   }
   
   public void test16() throws POJOException
   {
      testConstructorCaller(InvalidCallType.METHOD_BEFORE, true);
      testConstructorCaller(InvalidCallType.METHOD_AROUND, true);
      testConstructorCaller(InvalidCallType.METHOD_AFTER, true);
      testConstructorCaller(InvalidCallType.METHOD_THROWING, true);
      testConstructorCaller(InvalidCallType.METHOD_FINALLY, true);
   }
   
   public void test17() throws POJOException
   {
      testConstructorCaller(InvalidCallType.STATIC_METHOD_BEFORE, true);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_AROUND, true);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_AFTER, true);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_THROWING, true);
      testConstructorCaller(InvalidCallType.STATIC_METHOD_FINALLY, true);
   }
   
   public void test18()
   {
      boolean thrown = false;
      try
      {
         pojo.method1Before2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Before3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Before4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Around2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Around3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Around4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1After2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1After3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Throwing2();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Throwing3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Finally1();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Finally3();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method1Finally4();
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test19() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method3Before2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Before3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Before4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Around2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Around3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Around4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3After2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3After3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Throwing2();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Throwing3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Finally1();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Finally3();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Finally4();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testConstructorCaller(InvalidCallType callType, boolean throwException) throws POJOException
   {
      boolean thrown = false;
      try
      {
         new TargetCallerInvalidPOJO(callType, throwException);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
}