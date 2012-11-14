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
package org.jboss.aop;

import org.jboss.aop.util.logging.AOPLogger;
import org.jboss.util.file.ArchiveBrowser;
import org.jboss.util.file.ClassFileFilter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77493 $
 */
public class Deployment
{
   private static final AOPLogger logger = AOPLogger.getLogger(Deployment.class);
   
   public static boolean searchClasspath = true;

   public static void deploy()
   {
      try
      {

         deployThroughClassAnnotations();
         preconfigThroughClassPath();
         preconfigThroughSystemProperty();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * This is for running standalone.  AspectManager will automatically search classpath for resources
    * of jboss-aop.xml and load them.  The AspectManagerService will turn this off if you are running
    * from within JBoss.
    */
   public static void preconfigThroughClassPath()
   {
      String search = System.getProperty("jboss.aop.search.classpath", null);
      if (search != null) searchClasspath = (new Boolean(search)).booleanValue();
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("jboss.aop.search.classpath: '" + search + "' " + searchClasspath);
      if (searchClasspath)
      {
         try
         {
            Enumeration<URL> en = SecurityActions.getContextClassLoader().getResources("META-INF/jboss-aop.xml");
            while (en.hasMoreElements())
            {
               URL url = en.nextElement();
               if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("deploying  " + url);
               AspectXmlLoader.deployXML(url);
            }
         }
         catch (Exception ex)
         {
            throw new RuntimeException(ex);
         }
      }
   }

   /**
    * Load XML files through a system property value jboss.aop.path
    */
   public static void deployThroughClassAnnotations()
   {
      String path = System.getProperty("jboss.aop.class.path", null);
      if (path == null)
      {
         if (AspectManager.verbose) logger.debug("jboss.aop.class.path is NULL");
         return;
      }
      if (AspectManager.verbose) logger.debug("jboss.aop.class.path: " + path);
      StringTokenizer t = new StringTokenizer(path, File.pathSeparator);
      while (t.hasMoreTokens())
      {
         String token = t.nextToken();
         File f = new File(token);
         if (!f.exists())
         {
            logger.error("Unable to find jboss.aop.class.path: " + f.getName());
         }
         try
         {
            URL url = f.toURL();
            Iterator<InputStream> it = ArchiveBrowser.getBrowser(url, new ClassFileFilter());
            AspectAnnotationLoader loader = new AspectAnnotationLoader(AspectManager.instance());
            loader.deployInputStreamIterator(it);
         }
         catch (Exception ex)
         {
            ex.printStackTrace();
            if (ex instanceof RuntimeException)
               throw (RuntimeException) ex;
            else
               throw new RuntimeException("[error] failed to load aop class path: " + f.toString(), ex);
         }
      }
   }

   /**
    * Load XML files through a system property value jboss.aop.path
    */
   public static void preconfigThroughSystemProperty()
   {
      String path = System.getProperty("jboss.aop.path", null);
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("jboss.aop.path: " + path);
      if (path == null) return;
      StringTokenizer t = new StringTokenizer(path, File.pathSeparator);
      int j = 0;
      while (t.hasMoreTokens())
      {
         String token = t.nextToken();
         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("jboss.aop.path[" + j + "]: " + token);
         File f = new File(token);
         try
         {
            if (f.isDirectory())
            {
               FilenameFilter filter = new FilenameFilter()
               {
                  public boolean accept(File dir, String name)
                  {
                     return name.endsWith("aop.xml");
                  }
               };
               File[] files = f.listFiles(filter);
               for (int i = 0; i < files.length; i++)
               {
                  deployXmlFile(files[i]);
               }
            }
            else
            {
               deployXmlFile(f);
            }
         }
         catch (Exception ex)
         {
            ex.printStackTrace();
            if (ex instanceof RuntimeException)
               throw (RuntimeException) ex;
            else
               throw new RuntimeException("[error] failed to load aop path: " + f.toString(), ex);
         }
      }
   }

   private static void deployXmlFile(File f) throws Exception
   {
      // use toURI().toURL() because of bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4273532
      URL url = f.toURI().toURL();
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("deploying " + url);
      AspectXmlLoader.deployXML(url);
   }
}
