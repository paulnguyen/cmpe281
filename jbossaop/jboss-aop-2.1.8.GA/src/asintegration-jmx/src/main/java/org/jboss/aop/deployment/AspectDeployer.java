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
import java.net.URL;
import java.util.Iterator;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.ObjectName;

import org.jboss.aop.AspectAnnotationLoader;
import org.jboss.aop.AspectManager;
import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.asintegration.jboss4.ScopedRepositoryClassLoaderHelper;
import org.jboss.deployment.DeploymentException;
import org.jboss.deployment.DeploymentInfo;
import org.jboss.deployment.DeploymentState;
import org.jboss.deployment.SubDeployer;
import org.jboss.deployment.SubDeployerSupport;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.util.file.ArchiveBrowser;
import org.jboss.util.file.ClassFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Deployer for Aspects
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:dimitris@jboss.org">Dimitris Andreadis</a>
 * @version $Revision: 70841 $
 * @jmx:mbean name="jboss.aspect:AspectDeployer"
 * extends="org.jboss.deployment.SubDeployerMBean"
 */
public class AspectDeployer
extends SubDeployerSupport
implements SubDeployer, AspectDeployerMBean
{

   /**
    * Default CTOR used to set default values to the Suffixes and RelativeOrder
    * attributes. Those are read at subdeployer registration time by the MainDeployer
    * to alter its SuffixOrder.
    */
   public AspectDeployer()
   {
      initializeMainDeployer();
   }

   /**
    * Set the suffixes and relative order attributes.
    *
    * Those are read at subdeployer registration time by the MainDeployer
    * to update its SuffixOrder list.
    */
   protected void initializeMainDeployer()
   {
      setSuffixes(new String[]{".aop", "-aop.xml"});
      setRelativeOrder(100);
   }

   /**
    * Returns true if this deployer can deploy the given DeploymentInfo.
    *
    * @return True if this deployer can deploy the given DeploymentInfo.
    * @jmx:managed-operation
    */
   public boolean accepts(DeploymentInfo di)
   {
      String urlStr = di.url.toString();
      return urlStr.endsWith(".aop") || urlStr.endsWith(".aop/") ||
      urlStr.endsWith("-aop.xml");
   }

   /**
    * Describe <code>init</code> method here.
    *
    * @param di a <code>DeploymentInfo</code> value
    * @throws DeploymentException if an error occurs
    * @jmx:managed-operation
    */
   public void init(DeploymentInfo di)
   throws DeploymentException
   {
      try
      {
         if (di.watch == null)
         {
            // resolve the watch
            if (di.url.getProtocol().equals("file"))
            {
               File file = new File(di.url.getFile());

               // If not directory we watch the package
               if (!file.isDirectory())
               {
                  di.watch = di.url;
               }
               // If directory we watch the xml files
               else
               {
                  di.watch = new URL(di.url, "META-INF/jboss-aop.xml");
               }
            }
            else
            {
               // We watch the top only, no directory support
               di.watch = di.url;
            }
         }
      }
      catch (Exception e)
      {
         log.error("failed to parse AOP document: ", e);
         throw new DeploymentException(e);
      }
      super.init(di);
   }

   /**
    * Describe <code>create</code> method here.
    *
    * @param di a <code>DeploymentInfo</code> value
    * @throws DeploymentException if an error occurs
    * @jmx:managed-operation
    */
   public void create(DeploymentInfo di)
   throws DeploymentException
   {
      ClassLoader old = Thread.currentThread().getContextClassLoader();
      try
      {
         URL docURL = getDocUrl(di);
         ClassLoader scl = getScopedClassLoader(di, docURL);

         if (scl != null)
         {
            log.info("AOP deployment is scoped using classloader " + scl);
         }

         Thread.currentThread().setContextClassLoader(di.ucl);
         AspectManager manager = (scl != null) ? AspectManager.instance(scl) : AspectManager.instance();
         if (!di.isXML)
         {
            @SuppressWarnings("unchecked")
            Iterator it = ArchiveBrowser.getBrowser(di.localUrl, new ClassFileFilter());
            AspectAnnotationLoader loader = new AspectAnnotationLoader(manager);
            loader.setClassLoader(scl);
            loader.deployInputStreamIterator(it);
         }

         AspectXmlLoader.deployXML(docURL, scl, manager);
         Notification msg = new Notification("AOP Deploy", this, getNextNotificationSequenceNumber());
         sendNotification(msg);
         log.debug("Deployed AOP: " + di.url);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         throw new DeploymentException(ex);
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(old);
      }
   }

   /**
    * The <code>start</code> method starts all the mbeans in this DeploymentInfo..
    *
    * @param di a <code>DeploymentInfo</code> value
    * @throws DeploymentException if an error occurs
    * @jmx:managed-operation
    */
   public void start(DeploymentInfo di) throws DeploymentException
   {
   }

   /**
    * Undeploys the package at the url string specified. This will: Undeploy
    * packages depending on this one. Stop, destroy, and unregister all the
    * specified mbeans Unload this package and packages this package deployed
    * via the classpath tag. Keep track of packages depending on this one that
    * we undeployed so that they can be redeployed should this one be
    * redeployed.
    *
    * @param di the <code>DeploymentInfo</code> value to stop.
    * @jmx:managed-operation
    */
   public void stop(DeploymentInfo di)
   //throws DeploymentException
   {
      // Can happen on shutdown with a nested .aop module
      // which is first stopped then undeployed.
      if (di.state != DeploymentState.STARTED)
      {
         log.debug("Ignoring request to stop '" + di.url + "', current state: " + di.state);
         return;
      }

      log.debug("undeploying document " + di.url);
      ClassLoader old = Thread.currentThread().getContextClassLoader();
      try
      {
         Thread.currentThread().setContextClassLoader(di.ucl);
         if (!di.isXML)
         {
            @SuppressWarnings("unchecked")
            Iterator it = ArchiveBrowser.getBrowser(di.localUrl, new ClassFileFilter());
            AspectAnnotationLoader loader = new AspectAnnotationLoader(AspectManager.instance());
            loader.undeployInputStreamIterator(it);

         }

         URL docURL = getDocUrl(di);
         //long start = System.currentTimeMillis();
         AspectXmlLoader.undeployXML(docURL);
         AspectManager.instance().unregisterClassLoader(di.ucl);

         /*
//         System.out.println("************************");
//         System.out.println("undeploy took: " + (System.currentTimeMillis() - start));
//         System.out.println("************************");
         */
         Notification msg = new Notification("AOP Undeploy", this, getNextNotificationSequenceNumber());
         sendNotification(msg);
      }
      catch (Exception ex)
      {
         log.error("failed to stop", ex);
      }
      finally
      {
         Thread.currentThread().setContextClassLoader(old);
      }
   }

   /**
    * Describe <code>destroy</code> method here.
    *
    * @param di a <code>DeploymentInfo</code> value
    * @jmx:managed-operation
    */
   public void destroy(DeploymentInfo di)
   //throws DeploymentException
   {
   }

   /**
    * The startService method gets the mbeanProxies for MainDeployer
    * and ServiceController, used elsewhere.
    *
    * @throws Exception if an error occurs
    */
   protected void startService() throws Exception
   {
      super.startService();
   }

   protected ObjectName getObjectName(MBeanServer server, ObjectName name)
   throws MalformedObjectNameException
   {
      return name == null ? OBJECT_NAME : name;
   }

   private URL getDocUrl(DeploymentInfo di) throws DeploymentException
   {
      URL docURL = di.localUrl;
      if (di.isXML == false)
         docURL = di.localCl.findResource("META-INF/jboss-aop.xml");
      // Validate that the descriptor was found
      if (docURL == null)
         throw new DeploymentException("Failed to find META-INF/jboss-aop.xml");
      return docURL;
   }

   private ClassLoader getScopedClassLoader(DeploymentInfo di, URL docUrl) throws Exception
   {
      //Scoped AOP deployments are only available when deployed as part of a scoped sar, ear etc.
      //It can contain an aop.xml file, or it can be part of a .aop file
      //Linking a standalone -aop.xml file onto a scoped deployment is not possible at the moment
      if (ScopedRepositoryClassLoaderHelper.isScopedClassLoader(di.ucl))
      {
         return di.ucl;
      }

      LoaderRepository attachToRepository = getLoaderRepositoryIfAttaching(di, docUrl);
      if (attachToRepository != null)
      {
         di.ucl.setRepository(attachToRepository);
         attachToRepository.addClassLoader(di.ucl);
         return di.ucl;
      }
      return null;
   }

   private LoaderRepository getLoaderRepositoryIfAttaching(DeploymentInfo di, URL docUrl) throws Exception
   {
      if (di.parent == null)
      {
         //We are a top-level deployment, check if we are meant to be attaching to a scoped repository

         Document doc = AspectXmlLoader.loadURL(docUrl);
         Element top = doc.getDocumentElement();
         NodeList children = top.getChildNodes();
         int childlength = children.getLength();
         for (int i = 0; i < childlength ; i++)
         {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE)
            {
               Element element = (Element)child;
               if (element.getTagName().equals("loader-repository"))
               {
                  String repositoryName = null;
                  NodeList nestedChildren = child.getChildNodes();
                  int nestedChildLength = nestedChildren.getLength();
                  for (int j = 0 ; j < nestedChildLength ; j++)
                  {
                     Node nestedChild = nestedChildren.item(j);
                     if (nestedChild.getNodeType() == Node.TEXT_NODE || nestedChild.getNodeType() == Node.CDATA_SECTION_NODE)
                     {
                        repositoryName = nestedChild.getNodeValue().trim();
                        break;
                     }
                  }
                  log.info("Should attach deployment to loader repository " + repositoryName);
                  ObjectName on;
                  try
                  {
                     on = new ObjectName(repositoryName);
                  }
                  catch (MalformedObjectNameException e)
                  {
                     throw new RuntimeException("loader-repository='" + repositoryName + "' is not a valid object name", e);
                  }

                  try
                  {
                     @SuppressWarnings("unused")
                     MBeanInfo info = server.getMBeanInfo(on);
                  }
                  catch (InstanceNotFoundException e)
                  {
                     log.warn("No scoped loader repository exists with the name " + on);
                  }

                  LoaderRepository repository = (LoaderRepository)server.getAttribute(on, "Instance");
                  if (repository instanceof HeirarchicalLoaderRepository3)
                  {
                     return repository;
                  }
                  else
                  {
                     log.warn("Loader repository " + on + " is not a isolated loader repository. Deploying aspects in default domain.");
                  }
               }
            }
         }
      }

      return null;
   }
}