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


import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class NonOptimizedCallerTransformer extends CallerTransformer
{
   public final static String PLACEHOLDER = "whatever";
   
   public NonOptimizedCallerTransformer(Instrumentor instrumentor, AspectManager manager)
   {
      super(instrumentor, manager, false, new ClassicCallerInfoAdder(instrumentor));
   }

   protected CallerExprEditor callerExprEditorFactory(ClassAdvisor advisor, CtClass clazz)
   {
      return new NonOptimizedCallerExprEditor(advisor, clazz);
   }
   
   class NonOptimizedCallerExprEditor extends CallerExprEditor
   {

      public NonOptimizedCallerExprEditor(ClassAdvisor advisor, CtClass callingClass)
      {
         super(advisor, callingClass);
      }
      
      protected void setupConstructor(ConstructorDetail cd)throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            String invocationClassName = PLACEHOLDER;
         
            callerInfos.put(cd.callerInfoField, invocationClassName);
            callerInfoAdder.addMethodByConInfoField(callingClass, cd.callerInfoField, callingClass.getName(), cd.callingIndex, cd.classname, cd.calledHash);
         }
      }

      protected void setupMethod(MethodDetail md) throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(md.callerInfoField) == null)
         {
            String invocationClassName = PLACEHOLDER;
                  
            callerInfos.put(md.callerInfoField, invocationClassName);
            callerInfoAdder.addMethodByMethodInfoField(callingClass, md.callerInfoField, md.callingHash, md.classname, md.calledHash);
         }         
      }

      protected void setupMethod(ConByMethodDetail cd) throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            String invocationClassName = PLACEHOLDER;

            callerInfos.put(cd.callerInfoField, invocationClassName);
            callerInfoAdder.addConByMethodInfoField(callingClass, cd.callerInfoField, cd.callingHash, cd.classname, cd.calledHash);
         }
      }
      
      protected void setupConstructor(ConByConDetail cd)throws NotFoundException, CannotCompileException
      {
         if (callerInfos.get(cd.callerInfoField) == null)
         {
            String invocationClassName = PLACEHOLDER;
            
            callerInfos.put(cd.callerInfoField, invocationClassName);
            callerInfoAdder.addConByConInfoField(callingClass, cd.callerInfoField, callingClass.getName(), cd.callingIndex, cd.classname, cd.calledHash);
         }
      }

      
      
   }

}
