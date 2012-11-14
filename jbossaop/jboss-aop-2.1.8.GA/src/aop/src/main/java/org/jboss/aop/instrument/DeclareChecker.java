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
package org.jboss.aop.instrument;

import java.util.Iterator;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.pointcut.DeclareDef;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 71276 $
 */
public class DeclareChecker
{
   public static void checkDeclares(AspectManager manager, CtClass clazz, ClassAdvisor advisor)
   {
      for (Iterator<DeclareDef> it = manager.getDeclares() ; it.hasNext() ; )
      {
         DeclareDef declare = it.next();
         if (declare.matches(advisor, clazz))
         {
            StringBuffer sb = new StringBuffer(" condition\n\t'" + declare.getExpr() + "'\nwas broken for class " + 
            	clazz.getName() + "\n\t" + declare.getMsg() + "\n");
            
            if (declare.getWarning())
            {
               sb.insert(0, "WARNING: declare-warning");
               //System.out.println is ok here - want to guarantee that it works even if people have screwed up their logging config
               System.out.println(sb.toString());
            }
            else
            {
               sb.insert(0, "ERROR: declare-error");
               throw new RuntimeException(sb.toString());
            }
         }
      }
   }
   
   public static void checkDeclares(AspectManager manager, NewExpr call, ClassAdvisor advisor) throws NotFoundException
   {
      checkDeclares(manager, call, null, advisor);
   }
   
   public static void checkDeclares(AspectManager manager, MethodCall call, ClassAdvisor advisor) throws NotFoundException
   {
      checkDeclares(manager, null, call, advisor);
   }
   
   private static void checkDeclares(AspectManager manager, NewExpr newcall, MethodCall mcall, ClassAdvisor advisor) throws NotFoundException
   {
      for (Iterator<DeclareDef> it = manager.getDeclares() ; it.hasNext() ; )
      {
         DeclareDef declare = it.next();
         
         StringBuffer sb = new StringBuffer(" condition\n\t'" + declare.getExpr() + "'\nwas broken for "); 
         
         if ((newcall != null && declare.matchesCall(advisor, newcall) || (mcall != null && declare.matchesCall(advisor, mcall))))
         {
            if (mcall != null)
            {
               sb.append("method call:");
               CtBehavior caller = mcall.where();
               if (caller instanceof CtConstructor)
               {
                  CtConstructor con = (CtConstructor)caller;
                  addConstructor(sb, con);
                  sb.append(" calls "); 
                  addMethod(sb, mcall.getMethod());
               }
               else if (caller instanceof CtMethod)
               {
                  CtMethod met = (CtMethod)caller;
                  addMethod(sb, met);
                  sb.append(" calls ");
                  addMethod(sb, mcall.getMethod());
               }
            }
            else if (newcall != null)
            {
               sb.append("constructor call: ");
               CtBehavior caller = newcall.where();
               if (caller instanceof CtConstructor)
               {
                  CtConstructor con = (CtConstructor)caller;
                  addConstructor(sb, con);
                  sb.append(" calls ");
                  addConstructor(sb, newcall.getConstructor());
               }
               else if (caller instanceof CtMethod)
               {
                  CtMethod met = (CtMethod)caller;
                  addMethod(sb, met);
                  sb.append(" calls "); 
                  addConstructor(sb, newcall.getConstructor());
               }
            }

          	sb.append("\n\t" + declare.getMsg() + "\n");
            
            if (declare.getWarning())
            {
               sb.insert(0, "WARNING: declare-warning");
               //System.out.println is ok here - want to guarantee that it works even if people have screwed up their logging config
               System.out.println(sb.toString());
            }
            else
            {
               sb.insert(0, "ERROR: declare-error");
               throw new RuntimeException(sb.toString());
            }
         }
      }
   }

   private static void addMethod(StringBuffer sb, CtMethod method)
   {
      sb.append(method.getDeclaringClass().getName() + "." + method.getName() + method.getSignature());
   }
   
   private static void addConstructor(StringBuffer sb, CtConstructor con)
   {
      sb.append(con.getDeclaringClass().getName() + ".new" + con.getSignature());
   }
   
}
