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
package org.jboss.aop.deployers;

import java.io.File;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.JBossIntegration;
import org.jboss.aop.asintegration.core.AspectManagerServiceDelegate;
import org.jboss.logging.Logger;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractAspectManager
{
   final static String BASE_ASPECTS_XML = "base-aspects.xml";
   final static boolean REGISTER_AFTER_START = true;
   
   private AspectManagerServiceDelegate delegate = null;

   public AbstractAspectManager(String bootstrapXml)
   {
      delegate = createDelegate();
      delegate.setAspectManager(AspectManager.getTopLevelAspectManager());
      delegate.setBootstrapXml(bootstrapXml);
      delegate.setBaseXml(BASE_ASPECTS_XML);
      delegate.setRegisterHappensAfterStart(REGISTER_AFTER_START);
   }
   
   public AspectManager getAspectManager()
   {
      return AspectManager.getTopLevelAspectManager();
   }
   
   protected abstract AspectManagerServiceDelegate createDelegate();
   
   public AspectManagerServiceDelegate getDelegate()
   {
      return delegate;
   }
   
   
   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#aspectDefinitions()
    */
   public String aspectDefinitions()
   {
      return delegate.aspectDefinitions();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#bindings()
    */
   public String bindings()
   {
      return delegate.bindings();
   }

   /**
    * @throws Exception
    * @see org.jboss.system.ServiceMBeanSupport#create()
    */
   public void create() throws Exception
   {
      delegate.create();
   }

   /**
    * @throws Exception
    * @see org.jboss.system.ServiceMBeanSupport#start()
    */
   public void start() throws Exception
   {
      delegate.start();
   }


   public void stop()
   {
      delegate.stop();
   }
   /**
    * 
    * @see org.jboss.system.ServiceMBeanSupport#destroy()
    */
   public void destroy()
   {
      delegate.destroy();
   }

   /**
    * @param obj
    * @return
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj)
   {
      return delegate.equals(obj);
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getEnableLoadtimeWeaving()
    */
   public boolean getEnableLoadtimeWeaving()
   {
      return delegate.getEnableLoadtimeWeaving();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getEnableTransformer()
    */
   public boolean getEnableTransformer()
   {
      return delegate.getEnableTransformer();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getExclude()
    */
   public String getExclude()
   {
      return delegate.getExclude();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getIgnore()
    */
   public String getIgnore()
   {
      return delegate.getIgnore();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getInclude()
    */
   public String getInclude()
   {
      return delegate.getInclude();
   }
   
   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getIncludedInvisibleAnnotations()
    */
   public String getIncludedInvisibleAnnotations()
   {
      return delegate.getIncludedInvisibleAnnotations();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getInstrumentor()
    */
   public String getInstrumentor()
   {
      return delegate.getInstrumentor();
   }

   /**
    * @return
    * @see org.jboss.aop.core.AspectManagerServiceDelegat#getLog()
    */
   public Logger getLog()
   {
      return delegate.getLog();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getOptimized()
    */
   public boolean getOptimized()
   {
      return delegate.getOptimized();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getPrune()
    */
   public boolean getPrune()
   {
      return delegate.getPrune();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getSuppressReferenceErrors()
    */
   public boolean getSuppressReferenceErrors()
   {
      return delegate.getSuppressReferenceErrors();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getSuppressTransformationErrors()
    */
   public boolean getSuppressTransformationErrors()
   {
      return delegate.getSuppressTransformationErrors();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getTmpClassesDir()
    */
   public File getTmpClassesDir()
   {
      return delegate.getTmpClassesDir();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getVerbose()
    */
   public boolean getVerbose()
   {
      return delegate.getVerbose();
   }

   /**
    * @return
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return delegate.hashCode();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#interceptorFactories()
    */
   public String interceptorFactories()
   {
      return delegate.interceptorFactories();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#introductions()
    */
   public String introductions()
   {
      return delegate.introductions();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#registeredClassLoaders()
    */
   public String registeredClassLoaders()
   {
      return delegate.registeredClassLoaders();
   }

   /**
    * @param enableTransformer
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setEnableLoadtimeWeaving(boolean)
    */
   public void setEnableLoadtimeWeaving(boolean enableTransformer)
   {
      delegate.setEnableLoadtimeWeaving(enableTransformer);
   }

   /**
    * @param enableTransformer
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setEnableTransformer(boolean)
    */
   public void setEnableTransformer(boolean enableTransformer)
   {
      delegate.setEnableTransformer(enableTransformer);
   }

   /**
    * @param exclude
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setExclude(java.lang.String)
    */
   public void setExclude(String exclude)
   {
      delegate.setExclude(exclude);
   }

   /**
    * @param ignore
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setIgnore(java.lang.String)
    */
   public void setIgnore(String ignore)
   {
      delegate.setIgnore(ignore);
   }

   /**
    * @param include
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setInclude(java.lang.String)
    */
   public void setInclude(String include)
   {
      delegate.setInclude(include);
   }
   
   /**
    * @param include
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setIncludedInvisibleAnnotations(java.lang.String)
    */
   public void setIncludedInvisibleAnnotations(String iia)
   {
      delegate.setIncludedInvisibleAnnotations(iia);
   }

   /**
    * @param instrumentor
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setInstrumentor(java.lang.String)
    */
   public void setInstrumentor(String instrumentor)
   {
      delegate.setInstrumentor(instrumentor);
   }

   /**
    * @param verbose
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setOptimized(boolean)
    */
   public void setOptimized(boolean verbose)
   {
      delegate.setOptimized(verbose);
   }

   /**
    * @param prune
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setPrune(boolean)
    */
   public void setPrune(boolean prune)
   {
      delegate.setPrune(prune);
   }

   /**
    * @param suppressReferenceErrors
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setSuppressReferenceErrors(boolean)
    */
   public void setSuppressReferenceErrors(boolean suppressReferenceErrors)
   {
      delegate.setSuppressReferenceErrors(suppressReferenceErrors);
   }

   /**
    * @param suppressTransformationErrors
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setSuppressTransformationErrors(boolean)
    */
   public void setSuppressTransformationErrors(boolean suppressTransformationErrors)
   {
      delegate.setSuppressTransformationErrors(suppressTransformationErrors);
   }

   /**
    * @param tmpClassesDir
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setTmpClassesDir(java.io.File)
    */
   public void setTmpClassesDir(File tmpClassesDir)
   {
      delegate.setTmpClassesDir(tmpClassesDir);
   }

   /**
    * @param verbose
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setVerbose(boolean)
    */
   public void setVerbose(boolean verbose)
   {
      delegate.setVerbose(verbose);
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#stacks()
    */
   public String stacks()
   {
      return delegate.stacks();
   }

   public void deployBaseAspects()
   {
      delegate.deployBaseXml();
   }

   public void undeployBaseAspects()
   {
      delegate.undeployBaseXml();
   }


   /**
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getUseBaseXml()
    */
   public boolean getUseBaseXml()
   {
      return delegate.getUseBaseXml();
   }

   /**
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setUseBaseXml()
    */
   public synchronized void setUseBaseXml(boolean useBaseXml)
   {
      delegate.setUseBaseXml(useBaseXml);
   }

   /**
    * @return
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return delegate.toString();
   }

   /**
    * @return
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#getJBossIntegration()
    */
   public JBossIntegration getJbossIntegration()
   {
      return delegate.getJBossIntegration();
   }

   /**
    * @param integration
    * @see org.jboss.aop.asintegration.core.AspectManagerServiceDelegate#setJBossIntegration(org.jboss.asintegration.JBossIntegration)
    */
   public void setJbossIntegration(JBossIntegration integration)
   {
      delegate.setJBossIntegration(integration);
   }
}
