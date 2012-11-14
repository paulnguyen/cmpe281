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
package org.jboss.aop.deployment;

import java.io.File;

import javassist.scopedpool.ScopedClassPoolFactory;

import org.jboss.aop.AspectManager;
import org.jboss.aop.AspectNotificationHandler;
import org.jboss.aop.ClassLoaderValidation;
import org.jboss.aop.asintegration.JBossIntegration;
import org.jboss.aop.asintegration.core.AspectManagerServiceDelegate;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 70855 $
 */
public class AbstractAspectManagerService
        implements AbstractAspectManagerServiceMBean, AspectNotificationHandler
{
   Logger log = Logger.getLogger(AbstractAspectManagerService.class);
   
   private static final String BASE_XML = "base-aop.xml";
   
   //private boolean registerHappensAfterStart;
   
   private AspectManagerServiceDelegate delegate;
   
   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   /**
    * This constructor shouuld only get called when used in JBoss 4.x.x, not in JBoss 5.
    */
   public AbstractAspectManagerService()
   {
      //Initialise the manager and delegate
      delegate = createDelegate();
      delegate.setBaseXml(BASE_XML);
      delegate.setAspectManager(AspectManager.getTopLevelAspectManager());

      //This constructor shouuld only get called when used in JBoss 4.x.x, not in JBoss 5.
      //In JBoss 4 we need to maintain this field
      delegate.setAspectManagerMaintainAdvisorInterceptors(true);
      
      //Default 
      delegate.setUseBaseXml(true);
   }

   /**
    * This constructor should only get called when used in JBoss 5 by JBossAspectLibrary.
    */
   public AbstractAspectManagerService(AspectManagerServiceDelegate delegate)
   {
      this.delegate = delegate;
   }

   
   protected AspectManagerServiceDelegate createDelegate()
   {
      throw new RuntimeException("Should not use this class directly");
   }

   // Public -------------------------------------------------------

   public void setJBossIntegrationWrapper(JBossIntegrationWrapperMBean integration)
   {
      JBossIntegration intgr = integration != null ? integration.getIntegration() : null;
      delegate.setJBossIntegration(intgr);
   }

   protected ScopedClassPoolFactory createFactory() throws Exception
   {
      return delegate.createFactory();
   }
   
   protected ClassLoaderValidation createClassLoaderValidation()
   {
      return delegate.createClassLoaderValidation();
   }

   public void create()
           throws Exception
   {
      delegate.create();
      AspectManager.notificationHandler = this;
   }
   
   public void destroy()
   {
      delegate.destroy();
   }

   public void start() throws Exception
   {
      delegate.start();
   }

   public void stop()
   {
      delegate.stop();
   }

   public void attachClass(String classname)
   {
   }

   public boolean getPrune()
   {
      return delegate.getPrune();
   }

   public void setPrune(boolean prune)
   {
      delegate.setPrune(prune);
   }

   public String getExclude()
   {
      return delegate.getExclude();
   }

   public void setExclude(String exclude)
   {
      delegate.setExclude(exclude);
   }

   public String getInclude()
   {
      return delegate.getInclude();
   }

   public void setInclude(String include)
   {
      delegate.setInclude(include);
   }

   public String getIgnore()
   {
      return delegate.getIgnore();
   }

   public void setIgnore(String ignore)
   {
      delegate.setIgnore(ignore);
   }
   
   public String getIncludedInvisibleAnnotations()
   {
      return delegate.getIncludedInvisibleAnnotations();
   }
   
   public void setIncludedInvisibleAnnotations(String ia)
   {
      delegate.setIncludedInvisibleAnnotations(ia);
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public File getTmpClassesDir()
   {
      return delegate.getTmpClassesDir();
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public void setTmpClassesDir(File tmpClassesDir)
   {
      delegate.setTmpClassesDir(tmpClassesDir);
   }

   /**
    * Set the verbosity of aop logging.  It doesn't use log4j
    */
   public boolean getVerbose()
   {
      return delegate.getVerbose();
   }

   /**
    * Set the verbosity of aop logging.
    */
   public void setVerbose(boolean verbose)
   {
      delegate.setVerbose(verbose);
   }

   /**
    * Use aop optimizations.  Optional just in case there is a bug
    */
   public boolean getOptimized()
   {
      return delegate.getOptimized();
   }

   /**
    * Use aop optimizations.  Optional just in case there is a bug
    */
   public void setOptimized(boolean verbose)
   {
      delegate.setOptimized(verbose);
   }

   public boolean getSuppressTransformationErrors()
   {
      return delegate.getSuppressReferenceErrors();
   }

   public void setSuppressTransformationErrors(boolean suppressTransformationErrors)
   {
      delegate.setSuppressTransformationErrors(suppressTransformationErrors);
   }

   public boolean getSuppressReferenceErrors()
   {
      return delegate.getSuppressReferenceErrors();
   }

   public void setSuppressReferenceErrors(boolean suppressReferenceErrors)
   {
      delegate.setSuppressReferenceErrors(suppressReferenceErrors);
   }

   public boolean getEnableTransformer()
   {
      return delegate.getEnableTransformer();
   }

   public String interceptorFactories()
   {
      return delegate.interceptorFactories();
   }

   public String aspectDefinitions()
   {
      return delegate.aspectDefinitions();
   }

   public String introductions()
   {
      return delegate.introductions();
   }

   public String stacks()
   {
      return delegate.stacks();
   }

   public String bindings()
   {
      return delegate.bindings();
   }

   public String pointcuts()
   {
      return delegate.pointcuts();
   }

   public String registeredClassLoaders()
   {
      return delegate.registeredClassLoaders();
   }

   public void setEnableTransformer(boolean enableTransformer)
   {
      delegate.setEnableTransformer(enableTransformer);
   }

   public boolean getEnableLoadtimeWeaving()
   {
      return delegate.getEnableLoadtimeWeaving();
   }

   public void setEnableLoadtimeWeaving(boolean enableTransformer)
   {
      delegate.setEnableLoadtimeWeaving(enableTransformer);
   }

   public String getInstrumentor()
   {
      return delegate.getInstrumentor();
   }

   public void setInstrumentor(String instrumentor)
   {
      delegate.setInstrumentor(instrumentor);
   }
   
   public boolean getUseBaseXml()
   {
      return delegate.getUseBaseXml();
   }

   public synchronized void setUseBaseXml(boolean useBaseXml)
   {
      delegate.setUseBaseXml(useBaseXml);
   }

}
