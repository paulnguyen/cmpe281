/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.stress.weavetest;

import org.jboss.aop.Advised;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.ScenarioTest;
import org.jboss.test.aop.stress.SkipWarmup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unused")
public class WeaveManyClassesTestCase extends ScenarioTest
{

   public WeaveManyClassesTestCase(String arg0)
   {
      super(arg0);
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      //The delegate is called once and generates the classes
      GenerateClassesTestDelegate delegate = new GenerateClassesTestDelegate(clazz);
      return delegate;
   }

   public void testVanilla() throws Exception
   {
      getRunner().executeScenario(new VanillaScenario(), this);
   }
   
   public void testWeave() throws Exception
   {
      getRunner().executeScenario(new WeaveScenario(), this);
   }
   
   @SkipWarmup
   private class VanillaOrWeaveScenario extends AbstractScenario
   {
      boolean vanilla;
      Factory[] factories;
      
      protected VanillaOrWeaveScenario(boolean vanilla, Factory[] factories)
      {
         this.vanilla = vanilla;
         this.factories = factories;
      }
      
      public void execute(int thread, int loop) throws Exception
      {
         Class<?> clazz = factories[loop].loadClass();
         if (clazz == null)
         {
            throw new RuntimeException("Class was null");
         }
         if (vanilla)
         {
            if (Advised.class.isAssignableFrom(clazz))
            {
               throw new RuntimeException(clazz.getName() + " should not implement Advised");
            }
         }
         else
         {
            if (!Advised.class.isAssignableFrom(clazz))
            {
               throw new RuntimeException(clazz.getName() + " should implement Advised");
            }
         }
      }
   }
   
   @SkipWarmup
   private class VanillaScenario extends VanillaOrWeaveScenario
   {
      VanillaScenario()
      {
         super(true, ((GenerateClassesTestDelegate)getDelegate()).getVanillaFactories());
      }
   }
   
   @SkipWarmup
   private class WeaveScenario extends VanillaOrWeaveScenario
   {
      WeaveScenario()
      {
         super(false, ((GenerateClassesTestDelegate)getDelegate()).getWovenFactories());
      }
   }
   
   public void testInitAdvisor() throws Exception
   {
      getRunner().executeScenario(new InitAdvisorScenario(), this);
   }

   @SkipWarmup
   private class InitAdvisorScenario extends AbstractScenario
   {
      Factory[] factories = ((GenerateClassesTestDelegate)getDelegate()).getWovenFactories(); 
      public void execute(int thread, int loop) throws Exception
      {
         MethodCaller caller = factories[loop].create();
      }
   }
}
