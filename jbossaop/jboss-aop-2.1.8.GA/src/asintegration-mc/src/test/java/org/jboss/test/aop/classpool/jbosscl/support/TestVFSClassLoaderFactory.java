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

import java.util.Arrays;
import java.util.List;

import org.jboss.beans.metadata.spi.BeanMetaData;
import org.jboss.beans.metadata.spi.builder.BeanMetaDataBuilder;
import org.jboss.classloader.spi.ClassLoaderSystem;
import org.jboss.classloading.spi.vfs.dependency.VFSClassLoaderPolicyModule;
import org.jboss.classloading.spi.vfs.metadata.VFSClassLoaderFactory;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class TestVFSClassLoaderFactory extends VFSClassLoaderFactory
{
   private static final long serialVersionUID = 1L;
   
   private ClassLoader parent;

   @Override
   protected Class<? extends VFSClassLoaderPolicyModule> getModuleClass()
   {
      return TestVFSClassLoaderPolicyModule.class;
   }
   
   public void setParent(ClassLoader parent)
   {
      this.parent = parent;
   }
   
   @Override
   public List<BeanMetaData> getBeans()
   {

       List<BeanMetaData> result = super.getBeans();
      if (parent != null)
      {
         //We need to modify the Module factory method used to create the classloader to pass in the parent
         if (result.size() != 2)
         {
            throw new IllegalStateException("Expected size=2, was " + result.size());
         }
      
         BeanMetaData classLoader = null;
         BeanMetaData module = null;
         for (BeanMetaData bean : result)
         {
            if (bean.getBean().equals(ClassLoader.class.getName()))
            {
               classLoader = bean;
            }
            else
            {
               module = bean;
            }
         }
         
         if (module == null)
         {
            throw new IllegalStateException("Could not find module");
         }
         if (classLoader == null)
         {
            throw new IllegalStateException("Could not find module");
         }
         
         BeanMetaDataBuilder builder = BeanMetaDataBuilder.createBuilder(classLoader.getName(), ClassLoader.class.getName());
         builder.setNoClassLoader();
         builder.setFactory(module.getName());
         builder.setFactoryMethod("registerClassLoaderPolicy");
         builder.addConstructorParameter(ClassLoaderSystem.class.getName(), builder.createInject(getClassLoaderSystemName()));
         builder.addConstructorParameter(ClassLoader.class.getName(), parent);
         classLoader = builder.getBeanMetaData();

         result = Arrays.asList(classLoader, module);
      }
      
      return result;
   }
}
