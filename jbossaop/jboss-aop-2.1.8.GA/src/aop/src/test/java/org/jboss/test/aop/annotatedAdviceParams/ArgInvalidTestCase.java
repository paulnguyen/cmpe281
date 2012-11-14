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

import org.jboss.aop.advice.InvalidAdviceException;
import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the use of @Arg parameters with invalid advices.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see ArgsInvalidTestCase
 */
@SuppressWarnings({"cast"})
public class ArgInvalidTestCase extends AOPTestWithSetup
{
   private ArgsInvalidPOJO pojo;

   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("ArgTestCase");
      suite.addTestSuite(ArgInvalidTestCase.class);
      return suite;
   }

   public ArgInvalidTestCase(String name)
   {
      super(name);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      this.pojo = new ArgsInvalidPOJO();
      ArgAspect.clear();
      ArgAspectInterfaces.clear();
      ArgAspectInvertedArgs.clear();
      ArgAspectGenerics.clear();
   }

   public void test1()
   {
      boolean thrown = false;
      try
      {
         this.pojo.bunch1Around1(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch1Around2(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch1Around3(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch1After2(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch1After3(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch1After5(5, (double) 1.3, (float) 0, "test1", 1);
      }
      catch (InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test2()
   {
      boolean thrown = false;
      try
      {
         this.pojo.bunch2Before3(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch2After1(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch2After2(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch2After3(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch2After5(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
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
         this.pojo.bunch3Before3(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch3After1(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch3After2(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch3After3(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch3After5(1, (double) 2.0, (float) 3, 4);
      }
      catch(NoMatchingAdviceException e)
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
         this.pojo.bunch4Before3(10, (double) 9.0, (float) 8.0, 7);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test5()
   {
      boolean thrown = false;
      try
      {
         this.pojo.bunch5Before3(10, (double) 9.0, (float) 8.0, 7);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test6()
   {
      boolean thrown = false;
      try
      {
         this.pojo.bunch6After1(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch6After2(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch6After3(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch6After5(51, (double) 5.3, (float) 61, 131);
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
         this.pojo.bunch7After1(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch7After2(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch7After3(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch7After5(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
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
         this.pojo.bunch8After1(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch8After2(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch8After3(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch8After5(51, (double) 5.3, (float) 61, 131);
      }
      catch(NoMatchingAdviceException e)
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
         this.pojo.bunch9Before6(15, (double) 3.5, (float) 16, 131);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch9Around7(15, (double) 3.5, (float) 16, 131);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch9After1(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch9After2(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch9After3(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.bunch9After5(15, (double) 3.5, (float) 16, 131);
      }
      catch(NoMatchingAdviceException e)
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
         this.pojo.method9Before4(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Before5(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Around4(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Around5(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9After4(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9After5(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Throwing4(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Throwing5(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Finally4(new Implementor());
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method9Finally5(new Implementor());
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
         this.pojo.method10Before4(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Before5(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Around4(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Around5(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10After4(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10After5(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Throwing4(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Throwing5(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Finally4(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         this.pojo.method10Finally5(null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void test12()
   {
      boolean thrown = false;
      try
      {
         pojo.field3Before3 = 10;
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
         e.printStackTrace();
      }
      assertTrue(thrown);
   }
   
   public void testInverted1()
   {
      boolean thrown = false;
      try
      {
         pojo.method11Before2("testInverted", null);
      }
      catch (InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method11Around2("testInverted", null);
      }
      catch (InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method11After2("testInverted", null);
      }
      catch (InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method11Throwing2("testInverted", null);
      }
      catch (NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   public void testInverted2() throws POJOException
   {
      boolean thrown = false;
      try
      {
         pojo.method12Before2("testInverted", null);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method12Around2("testInverted", null);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method12After2("testInverted", null);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method12Throwing2("testInverted", null);
      }
      catch(NoMatchingAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
      thrown = false;
      try
      {
         pojo.method12Finally2("testInverted", null);
      }
      catch(InvalidAdviceException e)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
}