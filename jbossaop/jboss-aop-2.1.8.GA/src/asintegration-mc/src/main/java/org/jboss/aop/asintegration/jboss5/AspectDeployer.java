/*
* JBoss, Home of Professional Open Source
* Copyright 2005, Red Hat Middleware LLC., and individual contributors as indicated
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
package org.jboss.aop.asintegration.jboss5;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.ClassFile;

import org.jboss.aop.AspectAnnotationLoader;
import org.jboss.aop.AspectManager;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.Domain;
import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.spi.deployer.DeploymentStages;
import org.jboss.deployers.vfs.spi.deployer.AbstractVFSRealDeployer;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VirtualFileFilter;
import org.jboss.virtual.VisitorAttributes;
import org.jboss.virtual.plugins.context.jar.JarUtils;
import org.jboss.virtual.plugins.vfs.helpers.FilterVirtualFileVisitor;
import org.jboss.virtual.plugins.vfs.helpers.SuffixesExcludeFilter;
import org.w3c.dom.Document;

/**
 * Deployer for Aspects
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @author adrian@jboss.org
 */
public class AspectDeployer extends AbstractVFSRealDeployer
{
   private static final String AOP_JAR_SUFFIX = ".aop";
   private static final String AOP_DD_SUFFIX = "-aop.xml";

   /** The aspect manager */
   private AspectManager aspectManager;
   
   /**
    * Create a new AspectDeployer.
    */
   public AspectDeployer()
   {
      setStage(DeploymentStages.POST_CLASSLOADER);
   }

   /**
    * Get the aspectManager.
    * 
    * @return the aspectManager.
    */
   public AspectManager getAspectManager()
   {
      return aspectManager;
   }

   /**
    * Set the aspectManager.
    * 
    * @param aspectManager the aspectManager.
    */
   public void setAspectManager(AspectManager aspectManager)
   {
      this.aspectManager = aspectManager;
   }

   /**
    * Validate the configuration
    */
   public void create()
   {
      if (aspectManager == null)
         throw new IllegalStateException("No aspect manager configured");
   }
   
   public void deploy(VFSDeploymentUnit unit) throws DeploymentException
   {
      AspectManager manager = getCorrectManager(unit);
      unit.addAttachment(AspectManager.class, manager);
      
      List<VirtualFile> files = unit.getMetaDataFiles(null, AOP_DD_SUFFIX);

      if (isAopArchiveOrFolder(unit))
      {
         deployAnnotations(manager, unit);
      }
      
      if (files.size() > 0)
      {
         deployXml(manager, unit, files);
      }
   }
   
   public void undeploy(VFSDeploymentUnit unit)
   {
      try
      {
         AspectManager manager = unit.getAttachment(AspectManager.class);

         List<VirtualFile> files = unit.getMetaDataFiles(null, AOP_DD_SUFFIX);
   
         if (isAopArchiveOrFolder(unit))
         {
            undeployAnnotations(manager, unit);
         }
         
         if (files.size() > 0)
         {
            undeployXml(manager, unit, files);
         }
      }
      finally
      {
         AOPClassLoaderInitializer.unregisterLoaders(aspectManager, unit);
      }
   }

   private void deployXml(AspectManager manager, VFSDeploymentUnit unit, List<VirtualFile> files) throws DeploymentException
   {
      log.info("Deploying xml into " + manager + " for " + unit.getClassLoader());   
      
      ArrayList<VirtualFile> deployedFiles = new ArrayList<VirtualFile>(files.size());
      for (VirtualFile vf : files)
      {
         deployedFiles.add(vf);
         try
         {
            log.debug("deploying: " + vf.toURL() + " into " + manager);
            InputStream is = vf.openStream();
            try
            {
               Document doc = AspectXmlLoader.loadDocument(is);
               AspectXmlLoader loader = new AspectXmlLoader();
      
               loader.setManager(manager);
               loader.setClassLoader(unit.getClassLoader());
               loader.deployXML(doc, vf.toURL(), unit.getClassLoader());
            }
            finally
            {
               is.close();
            }
         }
         catch (Exception e)
         {
            //Unwind things already installed, in the reverse order
            for (int i = deployedFiles.size() - 1 ; i >= 0 ; i--)
            {
               undeployXml(manager, deployedFiles.get(i));
            }
            
            throw DeploymentException.rethrowAsDeploymentException("Error deploying xml " + vf.getName() + " into " + manager, e);
         }
      }
   }

   private void undeployXml(AspectManager manager, VFSDeploymentUnit unit, List<VirtualFile> files)
   {
      for (VirtualFile vf : files)
      {
         undeployXml(manager, vf);
      }
   }
   
