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

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.classpool.AOPClassPool;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 46253 $
 * @deprecated Will be removed when Javassist is upgraded past 3.0 beta2
 */
public class StandaloneClassPool extends AOPClassPool
{
   public StandaloneClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      super(cl, src, repository);
   }

   public StandaloneClassPool(ClassPool src, ScopedClassPoolRepository repository)
   {
      super(src, repository);
   }

   public Class<?> toClass(CtClass ctClass) throws CannotCompileException
   {
      try
      {
         byte[] b = ctClass.toBytecode();
         Class<?> cl = Class.forName("java.lang.ClassLoader");
         java.lang.reflect.Method method
                 = cl.getDeclaredMethod("defineClass",
                         new Class[]{String.class, byte[].class,
                                     int.class, int.class});
         method.setAccessible(true);
         Object[] args = new Object[]{ctClass.getName(), b, new Integer(0),
                                      new Integer(b.length)};
         Class<?> clazz = (Class<?>) method.invoke(getClassLoader(), args);
         method.setAccessible(false);
         return clazz;
      }
      catch (RuntimeException e)
      {
         throw e;
      }
      catch (java.lang.reflect.InvocationTargetException e)
      {
         throw new CannotCompileException(e);
      }
      catch (Exception e)
      {
         throw new CannotCompileException(e);
      }
   }

   public synchronized CtClass getLocally(String classname) throws NotFoundException
   {
      return super.getLocally(classname);
   }
}
