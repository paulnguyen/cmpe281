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
import java.io.IOException;
import java.net.URL;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import org.jboss.aop.classpool.AbstractJBossDelegatingClassPoolFactory;
import org.jboss.aop.classpool.BaseClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolDomain;
import org.jboss.aop.classpool.ClassPoolDomainRegistry;
import org.jboss.aop.classpool.NonDelegatingClassPool;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.loading.RepositoryClassLoader;
import org.jboss.mx.server.ServerConstants;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossUclDelegatingClassPoolFactory extends AbstractJBossDelegatingClassPoolFactory implements ScopedClassPoolFactory
{
   private ClassPoolDomain mainDomain;
   protected File tmpClassesDir;

   public JBossUclDelegatingClassPoolFactory(File tmpClassesDir) throws IOException
   {
      this.tmpClassesDir = tmpClassesDir;
   }
   
   public synchronized ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      ClassPool parent = getCreateParentClassPools(cl, src, repository);

      if (cl instanceof RepositoryClassLoader)
      {
         ClassPoolDomain domain = getDomain((RepositoryClassLoader)cl);  
         
         File tempdir = getTempDirectory(cl);
         URL tmpCP = createURLAndAddToLoader(cl, tempdir);
         
         return new JBossUclDelegatingClassPool(domain, cl, parent, repository, tempdir, tmpCP);
      }
      
      return new NonDelegatingClassPool(cl, parent, repository, true);
   }

   private ClassPoolDomain getDomain(RepositoryClassLoader cl)
   {
      LoaderRepository loaderRepository = cl.getLoaderRepository();
      ClassPoolDomainRegistry registry = ClassPoolDomainRegistry.getInstance();
      ClassPoolDomain domain = registry.getDomain(loaderRepository);
      if (domain == null)
      {
         if (loaderRepository instanceof HeirarchicalLoaderRepository3)
         {
            
            boolean parentFirst = ((HeirarchicalLoaderRepository3)loaderRepository).getUseParentFirst();
            domain = new BaseClassPoolDomain("Scoped" + System.identityHashCode(loaderRepository), mainDomain, parentFirst);
         }
         else
         {
            if (mainDomain == null)
            {
               domain = new BaseClassPoolDomain(ServerConstants.DEFAULT_LOADER_NAME, null, false);
               mainDomain = domain;
            }
            else
            {
               domain = mainDomain;
            }
            registry.addClassPoolDomain(loaderRepository, domain);
         }
      }
      return domain;
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
   
   private URL createURLAndAddToLoader(ClassLoader cl, File tempdir)
   {
      try
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
      catch(Exception e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

}
