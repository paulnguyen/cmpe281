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
package org.jboss.aop.asintegration.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;

import javax.xml.parsers.ParserConfigurationException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.ClassLoaderValidation;
import org.jboss.aop.ClassicWeavingStrategy;
import org.jboss.aop.Deployment;
import org.jboss.aop.SuperClassesFirstWeavingStrategy;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceStack;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.annotation.PortableAnnotationElement;
import org.jboss.aop.asintegration.JBossIntegration;
import org.jboss.aop.hook.JDK14Transformer;
import org.jboss.aop.hook.JDK14TransformerManager;
import org.jboss.aop.instrument.InstrumentorFactory;
import org.jboss.aop.instrument.TransformerCommon;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public abstract class AspectManagerServiceDelegate
{
   Logger log = Logger.getLogger(AspectManagerServiceDelegate.class);
   
   static {
      //pre-load necessary classes so that we avoid NoClassDefFoundErrors on JRockit when using the RepositoryClassloader hook
      //When AspectManager.translate() is called the first time, these classes have not been loaded yet, and this is what causes
      //JRockit to get confused
      @SuppressWarnings("unused")
      Class<?> clazz = TransformerCommon.class;
      clazz = SuperClassesFirstWeavingStrategy.class;
      clazz = ClassicWeavingStrategy.class;
   }

   // Attributes ---------------------------------------------------

//   boolean created = false;
   protected File tmpClassesDir;
   
   /** True if the deprecated translator is enabled */
   protected boolean enableTransformer = false;
   
   /** True if the noveau translator is enabled */
   protected boolean enableLoadtimeWeaving = false;
   protected boolean suppressTransformationErrors = true;
   protected boolean suppressReferenceErrors = true;
   protected String exclude;
   protected String include;
   protected String ignore;
   protected String includedInvisibleAnnotations;
   
   /**
    * A string containing xml to be deployed first thing when this service is created
    */
   private String bootstrapXml;
   /**
    * The name of the file used to deploy the core aspects
    */
   private String baseXml;
   
   /** Whether we found and deployed baseXml */
   private boolean deployedBaseXml;

   /** Whether we should deploy baseXml or not */ 
   private boolean useBaseXml;
   
   /** When running with JBoss 5 registration with MBeanServer happens after the service has been started */
   boolean registerHappensAfterStart;
   
   /** The encapsulation of the integration */
   private JBossIntegration integration;
   
   /** Whether this service was started */
   private boolean started;
   
   
   /** The AspectManager managed by this service */
   private AspectManager manager;
   
   
   
   // Static -------------------------------------------------------

   // Constructors -------------------------------------------------
   public AspectManagerServiceDelegate()
   {
   }

   public void setBootstrapXml(String bootstrapXml)
   {
      this.bootstrapXml = bootstrapXml;
   }
   
   public void setBaseXml(String baseXml)
   {
      this.baseXml = baseXml;
   }
   
   public void setRegisterHappensAfterStart(boolean registerHappensAfterStart)
   {
      this.registerHappensAfterStart = registerHappensAfterStart;
   }

   /**
    * This needs setting to true in AS 4
    */
   public void setAspectManagerMaintainAdvisorInterceptors(boolean maintain)
   {
      AspectManager.maintainAdvisorMethodInterceptors = maintain;
   }
   
   public void setAspectManager(AspectManager manager)
   {
      this.manager = manager;
   }
   
   public AspectManager getAspectManager()
   {
      return manager;
   }

   // Public -------------------------------------------------------

   /**
    * Get the integration
    * 
    * @return the integration
    */
   public JBossIntegration getJBossIntegration()
   {
      return integration;
   }
   
   /**
    * Set the integration
    * 
    * @param integration the integration 
    */
   public void setJBossIntegration(JBossIntegration integration)
   {
      this.integration = integration;
   }

   public ScopedClassPoolFactory createFactory() throws Exception
   {
      return initIntegration().createScopedClassPoolFactory(tmpClassesDir);
   }
   
   public ClassLoaderValidation createClassLoaderValidation()
   {
      return initIntegration();
   }

   /**
    * Initialize the integration if not alreday done so
    * 
    * @return the integration
    */
   protected JBossIntegration initIntegration()
   {
      if (integration == null)
      {
         throw new IllegalStateException("Integration was not set");
      }
      return integration;
   }
   
   public void create()
           throws Exception
   {
      initIntegration();
      if (started)
      {
         return;
      }
      PortableAnnotationElement.setClosingDownManager(false);
      
      
      // Set a default tmp classes dir to the jboss tmp dir/aopclasses
      if (tmpClassesDir == null)
      {
         //Hardcode this since the constant exists in different locations in AS 4 and AS 5
         String jbossTmpDir = System.getProperty("jboss.server.temp.dir");
         if (jbossTmpDir == null)
            jbossTmpDir = System.getProperty("java.io.tmpdir");
         tmpClassesDir = new File(jbossTmpDir, "aopdynclasses");
      }
      // Validate the the tmp dir exists
      if (tmpClassesDir.exists() == false && tmpClassesDir.mkdirs() == false)
         throw new FileNotFoundException("Failed to create tmpClassesDir: " + tmpClassesDir.getAbsolutePath());
      AspectManager.setClassPoolFactory(createFactory());

      AspectManager.classLoaderValidator = createClassLoaderValidation();
      // Add the tmp classes dir to our UCL classpath

      Deployment.searchClasspath = false; // turn off dynamic finding of DDs
      AspectManager.suppressTransformationErrors = suppressTransformationErrors;
      if (enableTransformer && enableLoadtimeWeaving) throw new RuntimeException("Cannot set both EnableTransformer and EnableLoadtimeWeaving");
      if (enableTransformer)
      {
         attachDeprecatedTranslator();
      }
      if (enableLoadtimeWeaving)
      {
         attachTranslator();
      }
      AspectManager.setClassLoaderScopingPolicy(integration.createAOPClassLoaderScopingPolicy());

      deployBootstrapXml();
      deployBaseXml();
   }
   
   public void start() throws Exception
   {
      started = true;
   }

   public void stop()
   {
      started = false;
   }
   
   public void destroy()
   {
      try
      {
         PortableAnnotationElement.setClosingDownManager(true);
         undeployBaseXml();
      }
      catch(Exception e)
      {
         log.error("Problem destroying AspectManager", e);
      }
      try
      {
         undeployBootstrapXml();
      }
      catch (Exception e)
      {
         log.error("Problem destroying AspectManager", e);
      }
   }
   
   public void deployBaseXml()
   {
      if (!deployedBaseXml && useBaseXml)
      {
         URL base = baseXmlUrl();
         try
         {
            if (base != null)
            {
               log.debug("Deploying base aspects " + base);
               ClassLoader cl = SecurityActions.getClassLoader(this.getClass());
               AspectXmlLoader.deployXML(base, cl, manager);
               deployedBaseXml = true;
            }
         }
         catch (Exception e)
         {
            throw new RuntimeException("Error loading " + baseXml + " file" + e);
         }
      }
   }
   
   public void undeployBaseXml()
   {
      if (deployedBaseXml)
      {
         URL base = baseXmlUrl();
         try
         {
            if (base != null)
            {
               log.debug("Undeploying base aspects " + base);
               AspectXmlLoader.undeployXML(base, manager);
            }
         }
         catch (Exception e)
         {
            log.warn("Error loading " + baseXml + " file" + e);
         }
         deployedBaseXml = false;
      }
   }
   
   private URL baseXmlUrl()
   {
      if (baseXml == null || baseXml.trim().length() == 0)
      {
         return null;
      }

      ClassLoader cl = SecurityActions.getClassLoader(this.getClass());
      URL base = cl.getResource(baseXml);
      if (base == null)
      {
         log.debug("Could not find " + baseXml + " file in the resources of " + cl);
      }
      return base;
   }
   
   private void deployBootstrapXml()
   {
      try
      {
         Document doc = bootstrapXmlDocument();
         if (doc != null)
         {
            AspectXmlLoader loader = new AspectXmlLoader();
            loader.setManager(manager);
            loader.deployXML(doc, null);
         }
      }
      catch(Exception e)
      {
         throw new RuntimeException("Error deploying bootstrap xml", e);
      }
   }
   
   private void undeployBootstrapXml()
   {
      try
      {
         Document doc = bootstrapXmlDocument();
         if (doc != null)
         {
            AspectXmlLoader loader = new AspectXmlLoader();
            loader.setManager(manager);
            loader.undeployXML(doc, null);
         }
      }
      catch(Exception e)
      {
         log.warn("Error undeploying bootstrap xml", e);
      }
   }

   
   private Document bootstrapXmlDocument() throws IOException, SAXException, ParserConfigurationException
   {
      if (bootstrapXml != null && bootstrapXml.trim().length() > 0)
      {
         InputStream in = new BufferedInputStream(new ByteArrayInputStream(bootstrapXml.getBytes()));
         try
         {
            Document doc = AspectXmlLoader.loadDocument(new BufferedInputStream(in));
            return doc;
         }
         finally
         {
            if (in != null)
            {
               in.close();
            }
         }
      }
      return null;
   }
   protected void attachDeprecatedTranslator()
   {
      log.warn("EnableTransformer has been deprecated, please use EnableLoadtimeWeaving.  See docs for more details");
      initIntegration().attachDeprecatedTranslator();
   }

   protected void detachDeprecatedTranslator()
   {
      initIntegration().detachDeprecatedTranslator();
   }

   /*
    * Despite its name, this is also used for the JRockit transformer
    */
   protected void attachTranslator()
   {
      //Despite its name, this is also used for the JRockit transformer
      JDK14TransformerManager.transformer = new JDK14Transformer()
      {
         public byte[] transform(ClassLoader loader, String classname, byte[] classBytes)
         {
            try
            {
               //Make sure that we use the correct classloader, in order to get the correct domain if it is a scoped loader
               return AspectManager.instance(loader).translate(classname, loader, classBytes);
            }
            catch (Exception e)
            {
               throw new RuntimeException("Error converting " + classname + " on " + loader, e);
            }
         }
      };
   }

   protected void detachTranslator()
   {
      JDK14TransformerManager.transformer = null;
   }

   public boolean getPrune()
   {
      return AspectManager.getPrune();
   }

   public void setPrune(boolean prune)
   {
      AspectManager.setPrune(prune);
   }

   public String getExclude()
   {
      return exclude;
   }

   public void setExclude(String exclude)
   {
      this.exclude = exclude;
      ArrayList<String> list = new ArrayList<String>();
      if (exclude != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(exclude, ",");
         while (tokenizer.hasMoreTokens())
         {
            list.add(tokenizer.nextToken().trim());
         }
      }
      manager.setExclude(list);
   }

   public String getInclude()
   {
      return include;
   }

   public void setInclude(String include)
   {
      this.include = include;
      ArrayList<String> list = new ArrayList<String>();
      if (include != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(include, ",");
         while (tokenizer.hasMoreTokens())
         {
            list.add(tokenizer.nextToken().trim());
         }
      }
      manager.setInclude(list);
   }

   public String getIgnore()
   {
      return ignore;
   }

   public void setIgnore(String ignore)
   {
      this.ignore = ignore;
      ArrayList<String> list = new ArrayList<String>();
      if (ignore != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(ignore, ",");
         while (tokenizer.hasMoreTokens())
         {
            list.add(tokenizer.nextToken().trim());
         }
      }
      manager.setIgnore(list);
   }
   
   public String getIncludedInvisibleAnnotations()
   {
      return includedInvisibleAnnotations;
   }
   
   public void setIncludedInvisibleAnnotations(String ia)
   {
      List<String> iiaList = new ArrayList<String>();
      if(ia != null)
      {
         for(String inc : ia.split(","))
           iiaList.add(inc.trim());
      }
      manager.setIncludedInvisibleAnnotations(iiaList);
   }


   /**
    * The temporary directory to which dyn class files are written
    */
   public File getTmpClassesDir()
   {
      return tmpClassesDir;
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public void setTmpClassesDir(File tmpClassesDir)
   {
      this.tmpClassesDir = tmpClassesDir;
   }

   /**
    * Set the verbosity of aop logging.  It doesn't use log4j
    */
   public boolean getVerbose()
   {
      return AspectManager.verbose;
   }

   /**
    * Set the verbosity of aop logging.  It doesn't use log4j
    */
   public void setVerbose(boolean verbose)
   {
      AspectManager.verbose = verbose;
   }

   /**
    * Use aop optimizations.  Optional just in case there is a bug
    */
   public boolean getOptimized()
   {
      return AspectManager.optimize;
   }

   /**
    * Use aop optimizations.  Optional just in case there is a bug
    */
   public void setOptimized(boolean verbose)
   {
      AspectManager.optimize = verbose;
   }

   /**
    * Gets whether the base aspects are being deployed
    */
   public boolean getUseBaseXml()
   {
      return useBaseXml;
   }

   /**
    * Set whether the base aspects should be deployed. If called against
    * a running server, will deploy/undeploy the base aspects depending on the 
    * value of the parameter 
    */
   public synchronized void setUseBaseXml(boolean useBaseXml)
   {
      if (useBaseXml != this.useBaseXml)
      {
         this.useBaseXml = useBaseXml;
         if (started)
         {
            if (useBaseXml)
            {
               deployBaseXml();
            }
            else
            {
               undeployBaseXml();
            }
         }
      }
   }

   public boolean getSuppressTransformationErrors()
   {
      return suppressTransformationErrors;
   }

   public void setSuppressTransformationErrors(boolean suppressTransformationErrors)
   {
      this.suppressTransformationErrors = suppressTransformationErrors;
      AspectManager.suppressTransformationErrors = suppressTransformationErrors;
   }

   public boolean getSuppressReferenceErrors()
   {
      return suppressReferenceErrors;
   }

   public void setSuppressReferenceErrors(boolean suppressReferenceErrors)
   {
      this.suppressReferenceErrors = suppressReferenceErrors;
      AspectManager.suppressReferenceErrors = suppressReferenceErrors;
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public boolean getEnableTransformer()
   {
      return enableTransformer;
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public String interceptorFactories()
   {
      Map<String, InterceptorFactory> factories = manager.getInterceptorFactories();
      StringBuffer buffer = new StringBuffer("");
      for (String name : factories.keySet())
      {
         buffer.append(name + "<br>");
      }
      return buffer.toString();
   }

   /**
    * The temporary directory to which dyn class files are written
    */
   public String aspectDefinitions()
   {
      Map<String, AspectDefinition> factories = manager.getAspectDefinitions();
      StringBuffer buffer = new StringBuffer("");
      for (String name : factories.keySet())
      {
         buffer.append(name + "<br>");
      }
      return buffer.toString();
   }

   public String introductions()
   {
      Map<String, InterfaceIntroduction> factories = manager.getInterfaceIntroductions();
      StringBuffer buffer = new StringBuffer("");
      for (String name : factories.keySet())
      {
         buffer.append(name + "<br>");
      }
      return buffer.toString();
   }

   public String stacks()
   {
      Map<String, AdviceStack> factories = manager.getInterceptorStacks();
      StringBuffer buffer = new StringBuffer("");
      for (String name : factories.keySet())
      {
         buffer.append(name + "<br>");
      }
      return buffer.toString();
   }

   public String bindings()
   {
      Map<String, AdviceBinding> factories = manager.getBindings();
      StringBuffer buffer = new StringBuffer("");
      for (String name : factories.keySet())
      {
         AdviceBinding binding = factories.get(name);
         StringBuilder detail = new StringBuilder();
         if (binding != null)
         {
            detail.append(binding.getPointcut());
            detail.append("{");
            InterceptorFactory[] ifactories = binding.getInterceptorFactories();
            for (InterceptorFactory ifactory : ifactories)
            {
               detail.append(ifactory.getName());
            }
            detail.append("}");
         }
         buffer.append("<b>" + name + "</b> - " + detail.toString() + "<br>");
      }
      return buffer.toString();
   }

   public String pointcuts()
   {
      Map<String, Pointcut> pointcuts = manager.getPointcuts();
      StringBuffer buffer = new StringBuffer("");
      for (String name : pointcuts.keySet())
      {
         Pointcut pointcut = pointcuts.get(name);
         buffer.append("<b>" + name + "</b> - " + pointcut + "<br>");
      }
      return buffer.toString();
   }

   public String registeredClassLoaders()
   {
      Map<ClassLoader, ClassPool> loaders = AspectManager.getRegisteredCLs();
      StringBuffer buffer = new StringBuffer("");
      for (ClassLoader loader : loaders.keySet())
      {
         buffer.append(loader + "<br>");
      }
      return buffer.toString();
   }

   public void setEnableTransformer(boolean enableTransformer)
   {
      // Testsuite uses enableTransformer, we may be testing new loadtime features though.

      if (this.enableTransformer == enableTransformer) return;
      if (enableLoadtimeWeaving && enableTransformer)
      {
         log.warn("enabledLoadtimeWeaving alread set");
         return;
      }
      if (started)
      {
         if (enableTransformer)
         {
            attachDeprecatedTranslator();
         }
         else
         {
            detachDeprecatedTranslator();
         }
      }
      this.enableTransformer = enableTransformer;
   }

   public boolean getEnableLoadtimeWeaving()
   {
      return enableLoadtimeWeaving;
   }

   public void setEnableLoadtimeWeaving(boolean enableTransformer)
   {
      if (this.enableLoadtimeWeaving == enableTransformer) return;
      if (enableLoadtimeWeaving && enableTransformer)
      {
         log.warn("enableTransformer already set");
      }
      if (started)
      {
         if (enableTransformer)
         {
            attachTranslator();
         }
         else
         {
            detachTranslator();
         }
      }
      this.enableLoadtimeWeaving = enableTransformer;
   }

   public String getInstrumentor()
   {
      return InstrumentorFactory.getInstrumentorName();
   }

   public void setInstrumentor(String instrumentor)
   {
      InstrumentorFactory.initialise(instrumentor);
   }

   
   public Logger getLog()
   {
      return log;
   }
   
}
