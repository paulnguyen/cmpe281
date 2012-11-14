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
package org.jboss.aop.classpool.ucl;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.asintegration.jboss4.ToClassInvoker;
import org.jboss.aop.asintegration.jboss4.ToClassInvokerPoolReference;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.DelegatingClassPool;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossUclDelegatingClassPool extends DelegatingClassPool implements ToClassInvokerPoolReference
{
   ToClassInvoker toClassInvoker = null;

   public JBossUclDelegatingClassPool(ClassPoolDomain domain, ClassLoader cl, ClassPool parent, 
         ScopedClassPoolRepository repository, File tmpDir, URL tmpURL)
   {
      super(domain, cl, parent, repository);
      toClassInvoker = new ToClassInvoker(tmpDir);
   }

   public Class<?> toClass(CtClass cc, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException
   {
      return toClassInvoker.toClass(this, cc, getResourceName(cc.getName()), loader, domain);
   }

   public Class<?> superPoolToClass(CtClass cc, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException
   {
      return super.toClass(cc, loader, domain);
   }

   @Override
   public void lockInCache(CtClass clazz)
   {
      super.lockInCache(clazz);
      localResources.put(getResourceName(clazz.getName()), Boolean.TRUE);
   }
}
