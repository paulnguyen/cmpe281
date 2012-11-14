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
package org.jboss.aop.standalone;

import org.jboss.aop.AspectManager;
import org.jboss.aop.DynamicAOPStrategy;
import org.jboss.aop.HotSwapStrategy;

import java.lang.instrument.Instrumentation;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 46253 $
 */
public class Agent
{
   private static Instrumentation instrumentation;
   
   public static Instrumentation getInstrumentation()
   {
      return instrumentation;
   }

   public static void premain(String agentArgs, Instrumentation inst)
   {
      instrumentation = inst;
      StandaloneClassPoolFactory factory = new StandaloneClassPoolFactory(); 
      AspectManager.setClassPoolFactory(factory);
      // necessary for configuration
      AspectManager.instance();
      if (agentArgs != null && agentArgs.indexOf("-hotSwap") != -1)
      {
         // setting dynamic aop strategy: hot swap classes through InstrumentationAdapter
         DynamicAOPStrategy strategy = new HotSwapStrategy(new InstrumentationAdapter(inst));
         AspectManager.instance().setDynamicAOPStrategy(strategy);
         AspectManager.setPrune(false); // no prune 
      }
      inst.addTransformer(new AOPTransformer());
   }
}