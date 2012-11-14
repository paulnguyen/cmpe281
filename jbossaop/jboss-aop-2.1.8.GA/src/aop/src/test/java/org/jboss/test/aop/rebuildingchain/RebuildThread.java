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
package org.jboss.test.aop.rebuildingchain;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.pointcut.ast.ParseException;

/**
 * A RebuildThread.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class RebuildThread extends Thread
{
   private volatile boolean done = false;
   private String pointcutExpression;
   private String bindingPrefix;
   
   public RebuildThread(String pointcutExpression, String bindingPrefix)
   {
      this.pointcutExpression = pointcutExpression;
      this.bindingPrefix = bindingPrefix;
   }
   
   @Override
   public void run()
   {
      try
      {
         for(int i = 0; i < 30; i++)
         {
            linkNewAdvice(bindingPrefix + i);
            unlinkAdvice(bindingPrefix + i);
            if(isDone())
            {
               return;
            }
         }
      }
      catch(Exception e)
      {
         System.out.println("An exception occurred: " + e);
         e.printStackTrace();
         RebuildingChainTestCase.setTestFailed();
      }
   }

   public void linkNewAdvice() throws ParseException
   {
      linkNewAdvice(bindingPrefix + "Base");
   }
   
   public void unlinkAdvice()
   {
      unlinkAdvice(bindingPrefix + "Base");
   }
    
   private void linkNewAdvice(String name) throws ParseException
   {
      AdviceBinding binding1 = new AdviceBinding(pointcutExpression, null);
      binding1.addInterceptor(SyncInterceptor.class);
      binding1.setName(name);
      AspectManager.instance().addBinding(binding1);
   }
   
   private void unlinkAdvice(String name)
   {
      AspectManager.instance().removeBinding(name);
   }
   

   public void setDone(boolean b)
   {
      done = b;
   }
   
   private boolean isDone()
   {
      return done;
   }
}