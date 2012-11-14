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
package org.jboss.aop.asintegration.jboss4;

import java.net.URL;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.LoaderRepository;
import org.jboss.mx.util.MBeanServerLocator;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class LoaderRepositoryUrlUtil implements NotificationListener
{
   final static MBeanServer SERVER;
   final static ObjectName MAIN_LOADER_REPOSITORY_OBJECT_NAME;
   final static LoaderRepository MAIN_LOADER_REPOSITORY;
   static
   {
      SERVER = MBeanServerLocator.locateJBoss();
      try
      {
         MAIN_LOADER_REPOSITORY_OBJECT_NAME = new ObjectName("JMImplementation:name=Default,service=LoaderRepository");
         MAIN_LOADER_REPOSITORY = (LoaderRepository)SERVER.invoke(MAIN_LOADER_REPOSITORY_OBJECT_NAME, "getInstance", new Object[0], new String[0]);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
   long currentSequenceNumber;
   long lastSequenceNumber = -1;
   URL[] urls;
   
   public LoaderRepositoryUrlUtil()
   {
      try
      {
         SERVER.addNotificationListener(MAIN_LOADER_REPOSITORY_OBJECT_NAME, this, null, null);
      }
      catch (InstanceNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   public synchronized void handleNotification(Notification notification, Object handback)
   {
      if (notification.getType().equals(LoaderRepository.CLASSLOADER_ADDED))
      {
         currentSequenceNumber = notification.getSequenceNumber();
      }
      else if (notification.getType().equals(LoaderRepository.CLASSLOADER_REMOVED))
      {
         currentSequenceNumber = notification.getSequenceNumber();
      }
   }
   
   public synchronized UrlInfo getURLInfo(HeirarchicalLoaderRepository3 scopedLoader, UrlInfo urlInfo)
   {
      boolean changed = false;
      if (lastSequenceNumber != currentSequenceNumber)
      {
         urls = MAIN_LOADER_REPOSITORY.getURLs();
         lastSequenceNumber = currentSequenceNumber;
         changed = true;
      }
      if (!changed)
      {
         changed = urlInfo != null && (urlInfo.getSequenceNumber() != lastSequenceNumber);
      }
      if (urlInfo == null || changed)
      {
         URL[] localUrls = getLocalUrls(scopedLoader, urls);
         urlInfo = new UrlInfo(urls, localUrls, lastSequenceNumber);
      }
      return urlInfo;
   }
   
   public long getCurrentSequenceNumber()
   {
      return currentSequenceNumber;
   }
   
   private URL[] getLocalUrls(HeirarchicalLoaderRepository3 scopedRepository, URL[] globalUrls)
   {
      URL[] scopedRepositoryUrls = scopedRepository.getURLs();

      //This is a bit of a hack, since this relies on the order of the urls returned by HeirarchicalLoaderRepository3
      //My urls, followed by parent urls.
      int scopedLength = 0;
      for (int i = 0 ; i < scopedRepositoryUrls.length ; i++)
      {
         for (int j = 0 ; j < globalUrls.length ; j ++)
         {
            if (scopedRepositoryUrls[i].equals(globalUrls[j]))
            {
               scopedLength = i;
               break;
            }
         }
         if (scopedLength > 0)
         {
            break;
         }
      }
      
      URL[] localUrls = new URL[scopedLength];
      System.arraycopy(scopedRepositoryUrls, 0, localUrls, 0, scopedLength);
      return localUrls;
   }
   
   public class UrlInfo
   {
      
      URL[] globalUrls;
      URL[] localUrls;
      long sequenceNumber;

      public UrlInfo(URL[] globalUrls, URL[] localUrls, long sequenceNumber)
      {
         super();
         this.globalUrls = globalUrls;
         this.localUrls = localUrls;
         this.sequenceNumber = sequenceNumber;
      }
      
      public URL[] getGlobalUrls()
      {
         return globalUrls;
      }
      
      public URL[] getLocalUrls()
      {
         return localUrls;
      }

      public long getSequenceNumber()
      {
         return sequenceNumber;
      }
   }
   
}
