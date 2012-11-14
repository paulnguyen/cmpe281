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
 * Tests parameter annotation @Thrown with invalid advices. 
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 *
 */
public class ThrownInvalidTestCase extends AOPTestWithSetup
{
   private ThrownInvalidPOJO pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ThrownTestCase");
      suite.addTestSuite(ThrownInvalidTestCase.class);
      return suite;
   }

   public ThrownInvalidTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      ThrownAspect.clear();
      this.pojo = new ThrownInvalidPOJO();
   }

   public void test1() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method1Throwing1(11);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }

   public void test3() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method3Throwing3(3);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method3Finally3(3);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test4() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method4Throwing4(4);
      }
      catch(NoMatchingAdviceException e)
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
         pojo.method5Throwing5(5);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test7() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method7Throwing8();
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
}