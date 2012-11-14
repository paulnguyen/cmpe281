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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.CtClass;

import org.jboss.classloading.spi.RealClassLoader;
import org.jboss.logging.Logger;
import org.jboss.virtual.plugins.context.memory.MemoryContextFactory;

/**
 * @author  <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version  $Revision: 1.1 $
 */
public class ToClassInvoker
{
   Logger logger = Logger.getLogger(this.getClass());
   
   public URL tempURL;

   public Object tmplock = new Object();

   public ToClassInvoker(URL tempURL)
   {
      this.tempURL = tempURL;
   }

   public Class<?> toClass(ToClassInvokerPoolReference pool, CtClass cc, String classFileName, ClassLoader loader, ProtectionDomain domain) throws CannotCompileException
   {
      boolean trace = logger.isTraceEnabled();
      pool.lockInCache(cc);
      final ClassLoader myloader = pool.getClassLoader();
      if (myloader == null || tempURL == null)
      {
         if (trace) logger.trace(this + " " + pool + ".toClass() myloader:" + myloader + " tempURL:" + tempURL + " default to superPool.toClass for " + cc.getName());
         Class<?> clazz = pool.superPoolToClass(cc, loader, domain);
         if (trace) logger.trace(this + " " + pool + " myloader:" + myloader + " created class:" + clazz);
         return clazz;
      }
      
      try
      {
         URL outputURL = new URL(tempURL.toString() + "/" + classFileName);
         //Write the classfile to the temporary url
         synchronized (tmplock)
         {
            if (trace) logger.trace(this + " " + pool + ".toClass() myloader:" + myloader + " writing bytes to " + tempURL);
            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(byteout);
            out.write(cc.toBytecode());
            out.flush();
            out.close();
            
            byte[] classBytes = byteout.toByteArray();
            MemoryContextFactory factory = MemoryContextFactory.getInstance();
            factory.putFile(outputURL, classBytes);

            if (myloader instanceof RealClassLoader)
            {
               ((RealClassLoader)myloader).clearBlackList(classFileName);
            }
            
            Class<?> clazz = myloader.loadClass(cc.getName());
            if (trace) logger.trace(this + " " + pool + " myloader:" + myloader + " created class:" + clazz);
            return clazz;
         }
      }
      catch(Exception e)
      {
         ClassFormatError cfe = new ClassFormatError("Failed to load dyn class: " + cc.getName() + " on " + this + " loader:" + myloader);
         cfe.initCause(e);
         throw cfe;
      }
   }
}