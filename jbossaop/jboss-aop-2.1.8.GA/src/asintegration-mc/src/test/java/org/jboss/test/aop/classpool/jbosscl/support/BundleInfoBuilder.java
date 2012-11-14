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

import java.util.ArrayList;
import java.util.List;

import org.jboss.classloading.spi.metadata.Capability;
import org.jboss.classloading.spi.metadata.ClassLoadingMetaDataFactory;
import org.jboss.classloading.spi.metadata.Requirement;
import org.jboss.classloading.spi.version.VersionRange;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class BundleInfoBuilder
{
   static ClassLoadingMetaDataFactory clmdf = ClassLoadingMetaDataFactory.getInstance();
   
   List<Capability> capabilities = new ArrayList<Capability>();
   List<Requirement> requirements = new ArrayList<Requirement>();
   
   private BundleInfoBuilder()
   {
      
   }
   
   public static BundleInfoBuilder getBuilder()
   {
      return new BundleInfoBuilder();
   }
   
   public BundleInfoBuilder createModule(String name)
   {
      capabilities.add(clmdf.createModule(name));
      return this;
   }
   
   public BundleInfoBuilder createModule(String name, Object version)
   {
      capabilities.add(clmdf.createModule(name, version));
      return this;
   }
   
   public BundleInfoBuilder createRequireModule(String name)
   {
      requirements.add(clmdf.createRequireModule(name, null));
      return this;
   }

   public BundleInfoBuilder createRequireModule(String name, VersionRange versionRange)
   {
      requirements.add(clmdf.createRequireModule(name, versionRange));
      return this;
   }

   public BundleInfoBuilder createRequireModule(String name, VersionRange versionRange, boolean optional, boolean reExport, boolean dynamic)
   {
      requirements.add(clmdf.createRequireModule(name, versionRange, optional, reExport, dynamic));
      return this;
   }

   public BundleInfoBuilder createPackage(String name)
   {
      capabilities.add(clmdf.createPackage(name));
      return this;
   }

   public BundleInfoBuilder createPackage(String name, Object version)
   {
      capabilities.add(clmdf.createPackage(name, version));
      return this;
   }

   public BundleInfoBuilder createRequirePackage(String name)
   {
      requirements.add(clmdf.createRequirePackage(name));
      return this;
   }

   public BundleInfoBuilder createRequirePackage(String name, VersionRange versionRange)
   {
      requirements.add(clmdf.createRequirePackage(name, versionRange));
      return this;
   }

   public BundleInfoBuilder createRequirePackage(String name, VersionRange versionRange, boolean optional, boolean reExport, boolean dynamic)
   {
      requirements.add(clmdf.createRequirePackage(name, versionRange, optional, reExport, dynamic));
      return this;
   }

   public BundleInfoBuilder createReExportModule(String name)
   {
      requirements.add(clmdf.createReExportModule(name));
      return this;
   }

   public BundleInfoBuilder createReExportModule(String name, VersionRange versionRange)
   {
      requirements.add(clmdf.createReExportModule(name, versionRange));
      return this;
   }

   public BundleInfoBuilder createReExportModule(String name, VersionRange versionRange, boolean optional)
   {
      requirements.add(clmdf.createReExportModule(name, versionRange, optional));
      return this;
   }

   public BundleInfoBuilder createReExportPackage(String name)
   {
      requirements.add(clmdf.createReExportPackage(name));
      return this;
   }

   public BundleInfoBuilder createReExportPackage(String name, VersionRange versionRange)
   {
      requirements.add(clmdf.createReExportPackage(name, versionRange));
      return this;
   }

   public BundleInfoBuilder createReExportPackage(String name, VersionRange versionRange, boolean optional)
   {
      requirements.add(clmdf.createReExportPackage(name, versionRange, optional));
      return this;
   }
   
   public BundleInfoBuilder createUsesPackage(String name)
   {
      requirements.add(clmdf.createUsesPackage(name));
      return this;
   }

   public BundleInfoBuilder createUsesPackage(String name, VersionRange versionRange)
   {
      requirements.add(clmdf.createUsesPackage(name, versionRange));
      return this;
   }

   public BundleInfoBuilder createUsesPackage(String name, VersionRange versionRange, boolean reExport)
   {
      requirements.add(clmdf.createUsesPackage(name, versionRange, reExport));
      return this;
   }

   
   
   public List<Capability> getCapabilities()
   {
      return capabilities;
   }
   
   public List<Requirement> getRequirements()
   {
      return requirements;
   }
}
