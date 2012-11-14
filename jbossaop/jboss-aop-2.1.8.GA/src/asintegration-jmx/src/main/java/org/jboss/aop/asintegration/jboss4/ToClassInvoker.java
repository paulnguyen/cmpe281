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
package org.jboss.aop.asintegration.jboss4;

import java.io.File;
import java.io.FileOutputStream;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.CtClass;

import org.jboss.logging.Logger;
import org.jboss.mx.loading.RepositoryClassLoader;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ToClassInvoker
{
   Logger logger = Logger.getLogger(this.getClass());

   public File tmpDir;

   public Object tmplock = new Object();

   public ToClassInvoker(File tmpDir)
   {
      this.tmpDir = tmpDir;
   }

   public Class<?> toClass(ToClassInvokerPoolReference pool, CtClass cc, String classFileName, ClassLoader loader, ProtectionDomain domain)
   throws CannotCompileException
   {
      boolean trace = logger.isTraceEnabled();
      pool.lockInCache(cc);
      final ClassLoader myloader = pool.getClassLoader();
      if (myloader == null || tmpDir == null)
      {
         if (trace) logger.trace(this + " " + pool + ".toClass() myloader:" + myloader + " tmpDir:" + tmpDir + " default to superPool.toClass for " + cc.getName());
         Class<?> clazz = pool.superPoolToClass(cc, loader, domain);
         if (trace) logger.trace(this + " " + pool + " myloader:" + myloader + " created class:" + clazz);
         return clazz;
      }
      Class<?> dynClass = null;
      try
      {
         File classFile = null;
         // Write the clas file to the tmpdir
         synchronized (tmplock)
         {
            classFile = new File(tmpDir, classFileName);
            if (trace) logger.trace(this + " " + pool + ".toClass() myloader:" + myloader + " writing bytes to " + classFile);
            File pkgDirs = classFile.getParentFile();
            pkgDirs.mkdirs();
            FileOutputStream stream = new FileOutputStream(classFile);
            stream.write(cc.toBytecode());
            stream.flush();
            stream.close();
            classFile.deleteOnExit();
         }
         // We have to clear Blacklist caches or the class will never
         // be found
         //((UnifiedClassLoader)dcl).clearBlacklists();
         // To be backward compatible
         RepositoryClassLoader rcl = (RepositoryClassLoader)myloader;
         rcl.clearClassBlackList();
         rcl.clearResourceBlackList();

         // Now load the class through the cl
         dynClass = myloader.loadClass(cc.getName());
         if (trace) logger.trace(this + " " + pool + " myloader:" + myloader + " created class:" + dynClass);
         return dynClass;
      }
      catch (Exception ex)
      {
         ClassFormatError cfe = new ClassFormatError("Failed to load dyn class: " + cc.getName());
         cfe.initCause(ex);
         throw cfe;
      }
   }

}