   private void undeployXml(AspectManager manager, VirtualFile vf)
   {
      try
      {
         log.debug("undeploying: " + vf.toURL() + " from " + manager);
         InputStream is = vf.openStream();
         try
         {
            Document doc = AspectXmlLoader.loadDocument(is);
            AspectXmlLoader loader = new AspectXmlLoader();
            
            loader.setManager(manager);
            loader.undeployXML(doc, vf.toURL());
         }
         finally
         {
            try
            {
               is.close();
            }
            catch(IOException ignore)
            {
            }
         }
      }
      catch (Exception e)
      {
         log.warn("Error undeploying xml " + vf.getName() + " from " + manager, e);
      }
   }

   private void deployAnnotations(AspectManager manager, VFSDeploymentUnit unit) throws DeploymentException
   {
      log.info("Deploying AOP annotations into " + manager + " for " + unit.getClassLoader());   

      AspectAnnotationLoader loader = getAnnotationLoader(manager, unit); 
      List<VirtualFile> files = getClasses(unit);
      ArrayList<VirtualFile> deployedFiles = new ArrayList<VirtualFile>(files.size());
      for(VirtualFile file : files)
      {
         try
         {
            ClassFile cf = loadClassFile(file);
            log.debug("Deploying possibly annotated class " + cf.getName() + " into " + manager);
            loader.deployClassFile(cf);
         }
         catch (Exception e)
         {
            //Unwind things already installed, in the reverse order
            for (int i = deployedFiles.size() ; i >= 0 ; i-- )
            {
               undeployAnnotation(loader, deployedFiles.get(i));
            }
            throw new DeploymentException("Error reading annotations for " + file + " during deployment into " + manager, e);
         }
      }
   }
   
   private void undeployAnnotations(AspectManager manager, VFSDeploymentUnit unit)
   {
      AspectAnnotationLoader loader = getAnnotationLoader(manager, unit); 
      List<VirtualFile> files = getClasses(unit);
      for(VirtualFile file : files)
      {
         undeployAnnotation(loader, file);
      }
   }

   private void undeployAnnotation(AspectAnnotationLoader loader, VirtualFile file)
   {
      try
      {
         ClassFile cf = loadClassFile(file);
         log.debug("Undeploying possibly annotated class " + cf.getName());
         loader.undeployClassFile(cf);
      }
      catch (Exception e)
      {
         log.warn("Error reading annotations for " + file, e);
      }
   }
   
   private AspectAnnotationLoader getAnnotationLoader(AspectManager manager, VFSDeploymentUnit unit)
   {
      AspectAnnotationLoader loader = new AspectAnnotationLoader(manager);
      loader.setClassLoader(unit.getClassLoader());
      return loader;
   }
   
   private ClassFile loadClassFile(VirtualFile file)
   {
      DataInputStream din = null;
      ClassFile cf = null;
      try
      {
         InputStream in = file.openStream();
         din = new DataInputStream(new BufferedInputStream(in));
         cf = new ClassFile(din);
      }
      catch (IOException e)
      {
         throw new RuntimeException("Error reading " + file, e);
      }
      finally
      {
         try
         {
            din.close();
         }
         catch (IOException ignored)
         {
         }
      }
      
      return cf;
   }
   
   private List<VirtualFile> getClasses(VFSDeploymentUnit unit)
   {
      VisitorAttributes va = new VisitorAttributes();
      va.setLeavesOnly(true);
      ClassFileFilter filter = new ClassFileFilter();
      SuffixesExcludeFilter noJars = new SuffixesExcludeFilter(JarUtils.getSuffixes());
      va.setRecurseFilter(noJars);
      FilterVirtualFileVisitor visitor = new FilterVirtualFileVisitor(filter, va);

      for (VirtualFile vf : unit.getClassPath())
      {
         try
         {
            vf.visit(visitor);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }
      return visitor.getMatched();

   }
   
   private boolean isAopArchiveOrFolder(VFSDeploymentUnit unit)
   {
      String name = unit.getName();
      
      //If name is of format 'blah-blah.aop!/' get rid of the trailing '!' and '/', and see if it ends with .aop
      int index = name.length();
      if (name.charAt(name.length() - 1) == '/') 
      {
         index--;
      }
      if (name.charAt(name.length() - 2) == '!')
      {
         index--;
      }
      String realName = (index == name.length()) ? name : name.substring(0, index);
      
      return (realName.endsWith(AOP_JAR_SUFFIX));
   }
   
   private AspectManager getCorrectManager(final VFSDeploymentUnit unit)
   {
      //Scoped AOP deployments are only available when deployed as part of a scoped sar, ear etc.
      //It can contain an aop.xml file, or it can be part of a .aop file
      Domain domain = AOPClassLoaderInitializer.initializeForUnit(unit); 
      if (domain != null)
      {
         return domain;
      }
      
      return aspectManager;
   }
   
   private static class ClassFileFilter implements VirtualFileFilter
   {
      public boolean accepts(VirtualFile file)
      {
         try
         {
            return file.isLeaf() && file.getName().endsWith(".class");
         }
         catch (IOException e)
         {
            throw new RuntimeException("Error visiting file: " + file.getName(), e);
         }
      }
   }

}
