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

/**
 * MBean interface.
 */
public interface AbstractAspectManagerServiceMBean {

   /**
    * create the service, do expensive operations etc
    * 
    * @throws Exception for any error
    */
   void create() throws Exception;
   
   /**
    * start the service, create is already called
    * 
    * @throws Exception for any error
    */
   void start() throws Exception;
   
   /**
    * stop the service
    */
   void stop();
   
   /**
    * destroy the service, tear down 
    */
   void destroy();

   /**
    * The temporary directory to which dyn class files are written
    */
  java.io.File getTmpClassesDir() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  void setTmpClassesDir(java.io.File tmpClassesDir) ;

   /**
    * Set the verbosity of aop logging. It doesn't use log4j
    */
  boolean getVerbose() ;

   /**
    * Set the verbosity of aop logging. It doesn't use log4j
    */
  void setVerbose(boolean verbose) ;

   /**
    * Use aop optimizations. Optional just in case there is a bug
    */
  boolean getOptimized() ;

   /**
    * Use aop optimizations. Optional just in case there is a bug
    */
  void setOptimized(boolean verbose) ;

  boolean getSuppressTransformationErrors() ;

  void setSuppressTransformationErrors(boolean suppressTransformationErrors) ;

   /**
    * The temporary directory to which dyn class files are written
    */
  boolean getEnableTransformer() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  boolean getEnableLoadtimeWeaving() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  java.lang.String interceptorFactories() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  java.lang.String aspectDefinitions() ;

  java.lang.String introductions() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  java.lang.String stacks() ;

   /**
    * The temporary directory to which dyn class files are written
    */
  java.lang.String bindings() ;

  java.lang.String pointcuts();
  
   /**
    * The temporary directory to which dyn class files are written
    */
  java.lang.String registeredClassLoaders() ;

   /**
    * The temporary directory to which dyn class files are written
    */
   void setEnableTransformer(boolean enableTransformer) ;
   void setEnableLoadtimeWeaving(boolean enableTransformer) ;

   String getExclude();

   void setExclude(String exclude);

   String getInclude();

   void setInclude(String include);
   
   String getIncludedInvisibleAnnotations();
   
   void setIncludedInvisibleAnnotations(String ia);

   boolean getPrune();

   void setPrune(boolean prune);
   
   String getIgnore();

   void setIgnore(String ignore);
   
   String getInstrumentor();
   
   void setInstrumentor(String instrumentor);

   /**
    * Whether or not tu use base-aop.xml
    */
   boolean getUseBaseXml();
   
   /**
    * Whether or not tu use base-aop.xml
    */
   void setUseBaseXml(boolean useBaseXml);
   
   /**
    * Sets the MBean containing the JBossIntegration implementation
    */
   void setJBossIntegrationWrapper(JBossIntegrationWrapperMBean integration);
}
