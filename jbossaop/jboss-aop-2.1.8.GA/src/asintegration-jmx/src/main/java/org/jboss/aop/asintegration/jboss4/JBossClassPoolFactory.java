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
package org.jboss.aop.asintegration.jboss4;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AbstractJBossClassPoolFactory;
import org.jboss.mx.loading.RepositoryClassLoader;
import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 70841 $
 **/
public class JBossClassPoolFactory extends AbstractJBossClassPoolFactory implements ScopedClassPoolFactory
{
   protected File tmpClassesDir;

   public JBossClassPoolFactory(File tmpClassesDir) throws IOException
   {
      this.tmpClassesDir = tmpClassesDir;

   }
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      ClassPool parent = getCreateParentClassPools(cl, src, repository);
      if (cl instanceof RepositoryClassLoader)
      {
         File tempdir = getTempDirectory(cl);
         URL tmpCP;
         try
         {
            tmpCP = createURLAndAddToLoader(cl, tempdir);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (ScopedRepositoryClassLoaderHelper.isScopedClassLoader(cl))
         {
            //It is scoped
            return new ScopedJBossClassPool(cl, parent, repository, tempdir, tmpCP);
         }
         return new JBossClassPool(cl, parent, repository, tempdir, tmpCP);
      }
      return new AOPClassPool(cl, parent, repository);
   }

   protected File getTempDirectory(ClassLoader cl)
   {
      File tempdir = null;
      int attempts = 0;
      IOException ex = null;
      while (tempdir == null && attempts < 5)
      {
         //Workaround for JBAOP-254, retry a few times
         try
         {
            tempdir = createTempDir(cl);
         }
         catch (IOException e)
         {
            ex = e;
         }
      }
      
      if (tempdir == null)
      {
         throw new RuntimeException("", ex);
      }
      
      return tempdir;
   }

   public File createTempDir(ClassLoader cl) throws IOException
   {
      File tempdir = File.createTempFile("ucl", "", tmpClassesDir);
      tempdir.delete();
      tempdir.mkdir();
      tempdir.deleteOnExit();

      return tempdir;
   }
   
   private URL createURLAndAddToLoader(ClassLoader cl, File tempdir) throws IOException
   {
      URL tmpURL = tempdir.toURL();
      URL tmpCP = new URL(tmpURL, "?dynamic=true");

      RepositoryClassLoader ucl = (RepositoryClassLoader) cl;

      // We may be undeploying.
      if (ucl.getLoaderRepository() != null)
      {
         ucl.addURL(tmpCP);
      }
      
      return tmpCP;
   }

}
