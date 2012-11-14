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
package org.jboss.aop.asintegration.jboss5;

import java.net.URL;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.classpool.AOPClassPool;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBoss5ClassPool extends AOPClassPool implements ToClassInvokerPoolReference
{
   protected ToClassInvoker toClassInvoker = null;

   boolean closed;
   
   protected JBoss5ClassPool(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository, URL tmpURL)
   {
      super(cl, src, repository);
      toClassInvoker = new ToClassInvoker(tmpURL);
   }

   protected JBoss5ClassPool(ClassPool src, ScopedClassPoolRepository repository)
   {
      super(src, repository);
   }

   public boolean isUnloadedClassLoader()
   {
      return closed;
   }

   public void close()
   {
      closed = true;
      super.close();
   }

   public Class<?> toClass(CtClass cc, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException
   {
      return toClassInvoker.toClass(this, cc, getResourceName(cc.getName()), loader, domain);
   }

   public Class<?> superPoolToClass(CtClass cc, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException
   {
      return super.toClass(cc, loader, domain);
   }
}
