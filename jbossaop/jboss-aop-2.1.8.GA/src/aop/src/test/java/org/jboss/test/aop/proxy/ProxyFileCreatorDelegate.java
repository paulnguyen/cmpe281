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
package org.jboss.test.aop.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.GenericAspectFactory;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.advice.ScopedInterceptorFactory;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.pointcut.ast.ParseException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class ProxyFileCreatorDelegate extends junit.framework.Assert
{
   public static final int FEW_ARGS = 1;
   public static final int MANY_ARGS = 2;
   public static final int NO_SUCH_FILE = 3;
   public static final int GENERAL_ERROR = 4;

   protected void createAndSerializeProxy(String[] args)
   {
      if (args.length == 0)
      {
         System.exit(FEW_ARGS);
      }
      else if (args.length > 1)
      {
         System.exit(MANY_ARGS);
      }
      
      File file = new File(args[0]);
      if (!file.exists())
      {
         System.exit(NO_SUCH_FILE);
      }
      
      try
      {
         createAndSerializeProxy(file);
      }
      catch (Throwable t)
      {
         PrintStream out = null;
         try
         {
            out = new PrintStream(new FileOutputStream(file));
            t.printStackTrace(out);
         }
         catch(Exception e)
         {
         }
         finally
         {
            try
            {
               out.close();
            }
            catch(Exception e)
            {
            }
         }
         System.exit(GENERAL_ERROR);
      }
   }
   

   
   public abstract void createAndSerializeProxy(File file) throws Exception;

   protected void addInterceptorBinding(AspectManager manager, int index, Scope scope, String aspectClass, String pointcut) throws ParseException
   {
      addAspectBinding(manager, index, scope, aspectClass, null, pointcut);
   }
   
   protected void addAspectBinding(AspectManager manager, int index, Scope scope, String aspectClass, String adviceName, String pointcut) throws ParseException
   {
      AspectDefinition def = new AspectDefinition("aspect" + index, scope, new GenericAspectFactory(aspectClass, null));
      
      InterceptorFactory advice = (adviceName != null) ? new AdviceFactory(def, "advice") : new ScopedInterceptorFactory(def);
      PointcutExpression pc = new PointcutExpression("pc2" + index, pointcut);
      InterceptorFactory[] interceptors = {advice};
      AdviceBinding binding = new AdviceBinding("binding" + index, pc, null, null, interceptors);

      manager.addAspectDefinition(def);
      manager.addInterceptorFactory(advice.getName(), advice);
      manager.addPointcut(pc);
      manager.addBinding(binding);
   }
}
