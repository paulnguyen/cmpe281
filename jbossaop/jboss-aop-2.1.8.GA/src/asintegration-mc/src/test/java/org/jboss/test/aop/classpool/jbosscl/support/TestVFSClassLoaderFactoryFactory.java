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
package org.jboss.test.aop.classpool.jbosscl.support;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.metadata.ClassLoadingMetaData;
import org.jboss.classloading.spi.metadata.ExportAll;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class TestVFSClassLoaderFactoryFactory
{
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, importAll, null, false, urls);
   }
   
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, BundleInfoBuilder builder, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, importAll, null, null, builder, false, urls);
   }
   
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, String domainName, BundleInfoBuilder builder, boolean parentFirst, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, domainName, null, builder, parentFirst, urls);
   }
   
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, String domainName, String parentDomainName, BundleInfoBuilder builder, boolean parentFirst, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, false, domainName, parentDomainName, builder, false, urls);
   }

   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, String moduleName, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, importAll, null, false, urls);
   }
   
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, String domainName, boolean parentFirst, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, importAll, domainName, null, parentFirst, urls);
   }

   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, String domainName, String parentDomainName, boolean parentFirst, URL... urls) throws Exception
   {
      return createClassLoaderFactory(name, importAll, domainName, parentDomainName, null, parentFirst, urls);
   }
   
   public static TestVFSClassLoaderFactory createClassLoaderFactory(String name, boolean importAll, String domainName, String parentDomainName, BundleInfoBuilder builder, boolean parentFirst, URL... urls) throws Exception
   {
      TestVFSClassLoaderFactory factory = new TestVFSClassLoaderFactory();
      factory.setName(name);
      factory.setImportAll(importAll);
      if (importAll)
      {
         factory.setExportAll(ExportAll.NON_EMPTY);
      }
      factory.setRoots(urlsToStringList(urls));
      addCapabilitiesAndRequirements(factory, builder);
      setupDomain(factory, domainName, parentDomainName, parentFirst);
      return factory;
   }

   private static void addCapabilitiesAndRequirements(ClassLoadingMetaData md, BundleInfoBuilder builder)
   {
      if (builder != null)
      {
         md.getCapabilities().setCapabilities(builder.getCapabilities());
         md.getRequirements().setRequirements(builder.getRequirements());
      }
   }

   private static void setupDomain(ClassLoadingMetaData md, String domainName, String parentDomainName, boolean parentFirst)
   {
      if (domainName != null)
      {
         md.setDomain(domainName);
         md.setJ2seClassLoadingCompliance(parentFirst);
         if (parentDomainName != null)
         {
            md.setParentDomain(parentDomainName);
         }
         else
         {
            md.setParentDomain(ClassLoaderSystem.DEFAULT_DOMAIN_NAME);
         }
      }
      else
      {
         md.setDomain(ClassLoaderSystem.DEFAULT_DOMAIN_NAME);
      }
   }
   
   private static List<String> urlsToStringList(URL... urls)
   {
      List<String> urlList = new ArrayList<String>(urls.length);
      if (urls.length > 0)
      {
         for (URL url : urls)
         {
            if (url != null)
            {
               urlList.add(url.toString());
            }
         }
      }
      return urlList;
   }

}
