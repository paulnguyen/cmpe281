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

import org.jboss.aop.ClassAdvisor;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class NonOptimizedMethodExecutionTransformer  extends MethodExecutionTransformer 
{
   public NonOptimizedMethodExecutionTransformer(Instrumentor instrumentor) 
   {
      super(instrumentor);
   }

   protected void transformMethod(MethodTransformation trans, boolean wrap) throws NotFoundException, CannotCompileException
   {
      String methodInfoField = addMethodInfoField(Modifier.PRIVATE | Modifier.STATIC, trans.getClazz(), trans);
      // generate Wrapper
      String wrappedName = ClassAdvisor.notAdvisedMethodName(trans.getClazzName(),
                                                             trans.getMethod().getName());
      CtMethod wmethod = CtNewMethod.copy(trans.getMethod(), trans.getClazz(), null);

      String originalName = trans.getOriginalName();
      wmethod.setName(wrappedName);
      trans.getClazz().addMethod(wmethod);
      moveAnnotationsAndCopySignature(trans.getMethod(), wmethod);
      trans.getMethod().setName(wrappedName);
      Instrumentor.addSyntheticAttribute(trans.getMethod());
      wmethod.setName(originalName);
      
      trans.setWMethod(wmethod, wrappedName);

      // prepareForWrapping
      getWrapper().prepareForWrapping(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);


      if (wrap)
      {
         // wrap
         getWrapper().wrap(wmethod, WrapperTransformer.SINGLE_TRANSFORMATION_INDEX);

         // executeWrapping
         setWrapperBody(trans, methodInfoField);
      }
   }

   protected void doWrap(MethodTransformation trans, String methodInfoFieldName)throws NotFoundException, Exception
   {
      setWrapperBody(trans, methodInfoFieldName);
   }
   
//   protected void setWrapperBody(CtMethod method, String name, long hash, String methodInfoField, String wrappedName, CtMethod wmethod) throws NotFoundException
   protected void setWrapperBody(MethodTransformation trans, String methodInfoField) throws NotFoundException
   {
      //make a copy of method and modify the existing method because we need to retain annotations
      //of existing method
      //wmethod = method;
      boolean isStatic = Modifier.isStatic(trans.getMethod().getModifiers());
      String code = null;
      if (!isStatic)
      {
         code =
         "{ " +
         "    " + methodInfoFromWeakReference("info", methodInfoField) + 
         "    org.jboss.aop.ClassInstanceAdvisor instAdv = (org.jboss.aop.ClassInstanceAdvisor)_getInstanceAdvisor();" +
         "    if (info.getInterceptors() != (Object[])null || (instAdv != null && instAdv.hasInstanceAspects)) " +
         "    { " +
         "       Object[] ags = $args; " +
         "       " + getAopReturnStr(trans.getMethod()) + Instrumentor.HELPER_FIELD_NAME + ".invokeMethod(instAdv, this, " + trans.getHash() + "L, ags, info); " +
         "    } " +
         "    else " +
         "    {" +
         "       " + getReturnStr(trans.getMethod()) + " " + trans.getWrappedName() + "($$); " +
         "    }" +
         "}";
      }
      else
      {
         code =
         "{ " +
         "    " + methodInfoFromWeakReference("info", methodInfoField) + 
         "    if (info.getInterceptors() != (Object[])null) " +
         "    { " +
         "       org.jboss.aop.ClassInstanceAdvisor ia = null; " +
         "       Object[] ags = $args; " +
         "       Object target = null; " +
         "       " + getAopReturnStr(trans.getMethod()) + Instrumentor.HELPER_FIELD_NAME + ".invokeMethod(ia, target, " + trans.getHash() + "L, ags, info); " +
         "    } " +
         "    else " +
         "    {" +
         "       " + getReturnStr(trans.getMethod()) + " " + trans.getWrappedName() + "($$); " +
         "    }" +
         "}";
      }
      try
      {
         trans.setWMethodBody(code);
      }
      catch (CannotCompileException e)
      {
         e.printStackTrace();
         throw new RuntimeException("code was: " + code + " for method " + trans.getOriginalName());  //To change body of catch statement use Options | File Templates.
      }
   }
}
