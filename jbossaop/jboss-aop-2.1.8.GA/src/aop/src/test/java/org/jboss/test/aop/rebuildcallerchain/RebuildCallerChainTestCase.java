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
package org.jboss.test.aop.rebuildcallerchain;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * A RebuildCallerChainTestCase.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RebuildCallerChainTestCase extends AOPTestWithSetup
{

   public RebuildCallerChainTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("RebuildCallerChainTestCase");
      suite.addTestSuite(RebuildCallerChainTestCase.class);
      return suite;
   }
  
   public void testRebuildCallerChainConCalledCon() throws Exception
   {
      try
      {
//         new Caller2();  // concallcon wont work if the class is already loaded by the cl
         
         AdviceBinding bindingCall = new AdviceBinding( 
               "call(org.jboss.test.aop.rebuildcallerchain.Caller1->new(int))", null); 
         bindingCall.addInterceptor(RebuildCallerChainInterceptor.class); 
         AspectManager.instance().addBinding(bindingCall); 

         RebuildCallerChainInterceptor.call = false;
         new Caller2(true);
         assertTrue("caller2 was not rebuilded", RebuildCallerChainInterceptor.call);
      } 
      catch (Exception e) 
      {
         assertFalse("Failed to rebuild chain....", true);
      }
   }
   
   public void testRebuildCallerChainMethodCalledMethod() throws Exception
   {
      try
      {
         new Caller1().execute();
         
         AdviceBinding bindingCall = new AdviceBinding( 
               "call(* org.jboss.test.aop.rebuildcallerchain.*->execute())", null); 
         bindingCall.addInterceptor(RebuildCallerChainInterceptor.class); 
         AspectManager.instance().addBinding(bindingCall); 

         new Caller1().execute();
         assertTrue("caller1 was not rebuilded", RebuildCallerChainInterceptor.call);
         RebuildCallerChainInterceptor.call = false;
      } 
      catch (Exception e) 
      {
         assertFalse("Failed to rebuild chain....", true);
      }
   }

}
